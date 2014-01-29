/**
 * 
 */
package org.common.model;

import java.util.ArrayList;

/**
 * @author nbabic
 *
 */
public interface IRow {
	/**
	 * 
	 * @param rowIndex
	 */
	public void setRowIndex(Integer rowIndex);
	/**
	 * 
	 * @return
	 */
	public Integer getRowIndex();
	
	public void setCell(ICell cell);
	
	public ArrayList<ICell> getCells();
	
	public Row getRow(Integer rowIndex);

}
