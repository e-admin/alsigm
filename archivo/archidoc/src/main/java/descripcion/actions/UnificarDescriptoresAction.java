package descripcion.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Messages;
import common.actions.BaseAction;
import common.exceptions.TooManyResultsException;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.forms.UnificarDescriptoresForm;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.ListaDescrVO;

public class UnificarDescriptoresAction extends BaseAction {

	/*******************************************************************
	 * KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR
	 *******************************************************************/

	/**
	 * Acción de Inicio de la funcionalidad de unificar descriptores
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
	protected void initExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR,
				request);

		// UnificarDescriptoresForm frm = (UnificarDescriptoresForm) form;
		// frm.reset();

		cargarListas(request);

		request.setAttribute(DescripcionConstants.NOT_IS_POSTBACK, new Boolean(
				true));

		setReturnActionFordward(request,
				mapping.findForward("ver_busqueda_unificar"));

	}

	private void cargarListas(HttpServletRequest request) {

		// Listas descriptoras
		List listaDescr = getGestionDescripcionBI(request)
				.getListasDescriptorasExt();

		LinkedHashMap mapListas = new LinkedHashMap();

		int maxLength = 0;

		if (!ListUtils.isEmpty(listaDescr)) {
			Iterator it = listaDescr.iterator();

			while (it.hasNext()) {
				ListaDescrVO vo = (ListaDescrVO) it.next();
				String nombre = vo.getNombre();

				if (nombre.length() > maxLength)
					maxLength = nombre.length();
				mapListas.put(vo.getId(), vo.getNombre());
			}
		}

		setInTemporalSession(request,
				DescripcionConstants.LISTAS_DESCRIPTORAS_KEY, mapListas);

		LinkedHashMap mapOperadores = new LinkedHashMap();

		mapOperadores.put(
				"igual",
				Messages.getString("archigest.archivo.simbolo.exacta",
						request.getLocale()));
		mapOperadores.put("empieza",
				Messages.getString("archigest.archivo.simbolo.quecomiencecon",
						request.getLocale()));
		mapOperadores
				.put("termina", Messages.getString(
						"archigest.archivo.simbolo.queterminecon",
						request.getLocale()));
		mapOperadores.put("contiene", Messages.getString(
				"archigest.archivo.simbolo.quecontenga", request.getLocale()));

		setInTemporalSession(request, DescripcionConstants.MAP_OPERADORES_KEY,
				mapOperadores);
		setInTemporalSession(request, DescripcionConstants.MAX_LENGTH_KEY,
				String.valueOf(maxLength));
	}

	/**
	 * Accion de búsqueda de descriptores
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
	protected void busquedaExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		busquedaCodeLogic(mapping, form, request, response);

		setReturnActionFordward(request,
				mapping.findForward("ver_busqueda_unificar"));
	}

	/**
	 * Código de Búsqueda de Descriptores
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
	protected void busquedaCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UnificarDescriptoresForm frm = (UnificarDescriptoresForm) form;

		// Listas descriptoras
		BusquedaDescriptoresVO criterios = new BusquedaDescriptoresVO();

		criterios.setIdLista(frm.getIdListaDescriptora());

		if (StringUtils.isNotBlank(frm.getFiltro())) {
			criterios.setNombre(frm.getFiltro());
			criterios.setCualificadorNombre(frm.getCalificadorFiltro());
		}

		try {
			List listaDescriptores = getGestionDescripcionBI(request)
					.getDescriptores(criterios);
			CollectionUtils.transform(listaDescriptores,
					new TransformerDescriptorVOToPO(
							getServiceRepository(request)));

			List listaAReemplazar = (List) getFromTemporalSession(request,
					DescripcionConstants.LISTA_DESCRIPTORES_A_REEMPLAZAR);

			if (!ListUtils.isEmpty(listaAReemplazar))
				listaDescriptores.removeAll(listaAReemplazar);

			setInTemporalSession(request,
					DescripcionConstants.DESCRIPTORES_LISTA_KEY,
					listaDescriptores);
		} catch (TooManyResultsException e) {
			guardarError(request, e);
		}
		frm.resetSeleccionados();

	}

	/**
	 * Acción de Añadir descriptores para unificar
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
	protected void addDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UnificarDescriptoresForm frm = ((UnificarDescriptoresForm) form);
		int[] posiciones = frm.getPosSeleccionadas();

		List listaAReemplazar = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_REEMPLAZAR);
		List listaDescriptores = (List) getFromTemporalSession(request,
				DescripcionConstants.DESCRIPTORES_LISTA_KEY);

		if (ListUtils.isEmpty(listaAReemplazar))
			listaAReemplazar = new ArrayList();

		if (!ListUtils.isEmpty(listaDescriptores)) {
			for (int i = 0; i < posiciones.length; i++) {
				int posicion = posiciones[i];
				DescriptorPO descriptor = (DescriptorPO) listaDescriptores
						.get(posicion);

				DescriptorPO descriptorExistente = (DescriptorPO) ListUtils
						.isAnyElementRepeated(listaAReemplazar, descriptor);

				if (descriptorExistente == null) {
					listaAReemplazar.add(descriptor);
				}
			}
		}

		if (!ListUtils.isEmpty(listaAReemplazar))
			listaDescriptores.removeAll(listaAReemplazar);

		setInTemporalSession(request,
				DescripcionConstants.DESCRIPTORES_LISTA_KEY, listaDescriptores);
		setInTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_REEMPLAZAR,
				listaAReemplazar);
		frm.resetSeleccionados();

		setReturnActionFordward(request,
				mapping.findForward("ver_busqueda_unificar"));
	}

	/**
	 * Acción para eliminar descriptores a unificar
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
	protected void removeDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UnificarDescriptoresForm frm = ((UnificarDescriptoresForm) form);
		int[] posiciones = frm.getPosReemplazar();

		List listaAEliminar = new ArrayList();
		List listaAReemplazar = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_REEMPLAZAR);

		if (!ListUtils.isEmpty(listaAReemplazar)) {
			for (int i = 0; i < posiciones.length; i++) {
				int posicion = posiciones[i];
				DescriptorPO descriptor = (DescriptorPO) listaAReemplazar
						.get(posicion);

				if (descriptor.getId().equals(frm.getIdPrincipal())) {
					frm.setIdPrincipal(null);
				}

				listaAEliminar.add(descriptor);
			}
		}

		if (!ListUtils.isEmpty(listaAEliminar))
			listaAReemplazar.removeAll(listaAEliminar);

		setInTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_REEMPLAZAR,
				listaAReemplazar);

		busquedaCodeLogic(mapping, form, request, response);

		setReturnActionFordward(request,
				mapping.findForward("ver_busqueda_unificar"));

	}

	/*******************************************************************
	 * FIN KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR
	 *******************************************************************/

