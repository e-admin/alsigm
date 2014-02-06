
package ieci.tecdoc.sbo.idoc.vldtable.std;

import java.util.HashMap;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.vldtable.base.*;


public final class VldBnoManager
{
   
   private static ThreadLocal m_cache = new ThreadLocal ();
   
   private VldBnoManager()
   {
   }
   
   public static VldTblToken loadValidationTable ( int tableId ) throws Exception
   {
      try{

         DbConnection.open(CfgMdoConfig.getDbConfig());

         VldTblToken vldToken = VldMdoVldTable.loadValidationTable(tableId) ;

         DbConnection.close();
         return vldToken;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }

   public static VldTblSearchResults executeQuery(VldTblToken vldToken) throws Exception
   {
     return executeQuery (vldToken, null, -1);
   }
   
   public static VldTblSearchResults executeQuery(VldTblToken vldToken, String hierarchicalDbTableName, int parentPk) throws Exception
   {
      try{

         DbConnection.open(CfgMdoConfig.getDbConfig());

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken, hierarchicalDbTableName, parentPk) ;

         DbConnection.close();
         return vldResults;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }
   
   public static VldTblSearchResults executeQuery(VldTblToken vldToken, VldTblSearchQuery searchQuery) throws Exception
   {
      try{

         DbConnection.open(CfgMdoConfig.getDbConfig());

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken,searchQuery) ;

         DbConnection.close();
         return vldResults;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }

   public static VldTblSearchResults executeQuery(VldTblToken vldToken, VldTblSearchQuery searchQuery, String hierarchicalDbTableName, int parentPk) throws Exception
   {
      try{

         DbConnection.open(CfgMdoConfig.getDbConfig());

         VldTblSearchResults vldResults = VldMdoVldTable.executeQuery(vldToken, searchQuery, hierarchicalDbTableName, parentPk) ;

         DbConnection.close();
         return vldResults;
      }
      catch (Exception e){
         DbConnection.ensureClose(e);
         return null;
      }
   }   
   
   public static VldTblData getValue(VldTblToken vldToken ,int pkValue) throws Exception
   {
      
      GetValueKey key = new GetValueKey (vldToken.getVInfo ().getId (), pkValue);
      VldTblData vldData = (VldTblData) getCacheValue (key);
      if (vldData == null) 
      {
	      try{
	
	         DbConnection.open(CfgMdoConfig.getDbConfig());
	
	         vldData = VldMdoVldTable.getValue(vldToken, pkValue) ;
	
	         DbConnection.close();
	         
	         setCacheValue (key, vldData);
	      }
	      catch (Exception e){
	         DbConnection.ensureClose(e);
	         //return null;
	         vldData = null;
	      }
      }
      
      return vldData;
   }

   public static Object mapValue(int tableID ,int vldTblType, Object value,int fromMappingTo) throws Exception
   {
      MapValueKey key = new MapValueKey (tableID, vldTblType, value, fromMappingTo);
      Object valueToReturn = getCacheValue (key);
      if (valueToReturn == null) 
      {
	      try{
	
	         DbConnection.open(CfgMdoConfig.getDbConfig());
	
	         valueToReturn = VldMdoVldTable.mapValue(tableID ,vldTblType ,value ,fromMappingTo) ;
	
	         DbConnection.close();

	         setCacheValue (key, valueToReturn);
	      }
	      catch (Exception e){
	         DbConnection.ensureClose(e);
	         //return null;
	         valueToReturn = null;
	      }
      }
      return valueToReturn;
   }

   public static int getVldDataPK(int vldTblId, Object data ,boolean isDbData) throws Exception
   {
      int pk = -1;
       
      GetVldDataPKKey key = new GetVldDataPKKey (vldTblId, data, isDbData);
      Integer iPk = (Integer) getCacheValue (key);
      if (iPk != null) {
         return iPk.intValue ();
      }
      
      
      try
      {

         DbConnection.open(CfgMdoConfig.getDbConfig());

         pk = VldMdoVldTable.getVldDataPK(vldTblId, data ,isDbData) ;

         DbConnection.close();
         
         setCacheValue (key, new Integer (pk));
         
         return pk;
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
         return -1;
      }
   }
   
   public static boolean checkHierarchicalValidation (int parent, int child, String tableName) throws Exception
   {
      boolean rc = false;
      
      try
      {

         DbConnection.open(CfgMdoConfig.getDbConfig());

         rc = VldMdoVldTable.checkHierarchicalValidation (parent, child, tableName);

         DbConnection.close();         
         
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);
      }      
      
      return rc;
   }
   
