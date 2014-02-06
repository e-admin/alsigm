
package ieci.tecdoc.sbo.idoc.folder.field;

import ieci.tecdoc.sbo.idoc.dao.*;
import ieci.tecdoc.sbo.idoc.folder.base.*;
import ieci.tecdoc.sbo.idoc.archive.base.*;
import java.util.ArrayList;

public class FolderMdoFldsToken
{
   
   private FolderMdoFldsToken()
   {      
   }
   
   public static FolderTokenRelFlds createRelFldsToken(ArchiveToken arch,
                                                       int fdrId)
                                    throws Exception
   {
      DaoRelFldsTbl         relTbl  = null;
      DaoRelFldsRow         rec     = null;
      int                   i, numRelFlds;
      ArchiveTokenFlds      relFlds = arch.getRelFlds();
      ArchiveTokenFld       fld = null;
      Object                val;
      String                tblName;
      FolderTokenRelFlds    token = new FolderTokenRelFlds();
      
      if (relFlds.count() == 0) return token;     
      
      tblName = DaoUtil.getRelFldsTblName(arch.getTblPrefix());
      
      relTbl = new DaoRelFldsTbl(tblName, 
                                 ArchiveUtil.getRelFldColsDef(relFlds));     
      
      rec = relTbl.selectFolderValues(ArchiveUtil.getDaoRelFlds(relFlds),
                                      fdrId);
      
      numRelFlds = relFlds.count();
      
      for(i = 0;i < numRelFlds; i++)
      {
         fld = relFlds.get(i);
         val = rec.getFldData(fld.getId());
         token.add(fld.getId(), fld.getName(), 
                   val);
      }
      
      return token;
   }
   
   public static FolderTokenMultFlds createMultFldsToken(ArchiveToken arch,
                                                         int fdrId)
                                     throws Exception
   {
      FolderTokenMultFlds token = new FolderTokenMultFlds();
      boolean             hasReadTextValues    = false;
      boolean             hasReadIntegerValues = false;
      boolean             hasReadDecimalValues = false;
      boolean             hasReadDateValues    = false;      
      int                 i;
      ArchiveTokenFlds    multFlds = arch.getMultFlds();
      ArchiveTokenFld     multFld  = null;
      int                 fldType, fldId;
      String              fldName;
      ArrayList           vals = null;
      
      for (i = 0; i < multFlds.count(); i++)
      {
         multFld = multFlds.get(i);
         fldType = multFld.getType();
         fldId   = multFld.getId();
         fldName = multFld.getName();
         
         switch (fldType)
         {
            case ArchiveFldType.LONG_TEXT:
            case ArchiveFldType.SHORT_TEXT:
               
               if (!hasReadTextValues)
               {
                  addMultFldsToken(arch, fdrId, fldType,
                                   token);                                 
                  hasReadTextValues = true;                  
               }               
               break;
               
            case ArchiveFldType.LONG_INTEGER:
            case ArchiveFldType.SHORT_INTEGER:
               
               if (!hasReadIntegerValues)
               {
                  addMultFldsToken(arch, fdrId, fldType,
                              token);                                 
                  hasReadIntegerValues = true;
                  
               }               
               break;
               
            case ArchiveFldType.LONG_DECIMAL:
            case ArchiveFldType.SHORT_DECIMAL:
               
               if (!hasReadDecimalValues)
               {
                  addMultFldsToken(arch, fdrId, fldType,
                              token);                                 
                  hasReadDecimalValues = true;
                  
               }
               break;
            case ArchiveFldType.DATE_TIME:
            case ArchiveFldType.DATE:
            case ArchiveFldType.TIME:
               
               if (!hasReadDateValues)
               {
                  addMultFldsToken(arch, fdrId, fldType,
                              token);                                 
                  hasReadDateValues = true;
                  
               }
               break;
         }  
         
         //Puede que este campo multivalor no tenga ningún valor en la tabla. Lo
         //añadimos sin valores
         if( token.findIndexById(fldId) == -1)
         {
            vals = new ArrayList();
            token.add(fldId, fldName, vals); 
         }
         
     
      }  
      
      return token;      
   }
   
