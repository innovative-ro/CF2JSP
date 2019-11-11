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

import java.io.PrintWriter;

import javax.servlet.jsp.PageContext;

public class Logger
{
	public final static int NOLOG = 0;
	public final static int ERRORLOG = 1;
	public final static int FULLLOG = 2;

	private static int log = 2;
	private static String path;
	private static int indent = 0;

	private static PrintWriter pw;

	public static boolean doLog()
	{
		return log > 0 ? true : false;
	}

	public static void setLog(int log)
	{
		Logger.log = log;
	}

	public static String getPath()
	{
		return path;
	}

	public static void setPath(String path)
	{
		if (Logger.path == null/* || !Logger.path.equals(path)*/)
		{
			/*if (pw != null)
			{
				pw.close();
			}*/
			try
			{
				pw = new PrintWriter(path);
			}
			catch (Exception e)
			{
				// silentlly ignore for debugin purposes
			}
			Logger.path = path;
		}
		pw.println("\n\\\\\\ New session");
		

	}

	public static void logCfSet(PageContext pc, String name, String value)
	{
		if (log == FULLLOG)
		{
			if (value == null)
			{
				println(pc, name + " = " + "NULL VALUE");
				return;
			}
			if (name == null)
			{
				println(pc, "NULL NAME = NULL VALUE>");
				return;
			}
			if (value.equals(""))
			{
				println(pc, name + " = " + "\"\" VALUE");
				return;
			}
			println(pc, name + " = " + value );
			if (pc == null)
				return;
			Object o = CFVariableUtil.exists(
					pc, 
					name);
			String s = o.toString();
			if (s != null)
			{
				if (!s.equals(value))
					println(pc, "\t>> ERROR: " + name + " is " + s + ", not " + value);
			}
			else
				println(pc, ">> " + name + " not found");
		}
		else if (log == ERRORLOG)
		{
			Object o = CFVariableUtil.exists(pc, name);
			String s = o.toString();
			if (s != null)
			{
				if (!s.equals(value))
				{
					print(pc, name + " = " + value);
					println(pc, "\t>> ERROR: " + name + " is " + s + ", not " + value);
				}
			}
			else
			{
				print(pc, name + " = " + value);
				println(pc, "\t>> " + name + " not found");
			}
		}
	}
	
	public static void logCfSwitch(PageContext pc, String exp)
	{
		println(pc, "<cfswitch expr = " + exp + ">"); 
	}
	
	public static void logCfCondition(PageContext pc, boolean exp)
	{
		println(pc, "<cfcondition expr = " + exp + ">"); 
	}
	
	public static void println(PageContext pc, String s)
	{
		printIndent();
		pw.println(s);
		pw.flush();
	}
	
	public static void print(PageContext pc, String s)
	{
		printIndent();
		pw.print(s);
		pw.flush();
	}
	
	public static void push()
	{
		indent++;
	}
	
	public static void pop()
	{
		if (indent > 0)
			indent--;
	}
	
	private static void printIndent()
	{
		for (int i = 0; i < indent; i++)
			pw.print("  ");
	}
	
	public static void reset()
	{
		path = "";
		indent = 0;
		log = 2;
	}
}