   public static void clearCache () {
      m_cache.set (null);
   }
   
   private static Object getCacheValue (Object key) {
	   HashMap map = (HashMap) m_cache.get ();
	   Object value;
	   if (map != null)
	   {
	      value = map.get (key);
	   } 
	   else
	   {
	      value = null;
	   }

	   return value;
   }
   
   private static void setCacheValue (Object key, Object value) {
 	   HashMap map = (HashMap) m_cache.get ();

 	   if (map == null)
 	   {
 	      map = new HashMap ();
 	      m_cache.set (map);
 	   } 

 	   map.put (key, value);
    }   
   
   
   private static class GetValueKey {
      private int m_vldTblId, m_pkValue;
      
      private GetValueKey (int vldTblId, int pkValue) {
         this.m_pkValue = pkValue;
         this.m_vldTblId = vldTblId;
      }
      
      
      /* (non-Javadoc)
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object obj)
      {
         if ( ! (obj instanceof GetValueKey)) {
            return false;
         }
         
         GetValueKey o2 = (GetValueKey) obj;
         
         return m_vldTblId == o2.m_vldTblId && m_pkValue == o2.m_pkValue;
      }
      
      /* (non-Javadoc)
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return  (m_pkValue + "@" + m_vldTblId).hashCode (); 
      }
   }
   
   private static class MapValueKey {
      private int m_tableID, m_vldTblType, m_fromMappingTo;
      private Object m_value;
      
      private MapValueKey (int tableID ,int vldTblType, Object value,int fromMappingTo) {
         this.m_fromMappingTo = fromMappingTo;
         this.m_tableID = tableID;
         this.m_value = value;
         this.m_vldTblType = vldTblType;
      }
      
      /* (non-Javadoc)
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object obj)
      {
         if ( ! (obj instanceof MapValueKey)) {
            return false;
         }
         
         MapValueKey o2 = (MapValueKey) obj;
         
         boolean eq = false;
         if (m_fromMappingTo == o2.m_fromMappingTo && m_tableID == o2.m_tableID && m_vldTblType == o2.m_vldTblType) 
         {
            if (m_value == null) 
            {
               if (o2.m_value == null)
               {
                  eq = true;
               }
            }
            else
            {
               eq = m_value.equals (o2.m_value);
            }
         }
         return eq;
      }
      
      /* (non-Javadoc)
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return  (m_tableID + "@" + m_vldTblType + "@" + m_fromMappingTo + "@" + m_value).hashCode (); 
      }      
      
   }
   
   
   private static class GetVldDataPKKey {
      private int m_vldTblId;
      private Object m_data;
      private boolean m_isDbData;
      
      GetVldDataPKKey (int vldTblId, Object data ,boolean isDbData) {
         m_vldTblId = vldTblId;
         m_data = data;
         m_isDbData = isDbData;
      }
      
      /* (non-Javadoc)
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object obj)
      {
         if ( ! (obj instanceof GetVldDataPKKey)) {
            return false;
         }
         
         GetVldDataPKKey o2 = (GetVldDataPKKey) obj;
         
         boolean eq = false;
         if (m_vldTblId == o2.m_vldTblId && m_isDbData == o2.m_isDbData) 
         {
            if (m_data == null) 
            {
               if (o2.m_data == null)
               {
                  eq = true;
               }
            }
            else
            {
               eq = m_data.equals (o2.m_data);
            }
         }
         return eq;
      }
      
      /* (non-Javadoc)
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return  (m_vldTblId + "@" + m_isDbData + "@" + m_data).hashCode (); 
      }   
   }
   
} // class
