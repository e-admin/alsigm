package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;


public final class FssDaoVolRecA implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int    m_id;
	public int    m_repId;
	public String m_info;
	public String m_actSize;
	public int    m_stat;

	//~ Constructors -----------------------------------------------------------

	public FssDaoVolRecA() {}

	//~ Methods ----------------------------------------------------------------

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_id	  = stmt.getLongInteger(i++);
		m_repId   = stmt.getLongInteger(i++);
		m_info    = stmt.getLongText(i++);
		m_actSize = stmt.getShortText(i++);
		m_stat    = stmt.getLongInteger(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoVolRecA[");
		buffer.append("m_id = ").append(m_id);
		buffer.append(", m_repId = ").append(m_repId);
		buffer.append(", m_info = ").append(m_info);
		buffer.append(", m_actSize = ").append(m_actSize);
		buffer.append(", m_stat = ").append(m_stat);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
