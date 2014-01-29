/**
 * 
 */
package org.common.db.sqlquery;


/**
 * @author nbabic
 * SQL 3d: get total number of IPO payment orders in Ready status  
 */
public class SQL3d extends SQLQuery {

	/**
	 * 
	 */
	public SQL3d() {
		//System.out.println("SQL3d.SQL3d()");	
	}

	/* (non-Javadoc)
	 * @see org.common.db.SQLQuery#getSQLStatement()
	 */
	@Override
	public String getSQLStatement() {
		//System.out.println("SQL3d.getSQLStatement()");
		String query = "select count(*) from c_pmtsys " +
				" where pmt_system='IPO' " +
				" and order_date<=(select db_stat_date from tbaadm.gct) " +
				" and po_status='R'";
		return query;
	}

}
