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
 
package ro.innovative.iml.lang.cf.node;

public class ExNode extends Node
{
	protected NodeType type;
	
	public ExNode(NodeType type)
	{
		this.type = type; 
	}
	
	public String toString()
	{
		if (type == NodeType.NOT)
			return "cfo:cfnot(" + ((Node)getChildren().get(0)) + ")";
		else if (type == NodeType.NEG)
			return "cfo:neg(" + ((Node)getChildren().get(0)) + ")";
		else
			return getBinOp();
	}
	
	/*public String getJSP()
	{
		return null;
	}*/
	
	private String getBinOp()
	{
		String s = null;
		if (type == NodeType.IMP)
			s = "cfimp";
		else if (type == NodeType.AND)
			s = "cfand";
		else if (type == NodeType.EQV)
			s = "cfeqv";
		else if (type == NodeType.OR)
			s = "cfor";
		else if (type == NodeType.XOR)
			s = "cfxor";
		else if (type == NodeType.ADD)
			s = "add";
		else if (type == NodeType.SUB)
			s = "sub";
		else if (type == NodeType.MUL)
			s = "mul";
		else if (type == NodeType.DIV)
			s = "cdiv";
		else if (type == NodeType.IDIV)
			s = "idiv";
		else if (type == NodeType.MOD)
			s = "cmod";
		else if (type == NodeType.POW)
			s = "pow";
		else if (type == NodeType.CAT)
			s = "cat";
		else if (type == NodeType.EQ)
			s = "cfeq";
		else if (type == NodeType.NEQ)
			s = "cfneq";
		else if (type == NodeType.LT)
			s = "less";
		else if (type == NodeType.LTE)
			s = "lte";
		else if (type == NodeType.GT)
			s = "greater";
		else if (type == NodeType.GTE)
			s = "gte";
		else if (type == NodeType.CONTAINS)
			s = "contains";
		else if (type == NodeType.DNC)
			s = "dnc";
		else
			;//throw new Exception("Aici nu se ajunge");
		return "cfo:" + s + "(" + ((Node)getChildren().get(0)) + ", " + ((Node)getChildren().get(1)) + ")";
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}
}
