package ieci.tecdoc.sbo.uas.std;

import ieci.tecdoc.core.collections.IeciTdShortTextArrayList;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbDeleteFns;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbInsertFns;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;

public class UasDaoPwdsTbl
{
// ~ Static fields/initializers ---------------------------------------------

	public static final String		    TN			    = "IUSERUSERPWDS";
	
	public static final DbColumnDef    CD_USERID       = new DbColumnDef("USERID",
																		  DbDataType.LONG_INTEGER,
																		  false);
	public static final DbColumnDef    CD_PASSWORD     = new DbColumnDef("PASSWORD",
			  															  DbDataType.SHORT_TEXT,
			  															  68,
			  															  false); 
	public static final DbColumnDef    CD_UPDRID	      = new DbColumnDef("UPDRID",
			  															  DbDataType.LONG_INTEGER,
			  															  true);
	public static final DbColumnDef    CD_UPDDATE	   = new DbColumnDef("UPDDATE",
	      															  DbDataType.DATE_TIME,
	      															  true);
	
	public static final DbIndexDef     ID_1	         = new DbIndexDef(TN+"1",
			                                               CD_USERID.getName(),
			                                               false,false);
   public static final DbIndexDef     ID_2	         = new DbIndexDef(TN+"2",
			                                               CD_PASSWORD.getName(),
			                                               false,false);

	
	public static final DbColumnDef [] ACD			    = { CD_USERID, CD_PASSWORD, 
	                                                     CD_UPDRID, CD_UPDDATE };
	
	public static final DbIndexDef []  AID		   = { ID_1, ID_2 };
	
	private static final String ACN = DbUtil.getColumnNames(ACD);
	
//	~ Constructors -----------------------------------------------------------

	/**
	 * @autor IECISA
	 * 
	 * @since V1.0
	 */
	private UasDaoPwdsTbl(){}

	/**
	 * @autor IECISA
	 * 
	 * @param id		Identificador del usuario
	 * @return String   Sentencia SQL
	 * 
	 * @since V1.0
	 */
	//~ Methods ----------------------------------------------------------------

	public static IeciTdShortTextArrayList selectPasswords(long userId) 
	                                               throws Exception
	{
	   
		IeciTdShortTextArrayList passwords = new IeciTdShortTextArrayList();
		String                   qual;
		
		qual = "WHERE " + CD_USERID.getName() + " = " + userId;		
		qual = qual + " ORDER BY " + CD_UPDDATE.getName() + " DESC";
		
		DbSelectFns.select(TN, CD_PASSWORD.getName(), qual, false, passwords);
		
	
		return passwords;
	}
	
// **************************************************************************
   
   public static void insertRow(UasDaoPwdsRecAc rec)
                      throws Exception
   {  
      DbInsertFns.insert(TN, ACN, rec);                             
   }
   
 // **************************************************************************  
   public static void deleteRow(int userId, String pwd)
                      throws Exception
   {  

   	String qual;

      qual = "WHERE " + CD_USERID.getName() + "=" + userId + " AND "
             + CD_PASSWORD.getName() + "='" + pwd + "'";

      DbDeleteFns.delete(TN, qual);

   }
	
// **************************************************************************
   public static void createTable() throws Exception
   {
      
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "USERID";
      indexName2 = TN + "2";
      colNamesIndex2 = "PASSWORD";
      
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
