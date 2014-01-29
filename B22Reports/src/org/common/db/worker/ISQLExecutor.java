package org.common.db.worker;

import java.sql.*;

/**
 * @author nbabic
 * methods executing sqls
 */
public interface ISQLExecutor {
	
	/**
	 * execute sql query
	 * @param connection
	 * @param statement
	 * @param SQLStatement
	 * @param classname
	 * @return
	 */
	public ResultSet executeSQL(Connection connection, Statement statement, String SQLStatement, String className);
	
	/**
	 * execute insert, update, create, drop table statements
	 * @param connection
	 * @param statement
	 * @param SQLStatement
	 * @param className
	 * @return
	 */
	public boolean createDropTables(Connection connection, Statement statement, String SQLStatement, String className);
	
}
