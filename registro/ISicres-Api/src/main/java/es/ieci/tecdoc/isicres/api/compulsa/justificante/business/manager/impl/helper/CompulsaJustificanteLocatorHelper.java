package es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.CompulsaKeys;

import es.ieci.tecdoc.isicres.api.compulsa.justificante.business.vo.ISicresCompulsaJustificanteVO;


/**
 * Clase que contiene el metodo para la generacion del Localizador del documento compulsado.
 * @author IECISA
 *
 */
public class CompulsaJustificanteLocatorHelper {
	private static final Logger logger = Logger
	.getLogger(CompulsaJustificanteHelper.class);	

	static CompulsaJustificanteLocatorHelper instance;
	
	public static synchronized CompulsaJustificanteLocatorHelper getInstance() {
		if (instance == null) 
			instance = new CompulsaJustificanteLocatorHelper();
		return instance;
	}
	
	/**
	 * Asigna el valor del Localizador al objeto ISicresCompulsaVO que se pasa como parametro. Para la generación del localizador se
	 * usaran los datos Entidad, Fecha de Compulsa y Numero de Registro del objeto ISicresCompulsaVO.
	 * @param iSicresCompulsaVO Objeto sobre el que se establecera el valor del localizador.
	 */
	public void setLocator(ISicresCompulsaJustificanteVO iSicresCompulsaVO) {
		String entidad = getEntidadLocator(iSicresCompulsaVO.getEntidad());
		String folderNumber = getFolderNumberLocator(iSicresCompulsaVO.getNumeroRegistro());

		SimpleDateFormat sdf = new SimpleDateFormat(
				CompulsaKeys.DATE_FORMAT_LOCATOR);
		String fecha = sdf.format(iSicresCompulsaVO.getFechaCompulsa());

		String codVerificacion = generateCodigoVerificacion(entidad,
				folderNumber, fecha);

		iSicresCompulsaVO.setLocator(getAlfanumericCodigoVerificacion(codVerificacion));
	}
	
	/**
	 * Método que obtiene el codigo de la entidad para la compulsa
	 *
	 * @param entidadId
	 * @return
	 */
	protected String getEntidadLocator(String entidadId) {
		String entidad = entidadId;
		if (CompulsaKeys.ENTIDAD_DEFAULT.equalsIgnoreCase(entidad)) {
			entidad = CompulsaKeys.ENTIDAD_DEFAULT_CODE;
		}

		return entidad;
	}

	/**
	 * Método que obtiene el los 4 ultimos digitos del numero de registro
	 *
	 * @param folderNumber
	 * @return
	 */
	protected String getFolderNumberLocator(String folderNumber) {
		String result = "0000";
		if (StringUtils.isNotBlank(folderNumber)) {
			if (folderNumber.length() >= 4) {
				result = folderNumber.substring(folderNumber.length() - 4);
			} else {
				result = result.substring(result.length()
						- folderNumber.length())
						+ folderNumber;
			}
		}

		return result;
	}

	/**
	 * Método que genera un localizador a partir de los parametros recibidos
	 *
	 * @param entidad
	 * @param folderNumber
	 * @param fecha
	 * @return
	 */
	protected String generateCodigoVerificacion(String entidad,
			String folderNumber, String fecha) {

		// Generar el código de verificación
		String codVerificacion = fecha.substring(11, 12) + // primer dígito de
				// la hora
				fecha.substring(5, 6) + // primer dígito del mes
				fecha.substring(2, 3) + // tercer dígito del año
				folderNumber.substring(2, 3) + // tercer dígito del id
				entidad.substring(0, 1) + // primer dígito de la entidad

				fecha.substring(12, 13) + // segundo dígito de la hora
				fecha.substring(6, 7) + // segundo dígito del mes
				fecha.substring(3, 4) + // cuarto dígito del año
				folderNumber.substring(1, 2) + // segundo dígito del id
				entidad.substring(1, 2) + // segundo dígito de la entidad

				fecha.substring(14, 15) + // primer dígito de los minutos
				fecha.substring(8, 9) + // primer dígito del día
				fecha.substring(1, 2) + // segundo dígito del año
				folderNumber.substring(3, 4) + // cuarto dígito del id
				entidad.substring(2, 3) + // tercer dígito de la entidad

				fecha.substring(15, 16) + // segundo dígito de los minutos
				fecha.substring(9, 10) + // segundo dígito del día
				fecha.substring(17, 18) + // primer dígito de los segundos
				fecha.substring(18, 19) + // segundo dígito de los segundos
				folderNumber.substring(0, 1);// primer dígito del id

		return codVerificacion;
	}

