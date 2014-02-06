
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoXNextIdTbl
{
   
   // **************************************************************************
   
   private static final String TN = "IDOCXNEXTID";

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
      return " WHERE " + CD_TYPE.getName() + "=" + type +
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
   
   public static void incrementNextId(int type, int parentId) throws Exception
   {

      incrementNextId(type, parentId, 1);

   }

   public static void incrementNextId(int type, int parentId, int incr) throws Exception
   {

      String stmtText;
      String qual = getDefaultQual(type, parentId);

      stmtText = "UPDATE " + TN + " SET " + CD_ID.getName() + "="
            + CD_ID.getName() + "+" + incr + qual;

      DbUtil.executeStatement(stmtText);

   }

   public static int getNextId(int type, int parentId) throws Exception
   {

      int nextId;

      nextId = DbSelectFns.selectLongInteger(TN, CD_ID.getName(),
                                             getDefaultQual(type, parentId));

      return nextId;

   } 
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "TYPE,PARENTID";
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,true);
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
   
} // class
