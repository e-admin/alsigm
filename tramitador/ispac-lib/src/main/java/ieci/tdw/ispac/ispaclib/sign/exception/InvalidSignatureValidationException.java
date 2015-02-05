package ieci.tdw.ispac.ispaclib.sign.exception;

import java.util.HashMap;
import java.util.Map;


/**
 * Excepción provocada si la validación de una firma
 * es incorrecta.
 * @author antoniomaria_sanchez at ieci.es
 * @since 18/12/2008
 */
public class InvalidSignatureValidationException extends RuntimeException{

	private static final long serialVersionUID = -9126646423282158869L;
	
	/**
	 * Detalle de la firma en caso que se haya podido parsear
	 * la firma.
	 */
	protected Map signProperties;
	
	public InvalidSignatureValidationException(String message) {
		super(message);
		this.signProperties = new HashMap();
	}
	
	public InvalidSignatureValidationException(String message, Map signProperties) {
		this(message);
		this.signProperties = signProperties;
	}

	public Map getSignProperties() {
		return signProperties;
	}

	public void setSignProperties(Map signProperties) {
		this.signProperties = signProperties;
	}
	
	
}
