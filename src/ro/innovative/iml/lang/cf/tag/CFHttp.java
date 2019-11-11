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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import ro.innovative.iml.lang.cf.basis.CFTypes;
import ro.innovative.iml.lang.cf.util.CFHttpBean;
import ro.innovative.iml.lang.cf.util.Logger;

public class CFHttp extends BodyTagSupport {

    private static final long serialVersionUID = -467432245343227L;

    private String url;
    private String port;
    private String method = "GET";
    private String proxyserver;
    private String proxyport;
    private String proxyuser;
    private String proxypassword;
    private String username;
    private String password;
    private String useragent;
    private String charset;
    private String resolveurl;
    private String throwonerror = "no";
    private String redirect;
    private String timeout;
    private String getasbinary;
    private String multipart;
    private String path;
    private String file;
    private String name;
    private String columns;
    private String firstrowasheaders;
    private String delimiter;
    private String textqualifier;
    private String result;

    private HttpURLConnection connection;
    private URL aurl;

    public int doStartTag() throws JspException {
	try {
	    CFHttpBean cfhttp = new CFHttpBean();
	    
	    Logger.println(pageContext, "puchu " + url);
	    aurl = new URL(url);
	    try {
		connection = (HttpURLConnection) aurl.openConnection();
		connection.setRequestMethod(method);
		connection.connect();

		boolean binary = isBinary();
		cfhttp.setMimetype(connection.getContentType());
		cfhttp.setResponseheader(connection.getHeaderFields());
		StringBuffer s = new StringBuffer("");
		for (int i = 0; i < connection.getHeaderFields().size(); i++)
		    s.append(connection.getHeaderField(i) + "\n");
		cfhttp.setHeader(s.toString());
		cfhttp.setCharset(connection.getContentEncoding());
		int responseCode = connection.getResponseCode();
		cfhttp.setStatuscode(responseCode + " " + connection.getResponseMessage());
		cfhttp.setText(!binary);
		if (400 <= responseCode && responseCode <= 599) {
		    cfhttp.setErrordetail(cfhttp.getStatuscode());
		    if (CFTypes.toStrictBoolean(throwonerror)) {
			throw new JspException("An error has ocured: " + cfhttp.getErrordetail());
		    }
		}
		if (cfhttp.getText()) {// is the response text?
		    cfhttp.setFilecontent(processText());
		} else {
		    cfhttp.setFilecontent(processBinary());
		}

		pageContext.setAttribute("cfhttp", cfhttp);
		/*
		 * XXX: for demonstration only! depends on variable treatment.
		 */
		pageContext.setAttribute("cfhttp_filecontent", cfhttp.getFilecontent());
		if (binary) {
		    cfhttp.setText(false);
		} else
		    cfhttp.setText(true);

	    } catch (IOException e) {
		cfhttp.setErrordetail(connection.getHeaderField(0));
		// throw new JspException(e);
	    }
	} catch (MalformedURLException e) {
	    throw new JspException(e);
	}
	return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
	return EVAL_PAGE;
    }

    private boolean isBinary() {
	boolean binary = false;
	if (getGetasbinary().equalsIgnoreCase("YES")) {
	    binary = true;
	} else if (getGetasbinary().equalsIgnoreCase("AUTO")) {
	    String type = connection.getContentType();
	    if (type != null)
		if (!(type.toLowerCase().startsWith("text") || type.toLowerCase().startsWith("message"))) {
		    binary = true;
		}
	}
	return binary;
    }

    private String processText() throws IOException {
	StringBuffer buff = new StringBuffer();
	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	String line;
	do {
	    line = reader.readLine();
	    buff.append(line);
	    buff.append("\n\r");
	} while (line != null);
	return buff.toString();
    }

