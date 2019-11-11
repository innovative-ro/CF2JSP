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

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import ro.innovative.iml.lang.cf.basis.CFQueryObj;
import ro.innovative.iml.lang.cf.util.CFListUtil;

public class CFLoop extends BodyTagSupport {

    private static final long serialVersionUID = -3257922905878180391L;

    // flags
    private static final int INDEX_LOOP = 1;
    private static final int CONDITIONAL_LOOP = 2;
    private static final int QUERY_LOOP = 3;
    private static final int LIST_LOOP = 4;
    private static final int STRUCT_LOOP = 5;

    private int loopType = INDEX_LOOP;

    // index
    private String index = null;
    private double from = 0;
    private double to = 0;
    private double step = 1;

    private double iterator = -1;

    // condition
    boolean cond_flag = true;

    // query
    private String startrow;
    private String endrow;
    private String query;

    private Map queryStruct;
    private CFQueryObj o;
    private Object next;

    // list
    private String list;
    private String delimiters;

    private CFListUtil l;

    // struct
    private Object collection;
    private String item;

    private Iterator structKeys;

    public int doStartTag() throws JspException {
	try {
	    if (index != null && list == null) {
		if (step == 0)
		    throw new JspTagException("Invalid <cfloop> tag: infinite loop");

		if (from != to && (step > 0) != (from < to))
		    return SKIP_BODY;

		iterator = from;

		pageContext.setAttribute(index, new Double(iterator));
		loopType = INDEX_LOOP;

		return EVAL_BODY_INCLUDE;
	    } else if (query != null) {
		queryStruct = (Map) pageContext.getAttribute(getQuery().toLowerCase());
		o = (CFQueryObj) queryStruct.get("_fieldThatWillNeverBeUsedAsATableColumn");
		o.reset();

		if (o == null)
		    throw new JspException("<cfloop> tag cannot resolve query.");
		if (getStartrow() != null)
		    if (Double.parseDouble(startrow) < 1)
			throw new JspException("<cfloop> startrow must be > 1.0 .");

		if (getEndrow() != null)
		    if (Double.parseDouble(endrow) < 1)
			throw new JspException("<cfloop> endrow must be > 1.0 .");

		loopType = QUERY_LOOP;

		if (getStartrow() != null && getEndrow() != null && Double.parseDouble(endrow) < Double.parseDouble(startrow))
		    return SKIP_BODY;

		if (o.hasNext()) {
		    next = o.next();

		    if (startrow != null)
			while (o.getCurrentrow() < Double.parseDouble(startrow) && o.hasNext())
			    next = o.next();

		    int i = 0;
		    for (String col : o.getColumnnames()) {
			pageContext.setAttribute(col.toLowerCase(), ((Object[]) next)[i]);
			queryStruct.put(col.toLowerCase(), ((Object[]) next)[i++]);
		    }
		} else {
		    return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	    } else if (list != null) {
		if (delimiters != null)
		    l = new CFListUtil(list, delimiters);
		else
		    l = new CFListUtil(list);

		loopType = LIST_LOOP;

		if (l.hasNext())
		    pageContext.setAttribute(index, l.next());
		else
		    return SKIP_BODY;

		return EVAL_BODY_INCLUDE;
	    } else if (collection != null) {
		loopType = STRUCT_LOOP;

		if (collection instanceof Map) {
		    if (item == null)
			throw new JspException("<cfloop> no item variable supplied");

		    structKeys = ((Map) collection).entrySet().iterator();

		    if (!structKeys.hasNext())
			return SKIP_BODY;
		    else
			pageContext.setAttribute(item, ((Map.Entry) structKeys.next()).getKey());

		    return EVAL_BODY_INCLUDE;
		} else
		    return SKIP_BODY;
	    } else if (cond_flag == true) {
		loopType = CONDITIONAL_LOOP;

		return EVAL_BODY_INCLUDE;
	    } else {
		return SKIP_BODY;
	    }
	} catch (Exception e) {
	    throw new JspException(e);
	}
    }

    public int doAfterBody() throws JspException {
	try {
	    if (loopType == INDEX_LOOP) {
		iterator += step;

		pageContext.setAttribute(index, new Double(iterator));

		return (iterator == to || (from < to) == (iterator < to)) ? EVAL_BODY_AGAIN : SKIP_BODY;
	    } else if (loopType == QUERY_LOOP) {
		if (o.hasNext()) {
		    if (endrow != null)
			if (Double.parseDouble(endrow) <= o.getCurrentrow())
			    return SKIP_BODY;
		    next = o.next();

		    int i = 0;
		    for (String col : o.getColumnnames()) {
			pageContext.setAttribute(col.toLowerCase(), ((Object[]) next)[i]);
			queryStruct.put(col.toLowerCase(), ((Object[]) next)[i++]);
		    }
		    pageContext.setAttribute(getQuery() + ".currentrow", o.getCurrentrow());
		    return EVAL_BODY_AGAIN;
		} else
		    return SKIP_BODY;
	    } else if (loopType == LIST_LOOP) {
		if (l.hasNext()) {
		    pageContext.setAttribute(index, l.next());

		    return EVAL_BODY_AGAIN;
		} else
		    return SKIP_BODY;
	    } else if (loopType == STRUCT_LOOP) {
		if (collection instanceof Map) {
		    if (structKeys.hasNext()) {
			pageContext.setAttribute(item, ((Map.Entry) structKeys.next()).getKey());

			return EVAL_BODY_AGAIN;
		    } else
			return SKIP_BODY;
		} else
		    return SKIP_BODY;
	    } else if (loopType == CONDITIONAL_LOOP) {
		if (cond_flag)
		    return EVAL_BODY_AGAIN;
		else
		    return SKIP_BODY;
	    } else
		return SKIP_BODY;
	} catch (Exception e) {
	    throw new JspException(e);
	}
    }

    public int doEndTag() throws JspTagException {
	try {
	    if (loopType == INDEX_LOOP) {
		pageContext.removeAttribute(index);

		index = null;
		iterator = -1;
		from = to = 0;
		step = 1;
	    } else if (loopType == QUERY_LOOP) {
		o.reset();
		for (String col : o.getColumnnames())
		    pageContext.removeAttribute(col.toLowerCase());
	    } else if (loopType == LIST_LOOP) {
		pageContext.removeAttribute(index);
	    } else if (loopType == STRUCT_LOOP) {
		pageContext.removeAttribute(item);
	    } else if (loopType == CONDITIONAL_LOOP) {
		cond_flag = true;
	    }

	    return EVAL_PAGE;
	} catch (Exception e) {
	    throw new JspTagException(e);
	}
    }
    
    // index attributes
    public void setIndex(String index) {
	this.index = index;
	cond_flag = false;
    }

    public String getIndex() {
	return index;
    }

    public void setFrom(double from) {
	this.from = from;
    }

    public double getFrom() {
	return from;
    }

    public void setTo(double to) {
	this.to = to;
    }

    public double getTo() {
	return to;
    }

    public void setStep(double step) {
	this.step = step;
    }

    public double getStep() {
	return step;
    }

    // query attributes
    public void setQuery(String query) {
	this.query = query;
	cond_flag = false;
    }

    public String getQuery() {
	return query;
    }

    public void setStartrow(String startRow) {
	this.startrow = startRow;
    }

    public String getStartrow() {
	return startrow;
    }

    public void setEndrow(String endRow) {
	this.endrow = endRow;
    }

    public String getEndrow() {
	return endrow;
    }

    // list attributes
    public String getDelimiters() {
	return delimiters;
    }

    public void setDelimiters(String delimiters) {
	this.delimiters = delimiters;
    }

    public String getList() {
	return list;
    }

    public void setList(String list) {
	this.list = list;
	cond_flag = false;
    }

    // struct attributes
    public Object getCollection() {
	return collection;
    }

    public void setCollection(Object collection) {
	this.collection = collection;
	cond_flag = false;
    }

    public String getItem() {
	return item;
    }

    public void setItem(String item) {
	this.item = item;
    }

}
