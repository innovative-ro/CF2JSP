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
import ro.innovative.iml.lang.cf.util.Logger;

public class CFCondition extends BodyTagSupport
{

	private static final long serialVersionUID = -2989110807340261938L;

	private boolean expression = false;

	private Tag t = null;

	public void setExpression(String expression)
	{
		this.expression = CFTypes.toBoolean(expression);
		t = this.getParent();
	}

	public String getExpression()
	{
		return (expression ? "true" : "false");
	}

	public int doStartTag() throws JspTagException
	{
		if (t == null)
			throw new JspTagException("Missplaced <cfcondition> tag!");
		else if (!(t instanceof CFLoop) && !(t instanceof CFIf))
			throw new JspTagException("Missplaced <cfcondition> tag!");
		
		/* Log */ if (Logger.doLog()) { Logger.logCfCondition(pageContext, expression); Logger.push(); }
		
		return expression ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	public int doEndTag() throws JspTagException
	{
		if ((t instanceof CFLoop))
			if (!expression)
				((CFLoop) t).cond_flag = false;

		if ((t instanceof CFIf))
			if (expression)
				((CFIf) t).executed = true;

		expression = false;
		
		/* Log */ if (Logger.doLog()) Logger.pop();
		
		return EVAL_PAGE;
	}

}
