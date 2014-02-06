
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;


public class DaoArchListTbl
{
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
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";      
      colNamesIndex = "ARCHID";
      indexName2 = TN + "2";
      colNamesIndex2 = "LISTID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false,false);
      
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
}
