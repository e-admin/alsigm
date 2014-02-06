package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;


public final class DaoUtil
{
   
   private static final String REL_FLDS_TBL_SUFFIX         = "SF";  
   private static final String REL_FLDS_TBL_COL_FDRID_NAME = "FDRID"; 
   private static final String REL_FLDS_TBL_COL_TS_NAME    = "TIMESTAMP";
   private static final String FDR_HDR_TBL_SUFFIX          = "FDRH"; 
   private static final String EXT_FLD_TBL_SUFFIX          = "XF";
   private static final String CLF_TBL_SUFFIX              = "CLFH";
   private static final String DOC_TBL_SUFFIX              = "DOCH";
   private static final String PAGE_TBL_SUFFIX             = "PAGEH";
   private static final String XNID_TBL_SUFFIX             = "XNID";
   
   private DaoUtil()
   {
   }
   
   public static String getRelFldsTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + REL_FLDS_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static String getRelFldsTblFdrIdColName()                        
   {  
      return REL_FLDS_TBL_COL_FDRID_NAME; 
   }
   
   
   public static String getRelFldsTblTsColName()                        
   {  
      return REL_FLDS_TBL_COL_TS_NAME;
   }
   
   public static String getFdrHdrTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + FDR_HDR_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static String getExtFldTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + EXT_FLD_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static String getMultFldTblName(String tblPrefix, int fldDbType)
                        throws Exception
   {  
      
      String tblName;
      String tblSuffix = null;
      
      switch(fldDbType)
      {
         case DbDataType.SHORT_TEXT:
            
            tblSuffix = "MFS";
            break;
         
         case DbDataType.SHORT_INTEGER:            
         case DbDataType.LONG_INTEGER:
         
            tblSuffix = "MFI";
            break;
            
         case DbDataType.SHORT_DECIMAL:
         case DbDataType.LONG_DECIMAL:
         
            tblSuffix = "MFD";
            break;         
         
         case DbDataType.DATE_TIME:
         
            tblSuffix = "MFDT";
            break;
            
         default:
            
            throw new IeciTdException(DaoError.EC_INVALID_PARAM, 
                                      DaoError.EM_INVALID_PARAM); 

      }
      
      tblName = tblPrefix + tblSuffix;       
      
      return tblName;

   }
   
   public static String getDividerTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + CLF_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static String getDocumentTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + DOC_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static String getPageTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + PAGE_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static String getXNIdTblName(String tblPrefix)                        
   {  
      
      String tblName;
      
      tblName = tblPrefix + XNID_TBL_SUFFIX;       
      
      return tblName;

   }
   
   public static IeciTdShortTextArrayList getColumnNames(DbColumnDef[] colDefs)
   {
      IeciTdShortTextArrayList names = new IeciTdShortTextArrayList();
      int                      count, i;
          
      count = colDefs.length;

      for (i = 1; i < count; i++)
         names.add(colDefs[i].getName());

      return names;
   }
   
   
} // class
