package ieci.tecdoc.sbo.uas.base;

public final class UasAuthTokenDept
{

	//~ Instance fields --------------------------------------------------------

	private int m_id;

	//~ Constructors -----------------------------------------------------------

	private UasAuthTokenDept(){}

	public UasAuthTokenDept(int id)
	{

		m_id = id;

	}

	//~ Methods ----------------------------------------------------------------

	public int getId()
	{

		return m_id;

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasAuthTokenDept[");
		buffer.append("m_id = ").append(m_id);
		buffer.append("]");

		return buffer.toString();

	}

}


// class
