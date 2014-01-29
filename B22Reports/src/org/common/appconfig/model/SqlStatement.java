/**
 * 
 */
package org.common.appconfig.model;

import org.common.appconfig.IInputParamHandler;
import org.common.appconfig.InputParamHandler;

/**
 * @author nbabic
 * Java application that reads the XML file collection.librml and defined in the file librml.dtd
 * there’s a main application class called ReadLibrary.class, 
 * a helper called LibraryHandler.class,
 * and a helper class called Book.class.
 */
public class SqlStatement implements ISqlStatement {

	/**
	 * The Book class is used to hold the different elements of each library book
	 * as they’re read from an XML file.
	 */
	private String Value;
	private String Name;
	private IInputParamHandler inputParamHandler;
	
	public SqlStatement(IInputParamHandler handler) {
		this.inputParamHandler = handler;
	}

	@Override
	public void setValue(String Value) {
		System.out.println("SqlStatement.setValue() - " + Value + "\n");
		
		//replace dateNow
		Value = replaceInSql(Value, "DATENOW", inputParamHandler.getDateNow());
		//replace timeNow
		Value = replaceInSql(Value, "TIMENOW", inputParamHandler.getTimeNow());
		//replace dateFrom
		Value = replaceInSql(Value, "DATEFROM", inputParamHandler.getDateFrom());
		//replace timeFrom
		Value = replaceInSql(Value, "TIMEFROM", inputParamHandler.getTimeFrom());
		//replace dateTo
		Value = replaceInSql(Value, "DATETO", inputParamHandler.getDateTo());
		//replace timeTo
		Value = replaceInSql(Value, "TIMETO", inputParamHandler.getTimeTo());
		//replace reasonCode
		//Value = replaceInSql(Value, "REASONCODE", inputParamHandler.getReasonCode());
						
		this.Value = Value;
	}

	@Override
	public String getValue() {
		return Value;
	}

	@Override
	public void setName(String Name) {
		this.Name = Name;
	}

	@Override
	public String getName() {
		return Name;
	}

	@Override
	public String toString() {
		return "SqlStatement [Value=" + Value + ", Name=" + Name + "]";
	}

	@Override
	public String replaceInSql(String inputString, String oldPattern, String newPattern) {
		//System.out.println("SqlStatement.replaceInSql() " + oldPattern + ", " + newPattern);
		return inputString.replace(oldPattern, newPattern);
	}
	
}
