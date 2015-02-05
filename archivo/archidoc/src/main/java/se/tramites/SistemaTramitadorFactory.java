package se.tramites;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.tramites.exceptions.SistemaTramitadorException;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Sistema;

import common.util.StringUtils;

/**
 * Factoría para la creación de conectores con Sistemas Tramitadores.
 */
public class SistemaTramitadorFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(SistemaTramitadorFactory.class);

	/**
	 * Obtiene el conector con el Sistema Tramitador.
	 * 
	 * @param id
	 *            Identificador del Sistema Tramitador.
	 * @param parametros
	 *            Parámetros de configuración
	 * @return Conector con el Sistema Gestor de Tramitador.
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 */
	public static SistemaTramitador getConnector(String id,
			Properties parametros) throws SistemaTramitadorException {
		SistemaTramitador connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			Sistema sistema = csa.findSistemaTramitadorById(id);
			if (sistema == null)
				throw new SistemaTramitadorException(
						"No se ha definido el Sistema Tramitador");

			// Leer la clase que implementa el conector
			String className = sistema.getClase();
			if (StringUtils.isBlank(className))
				throw new SistemaTramitadorException(
						"No se ha encontrado el conector con el Sistema Tramitador");

			// Instanciar la clase y parametrizarla
			connector = (SistemaTramitador) Class.forName(className)
					.newInstance();

			Properties params = sistema.getParametros();
			if (params == null) {
				params = new Properties();
			}

			if ((parametros != null) && (!parametros.isEmpty())) {
				Iterator it = parametros.entrySet().iterator();
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					params.put(entry.getKey(), entry.getValue());
				}
			}

			connector.initialize(params);

			if (logger.isInfoEnabled())
				logger.info("SistemaTramitador: "
						+ connector.getClass().getName());
		} catch (SistemaTramitadorException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new SistemaTramitadorException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Tramitador",
					e);
		}

		return connector;
	}
}