    private byte[] processBinary() throws IOException {
	BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
	byte buffer[][] = new byte[1024][];
	int bufferIndex = 0;
	buffer[bufferIndex] = new byte[1024];
	int byteCount = input.read(buffer[bufferIndex]);
	int resultSize = byteCount;
	// System.err.print(" "+bufferIndex+":"+byteCount);
	while (byteCount > 0 && bufferIndex < buffer.length - 1) {
	    bufferIndex++;
	    buffer[bufferIndex] = new byte[1024];
	    byteCount = 0;
	    int k;
	    do {
		k = input.read(buffer[bufferIndex], byteCount, 1024 - byteCount);
		byteCount += k > 0 ? k : 0;
	    } while (byteCount < 1024 && k > 0);
	    // System.err.print(" "+bufferIndex+":"+byteCount);
	    resultSize += byteCount;
	}
	if (byteCount == 0) {
	    buffer[bufferIndex] = null;
	    bufferIndex--;
	}

	byte result[] = new byte[resultSize];
	for (int i = 0; i < bufferIndex; i++) {
	    // System.err.println("\ti:"+i);
	    System.arraycopy(buffer[i], 0, result, 1024 * i, 1024);
	}
	// System.err.println("# from"+ (1024*(bufferIndex))+" to "+
	// (resultSize-1024*(bufferIndex)));
	System.arraycopy(buffer[bufferIndex], 0, result, 1024 * (bufferIndex), resultSize - 1024 * (bufferIndex));
	return result;
    }

    public String getCharset() {
	return charset;
    }

    public void setCharset(String charset) {
	this.charset = charset;
    }

    public String getColumns() {
	return columns;
    }

    public void setColumns(String columns) {
	this.columns = columns;
    }

    public String getDelimiter() {
	return delimiter;
    }

    public void setDelimiter(String delimiter) {
	this.delimiter = delimiter;
    }

    public String getFile() {
	return file;
    }

    public void setFile(String file) {
	this.file = file;
    }

    public String getFirstrowasheaders() {
	return firstrowasheaders;
    }

    public void setFirstrowasheaders(String firstrowasheaders) {
	this.firstrowasheaders = firstrowasheaders;
    }

    public String getGetasbinary() {
	return getasbinary == null ? "no" : getasbinary;
    }

    public void setGetasbinary(String getasbinary) {
	this.getasbinary = getasbinary;
    }

    public String getMethod() {
	return method;
    }

    public void setMethod(String method) {
	this.method = method;
    }

    public String getMultipart() {
	return multipart;
    }

    public void setMultipart(String multipart) {
	this.multipart = multipart;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getPort() {
	return port;
    }

    public void setPort(String port) {
	this.port = port;
    }

    public String getProxypassword() {
	return proxypassword;
    }

    public void setProxypassword(String proxypassword) {
	this.proxypassword = proxypassword;
    }

    public String getProxyport() {
	return proxyport;
    }

    public void setProxyport(String proxyport) {
	this.proxyport = proxyport;
    }

    public String getProxyserver() {
	return proxyserver;
    }

    public void setProxyserver(String proxyserver) {
	this.proxyserver = proxyserver;
    }

    public String getProxyuser() {
	return proxyuser;
    }

    public void setProxyuser(String proxyuser) {
	this.proxyuser = proxyuser;
    }

    public String getRedirect() {
	return redirect;
    }

    public void setRedirect(String redirect) {
	this.redirect = redirect;
    }

    public String getResolveurl() {
	return resolveurl;
    }

    public void setResolveurl(String resolveurl) {
	this.resolveurl = resolveurl;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getTextqualifier() {
	return textqualifier;
    }

    public void setTextqualifier(String textqualifier) {
	this.textqualifier = textqualifier;
    }

    public String getThrowonerror() {
	return throwonerror;
    }

    public void setThrowonerror(String throwonerror) {
	this.throwonerror = throwonerror;
    }

    public String getTimeout() {
	return timeout;
    }

    public void setTimeout(String timeout) {
	this.timeout = timeout;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getUseragent() {
	return useragent;
    }

    public void setUseragent(String useragent) {
	this.useragent = useragent;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

}
