
package ieci.tecdoc.sbo.idoc.folder.document;

import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocuments;
import ieci.tecdoc.sbo.idoc.folder.base.FolderBaseError;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.idoc.dao.DaoPageTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoDocTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoUtil;

public class FolderMdoDocumentToken
{
   
   private FolderMdoDocumentToken()
   {      
   }
   
   public static FolderTokenDocuments createDocumentsToken(String tblPrefix, int archId,
                                                           int fdrId)
                                      throws Exception
   {  
      DaoPageTbl              pageTbl   = null;
      FolderDaoPageTokenRows  pageRows  = null;      
      FolderDaoPageTokenRow   row       = null;
      DaoDocTbl               docTbl    = null;
      FolderDaoDocTokenRows   fdrDocs   = null;
      String                  docTblName, pageTblName;
      int                     i, j, parentId;
      String                  fileExt, loc; 
      FolderTokenDocuments    documents = new FolderTokenDocuments(); 
      
      docTblName  = DaoUtil.getDocumentTblName(tblPrefix);
      pageTblName = DaoUtil.getPageTblName(tblPrefix);      
      
      pageTbl  = new DaoPageTbl(pageTblName);      
      pageRows = new FolderDaoPageTokenRows();
          
      pageTbl.selectFolderRows(fdrId, pageRows); 
      
      if (pageRows.count() == 0) return documents; 
      
      docTbl  = new DaoDocTbl(docTblName);
      fdrDocs = new FolderDaoDocTokenRows();    
      docTbl.selectFolderRows(archId, fdrId, fdrDocs);     
      
      for(i = 0;i < pageRows.count(); i++)
      {
         row = pageRows.getRecord(i);
         
         loc = row.getLoc();
         fileExt = getFileExt(loc);
         
         parentId = getDocumentParentId(fdrDocs, row.getDocId());         
                         
         documents.add(row.getId(), row.getName(), parentId, row.getDocId(),
                       row.getFileId(), fileExt, row.getSortOrder(), row.getAnnId());  
      }
      
      return documents;
   }
   
   private static int getDocumentParentId(FolderDaoDocTokenRows fdrDocs, int docId)
                      throws Exception
   {  
      int     i, clfId = 0; 
      boolean find = false;      
      
      for(i = 0;i < fdrDocs.count(); i++)
      {
         if (fdrDocs.getRecordId(i) == docId)
         {
            find = true;
            clfId = fdrDocs.getRecordClfId(i);
            break;
         }         
      }
      
      if (!find)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 
      }
      
      return clfId;
   }
   
   private static String getFileExt(String loc)
                         throws Exception
   {  
      String ext;
      int    idx;
      
      idx = loc.indexOf(".");
      
      if (idx != -1)
         ext = loc.substring(idx+1);
      else
         ext = loc; 
      
      return ext;
   }           
      
   
   
} // class
