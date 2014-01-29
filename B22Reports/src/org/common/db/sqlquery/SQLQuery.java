/**
 * 
 */
package org.common.db.sqlquery;

/**
 * @author nbabic
 * SQL factory (factory pattern): SQLLoader class 
 * SQLQuery is abstract class and each sql statement is defined as a separated object /class
 */
public abstract class SQLQuery {

	/**
	 * sql query constructor
	 */
	public SQLQuery() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get sql statements
	 * @return
	 */
	public abstract String getSQLStatement();	

}
