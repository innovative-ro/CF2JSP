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
 
package ro.innovative.iml.lang.cf.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;


public class URLParser extends InputStreamReader {
	static URLParserState state[];


	static {
		state = new URLParserState[41];
		//start tag
		state[0] = new URLParserState(new char[] { '<' }, new int[] { 1 }, 0);
		//a Tag
		state[1] = new URLParserState(new char[] { 'a' }, new int[] { 2 }, 0);
		state[2] = new URLParserState(new char[] { 'p',' ','\t','\n' },new int[]{19,3,3,3}, 0);
		//a href=
		state[3] = new URLParserState(new char[] { 'h','>' },new int[]{4,0}, 3);
		state[4] = new URLParserState(new char[] { 'r','>' },new int[]{5,0}, 3);
		state[5] = new URLParserState(new char[] { 'e','>' },new int[]{6,0}, 3);
		state[6] = new URLParserState(new char[] { 'f','>' },new int[]{7,0}, 3);
		state[7] = new URLParserState(new char[] { '=',' ','\t','\n' },new int[]{8,7,7,7}, 0);
		state[8] = new URLParserState(new char[] { '"','\'',' ','\t','\n','>' },new int[]{9,13,8,8,8,0}, 17);
		//double quoted URL
		state[9] = new URLParserState(true, new char[] { '\\','"'},new int[]{10,12}, 11);
		state[10] = new URLParserState(true, new char[] { },new int[]{ }, 11);
		state[11] = new URLParserState(true, new char[] { '"','\\'},new int[]{12,10}, 11);
		state[12] = new URLParserState(new char[] { '>'},new int[]{0}, 12);
		//single quoted URL
		state[13] = new URLParserState(true, new char[] { '\\','\''},new int[]{14,16}, 15);
		state[14] = new URLParserState(true, new char[] { },new int[]{ }, 15);
		state[15] = new URLParserState(true, new char[] { '\'','\\'},new int[]{16,14}, 15);
		state[16] = new URLParserState(new char[] { '>'},new int[]{0}, 16);
		//unquoted URL
		state[17] = new URLParserState(true, new char[] { '>',' ','\t','\n'},new int[]{0,18,18,18}, 17);
		state[18] = new URLParserState(new char[] { '>'},new int[]{0}, 18);
		
		//applet Tag
		state[19] = new URLParserState(new char[] { 'p' }, new int[] { 20 }, 0);
		state[20] = new URLParserState(new char[] { 'l' }, new int[] { 21 }, 0);
		state[21] = new URLParserState(new char[] { 'e' }, new int[] { 22 }, 0);
		state[22] = new URLParserState(new char[] { 't' }, new int[] { 23 }, 0);
		state[23] = new URLParserState(new char[] { ' ','\t','\n' }, new int[] { 25,25,25 }, 0);
		
		//applet code=
		state[25] = new URLParserState(new char[] { 'c','>' },new int[]{26,0}, 19);
		state[26] = new URLParserState(new char[] { 'o','>' },new int[]{27,0}, 19);
		state[27] = new URLParserState(new char[] { 'd','>' },new int[]{28,0}, 19);
		state[28] = new URLParserState(new char[] { 'e','>' },new int[]{29,0}, 19);
		state[29] = new URLParserState(new char[] { '=',' ','\t','\n' },new int[]{30,29,29,29}, 0);
		state[30] = new URLParserState(new char[] { '"','\'',' ','\t','\n','>' },new int[]{31,35,30,30,30,0}, 39);
		//double quoted URL
		state[31] = new URLParserState(true, new char[] { '\\','"'},new int[]{32,34}, 33);
		state[32] = new URLParserState(true, new char[] { },new int[]{ }, 33);
		state[33] = new URLParserState(true, new char[] { '"','\\'},new int[]{34,32}, 33);
		state[34] = new URLParserState(new char[] { '>'},new int[]{0}, 34);
		//single quoted URL
		state[35] = new URLParserState(true, new char[] { '\\','\''},new int[]{36,38}, 37);
		state[36] = new URLParserState(true, new char[] { },new int[]{ }, 37);
		state[37] = new URLParserState(true, new char[] { '\'','\\'},new int[]{38,36}, 37);
		state[38] = new URLParserState(new char[] { '>'},new int[]{0}, 38);
		//unquoted URL
		state[39] = new URLParserState(true, new char[] { '>',' ','\t','\n'},new int[]{0,40,40,40}, 39);
		state[40] = new URLParserState(new char[] { '>'},new int[]{0}, 40);
	}

	public static void main(String args[])throws Exception{
		URLParser s = new URLParser(new java.io.FileInputStream("/home/radu/test.html"),new java.net.URL("http://foo/bar/home/radu/test.html"));
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
	private StringBuffer buff = new StringBuffer();
	private int buffPointer = 0;
	//private String prefix;
	private java.net.URL url; 

	public URLParser(InputStream s) {
		super(s);
	}
	/**
	 * @deprecated
	 */
	public URLParser(InputStream s, String prefix) {
		super(s);
		//this.prefix = prefix;
	}
	public URLParser(InputStream s, java.net.URL url) {
		super(s);
		this.url = url;
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
	private void parseURL(){
		//if url is absolute do noithing
		if(buff.toString().toLowerCase().indexOf("http://")>=0)
			return;
		switch(buff.charAt(0)){
		/*
		 * this is for thedailyWTF.com
		 */
		case '"':
			buff = new StringBuffer('"' +url.getProtocol()+"://"+
					url.getHost()+
					//url might be relative to curent path or relative to the root of the server
					(buff.substring(1).trim().charAt(0)=='/'?"":url.getPath().substring(0, url.getPath().lastIndexOf('/')+1))+
					buff.substring(1).trim());
			break;
		case '\'':
			buff = new StringBuffer('\'' +url.getProtocol()+"://"+
					url.getHost()+
					//url might be relative to curent path or relative to the root of the server
					(buff.substring(1).trim().charAt(0)=='/'?"":url.getPath().substring(0, url.getPath().lastIndexOf('/')+1))+
					buff.substring(1).trim());
			break;
		default:
			buff = new StringBuffer(url.getProtocol()+"://"+
					url.getHost()+
					//url might be relative to curent path or relative to the root of the server
					(buff.charAt(0)=='/'?"":url.getPath().substring(0, url.getPath().lastIndexOf('/')+1))+ buff);
		}
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
		throw new IOException("Oups.. i haven't gotten around tom implementing the behaviour of this method yeat! My bad..");
		//return super.read(target);
	}

	private int readFromBuffer() {
		int c;
		c = buff.charAt(buffPointer);
		buffPointer++;
		if(buffPointer>=buff.length()){
			buffPointer = 0;
			buff = new StringBuffer();
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
