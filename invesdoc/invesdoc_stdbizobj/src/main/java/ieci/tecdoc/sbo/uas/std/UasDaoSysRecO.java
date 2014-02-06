package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;


public final class UasDaoSysRecO implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	private int    m_maxBadCnts;
	private double m_pwdVp;
	private short  m_pwdMinLen;
   private short  m_numPwdLock;
   private int    m_flags;

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
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the NumPwdLock.
	 */
	public short getNumPwdLock()
	{
	
	   return m_numPwdLock;
	   
	}
	
	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the Flags.
	 */
	public int getFlags()
	{
	
	   return m_flags;
	   
	}

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;
		
		m_maxBadCnts = stmt.getLongInteger(i++);
		m_pwdVp		 = stmt.getLongDecimal(i++);
		m_pwdMinLen  = stmt.getShortInteger(i++);
		m_numPwdLock = stmt.getShortInteger(i++);
		m_flags      = stmt.getLongInteger(i++);

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
		buffer.append(", m_numPwdLock = ").append(m_numPwdLock);
		buffer.append(", m_flags = ").append(m_flags);
		buffer.append("]");

		return buffer.toString();

	}

}
