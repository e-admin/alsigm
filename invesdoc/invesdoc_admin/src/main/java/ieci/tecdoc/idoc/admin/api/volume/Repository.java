package ieci.tecdoc.idoc.admin.api.volume;

import java.util.Date;

/**
 * Proporciona toda la funcionalidad necesaria para manejar repositorios de
 * volúmenes en invesDoc.
 */
public interface Repository
{	
	/**
	 * Carga un repositorio de invesDoc.
	 * 
	 * @param repositoryId Identificador del repositorio.
	 * @throws Exception Si se produce algún error al leer la información del
	 *            repositorio.
	 */
	public void load(int repositoryId) throws Exception;

	/**
	 * Guarda el repositorio. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception Si se produce algún error al guardar. Por ejemplo, el
	 *            repositorio ya existe.
	 */
	public void store() throws Exception;

	/**
	 * Elimina el repositorio.
	 * 
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void delete() throws Exception;
	
	/**
	 * Obtiene el identificador del repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getId();
	
	/**
    * Obtiene el nombre del repositorio.
    *
    * @return El nombre mencionado.
    */
   public String getName();
	
	/**
	 * Establece el nombre del repositorio.
	 * 
	 * @param name El nombre del repositorio.
	 */
	public void setName(String name);
	
	/**
	 * Obtiene el identificador del tipo de repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getType();
	
	/**
	 * Establece el tipo de repositorio.
	 * 
	 * @param type El tipo de repositorio.
	 */
	public void setType(int type);
	
	/**
	 * Obtiene los comentarios del repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getRemarks();
	
	/**
	 * Establece los comentarios del repositorio.
	 * 
	 * @param remarks Los comentarios del repositorio
	 */
	public void setRemarks(String remarks);
	   
   /**
    * Obtiene el path completo del repositorio.
    * 
    * @return El nombre mencionado
    */
	public String getPath();
   
	/**
	 * Establece el path completo del repositorio.
	 * 
	 * @param path El path completo del repositorio.
	 */
	public void setPath(String path);
	
	/**
	 * Obtiene el estado del repositorio
	 * Ver - VolumeDefs
	 * 
	 * @return El estado mencionado
	 */
	public int getState();
	
	/**
	 * Establece el estado del repositorio
	 * 
	 * @param state El estado 
	 */
	public void setState(int state);
	
	/**
	 * Obtiene el flag del repositorio (null, centera_enable_collision_avoidance)
	 * @return
	 */
	public int getFlag();
	/**
	 * Establece el estado del repositorio
	 * @param Flag
	 */
	public void setFlag(int Flag);
	
	/**
	 * Obtiene el servidor ftp del repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getServer();
	
	/**
	 * Establece el servidor ftp del repositorio.
	 * 
	 * @param server El servidor ftp del repositorio
	 */
	public void setServer(String server);
	
	/**
	 * Obtiene el puerto ftp del repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public int getPort();
	
	/**
	 * Establece el puerto ftp del repositorio.
	 * 
	 * @param port El puerto ftp del repositorio.
	 */
	public void setPort(int port);
	
	/**
	 * Obtiene el usuario para conectarse por ftp al repositorio.
	 * 
	 * @return El nombre mencionado.
	 */
	public String getUser();
	
	/**
	 * Establece el usuario para conectarse por ftp al repositorio.
	 * 
	 * @param user el usuario para conectarse por ftp al repositorio.
	 */
	public void setUser(String user);
	
	/**
	 * Obtiene el password para conectarse por ftp al repositorio.
	 * 
	 * @return Nombre mencionado.
	 */
	public String getPassword();
	
	/**
	 * Establece el password para conectarse por ftp al repositorio.
	 * 
	 * @param password El password para conectarse por ftp al repositorio.
	 */
	public void setPassword(String password);
	
	/**
	 * Obtiene El identificador del sistema operativo del repositorio.
	 * 
	 * @return El identificador mencionado.
	 */
	public int getOs();
	
	/**
	 * Establece el sistema operativo del repositorio.
	 * 
	 * @param os El sistema operativo del repositorio.
	 */
	public void setOs(int os);
	
	/**
	 * Obtiene los volúmenes asociados al repositorio.
	 * No es necesario cargar el objeto antes.
	 * 
	 * @param id identificador del repositorio
	 * @return Los datos mencionados.
	 */
	public Volumes getVolumes(int id) throws Exception;
	
	/**
	 * Obtiene el identificador del usuario que ha creado el repositorio. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId();
	
	/**
    * Obtiene la fecha de creación del repositorio.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate();
   
   /**
    * Obtiene el identificador del usuario que ha actualizado el repositorio.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId();
   
   /**
    * Obtiene la fecha de actualización del repositorio.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate();
	
	/**
	 * Obtiene la información del repositorio en formato XML.
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
