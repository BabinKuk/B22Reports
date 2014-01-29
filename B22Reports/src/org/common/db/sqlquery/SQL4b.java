/**
 * 
 */
package org.common.db.sqlquery;


/**
 * @author nbabic
 * SQL 4b: get payment orders in Entered status (po_source = 'BANK') 
 */
public class SQL4b extends SQLQuery {

	/**
	 * 
	 */
	public SQL4b() {
		// TODO Auto-generated constructor stub
		//System.out.println("SQL4b.SQL4b()");
	}

	/* (non-Javadoc)
	 * @see org.common.db.SQLQuery#getSQLStatement()
	 */
	@Override
	public String getSQLStatement() {
		//System.out.println("SQL4b.getSQLStatement()");
		String query = "select po_ref_no, TYPE_OF_OPERATION,order_date,to_char(VALUE_DATE,'yyyy-MM-dd')  as datum_valute,tran_amt, " +
				" ENTERER_ID,verifier_id, sol_id,to_char(RCRE_TIME, 'yyyy-MM-dd') as datum_kreiranja,pmt_system, rcre_time as vrijeme_unosa " +
				" from c_pmtsys " +
				" where po_source = 'BANK'" +
				" and order_date = (select db_stat_date from tbaadm.gct)" +
				" and (pmt_system='IPO' or (pmt_system!='IPO' and direction!='I'))" +
				" and po_status = 'E'" +
				" order by rcre_time asc";
		return query;
	}

}
