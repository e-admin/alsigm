package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;


public class DaoSSNextIdTbl
{
// **************************************************************************
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN = "IDOCSSNEXTID";
        
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = {CD_ID};  
   
// **************************************************************************
   
   public static void initTblContentsSSNextId(DbConnection dbConn) throws Exception
   {
      String stmtText;

      stmtText = "INSERT INTO " + TN + " VALUES( 1 )" ;            
      DbUtil.executeStatement(dbConn, stmtText);
      
   }
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.createTable(dbConn, TN,ACD);
   } 
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.dropTable(dbConn, TN);
   }
   
}
