/**
 * 
 */
package org.common.db;

import java.sql.Connection;

/**
 * @author nbabic
 * methods for opening and closing db connection
 */
public interface IDbConnection {
	/**
	 * get connection
	 * @return
	 */
	public Connection getConnection();
	
	/**
	 * close connection
	 */
	public void close();

}
