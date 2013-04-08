package es.ieci.tecdoc.isicres.admin.sbo.uas.std;

import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;

public final class UasDaoGroupTbl
{
	
	//~ Static fields/initializers ---------------------------------------------
	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
	public static final String	         TN           ="IUSERGROUPHDR";
	
	public static final DbColumnDef    CD_ID         = new DbColumnDef
	("ID",DbDataType.LONG_INTEGER, false);
	
	public static final DbColumnDef    CD_NAME       = new DbColumnDef
	("NAME",DbDataType.SHORT_TEXT,32, false);
	
	public static final DbColumnDef    CD_MGRID      = new DbColumnDef
	("MGRID", DbDataType.LONG_INTEGER,false);
	
	public static final DbColumnDef    CD_TYPE       = new DbColumnDef
	("TYPE",DbDataType.LONG_INTEGER,false);
	
	public static final DbColumnDef    CD_REMARKS	 = new DbColumnDef
	("REMARKS", DbDataType.SHORT_TEXT,254,true);
	
	public static final DbColumnDef    CD_CRTRID	    = new DbColumnDef
	("CRTRID",DbDataType.LONG_INTEGER,false);
	
	public static final DbColumnDef    CD_CRTNDATE   = new DbColumnDef
	("CRTNDATE",DbDataType.DATE_TIME,false);
	
	public static final DbColumnDef    CD_UPDRID	    = new DbColumnDef
	("UPDRID",DbDataType.LONG_INTEGER,true);
	
	public static final DbColumnDef    CD_UPDATE	    = new DbColumnDef
	("UPDDATE", DbDataType.DATE_TIME,true);									 
	
	
	private static final DbColumnDef [] ACD  = 
	{ CD_ID, CD_NAME, CD_MGRID, CD_TYPE, CD_REMARKS, CD_CRTRID,CD_CRTNDATE, CD_UPDRID, CD_UPDATE};
	
	private static final String   ACN = DbUtil.getColumnNames(ACD);
	
	private UasDaoGroupTbl(){}
	
	private static String getDefaultQual(int id)
	{
	
		return "WHERE "+ CD_ID.getName()+ "=" + id;
	
	}
	
	public static String selectName(DbConnection dbConn, int id) throws Exception
	{
		String name = DbSelectFns.selectShortText(dbConn, TN, CD_NAME.getName(), getDefaultQual(id));
		return name;
	}
	
	public static IeciTdShortTextArrayList selectNames(DbConnection dbConn, IeciTdLongIntegerArrayList ids) throws Exception
	{
		IeciTdShortTextArrayList names = new IeciTdShortTextArrayList();
		
		if (ids.count() > 0)
		{
			String qual = getINQual(ids);
			DbSelectFns.select(dbConn, TN, CD_NAME.getName(), qual, true, names);
		}
	
		return names;
	}
	
	private static String getINQual(IeciTdLongIntegerArrayList ids) throws Exception
	{
		String qual = null;
		
		qual = " WHERE " + CD_ID.getName() + " IN (";
		
		for (int i=0; i < ids.count(); i++)
		{
			qual += ids.get(i);
			
			if (i < (ids.count() - 1))
				qual +=",";
		}
		
		qual += ")";
		
		return qual;
	}
	
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3,indexName4;
      String colNamesIndex,colNamesIndex2,colNamesIndex3,colNamesIndex4;
      DbIndexDef indexDef,indexDef2,indexDef3,indexDef4;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "NAME";
      indexName3 = TN + "3";
      colNamesIndex3 = "MGRID";
      indexName4 = TN + "4";
      colNamesIndex4 = "TYPE";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
      indexDef3= new DbIndexDef(indexName3,colNamesIndex3,false);
      indexDef4= new DbIndexDef(indexName4,colNamesIndex4,false);
      
      DbTableFns.createTable(dbConn, TN,ACD);   
      DbTableFns.createIndex(dbConn, TN,indexDef);
      DbTableFns.createIndex(dbConn, TN,indexDef2);
      DbTableFns.createIndex(dbConn, TN,indexDef3);
      DbTableFns.createIndex(dbConn, TN,indexDef4);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "3";
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "4";
      dropIndex(dbConn, TN, indexName);     
         
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
}// class
