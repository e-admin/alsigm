package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;


public final class FssDaoVolRecUc implements DbInputRecord
{

	//~ Instance fields --------------------------------------------------------

	public String m_actSize;
	public int    m_stat;

	//~ Constructors -----------------------------------------------------------

	public FssDaoVolRecUc() {}

	//~ Methods ----------------------------------------------------------------

	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;

		stmt.setShortText(i++, m_actSize);
		stmt.setLongInteger(i++, m_stat);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoVolRecUc[");
		buffer.append("m_actSize = ").append(m_actSize);
		buffer.append(", m_stat = ").append(m_stat);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
