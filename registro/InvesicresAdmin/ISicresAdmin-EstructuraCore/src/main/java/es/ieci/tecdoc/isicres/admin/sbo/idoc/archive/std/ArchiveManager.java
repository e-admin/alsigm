
package es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessToken;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.acs.AcsMdoArchive;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.base.ArchiveToken;

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
