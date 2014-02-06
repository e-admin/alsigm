
package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.core.db.*;


public final class SmsDaoSessNextIdTbl
{
   
   // **************************************************************************
   
   private static final String TN = "ITDSMSNEXTID";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("CID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef[] ACD = 
   {CD_ID};
     
   private static final String ACN = DbUtil.getColumnNames(ACD);   
   
   
   // **************************************************************************
   
   private SmsDaoSessNextIdTbl()
   {
   }
   
  // **************************************************************************
   public static void initTblContentsNextId() throws Exception
   {
      
      String stmtText;
      
      stmtText = "INSERT INTO " + TN + " VALUES( 1 )";            
      DbUtil.executeStatement(stmtText);
      
   }
   
   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN, ACD);
   }
   
   public static void dropTable() throws Exception
   {
      DbTableFns.dropTable(TN);
   }
   
   // **************************************************************************  
   
   public static void incrementNextId(short incr) throws Exception
   {

      String stmtText;

      stmtText = "UPDATE " + TN + " SET " + CD_ID.getName() + "="
            + CD_ID.getName() + "+" + incr;

      DbUtil.executeStatement(stmtText);

   }
   
   // **************************************************************************  
  
   public static int getNextId() throws Exception
   {

      int nextId;

      nextId = DbSelectFns.selectLongInteger(TN, CD_ID.getName(), null);

      return nextId;

   }
} // class
