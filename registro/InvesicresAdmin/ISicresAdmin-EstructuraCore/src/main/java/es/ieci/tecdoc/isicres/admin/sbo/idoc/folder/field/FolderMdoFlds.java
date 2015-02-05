
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.field;


import java.util.ArrayList;
import java.util.Date;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.datetime.DateTimeUtil;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.archive.base.*;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoExtFldsRow;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoExtFldsTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoMultFldRow;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoMultFldTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoRelFlds;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoRelFldsRow;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoRelFldsTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoUtil;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.base.*;


public class FolderMdoFlds
{
   
   private FolderMdoFlds()
   {      
   }
   
   //Debe llamarse dentro de una transacción
   public static void deleteFolderRelValues(String tblPrefix, int fdrId, String entidad)
                      throws Exception
   {
      
      String tblName = DaoUtil.getRelFldsTblName(tblPrefix); 
      
      DaoRelFldsTbl.deleteRow(tblName, fdrId, entidad);
   } 
   
   
   public static void deleteFolderExtValues(DbConnection dbConn, String tblPrefix, int fdrId)
                       throws Exception
   {      
     
      String        tblName = DaoUtil.getExtFldTblName(tblPrefix); 
      DaoExtFldsTbl tbl  = null;
      
      tbl = new DaoExtFldsTbl(tblName);
            
      tbl.deleteFolderRows(dbConn, fdrId);     
      
   }
   
   public static void deleteExtFldValue(DbConnection dbConn, String tblPrefix, int fdrId, int fldId)
                       throws Exception
   {      
     
      String        tblName = DaoUtil.getExtFldTblName(tblPrefix); 
      DaoExtFldsTbl tbl  = null;
      
      tbl = new DaoExtFldsTbl(tblName);
            
      tbl.deleteRow(dbConn, fdrId, fldId);     
      
   }  
   
   public static void deleteFolderMultValues(DbConnection dbConn, ArchiveTokenFlds multFldDefs, String tblPrefix,
                                             int fdrId)                                          
                      throws Exception
   {
      ArchiveTokenFld  multFldDef           = null;
      boolean          hasReadTextValues    = false;
      boolean          hasReadIntegerValues = false;
      boolean          hasReadDecimalValues = false;
      boolean          hasReadDateValues    = false;     
      DaoMultFldTbl    tbl                  = null;
      int              fldType;
      int              numMultFlds, i;  
      
      numMultFlds = multFldDefs.count();
      
      for(i = 0; i < numMultFlds; i++)
      {
         multFldDef = multFldDefs.get(i);
         fldType    = multFldDef.getType();         
         
         switch (fldType)
         {
            case ArchiveFldType.LONG_TEXT:
            case ArchiveFldType.SHORT_TEXT:
               
               if (!hasReadTextValues)
               { 
                  tbl = new DaoMultFldTbl(ArchiveUtil.getMultFldTblName(tblPrefix, fldType),
                                          ArchiveFldType.getDbDataType(fldType)); 
                  tbl.deleteFolderRows(dbConn, fdrId);                                           
                  hasReadTextValues = true;                  
               }               
               break;
               
            case ArchiveFldType.LONG_INTEGER:
            case ArchiveFldType.SHORT_INTEGER:
               
               if (!hasReadIntegerValues)
               {
                  tbl = new DaoMultFldTbl(ArchiveUtil.getMultFldTblName(tblPrefix, fldType),
                                          ArchiveFldType.getDbDataType(fldType));
                  tbl.deleteFolderRows(dbConn, fdrId); 
                  hasReadIntegerValues = true;
                  
               }               
               break;
               
            case ArchiveFldType.LONG_DECIMAL:
            case ArchiveFldType.SHORT_DECIMAL:
               
               if (!hasReadDecimalValues)
               {
                  tbl = new DaoMultFldTbl(ArchiveUtil.getMultFldTblName(tblPrefix, fldType),
                                          ArchiveFldType.getDbDataType(fldType));
                  tbl.deleteFolderRows(dbConn, fdrId);                                
                  hasReadDecimalValues = true;
                  
               }
               break;
            case ArchiveFldType.DATE_TIME:
            case ArchiveFldType.DATE:
            case ArchiveFldType.TIME:
               
               if (!hasReadDateValues)
               {
                  tbl = new DaoMultFldTbl(ArchiveUtil.getMultFldTblName(tblPrefix, fldType),
                                          ArchiveFldType.getDbDataType(fldType));
                  tbl.deleteFolderRows(dbConn, fdrId);                               
                  hasReadDateValues = true;
                  
               }
               break;
         }
           
      }      
      
   } 
   
