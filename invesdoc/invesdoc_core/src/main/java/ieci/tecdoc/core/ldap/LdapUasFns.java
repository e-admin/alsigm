
package ieci.tecdoc.core.ldap;

import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;

public final class LdapUasFns
{  
   
   private LdapUasFns()
   {
   }
   
   public static String findUserDn(LdapConnection conn, 
                                   String start, int scope,
                                   String attrName, String attrValue)
                        throws Exception
   {
      
      String     dn = null;
      LdapFilter filter;
      
      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getUserFilter(conn));
      filter.addFilter(attrName, attrValue);
      
      dn = LdapBasicFns.findEntryDn(conn, start, scope, filter.getText());
      
      return dn;
      
   }
   
   public static String findUserDn(LdapConnection conn, 
                                   String start, int scope,
                                   String filter)
                        throws Exception
   {
      
      String     dn = null;
      LdapFilter ffilter;
      
      ffilter = new LdapFilter();
      ffilter.addFilter(LdapFilter.getUserFilter(conn));
      ffilter.addFilter(filter);
      
      dn = LdapBasicFns.findEntryDn(conn, start, scope, ffilter.getText());
      
      return dn;
      
   }
   
   public static String findUserDnByGuid(LdapConnection conn, 
                                         String start, int scope,
                                         String guid)
                        throws Exception
   {
      
      String dn = null;
      
      dn = findUserDn(conn, start, scope,
                      LdapAttribute.getGuidAttributeName(conn),
                      LdapBasicFns.formatGuid(conn,guid));
      
      return dn;
      
   }
   
   public static String findUserGuid(LdapConnection conn, 
                                     String start, int scope,
                                     String attrName, String attrValue)
                        throws Exception
   {
      
      String     guid = null;
      LdapFilter filter;
      
      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getUserFilter(conn));
      filter.addFilter(attrName, attrValue);

      guid = LdapBasicFns.findEntryGuid(conn, start, scope, filter.getText());
      
      return guid;
      
   }
   
   public static String findUserGuid(LdapConnection conn, 
                                     String start, int scope,
                                     String filter)
                        throws Exception
   {
      
      String     guid = null;
      LdapFilter ffilter;
      
      ffilter = new LdapFilter();
      ffilter.addFilter(LdapFilter.getUserFilter(conn));
      ffilter.addFilter(filter);

      guid = LdapBasicFns.findEntryGuid(conn, start, scope, ffilter.getText());
      
      return guid;
      
   }
   
   public static String findUserGuidByDn(LdapConnection conn, String dn)
                        throws Exception
   {
      
      String     guid = null;
      String     start;
      int        scope;
      LdapFilter filter;
      
      start = conn.getBaseRPath(dn);      
      scope = LdapScope.BASE;
      
      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getUserFilter(conn));

      guid = LdapBasicFns.findEntryGuid(conn, start, scope, filter.getText());
      
      return guid;
      
   }

   // Si AD, start y scope no son necesarios: null y 0.
   
   public static IeciTdShortTextArrayList
   findUserGroupDns(LdapConnection conn, String start, int scope,
                    String userDn) throws Exception
   {
      
      IeciTdShortTextArrayList groupDns = null;
      int                      engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         groupDns = findAdUserGroupDns(conn, userDn);
      else
         groupDns = findIpUserGroupDns(conn, start, scope, userDn);
      
      return groupDns;
      
   }

   // Si AD, start y scope no son necesarios: null y 0.
   
   public static IeciTdShortTextArrayList
   findUserGroupGuids(LdapConnection conn, String start, int scope,
                      String userDn) throws Exception
   {
      
      IeciTdShortTextArrayList groupGuids = null;
      int                      engine;
      IeciTdShortTextArrayList groupDns;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
      {         
         groupDns   = findAdUserGroupDns(conn, userDn);
         groupGuids = findAdUserGroupGuids(conn, groupDns);         
      }
      else
         groupGuids = findIpUserGroupGuids(conn, start, scope, userDn);
      
      return groupGuids;
      
   }
   
   public static String findGroupDn(LdapConnection conn, 
                                   String start, int scope,
                                   String attrName, String attrValue)
                        throws Exception
   {
      
      String     dn = null;
      LdapFilter filter;
      
      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getGroupFilter(conn));
      filter.addFilter(attrName, attrValue);
      
      dn = LdapBasicFns.findEntryDn(conn, start, scope, filter.getText());
      
      return dn;
      
   }
   
   public static String findGroupGuidByDn(LdapConnection conn, String dn)
                        throws Exception
   {
      
      String     guid = null;
      String     start;
      int        scope;
      LdapFilter filter;
      
      start = conn.getBaseRPath(dn);      
      scope = LdapScope.BASE;
      
      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getGroupFilter(conn));

      guid = LdapBasicFns.findEntryGuid(conn, start, scope, filter.getText());
      
      return guid;
      
   }
   
  
   
   // **************************************************************************

   private static IeciTdShortTextArrayList 
   findAdUserGroupDns(LdapConnection conn, String userDn) throws Exception
   {
      
      IeciTdShortTextArrayList groupDns = null;                        

      groupDns = LdapBasicFns.findEntryAttributeValues(conn, userDn,
                                                       "memberOf");
                                          
      return groupDns;
      
   }

   private static IeciTdShortTextArrayList
   findAdUserGroupGuids(LdapConnection conn, IeciTdShortTextArrayList groupDns)
   throws Exception
   {
      
      IeciTdShortTextArrayList groupGuids = null;      
      int                      count, i;
      String                   groupDn, groupGuid;
         
      groupGuids = new IeciTdShortTextArrayList();
         
      count = groupDns.count();
         
      for (i = 0; i < count; i++)
      {
            
         groupDn   = groupDns.get(i);            
         groupGuid = findGroupGuidByDn(conn, groupDn);
            
         groupGuids.add(groupGuid);
            
      }
         
      return groupGuids;
      
   }

   private static IeciTdShortTextArrayList 
   findIpUserGroupDns(LdapConnection conn, String start, int scope,
                      String userDn) throws Exception
   {
      
      IeciTdShortTextArrayList groupDns = null;                     
      LdapFilter               filter;

      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getGroupFilter(conn));
      filter.addFilter("uniqueMember", userDn);
      
      groupDns = LdapBasicFns.findEntryDns(conn, start, scope, 
                                           filter.getText());
                                                       
      return groupDns;
      
   }
   
   private static IeciTdShortTextArrayList 
   findIpUserGroupGuids(LdapConnection conn, String start, int scope,
                        String userDn) throws Exception
   {
      
      IeciTdShortTextArrayList groupDns = null;                     
      LdapFilter               filter;

      filter = new LdapFilter();
      filter.addFilter(LdapFilter.getGroupFilter(conn));
      filter.addFilter("uniqueMember", userDn);
      
      groupDns = LdapBasicFns.findEntryGuids(conn, start, scope, 
                                             filter.getText());
                                                       
      return groupDns;
      
   }
   
} // class
