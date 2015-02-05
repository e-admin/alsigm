package es.ieci.tecdoc.isicres.admin.sbo.uas.std;



import es.ieci.tecdoc.isicres.admin.base.collections.IeciTdLongIntegerArrayList;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;



public final class UasDaoGURelTbl
{

	//~ Static fields/initializers ---------------------------------------------
/* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
	public static final String		    TN		   = DbConnectionConfig.getSchema() + "IUSERGROUPUSER";
	
	public static final DbColumnDef    CD_GROUPID = new DbColumnDef("GROUPID",
																	 DbDataType.LONG_INTEGER,
																	 false);
	public static final DbColumnDef    CD_USERID  = new DbColumnDef("USERID",
																	 DbDataType.LONG_INTEGER,
																	 false);
	public static final DbIndexDef     ID_1	   = new DbIndexDef(TN+"1",
																	CD_GROUPID.getName(),
																	false);
	public static final DbIndexDef     ID_2	   = new DbIndexDef(TN+"2",
																	CD_USERID.getName(),
																	false);
	
	public static final DbColumnDef [] ACD		   = { CD_GROUPID, CD_USERID };
	
	public static final DbIndexDef []  AID		   = { ID_1, ID_2 };
	
	public static final String		    ACN		   = DbUtil.getColumnNames(ACD);

	//~ Constructors -----------------------------------------------------------

	private UasDaoGURelTbl(){}

	//~ Methods ----------------------------------------------------------------

	public static IeciTdLongIntegerArrayList selectGroupIds(int userId, String entidad)
												   throws Exception
	{

		IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();
		String					   qual;
		
		qual = "WHERE "+CD_USERID.getName()+"="+userId;
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			DbSelectFns.select(dbConn, TN, CD_GROUPID.getName(), qual, false, vals);
		}catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}


		return vals;

	}

	public static IeciTdLongIntegerArrayList selectUserIds(int groupId)
												  throws Exception
	{

	    IeciTdLongIntegerArrayList vals = new IeciTdLongIntegerArrayList();
		String					   qual;
		
		qual = "WHERE "+CD_GROUPID.getName()+"="+groupId;
		
		DbSelectFns.select(null, TN, CD_USERID.getName(), qual, false, vals);

		return vals;

	}
	
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "GROUPID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,false);
      
      DbTableFns.createTable(dbConn,  TN,ACD);   
      DbTableFns.createIndex(dbConn,  TN,indexDef);
      DbTableFns.createIndex(dbConn,  TN,indexDef2);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";      
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";
      dropIndex(dbConn, TN, indexName);
         
      DbTableFns.dropTable(dbConn,  TN);      
      
   }
   
   private static void dropIndex(DbConnection dbConn, String tblName, String indexName)
   {
      try
      {
         DbTableFns.dropIndex(dbConn,  tblName,indexName);
      }
      catch(Exception e)
      {
         
      }
   }

}
