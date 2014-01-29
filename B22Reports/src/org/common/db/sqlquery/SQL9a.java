/**
 * 
 */
package org.common.db.sqlquery;


/**
 * @author nbabic
 * SQL 9a: check if there are new accounts with undefined account statements
 */
public class SQL9a extends SQLQuery {

	/**
	 * 
	 */
	public SQL9a() {
		//System.out.println("SQL9a.SQL9a()");
	}

	/* (non-Javadoc)
	 * @see org.common.db.SQLQuery#getSQLStatement()
	 */
	@Override
	public String getSQLStatement() {
		//System.out.println("SQL9a.getSQLStatement()");
		String query = "select * from TBAADM.GENERAL_ACCT_MAST_TABLE g " +
				" where g.SCHM_CODE like '5%' " +
				" and g.ACCT_CRNCY_CODE='HRK' " +
				" and g.ACCT_PREFIX not in ('29','27', '95') " +
				" and acct_cls_date is null " +
				" and not exists (select * from CUSTOM.CUST_CORP_STMT_TABLE c where g.FORACID=c.FORACID) and foracid <>'1000000048'";
		return query;
	}

}
