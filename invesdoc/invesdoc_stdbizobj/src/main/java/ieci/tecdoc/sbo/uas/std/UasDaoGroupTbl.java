package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;
import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;


public final class UasDaoGroupTbl
{
	
	//~ Static fields/initializers ---------------------------------------------
	
	public static final String	         TN           = "IUSERGROUPHDR";
	
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
	
	public static String selectName(int id) throws Exception
	{
		String name = DbSelectFns.selectShortText(TN, CD_NAME.getName(), getDefaultQual(id));
		return name;
	}
	
	public static IeciTdShortTextArrayList selectNames(IeciTdLongIntegerArrayList ids) throws Exception
	{
		IeciTdShortTextArrayList names = new IeciTdShortTextArrayList();
		
		if (ids.count() > 0)
		{
			String qual = getINQual(ids);
			DbSelectFns.select(TN, CD_NAME.getName(), qual, true, names);
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
   public static void createTable() throws Exception
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
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true,false);
      indexDef3= new DbIndexDef(indexName3,colNamesIndex3,false,false);
      indexDef4= new DbIndexDef(indexName4,colNamesIndex4,false,false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
      DbTableFns.createIndex(TN,indexDef3);
      DbTableFns.createIndex(TN,indexDef4);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
      indexName = TN + "2";
      dropIndex(TN,indexName);
      indexName = TN + "3";
      dropIndex(TN,indexName);
      indexName = TN + "4";
      dropIndex(TN, indexName);     
         
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
}// class
