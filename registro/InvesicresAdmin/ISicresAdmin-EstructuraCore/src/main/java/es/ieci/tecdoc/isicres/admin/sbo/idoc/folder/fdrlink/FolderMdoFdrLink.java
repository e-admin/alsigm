
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.fdrlink;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFdrLinkTbl;


public class FolderMdoFdrLink
{
   
   private FolderMdoFdrLink()
   {      
   }
  
   public static void deleteFolderLinks(DbConnection dbConn, int archId, int fdrId)
                      throws Exception
   {  
            
      DaoFdrLinkTbl.deleteCntrRows(dbConn, archId, fdrId);
      
   }    
   
} // class
