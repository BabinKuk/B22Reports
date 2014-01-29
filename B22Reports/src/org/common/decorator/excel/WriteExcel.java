/**
 * 
 */
package org.common.decorator.excel;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.common.decorator.Decorator;
import org.common.main.ConnectMain;
import org.common.model.ICell;
import org.common.model.IRow;
import org.common.appconfig.ConfigReader;
import org.common.constants.*;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author nbabic
 *
 */
public class WriteExcel extends Decorator {

	private WritableCellFormat arialBold;
	private WritableCellFormat arial;
	private String inputFile;
	private ArrayList<IRow> table;
	private ArrayList<String> headerRow;
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(ConnectMain.class.getName());

	/**
	 * 
	 */
	public WriteExcel(ArrayList<IRow> table, ArrayList<String> headerRow, String inputFile) {
		// constructor
		//System.out.println("WriteExcel.WriteExcel() " + table.size());
		this.headerRow = headerRow;
		this.table = table;
		setOutputFile(inputFile);
	}
	
	/**
	 * set output file
	 * @param inputFile
	 */
	public void setOutputFile(String inputFile) {
		//System.out.println("WriteExcel.setOutputFile() " + inputFile);
		//this.inputFile = AppConstants.DIR_PATH + inputFile + AppConstants.XLS_FILE_TYPE;
		//read from ConfigReader class
		//System.out.println("WriteExcel.setOutputFile() " + ConfigReader.dirPath + inputFile + ConfigReader.xlsFileType);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = df.format(new Date()); 
		this.inputFile = ConfigReader.dirPath + inputFile + "_" + formattedDate + ConfigReader.xlsFileType;
	}
	
	/**
	 * starting method for creating excel file
	 * @throws IOException 
	 * @throws WriteException 
	 */
	public void write() throws IOException, WriteException {
		//System.out.println("WriteExcel.write()");
		
		//create file object
		File file = new File(inputFile);
		System.out.println("file " + file);
		WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));
	    
	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Report", 0);
	    WritableSheet writableSheet = workbook.getSheet(0);
	    
	    //create header row
	    createLabel(writableSheet);
	    //write content
	    createContent(writableSheet);
   	    
	    workbook.write();
	    workbook.close();

	}
	
	/**
	 * starting method for writing header row: preparing arguments for createHeaderRow method
	 * @param sheet
	 * @throws WriteException 
	 */
	private void createLabel(WritableSheet sheet) throws WriteException {
		//System.out.println("WriteExcel.createLabel() " + sheet);
		
		// Create a bold font with underlines
		WritableFont writableFontBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		// Define the cell format
		arialBold = new WritableCellFormat(writableFontBold);
		// Lets automatically wrap the cells
		arialBold.setWrap(false);
		
		CellView cellView = new CellView();
		cellView.setFormat(arialBold);
		cellView.setAutosize(false);
		
		//write header row
		createHeaderRow(sheet, headerRow);
		
	}
	
	/**
	 * iterate through list to get arguments for writing into excel table (addCaption method)
	 * @param sheet
	 * @param headerRow2
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void createHeaderRow(WritableSheet sheet, ArrayList<String> header) throws RowsExceededException, WriteException {
		
		log.info("Creating header row for " + inputFile);
		
		//iterate
		Iterator it = header.iterator();
		int columnCounter = 0;
		while (it.hasNext()) {
			String columnName = (String) it.next();
			//System.out.println(columnName + "; " + columnCounter);
			
			//write into table
			addCaption(sheet, columnCounter, 0, columnName);
			
			//increase counter
			columnCounter += 1;
		}
		
		log.info("Header row for " + inputFile + " successfully created.");
		
	}

	/**
	 * starting method for writing data rows: preparing arguments for createContentRows method
	 * @param sheet
	 * @throws WriteException 
	 */
	private void createContent(WritableSheet sheet) throws WriteException {
		//System.out.println("WriteExcel.createContent() " + table.size());
		
		log.info("Writing content into " + inputFile);
		
		//create a arial font
	    WritableFont writableFont = new WritableFont(WritableFont.ARIAL, 10);
	    // Define the cell format
		arial = new WritableCellFormat(writableFont);
		// Lets automatically wrap the cells
		arial.setWrap(false);
		
		CellView cellView = new CellView();
		cellView.setFormat(arial);
		cellView.setAutosize(true);
		
		//write data rows
		createContentRows(sheet, table);

	}
	
	/**
	 * iterate through content table and prepare arguments for writing into table (addLabel method)
	 * @param sheet
	 * @param dataTable
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void createContentRows(WritableSheet sheet, ArrayList<IRow> dataTable) throws RowsExceededException, WriteException {
		//take column values from Arraylist of cells
		Iterator rowIterator = dataTable.iterator();
		while (rowIterator.hasNext()) {
			try {
				//create list of cells
				//System.out.println("size " + table.size());
				ArrayList<ICell> cells = ((IRow) rowIterator.next()).getCells();
				//System.out.println("cells " + cells);
				Iterator columnIterator = cells.iterator();
				while (columnIterator.hasNext()) {
					try {
						//get arguments for excel table
						ICell cellElement = (ICell) columnIterator.next();
						String columnName = cellElement.getCellName();
						String columnValue = cellElement.getValue();
						int row = cellElement.getRowIndex();
						int column = cellElement.getColumnIndex();
						//System.out.println(row + ", " +  column + ", " + columnName + "; " + columnValue);
						//write into excel table
						addLabel(sheet, column - 1, row + 1, columnValue);
						
					} catch (Exception e) {
						log.info("Could not create excel content.");
					}
				}
				
			} catch (Exception e) {
				log.info("Could not create excel content.");
			}
		}
		
		log.info("Writing content into " + inputFile + " successfully finished");
		
	}

	/**
	 * write header row into table
	 * @param sheet
	 * @param column
	 * @param row
	 * @param string
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void addCaption(WritableSheet sheet, int column, int row, String string) throws RowsExceededException, WriteException {
		//System.out.println("WriteExcel.addCaption() " + column + " " + row + " " + string);
		Label label = new Label(column, row, string, arialBold);
		sheet.addCell(label);
	}
	
	/**
	 * write content into table
	 * @param sheet
	 * @param column
	 * @param row
	 * @param string
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void addLabel(WritableSheet sheet, int column, int row, String string) throws RowsExceededException, WriteException {
		//log.info("WriteExcel.addLabel() " + column + " " + row + " " + string);
		Label label = new Label(column, row, string, arial);
		sheet.addCell(label);		
		
	}

	/**
	 * 
	 * @param sheet
	 * @param column
	 * @param row
	 * @param integer
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private void addNumber(WritableSheet sheet, int column, int row, Integer integer) throws RowsExceededException, WriteException {
		log.info("WriteExcel.addNumber() " + column + 
				 								" " + row +
				 								" " + integer);
		//Number number = new Number(column, row, integer, times);
		//sheet.addCell(number);
		
	}
	
	@Override
	public ArrayList<IRow> getTable() {
		//System.out.println("WriteExcel.getTable()");
		return table;
	}

}