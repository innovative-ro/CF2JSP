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
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ro.innovative.iml.lang.cf.basis.CFQueryObj;
import ro.innovative.iml.lang.cf.util.CFListUtil;
import ro.innovative.iml.lang.cf.util.CFVariableUtil;

public class CFTreeItem extends TagSupport {

    private static final long serialVersionUID = -1902989123476824856L;

    private String value;
    private String display;
    private String theparent;
    private String img;
    private String imgopen;
    private String href;
    private String target;
    private String query;
    private String queryasroot;
    private String expand;

    private Hashtable<String, String> ht = new Hashtable<String, String>();

    Map parent;
    private long gen = 0;

    public int doStartTag() throws JspException {
	JspWriter out = pageContext.getOut();

	parent = (Map) CFVariableUtil.exists(pageContext, "request.cf2jsp.cftree");
	
	if (parent == null)
	    throw new JspException("No enclosing <cftree>!");
	
	initHT();

	if (getQuery() == null)
	    try {
		out.println("" + parent.get("name") + ".add(" + (put(getValue())) + "," + // value
			getTheparent() + ",'" + // theparent
			getDisplay() + "','" + // display
			getHref() + "','" + // href
			getValue() + "','" + // title
			getTarget() + "','" + // target
			getImg() + "','" + // img
			getImgopen() + "'" + // imgopen
			getExpand() + ");"); // expand
	    } catch (java.io.IOException e) {
		e.printStackTrace();
	    }
	else {
	    CFQueryObj q = (CFQueryObj) ((Map) pageContext.getAttribute(getQuery())).get("_fieldThatWillNeverBeUsedAsATableColumn");
	    if (q == null)
		throw new JspException("<cftreeitem> query must not be null");

	    try {
		int multipleColsParent = Integer.parseInt(getTheparent());

		// put another root
		if (getQueryasroot() != null && getQueryasroot().equalsIgnoreCase("yes")) {
		    int currentparent = Integer.parseInt(getTheparent());
		    multipleColsParent = put(getQuery());
		    out.println("" + parent.get("name") + ".add(" + multipleColsParent + ","// value
			    + currentparent + ",'" // theparent
			    + getQuery().toString() + "','" // display
			    + getHref() + "','" // href
			    + getValue() + "','" // title
			    + getTarget() + "','" // target
			    + getImg() + "','" // img
			    + getImgopen() + "'" // imgopen
			    + getExpand() + ");"); // expand
		}
		q.reset();

		// check for multiple columns in value field
		// add them to a vector
		int i = 0;
		CFListUtil a = new CFListUtil(getValue(), ",");
		Vector<Integer> columnsToShow = new Vector<Integer>();

		for (String item : a) {
		    for (i = 0; i < q.getColumnnames().size(); i++)
			if (item.trim().equalsIgnoreCase(q.getColumnnames().get(i)))
			    break;

		    if (i == q.getColumnnames().size())
			throw new JspException(" You must specify the name of a column in the query " + getQuery()
				+ " for the Value attribute of the CFTREEITEM tag.");
		    else
			columnsToShow.add(i);
		}

		while (q.hasNext()) {
		    Object[] x = (Object[]) q.next();
		    int thequeryparent = multipleColsParent;
		    StringBuffer namesInTree = new StringBuffer();

		    for (int cols : columnsToShow) {
			namesInTree.append(getValue() + (++gen < Long.MAX_VALUE ? gen : 0));
			int futureParent = put(namesInTree.toString());

			out.println("" + parent.get("name") + ".add(" + futureParent + ","// value
				+ thequeryparent + ",'" // theparent
				+ x[cols].toString() + "','" // display
				+ getHref() + "','" // href
				+ x[cols].toString() + "','" // title
				+ getTarget() + "','" // target
				+ getImg() + "','" // img
				+ getImgopen() + "'" // imgopen
				+ getExpand() + ");"); // expand
			thequeryparent = futureParent;
		    }
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	return TagSupport.SKIP_BODY;
    }

    private void initHT() {
	String path = "/cfide/jstree/img/";
	
	Properties prop = new Properties();
	try {
	    prop.load(pageContext.getServletContext().getResourceAsStream("/WEB-INF/config/cfproperties.properties"));
	    path = prop.getProperty("cftree_images_location");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	ht.put("cd", path + "cd.gif");
	ht.put("computer", path + "base.gif");
	ht.put("document", path + "page.gif");
	ht.put("element", path + "empty.gif");
	ht.put("folder", path + "folder.gif");
	ht.put("floppy", path + "floppy.gif");
	ht.put("fixed", path + "fixed.gif");
	ht.put("remote", path + "globe.gif");
    }
    
    private int put(String value) throws JspException {
	Hashtable<String, Integer> ht = (Hashtable) parent.get("hashtable");
	int i = (Integer) parent.get("hashtablekey") + 1;
	ht.put(value, i);
	CFVariableUtil.create(pageContext, "request.cf2jsp.cftree.hashtablekey", new Integer(i));
	
	return i;
    }

    public String getDisplay() {
	if (display != null)
	    return display;
	else
	    return " ";
    }

    public void setDisplay(String display) {
	this.display = display;
    }

    public String getExpand() {
	if (expand != null)
	    return expand.equalsIgnoreCase("Yes") ? ",true" : ",false";
	else
	    return "";

    }

    public void setExpand(String expand) {
	this.expand = expand;
    }

    public String getHref() {
	if (href != null)
	    return (href + ((parent.get("appendkey").toString().equalsIgnoreCase("yes")) ? ("?CFTREEITEMKEY=" + getValue() + "?") : ("")))
		    .replaceAll("\\.cfm", ".jsp");
	else
	    return "javascript: void(0);";
    }

    public void setHref(String href) {
	this.href = href;
    }

    public String getImg() {
	if (img != null)
	    if (ht.containsKey(img))
		return ht.get(img);
	    else {
		CFListUtil x = new CFListUtil(img);
		if (x.hasNext()) {
		    String a = x.next();
		    if (ht.containsKey(a))
			return ht.get(a);
		}
		return img;
	    }
	else
	    return "";
    }

    public void setImg(String img) {
	this.img = img;
    }

    public String getImgopen() {
	if (imgopen != null)
	    if (ht.containsKey(imgopen))
		return ht.get(imgopen);
	    else
		return imgopen;
	else
	    return "";
    }

    public void setImgopen(String imgopen) {
	this.imgopen = imgopen;
    }

    public String getTheparent() {
	if (theparent == null)
	    return ((Hashtable) parent.get("hashtable")).get(parent.get("name")).toString();
	else
	    return ((Hashtable) parent.get("hashtable")).get(theparent).toString();
    }

    public void setTheparent(String theparent) {
	this.theparent = theparent;
    }

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public String getQueryasroot() {
	return queryasroot;
    }

    public void setQueryasroot(String queryasroot) {
	this.queryasroot = queryasroot;
    }

    public String getTarget() {
	if (target != null)
	    return target;
	else
	    return "";
    }

    public void setTarget(String target) {
	this.target = target;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

}
