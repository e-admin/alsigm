
package es.ieci.tecdoc.isicres.admin.sbo.idoc.vldtable.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnectionConfig;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.vldtable.base.*;


public final class VldBnoManagerEx
{
   
   private VldBnoManagerEx()
   {
   }
   
   public static VldTblToken loadValidationTable (DbConnectionConfig dbConConfig, int tableId, String entidad)
                             throws Exception
   {

	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

         VldTblToken vldToken = VldMdoVldTable.loadValidationTable(dbConn, tableId) ;

         return vldToken;
      }
      catch (Exception e)
      {
         return null;
      }finally{
    	  dbConn.close();
      }
   }

   public static VldTblSearchResults executeQuery(DbConnectionConfig dbConConfig,
                                                  VldTblToken        vldToken, String entidad)
                                     throws Exception
   {

	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken, entidad) ;

         return vldResults;
      }
      catch (Exception e){
         return null;
      }finally{
    	  dbConn.close();
      }
   }

   public static VldTblSearchResults executeQuery(DbConnectionConfig dbConConfig,
                                                  VldTblToken vldToken, 
                                                  VldTblSearchQuery searchQuery, String entidad) throws Exception
   {

	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken,searchQuery, entidad) ;

         return vldResults;
      }
      catch (Exception e){
         return null;
      }finally{
    	  dbConn.close();
      }
   }


   public static VldTblData getValue(DbConnectionConfig dbConConfig, VldTblToken vldToken ,int pkValue, String entidad) throws Exception
   {

	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

         VldTblData vldData = VldMdoVldTable.getValue(vldToken, pkValue, entidad) ;

         return vldData;
      }
      catch (Exception e){
         return null;
      }finally{
    	  dbConn.close();
      }
   }

   public static Object mapValue(DbConnectionConfig dbConConfig, int tableID ,int vldTblType, 
                                 Object value,int fromMappingTo, String entidad) throws Exception
   {

	   DbConnection dbConn=new DbConnection();
	   try{
	   	dbConn.open(DBSessionManager.getSession());

         Object valueToReturn = VldMdoVldTable.mapValue(tableID ,vldTblType ,value ,fromMappingTo, entidad) ;

         return valueToReturn;
      }
      catch (Exception e){
         return null;
      }finally{
    	  dbConn.close();
      }
   }

   public static boolean isValidData(DbConnectionConfig dbConConfig, int vldTblId, Object data ,
                                     boolean isDbData, String entidad) throws Exception
   {
      boolean isValid = false;
       
      DbConnection dbConn=new DbConnection();
      try{
      	dbConn.open(DBSessionManager.getSession());

         isValid = VldMdoVldTable.getVldDataPK(dbConn, vldTblId, data ,isDbData, entidad) != -1;

         
         return isValid;
      }
      catch (Exception e)
      {
         return false;
      }finally{
    	  dbConn.close();
      }
   }
   
} // class
