package es.ieci.tecdoc.fwktd.sir.api.manager.impl.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.core.exception.SIRException;
import es.ieci.tecdoc.fwktd.sir.core.types.ErroresEnum;

public class WSSIRHelper {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(WSSIRHelper.class);

	/**
	 * Formateador de fechas.
	 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * Formateador de fechas de trazabilidad
	 */
	private static final SimpleDateFormat SDF_TRAZABILIDAD = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * Comprobar la respuesta del servicio web.
	 * @param codigo Código de respuesta del servicio web.
	 * @param descripcion Descripción de la respuesta del servicio web.
	 */
	public static void checkRespuesta(String codigo, String descripcion) {
		if (!ErroresEnum.OK.getValue().equals(codigo)) {
			logger.error("Respuesta: codigo=[{}], descripción=[{}]", codigo,
					descripcion);
			throw new SIRException("error.sir." + codigo, new String[] {
					codigo, descripcion }, descripcion);
		}
	}

	/**
	 * Parsea una fecha.
	 *
	 * @param cadena
	 *            Cadena con la fecha.
	 * @return Información de la fecha.
	 */
	public static Date parseDate(String cadena) {
		Date fecha = null;

		if (StringUtils.isNotBlank(cadena)) {
			try {
				fecha = SDF.parse(cadena);
			} catch (ParseException e) {
				logger.warn("Error al parsear la fecha [" + cadena + "] con el formato [" + SDF.toPattern() + "]", e);
			}
		}

		return fecha;
	}

	/**
	 * Parsea una fecha del servicio de trazabilidad
	 *
	 * @param cadena
	 *            Cadena con la fecha.
	 * @return Información de la fecha.
	 */
	public static Date parseDateTrazabilidad(String cadena) {
		Date fecha = null;

		if (StringUtils.isNotBlank(cadena)) {
			try {
				fecha = SDF_TRAZABILIDAD.parse(cadena);
			} catch (ParseException e) {
				logger.warn("Error al parsear la fecha [" + cadena + "] con el formato [" + SDF_TRAZABILIDAD.toPattern() + "]", e);
			}
		}

		return fecha;
	}

	/**
	 * Parsea un booleano.
	 * 
	 * @param cadena
	 *            Cadena con el booleano.
	 * @param defaultValue
	 *            Valor por defecto.
	 * @return boolean
	 */
	public static boolean parseBoolean(String cadena, boolean defaultValue) {

		boolean bool = defaultValue;

		if (StringUtils.isNotBlank(cadena)) {
			bool = Boolean.parseBoolean(cadena);
		}

		return bool;
	}
	
	/**
	 * Formatea una fecha.
	 * @param fecha Fecha
	 * @return Cadena con la fecha.
	 */
	public static String formatDate(Date fecha) { 
		String cadena = null;

		if (fecha != null) {
			cadena = SDF.format(fecha);
		}

		return cadena;
	}

	/**
	 * Formatea una fecha para el servicio de trazabilidad.
	 * @param fecha Fecha
	 * @return Cadena con la fecha.
	 */
	public static String formatDateTrazabilidad(Date fecha) { 
		String cadena = null;

		if (fecha != null) {
			cadena = SDF_TRAZABILIDAD.format(fecha);
		}

		return cadena;
	}

}
