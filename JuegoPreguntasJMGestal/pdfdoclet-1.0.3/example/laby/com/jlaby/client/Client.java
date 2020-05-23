package com.jlaby.client;

/*
 * @(#)Client.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import com.jlaby.action.*;
import com.jlaby.action.actions.*;
import com.jlaby.action.exception.*;
import com.jlaby.client.*;
import com.jlaby.client.answers.*;
import com.jlaby.client.exception.*;
import com.jlaby.exception.*;
import com.jlaby.world.*;

import java.util.*;
import java.awt.*;

/**
 * Abstract base class for Java clients. It provides the
 * functionality to connect and log in to a Laby server,
 * send requests for Actions etc. All the HTTP communication
 * is hidden and completely transparent to the developer.
 *
 * @author  Marcel Schoen
 * @version $Id: Client.java,v 1.1 2013/10/27 23:51:33 marcelschoen Exp $
 */
public abstract class Client {

    private IWorld m_world = null;
    private GameCharacterInfo m_characterInfo = new GameCharacterInfo();
    private boolean m_loggedIn = false;

    /**
     * Connects the client with a Laby HTTP server (servlet)
     * and logs in with the given user account. This method
     * actually just sends a <code>LoginAction</code> object.
     *
     * @param url the URL of the Laby server
     * @param username the name of the Laby user
     * @param password the login password
     * @exception LabyException thrown if connect or login failed (see causing exception for details)
     */
    public final void login(String url, String username, String password) throws LabyException {
        m_world = WorldFactory.createRemoteWorld(url);
        LabyAnswer answer = null;
        answer = m_world.handleAction(new LoginAction(username, password));
        String className = answer.getClassName();
        if(className.equals("com.jlaby.client.answers.GameInfoAnswer")) {
            GameInfoAnswer infoAnswer = (GameInfoAnswer)answer;
            m_characterInfo = infoAnswer.getCharacterInfo();
            m_loggedIn = true;
        } else {
            m_loggedIn = false;
        }
        showAnswer(answer);
    }

    private void showAnswer(LabyAnswer answer) {
        System.out.println("> Answer class: "+answer.getClassName());
        System.out.println("> Type: "+answer.getType());
    }

    public final GameCharacterInfo getCharacterInfo() {
        return m_characterInfo;
    }

    /**
     * Let the world handle any kind of action
     * and return an appropriate answer.
     *
     * @param action the action which the client wants to perform.
     * @return the answer returned by the Laby world.
     * @exception LabyException
     */
    public final LabyAnswer performAction(LabyAction action) throws LabyException {
        LabyAnswer answer = null;
        answer = m_world.handleAction(action);
        String className = answer.getClassName();
        if(className.equals("com.jlaby.client.answers.GameInfoAnswer")) {
            GameInfoAnswer infoAnswer = (GameInfoAnswer)answer;
            m_characterInfo = infoAnswer.getCharacterInfo();
        }
        return answer;
    }

    /**
     * Logs the current user out from the Laby world.
     *
     * @exception LabyException
     */
    public final void logout() throws LabyException {
        performAction(new LogoutAction());
    }

    /**
     * Determines if the client is currently logged in in
     * a Laby world.
     *
     * @returns true if the client is currently logged in.
     */
    public final boolean isLoggedIn() {
        return m_loggedIn;
    }

    /**
     * This method takes a Component such as a Dialog or Window
     * and calculates the coordinates appropriate to center it
     * on the screen. It then changes the coordinates of the
     * given component to center it.
     *
     * @param component the component that should be centered on the screen
     */
    public static void centerOnScreen(Component component) {
        Dimension dim = component.getSize();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int posx = (screen.width - dim.width) / 2;
        int posy = (screen.height - dim.height) / 2;
        component.setLocation(new Point(posx, posy));
    }
}
