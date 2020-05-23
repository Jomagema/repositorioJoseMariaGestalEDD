package com.jlaby.server.servlet;

/**
 * @(#)LabyServlet.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 */

import com.jlaby.action.*;
import com.jlaby.action.handlers.*;
import com.jlaby.action.exception.*;
import com.jlaby.character.*;
import com.jlaby.client.*;
import com.jlaby.client.answers.*;
import com.jlaby.client.handler.*;
import com.jlaby.config.*;
import com.jlaby.exception.*;
import com.jlaby.inventory.*;
import com.jlaby.jdbc.*;
import com.jlaby.jdbc.exception.*;
import com.jlaby.log.*;
import com.jlaby.util.*;
import com.jlaby.world.*;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The main servlet of the Laby.
 *
 * @version $Id: LabyServlet.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 * @author  msc
 */
public class LabyServlet extends HttpServlet implements ILabyConstants {

    private static boolean ms_initialized = false;

    /**
     * This hashtable stores PrintWriter objects for the
     * appropriate Thread. It is only used for debug purposes
     * to allow the Util.handleException() function to show
     * every exception on the Servlet output stream.
     */
    private static Hashtable ms_out = new Hashtable();

    /**
     * This methode handles the http request.
     *
     * @param  req   The http request.
     * @param  res   The http response.
     * @return none.
     * @exception ServletException   Exception thrown by servlet method.
     * @exception IOException        Exception thrown by IO.
     */
    public void service(HttpServletRequest httpRequest,
                        HttpServletResponse httpResponse)
        throws ServletException, IOException {

        try {
            // Save output stream for current Thread
            // (used for debug- and errorhandling-purposes)
            Util.setOut(httpResponse.getWriter());

            // Get the session in case we must use it
            HttpSession session = httpRequest.getSession();
            Util.setSession(session);

            // ******************************************
            // Process the HTTP request and convert it
            // into a Laby request with additional info
            // ******************************************
            Log.info("LabyServlet", "service", "Get client handler for request...");
            IClientHandler clientHandler = ClientHandlerFactory.getClientHandler(httpRequest);

            Log.info("LabyServlet", "service", "Client handler: "+clientHandler.getClass().getName());

            // Create an action object
            Log.info("LabyServlet", "service", "Convert request to action...");
            LabyAction action = clientHandler.convertRequest(httpRequest);

            // create anonymous dummy-character for public
            // requests as default
            GameCharacter character = new GameCharacter();

            // the answer that will be sent to the client
            LabyAnswer answer = null;

            // *****************************************
            // Check if the user has already been
            // authenticated and if not, create an
            // appropriate answer
            // ******************************************
            try {
                Log.info("LabyServlet", "service", "Action: "+action.getClass().getName());

                // Check if the request needs authentication
                if(action.getType() == LabyAction.AUTHENTICATED_ACTION) {
                    Log.info("LabyServlet", "service", "Identify issuer of action");

                    // ******************************************
                    // Identify the character's name, thereby
                    // authenticating the character, and load
                    // its state from the database
                    // (throws Exception if authentication fails)
                    // ******************************************
                    String name = authenticateCharacter(session);
                    character = CharacterState.load(name);
                    action.setSource(character);
                }

                // ******************************************
                // Perform the action and create the answer
                // to be given to the client
                // ******************************************
                IWorld labyWorld = WorldFactory.getWorld();
                answer = labyWorld.handleAction(action);

                // ******************************************
                // Save character state in DB (but only if
                // it wasn't a dummy for a public request)
                // ******************************************
                if(character.isStateful()) {
                    Log.info("LabyServlet", "service", "Save character state");
                    CharacterState.save(character);
                }


            } catch(UserNotLoggedInException e) {
                session.invalidate();
                Log.info("LabyServlet", "service", "> UserNotLoggedInException "+e.getMessage());
                answer = new LoginAnswer(LoginAnswer.LOGIN_REQUIRED);
            } catch(InvalidPasswordException e) {
                Log.info("LabyServlet", "service", "> InvalidPasswordException "+e.getMessage());
                answer = new LoginAnswer(LoginAnswer.LOGIN_FAILED);
            } catch(UnknownUserException e) {
                Log.info("LabyServlet", "service", "> UnknownUserException "+e.getMessage());
                answer = new LoginAnswer(LoginAnswer.LOGIN_NEWUSER_CREATION);
            }

            // ******************************************
            // Send the answer to the client, no matter
            // what it is (must never be null)
            // ******************************************
            clientHandler.sendAnswer(httpResponse, answer);

            // remove references to output stream and session
            // from currrent-thread-hashtable
            Util.disposeCurrentThreadInfo();

        } catch(Throwable e) {
            Log.error("LabyServlet", "service", "Uncaught exception: "+e.getMessage());
            ExceptionHandler.handleException(e);
        }
    }

    /**
     * Read characters name from HTTP session. If the name
     * is not in the session yet, the character is not logged in.
     *
     * @param req The HTTP request
     * @return The name of the character
     * @exception UserNotLoggedInException
     */
    private String authenticateCharacter(HttpSession session) throws UserNotLoggedInException {
        Log.info(this, "authenticateCharacter", "Try to identify user...");
        String characterID = null;

        characterID = (String)session.getAttribute("LabyID");
        if(characterID == null) {
            Log.info(this, "authenticateCharacter", "ID not stored in HTTP session.");
            throw new UserNotLoggedInException();
        }

        Log.info(this, "authenticateCharacter", "User name: "+characterID);

        return characterID;
    }

    private void verifyPassword(String password) throws LabyException {
        if(password.toLowerCase().equals("fail")) {
            throw new InvalidPasswordException();
        }
    }

    /**
     * This initialization is done the first time
     * the servlet is called. Its main purpose is to
     * trigger the initialization of all static variables.
     */
    public static void initialize() throws Exception {
        if(!ms_initialized) {
            String configFile = System.getProperty(PROP_CONFIG_FILE, 
                                                   "/com/tarsecfun/laby/config/configuration.properties");
            System.out.println("Config file: "+configFile);
            Configuration.load(configFile);
            System.out.println("Config loaded, logdir: "+Configuration.getProperty(PROP_LOG_DIR));
            Log.initialize();
            Log.info("LabyServlet", "initialize", "Begin initialization...");
            WorldFactory.createLocalWorld();
            JdbcUtil.createConnection();
            Log.info("LabyServlet", "initialize", "Servlet initialized.");
            ms_initialized = true;
        }
    }

    public static boolean isInitialized() {
        return ms_initialized;
    }

    /**
     * The init method of the HTTPServlet class.
     */
    public void init(ServletConfig p_config) throws ServletException {
	super.init(p_config);
        try {
            initialize();
        } catch(Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    public void destroy() {
        try {
            Log.info("LabyServlet", "destroy", "Close database connection.");
            JdbcUtil.dispose();
            Log.info("LabyServlet", "destroy", "Database connection closed.");
        } catch(Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    /**
     * This methode returns information about the servlet.
     *
     * @return information about the servlet.
     */
    public String getServletInfo() {
        return "The Laby Main Servlet";
    }
}
