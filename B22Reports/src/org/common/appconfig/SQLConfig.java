/**
 * 
 */
package org.common.appconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;
import org.common.appconfig.model.ISqlStatement;
import org.common.appconfig.sqlhandler.CreateTableHandler;
import org.common.appconfig.sqlhandler.DropTableHandler;
import org.common.appconfig.sqlhandler.SqlHandler;

/**
 * @author nbabic
 *
 */
public class SQLConfig {

	private static SQLConfig uniqueInstance;
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(SQLConfig.class.getName());
		
	/**
	 * singleton - started only once
	 * ReadLibrary class loads a file specified by a inputstream, 
	 * creates a SAX parser, and tells it to parse the file.
	 */
	protected SQLConfig(String inputFile, IInputParamHandler inputParamHandler) {
		
		try {
			log.info("SQLConfig.SQLConfig() " + inputFile);
			/*
		    FileInputStream file = new FileInputStream("sqlStatements.xml");
			Properties properties = new Properties();
			properties.loadFromXML(file);
			//System.out.println("props " + properties.size());
			
			//System.out.println("All keys of the property file : ");
			//System.out.println(properties.keySet());
			//System.out.println("All values of the property file : ");
			Enumeration em = properties.keys();
			String data;
			sqlHashtable = new Hashtable<String, String>();
			
			while(em.hasMoreElements()) {
				String str = (String)em.nextElement();
				String value = properties.getProperty(str);
				//puta key/value pairs into hashtable
				sqlHashtable.put(str, value);
			}
			//System.out.println("sqlHashtable: " + sqlHashtable);
			*/			
			
			
			//not working inside jar
			//create file object 
			//File input = new File(libFile);
			//
			//create inputstream object
			InputStream input = getClass().getClassLoader().getResourceAsStream(inputFile);
			//create a SAXParserFactory object
			SAXParserFactory factory = SAXParserFactory.newInstance();
			//if validation is supported
			factory.setValidating(true);


			//create sax parser object
			//if saxparser is not created, ParserConfigurationException is generated
			SAXParser sax = factory.newSAXParser();
			//SAXparser reads XML data from files, input streams, and other sources. To read
			//from a file, the parser’s parse(File, DefaultHandler) method is called. 
			//This method throws two kinds of exceptions: 
			//IOException if an error occurs as the file is being read
			//SAXException if the SAX parser runs into some kind of problem parsing data
			//System.out.println("input " + input);
			sax.parse(input, new SqlHandler(inputParamHandler));
	
		} catch (ParserConfigurationException pce) {
			log.info("Error reading configuration file. Could not create parser.");
			log.info(pce.getMessage());
		} catch (SAXException se) {
			log.info("Error reading configuration file. Problem with the SAX parser.");
			log.info(se.getMessage());
		} catch (IOException ioe) {
			log.info("Error reading configuration file");
			log.info(ioe.getMessage());
		}

	}

	//method to create the class instance
	//since it is static it is CLASS method -> can be used as ChocolateBoiler.getInstance()
	static public SQLConfig getInstance(String inputFile, IInputParamHandler handler) {
		log.info("--------reading configuration file---------");
		if (uniqueInstance == null) {
			//System.out.println("null");
			uniqueInstance = new SQLConfig(inputFile, handler);
		}
		return uniqueInstance;
		
	}

}
