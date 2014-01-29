/**
 * 
 */
package org.common.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.common.db.worker.SQLLoader;

/**
 * @author nbabic
 * DbStatement object: try to create statement
 */
public class DbStatement implements IDbStatement {

	Connection connection;
	Statement statement;
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(DbStatement.class.getName());
	
	/**
	 * db statement constructor
	 */
	public DbStatement() {
		// TODO Auto-generated constructor stub
		//System.out.println("DbStatement.DbStatement()");
		log.info("--------in constructor statement---------");
		//this.connection = connection;
		//dbCreateStatement(connection);
	}

	/**
	 * create statement and try to load sql statements for execution (SQLLoader)
	 * @param connection
	 * @return statement
	 */
	 @Override
	public Statement dbCreateStatement(Connection connection) {
		// TODO Auto-generated method stub
		//System.out.println("DbStatement.dbCreateStatement() " + statement);
		log.info("--------in dbCreateStatement ---------");
		try {
			log.info("--------try in dbCreateStatement ---------");
			
			statement = connection.createStatement();
			//Execute the SQL statement and get the results in a Resultset
			
			//Load SQL statements and execute them
			SQLLoader loader = new SQLLoader();
			try {
				loader.loadQueries(connection, statement);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("Could not get db statement.");
			log.info("--------Could not get db statement---------");
		}
		//System.out.println("statement " + statement);
		return statement;
	}

}
