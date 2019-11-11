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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CFSearch extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 484043196362396389L;
	
	private String name;
	private String collection;
	private String category;
	private String categorytree;
	private String status;
	private String type;
	private String criteria;
	private String maxrows;
	private String startrow;
	private String suggestions;
	private String contextpassages;
	private String contextbytes;
	private String contexthighlightbegin;
	private String contexthighlightend;
	private String previouscriteria;
	private String language;

	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategorytree() {
		return categorytree;
	}

	public void setCategorytree(String categoryTree) {
		this.categorytree = categoryTree;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getContextbytes() {
		return contextbytes;
	}

	public void setContextbytes(String contextBytes) {
		this.contextbytes = contextBytes;
	}

	public String getContexthighlightbegin() {
		return contexthighlightbegin;
	}

	public void setContexthighlightbegin(String contextHighlightBegin) {
		this.contexthighlightbegin = contextHighlightBegin;
	}

	public String getContexthighlightend() {
		return contexthighlightend;
	}

	public void setContexthighlightend(String contextHighlightEnd) {
		this.contexthighlightend = contextHighlightEnd;
	}

	public String getContextpassages() {
		return contextpassages;
	}

	public void setContextpassages(String contextPassages) {
		this.contextpassages = contextPassages;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMaxrows() {
		return maxrows;
	}

	public void setMaxrows(String maxRows) {
		this.maxrows = maxRows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreviouscriteria() {
		return previouscriteria;
	}

	public void setPreviouscriteria(String previousCriteria) {
		this.previouscriteria = previousCriteria;
	}

	public String getStartrow() {
		return startrow;
	}

	public void setStartrow(String startRow) {
		this.startrow = startRow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
