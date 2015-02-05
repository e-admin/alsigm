
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;


public final class DaoXNextIdTbl
{
   
   // **************************************************************************
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */   
   private static final String TN ="IDOCXNEXTID";

   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_PARENTID = new DbColumnDef
   ("PARENTID", DbDataType.LONG_INTEGER, false);   
   
   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
  
   private static final DbColumnDef[] ACD = 
   {CD_TYPE, CD_PARENTID, CD_ID};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);  
   
   // **************************************************************************

   private static String getDefaultQual(int type, int parentId)
   {
      return "WHERE " + CD_TYPE.getName() + "=" + type +
             " AND " + CD_PARENTID.getName() + "=" + parentId;
   }
      
   // **************************************************************************
   
   private DaoXNextIdTbl()
   {
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
   
   public static String getTypeColName(boolean qualified)
   {
      return getColName(CD_TYPE, qualified);
   }
   
   public static String getParentIdColName(boolean qualified)
   {
      return getColName(CD_PARENTID, qualified);
   }
   
   public static String getIdColName(boolean qualified)
   {
      return getColName(CD_ID, qualified);
   }   
   
   // **************************************************************************
   
   public static void incrementNextId(DbConnection dbConn, int type, int parentId) throws Exception
   {

      incrementNextId(dbConn, type, parentId, 1);

   }

   public static void incrementNextId(DbConnection dbConn, int type, int parentId, int incr) throws Exception
   {

      String stmtText;
      String qual = getDefaultQual(type, parentId);

      stmtText = "UPDATE " + TN + " SET " + CD_ID.getName() + "="
            + CD_ID.getName() + "+" + incr + qual;

      DbUtil.executeStatement(dbConn, stmtText);

   }

   public static int getNextId(DbConnection dbConn, int type, int parentId) throws Exception
   {

      int nextId;

      nextId = DbSelectFns.selectLongInteger(dbConn, TN, CD_ID.getName(),
                                             getDefaultQual(type, parentId));

      return nextId;

   } 
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "TYPE,PARENTID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
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
   
} // class
