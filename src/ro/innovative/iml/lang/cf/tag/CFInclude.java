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

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;

public class CFInclude extends TagSupport {
    private static final long serialVersionUID = -3287418681150785053L;

    private String template;

    public void setTemplate(String arg) {
	this.template = arg;
    }

    public int doStartTag() throws JspException {
	CFVariableUtil.pushPageContext(pageContext);

	return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
	CFVariableUtil.popPageContext(pageContext);

	return EVAL_PAGE;
    }

    public String getTemplate() {
	return template;
    }
}
