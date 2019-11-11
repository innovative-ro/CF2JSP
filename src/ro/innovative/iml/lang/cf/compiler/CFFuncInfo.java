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
 
package ro.innovative.iml.lang.cf.compiler;

public class CFFuncInfo {
	private String name;
	private int minPar;
	private int maxPar;
	private String className;
	private boolean pageContext;
	
	public CFFuncInfo(String name, int minPar, String className, boolean pageContext)
	{
		this.name = name;
		this.minPar = minPar;
		this.maxPar = minPar;
		this.className = className;
		this.pageContext = pageContext;
	}

	public CFFuncInfo(String name, int minPar, int maxPar, String className, boolean pageContext)
	{
		this.name = name;
		this.minPar = minPar;
		this.maxPar = maxPar;
		this.className = className;
		this.pageContext = pageContext;
	}

	public CFFuncInfo(String name, int minPar, String className) {
		this.name = name;
		this.minPar = minPar;
		this.maxPar = minPar;
		this.className = className;
	}

	public CFFuncInfo(String name, int minPar, int maxPar, String className) {
		this.name = name;
		this.minPar = minPar;
		this.maxPar = maxPar;
		this.className = className;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinPar() {
		return minPar;
	}

	public void setMinPar(int minPar) {
		this.minPar = minPar;
	}

	public int getMaxPar() {
		return maxPar;
	}

	public void setMaxPar(int maxPar) {
		this.maxPar = maxPar;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}	
	
	public static final CFFuncInfo FUNCS[] = {
		new CFFuncInfo("arrayAppend", 2, "CFArray"),
		new CFFuncInfo("arrayAvg", 1, "CFArray"),
		new CFFuncInfo("arrayClear", 1, "CFArray"),
		new CFFuncInfo("arrayDeleteAt", 2, "CFArray"),
		new CFFuncInfo("arrayInsertAt", 3, "CFArray"),
		new CFFuncInfo("arrayIsEmpty", 1, "CFArray"),
		new CFFuncInfo("arrayLen", 1, "CFArray"),
		new CFFuncInfo("arrayMax", 1, "CFArray"),
		new CFFuncInfo("arrayMin", 1, "CFArray"),
		new CFFuncInfo("arrayNew", 1, "CFArray"),
		new CFFuncInfo("arrayPrepend", 2, "CFArray"),
		new CFFuncInfo("arrayResize", 2, "CFArray"),
		new CFFuncInfo("arraySet", 4, "CFArray"),
		new CFFuncInfo("arraySort", 2, 3, "CFArray"),
		new CFFuncInfo("arraySum", 1, "CFArray"),
		new CFFuncInfo("arraySwap", 2, "CFArray"),
		new CFFuncInfo("arrayToList", 1, 2, "CFArray"),
		new CFFuncInfo("isArray", 1, 2, "CFArray"),
		///////////////////////////////////////////////////
		new CFFuncInfo("cat3", 3, "CFFunctions"),
		new CFFuncInfo("cat_2", 2, "CFFunctions"),
		new CFFuncInfo("cat_3", 3, "CFFunctions"),
		new CFFuncInfo("setVariable", 2, "CFFunctions", true),
		new CFFuncInfo("bs", 0, "CFFunctions"),
		new CFFuncInfo("dbs", 0, "CFFunctions"),
		new CFFuncInfo("evaluate", 1, "CFFunctions", true),
		///////////////////////////////////////////////////
		new CFFuncInfo("asc", 1, "CFString"),
		new CFFuncInfo("binaryDecode", 2, "CFString"),
		new CFFuncInfo("binaryEncode", 2, "CFString"),
		new CFFuncInfo("charsetDencode", 2, "CFString"),
		new CFFuncInfo("charsetEncode", 2, "CFString"),
		new CFFuncInfo("chr", 1, "CFString"),
		new CFFuncInfo("cJustify", 2, "CFString"),
		new CFFuncInfo("compare", 2, "CFString"),
		new CFFuncInfo("compareNoCase", 2, "CFString"),
		new CFFuncInfo("decrypt", 2, 6, "CFString"),
		new CFFuncInfo("encrypt", 2, 6, "CFString"),
		new CFFuncInfo("find", 2, 3, "CFString"),
		new CFFuncInfo("findNoCase", 2, 3, "CFString"),
		new CFFuncInfo("findOneOf", 2, 3, "CFString"),
		new CFFuncInfo("formatBaseN", 2, "CFString"),
		new CFFuncInfo("generateSecretKey", 1, "CFString"),
		new CFFuncInfo("getToken", 2, 3, "CFString"),
		new CFFuncInfo("hash", 1, 3, "CFString"),
		new CFFuncInfo("htmlCodeFormat", 1, 2, "CFString"),
		new CFFuncInfo("htmlEditFormat", 1, 2, "CFString"),
		new CFFuncInfo("insert", 3, "CFString"),
		new CFFuncInfo("jsStringFormat", 1, "CFString"),
		new CFFuncInfo("lCase", 1, "CFString"),
		new CFFuncInfo("left", 2, "CFString"),
		new CFFuncInfo("len", 1, "CFString"),
		new CFFuncInfo("lJustify", 1, "CFString"),
		new CFFuncInfo("lTrim", 1, "CFString"),
		new CFFuncInfo("mid", 3, "CFString"),
		new CFFuncInfo("paragraphFormat", 3, "CFString"),
		new CFFuncInfo("reFind", 2, 4, "CFString"),
		new CFFuncInfo("reFindNoCase", 2, 4, "CFString"),
		new CFFuncInfo("removeChars", 3, "CFString"),
		new CFFuncInfo("repeatString", 3, "CFString"),
		new CFFuncInfo("replace", 3, 4, "CFString"),
		new CFFuncInfo("replaceNoCase", 3, 4, "CFString"),
		new CFFuncInfo("reReplace", 3, 4, "CFString"),
		new CFFuncInfo("reReplaceNoCase", 3, 4, "CFString"),
		new CFFuncInfo("reverse", 1, "CFString"),
		new CFFuncInfo("right", 2, "CFString"),
		new CFFuncInfo("rJustify", 2, "CFString"),
		new CFFuncInfo("rTrim", 1, "CFString"),
		new CFFuncInfo("spanExcluding", 2, "CFString"),
		new CFFuncInfo("spanInluding", 2, "CFString"),
		new CFFuncInfo("toString", 1, 2, "CFString"),
		new CFFuncInfo("tripCR", 1, "CFString"),
		new CFFuncInfo("trim", 1, "CFString"),
		new CFFuncInfo("uCase", 1, "CFString"),
		new CFFuncInfo("urlDecode", 1, 2, "CFString"),
		new CFFuncInfo("urlEncodedFormat", 1, 2, "CFString"),
		new CFFuncInfo("val", 1, "CFString"),
		///////////////////////////////////////////////////
		new CFFuncInfo("duplicate", 1, "CFStruct"),
		new CFFuncInfo("isStruct", 1, "CFStruct"),
		new CFFuncInfo("structAppend", 2, 3, "CFStruct"),
		new CFFuncInfo("structClear", 1, "CFStruct"),
		new CFFuncInfo("structCopy", 1, "CFStruct"),
		new CFFuncInfo("structCount", 1, "CFStruct"),
		new CFFuncInfo("structDelete", 2, 3, "CFStruct"),
		new CFFuncInfo("structFind", 2, "CFStruct"),
		new CFFuncInfo("structFindKey", 3, "CFStruct"),
		new CFFuncInfo("structFindValue", 2, 3, "CFStruct"),
		new CFFuncInfo("structGet", 1, "CFStruct"),
		new CFFuncInfo("structInsert", 3, 4, "CFStruct"),
		new CFFuncInfo("structIsEmpty", 1, "CFStruct"),
		new CFFuncInfo("structKeyArray", 1, "CFStruct"),
		new CFFuncInfo("structKeyExists", 2, "CFStruct"),
		new CFFuncInfo("structKeyList", 1, 2, "CFStruct"),
		new CFFuncInfo("structNew", 0, "CFStruct"),
		new CFFuncInfo("structSort", 4, "CFStruct"),
		new CFFuncInfo("structUpdate", 3, "CFStruct"),
		///////////////////////////////////////////////////
		new CFFuncInfo("listAppend", 2, 3, "CFList"),
		new CFFuncInfo("listChangeDelims", 2, 3, "CFList"),
		new CFFuncInfo("listContains", 2, 3, "CFList"),
		new CFFuncInfo("listContainsNoCase", 2, 3, "CFList"),
		new CFFuncInfo("listDeleteAt", 2, 3, "CFList"),
		new CFFuncInfo("listFind", 2, 3, "CFList"),
		new CFFuncInfo("listFindNoCase", 2, 3, "CFList"),
		new CFFuncInfo("listFirst", 1, 2, "CFList"),
		new CFFuncInfo("listGetAt", 2, 3, "CFList"),
		new CFFuncInfo("listInsertAt", 3, 4, "CFList"),
		new CFFuncInfo("listLast", 1, 2, "CFList"),
		new CFFuncInfo("listLen", 1, 2, "CFList"),
		new CFFuncInfo("listPrepend", 2, 3, "CFList"),
		new CFFuncInfo("listQualify", 2, 4, "CFList"),
		new CFFuncInfo("listRest", 1, 2, "CFList"),
		new CFFuncInfo("listSetAt", 3, 4, "CFList"),
		new CFFuncInfo("listSort", 2, 4, "CFList"),
		new CFFuncInfo("listValueCount", 2, 3, "CFList"),
		new CFFuncInfo("listValueCountNoCase", 2, 3, "CFList"),
		new CFFuncInfo("replaceList", 3, "CFList"),
		new CFFuncInfo("valueList", 1, 2, "CFList", true),
		///////////////////////////////////////////////////
		new CFFuncInfo("createDate", 3, "CFDate"),
		new CFFuncInfo("createDateTime", 6, "CFDate"),
		new CFFuncInfo("createODBCDate", 1, "CFDate"),
		new CFFuncInfo("createODBCDateTime", 1, "CFDate"),
		new CFFuncInfo("createODBCTime", 1, "CFDate"),
		new CFFuncInfo("createTime", 3, "CFDate"),
		new CFFuncInfo("createTimeSpan", 4, "CFDate"),
		new CFFuncInfo("dateAdd", 3, "CFDate"),
		new CFFuncInfo("dateCompare", 2, 3, "CFDate"),
		new CFFuncInfo("dateConvert", 2, "CFDate"),
		new CFFuncInfo("dateDiff", 3, "CFDate"),
		new CFFuncInfo("dateFormat", 1, 2, "CFDate"),
		new CFFuncInfo("datePart", 2, "CFDate"),
		new CFFuncInfo("day", 1, "CFDate"),
		new CFFuncInfo("dayOfWeek", 1, "CFDate"),
		new CFFuncInfo("dayOfWeekAsString", 1, "CFDate"),
		new CFFuncInfo("dayOfYear", 1, "CFDate"),
		new CFFuncInfo("daysInMonth", 1, "CFDate"),
		new CFFuncInfo("daysInYear", 1, "CFDate"),
		new CFFuncInfo("firstDayOfMonth", 1, "CFDate"),
		new CFFuncInfo("getHttpTimeString", 1, "CFDate"),
		new CFFuncInfo("getTickCount", 1, "CFDate"),
		new CFFuncInfo("getTimeZoneInfo", 0, "CFDate"),
		new CFFuncInfo("hour", 1, "CFDate"),
		new CFFuncInfo("lsDateFormat", 1, 2, "CFDate"),
		new CFFuncInfo("lsIsDate", 1, "CFDate"),
		new CFFuncInfo("lsParseDate", 1, "CFDate"),
		new CFFuncInfo("lsTimeFormat", 1, 2, "CFDate"),
		new CFFuncInfo("minute", 1, "CFDate"),
		new CFFuncInfo("month", 1, "CFDate"),
		new CFFuncInfo("monthAsString", 1, "CFDate"),
		new CFFuncInfo("parseDateTime", 1, 2, "CFDate"),
		new CFFuncInfo("now", 0, "CFDate"),
		new CFFuncInfo("quarter", 1, "CFDate"),
		new CFFuncInfo("second", 1, "CFDate"),
		new CFFuncInfo("timeFormat", 1, 2, "CFDate"),
		new CFFuncInfo("week", 1, "CFDate"),
		new CFFuncInfo("year", 1, "CFDate"),
		///////////////////////////////////////////////////
		new CFFuncInfo("deleteClientVariable", 1, "CFSystem", true),
		new CFFuncInfo("directoryExists", 1, "CFSystem"),
		new CFFuncInfo("expandPath", 1, "CFSystem"),
		new CFFuncInfo("fileExists", 1, "CFSystem"),
		new CFFuncInfo("getBaseTemplatePath", 0, "CFSystem", true),
		new CFFuncInfo("getContextRoot", 0, "CFSystem"),
		new CFFuncInfo("getCurrentTemplatePath", 0, "CFSystem", true),
		new CFFuncInfo("getDirectoryFromPath", 1, "CFSystem"),
		new CFFuncInfo("getEncoding", 1, "CFSystem"),
		new CFFuncInfo("getException", 1, "CFSystem"),
		new CFFuncInfo("getFileFromPath", 1, "CFSystem"),
		new CFFuncInfo("getFunctionList", 0, "CFSystem"),
		new CFFuncInfo("getHtttpRequestData", 0, "CFSystem"),
		new CFFuncInfo("getLocale", 0, "CFSystem"),
		new CFFuncInfo("getLocaleDisplayName", 0, 2, "CFSystem"),
		new CFFuncInfo("getMetaData", 1, "CFSystem"),
		new CFFuncInfo("getMetricData", 1, "CFSystem"),
		new CFFuncInfo("getPageContext", 0, "CFSystem"),
		new CFFuncInfo("getProfileSections", 1, "CFSystem"),
		new CFFuncInfo("getProfileString", 3, "CFSystem"),
		new CFFuncInfo("getTempDirectory", 0, "CFSystem"),
		new CFFuncInfo("getTempFile", 2, "CFSystem"),
		new CFFuncInfo("getTemplatePath", 0, "CFSystem"),
		new CFFuncInfo("setEncoding", 2, "CFSystem"),
		new CFFuncInfo("setLocale", 1, "CFSystem"),
		new CFFuncInfo("setProfileString", 4, "CFSystem"),
		new CFFuncInfo("writeOutput", 1, "CFSystem", true),
		///////////////////////////////////////////////////
		new CFFuncInfo("abs", 1, "CFMath"),
		new CFFuncInfo("acos", 1, "CFMath"),
		new CFFuncInfo("asin", 1, "CFMath"),
		new CFFuncInfo("atn", 1, "CFMath"),
		new CFFuncInfo("bitAnd", 2, "CFMath"),
		new CFFuncInfo("bitMaskClear", 3, "CFMath"),
		new CFFuncInfo("bitMaskRead", 3, "CFMath"),
		new CFFuncInfo("bitMaskSet", 4, "CFMath"),
		new CFFuncInfo("bitNot", 1, "CFMath"),
		new CFFuncInfo("bitOr", 2, "CFMath"),
		new CFFuncInfo("bitSHLN", 2, "CFMath"),
		new CFFuncInfo("bitSHRN", 2, "CFMath"),
		new CFFuncInfo("bitXor", 2, "CFMath"),
		new CFFuncInfo("ceiling", 1, "CFMath"),
		new CFFuncInfo("cos", 1, "CFMath"),
		new CFFuncInfo("decrementValue", 1, "CFMath"),
		new CFFuncInfo("exp", 1, "CFMath"),
		new CFFuncInfo("fix", 1, "CFMath"),
		new CFFuncInfo("incrementValue", 1, "CFMath"),
		new CFFuncInfo("int", 1, "CFMath"),
		new CFFuncInfo("log", 1, "CFMath"),
		new CFFuncInfo("log10", 1, "CFMath"),
		new CFFuncInfo("max", 2, "CFMath"),
		new CFFuncInfo("min", 2, "CFMath"),
		new CFFuncInfo("pi", 0, "CFMath"),
		new CFFuncInfo("rand", 0, 1, "CFMath"),
		new CFFuncInfo("randomize", 1, 2, "CFMath"),
		new CFFuncInfo("randRange", 2, 3, "CFMath"),
		new CFFuncInfo("round", 1, "CFMath"),
		new CFFuncInfo("sgn", 1, "CFMath"),
		new CFFuncInfo("sin", 1, "CFMath"),
		new CFFuncInfo("sqr", 1, "CFMath"),
		new CFFuncInfo("tan", 1, "CFMath"),
		new CFFuncInfo("inputBaseN", 2, "CFMath"),
		///////////////////////////////////////////////////
		new CFFuncInfo("isDefined", 1, "CFDecision", true),
		new CFFuncInfo("isNumeric", 1, "CFDecision"),
		new CFFuncInfo("isDate", 1, "CFDecision"),
		new CFFuncInfo("isLeapYear", 1, "CFDecision"),
		new CFFuncInfo("isNumericDate", 1, "CFDecision"),
	};

	public boolean isPageContext()
	{
		return pageContext;
	}

	public void setPageContext(boolean pageContext)
	{
		this.pageContext = pageContext;
	}	
}
