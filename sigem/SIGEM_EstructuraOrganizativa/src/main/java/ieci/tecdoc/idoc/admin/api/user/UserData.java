package ieci.tecdoc.idoc.admin.api.user;

/**
 * Clase con los atributos de los datos personales de los usuarios
 * 
 * @author IECISA
 * @version $Revision$
 * 
 */
public interface UserData {

	public int getId();

	public void setId(int id);

	public String getCargo();

	public void setCargo(String cargo);

	public String getEmail();

	public void setEmail(String email);

	public String getTfnoMovil();

	public void setTfnoMovil(String tfnoMovil);

	public String getIdCert();

	public void setIdCert(String idCert);
	
	
	public void setNombre(String nombre);
	
	public String getNombre();
	
	public void setApellidos(String apellidos);
	
	public String getApelliidos();
	

	/**
	 * Carga datos personales de un usuario.
	 * 
	 * @param userId
	 *            Identificador del usuario.
	 * @throws Exception
	 *             Si se produce algún error al leer la información del usuario.
	 */
	public void load(int userId, String entidad) throws Exception;

	/**
	 * Carga datos personales de un usuario a partir del identificador del
	 * certificado
	 * 
	 * @param idCert
	 *            Identificador del certificado digital
	 * @param entidad
	 *            Identificador de la entidad
	 * @throws Exception
	 *             Si se produce algún error al leer la información del usuario.
	 */
	public void loadFromIdCert(String idCert, String entidad) throws Exception;

	/**
	 * Guarda los datos del usuario. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception
	 *             Si se produce algún error al guardar. Por ejemplo, el usuario
	 *             ya existe.
	 */
	public void store(String entidad) throws Exception;

	/**
	 * Elimina los datos del usuario.
	 * 
	 * @throws Exception
	 *             Si se produce algún error al eliminar.
	 */
	public void delete(String entidad) throws Exception;

}
