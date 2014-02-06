
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.documental.search.DocumentalMdoSearch;
import ieci.tecdoc.sbo.idoc.documental.search.DocumentalSearchResult;


/**
 * Gestor de búsquedas documentales
 */


public final class DocumentalSearch
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
   public DocumentalSearch() throws Exception
   {
      m_dbConnConfig = null;
      m_configDir    = null;   
   }
   
   /**
    * Construtor
    * @param configDir configuración de la base de datos invesdoc
    * @throws Exception
    */
   public DocumentalSearch(String configDir) throws Exception
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
    * Realiza una búsqueda documental
    * @param acs este parámetro es opcional.
    * Contiene información del usuario invesdoc que realiza la búsqueda. Si es
    * pasado este parámetro, sólo se busca en los archivadores donde tenga permiso
    * y además sólo se obtienen carpetas que, además de cumplir los filtros de la
    * búsqueda, sean visibles para el usuario invesdoc al cual se refiere el parámetro acs.
    * @param query contiene los filtros que se van a aplicar a la búsqueda
    * @return referencia a un objeto de tipo DocumentalSearchResult que cotiene los resultados de la 
    * búsqueda
    * @throws Exception si se produce algún error en la búsqueda de carpetas
    * @see AcsAccessObject
    * @see DocumentalSearchQueryObject
    * @see DocumentalSearchResult 
    */
   public DocumentalSearchResult executeQuery(AcsAccessObject acs,
                                          DocumentalSearchQueryObject query)
                             throws Exception
   {
      
      DocumentalSearchResult rs = null;

         
      if (acs == null)
         rs = DocumentalMdoSearch.executeQuery(null,
                  query.getDocumentalSearchQuery (), getDbConfig ());
      else
         rs = DocumentalMdoSearch.executeQuery(acs.getAccessToken(),
                  query.getDocumentalSearchQuery (), getDbConfig ());

      return rs;
                                          

            
   }

   
} // class
