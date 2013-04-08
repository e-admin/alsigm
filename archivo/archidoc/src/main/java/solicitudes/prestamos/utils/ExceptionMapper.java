package solicitudes.prestamos.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import util.CollectionUtils;
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
			PrestamoActionNotAllowedException e) {
		ActionErrors errores = new ActionErrors();

		switch (e.getCodError()) {
		case ArchivoErrorCodes.ERROR_ENVIO_ESTADO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.SOLO_ES_POSIBLE_ENVIAR_PRESTAMOS_EN_ESTADO_ABIERTO_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENVIO_SIN_DETALLES:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_PUEDE_SER_ENVIADO_PQ_NO_TIENE_DETALLE_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENVIO_FECHA_NO_VALIDA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_PUEDE_SER_ENVIADO_X_FECHA_RESERVA_NOVALIDA_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_FINALIZARAUTORIZACION_ESTADO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_PUEDE_SER_AUTORIZADO_X_ESTADO_NOVALIDO_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_PRESTAMO_NO_BORRABLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.NO_ES_POSIBLE_ELIMINAR_PRESTAMO_EN_PROCESO_MESSAGE_KEY,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_CREACION_XOTRA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_PUEDE_SER_CREADO_X_OTRO_ABIERTO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_EDICION_NOEDITABLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_EDITABLE,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_PRESTAMO_NO_RESERVADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_RESERVADO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_PRESTAMO_NO_ENTREGABLE_X_FECHA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_ENTREGABLE_X_FECHA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_PRESTAMO_NO_ENTREGABLE_X_USUARIO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_ENTREGABLE_X_USUARIO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_PRESTAMO_NO_AUTORIZADO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.ERROR_PRESTAMO_NO_AUTORIZADO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENTREGA_ARCHIVO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.ERROR_ENTREGA_ARCHIVO_NO_VALIDO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_RECLAMAR_ESTADO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.ERROR_RECLAMAR_ESTADO_NO_VALIDO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_RECLAMAR_EN_FECHA_VALIDA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.ERROR_RECLAMAR_EN_FECHA_VALIDA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_RECLAMAR_X_PRORROGA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.ERROR_RECLAMAR_X_PRORROGA,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_DEVOLUCION_Y_NO_ENTREGADA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.ERROR_DEVOLUCION_DETALLE,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_AUTORIZAR_ARCHIVO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_ARCHIVO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_AUTORIZACION_ESTADO_NO_VALIDO:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_ESTADO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_AUTORIZACION_DETALLE_NO_DISPONIBLE:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_NO_DISPONIBLE,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_MODIFICACION_XOTRA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_PUEDE_SER_MODIFICADO_X_OTRO_ABIERTO,
									request.getLocale())));
			break;
		case ArchivoErrorCodes.ERROR_ENTREGA_FECHA_MAXIMA_FIN_SUPERADA:
			errores.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									PrestamosConstants.PRESTAMO_NO_PUEDE_SER_ENTREGADO_X_FMAXFIN_SUPERADA,
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
	public static ActionErrors getErrorsSignatures(int codError, List signatures) {
		ActionErrors errores = new ActionErrors();
		switch (codError) {
		case ArchivoErrorCodes.ERROR_AUTORIZACION_DETALLE_NO_DISPONIBLE:
			String strSignatures = CollectionUtils.join(signatures, ",");
			errores.add(
					PrestamosConstants.NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_NO_DISPONIBLE,
					new ActionError(
							PrestamosConstants.NO_ES_POSIBLE_AUTORIZAR_DETALLE_X_NO_DISPONIBLE,
							strSignatures));

			break;
		}

		return errores;
	}
}
