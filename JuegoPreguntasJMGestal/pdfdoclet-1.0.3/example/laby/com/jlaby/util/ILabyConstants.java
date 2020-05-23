/*
 * @(#)ILabyConstants.java
 *
 * @Copyright: Marcel Schoen, Switzerland, 2002, All Rights Reserved.
 */

package com.jlaby.util;

import java.util.Properties;
import java.io.InputStream;

/**
 * This interface contains constants used throughout the
 * whole rest of the sources in order to avoid hard-coded
 * strings (when referencing a property name, for instance).
 * This makes later changes easier and the code more robust.
 *
 * @author Marcel Schoen, TARSEC AG
 * @version $Id: ILabyConstants.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public interface ILabyConstants {

    /**
     * A String value representing a boolean value TRUE
     */
    public final static String TRUE = "true";

    /**
     * A String value representing a boolean value FALSE
     */
    public final static String FALSE = "false";

    /**
     * The property specifying the port for the server listener
     */
    public final static String PROP_SERVER_PORT = "server.port";

    /**
     * The property specifying the path of the configuration properties file.
     */
    public final static String PROP_CONFIG_FILE = "config.file";

    /**
     * The property specifying the directory for the JLog log files.
     */
    public final static String PROP_LOG_DIR = "log.dir";

    /**
     * The property specifying the name of the trace log file.
     */
    public final static String PROP_LOG_TRACE = "log.trace";

    /**
     * The property specifying the name of the standard log messages file.
     */
    public final static String PROP_LOG_FILE = "log.file";

    /**
     * The property specifying the JLog name of the Logger instance.
     */
    public final static String PROP_LOG_NAME = "log.name";

    /**
     * The property specifying the description for the Logger instance.
     */
    public final static String PROP_LOG_DESC = "log.desc";

    /**
     * The property specifying the SQL statement for loading a user's state.
     */
    public final static String PROP_SQL_UNIQUEID_MCKOI = "sql.query.uniqueid.mckoi";

    /**
     * The property specifying the SQL statement for loading a user's state.
     */
    public final static String PROP_SQL_UNIQUEID_ORACLE = "sql.query.uniqueid.oracle";

    /**
     * The property specifying the SQL statement for loading a user's state.
     */
    public final static String PROP_SQL_QUERY_LOADUSER = "sql.query.loaduserdata";

    /**
     * The property specifying the SQL statement for saving a user's state.
     */
    public final static String PROP_SQL_QUERY_SAVEUSER = "sql.query.saveuserdata";

    /**
     * The property specifying the SQL statement for creating data for a new user.
     */
    public final static String PROP_SQL_QUERY_CREATEUSER = "sql.query.createuserdata";

    /**
     * The property specifying the SQL statement for retrieving data about a user's view.
     */
    public final static String PROP_SQL_QUERY_GETVIEW = "sql.query.getviewusers";

    /**
     * The property specifying the SQL statement for creating a new unique id.
     */
    public final static String PROP_SQL_NEWUNIQUEID = "sql.query.newuniqueid";

    /**
     * The property specifying the name of the vendor of the database used.
     */
    public final static String PROP_DB_VENDOR = "database.vendor";

    /**
     * The property specifying the name of the ORACLE sequence object used to create unique id's.
     */
    public final static String PROP_DB_ORACLE_SEQNAME = "oracle.uniqueid.sequence.name";

    /**
     * The property specifying the JDBC driver class.
     */
    public final static String PROP_JDBC_DRIVER_CLASS = "jdbc.driver.class";

    /**
     * The property specifying the JDBC driver URL.
     */
    public final static String PROP_JDBC_DRIVER_URL = "jdbc.driver.url";


    /*
     * Some constants for the com.jlaby.html.* classes
     */


    /**
     * The property specifying the URL used to get to the "Log in" page.
     */
    public final static String PROP_URL_LOGIN = "url.gui.login";

    /**
     * The property specifying the URL used to get to the "Create user" page.
     */
    public final static String PROP_URL_CREATE = "url.gui.createuser";

    /**
     * The property specifying the URL used to get to the laby game.
     */
    public final static String PROP_URL_LABY = "url.laby";


    /*
     * Some constants for the com.jlaby.client.handler.BrowserClientHandler
     */


    /**
     * The property specifying the path of the HTML template for the inventory page.
     */
    public final static String PROP_HTML_INVENTORY = "inventory.html.template";

    /**
     * The property specifying the path of the HTML template for the laby game page.
     */
    public final static String PROP_HTML_MAINPANEL = "mainpanel.html.template";

    /**
     * The property specifying the path of the HTML template for the "Log in" page.
     */
    public final static String PROP_HTML_LOGIN = "login.user.html.template";

    /**
     * The property specifying the path of the HTML template for the "Create new user" page.
     */
    public final static String PROP_HTML_CREATE = "create.user.html.template";
   
}
