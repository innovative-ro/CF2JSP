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

public class CFApplication extends TagSupport {

	private static final long serialVersionUID = -4679312245041537607L;
	
	private String name;
	private String loginstorage;
	private String clientmanagement;
	private String clientstorage;
	private String setclientcookies; 
	private String sessionmanagement;
	private String sessiontimeout;
	private String applicationtimeout;
	private String setdomaincookies;
	private String scriptprotect;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getApplicationtimeout() {
		return applicationtimeout;
	}

	public void setApplicationtimeout(String applicationTimeout) {
		this.applicationtimeout = applicationTimeout;
	}

	public String getClientmanagement() {
		return clientmanagement;
	}

	public void setClientmanagement(String clientManagement) {
		this.clientmanagement = clientManagement;
	}

	public String getClientstorage() {
		return clientstorage;
	}

	public void setClientstorage(String clientStorage) {
		this.clientstorage = clientStorage;
	}

	public String getLoginstorage() {
		return loginstorage;
	}

	public void setLoginstorage(String loginStorage) {
		this.loginstorage = loginStorage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScriptprotect() {
		return scriptprotect;
	}

	public void setScriptprotect(String scriptProtect) {
		this.scriptprotect = scriptProtect;
	}

	public String getSessionmanagement() {
		return sessionmanagement;
	}

	public void setSessionmanagement(String sessionManagement) {
		this.sessionmanagement = sessionManagement;
	}

	public String getSessiontimeout() {
		return sessiontimeout;
	}

	public void setSessiontimeout(String sessionTimeout) {
		this.sessiontimeout = sessionTimeout;
	}

	public String getSetclientcookies() {
		return setclientcookies;
	}

	public void setSetclientcookies(String setClientCookies) {
		this.setclientcookies = setClientCookies;
	}

	public String getSetdomaincookies() {
		return setdomaincookies;
	}

	public void setSetdomaincookies(String setDomainCookies) {
		this.setdomaincookies = setDomainCookies;
	}
	
	
}
