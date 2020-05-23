/*
 * HTML Parser
 * Copyright (C) 1997 David McNicol
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * file COPYING for more details.
 */

package cvu.html.test;

import cvu.html.*;
import java.io.*;
import java.util.Enumeration;

class tree {

	public static DataInputStream dis;

	public static void main (String[] args) {
		try {
			go(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getResponse (String prompt) throws Exception {

		String response = "";

		while (response == null || response.length() < 1) {
			System.out.print(prompt);
			System.out.flush();

			response = dis.readLine();
		}

		return response.trim().toLowerCase();
	}

	public static void go (String[] args) throws Exception {

		if (args.length < 1) return;
		HTMLTree ht = new HTMLTree(args[0]);
		HTMLNode curr = null;
		HTMLNode el = null;
		String response;

		dis = new DataInputStream(System.in);

		while (true) {

			response = getResponse("tree> ");

			if (response.startsWith("quit")) {

				break;

			} else if (response.startsWith("null")) {

				curr = null;

			} else if (response.startsWith("string")) {

				response = dis.readLine();
				if (curr != null) curr.addChild(response);

			} else if (response.startsWith("new")) {

				el = makeNew();
				if (curr != null) curr.addChild(el);

			} else if (response.startsWith("parent")) {

				if (curr != null) curr = curr.getParent();

			} else if (response.startsWith("first")) {

				if (curr != null) curr = curr.firstChild();

			} else if (response.startsWith("next")) {

				if (curr != null) curr = curr.nextSibling();

			} else if (response.startsWith("previous")) {

				if (curr != null) curr = curr.previousSibling();

			} else if (response.startsWith("print")) {

				System.out.println(curr);

			} else if (response.startsWith("arg")) {

				if (curr == null) continue;

				response = getResponse("Enter string: ");
				System.out.println(
				  curr.getAttributeToString(response));

			} else if (response.startsWith("findinall")) {
			
				response = getResponse("Enter string: ");
				curr = ht.findInAll(response);

			} else if (response.startsWith("findnextinall")) {

				curr = ht.findNextInAll(curr);

			} else if (response.startsWith("findinsubtree")) {

				response = getResponse("Enter string: ");
				curr = ht.findInSubtree(response, curr);

			} else if (response.startsWith("findincontext")) {

				response = getResponse("Enter string: ");
				curr = ht.findInContext(response, curr);

			} else if (response.startsWith("findsibling")) {

				curr = ht.findSibling(curr);

			}
		}
	}

	private static HTMLNode makeNew () throws Exception {

		String name = getResponse("name: ");
		HTMLNode el = new HTMLNode(name);

		String arg = getResponse("arg: ");

		while (arg.length() > 0) {

			String value = getResponse("value: ");

			if (value.length() < 1) value = null;

			el.addAttribute(arg, value);

			arg = getResponse ("arg: ");
		}

		return el;
	}
}
