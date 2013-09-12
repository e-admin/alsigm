package common.util;

/**
 * Clase de utilidad que nos permite validar NIF, NIE y CIF
 */
public class IFValidator {
	private static final String conValores = "0246813579";

	public static final String DOCUMENTO_NIF = "N";
	public static final String DOCUMENTO_CIF = "C";
	public static final String DOCUMENTO_NIE = "X";

	/**
	 * Funcion que comprueba si un documento de identificacion(nif,cif o nie) es
	 * válido. Casos de NIF, CIF, NIE: (x - caracter alfabetico, 9 - caracter
	 * numerico, resto - tal cual)<br/>
	 * <ul>
	 * <li>NIF normales:099999999x</li>
	 * <li>NIF españoles menores 14 años y extranjeros menores 18:K9999999x</li>
	 * <li>NIF españoles mayores 14 años residiendo en el extranjero:L9999999x</li>
	 * <li>NIF extranjeros mayores 18 años NO residentes:M9999999x</li>
	 * <li>NIE:X9999999x</li>
	 * <li>CIF españoles NO relacionados con la Admon.:x99999999</li>
	 * <li>CIF españoles SI relacionados con la
	 * Admon.:S9999999x/P9999999x/Q9999999x</li>
	 * </li>CIF extranjeros: x9999999x</li>
	 * </ul>
    *
	 * @param document
	 *            El identificador de documento que se desea validar.
	 * @param document
	 *            El identificador de documento que se desea validar.
	 * @return true si el documento es válido o false en caso contrario.
	 */
	public static boolean isValidIF(String documento) {
		if (!isValidIF(documento, DOCUMENTO_NIF)) {
			if (!isValidIF(documento, DOCUMENTO_CIF)) {
				if (!isValidIF(documento, DOCUMENTO_NIE))
					return false;
				else
					return true;
			} else
				return true;
		} else
			return true;
	}

