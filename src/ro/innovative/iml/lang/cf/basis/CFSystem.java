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
 
package ro.innovative.iml.lang.cf.basis;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;

public class CFSystem {

    public static Boolean directoryExists(Object path) {
	File x = new File(path.toString());
	return x.exists() && x.isDirectory();
    }

    public static Boolean fileExists(Object path) {
	File x = new File(path.toString());
	return x.exists() && x.isFile();
    }

    public static Object getBaseTemplatePath(PageContext pageContext) {
	return pageContext.getServletContext().getRealPath("")
		+ ((HttpServletRequest) pageContext.getRequest()).getServletPath();
    }

    public static Object getCurrentTemplatePath(PageContext pageContext) {
	Object isIncluded = pageContext.getRequest().getAttribute("javax.servlet.include.servlet_path");

	if (isIncluded == null || isIncluded.equals(""))
	    return getBaseTemplatePath(pageContext);
	else
	    return pageContext.getServletContext().getRealPath("") + isIncluded.toString();
    }

    public static Object writeOutput(Object string, PageContext pageContext) throws JspException {
	try {
	    pageContext.getOut().print(string);
	} catch (IOException e) {
	    throw new JspException(e);
	}

	return true;
    }

    public static Object getDirectoryFromPath(Object path) {
	return path.toString().substring(0, path.toString().lastIndexOf(java.io.File.separator)) + java.io.File.separator;
    }

    public static Object getFileFromPath(Object path) {
	return path.toString().substring(path.toString().lastIndexOf(java.io.File.separator) + 1);
    }

    public static Object deleteClientVariable(Object var, PageContext pageContext) {
	try {
	    Map x = (Map) CFVariableUtil.exists(pageContext, "client");

	    if (x != null)
		x.remove(var);
	    else
		return Boolean.FALSE;
	} catch (Exception e) {
	    return Boolean.FALSE;
	}

	return Boolean.TRUE;
    }

}
