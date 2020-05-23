package com.jlaby.client.handler;

/*
 * @(#)BrowserClientHandler.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.io.*;
import javax.servlet.http.*;

import com.jlaby.action.*;
import com.jlaby.action.actions.*;
import com.jlaby.action.exception.*;
import com.jlaby.client.*;
import com.jlaby.client.answers.*;
import com.jlaby.config.*;
import com.jlaby.exception.*;
import com.jlaby.html.*;
import com.jlaby.log.*;
import com.jlaby.client.view.text.*;
import com.jlaby.util.ILabyConstants;

/**
 * Handler for Webbrowser clients. It takes their simple
 * GET-requests (with parameters) and creates <code>LabyAction</code>
 * objects based on those parameters.
 * <P>
 * Mapping of LabyAction classes and GET parameters:
 *
 *
 * @author  Marcel Schoen
 * @version $Id: BrowserClientHandler.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class BrowserClientHandler implements IClientHandler, ILabyConstants {

    private String m_loginHtmlTemplate = null;
    private String m_newUserHtmlTemplate = null;
    private String m_template = null;

    /**
     * Constructs the Browser client handler. This constructor
     * reads the names of the HTML templates for the pages
     * to be sent to the client from the configuration properties.
     */
    public BrowserClientHandler() {
        m_template = Configuration.getProperty(PROP_HTML_MAINPANEL);
        m_loginHtmlTemplate = Configuration.getProperty(PROP_HTML_LOGIN);
        m_newUserHtmlTemplate = Configuration.getProperty(PROP_HTML_CREATE);
    }

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
        String action = servletRequest.getParameter("action");
        if(action != null) {
            if(action.equals("login")) {
                labyAction = createLoginAction(servletRequest);
            }
            if(action.equals("createuser")) {
                labyAction = createCreateUserAction(servletRequest);
            }
            if(action.equals("logout")) {
                labyAction = new LogoutAction();
            }
            if(action.equals("showgui")) {
                labyAction = createShowGuiAction(servletRequest);
            }
            if(action.equals("refresh")) {
                labyAction = new NoAction();
            }
            if(action.equals("walk")) {
                labyAction = new WalkAction();
            }
            if(action.equals("turn")) {
                labyAction = createTurnAction(servletRequest);
            }
        }
        if(labyAction == null) {
            labyAction = new NoAction();
        }
        return labyAction;
    }

    /**
     * Creates a <code>ShowGuiAction</code>.
     */
    private ShowGuiAction createShowGuiAction(HttpServletRequest servletRequest) {
        String gui = servletRequest.getParameter("gui");
        ShowGuiAction action = null;
        if(gui.equals("login")) {
            action = new ShowGuiAction(ShowGuiAction.LOGIN);
        }
        if(gui.equals("createuser")) {
            action = new ShowGuiAction(ShowGuiAction.CREATEUSER);
        }
        return action;
    }

    /**
     * Creates a <code>LoginAction</code>.
     */
    private LoginAction createLoginAction(HttpServletRequest servletRequest) {
        String name = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");
        if(name == null) {
           name = "";
        }
        if(password == null) {
           password = "";
        }
        LoginAction action = new LoginAction(name, password);
        return action;
    }

    /**
     * Creates a <code>CreateUserAction</code>.
     */
    private CreateUserAction createCreateUserAction(HttpServletRequest servletRequest) {
        String name = servletRequest.getParameter("username");
        String password1 = servletRequest.getParameter("password1");
        String password2 = servletRequest.getParameter("password2");
        if(name == null) {
           name = "";
        }
        if(password1 == null) {
           password1 = "";
        }
        if(password2 == null) {
           password2 = "";
        }
        CreateUserAction action = new CreateUserAction(name, password1, password2);
        return action;
    }

    /**
     * Creates a <code>TurnAction</code>.
     */
    private TurnAction createTurnAction(HttpServletRequest servletRequest) {
        TurnAction action = null;
        String side = servletRequest.getParameter("side");
        if(side != null) {
            if(side.equals("left")) {
                action = new TurnAction(TurnAction.LEFT);
            }
            if(side.equals("right")) {
                action = new TurnAction(TurnAction.RIGHT);
            }
        }
        return action;
    }

    /**
     * This method has to create the appropriate HTML page
     * for the answer given by the server and send it back
     * to the Browser client.
     *
     * @param servletResponse the servlets response object
     * @param answer the answer given by the server
     */
    public void sendAnswer(HttpServletResponse servletResponse, LabyAnswer answer) {
        int type = answer.getType();
        switch(type) {
        case LabyAnswer.LOGIN_RELATED:
            Log.info(this, "sendAnswer", "Send login related answer...");
            sendLoginRelatedAnswer(servletResponse, answer);
            break;
        case LabyAnswer.GAME_RELATED:
            Log.info(this, "sendAnswer", "Send game page...");
            displayGamePage(servletResponse, answer);
            break;
        }
    }

    /**
     * Sends pages related to login issued (login,
     * create a new user account etc.)
     */
    private void sendLoginRelatedAnswer(HttpServletResponse servletResponse, LabyAnswer answer) {
        LoginAnswer loginAnswer = (LoginAnswer)answer;
        int type = loginAnswer.getDetailType();
        switch(type) {
        case LoginAnswer.LOGIN_REQUIRED:
            Log.info(this, "sendLoginRelatedAnswer", "Send login gui answer...");
            displayLoginPage(servletResponse);
            break;
        case LoginAnswer.LOGIN_FAILED:
            Log.info(this, "sendLoginRelatedAnswer", "Send login gui answer...");
            displayLoginPage(servletResponse);
            break;
        case LoginAnswer.LOGIN_NEWUSER_CREATION:
            Log.info(this, "sendLoginRelatedAnswer", "Send createuser gui answer...");
             displayNewUserPage(servletResponse);
             break;
        }
    }

    /**
     * Sends the Laby game HTML page to the client.
     */
    private void displayGamePage(HttpServletResponse servletResponse, LabyAnswer answer) {

        GameInfoAnswer infoAnswer = (GameInfoAnswer)answer;
        GameCharacterInfo characterInfo = infoAnswer.getCharacterInfo();

        // ******************************************
        // Prepare inventory part
        // ******************************************
        //        Inventory inventory = new Inventory();
        Log.info(this, "sendAnswer", "Render HTML for seeing "+characterInfo.getViewObjects().length+" objects.");


        // ******************************************
        // Prepare message text lines
        // ******************************************
        String[] messages = new String[4];
        messages[0] = "The first line of the message.<BR>";
        messages[1] = "This is the second hardcoded message.<BR>";
        messages[2] = "This is the third hardcoded message.<BR>";
        messages[3] = "This is the fourth hardcoded message.<BR>";

        // ******************************************
        // Prepare HTML page
        // ******************************************

        try {
            Log.info(this, "sendAnswer", "Create View3D object and render 3D view");
            View3D view3D = new View3D();
            String[] view3Dlines = view3D.renderView(characterInfo);

            Log.info(this, "sendAnswer", "Create main panel.");
            MainPanel mainPanel = new MainPanel(m_template, servletResponse);
            mainPanel.update(view3Dlines, messages);
            Log.info(this, "sendAnswer", "Display main panel.");
            mainPanel.display(servletResponse);
            Log.info(this, "sendAnswer", "Finished.");
        } catch(Exception e) {
            ExceptionHandler.handleException(e);
        }
    }

    /**
     * Sends the login HTML page to the client.
     */
    private void displayLoginPage(HttpServletResponse servletResponse) {
        LoginPanel loginPanel = new LoginPanel(m_loginHtmlTemplate, servletResponse);
        loginPanel.update();
        loginPanel.display(servletResponse);
    }

    /**
     * Sends the HTML page for creation of a new user account to the client.
     */
    private void displayNewUserPage(HttpServletResponse servletResponse) {
        CreateUserPanel newUserPanel = new CreateUserPanel(m_newUserHtmlTemplate, servletResponse);
        newUserPanel.update();
        newUserPanel.display(servletResponse);
    }
}
