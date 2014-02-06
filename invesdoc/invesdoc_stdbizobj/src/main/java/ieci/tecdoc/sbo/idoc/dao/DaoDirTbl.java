
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public class DaoDirTbl
{
   
   // **************************************************************************
   
   private static final String TN = "IDOCDIRHDR";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 32, false);
   
   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_FLAGS = new DbColumnDef
   ("FLAGS", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_REMS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT, 254, true); 
   
   private static final DbColumnDef CD_ACCESSTYPE = new DbColumnDef
   ("ACCESSTYPE", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_ACSID = new DbColumnDef
   ("ACSID", DbDataType.LONG_INTEGER, false); 
   
   private static final DbColumnDef CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);
   
   private static final DbColumnDef CD_UPDUSRID = new DbColumnDef 
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   private static final DbColumnDef CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
  
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_NAME, CD_TYPE, CD_FLAGS, CD_REMS, CD_ACCESSTYPE, CD_ACSID,
    CD_CRTUSRID, CD_CRTTS, CD_UPDUSRID, CD_UPDTS};  
   
   private static final String ACN = DbUtil.getColumnNames(ACD);
   
   private static final String OCN = DbUtil.getColumnNames
   (CD_ID, CD_NAME, CD_ACCESSTYPE, CD_ACSID);
   
   private static final int DIR_TYPE_STANDARD = 0;
   
   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   }   
      
   // **************************************************************************
   
   private DaoDirTbl()
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
   
   public static String getNameColName(boolean qualified)
   {
      return getColName(CD_NAME, qualified);
   }
   
   public static String getTypeColName(boolean qualified)
   {
      return getColName(CD_TYPE, qualified);
   }
   
   public static String getFlagsColName(boolean qualified)
   {
      return getColName(CD_FLAGS, qualified);
   }
   
   public static String getRemarksColName(boolean qualified)
   {
      return getColName(CD_REMS, qualified);
   }
   
   public static String getAccessTypeColName(boolean qualified)
   {
      return getColName(CD_ACCESSTYPE, qualified);
   }
   
   public static String getAcsIdColName(boolean qualified)
   {
      return getColName(CD_ACSID, qualified);
   }
   
   public static String getCrtUserIdColName(boolean qualified)
   {
      return getColName(CD_CRTUSRID, qualified);
   }
   
   public static String getCrtTSColName(boolean qualified)
   {
      return getColName(CD_CRTTS, qualified);
   }
   
   public static String getUpdUserIdColName(boolean qualified)
   {
      return getColName(CD_UPDUSRID, qualified);
   }
   
   public static String getUpdTSColName(boolean qualified)
   {
      return getColName(CD_UPDTS, qualified);
   }
   
   // **************************************************************************
   
   public static void selectChildrenRows(int parentDirId, DaoOutputRows rows) throws Exception
   {  
      
      String joinText, qual;       
      
      joinText = DaoDATNodeTbl.getSelectStatementTextChildrenDirNodes(parentDirId);
      
      qual = "WHERE EXISTS(" + joinText + " ) AND " +
             CD_TYPE.getName() + "=" + DIR_TYPE_STANDARD + 
             " ORDER BY " + CD_NAME.getName();     
      
      DbSelectFns.select(TN, rows.getColumnNames(), qual, false, rows);      
          
   }   
   
   // **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef, indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "TYPE";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,false,false);
      
      DbTableFns.createTable(TN,ACD);
      
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
   }
   
   public static void dropTable() throws Exception
   {
      String idxName, idxName2;
      
      idxName = TN + "1";
      idxName2 = TN + "2";
      
      dropIndex(TN,idxName);      
      dropIndex(TN,idxName2);         
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
