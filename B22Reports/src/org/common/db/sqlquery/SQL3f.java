/**
 * 
 */
package org.common.db.sqlquery;


/**
 * @author nbabic
 * SQL 3f: check if there is any HSVP payment order with wrong data
 */
public class SQL3f extends SQLQuery {

	/**
	 * 
	 */
	public SQL3f() {
		// TODO Auto-generated constructor stub
		//System.out.println("SQL3f.SQL3f()");
	}

	/* (non-Javadoc)
	 * @see org.common.db.SQLQuery#getSQLStatement()
	 */
	@Override
	public String getSQLStatement() {
		//System.out.println("SQL3f.getSQLStatement()");
		String query = "select * from c_pmtsys " +
				" where pmt_system='HSVP' and order_DATE=(select db_stat_date from tbaadm.gct) and direction='I'" +
				" and (length(db_vbdi_no)!=7 or length(db_acct_no)!=10 or length(cr_vbdi_no)!=7 or length(cr_acct_no)!=10 " +
				" OR REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" +
				" db_vbdi_no,'0',''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9','') IS not NULL " +
				" OR REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" +
				" db_acct_no,'0',''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9','') IS not NULL " +
				" OR REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" +
				" cr_vbdi_no,'0',''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9','') IS not NULL " +
				" OR REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" +
				" cr_acct_no,'0',''),'1',''),'2',''),'3',''),'4',''),'5',''),'6',''),'7',''),'8',''),'9','') IS  not NULL " +
				" or db_vbdi_no is null or db_acct_no is null or cr_vbdi_no is null or cr_acct_no is null )";
		return query;
	}

}
