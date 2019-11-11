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
 
package ro.innovative.iml.lang.cf.compiler;

import java.io.PrintWriter;

import ro.innovative.iml.lang.cf.node.Node;

public class NodeProcess {

	public static void process(Node n, PrintWriter out) throws Exception {
		if (n != null) {
			n.processStart(out);
			for (int i = 0; i < n.getChildren().size(); i++) {
				process(n.getChildren().get(i), out);
			}
			n.processFinish(out);
		} else {
			System.out.println("null");
		}
	}
}
