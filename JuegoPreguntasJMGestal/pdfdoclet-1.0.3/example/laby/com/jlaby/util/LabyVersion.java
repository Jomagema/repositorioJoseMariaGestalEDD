/*
 * @(#)LabyVersion.java
 *
 * @Copyright: Marcel Schoen, Switzerland, 2002, All Rights Reserved.
 */

package com.jlaby.util;

import java.util.Properties;
import java.io.InputStream;

/**
 * Reads the version information from "version.properties"
 * (which is in the root of the classpath) and provides
 * statis methods to access this information.
 *
 * @author Marcel Schoen, TARSEC AG
 * @version $Id: LabyVersion.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 */
public class LabyVersion {

    private static String buildID = "<unknown>";
    private static String releaseID = "<unknown>";
    private static String buildCVSTag = "<unknown>";

    static {
        try {
            Properties properties = new Properties();
            InputStream in = LabyVersion.class.getResourceAsStream("/version.properties");
            properties.load(in);
            buildID = properties.getProperty("version.build.id", "<unknown>");
            releaseID = properties.getProperty("version.release.id", "<unknown>");
            buildCVSTag = properties.getProperty("version.build.cvstag", "<unknown>");
        } catch(Exception e) {
            System.out.println("Unable to retrieve version info: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Returns the CVS tag with which all the
     * project files had been tagged for the build.
     *
     * @return The CVS tag string.
     */
    public static String getBuildCVSTag() {
        return buildCVSTag;
    }

    /**
     * Returns the build ID number.
     *
     * @return The build ID.
     */
    public static String getBuildID() {
        return buildID;
    }

    /**
     * Returns the release ID (which is
     * the public version number of the
     * product or library).
     *
     * @return The release ID.
     */
    public static String getReleaseID() {
        return releaseID;
    }

    /**
     * Returns the release ID (which is
     * the public version number of the
     * product or library).
     *
     * @return The release ID.
     */
    public static String getVersion() {
        return getReleaseID();
    }
}
