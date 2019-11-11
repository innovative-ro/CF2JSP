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

import java.io.StringReader;
import java.util.Vector;

import ro.innovative.iml.lang.cf.compiler.antlr.ExprLexer;
import ro.innovative.iml.lang.cf.compiler.antlr.ExprParser;

public class OpNode extends Node
{
	private Object value;

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public OpNode(Object value)
	{
		this.value = value;
	}

	public String toString()
	{
		if (value instanceof String)
			if (value.equals("\\\\"))
				return "cff:dbs()";
			else if (value.equals("\\"))
				return "cff:bs()";
			else
				return formatEl(value.toString());
		else
			return value.toString();
	}

	/*
	 * private String escape(String s) { int i = s.indexOf('\\'); String z = s;
	 * while (i != -1) { z = s.substring(0, i) + '\\' + s.substring(i); i =
	 * s.indexOf('\\', i + 1); } return z; }
	 */

	private String formatEl3(String s)
	{
		int i = s.indexOf("#");
		if (i == -1)
			return "'" + s + "'";
		else
		{
			String z = s.substring(0, i);
			s = s.substring(i + 1);
			i = s.indexOf("#");
			if (i == 0)
				return "'" + z + '#' + s.substring(i + 1) + "'";
			else if (i == -1)
				return "";
			else
			{
				try
				{
					ExprLexer le = new ExprLexer(new StringReader(s.substring(0, i)));
					ExprParser pe = new ExprParser(le);
					String s1 = z;
					String s2 = pe.gen().toString();
					String s3 = s.substring(i + 1);
					if ((s1 == null || s1.length() == 0) && (s3 == null || s3.length() == 0))
						return s2;
					else
						return "cff:cat3('" + s1 + "', " + s2 + ", '" + s3 + "')";
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	private String formatEl(String s)
	{
		int i = s.indexOf("#");
		if (i == -1)
			return "'" + s + "'";
		else
		{
			Vector<String> v = new Vector<String>();
			while (s != null && s.length() > 0)
			{
				i = s.indexOf("#");
				if (i == -1)
				{
					v.add("'" + s + "'");
					s = null;
				}
				else if (i == 0)
				{
					i = s.indexOf("#", 1);
					if (i == -1)
					{
						v.add("'" + s + "'");
						s = null;
					}
					else if (i == 0)
					{
						v.add("'" + s.substring(1) + "'");
						s = null;
					}
					else
					{
						try
						{
							String z = s.substring(1, i);
							ExprLexer le = new ExprLexer(new StringReader(z));
							ExprParser pe = new ExprParser(le);
							v.add(pe.gen().toString());
							s = s.substring(i + 1);
						}
						catch (Exception e)
						{
							e.printStackTrace();
							return null;
						}
					}
				}
				else
				{
					v.add("'" + s.substring(0, i) + "'");
					s = s.substring(i);
				}
			}
			if (v.size() == 1)
				return v.get(0);
			boolean first = true;
			for (String z : v)
			{
				if (z == null || z.length() == 0)
					continue;
				if (!first)
					s = s + ", ";
				if (s == null)
					s = z;
				else
					s += z;
				first = false;
			}
			return "cff:cat_" + v.size() + "(" + s + ")";
		}
	}
}
