package ieci.tecdoc.sbo.config;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUpdateFns;
import ieci.tecdoc.core.db.DbUtil;

public final class CfgDaoDbInfoTbl
{

   // **************************************************************************

   private static final String        TN      = "IDOCDBINFO";

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

   private CfgDaoDbInfoTbl()
   {
   }

   // **************************************************************************

   public static String selectMisc() throws Exception
   { 
   	
      return DbSelectFns.selectLongText(TN, CD_MISC.getName(), null);

   }
   
   public static String selectName() throws Exception
   { 
      
      return DbSelectFns.selectShortText(TN, CD_NAME.getName(), null);

   }
   
   public static void updateMisc(String misc) throws Exception
   {
      
      DbUpdateFns.updateShortText(TN, CD_MISC.getName(), misc, null);  
   }
   
   public static void initTblContentsDbInfo() throws Exception
   {
      
      String        misc,stmtText;
      CfgDbInfoMisc dbInfo;
      
      dbInfo = new CfgDbInfoMisc();
      
      misc = dbInfo.initInfoMisc();
      
      stmtText = "INSERT INTO " + TN + " (ID, NAME, MISC) VALUES( 1,'local','" + misc + "' )";            
      DbUtil.executeStatement(stmtText);
      
   }
   
   
   
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN,ACD);
   }
   
   public static void dropTable() throws Exception
   {
      DbTableFns.dropTable(TN);
   }

} // class
