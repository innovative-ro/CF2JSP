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

import antlr.TokenStreamException;

public class CfQuery extends ParamTagNode
{
	public void processFinish(PrintWriter out) throws Exception
	{
		TextNode.EL = false;
		super.processFinish(out);
	}

	public void processStart(PrintWriter out) throws TokenStreamException
	{
		TextNode.EL = true;
		super.processStart(out);
	}
	
	/*private void processOutput(Node root, Enumeration en, PrintWriter out) throws Exception
	{
		if (root instanceof TextNode)
		{
			TextNode t = (TextNode)root;
			String s = t.getText();
			if (s != null && s.length() > 0)
			{
				s = ParamTagNode.getEL(s);
				t.setText(s);
			}
		}
		root.processStart(out);
		while (en.hasMoreElements())
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)en.nextElement();
			Node node = (Node)child.getUserObject();
			processOutput(node, child.children(), out);
		}
		root.processFinish(out);
	}
	
	public void process(Enumeration en, PrintWriter out) throws Exception
	{
		processStart(out);
		while (en.hasMoreElements())
		{
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)en.nextElement();
			Node node = (Node)child.getUserObject();
			processOutput(node, child.children(), out);
		}
		processFinish(out);
	}*/
}
