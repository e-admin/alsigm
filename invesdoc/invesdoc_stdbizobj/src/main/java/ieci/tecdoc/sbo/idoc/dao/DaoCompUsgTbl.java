
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbTableFns;


public class DaoCompUsgTbl
{
   private static final String TN = "IDOCCOMPUSG";

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
   
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "COMPID,COMPTYPE";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false,false);      
      
      DbTableFns.createTable(TN,ACD);
      
      DbTableFns.createIndex(TN,indexDef);      
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";
      
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
