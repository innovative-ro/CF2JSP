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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.Vector;

import ro.innovative.iml.lang.cf.basis.*;

public class QueryIterator extends CFQueryObj {
	private int currentRow;
	private int recordCount;
	private ResultSet rs;
	private String columnList;
	private Vector<String> columnNames;

	public QueryIterator(ResultSet rs) throws SQLException {
		this.rs = rs;
		reset();
		populate();
	}

	private void populate() throws SQLException {
		// set RecordCount
		rs.last();
		// RecordCount = rs.
		recordCount = rs.getRow();
		rs.beforeFirst();
		// set columnList
		ResultSetMetaData rsmd = rs.getMetaData();
		if (rsmd.getColumnCount() > 0) {
			columnNames = new Vector<String>();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				columnNames.add(rsmd.getColumnName(i + 1));
				if (i == 0)
					columnList = rsmd.getColumnName(i + 1);
				else
					columnList += "," + rsmd.getColumnName(i + 1);
			}
		} else {
			columnList = "";
		}
	}

	public boolean hasNext() {
		if (recordCount <= 0)
			return false;
		try {
			return !rs.isLast();
		} catch (SQLException e) {
			return false;
		}
	}

	public Object next() {
		try {

			if (rs.next()) {
				currentRow = rs.getRow();

				Object[] result = new Object[columnNames.size()];
				ResultSetMetaData rsmd = rs.getMetaData();

				for (int i = 0; i < columnNames.size(); i++) {
					int type = rsmd.getColumnType(i + 1);
					switch (type) {
					case Types.SMALLINT:
					case Types.INTEGER:
						result[i] = rs.getInt(i + 1);
						if (result[i] == null)
							result[i] = "";
						break;
					case Types.BIGINT:
						result[i] = rs.getLong(i + 1);
						if (result[i] == null)
							result[i] = "";
						break;
					case Types.FLOAT:
					case Types.DOUBLE:
						result[i] = rs.getDouble(i + 1);
						if (result[i] == null)
							result[i] = "";
						break;
					case Types.BOOLEAN:
						result[i] = rs.getBoolean(i + 1);
						if (result[i] == null)
							result[i] = "";
						break;
					case Types.CHAR:
					case Types.VARCHAR:
						result[i] = rs.getString(i + 1);
						if (result[i] == null)
							result[i] = "";
						break;
					default:
						result[i] = rs.getObject(i + 1);
						if (result[i] == null)
							result[i] = "";
						else
							result[i] = result[i].toString();
					}
				}

				return result;
			} else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	public void remove() {
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public void reset() {
		try {
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getRecordcount() {
		return recordCount;
	}

	public int getCurrentrow() {
		return currentRow;
	}

	public String getColumnlist() {
		return columnList;
	}

	public Vector<String> getColumnnames() {
		return columnNames;
	}
}
