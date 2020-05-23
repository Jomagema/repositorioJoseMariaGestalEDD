package com.jlaby.client.answers;

/*
 * @(#)LoginAnswer.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.util.*;

import com.jlaby.client.*;

/**
 * Answer for a client that needs to carry out some
 * login-related task or to be informed about something
 * like that (login required, login failed, user
 * unknown etc.)
 *
 * @author  Marcel Schoen
 * @version $Id: LoginAnswer.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class LoginAnswer extends LabyAnswer {

    // **************************************************
    // CONSTANTS
    // **************************************************
    public static final int LOGIN_REQUIRED = 100;
    public static final int LOGIN_FAILED = 200;
    public static final int LOGIN_NEWUSER_CREATION = 300;

    // **************************************************
    // VARIABLES
    // **************************************************
    private int m_detailType = 0;

    /**
     * The detail type of the login related answer.
     *
     * @param detailType must be one of the constants.
     */
    public LoginAnswer(int detailType) {
        super(LabyAnswer.LOGIN_RELATED);
        m_detailType = detailType;
    }

    /**
     * Returns the type of login-related answer.
     *
     * @return the type (must be one of the constants)
     */
    public int getDetailType() {
        return m_detailType;
    }
}
