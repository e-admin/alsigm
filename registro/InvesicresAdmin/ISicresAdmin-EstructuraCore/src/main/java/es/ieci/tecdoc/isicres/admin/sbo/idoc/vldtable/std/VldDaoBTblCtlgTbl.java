
package es.ieci.tecdoc.isicres.admin.sbo.idoc.vldtable.std;


import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;

public  class VldDaoBTblCtlgTbl
{
   
   // **************************************************************************
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */   
   private static final String TN ="IDOCBTBLCTLG";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_NAME = new DbColumnDef
   ("NAME", DbDataType.SHORT_TEXT, 16, false);
   
   private static final DbColumnDef CD_DEF = new DbColumnDef
   ("DEF", DbDataType.LONG_TEXT , false);
   
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
   {CD_ID, CD_NAME, CD_DEF ,CD_FLAGS, CD_REMARKS , CD_CRTRID , CD_CRTTS ,
    CD_UPDUSRID, CD_UPDTS};
   
   private static final DbColumnDef[] BCD =
   {CD_ID, CD_NAME, CD_DEF};

   private static final String ACN = DbUtil.getColumnNames(ACD);

   private static final String BCN = DbUtil.getColumnNames
   (CD_ID, CD_NAME, CD_DEF );

   private static final int DIR_TYPE_STANDARD = 0;
   
   // **************************************************************************

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
   }   
      
   // **************************************************************************
   
   private VldDaoBTblCtlgTbl()
   {
   }
   
   
   protected static String getColName(DbColumnDef colDef, boolean qualified)
   {
      String colName = colDef.getName();
      
      if (qualified)
         colName =  TN + "." + colName;
      
      return colName;
   }
   
   // **************************************************************************
   

   public static String getTblName()
   {      
      return TN;
   }
   
   public static String getNameColName(boolean qualified)
   {
      return getColName(CD_NAME, qualified);
   }
   
   public static VldDaoBTblCtlgRecA selectRow(DbConnection dbConn, int Id) throws Exception
   {

      VldDaoBTblCtlgRecA rec = new VldDaoBTblCtlgRecA() ;

      DbSelectFns.select(dbConn, TN,BCN,getDefaultQual(Id),rec );
      /* 
       *@SF-SEVILLA 
       *02-may-2006 / antmaria
       */
      rec.m_name  = rec.m_name ;  

      return rec;

   }
   
// **************************************************************************
   
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);     
      
      DbTableFns.createTable(dbConn, TN,ACD);
      
      DbTableFns.createIndex(dbConn, TN,indexDef);
      
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";
      
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
