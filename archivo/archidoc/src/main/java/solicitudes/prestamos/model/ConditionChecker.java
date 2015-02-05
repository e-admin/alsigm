package solicitudes.prestamos.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.prestamos.vos.ProrrogaVO;
import util.CollectionUtils;
import auditoria.ArchivoErrorCodes;

import common.bi.GestionPrestamosBI;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Clase de ayuda que se encarga de realizar comprobaciones adicionales sobre
 * operaciones en el servicio de prestamos.
 */
public class ConditionChecker {
	/**
	 * Comprueba si el usuario puede realizar la inserción(creación) de un nuevo
	 * préstamo. Compruba: - El usuario no tiene abierto otro préstamo.
	 * 
	 * @param user
	 *            Usuario que desea relizar un nuevo préstamo(no el usuario
	 *            conectado)
	 * @param service
	 *            Servicio de acceso a préstamos
	 * @throws PrestamoActionNotAllowedException
	 *             Si el usuario no puede realizar la accion
	 */
	public static void checkOnPrestamoInsert(ServiceClient user,
			GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		Collection prestamosPorLosQueNosePuedeCrear = null;

		// Comprobamos si el usuario del servicio tiene otros prestamos abiertos
		if (!user.hasPermission(AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)
				&& !user.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			prestamosPorLosQueNosePuedeCrear = service
					.getPrestamosXIdUsuarioSolicitanteAbiertos(user.getId());
		} else {
			// Segun el tipo de usuario, comprobamos si el usuario introducido
			// tiene otro préstamo. Si NO es INTERNO
			if (!((String) user.getProperties().get(
					PrestamosConstants.PROPERTY_TIPO_SOLICITANTE))
					.equalsIgnoreCase(PrestamosConstants.TIPO_INTERNO)) {
				// por nusrsolicitante
				prestamosPorLosQueNosePuedeCrear = service
						.getPrestamosXUsuarioSolicitanteAbiertos(((String) user
								.getProperties().get(
										PrestamosConstants.PROPERTY_NOMBRE)));// Nusrsolicitante
			} else {
				// por idusrsolicitante
				prestamosPorLosQueNosePuedeCrear = service
						.getPrestamosXIdUsuarioSolicitanteAbiertos(((String) user
								.getProperties()
								.get(PrestamosConstants.PROPERTY_ID_USR_SOLICITANTE)));
			}
		}

		if (!CollectionUtils.isEmpty(prestamosPorLosQueNosePuedeCrear))
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_CREACION_XOTRA,
					PrestamoActionNotAllowedException.XOTRA_ABIERTA);
	}

	/**
	 * Comprueba si el préstamo es editable por el usuario
	 * 
	 * @param prestamo
	 *            Prestamos que deseamos comprobar
	 * @param user
	 *            Usuario que está accediendo al prestamo
	 * @param service
	 *            Servicio de acceso a préstamos
	 * @throws PrestamoActionNotAllowedException
	 *             Si el usuario no puede editar el préstamo
	 */
	public static void checkOnPrestamoEdit(PrestamoVO prestamo,
			ServiceClient user, GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		if (!prestamo.isEditable(user))
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_EDICION_NOEDITABLE,
					PrestamoActionNotAllowedException.XESTADO);

		// checkOnPrestamoInsert(user,service);
		Collection prestamos = null;

		// Comprobamos si el usuario del servicio tiene otros prestamos abiertos
		if (!user.hasPermission(AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS)
				&& !user.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			if (StringUtils.isNotEmpty(prestamo.getIdusrsolicitante()))
				prestamos = service
						.getPrestamosXIdUsuarioSolicitanteAbiertos(prestamo
								.getIdusrsolicitante());
			else
				prestamos = service
						.getPrestamosXIdUsuarioSolicitanteAbiertos(user.getId());
		} else {
			String tipoSolicitante = (String) user.getProperties().get(
					PrestamosConstants.PROPERTY_TIPO_SOLICITANTE);

			// Segun el tipo de usuario, comprobamos si el usuario introducido
			// tiene otro préstamo. Si NO es INTERNO
			if (!tipoSolicitante
					.equalsIgnoreCase(PrestamosConstants.TIPO_INTERNO)) {
				String nombre = (String) user.getProperties().get(
						PrestamosConstants.PROPERTY_NOMBRE);

				// por nusrsolicitante
				prestamos = service
						.getPrestamosXUsuarioSolicitanteAbiertos(nombre);
			} else {
				// por idusrsolicitante
				// prestamos =
				// service.getPrestamosXIdUsuarioSolicitanteAbiertos(user.getId());
				prestamos = service
						.getPrestamosXIdUsuarioSolicitanteAbiertos(prestamo
								.getIdusrsolicitante());
			}
		}

		if (!CollectionUtils.isEmpty(prestamos)) {
			PrestamoVO aux;
			Iterator it = prestamos.iterator();
			while (it.hasNext()) {
				aux = (PrestamoVO) it.next();
				if (!aux.getId().equals(prestamo.getId()))
					throw new PrestamoActionNotAllowedException(
							ArchivoErrorCodes.ERROR_MODIFICACION_XOTRA,
							PrestamoActionNotAllowedException.XOTRA_ABIERTA);
			}
		}
	}

	/**
	 * Realizar las comprobaciones para realizar un envio de solicitud de
	 * prestamo: - Si tiene reserva que esta sea válida - Que el préstamos este
	 * abierto - Que tenga detalles
	 * 
	 * @param prestamo
	 *            Prestamo a enviar
	 * @param service
	 *            Servicio de acceso a prestamos
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la accion por alguna de las condiciones
	 */
	public static void checkOnEnviarPrestamo(PrestamoVO prestamo,
			GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		// Comprobamos si se puede realizar el envio del prestamo
		if (prestamo.tieneReserva()) {
			Date hoy = Calendar.getInstance().getTime();
			Date fechainicio = prestamo.getFinicialreserva();

			if (fechainicio.before(hoy))
				throw new PrestamoActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ENVIO_FECHA_NO_VALIDA,
						PrestamoActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA);
		}
		if (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_ABIERTO) {
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_ENVIO_ESTADO_NO_VALIDO,
					PrestamoActionNotAllowedException.XESTADO);
		}
		if (!(service.numeroDetallesPrestamo(prestamo.getId()) > 0)) {
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_ENVIO_SIN_DETALLES,
					PrestamoActionNotAllowedException.XDETALLADA_SIN_DETALLE);
		}
	}

	/**
	 * Comprueba que el prestamo se puede autorizar: - Esté en estado solicitado
	 * - Que el archivo del préstamo coincida con algún archivo de custodia al
	 * que pertenece el usuario conectado
	 * 
	 * @param prestamo
	 *            Prestamo que deseamos comprobar
	 * @param archivosCustodia
	 *            Listado de lso archivos de custodia del usuario que
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la accion sobre el préstamo por alguna
	 *             de las condiciones
	 */
	public static void checkOnAutorizarDetalle(PrestamoVO prestamo,
			List archivosCustodia, ServiceClient sc)
			throws PrestamoActionNotAllowedException {
		boolean encontrado = false;

		if (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_AUTORIZACION_ESTADO_NO_VALIDO,
					PrestamoActionNotAllowedException.XESTADO);

		// Comprobamos si es administrador del sistema

		if (sc.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
			encontrado = true;

		// Comprobamos si esta presente el archivo de custodia en la lista del
		// usuario
		Iterator it = archivosCustodia.iterator();
		while (it.hasNext() && !encontrado) {
			String idArchivo = (String) it.next();

			if (prestamo.getIdarchivo().equalsIgnoreCase(idArchivo))
				encontrado = true;
		}
		if (!encontrado)

			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_AUTORIZAR_ARCHIVO_NO_VALIDO,
					PrestamoActionNotAllowedException.XARCHIVO);
	}

	/**
	 * Comprueba que el prestamo se puede denegar: - Esté en estado solicitado -
	 * Que el archivo del préstamo coincida con algún archivo de custodia al que
	 * pertenece el usuario conectado
	 * 
	 * @param prestamo
	 *            Prestamo que deseamos comprobar
	 * @param archivosCustodia
	 *            Listado de lso archivos de custodia del usuario que
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la accion sobre el préstamo por alguna
	 *             de las condiciones
	 */
	public static void checkOnDenegarDetalle(PrestamoVO prestamo,
			List archivosCustodia, ServiceClient sc)
			throws PrestamoActionNotAllowedException {
		checkOnAutorizarDetalle(prestamo, archivosCustodia, sc);
	}

	/**
	 * Realizar las comprobaciones para realizar un envio de solicitud de
	 * prestamo: - Si tiene reserva que esta sea válida - Que el préstamos este
	 * abierto - Que tenga detalles
	 * 
	 * @param prestamo
	 *            Prestamo a enviar
	 * @param service
	 *            Servicio de acceso a prestamos
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la accion por alguna de las condiciones
	 */
	public static void checkOnFinalizarAutorizacionPrestamo(
			PrestamoVO prestamo, GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		if (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO) {
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_FINALIZARAUTORIZACION_ESTADO_NO_VALIDO,
					PrestamoActionNotAllowedException.XESTADO);
		}
	}

	/**
	 * Realiza las comprobaciones para realizar una eliminacion de prestamos: -
	 * El prestamo tiene que estar en estado reservado o abierto.
	 * 
	 * @param prestamosAEliminar
	 *            Listado de los prestamos que deseamos comprobar si se pueden
	 *            borrar
	 * @param service
	 *            Servicio de acceso a prestamos
	 * @throws PrestamoActionNotAllowedException
	 *             Si no esta permitida la acción
	 */
	public static void checkOnEliminarPrestamos(String[] prestamosAEliminar,
			GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		boolean borrables = true;

		// Obtenemos los prestamos
		Collection prestamosVO = service.getPrestamos(prestamosAEliminar);
		for (Iterator itPrestamosVO = prestamosVO.iterator(); itPrestamosVO
				.hasNext() && borrables;) {
			PrestamoVO prestamoVO = (PrestamoVO) itPrestamosVO.next();

			if (prestamoVO.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_RESERVADO
					|| prestamoVO.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ABIERTO) {
				borrables = true;
			} else
				borrables = false;
		}

		if (!borrables)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_PRESTAMO_NO_BORRABLE,
					PrestamoActionNotAllowedException.XESTADO);
	}

	/**
	 * Realiza las comprobaciones para autorizar una reserva: - El préstamo debe
	 * estar reservado. - La fecha de reserva debe ser posterior a la actual -
	 * El usuario gestor debe ser el que la autoriza
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo a autorizar
	 * @param service
	 *            Servicio de acceso a prestamos
	 * @param sc
	 *            Usuario del servicio
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la acción
	 */
	public static void checkOnEntregaReserva(String idPrestamo,
			GestionPrestamosBI service, ServiceClient sc)
			throws PrestamoActionNotAllowedException {
		PrestamoVO prestamo = service.getPrestamo(idPrestamo);

		if (prestamo.getFinicialreserva() == null)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_PRESTAMO_NO_RESERVADO,
					PrestamoActionNotAllowedException.XESTADO);

		if (prestamo.getFinicialreserva().after(DateUtils.getFechaActual()))
			// if (!prestamo.getFinicialreserva().after(
			// DateUtils.getFechaActual() ))
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_PRESTAMO_NO_ENTREGABLE_X_FECHA,
					PrestamoActionNotAllowedException.XFECHA_RESERVA_NO_VALIDA);

		if (!prestamo.getIdusrgestor().equals(sc.getId()))
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_PRESTAMO_NO_ENTREGABLE_X_USUARIO,
					PrestamoActionNotAllowedException.XUSUARIO);

	}

	/**
	 * Realiza las comprobaciones para realizar una entrega: - Que el préstamo
	 * este autorizado - Que el archivo del prestamo pertenezca a la lista de
	 * archivos de custodia del usuario conectado
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo a entregar
	 * @param service
	 *            Servicio de acceso a aprestamos
	 * @param sc
	 *            Usuario del servicio
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la acción
	 */
	public static void checkOnEntrega(String idPrestamo,
			GestionPrestamosBI service, ServiceClient sc)
			throws PrestamoActionNotAllowedException {
		boolean encontrado = false;
		PrestamoVO prestamo = service.getPrestamo(idPrestamo);

		if (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_PRESTAMO_NO_AUTORIZADO,
					PrestamoActionNotAllowedException.XESTADO);

		// Comprobamos si esta presente el archivo de custodia en la lista del
		// usuario
		Iterator it = sc.getCustodyArchiveList().iterator();
		while (it.hasNext()) {
			String idArchivo = (String) it.next();

			if (prestamo.getIdarchivo().equalsIgnoreCase(idArchivo))
				encontrado = true;
		}
		if (!encontrado)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_ENTREGA_ARCHIVO_NO_VALIDO,
					PrestamoActionNotAllowedException.XARCHIVO);

		if (prestamo.getFmaxfinprestamo() != null) {
			// Si hemos sobrepasado la fecha máxima de fin de préstamo cuando lo
			// vamos a entregar, no lo permitimos porque se han podido autorizar
			// otros préstamos o consultas teniendo en cuenta en esas fechas y a
			// la hora de entregar no se vuelve a comprobar
			if (DateUtils.getFechaActualSinHora().compareTo(
					DateUtils.getFechaSinHora(prestamo.getFmaxfinprestamo())) > 0)
				throw new PrestamoActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ENTREGA_FECHA_MAXIMA_FIN_SUPERADA,
						PrestamoActionNotAllowedException.XFECHA);
		}

	}

	/**
	 * Realiza las comprobaciones para realizar una prorroga de un prestamo: -
	 * Que el prestamo este entregado - Que no tenga prorrogas denegadas o
	 * solicitadas
	 * 
	 * @param idPrestamo
	 *            Identificador del prestamo
	 * @param service
	 *            Servicio de acceso a prestamo
	 * @throws PrestamoActionNotAllowedException
	 *             Si no está permitida la aci
	 */
	public static void checkOnSolicitarProrroga(String idPrestamo,
			GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		PrestamoVO prestamo = service.getPrestamo(idPrestamo);

		if ((prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO)
				&& (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO))
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_PRESTAMO_NO_ENTREGADO,
					PrestamoActionNotAllowedException.XESTADO);

		// Vamos a comprobar si no tiene prorrogas solicitadas o enviada, q las
		// fechas de reservar son correctas.
		ProrrogaVO prorrogaVO = service.getProrrogaSolicitada(idPrestamo);

		if (prorrogaVO != null)
			if (prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_SOLICITADA
					|| prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_DENEGADA)
				throw new PrestamoActionNotAllowedException(
						ArchivoErrorCodes.ERROR_PRORROGA_TRATADA,
						PrestamoActionNotAllowedException.XPRORROGA_YA_TRATADA);
	}

	public static void checkOnReclamar(String idPrestamo,
			GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		PrestamoVO prestamo = service.getPrestamo(idPrestamo);

		// Validamos si se puede realizar la operación
		if (prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO
				&& prestamo.getEstado() != PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_RECLAMAR_ESTADO_NO_VALIDO,
					PrestamoActionNotAllowedException.XESTADO);

		if (!prestamo.getFmaxfinprestamo().before(DateUtils.getFechaActual()))
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_RECLAMAR_EN_FECHA_VALIDA,
					PrestamoActionNotAllowedException.XFECHA);

		if (service.getProrrogaSolicitada(idPrestamo) != null)
			throw new PrestamoActionNotAllowedException(
					ArchivoErrorCodes.ERROR_RECLAMAR_X_PRORROGA,
					PrestamoActionNotAllowedException.XPRORROGA_SOLICITADA);

	}

	public static void checkOnDenegarProrroga(String id,
			GestionPrestamosBI service)
			throws PrestamoActionNotAllowedException {
		// Vamos a comprobar si no tiene prorrogas solicitadas o enviada, q las
		// fechas de reservar son correctas.
		ProrrogaVO prorrogaVO = service.getProrrogaSolicitada(id);

		if (prorrogaVO != null)
			if ( // prorrogaVO.getEstado() ==
					// PrestamosConstants.ESTADO_PRORROGA_SOLICITADA ||
			prorrogaVO.getEstado() == PrestamosConstants.ESTADO_PRORROGA_DENEGADA)
				throw new PrestamoActionNotAllowedException(
						ArchivoErrorCodes.ERROR_PRORROGA_TRATADA,
						PrestamoActionNotAllowedException.XPRORROGA_YA_TRATADA);
	}
}
