
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDeleteFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;



public class DaoPageAnnsTbl
{
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   private static final String TN ="IDOCPAGEANNS";

   private static final DbColumnDef CD_ID = new DbColumnDef
   ("ID", DbDataType.LONG_INTEGER, false);
   
   private static final DbColumnDef CD_DATA = new DbColumnDef
   ("DATA", DbDataType.LONG_TEXT, 10240, false);
   
   private static final DbColumnDef CD_CRTUSRID = new DbColumnDef
   ("CRTRID", DbDataType.LONG_INTEGER, false);

   private static final DbColumnDef CD_CRTTS = new DbColumnDef
   ("CRTNDATE", DbDataType.DATE_TIME, false);

   private static final DbColumnDef CD_UPDUSRID = new DbColumnDef
   ("UPDRID", DbDataType.LONG_INTEGER, true);

   private static final DbColumnDef CD_UPDTS = new DbColumnDef
   ("UPDDATE", DbDataType.DATE_TIME, true);
   
   private static final DbColumnDef[] ACD = 
   {CD_ID, CD_DATA, CD_CRTUSRID, CD_CRTTS, CD_UPDUSRID, CD_UPDTS};  

   private static final String ACN = DbUtil.getColumnNames(ACD); 
   
   public static final String  UCN  = DbUtil.getColumnNames
  (CD_DATA, CD_UPDUSRID, CD_UPDTS);

// **************************************************************************
   
   private DaoPageAnnsTbl()
   {
   }

   private static String getDefaultQual(int id)
   {
      return "WHERE " + CD_ID.getName() + "= " + id;
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
   
   public static String getTblName()
   {
      return TN;
   }
   
   private static String getColName(DbColumnDef colDef, boolean qualified)
   {
      String colName = colDef.getName();
      
      if (qualified)
         colName =  TN + "." + colName;
      
      return colName;
   }
   
   public static String getIdColName(boolean qualified)
   {
      return getColName(CD_ID, qualified);      
   }
   
   
   public static void insertRow(DbConnection dbConn, DaoPageAnnsRow row)
                      throws Exception
   {
      DbInsertFns.insert(dbConn, TN, ACN, row);
   }

   public static void updateData(DbConnection dbConn, int id, DaoPageAnnsRowUpd row)
						  throws Exception
	{

      DbUpdateFns.update(dbConn, TN, UCN, row, getDefaultQual(id));
		
	}

   public static String selectData(DbConnection dbConn, int id) throws Exception
   {

      String data = null;

      data = DbSelectFns.selectLongText(dbConn, TN, CD_DATA.getName(), getDefaultQual(id));

      return data;

   } 

   // **************************************************************************

   public static void deleteRow(DbConnection dbConn, int id)  throws Exception
   {
      DbDeleteFns.delete(dbConn, TN, getDefaultQual(id));
   }  
   
   public static void deleteRows(DbConnection dbConn, String qual)  throws Exception
   {
      DbDeleteFns.delete(dbConn, TN, qual);
   }  

}