   public static void deleteMultFldValue(DbConnection dbConn, ArchiveToken arch, int fdrId,
                                         FolderTokenMultFld multFld)
                      throws Exception
   {
      int                  i;      
      ArchiveTokenFlds     multFldDefs    = arch.getMultFlds();     
      ArchiveTokenFld      multFldDef     = null;
      DaoMultFldTbl        tbl            = null;
      int                  fldType;
      String               tblPrefix      = arch.getTblPrefix();
               
      multFldDef = multFldDefs.findById(multFld.getId());
      fldType    = multFldDef.getType();
         
      tbl = new DaoMultFldTbl(ArchiveUtil.getMultFldTblName(tblPrefix, fldType),
                              ArchiveFldType.getDbDataType(fldType));     
     
      tbl.deleteRows(dbConn, fdrId, multFld.getId());
      
   }     
   
   
   public static void deleteFolderValues(DbConnection dbConn, ArchiveTokenFlds fldDefs, String tblPrefix, int fdrId, String entidad)
                       throws Exception
   {
            
     deleteFolderRelValues(tblPrefix, fdrId, entidad);
     deleteFolderExtValues(dbConn, tblPrefix, fdrId);
     deleteFolderMultValues(dbConn, fldDefs.getMultFlds(), tblPrefix, fdrId);  
      
   }
   
   
   //Debe llamarse dentro de una transacción
   public static void insertRelFldsValues(ArchiveToken arch,
                                          FolderToken  fdr, String entidad)
                       throws Exception
   {
      int                 i;
      ArchiveTokenFlds    relFldDefs    = arch.getRelFlds();
      ArchiveTokenFld     relFldDef     = null;
      FolderTokenFlds     flds          = fdr.getFlds();
      DaoRelFldsTbl       relTbl        = null;
      DaoRelFldsRow       row           = null;
      Object              val           = null;
      String              tblName       = DaoUtil.getRelFldsTblName(arch.getTblPrefix());
      DaoRelFlds          daoFlds       = ArchiveUtil.getDaoRelFlds(relFldDefs);
      int                 numRels       = relFldDefs.count();
      
      relTbl = new DaoRelFldsTbl(tblName, ArchiveUtil.getRelFldColsDef(relFldDefs)); 

      row = new DaoRelFldsRow(daoFlds, relTbl.getAllColumns());
      
      row.setFdrId(fdr.getId());
      row.setTimeStamp(DateTimeUtil.getCurrentDateTime());
      
      for(i = 0; i < numRels; i++)
      {
         relFldDef = relFldDefs.get(i);
         val       = flds.getRelFldValue(relFldDef.getId());

         if ((relFldDef.getType() == ArchiveFldType.TIME) && (val != null))
         {         
            val = ArchiveUtil.convertTimeToDateTime((Date)val);
         }
                  
         row.setFldData(relFldDef.getId(), val);
      }
      
      //    Se inserta la información de los campos relacionales
      relTbl.insertRow(row, entidad);       
   }
   
   public static void insertExtFldValue(DbConnection dbConn, ArchiveToken arch, int fdrId, 
                                        FolderTokenExtFld extFld, int extId)
                       throws Exception
   {      
      String        val     = null;      
      DaoExtFldsRow row     = null;
      DaoExtFldsTbl tbl     = null;
      String        tblName = DaoUtil.getExtFldTblName(arch.getTblPrefix()); 
      
      tbl = new DaoExtFldsTbl(tblName);  
               
      val = extFld.getVal();
         
      if ( val != null)      
      {
         row = fillExtFldRow(extId, fdrId, extFld.getId(), val);
            
         tbl.insertRow(dbConn, row);
                 
      }
      
   }
   
   public static void insertMultFldValues(DbConnection dbConn, ArchiveToken arch, FolderToken  fdr)
                      throws Exception
   {
      int                  i;
      FolderTokenMultFlds  multFlds        = fdr.getFlds().getMultFlds();      
      FolderTokenMultFld   multFld         = null;       
      int                  numMultFlds;
      
      numMultFlds = multFlds.count();
      
      for(i = 0; i < numMultFlds; i++)
      {
         multFld = multFlds.get(i);      
         
         if ( multFld.isModified())       
         {
            insertMultFldValue(dbConn, arch, fdr.getId(), multFld);            
         }
                 
      }       
   } 
   
   public static void insertMultFldValue(DbConnection dbConn, ArchiveToken arch, int fdrId, FolderTokenMultFld multFld)
                      throws Exception
   {
      int                  i;      
      ArchiveTokenFlds     multFldDefs = arch.getMultFlds();     
      ArchiveTokenFld      multFldDef  = null;
      DaoMultFldRow        row         = null;
      DaoMultFldTbl        tbl         = null;
      int                  fldType;
      Object               value;
      int                  sortOrder;
      ArrayList            vals; 
      int                  numVals;
      String               tblName;
      
      vals    = multFld.getVals();
            
      multFldDef = multFldDefs.findById(multFld.getId());
      fldType    = multFldDef.getType();
      
      tblName = ArchiveUtil.getMultFldTblName(arch.getTblPrefix(), fldType);
         
      tbl = new DaoMultFldTbl(tblName, ArchiveFldType.getDbDataType(fldType));
      
      sortOrder = 1;
      numVals   = multFld.getNumVals();
      
      for(i = 0; i < numVals; i++)
      {  
         value = vals.get(i);
         
         if (value != null)
         {
            
            row   = fillMultFldRow(fdrId, multFld.getId(), fldType,
                                   value, sortOrder);
            
            tbl.insertRow(dbConn, row);
            
            sortOrder = sortOrder + 1;
            
         }
            
      }
      
   }
   
