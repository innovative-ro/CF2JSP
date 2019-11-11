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

import ro.innovative.iml.lang.cf.basis.CFTypes;
import ro.innovative.iml.lang.cf.util.CFVariableUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class CFParam extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -7403905396830964471L;

    private String name;
    private String type;
    private Object defaultval;
    private String min;
    private String max;
    private String pattern;

    public int doStartTag() throws JspException {
	if (name == null)
	    throw new JspException("Variable name missing.");

	if (type == null && defaultval == null) {
	    Object x = CFVariableUtil.exists(pageContext, name);

	    Logger.println(pageContext, "<cfparam '" + name + "'  @" + x + "@");
	    if (x == null)
		throw new JspException("Variable " + name + " not found.");
	} else if (type == null && defaultval != null) {
	    Object x = CFVariableUtil.exists(pageContext, name);
	    
	    Logger.println(pageContext, "<cfparam '" + name + "'  @" + x + "@" + " default:" + defaultval);
	    if (x == null)
		CFVariableUtil.create(pageContext, name, defaultval);
	} else if (type != null && defaultval == null) {
	    Object x = CFVariableUtil.exists(pageContext, name);
	    
	    Logger.println(pageContext, "<cfparam '" + name + "'  @" + x + "@");
	    checkType(x, type);
	} else if (type != null && defaultval != null) {
	    Object x = CFVariableUtil.exists(pageContext, name);
	    
	    Logger.println(pageContext, "<cfparam '" + name + "'  @" + x + "@" + " default:" + defaultval);
	    if (x == null)
		CFVariableUtil.create(pageContext, name, defaultval);
	    else
		checkType(x, type);
	} else {
	    throw new JspException("Illeagal use of cfparam.");
	}

	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	return EVAL_PAGE;
    }

    private void checkType(Object x, String type) throws JspException {
	if (type.trim().equalsIgnoreCase("numeric"))
	    if (x != null && (x instanceof Double || x instanceof Long || x instanceof Integer || CFTypes.toDouble(x) != null))
		;
	    else
		throw new JspException("Variable " + name + " is not numeric.");
    }

    public Object getDefaultval() {
	return defaultval;
    }

    public void setDefaultval(Object defaultval) {
	this.defaultval = defaultval;
    }

    public String getMax() {
	return max;
    }

    public void setMax(String max) {
	this.max = max;
    }

    public String getMin() {
	return min;
    }

    public void setMin(String min) {
	this.min = min;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name.toLowerCase();
    }

    public String getPattern() {
	return pattern;
    }

    public void setPattern(String pattern) {
	this.pattern = pattern;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type.toLowerCase();
    }

}