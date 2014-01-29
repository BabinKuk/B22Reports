/**
 * 
 */
package org.common.db.worker;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.common.db.DbManager;
import org.common.db.adapter.IRsAdapter;
import org.common.db.adapter.RsAdapter;
import org.common.decorator.Decorator;
import org.common.decorator.excel.WriteExcel;
import org.common.model.Cell;
import org.common.model.ICell;
import org.common.model.IRow;

/**
 * @author nbabic
 *
 */
public class SQLExecutor implements ISQLExecutor {

	Statement statement;
	Connection connection;
	ResultSet resultSet;
	String SQLStatement;
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(DbManager.class.getName());

	/**
	 * sql executor constructor
	 * @param connection
	 * @param statement
	 * @param SQLStatement
	 */
	public SQLExecutor(Connection connection, Statement statement, String SQLStatement) {
		// TODO Auto-generated constructor stub
		//System.out.println("SQLExecutor.SQLExecutor()");
		this.connection = connection;
		this.statement = statement;
		this.SQLStatement = SQLStatement;
		
	}

	/**
	 * execute sql statement and try to extract resultset (call RSAdapter)
	 * @param connection
	 * @param statement
	 * @param SQLStatement
	 * @return
	 */
	@Override
	public ResultSet executeSQL(Connection connection, Statement statement, String SQLStatement, String className) {
		//System.out.println("SQLExecutor.executeSQL()");
		
		ResultSetMetaData rsmd;
		
		try {
			//Execute the SQL statement and get the results in a Resultset
			resultSet = statement.executeQuery(SQLStatement);
			
			//Retrieves the number, types and properties of this ResultSet object's columns
			rsmd = resultSet.getMetaData();
			
			//Iterate through the ResultSet, displaying the values
			IRsAdapter rsAdapter = new RsAdapter();
			//ArrayList<ArrayList<String>> rslList = rsAdapter.extract(resultSet);
			
			//2nd method - table with rows of cells
			ArrayList<IRow> rslListCell = rsAdapter.extractCell(resultSet);
			
			//list of column names
			ArrayList<String> headerRow = new ArrayList<String>(0);
			headerRow = rsAdapter.createHeaderRowList(headerRow, resultSet);
			//System.out.println("headerRow " + headerRow);
			
			//napravi exelicu
			Decorator decorator = new WriteExcel(rslListCell, headerRow, className);
			decorator.write();
			
		} catch (Exception e) {
			//System.out.println("Could not get db result set for " + SQLStatement);
			log.info("Could not get db result set for " + SQLStatement);
			e.printStackTrace();
		}
		
		return resultSet;
	}

	@Override
	public boolean createDropTables(Connection connection, Statement statement, String SQLStatement, String className) {
		System.out.println("SQLExecutor.createDropTables() " + statement + "\n" + SQLStatement + "\n" + className);
		boolean isExecuted = false;
		try {
			statement.execute(SQLStatement);
			isExecuted = true;
		} catch (SQLException e) {
			log.info("Could not execute " + SQLStatement);
			e.printStackTrace();
		}
		return isExecuted;
	}

}
