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

class ExprParser extends Parser;

options
{
	k = 4;
}

gen returns [Node n = null]:
	n = logical EOF;

cfset returns [Node n = null]:
	ID (DOT ID)* SGEQ n = logical EOF;

logical returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = eqv
		(IMP
			{
				op1 = n;
				n = new ExNode(NodeType.IMP);
				n.addChild(op1);
			}
			op2 = eqv {n.addChild(op2);}
		)*;

eqv returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = xor
		(EQV
			{
				op1 = n;
				n = new ExNode(NodeType.EQV);
				n.addChild(op1);
			}
			op2 = xor {n.addChild(op2);}
		)*;

xor returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = or
		(XOR
			{
				op1 = n;
				n = new ExNode(NodeType.XOR);
				n.addChild(op1);
			}
		op2 = or {n.addChild(op2);}
		)*;

or returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = and
		(OR
			{
				op1 = n;
				n = new ExNode(NodeType.OR);
				n.addChild(op1);
			}
		op2 = and {n.addChild(op2);}
		)*;

and returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = rel
		(AND
			{
				op1 = n;
				n = new ExNode(NodeType.AND);
				n.addChild(op1);
			}
		op2 = not {n.addChild(op2);}
		)*;

not returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = rel
|
	NOT n = rel
	{
		op1 = new ExNode(NodeType.NOT); op1.addChild(n); n = op1;
	};


rel returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = cat
		(
			(((IS | EQ | EQUAL)
				{
					op1 = n;
					n = new ExNode(NodeType.EQ);
					n.addChild(op1);
				})
			|
			((IS NOT | NEQ | NOT EQUAL)
			    {
					op1 = n;
					n = new ExNode(NodeType.NEQ);
					n.addChild(op1);
			    })
			|
			((GT | GREATER THAN)
				{
					op1 = n;
					n = new ExNode(NodeType.GT);
					n.addChild(op1);
				})
			|
			((LT | LESS THAN)
				{
					op1 = n;
					n = new ExNode(NodeType.LT);
					n.addChild(op1);
				})
			|
			((GE | GTE | GREATER THAN OR EQUAL TO)
				{
					op1 = n;
					n = new ExNode(NodeType.GTE);
					n.addChild(op1);
				})
			((LE | LTE | LESS THAN OR EQUAL TO))
				{
					op1 = n;
					n = new ExNode(NodeType.LTE);
					n.addChild(op1);
				})
			)

			op2 = cat {n.addChild(op2);}
		)*;


cat returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = arith
		(
			(CAT
				{
					op1 = n;
					n = new ExNode(NodeType.CAT);
					n.addChild(op1);
				}
			)
			op2 = arith {n.addChild(op2);}
		)*;

arith returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = mod
		((
			(MINUS
				{
					op1 = n;
					n = new ExNode(NodeType.MINUS);
					n.addChild(op1);
				}
			)
			|
			(PLUS
				{
					op1 = n;
					n = new ExNode(NodeType.PLUS);
					n.addChild(op1);
				}
			)
		)
		op2 = mod {n.addChild(op2);}
		)*;

mod returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = term
		(
		(MOD
			{
				op1 = n;
				n = new ExNode(NodeType.MOD);
				n.addChild(op1);
			}
		)
		op2 = val06 {n.addChild(op2);}
		)*;


val07 returns [Node n = null]
{
	Node op1 = null, op2 = null;
}:
	n = val08
|
	MINUS n = val08
	{
		op1 = new ExNode(NodeType.NEG); op1.addChild(n); n = op1;
	};

val08 returns [Node n = null]
{
	Node e = null;
	String ts = "";
}:
	s: STRING
	{
		ts = s.getText(); ts = ts.substring(1, ts.length() - 1); n = new OpNode(ts);
	}
|
	sq: STRINGSQ
	{
		ts = sq.getText(); ts = ts.substring(1, ts.length() - 1); n = new OpNode(ts);
	}
|
	(
		id2: ID OSB e = expr CSB { n = new IndexNode(); n.setValue(id2.getText()); }
		(DOT jid2: ID
			{ e = new Node(); e.setValue(jid2.getText()); n.addChild(e); }
		)*
	)
|
	ip: ID
		{
			//int i = 0;
			n = new FuncNode();
			n.setValue(ip.getText());
		}
		OB
		(
			e = expr
			{
				//n.setParameter("param" + i++, e);
				n.addChild(e);
			}
			(
				COM e = expr
				{
					//n.setParameter("param" + i++, e);
					n.addChild(e);
				}
			)*
		)?
		CB

|
	(
		id: ID { n = new IdentifNode(); n.setValue(id.getText()); }
		(DOT jid: ID
			{ e = new Node(); e.setValue(jid.getText()); n.addChild(e); }
		)*
	)
|
	p: NUMBER { n = new OpNode(Integer.parseInt(p.getText())); }
|
	OB n = expr CB
|
	SH n = expr SH;

class ExprLexer extends Lexer;

options
{
	charVocabulary = '\3'..'\377';
	caseSensitive = false;
	caseSensitiveLiterals = false;
	testLiterals = true;
}

tokens
{
	AND = "and";
	OR = "or";
	EQ = "eq";
	IS = "is";
	NOT = "not";
	GT = "gt";
	GTE = "gte";
	LT = "lt";
	LTE = "lte";
	MOD = "mod";
	NEQ = "neq";
	LESS = "less";
	EQUAL = "equal";
	GREATER = "greater";
	THAN = "than";
	TO = "to";
	LE = "le";
	GE = "ge";
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
DOT: '.';
COM: ',';
MINUS: '-';
PLUS: '+';
SH: '#';
CAT: '&';

ID: (LETTER | '_') (LETTER | '_' | DIGIT)*;
NUMBER: (DIGIT)+;

STRING: '"' (~'"')* '"';
STRINGSQ: '\'' (~'\'')* '\'';

WS: (' ' | '\t' | '\n' | '\r') { _ttype = Token.SKIP; };
