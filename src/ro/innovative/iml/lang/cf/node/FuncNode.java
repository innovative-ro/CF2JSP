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
 
package ro.innovative.iml.lang.cf.node;

import ro.innovative.iml.lang.cf.compiler.CFFuncInfo;
import ro.innovative.iml.lang.cf.compiler.WarningInfo;

public class FuncNode extends Node {
	
	public String toString() {
		CFFuncInfo f = null;
		String fname = getValue().toString();
		for (int i = 0; i < CFFuncInfo.FUNCS.length; i++)
			if (CFFuncInfo.FUNCS[i].getName().toLowerCase().equals(fname.toLowerCase())) {
				f = CFFuncInfo.FUNCS[i];
				break;
			}
		if (f == null) {
			WarningInfo.add();
			System.out.println("Unknown function found: " + fname);
		}
		else {
			int pnum = getChildNo();
			/*for(Enumeration<String> en = getParameters();en.hasMoreElements();) {
				pnum++;
				en.nextElement();
			}*/
			if (pnum < f.getMinPar())
				throw new IllegalArgumentException("Function " + fname + " called with to few arguments");
			if (pnum > f.getMaxPar())
				throw new IllegalArgumentException("Function " + fname + " called with to many arguments");
			if (pnum > f.getMinPar())
				fname = fname + "_" + pnum;
		}
		String ret = "cff:" + fname.toLowerCase() + "(";
		boolean sw = false;
		for (int i = 0; i < getChildNo(); i++)
		{
			if (sw)
				ret += ", ";
			if (i == 0 && (fname.toLowerCase().equals("valuelist")) || fname.toLowerCase().equals("valuelist_2"))
				if (getChildren().get(i) instanceof IdentifNode)
					ret += ((IdentifNode)getChildren().get(i)).forValueListName();
				else
					ret += getChildren().get(i);
			else	
				ret += getChildren().get(i);
			sw = true;
		}
		if (f != null && f.isPageContext())
		{
		    
			ret += getChildNo() == 0 ? "pageContext" : ", pageContext";
		}
		ret += ")";
		return ret;
	}
	
}
