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

//import java.io.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;

/**
 * 
 * Does not implement the charset option [change the encoding for the headers]!
 * <br />
 * Original Syntax: &lt;cfheader name = "name" value = "value" charset =
 * "charset""&gt; or &lt;cfheader statuscode = "statuscode" statustext =
 * "statustext"&gt;
 * 
 * @author radu
 * 
 */
public class CFHeader extends TagSupport {
	private static final long serialVersionUID = -3729976754978672058L;

	String name = null;

	String value = null;

	String charset = null;

	Integer statusCode = null;

	String statusText = null;

	/**
	 * Set the name of the header to add to the response.
	 * 
	 * @param name
	 *            The name of the header to add to the response.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the value of the header.
	 * 
	 * @param value
	 *            The value of the header.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Not implemented! Set the caracter encoding for the value.
	 * 
	 * @param charset
	 *            The caracter encoding for the value.
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * Set response status code.
	 * 
	 * @param statuscode
	 */
	public void setStatuscode(Integer statuscode) {
		this.statusCode = statuscode;
	}

	/**
	 * Set text explanation of the status code.
	 * 
	 * @param statustext
	 *            A text explanation of the status code.
	 */
	public void setStatustext(String statustext) {
		this.statusText = statustext;
	}

	@SuppressWarnings("deprecation")
	public int doStartTag() throws JspException {
		if ((name == null && statusCode == null)
				|| (name != null && statusCode != null)) {
			throw new JspException(
					"Wrong combination of arguments for tag CFHeader!");
		}
		HttpServletResponse response;
		Object resp = CFVariableUtil.exists(pageContext, "request.cf2jsp.response");
		if (resp != null)
			response = ((HttpServletResponse) resp);
		else
			response = (HttpServletResponse) pageContext.getResponse();
		if (name != null)
			response.setHeader(name, value);
		else
			response.setStatus(statusCode, statusText);

		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
