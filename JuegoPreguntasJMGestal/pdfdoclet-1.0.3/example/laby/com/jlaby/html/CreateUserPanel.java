package com.jlaby.html;

/*
 * @(#)CreateUserPanel.java 0.1 99/Feb/12
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
 * @version $Id: CreateUserPanel.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public class CreateUserPanel extends LabyHtmlPanel implements ILabyConstants {

    public CreateUserPanel(String templateResource, HttpServletResponse servletResponse) {
        super(templateResource, servletResponse);
    }

    public void update() {

        String labyURL = Configuration.getProperty(PROP_URL_LABY);
        String loginUserURL = Configuration.getProperty(PROP_URL_LOGIN);

        replaceURL("%LOGINURL%", loginUserURL);
        replaceURL("%LABYURL%", labyURL);
    }
}
