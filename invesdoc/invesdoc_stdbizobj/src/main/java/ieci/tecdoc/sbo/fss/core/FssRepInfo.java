package ieci.tecdoc.sbo.fss.core;

public final class FssRepInfo
{

	//~ Instance fields --------------------------------------------------------

	public int    m_type;
	public int    m_os;
	public String m_path;
	public String m_srv;
	public int    m_port;
	public String m_usr;
	public String m_pwd;
	public int    m_flags;

	//~ Constructors -----------------------------------------------------------

	public FssRepInfo() {}

	//~ Methods ----------------------------------------------------------------

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssRepInfo[");
		buffer.append("m_type = ").append(m_type);
		buffer.append(", m_os = ").append(m_os);
		buffer.append(", m_path = ").append(m_path);
		buffer.append(", m_srv = ").append(m_srv);
		buffer.append(", m_port = ").append(m_port);
		buffer.append(", m_usr = ").append(m_usr);
		buffer.append(", m_pwd = ").append(m_pwd);
		buffer.append(", m_flags = ").append(m_flags);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
