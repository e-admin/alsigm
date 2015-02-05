package ieci.tdw.ispac.ispaclib.thirdparty;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

/**
 * Adaptador para contener la información de un tercero.
 *
 */
public class ThirdPartyAdapter implements IThirdPartyAdapter {

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
	IPostalAddressAdapter [] postalAddresses = null;
	
	/** Direcciones electronicas asociadas al tercero. */
	IElectronicAddressAdapter [] electronicAddresses = null;

    
    /**
     * Constructor.
     * @param item Información del tercero.
     * @throws ISPACException si ocurre algún error.
     */
    public ThirdPartyAdapter() {
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
	 * Obtiene los apellidos del tercero.
	 * @return Apellidos del tercero.
	 */
    public String getApellidos() {
		StringBuffer apellidos = new StringBuffer()
			.append(StringUtils.nullToEmpty(getPrimerApellido()))
			.append(" ")
			.append(StringUtils.nullToEmpty(getSegundoApellido()));
		
		return apellidos.toString().trim();
    }

	/**
	 * Indica si el tipo de dirección de notificación es telemática.
	 * @return true si la notificación es telemática.
	 */
    public boolean isNotificacionTelematica() {
    	return notificacionTelematica;
    }

	/**
	 * Obtiene el nombre completo del tercero.
	 * @return Nombre completo del tercero.
	 */
    public String getNombreCompleto() {
		StringBuffer nombreCompleto = new StringBuffer()
			.append(StringUtils.nullToEmpty(getNombre()))
			.append(" ")
			.append(getApellidos());
		
		return nombreCompleto.toString().trim();
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

	public IElectronicAddressAdapter getDefaultDireccionElectronica() {
		if (ArrayUtils.isEmpty(electronicAddresses))
			return null;
		return electronicAddresses[0];
	}

	public void setDefaultDireccionElectronica(IElectronicAddressAdapter defaultElectronicAddress) {
		this.electronicAddresses = new IElectronicAddressAdapter[]{defaultElectronicAddress};
	}

	public IPostalAddressAdapter getDefaultDireccionPostal() {
		if (ArrayUtils.isEmpty(postalAddresses))
			return null;
		return postalAddresses[0];
	}

	public void setDefaultDireccionPostal(IPostalAddressAdapter defaultPostalAddress) {
		this.postalAddresses = new IPostalAddressAdapter[]{defaultPostalAddress};
	}

	public IElectronicAddressAdapter[] getDireccionesElectronicas() {
		return electronicAddresses;
	}

	public void setDireccionesElectronicas(IElectronicAddressAdapter[] electronicAddresses) {
		this.electronicAddresses = electronicAddresses;
	}

	public IPostalAddressAdapter[] getDireccionesPostales() {
		return postalAddresses;
	}

	public void setDireccionesPostales(IPostalAddressAdapter[] postalAddresses) {
		this.postalAddresses = postalAddresses;
	}
}