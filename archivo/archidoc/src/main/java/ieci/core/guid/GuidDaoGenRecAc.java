package ieci.core.guid;

import ieci.core.db.DbInputRecord;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputStatement;

final class GuidDaoGenRecAc implements DbInputRecord, DbOutputRecord {

	public String m_node;
	public int m_lpid;

	GuidDaoGenRecAc() {
		m_node = "000000000000";
		m_lpid = 0;
	}

	GuidDaoGenRecAc(String node, int lpid) {
		m_node = node;
		m_lpid = lpid;
	}

	public void setStatementValues(DbInputStatement stmt) throws Exception {
		stmt.setShortText(1, m_node);
		stmt.setLongInteger(2, m_lpid);
	}

	public void getStatementValues(DbOutputStatement stmt) throws Exception {
		m_node = stmt.getShortText(1);
		m_lpid = stmt.getLongInteger(2);
	}

} // class
