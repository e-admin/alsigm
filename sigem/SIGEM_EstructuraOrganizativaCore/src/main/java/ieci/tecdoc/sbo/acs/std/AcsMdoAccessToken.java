
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.sbo.uas.base.UasAuthToken;
import ieci.tecdoc.sbo.uas.base.UasAuthTokenGroup;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.acs.base.AcsDefs;

public class AcsMdoAccessToken
{
   
   private AcsMdoAccessToken()
   {      
   }
   
   public static AcsAccessToken createiDocAccessToken(UasAuthToken authToken, String entidad)
                                throws Exception
   {
      
      AcsAccessToken           token;
      int                      id;      
      String                   profile;
      int                      count, i;
      UasAuthTokenGroup        group;
      int                      genPerms;

      token = new AcsAccessToken();
   
      id       = authToken.getUser().getId();
      profile  = AcsMdoProfile.getiDocProfile(id, entidad);
      genPerms = AcsMdoGenPerms.getUseriDocGenPerms(id, entidad);
      
      token.setUser(id, profile, genPerms);

      if ( ( authToken.getDept() != null ) && ( id != AcsDefs.SYS_XSUPERUSER_ID ) )
      {
         id       = authToken.getDept().getId();
         genPerms = AcsMdoGenPerms.getDeptiDocGenPerms(id, entidad);
         
         token.setDept(id, genPerms);         
      }
      
      count = authToken.getGroups().count();
      
      for (i = 0; i < count; i++)
      {
         
         group = authToken.getGroups().get(i);
         
         id       = group.getId();
         genPerms = AcsMdoGenPerms.getGroupiDocGenPerms(id, entidad);
         
         token.addGroup(id, genPerms);
         
      }
      
      return token;
      
   }

   public static AcsAccessToken createiUserAccessToken(UasAuthToken authToken, String entidad)
                                throws Exception
   {
      
      AcsAccessToken           token;
      int                      id;      
      String                   profile;
      int                      count, i;
      UasAuthTokenGroup        group;
      int                      genPerms;

      token = new AcsAccessToken();
   
      id       = authToken.getUser().getId();
      profile  = AcsMdoProfile.getiUserProfile(id, entidad);
      genPerms = 0;
      
      token.setUser(id, profile, genPerms);

      if ( ( authToken.getDept() != null ) && ( id != AcsDefs.SYS_XSUPERUSER_ID ) )
      {
         id       = authToken.getDept().getId();
         genPerms = 0;
         
         token.setDept(id, genPerms);         
      }
      
      count = authToken.getGroups().count();
      
      for (i = 0; i < count; i++)
      {
         
         group = authToken.getGroups().get(i);
         
         id       = group.getId();
         genPerms = 0;
         
         token.addGroup(id, genPerms);
         
      }
      
      return token;
      
   }

   public static AcsAccessToken createiVolAccessToken(UasAuthToken authToken, String entidad)
                                throws Exception
   {
      
      AcsAccessToken           token;
      int                      id;      
      String                   profile;
      int                      count, i;
      UasAuthTokenGroup        group;
      int                      genPerms;

      token = new AcsAccessToken();
   
      id       = authToken.getUser().getId();
      profile  = AcsMdoProfile.getiVolProfile(id, entidad);
      genPerms = 0;
      
      token.setUser(id, profile, genPerms);

      if ( ( authToken.getDept() != null ) && ( id != AcsDefs.SYS_XSUPERUSER_ID ) )
      {
         id       = authToken.getDept().getId();
         genPerms = 0;
         
         token.setDept(id, genPerms);         
      }
      
      count = authToken.getGroups().count();
      
      for (i = 0; i < count; i++)
      {
         
         group = authToken.getGroups().get(i);
         
         id       = group.getId();
         genPerms = 0;
         
         token.addGroup(id, genPerms);
         
      }
      
      return token;
      
   }
   
} // class
