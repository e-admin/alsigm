package ieci.tecdoc.idoc.admin.api.system;

import ieci.tecdoc.sbo.config.CfgFtsConfig;
import ieci.tecdoc.sbo.config.CfgLdapConfig;

/**
 * Proporciona toda la funcionalidad necesaria para configuración del sistema. 
 */

public interface SystemCfg
{
    
    
    
    public void updateMisc() throws Exception;
    
    /**
     * Abre la Conexión a la base de datos
     * @throws Exception Si se produce algún error
     */
    public void connectDataBase()throws Exception;
    
    public CfgFtsConfig getFtsConfig() throws Exception;
    
    public CfgLdapConfig getLdapConfig()throws Exception;
   /**
    * Obtiene si existe o no configuración de motor documental
    * 
    * @return  true / false
    * @throws Exception
    */
   public boolean hasFtsConfig() throws Exception;
   
   
   /**
    * Obtiene el nombre raíz del árbol de archivadores
    * 
    * @return El nombre mencionado
    * @throws Exception
    */
   public String getRootNameArchs() throws Exception;
   
   
   public void setRootNameArchs(String rootName) throws Exception;
   
   /**
    * Inicializa la fuente de datos invesDoc
    * 
    * @throws Exception
    */
   public void initDataBase() throws Exception;
   
   /**
    * Desbloquea al usuario Syssuperuser
    * 
    * @throws Exception
    */
   public void unLockSyssuperuser() throws Exception;
   
   /**
    * Cierra la conexión a la Base de datos
    * @throws Exception Si se produce algún error
    */
   public void closeDataBase()throws Exception;
   
   /**
    * Determina si el SYSSUPERUSER está bloqueado o no
    * @return true si SYSSUPERUSER está bloqueado
    */
   public boolean isLockSyssuperuser();
   
   
   /**
    * Inicializa las tablas principales de W@rda. Se necesita una conexión abierta a BD
    * @throws Exception Si se produce algún error en la creación
    */
   public void setupDocDb() throws Exception;
   
   /**
    * Inicializa las tablas de usuarios de W@rda. Se necesita una conexión abierta a BD
    * @throws Exception Si se produce algún error en la creación
    */
   public void setupUserDb() throws Exception;
   
   /**
    * Inicializa las tablas de volúmenes de W@rda. Se necesita una conexión abierta a BD
    * @throws Exception Si se produce algún error en la creación
    */
   public void setupVolDb() throws Exception;
}
