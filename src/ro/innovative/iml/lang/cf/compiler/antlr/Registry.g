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

import java.util.HashMap;
}

class RegistryParser extends Parser;

gen returns [HashMap m = new HashMap()]
{
	String s1 = null, s2 = null, s3 = null;
	HashMap l = null;
}:
 	(
		(hs: HSTRING { l = new HashMap(); }
		(
			key: STRING
			{
				s2 = key.getText();
				s2 = s2.substring(1, s2.length() - 1);
			}
			EQ
			val: STRING
			{
				s3 = val.getText();
				s3 = s3.substring(1, s3.length() - 1);
				l.put(s2, s3);
			}
		)*
		)
		{
			s1 = hs.getText();
			s1 = s1.substring(1, s1.length() - 1);
			m.put(s1, l);
		}
	)* EOF;

class RegistryLexer extends Lexer;

options
{
	charVocabulary = '\3'..'\377';
	caseSensitive = false;
}

STRING: '"' (~'"')* '"';
HSTRING: '[' (~']')* ']';
EQ: '=';

WS: (' ' | '\t' | '\r' | '\n') { _ttype = Token.SKIP; };
