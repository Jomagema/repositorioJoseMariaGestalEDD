package com.jlaby.client;

/*
 * @(#)LabyAnswer.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.Serializable;

/**
 * Abstract base class for answers sent from the Laby
 * server to Java clients. The server uses these classes
 * to create an answer in a simple way, abstracted from
 * the network transport mechanism, while the client
 * uses it to deal with server answers in a more flexibel
 * and extensible way.
 *
 * @author  Marcel Schoen
 * @version $Id: LabyAnswer.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public abstract class LabyAnswer implements Serializable {

    // **************************************************
    // CONSTANTS
    // **************************************************
    public static final int LOGIN_RELATED = 100;
    public static final int GAME_RELATED = 200;
    public static final int ADMIN_RELATED = 300;

    // **************************************************
    // VARIABLES
    // **************************************************
    private String m_answerClassName = null;
    private int m_type = 0;

    /**
     * Constructor used by the Laby server to create
     * answer objects.
     *
     * @param type the type of answer (see constants).
     */
    public LabyAnswer(int type) {
        m_answerClassName = getClass().getName();
        m_type = type;
    }

    /**
     * Returns the name of the answer. Must be implemented
     * specifically by each answer class. This method can
     * be used by the client to determine the exact name
     * (type) of the class for further casting.
     *
     * @returns The name (text) of the answer.
     */
    public final String getClassName() {
        return m_answerClassName;
    }


    /**
     * Returns the type of the answer.
     *
     * @return the type of the answer (see constants).
     */
    public final int getType() {
        return m_type;
    }
}
