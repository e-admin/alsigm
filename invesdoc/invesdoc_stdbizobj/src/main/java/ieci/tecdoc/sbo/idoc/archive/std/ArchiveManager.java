
package ieci.tecdoc.sbo.idoc.archive.std;

import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoArchive;

public class ArchiveManager
{
   
   private ArchiveManager()
   {      
   }
   
   public static boolean canLoadArchive(AcsAccessToken accToken,
                                        int archId) 
                         throws Exception
   {
      
      boolean can = false;       
     
      can = AcsMdoArchive.hasAuthView(accToken, archId);
      
      return can;
      
   }
   
   public static ArchiveToken loadArchive(AcsAccessToken accToken, int archId)
                              throws Exception
   {
      
      ArchiveToken  token; 
      
      checkCanLoadArchive(accToken, archId);
      
      token = ArchiveMdoToken.createArchiveToken(accToken, archId);      
      
      return token;    
      
   }
   
   private static void checkCanLoadArchive(AcsAccessToken accToken,
                                           int archId)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canLoadArchive(accToken, archId))
         throw new IeciTdException(ArchiveError.EC_NOT_AUTH_LOAD_ARCHIVE, 
                                   ArchiveError.EM_NOT_AUTH_LOAD_ARCHIVE);
      
   }
   
} // class
