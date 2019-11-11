/**
 * 
 */
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

import java.util.Comparator;

/**
 * @author Puiutii
 * 
 */
public class CFListElementComparator implements Comparator<String>{

	private int type;

	private int order;

	public static final int TYPE_NUMERIC = 0;

	public static final int TYPE_TEXT = 1;

	public static final int TYPE_TEXTNOCASE = 2;

	public static final int ORDER_ASC = 0;

	public static final int ORDER_DESC = 1;

	/**
	 * @param type
	 */
	public CFListElementComparator(int type) {
		this.type = type;
		this.order = CFListElementComparator.ORDER_ASC;
	}

	/**
	 * @param type
	 * @param order
	 */
	public CFListElementComparator(int type, int order) {
		this.type = type;
		this.order = order;
	}

	/**
	 * @return
	 */
	public int compare(String a, String b) {
		if (order == CFListElementComparator.ORDER_ASC) {
			if (type == CFListElementComparator.TYPE_NUMERIC)
				return ((Math.abs(Double.parseDouble(a) - Double.parseDouble(b)) < 1)
						&& (Double.parseDouble(a) - Double.parseDouble(b) != 0))
						? ((int) Math.signum(Double.parseDouble(a) - Double.parseDouble(b)))
						: ((int) (Double.parseDouble(a) - Double.parseDouble(b)));
			else if (type == CFListElementComparator.TYPE_TEXT)
				return a.compareTo(b);
			else if (type == CFListElementComparator.TYPE_TEXTNOCASE)
				return a.compareToIgnoreCase(b);
		} else if (order == CFListElementComparator.ORDER_DESC) {
			if (type == CFListElementComparator.TYPE_NUMERIC)
				return ((Math.abs(Double.parseDouble(b) - Double.parseDouble(a)) < 1)
						&& (Double.parseDouble(b) - Double.parseDouble(a) != 0))
						? ((int) Math.signum(Double.parseDouble(b) - Double.parseDouble(a)))
						: ((int) (Double.parseDouble(b) - Double.parseDouble(a)));
			else if (type == CFListElementComparator.TYPE_TEXT)
				return b.compareTo(a);
			else if (type == CFListElementComparator.TYPE_TEXTNOCASE)
				return b.compareToIgnoreCase(a);
		}

		return 0;
	}

}
