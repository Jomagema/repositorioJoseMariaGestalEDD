package com.jlaby.client.stub;

/**
 * @(#)HttpStubHelper.java
 *
 * Created on August 7, 2001, 8:30 PM
 */
import com.jlaby.action.*;
import com.jlaby.client.*;
import com.jlaby.client.http.*;
import com.jlaby.client.exception.*;
import com.jlaby.util.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.io.*;


/**
 * Utility class used to send HTTP requests
 * from a Java client to a Laby server (servlet).
 * This class copes with issues such as serializing
 * and Base64 encoding the Java objects to be sent
 * and vice-versa for the objects retrieved in the
 * servers' answer.
 *
 * @version $Id: HttpStubHelper.java,v 1.1 2013/10/27 23:51:27 marcelschoen Exp $
 * @author  Marcel Schoen
 */
public class HttpStubHelper {

    private String m_urlString = "";
    private Vector m_cookies = new Vector();

    /**
     * Constructs a HTTP stub helper for communication
     * with a specific URL.
     *
     * @param url the (HTTP) URL of the target server.
     */
    public HttpStubHelper(String url) {
        m_urlString = url;
    }

    /**
     * Constructs a HTTP stub helper for communication
     * with a specific URL and port number.
     *
     * @param url the (HTTP) URL of the target server.
     * @param port the port number.
     */
    public HttpStubHelper(String url, int port) {
        m_urlString = url + ":" + port;
    }

    /**
     * Serializes and Base64-encodes a given action object
     * and sends it with a POST request to the server URL
     * specified in the constructor. This method also sets
     * a particular HTTP header for the type of user agent
     * and MIME type in order to allow to the server to
     * distinguish this request from those sent by browser clients.
     *
     * @param action the action object that is to be sent.
     * @return the answer returned from the server.
     * @exception RemoteException thrown if there was any problem.
     */
    public LabyAnswer sendAction(LabyAction action) throws RemoteException {
        LabyAnswer labyAnswer = null;
        HttpURLConnection connect = null;
        try {
            String handlerClassName = System.getProperty("javaclient.handler.class", "com.jlaby.client.handler.JavaClientHandler");
            System.out.println("Target URL: "+m_urlString);
            connect = ConnectionFactory.getHttpConnection(m_urlString);
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestMethod("POST");
            //            connect.setRequestMethod("GET");
            //            connect.setRequestProperty("Content-Type", "application/x-serjavaobject");
            connect.setRequestProperty("User-Agent", "JavaLabyClient/1.0 "+handlerClassName);
            if(m_cookies.size() > 0) {
                for(int i=0; i<m_cookies.size(); i++) {
                    System.out.println("Send cookie: "+(String)m_cookies.elementAt(i));
                    connect.setRequestProperty("Cookie", (String)m_cookies.elementAt(i));
                }
            }

            PrintWriter wrt = new PrintWriter(new OutputStreamWriter(connect.getOutputStream()));

            // convert action object into byte array
            byte[] actionData = Util.getObjectData(action);

            String encodedAction = Base64.encode(actionData);

            // send action with parameters
            wrt.println("action="+encodedAction);
            wrt.flush();

            String header = null;
            int ct = 0;
            while((header = connect.getHeaderField(ct)) != null) {
                if(header.startsWith("sesessionid")) {
                    m_cookies.addElement(header);
                    System.out.println("Add cookie "+header);
                }
                ct ++;
            }

            // read answer from server
            BufferedReader rd = new BufferedReader(new InputStreamReader(connect.getInputStream()));

            // retrieve Base64-encoded Answer object from server
            String answerText = rd.readLine();

            // create new action object from serialization data
            labyAnswer = (LabyAnswer)Util.getObject(Base64.decode(answerText));

            // close all streams
            rd.close();
            wrt.close();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new RemoteException(exp.toString());
        }

        return labyAnswer;
    }
}
