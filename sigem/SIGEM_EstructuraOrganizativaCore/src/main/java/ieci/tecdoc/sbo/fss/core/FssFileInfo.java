package ieci.tecdoc.sbo.fss.core;

public final class FssFileInfo
{

	//~ Instance fields --------------------------------------------------------

	public String m_ext;
	public int    m_extId1;
	public int    m_extId2;
	public int    m_extId3;
	public int    m_extId4;
	public int    m_flags;

	//~ Constructors -----------------------------------------------------------

	public FssFileInfo()
	{
	    m_extId1 = 0;
	    m_extId2 = 0;
	    m_extId3 = 0;
	    m_extId4 = 0;
		m_flags  = FssMdoUtil.FILE_FLAG_NONE;

	}	

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("FssFileInfo[");
		buffer.append("m_ext = ").append(m_ext);
		buffer.append(", m_extId1 = ").append(m_extId1);
		buffer.append(", m_extId2 = ").append(m_extId2);
		buffer.append(", m_extId3 = ").append(m_extId3);
		buffer.append(", m_extId4 = ").append(m_extId4);
		buffer.append(", m_flags = ").append(m_flags);
		buffer.append("]");
		return buffer.toString();

	}

   /**
    * @param ext The ext to set.
    */
   public void setExt(String ext)
   {
      m_ext = ext;
   }
}
 // class
