
package ieci.tecdoc.idoc.admin.api.archive;

import java.util.Date;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;

/**
 * Proporciona toda la funcionalidad necesaria para manejar un archivador. 
 */

public interface Archive 
{
      
   /**
    * Carga un archivador.
    * 
    * @param id Identificador del archivador.
    * @throws Exception Si se produce algún error al leer la información del 
    * archivador.
    */

   public void load(int id) throws Exception;
   
   
   /**
    * Crea el archivador.
    * 
    * @throws Exception Si se produce algún error al crear. Por ejemplo, 
    * el archivador ya existe.
    */

   public void create() throws Exception;
   
   /**
    * Elimina el archivador cargado    
    * 
    * @throws Exception Si se produce algún error al eliminar
    */
   public void delete() throws Exception;
   
   /**
    * Elimina el archivador indicado
    * 
    * @param archiveId Identificador del archivador 
    * @throws Exception
    */
   public void delete(int archiveId) throws Exception;
      
   /**
    * Actualiza la información del archivador. 
    * 
    * @param updInfo Estructura con la totalidad de la información
    * @throws Exception Si se produce algún error al actualizar
    */   
   public void update(ArchiveUpdInfo updInfo) throws Exception;
   
   
   /**
    * Actualiza el padre para el archivador indicado
    * no hace falta que se realice una carga del archivador.
    * 
    * @param parentId Identificador del padre
    * @param archiveId Identificador del archivador
    * @throws Exception Si se produce algún error al actualizar
    */
   public void updateParentId(int parentId, int archiveId) throws Exception;
   
    /**
    * Indica si se está trabando con usuarios de un repositorio Ldap (true) o
    * no (false).
    *
    * @param ldap Si se trata de usuarios Ldap o no.
    */
    
   public void setLdap(boolean ldap);
   

   /**
    * Obtiene el identificador del archivador.
    *
    * @return El identificador mencionado.
    */
   public int getId();
   
   /**
    * Establece el identificador del archivador.
    *
    * @param name El identificador del archivador.
    */
   public void setId(int archId);
   
   
   /**
    * Obtiene el nombre del archivador.
    *
    * @return El nombre mencionado.
    */    
   public String getName();
   
   /**
    * Establece el nombre del archivador.
    *
    * @param name El nombre mencionado.
    */    
   public void setName(String name);
   
   /**
    * Obtiene el identificador del directorio padre del archivador cargado
    * 
    * @return El identificador del directorio padre
    */
   public int getParentId();
   
   /**
    * Obtiene el identificador del directorio padre del archivador
    * 
    * @param archiveId Identificador del archivador
    * 
    * @return El identificador del directorio padre
    * @throws Exception
    */
   public int getParentId(int archiveId) throws Exception;

   /**
    * Obtiene la descripción del archivador.
    *
    * @return La descripción mencionada.
    */    
   public String getRemarks();
   
   
   /**
    * Establece la descripción del archivador.
    *
    * @param remarks La descripción mencionada.
    */
    
   public void setRemarks(String remarks);
   
   /**
    * Obtiene el identificador del administrador del archivador
    * 
    * @return El identificador mencionado.
    */
   public int getAdminUserId();
   
      
   /**
    * Obtiene si hay búsqueda en contenido de fichero para el archivador
    * 
    * @return true / false
    */
   public boolean isFtsInContents();
   
   
   /**
    * Estable la existencia o no de búsqueda en contenido de fichero para
    * el archivador
    * 
    * @param ftsInContents (true /false)
    */
   public void setFtsInContents(boolean ftsInContents);
   
   
   /**
    * Obtiene el tipo de acceso del archivador.
    *
    * @return El tipo de acceso mencionado.
    */
    
   public int getAccessType();
   
   
   /**
    * Obtiene el identificador de acceso del archivador.
    * 
    * @return El identificador mencionado.
    */   
   public int getAcsId();
   
   
   /**
    * Obtiene el identificador del usuario que ha creado del archivador.
    *
    * @return El identificador mencionado.
    */    
   public int getCreatorId();
   
   /**
    * Obtiene la fecha de creación del archivador.
    *
    * @return La fecha mencionada.
    */    
   public Date getCreationDate();
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el archivador.
    *
    * @return El identificador mencionado.
    */    
   public int getUpdaterId();
   
   /**
    * Obtiene la fecha de actualización del archivador.
    *
    * @return La fecha mencionada.
    */    
   public Date getUpdateDate();
   
   
   /**
    * Establece información referente a la definición de campos
    * del archivador
    *
    *  @param Estructura con la información mencionada.
    */
   public void setFldsDef(ArchiveFlds fldsArch);
	
   
   /**
    * Obtiene información referente a la definición de campos 
    * del archivador.
    * 
    * @return Estructura con la información mencionada.
    */
   public ArchiveFlds getFldsDef();
   
	
   /**
    * Establece información referente a la definición de índices
    * del archivador.
    *
    * @param Estructura con la información mencionada. 
    */   
	public void setIdxsDef(ArchiveIdxs idxsArch);
	
	
	/**
	 * Obtiene información referente a la definición de índices del
	 * archivador.
	 * 
	 * @return Estructura con la información mencionada.
	 */
	public ArchiveIdxs getIdxsDef();
	
		
	/**
    * Establece información referente a lista de volúmenes
    * y título de carpeta del archivador.
    *
    * @param Estructura con la información mencionada.
    */
	public void setMiscDef(ArchiveMisc miscArch);
	

	/**
	 * Obtiene información referente a lista de volúmenes
	 * y título de carpeta del archivador.
	 * 
	 * @return Estructura con la información mencionada.
	 */
   public ArchiveMisc getMiscDef();
   
   
   /**
    * Obtiene la lista de usuarios (identificadores y nombres) que pueden ser
    * administradores de este archivador.
    * 
    * @return La lista mencionada. 
    */    
   public BasicUsers getAdminUsers();
   
      
   /**
    * Obtiene si existen carpetas dadas de alta en el archivador
    * 
    * @return true / false
    * @throws Exception
    */
   public boolean existsFdrsInArch() throws Exception;
   
   /**
    * Obtiene la información del directorio en formato XML.
    *
    * @return La información mencionada.
    */
   public String toXML();
   

   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */
	public String toString();
	    
}