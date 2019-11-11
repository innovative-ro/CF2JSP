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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class CFCookie extends TagSupport
{

	private static final long serialVersionUID = 1L;

	private String name;
	private String value;
	private String expires;
	private String secure;
	private String path;
	private String domain;

	public int doStartTag() throws JspException
	{
		Cookie c = new Cookie(getName().toLowerCase(), getValue());
		c.setSecure(new Boolean(getSecure()));
		if (getDomain() != null)
			c.setDomain(getDomain());
		if (getPath() != null)
			c.setPath(getPath());
		// c.setMaxAge(new Integer(expires));

		Object resp = CFVariableUtil.exists(pageContext, "request.cf2jsp.response");
		if (resp != null)
			((HttpServletResponse) resp).addCookie(c);
		else
			((HttpServletResponse) pageContext.getResponse()).addCookie(c);

		// CFVariableUtil.create(pageContext, "cookie." +
		// getName().toLowerCase(), getValue());
		// Map cooks = ((Map) pageContext.getAttribute("cookie",
		// PageContext.REQUEST_SCOPE));
		// if (cooks == null) {
		// cooks = new HashMap();
		// pageContext.setAttribute("cookie", cooks, PageContext.REQUEST_SCOPE);
		// }
		// cooks.put(getName().toLowerCase(), getValue());

		Logger.println(pageContext, "<cfcookie " + getName() + " " + getValue() + ">");
		return TagSupport.SKIP_BODY;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	public String getExpires()
	{
		return expires;
	}

	public void setExpires(String expires)
	{
		this.expires = expires;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getSecure()
	{
		if (secure == null)
			return "false";
		else if (secure.trim().equals("0") || secure.trim().equalsIgnoreCase("false") || secure.trim().equals("0.0"))
			return "false";
		else
			return "true";
	}

	public void setSecure(String secure)
	{
		this.secure = secure;
	}

	public String getValue()
	{
		if (value != null)
			return value;
		else
			return "";
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
