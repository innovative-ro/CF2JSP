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
import java.util.HashMap;

import javax.servlet.jsp.PageContext;

import ro.innovative.iml.lang.cf.compiler.cf2jsp;


public class HtmlInc extends Node
{
	public void processStart(PrintWriter out)
	{
		out.println("<%@taglib prefix=\"cff\" uri=\"cffunctions\" %>");
		out.println("<%@taglib prefix=\"cfo\" uri=\"cfoperators\" %>");
		out.println("<%@taglib prefix=\"cf\" uri=\"cftags\" %>");
		out.println("<%@page import=\"ro.innovative.iml.lang.cf.util.CFException\" %>");
		//out.println("<%@page buffer=\"16kb\" %>");
		//out.println("<%@include file=\"/cfide/jstree/cftree_files.jspf\" %>");
		//out.println("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %>\n");
		out.println("");
		out.println("<cf:c2jinit file = \"" + cf2jsp.getCurrentFile() + "\">");
//		out.println("\t<cf:if><cf:condition expression=\"${requestScope.cf2jsp.stackcount <= 1}\">");
//		out.println("\t\t<jsp:include page=\"/Application.jsp\" />");
//		out.println("\t</cf:condition></cf:if>");
		out.println("\t<cf:if><cf:condition expression=\"${empty requestScope['javax.servlet.include.servlet_path']}\">");
		out.println("\t\t<jsp:include page=\"/Application.jsp\" />");
		out.println("\t</cf:condition></cf:if>");
		
		out.println("</cf:c2jinit>");
		out.println("");
		
		String s = cf2jsp.getCurrentFile();
		int i = s.lastIndexOf("\\");
		if (i != -1)
			s = s.substring(i + 1);
		/*if (!s.equalsIgnoreCase("application.jsp"))
		{
			
			//out.println("<jsp:include page=\"/Application.jsp\" />");
			out.println("<jsp:directive.include file=\"/Application.jsp\" />");
		
		}
		else
		{
			out.println("<cf:if><cf:condition expression=\"${requestScope.cf2jsp.stackcount <= 1}\">");
			//out.println("<cf:abort/>");
			
		}*/
		//out.println("<cf:if><cf:condition expression=\"${requestScope.cf2jsp.stackcount <= 1}\">");
		//out.println("<cf:include template=\"\\Application.jsp\"/>");
		//out.println("</cf:condition></cf:if>");
	}
	
	
	public void processFinish(PrintWriter out) throws Exception
	{
		//out.println("</cf:condition></cf:if>");
		out.print("\n<cf:c2jclean/>");
	}
}
