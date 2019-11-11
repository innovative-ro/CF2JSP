/**
 * Copyright (c) 2006 - present Innovative Systems SRL
 * Copyright (c) 2006 - present Ovidiu Podisor ovidiu.podisor@innodocs.com
 * 
 * Authors: Ovidiu Podisor
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

import java.util.Vector;

public class CFArray {
	
	public static Object arrayNew(Object dim) {
		long cfdim = CFTypes.toInteger(dim);
		if (cfdim == 1)
			return new Vector<Object>();
		else
			throw new IllegalArgumentException("arrayNew: array dimension must be 1, 2 or 3");
	}
	
	// ArrayAppend appends an array element to an array.
	// Returns true, on successful completion.
	public static boolean arrayAppend(Object[] array, Object value) {
		// to-do
		return true;
	}

	
}
