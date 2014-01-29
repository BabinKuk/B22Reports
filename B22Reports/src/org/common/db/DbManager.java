/**
 * 
 */
package org.common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.common.main.ConnectMain;


/**
 * @author nbabic
 *
 */
public class DbManager {

	ResultSet rs = null;
	Statement stmt;
	Connection connection;
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(DbManager.class.getName());

	/**
	 * 
	 */
	public DbManager() {
		// TODO Auto-generated constructor stub
		//System.out.println("DbManager.DbManager()");
		
	}
	
	/**
	 * starting method: get db connection and try to create statement (call DbStatement)
	 * in the end close everything
	 */
	public void go() {
		//System.out.println("DbManager.go()");
		//get db connection 
		IDbConnection connectDB = new DbConnection();
		System.out.println("connectDB " + connectDB);
		
		log.info("--------getting connection---------");
		connection = connectDB.getConnection();
		
		if (connection != null) {
			//System.out.println("got connection, --> sqlexecutor");
			log.info("--------got connection --> executing sql statements---------");
			//Create a Statement class to execute the SQL statement
			IDbStatement dbStatement = new DbStatement();
			dbStatement.dbCreateStatement(connection);
			log.info("--------after statements---------");
		}
		
		closeEverything(rs, stmt, connection);

	}
	
	/**
	 * close everything: connection, statement, resultset
	 * @param rs
	 * @param stmt
	 * @param connection
	 */
	public static void closeEverything(ResultSet rs, Statement stmt, Connection connection) {
		// TODO Auto-generated method stub
		//System.out.println("DbResultSet.closeEverything()");
		log.info("--------closing everything: connection, statement, resultset---------");
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				//System.out.println("Could not close db result set.");
				log.info("--------Could not close db result set---------");
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				//System.out.println("Could not close db statement.");
				log.info("--------Could not close db statement---------");
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				//System.out.println("Could not close db connection.");
				log.info("--------Could not close db connection---------");
			}
		}
	}

}
