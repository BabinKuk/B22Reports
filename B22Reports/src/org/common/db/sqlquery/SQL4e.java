/**
 * 
 */
package org.common.db.sqlquery;


/**
 * @author nbabic
 * SQL 4e: check if there are hanging payment order verifications (table c_pmtsysver)
 */
public class SQL4e extends SQLQuery {

	/**
	 * 
	 */
	public SQL4e() {
		// TODO Auto-generated constructor stub
		//System.out.println("SQL4e.SQL4e()");
	}

	/* (non-Javadoc)
	 * @see org.common.db.SQLQuery#getSQLStatement()
	 */
	@Override
	public String getSQLStatement() {
		// TODO Auto-generated method stub
		//System.out.println("SQL4e.getSQLStatement()");
		String query = "select * from c_pmtsysver order by rcre_time";
		return query;
	}

}
