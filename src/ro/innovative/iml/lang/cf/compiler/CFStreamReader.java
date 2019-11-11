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
 
package ro.innovative.iml.lang.cf.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import ro.innovative.iml.lang.cf.util.URLParserState;


public class CFStreamReader extends InputStreamReader {
	static URLParserState state[];
	int lineNumber = 0;
	InputStream s;
	static {
		state = new URLParserState[60];
		//start tag
		state[0] = new URLParserState(new char[] { '<' }, new int[] { 1 }, 0);
		//a Tag
		state[1] = new URLParserState(new char[] { 'a','f','<' }, new int[] { 2,19,1 }, 0);
		state[2] = new URLParserState(new char[] { ' ','\t','\n','<' },new int[]{3,3,3,1}, 0);
		//a href=
		state[3] = new URLParserState(new char[] { 'h','>','<' },new int[]{4,0,1}, 3);
		state[4] = new URLParserState(new char[] { 'r','>','<' },new int[]{5,0,1}, 3);
		state[5] = new URLParserState(new char[] { 'e','>','<' },new int[]{6,0,1}, 3);
		state[6] = new URLParserState(new char[] { 'f','>','<' },new int[]{7,0,1}, 3);
		state[7] = new URLParserState(new char[] { '=',' ','\t','\n','<' },new int[]{8,7,7,7,1}, 0);
		state[8] = new URLParserState(new char[] { '"','\'',' ','\t','\n','>' },new int[]{9,13,8,8,8,0}, 17);
		//double quoted URL
		state[9] = new URLParserState(true, new char[] { '\\','"'},new int[]{10,12}, 11);
		state[10] = new URLParserState(true, new char[] { },new int[]{ }, 11);
		state[11] = new URLParserState(true, new char[] { '"','\\'},new int[]{12,10}, 11);
		state[12] = new URLParserState(new char[] { },new int[]{}, 0);
		//single quoted URL
		state[13] = new URLParserState(true, new char[] { '\\','\''},new int[]{14,16}, 15);
		state[14] = new URLParserState(true, new char[] { },new int[]{ }, 15);
		state[15] = new URLParserState(true, new char[] { '\'','\\'},new int[]{16,14}, 15);
		state[16] = new URLParserState(new char[] { },new int[]{}, 0);
		//unquoted URL
		state[17] = new URLParserState(true, new char[] { '>',' ','\t','\n'},new int[]{0,18,18,18}, 17);
		state[18] = new URLParserState(new char[] { },new int[]{}, 0);
		
		//form or frame Tag
		state[19] = new URLParserState(new char[] { 'o','r','<' }, new int[] { 20,41,1 }, 0);
		//form
		state[20] = new URLParserState(new char[] { 'r','<' }, new int[] { 21,1 }, 0);
		state[21] = new URLParserState(new char[] { 'm','<' }, new int[] { 22,1 }, 0);
		state[22] = new URLParserState(new char[] { ' ','\t','\n','<' }, new int[] { 23,23,23,1 }, 0);
		
		state[23] = new URLParserState(new char[] { 'a','>','<' },new int[]{24,0,1}, 23);
		state[24] = new URLParserState(new char[] { 'c','>','<' },new int[]{25,0,1}, 23);
		state[25] = new URLParserState(new char[] { 't','>','<' },new int[]{26,0,1}, 23);
		state[26] = new URLParserState(new char[] { 'i','>','<' },new int[]{27,0,1}, 23);
		state[27] = new URLParserState(new char[] { 'o','>','<' },new int[]{28,0,1}, 23);
		state[28] = new URLParserState(new char[] { 'n','>','<' },new int[]{29,0,1}, 23);
		state[29] = new URLParserState(new char[] { '=',' ','\t','\n','<' },new int[]{30,29,29,29,1}, 0);
		state[30] = new URLParserState(new char[] { '"','\'',' ','\t','\n','>' },new int[]{13,37,30,30,30,0}, 39);
//		double quoted URL
		state[31] = new URLParserState(true, new char[] { '\\','"'},new int[]{32,34}, 33);
		state[32] = new URLParserState(true, new char[] { },new int[]{ }, 33);
		state[33] = new URLParserState(true, new char[] { '"','\\'},new int[]{34,32}, 33);
		state[34] = new URLParserState(new char[] { },new int[]{}, 0);
		//single quoted URL
		state[35] = new URLParserState(true, new char[] { '\\','\''},new int[]{36,38}, 37);
		state[36] = new URLParserState(true, new char[] { },new int[]{ }, 37);
		state[37] = new URLParserState(true, new char[] { '\'','\\'},new int[]{38,36}, 37);
		state[38] = new URLParserState(new char[] { },new int[]{}, 0);
		//unquoted URL
		state[39] = new URLParserState(true, new char[] { '>',' ','\t','\n'},new int[]{0,40,40,40}, 39);
		state[40] = new URLParserState(new char[] { },new int[]{}, 0);
		
		//frame
		state[41] = new URLParserState(new char[] { 'a','<' }, new int[] { 42,1 }, 0);
		state[42] = new URLParserState(new char[] { 'm','<' }, new int[] { 43,1 }, 0);
		state[43] = new URLParserState(new char[] { 'e','<' }, new int[] { 44,1 }, 0);
		state[44] = new URLParserState(new char[] { ' ','\t','\n','<' }, new int[] { 45,45,45,1 }, 0);
		

		state[45] = new URLParserState(new char[] { 's','>','<' },new int[]{46,0,1}, 45);
		state[46] = new URLParserState(new char[] { 'r','>','<' },new int[]{47,0,1}, 45);
		state[47] = new URLParserState(new char[] { 'c','>','<' },new int[]{48,0,1}, 45);
		state[48] = new URLParserState(new char[] { '=',' ','\t','\n','<' },new int[]{49,48,48,48,1}, 0);
		state[49] = new URLParserState(new char[] { '"','\'',' ','\t','\n','>' },new int[]{50,54,49,49,49,0}, 58);
		
//		double quoted URL
		state[50] = new URLParserState(true, new char[] { '\\','"'},new int[]{51,53}, 52);
		state[51] = new URLParserState(true, new char[] { },new int[]{ }, 52);
		state[52] = new URLParserState(true, new char[] { '"','\\'},new int[]{53,51}, 52);
		state[53] = new URLParserState(new char[] { },new int[]{}, 0);
		//single quoted URL
		state[54] = new URLParserState(true, new char[] { '\\','\''},new int[]{55,57}, 56);
		state[55] = new URLParserState(true, new char[] { },new int[]{ }, 56);
		state[56] = new URLParserState(true, new char[] { '\'','\\'},new int[]{57,55}, 56);
		state[57] = new URLParserState(new char[] { },new int[]{}, 0);
		//unquoted URL
		state[58] = new URLParserState(true, new char[] { '>',' ','\t','\n'},new int[]{0,59,59,59}, 58);
		state[59] = new URLParserState(new char[] { },new int[]{}, 0);
		
	}

