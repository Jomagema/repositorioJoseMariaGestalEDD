package com.jlaby.util;

/*
 * @(#)Util.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 */

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.*;

import com.jlaby.exception.*;

/**
 * Utility class
 *
 * @version $Id: Util.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 * @author  Marcel Schoen, TARSEC AG
 */
public class Util {

    private static Hashtable ms_out = new Hashtable();
    private static Hashtable ms_session = new Hashtable();

    public static void setOut(PrintWriter out) {
        ms_out.put(Thread.currentThread(), out);
    }

    public static void setSession(HttpSession session) {
        ms_session.put(Thread.currentThread(), session);
    }

    public static void disposeCurrentThreadInfo() {
        getOut().flush();
        getOut().close();
        ms_out.remove(Thread.currentThread());
        ms_session.remove(Thread.currentThread());
    }

    public static byte[] getObjectData(Object object) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteOut);
        oos.writeObject(object);
        oos.close();
        return byteOut.toByteArray();
    }

    public static Object getObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream input = new ObjectInputStream(byteStream);
        return input.readObject();
    }

    public static HttpSession getSession() {
        HttpSession result = null;
        Object object = ms_session.get(Thread.currentThread());
        if(object != null && object instanceof HttpSession) {
            result = (HttpSession)object;
        }
        return result;
    }

    public static PrintWriter getOut() {
        PrintWriter result = null;
        Object object = ms_out.get(Thread.currentThread());
        if(object != null && object instanceof PrintWriter) {
            result = (PrintWriter)object;
        }
        return result;
    }

    /**
     * Print to servlets output stream
     *
     * @param text The text to print
     */
    public static void println(String txt) {
        getOut().println(txt);
    }

    /**
     * Load an image either from a local file or a URL. It may also
     * be in an archive file within the classpath; it that case, it's
     * beeing loaded by the classloader as a resource.
     *
     * @param picname The name of the imagefile, either absolute or relative.
     */
    public static Image loadImage(String picname, Object client) {
        return loadImage(picname, null, client);
    }

    /**
     * Load an image either from a local file or a URL. It may also
     * be in an archive file within the classpath; in that case, it's
     * beeing loaded by the classloader as a resource.
     *
     * @param picname The name of the imagefile, either absolute or relative.
     * @param parent The parent Frame (which is used for the MediaTracker).
     *               This value may be null.
     */
    public static Image loadImage(String picname, Frame parent, Object client) {
        Frame target = parent;
        if(target == null) {
            target = new Frame();
        }
        File testfile = new File(picname);
        if(!testfile.exists()) {
            try {
                URL imgrsc = client.getClass().getResource(picname);
                if(imgrsc != null) {
                    Image myImage = Toolkit.getDefaultToolkit().getImage(imgrsc);
                    MediaTracker myTracker = new MediaTracker(target);
                    myTracker.addImage(myImage, 0);
                    try {
                        myTracker.waitForID(0);
                        return myImage;
                    }
                    catch(InterruptedException e) {
                        return null;
                    }
                }
                else {
                    try {
                        String file = extractFile(picname);
                        Image myImage = loadImageFromFile(file, parent);
                        return myImage;
                    }
                    catch(Exception e) {
                        System.out.println("Tool: Failed to load image "+picname);
                        e.printStackTrace();
                    }
                }
            }
            catch(Exception e) {
                System.out.println("Tool: Failed to load image "+picname);
                e.printStackTrace();
            }
        }
        else {
            return loadImageFromFile(picname, parent);
        }
        return null;
    }

    /**
     * Load an image from a GIF or JPEG file, using the MediaTracker to make
     * sure the image is loaded completely before the thread continues.
     *
     * @param filename The absolute path of the image file.
     */
    public static Image loadImageFromFile(String filename, Frame parent) {
        Frame target = parent;
        if(target == null) {
            target = new Frame();
        }
        Image myImage = Toolkit.getDefaultToolkit().getImage(filename);
        MediaTracker myTracker = new MediaTracker(target);
        myTracker.addImage(myImage, 0);
        try {
            myTracker.waitForID(0);
            return myImage;
        }
        catch(InterruptedException e) {
            return null;
        }
    }

    /**
     * Extracts a file which must be somewhere in the CLASSPATH to the
     * system specific temp directory and returns a reference to the
     * extracted file. Use slashes as package delimiters. For example,
     * if you want to extract a GIF image file which is in the
     * archive inside the package COM.tarsec.icons and is named
     * Acme.gif, use this parameter:<P>
     * <code>/COM/tarsec/icons/Acme.gif</code>
     *
     * @param resource The name of the resource to unpack.
     * @return The absolute path of the extracted file.extract file: " + re
     */
    public static String extractFile(String resource) {
        String result = null, path = "";
        boolean done = false;
        try {
            String filename = resource.replace(File.separatorChar, '/');
            if(filename.indexOf("/") != -1) {
                filename = filename.substring(filename.lastIndexOf("/")+1,
                                              filename.length());
            }
            File extractedFile = File.createTempFile(filename, "tmp");
            InputStream in = filename.getClass().getResourceAsStream(resource);
            if(in == null) {
                System.out.println("Resource "+resource+" not found; Util.extractFile() failed.");
            }
            else {
                extract(in, extractedFile.getAbsolutePath());
                result = extractedFile.getAbsolutePath();
            }
        }
        catch(Exception e) {
            System.out.println("Failed to extract "+resource);
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    private static void extract(InputStream in, String outfile) throws IOException {
        String sizeText = null;
	byte[] data = new byte[4000];
        int read = 0, pos = 0;

        FileUtil.makeDirsForFile(outfile);

        FileOutputStream out = new FileOutputStream(outfile);
        while((read = in.read(data)) > 0) {
            out.write(data,0,read);
        }
        out.flush();
        out.close();
        in.close();
    }
}
