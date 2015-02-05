
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.divider;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLiLi;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLiLiArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.datetime.DateTimeUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DbDataType;
import es.ieci.tecdoc.isicres.admin.sbo.acs.base.AcsAccessType;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoClfRow;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoClfTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoUtil;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderDividerType;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenDivider;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.FolderTokenDividers;
import es.ieci.tecdoc.isicres.admin.sbo.util.types.SboType;

public class FolderMdoDivider
{
   
   private FolderMdoDivider()
   {      
   }
  
   public static void deleteFolderDividers(DbConnection dbConn, String tblPrefix, int fdrId)
                      throws Exception
   {  
       
      String     tblName = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl  tbl     = new DaoClfTbl(tblName);
      
      tbl.deleteFolderRows(dbConn, fdrId);
      
   }
   
   public static void deleteDivider(DbConnection dbConn, String tblPrefix, int fdrId, int divId)
                      throws Exception
   {  
      String     tblName = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl  tbl     = new DaoClfTbl(tblName);
      
      tbl.deleteRow(dbConn, divId, fdrId);
   } 
   
   public static void updateDivider(DbConnection dbConn, int userId, String tblPrefix, int fdrId,
                                    FolderTokenDivider div)
                      throws Exception
   {  
     
      String              tblName = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl           tbl     = new DaoClfTbl(tblName);
      FolderDaoClfRowUpd  row;
      
      row = fillClfRowUpd(userId, div.getName());
      
      tbl.updateRow(dbConn, div.getId(), fdrId, row);
   } 
   
   private static FolderDaoClfRowUpd  fillClfRowUpd(int userId, String name)
                                      throws Exception
   {      
            
      FolderDaoClfRowUpd rec = new FolderDaoClfRowUpd();
      
      rec.m_name     = name;
      rec.m_updUsrId = userId;
      rec.m_updTs    = DateTimeUtil.getCurrentDateTime(); 
      
      return rec;
      
   }  
   
   public static IeciTdLiLiArrayList insertDividers(DbConnection dbConn, int userId, String tblPrefix, int fdrId,
                                                    FolderTokenDividers newDivs,
                                                    int firstDividerId)
                                     throws Exception
   {  
      IeciTdLiLiArrayList    divIds     = new IeciTdLiLiArrayList();
      FolderTokenDivider     div        = null;
      int                    nextId     = firstDividerId;
      int                    i, id;
      int                    parentId, idx;
      DaoClfRow              row        = null;
      String                 tblName    = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl              tbl        = new DaoClfTbl(tblName);
      int                    numNewDivs = newDivs.count();
      
      for(i = 0; i < numNewDivs; i++)
      {
         div = newDivs.get(i);
         
         id = div.getParentId();
         
         //Mira a ver si el padre también era un nodo nuevo
         
         idx = newDivs.findIndexById(id);
         
         if (idx != -1)
         {
            parentId = getDivParentId(divIds, id);
         }
         else
            parentId = id;
                  
         row = fillClfRow(userId, fdrId, nextId, 
                          parentId, div.getName());
         
         tbl.insertRow(dbConn, row);
         
         divIds.add(div.getId(), nextId);  
            
         nextId = nextId + 1;
                          
      }
      
      return divIds;
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
   
   private static DaoClfRow fillClfRow(int userId, int fdrId, int divId, 
                                       int divParentId, String name)
                            throws Exception
   {   
      int       type;   
      DaoClfRow rec = new DaoClfRow();
      
      rec.m_id       = divId;
      rec.m_fdrId    = fdrId;
      rec.m_name     = name;
      rec.m_type     = FolderDividerType.USER_NORMAL;  
      rec.m_parentId = divParentId;  
      rec.m_info     = 0;
      rec.m_stat     = 0;
      rec.m_refCount = 0;
      rec.m_rems     = DbDataType.NULL_SHORT_TEXT;
      rec.m_acsType  = AcsAccessType.ACS_PUBLIC;
      rec.m_acsId    = SboType.NULL_ID;   
      rec.m_crtUsrId = userId;
      rec.m_crtTs    = DateTimeUtil.getCurrentDateTime();
      rec.m_updUsrId = SboType.NULL_ID;    
      rec.m_updTs    = DbDataType.NULL_DATE_TIME;
      
      return rec;      
   }  
   
} // class
