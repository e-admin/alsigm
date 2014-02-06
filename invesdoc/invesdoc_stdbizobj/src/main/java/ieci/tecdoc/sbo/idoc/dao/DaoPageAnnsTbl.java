
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public class DaoPageAnnsTbl
{
   private static final String TN = "IDOCPAGEANNS";

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
   
// **************************************************************************
   
   public static void createTable() throws Exception
   {
      String indexName;
      String colNamesIndex;
      DbIndexDef indexDef;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);      
      
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
   
   
   public static void insertRow(DaoPageAnnsRow row)
                      throws Exception
   {
      DbInsertFns.insert(TN, ACN, row);
   }

   public static void updateData(int id, DaoPageAnnsRowUpd row)
						  throws Exception
	{

      DbUpdateFns.update(TN, UCN, row, getDefaultQual(id));
		
	}

   public static String selectData(int id) throws Exception
   {

      String data = null;

      data = DbSelectFns.selectLongText(TN, CD_DATA.getName(), getDefaultQual(id));

      return data;

   } 

   // **************************************************************************

   public static void deleteRow(int id)  throws Exception
   {
      DbDeleteFns.delete(TN, getDefaultQual(id));
   }  
   
   public static void deleteRows(String qual)  throws Exception
   {
      DbDeleteFns.delete(TN, qual);
   }  

}


