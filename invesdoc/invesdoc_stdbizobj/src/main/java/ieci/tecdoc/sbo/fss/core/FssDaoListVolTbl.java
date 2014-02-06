package ieci.tecdoc.sbo.fss.core;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public final class FssDaoListVolTbl
{

   // **************************************************************************

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

   public static IeciTdLongIntegerArrayList selectVolIds(int id)
      throws Exception
   {

      IeciTdLongIntegerArrayList volIds = new IeciTdLongIntegerArrayList();

      DbSelectFns.select(TN, CD_VOLID.getName(), getDefaultQualAndOrder(id),
            false, volIds);

      return volIds;

   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "LISTID";
      indexName2 = TN + "2";
      colNamesIndex2 = "VOLID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,false,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false,false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
      indexName = TN + "2";      
      dropIndex(TN,indexName);
         
      DbTableFns.dropTable(TN);      
      
   }
   
   private static void dropIndex(String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(tblName,indexName);
      }
      catch(Exception e)
      {         
      }
   }

} // class
