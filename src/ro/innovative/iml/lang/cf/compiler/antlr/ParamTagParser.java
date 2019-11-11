// $ANTLR : "ParamTag.g" -> "ParamTagParser.java"$

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

public class ParamTagParser extends antlr.LLkParser       implements ParamTagParserTokenTypes
 {

protected ParamTagParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public ParamTagParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected ParamTagParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public ParamTagParser(TokenStream lexer) {
  this(lexer,1);
}

public ParamTagParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final ParamTagNode  gen() throws RecognitionException, TokenStreamException {
		ParamTagNode n = new ParamTagNode();
		
		Token  i = null;
		Token  s = null;
		Token  z = null;
		Token  m = null;
		
			Node e = null;
			String tz = "";
		
		
		try {      // for error handling
			{
			_loop94:
			do {
				if ((LA(1)==IDENT)) {
					i = LT(1);
					match(IDENT);
					match(EQ);
					{
					switch ( LA(1)) {
					case STRING:
					{
						{
						s = LT(1);
						match(STRING);
						
										e = new Node();
										tz = s.getText();
										tz = tz.substring(1, tz.length() - 1);
										e.setValue(tz);
										n.setParameter(i.getText().toLowerCase(), e);
									
						}
						break;
					}
					case DSTRING:
					{
						{
						z = LT(1);
						match(DSTRING);
						
										e = new Node();
										tz = z.getText();
										//tz = tz.substring(1, tz.length() - 1);
										e.setValue(tz);
										n.setParameter(i.getText().toLowerCase(), e);
									
						}
						break;
					}
					case NUMBER:
					{
						{
						m = LT(1);
						match(NUMBER);
						
										e = new Node();
										e.setValue(m.getText());
										n.setParameter(i.getText(), e);
									
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					break _loop94;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case SLASH:
			{
				match(SLASH);
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return n;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"IDENT",
		"EQ",
		"STRING",
		"DSTRING",
		"NUMBER",
		"SLASH",
		"CB",
		"WS"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}
