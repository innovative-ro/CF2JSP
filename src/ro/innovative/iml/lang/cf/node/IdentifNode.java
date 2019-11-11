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

public class IdentifNode extends Node
{
	public String toString()
	{
		String s = getValue().toString().toLowerCase();
		
		if (s.equals("application"))
			s = "applicationScope";
		else if (s.equals("session"))
			s = "sessionScope";
		else if (s.equals("request"))
			s = "requestScope";
		if (getChildNo() > 0)
			if (s.equalsIgnoreCase("cookie")/* ||
					s.equalsIgnoreCase("client")*/)
				s = s + "['" + getChildren().get(0).getValue().toString().toLowerCase();
			else
				s = s + "." + getChildren().get(0).getValue().toString().toLowerCase();
		for (int i = 1; i < getChildNo(); i++)
			s = s + "." + getChildren().get(i).getValue().toString().toLowerCase();
		if (getChildNo() > 0)
			if (getValue().toString().equalsIgnoreCase("cookie")/* ||
					getValue().toString().equalsIgnoreCase("client")*/)
				s = s + "'].value";
		return s;
	}
	
	public String forValueListName()
	{
		String s = "'" + getValue().toString().toLowerCase();
		for (int i = 0; i < getChildNo(); i++)
			s = s + "." + getChildren().get(i).getValue().toString().toLowerCase();
		s += "'";
		return s;
	}
}