   private static void addMultFldsToken(ArchiveToken arch, int fdrId,
                                        int fldType, FolderTokenMultFlds token)
                       throws Exception
   {
      DaoMultFldTbl    multTbl  = null;
      DaoMultFldRows   rs       = null;
      int              i;
      DaoMultFldRow    row;
      int              fldId = -1;
      ArrayList        vals  = new ArrayList();
      String           fldName;
      ArchiveTokenFld  fld = null;
      String           tblName;
      int              fldDbType;
      
      fldDbType = ArchiveFldType.getDbDataType(fldType);
      
      tblName = DaoUtil.getMultFldTblName(arch.getTblPrefix(), fldDbType);      
            
      multTbl = new DaoMultFldTbl(tblName, fldDbType);
      
      //Viene ordenado por campo y order
      rs = multTbl.selectAllColumnRows(fdrId);
      
      for(i = 0; i < rs.count(); i++)
      {
         row = rs.getRecord(i);
         
         if (fldId != row.getFldId())
         {
            if (fldId != -1)
            {
               fld = arch.getFlds().findById(fldId);
               fldName = fld.getName();
               
               token.add(fldId, fldName, vals);  
            }
            
            fldId = row.getFldId();
            vals  = new ArrayList();            
         }
         
         vals.add(row.getVal());         
      }
      
      if (vals.size() > 0)
      {
         fld = arch.getFlds().findById(fldId);
         fldName = fld.getName();
         
         token.add(fldId, fldName, vals);
      }
         
   }
   
   public static FolderTokenExtFlds createExtFldsToken(ArchiveToken arch,
                                                       int fdrId)
                              throws Exception
   {
      DaoExtFldsTbl              extTbl  = null;
      FolderDaoExtFldsTokenRows  rs      = null;
      FolderDaoExtFldsTokenRow   row     = null;
      int                        i, fldId;
      String                     tblName;
      ArchiveTokenFlds           extFlds = arch.getExtFlds();
      ArchiveTokenFld            fld = null;
      FolderTokenExtFlds         token = new FolderTokenExtFlds();
      
      if (extFlds.count() == 0) return token;      
      
      tblName = DaoUtil.getExtFldTblName(arch.getTblPrefix());
      
      extTbl = new DaoExtFldsTbl(tblName); 
      
      rs = new FolderDaoExtFldsTokenRows();
      
      extTbl.selectRows(fdrId, rs);      
      
      for(i = 0;i < rs.count(); i++)
      {
         row = rs.getRecord(i);
         
         fldId = row.getFldId(); 
         fld   = extFlds.findById(fldId);
        
         token.add(fldId, fld.getName(), row.getText());  
      }
      
      //Puede que algún campo extendido no tenga ningún valor en la tabla. Lo
      //añadimos sin valores
      for(i = 0;i < extFlds.count(); i++)
      {        
         fld   = extFlds.get(i);
         fldId = fld.getId();
         
         if( token.findIndexById(fldId) == -1)
         {
            token.add(fldId, fld.getName(), null); 
         }
      }
      
      return token;
   }
   
   //Para carpetas nuevas
   
   public static FolderTokenRelFlds createRelFldsToken(ArchiveToken arch)
                                    throws Exception
   {
      int                   i, numRelFlds;
      ArchiveTokenFlds      relFlds = arch.getRelFlds();
      ArchiveTokenFld       fld = null;
      FolderTokenRelFlds    token = new FolderTokenRelFlds();
      
      if (relFlds.count() == 0) return token;  
      
      numRelFlds = relFlds.count();
      
      for(i = 0;i < numRelFlds; i++)
      {
         fld = relFlds.get(i);
         token.add(fld.getId(), fld.getName(), null);
      }
      
      return token;
   }
   
   public static FolderTokenMultFlds createMultFldsToken(ArchiveToken arch)
                                     throws Exception
   {
      FolderTokenMultFlds    token = new FolderTokenMultFlds();     
      int                    i;
      ArchiveTokenFlds       multFlds = arch.getMultFlds();
      ArchiveTokenFld        multFld  = null;
      int                    fldId;
      String                 fldName;
      ArrayList              vals = null;
      
      for (i = 0; i < multFlds.count(); i++)
      {
         multFld = multFlds.get(i);
         fldId   = multFld.getId();
         fldName = multFld.getName();
         
         vals = new ArrayList();
         token.add(fldId, fldName, vals);      
      }  
      
      return token;      
   }
   
   public static FolderTokenExtFlds createExtFldsToken(ArchiveToken arch)
                                    throws Exception
   {
      int                   i, fldId;
      ArchiveTokenFlds      extFlds = arch.getExtFlds();
      ArchiveTokenFld       fld = null;
      FolderTokenExtFlds    token = new FolderTokenExtFlds();
      
      if (extFlds.count() == 0) return token; 
      
      for(i = 0;i < extFlds.count(); i++)
      {        
         fld   = extFlds.get(i);
         fldId = fld.getId();
         
         token.add(fldId, fld.getName(), null);          
      }
      
      return token;
   }
   
   
   
   
   
} // class
