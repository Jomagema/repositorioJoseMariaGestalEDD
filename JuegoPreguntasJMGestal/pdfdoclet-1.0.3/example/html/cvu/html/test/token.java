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
import java.util.Enumeration;

class token {
	public static void main (String[] args) {

		if (args.length < 1) return;

		HTMLTokenizer ht = new HTMLTokenizer(args[0]);

		Enumeration e = ht.getTokens();

		while (e.hasMoreElements())
			System.out.println(e.nextElement());
	}
}
