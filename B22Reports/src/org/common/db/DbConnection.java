/**
 * 
 */
package org.common.db;

import java.sql.*;

import org.apache.log4j.Logger;
import org.common.appconfig.ConfigReader;
import org.common.constants.AppConstants;

/**
 * @author nbabic
 *
 */
public class DbConnection implements IDbConnection {

	Connection connection = null;
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(DbConnection.class.getName());

	/**
	 * 
	 */
	public DbConnection() {
		// TODO Auto-generated constructor stub
		//System.out.println("DbConnection.DbConnection()");

	}
	
	/**
	 * get connection
	 * @return connection
	 */
	public Connection getConnection() {
		
		//System.out.println("LoginDB.getConnection()");
		log.info("db konekcija - " + ConfigReader.oracleConnString + ", " + ConfigReader.username + ", " + ConfigReader.password);
				
		try {
			// Load the Driver class
			//Class.forName(AppConstants.SQL_DRIVER_CLASS);
			// Load the Driver class - read from ConfigReader class
			Class.forName(ConfigReader.sqlDriverClass);
						
			//System.out.println("AppConstants.SQLLITE_DRIVER_CLASS " + AppConstants.SQL_DRIVER_CLASS);
			
			//DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			//System.out.println("registerDriver " + DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()));
					
			//Create the connection using the static getConnection method
			//connection = DriverManager.getConnection(
			//		AppConstants.jdbcOracleConnectionString, AppConstants.ORACLE_USER, AppConstants.ORACLE_USER_PWD);
			//Create the connection using the static getConnection method - read from ConfigReader class
			connection = DriverManager.getConnection(
					ConfigReader.oracleConnString, ConfigReader.username, ConfigReader.password);
			//System.out.println("connection " + connection);
						
		} catch (Exception e) {
			//System.out.println("Could not get db connection.");
			log.info("--------Could not get db connection---------");
		}
		
		return connection;
		
	}

	/**
	 * close connection
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//System.out.println("Could not close db connection.");
			log.info("--------Could not close db connection---------");
		}
	}

}