	/********************************************************************************
	 * KeysClientsInvocations.
	 * DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR_CONFIRMACION
	 ********************************************************************************/

	/**
	 * Accion de ver informe de unificar
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
	protected void informeExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		List listaAReemplazar = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_REEMPLAZAR);

		// removeInTemporalSession(request,
		// DescripcionConstants.LISTA_DESCRIPTOR_PRINCIPAL);
		// removeInTemporalSession(request,
		// DescripcionConstants.LISTA_DESCRIPTORES_A_UNIFICAR);

		UnificarDescriptoresForm frm = (UnificarDescriptoresForm) form;

		List listaPrincipal = new ArrayList();
		List listaDescriptores = new ArrayList();

		ActionErrors errors = getErrors(request, true);
		ActionErrors messages = new ActionErrors();
		// ActionMessages messages = getMessages(request, true);

		boolean isConErrores = false;
		boolean isConMensajes = false;

		List listaDescriptoresConDocumentos = new ArrayList();
		List listaDescriptoresConFicha = new ArrayList();

		// Realizar comprobaciones
		if (!ListUtils.isEmpty(listaAReemplazar)) {
			ListIterator it = listaAReemplazar.listIterator();

			while (it.hasNext()) {
				DescriptorPO descriptor = (DescriptorPO) it.next();

				// Comprobar si es elemento Seleccionado
				if (descriptor.getId().equals(frm.getIdPrincipal())) {
					listaPrincipal.add(descriptor);
				} else {
					listaDescriptores.add(descriptor);

					// Comprobar si tiene documentos
					if (descriptor.isConDocumentos()) {
						messages.add(
								common.Constants.ERROR_GENERAL_MESSAGE,
								new ActionError(
										DescripcionConstants.MSG_DESCRIPTOR_CON_DOCUMENTOS,
										descriptor.getNombre()));
						isConMensajes = true;

						listaDescriptoresConDocumentos.add(descriptor);
					}

					if (descriptor.isConDescripcion()) {
						messages.add(
								common.Constants.ERROR_GENERAL_MESSAGE,
								new ActionError(
										DescripcionConstants.MSG_DESCRIPTOR_CON_FICHA,
										descriptor.getNombre()));
						isConMensajes = true;

						listaDescriptoresConFicha.add(descriptor);
					}

				}
			}

			if (!ListUtils.isEmpty(listaPrincipal)) {
				if (ListUtils.isEmpty(listaDescriptores)) {
					// No hay descriptores a Reeemplazar
					errors.add(
							common.Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(
									common.Constants.ERROR_GENERAL_MESSAGE,
									Messages.getString(
											DescripcionConstants.ERROR_NO_DESCRIPTORES_PARA_REEMPLAZAR,
											request.getLocale())));
					isConErrores = true;
				}
			} else {
				// No hay descriptor principal
				errors.add(
						common.Constants.ERROR_GENERAL_MESSAGE,
						new ActionError(
								common.Constants.ERROR_GENERAL_MESSAGE,
								Messages.getString(
										DescripcionConstants.ERROR_NO_DESCRIPTOR_PRINCIPAL,
										request.getLocale())));

				isConErrores = true;
			}
		} else {
			// No hay descriptores a Reeemplazar
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									DescripcionConstants.ERROR_NO_DESCRIPTORES_PARA_REEMPLAZAR,
									request.getLocale())));

			isConErrores = true;
		}

		if (!isConErrores) {
			if (isConMensajes) {
				setInTemporalSession(request,
						DescripcionConstants.LISTA_DESCRIPTORES_CON_DOCUMENTOS,
						listaDescriptoresConDocumentos);
				setInTemporalSession(request,
						DescripcionConstants.LISTA_DESCRIPTORES_CON_FICHA,
						listaDescriptoresConFicha);

				ErrorsTag.saveErrors(request, messages);
			}

			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_UNIFICAR_CONFIRMAR,
					request);
			setInTemporalSession(request,
					DescripcionConstants.LISTA_DESCRIPTOR_PRINCIPAL,
					listaPrincipal);
			setInTemporalSession(request,
					DescripcionConstants.LISTA_DESCRIPTORES_A_UNIFICAR,
					listaDescriptores);

			setReturnActionFordward(request,
					mapping.findForward("ver_confirmacion_unificar"));
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("ver_busqueda_unificar"));
		}

	}

	/**
	 * Accion de ver informe de unificar
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
	protected void atrasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		goBackExecuteLogic(mapping, form, request, response);
	}

	protected void unificarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionErrors errors = getErrors(request, true);
		String[] idsAReemplazar = new String[0];

		List listaIdPrincipal = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTOR_PRINCIPAL);
		List listaAReeemplazar = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_UNIFICAR);

		String[] nombresDescriptoresReemplazar = new String[0];

		try {
			DescriptorPO descriptorPrincipal = (DescriptorPO) listaIdPrincipal
					.get(0);
			// idDescriptor = descriptorPrincipal.getId();

			ListIterator it = listaAReeemplazar.listIterator();
			while (it.hasNext()) {
				DescriptorPO descriptor = (DescriptorPO) it.next();
				idsAReemplazar = (String[]) ArrayUtils.add(idsAReemplazar,
						descriptor.getId());

				nombresDescriptoresReemplazar = (String[]) ArrayUtils.add(
						nombresDescriptoresReemplazar, descriptor.getNombre());
			}

			getGestionDescripcionBI(request).unificarDescriptores(
					descriptorPrincipal, idsAReemplazar,
					nombresDescriptoresReemplazar);
		} catch (Exception e) {
			logger.error("Error al Unificar los Descriptores", e);

			// No hay descriptores a Reeemplazar
			errors.add(
					common.Constants.ERROR_GENERAL_MESSAGE,
					new ActionError(
							common.Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									DescripcionConstants.ERROR_AL_UNIFICAR_DESCRIPTORES,
									request.getLocale())));
		}

		if (errors == null || errors.isEmpty()) {
			UnificarDescriptoresForm frm = (UnificarDescriptoresForm) form;
			frm.reset();

			setReturnActionFordward(request,
					mapping.findForward("ver_informe_unificar"));
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("ver_confirmacion_unificar"));
		}
	}

}
