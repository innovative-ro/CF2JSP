// $ANTLR : "PreExpr.g" -> "PreExprLexer.java"$

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
 
package ro.innovative.iml.lang.cf.compiler.antlr;

import ro.innovative.iml.lang.cf.node.*;

public interface PreExprParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int CAB = 4;
	int WS = 5;
	int SH = 6;
	int ID = 7;
	int POINT = 8;
	int SGEQ = 9;
	int STRING = 10;
	int STRINGSQ = 11;
	int OB = 12;
	int CB = 13;
	int OSB = 14;
	int CSB = 15;
	int CAT = 16;
	int PLUS = 17;
	int MINUS = 18;
	int COM = 19;
	int NUMBER = 20;
	int LETTER = 21;
	int DIGIT = 22;
}
