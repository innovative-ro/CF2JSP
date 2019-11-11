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

import ro.innovative.iml.lang.cf.util.*;
import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import java.util.regex.Pattern;

public class CFDirectory extends TagSupport {
    
    private static final long serialVersionUID = 6083306217525736878L;
    
    private DirectoryIterator directoryIterator = null;
    private Comparator comp = null;
    private String[][] sortRule = new String[7][2];
    private String action = "List";
    private String directory = null;
    private String name = null;
    private String filter = null;
    private String mode = null;
    private String sort = null;
    private String newdirectory = null;
    private String recurse = "no";

    public int doStartTag() throws JspException {
	sortRule = retreiveSortRule(sort);
	comp = new Comparator<EntryStruct>() {

	    public int compare(EntryStruct o1, EntryStruct o2) {
		int rez = 0;

		EntryStruct ob1 = (EntryStruct) o1;
		EntryStruct ob2 = (EntryStruct) o2;

		for (int i = 0; i < sortRule.length; i++) {
		    if (sortRule[i][0] == null) {
			i = 10;
		    } else if (sortRule[i][0].equalsIgnoreCase("name")) {
			rez = ob1.getName().compareTo(ob2.getName());
		    } else if (sortRule[i][0].equalsIgnoreCase("directory")) {
			rez = ob1.getDirectory().compareTo(ob2.getDirectory());
		    } else if (sortRule[i][0].equalsIgnoreCase("size")) {
			if ((Long.parseLong(ob1.getSize()) > Long.parseLong(ob2.getSize()))) {
			    rez = 1;
			} else if ((Long.parseLong(ob1.getSize()) < Long.parseLong(ob2.getSize()))) {
			    rez = -1;
			} else
			    rez = 0;
		    } else if (sortRule[i][0].equalsIgnoreCase("type")) {
			rez = ob1.getType().compareTo(ob2.getType());
		    } else if (sortRule[i][0].equalsIgnoreCase("datelastmodified")) {
			rez = ob1.getDateLastModified().compareTo(ob2.getDateLastModified());
		    } else if (sortRule[i][0].equalsIgnoreCase("attributes")) {
			rez = ob1.getAttributes().compareTo(ob2.getAttributes());
		    } else if (sortRule[i][0].equalsIgnoreCase("mode")) {
			rez = ob1.getMode().compareTo(ob2.getMode());
		    }
		    if (rez != 0) {
			if (sortRule[i][1].equalsIgnoreCase("desc")) {
			    rez = -rez;
			}
			return rez;
		    }
		}
		return 1;
	    }
	};

	if (action.toLowerCase().equals("create")) {
	    (new File(directory)).mkdir();
	} else if (action.toLowerCase().equals("delete")) {
	    File toDel = new File(directory);
	    deleteDirectory(toDel);
	} else if (action.toLowerCase().equals("rename")) {
	    (new File(directory)).renameTo((new File(newdirectory)));
	} else {
	    if (recurse.toLowerCase().equals("no")) {
		directoryIterator = visitDirsAndFiles(new File(directory));
	    } else {
		directoryIterator = visitAllDirsAndFiles(new File(directory), new DirectoryIterator());
	    }
	    Collections.sort(directoryIterator.entries, comp);
	    pageContext.setAttribute(name, directoryIterator);
	}
	return SKIP_BODY;
    }

    public static boolean deleteDirectory(File dir) {
	if (dir.isDirectory()) {
	    String[] children = dir.list();
	    for (int i = 0; i < children.length; i++) {
		boolean success = deleteDirectory(new File(dir, children[i]));
		if (!success) {
		    return false;
		}
	    }
	}

	// The directory is now empty so delete it
	return dir.delete();
    }

