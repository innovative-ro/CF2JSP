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

import javax.servlet.jsp.JspException;

public class CFException extends JspException
{
	static final long serialVersionUID = 62392;

	private String type;
	private String message;
	private String detail;
	private String errorCode;
	private String extendedInfo;

	public CFException(String _type, String _message, String _detail, String _errorCode, String _extendedInfo)
	{
		type = _type;
		message = _message;
		detail = _detail;
		errorCode = _errorCode;
		extendedInfo = _extendedInfo;
	}

	public CFCatchData getCFCatchData()
	{
		return new CFCatchData(message, detail, errorCode);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getDetail()
	{
		return detail;
	}

	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getExtendedInfo()
	{
		return extendedInfo;
	}

	public void setExtendedInfo(String extendedInfo)
	{
		this.extendedInfo = extendedInfo;
	}
}
