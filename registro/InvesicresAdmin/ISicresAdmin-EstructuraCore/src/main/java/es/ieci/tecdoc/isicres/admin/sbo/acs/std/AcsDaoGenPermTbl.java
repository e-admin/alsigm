
package es.ieci.tecdoc.isicres.admin.sbo.acs.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;


public final class AcsDaoGenPermTbl
{
   
   // **************************************************************************

  /* 
  *@SF-SEVILLA 
  *02-may-2006 / antmaria
  */
   public static final String TN = DbConnectionConfig.getSchema() +  "IUSERGENPERMS";
   // end

   public static final DbColumnDef CD_DSTTYPE = new DbColumnDef
   ("DSTTYPE", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_DSTID = new DbColumnDef
   ("DSTID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_PRODID = new DbColumnDef
   ("PRODID", DbDataType.LONG_INTEGER, false);

   public static final DbColumnDef CD_PERMS = new DbColumnDef
   ("PERMS", DbDataType.LONG_INTEGER, false);   
   
   public static final DbColumnDef[] ACD = 
   {CD_DSTTYPE, CD_DSTID, CD_PRODID, CD_PERMS};
  
   public static final String ACN = DbUtil.getColumnNames(ACD);
   
   // **************************************************************************
   
   public static String getDefaultQual(int dstType, int dstId, int prodId)
   {
      return "WHERE " + CD_DSTTYPE.getName() + "=" + dstType  
             + " AND " + CD_DSTID.getName() + "=" + dstId + " AND "
             + CD_PRODID.getName() + "=" + prodId ;
   }
   
   // **************************************************************************
   
   private AcsDaoGenPermTbl()
   {
   }  
   
   // **************************************************************************   
    
   public static int selectGenPerms(int dstType, int dstId, int prodId, String entidad)
                     throws Exception
   {  
      
      int perms;   
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());
	      perms = DbSelectFns.selectLongInteger(dbConn, TN, CD_PERMS.getName(),
	                                            getDefaultQual(dstType, dstId, prodId));
      }catch (Exception e){
    		throw e;
    	}finally{
    		dbConn.close();
    	}
      return perms;
         
   }   
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "DSTTYPE,DSTID";
      indexName2 = TN + "2";
      colNamesIndex2 = "DSTTYPE,DSTID,PRODID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,false);
      indexDef2 = new DbIndexDef(indexName2,colNamesIndex2,true);
  
	    DbTableFns.createTable(dbConn, TN,ACD);   
	    DbTableFns.createIndex(dbConn, TN,indexDef);
	    DbTableFns.createIndex(dbConn, TN,indexDef2);
   }
   
   public static void dropTable(DbConnection dbConn) throws Exception
   {
      String indexName;      
      
      indexName = TN + "1";
      dropIndex(dbConn, TN,indexName);
      indexName = TN + "2";
      dropIndex(dbConn, TN,indexName);

      	DbTableFns.dropTable(dbConn, TN);    
      
   }
   
   private static void dropIndex(DbConnection dbConn,  String tblName, String indexName) throws Exception
   {
	   try{
         DbTableFns.dropIndex(dbConn, tblName,indexName);
	   }catch (Exception e){
		}
   }
   
} // class
