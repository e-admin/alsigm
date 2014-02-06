package ieci.tecdoc.sbo.fss.core;

public final class FssFtsInfo
{

	//~ Instance fields --------------------------------------------------------

	public int    m_ftsPlatform;
	public String m_ftsRoot;
	
	//~ Constructors -----------------------------------------------------------

	public FssFtsInfo()
	{
	}	

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssFtsInfo[");
		buffer.append("FtsPlatform = ").append(m_ftsPlatform);
		buffer.append(", FtsRoot = ").append(m_ftsRoot);		
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
