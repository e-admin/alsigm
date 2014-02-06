
package ieci.tecdoc.sbo.idoc.datree;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.acs.base.AcsAccessToken;

public final class DATBnoManager
{
   
   private DATBnoManager()
   {
   }
   
   //********** árbol de directorios y archivadores *************
   
   public static String getRootNodeName()
                        throws Exception
   {
      String name = null;
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());            

         name = DATManager.getRootNodeName();

         DbConnection.close();
         
         return name;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }   
   }
   
   // accToken es null si no se quieren comprobar permisos  
   public static DATNodeList getRootChildren(AcsAccessToken accToken)
                             throws Exception
   {   
      
      DATNodeList datChildren = null;
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());            

         datChildren = DATManager.getRootChildren(accToken);

         DbConnection.close();
         
         return datChildren;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }   

   }
   
// accToken es null si no se quieren comprobar permisos  
   public static DATNodeList getDirChildren(AcsAccessToken accToken, int dirId)
                             throws Exception
   {   
      
      DATNodeList datChildren = null;
      
      try
      {
         
         DbConnection.open(CfgMdoConfig.getDbConfig());            

         datChildren = DATManager.getDirChildren(accToken, dirId);

         DbConnection.close();
         
         return datChildren;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }   

   }

   
} // class
