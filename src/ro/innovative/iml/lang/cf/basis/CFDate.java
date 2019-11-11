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

import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFDate {

//	Transforms a java date (represented by a long) to a double format(intern representation of CF date/time objects)
	private static Double dateToDouble(Long time){
		double d=0.0;
		boolean b=true;
		if (time<0){
			b=false;
			time=Math.abs(time);
		}
		double dtime=((Long)time).doubleValue();
		d+=(dtime%1000.0)/(24.0*60.0*60.0*1000.0);
		dtime=Math.floor(dtime/1000.0);
		d+=(dtime%60.0)/(24.0*60.0*60.0);
		dtime=Math.floor(dtime/60.0);
		d+=(dtime%60.0)/(24.0*60.0);
		dtime=Math.floor(dtime/60.0);
		d+=(dtime%24.0)/(24.0);
		dtime=Math.floor(dtime/24.0);
		d+=dtime;
		if (!b) d=d*(-1.0);
		Double dD=new Double(d);
		return dD;
	} 

//	Transforms a double date format(intern representation of CF date/time objects) to a java date (represented by a long) 	
	private static Long doubleToDate(Double time){
		long l=0;
		boolean b=true;
		double dtime=time.doubleValue();
		dtime-=25569.0;
		if (dtime<0){
			b=false;
			dtime=Math.abs(dtime);
		}
		l+=Math.floor(dtime)*24*60*60*1000;
		dtime=dtime-Math.floor(dtime);
		dtime*=24.0;
		l+=Math.floor(dtime)*60*60*1000;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		l+=Math.floor(dtime)*60*1000;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		l+=Math.floor(dtime)*1000;
		dtime=dtime-Math.floor(dtime);
		dtime*=1000;
		l+=Math.floor(dtime);
		if (dtime-Math.floor(dtime)>0.9) l++;
		if (!b) l*=-1;
		return new Long(l);
	}
	
//  Creates a CF like representation of date(Double) using as argumenst the year, month and day	
	public static Double createDate(Object years,Object months, Object days){
		Long year=CFTypes.toInteger(years);
		Long month=CFTypes.toInteger(months);
		Long day=CFTypes.toInteger(days);
		if ((year.intValue()>=30) && (year.intValue()<=99)) year+=1900;
		else if ((year.intValue()>=0) && (year.intValue()<=29)) year+=2000;
		else if ((year.intValue()<0) || (year.intValue()>9999)) throw new IllegalArgumentException("function createDate: first parameter is not a valid year");
		if ((month.intValue()<=0) || (month.intValue()>12)) throw new IllegalArgumentException("function createDate: second parameter is not a valid month");
		if ((day.intValue()<=0) || (day.intValue()>31)) throw new IllegalArgumentException("function createDate: third parameter is not a valid day");
		SimpleDateFormat sd=new SimpleDateFormat(" d M yyyy");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date_string=new String(" ");
		date_string=date_string.concat(String.valueOf(day));
		date_string=date_string.concat(" ");
		date_string=date_string.concat(String.valueOf(month));
		date_string=date_string.concat(" ");
		date_string=date_string.concat(String.valueOf(year));
		Date dateobj=new Date(0);
		dateobj=sd.parse(date_string, new ParsePosition(0));
		Double doubleToReturn=new Double(dateToDouble(new Long(dateobj.getTime())).doubleValue()+25569.0);
		return doubleToReturn;
	}
	
//  Creates a CF like representation of date(Double) using as argumenst the year, month, day, hour, minute, second		
	public static Double createDateTime(Object years,Object months, Object days, Object hours, Object minutes, Object seconds){
		Long year=CFTypes.toInteger(years);
		Long month=CFTypes.toInteger(months);
		Long day=CFTypes.toInteger(days);
		Long hour=CFTypes.toInteger(hours);
		Long minute=CFTypes.toInteger(minutes);
		Long second=CFTypes.toInteger(seconds);
		if ((year.intValue()>=30) && (year.intValue()<=99)) year+=1900;
		else if ((year.intValue()>=0) && (year.intValue()<=29)) year+=2000;
		else if ((year.intValue()<0) || (year.intValue()>9999)) throw new IllegalArgumentException("function createDateTime: first parameter is not a valid year");
		if ((month.intValue()<=0) || (month.intValue()>12)) throw new IllegalArgumentException("function createDateTime: second parameter is not a valid month");
		if ((day.intValue()<=0) || (day.intValue()>31)) throw new IllegalArgumentException("function createDateTime: third parameter is not a valid day");
		if ((hour.intValue()<0) || (hour.intValue()>23)) throw new IllegalArgumentException("function createDateTime: fourth parameter is not a valid hour");
		if ((minute.intValue()<0) || (minute.intValue()>59)) throw new IllegalArgumentException("function createDateTime: fifth parameter is not a valid minute");
		if ((second.intValue()<0) || (second.intValue()>59)) throw new IllegalArgumentException("function createDateTime: sixth parameter is not a valid second");
		SimpleDateFormat sd=new SimpleDateFormat(" d M yyyy H:m:s");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date_string=new String(" ");
		date_string=date_string.concat(String.valueOf(day));
		date_string=date_string.concat(" ");
		date_string=date_string.concat(String.valueOf(month));
		date_string=date_string.concat(" ");
		date_string=date_string.concat(String.valueOf(year));
		date_string=date_string.concat(" ");
		date_string=date_string.concat(String.valueOf(hour));
		date_string=date_string.concat(":");
		date_string=date_string.concat(String.valueOf(minute));
		date_string=date_string.concat(":");
		date_string=date_string.concat(String.valueOf(second));
		Date dateobj=new Date(0);
		dateobj=sd.parse(date_string, new ParsePosition(0));
		Double doubleToReturn=new Double(dateToDouble(new Long(dateobj.getTime())).doubleValue()+25569.0);
		return doubleToReturn;
	}

//  Formats a CF dateobj to a String representation	that matches the ODBC date format	
	public static String createODBCDate(Object date){
		Double datedouble=CFTypes.toDate(date);
		Long datelong=doubleToDate(datedouble);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date datac=new Date(datelong);
		StringBuffer sb=sdf.format(datac, new StringBuffer(""), new FieldPosition(0));
		String returnat=new String("");
		returnat=returnat.concat("{d '");
		returnat=returnat.concat(new String(sb));
		returnat=returnat.concat("'}");
		return returnat;
	}

//  Formats a CF dateobj to a String representation	that matches the ODBC date-time format
	public static String createODBCDateTime(Object date){
		Double datedouble=CFTypes.toDate(date);
		Long datelong=doubleToDate(datedouble);
		Date datac=new Date(datelong);
		SimpleDateFormat sdf;
		if (datelong%1000!=0)
			sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");		
		else sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		StringBuffer sb=sdf.format(datac, new StringBuffer(""), new FieldPosition(0));
		String returnat=new String("");
		returnat=returnat.concat("{ts '");
		returnat=returnat.concat(new String(sb));
		returnat=returnat.concat("'}");
		return returnat;
	}
	
//  Formats a CF dateobj to a String representation	that matches the ODBC time format	
	public static String createODBCTime(Object date){
		Double datedouble=CFTypes.toDate(date);
		Long datelong=doubleToDate(datedouble);
		Date datac=new Date(datelong);
		SimpleDateFormat sdf;
		if (datelong%1000!=0)
			sdf=new SimpleDateFormat("HH:mm:ss.SSS");		
		else sdf=new SimpleDateFormat("HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		StringBuffer sb=sdf.format(datac, new StringBuffer(""), new FieldPosition(0));
		String returnat=new String("");
		returnat=returnat.concat("{t '");
		returnat=returnat.concat(new String(sb));
		returnat=returnat.concat("'}");
		return returnat;
	}

//  Creates a CF like representation of time(Double) using as argumenst the hour, minute and second		
	public static Double createTime(Object hours, Object minutes, Object seconds){
		Long hour=CFTypes.toInteger(hours);
		Long minute=CFTypes.toInteger(minutes);
		Long second=CFTypes.toInteger(seconds);
		if ((hour.intValue()<0) || (hour.intValue()>23)) throw new IllegalArgumentException("function createTime: first parameter is not a valid hour");
		if ((minute.intValue()<0) || (minute.intValue()>59)) throw new IllegalArgumentException("function createTime: second parameter is not a valid minute");
		if ((second.intValue()<0) || (second.intValue()>59)) throw new IllegalArgumentException("function createTime: third parameter is not a valid second");
		SimpleDateFormat sd=new SimpleDateFormat(" yyyy-MM-dd H:m:s");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date_string=new String(" 1899-12-30 ");
		date_string=date_string.concat(String.valueOf(hour));
		date_string=date_string.concat(":");
		date_string=date_string.concat(String.valueOf(minute));
		date_string=date_string.concat(":");
		date_string=date_string.concat(String.valueOf(second));
		Date dateobj=new Date(0);
		dateobj=sd.parse(date_string, new ParsePosition(0));
		Double doubleToReturn=new Double(dateToDouble(new Long(dateobj.getTime())).doubleValue()+25569.0);
		return doubleToReturn;
	}

//	creates a CF-like representation of time, a double value representing a period of time given by the 4 parameters:day, hour, minute, second	
	public static Double createTimeSpan(Object days, Object hours, Object minutes, Object seconds) {
		double d = 0.0;
		Long day=CFTypes.toInteger(days);
		Long hour=CFTypes.toInteger(hours);
		Long minute=CFTypes.toInteger(minutes);
		Long second=CFTypes.toInteger(seconds);
		d += day.doubleValue();
		d += (hour.doubleValue() * (1.0 / 24.0));
		d += (minute.doubleValue() * (1.0 / (24.0 * 60.0)));
		d += (second.doubleValue() * (1.0 / (24.0 * 3600.0)));
		return new Double(d);
	}

//  Adds units of time to a date represented in the CF date/time format	
	public static Double dateAdd(Object part, Object number, Object date){
		String parte=CFTypes.toString(part);
		Long numar=CFTypes.toInteger(number);
		Double datedouble=CFTypes.toDate(date);
		Long datelong=doubleToDate(datedouble);
		Date data=new Date(datelong);
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
//		changes the GregorianCalendar representing the date recieved as 3-rd parameter conforming to the other 2 parameters
//		adds up years to the Date		
		if (parte.equalsIgnoreCase("yyyy")){
			gc.set(GregorianCalendar.YEAR, gc.get(GregorianCalendar.YEAR)+numar.intValue());
		}
//		adds up quarters to the Date
		else if (parte.equalsIgnoreCase("q")){
			gc.set(GregorianCalendar.MONTH, gc.get(GregorianCalendar.MONTH)+3*numar.intValue());				
		}
//		adds up months to the Date
		else if (parte.equalsIgnoreCase("m")){
			gc.set(GregorianCalendar.MONTH, gc.get(GregorianCalendar.MONTH)+numar.intValue());
		}
//		adds up days of the year to the Date		
		else if (parte.equalsIgnoreCase("y")){
			gc.set(GregorianCalendar.DAY_OF_YEAR, gc.get(GregorianCalendar.DAY_OF_YEAR)+numar.intValue());
		}
//		adds up days to the Date		
		else if (parte.equalsIgnoreCase("d")){
			gc.set(GregorianCalendar.DAY_OF_MONTH, gc.get(GregorianCalendar.DAY_OF_MONTH)+numar.intValue());
		}
//		adds up weeksdays to the Date      		
		else if (parte.equalsIgnoreCase("w")){
			gc.set(GregorianCalendar.DAY_OF_WEEK, gc.get(GregorianCalendar.DAY_OF_WEEK)+numar.intValue());
		}
//		adds up weeks to the Date      		
		else if (parte.equalsIgnoreCase("ww")){
			gc.set(GregorianCalendar.WEEK_OF_YEAR, gc.get(GregorianCalendar.WEEK_OF_YEAR)+numar.intValue());
		}
//		adds up hours to the Date      		
		else if (parte.equalsIgnoreCase("h")){
			gc.set(GregorianCalendar.HOUR_OF_DAY, gc.get(GregorianCalendar.HOUR_OF_DAY)+numar.intValue());
		}
//		adds up minutes to the Date      		
		else if (parte.equalsIgnoreCase("n")){
			gc.set(GregorianCalendar.MINUTE, gc.get(GregorianCalendar.MINUTE)+numar.intValue());
		}
//		adds up seconds to the Date      		
		else if (parte.equalsIgnoreCase("s")){
			gc.set(GregorianCalendar.SECOND, gc.get(GregorianCalendar.SECOND)+numar.intValue());
		}
//		adds up milliseconds to the Date      		
		else if (parte.equalsIgnoreCase("l")){
			gc.set(GregorianCalendar.MILLISECOND, gc.get(GregorianCalendar.MILLISECOND)+numar.intValue());
		}
// 		if none matches the given string, throw exception
		else throw new IllegalArgumentException("function dateAdd: first parameter '"+parte+"' is not a valid argument");
		data=gc.getTime();
		return new Double(dateToDouble(data.getTime()).doubleValue()+25569.0);
	}

//	Function compares 2 dates returning:
	// -1 if date1 is later(bigger in value) than date2
	// 0 if date1 is equal to date2
	// 1 if date 1 is earlier than date2
	public static Integer dateCompare(Object date1, Object date2){
		Double dat1=CFTypes.toDate(date1);
		Double dat2=CFTypes.toDate(date2);
		Integer toreturn=new Integer(0);
		if (dat1.doubleValue()<dat2.doubleValue()) toreturn=1;
		else if (dat1.doubleValue()>dat2.doubleValue()) toreturn=-1;
		return toreturn;
	}
	
//	Function that compares 2 dates with the given precission
	//"yyyy" compares up to the year
	//"m" compares up to the month
	//"d" compares up to the day
	//"h" compares up to the hour
	//"n" compares up to the minute
	//"s" compares up to the second (same with dateCompare() with 2 parameters)
	public static Integer dateCompare(Object date1, Object date2, Object part){
		Long dat1=doubleToDate(CFTypes.toDate(date1));
		Long dat2=doubleToDate(CFTypes.toDate(date2));
		Date data1=new Date(dat1);
		Date data2=new Date(dat2);
		GregorianCalendar gc1=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc1.setTime(data1);
		GregorianCalendar gc2=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc2.setTime(data2);
		String precizie=CFTypes.toString(part);
		if (precizie.equalsIgnoreCase("s")){
			dat1=dat1.longValue()/1000;
			dat2=dat2.longValue()/1000;
		}
		else if (precizie.equalsIgnoreCase("n")){
			dat1=new Long(dat1.longValue()/(1000*60));
			dat2=new Long(dat2.longValue()/(1000*60));
		}
		else if (precizie.equalsIgnoreCase("h")){
			dat1=dat1.longValue()/(1000*60*60);
			dat2=dat2.longValue()/(1000*60*60);
		}
		else if (precizie.equalsIgnoreCase("d")){
			dat1=dat1.longValue()/(1000*60*60*24);
			dat2=dat2.longValue()/(1000*60*60*24);
		}
		else if (precizie.equalsIgnoreCase("m")){
			if (gc1.get(GregorianCalendar.YEAR)>gc2.get(GregorianCalendar.YEAR)){
				dat1=new Long(1);
				dat2=new Long(0);
			}else if (gc1.get(GregorianCalendar.YEAR)<gc2.get(GregorianCalendar.YEAR)){
				dat1=new Long(0);
				dat2=new Long(1);
			}else{
				if (gc1.get(GregorianCalendar.MONTH)>gc2.get(GregorianCalendar.MONTH)){
					dat1=new Long(1);
					dat2=new Long(0);
				}else if (gc1.get(GregorianCalendar.MONTH)<gc2.get(GregorianCalendar.MONTH)){
					dat1=new Long(0);
					dat2=new Long(1);
				}else {
					dat1=new Long(0);
					dat2=new Long(0);
				}
			}
		}
		else if (precizie.equalsIgnoreCase("yyyy")){
			if (gc1.get(GregorianCalendar.YEAR)>gc2.get(GregorianCalendar.YEAR)){
				dat1=new Long(1);
				dat2=new Long(0);
			}else if (gc1.get(GregorianCalendar.YEAR)<gc2.get(GregorianCalendar.YEAR)){
				dat1=new Long(0);
				dat2=new Long(1);
			}else{
				dat1=new Long(0);
				dat2=new Long(0);
			}
		}
		else throw new IllegalArgumentException("function dateCompare: third parameter '"+precizie+"' is not a valid argument");
		Integer toreturn=new Integer(0);
		if (dat1.longValue()<dat2.longValue()) toreturn=1;
		else if (dat1.longValue()>dat2.longValue()) toreturn=-1;
		return toreturn;
	}
	
//  Converts a CF date/time obj from gmt time to local time and the other way around
	public static Double dateConvert(Object type, Object date){
		String convert=CFTypes.toString(type);
		Double rez=CFTypes.toDate(date);
		Date data=new Date(doubleToDate(rez));
		String datasir=new String("");
		SimpleDateFormat sd=new SimpleDateFormat(" yyyy-MM-dd H:m:s");
		if (convert.equalsIgnoreCase("local2Utc")){
			sd.setTimeZone(TimeZone.getDefault());
			datasir=sd.format(data);
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			data=sd.parse(datasir,new ParsePosition(0));
		}else if (convert.equalsIgnoreCase("utc2Local")){
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			datasir=sd.format(data);
			sd.setTimeZone(TimeZone.getDefault());
			data=sd.parse(datasir,new ParsePosition(0));
		}else throw new IllegalArgumentException("function dateConvert: third parameter '"+convert+"' is not a valid argument");
		rez=dateToDouble(data.getTime())+25569;
		return rez;
		
	}
	
//	Returns the integer number of units by which date1 is less than date2
	public static Long dateDiff(Object datepart, Object date1, Object date2){
		Long datal1=doubleToDate(CFTypes.toDate(date1));
		Long datal2=doubleToDate(CFTypes.toDate(date2));
		String parte=CFTypes.toString(datepart);
		Date date=new Date(datal2.longValue()-datal1.longValue());
		Long datelong=date.getTime();
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(date);
		Long toreturn=new Long(0);
		if (parte.equalsIgnoreCase("yyyy")){
			toreturn=new Long(gc.get(GregorianCalendar.YEAR)-1970);
		}
		else if (parte.equalsIgnoreCase("q")){
			toreturn=new Long((gc.get(GregorianCalendar.YEAR)-1970)*4);
			toreturn+=new Long(gc.get(GregorianCalendar.MONTH)/3);				
		}
		else if (parte.equalsIgnoreCase("m")){
			toreturn=new Long((gc.get(GregorianCalendar.YEAR)-1970)*12);
			toreturn+=gc.get(GregorianCalendar.MONTH);				
		}
		else if ((parte.equalsIgnoreCase("y")) || (parte.equalsIgnoreCase("d"))){
			toreturn=(new Double(Math.floor(datelong/(1000*3600*24)))).longValue();
		}      		
		else if ((parte.equalsIgnoreCase("w")) || (parte.equalsIgnoreCase("ww"))){
			toreturn=(new Double(Math.floor(datelong/(1000*3600*24*7)))).longValue();
		}
		else if (parte.equalsIgnoreCase("h")){
			toreturn=(new Double(Math.floor(datelong/(1000*3600)))).longValue();
		}  		
		else if (parte.equalsIgnoreCase("n")){
			toreturn=(new Double(Math.floor(datelong/(1000*60)))).longValue();
		}
		else if (parte.equalsIgnoreCase("s")){
			toreturn=(new Double(Math.floor(datelong/1000))).longValue();
		}      		
		else throw new IllegalArgumentException("function dateDiff: first parameter '"+parte+"' is not a valid argument");
		return toreturn;	
	}

//  Returns a string representation (dd-MMM-yy) of the date given as parameter	
	public static String dateFormat(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yy");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		String toreturn=sd.format(data);
		return toreturn;
	}

//  Returns a custom string representation of the date given as parameter. Second parameter is the mask for the formating	
	public static String dateFormat(Object date, Object mask){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		String masca=CFTypes.toString(mask);
		String forma=new String("");
		if (masca.equalsIgnoreCase("short")){
			forma="M/d/y";
		}
		else if (masca.equalsIgnoreCase("medium")){
			forma="MMM d, yyyy";
		}
		else if (masca.equalsIgnoreCase("long")){
			forma="MMMM d, yyyy";
		}
		else if (masca.equalsIgnoreCase("full")){
			forma="EEEE, MMMM d, yyyy";
		}
		else{
			Pattern pat;
			Matcher mat;
			int prima_poz;
			int ultima_poz;
			String temp;
			while (Pattern.matches("(.*)(dddd)(.*)",masca)){
				pat=Pattern.compile("(dddd)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(ddd)(.*)",masca)){
				pat=Pattern.compile("(ddd)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(DDDD)(.*)",masca)){
				pat=Pattern.compile("(DDDD)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(DDD)(.*)",masca)){
				pat=Pattern.compile("(DDD)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(DD)(.*)",masca)){
				pat=Pattern.compile("(DD)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("dd");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(D)(.*)",masca)){
				pat=Pattern.compile("(D)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("d");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(mmmm)(.*)",masca)){
				pat=Pattern.compile("(mmmm)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat((masca.substring(prima_poz,ultima_poz)).toUpperCase());
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(mmm)(.*)",masca)){
				pat=Pattern.compile("(mmm)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("MMM");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(mm)(.*)",masca)){
				pat=Pattern.compile("(mm)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("MM");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(m)(.*)",masca)){
				pat=Pattern.compile("(m)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("M");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(gg)(.*)",masca)){
				pat=Pattern.compile("(gg)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("GG");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			forma=masca;
		}
		String toreturn;
		try{
			SimpleDateFormat sd=new SimpleDateFormat(forma);
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			toreturn=sd.format(data);
		}catch (Exception e){
			throw new IllegalArgumentException("function dateFormat: second parameter '"+masca+"' is not a valid argument");
		}
		return toreturn;
	}
	
//  Returns a part of a date, as an integer
	public static Long datePart(Object datepart, Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		String parte=CFTypes.toString(datepart);
		Long toreturn=new Long(0);
		if (parte.equalsIgnoreCase("yyyy")){
			toreturn=new Long(gc.get(GregorianCalendar.YEAR));
		}
		else if (parte.equalsIgnoreCase("q")){
			toreturn=new Long(new Double(Math.ceil(new Double(gc.get(GregorianCalendar.MONTH)+1)/3.0)).longValue());
		}
		else if (parte.equalsIgnoreCase("m")){
			toreturn=new Long(gc.get(GregorianCalendar.MONTH)+1);
		}
		else if (parte.equalsIgnoreCase("y")){
			toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_YEAR));
		}
		else if (parte.equalsIgnoreCase("d")){
			toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_MONTH));
		}
		else if (parte.equalsIgnoreCase("w")){
			toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_WEEK));
		}
		else if (parte.equalsIgnoreCase("ww")){
			toreturn=new Long(gc.get(GregorianCalendar.WEEK_OF_YEAR));
		}
		else if (parte.equalsIgnoreCase("h")){
			toreturn=new Long(gc.get(GregorianCalendar.HOUR_OF_DAY));
		}
		else if (parte.equalsIgnoreCase("n")){
			toreturn=new Long(gc.get(GregorianCalendar.MINUTE));
		}
		else if (parte.equalsIgnoreCase("s")){
			toreturn=new Long(gc.get(GregorianCalendar.SECOND));
		}
		else if (parte.equalsIgnoreCase("l")){
			toreturn=new Long(gc.get(GregorianCalendar.MILLISECOND));
		}
		else throw new IllegalArgumentException("function datePart: first parameter '"+parte+"' is not a valid argument");
		return toreturn;
	}
	
