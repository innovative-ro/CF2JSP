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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import ro.innovative.iml.lang.cf.basis.CFQueryObj;

public class RegistryIterator extends CFQueryObj
{
	private Vector<HashMap> vect = new Vector<HashMap>();
	private int index;
	
	private String columnList = "entry, type, value";
		
	public RegistryIterator(HashMap<String, String> hash)
	{
		for(String key : hash.keySet())
		{
			String value = (String)(hash.get(key));
			HashMap temp = new HashMap();
			temp.put("value", key);
			temp.put("entry", value);
			temp.put("type", "string");
			temp.put("currentrow", (vect.size() + 1) + "");
			vect.add(temp);
		}
		if (vect.size() > 0)
			index = 1;
		else
			index = 0;
	}
	
	public void reset() throws SQLException
	{
		if (vect.size() > 0)
			index = 1;
		else
			index = 0;
	}

	public Object next() throws SQLException
	{
		Object o = null;
		if (hasNext())
			o = vect.get(index - 1);
		index++;
		return o;
	}

	public boolean hasNext() throws SQLException
	{
		if (index > 0 && index <= vect.size())
			return true;
		else
			return false;
	}
	
	public int getRecordcount()
	{
		return vect.size();
	}
	
	public int getCurrentrow()
	{
		return index;
	}
	
	public String getColumnlist()
	{
		return columnList;
	}

	public Vector<String> getColumnnames()
	{
		Vector<String> v = new Vector<String>();
		v.add("entry");
		v.add("type");
		v.add("value");
		return v;
	}
}
