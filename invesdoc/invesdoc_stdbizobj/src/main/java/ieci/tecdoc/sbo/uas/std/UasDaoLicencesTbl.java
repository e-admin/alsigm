package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbTableFns;

public class UasDaoLicencesTbl
{
// **************************************************************************
   
   private static final String TN = "IUSERLICENCES";

   private static final DbColumnDef CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_TEXT, (100*1024), false);
   
   
   private static final DbColumnDef[] ACD = {CD_INFO}; 
   
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN,ACD);  
    
   }
   
   public static void dropTable() throws Exception
   {         
      DbTableFns.dropTable(TN);
   }
   
   
}
