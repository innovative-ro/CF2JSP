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
 
package ro.innovative.iml.lang.cf.basis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;

import ro.innovative.iml.lang.cf.util.Logger;

public class CFString
{
	// Determines the value of a character.
	// Returns the value of the first character of a string; if string is empty,
	// returns zero.
	public static Integer asc(Object string)
	{
		if (string.toString().length() == 0)
			return 0;
		return (int) string.toString().charAt(0);
	}

	public static Object compare(Object a, Object b)
	{
		return a.toString().compareTo(b.toString());
	}

	public static Object compareNoCase(Object a, Object b)
	{
		return a.toString().compareToIgnoreCase(b.toString());
	}

	public static Integer find(Object a, Object b)
	{
		if (a == null)
			throw new IllegalArgumentException("function Find: first parameter is null");
		if (b == null)
			throw new IllegalArgumentException("function Find: second parameter is null");
		return b.toString().indexOf(a.toString()) + 1;
	}

	public static Integer find(Object a, Object b, Object c)
	{
		if (a == null)
			throw new IllegalArgumentException("function Find: first parameter is null");
		if (b == null)
			throw new IllegalArgumentException("function Find: second parameter is null");
		return b.toString().indexOf(a.toString(), (int) (CFTypes.toInteger(c) - 1)) + 1;
	}

	public static Integer findNoCase(Object a, Object b)
	{
		if (a == null)
			throw new IllegalArgumentException("function FindNoCase: first parameter is null");
		if (b == null)
			throw new IllegalArgumentException("function FindNoCase: second parameter is null");
		return b.toString().toLowerCase().indexOf(a.toString().toLowerCase()) + 1;
	}

	public static Integer findNoCase(Object a, Object b, Object c)
	{
		if (a == null)
			throw new IllegalArgumentException("function FindNoCase: first parameter is null");
		if (b == null)
			throw new IllegalArgumentException("function FindNoCase: second parameter is null");
		return b.toString().toLowerCase().indexOf(a.toString().toLowerCase(), (int) (CFTypes.toInteger(c) - 1)) + 1;
	}

	public static Integer findOneOf(Object set, Object string)
	{
		for (int i = 0; i < string.toString().length(); i++)
			for (int j = 0; j < set.toString().length(); j++)
				if (string.toString().charAt(i) == set.toString().charAt(j))
					return i + 1;

		return 0;
	}

	public static Object findOneOf_3(Object set, Object string, Object start)
	{
		return findOneOf(set, string.toString().substring(Integer.parseInt(start.toString()) - 1))
				+ Integer.parseInt(start.toString()) - 1;
	}

	public static String getToken(Object str, Object pos)
	{
		return getToken(str, pos, "\t\n ");
	}

	public static String getToken(Object str, Object pos, Object delim)
	{
		try
		{
			return CFList.listGetAt(str, pos, delim);
		}
		catch (IllegalArgumentException e)
		{
			return "";
		}
	}

	public static Object jsStringFormat(Object a)
	{
		return org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(a.toString());
	}

	// LCase converts the alphabetic characters in a string to lowercase.
	// Returns a copy of a string, converted to lowercase.
	public static String lCase(Object string)
	{
		return string.toString().toLowerCase();
	}

	// Returns the first count characters in the string parameter.
	public static String left(Object string, Object count)
	{
		int i = 0;

		i = Integer.parseInt(count.toString());

		return string.toString().substring(0, i);
	}
	
	// Determines the length of a string or binary object.
	// Returns length of a string or a binary object
	public static Integer len(Object o)
	{
		return o.toString().length();
	}

	public static Object replace(Object a, Object b, Object c)
	{
		return replace(a, b, c, "one");
	}

	public static Object replace(Object a, Object b, Object c, Object d)
	{
		int seek = 0;

		if (b.toString().length() == 0)
			throw new IllegalArgumentException("Argument 2 of function Replace cannot be an empty value.");

		StringBuffer sb = new StringBuffer();

		if (d.toString().equalsIgnoreCase("all"))
		{
			while ((seek = a.toString().indexOf(b.toString())) >= 0)
			{
				sb.append(a.toString().substring(0, seek));
				sb.append(c.toString());
				a = a.toString().substring(seek + 1);
			}
			sb.append(a.toString());

			return sb;
		}
		else
		{
			if ((seek = a.toString().indexOf(b.toString())) >= 0)
			{
				sb.append(a.toString().substring(0, seek));
				sb.append(c.toString());
				a = a.toString().substring(seek + 1);
			}
			sb.append(a.toString());

			return sb;
		}
	}

	public static Object replaceNoCase(Object a, Object b, Object c)
	{
		return replaceNoCase_4(a, b, c, "one");
	}

