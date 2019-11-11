// $ANTLR : "Registry.g" -> "RegistryParser.java"$

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

public class RegistryParser extends antlr.LLkParser       implements RegistryParserTokenTypes
 {

protected RegistryParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public RegistryParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected RegistryParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public RegistryParser(TokenStream lexer) {
  this(lexer,1);
}

public RegistryParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final HashMap  gen() throws RecognitionException, TokenStreamException {
		HashMap m = new HashMap();
		
		Token  hs = null;
		Token  key = null;
		Token  val = null;
		
			String s1 = null, s2 = null, s3 = null;
			HashMap l = null; 
		
		
		try {      // for error handling
			{
			_loop263:
			do {
				if ((LA(1)==HSTRING)) {
					{
					hs = LT(1);
					match(HSTRING);
					l = new HashMap();
					{
					_loop262:
					do {
						if ((LA(1)==STRING)) {
							key = LT(1);
							match(STRING);
							
											s2 = key.getText();
											s2 = s2.substring(1, s2.length() - 1);
										
							match(EQ);
							val = LT(1);
							match(STRING);
							
											s3 = val.getText();
											s3 = s3.substring(1, s3.length() - 1);
											l.put(s2, s3);
										
						}
						else {
							break _loop262;
						}
						
					} while (true);
					}
					}
						
								s1 = hs.getText();
								s1 = s1.substring(1, s1.length() - 1);
								m.put(s1, l);
							
				}
				else {
					break _loop263;
				}
				
			} while (true);
			}
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		return m;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"HSTRING",
		"STRING",
		"EQ",
		"WS"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}
