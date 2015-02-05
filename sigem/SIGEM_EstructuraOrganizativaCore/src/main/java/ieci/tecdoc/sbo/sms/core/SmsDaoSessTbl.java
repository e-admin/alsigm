
package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbInsertFns;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;


public final class SmsDaoSessTbl
{
   
   // **************************************************************************
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */   
   private static final String TN ="ITDSMSSESS";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("CID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_APPID = new DbColumnDef
   ("CAPPID", DbDataType.SHORT_TEXT, 64, false);

   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("CUSERID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CCRTTS", DbDataType.DATE_TIME, false); 
   
   private static final DbIndexDef ID_1 = new DbIndexDef
   (TN + "1", CD_ID.getName(), true);
   
   private static final DbIndexDef ID_2 = new DbIndexDef
   (TN + "2", CD_APPID.getName(), false);
   
   private static final DbIndexDef ID_3 = new DbIndexDef
   (TN + "3", CD_USERID.getName(), false);   
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_APPID, CD_USERID, CD_CRTTS};
   
   private static final DbIndexDef[] AID =
   {ID_1, ID_2, ID_3};
      
   private static final String ACN = DbUtil.getColumnNames(ACD);
   
      
   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "=" + id;
   }
   
   // **************************************************************************
   
   private SmsDaoSessTbl()
   {
   }
   
   // **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.createTable(dbConn, TN, ACD, AID);
   }

   public static void dropTable(DbConnection dbConn) throws Exception
   {
      DbTableFns.dropTable(dbConn, TN);
   }
     
   // **************************************************************************
   
   public static void insertRow(DbConnection dbConn, SmsDaoSessRecAc rec)
                      throws Exception
   {  
      DbInsertFns.insert(dbConn, TN, ACN, rec);                             
   }
   
   // **************************************************************************

   public static void deleteRow(DbConnection dbConn, int id) throws Exception
   {  
      DbDeleteFns.delete(dbConn, TN, getDefaultQual(id));
   }
   
   public static void deleteRow(DbConnection dbConn, String appId, int userId)
                         throws Exception
   {  
      
      String qual;
      
      qual = "WHERE " + CD_USERID.getName() + "=" + userId + " AND "
             + CD_APPID.getName() + "='" + appId + "'";
    
      DbDeleteFns.delete(dbConn, TN, qual);
      
   }
      
   // **************************************************************************  
  
   public static SmsDaoSessRecAc selectRow(DbConnection dbConn, int id)
                                throws Exception
   {  
      
      SmsDaoSessRecAc rec = new SmsDaoSessRecAc();
      
      DbSelectFns.select(dbConn, TN, ACN, getDefaultQual(id), rec);      
         
      return rec;
         
   }
   
   public static boolean rowExists(DbConnection dbConn, int id, String appId)
                         throws Exception
   {  
      
      String qual;
      
      qual = "WHERE " + CD_ID.getName() + "=" + id + " AND "
             + CD_APPID.getName() + "='" + appId + "'";
    
      return DbSelectFns.rowExists(dbConn, TN, qual);
      
   }
   
   
} // class