	public static void main(String args[])throws Exception{
		CFStreamReader s = new CFStreamReader(new java.io.FileInputStream("/home/radu/test.html"));
		int c;
		s.test();
		while((c=s.read())>=0){
			System.out.print((char)c);			
			//System.out.print("|"+s.sp+" ");
		}
		s.close();
	}
	/**
	 * State pointer. Determines the current state of the DFA;
	 */
	private int sp;
	private boolean EOF = false;
	private StringBuilder buff = new StringBuilder();
	private int buffPointer = 0;

	public CFStreamReader(InputStream s) {
		super(s);
		this.s= s;
	}
	
	/**
	 * Consume one caracter of input.
	 * @return the character consumed or -1 if EOF is reached.
	 * @throws IOException
	 */
	private int consume() throws IOException{
		int c = super.read();
		//test for end of file
		if(c<0){
			EOF = true;
			return c;
		}
		char ch = Character.toLowerCase((char)c);
		if(ch=='\n')
			lineNumber++;
		//attempt to match rules in the current state.
		for(int i = 0; i<state[sp].c.length;i++){
			if(state[sp].c[i] == ch){
				sp = state[sp].fnc[i];
				return c;
			}
		}
		// nothing has matched. go with the default rule.
		sp = state[sp].def;
		return c;
	}
	
	
	/**
	 * Transform a relative URL in buff to an absolute one.
	 * If URL is absolute leaves it unchanged. 
	 */
	private void parseURL() throws IOException{
		//if url is absolute do noithing
		String matched = buff.toString().trim();
		if(matched.toLowerCase().indexOf("http://")>=0)
			return;
		int pos = matched.indexOf('?');
		String args = null;
		if(pos>=0){
			args = matched.substring(pos);
			matched = matched.substring(0,matched.indexOf('?'));
		}
		if((pos = matched.lastIndexOf(".cfm"))>=0){
			buff = new StringBuilder(
					matched.subSequence(0, pos)+".jsp"+matched.substring(pos+4)
					);
			if(args!=null){
				buff.append(args);
				if(args.indexOf(".cfm")>=0)
					System.err.printf("Warning: the URL at line %d contains GET parameters that may need to be rewriten",lineNumber);
			}
		}
		//buff = new StringBuilder("\n+"+buff+"+\n");
			
	}

