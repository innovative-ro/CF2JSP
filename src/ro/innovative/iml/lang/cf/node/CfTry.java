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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import antlr.TokenStreamException;

public class CfTry extends ParamTagNode
{
	private boolean first = true;
	
	private int cc = 0;
	
	private void processOutput(Node root, Enumeration en, PrintWriter out) throws Exception
	{
		if (root instanceof ParamTagNode && ((ParamTagNode)root).getName().equalsIgnoreCase("catch"))
			return;
		root.processStart(out);
		while (en.hasMoreElements())
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)en.nextElement();
			Node node = (Node)child.getUserObject();
			processOutput(node, child.children(), out);
		}
		root.processFinish(out);
	}
	
	private void processCatch(Node root, Enumeration en, PrintWriter out) throws Exception
	{
		if (root instanceof ParamTagNode && ((ParamTagNode)root).getName().equalsIgnoreCase("catch"))
		{
			String s = "e.getType()";
			ParamTagNode p = (ParamTagNode)root;
			Node z = (Node)p.getParameter("type");
			if (z != null)
			{
				String zz = z.getValue().toString();
				if (zz.equalsIgnoreCase("any") || zz.equalsIgnoreCase("all"))
					s = "true";
				else
					s = s + ".equalsIgnoreCase(\"" + zz + "\")";
			}
			else 
				s = "true";
			if (first)
			{
				out.println("\t<% if (" + s + ") { %>");
				first = false;
			}
			else
				out.println("<% } else if (" + s +") { %>");
			while (en.hasMoreElements())
			{
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)en.nextElement();
				Node node = (Node)child.getUserObject();
				processOutput(node, child.children(), out);
			}
			cc++;
			//out.println("\t<% } %>");
		}
	}
	
	public void process(Enumeration en, PrintWriter out) throws Exception
	{
		processStart(out);
		ArrayList al = new ArrayList();
		while (en.hasMoreElements())
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)en.nextElement();
			al.add(child);
			Node node = (Node)child.getUserObject();
			processOutput(node, child.children(), out);
		}
		processFinish(out);
		out.println("<% } catch (CFException e) { %>");
		for (Object ch: al)
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)ch;
			Node node = (Node)child.getUserObject();
			processCatch(node, child.children(), out);
		}
		if (cc != 0)
			out.println("<% } else throw e; %>");
		//else
		//	out.println("<% } %>");
		out.println("<% } %>");
	}
	
	public void processStart(PrintWriter out) throws TokenStreamException 
	{
		out.println("<% try { %>\n");
		if (getWhitespace() != null && getWhitespace().length() > 0)
			out.print(getWhitespace());
	}
	
	public void processFinish(PrintWriter out) throws TokenStreamException 
	{
		//out.println("<% } %>\n");
		if (getTrailwhitespace() != null && getTrailwhitespace().length() > 0)
			out.print(getTrailwhitespace());
	}
}
