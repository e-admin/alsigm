package ieci.tecdoc.sbo.uas.ldap;

import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbDataType;
import ieci.tecdoc.core.db.DbIndexDef;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.db.DbTableFns;
import ieci.tecdoc.core.db.DbUtil;


public final class UasDaoLdapGroupTbl
{

	//~ Static fields/initializers ---------------------------------------------

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
	
   public static int selectId(String guid) throws Exception
   {  

      int     id;

      id = DbSelectFns.selectLongInteger(TN, CD_ID.getName(), getGuidQual(guid));     

      return id;

   } 

   public static boolean guidExists(String guid) throws Exception
   {
      return DbSelectFns.rowExists(TN, getGuidQual(guid));
   }
   
// **************************************************************************
   public static void createTable() throws Exception
   {
      String indexName,indexName2;
      String colNamesIndex,colNamesIndex2;
      DbIndexDef indexDef,indexDef2;
      
      indexName = TN + "1";
      colNamesIndex = "ID";
      indexName2 = TN + "2";
      colNamesIndex2 = "LDAPGUID";
      
      indexDef= new DbIndexDef(indexName,colNamesIndex,true, false);
      indexDef2= new DbIndexDef(indexName2,colNamesIndex2,true, false);
      
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
      dropIndex(TN,indexName);
         
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


// class
