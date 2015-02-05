package ieci.tdw.ispac.ispaclib.thirdparty;

/**
 * Interfaz del adaptador para contener la información de la dirección electrónica
 * de un tercero.
 *
 */
public interface IElectronicAddressAdapter {

	/**
	 * La dirección electrónica es una dirección de correo electrónico.
	 */
	public final int MAIL_TYPE = 1;
	
	/**
	 * La dirección electrónica es un número de teléfono fijo.
	 */
	public final int PHONE_TYPE = 2; 

	/**
	 * La dirección electrónica es un DEU.
	 */
	public final int DEU_TYPE = 3;
	
	/**
	 * La dirección electrónica es un teléfono móvil.
	 */
	public final int MOBILE_PHONE_TYPE = 4;

	
	/**
	 * Obtiene el identificador de la dirección electrónica.
	 * @return identificador de la dirección electrónica.
	 */
	public String getId(); 
	
	/**
	 * Obtiene el tipo de dirección electrónica.
	 * @return tipo de dirección electrónica.
	 */
	public int getTipo();

	/**
	 * Obtiene la dirección electrónica.
	 * @return dirección electrónica.
	 */
	public String getDireccion();
	
}
