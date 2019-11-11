// $ANTLR : "Expr.g" -> "ExprParser.java"$

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

public interface ExprParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int ID = 4;
	int DOT = 5;
	int SGEQ = 6;
	int OR = 7;
	int AND = 8;
	int IS = 9;
	int EQ = 10;
	int NOT = 11;
	int NEQ = 12;
	int GT = 13;
	int LT = 14;
	int LESS = 15;
	int THAN = 16;
	int EQUAL = 17;
	int TO = 18;
	int LE = 19;
	int LTE = 20;
	int GREATER = 21;
	int GE = 22;
	int GTE = 23;
	int CAT = 24;
	int MINUS = 25;
	int PLUS = 26;
	int MOD = 27;
	int STRING = 28;
	int STRINGSQ = 29;
	int OSB = 30;
	int CSB = 31;
	int OB = 32;
	int COM = 33;
	int CB = 34;
	int NUMBER = 35;
	int SH = 36;
	int LETTER = 37;
	int DIGIT = 38;
	int CAB = 39;
	int WS = 40;
}
