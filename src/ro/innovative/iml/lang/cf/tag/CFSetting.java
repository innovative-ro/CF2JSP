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

public class CFSetting extends TagSupport {

	private static final long serialVersionUID = -4679432423041537607L;
	
	private String enablecfoutputonly;
	private String showdebugoutput;
	private String requesttimeout;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getEnablecfoutputonly() {
		return enablecfoutputonly;
	}

	public void setEnablecfoutputonly(String enableCFoutputOnly) {
		this.enablecfoutputonly = enableCFoutputOnly;
	}

	public String getRequesttimeout() {
		return requesttimeout;
	}

	public void setRequesttimeout(String requestTimeOut) {
		this.requesttimeout = requestTimeOut;
	}

	public String getShowdebugoutput() {
		return showdebugoutput;
	}

	public void setShowdebugoutput(String showDebugOutput) {
		this.showdebugoutput = showDebugOutput;
	}
	
	

}
