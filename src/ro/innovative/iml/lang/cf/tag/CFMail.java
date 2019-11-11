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

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CFMail extends BodyTagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 4118911120414663544L;

    private String to;
    private String from;
    private String cc;
    private String bcc;
    private String subject;
    private String replyto;
    private String failto;
    private String username;
    private String password;
    private String wraptext;
    private String charset;
    private String type;
    private String mimeattach;
    private String query;
    private String group;
    private String groupcasesensitive;
    private String startrow;
    private String maxrows;
    private String server;
    private String port;
    private String mailerid;
    private String timeout;
    private String spoolenable;
    private String debug;

    private java.util.Properties props;
    private Message msg;

    private Vector<BodyPart> attachments = new Vector<BodyPart>();
    private Map<String, String> headers = new HashMap<String, String>();

    Vector<BodyPart> getAttachments() {
	return attachments;
    }

    Map<String, String> getHeaders() {
	return headers;
    }

    public int doStartTag() throws JspException {
	// init
	attachments.clear();
	headers.clear();

	// Create a mail session
	props = new java.util.Properties();
	// needed props (tested with smtp.gmail.com)
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.quitwait", "false");

	// props that are overwitten from administrator
	if (getServer() != null)
	    props.put("mail.smtp.host", getServer());
	if (getPort() != null)
	    props.put("mail.smtp.port", "" + getPort());

	java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	Session session;

	// auth props if needed
	if (getUsername() != null) {
	    props.put("mail.smtp.auth", "true");
	    
	    session = Session.getDefaultInstance(props, new Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(getUsername(), getPassword() != null ? getPassword() : "");
		}
	    });
	} else
	    session = Session.getDefaultInstance(props);

	// Construct the message
	msg = new MimeMessage(session);
	try {
	    if (getFrom() != null)
		msg.setFrom(new InternetAddress(getFrom()));

	    // need to know whom to send ;)
	    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(getTo()));

	    // optional mail fields
	    if (getCc() != null)
		msg.setRecipient(Message.RecipientType.CC, new InternetAddress(getCc()));
	    if (getBcc() != null)
		msg.setRecipient(Message.RecipientType.BCC, new InternetAddress(getBcc()));
	    if (getSubject() != null)
		msg.setSubject(getSubject());
	    if (getMailerid() != null)
		msg.addHeader("Mailer-X", getMailerid());
	} catch (AddressException e) {
	    e.printStackTrace();
	} catch (MessagingException e) {
	    e.printStackTrace();
	}

	return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
	// Send the message
	try {
	    // add headers
	    Set<String> keys = headers.keySet();
	    for (String key : keys) {
		msg.addHeader(key, headers.get(key));
	    }

	    String body = getBodyContent().getString().trim();

	    // add body
	    BodyPart messageText = new MimeBodyPart();
	    messageText.setText(body);

	    // create the Multipart and its parts to it
	    Multipart mp = new MimeMultipart();
	    mp.addBodyPart(messageText);
	    for (BodyPart z : attachments)
		mp.addBodyPart(z);

	    // add the Multipart to the message
	    msg.setContent(mp);
	    msg.setSentDate(new Date());
	    msg.saveChanges();

	    Transport.send(msg);
	} catch (MessagingException e) {
	    // this is an issue when you have to authenticate via a ssh smpt
	    // server
	    props.put("mail.smtp.starttls.enable", "true");

	    Session session;
	    if (getUsername() != null) {
		session = Session.getDefaultInstance(props, new Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(getUsername(), getPassword() != null ? getPassword() : "");
		    }
		});
	    } else
		session = Session.getDefaultInstance(props);
	    
	    try {
		MimeMessage newMsg = new MimeMessage(session, ((MimeMessage) msg).getInputStream());
		newMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(getTo()));

		Transport.send(newMsg);
	    } catch (NoSuchProviderException e1) {
		e1.printStackTrace();
	    } catch (MessagingException e1) {
		e1.printStackTrace();
		throw new JspException(e1);
	    } catch (IOException e1) {
		e.printStackTrace();
	    }

	}

	return EVAL_PAGE;
    }

    public String getBcc() {
	return bcc;
    }

    public void setBcc(String bcc) {
	this.bcc = bcc;
    }

    public String getCc() {
	return cc;
    }

    public void setCc(String cc) {
	this.cc = cc;
    }

    public String getCharset() {
	return charset;
    }

    public void setCharset(String charset) {
	this.charset = charset;
    }

    public String getDebug() {
	return debug;
    }

    public void setDebug(String debug) {
	this.debug = debug;
    }

    public String getFailto() {
	return failto;
    }

    public void setFailto(String failto) {
	this.failto = failto;
    }

    public String getFrom() {
	return from;
    }

    public void setFrom(String from) {
	this.from = from;
    }

    public String getGroup() {
	return group;
    }

    public void setGroup(String group) {
	this.group = group;
    }

    public String getGroupcasesensitive() {
	return groupcasesensitive;
    }

    public void setGroupcasesensitive(String groupcasesensitive) {
	this.groupcasesensitive = groupcasesensitive;
    }

    public String getMailerid() {
	return mailerid;
    }

    public void setMailerid(String mailerid) {
	this.mailerid = mailerid;
    }

    public String getMaxrows() {
	return maxrows;
    }

    public void setMaxrows(String maxrows) {
	this.maxrows = maxrows;
    }

    public String getMimeattach() {
	return mimeattach;
    }

    public void setMimeattach(String mimeattach) {
	this.mimeattach = mimeattach;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPort() {
	if (port == null)
	    return new Integer(25).toString();
	return port;
    }

    public void setPort(String port) {
	this.port = port;
    }

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public String getReplyto() {
	return replyto;
    }

    public void setReplyto(String replyto) {
	this.replyto = replyto;
    }

    public String getServer() {
	return server;
    }

    public void setServer(String server) {
	this.server = server;
    }

    public String getSpoolenable() {
	return spoolenable;
    }

    public void setSpoolenable(String spoolenable) {
	this.spoolenable = spoolenable;
    }

    public String getStartrow() {
	return startrow;
    }

    public void setStartrow(String startrow) {
	this.startrow = startrow;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getTimeout() {
	return timeout;
    }

    public void setTimeout(String timeout) {
	this.timeout = timeout;
    }

    public String getTo() {
	return to;
    }

    public void setTo(String to) {
	this.to = to;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getWraptext() {
	return wraptext;
    }

    public void setWraptext(String wraptext) {
	this.wraptext = wraptext;
    }

}
