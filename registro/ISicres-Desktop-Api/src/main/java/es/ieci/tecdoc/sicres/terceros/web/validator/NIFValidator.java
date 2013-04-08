package es.ieci.tecdoc.sicres.terceros.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.ieci.tecdoc.isicres.desktopweb.Keys;

/**
 * Validador para Números de Identificación Fiscal de España.
 *
 * @author IECISA
 *
 */
public class NIFValidator extends DocumentoIdentificativoValidator {

	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		String nif = (String) target;

		if (!StringUtils.isEmpty(nif)) {
			// Funcion que comprueba si el NIF contiene guiones (por motivos de
			// compatibilidad de versiones anteriores a ISicres 8.0)
			nif = comprobarGuiones(nif);

			String upperCasedNif = nif.toUpperCase();

			Matcher matcher = pattern.matcher(upperCasedNif);
			if (!matcher.matches()) {
				matcher.usePattern(specialPattern);
				// Si el NIF empieza por K,L o M se trata como un CIF
				if (matcher.matches()) {
					new CIFValidator().validate(target, errors);
				} else {
					errors.rejectValue(Keys.BLANK, Keys.BLANK, "Nif incorrecto");
				}
			} else {
				String caracterControl = getCaracterControl(Integer.valueOf(nif
						.substring(0, nif.length() - 1)));
				if (!caracterControl.equals(String.valueOf(upperCasedNif
						.charAt(upperCasedNif.length() - 1)))) {
					errors.rejectValue(Keys.BLANK, Keys.BLANK,
							"El digito de control no coincide");
				}
			}
		}
	}

	/**
	 * Funciona que elimina del nif si existen guiones, esto se realiza por
	 * motivos de compatibilidad con versiones anterirores de ISicres 8.0, en
	 * las versiones anteriores se permitia introducir entre el numero del NIF y
	 * la letra un guión
	 *
	 * @param nif - NIF
	 * @return String (NIF sin guiones)
	 */
	private String comprobarGuiones(String nif) {
		// Borramos de la cadena de nif los guiones (compatibilidad con
		// versiones anteriores de ISicres)
		if (nif.indexOf(Keys.GUION) != -1){
			String cadenaNumNIF = nif.substring(0, nif.indexOf(Keys.GUION));
			String cadenaLetraNIF = nif.substring(nif.indexOf(Keys.GUION) + 1);

			//validamos si el tamaño del numero del NIF es de 8 posiciones y el guion de 1 posicion
			if((cadenaNumNIF.length() == 8) && (cadenaLetraNIF.length() == 1)){
				nif = nif.replace(Keys.GUION, Keys.BLANK);
			}
		}
		return nif;
	}




	protected static final String NIF_PATTERN = "\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]";
	protected static final String NIF_SPECIAL_PATTERN = "[KLM]\\d{7}[TRWAGMYFPDXBNJZSQVHLCKE]";

	protected static final Pattern pattern = Pattern.compile(NIF_PATTERN);
	protected static final Pattern specialPattern = Pattern
			.compile(NIF_SPECIAL_PATTERN);
}