    // Process (list) all files and directories under dir
    @SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
    public static DirectoryIterator visitAllDirsAndFiles(File dir, DirectoryIterator directoryIterator) {
	// DirectoryIterator DirectoryIterator = new DirectoryIterator();
	directoryIterator.entries.add(listFile(dir));

	if (dir.isDirectory()) {
	    String[] children = dir.list();
	    for (int i = 0; i < children.length; i++) {
		visitAllDirsAndFiles(new File(dir, children[i]), directoryIterator);
	    }
	}

	return directoryIterator;
    }

    // List directories and files under dir
    public static DirectoryIterator visitDirsAndFiles(File dir) {
	DirectoryIterator directoryIterator = new DirectoryIterator();
	String[] children = dir.list();
	if (children == null) {
	    // Either dir does not exist or is not a directory
	} else {
	    for (int i = 0; i < children.length; i++) {
		// Get filename of file or directory
		File filename = new File(children[i]);
		directoryIterator.entries.add(listFile(filename));
	    }
	}
	return directoryIterator;
    }

    // list infos about a file/dir
    public static EntryStruct listFile(File fileOrDir) {
	EntryStruct entry = new EntryStruct();
	entry.attributes = "[empty list]";
	entry.dateLastModified = new Date(fileOrDir.lastModified()).toString();
	entry.directory = fileOrDir.getParent();
	entry.mode = "[empty string]";
	entry.name = fileOrDir.getName();
	entry.size = new Long(fileOrDir.length()).toString();
	if (fileOrDir.isDirectory()) {
	    entry.type = "Dir";
	} else {
	    entry.type = "File";
	}
	return entry;
    }

    public static String[][] retreiveSortRule(String toSort) {
	String[][] rules = new String[7][2];
	if (toSort == null) {
	    rules[0][0] = "name";
	    rules[1][0] = "directory";
	    rules[2][0] = "size";
	    rules[3][0] = "type";
	    rules[4][0] = "datelastmodified";
	    rules[5][0] = "attributes";
	    rules[6][0] = "mode";
	    for (int k = 0; k < 7; k++)
		rules[k][1] = "asc";
	    return rules;
	}
	if (toSort.trim().equalsIgnoreCase("asc") || toSort.trim().equalsIgnoreCase("desc")) {
	    rules[0][0] = "name";
	    rules[1][0] = "directory";
	    rules[2][0] = "size";
	    rules[3][0] = "type";
	    rules[4][0] = "datelastmodified";
	    rules[5][0] = "attributes";
	    rules[6][0] = "mode";
	    for (int k = 0; k < 7; k++)
		rules[k][1] = toSort.trim();
	} else// if (toSort!=null)
	{
	    int k = 0;
	    String[] rulePartsAux = toSort.split(",");
	    for (int i = 0; i < rulePartsAux.length; i++) {
		String rule[] = rulePartsAux[i].trim().split(Pattern.compile("[\\s]+").pattern());
		rules[k][0] = rule[0].trim();
		if ((rule.length > 1) && ("desc".equalsIgnoreCase(rule[1].trim()))) {
		    rules[k][1] = "desc";
		} else {
		    rules[k][1] = "asc";
		}
		k++;
	    }

	}

	return rules;
    }

    public int doEndTag() {
	return EVAL_PAGE;
    }
    
    public void setAction(String arg) {
	this.action = arg;
    }

    public String getAction() {
	return this.action;
    }

    public void setDirectory(String arg) {
	this.directory = arg;
    }

    public String getDirectory() {
	return this.directory;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setFilter(String arg) {
	this.filter = arg;
    }

    public String getFilter() {
	return this.filter;
    }

    public void setMode(String arg) {
	this.mode = arg;
    }

    public String getMode() {
	return this.mode;
    }

    public void setSort(String arg) {
	this.sort = arg;
    }

    public String getSort() {
	return this.sort;
    }

    public void setNewdirectory(String arg) {
	this.newdirectory = arg;
    }

    public String getNewdirectory() {
	return this.newdirectory;
    }

    public void setRecurse(String arg) {
	this.recurse = arg;
    }

    public String getRecurse() {
	return this.recurse;
    }

}
