package ieci.tecdoc.sbo.uas.base;

public final class UasAuthTokenUser
{

	//~ Instance fields --------------------------------------------------------

	private int  m_id;	

	private UasAuthTokenUser(){}

	public UasAuthTokenUser(int id)
	{
		m_id = id;
	}
	
	public int getId()
	{
		return m_id;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param id The id to set.
	 */
	public void setId(int id)
	{

		m_id = id;

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasAuthTokenUser[");
		buffer.append("m_id = ").append(m_id);		
		buffer.append("]");

		return buffer.toString();

	}

}


// class
