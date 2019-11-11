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

import antlr.RecognitionException;
import antlr.TokenStreamException;

public class CfLoop extends ParamTagNode
{
	private boolean condition = false;
	
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
			else if (s.toLowerCase().equals("condition"))
				s = null;
			if (s != null)
				out.print(" " + s + " = \"" + getEL(z) + "\"");
		}
		if (!Closable || (Closable && closed))
			out.print("/");
		out.print(">");
		en = params.keys();
		while (en.hasMoreElements())
		{
			String s = (String)en.nextElement();
			if (s.equalsIgnoreCase("condition"))
			{
				String z = ((Node)getParameter(s)).getValue().toString();
				condition = true;
				ExprLexer le = new ExprLexer(new StringReader(z));
				ExprParser pe = new ExprParser(le);
				try
				{
					z =  "${" + pe.gen().toString() + "}";
				}
				catch (RecognitionException e)
				{
					e.printStackTrace();
				}
				out.println("<cf:condition expression=\"" + z + "\">");
				break;
			}
		}
		if (getWhitespace() != null && getWhitespace().length() > 0)
			out.print(getWhitespace());
	}
	
	public void processFinish(PrintWriter out) throws Exception
	{
		if (condition)
			out.println("</cf:condition>");
		super.processFinish(out);
	}
}
