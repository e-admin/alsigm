package ieci.tdw.ispac.ispaclib.sign.portafirmas.vo;

/**
 * ClasE que almacena la información de los firmantes
 * @author Iecisa
 * @version $Revision$
 *
 */
public class Signer {

	public static final String TYPE_SIGNER_USER		 		= "U";
	public static final String TYPE_SIGNER_CARGO			= "C";

	private String identifier;

	private String name;


	/*Indica el tipo de firmante asociado (usuario o cargo).
	 * Solo admite dos valores 'U' ó 'C'
	 */

	private String tipoFirmante;

	public Signer() {
		super();
		this.tipoFirmante=TYPE_SIGNER_USER;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTipoFirmante() {
		return tipoFirmante;
	}

	public void setTipoFirmante(String tipoFirmante) {
		this.tipoFirmante = tipoFirmante;
	}



}
