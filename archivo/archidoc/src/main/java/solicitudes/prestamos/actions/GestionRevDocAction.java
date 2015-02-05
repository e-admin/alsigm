package solicitudes.prestamos.actions;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.prestamos.EstadoRevDoc;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.forms.RevisionDocumentacionForm;
import solicitudes.prestamos.vos.RevisionDocumentacionToPO;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionPrestamosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.SecurityException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.security.SecurityManagerBase;
import common.security.SecurityManagerLocator;
import common.security.TransferenciasSecurityManager;

import fondos.vos.UnidadDocumentalVO;

/**
 * Action para las obtener la documentación de unidades documentales a revisar.
 */
public class GestionRevDocAction extends BaseAction {

	/**
	 * Muestra el listado de documentación asociada a unidades documentales en
	 * revisión.
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
	protected void listadoRevisionDocExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		Collection revisionesDocumentacion = prestamosService
				.getRevisionDocumentacionByEstado(appUser.getId(),
						EstadoRevDoc.ABIERTA.getIdentificador());
		CollectionUtils.transform(revisionesDocumentacion,
				RevisionDocumentacionToPO.getInstance(request.getLocale(),
						services));
		setInTemporalSession(request,
				PrestamosConstants.LISTA_REVISION_DOCUMENTACION_KEY,
				revisionesDocumentacion);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.PRESTAMOS_LISTADO_REVISION_DOCUMENTACION,
				request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mapping.findForward("listado_revisionDoc"));
	}

	/**
	 * Se encarga de mostrar los detalles la unidad documental con la
	 * descripcion y los documentos asociados.
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
	protected void verUnidadDocumentalExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String idUdoc = request.getParameter("idUdoc");
		ServiceRepository services = getServiceRepository(request);
		GestionUnidadDocumentalBI udocBI = services
				.lookupGestionUnidadDocumentalBI();
		UnidadDocumentalVO udoc = udocBI.getUnidadDocumental(idUdoc);

		ActionRedirect vistaUdoc = null;
		vistaUdoc = new ActionRedirect(
				mapping.findForward("ver_en_cuadro_clasificacion"));
		vistaUdoc.addParameter(Constants.ID, idUdoc);

		if (udoc != null && udoc.isSubtipoCaja()) {
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_VER_FRACCION_SERIE, request);
		} else {
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_VER_UNIDAD_DOCUMENTAL,
					request);
		}
		vistaUdoc.setRedirect(true);
		setReturnActionFordward(request, vistaUdoc);
	}

	/**
	 * Se encarga de pasar a rechazada la documentacion de la unidad documental
	 * seleccionada indicando el motivo de su rechazo en el campo observaciones.
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
	protected void rechazarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RevisionDocumentacionForm revDocForm = ((RevisionDocumentacionForm) form);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		String idRevDoc = revDocForm.getIdRevDocSeleccionado()[0];
		if (StringUtils.isNotEmpty(idRevDoc)) {
			ActionErrors errors = validateForm(request, revDocForm);
			if (errors.isEmpty()) {
				RevisionDocumentacionVO revDocVO = prestamosService
						.getRevisionDocumentacionById(idRevDoc);
				if (!relacionBI.existeRelacion(revDocVO.getIdAlta())) {
					revDocVO.setMotivoRechazo(revDocForm.getMotivoRechazo());
					revDocVO.setEstado(EstadoRevDoc.RECHAZADA
							.getIdentificador());
					prestamosService.actualizarRevisionDocumentacion(revDocVO);
					revDocForm.reset();
					goLastClientExecuteLogic(mapping, revDocForm, request,
							response);
				} else {
					revDocForm.setMotivoRechazo("");
					revDocForm.setOcultarMotivo(true);
					errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(
									PrestamosConstants.ERROR_NO_POSIBLE_RECHAZAR_REVDOC_CON_ALTA));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("listado_revisionDoc"));
				}
			} else {
				ErrorsTag.saveErrors(request, errors);
				revDocForm.setOcultarMotivo(false);
				setReturnActionFordward(request,
						mapping.findForward("listado_revisionDoc"));
			}
		}
	}

	/**
	 * Se encarga de Finalizar la revisión de documentación de la unidad
	 * documental seleccionada.
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
	protected void finalizarExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String idRevDoc = request.getParameterValues("idRevDocSeleccionado")[0];
		ServiceRepository services = getServiceRepository(request);
		GestionPrestamosBI prestamosBI = services.lookupGestionPrestamosBI();
		RevisionDocumentacionVO revDocVO = prestamosBI
				.getRevisionDocumentacionById(idRevDoc);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		boolean copiarUdocsRelOrigen = false;

		if (revDocVO != null) {
			if (StringUtils.isNotEmpty(revDocVO.getIdAlta())) {
				RelacionEntregaVO relacionVO = relacionBI
						.getRelacionXIdRelacion(revDocVO.getIdAlta());
				if (relacionVO != null
						&& EstadoREntrega.VALIDADA.getIdentificador() != relacionVO
								.getEstado()) {
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							PrestamosConstants.ERROR_ALTA_UDOC_NO_VALIDADA));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("listado_revisionDoc"));
				} else {
					if (StringUtils.isEmpty(relacionVO.getIdFicha()))
						copiarUdocsRelOrigen = true;
					prestamosBI.finalizarRevisionDocumentacion(revDocVO,
							copiarUdocsRelOrigen);
					goLastClientExecuteLogic(mapping, form, request, response);
				}
			} else {
				revDocVO.setEstado(EstadoRevDoc.FINALIZADA.getIdentificador());
				prestamosBI.actualizarRevisionDocumentacion(revDocVO);
				goLastClientExecuteLogic(mapping, form, request, response);
			}
		}
	}

	/**
	 * Se encarga de dar de alta la unidad documental siempre y cuando no este
	 * ya creada.
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
	protected void addAltaUdocExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		SecurityManagerBase securityManager = SecurityManagerLocator
				.loockupTransferenciasSM();

		try {
			securityManager
					.check(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS,
							getServiceClient(request));

			String idRevDoc = request.getParameter("idRevDoc");
			ServiceRepository services = getServiceRepository(request);
			GestionPrestamosBI prestamosService = services
					.lookupGestionPrestamosBI();
			RevisionDocumentacionVO revDocVO = prestamosService
					.getRevisionDocumentacionById(idRevDoc);
			if (revDocVO != null && revDocVO.getIdAlta() == null) {
				ActionRedirect addAltaUdoc = null;
				addAltaUdoc = new ActionRedirect(
						mapping.findForward("crear_alta_udoc"));
				addAltaUdoc.addParameter(Constants.ID_REV_DOC,
						revDocVO.getIdRevDoc());
				addAltaUdoc.setRedirect(true);
				setReturnActionFordward(request, addAltaUdoc);
			} else {
				ActionErrors errors = new ActionErrors();
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								PrestamosConstants.ERROR_ALTA_UDOC_USUARIO_SIN_PERMISOS));
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mapping.findForward("listado_revisionDoc"));
			}
		} catch (SecurityException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					PrestamosConstants.ERROR_ALTA_UDOC_USUARIO_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("listado_revisionDoc"));
		}
	}

	/**
	 * Se encarga de eliminar el alta de unidad documental siempre y cuando no
	 * este validada.
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
	protected void removeAltaUdocExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idRevDoc = request.getParameter("idRevDoc");
		ServiceRepository services = getServiceRepository(request);
		GestionPrestamosBI prestamosService = services
				.lookupGestionPrestamosBI();
		RevisionDocumentacionVO revDocVO = prestamosService
				.getRevisionDocumentacionById(idRevDoc);
		SecurityManagerBase securityManager = SecurityManagerLocator
				.loockupTransferenciasSM();
		try {
			securityManager
					.check(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS,
							getServiceClient(request));

			if (revDocVO != null && revDocVO.getIdAlta() != null) {
				GestionRelacionesEntregaBI relacionBI = services
						.lookupGestionRelacionesBI();
				relacionBI.eliminarIngresosDirectos(new String[] { revDocVO
						.getIdAlta() });

				revDocVO.setIdAlta(null);
				prestamosService.actualizarRevisionDocumentacion(revDocVO);
			} else {
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						PrestamosConstants.ERROR_ALTA_UDOC_NO_EXISTE));
				ErrorsTag.saveErrors(request, errors);
			}
		} catch (ActionNotAllowedException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					PrestamosConstants.ERROR_ALTA_UDOC_NO_ABIERTA));
			ErrorsTag.saveErrors(request, errors);
		} catch (SecurityException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					PrestamosConstants.ERROR_ALTA_UDOC_USUARIO_SIN_PERMISOS));
			ErrorsTag.saveErrors(request, errors);
		}
		setReturnActionFordward(request,
				mapping.findForward("listado_revisionDoc"));
	}

	/**
	 * Realiza la validación del formulario
	 * 
	 * @param request
	 * @param frm
	 * @return
	 */
	private ActionErrors validateForm(HttpServletRequest request,
			RevisionDocumentacionForm frm) {
		ActionErrors errors = new ActionErrors();

		if (GenericValidator.isBlankOrNull(frm.getMotivoRechazo()))
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_MOTIVO_DE_RECHAZO,
									request.getLocale())));

		return errors;
	}

	/**
	 * Muestra la documentación asociada a la unidad documental en revisión.
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
	protected void mostrarRevisionDocExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionPrestamosBI prestamosService = getGestionPrestamosBI(request);
		RevisionDocumentacionForm revDocForm = ((RevisionDocumentacionForm) form);
		Collection revisionesDocumentacion = prestamosService
				.getRevisionesDocumentacionByIdUdocYEstado(
						revDocForm.getIdUdoc(),
						new int[] { EstadoRevDoc.ABIERTA.getIdentificador() });
		CollectionUtils.transform(revisionesDocumentacion,
				RevisionDocumentacionToPO.getInstance(request.getLocale(),
						getServiceRepository(request)));
		setInTemporalSession(request,
				PrestamosConstants.LISTA_REVISION_DOCUMENTACION_KEY,
				revisionesDocumentacion);

		saveCurrentInvocation(
				KeysClientsInvocations.PRESTAMOS_REVISION_DOCUMENTACION,
				request);
		setReturnActionFordward(request,
				mapping.findForward("listado_revisionDoc"));
	}
}