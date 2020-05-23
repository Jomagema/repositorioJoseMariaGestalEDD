package com.jlaby.util;

/*
 * @(#)FileUtil.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 */

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.Vector;

/**
 * Generic file- and path-handling utility class
 *
 * @version $Id: FileUtil.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 * @author  Marcel Schoen, TARSEC AG
 */
public class FileUtil {

    /**
     * This little method ensures that a path string ends with
     * a file separator character by appending one if necessary.
     * It's just two lines of code, but its used very often
     * in a lot of classes throughout the TARSEC package, that's
     * why it has been put into this own method here.
     *
     * @param path The path that should get a file separator char.
     */
    public static String endPathWithSeparator(String path) {
        if(path == null) {
            return "";
        }
        if(!path.endsWith(File.separator)) {
            path = path.concat(File.separator);
	}
	return path;
    }

    /**
     * Reads a binary file and returns the content of the file
     * as a byte array. Be careful to not use this method
     * on very large files.
     *
     * @param filename The absolute path of the binary file.
     * @returns byte array containing the file data.
     */
    public static byte[] readBinaryFile(String filename) throws IOException {
        java.io.File file = new java.io.File(filename);
        int size = (int)file.length();
        byte[] content = new byte[size];

	BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
	in.read(content);
	in.close();

        return content;
    }

    /**
     * Reads a textfile and returns the content of the file
     * as a String array. Be careful to not use this method
     * on very large files.
     *
     * @param filename The absolute path of the textfile.
     * @returns String array containing the text lines.
     */
    public static String[] readTextFile(String filename) throws IOException {
        return readTextFileStream(new FileReader(filename));
    }

    /**
     * Reads a textfile and returns the content of the file
     * as a String array. Be careful to not use this method
     * on very large files.
     *
     * @param filename The absolute path of the textfile.
     * @returns String array containing the text lines.
     */
    public static String[] readTextFileStream(Reader reader) throws IOException {
        String[] content = null;
        String txt = null;
        Vector buffer = new Vector();

	BufferedReader rd = new BufferedReader(reader);
	while((txt = rd.readLine()) != null) {
	    buffer. addElement(txt);
	}
	rd.close();
	content = new String[buffer.size()];
	for(int i=0; i<content.length; i++) {
	    content[i] = (String)buffer.elementAt(i);
	}

        return content;
    }

    /**
     * Writes a textfile and creates the content of the file
     * from a String array.
     *
     * @param filename The absolute path of the textfile.
     * @param content The contents of the textfile.
     */
    public static void writeTextFile(String filename, String[] content) throws IOException {
	makeDirsForFile(filename);
	PrintWriter wrt = new PrintWriter(new FileWriter(filename));
	for(int i=0; i<content.length; i++) {
	    wrt.println(content[i]);
	}
	wrt.flush();
	wrt.close();
    }

    /**
     * Create all subdirectories for a given file path.
     * This is usefull when you want to create a new file
     * and want to make sure that the whole path exists.
     *
     * @param filepath Absolute path of the file, including the file itself.
     */
    public static void makeDirsForFile(String filename) throws IOException {
	File tst = new File(filename);
	String path = tst.getAbsolutePath();
	if(path.indexOf(File.separator) != -1) {
	    path = path.substring(0, path.lastIndexOf(File.separator));
	    File newdir = new File(path);
	    if(!newdir.exists())
		newdir.mkdirs();
	}
    }
}
