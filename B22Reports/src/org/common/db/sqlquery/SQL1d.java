/**
 * 
 */
package org.common.db.sqlquery;


/**
 * @author nbabic
 * SQL 1d: get total number of NKS payment orders in Ready status  
 */
public class SQL1d extends SQLQuery {

	/**
	 * 
	 */
	public SQL1d() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.common.db.SQLQuery#getSQLStatement()
	 */
	@Override
	public String getSQLStatement() {
		//System.out.println("SQL1d.getSQLStatement()");
		String query = "select count(*) from c_pmtsys " +
				" where order_date<=(select gct.db_stat_date from tbaadm.gct)" +
				" and pmt_system='NKS' and PO_status='R'";
		return query;
	}

}
