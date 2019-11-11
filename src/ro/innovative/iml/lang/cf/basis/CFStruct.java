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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

public class CFStruct {

    public static Object structNew() {
	return new HashMap<String, Object>();
    }

    public static Object structInsert(Object a, Object b, Object c) throws JspException {
	if (a == null)
	    throw new JspException("Variable is undefined.");
	
	Map struct = (Map) a;
	
	if (struct.containsKey(b.toString().toLowerCase()))
	    throw new JspException("Cannot insert item with key " + b.toString() + "." +
		    " This key already exists.");
	else
	    struct.put(b.toString().toLowerCase(), c);
	
	return true;
    }

    public static Object structInsert(Object a, Object b, Object c, Object d) throws JspException {
	if (a == null)
	    throw new JspException("Variable is undefined.");
	
	Map struct = (Map) a;
	
	if (struct.containsKey(b.toString().toLowerCase()) && !CFTypes.toStrictBoolean(d.toString()))
	    throw new JspException("Cannot insert item with key " + b.toString() + "." +
		    "This key already exists.");
	else
	    struct.put(b.toString().toLowerCase(), c);
	
	return true;
    }
    
    public static Object structClear(Object a) throws JspException {
    	if (a == null)
    	    throw new JspException("Variable is undefined.");
    	
    	((HashMap) a).clear();
    	
    	return true;
    }
    
    public static Object structFind(Object struct, Object key) throws JspException {
	if (struct == null)
	    throw new JspException("Struct not found!");
	
	return ((Map) struct).get(key.toString().toLowerCase());
    }

}