//  Returns a value from 1 to 31 (day in month)
	public static Long day(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_MONTH));
		return toreturn;
	}

//  Returns a value from 1 to 7 (day in week number)
	public static Long dayOfWeek(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_WEEK));
		return toreturn;
	}
	
//  Returns a String representing the day of the week for the day given as numeric parameter
	public static String dayOfWeekAsString(Object day){
		Long ordin=CFTypes.toInteger(day);
		int ord=ordin.intValue();
		String toreturn=new String();
		switch (ord){
			case 1: toreturn="Sunday"; break;
			case 2: toreturn="Monday"; break;
			case 3: toreturn="Tuesday"; break;
			case 4: toreturn="Wednesday"; break;
			case 5: toreturn="Thursday"; break;
			case 6: toreturn="Friday"; break;
			case 7: toreturn="Saturday"; break;
		}
		return toreturn;
	}
	
//  Returns the number of the day in the year
	public static Long dayOfYear(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_YEAR));
		return toreturn;
	}
			
//	Returns the number of days in the month in Date
	public static Long daysInMonth(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		return toreturn;
	}
	
//	Returns the number of days in the year in Date
	public static Long daysInYear(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
		return toreturn;
	}
	
//	Returns a number corespondig to the first day of the curent month in days of year
	public static Long firstDayOfMonth(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		gc.set(GregorianCalendar.DAY_OF_MONTH,gc.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		Long toreturn=new Long(gc.get(GregorianCalendar.DAY_OF_YEAR));
		return toreturn;
	}
		
//  Returns a string representation of the date
	public static String getHttpTimeString(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		SimpleDateFormat sd=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		String toreturn=sd.format(data);
		return toreturn;
	}
	
//  Returns a string representation of the system time in milliseconds.
	public static String getTickCount(){
		Long l=System.currentTimeMillis();
		return l.toString();
	}
	
//  Returns a number representation of the current hour (0-23)
	public static Long hour(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.HOUR));
		return toreturn;
	}

