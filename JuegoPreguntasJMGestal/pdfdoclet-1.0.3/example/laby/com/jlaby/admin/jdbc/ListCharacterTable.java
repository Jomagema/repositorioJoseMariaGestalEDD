package com.jlaby.admin.jdbc;

/*
 * @(#)ListCharacterTable.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.sql.*;

import com.jlaby.jdbc.*;
import com.jlaby.log.*;

/**
 * This class lists all the entries in the LABY_CHARACTER table.
 *
 * @author  Marcel Schoen
 * @version $Id: ListCharacterTable.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class ListCharacterTable extends AbstractBatchJob {

    /**
     * Main entry method.
     *
     * @param args the commandline arguments.
     */
    public static void main(String[] args) {
        ListCharacterTable batchJob = new ListCharacterTable();
        batchJob.process(args);
    }

    /**
     * Initialisation (not used here).
     *
     * @param args the commandline arguments
     */
    public void init(String[] args) throws Exception {
    }

    /**
     * Carries out the database operations.
     */
    public void run() throws Exception {
        Log.info(this, "run", "List all records in LABY_CHARACTER...");
        ResultSet result = JdbcUtil.executeQuery("SELECT * FROM UNIT_TLIB.LABY_CHARACTER");
        int number = 0;
        while(result.next()) {
            number++;
        }
        Log.info(this, "run", "Number of records: "+number);
    }
}
