package es.ieci.tecdoc.sicres.terceros.web.validator;

import org.springframework.validation.Validator;

/**
 *
 * @author IECISA
 *
 */
public abstract class DocumentoIdentificativoValidator implements Validator {

	public String getCaracterControl(int documento) {
		return String.valueOf(CARACTERES_CONTROL.charAt(documento % 23));
	}

	protected static String CARACTERES_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
}
