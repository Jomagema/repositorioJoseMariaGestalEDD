package com.jlaby.admin.jdbc;

/*
 * @(#)BatchJob.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.sql.*;
import java.util.*;
import java.io.*;

import com.jlaby.exception.*;
import com.jlaby.jdbc.*;
import com.jlaby.jdbc.exception.*;
import com.jlaby.config.*;
import com.jlaby.log.*;
import com.jlaby.util.*;

/**
 * Base class used to create batch programs for
 * database administration and maintenance.
 *
 * @author  Marcel Schoen
 * @version $Id: BatchJob.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class BatchJob extends AbstractBatchJob {

    private String[] statements = new String[100];

    /**
     * Initialisation method where the batch program
     * might initialise any properties and values based
     * on the commandline arguments.
     *
     * @param args the commandline arguments
     * @exception java.lang.Exception
     */
    public void init(String[] args) throws Exception {
        String sqlFile = args[0];
        if(sqlFile == null) {
            throw new IllegalArgumentException("First commandline argument must be an SQL file!");
        }
        File test = new File(sqlFile);
        if(test.exists() == false || test.isDirectory()) {
            throw new IllegalArgumentException("Specified SQL file is missing or directory!");
        }
        BufferedReader rd = new BufferedReader(new FileReader(test));
        int ct = 0;
        String line = null, statement = "";
        while((line = rd.readLine()) != null) {
            statement = statement + " " + line.trim();
            if(statement.endsWith(";")) {
                statement = statement.trim();
                statements[ct] = statement.substring(0, statement.lastIndexOf(";"));
                System.out.println("> SQL Statement: "+statements[ct]);
                ct++;
                statement = "";
            }
        }
        System.out.println("> Number of SQL statements: "+ct);
    }

    /**
     * The "worker" method which must contain the actual
     * logic of the batch job.
     *
     * @exception java.lang.Exception
     */
    public void run() throws Exception {
        Log.info(this, "run", "Begin to process SQL statements...");

        System.out.println("Begin SQL stuff...");
        for(int i=0; i<statements.length; i++) {
            if(statements[i] != null) {
                System.out.println("Execute: "+statements[i]);
                Log.info(this, "run", "> execute: "+statements[i]);
                try {
                    JdbcUtil.executeQuery(statements[i]);
                    System.out.println("Query executed.");
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.warning(this, "run", "Ignoring exception: " + e);
                }
            }
        }
        System.out.println("Finished.");
        Log.info(this, "run", "Finished.");
    }


    /**
     * Main entry method.
     *
     * @param args the commandline arguments.
     */
    public static void main(String[] args) {
        BatchJob batchJob = new BatchJob();
        batchJob.process(args);
        System.exit(0);
    }
}
