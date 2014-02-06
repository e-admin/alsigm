
package ieci.tecdoc.sbo.idoc.folder.fdrlink;

import ieci.tecdoc.sbo.idoc.dao.DaoFdrLinkTbl;

public class FolderMdoFdrLink
{
   
   private FolderMdoFdrLink()
   {      
   }
  
   public static void deleteFolderLinks(int archId, int fdrId)
                      throws Exception
   {  
            
      DaoFdrLinkTbl.deleteCntrRows(archId, fdrId);
      
   }    
   
} // class
