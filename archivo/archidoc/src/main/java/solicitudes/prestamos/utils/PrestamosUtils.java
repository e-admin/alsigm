package solicitudes.prestamos.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.exceptions.DetalleNotFoundException;
import solicitudes.prestamos.EstadoRevDoc;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.view.DetallePrestamoToPO;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.utils.PropertyHelper;
import solicitudes.vos.BusquedaDetalleVO;
import util.CollectionUtils;

import common.ConfigConstants;
import common.Messages;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.util.DateUtils;

public class PrestamosUtils {

	/**
	 * Genera el texto asociado al estado del préstamo.
	 * 
	 * @return String con el texto del estado del prestamo
	 */
	public static String getEstado(Locale locale, int estado) {
		String resul = null;

		switch (estado) {
		case PrestamosConstants.ESTADO_PRESTAMO_ABIERTO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "1",
					locale);
			break;// estado = "Abierto"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_SOLICITADO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "2",
					locale);
			break;// estado = "Solicitado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_RESERVADO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "3",
					locale);
			break;// estado = "Reservado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_AUTORIZADO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "4",
					locale);
			break;// estado = "Autorizado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_DENEGADO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "5",
					locale);
			break;// estado = "Denegado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "6",
					locale);
			break;// estado = "Entregado"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "7",
					locale);
			break;// estado = "Devuelto Incompleto"; break;
		case PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO:
			resul = Messages.getString(
					SolicitudesConstants.PREFFIX_SOLICITUDES_ESTADO + "8",
					locale);
			break;// estado = "Devuelto"; break;
		default:
			resul = "-";
			break;
		}
		return resul;
	}

	/**
	 * Establece los elementos a mostrar en la vista en función de los datos del
	 * préstamo y de los roles del usuario.
	 * 
	 * @param prestamoVO
	 *            Prestamo con los datos que se utilizará para establecer los
	 *            elementos de la vista.
	 * @param AppUser
	 *            Usuario que está accediendo a la aplicación.
	 * @param request
	 * @param detallesPrestamos
	 *            Listado de los detalles del prestamo
	 * @param prestamosService
	 *            Servicio para acceder a prestamos.
	 */
	public static void establecerVistas(PrestamoVO prestamoVO, AppUser appUser,
			HttpServletRequest request, GestionPrestamosBI prestamosService,
			Collection detallesPrestamos) {

		boolean conPermisoEntregaUDocs = isConPermisosGestionEntregaUdocs(
				appUser, prestamoVO);

		// Establecemos que tabla a mostrar
		if (conPermisoEntregaUDocs) {
			request.setAttribute(
					PrestamosConstants.VER_LISTA_DETALLES_PARA_DEPOSITO,
					new Boolean(true));
		}

		// En caso de existir Fecha Máxima de Fin Prestamo ==> Establecemos como
		// visible la Fecha Máxima de Fin Prestamo
		if (prestamoVO.getFmaxfinprestamo() != null && !prestamoVO.isDenegado()) {
			request.setAttribute(PrestamosConstants.FECHA_MAX_FIN_PRESTAMO_KEY,
					prestamoVO.getFmaxfinprestamo());
			request.setAttribute(
					PrestamosConstants.VER_FECHA_MAX_FIN_PRESTAMOS,
					new Boolean(true));
		} else {
			request.setAttribute(
					PrestamosConstants.VER_FECHA_MAX_FIN_PRESTAMOS,
					new Boolean(false));
		}

		// En caso de existir Fecha Entrega ==> Establecemos como visible la
		// Fecha Entrega
		if (prestamoVO.getFentrega() != null) {
			request.setAttribute(PrestamosConstants.FECHA_ENTREGA_KEY,
					prestamoVO.getFentrega());
			request.setAttribute(PrestamosConstants.VER_FECHA_ENTREGA,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_FECHA_ENTREGA,
					new Boolean(false));
		}

		boolean yaTieneProrrogasAutorizadas = prestamosService
				.yaTieneProrrogasAutorizadas(prestamoVO.getId());
		boolean yaTieneProrrogasDenegadas = prestamosService
				.yaTieneProrrogasDenegadas(prestamoVO.getId());
		boolean yaTieneProrroga = prestamosService.yaTieneProrroga(prestamoVO
				.getId());

		// En caso de existir Prorrogas ==> Establecemos como visible
		if (yaTieneProrrogasAutorizadas || yaTieneProrrogasDenegadas
				|| yaTieneProrroga) {
			int prorrogasAceptadas = prestamosService
					.devolverProrrogas(prestamoVO.getId());
			request.setAttribute(PrestamosConstants.NUM_PRORROGAS_KEY,
					new Integer(prorrogasAceptadas));
			request.setAttribute(PrestamosConstants.VER_PRORROGAS_SOLICITADAS,
					new Boolean(true));

			// En caso de existir Prorrogas autorizadas ==> Establecemos como
			// visible prorroga autorizad
			if (yaTieneProrrogasAutorizadas) {
				request.setAttribute(
						PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA_KEY,
						new Boolean(true));
			} else {
				request.setAttribute(
						PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA_KEY,
						new Boolean(false));
			}

			// En caso de existir Prorrogas Denegadas ==> Establecemos como
			// visible prorroga denegada
			if (yaTieneProrrogasDenegadas) {
				request.setAttribute(
						PrestamosConstants.ESTADO_PRORROGA_DENEGADA_KEY,
						new Boolean(true));

				String motivorechazoprorroga = prestamosService
						.motivoProrrogaDenegada(prestamoVO.getId());
				request.setAttribute(
						PrestamosConstants.MOTIVO_RECHAZO_PRORROGA_KEY,
						motivorechazoprorroga);

				Date fecha = prestamosService.fechaProrrogaDenegada(prestamoVO
						.getId());
				request.setAttribute(
						PrestamosConstants.FECHA_PRORROGA_DENEGADA_KEY, fecha);
			} else {
				request.setAttribute(
						PrestamosConstants.ESTADO_PRORROGA_DENEGADA_KEY,
						new Boolean(false));
			}

			// En caso de existir Prorrogas ==> Establecemos como visible
			// prorroga
			if (yaTieneProrroga) {
				request.setAttribute(
						PrestamosConstants.ESTADO_PRORROGA_SOLICITADA_KEY,
						new Boolean(true));

				Date fecha = prestamosService.yaTieneProrrogaFecha(prestamoVO
						.getId());
				request.setAttribute(
						PrestamosConstants.FECHA_PRORROGA_SOLICITADA_KEY, fecha);
			} else {
				request.setAttribute(
						PrestamosConstants.ESTADO_PRORROGA_SOLICITADA_KEY,
						new Boolean(false));
			}
		} else {
			request.setAttribute(PrestamosConstants.VER_PRORROGAS_SOLICITADAS,
					new Boolean(false));
		}

		// En caso de préstamo devuelto ==> Establecemos como su fecha de
		// devolución
		if (prestamoVO.isDevuelto()) {
			request.setAttribute(PrestamosConstants.FECHA_DEVOLUCION_KEY,
					prestamoVO.getFestado());
		} else {
			request.setAttribute(PrestamosConstants.VER_FECHA_DEVOLUCION,
					new Boolean(false));
		}

		// En caso tener fecha inicial de reserva ==> Establecemos ver fecha de
		// reseva y la reserva
		if (prestamoVO.getFinicialreserva() != null) {
			request.setAttribute(PrestamosConstants.VER_FECHA_INICIO_RESERVA,
					new Boolean(true));
			request.setAttribute(PrestamosConstants.VER_RESERVA, new Boolean(
					true));
		} else {
			if (prestamoVO.getFinicialreserva() == null) {
				request.setAttribute(PrestamosConstants.VER_RESERVA,
						new Boolean(true));
				request.setAttribute(
						PrestamosConstants.VER_FECHA_INICIO_RESERVA,
						new Boolean(false));
			}
		}

		// Establecemos los botones
		establecerBotones(prestamoVO, appUser, request, detallesPrestamos);

		establecerBotonesReclamacionesProrrogasReservas(prestamoVO, appUser,
				request, yaTieneProrrogasDenegadas, yaTieneProrroga);

		// Transformar la lista de elementos en POS
		CollectionUtils.transform(detallesPrestamos, DetallePrestamoToPO
				.getInstance(ServiceRepository.getInstance(ServiceClient
						.create(appUser)), true));
	}

	public static void establecerBotonesReclamacionesProrrogasReservas(
			PrestamoVO prestamo, AppUser appUser, HttpServletRequest request,
			boolean yaTieneProrrogasDenegadas, boolean yaTieneProrroga) {

		boolean conPermisosGestionPrestamos = isConPermisosGestionPrestamos(
				appUser, prestamo);

		// BOTON SOLICITAR PRORROGA
		if (appUser.getId().equalsIgnoreCase(prestamo.getIdusrgestor())
				&& (prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO || prestamo
						.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_DEVUELTO_INCOMPLETO)
				&& !yaTieneProrrogasDenegadas && !yaTieneProrroga)
			request.setAttribute(PrestamosConstants.VER_BOTON_SOL_PRORROGA,
					new Boolean(!yaTieneProrroga));
		else {
			request.setAttribute(PrestamosConstants.VER_BOTON_SOL_PRORROGA,
					new Boolean(false));
		}

		// BOTON AUTORIZAR PRORROGA
		if (conPermisosGestionPrestamos && prestamo.isNoDevuelto()) {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_AUTORIZAR_PRORROGA,
					new Boolean(yaTieneProrroga));
			request.setAttribute(
					PrestamosConstants.VER_BOTON_VER_DISPONIBILIDAD_PRORROGA,
					new Boolean(yaTieneProrroga));
		} else {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_AUTORIZAR_PRORROGA,
					new Boolean(false));
		}

		// BOTON DENEGAR PRORROGA
		if (conPermisosGestionPrestamos && prestamo.isNoDevuelto()) {
			request.setAttribute(PrestamosConstants.VER_BOTON_DENEGAR_PRORROGA,
					new Boolean(yaTieneProrroga));
			request.setAttribute(
					PrestamosConstants.VER_BOTON_VER_DISPONIBILIDAD_PRORROGA,
					new Boolean(yaTieneProrroga));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_DENEGAR_PRORROGA,
					new Boolean(false));
		}

		// BOTON reclamar 1
		if (conPermisosGestionPrestamos && prestamo.isNoDevuelto()
				&& prestamo.isCaducado() && prestamo.isNoReclamado()
				&& !yaTieneProrroga) {
			request.setAttribute(PrestamosConstants.VER_BOTON_SOL_PRORROGA,
					new Boolean(true));
			request.setAttribute(PrestamosConstants.VER_BOTON_RECLAMACION_1,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_RECLAMACION_1,
					new Boolean(false));
		}
		// Obtenemos los dias despues de la reclamación del fichero de
		// propiedades
		String dias = PropertyHelper
				.getProperty(PropertyHelper.PLAZO_TRAS_RECLAMACION);

		Calendar fechafinal = new GregorianCalendar();
		if (prestamo.getFreclamacion1() != null) {
			fechafinal.setTime(prestamo.getFreclamacion1());
			fechafinal.add(Calendar.HOUR, Integer.parseInt(dias)
					* PrestamosConstants.HORAS_DIA);
		}

		// BOTON reclamar 2
		if (conPermisosGestionPrestamos && prestamo.isNoDevuelto()
				&& prestamo.isCaducado() && prestamo.isReclamadoUnaVez()
				&& !yaTieneProrroga) {
			request.setAttribute(PrestamosConstants.VER_BOTON_SOL_PRORROGA,
					new Boolean(true));
			request.setAttribute(PrestamosConstants.VER_BOTON_RECLAMACION_2,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_RECLAMACION_2,
					new Boolean(false));
		}

		// BOTON VER_BOTON_SOLICITAR_RESERVA
		if ((appUser.getId().equals(prestamo.getIdusrgestor()))
				&& (prestamo.isReservado())
				&& (prestamo.getFinicialreserva() != null)) {
			// Solo lo mostramos si ha llegado el dia de la reserva.
			request.setAttribute(
					PrestamosConstants.VER_BOTON_SOLICITAR_RESERVA,
					new Boolean(!DateUtils.getFechaSinHora(
							prestamo.getFinicialreserva()).after(
							DateUtils.getFechaActualSinHora())
							&& !prestamo.getFfinalreserva().before(
									DBUtils.getFechaActual())));
			request.setAttribute(
					PrestamosConstants.VER_BOTON_VER_DISPONIBILIDAD_ENTREGA,
					new Boolean(!DateUtils.getFechaSinHora(
							prestamo.getFinicialreserva()).after(
							DateUtils.getFechaActualSinHora())));
		} else {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_SOLICITAR_RESERVA,
					new Boolean(false));
			request.setAttribute(
					PrestamosConstants.VER_BOTON_VER_DISPONIBILIDAD_ENTREGA,
					new Boolean(false));
		}
	}

	public static boolean isConPermisosGestionPrestamos(AppUser appUser,
			PrestamoVO prestamoVO) {

		if (appUser.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
				|| (appUser
						.hasPermission(AppPermissions.GESTION_PRESTAMOS_ARCHIVO) && appUser
						.belongsToCustodyArchive(prestamoVO.getIdarchivo())

				)) {
			return true;
		}

		return false;
	}

	public static boolean isConPermisosGestionEntregaUdocs(AppUser appUser,
			PrestamoVO prestamoVO) {

		if (appUser.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
				|| (appUser
						.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES) && appUser
						.belongsToCustodyArchive(prestamoVO.getIdarchivo())

				)) {
			return true;
		}

		return false;
	}

	/**
	 * Establece los botones de la vista
	 * 
	 * @param prestamo_VO
	 * @param AppUser
	 * @param request
	 * @param detallesPrestamos
	 */
	public static void establecerBotones(PrestamoVO prestamo, AppUser appUser,
			HttpServletRequest request, Collection detallesPrestamos) {

		boolean conPermisosGestionPrestamos = isConPermisosGestionPrestamos(
				appUser, prestamo);
		boolean conPermisosEntregaUdocs = isConPermisosGestionEntregaUdocs(
				appUser, prestamo);

		// BOTON ENVIAR SOLICITUD A ARCHIVO
		if (prestamo.isAbierto() && detallesPrestamos != null
				&& !detallesPrestamos.isEmpty()
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(PrestamosConstants.VER_BOTON_ENVIAR_SOLICITAR,
					new Boolean(true));
			request.setAttribute(
					PrestamosConstants.VER_BOTON_VER_DISPONIBILIDAD,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_ENVIAR_SOLICITAR,
					new Boolean(false));
		}

		// BOTON ENVIAR AUTORIZAR O DENEGAR
		if (prestamo.isSolicitado() && conPermisosGestionPrestamos) {

			if (detallesPrestamos != null && !detallesPrestamos.isEmpty()) {
				Iterator todosDetalles = detallesPrestamos.iterator();
				boolean todostratados = true;

				while (todostratados && todosDetalles.hasNext())
					todostratados = ((DetallePrestamoVO) todosDetalles.next())
							.getEstado() != PrestamosConstants.ESTADO_SOLICITUD_PENDIENTE;

				request.setAttribute(
						PrestamosConstants.VER_BOTON_ENVIAR_DENEGAR_AUTORIZAR,
						new Boolean(todostratados));
				request.setAttribute(
						PrestamosConstants.VER_BOTON_VER_DISPONIBILIDAD,
						new Boolean(true));
			}
		}

		// BOTON ENTREGAR
		if (prestamo.isAutorizado() && conPermisosEntregaUdocs) {
			request.setAttribute(PrestamosConstants.VER_BOTON_ENTREGAR,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_ENTREGAR,
					new Boolean(false));
		}

		// BOTON IMPRIMIR ETIQUETAS
		if (ConfigConstants.getInstance().getMostrarEtiquetasPrestamo()) {
			if ((appUser
					.hasPermission(AppPermissions.ENTREGA_UNIDADES_DOCUMENTALES) || appUser
					.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
					&& (prestamo.getEstado() == PrestamosConstants.ESTADO_PRESTAMO_ENTREGADO && prestamo
							.getNumreclamaciones() == 0)) {
				request.setAttribute(
						PrestamosConstants.VER_BOTON_IMPRIMIR_ETIQUETAS,
						new Boolean(true));
			}
		}

		// BOTON IMPRIMIR SALIDA
		if (prestamo.isEntregado() && prestamo.isNoReclamado()
				&& conPermisosEntregaUdocs

		) {
			request.setAttribute(PrestamosConstants.VER_BOTON_IMPRIMIR_SALIDA,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_IMPRIMIR_SALIDA,
					new Boolean(false));
		}

		// BOTON IMPRIMIR ENTRADA
		if (prestamo.isDevueltoCompletoIncompleto() && conPermisosEntregaUdocs) {
			request.setAttribute(PrestamosConstants.VER_BOTON_IMPRIMIR_ENTRADA,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_IMPRIMIR_ENTRADA,
					new Boolean(false));
		}

		// BOTON IMPRIMIR RECLAMACION 1 O 2
		if (prestamo.isNoDevuelto() && prestamo.isConReclamaciones()
				&& conPermisosGestionPrestamos) {

			if (prestamo.isReclamadoUnaVez())
				request.setAttribute(
						PrestamosConstants.VER_BOTON_IMPRIMIR_RECLAMACION_1,
						new Boolean(true));
			else
				request.setAttribute(
						PrestamosConstants.VER_BOTON_IMPRIMIR_RECLAMACION_2,
						new Boolean(true));

		} else {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_IMPRIMIR_RECLAMACION_2,
					new Boolean(false));
		}

		// BOTON EDITAR
		if (prestamo.isAbierto()
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(PrestamosConstants.VER_BOTON_EDITAR,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_EDITAR,
					new Boolean(false));

			if (appUser.isUsuarioArchivo()) {
				request.setAttribute(
						PrestamosConstants.PERMITIR_EDITAR_OBSERVACIONES,
						new Boolean(true));
			}
		}

		// BOTON ELIMINAR
		if ((prestamo.isAbierto() || prestamo.isReservado())
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR,
					new Boolean(false));
		}

		// BOTON ANADIR DETALLE
		if (prestamo.isAbierto()
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(PrestamosConstants.VER_BOTON_ANADIR_DETALLE,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_ANADIR_DETALLE,
					new Boolean(false));
		}

		// BOTON ELIMINAR DETALLE
		if (prestamo.isAbierto()
				&& (detallesPrestamos != null && !detallesPrestamos.isEmpty())
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR_DETALLE,
					new Boolean(true));
		} else {
			request.setAttribute(PrestamosConstants.VER_BOTON_ELIMINAR_DETALLE,
					new Boolean(false));
		}

		// BOTON AUTORIZAR-DENEGAR DETALLE
		if (prestamo.isSolicitado() && conPermisosGestionPrestamos) {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_AUTORIZAR_DETALLE,
					new Boolean(true));
			request.setAttribute(PrestamosConstants.VER_BOTON_DENEGAR_DETALLE,
					new Boolean(true));
		} else {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_AUTORIZAR_DETALLE,
					new Boolean(false));
			request.setAttribute(PrestamosConstants.VER_BOTON_DENEGAR_DETALLE,
					new Boolean(false));
		}

		// BOTON DEVOLVER
		if (prestamo.isNoDevuelto() && conPermisosGestionPrestamos) {
			request.setAttribute(PrestamosConstants.VER_BOTON_DEVOLVER,
					new Boolean(true));
		} else
			request.setAttribute(PrestamosConstants.VER_BOTON_DEVOLVER,
					new Boolean(false));

		// BOTON MODIFICAR COLUMNA OBSERVACIONES
		if (appUser.isUsuarioArchivo()
				|| (prestamo.isAbierto() || prestamo.isSolicitado())
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES,
					new Boolean(true));
		} else
			request.setAttribute(
					PrestamosConstants.VER_BOTON_MODIFICAR_COLUMNA_OBSERVACIONES,
					new Boolean(false));

		// BOTON MODIFICAR COLUMNA EXPEDIENTE DE FRACCIÓN SERIE
		if ((prestamo.isAbierto() || prestamo.isSolicitado())
				&& appUser.getId().equals(prestamo.getIdusrgestor())) {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS,
					new Boolean(true));
		} else {
			request.setAttribute(
					PrestamosConstants.VER_BOTON_MODIFICAR_COLUMNA_EXPEDIENTEFS,
					new Boolean(false));
		}

	}

	/**
	 * @param ltDetallesPrestamosConsultas
	 * @param idsDetallesRevisarDocumentacion
	 * @param prestamosService
	 * @return
	 */
	public static ActionErrors validateDevolverDetallesPrestamos(
			List ltDetallesPrestamosConsultas,
			String[] idsDetallesRevisarDocumentacion,
			GestionPrestamosBI prestamosService) {
		ActionErrors errors = new ActionErrors();

		if ((idsDetallesRevisarDocumentacion != null)
				&& (idsDetallesRevisarDocumentacion.length > 0)) {

			// Recorrer la documentación a revisar y almacenar la unidad
			// documental a la que pertenece y la cuenta por si
			// hay varias partes
			Map mapDetallesRevisarDocumentacion = new HashMap();
			if (idsDetallesRevisarDocumentacion != null) {
				for (int i = 0; i < idsDetallesRevisarDocumentacion.length; i++) {
					String idcompuesto = idsDetallesRevisarDocumentacion[i];
					String idudoc = idcompuesto.substring(0,
							idcompuesto.indexOf("|"));
					String signaturaudoc = idcompuesto.substring(
							idcompuesto.indexOf("|") + 1,
							idcompuesto.lastIndexOf("|"));

					List ltSignaturas = (List) mapDetallesRevisarDocumentacion
							.get(idudoc);
					if (ltSignaturas == null) {
						ltSignaturas = new ArrayList();
						ltSignaturas.add(signaturaudoc);
						mapDetallesRevisarDocumentacion.put(idudoc,
								ltSignaturas);
					} else {
						ltSignaturas.add(signaturaudoc);
						mapDetallesRevisarDocumentacion.put(idudoc,
								ltSignaturas);
					}
				}
			}

			// Almacenar los detalles del prestamo a devolver
			Map mapDetallesDevolver = new HashMap();
			if ((ltDetallesPrestamosConsultas != null)
					&& (!ltDetallesPrestamosConsultas.isEmpty())) {
				Iterator it = ltDetallesPrestamosConsultas.iterator();
				BusquedaDetalleVO busquedaDetalleVO = null;
				while (it.hasNext()) {
					busquedaDetalleVO = (BusquedaDetalleVO) it.next();
					if (PrestamosConstants.TIPO_SOLICITUD_PRESTAMO == busquedaDetalleVO
							.getTiposolicitud()) {
						String idcompuesto = busquedaDetalleVO.getIdudoc()
								+ "|" + busquedaDetalleVO.getSignaturaudoc();
						mapDetallesDevolver.put(idcompuesto,
								busquedaDetalleVO.getIdSolicitud());
					}
				}
			}

			// Recorrer los detalles a revisar
			Iterator it = mapDetallesRevisarDocumentacion.keySet().iterator();
			while (it.hasNext()) {
				String idudoc = (String) it.next();
				List value = (List) mapDetallesRevisarDocumentacion.get(idudoc);

				Iterator itSignatura = value.iterator();
				while (itSignatura.hasNext()) {

					String signaturaudoc = (String) itSignatura.next();
					String idSolicitud = (String) mapDetallesDevolver
							.get(idudoc + "|" + signaturaudoc);

					// Comprobar que la unidad a revisar se va a devolver
					if (mapDetallesDevolver.get(idudoc + "|" + signaturaudoc) == null) {
						try {
							DetallePrestamoVO detalle = prestamosService
									.getDetallePrestamo(idSolicitud, idudoc,
											signaturaudoc);
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_UNIDAD_MARCADA_REVISION_NO_DEVUELTA,
											detalle.getTitulo(), detalle
													.getSignaturaudoc()));
						} catch (DetalleNotFoundException e) {
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_UNIDAD_MARCADA_REVISION_NO_DEVUELTA));
						}
						break;
					}

					// Comprobar que sólo se ha marcado una parte de la unidad
					// documental
					if (value.size() > 1) {
						errors.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										PrestamosConstants.ERROR_PRESTAMOS_VARIAS_PARTES_UNIDAD_DOCUMENTAL_MARCADAS_REVISION,
										StringUtils.join(value.iterator(), ',')));
						break;
					}

					// Comprobar que la unidad documental no está en otra
					// revisión de documentación abierta
					List revisionesUdoc = prestamosService
							.getRevisionesDocumentacionByIdUdocYEstado(idudoc,
									new int[] { EstadoRevDoc.ABIERTA
											.getIdentificador() });
					if ((revisionesUdoc != null) && (revisionesUdoc.size() > 0)) {
						try {
							DetallePrestamoVO detalle = prestamosService
									.getDetallePrestamo(idSolicitud, idudoc,
											signaturaudoc);
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_REVISION_DOCUMENTACION_EXISTENTE_UNIDAD,
											detalle.getTitulo(), detalle
													.getSignaturaudoc()));
						} catch (DetalleNotFoundException e) {
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_REVISION_DOCUMENTACION_EXISTENTE_UNIDAD));
						}
						break;
					}
				}
			}
		}

		return errors;
	}

	/**
	 * Método para validar los detalles de
	 * 
	 * @param idsDetallesPrestamos
	 * @param idsDetallesRevisarDocumentacion
	 * @param idSolicitud
	 * @param prestamosService
	 * @param request
	 * @return
	 */
	public static ActionErrors validateDevolverDetallesPrestamos(
			String[] idsDetallesPrestamos,
			String[] idsDetallesRevisarDocumentacion, String idSolicitud,
			GestionPrestamosBI prestamosService) {
		ActionErrors errors = new ActionErrors();

		if ((idsDetallesRevisarDocumentacion != null)
				&& (idsDetallesRevisarDocumentacion.length > 0)) {

			// Recorrer la documentación a revisar y almacenar la unidad
			// documental a la que pertenece y la cuenta por si
			// hay varias partes
			Map mapDetallesRevisarDocumentacion = new HashMap();
			if (idsDetallesRevisarDocumentacion != null) {
				for (int i = 0; i < idsDetallesRevisarDocumentacion.length; i++) {
					String idcompuesto = idsDetallesRevisarDocumentacion[i];
					String idudoc = idcompuesto.substring(0,
							idcompuesto.indexOf("|"));
					String signaturaudoc = idcompuesto.substring(
							idcompuesto.indexOf("|") + 1,
							idcompuesto.lastIndexOf("|"));

					List ltSignaturas = (List) mapDetallesRevisarDocumentacion
							.get(idudoc);
					if (ltSignaturas == null) {
						ltSignaturas = new ArrayList();
						ltSignaturas.add(signaturaudoc);
						mapDetallesRevisarDocumentacion.put(idudoc,
								ltSignaturas);
					} else {
						ltSignaturas.add(signaturaudoc);
						mapDetallesRevisarDocumentacion.put(idudoc,
								ltSignaturas);
					}
				}
			}

			// Almacenar los detalles del prestamo a devolver
			Map mapDetallesDevolver = new HashMap();
			if (idsDetallesPrestamos != null) {
				for (int i = 0; i < idsDetallesPrestamos.length; i++) {
					String idcompuesto = idsDetallesPrestamos[i];
					String signaturaudoc = idcompuesto.substring(
							idcompuesto.indexOf("|") + 1, idcompuesto.length());
					mapDetallesDevolver.put(idcompuesto, signaturaudoc);
				}
			}

			// Recorrer los detalles a revisar
			Iterator it = mapDetallesRevisarDocumentacion.keySet().iterator();
			while (it.hasNext()) {
				String idudoc = (String) it.next();
				List value = (List) mapDetallesRevisarDocumentacion.get(idudoc);

				Iterator itSignatura = value.iterator();
				while (itSignatura.hasNext()) {

					String signaturaudoc = (String) itSignatura.next();

					// Comprobar que la unidad a revisar se va a devolver
					if (mapDetallesDevolver.get(idudoc + "|" + signaturaudoc) == null) {
						try {
							DetallePrestamoVO detalle = prestamosService
									.getDetallePrestamo(idSolicitud, idudoc,
											signaturaudoc);
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_UNIDAD_MARCADA_REVISION_NO_DEVUELTA,
											detalle.getTitulo(), detalle
													.getSignaturaudoc()));
						} catch (DetalleNotFoundException e) {
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_UNIDAD_MARCADA_REVISION_NO_DEVUELTA));
						}
						break;
					}

					// Comprobar que sólo se ha marcado una parte de la unidad
					// documental
					if (value.size() > 1) {
						errors.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										PrestamosConstants.ERROR_PRESTAMOS_VARIAS_PARTES_UNIDAD_DOCUMENTAL_MARCADAS_REVISION,
										StringUtils.join(value.iterator(), ',')));
						break;
					}

					// Comprobar que la unidad documental no está en otra
					// revisión de documentación abierta
					List revisionesUdoc = prestamosService
							.getRevisionesDocumentacionByIdUdocYEstado(idudoc,
									new int[] { EstadoRevDoc.ABIERTA
											.getIdentificador() });
					if ((revisionesUdoc != null) && (revisionesUdoc.size() > 0)) {
						try {
							DetallePrestamoVO detalle = prestamosService
									.getDetallePrestamo(idSolicitud, idudoc,
											signaturaudoc);
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_REVISION_DOCUMENTACION_EXISTENTE_UNIDAD,
											detalle.getTitulo(), detalle
													.getSignaturaudoc()));
						} catch (DetalleNotFoundException e) {
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											PrestamosConstants.ERROR_PRESTAMOS_REVISION_DOCUMENTACION_EXISTENTE_UNIDAD));
						}
						break;
					}
				}
			}
		}

		return errors;
	}
}
