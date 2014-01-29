/**
 * 
 */
package org.common.db.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.common.main.ConnectMain;
import org.common.model.Cell;
import org.common.model.ICell;
import org.common.model.IRow;
import org.common.model.Row;
import org.common.db.*;

/**
 * @author nbabic
 *
 */
public class RsAdapter implements IRsAdapter {

	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(RsAdapter.class.getName());
	
	ICell cell = null;
	IRow row;
	ArrayList<IRow> tableOfRows;
	
	NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
	DecimalFormat df = (DecimalFormat)nf;
	
	/**
	 * 
	 */
	public RsAdapter() {
		//System.out.println("RsAdapter.RsAdapter()");
	}

	/** 
	 * Returns an ArrayList of ArrayLists of Strings extracted from a 
	 * ResultSet retrieved from the database. 
	 * @param resultSet ResultSet to extract Strings from 
	 * @return an ArrayList of ArrayLists of Strings 
	 * @throws SQLException if an SQL exception occurs 
	 */  
	@Override
	public ArrayList<ArrayList<String>> extract(ResultSet resultSet) throws SQLException {
		//System.out.println("RsAdapter.extract()");
		ArrayList<ArrayList<String>> table;
		int columnCount = resultSet.getMetaData().getColumnCount();
		
		//System.out.println("columnCount " + columnCount);
		
		if (resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY) {
			table = new ArrayList<ArrayList<String>>();
			//System.out.println("table " + table);
		} else {
			resultSet.last();
			//System.out.println("resultSet.last()" + resultSet.last());
			table = new ArrayList<ArrayList<String>>(resultSet.getRow());
			//System.out.println("last table " + table);
			resultSet.beforeFirst();
			//System.out.println("resultSet.beforeFirst() ");
		}
		
		//add Column Names To Table
		ArrayList<String> headerRow = new ArrayList<String>(0);
		headerRow = addColumnNamesToList(columnCount, headerRow, resultSet);
		//System.out.println("headerRow " + headerRow);
		table.add(headerRow);
		//System.out.println("header table " + table);
		
		for (ArrayList<String> row; resultSet.next(); table.add(row)) {
			
			row = new ArrayList<String>(columnCount);
			//System.out.println("row begin " + row);
			
			//addRowToTable
			addRowToTable(columnCount, row, resultSet, table);
			
		}
		//System.out.println("end extract");
		//System.out.println("table " + table);
		//log.info("table " + table);
				
		return table;

	}

	@Override
	public void addRowToTable(int columnCount, ArrayList<String> row, ResultSet resultSet, ArrayList<ArrayList<String>> table) {
		//System.out.println("RsAdapter.addRowToTable()");
		
		for (int c = 1; c <= columnCount; ++ c) {
			try {
				row.add(resultSet.getString(c).intern());
			} catch (NullPointerException e) {
				//System.out.println("null");
				row.add("");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("row end " + row);
	}
	

	@Override
	public ArrayList<String> addColumnNamesToList(int columnCount, ArrayList<String> row, ResultSet resultSet) {
		//System.out.println("RsAdapter.addColumnNamesToList()");
		
		for (int c = 1; c <= columnCount; ++ c) {
			try {
				row.add(resultSet.getMetaData().getColumnName(c));
				//System.out.println("cname " + resultSet.getMetaData().getColumnName(c));
			} catch (NullPointerException e) {
				//System.out.println("null");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("header row end " + row);
		return row;
	}

	@Override
	public ICell createCell(ResultSet resultSet) {
		//System.out.println("RsAdapter.createCell()");
		int columnCount = 0;
		try {
			columnCount = resultSet.getMetaData().getColumnCount();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			log.info("Could not create cells");
		}
		
		//ArrayList<ICell> cellRow;
		int rowCount = 0;
		
		try {
			
			while (resultSet.next()) {
				
				row = new Row(columnCount);
				row.setRowIndex(rowCount);
				
				for (int c = 1; c <= columnCount; c++) {
					
					try {
						//set cell attributes
						cell = new Cell();
						cell.setRowIndex(rowCount);
						cell.setColumnIndex(c);
						cell.setLength(resultSet.getMetaData().getColumnDisplaySize(c));
						cell.setCellType(resultSet.getMetaData().getColumnType(c));
						getCellValue(c, resultSet);
						cell.setCellName(resultSet.getMetaData().getColumnLabel(c));
						//log.info(cell.toString());
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						log.info("Could not create cells");
					}
					
					row.setCell(cell);
					
				}
				
				//log.info(row.toString());
				
				//add row to table
				tableOfRows.add(row);
				
				//increase row index
				rowCount += 1;
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cell;
	}

	@Override
	public ArrayList<IRow> extractCell(ResultSet resultSet) throws SQLException {
		//System.out.println("RsAdapter.extractCell()");
		int columnCount = resultSet.getMetaData().getColumnCount();
		
		//System.out.println("columnCount " + columnCount);
		
		if (resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY) {
			tableOfRows = new ArrayList<IRow>();
			//System.out.println("table " + table);
		} else {
			resultSet.last();
			//System.out.println("resultSet.last()" + resultSet.last());
			tableOfRows = new ArrayList<IRow>(resultSet.getRow());
			//System.out.println("last table " + tableOfRows);
			resultSet.beforeFirst();
			//System.out.println("resultSet.beforeFirst() ");
		}
		
		//create cell objects from resultset
		createCell(resultSet);
		
		//print random row from table
		printRowFromTable(tableOfRows);
		
		return tableOfRows;

	}

	/**
	 * print random row from table
	 */
	public void printRowFromTable(ArrayList<IRow> tableOfRows) {
		//System.out.println("RsAdapter.printRowFromTable()");
		
		if (tableOfRows.size() > 0) {
			int randIndex = (int) (Math.random() * tableOfRows.size());
			//System.out.println("rand index " + randIndex);
			//log.info("random table row " + tableOfRows.get(randIndex).toString());
			
			//print random cell from row
			printCellFromRow(tableOfRows.get(randIndex).getCells());
		}
		
	}
	
	/**
	 * print random cell from row
	 */
	public void printCellFromRow(ArrayList<ICell> cells) {
		//System.out.println("RsAdapter.printCellFromRow() " + cells);
		int randIndex = (int) (Math.random() * cells.size());
		//System.out.println("rand index " + randIndex);
		//log.info("random row cell " + cells.get(randIndex).toString());
	}

	@Override
	public ArrayList<String> createHeaderRowList(ArrayList<String> row, ResultSet resultSet) {
		try {
			int columnCount = resultSet.getMetaData().getColumnCount();
			
			for (int c = 1; c <= columnCount; ++ c) {
				try {
					row.add(resultSet.getMetaData().getColumnName(c));
					//System.out.println("cname " + resultSet.getMetaData().getColumnName(c));
				} catch (NullPointerException e) {
					log.info("Could not create header row");
					row = null;
				} catch (SQLException e) {
					log.info("Could not create header row");
					row = null;
				}
			}
			
		} catch (SQLException e) {
			log.info("Could not create header row");
			row = null;
		}
		
		return row;
	}

	@Override
	public void getCellValue(int c, ResultSet resultSet) {
		String columnTypeName;
		try {
			columnTypeName = resultSet.getMetaData().getColumnTypeName(c);
			//log.info("columnTypeName - " + columnTypeName + ", " + resultSet.getString(c));
			
			if (columnTypeName.equals("NUMBER")) {
				//log.info("getDouble - " + resultSet.getDouble(c));
				cell.setValue(df.format(resultSet.getDouble(c)));
			} else {
				cell.setValue(resultSet.getString(c));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
