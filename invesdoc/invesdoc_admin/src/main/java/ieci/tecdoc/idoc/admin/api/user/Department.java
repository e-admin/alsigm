package ieci.tecdoc.idoc.admin.api.user;

import java.util.Date;

/**
 * Proporciona toda la funcionalidad necesaria para manejar departamentos
 * de usuarios de invesDoc.
 */
public interface Department
{
	/**
    * Carga un departamento invesDoc.
    * 
    * @param deptId Identificador del departamento.
    * @throws Exception Si se produce algún error al leer la información del 
    * departamento.
    */

   public void load(int deptId) throws Exception;

   /**
    * Carga un departamento invesDoc.
    * 
    * @param deptName Nombre del departamento.
    * @throws Exception Si se produce algún error al leer la información del 
    * departamento.
    */

   public void load(String deptName) throws Exception;

   /**
    * Guarda el departamento. Se utiliza tanto para inserciones como para 
    * actualizaciones.
    * 
    * @throws Exception Si se produce algún error al guardar. Por ejemplo, 
    * el departamento ya existe.
    */

   public void store() throws Exception;

   /**
    * Elimina el departamento.
    * 
    * @throws Exception Si se produce algún error al eliminar.
    */

   public void delete() throws Exception;
   
   /**
    * Obtiene el identificador de departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getId();
   
   /**
    * Obtiene el nombre del departamento.
    *  
    * @return El nombre mencionado.
    */
   public String getName();
   
   /**
    * Establece el nombre del departamento.
    * 
    * @param name Nombre del departamento.
    */
   public void setName(String name);
   
   /**
    * Obtiene el identificador del padre del departamento.
    *  
    * @return El nombre mencionado.
    */
   public int getParentId();
   
   /**
    * Obtiene la lista de usuarios (identificadores y nombres) que pueden ser
    * administradores de este departamento.
    * 
    * @return La lista mencionada. 
    */    
   public BasicUsers getAdminUsers() throws Exception;
   
   /**
    * Establece el identificador del padre del departamento.
    * 
    * @param parentId El identificador del padre del departamento.
    */
   public void setParentId(int parentId);
   
   /**
    * Obtiene el identificador del administrador del departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getManagerId();
   
   /**
    * Establece el identificador del administrador del departamento.
    * 
    * @param managerId El identificador del administrador.
    */
   public void setManagerId(int managerId);
   
   /**
    * Obtiene el identificador del tipo de departamento.
    * 
    * @return El identificador mencionado.
    */
   public int getType();
   
   /**
    * Obtiene la descripción del departamento.
    * 
    * @return El nombre mencionado.
    */
   public String getDescription();
   
   /**
    * Establece la descripción del departamento.
    * 
    * @param description La descripción del grupo.
    */
   public void setDescription(String description);
   
   /**
	 * Obtiene el identificador del usuario que ha creado el departamento. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId();
	
	/**
    * Obtiene la fecha de creación del departamento.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate();
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el departamento.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId();
   
   /**
    * Obtiene la fecha de actualización del departamento.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate();
   
   /**
    * Devuelve la lista de permisos del departamento.
    * 
    * @return La lista mencionada.
    */
   
   public Permissions getPermissions();
   
   
   /**
    * Devuelve la lista de usuarios del departamento.
    * 
    * @param id Identificador del departamento.
    * @return Los usuarios del departamento.
    */
   public Users getUsersByDept(int id) throws Exception;
   
   /**
    * Obtiene la información del departamento en formato XML.
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
