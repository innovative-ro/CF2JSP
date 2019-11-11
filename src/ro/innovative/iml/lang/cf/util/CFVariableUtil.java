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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class CFVariableUtil {

	public static Object exists(PageContext pageContext, String name) {
		String[] var = name.trim().toLowerCase().split("\\.");

		if (var.length == 1)
			return pageContext.getAttribute(name.toLowerCase());

		int scope = PageContext.PAGE_SCOPE;
		int pos = 1;

		if (var[0].equalsIgnoreCase("page"))
			scope = PageContext.PAGE_SCOPE;
		else if (var[0].equalsIgnoreCase("request"))
			scope = PageContext.REQUEST_SCOPE;
		else if (var[0].equalsIgnoreCase("session"))
			scope = PageContext.SESSION_SCOPE;
		else if (var[0].equalsIgnoreCase("application"))
			scope = PageContext.APPLICATION_SCOPE;
		else if (var[0].equalsIgnoreCase("cookie")) {
			Cookie[] cooks = ((HttpServletRequest) pageContext.getRequest())
					.getCookies();

			if (cooks != null) {
				try {
					StringBuffer sb = new StringBuffer();
					for (int i = 1; i < var.length - 1; i++)
						sb.append(var[i] + ".");
					sb.append(var[var.length - 1]);

					for (Cookie a : cooks)
						if (a.getName().equalsIgnoreCase(sb.toString()))
							return a;

					return null;
				} catch (ArrayIndexOutOfBoundsException e) {
					return cooks;
				}
			} else
				return null;
		} else if (var[0].equalsIgnoreCase("caller")
				|| var[0].equalsIgnoreCase("attributes")
				|| var[0].equalsIgnoreCase("form")
				|| var[0].equalsIgnoreCase("url")
				|| var[0].equalsIgnoreCase("cgi")
				|| var[0].equalsIgnoreCase("server")
				|| var[0].equalsIgnoreCase("thistag")) {
			scope = PageContext.REQUEST_SCOPE;
			pos = 0;
		} else if (var[0].equalsIgnoreCase("client")) {
			scope = PageContext.APPLICATION_SCOPE;
			pos = 0;
		} else if (var[0].equalsIgnoreCase("flash")
				|| var[0].equalsIgnoreCase("arguments")) {
			scope = PageContext.PAGE_SCOPE;
			pos = 0;
		} else
			pos = 0;

		if (var.length - pos - 1 == 0)
			return pageContext.getAttribute(var[pos], scope);
		else {
			String[] trueVar = new String[var.length - pos - 1];
			System.arraycopy(var, pos, trueVar, 0, var.length - pos - 1);

			Object v = pageContext.getAttribute(var[pos], scope);
			boolean passOnce = false; // it may repeat the process

			if (v == null)
				return null;

			if (v instanceof Map) {
				Object map = null;

				for (String token : trueVar) {
					if (token.equals(""))
						continue;

					if (map == null) {
						if (passOnce)
							return null;
						else {
							map = (Map) v;
							passOnce = true;
						}
					} else if (map instanceof Map
							&& ((Map) map).containsKey(token)) {
						map = ((Map) map).get(token);
					} else if (!(map instanceof Map))
						return null;
					else
						return null;
				}
				if (((Map) map).containsKey(var[var.length - 1]))
					return ((Map) map).get(var[var.length - 1]);
				else
					return null;
			}

			return v;
		}
	}

	public static void create(PageContext pageContext, String name, Object value)
			throws JspException {
		String[] var = name.toLowerCase().trim().split("\\.");
		String[] trueVar;
		int scope = PageContext.PAGE_SCOPE;
		int pos = 1;
		// Object myValue = value == null ? "" : value;

		if (var.length == 1) {
			pageContext.setAttribute(name.toLowerCase(), value);
			return;
		}

		if (var[0].equalsIgnoreCase("page"))
			scope = PageContext.PAGE_SCOPE;
		else if (var[0].equalsIgnoreCase("request"))
			scope = PageContext.REQUEST_SCOPE;
		else if (var[0].equalsIgnoreCase("session"))
			scope = PageContext.SESSION_SCOPE;
		else if (var[0].equalsIgnoreCase("application"))
			scope = PageContext.APPLICATION_SCOPE;
		else if (var[0].equalsIgnoreCase("cookie")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < var.length; i++)
				sb.append(var[i] + ".");
			String cookieName = sb.toString().substring(0, sb.length() - 1)
					.toLowerCase();

			// put the cookie in response or modify request
			Cookie[] cookies = ((HttpServletRequest) pageContext.getRequest())
					.getCookies();

			Cookie newCookie = null;
			if (cookies != null)
				for (Cookie a : cookies)
					if (a.getName().equalsIgnoreCase(cookieName)) {
						newCookie = a;
						newCookie.setValue(value.toString());
						break;
					}
			if (newCookie == null)
				((HttpServletResponse) pageContext.getResponse())
						.addCookie(new Cookie(cookieName, value.toString()));
			else
				((HttpServletResponse) pageContext.getResponse())
						.addCookie(newCookie);

			return;
		} else if (var[0].equalsIgnoreCase("caller")
				|| var[0].equalsIgnoreCase("attributes")
				|| var[0].equalsIgnoreCase("form")
				|| var[0].equalsIgnoreCase("url")
				|| var[0].equalsIgnoreCase("cgi")
				|| var[0].equalsIgnoreCase("server")
				|| var[0].equalsIgnoreCase("thistag")) {
			scope = PageContext.REQUEST_SCOPE;
			pos = 0;
		} else if (var[0].equalsIgnoreCase("client")) {
			scope = PageContext.APPLICATION_SCOPE;
			pos = 0;
		} else if (var[0].equalsIgnoreCase("flash")
				|| var[0].equalsIgnoreCase("arguments")) {
			scope = PageContext.PAGE_SCOPE;
			pos = 0;
		} else
			pos = 0;

		if (var.length - pos - 1 == 0) {
			pageContext.setAttribute(var[pos], value, scope);
			return;
		} else {
			trueVar = new String[var.length - pos - 1];
			System.arraycopy(var, pos, trueVar, 0, var.length - pos - 1);

			Object map = pageContext.getAttribute(var[pos], scope);

			Map previousMap = ((map == null) || !(map instanceof Map)) ? (new HashMap<String, Object>())
					: ((Map) map);

			map = previousMap;

			boolean passFirstElement = false; // skip first map
			// (struct)

			for (String token : trueVar) {
				if (token.equals(""))
					continue;

				if (!passFirstElement) {
					passFirstElement = true;
					continue;
				}

				if (previousMap.containsKey(token)
						&& previousMap.get(token) instanceof Map) {
					previousMap = (Map) previousMap.get(token);
				} else if (previousMap.containsKey(token)
						&& !(previousMap.get(token) instanceof Map)) {
					throw new JspException("Invalid deference in structure.");
				} else {
					previousMap.put(token, new HashMap<String, Object>());
					previousMap = (Map) previousMap.get(token);
				}
			}

			// add final the object to the map
			previousMap.put(var[var.length - 1].toString(), value);
			// add to scope context
			pageContext.setAttribute(var[pos], (Map) map, scope);
			return;
		}
	}

	public static void pushPageContext(PageContext pageContext)
			throws JspException {
		Enumeration<String> en = pageContext
				.getAttributeNamesInScope(PageContext.PAGE_SCOPE);

		while (en.hasMoreElements()) {
			String s = en.nextElement();
			Object o = pageContext.getAttribute(s, PageContext.PAGE_SCOPE);
			CFVariableUtil.create(pageContext, "request.cf2jsp.pages." + s, o);
		}
	}

	public static void popPageContext(PageContext pageContext)
			throws JspException {
		HashMap<String, Object> en = (HashMap) CFVariableUtil.exists(
				pageContext, "request.cf2jsp.pages");

		if (en != null)
			for (String s : en.keySet())
				CFVariableUtil.create(pageContext, s, en.get(s));
	}

}
