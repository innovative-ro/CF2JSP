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
 
package ro.innovative.iml.lang.cf.node;

import java.io.PrintWriter;

import antlr.TokenStreamException;

public class CfSet extends Node {
	private String var;

	private Node expr;

	public CfSet(String var, Node expr) {
		this.expr = expr;
		this.var = var;
	}

	public Node getExpr() {
		return expr;
	}

	public void setExpr(Node expr) {
		this.expr = expr;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void processStart(PrintWriter out) throws TokenStreamException {
		out.print("<cf:set name = \"" + var + "\" value = \"");
		/*
		 * if (expr instanceof OpNode) { OpNode p = (OpNode)expr; if
		 * (p.getValue() instanceof String) p.setExpand(false);
		 * out.print(p.toString() + "\""); } else {
		 */
		String s;
		// if (expr instanceof OpNode && ((OpNode) expr).getValue() instanceof
		// String && ((OpNode) expr).getValue().toString().equals("\\\\"))
		// s = ((OpNode) expr).getValue().toString();
		// else
		s = expr.toString();
		out.print("${" + s + "}\"");
		// }
		out.print("/>");
		if (getWhitespace() != null && getWhitespace().length() > 0)
			out.print(getWhitespace());
	}

	/*
	 * public String escape(String s) { int i = s.indexOf('\\'); String z = s;
	 * while (i != -1) { z = s.substring(0, i) + '\\' + s.substring(i); i =
	 * s.indexOf('\\', i + 1); } return z; }
	 */
}
