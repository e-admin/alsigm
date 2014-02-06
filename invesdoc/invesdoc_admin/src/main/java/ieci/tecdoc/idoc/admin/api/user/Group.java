package ieci.tecdoc.idoc.admin.api.user;

import java.util.Date;

/**
 * Proporciona toda la funcionalidad necesaria para manejar grupos invesDoc.
 */

public interface Group
{
	/**
    * Carga un grupo invesDoc.
    * 
    * @param groupId Identificador del grupo.
    * @throws Exception Si se produce algún error al leer la información del 
    * grupo.
    */

   public void load(int groupId) throws Exception;
   
   /**
    * Carga un grupo invesDoc.
    * 
    * @param groupName Nombre del grupo.
    * @throws Exception Si se produce algún error al leer la información del 
    * grupo.
    */

   public void load(String  groupName) throws Exception;

   /**
    * Guarda el grupo. Se utiliza tanto para inserciones como para 
    * actualizaciones.
    * 
    * @throws Exception Si se produce algún error al guardar. Por ejemplo, 
    * el grupo ya existe.
    */

   public void store() throws Exception;

   /**
    * Elimina el grupo.
    * 
    * @throws Exception Si se produce algún error al eliminar.
    */

   public void delete() throws Exception;
   
   /**
    * Obtiene el identificador de grupo.
    * 
    * @return El identificador mencionado.
    */
   public int getId();
   
   /**
    * Obtiene el nombre del grupo.
    *  
    * @return El nombre mencionado.
    */
   public String getName();
   
   /**
    * Obtiene la lista de usuarios (identificadores y nombres) que pueden ser
    * administradores de este grupo.
    * 
    * @return La lista mencionada. 
    */    
   public BasicUsers getAdminUsers() throws Exception;
   
   /**
    * Establece el nombre del grupo.
    * 
    * @param name Nombre del grupo.
    */
   public void setName(String name);
   
   /**
    * Obtiene el identificador del administrador del gupo.
    * 
    * @return El identificador mencionado.
    */
   public int getManagerId();
   
   /**
    * Establece el identificador del administrador del gupo.
    * 
    * @param managerId El identificador del administrador.
    */
   public void setManagerId(int managerId);
   
   /**
    * Obtiene el identificador del tipo de grupo.
    * 
    * @return El identificador mencionado.
    */
   public int getType();
   
   /**
    * Obtiene la descripción del grupo.
    * 
    * @return El nombre mencionado.
    */
   public String getDescription();
   
   /**
    * Establece la descripción del grupo.
    * 
    * @param description La descripción del grupo.
    */
   public void setDescription(String description);
   
   /**
	 * Obtiene el identificador del usuario que ha creado el grupo. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId();
	
	/**
    * Obtiene la fecha de creación del grupo.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate();
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el grupo.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId();
   
   /**
    * Obtiene la fecha de actualización del grupo.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate();
   
   /**
    * Devuelve la lista de permisos del grupo.
    * 
    * @return La lista mencionada.
    */
   
   public Permissions getPermissions();
   
   /**
    * Devuelve la lista de usuarios del grupo.
    * No es necesario cargar antes la clase Group.
    * 
    * @param id Identificador del grupo.
    * @return Los usuarios del grupo.
    */
   
   public Users getUsersByGroup(int id) throws Exception;
   
   /**
    * Obtiene la información del grupo en formato XML.
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
