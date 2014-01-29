/**
 * 
 */
package org.common.db.worker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author nbabic
 *
 */
public interface ISQLLoader {
	/**
	 * get available sql statements
	 * @return the availableQueries
	 */
	public String getAvailableQueries();
	
	/**
	 * load all available queries from db package
	 * @param connection
	 * @param statement
	 * @throws IOException 
	 */
	public void loadQueries(Connection connection, Statement statement) throws IOException;
	
	/**
	 * find all sql statements (java classes) in db package and execute them (call SQLExecutor)
	 * @param pckgname
	 * @param connection
	 * @param statement
	 * @throws IOException 
	 */
	public void find(String pckgname, Connection connection, Statement statement) throws IOException;

}
