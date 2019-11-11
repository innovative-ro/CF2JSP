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

public class CFOperators {

	public static Object neg(Object a) {
		return -CFTypes.toDouble(a);
	}
	
	public static Object add(Object a, Object b) {
		Double da, db;
		if ((da = CFTypes.toStrictDouble(a)) != null)
			if ((db = CFTypes.toStrictDouble(b)) != null)
				return da + db;
			else
				return da + CFTypes.toInteger(b);
		else if ((db = CFTypes.toStrictDouble(b)) != null)
			return CFTypes.toInteger(a) + db;
		else
			return CFTypes.toInteger(a) + CFTypes.toInteger(b);
	}

	public static Object sub(Object a, Object b) {
		Double da, db;
		if ((da = CFTypes.toStrictDouble(a)) != null)
			if ((db = CFTypes.toStrictDouble(b)) != null)
				return da - db;
			else
				return da - CFTypes.toInteger(b);
		else if ((db = CFTypes.toStrictDouble(b)) != null)
			return CFTypes.toInteger(a) - db;
		else
			return CFTypes.toInteger(a) - CFTypes.toInteger(b);
	}

	public static Object mul(Object a, Object b) {
		Double da, db;
		if ((da = CFTypes.toStrictDouble(a)) != null)
			if ((db = CFTypes.toStrictDouble(b)) != null)
				return da * db;
			else
				return da * CFTypes.toInteger(b);
		else if ((db = CFTypes.toStrictDouble(b)) != null)
			return CFTypes.toInteger(a) * db;
		else
			return CFTypes.toInteger(a) * CFTypes.toInteger(b);
	}

	public static Object div(Object a, Object b) {
		Double da, db;
		if ((da = CFTypes.toStrictDouble(a)) != null)
			if ((db = CFTypes.toStrictDouble(b)) != null)
				return da / db;
			else
				return da / CFTypes.toInteger(b);
		else if ((db = CFTypes.toStrictDouble(b)) != null)
			return (double) CFTypes.toInteger(a) / db;
		else
			return (double) CFTypes.toInteger(a) / CFTypes.toInteger(b);
	}

	public static Object idiv(Object a, Object b) {
		Double da, db;
		if ((da = CFTypes.toStrictDouble(a)) != null)
			if ((db = CFTypes.toStrictDouble(b)) != null)
				return (int) (da / db);
			else
				return (int) (da / CFTypes.toInteger(b));
		else if ((db = CFTypes.toStrictDouble(b)) != null)
			return (int) (CFTypes.toInteger(a) / db);
		else
			return CFTypes.toInteger(a) / CFTypes.toInteger(b);
	}

	public static Object mod(Object a, Object b) {
		Double da, db;
		if ((da = CFTypes.toStrictDouble(a)) != null)
			if ((db = CFTypes.toStrictDouble(b)) != null)
				return (int) (da % db);
			else
				return (int) (da % CFTypes.toInteger(b));
		else if ((db = CFTypes.toStrictDouble(b)) != null)
			return (int) (CFTypes.toInteger(a) % db);
		else
			return CFTypes.toInteger(a) % CFTypes.toInteger(b);
	}

	public static Object pow(Object a, Object b) {
		return Math.pow(CFTypes.toDouble(a), CFTypes.toDouble(b));
	}

	public static Object cfnot(Object a) {
		return !CFTypes.toBoolean(a);
	}

	public static Object cfand(Object a, Object b) {
		if (CFTypes.toBoolean(a.toString().trim()) == true)
			return b;
		else
			return CFTypes.toBoolean(a.toString().trim());
	}

	public static Object cfor(Object a, Object b) {
		if (CFTypes.toBoolean(a.toString().trim()) == true)
			return CFTypes.toBoolean(a.toString().trim());
		else
			return b;
	}

	public static Object cfxor(Object a, Object b) {
		return CFTypes.toBoolean(a.toString().trim())
				^ CFTypes.toBoolean(b.toString().trim());
	}

	public static Object cfeqv(Object a, Object b) {
		return !((Boolean) cfxor(a, b));
	}

	public static Object cfimp(Object a, Object b) {
		if (CFTypes.toBoolean(a.toString().trim()) == true
				&& CFTypes.toBoolean(b.toString().trim()) == false)
			return false;
		else
			return true;
	}

	public static Object cfeq(Object a, Object b) {
		if (a instanceof String && b instanceof String && ((String)a).length() == 0)
			return b.equals(a);
		if (b instanceof String && a instanceof String && ((String)b).length() == 0)
			return a.equals(b);
		if (CFTypes.toStrictBoolean(a) != null && CFTypes.toStrictBoolean(b) != null)
			return (CFTypes.toStrictBoolean(a)).equals((CFTypes.toStrictBoolean(b)));
		else if (CFTypes.toStrictDouble(a) != null && CFTypes.toStrictDouble(b) != null)
			return CFTypes.toStrictDouble(a).equals(CFTypes.toStrictDouble(b));
		else {
			if (a.toString().equalsIgnoreCase(b.toString()))
				return true;
			else
				return false;
		}
	}

	public static Object cfneq(Object a, Object b) {
		return !((Boolean) cfeq(a, b));
	}

	public static Object less(Object a, Object b) {
		if ((a instanceof Boolean || a instanceof Integer || a instanceof Double)
				&& (b instanceof Boolean || b instanceof Integer || b instanceof Double)) {
			return CFTypes.toDouble(a) < CFTypes.toDouble(b);
		} else {
			try {
				return CFTypes.toDouble(a) < CFTypes.toDouble(b);
			} catch (IllegalArgumentException e) {
				return a.toString().compareTo(b.toString()) < 0 ? true : false;
			}
		}
	}

	public static Object greater(Object a, Object b) {
		if ((a instanceof Boolean || a instanceof Integer || a instanceof Double)
				&& (b instanceof Boolean || b instanceof Integer || b instanceof Double)) {
			return CFTypes.toDouble(a) > CFTypes.toDouble(b);
		} else {
			try {
				return CFTypes.toDouble(a) > CFTypes.toDouble(b);
			} catch (IllegalArgumentException e) {
				return a.toString().compareTo(b.toString()) > 0 ? true : false;
			}
		}
	}

	public static Object lte(Object a, Object b) {
		if ((a instanceof Boolean || a instanceof Integer || a instanceof Double)
				&& (b instanceof Boolean || b instanceof Integer || b instanceof Double)) {
			return CFTypes.toDouble(a) <= CFTypes.toDouble(b);
		} else {
			try {
				return CFTypes.toDouble(a) <= CFTypes.toDouble(b);
			} catch (IllegalArgumentException e) {
				return a.toString().compareTo(b.toString()) <= 0 ? true : false;
			}
		}
	}

	public static Object gte(Object a, Object b) {
		if ((a instanceof Boolean || a instanceof Integer || a instanceof Double)
				&& (b instanceof Boolean || b instanceof Integer || b instanceof Double)) {
			return CFTypes.toDouble(a) >= CFTypes.toDouble(b);
		} else {
			try {
				return CFTypes.toDouble(a) >= CFTypes.toDouble(b);
			} catch (IllegalArgumentException e) {
				return a.toString().compareTo(b.toString()) >= 0 ? true : false;
			}
		}
	}

	public static Object cfcontains(Object a, Object b) {
		return a.toString().contains(
				b.toString().subSequence(0, b.toString().length()));
	}

	public static Object cfdoesNotContain(Object a, Object b) {
		return !((Boolean) cfcontains(a, b));
	}

	public static Object cat(Object a, Object b) {
		return a.toString() + b.toString();
	}
	
}
