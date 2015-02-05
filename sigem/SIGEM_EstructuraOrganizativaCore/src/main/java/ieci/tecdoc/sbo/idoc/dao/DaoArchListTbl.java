
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;


public class DaoArchListTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN = "IVOLARCHLIST";

   private static final DbColumnDef CD_ARCHID = new DbColumnDef
   ("ARCHID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_LISTID = new DbColumnDef
   ("LISTID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = {CD_ARCHID, CD_LISTID};  
   
// **************************************************************************

   private DaoArchListTbl()
   {
   }
   
   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ARCHID.getName() + "= " + id;
   }  
   
   // **************************************************************************
   
	protected static String getColName(DbColumnDef colDef, boolean qualified)
	{
	   String colName = colDef.getName();
	   
	   if (qualified)
	      colName =  TN + "." + colName;
	   
	   return colName;
	}
   
   public static String getTblName()
   {      
      return TN;
   }
   
   public static String getArchIdColName(boolean qualified)
   {
      return getColName(CD_ARCHID, qualified);
   }
   
   public static String getListIdColName(boolean qualified)
   {
      return getColName(CD_LISTID, qualified);
   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";      
      colNamesIndex = "ARCHID";
      indexName2 = TN + "2";
      colNamesIndex2 = "LISTID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";      
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
