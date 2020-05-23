package com.jlaby.html;

/*
 * @(#)LabyHtmlPanel.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import java.util.*;

import com.jlaby.config.*;
import com.jlaby.exception.*;
import com.jlaby.log.*;
import com.jlaby.util.*;

import javax.servlet.http.*;

/**
 * This class allows to read a HTML template and have
 * parts of it replaced dynamically.
 *
 * @author  Marcel Schoen
 * @version $Id: LabyHtmlPanel.java,v 1.1 2013/10/27 23:51:32 marcelschoen Exp $
 */
public abstract class LabyHtmlPanel {

    private static Hashtable ms_pages = new Hashtable();

    private String m_resource = null;
    private Hashtable m_replaces = new Hashtable();
    private HttpServletResponse m_servletResponse = null;

    public LabyHtmlPanel(String templateResource, HttpServletResponse servletResponse) {
        m_servletResponse = servletResponse;
        m_resource = templateResource;
        if(ms_pages.get(m_resource) == null) {
            String[] page = readPage(m_resource);
            ms_pages.put(m_resource, page);
        }
        m_replaces = new Hashtable();
    }

    public final void display(HttpServletResponse servletResponse) {
        try {
            PrintWriter out = servletResponse.getWriter();
            String[] page = (String[])ms_pages.get(m_resource);
            page = doReplaces(page);
            Log.info(this, "display", "Render "+page.length+" lines.");
            // Now display the page
            for(int i=0; i<page.length; i++) {
                out.println(page[i]);
            }
        } catch(IOException e) {
            throw new LabyException(e);
        }
    }

    public final void replace(String toReplace, String[] lines) {
        String newLine = "";
        for(int i=0; i<lines.length; i++) {
            newLine = newLine + lines[i];
        }
        m_replaces.put(toReplace, newLine);
    }

    public final void replace(String toReplace, String line) {
        Log.info(this, "replace", "Replace '"+toReplace+"' with '"+line+"'");
        m_replaces.put(toReplace, line);
    }

    public final void replaceURL(String toReplace, String url) {
        Log.info(this, "replace", "Replace '"+toReplace+"' with encoded '"+url+"'");
        //        m_replaces.put(toReplace, m_servletResponse.encodeURL(url));
    }

    private String[] doReplaces(String[] page) {
        Enumeration keys = m_replaces.keys();
        while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            Object value = m_replaces.get(key);
            if(value instanceof String) {
                String text = (String)value;
                page = doReplace(page, key, text);
            } else {
                String[] textLines = (String[])value;
                page = doReplace(page, key, textLines);
            }
        }
        return page;
    }

    private String[] doReplace(String[] page, String toReplace, String line) {
        page = StringUtil.patch(page, toReplace, line);
        return page;
    }

    private String[] doReplace(String[] page, String toReplace, String[] lines) {
        String newString = "";
        for(int i=0; i<lines.length; i++) {
            newString = newString.concat(lines[i]);
        }
        page = StringUtil.patch(page, toReplace, newString);
        return page;
    }

    private static String[] readPage(String templateFile) {
        Log.info("LabyHtmlPanel", "readPage", "Try to read template "+templateFile);
        String[] page = null;
        try {
            // 1st try: Read it from file in filesystem
            File test = new File(templateFile);
            if(test.exists() && test.isFile()) {
                Log.info("LabyHtmlPanel", "readPage", "Read file "+templateFile);
                page = FileUtil.readTextFile(test.getAbsolutePath());
            } else {
                // 2nd try: Read it from file in classpath
                InputStream in = test.getClass().getResourceAsStream(templateFile);
                if(in != null) {
                    Log.info("LabyHtmlPanel", "readPage", "Read resource "+templateFile);
                    page = FileUtil.readTextFileStream(new InputStreamReader(in));
                } else {
                    Log.error("LabyHtmlPanel", "readPage", "NOT FOUND: "+templateFile);
                    page = new String[1];
                    page[0] = "<HTML><HEAD><TITLE>Not found</TITLE></HEAD><BODY>error</BODY></HTML>";
                }
            }
        } catch(Exception e) {
            ExceptionHandler.handleException(e);
        }
        return page;
    }
}
