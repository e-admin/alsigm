package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;

public class UasDaoLicencesTbl
{
// **************************************************************************
   
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */	
   private static final String TN ="IUSERLICENCES";

   private static final DbColumnDef CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_TEXT, (100*1024), false);
   
   
   private static final DbColumnDef[] ACD = {CD_INFO}; 
   
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.createTable(dbConn, TN,ACD);  
    
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {         
      DbTableFns.dropTable(dbConn, TN);
   }
   
   
}
