/**
 * 
 */
package org.common.db.worker;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.net.*;

import org.apache.log4j.Logger;
import org.common.appconfig.ConfigReader;
import org.common.appconfig.SQLConfig;
import org.common.appconfig.model.ISqlStatement;
import org.common.appconfig.sqlhandler.CreateTableHandler;
import org.common.appconfig.sqlhandler.DropTableHandler;
import org.common.appconfig.sqlhandler.SqlHandler;
import org.common.db.DbStatement;
import org.common.db.sqlquery.SQLQuery;

/**
 * @author nbabic
 * SQL factory (factory pattern): 
 * SQLQuery is abstract class and each sql statement is defined as a separated object /class
 */
public class SQLLoader implements ISQLLoader {

	Statement statement;
	Connection connection;
	private static final String DB_PACKAGE = "org.common.db.sqlquery";
	private static String availableQueries = new String();
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(SQLLoader.class.getName());
	
	/**
	 * sql loader constructor: get input db connection and statement and load sql queries
	 */
	public SQLLoader() {
		// TODO Auto-generated constructor stub
		//System.out.println("SQLLoader.SQLLoader()");
		log.info("SQLLoader.SQLLoader()");
		this.connection = connection;
		this.statement = statement;
		//loadQueries(connection, statement);
	}
	
	/**
	 * get available sql statements
	 * @return the availableQueries
	 */
	public String getAvailableQueries() {
		//System.out.println("SQLLoader.getAvailableQueries()");
		return availableQueries;
	}

	/**
	 * load all available queries from db package
	 * @param connection
	 * @param statement
	 * @throws IOException 
	 */
	public void loadQueries(Connection connection, Statement statement) throws IOException {
		//System.out.println("SQLLoader.loadQueries()");
		//find(DB_PACKAGE, connection, statement);
		log.info("SQLLoader.loadQueries()");
		find(ConfigReader.sqlPackage, connection, statement);
	}

	/**
	 * find all sql statements (java classes) in db package and execute them (call SQLExecutor)
	 * @param pckgname
	 * @param connection
	 * @param statement
	 * @throws IOException 
	 */
	public void find(String pckgname, Connection connection, Statement statement) throws IOException {
		log.info("SQLLoader.find() " + pckgname + "\nconnection " + connection + "\nstatement " + statement);
		
		//drop tables
		dropDbTables(connection, statement);
		//log.info("after drop1---------------");
		
		//create tables
		createDbTables(connection, statement);
		//log.info("after create---------------");
		
		//execute sql statements
		executeSqlStatements(connection, statement);
		//log.info("after sql---------------");
		
		//drop tables
		dropDbTables(connection, statement);
		//log.info("after drop2---------------");
	}
	
	/**
	 * iterate over list of statements and drop necessary db tables
	 */
	private void dropDbTables(Connection connection, Statement statement) {
		//System.out.println("SQLLoader.dropDbTables()" + "\nconnection " + connection + "\nstatement " + statement);
		//execute sql statements
		Iterator it = DropTableHandler.dropTableStatementList.iterator();
		int columnCounter = 0;
		while (it.hasNext()) {
			ISqlStatement listElement = (ISqlStatement) it.next();
			String sqlName = listElement.getName();
			String sqlValue = listElement.getValue();
			log.info("currentSQL Name - " + sqlName + "\ncurrentSQL Value - " + sqlValue);
			
			ISQLExecutor executor = new SQLExecutor(connection, statement, sqlValue);
			boolean isTableDroped = executor.createDropTables(connection, statement, sqlValue, sqlName);
			log.info("currentSQL dropTable - " + isTableDroped);
		}
		
	}

	/**
	 * iterate over list of statements and create necessary db tables
	 * @param statement 
	 * @param connection 
	 */
	public void createDbTables(Connection connection, Statement statement) {
		//System.out.println("SQLLoader.createDbTables()" + "\nconnection " + connection + "\nstatement " + statement);
		//execute sql statements
		Iterator it = CreateTableHandler.createTableStatementList.iterator();
		int columnCounter = 0;
		while (it.hasNext()) {
			ISqlStatement listElement = (ISqlStatement) it.next();
			String sqlName = listElement.getName();
			String sqlValue = listElement.getValue();
			log.info("currentSQL Name - " + sqlName + "\ncurrentSQL Value - " + sqlValue);
			
			ISQLExecutor executor = new SQLExecutor(connection, statement, sqlValue);
			boolean isTableCreated = executor.createDropTables(connection, statement, sqlValue, sqlName);
			log.info("currentSQL createTable - " + isTableCreated);
		}
		
	}
	
	/**
	 * iterate over list of statements and execute sql statements
	 */
	private void executeSqlStatements(Connection connection, Statement statement) {
		//System.out.println("SQLLoader.executeSqlStatements()");
		Iterator it = SqlHandler.sqlStatementList.iterator();
		int columnCounter = 0;
		while (it.hasNext()) {
			ISqlStatement listElement = (ISqlStatement) it.next();
			String sqlName = listElement.getName();
			String sqlValue = listElement.getValue();
			log.info("currentSQL Name - " + sqlName + "\ncurrentSQL Value - " + sqlValue);
			
			ISQLExecutor executor = new SQLExecutor(connection, statement, sqlValue);
			ResultSet resultSet = executor.executeSQL(connection, statement, sqlValue, sqlName);
		}
		
		/* ne koristiti - hashtable nije sortirana
		Enumeration enumKey = SQLConfig.sqlHashtable.keys();
		while(enumKey.hasMoreElements()) {
			String sqlName = (String) enumKey.nextElement();
			String sqlValue = SQLConfig.sqlHashtable.get(sqlName);
			log.info("sqlHashtable enim: " + sqlName + ", " + sqlValue);
			
			//execute sql (call SQLExecutor)
			ISQLExecutor executor = new SQLExecutor(connection, statement, sqlValue);
			ResultSet resultSet = executor.executeSQL(connection, statement, sqlValue, sqlName);

		}*/
	}

	public static ArrayList<String> getClassNamesInPackage(String jarName, String packageName) throws IOException {
	    System.out.println("SQLLoader.getClassNamesInPackage() " + jarName + ", " + packageName);
		JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
	    packageName = packageName.replace(".", "/");
	    
	    ArrayList<String> classes = new ArrayList<String>();

	    try {
	        for (JarEntry jarEntry; (jarEntry = jarFile.getNextJarEntry()) != null;) {
	            if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
	                classes.add(jarEntry.getName().replace("/", "."));
	            }
	        }
	    } finally {
	        jarFile.close();
	    }
	    
	    System.out.println("classes " + classes);

	    return classes;
	}
	
}