	public int read() throws IOException {
		int c;
		
		/* if bufferd data exists return a char 
		 * from that
		 */
		if(buffPointer < buff.length()){
			c = readFromBuffer();
			return c;
		}
		// no data in buffer, consume a char
		c = consume();
		/* if in "hungry" mode consume chars and 
		 * buffer them
		 */
		if(state[sp].fin){
			while(state[sp].fin && !EOF){
				if(c<0)
					EOF=true;
				else{
					buff.append((char)c);
					//buff.append((char)c+"|"+sp+" ");
					c = consume();
				}
			}
			/* not hungry any more. ad the last 
			 * char read to the buffer and it's
			 * time to parse the URL
			 */			
			buff.append((char)c);
			parseURL();
			
			//now we have buffered data. return one catacter from that
			if(buffPointer < buff.length()){
				c = readFromBuffer();
				return c;
			}
		}
		return c;
	}

	public int read(char[] cbuf) throws IOException {
		int val,i=0;
		if(cbuf.length==0)
			return 0;
		do{
			val=read();
			if(val>=0){
				cbuf[i]=(char)val;
				i++;
			}				
		}while(i<cbuf.length && val>=0);
		if(i==0) return val;//return -1 in case of EOF
		else return i;
		
	}
	
	public int read(char[] cbuf, int offset, int length) throws IOException {
		if(offset+length>cbuf.length)
			throw new IndexOutOfBoundsException();
		if(cbuf.length==0)
			return 0;
		int val,i=offset,end=offset+length;
		do{
			val=read();
			if(val>=0){
				cbuf[i]=(char)val;
				i++;
			}				
		}while(i<end && val>=0);
		if(i==0) return -1;//return -1 in case of EOF
		else return i;
	}
	
	public int read(CharBuffer target) throws IOException {
		throw new IOException("Oups.. i haven't gotten around to implementing the behaviour of this method yeat! My bad..");
		//return super.read(target);
	}

	private int readFromBuffer() {
		int c;
		c = buff.charAt(buffPointer);
		buffPointer++;
		if(buffPointer>=buff.length()){
			buffPointer = 0;
			buff = new StringBuilder();
		}
		return c;
	}

	/**
	 * test for possible errors in the definition of the DFA.
	 */
	private void test() throws Exception{
		for(int i = 0; i<state.length;i++){
			if(state[i]!=null)
				if(state[i].c.length!=state[i].fnc.length)
					throw new Exception("poorly defined DFA: length mismatch between c and fnc on state "+i);
		}
	}
}
