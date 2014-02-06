package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;


public final class UasDaoGURelTbl
{

	//~ Static fields/initializers ---------------------------------------------

	public static final String		    TN		   = "IUSERGROUPUSER";
	
	public static final DbColumnDef    CD_GROUPID = new DbColumnDef("GROUPID",
																	 DbDataType.LONG_INTEGER,
																	 false);
	public static final DbColumnDef    CD_USERID  = new DbColumnDef("USERID",
																	 DbDataType.LONG_INTEGER,
																	 false);
	public static final DbIndexDef     ID_1	   = new DbIndexDef(TN+"1",
																	CD_GROUPID.getName(),
																	false,false);
	public static final DbIndexDef     ID_2	   = new DbIndexDef(TN+"2",
																	CD_USERID.getName(),
																	false,false);
	
	public static final DbColumnDef [] ACD		   = { CD_GROUPID, CD_USERID };
	
	public static final DbIndexDef []  AID		   = { ID_1, ID_2 };
	
	public static final String		    ACN		   = DbUtil.getColumnNames(ACD);

	//~ Constructors -----------------------------------------------------------

	private UasDaoGURelTbl(){}

	//~ Methods ----------------------------------------------------------------

	public static IeciTdLongIntegerArrayList selectGroupIds(int userId)
												   throws Exception
	{

		IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();
		String					   qual;
		
		qual = "WHERE "+CD_USERID.getName()+"="+userId;
		
		DbSelectFns.select(TN, CD_GROUPID.getName(), qual, false, vals);

		return vals;

	}

	public static IeciTdLongIntegerArrayList selectUserIds(int groupId)
												  throws Exception
	{

	    IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();
		String					   qual;
		
		qual = "WHERE "+CD_GROUPID.getName()+"="+groupId;
		
		DbSelectFns.select(TN, CD_USERID.getName(), qual, false, vals);

		return vals;

	}
	
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "GROUPID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false,false);
      
      DbTableFns.createTable(TN,ACD);   
      DbTableFns.createIndex(TN,indexDef);
      DbTableFns.createIndex(TN,indexDef2);
   }
   
   public static void dropTable() throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(TN,indexName);
      indexName = TN + "2";
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

}
