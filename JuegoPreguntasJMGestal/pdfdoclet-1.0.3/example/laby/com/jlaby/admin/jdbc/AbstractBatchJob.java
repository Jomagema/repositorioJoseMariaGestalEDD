package com.jlaby.admin.jdbc;

/*
 * @(#)AbstractBatchJob.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.sql.*;
import java.util.*;
//import oracle.jdbc.driver.*;

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
 * @version $Id: AbstractBatchJob.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public abstract class AbstractBatchJob implements ILabyConstants {

    /**
     * Default constructor. It also reads the "configuration.properties"
     * so that the batch program has access to the same configuration values
     * as the Laby server has.
     */
    public AbstractBatchJob() {
        String configFile = System.getProperty(PROP_CONFIG_FILE, 
                                               "/com/jlaby/config/configuration.properties");
        Configuration.load(configFile);
        Log.initialize();
    }

    /**
     * Initialisation method where the batch program
     * might initialise any properties and values based
     * on the commandline arguments.
     *
     * @param args the commandline arguments
     * @exception java.lang.Exception
     */
    public abstract void init(String[] args) throws Exception;

    /**
     * The "worker" method which must contain the actual
     * logic of the batch job.
     *
     * @exception java.lang.Exception
     */
    public abstract void run() throws Exception;

    /**
     * This method should be called in the main() method
     * of the subclass; it kicks off the batch job process
     * by first calling the "init()" method and then the
     * "run()" method.
     *
     * @param args the commandline arguments.
     */
    public final void process(String[] args) {
        try {

            Log.info(this, "process", "Initialize.");
            init(args);

            Log.info(this, "process", "Create database connection.");
            JdbcUtil.createConnection();

            Log.info(this, "process", "Run batch processing...");
            run();

            Log.info(this, "process", "Close database connection.");
            JdbcUtil.dispose();

        } catch(Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
}
