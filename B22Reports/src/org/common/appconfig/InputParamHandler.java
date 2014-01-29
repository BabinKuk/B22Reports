/**
 * 
 */
package org.common.appconfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nbabic
 * class for managing input sql parameters
 */
public class InputParamHandler implements IInputParamHandler {

	private String dateNow;
	private String timeNow;
	private String dateFrom;
	private String timeFrom;
	private String dateTo;
	private String timeTo;
	private String reasonCode;
	
	@Override
	public void setDateNow(String dateNow) {
		//System.out.println("InputParamHandler.setDateNow() dateNow - " + dateNow);
		this.dateNow = dateNow;
	}

	@Override
	public String getDateNow() {
		//System.out.println("InputParamHandler.getDateNow() dateNow - " + dateNow);
		return dateNow;
	}

	@Override
	public void setTimeNow(String timeNow) {
		//System.out.println("InputParamHandler.setTimeNow() timeNow - " + timeNow);
		this.timeNow = timeNow;
	}

	@Override
	public String getTimeNow() {
		//System.out.println("InputParamHandler.getTimeNow() timeNow - " + timeNow);
		return timeNow;
	}
	
	@Override
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Override
	public String getDateFrom() {
		return dateFrom;
	}

	@Override
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	@Override
	public String getTimeFrom() {
		return timeFrom;
	}

	@Override
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String getDateTo() {
		return dateTo;
	}

	@Override
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;		
	}

	@Override
	public String getTimeTo() {
		return timeTo;
	}

	@Override
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	@Override
	public String getReasonCode() {
		return reasonCode;
	}

	@Override
	public String toString() {
		return "InputParamHandler [dateFrom=" + dateFrom + ", timeFrom="
				+ timeFrom + ", dateTo=" + dateTo + ", timeTo=" + timeTo
				+ ", reasonCode=" + reasonCode + "]";
	}

}