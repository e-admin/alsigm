package ieci.tecdoc.sgm.terceros.ws.server.dto;

import java.io.Serializable;

public class Tercero implements Serializable {
	
	/** Identificador del tercero en el sistema externo. */
    private String idExt = null;
    
	/** Número de identificación del tercero. */
    private String identificacion = null;
    
	/** Nombre del tercero. */
	private String nombre = null;

	/** Primer apellido del tercero. */
	private String primerApellido = null;
	
	/** Primer apellido del tercero. */
	private String segundoApellido = null;
	
	/** Indica si el tipo de dirección de notificación es telemática. */
    private boolean notificacionTelematica = false;
    
    /** Tipo de persona. */
    private String tipoPersona = null;
	
	/** Direcciones postales asociadas al tercero. */
	DireccionPostal [] direccionesPostales = null;
	
	/** Direcciones electronicas asociadas al tercero. */
	DireccionElectronica [] direccionesElectronicas = null;

    
    /**
     * Constructor.
     *
     */
    public Tercero() {
    	super();
    }

	/**
	 * Obtiene el identificador del tercero en el sistema externo.
	 * @return Identificador del tercero.
	 */
    public String getIdExt() {
    	return idExt;
    }
    
	/**
	 * Obtiene el número de identificación del tercero.
	 * @return Número de identificación del tercero.
	 */
    public String getIdentificacion() {
    	return identificacion;
    }

	/**
	 * Obtiene el nombre del tercero.
	 * @return Nombre del tercero.
	 */
    public String getNombre() {
		return nombre;
    }
    
	/**
	 * Obtiene el primer apellido del tercero.
	 * @return Primer apellido del tercero.
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Obtiene el segundo apellido del tercero.
	 * @return Segundo apellido del tercero.
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Indica si el tipo de dirección de notificación es telemática.
	 * @return true si la notificación es telemática.
	 */
    public boolean isNotificacionTelematica() {
    	return notificacionTelematica;
    }

    /**
     * Obtiene el tipo de persona.
     * @return Tipo de persona: (F)ísica o (J)urídica.
     */
    public String getTipoPersona() {
    	return tipoPersona;
    }


	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public void setNotificacionTelematica(boolean notificacionTelematica) {
		this.notificacionTelematica = notificacionTelematica;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public DireccionPostal[] getDireccionesPostales() {
		return direccionesPostales;
	}

	public void setDireccionesPostales(DireccionPostal[] direccionesPostales) {
		this.direccionesPostales = direccionesPostales;
	}

	public DireccionElectronica[] getDireccionesElectronicas() {
		return direccionesElectronicas;
	}

	public void setDireccionesElectronicas(
			DireccionElectronica[] direccionesElectronicas) {
		this.direccionesElectronicas = direccionesElectronicas;
	}

}
