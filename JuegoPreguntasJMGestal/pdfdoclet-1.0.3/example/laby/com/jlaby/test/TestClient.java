package com.jlaby.test;

/*
 * @(#)TestClient.java 0.1 99/Feb/12
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 *
 * CopyrightVersion 1.0
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.jlaby.action.*;
import com.jlaby.action.actions.*;
import com.jlaby.client.*;
import com.jlaby.config.*;
import com.jlaby.view.*;
import com.jlaby.client.view.text.*;
import com.jlaby.world.*;

/**
 * Object in the laby.
 *
 * @author  Marcel Schoen
 * @version $Id: TestClient.java,v 1.1 2013/10/27 23:51:34 marcelschoen Exp $
 */
public class TestClient extends Client implements ActionListener {

    private String m_url = "http://icefire.tarsec.com:5510/servlet/Laby";
    private String m_username = "msc";
    private String m_password = "";

    private IWorld m_world = null;
    private JFrame m_mainWindow = null;
    private boolean m_connected = false;
    private ViewPanel m_viewPanel = null;

    public static void main(String[] args) {
        TestClient testClient = new TestClient();
        testClient.display();
    }

    public TestClient() {
        m_mainWindow = new JFrame("TestClient");

        m_mainWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        m_viewPanel = new ViewPanel();
        /*
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout());
        m_viewPane = new JEditorPane();
        m_viewPane.setBackground(Color.black);
        m_viewPane.setForeground(Color.white);
        m_viewPane.setSize(new Dimension(320, 200));
        m_viewPane.setEditable(false);
        viewPanel.add("Center", m_viewPane);
        */
        JPanel buttonPanel = new JPanel();

        JButton turnLeftButton = new JButton("Left");
        turnLeftButton.setActionCommand("TURNLEFT");
        turnLeftButton.addActionListener(this);

        JButton turnRightButton = new JButton("Right");
        turnRightButton.setActionCommand("TURNRIGHT");
        turnRightButton.addActionListener(this);

        JButton walkButton = new JButton("Walk");
        walkButton.setActionCommand("WALK");
        walkButton.addActionListener(this);

        JButton connectButton = new JButton("Login");
        connectButton.setActionCommand("LOGIN");
        connectButton.addActionListener(this);

        JButton disconnectButton = new JButton("Log out");
        disconnectButton.setActionCommand("LOGOUT");
        disconnectButton.addActionListener(this);

        buttonPanel.add(turnLeftButton);
        buttonPanel.add(turnRightButton);
        buttonPanel.add(walkButton);
        buttonPanel.add(connectButton);
        buttonPanel.add(disconnectButton);

        mainPanel.add("Center", m_viewPanel);
        mainPanel.add("South", buttonPanel);

        m_mainWindow.getContentPane().setLayout(new BorderLayout());
        m_mainWindow.getContentPane().add("Center", mainPanel);

        Configuration.load("/com/jlaby/config/configuration.properties");
        //        m_world = WorldFactory.getWorld();
    }


    /**
     * Shows the help window centered on screen.
     */
    public void display() {

        m_viewPanel.showView(new GameCharacterInfo());

        m_mainWindow.pack();
        super.centerOnScreen(m_mainWindow);
        m_mainWindow.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        if(isLoggedIn()) {
            if(evt.getActionCommand().equals("WALK")) {
                performAction(new WalkAction());
                m_viewPanel.showView(getCharacterInfo());
            }
            if(evt.getActionCommand().equals("TURNRIGHT")) {
                performAction(new TurnAction(TurnAction.RIGHT));
                m_viewPanel.showView(getCharacterInfo());
            }
            if(evt.getActionCommand().equals("TURNLEFT")) {
                performAction(new TurnAction(TurnAction.LEFT));
                m_viewPanel.showView(getCharacterInfo());
            }
            if(evt.getActionCommand().equals("LOGOUT")) {
                logout();
                System.exit(0);
            }
        } else {
            if(evt.getActionCommand().equals("LOGIN")) {
                try {
                    login(m_url, m_username, m_password);
                    m_viewPanel.showView(getCharacterInfo());
                } catch(Exception e) {
                }
            }
        }
    }

    private class ViewPanel extends JPanel {

        private final static int COLUMNS = 58;
        private final static int LINES = 18;

        private int m_fontWidth = 0;
        private int m_fontHeight = 0;

        private FontMetrics m_metrics = null;
        private JEditorPane m_viewPane = null;
        private View3D m_view3D = null;

        public ViewPanel() {
            setLayout(new BorderLayout());
            m_viewPane = new JEditorPane();
            m_viewPane.setBackground(Color.black);
            m_viewPane.setForeground(Color.white);
            m_viewPane.setSize(new Dimension(320, 200));
            m_viewPane.setEditable(false);
            add("Center", m_viewPane);

            m_metrics = m_viewPane.getFontMetrics(m_viewPane.getFont());
            String line = "";
            for(int i=0; i<COLUMNS; i++) {
                line = line + "_";
            }
            m_fontWidth = m_metrics.stringWidth(line);
            m_fontHeight = m_metrics.getHeight();

            System.out.println("Font width: "+m_fontWidth);
            System.out.println("Font height: "+m_fontHeight);

            m_view3D = new View3D();
        }

        public Dimension getPreferredSize() {
            //            return new Dimension(m_fontWidth * COLUMNS, m_fontHeight * LINES);
            return new Dimension(m_fontWidth, m_fontHeight * LINES);
        }

        public void showView(GameCharacterInfo characterInfo) {
            ViewObject[] viewObjects = characterInfo.getViewObjects();

            if(viewObjects != null && viewObjects.length > 0) {
            } else {
                viewObjects = new ViewObject[5];
                int pos = -2;
                for(int i=0; i<viewObjects.length; i++) {
                    viewObjects[i] = new ViewObject();
                    viewObjects[i].setDirection(ViewObject.FACING_PLAYER);
                    viewObjects[i].setObjectType(ViewObject.WALL);
                    viewObjects[i].setPosition(new Position(pos++, -3, 0));
                }
                //        obj.setObjectType(ViewObject.WALL);
                //        obj.setDirection(ViewObject.FACING_PLAYER);
                // Set position of view object relative to characters
                // position. Note that the "z" coordinate (height) is
                // always 0 because in the current laby world a character
                // will always only see objects on the same height level.
                ///        obj.setPosition(0, -3, 0);

                System.out.println("Render 3d view");
                characterInfo.setDirection(IWorld.NORTH);
                characterInfo.setViewObjects(viewObjects);
            }

            String[] lines = m_view3D.renderView(characterInfo);
            System.out.println("Set "+lines.length+" lines in HTML pane");
            m_viewPane.setContentType("text/html");
            m_viewPane.setText(concatenateAll(lines));
        }

        private String concatenateAll(String[] text) {
            String result = "";
            for(int i=0; i<text.length; i++) {
                System.out.println("** "+text[i]);
                result = result + text[i];
            }
            return result;
        }
    }
}

