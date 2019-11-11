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
import java.io.StringReader;
import java.util.Enumeration;

import ro.innovative.iml.lang.cf.compiler.antlr.ExprLexer;
import ro.innovative.iml.lang.cf.compiler.antlr.ExprParser;

import antlr.TokenStreamException;

public class ParamTagNode extends Tag
{
	protected boolean Closable = false; 
	protected boolean closed = false;
	protected String handler = null;
	protected int action;
	
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public ParamTagNode(String name, String opt)
	{
		super(name, opt);
	}
	
	public ParamTagNode()
	{
	
	}
	
	public void processStart(PrintWriter out)throws TokenStreamException 
	{
		out.print("<cf:" + name);
		Enumeration<String> en = params.keys(); 
		while (en.hasMoreElements())
		{
			String s = (String)en.nextElement();
			String z = ((Node)getParameter(s)).getValue().toString();
			if (s.toLowerCase().equals("default"))
				s = "defaultval";
			else if (s.toLowerCase().equals("class"))
				s = "theclass";
			else if (s.toLowerCase().equals("parent"))
				s = "theparent";
			//System.out.println(z);
			out.print(" " + s + " = \"" + getEL(z) + "\"");
		}
		if (!Closable || (Closable && closed))
			out.print("/");
		out.print(">");
		if (getWhitespace() != null && getWhitespace().length() > 0)
			out.print(getWhitespace());
	}
	
	public static String getEL(String s)
	{
		int i = s.indexOf("#");
		if (i == -1)
			return s;
		else
		{
			String z = s.substring(0, i);
			s = s.substring(i + 1);
			i = s.indexOf("#");
			if (i == 0)
				return z + '#' + s.substring(i + 1);
			else if (i == -1)
				return s;
			else
			{
				try
				{
					ExprLexer le = new ExprLexer(new StringReader(s.substring(0, i)));
					ExprParser pe = new ExprParser(le);
					z =  z + "${" + pe.gen().toString() + "}" + getEL(s.substring(i + 1));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return z;
			}
		}
	}
	
	/*public String escape(String s)
	{
		int i = s.indexOf('\\');
		String z = s;
		while (i != -1)
		{
			z = s.substring(0, i) + '\\' + s.substring(i);
			i = s.indexOf('\\', i + 1);
		}
		return z;
	}*/
	
	public void processFinish(PrintWriter out) throws Exception
	{
		if (Closable && !closed)
		{
			out.println("</cf:" + name + '>');
			if (getTrailwhitespace() != null && getTrailwhitespace().length() > 0)
				out.print(getTrailwhitespace());
		}
	}

	public boolean isClosable() {
		return Closable;
	}

	public void setClosable(boolean closable) {
		Closable = closable;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		String pref = "";
		if (Closable)
			if (closed)
				pref = "[CL] ";
			else
				pref = "[CO] ";
		else
			pref = "[CT] ";
		sb.append(pref + "<cf:" + name);
		Enumeration<String> en = params.keys(); 
		while (en.hasMoreElements())
		{
			String s = (String)en.nextElement();
			String z = ((Node)getParameter(s)).getValue().toString();
			sb.append(" " + s + " = \"" + getEL(z) + "\"");
		}
		sb.append(">");
		return sb.toString();
	}

	public String getHandler()
	{
		return handler;
	}

	public void setHandler(String handler)
	{
		this.handler = handler;
	}

	public void setAction(int action)
	{
		this.action = action;		
	}

	public int getAction()
	{
		return action;
	}
}
