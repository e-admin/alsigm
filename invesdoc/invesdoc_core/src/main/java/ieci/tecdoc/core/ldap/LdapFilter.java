
package ieci.tecdoc.core.ldap;

public final class LdapFilter
{  
   
   private static final String AD_NULL_FILTER =
   "(objectClass=*)";
   private static final String IP_NULL_FILTER =
   "(objectClass=*)";
   private static final String OL_NULL_FILTER =
   "(objectClass=*)";   
   
   private static final String AD_USER_FILTER =
   "(&(objectClass=person)(objectClass=user))";
   private static final String IP_USER_FILTER =
   "(objectClass=person)";
   private static final String OL_USER_FILTER =
   "(objectClass=person)";
   
   private static final String AD_GROUP_FILTER =
   "(objectClass=group)";
   private static final String IP_GROUP_FILTER =
   "(objectClass=groupofuniquenames)";
   private static final String OL_GROUP_FILTER =
   "(objectClass=groupofuniquenames)";
   
   private static final String AD_OU_FILTER =
   "(objectClass=organizationalUnit)";
   private static final String IP_OU_FILTER =
   "(objectClass=organizationalUnit)";
   private static final String OL_OU_FILTER =
   "(objectClass=organizationalUnit)";

   
   private StringBuffer m_tbdr;

   public LdapFilter()
   {
      m_tbdr = new StringBuffer();
   }
   
   public void addFilter(String filter)
   {
      
      int len = m_tbdr.length();
      
      if (len > 0)
         m_tbdr.insert(0, "(&");
      
      m_tbdr.append(filter);
      
      if (len > 0)
         m_tbdr.append(")");
      
   }
   
   public void addFilter(String attrName, String attrValue)
   {
            
      int len = m_tbdr.length();
      
      if (len > 0)
         m_tbdr.insert(0, "(&");

      m_tbdr.append("(");
      m_tbdr.append(attrName).append("=").append(attrValue);
      m_tbdr.append(")");
      
      if (len > 0)
         m_tbdr.append(")");
      
   }
   
   public String getText()
   {
      return m_tbdr.toString();
   }
   
   // **************************************************************************
   
   public static String getNullFilter(LdapConnection conn)
   {
      
      String filter = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         filter = AD_NULL_FILTER;
      else if (engine == LdapEngine.I_PLANET)
         filter = IP_NULL_FILTER;
      else
         filter = OL_NULL_FILTER;
      
      return filter;
      
   }
   
   public static String getUserFilter(LdapConnection conn)
   {
      
      String filter = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         filter = AD_USER_FILTER;
      else if (engine == LdapEngine.I_PLANET)
         filter = IP_USER_FILTER;
      else
         filter = OL_USER_FILTER;
      
      return filter;
      
   }
   
   public static String getGroupFilter(LdapConnection conn)
   {
      
      String filter = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         filter = AD_GROUP_FILTER;
      else if (engine == LdapEngine.I_PLANET)
         filter = IP_GROUP_FILTER;
      else
         filter = OL_GROUP_FILTER;
      
      return filter;
      
   }
   
   public static String getOuFilter(LdapConnection conn)
   {
      
      String filter = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         filter = AD_OU_FILTER;
      else if (engine == LdapEngine.I_PLANET)
         filter = IP_OU_FILTER;
      else
         filter = OL_OU_FILTER;
      
      return filter;
      
   }
   
} // class
