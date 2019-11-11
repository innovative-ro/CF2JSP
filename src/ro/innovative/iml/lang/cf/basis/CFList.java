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
 
package ro.innovative.iml.lang.cf.basis;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.jsp.PageContext;

//import com.sun.xml.internal.bind.v2.TODO;

import ro.innovative.iml.lang.cf.util.CFListElementComparator;
import ro.innovative.iml.lang.cf.util.CFListUtil;

/**
 * @author Puiutii
 * 
 */
public class CFList {

    public static String listAppend(Object list1, Object list2) {
	return listAppend(list1, list2, ',');
    }

    public static String listAppend(Object list1, Object list2, Object delim) {
	if (!list1.toString().equals(""))
	    return delim.toString().equals("") ? list1.toString() : list1.toString() + delim.toString().charAt(0)
		    + list2.toString();
	else
	    return delim.toString().equals("") ? list1.toString() : list2.toString();
    }

    public static Integer listContains(Object list1, Object element) {
	return listContains(list1, element, ',');
    }

    public static Integer listContains(Object list1, Object element, Object delim) {
	CFListUtil x = new CFListUtil(list1.toString(), delim.toString());

	int i = 0;
	for (String a : x) {
	    if (a.contains(element.toString()))
		return i + 1;
	    else
		i++;
	}

	return 0;
    }

    public static String listGetAt(Object list, Object pos) {
	return listGetAt(list, pos, ',');
    }

    public static String listGetAt(Object list, Object pos, Object delim) {
	CFListUtil x = new CFListUtil(list.toString(), delim.toString());

	int i = 0, goal = CFTypes.toDouble(pos).intValue();

	if (goal < 1)
	    throw new IllegalArgumentException("The index cannot be 0 or lower.");

	for (String a : x) {
	    if (i + 1 == goal)
		return a;
	    else
		i++;
	}

	throw new IllegalArgumentException("Invalid list index " + goal + ".");
    }

    public static String listSort(Object list, Object type) {
	return listSort(list, type, "asc", ',');
    }

    public static String listSort(Object list, Object type, Object order) {
	return listSort(list, type, order, ',');
    }

    public static String listSort(Object list, Object type, Object order, Object delim) throws NumberFormatException {
	int t = CFListElementComparator.TYPE_TEXT, o = CFListElementComparator.ORDER_ASC;

	if (type.toString().equalsIgnoreCase("text"))
	    t = CFListElementComparator.TYPE_TEXT;
	else if (type.toString().equalsIgnoreCase("textnocase"))
	    t = CFListElementComparator.TYPE_TEXTNOCASE;
	else if (type.toString().equalsIgnoreCase("numeric"))
	    t = CFListElementComparator.TYPE_NUMERIC;
	else
	    throw new IllegalArgumentException("The type must be: text, textnocase or numeric.");

	if (order.toString().equalsIgnoreCase("asc"))
	    o = CFListElementComparator.ORDER_ASC;
	else if (order.toString().equalsIgnoreCase("desc"))
	    o = CFListElementComparator.ORDER_DESC;
	else
	    throw new IllegalArgumentException("The order must be: asc or desc.");

	if (list.toString().equals(""))
	    return "";

	String[] a = new CFListUtil(list.toString(), delim.toString()).stringToArray();
	Arrays.sort(a, new CFListElementComparator(t, o));

	if (delim.toString().equals(""))
	    delim = ","; // TODO what if delim really is ""

	return CFListUtil.arrayToString(a, delim.toString().charAt(0));
    }

    public static String valueList(Object queryColumn, PageContext pc) {
	String[] s = queryColumn.toString().split("\\.");

	CFQueryObj o = (CFQueryObj) ((Map) pc.getAttribute(s[0])).get("_fieldThatWillNeverBeUsedAsATableColumn");
	StringBuffer sb = new StringBuffer();
	try {
	    o.reset();

	    int i = 0;
	    for (String a : new CFListUtil(o.getColumnlist()))
		if (a.equalsIgnoreCase(s[1]))
		    break;
		else
		    i++;
	    if (i >= o.getColumnnames().size())
		return "";

	    while (o.hasNext()) {
		Object[] next = (Object[]) o.next();
		sb.append(next[i] + ",");
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return sb.toString().substring(0, sb.length() - 2);
    }

    public static Object listLen(Object list) {
	return listLen(list, ',');
    }

    public static Object listLen(Object list, Object delim) {
	int i = 0;
	CFListUtil x = new CFListUtil(list.toString(), delim.toString());

	for (String a : x)
	    i++;

	return i;
    }

}
