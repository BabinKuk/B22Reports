/**
 * 
 */
package org.common.main;

import org.apache.log4j.Logger;
import org.common.appconfig.ConfigReader;
import org.common.appconfig.CreateTableConfig;
import org.common.appconfig.DropTableConfig;
import org.common.appconfig.IInputParamHandler;
import org.common.appconfig.InputParamHandler;
import org.common.appconfig.SQLConfig;
import org.common.db.DbManager;
import org.jdesktop.swingx.calendar.SingleDaySelectionModel;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DateFormatter;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author nbabic
 * Graphic interface for application 
 */
public class ReportCreatorFrame extends JXDatePicker {
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(ReportCreatorFrame.class.getName());
	
	//display components
	//windows and layout managers
	JFrame viewFrame;
	// Set up file menu.
	JMenuBar menuBar;
	JMenu fileMenu;
	// Set up panels
	JPanel inputPanel;
	JPanel labelPanel;
	JPanel fieldPanel;
	JPanel buttonsPanel;
	//buttons for managing the selected download.
	JButton goButton;
	JButton pauseButton;
	//menu bar components
	JMenuItem fileExitMenuItem;
	//field labels
	JLabel dateFromLabel;
	JLabel dateToLabel;
	
	private JSpinner timeSpinner;
	private JPanel timePanel;
	private DateFormat timeFormat;
	
	ReportCreatorFrame dateFrom;
	ReportCreatorFrame dateTo;
	
	public ReportCreatorFrame() {
		super();
		//System.out.println("ReportCreatorFrame.ReportCreatorFrame()");
		getMonthView().setSelectionModel(new SingleDaySelectionModel());
	}
	
	public ReportCreatorFrame(Date d) {
		this();
		//System.out.println("ReportCreatorFrame.ReportCreatorFrame(Date d)");
		setDate(d);
	}

	
	public void commitEdit() throws ParseException {
		//System.out.println("ReportCreatorFrame.commitEdit()");
		commitTime();
		super.commitEdit();
	}
	
	public void cancelEdit() {
		//System.out.println("ReportCreatorFrame.cancelEdit()");
		super.cancelEdit();
		setTimeSpinners();
	}
	
	@Override
	public JPanel getLinkPanel() {
		//System.out.println("ReportCreatorFrame.getLinkPanel()");
		super.getLinkPanel();
        if(timePanel == null) {
			timePanel = createTimePanel();
		}
		setTimeSpinners();
		return timePanel;
	}
	
	private JPanel createTimePanel() {
		//System.out.println("DateTimePicker.createTimePanel()");
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new FlowLayout());
		//newPanel.add(panelOriginal);
		
		SpinnerDateModel dateModel = new SpinnerDateModel();
		timeSpinner = new JSpinner(dateModel);

