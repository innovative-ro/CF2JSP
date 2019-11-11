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

class ParamTagParser extends Parser;

gen returns [ParamTagNode n = new ParamTagNode()]
{
	Node e = null;
	String tz = "";
}:
	(
		i: IDENT EQ
		(
		(s: STRING
			{
				e = new Node();
				tz = s.getText();
				tz = tz.substring(1, tz.length() - 1);
				e.setValue(tz);
				n.setParameter(i.getText().toLowerCase(), e);
			}
		)
		|
		(z: DSTRING
			{
				e = new Node();
				tz = z.getText();
				//tz = tz.substring(1, tz.length() - 1);
				e.setValue(tz);
				n.setParameter(i.getText().toLowerCase(), e);
			}
		)
		|
		(m: NUMBER
			{
				e = new Node();
				e.setValue(m.getText());
				n.setParameter(i.getText(), e);
			}
		)
		)
	)*
	(SLASH)?
	CB;

class ParamTagLexer extends Lexer;

options
{
	charVocabulary = '\3'..'\377';
	caseSensitive = false;
}

CB: '>';
EQ: '=';
SLASH: '/';
STRING: '"' (~'"')* '"';
DSTRING: '#' (~'#')* '#';
NUMBER: ('0'..'9')+;
IDENT: ('a'..'z' | '_') ('a'..'z' | '_' | '0'..'9')*;

WS: (' ' | '\t' | '\r' | '\n') { _ttype = Token.SKIP; };
