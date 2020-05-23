package com.jlaby.jdbc.oracle;

/*
 * @(#)IdGenerator.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.config.*;
import com.jlaby.exception.*;
import com.jlaby.jdbc.*;
import com.jlaby.jdbc.exception.*;
import com.jlaby.log.*;
import com.jlaby.util.ILabyConstants;

import java.sql.*;

/**
 * Oracle-specific implementation of a unique ID
 * generator, working with Oracle sequence objects.
 *
 * @author  Marcel Schoen
 * @version $Id: IdGenerator.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class IdGenerator implements IUniqueIdGenerator, ILabyConstants {

    private Connection m_connection = null;
    private static PreparedStatement ms_prepGetNewUniqueID = null;
    private String m_sequenceName = null;

    /**
     * Constructs a generator for unique IDs which
     * uses an ORACLE Sequence.
     *
     * @param connection the JDBC database connection.
     */
    public IdGenerator(Connection connection) {
        m_connection = connection;
    }

    /**
     * This method initializes the generator by
     * reading the name of the Sequence object
     * and the SQL query from the configuration.
     *
     * @exception LabyException thrown if initialisation of the facility failed.
     */
    public void initialize() throws LabyException {
        Log.info("IdGenerator", "initialize", "Initializing ID generator.");
        m_sequenceName = Configuration.getProperty(PROP_DB_ORACLE_SEQNAME);
        if(m_sequenceName == null) {
            throw new LabySQLException("ORACLE sequence missing in configuration!");
        }
        try {
            ms_prepGetNewUniqueID = m_connection.prepareStatement(Configuration.getProperty(PROP_SQL_UNIQUEID_ORACLE));
        } catch(java.sql.SQLException e) {
            throw new LabySQLException(e);
        }
    }

    /**
     * Creates a new, unique integer ID value.
     *
     * @return the unique integer value
     */
    public int createNewID() throws LabySQLException {
        int id = -1;
        try {
            Log.info("IdGenerator", "createNewID", "Create new unique ID");
            ms_prepGetNewUniqueID.setString(1, m_sequenceName);
            Log.info("IdGenerator", "createNewID", "Execute query for sequence "+m_sequenceName);
            ResultSet rs = JdbcUtil.executeQuery(Configuration.getProperty(PROP_SQL_UNIQUEID_ORACLE));
            if (rs.next()) {
                Log.info("IdGenerator", "createNewID", "ID: "+rs.getInt(1));
                id = rs.getInt(1);
            } else {
                Log.error("IdGenerator", "createNewID", "No new unique ID created.");
                throw new NoRecordFoundException("No new unique ID created.");
            }
        } catch(Exception e) {
            throw new LabySQLException(e);
        }
        return id;
    }
}
