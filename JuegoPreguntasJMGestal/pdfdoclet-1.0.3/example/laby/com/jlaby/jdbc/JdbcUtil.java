package com.jlaby.jdbc;

/*
 * @(#)JdbcUtil.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.sql.*;
import java.util.*;
//import oracle.jdbc.driver.*;

import com.jlaby.character.*;
import com.jlaby.exception.*;
import com.jlaby.jdbc.exception.*;
import com.jlaby.config.*;
import com.jlaby.log.*;
import com.jlaby.util.*;

/**
 * Helper class for database access.
 *
 * @author  Marcel Schoen
 * @version $Id: JdbcUtil.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class JdbcUtil {

    // **************************************************
    // VARIABLES
    // **************************************************
    private static Connection ms_connection = null;
    private static boolean ms_connected = false;
    private static IUniqueIdGenerator ms_idGenerator = null;

    private static PreparedStatement ms_prepLoadUserSt = null;
    private static PreparedStatement ms_prepSaveUserSt = null;
    private static PreparedStatement ms_prepCreateUserSt = null;
    private static PreparedStatement ms_prepGetViewCharacters = null;

    /**
     * Determines if there is currently a connection
     * to the database or not.
     *
     * @return true if the connection exists.
     */
    public static boolean isConnected() {
        return ms_connected;
    }

    /**
     * This method is used to determine which characters are in the
     * viewing area of the current character. It "scans" a certain
     * area for other characters.
     *
     * @param leftX The "upper left" x-coordinate (column) of the rectangular area
     * @param leftY The "upper left" y-coordinate (row) of the rectangular area
     * @param rightX The "lower right" x-coordinate (column) of the rectangular area
     * @param rightY The "lower right" y-coordinate (row) of the rectangular area
     * @return An array of characters found in that area. The array has
     *         a length of 0, if no characters had been found.
     * @exception LabyException Thrown in case of DB problems
     */
    public static GameCharacter[] getCharacters(int leftX, int leftY, int rightX, int rightY) {
        try {
            Log.info("JdbcUtil", "getCharacters", "Look for characters in area "+leftX+","+leftY+" - "+rightX+","+rightY);
            GameCharacter[] characters = null;
            ms_prepGetViewCharacters.setInt(1, leftX);
            ms_prepGetViewCharacters.setInt(2, leftY);
            ms_prepGetViewCharacters.setInt(3, rightX);
            ms_prepGetViewCharacters.setInt(4, rightY);
            ResultSet rs = ms_prepGetViewCharacters.executeQuery();
            Vector objects = new Vector();

            int counter = 0;
            while(rs.next()) {
                GameCharacter character = new GameCharacter();
                character.setID(rs.getInt("ID"));
                character.setName(rs.getString("NAME"));
                character.setAlias(rs.getString("ALIAS"));
                character.setX(rs.getInt("POSITIONX"));
                character.setY(rs.getInt("POSITIONY"));
                character.setZ(rs.getInt("POSITIONZ"));
                character.setDirection(rs.getInt("DIRECTION"));
                character.setState(rs.getInt("STATE"));
                character.setLife(rs.getInt("LIFE"));
                Log.info("JdbcUtil", "getCharacters", "Found character: "+character);
                objects.addElement(character);
                counter++;
            }
            Log.info("JdbcUtil", "getCharacters", "Number of characters found: "+counter);
            if(counter > 0) {
                characters = new GameCharacter[counter];
                for(int i=0; i<characters.length; i++) {
                    characters[i] = (GameCharacter)objects.elementAt(i);
                }
            } else {
                characters = new GameCharacter[0];
            }
            return characters;
        } catch(java.sql.SQLException e) {
            throw new LabySQLException(e);
        }
    }

    /**
     * This simplifying method creates a new user
     * record in the database.
     *
     * @param userID The new users unique name ID
     * @return The character object that represents the character.
     * @exception LabyException Thrown in case of DB problems
     */
    public static GameCharacter createCharacter(String userName) throws LabyException {
        GameCharacter character = null;
        try {
            character = new GameCharacter();
            character.setID(ms_idGenerator.createNewID());
            character.setName(userName);
            character.setAlias("Alias");
            character.setX(10);
            character.setY(10);
            character.setZ(1);
            character.setDirection(1);
            character.setState(1);
            character.setLife(100);

            Log.info("JdbcUtil", "createCharacter", "Create character: "+character.toString());

            ms_prepCreateUserSt.setInt(1, character.getID());
            ms_prepCreateUserSt.setString(2, character.getName());
            ms_prepCreateUserSt.setString(3, character.getAlias());
            ms_prepCreateUserSt.setInt(4, character.getX());
            ms_prepCreateUserSt.setInt(5, character.getY());
            ms_prepCreateUserSt.setInt(6, character.getZ());
            ms_prepCreateUserSt.setInt(7, character.getDirection());
            ms_prepCreateUserSt.setInt(8, character.getState());
            ms_prepCreateUserSt.setInt(9, character.getLife());

            int recNo = ms_prepCreateUserSt.executeUpdate();
            if(recNo != 1) {
                throw new LabySQLException("Number of records saved: "+recNo);
            }
        } catch(java.sql.SQLException e) {
            throw new LabySQLException(e);
        }
        return character;
    }

    /**
     * This simplifying method reads the record of a
     * game character from the database and fills it into
     * an existing GameCharacter object.
     * <P>Please note: Since a game character can both be
     * a human player or a non-player-character (a bot),
     * this method has intentionally not been named "getUser()".
     *
     * @param name The (unique) name of the game character.
     * @return The character object that represents the character.
     * @exception LabyException Thrown in case of DB problems
     */
    public static GameCharacter loadCharacter(String name) throws LabyException {
        try {
            Log.info("JdbcUtil", "loadCharacter", "Read data for "+name);
            ms_prepLoadUserSt.setString(1, name);
            Log.info("JdbcUtil", "loadCharacter", "Execute query...");
            ResultSet rs = ms_prepLoadUserSt.executeQuery();
            if (rs.next()) {
                GameCharacter character = new GameCharacter();
                character.setID(rs.getInt("ID"));
                character.setName(rs.getString("NAME"));
                character.setAlias(rs.getString("ALIAS"));
                character.setX(rs.getInt("POSITIONX"));
                character.setY(rs.getInt("POSITIONY"));
                character.setZ(rs.getInt("POSITIONZ"));
                character.setDirection(rs.getInt("DIRECTION"));
                character.setState(rs.getInt("STATE"));
                character.setLife(rs.getInt("LIFE"));
                Log.info("JdbcUtil", "loadCharacter", "Loaded character: "+character);
                return character;
            } else {
                Log.error("JdbcUtil", "loadCharacter", "No record found for "+name);
                throw new NoRecordFoundException("No record for user: "+name);
            }
        } catch(java.sql.SQLException e) {
            throw new LabySQLException(e);
        }
    }

    /**
     * This simplifying method saves a GameCharacter into
     * an existing record in the database.
     *
     * @param character The character object that should be saved
     * @exception LabyException Thrown in case of DB problems
     */
    public static void saveCharacter(GameCharacter character) throws LabyException {
        try {
            ms_prepSaveUserSt.setString(1, character.getAlias());
            ms_prepSaveUserSt.setInt(2, character.getX());
            ms_prepSaveUserSt.setInt(3, character.getY());
            ms_prepSaveUserSt.setInt(4, character.getZ());
            ms_prepSaveUserSt.setInt(5, character.getDirection());
            ms_prepSaveUserSt.setInt(6, character.getState());
            ms_prepSaveUserSt.setInt(7, character.getLife());
            ms_prepSaveUserSt.setInt(8, character.getID());
            Log.info("JdbcUtil", "saveCharacter", "Save character: " + character);
            int recNo = ms_prepSaveUserSt.executeUpdate();
            Log.info("JdbcUtil", "saveCharacter", "Saved records: " + recNo);
            if(recNo < 1) {
                Log.error("JdbcUtil", "saveCharacter", "No record updated for user " + character.getName());
                throw new NoRecordUpdatedException("No record updated for user: " + character.getName());
            }
        } catch(java.sql.SQLException e) {
            throw new LabySQLException(e);
        }
    }

    /**
     * Create a physical connection to the database based
     * on the configuration values in the main configuration file.
     */
    public static void createConnection() {
        Log.info("JdbcUtil", "createConnection", "Read JDBC configuration values...");
        String driverClassName = Configuration.getProperty("jdbc.driver.class");
        Log.info("JdbcUtil", "createConnection", "JDBC Driver class: " + driverClassName);
        String databaseURL = Configuration.getProperty("jdbc.driver.url");
        Log.info("JdbcUtil", "createConnection", "JDBC Database URL: " + databaseURL);
        String databaseUser = Configuration.getProperty("jdbc.user");
        Log.info("JdbcUtil", "createConnection", "JDBC Database user: " + databaseUser);
        String databasePassword = Configuration.getProperty("jdbc.password");
        Log.info("JdbcUtil", "createConnection", "JDBC Database password: " + databasePassword);
        if(driverClassName != null && databaseURL != null) {
            try {
                Class jdbcDriver = Class.forName(driverClassName);
                Log.info("JdbcUtil", "createConnection", "JDBC driver class loaded.");
            } catch(ClassNotFoundException e) {
                Log.error("JdbcUtil", "createConnection", "JDBC driver class not found.");
                ExceptionHandler.handleException(e);
            }

            try {
                Log.info("JdbcUtil", "createConnection", "Trying to open connection to "+databaseURL);
                if(databaseUser != null && databasePassword != null) {
                    ms_connection = DriverManager.getConnection(databaseURL,
                                                                databaseUser,
                                                                databasePassword);
                } else {
                    ms_connection = DriverManager.getConnection(databaseURL);
                }
                ms_connected = true;
                Log.info("JdbcUtil", "createConnection", "Connection created.");
                initialize(ms_connection);
            } catch(Exception e) {
                Log.error("JdbcUtil", "createConnection", "Failed to create connection.");
                ExceptionHandler.handleException(e);
            }
        } else {
            Log.error("JdbcUtil", "createConnection", "JDBC Configuration invalid.");
        }
    }

    /**
     * Executes any query. Used mainly for admin purposes.
     *
     * @param query the SQL query string to be executed.
     * @returns a result set or null
     * @exception Exception thrown if there was a database problem
     */
    public static ResultSet executeQuery(String query) throws Exception {
        ResultSet result = null;
        String upperQuery = query.toUpperCase().trim();
        Statement statement = ms_connection.createStatement();
        Log.info("JdbcUtil", "executeQuery", "Execute query: "+query);
        if(upperQuery.startsWith("SELECT")) {
            result = statement.executeQuery(query);
        } else {
            int no = statement.executeUpdate(query);
        }
        return result;
    }

    /**
     * Free all resources and dispose of the JDBC Utility class
     */
    public static void dispose() throws Exception {
        Log.info("JdbcUtil", "dispose", "Close database connection");
        //        ms_connection.close();
        Log.info("JdbcUtil", "dispose", "Database connection closed");
    }

    /**
     * Initialize the JDBC utility class by creating all
     * prepared statements, an instance of a unique ID
     * generator etc.
     *
     * @param connection A reference to a valid database connection object.
     * @exception LabyException
     */
    private static void initialize(Connection connection) throws LabyException {
        try {
            ms_idGenerator = null;
            Log.info("JdbcUtil", "initialize", "Prepare Unique ID generator");
            String dbType = Configuration.getProperty("database.vendor");
            if(dbType.trim().toLowerCase().equals("oracle")) {
                Log.info("JdbcUtil", "initialize", "Use ORACLE sequence ID generator");
                ms_idGenerator = new com.jlaby.jdbc.oracle.IdGenerator(ms_connection);
            }
            if(dbType.trim().toLowerCase().equals("mckoi")) {
                Log.info("JdbcUtil", "initialize", "Use McKoi unique ID generator");
                ms_idGenerator = new com.jlaby.jdbc.mckoi.IdGenerator(ms_connection);
            }
            if(ms_idGenerator == null) {
                Log.error("JdbcUtil", "initialize", "No unique ID generator found for DB type " + dbType);
                throw new IllegalArgumentException("No unique ID generator found for DB type " + dbType);
            }
            ms_idGenerator.initialize();

            Log.info("JdbcUtil", "initialize", "Create prepared statement queries:");

            ms_prepLoadUserSt = connection.prepareStatement(Configuration.getProperty("sql.query.loaduserdata"));
            Log.info("JdbcUtil", "initialize", "Load user: "+Configuration.getProperty("sql.query.loaduserdata"));

            ms_prepSaveUserSt = connection.prepareStatement(Configuration.getProperty("sql.query.saveuserdata"));
            Log.info("JdbcUtil", "initialize", "Save user: "+Configuration.getProperty("sql.query.saveuserdata"));

            Log.info("JdbcUtil", "initialize", "Create users: "+Configuration.getProperty("sql.query.createuserdata"));
            ms_prepCreateUserSt = connection.prepareStatement(Configuration.getProperty("sql.query.createuserdata"));

            Log.info("JdbcUtil", "initialize", "Get view users: "+Configuration.getProperty("sql.query.getviewusers"));
            ms_prepGetViewCharacters = connection.prepareStatement(Configuration.getProperty("sql.query.getviewusers"));

            Log.info("JdbcUtil", "initialize", "Database stuff initialized.");
        } catch(java.sql.SQLException e) {
            throw new LabySQLException(e);
        }
    }
}
