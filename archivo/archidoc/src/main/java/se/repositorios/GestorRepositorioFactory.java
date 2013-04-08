package se.repositorios;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.repositorios.exceptions.GestorRepositorioException;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorRepositorioElectronico;

import common.util.StringUtils;

/**
 * Factoría para la creación de conectores con Sistemas Gestores de Repositorio.
 */
public class GestorRepositorioFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorRepositorioFactory.class);

	/**
	 * Obtiene el conector con el Sistema Gestor de Repositorio.
	 * 
	 * @param parametros
	 *            Parametros de configuración
	 * @return Conector con el Sistema Gestor de Repositorio.
	 * @throws GestorRepositorioException
	 *             si ocurre algún error.
	 */
	public static IGestorRepositorio getConnector(Properties parametros)
			throws GestorRepositorioException {
		IGestorRepositorio connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			SistemaGestorRepositorioElectronico sistGestorRep = csa
					.getSistemaGestorRepositorioElectronico();
			if (sistGestorRep == null)
				throw new GestorRepositorioException(
						"No se ha definido un Sistema Gestor de Repositorio Electrónico");

			// Leer la clase que implementa el conector
			String className = sistGestorRep.getClase();
			if (StringUtils.isBlank(className))
				throw new GestorRepositorioException(
						"No se ha encontrado el conector con el Sistema Gestor de Repositorio Electrónico");

			// Instanciar la clase y parametrizarla
			connector = (IGestorRepositorio) Class.forName(className)
					.newInstance();

			Properties params = sistGestorRep.getParametros();
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
				logger.info("GestorRepositorioElectronico: "
						+ connector.getClass().getName());
		} catch (GestorRepositorioException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new GestorRepositorioException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Repositorio Electrónico",
					e);
		}

		return connector;
	}
}
