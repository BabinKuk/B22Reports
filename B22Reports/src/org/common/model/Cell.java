/**
 * 
 */
package org.common.model;

/**
 * @author nbabic
 * create cell objects from result set
 */
public class Cell implements ICell {

	private Integer columnIndex;
	private Integer rowIndex;
	private Integer cellLength;
	private Integer cellType;
	private String cellValue;
	private String cellName;
	
	/**
	 * 
	 */
	public Cell() {
		//System.out.println("Cell.Cell()");
	}

	@Override
	public void setRowIndex(Integer rowIndex) {
		//System.out.println("Cell.setRowIndex()");
		this.rowIndex = rowIndex;
	}

	@Override
	public Integer getRowIndex() {
		//System.out.println("Cell.getRowIndex()");
		return rowIndex;
	}

	@Override
	public void setColumnIndex(Integer columnIndex) {
		//System.out.println("Cell.setColumnIndex()");
		this.columnIndex = columnIndex;
	}

	@Override
	public Integer getColumnIndex() {
		//System.out.println("Cell.getColumnIndex()");
		return columnIndex;
	}

	@Override
	public void setValue(String cellValue) {
		//System.out.println("Cell.setValue()");
		this.cellValue = cellValue;
	}

	@Override
	public String getValue() {
		//System.out.println("Cell.getValue()");
		return cellValue;
	}

	@Override
	public void setLength(Integer cellLength) {
		this.cellLength = cellLength;
	}

	@Override
	public Integer getLength() {
		// TODO Auto-generated method stub
		return cellLength;
	}

	@Override
	public void setCellType(Integer cellType) {
		//System.out.println("Cell.setCellType()");
		this.cellType = cellType;
	}

	@Override
	public Integer getCellType() {
		//System.out.println("Cell.getCellType()");
		return cellType;
	}

	@Override
	public void setCellName(String cellName) {
		//System.out.println("Cell.setCellName()");
		this.cellName = cellName;
		
	}

	@Override
	public String getCellName() {
		//System.out.println("Cell.getCellName()");
		return cellName;
	}

	@Override
	public String toString() {
		return "Cell [rowIndex=" + rowIndex + ", columnIndex=" + columnIndex + ", cellLength="
				+ cellLength + ", cellType=" + cellType + ", cellValue="
				+ cellValue + ", cellName=" + cellName + "]";
	}
	
}
