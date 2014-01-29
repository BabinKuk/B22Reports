/**
 * 
 */
package org.common.model;

import java.util.ArrayList;

/**
 * @author nbabic
 *
 */
public class Row implements IRow {

	ArrayList<ICell> cells;
	private ICell cell;
	private Integer rowIndex;
	
	/**
	 * 
	 */
	public Row(Integer columnCount) {
		//System.out.println("Row.Row()");
		this.cells = new ArrayList<ICell>(columnCount);
	}

	@Override
	public void setRowIndex(Integer rowIndex) {
		//System.out.println("Row.setRowIndex()");
		this.rowIndex = rowIndex;
	}

	@Override
	public Integer getRowIndex() {
		// TODO Auto-generated method stub
		return rowIndex;
	}

	@Override
	public void setCell(ICell cell) {
		//System.out.println("Row.setCell() " + cell);
		cells.add(cell);
		
	}

	@Override
	public ArrayList<ICell> getCells() {
		//System.out.println("Row.getCells()");
		return cells;
	}

	@Override
	public String toString() {
		return "Row [rowIndex=" + rowIndex + ", cells=" + cells +"]";
	}

	@Override
	public Row getRow(Integer rowIndex) {
		return null;
	}
	
}
