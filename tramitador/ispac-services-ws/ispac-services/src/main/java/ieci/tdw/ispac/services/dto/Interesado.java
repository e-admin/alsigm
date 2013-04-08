package ieci.tdw.ispac.services.dto;

import java.io.Serializable;

/**
 * Información de un interesado.
 */
public class Interesado implements Serializable {

	private static final long serialVersionUID = 4747668346528261003L;

	/** Nombre y apellidos del interesado. */
	private String nombre = null;

	/** Número de identidad del interesado. */
	private String numIdentidad = null;

	/** Rol del interesado. */
	private String rol = null;

	/** Indica si es interesado principal. */
	private boolean interesadoPrincipal = false;

	/** Identificador del interesado en la base de datos de terceros. */
	private String idEnTerceros = null;

	/**
	 * Constructor.
	 */
	public Interesado() {
		super();
	}

	/**
	 * Obtiene el identificador del interesado en la base de datos de terceros.
	 * @return Identificador del interesado en la base de datos de terceros.
	 */
	public String getIdEnTerceros() {
		return idEnTerceros;
	}

	/**
	 * Establece el identificador del interesado en la base de datos de terceros.
	 * @param idEnTerceros Identificador del interesado en la base de datos de terceros.
	 */
	public void setIdEnTerceros(String idEnTerceros) {
		this.idEnTerceros = idEnTerceros;
	}

	/**
	 * Indica si es interesado principal.
	 * @return Si es interesado principal.
	 */
	public boolean isInteresadoPrincipal() {
		return interesadoPrincipal;
	}

	/**
	 * Establece si es interesado principal.
	 * @param interesadoPrincipal Si es interesado principal.
	 */
	public void setInteresadoPrincipal(boolean interesadoPrincipal) {
		this.interesadoPrincipal = interesadoPrincipal;
	}

	/**
	 * Obtiene el nombre y apellidos del interesado.
	 * @return Nombre y apellidos del interesado.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre y apellidos del interesado.
	 * @param nombre Nombre y apellidos del interesado.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el número de identidad del interesado.
	 * @return Número de identidad del interesado.
	 */
	public String getNumIdentidad() {
		return numIdentidad;
	}

	/**
	 * Establece el número de identidad del interesado.
	 * @param numIdentidad Número de identidad del interesado.
	 */
	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}

	/**
	 * Obtiene el rol del interesado.
	 * @return Rol del interesado.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol del interesado.
	 * @param rol Rol del interesado.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
}
