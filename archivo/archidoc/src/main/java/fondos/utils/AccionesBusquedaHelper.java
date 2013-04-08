package fondos.utils;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;

import se.usuarios.ServiceClient;
import xml.config.AccionBusqueda;
import xml.config.ConfiguracionArchivoManager;
import xml.config.RestriccionTipoAccionBusqueda;
import xml.config.TipoAccionBusqueda;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.util.StringUtils;

import fondos.model.RestriccionesTipoAccionBusqueda;

/**
 * 
 * Utilidad para realizar todo tipo de acciones sobre la busqueda.
 */
public class AccionesBusquedaHelper {

	public static ActionErrors check(ServiceClient sc,
			GestionCuadroClasificacionBI cuadroClasificacionBI, String[] ids,
			String accion) throws Exception {
		ActionErrors errors = new ActionErrors();
		AccionBusqueda accionBusqueda = ConfiguracionArchivoManager
				.getInstance().getAccionBusqueda(accion);
		if (accionBusqueda != null) {
			if (ids != null) {
				if (ids.length == 1
						|| checkElementosMismoTipo(cuadroClasificacionBI, ids)) {
					int tipo = cuadroClasificacionBI.getTipoElemento(ids[0]);
					TipoAccionBusqueda tipoAccionBusqueda = accionBusqueda
							.getTipoPermitido(String.valueOf(tipo));
					if (tipo == -1 || tipoAccionBusqueda == null) {
						errors.add(
								ActionMessages.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_TIPO_ACCION_BUSQUEDA_NO_ENCONTRADA));
					} else {
						Map restricciones = tipoAccionBusqueda
								.getMapRestricciones();
						if (restricciones.size() > 0) {
							checkRestricciones(sc, cuadroClasificacionBI,
									errors, ids, restricciones);
						}
					}
				} else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_ELEMENTOS_DISTINTO_TIPO));
				}
			}
		} else
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_ACCION_BUSQUEDA_NO_ENCONTRADA));
		return errors;
	}

	private static void checkRestricciones(ServiceClient sc,
			GestionCuadroClasificacionBI cuadroClasificacionBI,
			ActionErrors errors, String[] ids, Map restricciones) {
		for (Iterator iter = restricciones.keySet().iterator(); iter.hasNext();) {
			String restriccion = (String) iter.next();
			RestriccionTipoAccionBusqueda value = (RestriccionTipoAccionBusqueda) restricciones
					.get(restriccion);
			if (RestriccionesTipoAccionBusqueda.RESTRICCION_VARIOS_ELEMENTOS
					.equals(restriccion)) {
				if (!checkVariosElementosSeleccionados(ids,
						value.isRestriccionPermitida())) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_SOLO_SELECCION_UN_ELEMENTO));
					break;
				}
			}
			if (RestriccionesTipoAccionBusqueda.RESTRICCION_DISTINTO_PADRE
					.equals(restriccion)) {
				if (!checkElementosDistintoPadre(cuadroClasificacionBI, ids,
						value.isRestriccionPermitida())) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_ELEMENTOS_DISTINTO_ARCHIVO));
					break;
				}
			}

			if (RestriccionesTipoAccionBusqueda.RESTRICCION_DISTINTO_ARCHIVO
					.equals(restriccion)) {
				if (!checkElementosArchivo(cuadroClasificacionBI, ids,
						value.isRestriccionPermitida())) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_ELEMENTOS_DISTINTO_ARCHIVO));
					break;
				}
			}

			if (RestriccionesTipoAccionBusqueda.RESTRICCION_PERMISOS
					.equals(restriccion)) {

				String permisos = value.getPermitido();

				if (StringUtils.isNotEmpty(permisos)) {
					if (!checkPermisos(sc, permisos)) {
						errors.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_ACCION_SIN_PERMISOS));
						break;
					}
				}
			}

		}
	}

	private static boolean checkVariosElementosSeleccionados(String[] ids,
			boolean permitidoMultipleSeleccion) {
		if (!permitidoMultipleSeleccion && ids != null && ids.length > 1)
			return false;
		return true;
	}

	private static boolean checkElementosMismoTipo(
			GestionCuadroClasificacionBI cuadroClasificacionBI, String[] ids) {
		return cuadroClasificacionBI.checkElementosMismoTipo(ids);
	}

	private static boolean checkElementosDistintoPadre(
			GestionCuadroClasificacionBI cuadroClasificacionBI, String[] ids,
			boolean permitidoDistintoPadre) {
		return cuadroClasificacionBI.checkElementosDistintoPadre(ids,
				permitidoDistintoPadre);
	}

	private static boolean checkElementosArchivo(
			GestionCuadroClasificacionBI cuadroClasificacionBI, String[] ids,
			boolean permitidoDistintoArchivo) {
		if (permitidoDistintoArchivo) {
			return true;
		}
		return cuadroClasificacionBI.checkElementosMismoArchivo(ids);
	}

	private static boolean checkPermisos(ServiceClient sc, String permisos) {

		if (permisos != null) {
			String[] rights = permisos.split(Constants.COMMA);

			if (sc.hasAnyPermission(rights)) {
				return true;
			}
		}
		return false;
	}

	public static boolean accionPermitida(ServiceClient sc,
			AccionBusqueda accionBusqueda) {
		if (accionBusqueda != null) {
			String permisos = accionBusqueda.getPermisos();
			// Si no hay permisos defindos se permite la accion.

			if (StringUtils.isEmpty(permisos)
					|| checkPermisos(sc, accionBusqueda.getPermisos())) {
				return true;
			}
		}

		return false;
	}

	public static String getForwardActionRedirect(
			GestionCuadroClasificacionBI cuadroClasificacionBI, String[] ids,
			String accion, HttpServletRequest request) throws Exception {
		AccionBusqueda accionBusqueda = ConfiguracionArchivoManager
				.getInstance().getAccionBusqueda(accion);

		// Obtener el Id del Elemento
		String id = ids[0];

		int tipo = cuadroClasificacionBI.getTipoElemento(id);

		TipoAccionBusqueda tipoAccionBusqueda = accionBusqueda
				.getTipoPermitido(String.valueOf(tipo));

		return tipoAccionBusqueda.getForward();
	}
}