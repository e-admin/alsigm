
package ieci.tecdoc.sbo.idoc.folder.divider;

import ieci.tecdoc.sbo.idoc.dao.DaoUtil;
import ieci.tecdoc.sbo.idoc.dao.DaoClfTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoClfRow;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.collections.IeciTdLiLiArrayList;
import ieci.tecdoc.core.collections.IeciTdLiLi;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.idoc.folder.base.FolderDividerType;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;

public class FolderMdoDivider
{
   
   private FolderMdoDivider()
   {      
   }
  
   public static void deleteFolderDividers(String tblPrefix, int fdrId)
                      throws Exception
   {  
       
      String     tblName = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl  tbl     = new DaoClfTbl(tblName);
      
      tbl.deleteFolderRows(fdrId);
      
   }
   
   public static void deleteDivider(String tblPrefix, int fdrId, int divId)
                      throws Exception
   {  
      String     tblName = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl  tbl     = new DaoClfTbl(tblName);
      
      tbl.deleteRow(divId, fdrId);
   } 
   
   public static void updateDivider(int userId, String tblPrefix, int fdrId,
                                    FolderTokenDivider div)
                      throws Exception
   {  
     
      String              tblName = DaoUtil.getDividerTblName(tblPrefix);      
      DaoClfTbl           tbl     = new DaoClfTbl(tblName);
      FolderDaoClfRowUpd  row;
      
      row = fillClfRowUpd(userId, div.getName());
      
      tbl.updateRow(div.getId(), fdrId, row);
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
   
   public static IeciTdLiLiArrayList insertDividers(int userId, String tblPrefix, int fdrId,
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
         
         tbl.insertRow(row);
         
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
