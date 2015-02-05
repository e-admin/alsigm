
package ieci.tecdoc.sbo.idoc.folder.fdrlink;

import ieci.tecdoc.sbo.idoc.dao.DaoArchHdrTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoFdrLinkTbl;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLinks;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public class FolderMdoFdrLinkToken
{
   
   private FolderMdoFdrLinkToken()
   {      
   }
   
   public static FolderTokenFdrLinks createFdrLinksToken(DbConnection dbConn, int archId, int fdrId)
                                     throws Exception
   {  
      FolderDaoFdrLinkTokenRows rs   = null;
      FolderDaoFdrLinkTokenRow  row  = null;
      int                       i; 
      String                    srvrArchName;
      int                       srvrArchId;
      FolderTokenFdrLinks       fdrLinks = new FolderTokenFdrLinks();
      
      rs = new FolderDaoFdrLinkTokenRows();       
      
      DaoFdrLinkTbl.selectRowsByCntr(dbConn, archId, fdrId, rs);      
      
      for(i = 0;i < rs.count(); i++)
      {
         row = rs.getRecord(i); 
         
         srvrArchId   = row.getSrvrArchId();
         srvrArchName = DaoArchHdrTbl.selectName(dbConn, srvrArchId);        
                
         fdrLinks.add(row.getId(), row.getName(), row.getCntrClfId(),
                      srvrArchId, srvrArchName,
                      row.getSrvrFdrId());  
      }
      
      return fdrLinks;
   }
   
} // class
