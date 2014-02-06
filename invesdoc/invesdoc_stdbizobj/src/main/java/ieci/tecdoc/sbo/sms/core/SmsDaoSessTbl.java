
package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.core.db.*;

public final class SmsDaoSessTbl
{
   
   // **************************************************************************
   
   private static final String TN = "ITDSMSSESS";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("CID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_APPID = new DbColumnDef
   ("CAPPID", DbDataType.SHORT_TEXT, 64, false);

   private static final DbColumnDef CD_USERID = new DbColumnDef
   ("CUSERID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CCRTTS", DbDataType.DATE_TIME, false); 
   
   private static final DbIndexDef ID_1 = new DbIndexDef
   (TN + "1", CD_ID.getName(), true, false);
   
   private static final DbIndexDef ID_2 = new DbIndexDef
   (TN + "2", CD_APPID.getName(), false, false);
   
   private static final DbIndexDef ID_3 = new DbIndexDef
   (TN + "3", CD_USERID.getName(), false, false);   
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_APPID, CD_USERID, CD_CRTTS};
   
   private static final DbIndexDef[] AID =
   {ID_1, ID_2, ID_3};
      
   private static final String ACN = DbUtil.getColumnNames(ACD);
   
      
   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "=" + id;
   }
   
   // **************************************************************************
   
   private SmsDaoSessTbl()
   {
   }
   
   // **************************************************************************
   
   public static void createTable() throws Exception
   {
      DbTableFns.createTable(TN, ACD, AID);
   }

   public static void dropTable() throws Exception
   {
      DbTableFns.dropTable(TN);
   }
     
   // **************************************************************************
   
   public static void insertRow(SmsDaoSessRecAc rec)
                      throws Exception
   {  
      DbInsertFns.insert(TN, ACN, rec);                             
   }
   
   // **************************************************************************

   public static void deleteRow(int id) throws Exception
   {  
      DbDeleteFns.delete(TN, getDefaultQual(id));
   }
   
   public static void deleteRow(String appId, int userId)
                         throws Exception
   {  
      
      String qual;
      
      qual = "WHERE " + CD_USERID.getName() + "=" + userId + " AND "
             + CD_APPID.getName() + "='" + appId + "'";
    
      DbDeleteFns.delete(TN, qual);
      
   }
      
   // **************************************************************************  
  
   public static SmsDaoSessRecAc selectRow(int id)
                                throws Exception
   {  
      
      SmsDaoSessRecAc rec = new SmsDaoSessRecAc();
      
      DbSelectFns.select(TN, ACN, getDefaultQual(id), rec);      
         
      return rec;
         
   }
   
   public static boolean rowExists(int id, String appId)
                         throws Exception
   {  
      
      String qual;
      
      qual = "WHERE " + CD_ID.getName() + "=" + id + " AND "
             + CD_APPID.getName() + "='" + appId + "'";
    
      return DbSelectFns.rowExists(TN, qual);
      
   }
   
   
} // class
