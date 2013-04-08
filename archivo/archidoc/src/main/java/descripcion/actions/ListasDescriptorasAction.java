package descripcion.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionDescripcionBI;
import common.exceptions.TooManyResultsException;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.StringUtils;
import common.vos.ResultadoRegistrosVO;

import descripcion.DescripcionConstants;
import descripcion.TipoObjetoUsuario;
import descripcion.forms.DescriptorForm;
import descripcion.forms.ListaDescriptoraForm;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.FichaVO;
import descripcion.vos.ListaDescrVO;
import descripcion.vos.UsoObjetoVO;
import fondos.model.ElementoCuadroClasificacion;
import gcontrol.model.TipoListaControlAcceso;

/**
 * Acción para la gestión de las listas descriptoras.
 */
public class ListasDescriptorasAction extends BaseAction {

	private static final String FICHA_USA_LISTA_DESCRIPTORA = DescripcionConstants.DESCRIPCION_OBJETO_FORM_DELETE_FICHA;

	/**
	 * Muestra la lista de listas descriptoras.
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
	protected void showExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de showExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS, request);

		// Listas descriptoras
		request.setAttribute(DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
				getGestionDescripcionBI(request).getListasDescriptorasExt());

		// Comprobar que no haya errores
		ActionErrors errores = (ActionErrors) getFromTemporalSession(request,
				DescripcionConstants.ERRORES_KEY);
		if ((errores != null) && !errores.isEmpty()) {
			obtenerErrores(request, true).add(errores);
			removeInTemporalSession(request, DescripcionConstants.ERRORES_KEY);
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_listas_descriptoras"));
	}

	/**
	 * Muestra el formulario para crear una lista descriptora.
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
	protected void formExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de formExecuteLogic");

		// Identificador de la lista
		String id = request.getParameter(Constants.ID);
		logger.info("Id lista descriptora: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_EDIT,
					request);

			// Interfaz de acceso a los métodos de descripción
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			// Obtener la información de la lista
			((ListaDescriptoraForm) form).set(descripcionBI
					.getListaDescriptoraExt(id));

			// Averiguar si la lista tiene descriptores
			request.setAttribute(DescripcionConstants.TIENE_DESCRIPTORES_KEY,
					new Boolean(descripcionBI.tieneDescriptores(id)));
		} else {
			((ListaDescriptoraForm) form).reset();

			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_FORM,
					request);

			// Lista sin descriptores
			request.setAttribute(DescripcionConstants.TIENE_DESCRIPTORES_KEY,
					new Boolean(false));
		}

		// Fichas de descripción
		request.setAttribute(
				DescripcionConstants.FICHAS_DESCRIPCION_KEY,
				getGestionDescripcionBI(request).getFichasByTiposNivel(
						new int[] {
								ElementoCuadroClasificacion.TIPO_DESCRIPTOR,
								ElementoCuadroClasificacion.TIPO_ALL }));

		// Fichas de clasificadores documentales
		request.setAttribute(DescripcionConstants.FICHAS_CLF_DOC_KEY,
				getGestionDocumentosElectronicosBI(request).getFichas());

		cargarListaRepositoriosECM(request);

		setReturnActionFordward(request,
				mapping.findForward("editar_lista_descriptora"));
	}

	/**
	 * Muestra la lista descriptora.
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
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de retrieveExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_VIEW,
				request);

		// Identificador de la lista descriptora
		String id = request.getParameter(Constants.ID);
		logger.info("Id lista descriptora: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Interfaz de acceso a los métodos de descripción
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			// Obtener la información de la lista
			((ListaDescriptoraForm) form).set(descripcionBI
					.getListaDescriptoraExt(id));

			try {
				// Obtener los descriptores de la lista descriptora
				setInTemporalSession(request,
						DescripcionConstants.DESCRIPTORES_LISTA_KEY,
						descripcionBI.getDescriptoresExt(id, new PageInfo(
								request, "nombre")));
			} catch (TooManyResultsException e) {
				// Este error nunca se va a producir
			}
		}

		// Comprobar que no haya errores
		ActionErrors errores = (ActionErrors) getFromTemporalSession(request,
				DescripcionConstants.ERRORES_KEY);
		if ((errores != null) && !errores.isEmpty()) {
			obtenerErrores(request, true).add(errores);
			removeInTemporalSession(request, DescripcionConstants.ERRORES_KEY);
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_lista_descriptora"));
	}

	/**
	 * Guarda la lista descriptora.
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
	protected void saveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de saveExecuteLogic");

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Interfaz de acceso a los métodos de descripción
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			// Recoger la información de la lista descriptora
			ListaDescrVO listaDescriptora = new ListaDescrVO();
			((ListaDescriptoraForm) form).populate(listaDescriptora);

			if (StringUtils.isBlank(listaDescriptora.getId())) {
				// Crear la lista descriptora
				listaDescriptora = descripcionBI
						.insertListaDescriptora(listaDescriptora);
				((ListaDescriptoraForm) form).set(listaDescriptora);

				// Eliminar la invocación anterior
				popLastInvocation(request);
			} else {
				// Modificar la lista descriptora
				descripcionBI.updateListaDescriptora(listaDescriptora);
			}

			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "method", "retrieve&id="
							+ listaDescriptora.getId()));
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			// Fichas de descripción
			request.setAttribute(
					DescripcionConstants.FICHAS_DESCRIPCION_KEY,
					getGestionDescripcionBI(request)
							.getFichasByTiposNivel(
									new int[] {
											ElementoCuadroClasificacion.TIPO_DESCRIPTOR,
											ElementoCuadroClasificacion.TIPO_ALL }));

			// Fichas de clasificadores documentales
			request.setAttribute(DescripcionConstants.FICHAS_CLF_DOC_KEY,
					getGestionDocumentosElectronicosBI(request).getFichas());

			cargarListaRepositoriosECM(request);

			setReturnActionFordward(request,
					mapping.findForward("editar_lista_descriptora"));
		}
	}

	/**
	 * Elimina las listas descriptoras.
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
	protected void removeExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de removeExecuteLogic");

		// Identificadores de las listas descriptoras
		ListaDescriptoraForm frm = (ListaDescriptoraForm) form;

		String[] listaIds = frm.getIds();
		if (StringUtils.isNotEmpty(listaIds)
				&& !isListaDescriptoraEnUso(request, listaIds)) {
			// Eliminar las listas descriptoras
			ResultadoRegistrosVO res = getGestionDescripcionBI(request)
					.deleteListasDescriptoras(listaIds);

			// Comprobar que no haya errores
			if (!res.getErrores().isEmpty()) {
				ActionErrors errores = obtenerErrores(request, true);
				errores.add(res.getErrores());
			}
		}

		setReturnActionFordward(request,
				redirectForwardMethod(request, "method", "show"));
	}

	/**
	 * Elimina del valor de los ids del array, la cadena "#nombre" que le sigue
	 *
	 * @param ids
	 * @param delimiter
	 * @return array con los valores de los ids sin la cadena "#nombre"
	 */
	private String[] getFirstTokenId(String[] ids, String delimiter) {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				ids[i] = StringUtils.getToken(ids[i], delimiter, 1);
			}
		}
		return ids;
	}

	/**
	 * Comprueba si está en uso una lista descriptora
	 *
	 * @param request
	 * @param idListaDescriptora
	 * @return cierto si la lista descriptora está en uso, false en caso
	 *         contrario
	 */
	private boolean isListaDescriptoraEnUso(HttpServletRequest request,
			String idListaDescriptora) {

		return isListaDescriptoraEnUso(request,
				new String[] { idListaDescriptora });
	}

	/**
	 * Comprueba si está en uso un conjunto de listas descriptoras
	 *
	 * @param request
	 * @param idsListasDescriptoras
	 * @return cierto si la lista descriptora está en uso, false en caso
	 *         contrario
	 */
	private boolean isListaDescriptoraEnUso(HttpServletRequest request,
			String[] idsListasDescriptoras) {
		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		idsListasDescriptoras = getFirstTokenId(idsListasDescriptoras, "#");

		List list = descripcionService
				.getElementosEnUsoXIdsObj(idsListasDescriptoras);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UsoObjetoVO usoObjetoVO = (UsoObjetoVO) list.get(i);
				String idListaDescriptora = usoObjetoVO.getIdObj();
				ListaDescrVO listaDescrVO = descripcionService
						.getListaDescriptora(idListaDescriptora);

				if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.FICHA) {
					FichaVO fichaVO = descripcionService.getFicha(usoObjetoVO
							.getIdObjUsuario());
					ActionErrors errors = new ActionErrors();
					errors.add(
							FICHA_USA_LISTA_DESCRIPTORA,
							new ActionError(FICHA_USA_LISTA_DESCRIPTORA,
									listaDescrVO.getNombre(), fichaVO
											.getNombre()));
					ErrorsTag.saveErrors(request, errors);

					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Elimina la lista descriptora seleccionada.
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
	protected void removeListaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de removeListaExecuteLogic");

		// Identificadores de la lista descriptora
		String id = request.getParameter(Constants.ID);
		String name = request.getParameter("name");
		if (StringUtils.isNotBlank(id)) {
			StringBuffer idLista = new StringBuffer(id);
			if (StringUtils.isNotBlank(name))
				idLista.append("#").append(name);

			if (!isListaDescriptoraEnUso(request, id)) {
				// Eliminar las listas descriptoras
				ResultadoRegistrosVO res = getGestionDescripcionBI(request)
						.deleteListasDescriptoras(
								new String[] { idLista.toString() });

				// Comprobar que no haya errores
				if (!res.getErrores().isEmpty()) {
					ActionErrors errores = obtenerErrores(request, true);
					errores.add(res.getErrores());
					goLastClientExecuteLogic(mapping, form, request, response);
					return;
				}
			} else {
				setReturnActionFordward(request,
						mapping.findForward("ver_lista_descriptora"));
				return;
			}
		}

		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Elimina los descriptores.
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
	protected void removeDescriptorsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de removeDescriptorsExecuteLogic");

		// Identificador de la lista descriptora
		String id = request.getParameter(Constants.ID);
		logger.info("Id lista descriptora: " + id);

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Identificadores de los descriptores
		ListaDescriptoraForm frm = (ListaDescriptoraForm) form;
		String[] listaIds = frm.getIds();

		if (StringUtils.isNotEmpty(listaIds)
				&& !isListaDescriptoraEnUso(request, listaIds)) {

			// Eliminar los descriptores
			ResultadoRegistrosVO res = descripcionBI
					.deleteDescriptores(listaIds);

			// Comprobar que no haya errores
			if (!res.getErrores().isEmpty()) {
				ActionErrors errores = obtenerErrores(request, true);
				errores.add(res.getErrores());
			}
		}

		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Elimina el descriptor.
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
		logger.info("Inicio de removeDescriptorExecuteLogic");

		// Identificador del descriptor
		String id = request.getParameter(Constants.ID);
		if (StringUtils.isNotEmpty(id)) {
			// Eliminar el descriptor
			ResultadoRegistrosVO res = getGestionDescripcionBI(request)
					.deleteDescriptores(new String[] { id });

			// Comprobar que no haya errores
			if (!res.getErrores().isEmpty()) {
				ActionErrors errores = obtenerErrores(request, true);
				errores.add(res.getErrores());
				goLastClientExecuteLogic(mapping, form, request, response);
				return;
			}
		}

		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Muestra el formulario de descriptores.
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
	protected void formDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formDescriptorExecuteLogic");

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Identificador del descriptor
		String id = request.getParameter(Constants.ID);
		logger.info("Id descriptor: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_DESCRIPTOR_EDIT,
					request);

			// Obtener la información de la tabla
			((DescriptorForm) form).set(descripcionBI.getDescriptorExt(id));
		} else {
			// Guardar el enlace a la página
			saveCurrentInvocation(
					KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_DESCRIPTOR_FORM,
					request);

			// Obtener el nombre de la lista descriptora
			String idLista = request.getParameter("idLista");
			ListaDescrVO listaDescriptora = descripcionBI
					.getListaDescriptora(idLista);
			if (listaDescriptora != null)
				((DescriptorForm) form).setNombreLista(listaDescriptora
						.getNombre());
		}

		// Fichas de descripción
		request.setAttribute(
				DescripcionConstants.FICHAS_DESCRIPCION_KEY,
				descripcionBI.getFichasByTiposNivel(new int[] {
						ElementoCuadroClasificacion.TIPO_DESCRIPTOR,
						ElementoCuadroClasificacion.TIPO_ALL }));

		// Listas de control de acceso
		request.setAttribute(
				DescripcionConstants.LISTAS_CONTROL_ACCESO_KEY,
				getGestionControlUsuarios(request)
						.getListasControlAccesoByTipo(
								TipoListaControlAcceso.DESCRIPTOR));

		cargarListaRepositoriosECM(request);


		setReturnActionFordward(request,
				mapping.findForward("editar_descriptor"));
	}

	/**
	 * Muestra la información del descriptor.
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
	protected void retrieveDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de retrieveDescriptorExecuteLogic");

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_DESCRIPTOR_VIEW,
				request);

		// Identificador del descriptor
		String id = request.getParameter(Constants.ID);
		logger.info("Id descriptor: " + id);

		if (StringUtils.isNotBlank(id)) {
			// Interfaz de acceso a los métodos de descripción
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			// Obtener la información de la tabla
			((DescriptorForm) form).set(descripcionBI.getDescriptorExt(id));
		}

		setReturnActionFordward(request, mapping.findForward("ver_descriptor"));
	}

	/**
	 * Guarda el descriptor.
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
	protected void saveDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de saveDescriptorExecuteLogic");

		// Interfaz de acceso a los métodos de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Validar el formulario
		ActionErrors errores = form.validate(mapping, request);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");

			// Recoger la información del descriptor
			DescriptorVO descriptor = new DescriptorVO();
			((DescriptorForm) form).populate(descriptor);

			if (StringUtils.isBlank(descriptor.getId())) {
				// Crear el descriptor
				descriptor = descripcionBI.insertDescriptor(descriptor);
				((DescriptorForm) form).set(descriptor);

				// Eliminar la invocación anterior
				popLastInvocation(request);
			} else {
				// Modificar el descriptor
				descripcionBI.updateDescriptor(descriptor);
			}

			setReturnActionFordward(
					request,
					redirectForwardMethod(request, "/descriptor", "method",
							"retrieveDescriptor&id=" + descriptor.getId()));
		} else {
			logger.info("Formulario inv\u00E1lido");

			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			// Añadir el nombre de la lista en creación
			if (StringUtils.isBlank(((DescriptorForm) form).getId())) {
				// Obtener el nombre de la lista descriptora
				String idLista = request.getParameter("idLista");
				ListaDescrVO listaDescriptora = descripcionBI
						.getListaDescriptora(idLista);
				if (listaDescriptora != null)
					((DescriptorForm) form).setNombreLista(listaDescriptora
							.getNombre());
			}

			// Fichas de descripción
			request.setAttribute(
					DescripcionConstants.FICHAS_DESCRIPCION_KEY,
					descripcionBI.getFichasByTiposNivel(new int[] {
							ElementoCuadroClasificacion.TIPO_DESCRIPTOR,
							ElementoCuadroClasificacion.TIPO_ALL }));

			cargarListaRepositoriosECM(request);

			// Listas de control de acceso
			request.setAttribute(
					DescripcionConstants.LISTAS_CONTROL_ACCESO_KEY,
					getGestionControlUsuarios(request)
							.getListasControlAccesoByTipo(
									TipoListaControlAcceso.DESCRIPTOR));

			setReturnActionFordward(request,
					mapping.findForward("editar_descriptor"));
		}
	}

	/**
	 * Muestra el buscador de descriptores.
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
	protected void verBuscadorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_DESCRIPTOR_BUSQ,
				request);

		// Listas descriptoras
		request.setAttribute(DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
				getGestionDescripcionBI(request).getListasDescriptoras());

		setReturnActionFordward(request,
				mapping.findForward("resultado_busqueda_descriptores"));
	}

	/**
	 * Realiza la búsqueda de descriptores.
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
	protected void buscarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_DESCRIPTOR_BUSQ,
				request);

		// Listas descriptoras
		request.setAttribute(DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
				getGestionDescripcionBI(request).getListasDescriptoras());

		// Información de la paginación
		PageInfo pageInfo = new PageInfo(request, "nombre");
		pageInfo.setDefautMaxNumItems();

		// Criterios de búsqueda
		DescriptorForm frm = (DescriptorForm) form;
		BusquedaDescriptoresVO criterios = new BusquedaDescriptoresVO();
		frm.populate(criterios);
		criterios.setPageInfo(pageInfo);

		try {
			// Descriptores encontrados
			request.setAttribute(DescripcionConstants.DESCRIPTORES_LISTA_KEY,
					getGestionDescripcionBI(request).getDescriptores(criterios));
		} catch (TooManyResultsException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
							new Object[] { new Integer(e.getCount()),
									new Integer(e.getMaxNumResults()) }));
		}

		setReturnActionFordward(request,
				mapping.findForward("resultado_busqueda_descriptores"));
	}

	/**
	 * Realiza el filtrado de descriptores en una lista de descriptores
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
	protected void filtrarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.DESCRIPCION_LISTAS_DESCRIPTORAS_VIEW,
				request);
		filtrarCodeLogic(mapping, form, request, response);
	}

	private void filtrarCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ListaDescriptoraForm frm = ((ListaDescriptoraForm) form);
		// Guardar el enlace a la página

		String idLista = frm.getId();

		if (StringUtils.isNotBlank(idLista)) {
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);
			((ListaDescriptoraForm) form).set(descripcionBI
					.getListaDescriptoraExt(idLista));

			BusquedaDescriptoresVO criterios = new BusquedaDescriptoresVO();
			criterios.setIdLista(idLista);
			criterios.setNombre(frm.getFiltro());
			criterios.setCualificadorNombre(frm.getCalificadorFiltro());
			criterios.setPageInfo(new PageInfo(request, "nombre"));

			criterios.setEstado(frm.getFiltroEstado());

			try {

				List listaDescriptores = descripcionBI
						.getDescriptores(criterios);
				setInTemporalSession(request,
						DescripcionConstants.DESCRIPTORES_LISTA_KEY,
						listaDescriptores);
			} catch (TooManyResultsException e) {
				logger.debug("Numero máximo de resultados", e);
			}
		}

		// Comprobar que no haya errores
		ActionErrors errores = (ActionErrors) getFromTemporalSession(request,
				DescripcionConstants.ERRORES_KEY);
		if ((errores != null) && !errores.isEmpty()) {
			obtenerErrores(request, true).add(errores);
			removeInTemporalSession(request, DescripcionConstants.ERRORES_KEY);
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_lista_descriptora"));

	}

	/**
	 * Realiza el filtrado de descriptores en una lista de descriptores
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
	protected void validarDescriptoresExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ListaDescriptoraForm frm = ((ListaDescriptoraForm) form);

		if (StringUtils.isNotEmpty(frm.getIds())) {
			getGestionDescripcionBI(request).validarDescriptores(frm.getIds());
		}

		filtrarCodeLogic(mapping, form, request, response);

	}
}
