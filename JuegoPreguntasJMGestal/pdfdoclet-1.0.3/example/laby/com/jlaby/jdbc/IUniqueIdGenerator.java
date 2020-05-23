package com.jlaby.jdbc;

/*
 * @(#)IUniqueIdGenerator.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.exception.*;

/**
 * Interface for database-specific implementations of
 * mechanisms for creating unique IDs.
 *
 * @author  Marcel Schoen
 * @version $Id: IUniqueIdGenerator.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public interface IUniqueIdGenerator {

    /**
     * Creates a new, unique integer ID value.
     *
     * @return the unique integer value
     */
    public int createNewID();

    /**
     * This method must initialize and / or
     * configure the ID generator. It may throw
     * any kind of LabyException if necessary.
     *
     * @exception LabyException thrown if initialisation of the facility failed.
     */
    public void initialize() throws LabyException;
}
