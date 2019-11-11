header{
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
}

class PreExprParser extends Parser;

options
{
	k = 2;
}

gen returns [String n = null]
{
	StringBuilder sb = new StringBuilder();
	String m;
}:

	(m = expr { sb.append(m); })+ CAB
	{ n = sb.toString(); };

cfset returns [Node n = new Node()]
{
	StringBuilder sb = new StringBuilder();
	String m;
	String id;
}:
	(WS)
	(SH)?
	(WS)?
	i: ID {id = i.getText();} (POINT j: ID { id += "." + j.getText(); })*
	(WS)?
	(SH)?
	(WS)?
	SGEQ (m = expr { sb.append(m); })+ CAB
	{
		n.addChild(new Node(id));
		n.addChild(new Node(sb.toString()));
	};

expr returns [String n = null]
{
	Node e = null;
	String ts = "";
}:
	ws: WS { n = ws.getText(); }
|
	s: STRING { n = s.getText(); }
|
	sq: STRINGSQ { n = sq.getText(); }
|
	ip: ID { n= ip.getText(); }
|
	OB { n = "("; }
|
	CB { n = ")"; }
|
	OSB { n = "("; }
|
	CSB { n = ")"; }
|
	POINT { n = "."; }
|
	CAT { n = "&"; }
|
	PLUS { n = "+"; }
|
	MINUS { n = "-"; }
|
	COM { n = ","; }
|
	p: NUMBER { n = p.getText(); }
|
	SH { n = "#"; };

class PreExprLexer extends Lexer;

options
{
	charVocabulary = '\3'..'\377';
	caseSensitive = false;
}

protected
LETTER: 'a'..'z';

protected
DIGIT: '0'..'9';

CAB: '>';
OB: '(';
CB: ')';
OSB: '[';
CSB: ']';
SGEQ: '=';
POINT: '.';
COM: ',';
MINUS: '-';
PLUS: '+';
SH: '#';
CAT: '&';

ID: (LETTER | '_') (LETTER | '_' | DIGIT)*;
NUMBER: (DIGIT)+;

STRING: '"' (~'"')* '"';
STRINGSQ: '\'' (~'\'')* '\'';

WS: (' ' | '\t' | '\n' | '\r')+;
