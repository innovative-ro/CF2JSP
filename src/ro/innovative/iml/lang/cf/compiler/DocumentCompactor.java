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
 
package ro.innovative.iml.lang.cf.compiler;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import ro.innovative.iml.lang.cf.node.ClosingTag;
import ro.innovative.iml.lang.cf.node.ParamTagNode;
import ro.innovative.iml.lang.cf.node.UndefTag;

public class DocumentCompactor
{
	public static DefaultMutableTreeNode compact(DefaultMutableTreeNode root)
	{
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(root.getUserObject());
		while (root.getChildCount() != 0)
			tree.add(process(root));
		return tree;
	}
	
	private static DefaultMutableTreeNode process(DefaultMutableTreeNode root)
	{
		DefaultMutableTreeNode n = (DefaultMutableTreeNode)root.getChildAt(0);
		if (n.getUserObject() instanceof ParamTagNode)
		{
			root.remove(0);
			ParamTagNode p = (ParamTagNode)n.getUserObject();
			if (p.getHandler() != null)
			{
				ParamTagNode t = (ParamTagNode) NodeFactory.createNode(p.getHandler());
				t.setClosable(p.isClosable());
				t.setClosed(p.isClosed());
				t.setName(p.getName());
				t.setAction(p.getAction());
				//t.setType(p.getType());
				Enumeration<String> en = p.getParameters();
				while (en.hasMoreElements())
				{
					String s = (String) en.nextElement();
					t.setParameter(s, p.getParameter(s));
				}
				n.setUserObject(t);
				p = t;
			}
			if (!p.getName().equals("elseif") && p.isClosable() && !p.isClosed())
			{
				while (root.getChildCount() != 0)
				{
					DefaultMutableTreeNode m = process(root);
					if (m.getUserObject() instanceof ClosingTag)
					{
						if (((ClosingTag)m.getUserObject()).getName().equals(p.getName()))
						{
							root.remove(0);
							p.addTrailWhitespace(((ClosingTag)m.getUserObject()).getWhitespace());
							break;
						}
						else
						{
							System.out.println("CFM structure error: </" + ((ClosingTag)m.getUserObject()).getName() + '>');
							WarningInfo.add();
							break;
						}
					}
					else
						n.add(m);
				}
			}
			else if (p.getName().equals("elseif"))
			{
				while (root.getChildCount() != 0)
				{
					DefaultMutableTreeNode m = (DefaultMutableTreeNode)root.getChildAt(0);
					if (m.getUserObject() instanceof ClosingTag)
						break;
					else if (m.getUserObject() instanceof ParamTagNode)
					{
						ParamTagNode q = (ParamTagNode)m.getUserObject();
						if (q.getName().equals("elseif"))
							break;
						else
						{
							m = process(root);
							n.add(m);
						}
					}
					else if (m.getUserObject() instanceof UndefTag &&
							((UndefTag)m.getUserObject()).getName().equals("else"))
						break;							
					else
					{
						m = process(root);
						n.add(m);
					}
				}
			}
		}
		else if (n.getUserObject() instanceof UndefTag)
		{
			root.remove(0);
			if (((UndefTag)n.getUserObject()).getName().equals("else"))
			{
				while (root.getChildCount() != 0)
				{
					DefaultMutableTreeNode m = process(root);
					if (m.getUserObject() instanceof ClosingTag)
						break;
					else
						n.add(m);
				}
			}
			ParamTagNode p = new ParamTagNode();
			p.setName("else");
			p.setClosable(true);
			n.setUserObject(p);
		}
		else if (!(n.getUserObject() instanceof ClosingTag))
			root.remove(0);
		return n;
	}
}
