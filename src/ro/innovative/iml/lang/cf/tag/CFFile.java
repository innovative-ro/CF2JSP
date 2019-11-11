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
 
package ro.innovative.iml.lang.cf.tag;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.oreilly.servlet.multipart.*;


public class CFFile extends TagSupport{
	
	private static final long serialVersionUID = 1952152815319631114L;
	String action = null;
	String file = null;
	String output = null;
	String addnewline = "Yes";
	String attributes = null;
	String mode = null;
	String charset = null;
	String source = null;
	String destination = null;
	Object variable = null;
	String filefield = null;
    String nameconflict = "Error";
    String accept = "";

   
	public void setAction(String arg){
		this.action = arg;
	}
	public String getAction(){
			return this.action;
	}
	// for action=append
	public void setFile(String arg){
			this.file = arg;
	}
	public String getFile(){
			return this.file;
	}
	public void setOutput(String arg){
				this.output = arg;
	}
	public String getOutput(){
				return this.output;
	}
	public void setAddnewline(String arg){
					this.addnewline = arg;
	}
	public String getAddnewline(){
					return this.addnewline;
	}
	public void setAttributes(String arg){
					this.attributes = arg;
	}
	public String getAttributes(){
					return this.attributes;
	}
	public void setMode(String arg){
				this.mode = arg;
	}
	public String getMode(){
				return this.mode;
	}
	public void setCharset(String arg){
					this.charset = arg;
	}
	public String getCharset(){
					return this.charset;
	}
	//for action=copy
	public void setSource(String arg){
						this.source = arg;
	}
	public String getSource(){
						return this.source;
	}
	public void setDestination(String arg){
						this.destination = arg;
	}
	public String getDestination(){
						return this.destination;
	}
	//for action=read
	public void setVariable(Object arg){
						this.variable = arg;
	}
	public Object getVariable(){
						return this.variable;
	}
	//for action=upload
	public void setfilefield(String arg){
				this.filefield = arg;
	}
	public String getfilefield(){
				return this.filefield;
	}
	public void setNameConflict(String arg){
					this.nameconflict = arg;
	}
	public String getNameConflict(){
					return this.nameconflict;
	}
	public void setAccept(String arg){
							this.accept = arg;
	}
	public String getAccept(){
							return this.accept;
	}




	public int doStartTag() throws JspException	{
		
		if (action.toLowerCase().equals("append")){
			try{
				File fisier = new File(file);
				BufferedWriter fis = new BufferedWriter(new FileWriter(fisier,true));
				fis.append(output);
				if (addnewline.equalsIgnoreCase("no")){
					
				}else{
					fis.append("\n");
				}
				fis.close();
			}catch(Exception e){
				//
			}
			
		}else if (action.toLowerCase().equals("copy")){
			try {
				copy(new File(source),new File(destination));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (action.toLowerCase().equals("delete")){
			(new File(file)).delete();
		}else if (action.toLowerCase().equals("move")){
			try {
				copy(new File(source),new File(destination));
			} catch (IOException e) {
				e.printStackTrace();
			}
            (new File(file)).delete();
		}else if (action.toLowerCase().equals("read")){
			variable = read(new File(file));
		}else if (action.toLowerCase().equals("readbinary")){
			try {
				variable = readBinary(new File(file));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (action.toLowerCase().equals("rename")){
			(new File(source)).renameTo(new File(destination));
		}else if (action.toLowerCase().equals("upload")){
			//File dir = new File(destination);
			/*MultipartRequest multi=null;
			try {
				
					multi = new MultipartRequest((HttpServletRequest)pageContext.getRequest(), destination,1024*1024,"ISO-8859-1", new DefaultFileRenamePolicy());
					String mimeType = multi.getContentType(filefield);
					if (accept.indexOf(mimeType) < 0){
						throw new JspException("Unaccepted file type");
					}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			MultipartParser mp = null;
			try {
				mp = new MultipartParser((HttpServletRequest) pageContext
						.getRequest(), 10 * 1024 * 1024); // 10MB
				Part part;
				while ((part = mp.readNextPart()) != null) {
					String nume = part.getName();
					if (nume.equalsIgnoreCase(filefield) && part.isFile()) {
						FilePart filePart = (FilePart) part;
						String fileName = filePart.getFileName();
						String contentType = filePart.getContentType();
						if (accept.indexOf(contentType) < 0) {
							throw new JspException("Unaccepted file type");
						} else {
							File uploadDir = new File(destination);
							if (!uploadDir.exists()) {
								uploadDir.mkdir();
							}
							long size = filePart.writeTo(uploadDir);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (action.toLowerCase().equals("write")){
			try{
				File fisier = new File(file);
				BufferedWriter fis = new BufferedWriter(new FileWriter(fisier));
				fis.append(output);
				if (addnewline.equalsIgnoreCase("no")){
				//nothing	
				}else{
					fis.append("\n");
				}
				fis.close();
			}catch(Exception e){
				//
			}
		}else {throw new JspException("bad action!");}



		return SKIP_BODY;
	}

	void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    String read(File infilename){
		StringBuffer tempVar=null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(infilename));
			String str;
			while ((str = in.readLine()) != null) {
					tempVar.append(str);
					}
			in.close();
			} catch (IOException e) {e.printStackTrace();};
		return (String) tempVar.toString();
	}

	byte[] readBinary(File infilename) throws IOException {
			InputStream is = new FileInputStream(infilename);
			long length = infilename.length();
			byte[] bytes = new byte[(int)length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			            offset += numRead;
			        }

			if (offset < bytes.length) {
			            throw new IOException("Could not completely read file "+infilename.getName());
			        }

			is.close();
			return bytes;
	}

	public int doEndTag(){
		return EVAL_PAGE;
	}

}
