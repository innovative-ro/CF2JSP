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
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class CFLocation extends TagSupport {

    private static final long serialVersionUID = 3755337390937710345L;

    private String url;
    private String addtoken;

    public void setUrl(String s) {
	url = s;
    }

    public String getUrl() {
	return url;
    }

    public void setAddtoken(String s) {
	addtoken = s;
    }

    public String getAddtoken() {
	return addtoken;
    }

    public int doStartTag() throws JspException {
	url = url.replaceAll("\\.cfm", ".jsp");
	url = url.replaceAll(" ", "%20");
	try {
	    //url = URLEncoder.encode(url, "UTF-8");
	    Logger.println(pageContext, "<cflocation was here and maybe ruined everything :((!" + "        " + url);
	    
	    Object resp = CFVariableUtil.exists(pageContext, "request.cf2jsp.response");
	    if (resp != null)
		((HttpServletResponse) resp).sendRedirect(url);
	    else
		((HttpServletResponse) pageContext.getResponse()).sendRedirect(url);
	} catch (IOException e) {
	    throw new JspException(e);
	}

	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	return EVAL_PAGE;
    }

}
