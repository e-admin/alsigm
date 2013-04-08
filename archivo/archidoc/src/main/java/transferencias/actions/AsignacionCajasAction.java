package transferencias.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import transferencias.forms.AsignacionCajasForm;
import transferencias.model.EstadoREntrega;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadInstalacionVO;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.TypeConverter;

/**
 *
 */
public class AsignacionCajasAction extends BaseAction {

	public void cajasRelacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_ORGANIZACION_CAJAS,
				request);

		String idRelacion = request.getParameter("idRelacion");
		RelacionEntregaVO relacionEntrega = relacionBI
				.abrirRelacionEntrega(idRelacion);
		List unidadesInstalacion = relacionBI
				.getUnidadesInstalacion(idRelacion);
		CollectionUtils.transform(unidadesInstalacion,
				UnidadInstalacionToPO2.getInstance(services));

		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				(RelacionEntregaPO) RelacionToPO.getInstance(services)
						.transform(relacionEntrega));

		setInTemporalSession(request, TransferenciasConstants.LISTA_CAJAS_KEY,
				unidadesInstalacion);

		setReturnActionFordward(request, mappings.findForward("cajas_relacion"));
	}

	public void nuevaCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		String pIdRelacion = request.getParameter("idRelacion");
		List udocsSinAsignar = relacionBI.getExpedientesSinCaja(pIdRelacion);
		// Para indicar si se va asociar o crear una nueva caja, en cuyo caso se
		// mostrara o no el campo signatura.
		if (request.getParameter("asignacion") != null) {
			((AsignacionCajasForm) form).setAsignando(true);
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_ASOCIAR_CAJA, request);
		} else {
			((AsignacionCajasForm) form).setAsignando(false);
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_NUEVA_CAJA, request);
		}
		request.setAttribute(TransferenciasConstants.EXPEDIENTES_SIN_ASIGNAR,
				udocsSinAsignar);
		setReturnActionFordward(request, mappings.findForward("nueva_caja"));
	}

	private ActionErrors validateFormInCrearCaja(
			AsignacionCajasForm asignacionForm, RelacionEntregaPO relacion) {
		ActionErrors errores = new ActionErrors();
		if (relacion.isSignaturaSolictableEnUDoc()
				|| relacion.isSignaturaSolictableEnUI()
				|| asignacionForm.isAsignando()) {
			if (GenericValidator.isBlankOrNull(asignacionForm
					.getSignaturaCaja())) {
				errores.add(
						TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA,
						new ActionError(
								TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA));
			}
			if (!ConfigConstants.getInstance()
					.getPermitirSignaturaAlfanumerica())
				if (!GenericValidator.isBlankOrNull(asignacionForm
						.getSignaturaCaja()))
					if (!GenericValidator.isInt(asignacionForm
							.getSignaturaCaja()))
						errores.add(
								TransferenciasConstants.SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO,
								new ActionError(
										TransferenciasConstants.SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO));
		}

		if (asignacionForm.getUdocSeleccionada() == null
				|| asignacionForm.getUdocSeleccionada().length == 0)
			errores.add(
					TransferenciasConstants.NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA,
					new ActionError(
							TransferenciasConstants.NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA));

		return errores;
	}

	private ActionErrors validateFormInEditarSignatura(
			AsignacionCajasForm asignacionForm, RelacionEntregaPO relacion) {
		ActionErrors errores = new ActionErrors();
		if (relacion.isSignaturaSolictableEnUI()) {
			if (GenericValidator.isBlankOrNull(asignacionForm
					.getSignaturaCaja())) {
				errores.add(
						TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA,
						new ActionError(
								TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA));
			}

			if (!ConfigConstants.getInstance()
					.getPermitirSignaturaAlfanumerica())
				if (!GenericValidator.isBlankOrNull(asignacionForm
						.getSignaturaCaja()))
					if (!GenericValidator.isInt(asignacionForm
							.getSignaturaCaja()))
						errores.add(
								TransferenciasConstants.SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO,
								new ActionError(
										TransferenciasConstants.SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO));
		}

		return errores;
	}

	private ActionErrors validateFormSignatura(
			AsignacionCajasForm asignacionForm) {
		ActionErrors errores = new ActionErrors();
		if (GenericValidator.isBlankOrNull(asignacionForm.getSignaturaCaja()))
			errores.add(
					TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA,
					new ActionError(
							TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA));
		return errores;
	}

	public void crearCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		AsignacionCajasForm asignacionForm = (AsignacionCajasForm) form;

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((AsignacionCajasForm) form).getMap());

		String pIdRelacion = request.getParameter("idRelacion");
		RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		try {
			ActionErrors errors = validateFormInCrearCaja(asignacionForm,
					relacion);
			if (errors.isEmpty()) {
				UnidadInstalacionVO caja = null;
				if (asignacionForm.getSignaturaCaja() != null) {
					caja = relacionBI.crearCajaSignaturada(pIdRelacion,
							relacion.getIdarchivoreceptor(),
							asignacionForm.getUdocSeleccionada(),
							asignacionForm.getSignaturaCaja(),
							asignacionForm.isAsignando(),
							relacion.getIdFormatoDestino());
				} else
					caja = relacionBI.crearCajaSinSignatura(pIdRelacion,
							asignacionForm.getUdocSeleccionada());

				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) UnidadInstalacionToPO2
						.getInstance(services).transform(caja);

				setInTemporalSession(request, TransferenciasConstants.CAJA_KEY,
						cajaPO);

				popLastInvocation(request);

				int numeroCajas = relacionBI
						.getNUnidadesInstalacion(pIdRelacion);
				ActionRedirect ret = new ActionRedirect(
						mappings.findForward("redirect_editar_caja"));
				ret.setRedirect(true);
				ret.addParameter("idUnidadInstalacion", cajaPO.getId());
				ret.addParameter("ordenCaja", new Integer(numeroCajas));

				updateEstadoRelacionRechazadaInSession(request);
				setReturnActionFordward(request, ret);

			} else {
				ErrorsTag.saveErrors(request, errors);
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void editarSignaturaCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		AsignacionCajasForm asignacionForm = (AsignacionCajasForm) form;

		UnidadInstalacionVO caja = relacionBI
				.getUnidadInstalacion(asignacionForm.getIdUnidadInstalacion());
		UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) UnidadInstalacionToPO2
				.getInstance(services).transform(caja);

		asignacionForm.setSignaturaCaja(cajaPO.getSignaturaUI());

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EDICION_CAJA, request);
		setInTemporalSession(request, TransferenciasConstants.CAJA_KEY, cajaPO);
		setReturnActionFordward(request,
				mappings.findForward("edicion_signatura_caja"));
	}

	public void guardarEditarSignaturaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
				request, TransferenciasConstants.CAJA_KEY);
		try {
			// Si no se ha modificado la signatura, simplemente se vuelve a
			// atrás
			if ((cajaPO != null)
					&& (cajaPO.getSignaturaUI() != null)
					&& (cajaPO.getSignaturaUI().equalsIgnoreCase(formAsignacion
							.getSignaturaCaja()))) {
				removeInTemporalSession(request,
						TransferenciasConstants.CAJA_KEY);
				goBackExecuteLogic(mappings, form, request, response);
			} else {
				ActionErrors errors = validateFormInEditarSignatura(
						formAsignacion, relacion);
				if (errors.isEmpty()) {
					relacionBI.updateSignaturaCaja(relacion.getId(), cajaPO,
							formAsignacion.getSignaturaCaja(),
							relacion.getIdarchivoreceptor());
					removeInTemporalSession(request,
							TransferenciasConstants.CAJA_KEY);
					updateEstadoRelacionRechazadaInSession(request);
					goBackExecuteLogic(mappings, form, request, response);
				} else {
					ErrorsTag.saveErrors(request, errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				}
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void editarCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		AsignacionCajasForm asignacionForm = (AsignacionCajasForm) form;
		UnidadInstalacionVO caja = relacionBI
				.getUnidadInstalacion(asignacionForm.getIdUnidadInstalacion());
		UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) UnidadInstalacionToPO2
				.getInstance(services).transform(caja);
		if (StringUtils.isNotEmpty(caja.getSignaturaUI()))
			asignacionForm.setAsignando(true);

		asignacionForm.setIdRelacion(cajaPO.getIdRelEntrega());

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EDICION_CAJA, request);
		setInTemporalSession(request, TransferenciasConstants.CAJA_KEY, cajaPO);
		setReturnActionFordward(request, mappings.findForward("edicion_caja"));
	}

	public void actualizarPosicionExpedienteExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		/* GestionRelacionesEntregaBI relacionBI = */services
				.lookupGestionRelacionesBI();
		// relacionBI.moveUdocEnUI(formAsignacion.getIdUnidadDocumental(),
		// formAsignacion.getIdUnidadInstalacion(),
		// formAsignacion.getCambioPosicion());
		setReturnActionFordward(request, mappings.findForward("edicion_caja"));
	}

	public void sacarExpedienteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);
				relacionBI.removeUdocsFromUi(TypeConverter
						.toIntArray(formAsignacion.getUdocSeleccionada()),
						cajaPO.getId());
				updateEstadoRelacionRechazadaInSession(request);
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);

		}
		getInvocationStack(request).getLastClientInvocation().addParameter(
				"udocSeleccionada", null);
		goLastClientExecuteLogic(mappings, form, request, response);

	}

	/**
	 * Cuando la relación de entrega está con errores de cotejo, al eliminar una
	 * parte de una caja se eliminan el resto de partes de las cajas.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void sacarExpedienteRelacionConErroresExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();

		try {
			if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);
				relacionBI.removeUdocsFromUiConErrores(TypeConverter
						.toIntArray(formAsignacion.getUdocSeleccionada()),
						cajaPO.getId());
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
		}
		getInvocationStack(request).getLastClientInvocation().addParameter(
				"udocSeleccionada", null);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void incorporarACajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		List udocsSinAsignar = relacionBI.getExpedientesSinCaja(relacion
				.getId());
		// AsignacionCajasForm frm = (AsignacionCajasForm) form;
		// int ordenCaja = frm.getOrdenCaja();

		request.setAttribute(
				TransferenciasConstants.INCORPORANDO_UNIDAD_DOCUMENTAL,
				new Boolean(true));
		request.setAttribute(TransferenciasConstants.EXPEDIENTES_SIN_ASIGNAR,
				udocsSinAsignar);
		request.setAttribute(Constants.METHOD_KEY, "guardarIncorporarACaja");
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_INCORPORAR_A_CAJA,
				request);
		setReturnActionFordward(request, mappings.findForward("nueva_caja"));
	}

	public void guardarIncorporarACajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
				request, TransferenciasConstants.CAJA_KEY);
		/* RelacionEntregaVO relacion = (RelacionEntregaVO) */getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		try {
			GestionRelacionesEntregaBI relacionesBI = getServiceRepository(
					request).lookupGestionRelacionesBI();
			relacionesBI.incorporarACaja(cajaPO,
					formAsignacion.getUdocSeleccionada());
			formAsignacion.setUdocSeleccionada(null);
			updateEstadoRelacionRechazadaInSession(request);
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
		}
		goBackExecuteLogic(mappings, form, request, response);
	}

	private class PredicateFinderByPos implements Predicate {
		int posToFind;

		PredicateFinderByPos(int pos) {
			posToFind = pos;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) arg0;
			return aPart.getPosUdocEnUI() == posToFind;
		}

	}

	public void subirDentroDeCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);
				List partesDocumentalesDeCaja = cajaPO.getContenido();
				List partesSeleccionadas = new ArrayList();
				int[] posiciones = TypeConverter.toIntArray(formAsignacion
						.getUdocSeleccionada());
				for (int i = 0; i < posiciones.length; i++) {
					IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) CollectionUtils
							.find(partesDocumentalesDeCaja,
									new PredicateFinderByPos(posiciones[i]));
					partesSeleccionadas.add(parteUDoc);

				}
				relacionBI.subirExpediente(partesSeleccionadas, cajaPO.getId());

				// Nuevo para recuperar las posiciones seleccionadas
				String[] nuevasPosiciones = new String[posiciones.length];
				for (int i = 0; i < posiciones.length; i++)
					nuevasPosiciones[i] = new Integer((posiciones[i] - 1))
							.toString();
				formAsignacion.setUdocSeleccionada(nuevasPosiciones);

				updateEstadoRelacionRechazadaInSession(request);

				// Añadir el parámetro con las unidades documentales
				// seleccionadas para que aparezcan marcadas
				getInvocationStack(request).getLastClientInvocation()
						.addParameter("udocSeleccionada", nuevasPosiciones);
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			getInvocationStack(request).getLastClientInvocation().addParameter(
					"udocSeleccionada", formAsignacion.getUdocSeleccionada());
			guardarError(request, e);

		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void bajarDentroDeCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			if (!ArrayUtils.isEmpty(formAsignacion.getUdocSeleccionada())) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);
				List partesDocumentalesDeCaja = cajaPO.getContenido();
				List partesSeleccionadas = new ArrayList();
				int[] posiciones = TypeConverter.toIntArray(formAsignacion
						.getUdocSeleccionada());
				for (int i = 0; i < posiciones.length; i++) {
					IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) CollectionUtils
							.find(partesDocumentalesDeCaja,
									new PredicateFinderByPos(posiciones[i]));
					partesSeleccionadas.add(parteUDoc);

				}
				relacionBI.bajarExpediente(partesSeleccionadas, cajaPO.getId());

				// Nuevo para recuperar las posiciones seleccionadas
				String[] nuevasPosiciones = new String[posiciones.length];
				for (int i = 0; i < posiciones.length; i++)
					nuevasPosiciones[i] = new Integer((posiciones[i] + 1))
							.toString();
				formAsignacion.setUdocSeleccionada(nuevasPosiciones);

				updateEstadoRelacionRechazadaInSession(request);

				// Añadir el parámetro con las unidades documentales
				// seleccionadas para que aparezcan marcadas
				getInvocationStack(request).getLastClientInvocation()
						.addParameter("udocSeleccionada", nuevasPosiciones);
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			getInvocationStack(request).getLastClientInvocation().addParameter(
					"udocSeleccionada", formAsignacion.getUdocSeleccionada());
			guardarError(request, e);

		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	public void editarDescripcionContenidoParteExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacionEntrega = relacionBI
				.getRelacionXIdRelacion(formAsignacion.getIdRelacion());
		Integer tipoTransferencia = (relacionEntrega == null) ? null
				: new Integer(relacionEntrega.getTipotransferencia());
		String[] udocsSeleccionadas = formAsignacion.getUdocSeleccionada();
		if (udocsSeleccionadas != null && udocsSeleccionadas.length == 1) {
			UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
					request, TransferenciasConstants.CAJA_KEY);

			String posSeleccionada = udocsSeleccionadas[0];
			IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) CollectionUtils
					.find(cajaPO.getContenido(), new PredicateFinderByPos(
							TypeConverter.toInt(posSeleccionada)));

			setInTemporalSession(request,
					TransferenciasConstants.PARTE_UDOC_SELECCIONADA,
					(IParteUnidadDocumentalVO) ParteUnidadDocumentalPO
							.getInstance(services, tipoTransferencia)
							.transform(parteUDoc));

			setReturnActionFordward(request,
					mappings.findForward("edicion_descripcion_contenido_parte"));

			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_EDICION_DESCRIPCION_PARTE,
					request);

		} else if (udocsSeleccionadas == null || udocsSeleccionadas.length == 0) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Messages.getString(
									TransferenciasConstants.NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA,
									request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_caja"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Messages.getString(
									TransferenciasConstants.ERROR_TRANSFERENCIAS_SEL_UN_EXPEDIENTE,
									request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_caja"));
		}

	}

	public void guardarEditarDescripcionContenidoParteExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		IParteUnidadDocumentalVO parte = (IParteUnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.PARTE_UDOC_SELECCIONADA);

		relacionBI.updateDescripcionUdoc(parte.getIdUnidadDoc(),
				parte.getNumParteUdoc(),
				formAsignacion.getDescripcionContenido());

		removeInTemporalSession(request,
				TransferenciasConstants.PARTE_UDOC_SELECCIONADA);
		updateEstadoRelacionRechazadaInSession(request);
		goBackExecuteLogic(mappings, form, request, response);

	}

	public void dividirUdocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			String[] udocsSeleccionadas = formAsignacion.getUdocSeleccionada();

			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);

				String posSeleccionada = udocsSeleccionadas[0];
				ParteUnidadDocumentalVO parteUDoc = (ParteUnidadDocumentalVO) CollectionUtils
						.find(cajaPO.getContenido(), new PredicateFinderByPos(
								TypeConverter.toInt(posSeleccionada)));

				RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
						request, TransferenciasConstants.RELACION_KEY);

				relacionBI.dividirParteUdoc(relacion, parteUDoc);
				updateEstadoRelacionRechazadaInSession(request);
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	public void dividirUdocUIExistenteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			String[] udocsSeleccionadas = formAsignacion.getUdocSeleccionada();
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);
				String posSeleccionada = udocsSeleccionadas[0];
				ParteUnidadDocumentalVO parteUDoc = (ParteUnidadDocumentalVO) CollectionUtils
						.find(cajaPO.getContenido(), new PredicateFinderByPos(
								TypeConverter.toInt(posSeleccionada)));
				relacionBI.checkIsPartDivisible(parteUDoc);

				setInTemporalSession(request,
						TransferenciasConstants.PARTE_UDOC_SELECCIONADA,
						parteUDoc);
				saveCurrentInvocation(
						KeysClientsInvocations.TRANSFERENCIAS_DIVIDIR_UDOC,
						request);
				setReturnActionFordward(request,
						mappings.findForward("dividir_udoc"));
			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, formAsignacion, request,
					response);
		}
	}

	public void dividirUDocEnUIExistenteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		try {
			ActionErrors errors = validateFormSignatura(formAsignacion);
			if (errors.isEmpty()) {
				String signatura = formAsignacion.getSignaturaCaja();
				IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) getFromTemporalSession(
						request,
						TransferenciasConstants.PARTE_UDOC_SELECCIONADA);
				UnidadInstalacionVO uInstalacion = relacionBI
						.getUnidadInstalacion(parteUDoc.getIdUinstalacionRe());
				/* SI se da este caso, es que estamos en una caja creada nueva */
				if (uInstalacion != null
						&& uInstalacion.getSignaturaUI() == null) {
					relacionBI.dividirParteUdocEnUIExistente(relacion,
							parteUDoc, formAsignacion.getSignaturaCaja());
					removeInTemporalSession(request,
							TransferenciasConstants.CAJA_KEY);
					goBackExecuteLogic(mappings, form, request, response);
				}
				/* Este caso es cuando estamos en una caja existente */
				else if (uInstalacion != null
						&& uInstalacion.getSignaturaUI() != null
						&& !uInstalacion.getSignaturaUI().equals(signatura)) {
					relacionBI.dividirParteUdocEnUIExistente(relacion,
							parteUDoc, formAsignacion.getSignaturaCaja());
					removeInTemporalSession(request,
							TransferenciasConstants.CAJA_KEY);
					goBackExecuteLogic(mappings, form, request, response);
				} else
					throw new RelacionOperacionNoPermitidaException(
							RelacionOperacionNoPermitidaException.X_IGUAL_SIGNATURA_QUE_UI_EXISTENTE);
			} else {
				ErrorsTag.saveErrors(request, errors);
				goLastClientExecuteLogic(mappings, form, request, response);
			}

		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);
			setReturnActionFordward(request,
					mappings.findForward("dividir_udoc"));
		}
	}

	public void eliminarParteExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			String[] udocsSeleccionadas = formAsignacion.getUdocSeleccionada();
			if (udocsSeleccionadas != null && udocsSeleccionadas.length > 0) {
				UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
						request, TransferenciasConstants.CAJA_KEY);

				String posSeleccionada = udocsSeleccionadas[0];
				IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) CollectionUtils
						.find(cajaPO.getContenido(), new PredicateFinderByPos(
								TypeConverter.toInt(posSeleccionada)));

				relacionBI.removeParteUdoc(parteUDoc);
				updateEstadoRelacionRechazadaInSession(request);

			}
		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);

		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminarUDocElectronicaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;

		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();

		relacionBI
				.deleteUdocElectronica(formAsignacion.getIdUnidadDocumental());
		updateEstadoRelacionRechazadaInSession(request);
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminarCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AsignacionCajasForm formAsignacion = (AsignacionCajasForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			UnidadInstalacionVO caja = relacionBI
					.getUnidadInstalacion(formAsignacion
							.getIdUnidadInstalacion());
			relacionBI.removeUnidadInstalacion(relacion, caja);

		} catch (RelacionOperacionNoPermitidaException e) {
			guardarError(request, e);

		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	private void updateEstadoRelacionRechazadaInSession(
			HttpServletRequest request) {
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (relacionEntrega.isRechazada())
			relacionEntrega
					.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
	}

}
