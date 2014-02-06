package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;


public final class FssDaoActDirRecA implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int m_volId;
	public int m_actDir;
	public int m_numFiles;

	//~ Constructors -----------------------------------------------------------

	public FssDaoActDirRecA() {}

	//~ Methods ----------------------------------------------------------------

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_volId    = stmt.getLongInteger(i++);
		m_actDir   = stmt.getLongInteger(i++);
		m_numFiles = stmt.getLongInteger(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssDaoActDirRecA[");
		buffer.append("m_volId = ").append(m_volId);
		buffer.append(", m_actDir = ").append(m_actDir);
		buffer.append(", m_numFiles = ").append(m_numFiles);
		buffer.append("]");
		return buffer.toString();

	}

}


// class
