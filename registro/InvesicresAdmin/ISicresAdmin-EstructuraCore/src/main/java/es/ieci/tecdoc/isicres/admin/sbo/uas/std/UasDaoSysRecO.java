package es.ieci.tecdoc.isicres.admin.sbo.uas.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;


public final class UasDaoSysRecO implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	private int    m_maxBadCnts;
	private double m_pwdVp;
	private short  m_pwdMinLen;

	//~ Constructors -----------------------------------------------------------

	public UasDaoSysRecO(){}

	//~ Methods ----------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the maxBadCnts.
	 */
	public int getMaxBadCnts()
	{

		return m_maxBadCnts;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwdMinLen.
	 */
	public short getPwdMinLen()
	{

		return m_pwdMinLen;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwdVp.
	 */
	public double getPwdVp()
	{

		return m_pwdVp;

	}

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;
		
		m_maxBadCnts = stmt.getLongInteger(i++);
		m_pwdVp		 = stmt.getLongDecimal(i++);
		m_pwdMinLen  = stmt.getShortInteger(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasDaoSysRecO[");
		buffer.append("m_maxBadCnts = ").append(m_maxBadCnts);
		buffer.append(", m_pwdVp = ").append(m_pwdVp);
		buffer.append(", m_pwdMinLen = ").append(m_pwdMinLen);
		buffer.append("]");

		return buffer.toString();

	}

}
