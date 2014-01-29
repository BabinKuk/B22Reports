/**
 * 
 */
package org.common.appconfig.sqlhandler;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.log4j.Logger;
import org.common.appconfig.IInputParamHandler;
import org.common.appconfig.model.ISqlStatement;
import org.common.appconfig.model.SqlStatement;

/**
 * @author nbabic
 * Java application that reads the XML file collection.librml and defined in the file librml.dtd
 * there’s a main application class called ReadLibrary.class, 
 * a helper called LibraryHandler.class,
 * and a helper class called Book.class.
 */
public class SqlHandler extends DefaultHandler {

	static int READING_VALUE = 1;
	static int READING_NAME = 2;
	static int READING_NOTHING = 0;
	//stores the current parsing activity
	int currentActivity = READING_NOTHING;
	ISqlStatement librarySql;
	private StringBuilder chars = new StringBuilder();
	public static ArrayList<ISqlStatement> sqlStatementList = new ArrayList<ISqlStatement>();
	private IInputParamHandler inputHandler;
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(SqlHandler.class.getName());

	/**
	 * The LibraryHandler class, a subclass of DefaultHandler, 
	 * contains the methods that keep track of what the parser is doing 
	 * and take actions at different steps of the XMLparsing process.
	 */
	public SqlHandler(IInputParamHandler inputHandler) {
		super();
		log.info("LibraryHandler.LibraryHandler()");
		this.inputHandler = inputHandler;
		librarySql = new SqlStatement(inputHandler);
	}
	
	/**
	 * find out when a new start tag is parsed
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		/*System.out.println("LibraryHandler.startElement() " + uri + 
							", " + localName +
							", " + qName +
							", " + attributes);*/
		if (qName.equals("Value")) {
			currentActivity = READING_VALUE;
			chars.setLength(0);
		} else if (qName.equals("Name")) {
			currentActivity = READING_NAME;
		}
	}
	
	/**
	 * find out what a tag contains
	 */
	public void characters(char[] ch, int start, int length) {
		//string that contains the character data within the tag
		//System.out.println("LibraryHandler.characters()");
		String strValue = new String(ch, start, length);
		if (currentActivity == READING_VALUE) {
			librarySql.setValue(strValue);
			chars.append(ch, start, length);
		}
		if (currentActivity == READING_NAME) {
			librarySql.setName(strValue);
		}
	}
	
	/**
	 * find out when an end tag is reached
	 */
	public void endElement(String uri, String localName, String qName) {
		//System.out.println("LibraryHandler.endElement()");
		if (qName.equals("Sql")) {
			String sqlValue = chars.toString();
			librarySql.setValue(sqlValue);
			//log.info("Name: " + librarySql.getName() + "\nValue: " + librarySql.getValue());
			sqlStatementList.add(librarySql);
			librarySql = new SqlStatement(inputHandler);
		}
	}
	
}
