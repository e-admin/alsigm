package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

import java.util.Date;


public final class DaoFdrStatRow implements DbInputRecord, DbOutputRecord
{
	public int    m_fdrId;
	public int    m_archId;
	public int    m_stat;
	public int    m_userId;
	public Date   m_ts;	
	public int    m_flags;	

	public DaoFdrStatRow()
	{
	}

	public void setStatementValues(DbInputStatement stmt)
					throws Exception
	{

		int i = 1;

		stmt.setLongInteger(i++, m_fdrId);
		stmt.setLongInteger(i++, m_archId);		
		stmt.setLongInteger(i++, m_stat);
		stmt.setLongInteger(i++, m_userId);			
		stmt.setDateTime(i++, m_ts);
		stmt.setLongInteger(i++, m_flags);

	}
	
	public void getStatementValues(DbOutputStatement stmt)
					throws Exception
	{

		int i = 1;

		m_fdrId  = stmt.getLongInteger(i++);
		m_archId = stmt.getLongInteger(i++);		
		m_stat   = stmt.getLongInteger(i++);		
		m_userId = stmt.getLongInteger(i++);			
		m_ts     = stmt.getDateTime(i++);
		m_flags  = stmt.getLongInteger(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("DaoStatRow[");
		buffer.append("FrId = ").append(m_fdrId);
		buffer.append(", ArchId = ").append(m_archId);
		buffer.append(", Stat = ").append(m_stat);
		buffer.append(", UserId = ").append(m_userId);
		buffer.append(", TimeStamp = ").append(m_ts);
		buffer.append(", Flags = ").append(m_flags);		
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
