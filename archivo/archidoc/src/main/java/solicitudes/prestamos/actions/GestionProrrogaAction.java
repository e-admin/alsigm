/**
 *
 */
package solicitudes.prestamos.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.exceptions.PrestamoActionNotAllowedException;
import solicitudes.prestamos.forms.ProrrogaForm;
import solicitudes.prestamos.utils.ExceptionMapper;
import solicitudes.prestamos.utils.PrestamosUtils;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.prestamos.vos.ProrrogaVO;
import solicitudes.utils.PropertyHelper;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GestionProrrogaAction extends GestionPrestamosAction {

	protected static final String FORWARD_SOLICITUD_PRORROGA = "solicitud_prorroga";
	protected static final String FORWARD_GESTIONAR_PRORROGA = "solicitud_ver_prorroga";

	protected void nuevaProrrogaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.SOLICITUD_PRORROGA,
				request);

		ProrrogaForm formulario = (ProrrogaForm) form;

		PrestamoVO prestamoVO = getGestionPrestamosBI(request).getPrestamo(
				formulario.getIdPrestamo());

		if (prestamoVO != null) {

			// Comprobar si existe ya una prorroga solicitada
			ProrrogaVO prorrogaVO = getGestionPrestamosBI(request)
					.getProrrogaSolicitada(prestamoVO.getId());

			if (prorrogaVO != null) {

				verProrrogaCodeLogic(mapping, formulario, request, response,
						prorrogaVO);
			} else {
				if (PrestamosUtils.isConPermisosGestionPrestamos(
						getAppUser(request), prestamoVO)) {
					request.setAttribute(
							PrestamosConstants.PRESTAMO_USUARIO_ARCHIVO_GESTOR_KEY,
							Boolean.TRUE);
				}

				Date fechaDevolucion = getFechaProrroga(prestamoVO
						.getFmaxfinprestamo());
				formulario.setFechaFinProrroga(DateUtils
						.formatDate(fechaDevolucion));
				setReturnActionFordward(request,
						mapping.findForward(FORWARD_SOLICITUD_PRORROGA));
			}

		} else {
			logger.error("No existe el préstamo con id:"
					+ formulario.getIdPrestamo());
			goLastClientExecuteLogic(mapping, formulario, request, response);
		}
	}

	protected void gestionProrrogaSolicitadaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.GESTION_PRORROGA, request);

		ProrrogaForm formulario = (ProrrogaForm) form;

		String idPrestamo = formulario.getIdPrestamo();

		// Comprobar si existe ya una prorroga solicitada
		ProrrogaVO prorrogaVO = getGestionPrestamosBI(request)
				.getProrrogaSolicitada(idPrestamo);

		if (prorrogaVO != null) {
			verProrrogaCodeLogic(mapping, formulario, request, response,
					prorrogaVO);
		} else {
			ActionErrors errors = new ActionErrors();

			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_PRESTAMO_SIN_PRORROGAS_EN_ESTADO,
							Messages.getString(
									PrestamosConstants.ESTADO_PRORROGA_SOLICITADA_MESSAGE_KEY,
									request.getLocale())));

			ErrorsTag.saveErrors(request, errors);

			goBackExecuteLogic(mapping, formulario, request, response);
		}
	}

	/**
	 * Solicita la prorroga de un préstamo reclamado.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void solicitarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionPrestamosBI prestamosService = getGestionPrestamosBI(request);

		ProrrogaForm formulario = (ProrrogaForm) form;

		ProrrogaVO prorrogaActiva = prestamosService
				.getProrrogaActiva(formulario.getIdPrestamo());

		Date fechaFinProrrogaActiva = null;

		if (prorrogaActiva != null) {
			fechaFinProrrogaActiva = prorrogaActiva.getFechaFinProrroga();
		}

		ActionErrors errores = formulario.validateFormulario(mapping, request,
				fechaFinProrrogaActiva);

		if (errores.isEmpty()) {
			try {
				ProrrogaVO prorrogaVO = new ProrrogaVO();

				formulario.populate(prorrogaVO);

				boolean autorizar = false;

				if (Boolean.TRUE.equals(formulario.getAutorizar())) {
					PrestamoVO prestamoVO = prestamosService
							.getPrestamo(formulario.getIdPrestamo());

					if (prestamoVO != null) {
						if (PrestamosUtils.isConPermisosGestionPrestamos(
								getAppUser(request), prestamoVO)) {
							autorizar = true;
						}
					}
				}
				prestamosService.solicitarProrroga(prorrogaVO, autorizar);
			} catch (PrestamoActionNotAllowedException e) {
				errores = ExceptionMapper.getErrorsExcepcion(request, e);
			}

			if (errores != null && !errores.isEmpty()) {
				ErrorsTag.saveErrors(request, errores);
				setReturnActionFordward(request,
						mapping.findForward(FORWARD_SOLICITUD_PRORROGA));
			} else {
				goBackExecuteLogic(mapping, formulario, request, response);
			}
		} else {
			ErrorsTag.saveErrors(request, errores);
			setReturnActionFordward(request,
					mapping.findForward(FORWARD_SOLICITUD_PRORROGA));
		}

	}

	/**
	 * Realizar la autorización de una prorroga.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void autorizarprorrogadesdevistaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ProrrogaForm frm = (ProrrogaForm) form;

		String idPrestamo = frm.getIdPrestamo();
		String idProrroga = frm.getId();

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();

		ProrrogaVO prorrogaActiva = prestamosService
				.getProrrogaActiva(idPrestamo);

		Date fechaFinProrrogaActiva = null;

		if (prorrogaActiva != null) {
			fechaFinProrrogaActiva = prorrogaActiva.getFechaFinProrroga();
		}

		ActionErrors errores = frm.validateFormulario(mapping, request,
				fechaFinProrrogaActiva);

		if (errores.isEmpty()) {
			try {

				prestamosService.autorizarProrrogas(idProrroga, idPrestamo,
						DateUtils.getDate(frm.getFechaFinProrroga()));
			} catch (PrestamoActionNotAllowedException e) {
				errores = ExceptionMapper.getErrorsExcepcion(request, e);
			}
		}

		if (errores != null && !errores.isEmpty()) {
			ErrorsTag.saveErrors(request, errores);
		}

		setReturnActionFordward(request, verPrestamoBeforeCreate(idPrestamo));
	}

	/**
	 * Realizar la autorización de una prorroga.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void inicioAutorizacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

	}

	protected void verProrrogaCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ProrrogaVO prorrogaVO) {
		ProrrogaForm formulario = (ProrrogaForm) form;

		if (prorrogaVO != null) {
			formulario.set(prorrogaVO);
		}
		setReturnActionFordward(request,
				mapping.findForward(FORWARD_GESTIONAR_PRORROGA));
	}

	private Date getFechaProrroga(Date fecha) {
		int plazoProrroga = Integer.parseInt(PropertyHelper
				.getProperty(PropertyHelper.PLAZO_PRORROGA));

		Date fechaActual = DateUtils.getFechaActualSinHora();

		if (fecha == null || DateUtils.isFechaMenor(fecha, fechaActual)) {
			fecha = DateUtils.addDays(fechaActual, plazoProrroga);
		} else {
			fecha = DateUtils.addDays(fecha, plazoProrroga);
		}

		return fecha;
	}

}
