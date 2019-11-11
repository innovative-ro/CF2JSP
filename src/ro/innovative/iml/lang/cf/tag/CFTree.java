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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public class CFTree extends BodyTagSupport {

    private static final long serialVersionUID = 2471116386939339383L;

    private String name;
    private String format;
    private String required;
    private String delimiter;
    private String completepath;
    private String appendkey;
    private String highlighthref;
    private String onvalidate;
    private String message;
    private String onerror;
    private String lookandfeel;
    private String font;
    private String fontsize;
    private String italic;
    private String bold;
    private String height;
    private String width;
    private String vspace;
    private String hspace;
    private String align;
    private String border;
    private String hscroll;
    private String vscroll;
    private String style;
    private String enabled;
    private String visible;
    private String tooltip;
    private String onchange;
    private String notsupported;
    private String onblur;
    private String onfocus;

    private String scroll;

    private Hashtable<String, Integer> ht = new Hashtable<String, Integer>();

    private int key = 1;

    private CFForm parent = null;

    public int doStartTag() throws JspException {
	JspWriter out = pageContext.getOut();
	parent = (CFForm) findAncestorWithClass(this, CFForm.class);

	scroll = "auto";
	
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.name", getName());
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.hashtable", ht);
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.hashtablekey", new Integer(key));
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.appendkey", getAppendkey());
	
	try {
	    /*
	     * out.println("<script type=\"text/javascript\">");
	     * out.println("<!--");
	     * pageContext.include(path + "cftree_script.js", true);
	     * out.println("-->");
	     * out.println("</script>");
	     * 
	     * out.println("<style type=\"text/css\">");
	     * pageContext.include(path + "cftree_style.css", true);
	     * out.println(); out.println("</style>");
	     */

	    // set path
	    String path = "/cfide/jstree/";
	    
	    Properties prop = new Properties();
	    try {
		prop.load(pageContext.getServletContext().getResourceAsStream("/WEB-INF/config/cfproperties.properties"));
		path = prop.getProperty("cftree_files_location");
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    
	    // output html
	    out.println("<input " + "id=\"__CFTREE__" + parent.getName() + "__" + getName() + "\" "
		    + "type=\"hidden\" name=\"__CFTREE__" + parent.getName() + "__" + getName() + "\" value=\"\" />");
	    out.println("<div class=\"cftree\" style=\"" + getWidth() + getHeight() + getBorder() + getFont() + getFontsize()
		    + getItalic() + getBold() + getHscroll() + getVscroll() + "overflow:" + scroll + ";" + "\" " + ">");

	    out.println("<p style=\"display:inline\">");
	    out.println("<a href=\"javascript: " + getName() + ".openAll();\">open all</a>");
	    out.println(" | ");
	    out.println("<a href=\"javascript: " + getName() + ".closeAll();\">close all</a>");
	    out.println("</p>");

	    out.println("<script type=\"text/javascript\">");
	    out.println("<!--");
	    out.println(getName() + " = new CFTree('" + getName() + "', '" + parent.getName() + "', '" + path + "');"
		    + getName() + ".add(0,-1,'" + getName() + "','javascript: void(0);','" + getName() + "');");
	    ht.put(getName(), 0);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return TagSupport.EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	JspWriter out = pageContext.getOut();

	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree" + name, null);
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.hashtable", null);
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.hashtablekey", null);
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.appendkey", null);
	
	try {
	    out.println("document.write(" + getName() + ");");
	    out.println(getName() + ".openAll();");
	    // out.println(getName() + ".config.useCookies = false;");
	    out.println("//-->");
	    out.println("</script>");
	    out.println("</div>");
	} catch (java.io.IOException e) {
	    e.printStackTrace();
	}

	return EVAL_PAGE;
    }

    public String getEnabled() {
	return enabled;
    }

    public void setEnabled(String enabled) {
	this.enabled = enabled;
    }

    public String getFormat() {
	return format;
    }

    public void setFormat(String format) {
	this.format = format;
    }

    public String getOnblur() {
	return onblur;
    }

    public void setOnblur(String onblur) {
	this.onblur = onblur;
    }

    public String getOnchange() {
	return onchange;
    }

    public void setOnchange(String onchange) {
	this.onchange = onchange;
    }

    public String getOnfocus() {
	return onfocus;
    }

    public void setOnfocus(String onfocus) {
	this.onfocus = onfocus;
    }

    public String getStyle() {
	return style;
    }

    public void setStyle(String style) {
	this.style = style;
    }

    public String getTooltip() {
	return tooltip;
    }

    public void setTooltip(String tooltip) {
	this.tooltip = tooltip;
    }

    public String getVisible() {
	return visible;
    }

    public void setVisible(String visible) {
	this.visible = visible;
    }

    public String getAlign() {
	if (align != null)
	    return "align=\"" + align + "\"";
	else
	    return "";
    }

    public void setAlign(String align) {
	this.align = align;
    }

    public String getAppendkey() {
	if (appendkey == null)
	    return "yes";
	return appendkey;
    }

    public void setAppendkey(String appendkey) {
	this.appendkey = appendkey;
    }

    public String getBold() {
	if (bold != null)
	    return bold.equalsIgnoreCase("Yes") ? "font-weight:bold;" : "";
	else
	    return "";
    }

    public void setBold(String bold) {
	this.bold = bold;
    }

    public String getBorder() {
	if (border != null)
	    return border.equalsIgnoreCase("Yes") ? "border-style:solid;border-width:thin;" : "";
	else
	    return "";
    }

    public void setBorder(String border) {
	this.border = border;
    }

    public String getCompletepath() {
	return completepath;
    }

    public void setCompletepath(String completepath) {
	this.completepath = completepath;
    }

    public String getDelimiter() {
	return delimiter;
    }

    public void setDelimiter(String delimiter) {
	this.delimiter = delimiter;
    }

    public String getFont() {
	if (font != null)
	    return "font-family:" + font + ";";
	else
	    return "";
    }

    public void setFont(String font) {
	this.font = font;
    }

    public String getFontsize() {
	if (font != null)
	    return "font-size:" + fontsize + ";";
	else
	    return "";
    }

    public void setFontsize(String fontsize) {
	this.fontsize = fontsize;
    }

    public String getHeight() {
	if (height != null) {
	    scroll = "hidden";
	    return "height:" + height + ";";
	} else
	    return "";
    }

    public void setHeight(String height) {
	this.height = height;
    }

    public String getHighlighthref() {
	return highlighthref;
    }

    public void setHighlighthref(String highlighthref) {
	this.highlighthref = highlighthref;
    }

    public String getHscroll() {
	if (hscroll != null) {
	    if (hscroll.equalsIgnoreCase("Yes"))
		scroll = "scroll";
	    else
		scroll = "hidden";
	    return "";
	} else {
	    scroll = "auto";
	    return "";
	}
    }

    public void setHscroll(String hscroll) {
	this.hscroll = hscroll;
    }

    public String getHspace() {
	return hspace;
    }

    public void setHspace(String hspace) {
	this.hspace = hspace;
    }

    public String getItalic() {
	if (italic != null)
	    return italic.equalsIgnoreCase("Yes") ? "font-style:italic;" : "";
	else
	    return "";
    }

    public void setItalic(String italic) {
	this.italic = italic;
    }

    public String getLookandfeel() {
	return lookandfeel;
    }

    public void setLookandfeel(String lookandfeel) {
	this.lookandfeel = lookandfeel;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) throws java.io.IOException {
	this.name = name;
    }

    public String getNotsupported() {
	return notsupported;
    }

    public void setNotsupported(String notsupported) {
	this.notsupported = notsupported;
    }

    public String getOnerror() {
	return onerror;
    }

    public void setOnerror(String onerror) {
	this.onerror = onerror;
    }

    public String getOnvalidate() {
	return onvalidate;
    }

    public void setOnvalidate(String onvalidate) {
	this.onvalidate = onvalidate;
    }

    public String getRequired() {
	return required;
    }

    public void setRequired(String required) {
	this.required = required;
    }

    public String getVscroll() {
	if (vscroll != null) {
	    if (vscroll.equalsIgnoreCase("Yes"))
		scroll = "scroll";
	    else
		scroll = "hidden";
	    return "";
	} else {
	    scroll = "auto";
	    return "";
	}
    }

    public void setVscroll(String vscroll) {
	this.vscroll = vscroll;
    }

    public String getVspace() {
	return vspace;
    }

    public void setVspace(String vspace) {
	this.vspace = vspace;
    }

    public String getWidth() {
	if (width != null)
	    return "width:" + width + ";";
	else
	    return "";
    }

    public void setWidth(String width) {
	this.width = width;
    }

}
