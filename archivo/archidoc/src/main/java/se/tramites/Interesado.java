package se.tramites;

import java.io.Serializable;

/**
 * Interfaz para la información de un interesado.
 */
public interface Interesado extends Serializable {

	/**
	 * Devuelve el nombre y apellidos del interesado.
	 * 
	 * @return Nombre y apellidos del interesado.
	 */
	public String getNombre();

	/**
	 * Devuelve el número de identidad.
	 * 
	 * @return Número de identidad.
	 */
	public String getNumIdentidad();

	/**
	 * Devuelve el rol.
	 * 
	 * @return Rol.
	 */
	public String getRol();

	/**
	 * Devuelve true si es el interesado principal; en otro caso, devuelve
	 * false.
	 * 
	 * @return true si es el interesado principal.
	 */
	public boolean esInteresadoPrincipal();

	/**
	 * Devuelve el identificador del interesado en la base de datos de terceros.
	 * El valor será nulo si no está validado en Terceros.
	 * 
	 * @return Identificador del interesado en la base de datos de terceros.
	 */
	public String getIdEnTerceros();

}
