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

/**
 * @author nbabic
 *
 */
public class StartApp {
	
	/* Get actual class name to be printed on */
	private static Logger log = Logger.getLogger(ConnectMain.class.getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		log.info("--------starting application---------");
		
		ReportCreatorFrame creatorFrame = new ReportCreatorFrame();
		creatorFrame.go();
	}

}
