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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class CFExit extends TagSupport {

    private static final long serialVersionUID = -8887089248914535446L;

    private String method;

    public void setMethod(String method) {
	this.method = method;
    }

    public String getMethod() {
	if (method != null)
	    return method;
	else
	    return "exitTag";
    }

    public int doStartTag() throws JspException {
	Logger.println(pageContext, "<cfexit>" + getMethod());

	if (getMethod().equalsIgnoreCase("exittag"))
	    CFVariableUtil.create(pageContext, "request.cf2jsp.cfexitdoclosingtag", false);
	else if (getMethod().equalsIgnoreCase("exittemplate"))
	    ;
	else if (getMethod().equalsIgnoreCase("loop"))
	    ;
	else
	    throw new JspException("<cfexit> invalid method attribute value.");
	
	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	return SKIP_PAGE;
    }

}
