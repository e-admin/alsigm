package common.security;

import gcontrol.model.NivelAcceso;
import gcontrol.model.TipoAcceso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import util.CollectionUtils;

import common.exceptions.SecurityException;

public abstract class SecurityManagerBase {

	/** Logger de la clase. */
	private Logger logger = Logger.getLogger(SecurityManagerBase.class);

	private HashMap actionsMapped = new HashMap();

	/**
	 * Constructor por defecto
	 */
	SecurityManagerBase() {
	}

	protected void addAction(ActionObject action, String[][] permits) {
		actionsMapped.put(action, permits);
	}

	public void check(ActionObject action, ServiceClient serviceClient)
			throws SecurityException {

		log("Realizando chequeo de seguridad...");

		if (serviceClient == null)
			throw new SecurityException(
					"Se ha intentando realizar una operación no permitida");

		if (!serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {

			// Obtenemos los permisos del usuario
			String[] permisos = serviceClient.getPermissions();
			// Obtenemos los permisos necesarios(minimos) de la accion
			String[][] actionPermisions = (String[][]) actionsMapped
					.get(action);

			// Comprobamos que tenemos todos los permisos en alguna de las
			// posibles filas de permisos
			int j = 0;
			Arrays.sort(permisos);
			if (actionPermisions != null)
				for (int i = 0; i < actionPermisions.length; i++) {
					for (j = 0; j < actionPermisions[i].length; j++) {
						boolean noEncontrado = Arrays.binarySearch(permisos,
								actionPermisions[i][j]) < 0;
						if (noEncontrado)
							break;
					}

					if (j == actionPermisions[i].length) {
						log("Terminado chequeo de seguridad.");
						return;
					}
				}
			else
				throw new SecurityException(
						"Permiso no definidos para esta accion");

			log("Terminado chequeo de seguridad...");

			// Excepción de seguridad
			SecurityException se = new SecurityException(
					"Se ha intentando realizar una operación no permitida");
			se.setPermission(actionPermisions);
			throw se;
		}
	}

	/**
	 * Comprueba si el usuario tiene acceso al objeto.
	 * 
	 * @param serviceClient
	 *            Cliente de servicio.
	 * @param accessType
	 *            Tipo de acceso ({@link TipoAcceso}).
	 * @param accessLevel
	 *            Nivel de acceso ({@link NivelAcceso}).
	 * @param custodyArchiveId
	 *            Identificador del archivo de custodia.
	 * @param aclId
	 *            Identificador de la lista de control de acceso.
	 * @return true si el usuario tiene acceso al objeto, false en caso
	 *         contrario.
	 */
	public static boolean isAccessAllowed(ServiceClient serviceClient,
			int accessType, int accessLevel, String custodyArchiveId,
			String aclId) {
		// Comprobar si el usuario tiene permiso genérico de
		// "Administrador Total del Sistema"
		if (serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
			return true;

		// Comprobar si el usuario tiene permiso genérico de
		// "Consulta Total del Sistema"
		// y el tipo de acceso es de consulta
		if ((accessType == TipoAcceso.CONSULTA)
				&& serviceClient
						.hasPermission(AppPermissions.CONSULTA_TOTAL_SISTEMA))
			return true;

		// Comprobar si el nivel de acceso es PUBLICO
		if (accessLevel == NivelAcceso.PUBLICO)
			return true;

		// Comprobar si el nivel de acceso es ARCHIVO y el usuario pertenece
		// a algún archivo
		if ((accessLevel == NivelAcceso.ARCHIVO) && (custodyArchiveId != null)
				&& serviceClient.belongsToCustodyArchive(custodyArchiveId))
			return true;

		// Comprobar si el nivel de acceso es ARCHIVO o RESTRINGIDO
		// y el usuario pertenece a la lista de control de acceso
		if (((accessLevel == NivelAcceso.ARCHIVO) || (accessLevel == NivelAcceso.RESTRINGIDO))
				&& (aclId != null) && serviceClient.hasAccessControlList(aclId))
			return true;

		return false;
	}

	/**
	 * Comprueba si el usuario tiene acceso al objeto.
	 * 
	 * @param serviceClient
	 *            Cliente de servicio.
	 * @param accessType
	 *            Tipo de acceso ({@link TipoAcceso}).
	 * @param accessLevel
	 *            Nivel de acceso ({@link NivelAcceso}).
	 * @param custodyArchiveId
	 *            Identificador del archivo de custodia.
	 * @param aclId
	 *            Identificador de la lista de control de acceso.
	 * @return true si el usuario tiene acceso al objeto, false en caso
	 *         contrario.
	 */
	public static boolean isAccessAllowed(ServiceClient serviceClient,
			int accessType, int accessLevel) {
		// Comprobar si el usuario tiene permiso genérico de
		// "Administrador Total del Sistema"
		if (serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
			return true;

		// Comprobar si el usuario tiene permiso genérico de
		// "Consulta Total del Sistema"
		// y el tipo de acceso es de consulta
		if ((accessType == TipoAcceso.CONSULTA)
				&& serviceClient
						.hasPermission(AppPermissions.CONSULTA_TOTAL_SISTEMA))
			return true;

		// Comprobar si el nivel de acceso es PUBLICO
		if (accessLevel == NivelAcceso.PUBLICO)
			return true;

		// Comprobar si el nivel de acceso es ARCHIVO y el usuario pertenece
		// a algún archivo
		if (accessLevel == NivelAcceso.ARCHIVO
				&& serviceClient.isPersonalArchivo())
			return true;

		return false;
	}

	/**
	 * Comprueba si el usuario tiene permiso específico para acceder al objeto.
	 * 
	 * @param serviceClient
	 *            Cliente de servicio.
	 * @param accessType
	 *            Tipo de acceso ({@link TipoAcceso}).
	 * @param accessLevel
	 *            Nivel de acceso ({@link NivelAcceso}).
	 * @param custodyArchiveId
	 *            Identificador del archivo de custodia.
	 * @param aclId
	 *            Identificador de la lista de control de acceso.
	 * @param specificPerm
	 *            Permiso específico.
	 * @return true si el usuario tiene acceso al objeto, false en caso
	 *         contrario.
	 */
	public static boolean isAccessAllowed(ServiceClient serviceClient,
			int accessType, int accessLevel, String custodyArchiveId,
			String aclId, String specificPerm) {
		// Comprobar si el usuario tiene permiso genérico de
		// "Administrador Total del Sistema"
		if (serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
			return true;

		// Comprobar si el usuario tiene permiso genérico de
		// "Consulta Total del Sistema"
		// y el tipo de acceso es de consulta
		if ((accessType == TipoAcceso.CONSULTA)
				&& serviceClient
						.hasPermission(AppPermissions.CONSULTA_TOTAL_SISTEMA))
			return true;

		// Comprobar si el nivel de acceso es PUBLICO
		if (accessLevel == NivelAcceso.PUBLICO)
			return true;

		// Comprobar si el nivel de acceso es ARCHIVO y el usuario pertenece
		// a algún archivo
		if ((accessLevel == NivelAcceso.ARCHIVO) && (custodyArchiveId != null)
				&& serviceClient.belongsToCustodyArchive(custodyArchiveId))
			return true;

		// Comprobar si el nivel de acceso es ARCHIVO o RESTRINGIDO
		// y el usuario tiene el permiso específico
		if (((accessLevel == NivelAcceso.ARCHIVO) || (accessLevel == NivelAcceso.RESTRINGIDO))
				&& (aclId != null) && serviceClient.hasAccessControlList(aclId)) {
			// Comprobar el permiso específico de edición
			List permisosEspecificos = (List) serviceClient.getAcls()
					.get(aclId);
			if (!CollectionUtils.isEmpty(permisosEspecificos)
					&& permisosEspecificos.contains(specificPerm))
				return true;
		}

		return false;
	}

	private void log(String message) {
		if (logger.isDebugEnabled())
			logger.debug(message);

	}

}
