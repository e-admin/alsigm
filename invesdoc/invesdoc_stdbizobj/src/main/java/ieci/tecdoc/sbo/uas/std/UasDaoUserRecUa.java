package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;


public class UasDaoUserRecUa implements DbInputRecord
{

	//~ Instance fields --------------------------------------------------------

	private int m_stat;
	private int m_numBadCnts;

	//~ Constructors -----------------------------------------------------------

	public UasDaoUserRecUa(){}

	//~ Methods ----------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the numBadCnts.
	 */
	public int getNumBadCnts()
	{

		return m_numBadCnts;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param numBadCnts The numBadCnts to set.
	 */
	public void setNumBadCnts(int numBadCnts)
	{

		m_numBadCnts = numBadCnts;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the stat.
	 */
	public int getStat()
	{

		return m_stat;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param stat The stat to set.
	 */
	public void setStat(int stat)
	{

		m_stat = stat;

	}

	/* (non-Javadoc)
	 * @see ieci.tecdoc.core.database.DbInputRecord#setStatementValues(ieci.tecdoc.core.database.DbInputStatement)
	 */
	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;
		
		stmt.setLongInteger(i++, m_stat);
		stmt.setLongInteger(i++, m_numBadCnts);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasDaoUserRecUa[");
		buffer.append("m_stat = ").append(m_stat);
		buffer.append(", m_numBadCnts = ").append(m_numBadCnts);
		buffer.append("]");

		return buffer.toString();

	}

}
