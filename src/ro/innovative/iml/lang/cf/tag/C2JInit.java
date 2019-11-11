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
 
package ro.innovative.iml.lang.cf.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class C2JInit extends TagSupport {

	private static final long serialVersionUID = -7700957768224882602L;

	private String file = null;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int doStartTag() throws JspException {

		Properties prop = new Properties();
		try {
			prop.load(pageContext.getServletContext().getResourceAsStream(
					"/WEB-INF/config/cfproperties.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// logger space
		Logger.setLog(Integer.parseInt(prop.getProperty("cf_log_level")));
		Logger.setPath(pageContext.getServletContext().getRealPath("/")
				+ prop.getProperty("cf_log_file"));
		if (Logger.doLog())
			Logger.println(pageContext, "////// init " + file);

		// application.cfm init
		Object cnt = CFVariableUtil.exists(pageContext,
				"request.cf2jsp.stackcount");
		if (cnt == null || (cnt != null && !(cnt instanceof Long))) {
			CFVariableUtil.create(pageContext, "request.cf2jsp.stackcount", 1L);

			populateScopes();

			CFVariableUtil.create(pageContext, "request.cf2jsp.response",
					pageContext.getResponse());

			if (Logger.doLog())
				Logger.println(pageContext, "////// create "
						+ CFVariableUtil.exists(pageContext,
								"request.cf2jsp.stackcount"));
		} else {
			Long l = (Long) (cnt);
			CFVariableUtil.create(pageContext, "request.cf2jsp.stackcount",
					l + 1L);
			if (Logger.doLog())
				Logger.println(pageContext, "////// create "
						+ CFVariableUtil.exists(pageContext,
								"request.cf2jsp.stackcount"));
		}

		// cf scopes init

		CFVariableUtil.popPageContext(pageContext);

		CFVariableUtil.pushPageContext(pageContext);

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		CFVariableUtil.popPageContext(pageContext);

		return EVAL_PAGE;
	}

	private void populateScopes() throws JspException {
		// functions have to be called in this order

		// application init
		populateAPPLICATION();
		// server init
		populateSERVER();
		// cgi init
		populateCGI();
		// url init (must be before form init)
		populateURL();
		// form init
		populateFORM();
		// cookie init
		populateCOOKIE();
	}

	private void populateAPPLICATION() {
		if (pageContext
				.getAttribute("path_info", PageContext.APPLICATION_SCOPE) == null)
			pageContext.setAttribute("path_info", "",
					PageContext.APPLICATION_SCOPE);
	}

	private void populateSERVER() throws JspException {
		CFVariableUtil.create(pageContext, "server.os.name", System
				.getProperty("os.name"));
	}

	private void populateCGI() throws JspException {
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		ServletContext sc = pageContext.getServletContext();
		String s;

		s = hsr.getAuthType();
		CFVariableUtil.create(pageContext, "cgi.AUTH_TYPE", s == null ? "" : s);
		s = String.valueOf(hsr.getContentLength());
		CFVariableUtil.create(pageContext, "cgi.CONTENT_LENGTH", s == null ? ""
				: s);
		s = hsr.getContentType();
		CFVariableUtil.create(pageContext, "cgi.CONTENT_TYPE", s == null ? ""
				: s);
		s = sc.getRealPath("/");
		CFVariableUtil.create(pageContext, "cgi.DOCUMENT_ROOT", s == null ? ""
				: s);
		s = hsr.getPathInfo();
		CFVariableUtil.create(pageContext, "cgi.PATH_INFO", s == null ? "" : s);
		s = hsr.getPathTranslated();
		CFVariableUtil.create(pageContext, "cgi.PATH_TRANSLATED",
				s == null ? "" : s);
		s = hsr.getQueryString();
		CFVariableUtil.create(pageContext, "cgi.QUERY_STRING", s == null ? ""
				: s);
		s = hsr.getRemoteAddr();
		CFVariableUtil.create(pageContext, "cgi.REMOTE_ADDR", s == null ? ""
				: s);
		s = hsr.getRemoteHost();
		CFVariableUtil.create(pageContext, "cgi.REMOTE_HOST", s == null ? ""
				: s);
		s = hsr.getRemoteUser();
		CFVariableUtil.create(pageContext, "cgi.REMOTE_USER", s == null ? ""
				: s);
		s = hsr.getMethod();
		CFVariableUtil.create(pageContext, "cgi.REQUEST_METHOD", s == null ? ""
				: s);
		s = hsr.getServletPath();
		CFVariableUtil.create(pageContext, "cgi.SCRIPT_NAME", s == null ? ""
				: s);
		s = hsr.getServerName();
		CFVariableUtil.create(pageContext, "cgi.SERVER_NAME", s == null ? ""
				: s);
		s = String.valueOf(hsr.getServerPort());
		CFVariableUtil.create(pageContext, "cgi.SERVER_PORT", s == null ? ""
				: s);
		s = hsr.getProtocol();
		CFVariableUtil.create(pageContext, "cgi.SERVER_PROTOCOL",
				s == null ? "" : s);
		s = sc.getServerInfo();
		CFVariableUtil.create(pageContext, "cgi.SERVER_SOFTWARE",
				s == null ? "" : s);
		// CGI.HTTP_XXX
		s = hsr.getHeader("Referer");
		CFVariableUtil.create(pageContext, "cgi.HTTP_REFERER",
				s == null ? "http://localhost:8080" : "http://localhost:8080");
		s = hsr.getHeader("User-Agent");
		CFVariableUtil.create(pageContext, "cgi.HTTP_USER_AGENT",
				s == null ? "" : s);
		s = hsr.getHeader("If-Modified-Since");
		CFVariableUtil.create(pageContext, "cgi.HTTP_IF_MODIFIED_SINCE",
				s == null ? "" : s);
	}

	private void populateURL() throws JspException {
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();

		if (hsr.getQueryString() != null)
			try {
				String params = URLDecoder
						.decode(hsr.getQueryString(), "UTF-8");
				String[] paramPairs = params.split("&");

				for (int i = 0; i < paramPairs.length; i++) {
					String[] queryParts = paramPairs[i].split("=");
					if (queryParts.length > 1)
						CFVariableUtil.create(pageContext, "url."
								+ queryParts[0], queryParts[1]);
					else
						CFVariableUtil.create(pageContext, "url."
								+ queryParts[0], "");
				}

				// for (String keyVal : paramPairs) {
				// int pos = keyVal.indexOf('=');
				// CFVariableUtil.create(pageContext, "url." +
				// keyVal.substring(0, pos),
				// keyVal.substring(pos + 1, keyVal.length()));
				// }

			} catch (UnsupportedEncodingException e) {
				throw new JspException(e);
			}
	}

	private void populateFORM() throws JspException {
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();

		if (hsr.getMethod().equalsIgnoreCase("POST")) {
			for (Object key : pageContext.getRequest().getParameterMap()
					.keySet()) {
				String s = key.toString().toLowerCase();
				if (CFVariableUtil.exists(pageContext, "url." + s) == null)
					CFVariableUtil.create(pageContext, "form." + s, pageContext
							.getRequest().getParameter(key.toString()));
				Logger
						.println(pageContext, "miu form."
								+ s
								+ " = "
								+ pageContext.getRequest().getParameter(
										key.toString()));
			}
		} else {
			for (Object key : pageContext.getRequest().getParameterMap()
					.keySet()) {
				String s = key.toString().toLowerCase();
				CFVariableUtil.create(pageContext, "url." + s, pageContext
						.getRequest().getParameter(key.toString()));
				Logger
						.println(pageContext, "miu url."
								+ s
								+ " = "
								+ pageContext.getRequest().getParameter(
										key.toString()));
			}
		}
	}

	private void populateCOOKIE() throws JspException {
		Cookie[] x = ((HttpServletRequest) pageContext.getRequest())
				.getCookies();

		if (x != null)
			for (Cookie a : x) {
				CFVariableUtil.create(pageContext, "cookie."
						+ a.getName().toLowerCase(), a.getValue());
			}
	}

}
