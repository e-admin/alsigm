package ieci.tecdoc.sbo.config;


import ieci.tecdoc.core.db.DbConnectionConfig;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUpdateFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;

public final class CfgDaoDbInfoTbl
{

   // **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String        TN      = DbConnectionConfig.getSchema() +  "IDOCDBINFO";

   private static final DbColumnDef   CD_ID   = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef   CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   private static final DbColumnDef   CD_GRALDBNAME = new DbColumnDef
   ("GRALDBNAME", DbDataType.SHORT_TEXT, 32, true);
   
   private static final DbColumnDef   CD_GRALDBUSER = new DbColumnDef
   ("GRALDBUSER", DbDataType.SHORT_TEXT, 32, true); 
                                                        
   private static final DbColumnDef   CD_GRALDBPWD  = new DbColumnDef
   ("GRALDBPASSWORD", DbDataType.SHORT_TEXT, 34, true);    

   private static final DbColumnDef   CD_MISC       = new DbColumnDef
   ("MISC", DbDataType.LONG_TEXT, 10240, false);

   private static final DbColumnDef[] ACD           = {CD_ID, CD_NAME, CD_GRALDBNAME,
                                                       CD_GRALDBUSER, CD_GRALDBPWD, CD_MISC};

   private static final String        ACN           = DbUtil.getColumnNames(ACD); 
   

   // **************************************************************************

   public CfgDaoDbInfoTbl()
   {
   }

   // **************************************************************************

   
   public String selectMisc(DbConnection dbConn) throws Exception
   { 
   	
      return DbSelectFns.selectLongText(dbConn, TN, CD_MISC.getName(), null);

   }
   
   public static String selectName(DbConnection dbConn) throws Exception
   { 
      
      return DbSelectFns.selectShortText(dbConn, TN, CD_NAME.getName(), null);

   }
   
   public static void updateMisc(DbConnection dbConn, String misc) throws Exception
   {
      
      DbUpdateFns.updateShortText(dbConn, TN, CD_MISC.getName(), misc, null);  
   }
   
   public static void initTblContentsDbInfo(DbConnection dbConn) throws Exception
   {
      
      String        misc,stmtText;
      CfgDbInfoMisc dbInfo;
      
      dbInfo = new CfgDbInfoMisc();
      
      misc = dbInfo.initInfoMisc();
      
      stmtText = "INSERT INTO " + TN + " (ID, NAME, MISC) VALUES( 1,'local','" + misc + "' )";            
      DbUtil.executeStatement(dbConn, stmtText);
      
   }
   
   
   
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.createTable(dbConn, TN,ACD);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.dropTable(dbConn, TN);
   }

} // class
