package se.terceros;

/**
 * Implementación del interfaz para la información de un elemento dentro de la
 * base de datos de Terceros.
 */
public class InfoTerceroImpl implements InfoTercero {

	/** Identificador único del usuario. */
	private String id = null;

	/** Identificación del usuario (NIF, NIE, ...). */
	private String identificacion = null;

	/** Nombre del usuario. */
	private String nombre = null;

	/** Primer apellido del usuario. */
	private String primerApellido = null;

	/** Segundo apellido del usuario. */
	private String segundoApellido = null;

	/** Dirección del usuario. */
	private String direccion = null;

	/** E-mail del usuario. */
	private String email = null;

	/**
	 * Constructor.
	 */
	public InfoTerceroImpl() {
	}

	/**
	 * Devuelve la identificación.
	 * 
	 * @return Identificación.
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * Devuelve el nombre del usuario.
	 * 
	 * @return Nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el primer apellido del usuario.
	 * 
	 * @return Primer apellido.
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Devuelve el segundo apellido del usuario.
	 * 
	 * @return Segundo apellido.
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Devuelve la dirección del usuario.
	 * 
	 * @return Dirección.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Devuelve el e-mail del usuario.
	 * 
	 * @return E-mail.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Devuelve el identificador único del usuario.
	 * 
	 * @return Identificador único del usuario.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtiene la identificación del usuario.
	 * 
	 * @param identificacion
	 *            Identificación del usuario.
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * Obtiene el identificador único del usuario.
	 * 
	 * @param id
	 *            Identificador único del usuario.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @param nombre
	 *            Nombre del usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Establece el primer apellido del usuario.
	 * 
	 * @param primerApellido
	 *            Primer apellido del usuario.
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * Establece el segundo apellido del usuario.
	 * 
	 * @param segundoApellido
	 *            Segundo apellido del usuario.
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * Establece la dirección del usuario.
	 * 
	 * @param direccion
	 *            Direccion del usuario.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Establece el e-mail del usuario.
	 * 
	 * @param email
	 *            E-mail del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
