package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbInputRecord;
import ieci.tecdoc.core.db.DbInputStatement;


public final class UasDaoUserRecUb implements DbInputRecord
{

	//~ Instance fields --------------------------------------------------------

	private String m_pwd;
   	private double m_pwdLastUpdts;
	private String m_pwdMbc;
	
	//~ Constructors -----------------------------------------------------------

	public UasDaoUserRecUb()
	{
		super();

	}

	//~ Methods ----------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwdLastUpdts.
	 */
	public double getPwdLastUpdts()
	{

		return m_pwdLastUpdts;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pwdLastUpdts The pwdLastUpdts to set.
	 */
	public void setPwdLastUpdts(double pwdLastUpdts)
	{

		m_pwdLastUpdts = pwdLastUpdts;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwdMbc.
	 */
	public String getPwdMbc()
	{

		return m_pwdMbc;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pwdMbc The pwdMbc to set.
	 */
	public void setPwdMbc(String pwdMbc)
	{

		m_pwdMbc = pwdMbc;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwd.
	 */
	public String getPwd()
	{

		return m_pwd;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pwd The password.
	 */
	public void setPwd(String pwd)
	{

		m_pwd = pwd;

	}

	public void setStatementValues(DbInputStatement stmt)
							throws Exception
	{

		int i = 1;
		
		stmt.setShortText(i++, m_pwd);
		stmt.setLongDecimal(i++, m_pwdLastUpdts);
		stmt.setShortText(i++, m_pwdMbc);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasDaoUserRecUb[");
		buffer.append("m_pwd = ").append(m_pwd);
		buffer.append(", m_pwdMbc = ").append(m_pwdMbc);
		buffer.append(", m_pwdLastUpdts = ").append(m_pwdLastUpdts);
		buffer.append("]");

		return buffer.toString();

	}

}