	/**
	 * Funcion que comprueba si un documento de identificacion(nif,cif o nie) es
	 * válido. Casos de NIF, CIF, NIE: (x - caracter alfabetico, 9 - caracter
	 * numerico, resto - tal cual)<br/>
	 * <ul>
	 * <li>NIF normales:099999999x</li>
	 * <li>NIF españoles menores 14 años y extranjeros menores 18:K9999999x</li>
	 * <li>NIF españoles mayores 14 años residiendo en el extranjero:L9999999x</li>
	 * <li>NIF extranjeros mayores 18 años NO residentes:M9999999x</li>
	 * <li>NIE:X9999999x</li>
	 * <li>CIF españoles NO relacionados con la Admon.:x99999999</li>
	 * <li>CIF españoles SI relacionados con la
	 * Admon.:S9999999x/P9999999x/Q9999999x</li>
	 * </li>CIF extranjeros: x9999999x</li>
	 * </ul>
    *
	 * @param document
	 *            El identificador de documento que se desea validar.
	 * @param tipo
	 *            El tipo del documento a validar, segun sea NIF {@see
	 *            IFValidator#DOCUMENTO_NIF} ,CIF {@see
	 *            IFValidator#DOCUMENTO_CIF} o NIE {@see
	 *            IFValidator#DOCUMENTO_NIE}
	 * @return true si el documento es válido o false en caso contrario.
	 */
	public static boolean isValidIF(String documento, String tipo) {
		boolean nifRaro = false;
		boolean result = false;
		String norma = null;
		long num = 0;
		String cc_T = null;
		String cc_N = null;

		documento = documento.toUpperCase();
		tipo = tipo.toUpperCase();

		if (documento.length() == 0)
			return result;
		if ((!tipo.equalsIgnoreCase("N")) && (!tipo.equalsIgnoreCase("X"))
				&& (!tipo.equalsIgnoreCase("C")))
			return result;
		if (documento.length() > 10)
			return result;

		char cero = documento.charAt(0);
		if ("KLM".indexOf(cero) >= 0)
			nifRaro = true;

		if (documento.length() < 10) {
			if (tipo.equalsIgnoreCase("N") && (nifRaro == false)) {
				documento = fillNumberString(
						documento.substring(0, documento.length() - 1), 9, '0')
						+ documento.charAt(documento.length() - 1);
			} else {
				if (documento.length() < 9)
					return result;
				else
					documento = documento + " ";
			}
		}

		if (tipo.equalsIgnoreCase("N")) {
			if (nifRaro == false) {
				if (documento.charAt(0) != '0')
					return result;

				try {
					Integer.parseInt(documento.substring(0,
							documento.length() - 1));
				} catch (NumberFormatException nfe) {
					return result;
				}

				norma = "DNI1";
			} else {
				try {
					Integer.parseInt(documento.substring(1,
							documento.length() - 2));
				} catch (NumberFormatException nfe) {
					return result;
				}
				if (documento.charAt(documento.length() - 1) != ' ')
					return result;

				norma = "DNI2";
			}
		} else {
			if (tipo.equalsIgnoreCase("C")) {
				if ("ABCDEFGHIJNPQRSUVW".indexOf(documento.charAt(0)) < 0)
					return result;

				try {
					Integer.parseInt(documento.substring(1,
							documento.length() - 3));
				} catch (NumberFormatException nfe) {
					return result;
				}

				if (documento.charAt(documento.length() - 1) != ' ')
					return result;

				norma = "CIF";
			} else {
				if (tipo.equalsIgnoreCase("X")) {
					if (documento.charAt(0) != 'X')
						return result;

					try {
						Integer.parseInt(documento.substring(1,
								documento.length() - 2));
					} catch (NumberFormatException nfe) {
						return result;
					}

					if ((documento.charAt(documento.length() - 1)) != ' ')
						return result;

					norma = "DNI2";
				} else
					return result;
			}
		}

		if (norma.equalsIgnoreCase("DNI1") || norma.equalsIgnoreCase("DNI2")) {
			if (norma.equalsIgnoreCase("DNI1")) {
				try {
					num = Long.parseLong(documento.substring(0,
							documento.length() - 1));
				} catch (NumberFormatException nfe) {
				}
			} else {
				try {
					num = Long.parseLong(documento.substring(1,
							documento.length() - 2));
				} catch (NumberFormatException nfe) {
				}
			}

			int mod = ((int) num) % 23;
			cc_T = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(mod) + "";
		} else {
			num = 0;
			for (int i = 1; i <= 5; i += 2) {
				String valorS = ""
						+ conValores.charAt((Integer.parseInt(""
								+ documento.charAt(i))));
				num = num + Long.parseLong(valorS);
				valorS = "" + documento.charAt(i + 1);
				num = num + Long.parseLong(valorS);
			}
			String valorS2 = ""
					+ conValores.charAt((Integer.parseInt(""
							+ documento.charAt(7))));
			num = num + Long.parseLong(valorS2);
			num = 10 - (num % 10);

			if (num == 10) {
				cc_T = "J";
				cc_N = "0";
			} else {
				cc_T = (new Character((char) ((int) num + 64))).toString();
				cc_N = Long.toString(num);
			}
		}

		if (tipo.equalsIgnoreCase("N")) {
			if (nifRaro == false) {
				if (documento.charAt(documento.length() - 1) != cc_T.charAt(0))
					return result;
			} else {
				char c = documento.charAt(documento.length() - 2);
				if (c != cc_T.charAt(0))
					return result;
			}
		} else {
			if (tipo.equalsIgnoreCase("C")) {
				if (documento.charAt(0) == 'P' || documento.charAt(0) == 'Q'
						|| documento.charAt(0) == 'S') {
					if (documento.charAt(documento.length() - 2) != cc_T
							.charAt(0))
						return result;
				} else {
					char a = (documento.charAt(documento.length() - 2));
					char b = (documento.charAt(documento.length() - 2));
					if ((a != cc_T.charAt(0)) && (b != cc_N.charAt(0)))
						return result;
				}
			} else {
				if (tipo.equalsIgnoreCase("X")) {
					char c = documento.charAt(documento.length() - 2);
					if (c != cc_T.charAt(0))
						return result;
				} else
					return result;
			}
		}
		return true;
	}

	/**
	 * Completa una cadena con caracteres por la izquierda.
    *
	 * @param numero
	 *            Cadena que contiene el número.
	 * @param numCifras
	 *            Número de caracteres o cifras de la cadena final.
	 * @param caracter
	 *            Carácter que se va a añadir por la izquierda, normalmente un
	 *            '0'.
	 * @return Cadena con los caracteres añadidos por la izquierda.
	 */
	private static String fillNumberString(String numero, int numCifras,
			char caracter) {
		StringBuffer respuesta = new StringBuffer();
		int contador = 0;

		if (numero == null) {
			contador = numCifras;
			numero = "";
		} else if (numero.length() < numCifras)
			contador = numCifras - numero.length();

		for (int i = 0; i < contador; i++)
			respuesta.append(caracter);

		respuesta.append(numero);

		return respuesta.toString();
	}
}
