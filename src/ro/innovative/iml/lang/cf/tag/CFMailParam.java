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

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CFMailParam extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 484043196362393469L;

    private String file;
    private String type;
    private String contentid;
    private String disposition;
    private String name;
    private String value;

    public int doStartTag() throws JspException {
	CFMail parent = (CFMail) findAncestorWithClass(this, CFMail.class);
	if (name != null)
	    parent.getHeaders().put(name, value);
	else if (file != null) {
	    try {
		BodyPart attachment = new MimeBodyPart();
		DataSource fds = new FileDataSource(file);
		attachment.setDataHandler(new DataHandler(fds));
		
		if (getContentid() != null && getDisposition() != null)
		    if (getDisposition().equalsIgnoreCase("inline"))
			attachment.setHeader("Content-ID", getContentid());
		
		if (! fds.getContentType().startsWith("text"))
		    attachment.setFileName(fds.getName());

		parent.getAttachments().add(attachment);
	    } catch (MessagingException e) {
		e.printStackTrace();
	    }
	    
	} else
	    throw new JspException("<cfmailparam> illeagal use.");

	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	return EVAL_PAGE;
    }

    public String getContentid() {
	return contentid;
    }

    public void setContentid(String contentid) {
	this.contentid = contentid;
    }

    public String getDisposition() {
	return disposition;
    }

    public void setDisposition(String disposition) {
	this.disposition = disposition;
    }

    public String getFile() {
	return file;
    }

    public void setFile(String file) {
	this.file = file;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getType() {
	if (type != null)
	    if (type.equalsIgnoreCase("text"))
		return "text/plain";
	    else if (type.equalsIgnoreCase("plain"))
		return "text/plain";
	    else if (type.equalsIgnoreCase("html"))
		return "text/html";
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

}
