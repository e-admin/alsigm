package es.ieci.tecdoc.sicres.terceros.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.ieci.tecdoc.isicres.desktopweb.Keys;

/**
 *
 * @author IECISA
 *
 */
public class NIEValidator extends DocumentoIdentificativoValidator {

	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		String nie = (String) target;

		if (!StringUtils.isEmpty(nie)) {

			// Funcion que comprueba si el NIE contiene guiones (por motivos de
			// compatibilidad de versiones anteriores a ISicres 8.0)
			nie = comprobarGuiones(nie);


			String upperCasedNie = nie.toUpperCase();

			Matcher matcher = pattern.matcher(upperCasedNie);

			if (!matcher.matches()) {
				errors.rejectValue(Keys.BLANK, Keys.BLANK, "Nie incorrecto");
			} else {
				// Se cambian los caracteres XYZ por 012
				String adaptedNie = StringUtils.replaceChars(
						upperCasedNie.substring(0, upperCasedNie.length() - 1),
						PREFIX_NIE, PREFIX_NIE_VALUE);
				adaptedNie = StringUtils.join(new String[] {
						adaptedNie,
						String.valueOf(upperCasedNie.charAt(upperCasedNie
								.length() - 1)) });
				// una vez transformado el NIE se valida como un NIF
				new NIFValidator().validate(adaptedNie, errors);
			}

		}
	}

	/**
	 * Funciona que elimina del nie si existen guiones, esto se realiza por
	 * motivos de compatibilidad con versiones anterirores de ISicres 8.0 en
	 * dichas versiones se permitia introducir guiones separando la parte
	 * numerica de los caracteres, además el NIE también se permitia comenzar
	 * por codigo X0, X, Y o Z. En el caso de prefijo X0 no se permite que
	 * contenga guion entre el prefijo y el numero de nie
	 *
	 *
	 * @param nie - NIE
	 * @return String (NIE sin guiones)
	 */
	private String comprobarGuiones(String nie) {
		// comprobamos el prefijo de la cadena de entrada empieza por X0 y la
		// longitud del nie sin guiones es mayor a nueve caracteres (en la
		// versión anterior de ISicres 8.0, se permitia introducir NIE con prefijo
		// X0)
		if (PREFIX_NIE_X0.equalsIgnoreCase(nie.substring(0,
				PREFIX_NIE_X0.length()))
				&& (nie.replace(Keys.GUION, Keys.BLANK).length() > 9)) {
			//comprobamos si hay algun guion despues del prefijo
			if (!(Keys.GUION.equals(nie.substring(PREFIX_NIE_X0.length(),
					(PREFIX_NIE_X0.length() + 1))))) {
				//reemplazamos el prefijo X0 por X
				nie = nie.replace(PREFIX_NIE_X0, PREFIX_NIE_X);
			}
		}

		int lengthNIE = 9;
		//buscamos el prefijo
		if ((PREFIX_NIE_X.equalsIgnoreCase(nie.substring(0, PREFIX_NIE_X.length())))
				|| (PREFIX_NIE_Y.equalsIgnoreCase(nie.substring(0, PREFIX_NIE_Y.length())))
				|| (PREFIX_NIE_Z.equalsIgnoreCase(nie.substring(0, PREFIX_NIE_Z.length())))) {
			//obtenemos de la cadena del NIE la parte sin el prefijo
			String nieSinPrefijo = nie.substring(1);

			//Comprobamos si existen guiones en la cadena y los borramos de la cadena
			if (Keys.GUION.equals(nieSinPrefijo.substring(0, 1))){
				nieSinPrefijo = nieSinPrefijo.substring(1);
			}

			if (nieSinPrefijo.indexOf(Keys.GUION) == -1){
				if(nieSinPrefijo.length() == (lengthNIE - 1)){
					nie = nie.replace(Keys.GUION, Keys.BLANK);
				}

			}else {
				//comprobamos si hay guiones en el nie
				if(nieSinPrefijo.indexOf(Keys.GUION)!=-1){
					//obtenemos las cadenas
					String cadenaNumNIE = nieSinPrefijo.substring(0, nieSinPrefijo.indexOf(Keys.GUION));
					String cadenaGuionNIE = nieSinPrefijo.substring(nieSinPrefijo.indexOf(Keys.GUION) + 1);
					//validamos el correcto tamaño de las cadenas
					if((cadenaNumNIE.length() == (lengthNIE - 2)) && (cadenaGuionNIE.length() == 1)){
						nie = nie.replace(Keys.GUION, Keys.BLANK);
					}
				}
			}
		}

		return nie;
	}

	//Variables que contienen los posibles prefijos del nie
	protected static final String PREFIX_NIE_X0 = "X0";
	protected static final String PREFIX_NIE_X = "X";
	protected static final String PREFIX_NIE_Y = "Y";
	protected static final String PREFIX_NIE_Z = "Z";
	protected static final String PREFIX_NIE = "XYZ";


	protected static final String PREFIX_NIE_VALUE = "012";

	protected static final String NIE_PATTERN = "[XYZ]\\d{7}[TRWAGMYFPDXBNJZSQVHLCKE]";
	protected static final Pattern pattern = Pattern.compile(NIE_PATTERN);
}
