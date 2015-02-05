
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;

public class DaoDefFmtTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN ="IDOCPREFFMT";

   private static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_FMTTYPE = new DbColumnDef
   ("FMTTYPE", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_FMTID = new DbColumnDef
   ("FMTID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = 
   {CD_ARCHID, CD_FMTTYPE, CD_USERID, CD_FMTID};  
   
// **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "ARCHID,FMTTYPE,USERID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);      
      
      DbTableFns.createTable(dbConn, TN,ACD);
      
      DbTableFns.createIndex(dbConn, TN,indexDef);      
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String idxName;
      
      idxName = TN + "1";
      dropIndex(dbConn, TN,idxName);
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
