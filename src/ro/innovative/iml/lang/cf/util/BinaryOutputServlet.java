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
 
package ro.innovative.iml.lang.cf.util;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class BinaryOutputServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1071066003582127970L;

	static final int BUFFSIZE = 1024;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get session
		HttpSession session = request.getSession(true);
		/*
		 * If a file has beet registred for output,
		 * unregister it and output it.
		 */
		if (Boolean.parseBoolean(request.getParameter("file"))) {
			String file = (String) session.getAttribute("FILE_TO_OUTPUT"+request.getParameter("uid"));
			session.removeAttribute("FILE_TO_OUTPUT"+request.getParameter("uid"));
			if (file == null){
				//an error has ocured, most likely someone is tampering with the servlet
				//die silently!
				System.err.println("BinaryOutputServlet: No file!");
				return;
			} 
			response.setContentType((String)session.getAttribute("HEADER"+request.getParameter("uid")));
			session.removeAttribute("HEADER"+request.getParameter("uid"));
			FileInputStream fileStream;
			byte buffer[] = new byte[BUFFSIZE];
			int byteCount;
			ServletOutputStream binaryOut = response.getOutputStream();
			fileStream = new FileInputStream(file);
			byteCount = fileStream.read(buffer);
			while (byteCount > 0) {
				binaryOut.write(buffer);
				byteCount = fileStream.read(buffer);
			}
			fileStream.close();
		}
		/*
		 * A variable has been registred for output,
		 * unregister it and output it's contents.
		 * TODO: var.member does not work.
		 */
		else {
			Object var = session.getAttribute("VARIABLE_TO_OUTPUT"+request.getParameter("uid"));
			session.removeAttribute("VARIABLE_TO_OUTPUT"+request.getParameter("uid"));
			if (var == null){
				//an error has ocured, most likely someone is tampering with the servlet
				//die silently!
				System.err.println("BinaryOutputServlet: No such variable!");
				return;
			} 
			response.setContentType((String)session.getAttribute("HEADER"+request.getParameter("uid")));
			session.removeAttribute("HEADER"+request.getParameter("uid"));
			if(var instanceof byte[]){
				response.getOutputStream().write((byte[])var);
			}

			// /XXX: output a variable. since cf7.
		}
	}

}