//  Returns "true" if given parameter is a date and "false" if not
	public static Boolean isDate(Object date){
		Boolean toreturn=Boolean.TRUE;
		double dtime=0.0;
		try{
			dtime=CFTypes.toDate(date);
		}catch (Exception e){
			toreturn=Boolean.FALSE;
		}
		dtime=Math.abs(dtime);
		dtime=dtime-Math.floor(dtime);
		dtime*=24.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=1000;
		if ((dtime-Math.floor(dtime)<0.9)&&(dtime-Math.floor(dtime)>=0.1)) toreturn=Boolean.FALSE;
		return toreturn;
	}
	
//  Returns "true" if year of the given date parameter is a leap year false otherwise
	public static Boolean isLeapYear(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Boolean toreturn;
		if (gc.isLeapYear(gc.get(GregorianCalendar.YEAR))) toreturn=Boolean.TRUE;
		else toreturn=Boolean.FALSE;
		return toreturn;
	}
	
//  Returns "true" if the argument is the numeric representation of a date, false if not\
	public static Boolean isNumericDate(Object date){
		Boolean toreturn=Boolean.TRUE;
		double dtime=CFTypes.toDouble(date);
		dtime=Math.abs(dtime);
		dtime=dtime-Math.floor(dtime);
		dtime*=24.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=1000;
		if ((dtime-Math.floor(dtime)<0.9)&&(dtime-Math.floor(dtime)>=0.1)) toreturn=Boolean.FALSE;
		return toreturn;
	}
	
