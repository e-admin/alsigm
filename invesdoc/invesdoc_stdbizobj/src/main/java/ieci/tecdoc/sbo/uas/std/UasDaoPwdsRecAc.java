package ieci.tecdoc.sbo.uas.std;

import java.util.Date;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;
import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;

public final class UasDaoPwdsRecAc implements DbInputRecord, DbOutputRecord
{

   // ~ Instance fields --------------------------------------------------------

	public int    m_id;	
	public String m_pwd;	
	public int    m_updId;
	public Date   m_updDate;

	//~ Constructors -----------------------------------------------------------

	public UasDaoPwdsRecAc() {}

	//~ Methods ----------------------------------------------------------------

	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;

		stmt.setLongInteger(i++, m_id);		
		stmt.setShortText(i++, m_pwd);
		stmt.setLongInteger(i++, m_updId);
		stmt.setDateTime(i++, m_updDate);
		
	}

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_id      = stmt.getLongInteger(i++);		
		m_pwd     = stmt.getShortText(i++);
		m_updId   = stmt.getLongInteger(i++);
		m_updDate = stmt.getDateTime(i++);


	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("UasDaoPwdsRecAc[");
		buffer.append("m_id = ").append(m_id);		
		buffer.append(", m_pwd = ").append(m_pwd);
		buffer.append(", m_updId = ").append(m_updId);		
		buffer.append(", m_updDate = ").append(m_updDate);		
		buffer.append("]");
		return buffer.toString();

	}

}
