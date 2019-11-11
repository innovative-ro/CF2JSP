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
 
package ro.innovative.iml.lang.cf.node;

import java.io.*;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node {
	protected Hashtable<String, Node> params;
	private ArrayList<Node> children;
	private String whitespace = "";
	private String trailwhitespace = "";

	private String value;

	public Node() {
		params = new Hashtable<String, Node>();
		children = new ArrayList<Node>();
	}

	public Node(String s) {
		value = s;
	}

	public void addWhitespace(String ws) {
		if (ws != null)
			whitespace += ws;
	}

	public void addTrailWhitespace(String ws) {
		if (ws != null)
			trailwhitespace += ws;
	}

	public void setParameter(String key, Node value) {
		if ((key != null) && (value != null))
			params.put(key, value);
	}

	public Node getParameter(String key) {
		return (Node) params.get(key);
	}

	public Enumeration<String> getParameters() {
		return params.keys();
	}

	public void addChild(Node child) {
		if (child != null)
			children.add(child);
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void processStart(PrintWriter out) throws Exception {
	}

	public void processFinish(PrintWriter out) throws Exception {
		if (getWhitespace() != null && getWhitespace().length() > 0)
			out.print(getWhitespace());
	}

	public int getChildNo() {
		return children.size();
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void process(Enumeration en, PrintWriter out) throws Exception {
		processStart(out);
		/*
		 * if (getWhitespace() != null && getWhitespace().length() > 0)
		 * out.print(getWhitespace());
		 */
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) en
					.nextElement();
			Node node = (Node) child.getUserObject();
			node.process(child.children(), out);
		}
		processFinish(out);
	}

	public String toString() {
		return value;
	}

	public String getWhitespace() {
		return whitespace;
	}

	public String getTrailwhitespace() {
		return trailwhitespace;
	}

	public void setTrailwhitespace(String trailwhitespace) {
		this.trailwhitespace = trailwhitespace;
	}
}
