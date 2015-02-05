package es.ieci.tecdoc.isicres.admin.sbo.uas.ldap;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbDataType;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbIndexDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbTableFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUtil;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;

public final class UasDaoLdapGroupTbl
{

	//~ Static fields/initializers ---------------------------------------------

	/* 
	 *@SF-SEVILLA 
	 *02-may-2006 / antmaria
	 */
   public static final String	        TN			    = "IUSERLDAPGRPHDR";
	
   public static final DbColumnDef    CD_ID		    = new DbColumnDef("ID",
																		  DbDataType.LONG_INTEGER,
																		  false);
   public static final DbColumnDef    CD_LDAPGUID	    = new DbColumnDef("LDAPGUID",
																		  DbDataType.SHORT_TEXT,
																		  36,
																		  false);
   public static final DbColumnDef    CD_LDAPFULLNAME = new DbColumnDef("LDAPFULLNAME",
																		  DbDataType.SHORT_TEXT,
																		  254,
																		  false);
	
   public static final DbColumnDef    CD_TYPE         = new DbColumnDef("TYPE",
	      																  DbDataType.LONG_INTEGER,
		                                                                  false);
	
   public static final DbColumnDef [] ACD			    = { CD_ID, CD_LDAPGUID, CD_LDAPFULLNAME, CD_TYPE };
	
   public static final String		    ACN			    = DbUtil.getColumnNames(ACD);
	
	//~ Constructors -----------------------------------------------------------

   private UasDaoLdapGroupTbl(){}

	//~ Methods ----------------------------------------------------------------

   public static String getDefaultQual(int id)
   {

      return "WHERE "+CD_ID.getName()+"="+id;

   }
	
   public static String getGuidQual(String guid)
   {
      
      return "WHERE "+ CD_LDAPGUID.getName()+"='"+guid+"'";

   }
   
//	 **************************************************************************
	
   public static int selectId(DbConnection dbConn, String guid) throws Exception
   {  

      int     id;

      id = DbSelectFns.selectLongInteger(dbConn, TN, CD_ID.getName(), getGuidQual(guid));     

      return id;

   } 

   public static boolean guidExists(String guid, String entidad) throws Exception
   {
	   DbConnection dbConn = new DbConnection();
	   try{
		   dbConn.open(DBSessionManager.getSession());
		   return DbSelectFns.rowExists(dbConn, TN, getGuidQual(guid));
	   } finally {
		   dbConn.close();
	   }
   }
   
// **************************************************************************
   public static void createTable(DbConnection dbConn) throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "LDAPGUID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true);
      
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

	
}


// class
