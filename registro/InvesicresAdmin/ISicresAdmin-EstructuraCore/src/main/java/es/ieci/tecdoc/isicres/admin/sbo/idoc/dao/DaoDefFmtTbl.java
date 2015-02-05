
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;

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
