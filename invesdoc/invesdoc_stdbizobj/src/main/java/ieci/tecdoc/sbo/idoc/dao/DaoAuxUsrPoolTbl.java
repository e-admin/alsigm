package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public class DaoAuxUsrPoolTbl 
{
	
   public static final String		   TN  		    = "UPIDOCAUXUSRPOOL";
	
   public static final DbColumnDef    CD_USERID	    = new DbColumnDef("USERID",
																		  DbDataType.LONG_INTEGER,
																		  false);   
   public static final DbColumnDef [] ACD		    = { CD_USERID };
   
   
   private DaoAuxUsrPoolTbl(){}
   
   public static String getDefaultQual(int userId)
   {
      String qual;
     
      qual = "WHERE "+CD_USERID.getName()+"="+userId;   
     
	  return qual;
   }
	
   public static void lockUser(int userId) throws Exception
   {
      String stmt;
	   
	  stmt = "update " + TN + " set " +  CD_USERID.getName() + " = " ;
	  stmt = stmt + CD_USERID.getName() + " " + getDefaultQual(userId);
	   
	   
	  DbUtil.executeStatement(stmt);
   }
	
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
