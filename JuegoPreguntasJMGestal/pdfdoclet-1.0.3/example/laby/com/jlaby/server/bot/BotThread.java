package com.jlaby.bot.server;

/*
 * @(#)BotThread.java 0.1 99/Feb/12
 *
 */
import java.util.Properties;

import com.jlaby.action.*;
import com.jlaby.action.actions.*;
import com.jlaby.bot.*;
import com.jlaby.character.*;
import com.jlaby.client.*;
import com.jlaby.client.answers.*;
import com.jlaby.config.*;
import com.jlaby.exception.*;
import com.jlaby.jdbc.*;
import com.jlaby.log.*;
import com.jlaby.world.*;

/**
 * This class implements the thread which is responsible
 * for "driving" the bots (non-player-characters) in the
 * laby game.
 * <P>
 * This class could have been derived from "java.lang.Thread"
 * as well, but I prefer to stick to interfaces to retain
 * more flexibility (hence the "start()" method).
 *
 * @author  Marcel Schoen
 * @version $Id: BotThread.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class BotThread implements Runnable {

    private Bot[] m_bots = null;
    private IWorld m_world = null;

    /**
     * Constructs a BotThread.
     *
     * @param world Instance of the world in which 
     */
    public BotThread(IWorld world) {
        m_world = world;
        m_bots = new Bot[1];
        m_bots[0] = new Bot("tfr");
    }

    /**
     * Creates a new thread and runs this class
     * in it.
     */ 
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Contains the logic which drives the bots.
     */
    public void run() {

        try {

            String[] name = new String[1];
            name[0] = "tfr";

            // first, create bots and, thereby,
            // have them logging in into the laby world.
            for(int i=0; i<m_bots.length; i++) {
                try {
                    m_bots[i] = new Bot(name[i]);
                    if(m_bots[i].isLoggedIn()) {
                        GameCharacter character = CharacterState.load(name[i]);
                        m_bots[i].setCharacter(character);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.error(this, "run", "Error occurred with bot "+m_bots[i].getName());
                }
            }

            // now start main loop
            while(true) {
                for(int i=0; i<m_bots.length; i++) {
                    Thread.sleep(1000);
                    try {
                        if(m_bots[i].isLoggedIn()) {
                            Log.info(this, "run", "Handle bot "+m_bots[i].getName());
                            LabyAction action = m_bots[i].getBrain().think(m_bots[i].getInfo());
                            if(action != null) {
                                action.setSource(m_bots[i].getCharacter());
                                LabyAnswer answer = m_world.handleAction(action);
                                String className = answer.getClassName();
                                if(className.equals("com.jlaby.client.answers.GameInfoAnswer")) {
                                    GameInfoAnswer infoAnswer = (GameInfoAnswer)answer;
                                    m_bots[i].setInfo(infoAnswer.getCharacterInfo());
                                }

                                GameCharacter character = m_bots[i].getCharacter();
                                if(character.isStateful()) {
                                    Log.info(this, "run", "Save character state");
                                    CharacterState.save(character);
                                }

                            }
                        } else {
                            Log.info(this, "run", "Bot "+m_bots[i].getName()+" not logged in, do nothing.");
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                        Log.error(this, "run", "Error occurred with bot "+m_bots[i].getName());
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            try {
                Log.error(this, "run", "Bot-Server aborting due to exception: "+e.getMessage());
            } catch(Exception ex) {
            }
            System.exit(-1);
        }
    }

    /**
     * helper class used to store a Bot's state
     */
    private class Bot {

        private BotBrain m_brain = new BotBrain();
        private GameCharacterInfo m_info = null;
        private GameCharacter m_character = null;
        private String m_name = "NO_PLAYER";
        private boolean m_loggedIn = false;

        public Bot(String name) {
            m_name = name;

            String password = "htzenpfrtz";

            IWorld world = WorldFactory.getWorld();
            LabyAnswer answer = world.handleAction(new LoginLocalAction(m_name, password));
            String className = answer.getClassName();
            if(className.equals("com.jlaby.client.answers.GameInfoAnswer")) {
                GameInfoAnswer infoAnswer = (GameInfoAnswer)answer;
                m_info = infoAnswer.getCharacterInfo();
                m_loggedIn = true;
            }

        }

        public boolean isLoggedIn() {
            return m_loggedIn;
        }

        public String getName() {
            return m_name;
        }

        public void setBrain(BotBrain brain) {
            m_brain = brain;
        }

        public BotBrain getBrain() {
            return m_brain;
        }

        public void setCharacter(GameCharacter character) {
            m_character = character;
        }

        public GameCharacter getCharacter() {
            return m_character;
        }

        public void setInfo(GameCharacterInfo info) {
            m_info = info;
        }

        public GameCharacterInfo getInfo() {
            return m_info;
        }
    }
}

