package se.instituciones;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.instituciones.exceptions.GestorOrganismosException;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Sistema;
import xml.config.Usuario;

import common.util.StringUtils;

/**
 * Factoría para la creación de conectores con Sistemas Gestores de
 * Organización.
 */
public class GestorOrganismosFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GestorOrganismosFactory.class);

	/**
	 * Obtiene el conector con el Sistema Gestor de Organización.
	 * 
	 * @param userType
	 *            Tipo de usuario.
	 * @param parametros
	 *            Parámetros del conector
	 * @return Conector con el Sistema Gestor de Organización.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public static GestorOrganismos getConnectorByUserType(String userType,
			Properties parametros) throws GestorOrganismosException {
		GestorOrganismos connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			Usuario usuario = csa.getConfiguracionControlAcceso()
					.findUsuarioByTipo(userType);
			if (usuario == null)
				throw new GestorOrganismosException(
						"El tipo de usuario no es v\u00E1lido");

			// Leer la información del Sistema Gestor de Organización
			if (StringUtils.isNotBlank(usuario.getIdSistGestorOrg()))
				connector = getConnectorById(usuario.getIdSistGestorOrg(),
						parametros);
			else
				logger.info("El usuario no tiene Sistema Gestor de Organizaci\u00F3n asociado");
		} catch (GestorOrganismosException e) {
			throw e;
		} catch (Exception e) {
			logger.error(
					"Error interno en la creaci\u00F3n del conector para el tipo de usuario: "
							+ userType, e);
			throw new GestorOrganismosException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Organizaci\u00F3n",
					e);
		}

		return connector;
	}

	/**
	 * Obtiene el conector con el Sistema Gestor de Organización.
	 * 
	 * @param id
	 *            Identificador del Sistema Gestor de Organización.
	 * @param parametros
	 *            Parámetros del conector
	 * @return Conector con el Sistema Gestor de Organización.
	 * @throws GestorOrganismosException
	 *             si ocurre algún error.
	 */
	public static GestorOrganismos getConnectorById(String id,
			Properties parametros) throws GestorOrganismosException {
		GestorOrganismos connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();

			// Sistema Gestor de Organización
			Sistema sistGestorOrg = csa.findSistemaGestorOrganismosById(id);
			if (sistGestorOrg == null)
				throw new GestorOrganismosException(
						"No se ha encontrado el Sistema Gestor de Organizaci\u00F3n");

			// Obtener el nombre de la clase
			String className = sistGestorOrg.getClase();
			if (StringUtils.isBlank(className))
				throw new GestorOrganismosException(
						"No se ha encontrado el conector con el Sistema Gestor de Organizaci\u00F3n");

			// Instanciar la clase y parametrizarla
			connector = (GestorOrganismos) Class.forName(className)
					.newInstance();

			Properties params = sistGestorOrg.getParametros();
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
				logger.info("GestorOrganismos: "
						+ connector.getClass().getName());
		} catch (GestorOrganismosException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector: "
					+ id, e);
			throw new GestorOrganismosException(
					"Error interno en la creaci\u00F3n del conector con el Sistema Gestor de Organizaci\u00F3n",
					e);
		}

		return connector;
	}
}
