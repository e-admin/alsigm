
package ieci.tecdoc.sbo.idoc.archive.base;

import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.exception.IeciTdException;

public final class ArchiveFldType
{
   
   // **************************************************************************
       
   public static final int SHORT_TEXT      = 1;
   public static final int LONG_TEXT       = 2;   
   public static final int SHORT_INTEGER   = 3;  
   public static final int LONG_INTEGER    = 4;  
   public static final int SHORT_DECIMAL   = 5;  
   public static final int LONG_DECIMAL    = 6;  
   public static final int DATE            = 7;  
   public static final int TIME            = 8;  
   public static final int DATE_TIME       = 9;  
   
   // **************************************************************************
 
   private ArchiveFldType()
   {
   }
   
   public static int getDbDataType(int fldType)
                     throws Exception
   {
      int dbType;
      
      switch(fldType)
      {
         case SHORT_TEXT:
            
            dbType = DbDataType.SHORT_TEXT;
            break;
            
         case LONG_TEXT:
         
            dbType = DbDataType.LONG_TEXT;
            break;
         
         case SHORT_INTEGER:
         
            dbType = DbDataType.SHORT_INTEGER;
            break;
            
         case LONG_INTEGER:
         
            dbType = DbDataType.LONG_INTEGER;
            break;
            
         case SHORT_DECIMAL:
         
            dbType = DbDataType.SHORT_DECIMAL;
            break;
            
         case LONG_DECIMAL:
         
            dbType = DbDataType.LONG_DECIMAL;
            break;
            
         case DATE:
         
            dbType = DbDataType.DATE_TIME;
            break;
            
         case TIME:
         
            dbType = DbDataType.DATE_TIME;
            break;
            
         case DATE_TIME:
         
            dbType = DbDataType.DATE_TIME;
            break;
            
         default:
         
             throw new IeciTdException(ArchiveBaseError.EC_INVALID_PARAM, 
                                       ArchiveBaseError.EM_INVALID_PARAM); 
             
            
      }
      
      return dbType;
      
   }
   
} // class
