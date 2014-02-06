package ieci.tecdoc.idoc.admin.api.volume;

import java.util.Date;

/**
 * Proporciona toda la funcionalidad necesaria para manejar listas de
 * volúmenes en invesDoc.
 */
public interface VolumeList
{	
	/**
	 * Carga una lista de volúmenes de invesDoc.
	 * 
	 * @param listId Identificador de la lista.	 
	 * @throws Exception Si se produce algún error al leer la información de 
	 *         la lista.
	 */
	public void load(int listId) throws Exception;
	
	/**
	 * Guarda la lista. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception Si se produce algún error al guardar. Por ejemplo, 
	 * la lista ya existe.
	 */
	public void store() throws Exception;
	
	/**
	 * Elimina la lista.
	 * 
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void delete() throws Exception;
	
	/**
	 * Añade un volumen a la lista de volúmenes.
	 * 
	 * @param volId El identificador del volumen.
	 * @throws Exception Si se produce algún error al añadir.
	 */
	public void add(int volId) throws Exception;
	
	/**
	 * Elimina un volumen de la lista de volúmenes.
	 * 
	 * @param volId El identificador del volumen.
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void deleteVolume(int volId) throws Exception;
	
	/**
	 * Establece el orden de un volumen detro de una lista de volúmenes
	 * 
	 * @param listId Idenficador de la lista de volúmenes
	 * @param volId  Identificador del volumen
	 * @param sortOrder Orden a establece
	 * @throws Exception Si se produce error
	 */
	public void setVolumeSortOrder(int listId, int volId, int sortOrder) throws Exception;
	
	/**
	 * Obtiene el orden del volumen dentro de una lista de volúmenes
	 * 
	 * @param listId Identificador de la lista de volúmenes
	 * @param volId Identificador del volumen
	 * @return  Orden del volument dentro de la lista de volúmenes
	 * @throws Exception Si se produce error
	 */
	public int getVolumeSortOrder(int listId, int volId) throws Exception;
	
	/**
	 * Obtine el identificador de la lista de volúmenes
	 * 
	 * @return El identificador mencionado
	 */
	public int getId();
	
	/**
    * Obtiene el nombre de la lista de volúmenes.
    *
    * @return El nombre mencionado.
    */
	public String getName();
	/**
	 * Establece el nombre de la lista de volúmenes.
	 * 
	 * @param name Nombre de la lista.
	 */
	public void setName(String name);
	
	/**
    * Obtiene los flags de la lista de volúmenes.
    *
    * @return El dato mencionado.
    */
	public int getFlags();
	
	/**
    * Obtiene el estado de la lista de volúmenes.
    *
    * @return El dato mencionado.
    */
	public int getState();
	
	/**
	 * Establece el estado de la lista de volúmenes.
	 * 
	 * @param state El estado de la lista.
	 */
	public void setState(int state);
	
	/**
	 * Obtiene los comentarios de la lista de volúmenes.
	 * 
	 * @return El dato mencionado.
	 */
	public String getRemarks();
	
	/**
	 * Establece los comentarios de la lista de volúmenes.
	 * 
	 * @param remarks Los comentarios de la lista.
	 */
	public void setRemarks(String remarks);
	
	/**
	 * Obtiene el identificador del usuario que ha creado 
	 * la lista de volúmenes. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId();
	
	/**
    * Obtiene la fecha de creación de la lista de volúmenes.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate();
   
   /**
    * Obtiene el identificador del usuario que ha actualizado 
    * la lista de volúmenes.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId();
   
   /**
    * Obtiene la fecha de actualización de la lista de volúmenes.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate();
   
   /**
    * Obtiene una lista de identificadores de volúmenes.
    * No es necesario cargar el objeto antes.
    * 
    * @param id Identificador de la lista de volúmenes.
    * @return La lista mencionada.
    */
   public Volumes getVolumes(int id) throws Exception;
   
   /**
	 * Obtiene la información de la lista de volúmenes en formato XML.
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
