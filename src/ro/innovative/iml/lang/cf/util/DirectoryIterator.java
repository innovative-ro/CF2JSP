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

import java.util.*;

import ro.innovative.iml.lang.cf.basis.*;

public class DirectoryIterator extends CFQueryObj {
	private int currentRow;

	private int RecordCount;

	private final static String columnList = "name,directory,size,type,datelastmodified,attributes,mode";
    private final static Vector<String> columnNames = new Vector<String>();;
    static {
		String cns[] = columnList.split(",");
		for (String cn : cns)
			columnNames.add(cn);
    }
	
	public List entries;

	public List getEntries() {
		return entries;
	}

	public void setEntries(List entries) {
		this.entries = entries;
	}

	public DirectoryIterator() {
		entries = Collections.synchronizedList(new ArrayList());
		currentRow = 0;
		RecordCount = entries.size();
	}

	public void reset() {
		currentRow = 0;
	}

	public boolean hasNext() {
		if (currentRow <= RecordCount)
			return true;
		else
			return false;
	}

	public Object next() {
		if (currentRow <= RecordCount) {
			Object returnable[] = new Object[7];
			EntryStruct entry = (EntryStruct) entries.get(currentRow);
			returnable[0] = entry.getName();
			returnable[1] = entry.getDirectory();
			returnable[2] = entry.getSize();
			returnable[3] = entry.getType();
			returnable[4] = entry.getDateLastModified();
			returnable[5] = entry.getAttributes();
			returnable[6] = entry.getMode();
			return returnable;
		} else
			return null;
	}

	public void remove() {
		entries.remove(currentRow);
	}

	public int getRecordcount() {
		return RecordCount;
	}

	public int getCurrentrow() {
		return currentRow;
	}

	public String getColumnlist() {
		return columnList;
	}

	public Vector<String> getColumnnames() {
		return columnNames;
	}
}