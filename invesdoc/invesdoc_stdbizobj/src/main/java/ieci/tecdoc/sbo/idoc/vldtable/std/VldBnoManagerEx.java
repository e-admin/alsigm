
package ieci.tecdoc.sbo.idoc.vldtable.std;

import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.idoc.vldtable.base.*;


public final class VldBnoManagerEx
{
   
   private VldBnoManagerEx()
   {
   }
   
   public static VldTblToken loadValidationTable (DbConnectionConfig dbConConfig, int tableId )
                             throws Exception
   {
      try
      {

         DbConnection.open(dbConConfig);

         VldTblToken vldToken = VldMdoVldTable.loadValidationTable(tableId) ;

         DbConnection.close();
         return vldToken;
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         return null;
      }
   }

   public static VldTblSearchResults executeQuery(DbConnectionConfig dbConConfig,
                                                  VldTblToken        vldToken)
                                     throws Exception
   {
      try{

         DbConnection.open(dbConConfig);

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken) ;

         DbConnection.close();
         return vldResults;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }

   public static VldTblSearchResults executeQuery(DbConnectionConfig dbConConfig,
                                                  VldTblToken vldToken, 
                                                  VldTblSearchQuery searchQuery) throws Exception
   {
      try{

         DbConnection.open(dbConConfig);

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken,searchQuery) ;

         DbConnection.close();
         return vldResults;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }


   public static VldTblData getValue(DbConnectionConfig dbConConfig, VldTblToken vldToken ,int pkValue) throws Exception
   {
      try{

         DbConnection.open(dbConConfig);

         VldTblData vldData = VldMdoVldTable.getValue(vldToken, pkValue) ;

         DbConnection.close();
         return vldData;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }

   public static Object mapValue(DbConnectionConfig dbConConfig, int tableID ,int vldTblType, 
                                 Object value,int fromMappingTo) throws Exception
   {
      try{

         DbConnection.open(dbConConfig);

         Object valueToReturn = VldMdoVldTable.mapValue(tableID ,vldTblType ,value ,fromMappingTo) ;

         DbConnection.close();
         return valueToReturn;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }

   public static boolean isValidData(DbConnectionConfig dbConConfig, int vldTblId, Object data ,
                                     boolean isDbData) throws Exception
   {
      boolean isValid = false;
       
      try
      {

         DbConnection.open(dbConConfig);

         isValid = VldMdoVldTable.getVldDataPK(vldTblId, data ,isDbData) != -1;

         DbConnection.close();
         
         return isValid;
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         return false;
      }
   }
   
} // class