//  Returns the date given as parameter as a locale formated string	
	public static String lsDateFormat(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		String toreturn=(DateFormat.getDateInstance()).format(data);
		return toreturn;
	}
	
//	Returns a date given as parameter as string formated after the given mask
	public static String lsDateFormat(Object date, Object mask){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		String masca=CFTypes.toString(mask);
		String forma=new String("");
		if (masca.equalsIgnoreCase("short")){
			forma="M/d/y";
		}
		else if (masca.equalsIgnoreCase("medium")){
			forma="MMM d, yyyy";
		}
		else if (masca.equalsIgnoreCase("long")){
			forma="MMMM d, yyyy";
		}
		else if (masca.equalsIgnoreCase("full")){
			forma="EEEE, MMMM d, yyyy";
		}
		else{
			Pattern pat;
			Matcher mat;
			int prima_poz;
			int ultima_poz;
			String temp;
			while (Pattern.matches("(.*)(dddd)(.*)",masca)){
				pat=Pattern.compile("(dddd)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(ddd)(.*)",masca)){
				pat=Pattern.compile("(ddd)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(DDDD)(.*)",masca)){
				pat=Pattern.compile("(DDDD)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(DDD)(.*)",masca)){
				pat=Pattern.compile("(DDD)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("EEE");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(DD)(.*)",masca)){
				pat=Pattern.compile("(DD)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("dd");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(D)(.*)",masca)){
				pat=Pattern.compile("(D)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("d");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(mmmm)(.*)",masca)){
				pat=Pattern.compile("(mmmm)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat((masca.substring(prima_poz,ultima_poz)).toUpperCase());
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(mmm)(.*)",masca)){
				pat=Pattern.compile("(mmm)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("MMM");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(mm)(.*)",masca)){
				pat=Pattern.compile("(mm)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("MM");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(m)(.*)",masca)){
				pat=Pattern.compile("(m)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("M");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)(gg)(.*)",masca)){
				pat=Pattern.compile("(gg)");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("GG");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			forma=masca;
		}
		String toreturn;
		try{
			SimpleDateFormat sd=new SimpleDateFormat(forma);
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			toreturn=sd.format(data);
		}catch (Exception e){
			throw new IllegalArgumentException("function lsdateFormat: second parameter '"+masca+"' is not a valid argument");
		}
		return toreturn;
	}

//  Returns true if the object can be turned into a date, false otherwise
	public static Boolean lsIsDate(Object date){
		Boolean toreturn=Boolean.TRUE;
		double dtime=0.0;
		try{
			dtime=CFTypes.toDate(date);
		}catch (Exception e){
			toreturn=Boolean.FALSE;
		}
		dtime=Math.abs(dtime);
		dtime=dtime-Math.floor(dtime);
		dtime*=24.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=60.0;
		dtime=dtime-Math.floor(dtime);
		dtime*=1000;
		if ((dtime-Math.floor(dtime)<0.9)&&(dtime-Math.floor(dtime)>=0.1)) toreturn=Boolean.FALSE;
		return toreturn;
	}

//  lsParseDate missing
	
//  Returns a local formated time string
	public static String lsTimeFormat(Object date){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		SimpleDateFormat sd=new SimpleDateFormat("hh:mm a");
		sd.setTimeZone(TimeZone.getDefault());
		String toreturn=sd.format(data);
		return toreturn;
	}
	
//  Returns a string representing the time formated by the parameter
	public static String lsTimeFormat(Object date, Object mask){
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		String masca=CFTypes.toString(mask);
		SimpleDateFormat sd;
		String toreturn=new String("");
		if (masca.equalsIgnoreCase("short")){
			toreturn=DateFormat.getTimeInstance(DateFormat.SHORT).format(data);
		}
		else if (masca.equalsIgnoreCase("medium")){
			toreturn=DateFormat.getTimeInstance(DateFormat.MEDIUM).format(data);
		}
		else if (masca.equalsIgnoreCase("long")){
			toreturn=DateFormat.getTimeInstance(DateFormat.LONG).format(data);
		}
		else if (masca.equalsIgnoreCase("full")){
			toreturn=DateFormat.getTimeInstance(DateFormat.FULL).format(data);
		}
		else{
			Pattern pat;
			Matcher mat;
			int prima_poz;
			int ultima_poz;
			String temp;
			while (Pattern.matches("(.*)((LLL)|(lll))(.*)",masca)){
				pat=Pattern.compile("((LLL)|(lll))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("SSS");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((LL)|(ll))(.*)",masca)){
				pat=Pattern.compile("((LL)|(ll))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("SS");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((l)|(L))(.*)",masca)){
				pat=Pattern.compile("((l)|(L))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("S");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((tt)|(TT))(.*)",masca)){
				pat=Pattern.compile("((tt)|(TT))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("a");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((t)|(T))(.*)",masca)){
				pat=Pattern.compile("((t)|(T))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("a");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			try{
				sd=new SimpleDateFormat(masca);
				sd.setTimeZone(TimeZone.getTimeZone("GMT"));
				toreturn=sd.format(data);
			}catch (Exception e){
				throw new IllegalArgumentException("function lsTimeFormat: second parameter '"+masca+"' is not a valid argument");
			}
		}		
		return toreturn;
	}

//  Returns a value from 0 to 59 (minute)
	public static Long minute(Object date){	
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.MINUTE));
		return toreturn;
	}

//  Returns a value from 1 to 12 (month)
	public static Long month(Object date){	
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.MONTH)+1);
		return toreturn;
	} 	
	
