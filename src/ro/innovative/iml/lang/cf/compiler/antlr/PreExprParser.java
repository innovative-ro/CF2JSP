// $ANTLR : "PreExpr.g" -> "PreExprParser.java"$

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

public class PreExprParser extends antlr.LLkParser       implements PreExprParserTokenTypes
 {

protected PreExprParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public PreExprParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected PreExprParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public PreExprParser(TokenStream lexer) {
  this(lexer,2);
}

public PreExprParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
}

	public final String  gen() throws RecognitionException, TokenStreamException {
		String n = null;
		
		
			StringBuilder sb = new StringBuilder();
			String m;
		
		
		try {      // for error handling
			{
			int _cnt276=0;
			_loop276:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					m=expr();
					sb.append(m);
				}
				else {
					if ( _cnt276>=1 ) { break _loop276; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt276++;
			} while (true);
			}
			match(CAB);
			n = sb.toString();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return n;
	}
	
	public final String  expr() throws RecognitionException, TokenStreamException {
		String n = null;
		
		Token  ws = null;
		Token  s = null;
		Token  sq = null;
		Token  ip = null;
		Token  p = null;
		
			Node e = null;
			String ts = "";
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case WS:
			{
				ws = LT(1);
				match(WS);
				n = ws.getText();
				break;
			}
			case STRING:
			{
				s = LT(1);
				match(STRING);
				n = s.getText();
				break;
			}
			case STRINGSQ:
			{
				sq = LT(1);
				match(STRINGSQ);
				n = sq.getText();
				break;
			}
			case ID:
			{
				ip = LT(1);
				match(ID);
				n= ip.getText();
				break;
			}
			case OB:
			{
				match(OB);
				n = "(";
				break;
			}
			case CB:
			{
				match(CB);
				n = ")";
				break;
			}
			case OSB:
			{
				match(OSB);
				n = "(";
				break;
			}
			case CSB:
			{
				match(CSB);
				n = ")";
				break;
			}
			case POINT:
			{
				match(POINT);
				n = ".";
				break;
			}
			case CAT:
			{
				match(CAT);
				n = "&";
				break;
			}
			case PLUS:
			{
				match(PLUS);
				n = "+";
				break;
			}
			case MINUS:
			{
				match(MINUS);
				n = "-";
				break;
			}
			case COM:
			{
				match(COM);
				n = ",";
				break;
			}
			case NUMBER:
			{
				p = LT(1);
				match(NUMBER);
				n = p.getText();
				break;
			}
			case SH:
			{
				match(SH);
				n = "#";
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
			recover(ex,_tokenSet_2);
		}
		return n;
	}
	
	public final Node  cfset() throws RecognitionException, TokenStreamException {
		Node n = new Node();
		
		Token  i = null;
		Token  j = null;
		
			StringBuilder sb = new StringBuilder();
			String m;
			String id;
		
		
		try {      // for error handling
			{
			match(WS);
			}
			{
			switch ( LA(1)) {
			case SH:
			{
				match(SH);
				break;
			}
			case WS:
			case ID:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case WS:
			{
				match(WS);
				break;
			}
			case ID:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			i = LT(1);
			match(ID);
			id = i.getText();
			{
			_loop282:
			do {
				if ((LA(1)==POINT)) {
					match(POINT);
					j = LT(1);
					match(ID);
					id += "." + j.getText();
				}
				else {
					break _loop282;
				}
				
			} while (true);
			}
			{
			if ((LA(1)==WS) && (LA(2)==WS||LA(2)==SH||LA(2)==SGEQ)) {
				match(WS);
			}
			else if ((LA(1)==WS||LA(1)==SH||LA(1)==SGEQ) && ((LA(2) >= WS && LA(2) <= NUMBER))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			switch ( LA(1)) {
			case SH:
			{
				match(SH);
				break;
			}
			case WS:
			case SGEQ:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case WS:
			{
				match(WS);
				break;
			}
			case SGEQ:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SGEQ);
			{
			int _cnt287=0;
			_loop287:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					m=expr();
					sb.append(m);
				}
				else {
					if ( _cnt287>=1 ) { break _loop287; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt287++;
			} while (true);
			}
			match(CAB);
			
					n.addChild(new Node(id));
					n.addChild(new Node(sb.toString()));
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		return n;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"CAB",
		"WS",
		"SH",
		"ID",
		"POINT",
		"SGEQ",
		"STRING",
		"STRINGSQ",
		"OB",
		"CB",
		"OSB",
		"CSB",
		"CAT",
		"PLUS",
		"MINUS",
		"COM",
		"NUMBER",
		"LETTER",
		"DIGIT"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2096608L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 2096624L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
