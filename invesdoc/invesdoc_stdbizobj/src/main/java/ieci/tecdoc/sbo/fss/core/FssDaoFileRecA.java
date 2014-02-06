package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;


public final class FssDaoFileRecA implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int    m_id;
	public int    m_volId;
	public String m_loc;
   public int    m_flags;
   public double m_size;

	//~ Constructors -----------------------------------------------------------

	public FssDaoFileRecA() {}

	//~ Methods ----------------------------------------------------------------

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_id    = stmt.getLongInteger(i++);
		m_volId = stmt.getLongInteger(i++);
		m_loc   = stmt.getShortText(i++);
      m_flags = stmt.getLongInteger(i++);
      m_size  = stmt.getLongDecimal(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoFileRecA[");
		buffer.append("m_id = ").append(m_id);
		buffer.append(", m_volId = ").append(m_volId);
		buffer.append(", m_loc = ").append(m_loc);
      buffer.append(", m_flags = ").append(m_flags);
      buffer.append(", m_size = ").append(m_size);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
