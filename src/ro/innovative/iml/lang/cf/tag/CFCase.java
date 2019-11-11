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

import ro.innovative.iml.lang.cf.basis.CFOperators;
import ro.innovative.iml.lang.cf.util.CFListUtil;
import ro.innovative.iml.lang.cf.util.Logger;

public class CFCase extends BodyTagSupport {

	private static final long serialVersionUID = -2903401747929554757L;

	private String expression = null;

	private String value = null;

	private String delimiter = ",";

	public int doStartTag() throws JspTagException {
		CFSwitch parent = (CFSwitch) findAncestorWithClass(this, CFSwitch.class);

		if (parent == null) {
			throw new JspTagException("Missplaced <cfcase> tag!");
		} else {
			boolean matched = false;

			expression = parent.getExpression();

			CFListUtil x = new CFListUtil(value, delimiter != null ? delimiter
					: "");

			for (String token : x) {
				if ((Boolean) CFOperators.cfeq(token, expression))
					matched = true;
			}

			if (matched) {
				parent.setMatchedCondition(true);
				return EVAL_BODY_INCLUDE;
			} else
				return SKIP_BODY;
		}
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setDelimiter(String value) {
		delimiter = value;
	}

	public String getDelimiter() {
		return delimiter;
	}

}