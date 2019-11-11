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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.*;
import javax.sql.*;
import ro.innovative.iml.lang.cf.basis.CFQueryObj;
import ro.innovative.iml.lang.cf.util.Logger;
import ro.innovative.iml.lang.cf.util.QueryIterator;

public class CFQuery extends BodyTagSupport {

    private static final long serialVersionUID = 677345104485418962L;

    public String name;

    public String datasource = null;

    public String dbtype = null;

    public String username = null;

    public String password = null;

    public int maxrows = 100;

    public int blockfactor = 100;

    public int timeout;

    public java.util.Date cachedafter = null;

    // java.util.Date cachedWithin = null;
    public boolean debug;

    // private Connection conn = null;

    private ResultSet rs;

    // private long datars;

    public ResultSet getRs() {
	return rs;
    }

    public int doStartTag() throws JspException {
	return super.doStartTag();

    }

    public int doEndTag() throws JspException {
	try {
	    CFTransaction parentTr = (CFTransaction) findAncestorWithClass(this, CFTransaction.class);
	    Connection conn;

	    if (parentTr == null) {
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/" + getDatasource());
		conn = ds.getConnection();
		// where is DB
	    } else {
		conn = parentTr.getConnection("java:comp/env/" + getDatasource());
	    }

	    if (conn != null) {

		String body = getBodyContent().getString().trim();
		Logger.println(pageContext, "<cfquery " + body + ">");

		if (body.trim().substring(0, 6).equalsIgnoreCase("SELECT")) {

		    Statement s = conn.createStatement();
		    s.setFetchSize(getMaxrows());

		    s.executeQuery(body);

		    rs = s.getResultSet();
		    CFQueryObj cfqo = new QueryIterator(rs);

		    Map<String, Object> queryMap = new HashMap<String, Object>();
		    queryMap.put("_fieldThatWillNeverBeUsedAsATableColumn", cfqo);
		    queryMap.put("recordcount", cfqo.getRecordcount());
		    queryMap.put("currentrow", cfqo.getCurrentrow());
		    queryMap.put("columnlist", cfqo.getColumnlist());
		    queryMap.put("columnnames", cfqo.getColumnnames());

		    if (cfqo.hasNext()) {
			Object next = cfqo.next();

			int i = 0;
			for (String col : cfqo.getColumnnames())
			    queryMap.put(col.toLowerCase(), ((Object[]) next)[i++]);
		    } else {
			for (String col : cfqo.getColumnnames())
			    queryMap.put(col.toLowerCase(), "");
		    }

		    pageContext.setAttribute(getName().toLowerCase(), queryMap);
		    pageContext.setAttribute("conn", conn);
		    pageContext.setAttribute("statement", conn);
		} else if (body.trim().substring(0, 6).equalsIgnoreCase("INSERT")) {
		    Statement s = conn.createStatement();
		    s.executeUpdate(body);
		    s.close();
		    conn.close();
		} else if (body.trim().substring(0, 6).equalsIgnoreCase("UPDATE")) {
		    Statement s = conn.createStatement();
		    s.executeUpdate(body);
		    s.close();
		    conn.close();
		} else if (body.trim().substring(0, 6).equalsIgnoreCase("DELETE")) {
		    Statement s = conn.createStatement();
		    s.executeUpdate(body);
		    s.close();
		    conn.close();
		} else {
		    Statement s = conn.createStatement();
		    s.executeUpdate(body);
		    s.close();
		    conn.close();
		}
	    } else {
		throw new JspException("conn is null");
	    }
	} catch (Exception e) {
	    throw new JspException(e);
	}

	return EVAL_PAGE;
    }

    // Optional Specifies the maximum number of rows to fetch at a time from the
    // server.
    // The range is 1 (default) to 100.

    public int getBlockfactor() {
	return blockfactor;
    }

    public void setBlockfactor(int blockfactor) {
	this.blockfactor = blockfactor;
    }

    // Optional. Specify a date value (for example, 4/16/98, April 16, 1999,
    // 4-16-99).
    // ColdFusion uses cached query data if the date of the original query is
    // after the date specified.

    public java.util.Date getCachedafter() {
	return cachedafter;
    }

    public void setCachedafter(java.util.Date cachedafter) {
	this.cachedafter = cachedafter;

    }

    // Required. The name of the data source from which this query should
    // retrieve data.
    public String getDatasource() {
	return datasource;
    }

    public void setDatasource(String datasource) {
	this.datasource = datasource;
    }

    // Optional. The database driver type:
    // * ODBC (default) -- ODBC driver.
    // * Oracle73 -- Oracle 7.3 native database driver. Using this option, the
    // ColdFusion Server computer must have Oracle 7.3.4.0.0 (or greater) client
    // software installed.
    // * Oracle80 --Oracle 8.0 native database driver. Using this option, the
    // ColdFusion Server computer must have Oracle 8.0 (or greater) client
    // software installed.
    // * Sybase11 --Sybase System 11 native database driver. Using this option,
    // the ColdFusion Server computer must have Sybase 11.1.1 (or greater)
    // client software installed. Sybase patch ebf 7729 is recommended.
    // * OLEDB --OLE DB provider. If specified, this database provider overrides
    // the driver type specified in the ColdFusion Administrator.
    // * DB2 --DB2 5.2 native database driver.
    // * Informix73 --Informix73 native database driver.

    public String getDbtype() {
	return dbtype;
    }

    public void setDbtype(String dbtype) {
	this.dbtype = dbtype;
    }

    public boolean isDebug() {
	return debug;
    }

    public void setDebug(boolean debug) {
	this.debug = debug;
    }

    // Optional. Specifies the maximum number of rows you want returned in the
    // record set.

    public int getMaxrows() {
	return maxrows;
    }

    public void setMaxrows(int maxrows) {
	this.maxrows = maxrows;
    }

    // Required. The name you assign to the query.
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    // Optional. If specified, PASSWORD overrides the password value specified
    // in the data source setup.

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	if (password == null) {

	} else {
	    this.password = password;
	}
    }

    // Optional. Lets you specify a maximum number of milliseconds for the query
    // to execute before returning an
    // error indicating that the query has timed-out.
    // This attribute is not supported by most ODBC drivers.
    // TIMEOUT is supported by the SQL Server 6.x or above driver.
    // The minimum and maximum allowable values vary, depending on the driver.

    public int getTimeout() {
	return timeout;
    }

    public void setTimeout(int timeout) {
	this.timeout = timeout;
    }

    // Optional. If specified, USERNAME overrides the username value specified
    // in the data source setup.
    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	if (username == null) {

	} else {
	    this.username = username;
	}
    }

}