	/**
	 * Método que transforma el localizor numerico en un localizador
	 * alfanumerico
	 *
	 * @param codVerificacion
	 * @return
	 */
	protected String getAlfanumericCodigoVerificacion(String codVerificacion) {

		String alfaCodVerificacion = codVerificacion;

		// Obtenemos el penúltimo dígito del Código de verificación
		String choice = codVerificacion.substring(18, 19);
		int iChoice = Integer.parseInt(choice);

		switch (iChoice) {
		case 0:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 3,
					getSubstitute(3, codVerificacion.charAt(3), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 5,
					getSubstitute(5, codVerificacion.charAt(5), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 10,
					getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			break;
		case 1:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 1,
					getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 8,
					getSubstitute(8, codVerificacion.charAt(8), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 11,
					getSubstitute(11, codVerificacion.charAt(11), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 13,
					getSubstitute(13, codVerificacion.charAt(13), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 15,
					getSubstitute(15, codVerificacion.charAt(15), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 2:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 10,
					getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 17,
					getSubstitute(17, codVerificacion.charAt(17), iChoice));
			break;
		case 3:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 1,
					getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 4:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 5,
					getSubstitute(5, codVerificacion.charAt(5), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			break;
		case 5:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 10,
					getSubstitute(10, codVerificacion.charAt(10), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 15,
					getSubstitute(15, codVerificacion.charAt(15), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 6:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 3,
					getSubstitute(3, codVerificacion.charAt(3), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 17,
					getSubstitute(17, codVerificacion.charAt(17), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 7:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 6,
					getSubstitute(6, codVerificacion.charAt(6), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 12,
					getSubstitute(12, codVerificacion.charAt(12), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			break;
		case 8:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 0,
					getSubstitute(0, codVerificacion.charAt(0), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 7,
					getSubstitute(7, codVerificacion.charAt(7), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 11,
					getSubstitute(11, codVerificacion.charAt(11), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;
		case 9:
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 1,
					getSubstitute(1, codVerificacion.charAt(1), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 2,
					getSubstitute(2, codVerificacion.charAt(2), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 8,
					getSubstitute(8, codVerificacion.charAt(8), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 13,
					getSubstitute(13, codVerificacion.charAt(13), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 16,
					getSubstitute(16, codVerificacion.charAt(16), iChoice));
			alfaCodVerificacion = replaceChar(alfaCodVerificacion, 19,
					getSubstitute(19, codVerificacion.charAt(19), iChoice));
			break;

		}

		return alfaCodVerificacion;
	}

	/**
	 * Métodos para sustituir digitos por letras
	 *
	 * @param posicion
	 * @param digito
	 * @param choice
	 * @return
	 */
	private char getSubstitute(int posicion, char digito, int choice) {

		final char letras[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z' };

		final List grupo1 = new ArrayList(7);
		final List grupo2 = new ArrayList(6);
		final List grupo3 = new ArrayList(3);

		grupo1.add(new Integer(1));
		grupo1.add(new Integer(3));
		grupo1.add(new Integer(6));
		grupo1.add(new Integer(8));
		grupo1.add(new Integer(11));
		grupo1.add(new Integer(13));
		grupo1.add(new Integer(16));

		grupo2.add(new Integer(2));
		grupo2.add(new Integer(5));
		grupo2.add(new Integer(7));
		grupo2.add(new Integer(12));
		grupo2.add(new Integer(15));
		grupo2.add(new Integer(19));

		grupo3.add(new Integer(0));
		grupo3.add(new Integer(10));
		grupo3.add(new Integer(17));

		int iDigito = Integer.parseInt(Character.toString(digito));
		Integer iPosicion = new Integer(posicion);

		if (grupo1.contains(iPosicion)) {
			if (samePar(iDigito, choice)) {
				// MAYUSCULAS
				return Character.toUpperCase(letras[iDigito]);
			} else {
				// minusculas
				return letras[iDigito];
			}

		} else if (grupo2.contains(iPosicion)) {
			if (samePar(iDigito, choice)) {
				// MAYUSCULAS
				return Character.toUpperCase(letras[10 + iDigito]);
			} else {
				// minusculas
				return letras[10 + iDigito];
			}
		} else if (grupo3.contains(iPosicion)) {
			if (samePar(iDigito, choice)) {
				// MAYUSCULAS
				return Character.toUpperCase(letras[20 + iDigito]);
			} else {
				// minusculas
				return letras[20 + iDigito];
			}
		}

		return digito;
	}

	/**
	 * Método que reemplaza caracteres de una cadena
	 *
	 * @param cadena
	 * @param pos
	 * @param car
	 * @return
	 */
	private String replaceChar(String cadena, int pos, char car) {

		String resultado = "";

		if (pos >= 0 && pos < cadena.length()) {
			int i = 0;
			while (i < pos) {
				resultado += cadena.charAt(i);
				i++;
			}
			resultado += car;
			i++;
			while (i > pos && i < cadena.length()) {
				resultado += cadena.charAt(i);
				i++;
			}
		}

		return resultado;
	}

	/**
	 * Método que comprueba si un par de numero son pares o no
	 *
	 * @param num1
	 * @param num2
	 * @return
	 */
	private boolean samePar(int num1, int num2) {
		boolean p = false;

		if ((num1 % 2 == 0 && num2 % 2 == 0)
				|| (num1 % 2 != 0 && num2 % 2 != 0)) {
			p = true;
		}

		return p;
	}	
}
