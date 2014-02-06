package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;

import java.util.Date;


public final class FssDaoFtsRecAc implements DbInputRecord, DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int    m_id;		
	public int    m_extId1;
	public int    m_extId2;
	public int    m_extId3;
	public int    m_extId4;
	public String m_path;	
	public Date   m_ts;

	//~ Constructors -----------------------------------------------------------

	public FssDaoFtsRecAc() {}

	//~ Methods ----------------------------------------------------------------

	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;

		stmt.setLongInteger(i++, m_id);			
		stmt.setLongInteger(i++, m_extId1);
		stmt.setLongInteger(i++, m_extId2);
		stmt.setLongInteger(i++, m_extId3);
		stmt.setLongInteger(i++, m_extId4);
		stmt.setShortText(i++, m_path);		
		stmt.setDateTime(i++, m_ts);

	}

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_id     = stmt.getLongInteger(i++);				
		m_extId1 = stmt.getLongInteger(i++);
		m_extId2 = stmt.getLongInteger(i++);
		m_extId3 = stmt.getLongInteger(i++);
		m_extId4 = stmt.getLongInteger(i++);
		m_path   = stmt.getShortText(i++);		
		m_ts     = stmt.getDateTime(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoFtsRecAc[");
		buffer.append("m_id = ").append(m_id);			
		buffer.append(", m_extId1 = ").append(m_extId1);
		buffer.append(", m_extId2 = ").append(m_extId2);
		buffer.append(", m_extId3 = ").append(m_extId3);
		buffer.append(", m_extId4 = ").append(m_extId4);
		buffer.append(", m_path = ").append(m_path);		
		buffer.append(", m_ts = ").append(m_ts);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
