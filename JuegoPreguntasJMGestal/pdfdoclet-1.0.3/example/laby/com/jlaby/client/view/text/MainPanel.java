package com.jlaby.client.view.text;

/*
 * @(#)MainPanel.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import javax.servlet.http.*;

import com.jlaby.html.*;
import com.jlaby.config.*;
import com.jlaby.util.*;

/**
 * This class handles the main HTML page
 * of the laby. It loads the template and
 * fills in the 3d view, the messages and
 * inventory before displaying it.
 *
 * @author  Marcel Schoen
 * @version $Id: MainPanel.java,v 1.1 2013/10/27 23:51:26 marcelschoen Exp $
 */
public class MainPanel extends LabyHtmlPanel {

    public MainPanel(String templateResource, HttpServletResponse servletResponse) {
        super(templateResource, servletResponse);
    }

    public void update(String[] view,
                       String[] messages)
    {
        String labyUrl = Configuration.getProperty("url.laby");

        replaceURL("%LABYURL%", labyUrl);

        // insert the "3D" view
        replace("%3DVIEW%", view);

        // insert the message list
        replace("%MESSAGES%", messages);
    }
}
