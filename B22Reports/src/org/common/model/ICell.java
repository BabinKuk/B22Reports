/**
 * 
 */
package org.common.model;

/**
 * @author nbabic
 * create cell objects from resulset
 */
public interface ICell {
	public void setRowIndex(Integer rowIndex);
	public Integer getRowIndex();
	public void setColumnIndex(Integer columnIndex);
	public Integer getColumnIndex();
	public void setValue(String cellValue);
	public String getValue();
	public void setLength(Integer cellLength);
	public Integer getLength();
	public void setCellType(Integer cellType);
	public Integer getCellType();
	public void setCellName(String cellName);
	public String getCellName();
	
}
