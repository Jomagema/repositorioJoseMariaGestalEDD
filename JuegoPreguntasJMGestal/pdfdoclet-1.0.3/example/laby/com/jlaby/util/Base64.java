package com.jlaby.util;

/**
 * @(#)Base64.java
 *
 * Copyright TARSEC Corp.
 * 8047 Zurich, Switzerland,  All Rights Reserved.
 */

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * This class is used for Base64-encoding (as specified in RFC1521).
 * PLEASE NOTE: There is no 76 character limit for the resulting
 * string. The primary purpose of this class is to encode username
 * and passwords in HTTP/Proxy authentication requests and to be
 * as small, lightweight and fast as possible.
 *
 * @version $Id: Base64.java,v 1.1 2013/10/27 23:51:29 marcelschoen Exp $
 * @author  Marcel Sch�n
 */
public class Base64 {

    private static char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                    'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                    'w', 'x', 'y', 'z', '0', '1', '2', '3',
                    '4', '5', '6', '7', '8', '9', '+', '/'};

    private static byte[] data = new byte[4];
    private static char[] source = null;
    private static byte[] buffer = null;
    private static char[] charbuffer = null;

    /**
     * Default constructor
     */
    public Base64() {
    }

    /**
     * Decode Base64 encoded data which is contained in a String.
     *
     * @param text The String containing the Base64 encoded data.
     * @return A byte-array with the decoded data.
     */
    public synchronized static byte[] decode(String text) {
        int len = 0, valids = text.length();
        if(text.endsWith("=="))
            valids -= 2;
        else {
            if(text.endsWith("="))
                valids -= 1;
        }
        source = new char[valids];
        buffer = new byte[source.length];

        text.getChars(0,source.length,source,0);
        int num = source.length / 4;
        int crap = source.length - (num * 4);
        int i = 0;
        for(; i<num*4; i+=4) {
            for(int u=0; u<4; u++) {
                data[u] = getNumber(source[i+u]);
	    }
            buffer[len] = (byte)((data[0] << 2) | (data[1] >> 4));
            buffer[len+1] = (byte)((data[1] << 4) | (data[2] >> 2));
            buffer[len+2] = (byte)((data[2] << 6) | data[3]);
            len += 3;
        }
        if(crap > 0) {
            if(crap == 3) {
                for(int u=0; u<3; u++)
                    data[u] = getNumber(source[i+u]);
                buffer[len] = (byte)((data[0] << 2) | (data[1] >> 4));
                buffer[len+1] = (byte)((data[1] << 4) | (data[2] >> 2));
                len += 2;
            }
            if(crap == 2) {
                for(int u=0; u<2; u++)
                    data[u] = getNumber(source[i+u]);
                buffer[len] = (byte)((data[0] << 2) | (data[1] >> 4));
                len += 1;
            }
        }
        byte[] newdata = new byte[len];
        for(i=0; i<newdata.length; i++)
            newdata[i] = buffer[i];
        return newdata;
    }

    /**
     * Encode a byte array Base64 compliant.
     *
     * @param source A byte-array containing the data to be encoded
     * @return A String object holding a Base64-compliant String
     */
    public synchronized static String encode(byte[] source) {
        charbuffer = new char[(source.length*3) + 1];
        int pos = 0, pos2 = 0;
        int steps = source.length / 3;  // convert 24 bits at a time
        for(int i=0; i<steps; i++) {
            charbuffer[pos2++] = chars[((source[pos] >> 2) & 63)];
            charbuffer[pos2++] = chars[(((source[pos] & 3) << 4) + ((source[pos+1] >> 4) & 15))];
            charbuffer[pos2++] = chars[(((source[pos+1] & 15) << 2) + ((source[pos+2] >> 6) & 3))];
            charbuffer[pos2++] = chars[(source[pos+2] & 63)];
            pos += 3;
        }

        if(pos < source.length) {       // some bits left?
            if(source.length - pos == 1) {
                charbuffer[pos2++] = chars[((source[pos] >> 2) & 63)];
                charbuffer[pos2++] = chars[((source[pos] & 3) << 4)];
                charbuffer[pos2++] = '=';
            }
            if(source.length - pos == 2) {
                charbuffer[pos2++] = chars[((source[pos] >> 2) & 63)];
                charbuffer[pos2++] = chars[(((source[pos] & 3) << 4) + ((source[pos+1] >> 4) & 15))];
                charbuffer[pos2++] = chars[((source[pos+1] & 15) << 2)];
            }
            charbuffer[pos2++] = '=';
        }

        return new String(charbuffer,0,pos2);
    }

    /**
     * Encode a textstring Base64 compliant.
     *
     * @param source A String object containing the text to be encoded
     * @return A String object holding a Base64-compliant String
     */
    public static String encode(String source) {
        if(source!=null && source.trim().length()>0) {
            return encode(source.getBytes());
        }
        else {
            return new String("");
        }
    }

    private static byte getNumber(char value) {
        byte ret = -1;
        for(int i=0; i<chars.length && ret==-1; i++) {
            if(chars[i] == value) {
                ret = (byte)i;
	    }
	}
        return ret;
    }
}
