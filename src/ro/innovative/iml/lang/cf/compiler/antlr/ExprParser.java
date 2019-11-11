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

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class ExprParser extends antlr.LLkParser       implements ExprParserTokenTypes
 {

protected ExprParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public ExprParser(TokenBuffer tokenBuf) {
  this(tokenBuf,4);
}

protected ExprParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public ExprParser(TokenStream lexer) {
  this(lexer,4);
}

public ExprParser(ParserSharedInputState state) {
  super(state,4);
  tokenNames = _tokenNames;
}

	public final Node  gen() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
		try {      // for error handling
			n=expr();
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return n;
	}
	
	public final Node  expr() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			n=and();
			{
			_loop363:
			do {
				if ((LA(1)==OR)) {
					match(OR);
					
										op1 = n;
										n = new ExNode(NodeType.OR);
										n.addChild(op1);
									
					op2=and();
					n.addChild(op2);
				}
				else {
					break _loop363;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return n;
	}
	
	public final Node  cfset() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
		try {      // for error handling
			match(ID);
			{
			_loop360:
			do {
				if ((LA(1)==DOT)) {
					match(DOT);
					match(ID);
				}
				else {
					break _loop360;
				}
				
			} while (true);
			}
			match(SGEQ);
			n=expr();
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return n;
	}
	
	public final Node  and() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			n=eq();
			{
			_loop366:
			do {
				if ((LA(1)==AND)) {
					match(AND);
					
										op1 = n;
										n = new ExNode(NodeType.AND);
										n.addChild(op1);
									
					op2=eq();
					n.addChild(op2);
				}
				else {
					break _loop366;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		return n;
	}
	
	public final Node  eq() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			n=val03();
			{
			_loop387:
			do {
				if ((_tokenSet_3.member(LA(1)))) {
					{
					switch ( LA(1)) {
					case IS:
					case EQ:
					{
						{
						{
						switch ( LA(1)) {
						case IS:
						{
							match(IS);
							break;
						}
						case EQ:
						{
							match(EQ);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						
											op1 = n;
											n = new ExNode(NodeType.EQ);
											n.addChild(op1);
										
						{
						if ((LA(1)==NOT) && (_tokenSet_4.member(LA(2))) && (_tokenSet_5.member(LA(3))) && (_tokenSet_6.member(LA(4)))) {
							match(NOT);
							((ExNode)n).setType(NodeType.NEQ);
						}
						else if ((_tokenSet_4.member(LA(1))) && (_tokenSet_5.member(LA(2))) && (_tokenSet_6.member(LA(3))) && (_tokenSet_6.member(LA(4)))) {
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						
						}
						}
						break;
					}
					case NEQ:
					{
						{
						match(NEQ);
						
											op1 = n;
											n = new ExNode(NodeType.NEQ);
											n.addChild(op1);
										
						}
						break;
					}
					case GT:
					{
						{
						match(GT);
						
											op1 = n;
											n = new ExNode(NodeType.GT);
											n.addChild(op1);
										
						}
						break;
					}
					case LT:
					{
						{
						match(LT);
						
											op1 = n;
											n = new ExNode(NodeType.LT);
											n.addChild(op1);
										
						}
						break;
					}
					case LE:
					{
						{
						match(LE);
						
											op1 = n;
											n = new ExNode(NodeType.LTE);
											n.addChild(op1);
										
						}
						break;
					}
					case LTE:
					{
						{
						match(LTE);
						
											op1 = n;
											n = new ExNode(NodeType.LTE);
											n.addChild(op1);
										
						}
						break;
					}
					case EQUAL:
					{
						{
						match(EQUAL);
						
											op1 = n;
											n = new ExNode(NodeType.EQ);
											n.addChild(op1);
										
						}
						break;
					}
					case GE:
					{
						{
						match(GE);
						
											op1 = n;
											n = new ExNode(NodeType.GTE);
											n.addChild(op1);
										
						}
						break;
					}
					case GTE:
					{
						{
						match(GTE);
						
											op1 = n;
											n = new ExNode(NodeType.GTE);
											n.addChild(op1);
										
						}
						break;
					}
					default:
						if ((LA(1)==LESS) && (LA(2)==THAN) && (LA(3)==OR)) {
							{
							match(LESS);
							match(THAN);
							match(OR);
							match(EQUAL);
							match(TO);
							
												op1 = n;
												n = new ExNode(NodeType.LTE);
												n.addChild(op1);
											
							}
						}
						else if ((LA(1)==LESS) && (LA(2)==THAN) && (_tokenSet_4.member(LA(3)))) {
							{
							match(LESS);
							match(THAN);
							
												op1 = n;
												n = new ExNode(NodeType.LT);
												n.addChild(op1);
											
							}
						}
						else if ((LA(1)==LESS) && (_tokenSet_4.member(LA(2)))) {
							{
							match(LESS);
							
												op1 = n;
												n = new ExNode(NodeType.LT);
												n.addChild(op1);
											
							}
						}
						else if ((LA(1)==GREATER) && (LA(2)==THAN) && (LA(3)==OR)) {
							{
							match(GREATER);
							match(THAN);
							match(OR);
							match(EQUAL);
							match(TO);
							
												op1 = n;
												n = new ExNode(NodeType.GTE);
												n.addChild(op1);
											
							}
						}
						else if ((LA(1)==GREATER) && (LA(2)==THAN) && (_tokenSet_4.member(LA(3)))) {
							{
							match(GREATER);
							match(THAN);
							
												op1 = n;
												n = new ExNode(NodeType.GT);
												n.addChild(op1);
											
							}
						}
						else if ((LA(1)==GREATER) && (_tokenSet_4.member(LA(2)))) {
							{
							match(GREATER);
							
												op1 = n;
												n = new ExNode(NodeType.GT);
												n.addChild(op1);
											
							}
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					op2=val03();
					n.addChild(op2);
				}
				else {
					break _loop387;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_7);
		}
		return n;
	}
	
	public final Node  val03() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			n=val04();
			{
			_loop392:
			do {
				if ((LA(1)==CAT)) {
					{
					{
					match(CAT);
					
										op1 = n;
										n = new ExNode(NodeType.CAT);
										n.addChild(op1);
									
					}
					}
					op2=val04();
					n.addChild(op2);
				}
				else {
					break _loop392;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_8);
		}
		return n;
	}
	
	public final Node  val04() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			n=val05();
			{
			_loop398:
			do {
				if ((LA(1)==MINUS||LA(1)==PLUS)) {
					{
					switch ( LA(1)) {
					case MINUS:
					{
						{
						match(MINUS);
						
											op1 = n;
											n = new ExNode(NodeType.SUB);
											n.addChild(op1);
										
						}
						break;
					}
					case PLUS:
					{
						{
						match(PLUS);
						
											op1 = n;
											n = new ExNode(NodeType.ADD);
											n.addChild(op1);
										
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					op2=val05();
					n.addChild(op2);
				}
				else {
					break _loop398;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_9);
		}
		return n;
	}
	
	public final Node  val05() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			n=val06();
			{
			_loop403:
			do {
				if ((LA(1)==MOD)) {
					{
					{
					match(MOD);
					
										op1 = n;
										n = new ExNode(NodeType.MOD);
										n.addChild(op1);
									
					}
					}
					op2=val06();
					n.addChild(op2);
				}
				else {
					break _loop403;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_10);
		}
		return n;
	}
	
	public final Node  val06() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case ID:
			case MINUS:
			case STRING:
			case STRINGSQ:
			case OB:
			case NUMBER:
			case SH:
			{
				n=val07();
				break;
			}
			case NOT:
			{
				match(NOT);
				n=val07();
				
						op1 = new ExNode(NodeType.NOT); op1.addChild(n); n = op1;
					
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
		return n;
	}
	
	public final Node  val07() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		
			Node op1 = null, op2 = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case ID:
			case STRING:
			case STRINGSQ:
			case OB:
			case NUMBER:
			case SH:
			{
				n=val08();
				break;
			}
			case MINUS:
			{
				match(MINUS);
				n=val08();
				
						op1 = new ExNode(NodeType.NEG); op1.addChild(n); n = op1;
					
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
		return n;
	}
	
	public final Node  val08() throws RecognitionException, TokenStreamException {
		Node n = null;
		
		Token  s = null;
		Token  sq = null;
		Token  id2 = null;
		Token  jid2 = null;
		Token  ip = null;
		Token  id = null;
		Token  jid = null;
		Token  p = null;
		
			Node e = null;
			String ts = "";
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING:
			{
				s = LT(1);
				match(STRING);
				
						ts = s.getText(); ts = ts.substring(1, ts.length() - 1); n = new OpNode(ts);
					
				break;
			}
			case STRINGSQ:
			{
				sq = LT(1);
				match(STRINGSQ);
				
						ts = sq.getText(); ts = ts.substring(1, ts.length() - 1); n = new OpNode(ts);
					
				break;
			}
			case NUMBER:
			{
				p = LT(1);
				match(NUMBER);
				n = new OpNode(Integer.parseInt(p.getText()));
				break;
			}
			case OB:
			{
				match(OB);
				n=expr();
				match(CB);
				break;
			}
			case SH:
			{
				match(SH);
				n=expr();
				match(SH);
				break;
			}
			default:
				if ((LA(1)==ID) && (LA(2)==OSB)) {
					{
					id2 = LT(1);
					match(ID);
					match(OSB);
					e=expr();
					match(CSB);
					n = new IndexNode(); n.setValue(id2.getText());
					{
					_loop409:
					do {
						if ((LA(1)==DOT)) {
							match(DOT);
							jid2 = LT(1);
							match(ID);
							e = new Node(); e.setValue(jid2.getText()); n.addChild(e);
						}
						else {
							break _loop409;
						}
						
					} while (true);
					}
					}
				}
				else if ((LA(1)==ID) && (LA(2)==OB)) {
					ip = LT(1);
					match(ID);
					
								//int i = 0;
								n = new FuncNode();
								n.setValue(ip.getText());
							
					match(OB);
					{
					switch ( LA(1)) {
					case ID:
					case NOT:
					case MINUS:
					case STRING:
					case STRINGSQ:
					case OB:
					case NUMBER:
					case SH:
					{
						e=expr();
						
										//n.setParameter("param" + i++, e);
										n.addChild(e);
									
						{
						_loop412:
						do {
							if ((LA(1)==COM)) {
								match(COM);
								e=expr();
								
													//n.setParameter("param" + i++, e);
													n.addChild(e);
												
							}
							else {
								break _loop412;
							}
							
						} while (true);
						}
						break;
					}
					case CB:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(CB);
				}
				else if ((LA(1)==ID) && (_tokenSet_12.member(LA(2)))) {
					{
					id = LT(1);
					match(ID);
					n = new IdentifNode(); n.setValue(id.getText());
					{
					_loop415:
					do {
						if ((LA(1)==DOT)) {
							match(DOT);
							jid = LT(1);
							match(ID);
							e = new Node(); e.setValue(jid.getText()); n.addChild(e);
						}
						else {
							break _loop415;
						}
						
					} while (true);
					}
					}
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
		return n;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"ID",
		"DOT",
		"SGEQ",
		"\"or\"",
		"\"and\"",
		"\"is\"",
		"\"eq\"",
		"\"not\"",
		"\"neq\"",
		"\"gt\"",
		"\"lt\"",
		"\"less\"",
		"\"than\"",
		"\"equal\"",
		"\"to\"",
		"\"le\"",
		"\"lte\"",
		"\"greater\"",
		"\"ge\"",
		"\"gte\"",
		"CAT",
		"MINUS",
		"PLUS",
		"\"mod\"",
		"STRING",
		"STRINGSQ",
		"OSB",
		"CSB",
		"OB",
		"COM",
		"CB",
		"NUMBER",
		"SH",
		"LETTER",
		"DIGIT",
		"CAB",
		"WS"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 96636764162L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 96636764290L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 16446976L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 108213045264L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 137438625714L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 137438691250L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 96636764546L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 96653211522L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 96669988738L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 96770652034L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 96904869762L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 96904869794L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	
	}
