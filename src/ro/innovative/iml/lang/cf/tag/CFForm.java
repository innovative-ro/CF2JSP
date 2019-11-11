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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

public class CFForm extends BodyTagSupport {

    private static final long serialVersionUID = 2959867684040790762L;

    private String name; // HTML, XML, Flash
    private String action; // HTML, XML, Flash
    private String method; // HTML, XML, Flash
    private String format; // HTML, XML, Flash
    private String skin; // XML, Flash
    private String style; // HTML, XML, Flash
    private String preservedata; // HTML, XML
    private String onload; // HTML, XML
    private String onreset; // HTML, XML
    private String onsubmit; // HTML, XML, Flash
    private String scriptsrc; // HTML, XML, Flash
    private String codebase; // HTML, XML -- applets
    private String archive; // HTML, XML -- applets
    private String height; // XML, Flash
    private String width; // XML, Flash
    private String onerror; // Flash
    private String wmode; // Flash
    private String accessible; // Flash
    private String preloader; // Flash
    private String timeout; // Flash
    private String target; // HTML, XML
    private String id; // HTML, XML
    private String theclass; // HTML, XML
    private String enctype; // HTML, XML

    public int doStartTag() throws JspException {
	if (getName() == null) {
	    String prefix = "CFForm_";

	    String x = (String) pageContext.getAttribute(prefix + "variable_name");

	    if (x == null) {
		pageContext.setAttribute(prefix + "variable_name", prefix + 1);

		name = prefix + 1;
	    } else {
		String gen = prefix + Integer.parseInt(x.substring(7)) + 1;
		pageContext.setAttribute(prefix + "variable_name", gen);

		name = gen;
	    }
	}

	JspWriter out = pageContext.getOut();

	try {
	    out.println("<div class=\"cfform\">");

	    out.println("<form name=\"" + getName() + "\" " + "method=\"" + getMethod() + "\" " + getAction() + getStyle()
		    + getTarget() + ">");
	} catch (java.io.IOException e) {
	    e.printStackTrace();
	}

	return TagSupport.EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
	JspWriter out = pageContext.getOut();

	try {
	    out.println("</form>");
	    out.println("</div>");
	} catch (java.io.IOException e) {
	    e.printStackTrace();
	}

	return EVAL_PAGE;
    }

    public String getAccessible() {
	return accessible;
    }

    public void setAccessible(String accessible) {
	this.accessible = accessible;
    }

    public String getAction() {
	if (action == null)
	    return "";
	return "action=\"" + action + "\" ";
    }

    public void setAction(String action) {
	this.action = action;
    }

    public String getArchive() {
	return archive;
    }

    public void setArchive(String archive) {
	this.archive = archive;
    }

    public String getCodebase() {
	return codebase;
    }

    public void setCodebase(String codebase) {
	this.codebase = codebase;
    }

    public String getFormat() {
	return format;
    }

    public void setFormat(String format) {
	this.format = format;
    }

    public String getHeight() {
	return height;
    }

    public void setHeight(String height) {
	this.height = height;
    }

    public String getMethod() {
	if (method == null)
	    return "POST";
	return method.toUpperCase();
    }

    public void setMethod(String method) {
	this.method = method;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getOnerror() {
	return onerror;
    }

    public void setOnerror(String onerror) {
	this.onerror = onerror;
    }

    public String getOnload() {
	return onload;
    }

    public void setOnload(String onload) {
	this.onload = onload;
    }

    public String getOnreset() {
	return onreset;
    }

    public void setOnreset(String onreset) {
	this.onreset = onreset;
    }

    public String getOnsubmit() {
	return onsubmit;
    }

    public void setOnsubmit(String onsubmit) {
	this.onsubmit = onsubmit;
    }

    public String getPreloader() {
	return preloader;
    }

    public void setPreloader(String preloader) {
	this.preloader = preloader;
    }

    public String getPreservedata() {
	return preservedata;
    }

    public void setPreservedata(String preservedata) {
	this.preservedata = preservedata;
    }

    public String getScriptsrc() {
	return scriptsrc;
    }

    public void setScriptsrc(String scriptsrc) {
	this.scriptsrc = scriptsrc;
    }

    public String getSkin() {
	return skin;
    }

    public void setSkin(String skin) {
	this.skin = skin;
    }

    public String getStyle() {
	if (style != null)
	    return "style=\"" + style + "\" ";
	return "";
    }

    public void setStyle(String style) {
	this.style = style;
    }

    public String getTimeout() {
	return timeout;
    }

    public void setTimeout(String timeout) {
	this.timeout = timeout;
    }

    public String getWidth() {
	return width;
    }

    public void setWidth(String width) {
	this.width = width;
    }

    public String getWmode() {
	return wmode;
    }

    public void setWmode(String wmode) {
	this.wmode = wmode;
    }

    public String getEnctype() {
	return enctype;
    }

    public void setEnctype(String enctype) {
	this.enctype = enctype;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getTarget() {
	if (target != null)
	    return "target=\"" + target + "\" ";
	return "";
    }

    public void setTarget(String target) {
	this.target = target;
    }

    public String getTheclass() {
	return theclass;
    }

    public void setTheclass(String theclass) {
	this.theclass = theclass;
    }

}
