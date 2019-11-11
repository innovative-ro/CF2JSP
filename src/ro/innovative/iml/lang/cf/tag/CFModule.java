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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;

public class CFModule extends TagSupport {

    private static final long serialVersionUID = -2933389247253675228L;

    private Map attributecollection;
    private String mode;

    private Map attributes;
    private Map attributes0;
    private Map<String, Object> caller;
    private Object caller0;
    private Map<String, Object> thistag;
    private Object thistag0;

    public int doStartTag() throws JspException {
	if (CFVariableUtil.exists(pageContext, "request.cf2jsp.cfexitdoclosingtag") == null) {
	    // attributes

	    // init attributes
	    attributes = (Map) pageContext.getAttribute("attributes", PageContext.REQUEST_SCOPE);

	    if (attributes != null) {
		attributes0 = new HashMap<String, Object>();
		attributes0.putAll(attributes);
	    } else {
		attributes = new HashMap<String, Object>();
		pageContext.setAttribute("attributes", attributes, PageContext.REQUEST_SCOPE);
	    }
	    
	    if (attributecollection != null)
		for (Object key : attributecollection.keySet())
		    put(key.toString().toLowerCase(), attributecollection.get(key));
	    
	    // this is raul's hand and it doesn't smell right
	    // pageContext.setAttribute("attributes", attributes,
	    // PageContext.REQUEST_SCOPE);

	    // caller
	    caller0 = pageContext.getAttribute("caller", PageContext.REQUEST_SCOPE);

	    // init caller
	    caller = new HashMap<String, Object>();

	    Enumeration e = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
	    while (e.hasMoreElements()) {
		String key = (String) e.nextElement();
		caller.put(key, pageContext.getAttribute(key));
	    }

	    pageContext.setAttribute("caller", caller, PageContext.REQUEST_SCOPE);

	    // thistag
	    thistag0 = pageContext.getAttribute("thistag", PageContext.REQUEST_SCOPE);

	    // init thistag
	    thistag = new HashMap<String, Object>();
	    thistag.put("executionmode", getMode());

	    pageContext.setAttribute("thistag", thistag, PageContext.REQUEST_SCOPE);

	    return EVAL_BODY_INCLUDE;
	} else {
	    return SKIP_BODY;
	}
    }

    public int doEndTag() throws JspException {
	if (CFVariableUtil.exists(pageContext, "request.cf2jsp.cfexitdoclosingtag") == null) {
	    // return back to normal
	    if (attributes0 != null)
		pageContext.setAttribute("attributes", attributes0, PageContext.REQUEST_SCOPE);
	    else
		pageContext.removeAttribute("attributes", PageContext.REQUEST_SCOPE);

	    // put caller into page
	    if (!caller.isEmpty()) {
		for (Object key : caller.keySet())
		    pageContext.setAttribute(key.toString().toLowerCase(), caller.get(key));
	    }

	    // return back to normal
	    if (caller0 != null)
		pageContext.setAttribute("caller", caller0, PageContext.REQUEST_SCOPE);
	    else
		pageContext.removeAttribute("caller", PageContext.REQUEST_SCOPE);

	    // return back to normal
	    if (thistag0 != null)
		pageContext.setAttribute("thistag", thistag0, PageContext.REQUEST_SCOPE);
	    else
		pageContext.removeAttribute("thistag", PageContext.REQUEST_SCOPE);
	} else {
	    ((Map) pageContext.getAttribute("cf2jsp", PageContext.REQUEST_SCOPE)).remove("cfexitdoclosingtag");
	}

	return EVAL_PAGE;
    }

    void put(String key, Object val) {
	attributes.put(key, val);
    }

    public Map getAttributecollection() {
	return attributecollection;
    }

    public void setTemplate(Map attributecollection) {
	this.attributecollection = attributecollection;
    }

    public String getMode() {
	return mode;
    }

    public void setMode(String mode) {
	this.mode = mode;
    }

}
