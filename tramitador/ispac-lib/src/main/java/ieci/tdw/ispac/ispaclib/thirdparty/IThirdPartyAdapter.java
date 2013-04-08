package ieci.tdw.ispac.ispaclib.thirdparty;

import java.io.Serializable;

/**
 * Interfaz del adaptador para contener la información de un tercero.
 *
 */
public interface IThirdPartyAdapter extends Serializable {
	
	public static final String ADDRESS_TYPE_POSTAL 		= "P";
	public static final String ADDRESS_TYPE_TELEMATIC	= "T";
	
	public static final String TIPO_PERSONA_FISICA 		= "F";
	public static final String TIPO_PERSONA_JURIDICA 	= "J";

	/**
	 * Obtiene el identificador del tercero en el sistema externo.
	 * @return Identificador del tercero.
	 */
    public String getIdExt();

	/**
	 * Obtiene la identificación del tercero.
	 * @return Identificación del tercero.
	 */
    public String getIdentificacion();

    /**
	 * Obtiene el nombre del tercero.
	 * @return Nombre del tercero.
	 */
	public String getNombre();

    /**
	 * Obtiene el primer apellido del tercero.
	 * @return Primer apellido del tercero.
	 */
    public String getPrimerApellido();
    
    /**
	 * Obtiene el segundo apellido del tercero.
	 * @return Primer apellido del tercero.
	 */
    public String getSegundoApellido();

	/**
	 * Obtiene los apellidos del tercero.
	 * @return Apellidos del tercero.
	 */
    public String getApellidos();
    
	/**
	 * Obtiene el nombre completo del tercero.
	 * @return Nombre completo del tercero.
	 */
    public String getNombreCompleto();
    
	/**
	 * Indica si el tipo de dirección de notificación es telemática.
	 * @return true si la notificación es telemática.
	 */
    public boolean isNotificacionTelematica();

    /**
     * Obtiene el tipo de persona.
     * @return Tipo de persona: (F)ísica o (J)urídica.
     */
    public String getTipoPersona();

	/**
	 * Obtiene las direcciones electrónicas.
	 * @return direcciones electrónicas asociadas al tercero
	 */
	public IElectronicAddressAdapter[] getDireccionesElectronicas();

    /**
     * Obtiene la dirección electrónica por defecto.
     * @return dirección electrónica por defecto para el tercero
     */
    public IElectronicAddressAdapter getDefaultDireccionElectronica();

    /**
     * Establece la dirección electrónica por defecto.
     * @param direccion dirección electrónica por defecto para el tercero
     */
    public void setDefaultDireccionElectronica(IElectronicAddressAdapter direccion);
    
	/**
	 * Obtiene las direcciones postales.
	 * @return direcciones postales aosicadas al tercero
	 */
	public IPostalAddressAdapter[] getDireccionesPostales();

	/**
	 * Obtiene la dirección postal por defecto.
	 * @return dirección postal por defecto para el tercero
	 */
	public IPostalAddressAdapter getDefaultDireccionPostal();

    /**
     * Establece la dirección postal por defecto.
     * @param direccion dirección postal por defecto para el tercero
     */
    public void setDefaultDireccionPostal(IPostalAddressAdapter direccion);

}