package com.jlaby.client.handler;

/*
 * @(#)JavaClientHandler.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import java.util.*;
import javax.servlet.http.*;

import com.jlaby.action.*;
import com.jlaby.action.exception.*;
import com.jlaby.client.*;
import com.jlaby.config.*;
import com.jlaby.exception.*;
import com.jlaby.log.*;
import com.jlaby.util.*;

/**
 * Handler for Java clients (J2SE or MIDP). This class uses
 * serialization to retrieve Action objects from the client
 * and send Answer objects back. All objects have to be
 * Base64-encoded during the transmission.
 *
 * @author  Marcel Schoen
 * @version $Id: JavaClientHandler.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class JavaClientHandler implements IClientHandler {

    private String m_template = null;

    /**
     * Every handler must convert the request sent from the client
     * into the appropriate Action object. In case of a browser, the
     * request is a simple string consisting of parameter/value pairs.
     * In case of a Java client, the request will be a string with a
     * base64 encoded serialized Action object.
     *
     * @param servletRequest the request sent from the client
     * @exception UnsupportedActionException thrown if the request could not successfully
     *                                       be converted into a <code>LabyAction</code> object.
     */
    public LabyAction convertRequest(HttpServletRequest servletRequest) throws UnsupportedActionException {

        LabyAction labyAction = null;

        try {
            Enumeration parms = servletRequest.getParameterNames();
            int i=0;
            while(parms.hasMoreElements()) {
                String parm = (String)parms.nextElement();
                Log.info(this, "convertRequest", "Parameters "+i+": "+parm);
                i++;
            }

            // Read Base64-encoded serialized action object data from request
            String action = servletRequest.getParameter("action");

            Log.info(this, "convertRequest", "Request: "+action);

            // create new action object from serialization data
            labyAction = (LabyAction)Util.getObject(Base64.decode(action));

        } catch(IOException e) {
            throw new UnsupportedActionException(e);
        } catch(ClassNotFoundException e) {
            throw new UnsupportedActionException(e);
        }

        return labyAction;
    }

    /**
     * This method is called for answers of type ANSWER_STATE.
     * It must return appropriate data for the client based
     * on the type of the client. In case of an intelligent
     * (Java) client, which can render its own 3D view, the data
     * sent back should just be information about the objects.
     * In case of a dumb (Browser) client, the answer must be
     * a complete HTML page.
     *
     * @param servletResponse the servlets response object
     * @param characterInfo The object containing information about the game character
     */
    public void sendAnswer(HttpServletResponse servletResponse, LabyAnswer answer) {

        // ******************************************
        // Send state information
        // ******************************************

        try {
            Log.info(this, "sendAnswer", "Send serialized and Base64-encoded answer object");

            byte[] objectData = Util.getObjectData(answer);
            String encodedObject = Base64.encode(objectData);

            PrintWriter wrt = new PrintWriter(servletResponse.getWriter());
            wrt.println(encodedObject);
            wrt.flush();
            wrt.close();

            Log.info(this, "sendAnswer", "Finished.");
        } catch(Exception e) {
            ExceptionHandler.handleException(e);
        }
    }
}
