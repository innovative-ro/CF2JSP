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

public class HtmlNode extends TextNode
{
	private final int formLenght = "<form ".length();
	private final int frameLenght = "<frame ".length();
	private final int aLenght = "<a ".length();
	
	public HtmlNode(String atext) {
		text = atext;
	}
	
	public void processStart(PrintWriter out)
	{
		if (text != null && text.length() >= formLenght 
				&& text.substring(0, formLenght).equalsIgnoreCase("<form ")
				&& text.charAt(text.length() - 1) == '>')
		{
			int i = text.toLowerCase().indexOf(" action");
			if (i != -1)
			{
				String s = text.substring(0, i + " action".length());
				String z = text.substring(i + " action".length());
				z = z.replaceAll("cfm", "jsp");
				text = s + z;
			}
		}
		else if (text != null && text.length() >= frameLenght 
				&& text.substring(0, frameLenght).equalsIgnoreCase("<frame ")
				&& text.charAt(text.length() - 1) == '>')
		{
			int i = text.toLowerCase().indexOf(" src");
			if (i != -1)
			{
				String s = text.substring(0, i + " src".length());
				String z = text.substring(i + " src".length());
				z = z.replaceAll("cfm", "jsp");
				text = s + z;
			}
		}
		else if (text != null && text.length() >= aLenght 
				&& text.substring(0, aLenght).equalsIgnoreCase("<a ")
				&& text.charAt(text.length() - 1) == '>')
		{
			int i = text.toLowerCase().indexOf(" href");
			if (i != -1)
			{
				String s = text.substring(0, i + " href".length());
				String z = text.substring(i + " href".length());
				z = z.replaceAll("cfm", "jsp");
				text = s + z;
			}
		}
		out.print(text);
	}
}
