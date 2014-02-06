
package ieci.tecdoc.sbo.idoc.vldtable.std;

import ieci.tecdoc.core.db.*;

public  class VldDaoVTblCtlgTbl
{
   
   // **************************************************************************
   
   private static final String TN = "IDOCVTBLCTLG";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 64, false);
   
   private static final DbColumnDef CD_BTBLID = new DbColumnDef
   ("BTBLID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_INFO = new DbColumnDef
   ("INFO", DbDataType.LONG_TEXT ,10240, false);
   
   private static final DbColumnDef CD_FLAGS = new DbColumnDef
   ("FLAGS", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_REMARKS = new DbColumnDef
   ("REMARKS", DbDataType.SHORT_TEXT ,254, true);
   
   private static final DbColumnDef CD_CRTRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);

   private static final DbColumnDef CD_UPDUSRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   private static final DbColumnDef CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
  
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_NAME, CD_BTBLID ,CD_TYPE, CD_INFO ,CD_FLAGS, CD_REMARKS , CD_CRTRID , CD_CRTTS ,
    CD_UPDUSRID, CD_UPDTS};
   
   private static final DbColumnDef[] BCD =
   {CD_ID, CD_NAME, CD_BTBLID ,CD_TYPE, CD_INFO };

   private static final String ACN = DbUtil.getColumnNames(ACD);

   private static final String BCN = DbUtil.getColumnNames
   (CD_ID, CD_NAME, CD_BTBLID , CD_TYPE,CD_INFO );

   private static final int DIR_TYPE_STANDARD = 0;
   
   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   }   
      
   // **************************************************************************
   
   private VldDaoVTblCtlgTbl()
   {
   }
   
   // **************************************************************************
   

   public static VldDaoVTblCtglRecA selectRow(int Id) throws Exception
   {

      VldDaoVTblCtglRecA rec = new VldDaoVTblCtglRecA() ;

      DbSelectFns.select(TN,BCN,getDefaultQual(Id),rec );

      return rec;

   }
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
           
      indexDef= new DbIndexDef(indexName,colNamesIndex,true, false);     
      
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
