/**
 * 
 */
package org.common.appconfig;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.common.main.ConnectMain;

/**
 * @author nbabic
 * class for reading properties.xml config file - singleton pattern
 */
public class ConfigReader {

	//static var to hold one instance of ChocolateBoiler class
	private static ConfigReader uniqueInstance;
	static public String oracleConnString = null;
	static public String username = null;
	static public String password = null;
	static public String sqlDriverClass = null;
	static public String sqlPackage = null;
	static public String dirPath = null;
	static public String xlsFileType = null;
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(ConfigReader.class.getName());

	/**
	 * it is started only once
	 */
	protected ConfigReader() {
		try {
			FileInputStream file = new FileInputStream("properties.xml");
			Properties props = new Properties();
			props.loadFromXML(file);
			oracleConnString = props.getProperty("jdbc.oracleConnString");
			username = props.getProperty("jdbc.username");
			password = props.getProperty("jdbc.password");
			sqlDriverClass = props.getProperty("jdbc.sqlDriverClass");
			sqlPackage = props.getProperty("jdbc.sqlPackage");
			dirPath = props.getProperty("dirPath");
			xlsFileType = props.getProperty("xlsFileType");
		    
		} catch(Exception e) {
			log.info("Error reading configuration file");
		}	 
	}
		 
	//method to create the class instance
	//since it is static it is CLASS method -> can be used as ChocolateBoiler.getInstance()
	static public ConfigReader getInstance() {
		log.info("--------reading configuration file---------");
		if (uniqueInstance == null) {
			uniqueInstance = new ConfigReader();
		}
		return uniqueInstance;
	}

}