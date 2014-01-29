/**
 * 
 */
package org.common.main;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.common.appconfig.ConfigReader;
import org.common.appconfig.CreateTableConfig;
import org.common.appconfig.DropTableConfig;
import org.common.appconfig.IInputParamHandler;
import org.common.appconfig.InputParamHandler;
import org.common.appconfig.SQLConfig;
import org.common.appconfig.sqlhandler.DropTableHandler;
import org.common.appconfig.sqlhandler.SqlHandler;
import org.common.constants.AppConstants;
import org.common.db.DbConnection;
import org.common.db.DbManager;
import org.common.db.DbStatement;
import org.common.db.IDbConnection;
import org.common.db.IDbStatement;

/**
 * @author nbabic
 *
 */
public class ConnectMain {

	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(ConnectMain.class.getName());
	
	/**
	 * 
	 */
	public ConnectMain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String args[]) throws Exception {
		 
		log.info("--------starting application---------");
		
		if (args.length < 5) {
			log.info("Set input parameters to start application...");
		} else {
			//singleton class for reading config file
			ConfigReader configReader = ConfigReader.getInstance();
			log.info("configReader " + ConfigReader.dirPath + "; " + ConfigReader.sqlDriverClass + 
					", " + ConfigReader.oracleConnString + ", " + ConfigReader.username);
			
			log.info("--------parsing application---------");
			log.info("input parameters: " + args[0] + ", " + args[1] + ", " + args[2] + ", " + args[3] + ", " + args[4]);
			
			//input parameters
			IInputParamHandler inputParams = new InputParamHandler();
			inputParams.setDateFrom(args[0]);
			inputParams.setTimeFrom(args[1]);
			inputParams.setDateTo(args[2]);
			inputParams.setTimeTo(args[3]);
			inputParams.setReasonCode(args[4]);
			
			log.info(inputParams.toString());
			
			//singleton class for reading sql statements config file
			CreateTableConfig createTableConfig = CreateTableConfig.getInstance("CreateTables.xml", inputParams);
			//log.info("sqlHashtable: " + SqlHandler.sqlStatementList.toString());
			
			//singleton class for reading sql statements config file
			DropTableConfig dropTableConfig = DropTableConfig.getInstance("DropTables.xml", inputParams);
			//log.info("sqlHashtable: " + SqlHandler.sqlStatementList.toString());
					
			//singleton class for reading sql statements config file
			SQLConfig sqlStatementsConfig = SQLConfig.getInstance("SqlStatements.xml", inputParams);
			//log.info("sqlHashtable: " + SqlHandler.sqlStatementList.toString());
		
			DbManager manager = new DbManager();
			manager.go();
		}
		
		log.info("--------application shutting down---------");

	}

}
