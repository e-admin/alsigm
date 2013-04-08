
package ieci.tecdoc.sbo.idoc.datree;


import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public final class DATBnoManager
{
   
   private DATBnoManager()
   {
   }
   
   //********** árbol de directorios y archivadores *************
   
   public static String getRootNodeName(String entidad)
                        throws Exception
   {
      String name = null;
      
      DbConnection dbConn=new DbConnection();
      try
      {
         
    	  dbConn.open(DBSessionManager.getSession(entidad));	  

         name = DATManager.getRootNodeName(entidad);
         
         return name;
                                          
      }
      catch (Exception e)
      { 
         return null;
      }finally{
    	  dbConn.close();
      }
   }
   
   // accToken es null si no se quieren comprobar permisos  
   public static DATNodeList getRootChildren(AcsAccessToken accToken, String entidad)
                             throws Exception
   {   
      
      DATNodeList datChildren = null;
      
      DbConnection dbConn=new DbConnection();
      try
      {
         
    	  dbConn.open(DBSessionManager.getSession(entidad));	          

         datChildren = DATManager.getRootChildren(dbConn, accToken);         
         return datChildren;
                                          
      }
      catch (Exception e)
      {
         return null;
      }finally{
    	  dbConn.close();
      }

   }
   
// accToken es null si no se quieren comprobar permisos  
   public static DATNodeList getDirChildren(AcsAccessToken accToken, int dirId, String entidad)
                             throws Exception
   {   
      
      DATNodeList datChildren = null;
      
      DbConnection dbConn=new DbConnection();
      try
      {
         
    	  dbConn.open(DBSessionManager.getSession(entidad));	   

         datChildren = DATManager.getDirChildren(dbConn, accToken, dirId);
         
         return datChildren;
                                          
      }
      catch (Exception e)
      {
         return null;
      }finally{
    	  dbConn.close();
      }

   }

   
} // class
