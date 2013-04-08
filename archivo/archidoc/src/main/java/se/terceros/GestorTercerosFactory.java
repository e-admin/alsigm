package se.terceros;

import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import se.terceros.exceptions.GestorTercerosException;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorTerceros;

import common.util.StringUtils;

/**
 * Factoría para la creación de conectores con Gestores de Terceros.
 */
public class GestorTercerosFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorTercerosFactory.class);

	/**
	 * Obtiene el conector con el Gestor de Terceros.
	 * 
	 * @param params
	 *            Parámetros del conector
	 * @return Conector con el Gestor de Terceros.
	 * @throws GestorTercerosException
	 *             si ocurre algún error.
	 */
	public static GestorTerceros getConnector(Properties parametros)
			throws GestorTercerosException {
		GestorTerceros connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			SistemaGestorTerceros sistema = csa.getSistemaGestorTerceros();
			if (sistema == null)
				throw new GestorTercerosException(
						"No se ha definido el Sistema Gestor de Terceros");

			// Leer la clase que implementa el conector
			String className = sistema.getClase();
			if (StringUtils.isBlank(className))
				throw new GestorTercerosException(
						"No se ha encontrado el conector con el Sistema Gestor de Terceros");

			// Instanciar la clase y parametrizarla
			connector = (GestorTerceros) Class.forName(className).newInstance();

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
				logger.info("GestorTerceros: " + connector.getClass().getName());
		} catch (GestorTercerosException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new GestorTercerosException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Terceros",
					e);
		}

		return connector;
	}
}
