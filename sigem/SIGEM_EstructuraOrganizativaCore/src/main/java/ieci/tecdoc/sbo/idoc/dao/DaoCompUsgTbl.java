
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;


public class DaoCompUsgTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN ="IDOCCOMPUSG";

   private static final DbColumnDef CD_COMPID = new DbColumnDef
   ("COMPID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_COMPTYPE = new DbColumnDef
   ("COMPTYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("USRID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_USRTYPE = new DbColumnDef
   ("USRTYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = 
   {CD_COMPID, CD_COMPTYPE, CD_USERID, CD_USRTYPE, CD_ARCHID};  
   
// **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "COMPID,COMPTYPE";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);      
      
      DbTableFns.createTable(dbConn, TN,ACD);
      
      DbTableFns.createIndex(dbConn, TN,indexDef);      
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";
      
      dropIndex(dbConn, TN,indexName);
         
      DbTableFns.dropTable(dbConn, TN);      
      
   }
   
   private static void dropIndex(DbConnection dbConn, String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(dbConn, tblName,indexName);
      }
      catch(Exception e)
      {
         
      }
   }

}
