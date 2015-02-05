package solicitudes.consultas.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import auditoria.ArchivoErrorCodes;

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
			ConsultaActionNotAllowedException e) {
		ActionErrors errores = new ActionErrors();

		switch (e.getCodError()) {
		case ArchivoErrorCodes.ERROR_CREACIONCONSULTA_XOTRA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.CONSULTA_NO_PUEDE_SER_CREADA_X_OTRO_ABIERTA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_CONSULTA_NO_BORRABLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.NO_ES_POSIBLE_ELIMINAR_CONSULTA_EN_PROCESO_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENVIO_CONSULTA_ESTADO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.SOLO_ES_POSIBLE_ENVIAR_CONSULTA_EN_ESTADO_ABIERTO_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENVIO_SIN_DETALLES:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.CONSULTA_NO_PUEDE_SER_ENVIADA_PQ_NO_TIENE_DETALLE_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_AUTORIZACION_ESTADO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_ESTADO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_CONSULTA_NO_RESERVADA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.CONSULTA_NO_RESERVADA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.CONSULTA_NO_ENTREGABLE_X_FECHA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_CONSULTA_NO_AUTORIZADA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.ERROR_CONSULTA_NO_AUTORIZADA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENVIO_CONSULTA_FECHA_NO_VALIDA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultaActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_EDICION_NOEDITABLE:
		case ArchivoErrorCodes.ERROR_DEVOLUCION_Y_NO_ENTREGADA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultaActionNotAllowedException.XESTADO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENTREGA_FECHA_MAXIMA_FIN_SUPERADA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									ConsultasConstants.CONSULTA_NO_PUEDE_SER_ENTREGADA_X_FMAXFIN_SUPERADA,
									request.getLocale())));
			break;
		}

		return errores;
	}
}
