/**
 * Copyright (c) 2006 - present Innovative Systems SRL
 * Copyright (c) 2006 - present Ovidiu Podisor ovidiu.podisor@innodocs.com
 * 
 * Authors: Ovidiu Podisor and members of the
 *          IML lab at West University Timisoara (www.uvt.ro)
 * 
 * This file is part of the CF2JSP project.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package ro.innovative.iml.lang.cf.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class CFListUtil implements java.lang.Iterable<String>, Iterator<String> {

	protected String list;

	private char[] delim;

	private int seek;

	public CFListUtil(String list) {
		this.list = list;
		this.delim = new char[1];
		this.delim[0] = ',';
		startProcessing();
	}

	public CFListUtil(String list, String delim) {
		this.list = list;
		this.delim = new char[delim.length()];

		for (int i = 0; i < this.delim.length; i++)
			this.delim[i] = delim.charAt(i);
		startProcessing();
	}

	public CFListUtil(String list, char delim) {
		this.list = list;
		this.delim = new char[1];
		this.delim[0] = delim;
		startProcessing();
	}

	public CFListUtil(String list, char[] delim) {
		this.list = list;
		this.delim = delim;
		startProcessing();
	}

	public void startProcessing() {
		seek = 0;
		// take delims from behind
		while (seek < list.length() && isDelimiter(list.charAt(seek))) {
			seek++;
		}
	}

	public boolean hasNext() {
		if (list.length() == 0)
			return false;

		return seek < list.length();
	}

	public String next() {
		StringBuffer sb = new StringBuffer();
		// append those good chars
		while (seek < (list.length()) && !isDelimiter(list.charAt(seek))) {
			sb.append(list.charAt(seek));
			seek++;
		}
		// take delims from ahead
		while (seek < (list.length()) && isDelimiter(list.charAt(seek))) {
			seek++;
		}

		return sb.toString();
	}

	public void remove() {
	}

	private boolean isDelimiter(char x) {
		for (int i = 0; i < delim.length; i++)
			if (x == delim[i])
				return true;
		return false;
	}

	public Iterator<String> iterator() {
		return this;
	}

	public String[] stringToArray() {
		Vector<String> x = new Vector<String>();

		for (String a : this) {
			x.add(a);
		}

		String[] q = new String[0];
		return x.toArray(q);
	}

	public static String arrayToString(String[] x, char delim) {
		StringBuffer sb = new StringBuffer();

		for (String a : x) {
			sb.append(a + delim);
		}

		return sb.toString().substring(0, sb.lastIndexOf(delim + ""));
	}

	public char[] getDelim() {
		return delim;
	}

	public void setDelim(char[] delim) {
		this.delim = delim;
	}

	public void setDelim(String delim) {
		this.delim = new char[delim.length()];

		for (int i = 0; i < this.delim.length; i++)
			this.delim[i] = delim.charAt(i);
	}

	public void setDelim(char delim) {
		this.delim = new char[1];
		this.delim[0] = delim;
	}

}
