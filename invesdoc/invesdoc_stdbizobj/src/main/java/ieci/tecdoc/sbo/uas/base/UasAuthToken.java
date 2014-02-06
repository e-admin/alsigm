package ieci.tecdoc.sbo.uas.base;

public final class UasAuthToken
{

	//~ Instance fields --------------------------------------------------------

	private UasAuthTokenUser   m_user;
	private UasAuthTokenDept   m_dept;
	private UasAuthTokenGroups m_groups;

	//~ Constructors -----------------------------------------------------------

	public UasAuthToken()
	{

		m_user   = null;
		m_dept   = null;
		m_groups = new UasAuthTokenGroups();

	}

	//~ Methods ----------------------------------------------------------------

	public void setUser(int id)
	{

		m_user = new UasAuthTokenUser(id);

	}

	public void setDept(int id)
	{

		m_dept = new UasAuthTokenDept(id);

	}

	public void addGroup(int id)
	{

		m_groups.add(id);

	}

	public UasAuthTokenUser getUser()
	{

		return m_user;

	}

	public UasAuthTokenDept getDept()
	{

		return m_dept;

	}

	public UasAuthTokenGroups getGroups()
	{

		return m_groups;

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasAuthToken[");
		buffer.append("m_user = ").append(m_user);
		buffer.append(", m_dept = ").append(m_dept);
		buffer.append(", m_groups = ").append(m_groups);
		buffer.append("]");

		return buffer.toString();

	}

}


// class