   private static DaoExtFldsRow fillExtFldRow(int id, int fdrId, int fldId,
                                              String val)
                                      throws Exception
   {      
      DaoExtFldsRow rec = new DaoExtFldsRow();
      
      rec.m_id       = id;  
      rec.m_fdrId    = fdrId;  
      rec.m_fldId    = fldId;
      rec.m_text     = val; 
      rec.m_ts       = DateTimeUtil.getCurrentDateTime();
      
      return rec;      
   }
   
   private static DaoMultFldRow fillMultFldRow(int fdrId, int fldId, int fldType,
                                                Object value, int sortOrder)
                                 throws Exception
   {      
      DaoMultFldRow row = new DaoMultFldRow(ArchiveFldType.getDbDataType(fldType));
      
      if (fldType == ArchiveFldType.TIME)
      {         
         value = ArchiveUtil.convertTimeToDateTime((Date)value);
      }
      
      row.setFdrId(fdrId);  
      row.setFldId(fldId);
      row.setVal(value); 
      row.setSortOrder(sortOrder);
      
      return row;      
   }
   
   //Debe llamarse dentro de una transacción
   public static void updateRelFldsValues(ArchiveToken arch,
                                          FolderToken  fdr, String entidad)
                       throws Exception
   {
      int                 i;
      ArchiveTokenFlds    relFldDefs    = arch.getRelFlds();
      ArchiveTokenFlds    updRelFldsDef = null;
      ArchiveTokenFld     updRelFldDef  = null;
      FolderTokenFlds     flds          = fdr.getFlds();
      DaoRelFldsRow       row           = null; 
      Object              val           = null;
      String              tblName       = DaoUtil.getRelFldsTblName(arch.getTblPrefix());
      DaoRelFlds          daoFlds       = null;
      int                 numUpdFlds;
      
      if (flds.existsAnyRelFldValueUpdate())
      {
      
         updRelFldsDef =  getUpdateRelFldsDef(relFldDefs, fdr);
         daoFlds       =  ArchiveUtil.getDaoRelFlds(updRelFldsDef);
      
         DaoRelFldsTbl tbl = new DaoRelFldsTbl(tblName,
                                               ArchiveUtil.getRelFldColsDef(relFldDefs));
   
         row = new DaoRelFldsRow(daoFlds, tbl.getColsDef(daoFlds, false, true));
         
         row.setTimeStamp(DateTimeUtil.getCurrentDateTime());
         
         numUpdFlds = updRelFldsDef.count();
         
         for(i = 0; i < numUpdFlds; i++)
         {
            updRelFldDef  = updRelFldsDef.get(i);
            val           = flds.getRelFldValue(updRelFldDef.getId());
            
            if (updRelFldDef.getType() == ArchiveFldType.TIME && 
                val != null && !val.equals(""))
            {         
               val = ArchiveUtil.convertTimeToDateTime((Date)val);
            }
            
            row.setFldData(updRelFldDef.getId(), val);
         }
         
         tbl.updateRow(fdr.getId(), row, entidad);
          
      } 
      
   }
   
   private static ArchiveTokenFlds getUpdateRelFldsDef(ArchiveTokenFlds relFldsDef,
                                                       FolderToken  fdr)
                                   throws Exception
   {
      int                 i;
      ArchiveTokenFlds    updRelFldsDef = new ArchiveTokenFlds();
      FolderTokenRelFlds  updRelFlds    = fdr.getFlds().getUpdateRelFlds();
      FolderTokenRelFld   updRelFld     = null;
      int                 fldId;
      ArchiveTokenFld     updRelFldDef  = null;
     
      
      for(i = 0; i < updRelFlds.count(); i++)
      {
         updRelFld   = updRelFlds.get(i);
         
         fldId = updRelFld.getId();
         
         updRelFldDef = relFldsDef.findById(fldId);
         
         updRelFldsDef.add(updRelFldDef);
      }
      
      return updRelFldsDef;
      
   } 
   
   public static void updateExtFldValue(DbConnection dbConn, ArchiveToken arch, int fdrId, 
                                        FolderTokenExtFld extFld)
                       throws Exception
   {       
      String                  val     = null;      
      FolderDaoExtFldsDataUpd row     = null;
      DaoExtFldsTbl           tbl     = null;
      String                  tblName = DaoUtil.getExtFldTblName(arch.getTblPrefix()); 
      
      tbl = new DaoExtFldsTbl(tblName);  
               
      val = extFld.getVal();
         
      if ( val != null)      
      {
         row = fillExtFldUpdRow(val);
            
         tbl.updateRow(dbConn, fdrId, extFld.getId(), row); 
                 
      }    
      
   }
   
   private static FolderDaoExtFldsDataUpd fillExtFldUpdRow(String val)
                                          throws Exception
   {      
      FolderDaoExtFldsDataUpd rec = new FolderDaoExtFldsDataUpd();
      
      rec.m_text     = val; 
      rec.m_ts       = DateTimeUtil.getCurrentDateTime();
      
      return rec;      
   }       
   
} // class
