
package ieci.tecdoc.sbo.idoc.folder.fdrlink;

import ieci.tecdoc.sbo.idoc.dao.DaoFdrLinkTbl;
import ieci.tecdoc.sgm.base.dbex.DbConnection;


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
