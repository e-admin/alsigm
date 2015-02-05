
package ieci.tecdoc.sgm.base.ldap;

public final class LdapAttribute
{  

   private static final String AD_GUID = "objectGUID";
   private static final String IP_GUID = "nsuniqueid";

   private static final String AD_OCLS = "objectClass";
   private static final String IP_OCLS = "objectClass";
   
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
      else
         name = IP_GUID;
      
      return name;
      
   }
   
   public static String getObjectClassAttributeName(LdapConnection conn)
   {
      
      String name = null;
      int    engine;
      
      engine = conn.getEngine();
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY)
         name = AD_OCLS;
      else
         name = IP_OCLS;
      
      return name;
      
   }
   
} // class
