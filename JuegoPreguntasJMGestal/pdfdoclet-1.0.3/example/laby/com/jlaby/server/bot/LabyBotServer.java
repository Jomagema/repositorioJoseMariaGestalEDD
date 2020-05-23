package com.jlaby.bot.server;

/*
 * @(#)LabyBotServer.java 0.1 99/Feb/12
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
 * Server for running multiple Laby bots within one
 * Java VM instance.
 *
 * @author  Marcel Schoen
 * @version $Id: LabyBotServer.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class LabyBotServer {

    /**
     * The main method used to start the server.
     * It takes the path of the configuration properties
     * file as a single commandline parameter.
     *
     * @param args the commandline arguments.
     */
    public static void main(String[] args) {
        if(args != null && args.length > 0) {
            try {
                Configuration.load(args[0]);
                Log.initialize();
                Log.info("LabyBotServer", "Constructor", "Begin initialization...");
                WorldFactory.createLocalWorld();
                JdbcUtil.createConnection();
                Log.info("LabyBotServer", "Constructor", "Bot server initialized.");

                BotThread botThread = new BotThread(WorldFactory.getWorld());
                botThread.start();

            } catch(Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            System.out.println("usage: com.jlaby.server.bot.LabyBotServer <path of configuration properties file>");
        }
    }
}

