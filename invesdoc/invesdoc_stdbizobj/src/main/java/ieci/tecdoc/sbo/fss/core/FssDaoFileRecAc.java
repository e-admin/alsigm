package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;

import java.util.Date;


public final class FssDaoFileRecAc implements DbInputRecord, DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int    m_id;
	public int    m_volId;
	public String m_loc;
	public int    m_extId1;
	public int    m_extId2;
	public int    m_extId3;
	public int    m_flags;
	public int    m_stat;
	public Date   m_ts;
	public double m_size;

	//~ Constructors -----------------------------------------------------------

	public FssDaoFileRecAc() {}

	//~ Methods ----------------------------------------------------------------

	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;

		stmt.setLongInteger(i++, m_id);
		stmt.setLongInteger(i++, m_volId);
		stmt.setShortText(i++, m_loc);
		stmt.setLongInteger(i++, m_extId1);
		stmt.setLongInteger(i++, m_extId2);
		stmt.setLongInteger(i++, m_extId3);
		stmt.setLongInteger(i++, m_flags);
		stmt.setLongInteger(i++, m_stat);
		stmt.setDateTime(i++, m_ts);
		stmt.setLongDecimal(i++, m_size);

	}

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_id     = stmt.getLongInteger(i++);
		m_volId  = stmt.getLongInteger(i++);
		m_loc    = stmt.getShortText(i++);
		m_extId1 = stmt.getLongInteger(i++);
		m_extId2 = stmt.getLongInteger(i++);
		m_extId3 = stmt.getLongInteger(i++);
		m_flags  = stmt.getLongInteger(i++);
		m_stat   = stmt.getLongInteger(i++);
		m_ts     = stmt.getDateTime(i++);
		m_size   = stmt.getLongDecimal(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoFileRecAc[");
		buffer.append("m_id = ").append(m_id);
		buffer.append(", m_volId = ").append(m_volId);
		buffer.append(", m_loc = ").append(m_loc);
		buffer.append(", m_extId1 = ").append(m_extId1);
		buffer.append(", m_extId2 = ").append(m_extId2);
		buffer.append(", m_extId3 = ").append(m_extId3);
		buffer.append(", m_flags = ").append(m_flags);
		buffer.append(", m_stat = ").append(m_stat);
		buffer.append(", m_ts = ").append(m_ts);
		buffer.append(", m_size = ").append(m_size);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
