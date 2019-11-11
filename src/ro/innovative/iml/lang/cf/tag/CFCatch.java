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
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CFCatch extends BodyTagSupport {

	private static final long serialVersionUID = -792212245041537607L;
	
	private String type;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
