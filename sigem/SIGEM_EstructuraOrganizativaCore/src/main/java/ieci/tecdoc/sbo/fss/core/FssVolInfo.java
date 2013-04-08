package ieci.tecdoc.sbo.fss.core;

public final class FssVolInfo
{

	//~ Instance fields --------------------------------------------------------

	public int    m_repType;
	public String m_path;
	public String m_maxSize; //en bytes
	public int    m_flags;

	//~ Constructors -----------------------------------------------------------

	public FssVolInfo() {}

	//~ Methods ----------------------------------------------------------------

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssVolInfo[");
		buffer.append("m_repType = ").append(m_repType);
		buffer.append(", m_path = ").append(m_path);
		buffer.append(", m_maxSize = ").append(m_maxSize);
		buffer.append(", m_flags = ").append(m_flags);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
