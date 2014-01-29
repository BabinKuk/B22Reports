/**
 * 
 */
package org.common.db.adapter;

import java.sql.*;
import java.util.*;

import org.common.model.Cell;
import org.common.model.ICell;
import org.common.model.IRow;

/**
 * @author nbabic
 *
 */
public interface IRsAdapter {
	/** 
	 * Returns an ArrayList of ArrayLists of Strings extracted from a 
	 * ResultSet retrieved from the database. 
	 * @param resultSet ResultSet to extract Strings from 
	 * @return an ArrayList of ArrayLists of Strings 
	 * @throws SQLException if an SQL exception occurs 
	 */  
	public ArrayList<ArrayList<String>> extract(ResultSet resultSet) throws SQLException;
	
	/**
	 * add field values from result set to table
	 * @param columnCount
	 * @param row
	 * @param resultSet
	 * @param table
	 */
	public void addRowToTable(int columnCount, ArrayList<String> row, ResultSet resultSet,ArrayList<ArrayList<String>> table);
	
	/**
	 * add column names from result set to table
	 * @param columnCount
	 * @param row
	 * @param resultSet
	 * @return ArrayList<String>
	 */
	public ArrayList<String> addColumnNamesToList(int columnCount, ArrayList<String> row, ResultSet resultSet);
	
	/**
	 * create cells from result set
	 * @param resultSet
	 * @return ICell
	 */
	public ICell createCell(ResultSet resultSet);

	/**
	 * Returns an ArrayList of ArrayLists of Cells extracted from a 
	 * ResultSet retrieved from the database. 
	 * @param resultSet to extract Strings from 
	 * @return an ArrayList of ArrayLists of Cells
	 * @throws SQLException 
	 */
	public ArrayList<IRow> extractCell(ResultSet resultSet) throws SQLException;
	
	/**
	 * print row from table
	 * @param tableOfRows
	 */
	public void printRowFromTable(ArrayList<IRow> tableOfRows);
	
	/**
	 * print cell from row
	 * @param cells
	 */
	public void printCellFromRow(ArrayList<ICell> cells);
	
	/**
	 * create header row list (column names)
	 * @param row
	 * @param resultSet
	 * @return ArrayList<String>
	 */
	public ArrayList<String> createHeaderRowList(ArrayList<String> row, ResultSet resultSet);
	
	/**
	 * get cell value
	 * @param columnCount
	 * @param resultSet
	 */
	public void getCellValue(int columnCount, ResultSet resultSet);
}
