
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbIndexDef;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbTableFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;


public final class AcsDaoUserTypeTbl
{
   
   // **************************************************************************
   /* 
 *@SF-SEVILLA 
 *02-may-2006 / antmaria
 */
   public static final String TN = DbConnectionConfig.getSchema() +  "IUSERUSERTYPE";

   public static final DbColumnDef CD_USERID = new DbColumnDef
   ("USERID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);
   
   public static final DbColumnDef CD_TYPE = new DbColumnDef
   ("TYPE", DbDataType.LONG_INTEGER, false);
  
   public static final DbColumnDef[] ACD = 
   {CD_USERID, CD_PRODID, CD_TYPE};  
   
   public static final String ACN = DbUtil.getColumnNames(ACD);
   
   // **************************************************************************

   public static String getDefaultQual(int userId)
   {
      return "WHERE " + CD_USERID.getName() + "= " + userId;
   }

   public static String getDefaultQual(int userId, int prodId)
   {
      return "WHERE " + CD_USERID.getName() + "=" + userId  
             + " AND " + CD_PRODID.getName() + "=" + prodId;
   }
      
   // **************************************************************************
   
   private AcsDaoUserTypeTbl()
   {
   }
   
   // **************************************************************************
    
   public static int selectType(int userId, int prodId, String entidad)
                     throws Exception
   {  
      int type;      
      
      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession(entidad));
	      type = DbSelectFns.selectLongInteger(dbConn, TN, CD_TYPE.getName(),
	                                           getDefaultQual(userId, prodId));
      }catch (Exception e){
    		throw e;
    	}finally{
    		dbConn.close();
    	}
      return type;
         
   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2,indexName3;
      String colNamesIndex,colNamesIndex2,colNamesIndex3;
      DbIndexDef indexDef,indexDef2,indexDef3;
      
      indexName = TN + "1";
      colNamesIndex = "USERID";
      indexName2 = TN + "2";
      colNamesIndex2 = "USERID,PRODID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
      DbTableFns.createTable(dbConn, TN,ACD);   
	      DbTableFns.createIndex(dbConn, TN,indexDef);
	      DbTableFns.createIndex(dbConn, TN,indexDef2);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;      
      
      indexName = TN + "1";
      indexName2 = TN + "2";
      
      dropIndex(dbConn, TN,indexName);
      dropIndex(dbConn, TN, indexName2);

      	DbTableFns.dropTable(dbConn, TN);      
   }
   
   private static void dropIndex(DbConnection dbConn, String tblName, String indexName) throws Exception
   {
	   try{
         DbTableFns.dropIndex(dbConn, tblName,indexName);
	   }catch (Exception e){
		
	   }
   }
   
} // class
