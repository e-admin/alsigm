package ieci.tecdoc.sbo.uas.ldap;


public final class UasAuthConfig
{

	//~ Instance fields --------------------------------------------------------

    private int     m_maxNumTries;
    private boolean m_userSearchByDn;
    private String  m_userAttrName;
    private String  m_userStart;
    private int     m_userScope;
    private String  m_groupStart;
    private int     m_groupScope;	

	//~ Constructors -----------------------------------------------------------

	public UasAuthConfig(){}

	//~ Methods ----------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the groupScope.
	 */
	public int getGroupScope()
	{
		return m_groupScope;
	}
   
   public void setGroupScope(int scope)
   {
      m_groupScope = scope;
   }

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the groupStart.
	 */
	public String getGroupStart()
	{
		return m_groupStart;
	}
   
   public void setGroupStart(String start)
   {
      m_groupStart = start;
   }

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the maxNumTries.
	 */
	public int getMaxNumTries()
	{
		return m_maxNumTries;
	}
   
   public void setMaxNumTries(int maxNumTries)
   {
      m_maxNumTries = maxNumTries;
   }

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the userAttrName.
	 */
	public String getUserAttrName()
	{
		return m_userAttrName;
	}
   
   public void setUserAttrName(String attrName)
   {
      m_userAttrName = attrName;
   }

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the userScope.
	 */
	public int getUserScope()
	{
		return m_userScope;
	}
   
   public void setUserScope(int scope)
   {
      m_userScope = scope;
   }

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the userSearchByDn.
	 */
	public boolean isUserSearchByDn()
	{
		return m_userSearchByDn;
	}
   
   public void setUserSearchByDn(boolean byDn)
   {
      m_userSearchByDn = byDn;
   }

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the userStart.
	 */
	public String getUserStart()
	{
		return m_userStart;
	}

   public void setUserStart(String start)
   {
      m_userStart = start;
   }	

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("UasAuthConfig[");		
		buffer.append(", m_maxNumTries = ").append(m_maxNumTries);
		buffer.append(", m_userSearchByDn = ").append(m_userSearchByDn);
		buffer.append(", m_userAttrName = ").append(m_userAttrName);
		buffer.append(", m_userStart = ").append(m_userStart);
		buffer.append(", m_userScope = ").append(m_userScope);
		buffer.append(", m_groupStart = ").append(m_groupStart);
		buffer.append(", m_groupScope = ").append(m_groupScope);
		buffer.append("]");

		return buffer.toString();

	}

}
 // class
