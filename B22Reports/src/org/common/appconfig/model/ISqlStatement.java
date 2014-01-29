/**
 * 
 */
package org.common.appconfig.model;

/**
 * @author nbabic
 * create cell objects from resulset
 */
public interface ISqlStatement {
	public void setValue(String Value);
	public String getValue();
	public void setName(String Name);
	public String getName();
	public String replaceInSql(String inputString, String oldPattern, String newPattern);

}
