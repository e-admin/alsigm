package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.sgm.base.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;



public final class FssDaoListVolTbl
{

   // **************************************************************************
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String        TN        = "IVOLLISTVOL";

   public static final DbColumnDef   CD_LISTID = new DbColumnDef("LISTID",
                                                      DbDataType.LONG_INTEGER,
                                                      false);

   public static final DbColumnDef   CD_VOLID  = new DbColumnDef("VOLID",
                                                      DbDataType.LONG_INTEGER,
                                                      false);

   public static final DbColumnDef   CD_ORDER  = new DbColumnDef("SORTORDER",
                                                      DbDataType.LONG_INTEGER,
                                                      false);

   public static final DbColumnDef[] ACD       =
  { CD_LISTID, CD_VOLID, CD_ORDER};

   public static final String        ACN       = DbUtil.getColumnNames(ACD);

   // **************************************************************************

   private static String getDefaultQualAndOrder(int id)
   {
      return "WHERE " + CD_LISTID.getName() + "=" + id + " ORDER BY "
            + CD_ORDER.getName();
   }

   // **************************************************************************
   // **************************************************************************

   private FssDaoListVolTbl()
   {
   }

   // **************************************************************************

   public static IeciTdLongIntegerArrayList selectVolIds(DbConnection dbConn, int id)
      throws Exception
   {

      IeciTdLongIntegerArrayList volIds = new IeciTdLongIntegerArrayList();

      DbSelectFns.select(dbConn, TN, CD_VOLID.getName(), getDefaultQualAndOrder(id),
            false, volIds);

      return volIds;

   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "LISTID";
      indexName2 = TN + "2";
      colNamesIndex2 = "VOLID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false);
      
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
