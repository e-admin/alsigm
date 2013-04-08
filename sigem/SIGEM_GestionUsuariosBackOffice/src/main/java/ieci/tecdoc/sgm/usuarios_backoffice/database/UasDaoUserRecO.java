package ieci.tecdoc.sgm.usuarios_backoffice.database;

import ieci.tecdoc.sgm.base.dbex.DbOutputRecord;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;

public class UasDaoUserRecO implements DbOutputRecord {

	private int m_id;
	private String m_name;
	private String m_pwd;
	private int m_deptId;
	private int m_stat;
	private int m_numBadCnts;
	private String m_remarks;

	public UasDaoUserRecO() {
	}

	public int getNumBadCnts() {
		return m_numBadCnts;
	}

	public String getPwd() {
		return m_pwd;
	}

	public int getDeptId() {
		return m_deptId;
	}

	public int getId() {
		return m_id;
	}

	public String getName() {
		return m_name;
	}

	public int getStat() {
		return m_stat;
	}

	public String getRemarks() {
		return m_remarks;
	}

	public void getStatementValues(DbOutputStatement stmt) throws Exception {
		int i = 1;
		m_id = stmt.getLongInteger(i++);
		m_name = stmt.getShortText(i++);
		m_pwd = stmt.getShortText(i++);
		m_deptId = stmt.getLongInteger(i++);
		m_stat = stmt.getLongInteger(i++);
		m_numBadCnts = stmt.getLongInteger(i++);
		m_remarks = stmt.getShortText(i++);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("UasDaoUserRecO[");
		buffer.append("m_id = ").append(m_id);
		buffer.append(", m_name = ").append(m_name);
		buffer.append(", m_pwd = ").append(m_pwd);
		buffer.append(", m_deptId = ").append(m_deptId);
		buffer.append(", m_stat = ").append(m_stat);
		buffer.append(", m_numBadCnts = ").append(m_numBadCnts);
		buffer.append(", m_remarks = ").append(m_remarks);
        buffer.append("]");
		return buffer.toString();
	}
}
