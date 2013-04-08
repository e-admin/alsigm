
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.divider;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLiLi;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLiLiArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.base.ArchiveToken;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.base.ArchiveTokenDivider;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.base.ArchiveTokenDividers;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoClfTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoUtil;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderBaseDefs;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderDividerType;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenDividers;
import es.ieci.tecdoc.isicres.admin.sbo.util.types.SboType;


public class FolderMdoDividerToken
{
   
   private FolderMdoDividerToken()
   {      
   }
   
   public static FolderTokenDividers createDividersToken(DbConnection dbConn, String tblPrefix, int fdrId)
                                     throws Exception
   {  
      DaoClfTbl             tbl  = null;
      FolderDaoClfTokenRows rs   = null;
      FolderDaoClfTokenRow  row  = null;
      String                tblName;
      int                   i, type; 
      boolean               isFixed;
      boolean               orderByName = true;
      FolderTokenDividers   dividers = new FolderTokenDividers();
      
      tblName = DaoUtil.getDividerTblName(tblPrefix);     
      
      tbl = new DaoClfTbl(tblName); 
      
      rs = new FolderDaoClfTokenRows();    
      
      tbl.selectFolderRows(dbConn, fdrId, orderByName, rs);      
      
      for(i = 0;i < rs.count(); i++)
      {
         row = rs.getRecord(i);
         
         type = row.getType();
         
         if (type == FolderDividerType.DEFAULT_FIXED)
            isFixed = true;
         else
            isFixed = false; 
                
         dividers.add(row.getId(), row.getName(), row.getParentId(),
                      isFixed);  
      }
      
      return dividers;
   }
   
   public static FolderTokenDividers createDividersToken(ArchiveToken arch)
                                     throws Exception
   {   
      int                   i, type; 
      boolean               isFixed;
      boolean               orderByName  = true;
      ArchiveTokenDividers  archDividers = arch.getDividers();
      ArchiveTokenDivider   archDivider  = null;
      FolderTokenDividers   dividers     = new FolderTokenDividers();
      int                   parentDivId, divId; 
      IeciTdLiLiArrayList   divIds     = new IeciTdLiLiArrayList();
      
      //Los clasificadores predeterminados están colocados de forma que viene el padre
      //antes que el hijo
      
      for(i = 0;i < archDividers.count(); i++)
      {
         archDivider = archDividers.get(i);
         
         type = archDivider.getType();
         
         if (type == FolderDividerType.DEFAULT_FIXED)
            isFixed = true;
         else
            isFixed = false;
           
         if (archDivider.getParentId() == FolderBaseDefs.CLF_ROOT_ID)
         {
            divId = dividers.addNewDivider(archDivider.getName(), FolderBaseDefs.CLF_ROOT_ID,
                                           isFixed);
         }
         else
         {
            parentDivId = getDivParentId(divIds, archDivider.getParentId());
            divId       = dividers.addNewDivider(archDivider.getName(), parentDivId,
                                                 isFixed);
         }
         
         divIds.add(archDivider.getId(), divId);  
        
      }
      
      return dividers;
   }
   
   private static int getDivParentId(IeciTdLiLiArrayList divIds, int id)
                      throws Exception
   {   
      int         parentId = SboType.NULL_ID; 
      int         i;
      IeciTdLiLi  node = null;
      
      for(i = 0; i < divIds.count(); i++)
      {
         node = divIds.get(i);
         
         if (node.m_val1 == id)
         {
            parentId = node.m_val2;
            break;
         }         
         
      }
      
      return parentId;      
   }
   
   
      
   
} // class
