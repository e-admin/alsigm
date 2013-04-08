
package ieci.tecdoc.sbo.idoc.archive.std;

import ieci.tecdoc.sbo.idoc.archive.base.ArchiveToken;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sbo.idoc.acs.AcsMdoArchive;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public class ArchiveManager
{
   
   private ArchiveManager()
   {      
   }
   
   public static boolean canLoadArchive(DbConnection dbConn, AcsAccessToken accToken,
                                        int archId) 
                         throws Exception
   {
      
      boolean can = false;       
     
      can = AcsMdoArchive.hasAuthView(dbConn, accToken, archId);
      
      return can;
      
   }
   
   public static ArchiveToken loadArchive(DbConnection dbConn, AcsAccessToken accToken, int archId)
                              throws Exception
   {
      
      ArchiveToken  token; 
      
      checkCanLoadArchive(dbConn, accToken, archId);
      
      token = ArchiveMdoToken.createArchiveToken(dbConn, accToken, archId);      
      
      return token;    
      
   }
   
   private static void checkCanLoadArchive(DbConnection dbConn, AcsAccessToken accToken,
                                           int archId)
                       throws Exception
   {
      
      if (accToken == null) return;
            
      if (!canLoadArchive(dbConn, accToken, archId))
         throw new IeciTdException(ArchiveError.EC_NOT_AUTH_LOAD_ARCHIVE, 
                                   ArchiveError.EM_NOT_AUTH_LOAD_ARCHIVE);
      
   }
   
} // class
