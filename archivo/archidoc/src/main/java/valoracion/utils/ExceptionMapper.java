package valoracion.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import valoracion.ValoracionConstants;
import valoracion.exceptions.EliminacionActionNotAllowedException;
import valoracion.exceptions.ValoracionActionNotAllowedException;
import auditoria.ArchivoErrorCodes;

import common.Constants;
import common.Messages;

/**
 * Clase de utilidad que se encarga de mapear exception a mesages del fichero de
 * recursos para mostrarlos al usuario
 */
public class ExceptionMapper {
	/**
	 * Genera los errores en un ActionErrors a partir de la exception producida
	 * en el método
	 * 
	 * @param e
	 *            Exception producida en el método de envio de errores.
	 * @return {@link ActionErrors} con la descripción del error
	 */
	public static ActionErrors getErrorsExcepcion(HttpServletRequest request,
			ValoracionActionNotAllowedException e) {
		ActionErrors errores = new ActionErrors();

		switch (e.getCodError()) {
		case ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XESTADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_VALORACION_NO_CREABLE_XESTADO_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XUSUARIO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_VALORACION_NO_CREABLE_XUSUARIO_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_NAME,
									request.getLocale())));
			break;
		default:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									Constants.ERROR_ACCION_NO_PERMITIDA,
									request.getLocale())));
			break;
		}

		return errores;
	}

	/**
	 * Genera los errores en un ActionErrors a partir de la exception producida
	 * en el método
	 * 
	 * @param e
	 *            Exception producida en el método de envio de errores.
	 * @return {@link ActionErrors} con la descripción del error
	 */
	public static ActionErrors getErrorsExcepcion(HttpServletRequest request,
			EliminacionActionNotAllowedException e) {
		ActionErrors errores = new ActionErrors();

		switch (e.getCodError()) {
		case ArchivoErrorCodes.ERROR_ELIMINACION_NO_CREABLE_XESTADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_ELIMINACION_NO_CREABLE_XESTADO_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ELIMINACION_NO_EJECUTABLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_ELIMINACION_NO_EJECUTABLE_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ELIMINACION_NO_DESTRUIBLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_ELIMINACION_NO_DESTRUIBLE_NAME,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ELIMINACION_NO_FINALIZABLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ValoracionConstants.ERROR_ELIMINACION_NO_FINALIZABLE_NAME,
									request.getLocale())));
			break;
		default:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									Constants.ERROR_ACCION_NO_PERMITIDA,
									request.getLocale())));
			break;
		}

		return errores;
	}

	/**
	 * Obtiene los mensajes de error de las excepciones de la eliminación.
	 * 
	 * @param e
	 *            {@link EliminacionActionNotAllowedException}
	 * @return String con el mensaje de error
	 */
	public static String getErrorsMessage(EliminacionActionNotAllowedException e) {
		String msg = null;
		switch (e.getCodError()) {
		case ArchivoErrorCodes.ERROR_ELIMINACION_DESTRUCCION_NO_REALIZADA:
			msg = Messages
					.getString(ValoracionConstants.ERROR_ELIMINACION_NO_DESTRUCCION_NAME);
			break;
		case ArchivoErrorCodes.ERROR_ELIMINACION_THREAD_EJECUTANDOSE:
			msg = Messages
					.getString(ValoracionConstants.ERROR_ELIMINACION_THREAD_EJECUTANDOSE_NAME);
			break;
		}
		return msg;
	}
}
