/**
 * 
 */
package org.common.db;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author nbabic
 * methods for creating db statement
 */
public interface IDbStatement {

	/**
	 * create statement
	 * @param connection
	 * @return statement
	 */
	public Statement dbCreateStatement(Connection connection);

}
