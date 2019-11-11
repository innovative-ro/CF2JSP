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
 
import java.io.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Note: In certain cases this tag redirects the request to a servlet
 * to perform binary output.
 * 
 * <br />
 * Original Syntax: &lt;cfcontent type = "file_type" deleteFile = "yes" or "no"
 * file = "filename" variable = "variablename" reset = "yes" or "no"&gt;
 * 
 * @author radu
 */
public class CFContent extends TagSupport {
	private static final long serialVersionUID = -9068869102307792800L;

	static int BUFFSIZE = 1024;

	static String BINARY_SERVLET_URL = "/test/binary";

	String type = null;

	boolean deleteFile = false;

	String file = null;

	String variable = null;

	boolean reset = true;

	long uid = 0L;

	/**
	 * @param type
	 *            A file or MIME content type, optionally including character
	 *            encoding, in which to return the page.
	 */
	public void setType(String arg) {
		type = arg;
	}

	public void setFile(String arg) {
		file = arg;
	}

	public void setDeletefile(Boolean arg) {
		deleteFile = arg;
	}

	public void setReset(String arg) {
		double doubleArg = 0.0D;
		try {
			doubleArg = Double.parseDouble(arg);
		} catch (NumberFormatException e) {
			// silently catch exception
		}
		if (arg.trim().equalsIgnoreCase("true")
				|| arg.trim().equalsIgnoreCase("yes"))
			reset = true;
		else
			reset = (doubleArg == 0.0D) ? false : true;
	}

	public void setVariable(String arg) {
		variable = arg;
	}

	public int doStartTag() throws JspException {
		HttpServletResponse response;
		response = (HttpServletResponse) pageContext.getResponse();
		if (type != null) {
			response.setContentType(type);
		}
		if (file != null) {
			HttpSession session = pageContext.getSession();
			session.setAttribute("FILE_TO_OUTPUT" + uid,file);
			session.setAttribute("HEADER" + uid,type);
			try {
				response.sendRedirect(response
						.encodeRedirectURL(BINARY_SERVLET_URL + "?file=true&uid=" + uid));
			} catch (IOException e) {
				throw new JspException(e);
			}
			uid++;

		} else if (variable != null) {
			HttpSession session = pageContext.getSession();
			session.setAttribute("VARIABLE_TO_OUTPUT" + uid,
					pageContext.getAttribute(variable));// TODO: variable
			session.setAttribute("HEADER" + uid,type);
			try {
				response.sendRedirect(response
						.encodeRedirectURL(BINARY_SERVLET_URL + "?file=false&uid=" + uid));
			} catch (IOException e) {
				throw new JspException(e);
			}
			uid++;

		} else if (reset) {
			pageContext.getResponse().resetBuffer();// TODO:test on win
			try {
				pageContext.getOut().clearBuffer();
				// out.println("Call to resetBuffer.
				// buffsize:"+pageContext.getOut().getBufferSize());
			} catch (IOException e) {
				new JspException(e);
			}
		}
		return EVAL_PAGE;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}
}