//  Returns a String representing the month for the day given as numeric parameter
	public static String monthAsString(Object month){
		Long ordin=CFTypes.toInteger(month);
		int ord=ordin.intValue();
		String toreturn=new String();
		switch (ord){
			case 1: toreturn="January"; break;
			case 2: toreturn="February"; break;
			case 3: toreturn="March"; break;
			case 4: toreturn="April"; break;
			case 5: toreturn="May"; break;
			case 6: toreturn="June"; break;
			case 7: toreturn="July"; break;
			case 8: toreturn="August"; break;
			case 9: toreturn="September"; break;
			case 10: toreturn="October"; break;
			case 11: toreturn="November"; break;
			case 12: toreturn="December"; break;
		}
		return toreturn;
	}	

//	Returns a double representation of the current time
	public static Double now(){
		double toreturn=dateToDouble(System.currentTimeMillis())+25569.0;
		return new Double(toreturn);
	}

//  Returnes a CF date/time double representation
	public static Double parseDateTime(Object date){
		return CFTypes.toDate(date);
	}
	
//  Returnes a CF date/time double representation
	public static Double parseDateTime(Object date, Object type){
		String tip=CFTypes.toString(type);
		Double toreturn=new Double(0.0);
		if (tip.equalsIgnoreCase("standard")) toreturn=CFTypes.toDate(date);
		else if (tip.equalsIgnoreCase("pop")){
			Date data=new Date(doubleToDate(CFTypes.toDate(date)));
			String datasir=new String("");
			SimpleDateFormat sd=new SimpleDateFormat(" yyyy-MM-dd H:m:s");
			sd.setTimeZone(TimeZone.getDefault());
			datasir=sd.format(data);
			sd.setTimeZone(TimeZone.getTimeZone("GMT"));
			data=sd.parse(datasir,new ParsePosition(0));
			toreturn=dateToDouble(data.getTime())+25569.0;
		}else throw new IllegalArgumentException("function parseDateTime: second parameter '"+tip+"' is not a valid argument");
		return toreturn;
	}
	
