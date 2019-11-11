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

public class CFMath 
{
	public static Object abs(Object num)
	{
		return new Double(Math.abs(CFTypes.toDouble(num)));
	}
	
	public static Object acos(Object num)
	{
		return new Double(Math.acos(CFTypes.toDouble(num)));
	}
	
	public static Object asin(Object num)
	{
		return new Double(Math.asin(CFTypes.toDouble(num)));
	}
	
	public static Object atn(Object num)
	{
		return new Double(Math.atan(CFTypes.toDouble(num)));
	}
	
	public static Object bitAnd(Object a, Object b)
	{
		return new Long(CFTypes.toInteger(a) & CFTypes.toInteger(b));
	}
	
	public static Object bitNot(Object a)
	{
		return new Long(~CFTypes.toInteger(a));
	}
	
	public static Object bitOr(Object a, Object b)
	{
		return new Long(CFTypes.toInteger(a) | CFTypes.toInteger(b));
	}
	
	public static Object bitSHLN(Object a, Object b)
	{
		return new Long(CFTypes.toInteger(a) << CFTypes.toInteger(b));
	}
	
	public static Object bitSHRN(Object a, Object b)
	{
		return new Long(CFTypes.toInteger(a) >> CFTypes.toInteger(b));
	}
	
	public static Object bitXor(Object a, Object b)
	{
		return new Long(CFTypes.toInteger(a) ^ CFTypes.toInteger(b));
	}
	
	public static Object ceiling(Object a)
	{
		return new Double(Math.ceil(CFTypes.toDouble(a)));
	}
	
	public static Object cos(Object num)
	{
		return new Double(Math.cos(CFTypes.toDouble(num)));
	}
	
	public static Object decrementValue(Object num)
	{
		return new Double(CFTypes.toDouble(num) - 1);
	}
	
	public static Object exp(Object num)
	{
		return new Double(Math.exp(CFTypes.toDouble(num)));
	}
	
	public static Object fix(Object num)
	{
		return new Long((long) CFTypes.toDouble(num).doubleValue());
	}
	
	public static String inputBaseN(Object string, Object radix) {
		int base = Integer.parseInt(radix.toString());
		String n = string.toString().toUpperCase();
		
		if (base < 2 || base > 36)
			throw new IllegalArgumentException("Invalid argument for function inputBaseN.");
		
		long sum = 0;
		for (int i = 0; i < n.length(); i++)
			if (isCharInBase(n.charAt(i), base))
				sum += charToNumber(n.charAt(i)) * Math.pow(base, n.length() - i - 1);
		
		return sum + "";
	}
	
	private static boolean isCharInBase(char x, int base) {
		if (!(x - '0' >= 0 && x - '9' <= 0) && !(x - 'A' >= 0 && x - 'Z' <= 0))
			throw new IllegalArgumentException("Not a valid number");
		else if (base <= 10) {
			if (x - '0' >= base)
				throw new IllegalArgumentException("Invalid argument for function inputBaseN.");
			else
				return true;
		} else if (base > 10) {
			if (x - 'A' + 10 >= base)
				throw new IllegalArgumentException("Invalid argument for function inputBaseN.");
			else
				return true;
		} else
			return false;
	}
	
	private static int charToNumber(char x) {
		if (!(x - '0' >= 0 && x - '9' <= 0) && !(x - 'A' >= 0 && x - 'Z' <= 0))
			throw new IllegalArgumentException("Not a valid number");
		else
			if (x <= '9')
				return x - '0';
			else
				return x - 'A' + 10;
	}
	
}
