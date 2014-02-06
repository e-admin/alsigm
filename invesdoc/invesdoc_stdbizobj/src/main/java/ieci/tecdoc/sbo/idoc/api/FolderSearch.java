
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFlds;
import ieci.tecdoc.sbo.idoc.folder.search.FolderSearchResult;
import ieci.tecdoc.sbo.idoc.api.FolderSearchQueryObject;
import ieci.tecdoc.sbo.idoc.folder.search.FolderMdoSearch;

/**
 * Gestor de búsquedas de carpetas
 */


public final class FolderSearch
{
   /** 
    * Configuración de la base de datos donde se encuentra el modelo
    * de datos de invesdoc
    */
   DbConnectionConfig m_dbConnConfig;
   
   /** 
    * Directorio donde se encuentra el fichero con la configuración de
    * la base de datos invesdoc
    */
   String             m_configDir;
   
   /**
    * Constructor
    * @throws Exception
    */
   public FolderSearch() throws Exception
   {
      m_dbConnConfig = null;
      m_configDir    = null;   
   }
   
   /**
    * Construtor
    * @param configDir configuración de la base de datos invesdoc
    * @throws Exception
    */
   public FolderSearch(String configDir) throws Exception
   {
      m_dbConnConfig = null;
      m_configDir    = configDir;       
   }

   /**
    * Devuelve la configuración de la base de datos donde se encuentran
    * usuarios invesdoc. 
    * @return
    * @throws Exception
    */   
   private DbConnectionConfig getDbConfig() throws Exception
   {
      if (m_dbConnConfig == null)
      {
         if (m_configDir == null)
         {
            m_dbConnConfig = CfgMdoConfig.getDbConfig();
         }
         else
         {
            m_dbConnConfig = CfgMdoConfig.getDbConfig(m_configDir);
         }
      }
      
      return  m_dbConnConfig;
   }
   
   /**
    * Establece una configuración de base de datos. Esta configuración
    * corresponderá con la base de datos invesdoc.
    * @param dbConnConfig Configuración de base de datos
    * @throws Exception
    */
   public void setConnectionConfig(DbConnectionConfig dbConnConfig)
               throws Exception
   {
      m_dbConnConfig = dbConnConfig;
   }
   
   /**
    * Realiza una búsqueda de carpetas dentro de un archivador. 
    * @param acs este parámetro es opcional.
    * Contiene información del usuario invesdoc que realiza la búsqueda. Si es
    * pasado este parámetro, sólo se obtienen carpetas que, además de cumplir los filtros de la
    * búsqueda, sean visibles para el usuario invesdoc al cual se refiere el parámetro acs.
    * @param arch este parámetro contiene información del archivador sobre el cual se van a buscar 
    * carpetas. 
    * @param query contiene los filtros que se van a aplicar a la búsqueda
    * @return referencia a un objeto de tipo FolderSearchResult que cotiene los resultados de la 
    * búsqueda
    * @throws Exception si se produce algún error en la búsqueda de carpetas
    * @see AcsAccessObject
    * @see ArchiveObject
    * @see FolderSearchQueryObject
    * @see FolderSearchResult 
    */
   public FolderSearchResult executeQuery(AcsAccessObject acs,
                                          ArchiveObject arch,
                                          FolderSearchQueryObject query)
                             throws Exception
   {
      
      FolderSearchResult rs = null;
      
      try
      {
         
         DbConnection.open(getDbConfig());   
         
         if (acs == null)
            rs = FolderMdoSearch.executeQuery(null,
                                              arch.getArchiveToken(),
                                              query.getFolderSearchQuery());
         else
            rs = FolderMdoSearch.executeQuery(acs.getAccessToken(),
                                              arch.getArchiveToken(),
                                              query.getFolderSearchQuery());

         DbConnection.close();
         
         return rs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }   
            
   }

   /**
    * Realiza una búsqueda de carpetas dentro de un archivador. 
    * @param acs este parámetro es opcional.
    * Contiene información del usuario invesdoc que realiza la búsqueda. Si es
    * pasado este parámetro, sólo se obtienen carpetas que, además de cumplir los filtros de la
    * búsqueda, sean visibles para el usuario invesdoc al cual se refiere el parámetro acs.
    * @param arch este parámetro contiene información del archivador sobre el cual se van a buscar 
    * carpetas. 
    * @param qual condición sql que define los filtros de búsqueda de carpetas
    * @return referencia a un objeto de tipo FolderSearchResult que cotiene los resultados de la 
    * búsqueda
    * @throws Exception si se produce algún error en la búsqueda de carpetas
    * @see AcsAccessObject
    * @see ArchiveObject
    * @see FolderSearchResult 
    */
   public FolderSearchResult executeQuery(AcsAccessObject acs,
                                          ArchiveObject arch,
                                          String qual)
                             throws Exception
   {
      
      FolderSearchResult rs = null;
      
      try
      {
         
         DbConnection.open(getDbConfig());   
         
         if (acs == null)
            rs = FolderMdoSearch.executeQuery(null,
                                              arch.getArchiveToken(),
                                              qual);
         else
            rs = FolderMdoSearch.executeQuery(acs.getAccessToken(),
                                              arch.getArchiveToken(),
                                              qual);

         DbConnection.close();
         
         return rs;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }   
            
   }
   
   /**
    * Devuelve los valores de los campos asociados a la carpeta i-ésima de una colección de 
    * carpetas obtenida como resultado de una búsqueda dentro de un archivador.
    * @param arch este parámetro contiene información del archivador al cual pertenece
    * la carpeta
    * @param rs resultado de una búsqueda de carpetas sobre un archivador
    * @param idx índice iésimo de la carpeta sobre la cual obtener los valores de sus campos 
    * @return referencia a un objeto de tipo FolderFieldObjects que contiene los valores de los
    * campos de la carpeta
    * @throws Exception si se produce algún error en la obtención de los valores de los campos
    * de una carpeta
    * @see FolderFieldObjects
    */
   public FolderFieldObjects getFolderValues(ArchiveObject arch, FolderSearchResult rs,
                                             int idx)
                             throws Exception
   {
      FolderTokenFlds    values = null;
      FolderFieldObjects flds = null;
      
      try
      {
         
         DbConnection.open(getDbConfig());            

         values = FolderMdoSearch.getFolderValues(arch.getArchiveToken(), rs, idx);

         DbConnection.close();

         flds = new FolderFieldObjects(values);
         
         return flds;
                                          
      }
      catch (Exception e)
      {
         DbConnection.ensureClose(e);  
         return null;
      }   
   }
   
} // class
