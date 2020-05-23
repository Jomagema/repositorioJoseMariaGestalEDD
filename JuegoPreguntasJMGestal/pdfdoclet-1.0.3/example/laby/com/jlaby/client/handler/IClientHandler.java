package com.jlaby.client.handler;

/*
 * @(#)IClientHandler.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import javax.servlet.http.*;

import com.jlaby.action.*;
import com.jlaby.action.exception.*;
import com.jlaby.client.*;

/**
 * Interface for classes handling the response for a
 * certain type of laby client. Note that an implementation
 * of this interface must be thread-safe because only one
 * instance of each handler type will be used within the
 * servlet VM to serve all requests (from one type of client).
 *
 * @author  Marcel Schoen
 * @version $Id: IClientHandler.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public interface IClientHandler {

    /**
     * Every handler must convert the request sent from the client
     * into the appropriate Action object. In case of a browser, the
     * request is a simple string consisting of parameter/value pairs.
     * In case of a Java client, the request will be a string with a
     * base64 encoded serialized Action object.
     *
     * @param servletRequest the request sent from the client
     */
    public LabyAction convertRequest(HttpServletRequest servletRequest) throws UnsupportedActionException;

    /**
     * This method is called for answers of type ANSWER_STATE.
     * It must return appropriate data for the client based
     * on the type of the client. In case of an intelligent
     * (Java) client, which can render its own 3D view, the data
     * sent back should just be information about the objects.
     * In case of a dumb (Browser) client, the answer must be
     * a complete HTML page.
     *
     * @param servletResponse the servlets response object. The output stream of
     *                        this response object must be used to send the answer
     *                        back to the client.
     * @param answer the answer information object
     */
    public void sendAnswer(HttpServletResponse servletResponse, LabyAnswer answer);
}
