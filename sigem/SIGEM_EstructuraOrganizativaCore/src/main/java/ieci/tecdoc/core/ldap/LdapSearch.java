
package ieci.tecdoc.core.ldap;

import javax.naming.directory.DirContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;

public final class LdapSearch
{ 

   private LdapConnection    m_conn;
   private String            m_start;
   private int               m_scope;
   private String            m_filter;
   private String[]          m_retAttrs;
   private int               m_maxCount;   
   private NamingEnumeration m_ne;
   private SearchResult      m_sr;
   private Attributes        m_srAttrs;

   public LdapSearch()
   {
      m_conn     = null;
      m_start    = null;
      m_scope    = SearchControls.OBJECT_SCOPE;
      m_filter   = null;
      m_retAttrs = null;
      m_maxCount = 0;      
      m_ne       = null;
      m_sr       = null;
      m_srAttrs  = null;
   }

   public void initialize(LdapConnection conn, String start,
                          int scope, String filter,
                          String[] retAttrs, int maxCount)
   {
      m_conn     = conn;
      m_start    = start;
      m_scope    = scope;
      m_filter   = filter;
      m_retAttrs = retAttrs;
      m_maxCount = maxCount;
   }

   public void release() throws Exception
   {
      if (m_ne != null)
         m_ne.close();
   }

   public static void ensureRelease(LdapSearch search, Exception exc)
                      throws Exception
   {
      
      try
      {
         if (search != null) search.release();
         throw exc;
      }
      catch (Exception e)
      {
         throw exc;
      }
      
   }
   
   public void execute() throws Exception
   {
      m_ne = search(m_conn.getInitDirContext(), m_start, m_scope, 
                    m_filter, m_retAttrs, m_maxCount);
   }
   
   public boolean next() throws Exception
   {
      
      boolean hm;
      
      hm = m_ne.hasMoreElements();
      
      if (hm)
      {
         
         m_sr = (SearchResult) m_ne.nextElement();
         
         m_srAttrs = m_sr.getAttributes();
         
      }
      
      return hm;
      
   }
   
   public int getAttributeValueCount(String attrName)
   {
      
      Attribute attr;
      
      attr = m_srAttrs.get(attrName);
      
      if (attr == null)
         return 0;
      else
         return attr.size();
      
   }
   
   public Object getAttributeValue(String attrName, int valIdx) throws Exception
   {
      return m_srAttrs.get(attrName).get(valIdx);
   }
   
   public Object getAttributeValue(String attrName) throws Exception
   {
      return m_srAttrs.get(attrName).get(0);
   }

   public String getEntryDn() throws Exception
   {      
      if ((m_start != null) && (!"".equals(m_start)))
         return m_sr.getName() + "," + m_start + "," + m_conn.getBaseDn();
      else
         return m_sr.getName() + "," + m_conn.getBaseDn();
   }
   
   // **************************************************************************
   
   private static NamingEnumeration search(DirContext dirCtx,
                                           String start, int scope, 
                                           String filter, String[] attrs,
                                           int maxCount)
                                    throws Exception
   {
      
      NamingEnumeration ne = null;
      SearchControls    sc;
      
      sc = new SearchControls();
      
      sc.setSearchScope(scope);
      sc.setReturningAttributes(attrs);
      sc.setCountLimit(maxCount);
      
      ne = dirCtx.search(start, filter, sc);
      
      return ne;
      
   }
   
} // class
