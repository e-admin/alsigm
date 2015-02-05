package se.terceros;

/**
 * Interfaz para la información de un elemento dentro de la base de datos de
 * Terceros.
 */
public interface InfoTercero {

	/**
	 * Devuelve el identificador único del usuario.
	 * 
	 * @return Identificador único del usuario.
	 */
	public String getId();

	/**
	 * Devuelve la identificación.
	 * 
	 * @return Identificación.
	 */
	public String getIdentificacion();

	/**
	 * Devuelve el nombre del tercero.
	 * 
	 * @return Nombre.
	 */
	public String getNombre();

	/**
	 * Devuelve el primer apellido del tercero.
	 * 
	 * @return Primer apellido.
	 */
	public String getPrimerApellido();

	/**
	 * Devuelve el segundo apellido del tercero.
	 * 
	 * @return Segundo apellido.
	 */
	public String getSegundoApellido();

	/**
	 * Devuelve la dirección del tercero.
	 * 
	 * @return Dirección.
	 */
	public String getDireccion();

	/**
	 * Devuelve el e-mail del tercero.
	 * 
	 * @return E-mail.
	 */
	public String getEmail();
}
