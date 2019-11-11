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

import java.util.Map;

public class CFHttpBean {
    
	private String charset;
	private String errordetail;
	/**
	 * A StringBuffer for text response or a byte[] for binary response.
	 */
	private Object filecontent;
	private String header;
	private String mimetype;
	private Map responseheader;
	private String statuscode;
	private Boolean text;

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getErrordetail() {
		return errordetail;
	}

	public void setErrordetail(String errordetail) {
		this.errordetail = errordetail;
	}

	public Object getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(Object filecontent) {
		this.filecontent = filecontent;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Map getResponseheader() {
		return responseheader;
	}

	public void setResponseheader(Map responseheader) {
		this.responseheader = responseheader;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public Boolean getText() {
		return text;
	}

	public void setText(Boolean text) {
		this.text = text;
	}

}
