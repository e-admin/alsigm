package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbInsertFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;



public final class FssDaoFtsTbl
{

   // **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   private static final String         TN       ="IVOLFILEFTS";

   private static final DbColumnDef   CD_ID     = new DbColumnDef("ID",
                                                      DbDataType.LONG_INTEGER,
                                                      false);

   private static final DbColumnDef   CD_EXTID1 = new DbColumnDef("EXTID1",
                                                      DbDataType.LONG_INTEGER,
                                                      false);   

   private static final DbColumnDef   CD_EXTID2 = new DbColumnDef("EXTID2",
                                                      DbDataType.LONG_INTEGER,
                                                      false);

   private static final DbColumnDef   CD_EXTID3 = new DbColumnDef("EXTID3",
                                                      DbDataType.LONG_INTEGER,
                                                      false);
   
   private static final DbColumnDef   CD_EXTID4 = new DbColumnDef("EXTID4",
         											  DbDataType.LONG_INTEGER,
         											  false);

   private static final DbColumnDef   CD_PATH   = new DbColumnDef("PATH",
         											  DbDataType.SHORT_TEXT, 254, 
                                                      false);
  
   private static final DbColumnDef   CD_TS     = new DbColumnDef("TIMESTAMP",
                                                      DbDataType.DATE_TIME,
                                                      true);

   private static final DbColumnDef[] ACD       = { CD_ID, CD_EXTID1, CD_EXTID2, CD_EXTID3,
         											CD_EXTID4, CD_PATH, CD_TS};

   private static final String        ACN       = DbUtil.getColumnNames(ACD);

  
   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "=" + id;
   }

   // **************************************************************************

   private FssDaoFtsTbl()
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
   
   public static String getIdColName(boolean qualified)
   {
      return getColName(CD_ID, qualified);
   }
   
   public static String getExtId1ColName(boolean qualified)
   {
      return getColName(CD_EXTID1, qualified);
   }
   
   public static String getExtId2ColName(boolean qualified)
   {
      return getColName(CD_EXTID2, qualified);
   } 
   
   public static String getExtId3ColName(boolean qualified)
   {
      return getColName(CD_EXTID3, qualified);
   }

   public static String getExtId4ColName(boolean qualified)
   {
      return getColName(CD_EXTID4, qualified);
   }
   
   public static String getPathColName(boolean qualified)
   {
      return getColName(CD_PATH, qualified);
   }
   
   public static String getTSColName(boolean qualified)
   {
      return getColName(CD_TS, qualified);
   } 

   // **************************************************************************

   public static void insertRow(DbConnection dbConn, FssDaoFtsRecAc rec) throws Exception
   {
      DbInsertFns.insert(dbConn, TN, ACN, rec);
   }   

   // **************************************************************************

   public static void deleteRow(DbConnection dbConn, int id) throws Exception
   {
      DbDeleteFns.delete(dbConn, TN, getDefaultQual(id));
   }   
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "PATH";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
      
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

} // class
