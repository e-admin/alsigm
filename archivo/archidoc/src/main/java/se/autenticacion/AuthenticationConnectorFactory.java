package se.autenticacion;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

import xml.config.ConfiguracionControlAcceso;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.Sistema;
import xml.config.Usuario;

import common.Constants;
import common.util.StringUtils;

/**
 * Factoría para la creación de conectores de autenticación de usuarios.
 */
public class AuthenticationConnectorFactory {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(AuthenticationConnectorFactory.class);

	/**
	 * Obtiene el conector de autenticación de usuarios.
	 * 
	 * @param userType
	 *            Tipo de usuario
	 * @param superuser
	 *            Indica si se intenta entrar como superusuario
	 * @param parametros
	 *            Parámetros del conector
	 * @return Conector de autenticación de usuarios.
	 */
	public static AuthenticationConnector getConnector(String userType,
			String superuser, Properties parametros) {
		AuthenticationConnector connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();

			// Clase que implementa la autenticación contra el sistema externo
			String className = null;

			// Parámetros para inicializar la clase de autenticación
			Properties params = null;

			if (superuser == null
					|| !superuser.equalsIgnoreCase(Constants.CHECKED_STRING)) {
				Usuario usuario = csa.getConfiguracionControlAcceso()
						.findUsuarioByTipo(userType);
				if (usuario == null)
					throw new AuthenticationConnectorException(
							"El tipo de usuario no es v\u00E1lido");

				Sistema sistGestorUsuarios = csa
						.findSistemaGestorUsuariosById(usuario
								.getIdSistGestorUsr());

				if (sistGestorUsuarios == null)
					throw new AuthenticationConnectorException(
							"No se ha encontrado un Sistema Gestor de Usuarios asociado al tipo de usuario");

				className = sistGestorUsuarios.getClase();

				params = sistGestorUsuarios.getParametros();
			} else {
				// Si el usuario ha elegido entrar como superusuario, es
				// necesario utilizar la clase definida en el fichero de
				// configuración para la autenticación
				className = ConfiguracionControlAcceso.SUPERUSER_AUTHENTICATION_CLASS;
			}

			if (StringUtils.isBlank(className))
				throw new AuthenticationConnectorException(
						"No se ha encontrado el conector con el Sistema Gestor de Usuarios");

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

			// Instanciar la clase y parametrizarla
			connector = (AuthenticationConnector) Class.forName(className)
					.newInstance();
			connector.initialize(params);

			if (logger.isInfoEnabled())
				logger.info("AuthenticationConnector: "
						+ connector.getClass().getName());
		} catch (AuthenticationConnectorException e) {
			throw e;
		} catch (Exception e) {
			logger.error(
					"Error interno en la creaci\u00F3n del conector para el tipo de usuario: "
							+ userType, e);
			throw new AuthenticationConnectorException(e,
					AuthenticationConnectorFactory.class.getName(),
					"Error interno en la creaci\u00F3n del conector para la autenticaci\u00F3n");
		} catch (Throwable t) {
			logger.error(
					"Error interno en la creaci\u00F3n del conector para el tipo de usuario: "
							+ userType, t);
			throw new AuthenticationConnectorException(
					"Error interno en la creaci\u00F3n del conector para la autenticaci\u00F3n: "
							+ t.toString());
		}

		return connector;
	}

	/**
	 * Obtiene el conector de autenticación de usuarios.
	 * 
	 * @param userType
	 *            Tipo de usuario
	 * @param parametros
	 *            Parámetros del conector
	 * @return Conector de autenticación de usuarios.
	 */
	public static AuthenticationConnector getConnector(String userType,
			Properties parametros) {
		AuthenticationConnector connector = null;

		try {
			// Configuración de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			Usuario usuario = csa.getConfiguracionControlAcceso()
					.findUsuarioByTipo(userType);
			if (usuario == null)
				throw new AuthenticationConnectorException(
						"El tipo de usuario no es v\u00E1lido");

			Sistema sistGestorUsuarios = csa
					.findSistemaGestorUsuariosById(usuario.getIdSistGestorUsr());
			if (sistGestorUsuarios == null)
				throw new AuthenticationConnectorException(
						"No se ha encontrado un Sistema Gestor de Usuarios asociado al tipo de usuario");

			String className = sistGestorUsuarios.getClase();
			if (StringUtils.isBlank(className))
				throw new AuthenticationConnectorException(
						"No se ha encontrado el conector con el Sistema Gestor de Usuarios");

			// Instanciar la clase y parametrizarla
			connector = (AuthenticationConnector) Class.forName(className)
					.newInstance();

			Properties params = sistGestorUsuarios.getParametros();
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
				logger.info("AuthenticationConnector: "
						+ connector.getClass().getName());
		} catch (AuthenticationConnectorException e) {
			throw e;
		} catch (Exception e) {
			logger.error(
					"Error interno en la creaci\u00F3n del conector para el tipo de usuario: "
							+ userType, e);
			throw new AuthenticationConnectorException(e,
					AuthenticationConnectorFactory.class.getName(),
					"Error interno en la creaci\u00F3n del conector para la autenticaci\u00F3n");
		} catch (Throwable t) {
			logger.error(
					"Error interno en la creaci\u00F3n del conector para el tipo de usuario: "
							+ userType, t);
			throw new AuthenticationConnectorException(
					"Error interno en la creaci\u00F3n del conector para la autenticaci\u00F3n: "
							+ t.toString());
		}

		return connector;
	}

	/**
	 * Metodo que devuelve el conector correspondiente al sistema de gestión de
	 * usuarios externo seleccionado.
	 * 
	 * @param idSistema
	 *            String
	 * @return AuthenticationConnector con el conector seleccionado.
	 */
	public static AuthenticationConnector getConnectorById(String idSistema,
			Properties parametros) {
		AuthenticationConnector connector = null;

		try {
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			Sistema sistGestorUsuarios = csa
					.findSistemaGestorUsuariosById(idSistema);
			String className = sistGestorUsuarios.getClase();

			if (StringUtils.isBlank(className))
				throw new AuthenticationConnectorException(
						"No se ha encontrado el conector con el Sistema Gestor de Usuarios");

			// Instanciar la clase y parametrizarla
			connector = (AuthenticationConnector) Class.forName(className)
					.newInstance();

			Properties params = sistGestorUsuarios.getParametros();
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
				logger.info("AuthenticationConnector: "
						+ connector.getClass().getName());
		} catch (AuthenticationConnectorException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error interno en la creaci\u00F3n del conector", e);
			throw new AuthenticationConnectorException(e,
					AuthenticationConnectorFactory.class.getName(),
					"Error interno en la creaci\u00F3n del conector para la autenticaci\u00F3n");
		} catch (Throwable t) {
			logger.error("Error interno en la creaci\u00F3n del conector", t);
			throw new AuthenticationConnectorException(
					"Error interno en la creaci\u00F3n del conector para la autenticaci\u00F3n: "
							+ t.toString());
		}

		return connector;
	}
}