	public static Object replaceNoCase_4(Object a, Object b, Object c, Object d)
	{
		int seek = 0;

		if (b.toString().length() == 0)
			throw new IllegalArgumentException("Argument 2 of function Replace cannot be an empty value.");

		StringBuffer sb = new StringBuffer();

		if (d.toString().equalsIgnoreCase("all"))
		{
			while ((seek = a.toString().toLowerCase().indexOf(b.toString().toLowerCase())) >= 0)
			{
				sb.append(a.toString().substring(0, seek));
				sb.append(c.toString());
				a = a.toString().substring(seek + 1);
			}
			sb.append(a.toString());

			return sb;
		}
		else
		{
			if ((seek = a.toString().toLowerCase().indexOf(b.toString().toLowerCase())) >= 0)
			{
				sb.append(a.toString().substring(0, seek));
				sb.append(c.toString());
				a = a.toString().substring(seek + 1);
			}
			sb.append(a.toString());

			return sb;
		}
	}

	// Reverse reverses the order of items, such as the characters in a string
	// or the digits in a number.
	// Returns a copy of string, with the characters in reverse order.
	public static Object reverse(Object param)
	{
		// TODO for date object
		int type = 0;
		char aux;
		String s = "";
		if (param instanceof String)
		{
			s = (String) s;
			type = 1;
		}
		else if (param instanceof Integer)
		{
			s = param.toString();
			type = 2;
		}
		else if (param instanceof Double)
		{
			s = param.toString();
			type = 3;
		}

		char c[] = s.toCharArray();
		for (int i = 0; i < s.length() / 2; i++)
		{

			aux = c[i];
			c[i] = c[s.length() - i - 1];
			c[s.length() - i - 1] = aux;
		}
		String rString = new String(c);

		switch (type)
		{
		case 1:
			return rString;
		case 2:
			try
			{
				return Integer.parseInt(rString);
			}
			catch (NumberFormatException e)
			{
				return param;
			}
		case 3:
			try
			{
				return Double.parseDouble(rString);
			}
			catch (NumberFormatException e)
			{
				return param;
			}
		default:
			return param;
		}
	}

	// Returns the specified number of characters from the end (or right side)
	// of the specified string.
	public static String right(Object string, Object count)
	{
		String s = string.toString();
		if (s.length() == 0)
			return "";
		int i = 0;
		i = Integer.parseInt(count.toString());
		if (i > s.length())
			return string.toString();
		else
			return s.substring(i, s.length());
	}

	// toString converts a value to a string.
	// Returns a string
	public static String toString(Object value)
	{
		return value.toString();
	}

	// Trim removes leading and trailing spaces and control characters from a
	// string.
	// Returns a copy of the string parameter, after removing leading and
	// trailing spaces and control characters
	public static String trim(Object string)
	{
		return string.toString().trim();
	}

	// UCase converts the alphabetic characters in a string to uppercase.
	// Returns a copy of a string, converted to uppercase.
	public static String uCase(Object string)
	{
		return string.toString().toUpperCase();
	}

	public static String urlEncodedFormat(Object str, Object format)
	{
		try
		{
			return URLEncoder.encode(str.toString(), format.toString());
		}
		catch (UnsupportedEncodingException e)
		{
			// to-do
			return "";
		}
	}

	public static String urlEncodedFormat(Object str)
	{
		String format = new String("UTF-8");
		try
		{
			return URLEncoder.encode(str.toString(), format);
		}
		catch (UnsupportedEncodingException e)
		{
			// to-do
			return "";
		}
	}

	public static String urlDecode(Object str)
	{
		String format = new String("UTF-8");
		try
		{
			return URLDecoder.decode(str.toString(), format);
		}
		catch (UnsupportedEncodingException e)
		{
			// to-do
			return "";
		}
	}

	public static String urlDecode_2(Object str, Object format)
	{
		try
		{
			return URLDecoder.decode(str.toString(), format.toString());
		}
		catch (UnsupportedEncodingException e)
		{
			// to-do
			return "";
		}
	}

	public static Integer val(Object o)
	{
		return 0;
	}

	public static String removeChars(Object str, Object ind, Object count)
	{
		if (CFTypes.toInteger(ind) - 1 + CFTypes.toInteger(count) > str.toString().length())
			count = str.toString().length() - CFTypes.toInteger(ind) + 1;

		return str.toString().substring(0, (int) (CFTypes.toInteger(ind) - 1))
				+ str.toString().substring((int) ((long) (CFTypes.toInteger(ind) - 1) + (long) (CFTypes.toInteger(count))));
	}

}
