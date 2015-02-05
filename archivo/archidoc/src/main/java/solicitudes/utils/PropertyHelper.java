package solicitudes.utils;

import org.apache.log4j.Logger;

import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.exceptions.ConfigException;

/**
 * Clase que facilita el acceso al fichero de propiedades
 */
public class PropertyHelper {

	private static Logger logger = Logger.getLogger(PropertyHelper.class);

	public final static int PLAZO_PRESTAMO = 1;
	public final static int PLAZO_TRAS_RECLAMACION = 2;
	public final static int PLAZO_RESERVA = 3;
	public final static int PLAZO_PRORROGA = 4;
	public final static int PLAZO_CONSULTA = 5;
	public final static int PLAZO_CONSULTA_RESERVA = 6;

	public static String getProperty(int property) {
		String value = null;
		ConfiguracionSistemaArchivo csa = null;
		try {
			csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
		} catch (Exception e) {
			logger.error(
					"Se ha producido un error recuperando la propiedades del fichero",
					e);

			throw new ConfigException(e,
					ConfiguracionSistemaArchivoFactory.class.getName(),
					"Error al leer la configuración del sistema de archivo");
		}

		switch (property) {
		case PLAZO_PRESTAMO:
			value = csa.getConfiguracionServicios().getPlazoPrestamo();
			break;
		case PLAZO_TRAS_RECLAMACION:
			value = csa.getConfiguracionServicios().getPlazoTrasReclamacion();
			break;
		case PLAZO_RESERVA:
			value = csa.getConfiguracionServicios().getPlazoReserva();
			break;
		case PLAZO_PRORROGA:
			value = csa.getConfiguracionServicios().getPlazoProrroga();
			break;
		case PLAZO_CONSULTA:
			value = "0";
			break;
		case PLAZO_CONSULTA_RESERVA:
			value = "0";
			break;
		default:
			break;
		}

		return value;
	}
}
