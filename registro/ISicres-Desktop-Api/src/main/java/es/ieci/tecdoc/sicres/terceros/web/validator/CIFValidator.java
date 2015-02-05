package es.ieci.tecdoc.sicres.terceros.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * Validador para Códigos de Identificación Fiscal de España.
 *
 * @author IECISA
 *
 */
public class CIFValidator extends DocumentoIdentificativoValidator {

	public boolean supports(Class<?> clazz) {
		return String.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		String cif = (String) target;

		if (!StringUtils.isEmpty(cif)) {
			String upperCasedCif = cif.toUpperCase();

			Matcher matcher = pattern.matcher(upperCasedCif);
			if (!matcher.matches()) {
				errors.rejectValue("", "", "Cif incorrecto");
			} else {
				String digitos = upperCasedCif.substring(1,
						upperCasedCif.length() - 1);

				// Sumamos posiciones pares (A)
				int A = 0;
				for (int i = 1; i < digitos.length(); i += 2) {
					A += Character.digit(digitos.charAt(i), 10);
				}
				// Sumamos posiciones impares multiplicando valores por 2 (B)
				int B = 0;
				for (int i = 0; i < digitos.length(); i += 2) {
					final int digito = Character.digit(digitos.charAt(i), 10);
					int nn = 2 * digito;
					if (nn > 9) {
						nn = 1 + (nn - 10);
					}
					B += nn;
				}
				int C = A + B;
				int E = C % 10;
				int D = (E > 0) ? (10 - E) : 0;

				final char letraIni = upperCasedCif.charAt(0);
				final char caracterFin = upperCasedCif.charAt(8);

				final boolean esControlValido =
				// ¿el caracter de control es válido como letra?
				(CONTROL_SOLO_NUMEROS.indexOf(letraIni) < 0 && CONTROL_NUMERO_A_LETRA
						.charAt(D) == caracterFin) ||
				// ¿el caracter de control es válido como dígito?
						(CONTROL_SOLO_LETRAS.indexOf(letraIni) < 0 && D == Character
								.digit(caracterFin, 10));

				if (!esControlValido) {
					errors.rejectValue("", "", "Formato incorrecto");
				}
			}
		}
	}

	protected static final String CIF_PATTERN = "[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]";
	protected static final Pattern pattern = Pattern.compile(CIF_PATTERN);

	private static final String CONTROL_SOLO_NUMEROS = "ABEH"; /*
																 * Sólo admiten
																 * números como
																 * caracter de
																 * control
																 */
	private static final String CONTROL_SOLO_LETRAS = "KPQS"; /*
															 * Sólo admiten
															 * letras como
															 * caracter de
															 * control
															 */
	private static final String CONTROL_NUMERO_A_LETRA = "JABCDEFGHI"; /*
																		 * Conversión
																		 * de
																		 * dígito
																		 * a
																		 * letra
																		 * de
																		 * control
																		 */
}
