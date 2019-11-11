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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import ro.innovative.iml.lang.cf.compiler.antlr.*;
import ro.innovative.iml.lang.cf.node.*;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class DocumentParser
{
	private final static int gtUnknown = 0;
	private final static int gtWhitespace = 1;
	private final static int gtComment = 2;
	private final static int gtHtml = 3;
	private final static int gtText = 4;
	private final static int gtCF = 5;
	private final static int gtCFEnd = 6;
	private final static int gtEOF = 7;

	public static int guess(Reader is)
	{
		int ret = gtUnknown;
		try
		{
			is.mark(6);
			int c = is.read(), k = 0;
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n')
				ret = gtWhitespace;
			else if (c == '<')
			{
				c = is.read();
				if (c == '!' && is.read() == '-' && is.read() == '-')
					ret = gtComment;
				else if ((c == 'c' || c == 'C') && ((k = is.read()) == 'f' || k == 'F'))
					ret = gtCF;
				else if (c == '/' && ((k = is.read()) == 'c' || k == 'C') && ((k = is.read()) == 'f' || k == 'F'))
					ret = gtCFEnd;
				else
					ret = gtHtml;
			}
			else if (c == -1)
				ret = gtEOF;
			else
				ret = gtText;
			is.reset();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return ret;
	}

	public static String getUntil(Reader is, char cu)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			is.mark(3);
			int c;
			c = is.read();
			while (true)
			{
				if (c == cu)
				{
					is.reset();
					break;
				}
				else
				{
					sb.append((char) c);
					is.mark(1);
					c = is.read();
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getUntil(Reader is, char cu, char cucu, char cu2)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			is.mark(3);
			int c;
			c = is.read();
			while (c != -1)
			{
				if (c == cu || c == cu2 || c == cucu)
				{
					is.reset();
					break;
				}
				else
				{
					sb.append((char) c);
					// if (c == cu2)
					// break;
					is.mark(1);
					c = is.read();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	private static String getId(Reader is) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		is.mark(1);
		int i = is.read();
		while ((i >= '0' && i <= '9') || (i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z') || i == '_')
		{
			is.mark(1);
			sb.append((char) i);
			i = is.read();
		}
		is.reset();
		return sb.toString();
	}

	static CFTagInfo[] taginfo =
		{
			new CFTagInfo("cookie",			false),
			new CFTagInfo("import",			false),
			new CFTagInfo("associate",		false),
			new CFTagInfo("query",			true,	"CfQuery"),
			new CFTagInfo("param",			false),
			new CFTagInfo("include",		false,	"CfInclude"),
			new CFTagInfo("module",			false,	"CfModule"),
			new CFTagInfo("throw",			false),
			new CFTagInfo("transaction",	true),
			new CFTagInfo("try",			true,	"CfTry"),
			new CFTagInfo("catch",			true),
			new CFTagInfo("loop",			true,	"CfLoop"),
			new CFTagInfo("location",		false),
			new CFTagInfo("file",			false),
			new CFTagInfo("setting",		false),
			new CFTagInfo("content",		false),
			new CFTagInfo("script",			true),
			new CFTagInfo("rethrow",		false),
			new CFTagInfo("lock",			true),
			new CFTagInfo("abort",			false,	"CfAbort"),
			new CFTagInfo("mail",			true),
			new CFTagInfo("mailparam",		false),
			new CFTagInfo("registry",		false),
			new CFTagInfo("index",			false),
			new CFTagInfo("search",			false),
			new CFTagInfo("exit",			false,	"CfExit"),
			new CFTagInfo("header",			false),
			new CFTagInfo("form",			true),
			new CFTagInfo("http",			true),
			new CFTagInfo("tree",			true,	"CfTree"),
			new CFTagInfo("treeitem",		false),
			new CFTagInfo("application",	false),
			new CFTagInfo("switch",			true),
			new CFTagInfo("case",			true),
			new CFTagInfo("defaultcase",	true),
			new CFTagInfo("directory",		false),
			new CFTagInfo("output",			true,	"CfOutput"),
			//new CFTagInfo("if",				true),
		};

	static private CFTagInfo getTagInfo(String name)
	{
		for (int i = 0; i < taginfo.length; i++)
		{
			if (taginfo[i].getName().equals(name))
				return taginfo[i];
		}
		return null;
	}
	
	public static DefaultMutableTreeNode getDocument(String input) throws RecognitionException, TokenStreamException, IOException
	{
		DefaultMutableTreeNode root = null;
		HtmlInc temp = new HtmlInc();
		temp.setValue(input);
		root = new DefaultMutableTreeNode(temp);
		//DefaultMutableTreeNode last = root;
		BufferedReader fis = new BufferedReader(new FileReader(input));
		int g = gtEOF;
		do
		{
			g = guess(fis);
			DefaultMutableTreeNode n = parseDoc(fis, g);
			if (n != null)
				root.add(n);
		} while (g != gtEOF);
		return root;
	}

	private static DefaultMutableTreeNode parseDoc(Reader fis, int g)
			throws RecognitionException, TokenStreamException, IOException
	{
		DefaultMutableTreeNode n = null;
		if (g == gtWhitespace)
			n = getWhitespace(fis);
		else if (g == gtComment)
			n = getComment(fis);
		else if (g == gtText)
			n = getText(fis);
		else if (g == gtHtml)
			n = getHtml(fis);
		else if (g == gtCFEnd)
			n = getCloseTag(fis);
		else if (g == gtCF)
			n = getTag(fis);
		g = guess(fis);
		while (g == gtWhitespace)
		{
			((Node)n.getUserObject()).addWhitespace(((WhitespaceNode)getWhitespace(fis).getUserObject()).getText());
			g = guess(fis);
		}
		return n;
	}

	private static DefaultMutableTreeNode getWhitespace(Reader input)
			throws RecognitionException, TokenStreamException
	{
		WhitespaceLexer l = new WhitespaceLexer(input);
		WhitespaceParser p = new WhitespaceParser(l);
		return new DefaultMutableTreeNode(p.gen());
	}

	private static DefaultMutableTreeNode getComment(Reader input)
			throws RecognitionException, TokenStreamException
	{
		HtmlCommentLexer l = new HtmlCommentLexer(input);
		HtmlCommentParser p = new HtmlCommentParser(l);
		return new DefaultMutableTreeNode(p.gen());
	}

	private static DefaultMutableTreeNode getText(Reader input)
			throws RecognitionException, TokenStreamException
	{
		return new DefaultMutableTreeNode(new TextNode(getUntil(input, '<')));
	}

	private static DefaultMutableTreeNode getHtml(Reader input)
			throws RecognitionException, TokenStreamException, IOException
	{
		int c = input.read();
		HtmlNode t = new HtmlNode((char)c + getUntil(input, '>', '<', '<'));
		input.mark(1);
		if (input.read() == '>')
			t.setText(t.getText() + '>'); 
		else
			input.reset();
		return new DefaultMutableTreeNode(t);
	}

	private static DefaultMutableTreeNode getCloseTag(Reader input)
			throws RecognitionException, TokenStreamException, IOException
	{
		input.read();
		input.read();
		String tag = getId(input).toLowerCase();
		TagLexer l = new TagLexer(input);
		TagParser p = new TagParser(l);
		return new DefaultMutableTreeNode(new ClosingTag(tag.substring(2), p.gen().toString()));
	}

	private static DefaultMutableTreeNode getTag(Reader input)
			throws RecognitionException, TokenStreamException, IOException
	{
		input.read();
		String tag = getId(input).toLowerCase();
		CFTagInfo ti = getTagInfo(tag.substring(2));
		if (ti != null)
		{
			if (ti.isClosable())
			{
				ClosableTagLexer l = new ClosableTagLexer(input);
				ClosableTagParser p = new ClosableTagParser(l);
				 
				ParamTagNode n = p.gen();
				n.setClosable(ti.isClosable());
				if (ti.getNode() != null)
					n.setHandler(ti.getNode());
				DefaultMutableTreeNode cftag = new DefaultMutableTreeNode(n);
				n.setName(tag.substring(2));
				
				if (n.getName().equals("transaction"))
				{
					Enumeration<String> en = n.getParameters();
					while (en.hasMoreElements())
					{
						String s = en.nextElement();
						if (s.equalsIgnoreCase("action"))
						{
							String z = n.getParameter(s).getValue().toString();
							if (z.equalsIgnoreCase("rollback") || z.equalsIgnoreCase("commit"))
								n.setClosed(true);
						}
					}
				}
				return cftag;
			} 
			else
			{
				ParamTagLexer l = new ParamTagLexer(input);
				ParamTagParser p = new ParamTagParser(l);
				ParamTagNode n = p.gen();
				n.setClosable(ti.isClosable());
				n.setHandler(ti.getNode());
				DefaultMutableTreeNode cftag = new DefaultMutableTreeNode(n);
				n.setName(tag.substring(2));
				if (n.getName().equals("module"))
				{
					input.mark("</cfmodule>".length() + 1);
					String s = "";
					for (int i = 0; i < "</cfmodule>".length(); i++)
					{
						int c = input.read();
						s += (char) c;
					}
					if (!s.equals("</cfmodule>"))
						input.reset();
					else
						n.setAction(1);
				}
				return cftag;
			}
		} 
		else if (tag.equals("cfif"))
			return getIfTag(input, tag.substring(2));
		else if (tag.equals("cfelseif"))
			return getElseIfTag(input, tag.substring(2));
		else if (tag.equals("cfset"))
		{
			PreExprLexer l = new PreExprLexer(input);
			PreExprParser p = new PreExprParser(l);
			Node val = p.cfset();
						
			ExprLexer le = new ExprLexer(new StringReader(val.getChildren().get(1).toString()));
			ExprParser pe = new ExprParser(le);
			
			CfSet cfset = new CfSet(val.getChildren().get(0).toString(), pe.gen());
			DefaultMutableTreeNode cfsetnode = new DefaultMutableTreeNode(cfset);
			return cfsetnode;
		} 
		else
		{
			TagLexer l = new TagLexer(input);
			TagParser p = new TagParser(l);
			return new DefaultMutableTreeNode(new UndefTag(tag.substring(2), p
					.gen().toString()));
		}
	}

	private static DefaultMutableTreeNode getIfTag(Reader input, String tag)
			throws RecognitionException, TokenStreamException, IOException
	{
		PreExprLexer l = new PreExprLexer(input);
		PreExprParser p = new PreExprParser(l);

		CfIf cfif = new CfIf();
		cfif.setName(tag);
		cfif.setClosable(true);

		DefaultMutableTreeNode cfifnode = new DefaultMutableTreeNode(cfif);

		String n = p.gen();
		ExprLexer le = new ExprLexer(new StringReader(n));
		ExprParser pe = new ExprParser(le);
		
		cfif.setExp(new OpNode(pe.gen()));
		
		return cfifnode;
	}
	
	private static DefaultMutableTreeNode getElseIfTag(Reader input, String tag)
		throws RecognitionException, TokenStreamException, IOException
	{
		PreExprLexer l = new PreExprLexer(input);
		PreExprParser p = new PreExprParser(l);

		CfElseIf cfif = new CfElseIf();
		cfif.setName(tag);
		cfif.setClosable(true);

		DefaultMutableTreeNode cfifnode = new DefaultMutableTreeNode(cfif);

		String n = p.gen();
		ExprLexer le = new ExprLexer(new StringReader(n));
		ExprParser pe = new ExprParser(le);
		cfif.setExp(new OpNode(pe.gen()));
		
		return cfifnode;
	}
}
