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

import antlr.TokenStreamException;

public class CfModule extends ParamTagNode
{
	public void processStart(PrintWriter out)throws TokenStreamException 
	{
		out.print("<cf:" + name);
		if (action <= 1)
			out.print(" mode=\"start\"");
		else if (action == 2)
			out.print(" mode=\"end\"");
		out.println(">");
		Enumeration en = params.keys(); 
		while (en.hasMoreElements())
		{
			String s = (String)en.nextElement();
			String z = ((Node)getParameter(s)).getValue().toString();
			if (s.toLowerCase().equals("default"))
				s = "defaultval";
			if (!s.equalsIgnoreCase("template"))
				out.println("<cf:moduleparam name = \"" + s + "\" value = \"" + getEL(z) + "\"/>");
		}
		en = params.keys(); 
		while (en.hasMoreElements())
		{
			String s = (String)en.nextElement();
			String z = ((Node)getParameter(s)).getValue().toString();
			if (s.toLowerCase().equals("template"))
			{
				//out.print(" " + s + " = \"" + getEL(z) +"\"");
				z = getEL(z);
				int i = z.toLowerCase().lastIndexOf(".cfm");
				if (i == z.length() - 4)
					z = z.substring(0, z.length() - 4) + ".jsp";
				out.println("<jsp:include page=\"" + z + "\" />");
			}
		}		
		out.println("</cf:" + name + ">");
		
		if (action == 1)
		{
			action = 2;
			processStart(out);
			action = 1;
		}
	}
}
