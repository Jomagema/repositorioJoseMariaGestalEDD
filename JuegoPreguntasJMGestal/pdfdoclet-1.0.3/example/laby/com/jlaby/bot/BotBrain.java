package com.jlaby.bot;

/*
 * @(#)BotBrain.java 0.1 99/Feb/12
 *
 */
import com.jlaby.action.*;
import com.jlaby.action.actions.*;
import com.jlaby.client.*;
import com.jlaby.exception.*;

/**
 * Base class for bot KI implementations. In order to
 * "deploy" bots in the central bot server, their logic
 * must be implemented in a class derived from this one.
 * <P>
 * The second option is to create a bot in an own Java VM
 * process which behaves just like a GUI client and may
 * run on any remote client system. For such bots, the client
 * classes should be used.
 *
 * @see com.jlaby.client.Client
 * @author  Marcel Schoen
 * @version $Id: BotBrain.java,v 1.1 2013/10/27 23:51:28 marcelschoen Exp $
 */
public class BotBrain  {

    // instance variable used to ensure that the bot carries
    // out the turn-action not faster than every 3 seconds.
    private long m_lastTurn = 0;

    /**
     * This method must implement the bot's logic. The default implementation
     * of this method has the bot carrying out 90-degree turns every 3 seconds.
     *
     * @param info Information about the bot's environment. Based on this
     *                info, the bot must decide what action it wants to take.
     * @return A LabyAction object. May be null if the bot does not
     *         want to take any action.
     * @exception LabyException If the bot encountered an internal problem
     */
    public LabyAction think(GameCharacterInfo info) throws LabyException {
        LabyAction action = null;
        long current = System.currentTimeMillis();
        if(current - m_lastTurn > 3000) {
            m_lastTurn = current;
            action = new TurnAction(TurnAction.LEFT);
        }
        return action;
    }
}

