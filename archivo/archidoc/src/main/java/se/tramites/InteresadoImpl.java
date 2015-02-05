package se.tramites;

public class InteresadoImpl implements Interesado {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String nombre;
	String numIdentidad;
	String rol;
	String idEnTerceros;
	boolean interesadoPrincipal;

	public InteresadoImpl() {
	}

	/**
	 * @param nombre
	 * @param numIdentidad
	 * @param rol
	 * @param idEnTerceros
	 * @param interesadoPrincipal
	 */
	public InteresadoImpl(String nombre, String numIdentidad, String rol,
			String idEnTerceros, boolean interesadoPrincipal) {
		super();
		this.nombre = nombre;
		this.numIdentidad = numIdentidad;
		this.rol = rol;
		this.idEnTerceros = idEnTerceros;
		this.interesadoPrincipal = interesadoPrincipal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.Interesado#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.Interesado#getNumIdentidad()
	 */
	public String getNumIdentidad() {
		return numIdentidad;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.Interesado#getRol()
	 */
	public String getRol() {
		return rol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.Interesado#esInteresadoPrincipal()
	 */
	public boolean esInteresadoPrincipal() {
		return interesadoPrincipal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.tramites.Interesado#getIdEnTerceros()
	 */
	public String getIdEnTerceros() {
		return idEnTerceros;
	}

	/**
	 * @return Returns the interesadoPrincipal.
	 */
	public boolean isInteresadoPrincipal() {
		return interesadoPrincipal;
	}

	/**
	 * @param interesadoPrincipal
	 *            The interesadoPrincipal to set.
	 */
	public void setInteresadoPrincipal(boolean interesadoPrincipal) {
		this.interesadoPrincipal = interesadoPrincipal;
	}

	/**
	 * @param idEnTerceros
	 *            The idEnTerceros to set.
	 */
	public void setIdEnTerceros(String idEnTerceros) {
		this.idEnTerceros = idEnTerceros;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param numIdentidad
	 *            The numIdentidad to set.
	 */
	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}

	/**
	 * @param rol
	 *            The rol to set.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
}
