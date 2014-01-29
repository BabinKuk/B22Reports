/**
 * 
 */
package org.common.appconfig.sqlhandler;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.common.appconfig.IInputParamHandler;
import org.common.appconfig.InputParamHandler;
import org.common.appconfig.model.ISqlStatement;
import org.common.appconfig.model.SqlStatement;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author nbabic
 *
 */
public class CreateTableHandler extends DefaultHandler {

	static int READING_VALUE = 1;
	static int READING_NAME = 2;
	static int READING_NOTHING = 0;
	//stores the current parsing activity
	int currentActivity = READING_NOTHING;
	ISqlStatement createTableStatement;
	private StringBuilder chars = new StringBuilder();
	private IInputParamHandler inputHandler;
	public static ArrayList<ISqlStatement> createTableStatementList = new ArrayList<ISqlStatement>();
	
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(CreateTableHandler.class.getName());

	/**
	 * The LibraryHandler class, a subclass of DefaultHandler, 
	 * contains the methods that keep track of what the parser is doing 
	 * and take actions at different steps of the XMLparsing process.
	 */
	public CreateTableHandler(IInputParamHandler inputHandler) {
		super();
		log.info("CreateTableHandler.CreateTableHandler()");
		this.inputHandler = inputHandler;
		createTableStatement = new SqlStatement(inputHandler);
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
			createTableStatement.setValue(strValue);
			log.info("createTableStatement value - " + createTableStatement.getValue());
			chars.append(ch, start, length);
		}
		if (currentActivity == READING_NAME) {
			createTableStatement.setName(strValue);
		}
	}
	
	/**
	 * find out when an end tag is reached
	 */
	public void endElement(String uri, String localName, String qName) {
		//System.out.println("LibraryHandler.endElement()");
		if (qName.equals("Sql")) {
			String sqlValue = chars.toString();
			createTableStatement.setValue(sqlValue);
			//log.info("Name: " + createTableStatement.getName() + "\nValue: " + createTableStatement.getValue());
			createTableStatementList.add(createTableStatement);
			createTableStatement = new SqlStatement(inputHandler);
		}
	}
	
}
