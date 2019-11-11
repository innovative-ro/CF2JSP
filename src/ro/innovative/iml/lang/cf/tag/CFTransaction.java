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

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

public class CFTransaction extends TagSupport {

    private static final long serialVersionUID = -4679312245041537607L;

    private String action;
    private String isolation;

    private boolean transactionFinished;
    private String dataSourceInUse;
    private Connection con;

    public int doStartTag() throws JspException {
	if (getAction() == null || getAction().equalsIgnoreCase("begin")) {
	    init();
	    return EVAL_BODY_INCLUDE;
	} else {
	    if (getAction().equalsIgnoreCase("commit")) {
		try {
		    con.commit();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		transactionFinished = true;
	    } else if (getAction().equalsIgnoreCase("rollback")) {
		try {
		    con.rollback();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		transactionFinished = true;
	    }
	    return SKIP_BODY;
	}
    }

    public int doEndTag() throws JspException {
	if (con != null)
	    try {
		con.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	return EVAL_PAGE;
    }

    private void init() {
	transactionFinished = true;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public String getIsolation() {
	return isolation;
    }

    public void setIsolation(String isolation) {
	this.isolation = isolation;
    }

    public Connection getConnection(String datasource) throws JspException {
	if (dataSourceInUse == null) {
	    return newConn(datasource);
	} else if (datasource.equals(dataSourceInUse))
	    return con;
	else if (transactionFinished) {
	    return newConn(datasource);
	} else
	    throw new JspException(
		    "<cftransaction> the transaction must be commited/rolled before changing the datasource");
    }

    private Connection newConn(String datasource) throws JspException {
	try {
	    Context ctx = new InitialContext();
	    DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + datasource);
	    con = ds.getConnection();
	    dataSourceInUse = datasource;
	    
	    if (getIsolation() != null)
		if (getIsolation().equalsIgnoreCase("read_uncommitted"))
		    con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		else if (getIsolation().equalsIgnoreCase("read_committed"))
		    con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		else if (getIsolation().equalsIgnoreCase("repeatable_read"))
		    con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		else if (getIsolation().equalsIgnoreCase("serializable"))
		    con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		else
		    throw new JspException("<cftransaction> attribute isolation has invalid value");
	    else
		con.setTransactionIsolation(con.getTransactionIsolation());
	    
	    con.setAutoCommit(false);
	} catch (NamingException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	transactionFinished = false;

	return con;
    }

}
