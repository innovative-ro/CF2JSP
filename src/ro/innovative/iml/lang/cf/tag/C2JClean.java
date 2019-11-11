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

import java.sql.Connection;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import ro.innovative.iml.lang.cf.util.CFVariableUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class C2JClean extends TagSupport {
	private static final long serialVersionUID = -9200947615804081382L;

	public int doStartTag() throws JspException {
		Object o;
		if (Logger.doLog())
			Logger.println(pageContext, "////// clean");
		try {
			if ((o = pageContext.findAttribute("statement")) != null) {
				if (o instanceof Statement) {
					((Statement) o).close();
				}
			}
			if ((o = pageContext.findAttribute("conn")) != null) {
				if (o instanceof Connection) {
					if (!((Connection) o).isClosed())
						((Connection) o).close();
				}
			}
			return EVAL_PAGE;
		} catch (Exception e) {
			throw new JspException(e);
		}

	}

	public int doEndTag() throws JspException {
		Long cnt = (Long) CFVariableUtil.exists(pageContext,
				"request.cf2jsp.stackcount");
		CFVariableUtil
				.create(pageContext, "request.cf2jsp.stackcount", cnt - 1);
		// if (cnt == 0)
		// Logger.reset();

		CFVariableUtil.pushPageContext(pageContext);

		return EVAL_PAGE;
	}
}
