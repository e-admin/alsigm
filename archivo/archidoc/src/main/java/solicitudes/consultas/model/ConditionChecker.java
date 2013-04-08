package solicitudes.consultas.model;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.exceptions.ConsultaActionNotAllowedException;
import solicitudes.consultas.vos.ConsultaVO;
import auditoria.ArchivoErrorCodes;

import common.Messages;
import common.bi.GestionConsultasBI;
import common.util.DateUtils;

/**
 * Clase de ayuda que se encarga de realizar comprobaciones adicionales sobre
 * operaciones en el servicio de consultas.
 */
public class ConditionChecker {
	/**
	 * Comprueba si el usuario puede realizar la inserción(creación) de una
	 * nueva consulta. Compruba: - El usuario no tiene abierto otra consulta.
	 * 
	 * @param user
	 *            Usuario que desea relizar una nueva consulta(no el usuario
	 *            conectado)
	 * @param service
	 *            Servicio de acceso a consultas
	 * @throws ConsultaActionNotAllowedException
	 *             Si el usuario no puede realizar la accion
	 */
	public static void checkOnConsultaInsert(ServiceClient user,
			GestionConsultasBI service)
			throws ConsultaActionNotAllowedException {
		Collection consultas = null;

		if (!user.hasPermission(AppPermissions.GESTION_SOLICITUDES_CONSULTAS))
			consultas = service
					.getConsultasXUsuarioGestorAbiertos(user.getId());
		else
			consultas = service
					.getConsultasXUsuarioConsultorAbiertos((String) user
							.getProperties().get(
									ConsultasConstants.PROPERTY_NOMBRE));

		if ((consultas != null && !consultas.isEmpty()))
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CREACIONCONSULTA_XOTRA,
					Messages.getString(
							ConsultaActionNotAllowedException.XOTRA_ABIERTA,
							user.getLocale()));
	}

	/**
	 * Comprueba si la consulta es editable por el usuario
	 * 
	 * @param consulta
	 *            Consulta que deseamos comprobar
	 * @param user
	 *            Usuario que está accediendo a la consulta
	 * @throws ConsultaActionNotAllowedException
	 *             Si el usuario no puede editar la consulta
	 */
	public static void checkOnConsultaEdit(ConsultaVO consulta,
			ServiceClient user) throws ConsultaActionNotAllowedException {
		if (!consulta.isEditable(user))
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_EDICION_NOEDITABLE,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO,
							user.getLocale()));
		// ConsultaActionNotAllowedException.XESTADO );
	}

	/**
	 * Realiza las comprobaciones para realizar una eliminacion de consulta: -
	 * La consulta tiene que estar en estado reservado o abierto.
	 * 
	 * @param consultasAEliminar
	 *            Listado de las consulta que deseamos comprobar si se pueden
	 *            borrar
	 * @param service
	 *            Servicio de acceso a consulta
	 * @throws ConsultaActionNotAllowedException
	 *             Si no esta permitida la acción
	 */
	public static void checkOnEliminarConsultas(String[] consultasAEliminar,
			GestionConsultasBI service, ServiceClient user)
			throws ConsultaActionNotAllowedException {
		boolean borrables = true;

		// Obtenemos los prestamos
		Collection consultas = service.getConsultas(consultasAEliminar);
		for (Iterator it = consultas.iterator(); it.hasNext() && borrables;) {
			ConsultaVO consultaVO = (ConsultaVO) it.next();

			if (consultaVO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_RESERVADA
					|| consultaVO.getEstado() == ConsultasConstants.ESTADO_CONSULTA_ABIERTA) {
				borrables = true;
			} else
				borrables = false;
		}

		if (!borrables) {
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CONSULTA_NO_BORRABLE,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO,
							user.getLocale()));
		}
		// ConsultaActionNotAllowedException.XESTADO );
	}

	/**
	 * Realiza las comprobaciones para realizar un envio de consulta: - Si tiene
	 * reserva que esta sea válida - Que la consulta este abierta - Que tenga
	 * detalles
	 * 
	 * @param consulta
	 *            Consulta a enviar
	 * @param service
	 *            Servicio de acceso a consulta
	 * @throws ConsultaActionNotAllowedException
	 *             Si no esta permitida la acción
	 */
	public static void checkOnEnviarConsulta(ConsultaVO consulta,
			GestionConsultasBI service, ServiceClient user)
			throws ConsultaActionNotAllowedException {
		// Comprobamos si se puede realizar el envio de la consulta
		if (consulta.tieneReserva()) {
			Date fechainicio = consulta.getFinicialreserva();

			if (DateUtils.getFechaSinHora(fechainicio).before(
					DateUtils.getFechaActualSinHora()))
				throw new ConsultaActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ENVIO_CONSULTA_FECHA_NO_VALIDA,
						// ConsultaActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA
						Messages.getString(
								ConsultaActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA,
								user.getLocale()));
		}

		if (consulta.getEstado() != ConsultasConstants.ESTADO_CONSULTA_ABIERTA) {
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_ENVIO_CONSULTA_ESTADO_NO_VALIDO,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO,
							user.getLocale()));
			// ConsultaActionNotAllowedException.XESTADO);
		}

		if (!(service.numeroDetallesConsulta(consulta.getId()) > 0)) {
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_ENVIO_SIN_DETALLES,
					Messages.getString(
							ConsultaActionNotAllowedException.XDETALLADA_SIN_DETALLE,
							user.getLocale()));
			// ConsultaActionNotAllowedException.XDETALLADA_SIN_DETALLE );
		}
	}

	/**
	 * Comprueba que la consulta se puede autorizar: - Esté en estado solicitado
	 * 
	 * @param consulta
	 *            Consulta que deseamos comprobar
	 * @throws ConsultaActionNotAllowedException
	 *             Si no está permitida la accion sobre la consulta por alguna
	 *             de las condiciones
	 */
	public static void checkOnAutorizarDetalle(ConsultaVO consulta,
			ServiceClient user) throws ConsultaActionNotAllowedException {
		if (consulta.getEstado() != ConsultasConstants.ESTADO_CONSULTA_SOLICITADA)
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_AUTORIZACION_ESTADO_NO_VALIDO,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO,
							user.getLocale()));
		// ConsultaActionNotAllowedException.XESTADO);
	}

	/**
	 * Realiza las comprobaciones para autorizar una reserva: - La consulta debe
	 * estar reservada. - La fecha de reserva debe ser posterior a la actual -
	 * El usuario solicitante debe ser el que la autoriza
	 * 
	 * @param idConsulta
	 *            Identificador de la consulta a autorizar
	 * @param service
	 *            Servicio de acceso a consulta
	 * @param sc
	 *            Usuario del servicio
	 * @throws ConsultaActionNotAllowedException
	 *             Si no está permitida la acción
	 */
	public static void checkOnEntregaReserva(String idConsulta,
			GestionConsultasBI service, ServiceClient sc)
			throws ConsultaActionNotAllowedException {
		ConsultaVO consulta = service.getConsulta(idConsulta);

		if (consulta.getFinicialreserva() == null)
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CONSULTA_NO_RESERVADA,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO,
							sc.getLocale()));
		// ConsultaActionNotAllowedException.XESTADO);

		if (!DateUtils.esHoy(consulta.getFinicialreserva()))
			// if (
			// !(consulta.getFinicialreserva().compareTo(DateUtils.getFechaActual()
			// ) == 0) )
			// if (consulta.getFinicialreserva().after(
			// DateUtils.getFechaActual() ))
			// if (!consulta.getFinicialreserva().after(
			// DateUtils.getFechaActual() ))
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA,
					Messages.getString(
							ConsultaActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA,
							sc.getLocale()));

		// ArchivoErrorCodes.ERROR_CONSULTA_NO_ENTREGABLE_X_FECHA,
		// ConsultaActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA);

		// FIXME Revisar
		if (!consulta.getIdusrsolicitante().equals(sc.getId()))
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CONSULTA_NO_ENTREGABLE_X_USUARIO,
					Messages.getString(
							ConsultaActionNotAllowedException.XUSUARIO,
							sc.getLocale()));
		// ConsultaActionNotAllowedException.XUSUARIO);

	}

	/**
	 * Realiza las comprobaciones para realizar una entrega: - Que la consulta
	 * este autorizado
	 * 
	 * @param idConsulta
	 *            Identificador de la consulta a entregar
	 * @param service
	 *            Servicio de acceso a consultas
	 * @param sc
	 *            Usuario del servicio
	 * @throws ConsultaActionNotAllowedException
	 *             Si no está permitida la acción
	 */
	public static void checkOnEntrega(String idConsulta,
			GestionConsultasBI service, ServiceClient sc)
			throws ConsultaActionNotAllowedException {

		// boolean encontrado = false;
		ConsultaVO consulta = service.getConsulta(idConsulta);

		if (consulta.getEstado() != ConsultasConstants.ESTADO_CONSULTA_AUTORIZADA)
			throw new ConsultaActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CONSULTA_NO_AUTORIZADA,
					Messages.getString(
							ConsultaActionNotAllowedException.XESTADO,
							sc.getLocale()));

		if (consulta.getFmaxfinconsulta() != null) {
			// Si hemos sobrepasado la fecha máxima de fin de consulta cuando la
			// vamos a entregar, no lo permitimos porque se han podido autorizar
			// otros préstamos o consultas teniendo en cuenta en esas fechas y a
			// la hora de entregar no se vuelve a comprobar
			if (DateUtils.getFechaActualSinHora().compareTo(
					DateUtils.getFechaSinHora(consulta.getFmaxfinconsulta())) > 0)
				throw new ConsultaActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ENTREGA_FECHA_MAXIMA_FIN_SUPERADA,
						ConsultaActionNotAllowedException.XFECHA);
		}

		// ConsultaActionNotAllowedException.XESTADO);

		// //Comprobamos si esta presente el archivo de custodia en la lista del
		// usuario
		// Iterator it = sc.getCustodyArchiveList().iterator();
		// while (it.hasNext()){
		// String idArchivo = (String)it.next();
		//
		// if (prestamo.getIdarchivo().equalsIgnoreCase(idArchivo)) encontrado =
		// true;
		// }
		// if (!encontrado)
		// throw new PrestamoActionNotAllowedException(
		// ArchivoErrorCodes.ERROR_ENTREGA_ARCHIVO_NO_VALIDO,
		// PrestamoActionNotAllowedException.XARCHIVO);
	}
}
