package com.jlaby.admin.jdbc;

/*
 * @(#)ClearCharacterTable.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */

import com.jlaby.jdbc.*;
import com.jlaby.log.*;

/**
 * Administration utility class.
 * This class clears the LABY_CHARACTER table.
 *
 * @author  Marcel Schoen
 * @version $Id: ClearCharacterTable.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class ClearCharacterTable extends AbstractBatchJob {

    /**
     * Main entry method.
     *
     * @param args the commandline arguments.
     */
    public static void main(String[] args) {
        ClearCharacterTable batchJob = new ClearCharacterTable();
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
        Log.info(this, "run", "Delete all records in LABY_CHARACTER...");
        JdbcUtil.executeQuery("DELETE FROM UNIT_TLIB.LABY_CHARACTER");
        Log.info(this, "run", "Insert one dummy record in LABY_CHARACTER...");
        JdbcUtil.executeQuery("INSERT INTO UNIT_TLIB.LABY_CHARACTER(ID,NAME,ALIAS,POSITIONX,POSITIONY,POSITIONZ,DIRECTION,STATE,LIFE) VALUES(0,'dummy','dummy',0,0,0,0,0,0)");

        Log.info(this, "run", "Records deleted.");
    }
}