//  Returns a value from 1 to 4 (quarter of the year)
	public static Long quarter(Object date){	
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long luna=new Long(gc.get(GregorianCalendar.MONTH)+1);
		Long toreturn=new Long(0);
		if (luna<=3) toreturn=new Long(1);
		else if (luna<=6) toreturn=new Long(2);
		else if (luna<=9) toreturn=new Long(3);
		else if (luna<=12) toreturn=new Long(4);
		return toreturn;
	} 		
	
//  Returns a value from 0 to 59 (representing the number of seconds)
	public static Long second(Object date){	
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.SECOND));
		return toreturn;
	} 	
	
//  Returns a string representing a formated (hh:mm tt) time value
	public static String timeFormat(Object time){
		Date data=new Date(doubleToDate(CFTypes.toDate(time)));
		SimpleDateFormat sd=new SimpleDateFormat("HH:mm aa");
		sd.setTimeZone(TimeZone.getTimeZone("GMT"));
		String toreturn=sd.format(data);
		return toreturn;
	}
	
//  Returns a string representing a formated time value
	public static String timeFormat(Object time, Object mask){
		Date data=new Date(doubleToDate(CFTypes.toDate(time)));
		String masca=CFTypes.toString(mask);
		SimpleDateFormat sd;
		String toreturn=new String("");
		if (masca.equalsIgnoreCase("short")){
			toreturn=DateFormat.getTimeInstance(DateFormat.SHORT).format(data);
		}
		else if (masca.equalsIgnoreCase("medium")){
			toreturn=DateFormat.getTimeInstance(DateFormat.MEDIUM).format(data);
		}
		else if (masca.equalsIgnoreCase("long")){
			toreturn=DateFormat.getTimeInstance(DateFormat.LONG).format(data);
		}
		else if (masca.equalsIgnoreCase("full")){
			toreturn=DateFormat.getTimeInstance(DateFormat.FULL).format(data);
		}
		else{
			Pattern pat;
			Matcher mat;
			int prima_poz;
			int ultima_poz;
			String temp;
			while (Pattern.matches("(.*)((LLL)|(lll))(.*)",masca)){
				pat=Pattern.compile("((LLL)|(lll))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("SSS");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((LL)|(ll))(.*)",masca)){
				pat=Pattern.compile("((LL)|(ll))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("SS");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((l)|(L))(.*)",masca)){
				pat=Pattern.compile("((l)|(L))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("S");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((tt)|(TT))(.*)",masca)){
				pat=Pattern.compile("((tt)|(TT))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("a");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			while (Pattern.matches("(.*)((t)|(T))(.*)",masca)){
				pat=Pattern.compile("((t)|(T))");
				mat=pat.matcher(masca);
				if (mat.find()){
					prima_poz=mat.start();
					ultima_poz=mat.end();
					temp=masca.substring(0,prima_poz);
					temp=temp.concat("a");
					temp=temp.concat(masca.substring(ultima_poz,masca.length()));
					masca=temp;
				}
			}
			try{
				sd=new SimpleDateFormat(masca);
				sd.setTimeZone(TimeZone.getTimeZone("GMT"));
				toreturn=sd.format(data);
			}catch (Exception e){
				throw new IllegalArgumentException("function timeFormat: second parameter '"+masca+"' is not a valid argument");
			}
		}		
		return toreturn;
	}
	
//  Returns a value from 1 to 53 (representing the number of the week within the year)
	public static Long week(Object date){	
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.WEEK_OF_YEAR));
		return toreturn;
	} 
	
//  Returns a numeric value (representing the year)
	public static Long year(Object date){	
		Date data=new Date(doubleToDate(CFTypes.toDate(date)));
		GregorianCalendar gc=new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		gc.setTime(data);
		Long toreturn=new Long(gc.get(GregorianCalendar.YEAR));
		return toreturn;
	} 	
}
