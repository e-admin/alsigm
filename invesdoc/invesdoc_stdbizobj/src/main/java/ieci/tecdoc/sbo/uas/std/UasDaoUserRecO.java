package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;


public final class UasDaoUserRecO implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	private int    m_id;
	private String m_name;
	private String m_pwd;
	private int    m_deptId;
	private int    m_stat;
	private int    m_numBadCnts;
	private double m_pwdLastUpdts;
	private String m_pwdMbc;
	private String m_pwdVpCheck;

	//~ Constructors -----------------------------------------------------------

	public UasDaoUserRecO(){}

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
	 * @return Returns the pwd.
	 */
	public String getPwd()
	{

		return m_pwd;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pwd The pwd to set.
	 */
	public void setPwd(String pwd)
	{

		m_pwd = pwd;

	}

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
	 * @return Returns the deptId.
	 */
	public int getDeptId()
	{

		return m_deptId;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the id.
	 */
	public int getId()
	{

		return m_id;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the name.
	 */
	public String getName()
	{

		return m_name;

	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pwdVpCheck.
	 */
	public String getPwdVpCheck()
	{

		return m_pwdVpCheck;

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

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;
		
		m_id		   = stmt.getLongInteger(i++);
		m_name		   = stmt.getShortText(i++);
		m_pwd		   = stmt.getShortText(i++);
		m_deptId	   = stmt.getLongInteger(i++);
		m_stat		   = stmt.getLongInteger(i++);
		m_numBadCnts   = stmt.getLongInteger(i++);
		m_pwdLastUpdts = stmt.getLongDecimal(i++);
		m_pwdMbc	   = stmt.getShortText(i++);
		m_pwdVpCheck   = stmt.getShortText(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasDaoUserRecA[");
		buffer.append("m_id = ").append(m_id);
		buffer.append(", m_name = ").append(m_name);
		buffer.append(", m_pwd = ").append(m_pwd);
		buffer.append(", m_deptId = ").append(m_deptId);
		buffer.append(", m_stat = ").append(m_stat);
		buffer.append(", m_numBadCnts = ").append(m_numBadCnts);
		buffer.append(", m_pwdLastUpdts = ").append(m_pwdLastUpdts);
		buffer.append(", m_pwdMbc = ").append(m_pwdMbc);
		buffer.append(", m_pwdVpCheck = ").append(m_pwdVpCheck);
		buffer.append("]");

		return buffer.toString();

	}

}


// class