		if(timeFormat == null) {
			timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
		}
		updateTextFieldFormat();
		newPanel.add(new JLabel("Time:"));
		newPanel.add(timeSpinner);
		newPanel.setBackground(Color.WHITE);
		return newPanel;
    }

	private void updateTextFieldFormat() {
		//System.out.println("ReportCreatorFrame.updateTextFieldFormat()");
		if(timeSpinner == null) return;
		JFormattedTextField tf = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
		DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
		DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();
		
		// Change the date format to only show the hours
		formatter.setFormat(timeFormat);
	}
	
	private void commitTime() {
		//System.out.println("ReportCreatorFrame.commitTime()");
		Date date = getDate();
		
		if (date != null) {
			Date time = (Date) timeSpinner.getValue();
			GregorianCalendar timeCalendar = new GregorianCalendar();
			timeCalendar.setTime(time);
			
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			Date newDate = calendar.getTime();
			setDate(newDate);
		}
	}
	
	private void setTimeSpinners() {
		//System.out.println("ReportCreatorFrame.setTimeSpinners()");
		Date date = getDate();
		if (date != null) {
			timeSpinner.setValue(date);
		}
	}
	
	public DateFormat getTimeFormat() {
		//System.out.println("ReportCreatorFrame.getTimeFormat()");
		return timeFormat;
	}
	
	public void setTimeFormat(DateFormat timeFormat) {
		//System.out.println("ReportCreatorFrame.setTimeFormat()");
		this.timeFormat = timeFormat;
		updateTextFieldFormat();
	}

	// Run application
	public void go() {
		//System.out.println("ReportCreatorFrame.go()");
		
		Date date = new Date();
		dateFrom = new ReportCreatorFrame();
		dateFrom.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateFrom.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		
		dateFrom.setDate(date);
		
		dateTo = new ReportCreatorFrame();
		dateTo.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
		dateTo.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
		
		dateTo.setDate(date);
		
		// Create all Swing components here
		JFrame.setDefaultLookAndFeelDecorated(false);
		viewFrame = new JFrame("B22 Report Manager");
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewFrame.setSize(240, 200);
				
		// Handle window closing event
		viewFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
				actionExit();
			}
		});
		
		// Set up menu bar
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
        //menu bar components
		fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		//register action event
		fileExitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	actionExit();
            }
        });
		//add menubar to menu
		menuBar.add(fileMenu);
		fileMenu.add(fileExitMenuItem);
		//set menubar in the frame
		viewFrame.setJMenuBar(menuBar);
		
		// Set up add panel
		inputPanel = new JPanel(new GridLayout(1, 2));
		inputPanel.setBorder(BorderFactory.createTitledBorder("Input parameters"));
		inputPanel.setLayout(new BorderLayout());
		
		//set up label panel
		labelPanel = new JPanel(new GridLayout(2, 1));
		labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		dateFromLabel = new JLabel("From Date:");
		dateToLabel = new JLabel("To Date:");
		//add to panel
		labelPanel.add(dateFromLabel, BorderLayout.NORTH);
		labelPanel.add(dateToLabel, BorderLayout.SOUTH);
		
		//set up field panel
		fieldPanel = new JPanel(new GridLayout(2, 1));
		fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//add to panel
		fieldPanel.add(dateFrom, BorderLayout.NORTH);
		fieldPanel.add(dateTo, BorderLayout.SOUTH);
		
		//add to panel
		inputPanel.add(labelPanel, BorderLayout.WEST);
		inputPanel.add(fieldPanel, BorderLayout.EAST);
		
		// Set up buttons panel.
		buttonsPanel = new JPanel();
		//button panel components
		goButton = new JButton("                Go                ");
		//register action event
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goCreate();
			}
		});
		
		//add to panel
		buttonsPanel.add(goButton);
		
		// Add panels to display.
		viewFrame.getContentPane().setLayout(new BorderLayout());
		viewFrame.getContentPane().add(inputPanel, BorderLayout.CENTER);
		viewFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		viewFrame.pack();
	    viewFrame.setVisible(true);
	}
	
	/**
	 * Exit application
	 */
	private void actionExit() {
		log.info("--------exit application---------");
		//exit application
    	System.exit(0);
	}
	
	/**
	 * start generating report
	 */
	private void goCreate() {
		
		//disable fields
		updateFields(false, "Generating reports...");
		
		int dialogButton = JOptionPane.showConfirmDialog(this, "Do you REALLY want all these silly reports?", goButton.getText(), JOptionPane.YES_NO_OPTION);
		  
		if (dialogButton == JOptionPane.YES_OPTION) {
			
			JOptionPane.showMessageDialog(this, "It will take some time. Sit back and relax or go get a coffee...", "INFO", JOptionPane.INFORMATION_MESSAGE);
			//JOptionPane.showMessageDialog(this, goButton.getText(), "INFO", JOptionPane.INFORMATION_MESSAGE);
			try {
				// wait 3 seconds to show job
				Thread.sleep(3L * 1000L);
				// executing...
			} catch (Exception e) {
			}
			prepareInputs();
		} else {
			//enable fields
			updateFields(true, "Go");
		}
	}
	
	/**
	 * prepare inputs parameters
	 */
	private void prepareInputs() {
		//log.info("ReportCreatorFrame.actionAdd()" + dateFrom.getDate() + ", " + dateTo.getDate());
		
		//today date
		DateFormat today = new SimpleDateFormat("dd-MM-yyyy");
		String dateNow = today.format(new Date());
		//time now
		DateFormat now = new SimpleDateFormat("HH:mm:ss");
		String timeNow = now.format(new Date());
		
		//from date
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDf = df.format(dateFrom.getDate());
		//from time
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		String formattedTf = tf.format(dateFrom.getDate());
		//to date
		DateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDt = dt.format(dateTo.getDate());
		//to time
		DateFormat tt = new SimpleDateFormat("HH:mm:ss");
		String formattedTt = tt.format(dateTo.getDate());
		
		log.info("ReportCreatorFrame.actionAdd() formatirano - " + dateNow + ", " + timeNow + ", " + formattedDf + ", " + formattedTf + ", " + formattedDt + ", " + formattedTt);
		
		//input parameters
		IInputParamHandler inputParams = new InputParamHandler();
		inputParams.setDateNow(dateNow);
		inputParams.setTimeNow(timeNow);
		inputParams.setDateFrom(formattedDf);
		inputParams.setTimeFrom(formattedTf);
		inputParams.setDateTo(formattedDt);
		inputParams.setTimeTo(formattedTt);
		
		log.info(inputParams.toString());
		
		//singleton class for reading config file
		ConfigReader configReader = ConfigReader.getInstance();
		//log.info("configReader " + ConfigReader.dirPath + "; " + ConfigReader.sqlDriverClass + 
		//		", " + ConfigReader.oracleConnString + ", " + ConfigReader.username);
		
		//singleton class for reading sql statements config file
		CreateTableConfig createTableConfig = CreateTableConfig.getInstance("createTables.xml", inputParams);
		//log.info("sqlHashtable: " + SqlHandler.sqlStatementList.toString());
		
		//singleton class for reading sql statements config file
		DropTableConfig dropTableConfig = DropTableConfig.getInstance("DropTables.xml", inputParams);
		//log.info("sqlHashtable: " + SqlHandler.sqlStatementList.toString());
				
		//singleton class for reading sql statements config file
		SQLConfig sqlStatementsConfig = SQLConfig.getInstance("SqlStatements.xml", inputParams);
		//log.info("sqlHashtable: " + SqlHandler.sqlStatementList.toString());
	
		//generate reports
		generateReport();
	}

	/**
	 * Generate reports
	 */
	private void generateReport() {
		//System.out.println("ReportCreatorFrame.generateReport()");
		
		DbManager manager = new DbManager();
		manager.go();
		
		JOptionPane.showMessageDialog(this, "Reports are finished.\nMore details in:\n" + ConfigReader.dirPath, "INFO", JOptionPane.INFORMATION_MESSAGE);
		
		//enable fields
		//updateFields(true, "Go");
		
		log.info("--------application shutting down---------");
		
		//exit application
		actionExit();
	}

	private void updateFields(boolean b, String msg) {
		//System.out.println("ReportCreatorFrame.updateFields() " + b);
		goButton.setText(msg);
		goButton.setEnabled(b);
		dateFrom.setEnabled(b);
		dateTo.setEnabled(b);
	}

}