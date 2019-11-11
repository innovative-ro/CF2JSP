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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import ro.innovative.iml.lang.cf.compiler.antlr.RegistryLexer;
import ro.innovative.iml.lang.cf.compiler.antlr.RegistryParser;
import ro.innovative.iml.lang.cf.util.RegistryIterator;

public class CFRegistry extends TagSupport {

	private static final long serialVersionUID = -46732341537607L;
	
	private String action;
	private String branch;
	private String type;
	private String name; 
	private String sort;
	private String entry;
	private String variable;
	private String value;

	private HashMap<String, Object> hash = null;
	
	@SuppressWarnings("unchecked")	
	public int doStartTag() throws JspException
	{
		if (hash == null)
			readRegistry();
		if (branch == null || branch.length() == 0)
			throw new JspException("CFRegistry: must specify a branch");
		if (action.toLowerCase().equals("getall"))
		{
			for(String key : hash.keySet())
				if (key.toLowerCase().equals(branch.toLowerCase()))
				{
					HashMap<String, String> value = (HashMap<String, String>)(hash.get(key));
					pageContext.setAttribute(variable, new RegistryIterator(value), PageContext.REQUEST_SCOPE);
				}
			
		}
		else if (action.toLowerCase().equals("getall"))
		{
			for(String key : hash.keySet())
				if (key.toLowerCase().equals(branch.toLowerCase()))
				{
					HashMap<String, String> value = (HashMap<String, String>)(hash.get(key));
					for (String seckey: value.keySet())
						if (seckey.toLowerCase().equals(entry.toLowerCase()))
							pageContext.setAttribute(variable, value.get(seckey), PageContext.REQUEST_SCOPE);
				}
		}
		return SKIP_BODY;
	}

	@SuppressWarnings("unchecked")	
	private void readRegistry()
	{
		try
		{
			RegistryLexer l = new RegistryLexer(new FileReader("/srv/www/tomcat5/base/webapps/test/WEB-INF/web.reg"));
			RegistryParser p = new RegistryParser(l);
			hash = p.gen();		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (TokenStreamException e) {
			e.printStackTrace();
		}
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}
}
