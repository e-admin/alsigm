package se.procedimientos;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.procedimientos.exceptions.GestorCatalogoException;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorCatalogo;

import common.util.StringUtils;

/**
 * Factoría para la creación de conectores con Sistemas Gestores de Catálogos.
 */
public class GestorCatalogoFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorCatalogoFactory.class);

	/**
	 * Obtiene el conector con el Sistema Gestor de Catálogo.
	 * 
	 * @param parametros
	 *            Parámetros de configuración.
	 * @return Conector con el Sistema Gestor de Catálogo.
	 * @throws GestorCatalogoException
	 *             si ocurre algún error.
	 */
	public static GestorCatalogo getConnector(Properties parametros)
			throws GestorCatalogoException {
		GestorCatalogo connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			SistemaGestorCatalogo sistGestorCat = csa
					.getSistemaGestorCatalogo();
			if (sistGestorCat == null)
				throw new GestorCatalogoException(
						"No se ha definido un Sistema Gestor de Cat\u00E1logo");

			// Leer la clase que implementa el conector
			String className = sistGestorCat.getClase();
			if (StringUtils.isBlank(className))
				throw new GestorCatalogoException(
						"No se ha encontrado el conector con el Sistema Gestor de Cat\u00E1logo");

			// Instanciar la clase y parametrizarla
			connector = (GestorCatalogo) Class.forName(className).newInstance();

			Properties params = sistGestorCat.getParametros();
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
				logger.info("GestorCatalogo: " + connector.getClass().getName());
		} catch (GestorCatalogoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new GestorCatalogoException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Cat\u00E1logo",
					e);
		}

		return connector;
	}
}
