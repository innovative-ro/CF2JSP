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
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

public class CfIf extends ParamTagNode {
	Node exp;

	private boolean condprinted = false;

	public Node getExp() {
		return exp;
	}

	public void setExp(Node exp) {
		this.exp = exp;
	}

	public CfIf() {
		super();
	}

	public void processStart(PrintWriter out) {
		out.println("<cf:if>");
		out.println("<cf:condition expression=\"${" + exp + "}\">");
	}

	public void processFinish(PrintWriter out) throws Exception {
		if (!condprinted)
			out.println("</cf:condition>\n");
		out.println("</cf:if>");
		if (getWhitespace() != null && getWhitespace().length() > 0)
			out.print(getWhitespace());
	}

	public void process(Enumeration en, PrintWriter out) throws Exception {
		processStart(out);
		/*
		 * if (getWhitespace() != null && getWhitespace().length() > 0)
		 * out.print(getWhitespace());
		 */
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) en
					.nextElement();
			Node node = (Node) child.getUserObject();
			if (!condprinted && node instanceof ParamTagNode) {
				ParamTagNode p = (ParamTagNode) node;
				if (p.getName().equals("else") || p.getName().equals("elseif")) {
					condprinted = true;
					out.print("</cf:condition>\n");
				}
			}
			node.process(child.children(), out);
		}
		processFinish(out);
	}

	public String toString() {
		return "<cf:condition expression=\"${" + exp + "}\">";
	}
}
