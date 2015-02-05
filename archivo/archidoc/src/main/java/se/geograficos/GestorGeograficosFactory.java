package se.geograficos;

import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import se.geograficos.exceptions.GestorGeograficosException;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorGeograficos;

import common.util.StringUtils;

/**
 * Factoría para la creación de conectores con Gestores Geográficos.
 */
public class GestorGeograficosFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorGeograficosFactory.class);

	/**
	 * Obtiene el conector con el Gestor de Geográficos.
	 * 
	 * @param parametros
	 *            Propiedades adicionales del conector
	 * @return Conector con el Gestor de Geográficos.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public static GestorGeograficos getConnector(Properties parametros)
			throws GestorGeograficosException {
		GestorGeograficos connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			SistemaGestorGeograficos sistema = csa
					.getSistemaGestorGeograficos();
			if (sistema == null)
				throw new GestorGeograficosException(
						"No se ha definido el Sistema Gestor de Geogr\u00E1ficos");

			// Leer la clase que implementa el conector
			String className = sistema.getClase();
			if (StringUtils.isBlank(className))
				throw new GestorGeograficosException(
						"No se ha encontrado el conector con el Sistema Gestor de Geogr\u00E1ficos");

			// Instanciar la clase y parametrizarla
			connector = (GestorGeograficos) Class.forName(className)
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
				logger.info("GestorGeograficos: "
						+ connector.getClass().getName());
		} catch (GestorGeograficosException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new GestorGeograficosException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Geogr\u00E1ficos",
					e);
		}

		return connector;
	}
}
