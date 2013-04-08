
package ieci.tecdoc.core.ldap;

public final class LdapAttribute
{  

   private static final String AD_GUID = "objectGUID";
   private static final String IP_GUID = "nsuniqueid";
   private static final String OL_GUID = "uidnumber";
   

   private static final String AD_OCLS = "objectClass";
   private static final String IP_OCLS = "objectClass";
   private static final String OL_OCLS = "objectClass";
   
   private LdapAttribute()
   {
   }
   
   public static String getGuidAttributeName(LdapConnection conn)
   {
      
      String name = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         name = AD_GUID;
      else if (engine == LdapEngine.I_PLANET)
         name = IP_GUID;
      else
         name = OL_GUID;
      
      return name;
      
   }
   
   public static String getObjectClassAttributeName(LdapConnection conn)
   {
      
      String name = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         name = AD_OCLS;
      else if (engine == LdapEngine.I_PLANET)
         name = IP_OCLS;
      else
         name = OL_OCLS;
      
      return name;
      
   }
   
} // class
