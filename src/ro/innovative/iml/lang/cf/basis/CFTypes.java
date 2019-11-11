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

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFTypes {

    public static Boolean toBoolean(Object o) throws IllegalArgumentException {
	if (o instanceof Boolean)
	    return (Boolean) o;
	else if (o instanceof Integer)
	    return ((Integer) o).intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
	else if (o instanceof Long)
	    return ((Long) o).longValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
	else if (o instanceof Double)
	    return ((Double) o).doubleValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
	else if (o instanceof String) {
	    String s = (String) o;
	    if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes"))
		return Boolean.TRUE;
	    else if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("no"))
		return Boolean.FALSE;
	    else {
		try {
		    int i = Integer.parseInt(s);
		    return i == 0 ? Boolean.FALSE : Boolean.TRUE;
		} catch (NumberFormatException e) {
		    try {
			double d = Double.parseDouble(s);
			return d == 0 ? Boolean.FALSE : Boolean.TRUE;
		    } catch (NumberFormatException ee) {
			throw new IllegalArgumentException("Value could not be converted to boolean (" + o.toString() + ')');
		    }
		}
	    }
	} else
	    throw new IllegalArgumentException("Value could not be converted to boolean (" + o.toString() + ')');
    }

    public static Long toInteger(Object o) throws IllegalArgumentException {
	if (o instanceof Boolean)
	    return (Boolean) o ? 1L : 0L;
	else if (o instanceof Integer)
	    return (long) ((Integer) o).intValue();
	else if (o instanceof Long)
	    return (Long) o;
	else if (o instanceof Double)
	    return ((Double) o).longValue();
	else if (o instanceof String) {
	    String s = (String) o;
	    if (s.trim().equalsIgnoreCase("true") || s.trim().equalsIgnoreCase("yes"))
		return 1L;
	    else if (s.trim().equalsIgnoreCase("false") || s.trim().equalsIgnoreCase("no"))
		return 0L;
	    else {
		try {
		    long i = Long.parseLong(s);
		    return i;
		} catch (NumberFormatException e) {
		    try {
			double d = Double.parseDouble(s);
			return (long) d;
		    } catch (NumberFormatException ee) {
			throw new IllegalArgumentException("Value could not be converted to integer");
		    }
		}
	    }
	} else
	    throw new IllegalArgumentException("Value could not be converted to integer");
    }

    public static Double toDouble(Object o) throws IllegalArgumentException {
	if (o instanceof Boolean)
	    return (toBoolean(o) == true) ? 1.0 : 0.0;
	else if (o instanceof Integer)
	    return ((Integer) o).doubleValue();
	else if (o instanceof Long)
	    return ((Long) o).doubleValue();
	else if (o instanceof Double)
	    return ((Double) o).doubleValue();
	else if (o instanceof String) {
	    String s = (String) o;
	    if (s.trim().equalsIgnoreCase("true") || s.trim().equalsIgnoreCase("yes"))
		return 1.0;
	    else if (s.trim().equalsIgnoreCase("false") || s.trim().equalsIgnoreCase("no"))
		return 0.0;
	    else {
		double a;
		try {
		    // check if boolean
		    a = Double.parseDouble(o.toString().trim());
		} catch (IllegalArgumentException e) {
		    throw new IllegalArgumentException("Value could not be converted to double (has type "
			    + o.getClass().getName() + ":" + o.toString() + ")");
		}
		return a;
	    }
	}
	throw new IllegalArgumentException("Value could not be converted to double (has type " + o.getClass().getName() + ")");
    }

    public static String toString(Object o) throws IllegalArgumentException {
	return o.toString();
    }

    // Transforms a given Object to a double (CF representation of Date)
    public static Double toDate(Object obj) throws IllegalArgumentException {
	if (obj instanceof Integer)
	    return ((Integer) obj).doubleValue();
	else if (obj instanceof Double)
	    return (Double) obj;
	else if (obj instanceof Boolean)
	    return (toBoolean(obj) == true) ? 1.0 : 0.0;
	else if (obj instanceof String) {
	    try {
		return Double.parseDouble((String) obj);
	    } catch (NumberFormatException e) {
		String original = obj.toString();
		String str = original;
		ArrayList<String> lista = new ArrayList<String>();
		str = str.concat(" ");
		String x = new String(" ");
		str = x.concat(str);
		Pattern pat;
		Matcher mat;
		int prima_poz;
		int ultima_poz;
		int pozc;
		String time;
		String init = "0:0:0";
		SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date timeobj = sdf.parse(init, new ParsePosition(0));
		Date dateobj = sdf.parse(init, new ParsePosition(0));
		// looking for time form in String hh:mm:ss with possible time
		// marker
		if (Pattern
			.matches(
				"(.*)([^0-9:])((((0){0,1})[0-9])|(1[0-9])|(2[0-3])):((([0-5]){0,1})[0-9]):((([0-5]){0,1})[0-9])([^0-9:])(.*)",
				str)) {
		    pat = Pattern.compile("((((0){0,1})[0-9])|(1[0-9])|(2[0-3])):((([0-5]){0,1})[0-9]):((([0-5]){0,1})[0-9])");
		    mat = pat.matcher(str);
		    // if time format found
		    if (mat.find()) {
			prima_poz = mat.start();
			ultima_poz = mat.end();
			time = str.substring(prima_poz, ultima_poz);
			pozc = ultima_poz;
			while ((!Character.isLetterOrDigit(str.charAt(pozc))) && (pozc != str.length() - 1))
			    pozc++;
			ultima_poz = pozc;
			// looking for time marker A,a,P,p,AM,am,Am,aM,PM,pm,Pm
			// or pM
			String time_marker = "";
			if (pozc != str.length() - 1)
			    if ((str.charAt(pozc) == 'a') || (str.charAt(pozc) == 'A') || (str.charAt(pozc) == 'p')
				    || (str.charAt(pozc) == 'P')) {
				time_marker = time_marker.concat((String.valueOf(str.charAt(pozc))).toUpperCase());
				pozc++;
				if ((pozc != str.length() - 1) && ((str.charAt(pozc) == 'm') || (str.charAt(pozc) == 'M')))
				    pozc++;
				if ((pozc != str.length() - 1) && (Character.isLetterOrDigit(str.charAt(pozc)))) {
				    pozc = ultima_poz;
				    time_marker = "";
				}
			    }
			// substraction of found data from string
			str = str.substring(0, prima_poz) + str.substring(pozc, str.length());
			// creating time
			String t[] = time.split(":");
			time = "";
			int i1 = Integer.parseInt(t[0]);
			if (!time_marker.equals((String) ""))
			    if ((time_marker.equals((String) "P") && (i1 < 12)))
				i1 += 12;
			time = time.concat(String.valueOf(i1));
			time = time.concat(":");
			i1 = Integer.parseInt(t[1]);
			time = time.concat(String.valueOf(i1));
			time = time.concat(":");
			i1 = Integer.parseInt(t[2]);
			time = time.concat(String.valueOf(i1));
			SimpleDateFormat sd = new SimpleDateFormat("H:m:s");
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			timeobj = sd.parse(time, new ParsePosition(0));
		    }
		} else if (Pattern.matches(
			"(.*)([^0-9:])((((0){0,1})[0-9])|(1[0-9])|(2[0-3])):((([0-5]){0,1})[0-9])([^0-9:])(.*)", str)) {
		    // looking for time format hh:mm with possible time marker.
		    pat = Pattern.compile("((((0){0,1})[0-9])|(1[0-9])|(2[0-3])):((([0-5]){0,1})[0-9])");
		    mat = pat.matcher(str);
		    if (mat.find()) {
			prima_poz = mat.start();
			ultima_poz = mat.end();
			time = str.substring(prima_poz, ultima_poz);
			// looking for time marker A,a,P,p,AM,am,Am,aM,PM,pm,Pm
			// or pM
			pozc = ultima_poz;
			while ((!Character.isLetterOrDigit(str.charAt(pozc))) && (pozc != str.length() - 1))
			    pozc++;
			ultima_poz = pozc;
			String time_marker = "";
			if (pozc != str.length() - 1)
			    if ((str.charAt(pozc) == 'a') || (str.charAt(pozc) == 'A') || (str.charAt(pozc) == 'p')
				    || (str.charAt(pozc) == 'P')) {
				time_marker = time_marker.concat((String.valueOf(str.charAt(pozc))).toUpperCase());
				pozc++;
				if ((pozc != str.length() - 1) && ((str.charAt(pozc) == 'm') || (str.charAt(pozc) == 'M')))
				    pozc++;
				if ((pozc != str.length() - 1) && (Character.isLetterOrDigit(str.charAt(pozc)))) {
				    pozc = ultima_poz;
				    time_marker = "";
				}
			    }
			// substraction of found data from string
			str = str.substring(0, prima_poz) + str.substring(pozc, str.length());
			// creating time
			String t[] = time.split(":");
			time = "";
			int i1 = Integer.parseInt(t[0]);
			if (!time_marker.equals((String) ""))
			    if ((time_marker.equals((String) "P")) && (i1 < 12))
				i1 += 12;
			time = time.concat(String.valueOf(i1));
			time = time.concat(":");
			i1 = Integer.parseInt(t[1]);
			time = time.concat(String.valueOf(i1));
			SimpleDateFormat sd = new SimpleDateFormat("H:m");
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			timeobj = sd.parse(time, new ParsePosition(0));
		    }
		}
		// if there are any more valid characters (letter or digit) in
		// the string, split them into an ArrayList
		String temp = new String("");
		int i = 0;
		while (i < str.length() - 1) {
		    temp = "";
		    while ((Character.isLetter(str.charAt(i))) && (i < str.length() - 1)) {
			temp = temp.concat(String.valueOf(str.charAt(i)));
			i++;
		    }
		    if (!temp.equals((String) ""))
			lista.add(temp);
		    temp = "";
		    while ((Character.isDigit(str.charAt(i))) && (i < str.length() - 1)) {
			temp = temp.concat(String.valueOf(str.charAt(i)));
			i++;
		    }
		    if (!temp.equals((String) ""))
			lista.add(temp);
		    while ((!Character.isLetterOrDigit(str.charAt(i))) && (i < str.length() - 1))
			i++;
		}
		// try to create a date out of the ArrayList
		/*
		 * valid date formats yy mm dd yy mmm dd yy mmmm dd yyyy mm dd
		 * yyyy mmm dd yyyy mmmm dd mm dd yy mmm dd yy mmmm dd yy mm dd
		 * yyyy mmm dd yyyy mmmm dd yyyy the Space " " character can be
		 * changed with any non character/digit character/string besides
		 * ":".
		 */
		boolean isDate = true;
		int day = 0;
		int month = 0;
		int year = 0;
		i = 0;
		temp = "";
		if (lista.size() >= 3) {
		    Iterator<String> it = lista.iterator();
		    while (it.hasNext()) {
			temp = (String) it.next();
			if (Character.isLetter(temp.charAt(0)) && (month == 0)) {
			    if ((temp.equalsIgnoreCase("January")) || (temp.equalsIgnoreCase("Jan")))
				month = 1;
			    else if ((temp.equalsIgnoreCase("February")) || (temp.equalsIgnoreCase("Feb")))
				month = 2;
			    else if ((temp.equalsIgnoreCase("March")) || (temp.equalsIgnoreCase("Mar")))
				month = 3;
			    else if ((temp.equalsIgnoreCase("April")) || (temp.equalsIgnoreCase("Apr")))
				month = 4;
			    else if ((temp.equalsIgnoreCase("May")) || (temp.equalsIgnoreCase("May")))
				month = 5;
			    else if ((temp.equalsIgnoreCase("June")) || (temp.equalsIgnoreCase("Jun")))
				month = 6;
			    else if ((temp.equalsIgnoreCase("July")) || (temp.equalsIgnoreCase("Jul")))
				month = 7;
			    else if ((temp.equalsIgnoreCase("August")) || (temp.equalsIgnoreCase("Aug")))
				month = 8;
			    else if ((temp.equalsIgnoreCase("September")) || (temp.equalsIgnoreCase("Sep")))
				month = 9;
			    else if ((temp.equalsIgnoreCase("October")) || (temp.equalsIgnoreCase("Oct")))
				month = 10;
			    else if ((temp.equalsIgnoreCase("November")) || (temp.equalsIgnoreCase("Nov")))
				month = 11;
			    else if ((temp.equalsIgnoreCase("December")) || (temp.equalsIgnoreCase("Dec")))
				month = 12;
			    if (month != 0)
				it.remove();
			} else if ((temp.length() > 2) && (Character.isDigit(temp.charAt(0))) && (year == 0)) {
			    year = Integer.parseInt(temp);
			    if (String.valueOf(year).length() <= 2)
				year = 0;
			    else
				it.remove();
			} else if ((i == 0) && (temp.length() <= 2) && (Character.isDigit(temp.charAt(0)))) {
			    month = Integer.parseInt(temp);
			    if ((month > 12) || (month == 0)) {
				if (year == 0) {
				    if (month <= 29)
					year = 2000 + month;
				    else
					year = 1900 + month;
				    it.remove();
				}
				month = 0;
			    } else
				it.remove();
			} else if ((i == 1) && (month == 0) && (Character.isDigit(temp.charAt(0))) && (temp.length() <= 2)) {
			    month = Integer.parseInt(temp);
			    if ((month > 12) || (month == 0))
				month = 0;
			    else
				it.remove();
			} else if (((i == 1) || (i == 2)) && (month != 0) && (Character.isDigit(temp.charAt(0))) && (day == 0)) {
			    day = Integer.parseInt(temp);
			    if (((day > 29) && (month == 2))
				    || ((day > 28) && (month == 2) && (year % 4 != 0))
				    || ((day > 30) && ((month == 4) || (month == 6) || (month == 9) || (month == 11)))
				    || ((day > 31) && ((month == 1) || (month == 3) || (month == 5) || (month == 7)
					    || (month == 8) || (month == 10) || (month == 12))))
				day = 0;
			    else
				it.remove();
			} else if ((i == 2) && (day != 0) && (year == 0) && (Character.isDigit(temp.charAt(0)))
				&& (temp.length() >= 2)) {
			    year = Integer.parseInt(temp);
			    if ((year >= 100) && (year <= 9999))
				lista.remove(temp);
			    else if (temp.length() == 2) {
				if (year <= 29)
				    year += 2000;
				else
				    year += 1900;
				it.remove();
			    } else
				year = 0;
			}
			i++;
		    }
		}
		// creating date if there was any found
		if ((day != 0) && (month != 0) && (year != 0)) {
		    time = " ";
		    time = time.concat(String.valueOf(day));
		    time = time.concat(" ");
		    time = time.concat(String.valueOf(month));
		    time = time.concat(" ");
		    time = time.concat(String.valueOf(year));
		    SimpleDateFormat sd = new SimpleDateFormat(" d M yyyy");
		    sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		    dateobj = sd.parse(time, new ParsePosition(0));
		}
		str = " ";
		// if there is still something in the list and no timeobj was
		// found by now, look for time in format hh with optional time
		// marker
		if (!lista.isEmpty()) {
		    Iterator<String> it2 = lista.iterator();
		    while (it2.hasNext()) {
			str = str.concat(it2.next().toString());
			str = str.concat(" ");
		    }
		}
		if ((!str.equals((String) " ")) && (timeobj.equals(new Date(0)))) {
		    if (Pattern.matches("(.*)([^0-9:])((((0){0,1})[0-9])|(1[0-9])|(2[0-3]))([^0-9:])(.*)", str)) {
			// looking for time format hh with possible time marker.
			pat = Pattern.compile("([^0-9:])((2[0-3])|(1[0-9])|(((0){0,1})[0-9]))([^0-9:])");
			mat = pat.matcher(str);
			if (mat.find()) {
			    prima_poz = mat.start() + 1;
			    ultima_poz = mat.end() - 1;
			    time = str.substring(prima_poz, ultima_poz);
			    // looking for time marker
			    // A,a,P,p,AM,am,Am,aM,PM,pm,Pm or pM
			    pozc = ultima_poz;
			    while ((!Character.isLetterOrDigit(str.charAt(pozc))) && (pozc != str.length() - 1))
				pozc++;
			    ultima_poz = pozc;
			    String time_marker = "";
			    if (pozc != str.length() - 1)
				if ((str.charAt(pozc) == 'a') || (str.charAt(pozc) == 'A') || (str.charAt(pozc) == 'p')
					|| (str.charAt(pozc) == 'P')) {
				    time_marker = time_marker.concat((String.valueOf(str.charAt(pozc))).toUpperCase());
				    pozc++;
				    if ((pozc != str.length() - 1) && ((str.charAt(pozc) == 'm') || (str.charAt(pozc) == 'M')))
					pozc++;
				    if ((pozc != str.length() - 1) && (Character.isLetterOrDigit(str.charAt(pozc)))) {
					pozc = ultima_poz;
					time_marker = "";
				    }
				}
			    // substraction of found data from string
			    str = str.substring(0, prima_poz) + str.substring(pozc, str.length());
			    // creating time
			    int i1 = Integer.parseInt(time);
			    if (!time_marker.equals((String) ""))
				if ((time_marker.equals((String) "P")) && (i1 < 12))
				    i1 += 12;
			    time = "";
			    time = time.concat(String.valueOf(i1));
			    SimpleDateFormat sd = new SimpleDateFormat("H");
			    sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			    timeobj = sd.parse(time, new ParsePosition(0));
			}
		    }
		}
		i = 0;
		if (!str.equals(null))
		    while (i < str.length() - 1) {
			if (Character.isLetterOrDigit(str.charAt(i)))
			    isDate = false;
			i++;
		    }
		if (isDate)
		    if ((dateobj.equals(new Date(0))) && (timeobj.equals(new Date(0))))
			isDate = false;
		// if there is a valid time and/or date object and the string
		// has no more valid characters in it, put date and time
		// together
		if (isDate) {
		    SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy ");
		    data.setTimeZone(TimeZone.getTimeZone("GMT"));
		    String date_string = new String("");
		    if (dateobj.equals(new Date(0))) {
			date_string = "30-12-1899 ";
		    } else
			date_string = data.format(dateobj, new StringBuffer(""), new FieldPosition(0)).toString();
		    SimpleDateFormat timp = new SimpleDateFormat("HH:mm:ss");
		    timp.setTimeZone(TimeZone.getTimeZone("GMT"));
		    date_string = date_string.concat(timp.format(timeobj, new StringBuffer(""), new FieldPosition(0))
			    .toString());
		    Date date = new Date(0);
		    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		    format.setTimeZone(TimeZone.getTimeZone("GMT"));
		    date = format.parse(date_string, new ParsePosition(0));
		    Double doubleToReturn = new Double(dateToDouble(new Long(date.getTime())).doubleValue() + 25569.0);
		    return doubleToReturn;
		} else
		    throw new IllegalArgumentException("Value '" + original + "' could not be converted to Date");
	    }

	} else
	    throw new IllegalArgumentException("Value '" + obj.toString() + "' could not be converted to Date");
    }

    public static Boolean toStrictBoolean(Object o) {
	if (o instanceof Boolean)
	    return (Boolean) o;
	else if (o instanceof Integer)
	    if ((Integer) o == 0)
		return false;
	    else if ((Integer) o == 1)
		return true;
	    else
		return null;
	else if (o instanceof Long)
	    if ((Long) o == 0)
		return false;
	    else if ((Long) o == 1)
		return true;
	    else
		return null;
	else if (o instanceof Double)
	    if ((Double) o == 0.0)
		return false;
	    else if ((Double) o == 1.0)
		return true;
	    else
		return null;
	else if (o instanceof String)
	    if (o.toString().equalsIgnoreCase("true") || o.toString().equalsIgnoreCase("yes"))
		return true;
	    else if (o.toString().equalsIgnoreCase("false") || o.toString().equalsIgnoreCase("no"))
		return false;
	    else {
		try {
		    double x = Double.parseDouble(o.toString());

		    if (x == 1)
			return true;
		    else if (x == 0)
			return false;
		    else
			return null;
		} catch (NumberFormatException e) {
		    return null;
		}
	    }
	else
	    return null;
    }

    public static Integer toStrictInteger(Object o) {
	if (o instanceof Integer)
	    return (Integer) o;
	if (o instanceof Long)
	    return ((Long) o).intValue();
	else if (o instanceof String)
	    try {
		return Integer.parseInt((String) o);
	    } catch (NumberFormatException e) {
		return null;
	    }
	else
	    return null;
    }

    public static Double toStrictDouble(Object o) {
	if (o instanceof Double)
	    return (Double) o;
	else if (o instanceof String)
	    try {
		return Double.parseDouble((String) o);
	    } catch (NumberFormatException e) {
		return null;
	    }
	else
	    return null;
    }

    // Transforms a java Time value(long) to the a CF date format(double)
    // long value 0==25569.0 in CF. this is not considered here.
    private static Double dateToDouble(Long time) {
	double d = 0.0;
	boolean b = true;
	if (time < 0) {
	    b = false;
	    time = Math.abs(time);
	}
	double dtime = ((Long) time).doubleValue();
	d += (dtime % 1000.0) / (24.0 * 60.0 * 60.0 * 1000.0);
	dtime = Math.floor(dtime / 1000.0);
	d += (dtime % 60.0) / (24.0 * 60.0 * 60.0);
	dtime = Math.floor(dtime / 60.0);
	d += (dtime % 60.0) / (24.0 * 60.0);
	dtime = Math.floor(dtime / 60.0);
	d += (dtime % 24.0) / (24.0);
	dtime = Math.floor(dtime / 24.0);
	d += dtime;
	if (!b)
	    d = d * (-1.0);
	// d+=25569.0;
	Double dD = new Double(d);
	return dD;
    }

}
