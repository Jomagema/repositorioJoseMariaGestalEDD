package com.jlaby.html;

/*
 * @(#)LoginPanel.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.config.Configuration;
import com.jlaby.util.ILabyConstants;

import javax.servlet.http.*;

/**
 * This class handles the login HTML page
 * of the laby.
 *
 * @author  Marcel Schoen
 * @version $Id: LoginPanel.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class LoginPanel extends LabyHtmlPanel implements ILabyConstants {

    public LoginPanel(String templateResource, HttpServletResponse servletResponse) {
        super(templateResource, servletResponse);
    }

    public void update() {

        String newUserURL = Configuration.getProperty(PROP_URL_CREATE);
        String labyURL = Configuration.getProperty(PROP_URL_LABY);

        replaceURL("%LABYURL%", labyURL);
        replaceURL("%CREATEUSERURL%", newUserURL);

    }
}
