package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.forms.NivelesArchivoForm;
import gcontrol.model.ArchivosException;
import gcontrol.vos.NivelArchivoVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.ErrorsTag;
import valoracion.vos.ValoracionSerieVO;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionNivelesArchivoBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.util.StringUtils;

public class GestionNivelesArchivoAction extends BaseAction {

	/**
	 * Método Inicial de la pantalla
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void initialListaNivelesArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.ARCHIVOS_NIVELES, request);
		invocation.setAsReturnPoint(true);

		goListaNivelesArchivo(request, mappings);

	}

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Cargar la lista de la base de datos.
		List listaNivelesArchivo = getListaNivelesArchivo(request, true);
		if (ListUtils.isEmpty(listaNivelesArchivo))
			listaNivelesArchivo = new ArrayList();

		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO_ELIMINADOS,
				new ArrayList());
		setListaNivelesArchivo(request, listaNivelesArchivo);

		setCambiosSinGuardar(request, new Boolean(false));
		setHayNuevosElementosEnSession(request, new Boolean(false));
		setOrdenSeleccionado(request, null);
		setReturnActionFordward(request, mappings.findForward("init"));
	}

	/**
	 * Muestra la lista de Niveles de Archivo
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void listaNivelesArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		goListaNivelesArchivo(request, mappings);
	}

	public void listaArchivosAsociadosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.LISTA_ARCHIVOS_ASOCIADOS,
				request);

		String idNivel = request.getParameter("idNivel");
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionArchivosBI archivosBI = services.lookupGestionArchivosBI();
		List listaDeArchivosAsociados = archivosBI.getArchivosXNivel(idNivel);

		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_ARCHIVOS_ASOCIADOS,
				listaDeArchivosAsociados);
		setReturnActionFordward(request,
				mappings.findForward("lista_archivos_asociados"));

	}

	/**
	 * Carga la página para la edición del registro seleccionado.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void editarNivelArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.ARCHIVOS_NIVELES_EDICION,
				request);

		String ordenNivel = (String) request.getParameter("ordenNivel");

		List listaDeArchivosAsociados = null;
		if (ordenNivel != null) {
			int orden = new Integer(ordenNivel).intValue();
			List listaNivelesArchivo = getListaNivelesArchivo(request, false);
			NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo
					.get(orden - 1);

			NivelesArchivoForm nivelesArchivoForm = (NivelesArchivoForm) form;
			nivelesArchivoForm.set(nivelArchivoVO);

			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionArchivosBI archivosBI = services.lookupGestionArchivosBI();
			listaDeArchivosAsociados = archivosBI
					.getArchivosXNivel(nivelArchivoVO.getId());

		}
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_ARCHIVOS_ASOCIADOS,
				listaDeArchivosAsociados);

		setReturnActionFordward(request,
				mappings.findForward("edicion_nivel_archivo"));
	}

	/**
	 * Carga la página para dar de alta un nuevo nivel de archivo.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void altaNivelArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.ARCHIVOS_NIVELES_ALTA,
				request);

		setReturnActionFordward(request,
				mappings.findForward("alta_nivel_archivo"));

	}

	/**
	 * Agrega el nuevo nivel en la lista, en la posición especificada. Si no se
	 * ha seleccionado posición, lo inserta al final de la lista
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void addNivelArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		NivelArchivoVO nivelArchivoVO = new NivelArchivoVO();
		NivelesArchivoForm nivelesArchivoForm = (NivelesArchivoForm) form;
		nivelesArchivoForm.populate(nivelArchivoVO);

		ActionErrors errors = validateForm(request, nivelesArchivoForm);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		} else {

			List listaNivelesArchivo = getListaNivelesArchivo(request, false);

			nivelArchivoVO.setNuevoElemento(true);

			int orden = 0;
			if (!ListUtils.isEmpty(listaNivelesArchivo)) {
				orden = listaNivelesArchivo.size();
			}

			if (nivelArchivoVO.getOrden() != null) {
				orden = nivelArchivoVO.getOrden().intValue() - 1;
			}

			listaNivelesArchivo.add(orden, nivelArchivoVO);
			setHayNuevosElementosEnSession(request, new Boolean(
					hayNuevosElementos(listaNivelesArchivo)));
			actualizarNiveles(listaNivelesArchivo);
			setCambiosSinGuardar(request, new Boolean(true));
			setOrdenSeleccionado(request, null);
			popLastInvocation(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Actualiza los valores del elemento seleccionado
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void actualizarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NivelesArchivoForm nivelesArchivoForm = (NivelesArchivoForm) form;
		NivelArchivoVO nivelArchivoVO = new NivelArchivoVO();

		ActionErrors errors = validateForm(request, nivelesArchivoForm);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			nivelesArchivoForm.populate(nivelArchivoVO);
			int orden = 0;

			if (nivelArchivoVO.getOrden() != null) {
				orden = nivelArchivoVO.getOrden().intValue();
			}

			if (orden != 0) {
				List listaNivelesArchivo = getListaNivelesArchivo(request,
						false);
				boolean isNuevoElemento = ((NivelArchivoVO) listaNivelesArchivo
						.get(orden - 1)).isNuevoElemento();
				nivelArchivoVO.setNuevoElemento(isNuevoElemento);
				listaNivelesArchivo.remove(orden - 1);
				listaNivelesArchivo.add(orden - 1, nivelArchivoVO);
				actualizarNiveles(listaNivelesArchivo);
				setCambiosSinGuardar(request, new Boolean(true));

			}

			goReturnPointExecuteLogic(mappings, form, request, response);
		}

	}

	/**
	 * Elimina el elemento seleccionado. (Solo para nuevos elementos)
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NivelesArchivoForm nivelesArchivoForm = (NivelesArchivoForm) form;

		ActionErrors errors = new ActionErrors();
		errors = validateNivelArchivoSelected(request, nivelesArchivoForm,
				errors);
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			goListaNivelesArchivo(request, mappings);
			return;
		}

		NivelArchivoVO nivelArchivoVO = new NivelArchivoVO();
		nivelesArchivoForm.populate(nivelArchivoVO);
		List listaNivelesArchivo = getListaNivelesArchivo(request, false);

		int orden = nivelArchivoVO.getOrden().intValue();
		nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo.get(orden - 1);

		errors = validateFormAlEliminar(request, nivelArchivoVO, errors);
		String idNivelSeleccionado = nivelArchivoVO.getId();
		if (!validarNivelPlazosValidaciones(mappings, request,
				idNivelSeleccionado, errors))
			return;

		if (!errors.isEmpty()) {
			setOrdenSeleccionado(request, String.valueOf(orden));
			ErrorsTag.saveErrors(request, errors);
			goListaNivelesArchivo(request, mappings);
		} else {
			if (!nivelArchivoVO.isNuevoElemento()) {
				addNivelEliminado(request, nivelArchivoVO);
				setCambiosSinGuardar(request, Boolean.TRUE);
			}

			listaNivelesArchivo.remove(orden - 1);
			actualizarNiveles(listaNivelesArchivo);
			setHayNuevosElementosEnSession(request, new Boolean(
					hayNuevosElementos(listaNivelesArchivo)));
			setOrdenSeleccionado(request, null);

		}
		if (listaNivelesArchivo.size() == 0) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionNivelesArchivoBI nivelesArchivoBI = services
					.lookupGestionNivelesArchivoBI();
			List listaNivelesEliminados = (List) getFromTemporalSession(
					request,
					ControlAccesoConstants.LISTA_NIVELES_ARCHIVO_ELIMINADOS);
			try {
				nivelesArchivoBI.actualizarListaNiveles(listaNivelesArchivo,
						listaNivelesEliminados);
			} catch (ArchivosException e) {
				guardarError(request, e);
				goLastClientExecuteLogic(mappings, form, request, response);
				return;
			}

		}
		goListaNivelesArchivo(request, mappings);
	}

	/**
	 * Sube el elemento seleccionado una posición.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void subirExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NivelesArchivoForm nivelesArchivoForm = (NivelesArchivoForm) form;

		ActionErrors errors = new ActionErrors();
		errors = validateNivelArchivoSelected(request, nivelesArchivoForm,
				errors);
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			goListaNivelesArchivo(request, mappings);
			return;
		}
		List listaNivelesArchivo = getListaNivelesArchivo(request, false);
		NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo
				.get(nivelesArchivoForm.getOrden().intValue() - 1);
		errors = validateFormAlSubir(request, nivelArchivoVO, errors);

		// validar tambien con los plazos de transferencias de las valoraciones
		// tener en cuenta que ademas del elemento que se esta subiendo, tambien
		// hay que tener el imnediato superior (si existe)
		// debe seguirse manteniendo el orden en los plazos de las validaciones
		String idNivelSeleccionado = nivelArchivoVO.getId();
		if (nivelArchivoVO.getOrden().intValue() > 1) { // esta comprobacion en
														// realidad sobra
			String idNivelAfectado = ((NivelArchivoVO) listaNivelesArchivo
					.get(nivelesArchivoForm.getOrden().intValue() - 2)).getId();
			if (!validarNivelesPlazosValidaciones(mappings, request,
					idNivelSeleccionado, idNivelAfectado, errors, true))
				return;
		}

		if (!errors.isEmpty()) {
			setOrdenSeleccionado(request, nivelArchivoVO.getOrden().toString());
			ErrorsTag.saveErrors(request, errors);

			goListaNivelesArchivo(request, mappings);

			return;
		}

		int orden = nivelArchivoVO.getOrden().intValue();

		if (orden > 1) {
			nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo
					.get(orden - 1);
			listaNivelesArchivo.remove(orden - 1);
			orden--;
			listaNivelesArchivo.add(orden - 1, nivelArchivoVO);
			actualizarNiveles(listaNivelesArchivo);
			setCambiosSinGuardar(request, new Boolean(true));
		}
		setOrdenSeleccionado(request, nivelArchivoVO.getOrden().toString());
		goListaNivelesArchivo(request, mappings);
	}

	private boolean validarNivelesPlazosValidaciones(ActionMapping mappings,
			HttpServletRequest request, String idNivelSeleccionado,
			String idNivelAfectado, ActionErrors errors, boolean subir) {
		// validar tambien con los plazos de transferencias de las valoraciones
		// tener en cuenta que ademas del elemento que se esta subiendo, tambien
		// hay que tener el imnediato superior (si existe)
		// debe seguirse manteniendo el orden en los plazos de las validaciones
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		List valoracionesConfictivas = null;
		if (subir)
			valoracionesConfictivas = valoracionBI.getValoracionesPorPlazos(
					idNivelAfectado, idNivelSeleccionado); // subir
		else
			valoracionesConfictivas = valoracionBI.getValoracionesPorPlazos(
					idNivelSeleccionado, idNivelAfectado); // bajar

		if (valoracionesConfictivas.size() > 0) {
			generateErrorValidacionesConflictivas(mappings, request,
					valoracionesConfictivas, errors, true);
			return false;
		}
		return true;
	}

	private boolean validarNivelPlazosValidaciones(ActionMapping mappings,
			HttpServletRequest request, String idNivel, ActionErrors errors) {
		// validar tambien con los plazos de transferencias de las valoraciones
		// tener en cuenta que ademas del elemento que se esta subiendo, tambien
		// hay que tener el imnediato superior (si existe)
		// debe seguirse manteniendo el orden en los plazos de las validaciones
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		List valoracionesConfictivas = valoracionBI
				.getValoracionesPorIdNivelOrigenDestino(idNivel);

		if (valoracionesConfictivas.size() > 0) {
			generateErrorValidacionesConflictivas(mappings, request,
					valoracionesConfictivas, errors, false);
			return false;
		}
		return true;
	}

	private void generateErrorValidacionesConflictivas(ActionMapping mappings,
			HttpServletRequest request, List valoracionesConfictivas,
			ActionErrors errors, boolean subirBajar) {
		if (valoracionesConfictivas.size() > 0) {
			// componer la lista con los titulos de las valoraciones que impiden
			// el cambio.
			List titulosValidacionesConflictivas = new ArrayList();
			for (Iterator it = valoracionesConfictivas.iterator(); it.hasNext();) {
				ValoracionSerieVO valoracion = (ValoracionSerieVO) (it.next());
				titulosValidacionesConflictivas.add(valoracion.getTitulo());
			}
			Map mapElementosError = new HashMap();
			String mensajeError = subirBajar ? Constants.ERROR_MOVER_NIVELES_VALIDACIONES_CONFLICTIVAS
					: Constants.ERROR_ELIMINAR_NIVELES_VALIDACIONES_CONFLICTIVAS;
			mapElementosError
					.put(mensajeError, titulosValidacionesConflictivas);
			request.setAttribute(Constants.LISTA_ELEMENTOS_ERROR_KEY,
					mapElementosError);

			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					mensajeError));
			goListaNivelesArchivo(request, mappings);
			ErrorsTag.saveErrors(request, errors);
		}
	}

	/**
	 * Baja el elemento seleccionado una posición.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void bajarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NivelesArchivoForm nivelesArchivoForm = (NivelesArchivoForm) form;
		ActionErrors errors = new ActionErrors();
		errors = validateNivelArchivoSelected(request, nivelesArchivoForm,
				errors);
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			goListaNivelesArchivo(request, mappings);
			return;
		}

		List listaNivelesArchivo = getListaNivelesArchivo(request, false);
		NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo
				.get(nivelesArchivoForm.getOrden().intValue() - 1);

		errors = validateFormAlBajar(request, nivelArchivoVO, errors,
				listaNivelesArchivo.size());
		if (!errors.isEmpty()) {
			setOrdenSeleccionado(request, nivelArchivoVO.getOrden().toString());
			ErrorsTag.saveErrors(request, errors);
			goListaNivelesArchivo(request, mappings);
			return;
		}

		// validar tambien con los plazos de transferencias de las valoraciones
		// tener en cuenta que ademas del elemento que se esta subiendo, tambien
		// hay que tener el imnediato superior (si existe)
		// debe seguirse manteniendo el orden en los plazos de las validaciones
		String idNivelSeleccionado = nivelArchivoVO.getId();
		if (nivelArchivoVO.getOrden().intValue() < listaNivelesArchivo.size()) { // esta
																					// comprobacion
																					// en
																					// realidad
																					// sobra
			String idNivelAfectado = ((NivelArchivoVO) listaNivelesArchivo
					.get(nivelesArchivoForm.getOrden().intValue())).getId();
			if (!validarNivelesPlazosValidaciones(mappings, request,
					idNivelSeleccionado, idNivelAfectado, errors, false))
				return;
		}

		int orden = nivelArchivoVO.getOrden().intValue();

		if (orden >= 1 && orden <= listaNivelesArchivo.size() - 1) {
			nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo
					.get(orden - 1);
			listaNivelesArchivo.remove(orden - 1);
			orden++;
			listaNivelesArchivo.add(orden - 1, nivelArchivoVO);
			actualizarNiveles(listaNivelesArchivo);
			setCambiosSinGuardar(request, new Boolean(true));
		}
		setOrdenSeleccionado(request, nivelArchivoVO.getOrden().toString());
		goListaNivelesArchivo(request, mappings);
	}

	/**
	 * Cierra la ventana y va a la bandeja de entrada.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void cerrarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		goBackExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Graba los cambios realizados en la aplicación.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void grabarNivelesArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List listaNivelesArchivo = getListaNivelesArchivo(request, false);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionNivelesArchivoBI nivelesArchivoBI = services
				.lookupGestionNivelesArchivoBI();
		List listaNivelesEliminados = getListaNivelesEliminados(request);

		try {
			actualizarNiveles(listaNivelesArchivo);
			nivelesArchivoBI.actualizarListaNiveles(listaNivelesArchivo,
					listaNivelesEliminados);
		} catch (ArchivosException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
		setCambiosSinGuardar(request, new Boolean(false));

		List listaNivelesArchivoBD = getListaNivelesArchivo(request, true);
		listaNivelesArchivo.addAll(listaNivelesArchivoBD);
		popLastInvocation(request);
		setReturnActionFordward(request, mappings.findForward("grabar"));
	}

	/**
	 * Obtiene la lista de Niveles de Archivo de sesión o de Base de Datos
	 * 
	 * @param request
	 * @param cargarDesdeBD
	 *            Si se le pasa <b>true</b>, carga los valores de la base de
	 *            datos, Si se le pasa <b>false</b>, obtiene la lista de Sesión.
	 * @return List La lista obtenida de la base de datos o de la sesión.
	 */
	private List getListaNivelesArchivo(HttpServletRequest request,
			boolean cargarDesdeBD) {
		List listaNivelesArchivo;

		if (cargarDesdeBD) {
			removeInTemporalSession(request,
					ControlAccesoConstants.LISTA_NIVELES_ARCHIVO);
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionNivelesArchivoBI nivelesArchivoBI = services
					.lookupGestionNivelesArchivoBI();
			listaNivelesArchivo = nivelesArchivoBI.getListaNivelesArchivo();
		} else {
			listaNivelesArchivo = (List) getFromTemporalSession(request,
					ControlAccesoConstants.LISTA_NIVELES_ARCHIVO);
		}

		return listaNivelesArchivo;
	}

	/**
	 * Añade el nivel especificado a la lista de niveles de eliminados de la
	 * sesión
	 * 
	 * @param request
	 *            Nivel A Eliminar.
	 * @param nivelArchivoVO
	 *            Nivel A Eliminar
	 */
	private void addNivelEliminado(HttpServletRequest request,
			NivelArchivoVO nivelArchivoVO) {
		List listaNivelesArchivoEliminados = (List) getFromTemporalSession(
				request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO_ELIMINADOS);

		listaNivelesArchivoEliminados.add(nivelArchivoVO);

	}

	/**
	 * Obtiene la Lista de Niveles Eliminados.
	 * 
	 * @param request
	 *            Request
	 * @return Lista de Elementos Eliminados
	 */
	private List getListaNivelesEliminados(HttpServletRequest request) {
		return (List) getFromTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO_ELIMINADOS);

	}

	/**
	 * Guarda en Sesión la lista de Niveles de Archivo.
	 * 
	 * @param request
	 * @param listaNivelesArchivo
	 *            Lista de Niveles a establecer
	 */
	private void setListaNivelesArchivo(HttpServletRequest request,
			List listaNivelesArchivo) {
		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO,
				listaNivelesArchivo);
	}

	/**
	 * Establece el valor de la variable CAMBIOS_SIN_GUARDAR
	 * 
	 * @param request
	 * @param valor
	 */
	private void setCambiosSinGuardar(HttpServletRequest request, Boolean valor) {
		request.getSession().setAttribute(
				ControlAccesoConstants.CAMBIOS_SIN_GUARDAR, valor);

	}

	/**
	 * Actualiza la lista de niveles con la el orden que ocupan en la lista de
	 * memoria.
	 * 
	 * @param listaNiveles
	 *            Lista de Niveles para modificar
	 */
	private void actualizarNiveles(List listaNiveles) {
		for (int i = 0; i < listaNiveles.size(); i++) {
			((NivelArchivoVO) listaNiveles.get(i)).setOrden(new Integer(i + 1));
		}
	}

	/**
	 * Valida el formulario al añadir o modificar un nivel
	 * 
	 * @param request
	 * @param nivelesArchivoForm
	 * @return ActionErrors con los errores de la validación
	 */
	public ActionErrors validateForm(HttpServletRequest request,
			NivelesArchivoForm nivelesArchivoForm) {
		ActionErrors errors = new ActionErrors();

		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		service.lookupGestionNivelesArchivoBI();

		if (StringUtils.isEmpty(nivelesArchivoForm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		List listaNivelesArchivo = (List) getFromTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO);
		if (!ListUtils.isEmpty(listaNivelesArchivo)) {
			for (int i = 0; i < listaNivelesArchivo.size(); i++) {
				NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) listaNivelesArchivo
						.get(i);

				if (nivelArchivoVO.getNombre().equalsIgnoreCase(
						nivelesArchivoForm.getNombre())
						&& !nivelArchivoVO.getOrden().equals(
								nivelesArchivoForm.getOrden()))

				{
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_NOMBRE_NIVEL_ARCHIVO_DUPLICADO,
									Messages.getString(
											Constants.ETIQUETA_NOMBRE,
											request.getLocale())));
					break;
				}
			}
		}

		return errors;
	}

	/**
	 * Comprueba si hay algún elemento nuevo en la lista pasada por parámetro
	 * 
	 * @param listaNiveles
	 *            lista de {@link NivelArchivoVO}
	 * @return true si la lista tiene algún elemento nuevo, false en caso
	 *         contrario
	 */
	public boolean hayNuevosElementos(List listaNiveles) {
		if (!ListUtils.isEmpty(listaNiveles)) {
			for (int i = 0; i < listaNiveles.size(); i++) {
				NivelArchivoVO nivelArchivoVO = (NivelArchivoVO) listaNiveles
						.get(i);
				if (nivelArchivoVO.isNuevoElemento())
					return true;
			}
		}
		return false;
	}

	/**
	 * @param request
	 * @param mappings
	 */
	private void goListaNivelesArchivo(HttpServletRequest request,
			ActionMapping mappings) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		List listaNivelesArchivo = getListaNivelesArchivo(request, false);

		GestionArchivosBI archivoBI = services.lookupGestionArchivosBI();
		List listaArchivos = archivoBI.getListaArchivos();

		GestionNivelesArchivoBI nivelesArchivoBI = services
				.lookupGestionNivelesArchivoBI();
		listaNivelesArchivo = nivelesArchivoBI.setNivelesArchivoAsociado(
				listaNivelesArchivo, listaArchivos);

		setReturnActionFordward(request,
				mappings.findForward("ver_lista_niveles_archivo"));
	}

	/**
	 * Establece en sesión un valor que indica si hay nuevos niveles
	 * 
	 * @param request
	 * @param value
	 */
	private void setHayNuevosElementosEnSession(HttpServletRequest request,
			Boolean value) {
		request.getSession().setAttribute(
				ControlAccesoConstants.HAY_NUEVOS_ELEMENTOS, value);

	}

	/**
	 * Establece en sesión un valor que indica el orden seleccionado
	 * 
	 * @param request
	 * @param orden
	 */
	private void setOrdenSeleccionado(HttpServletRequest request, String orden) {
		request.getSession().setAttribute(
				ControlAccesoConstants.ORDEN_SELECCIONADO, orden);
	}

	/**
	 * Valida el formulario para saber si se ha seleccionado algún nivel de
	 * archivo
	 * 
	 * @param request
	 * @param nivelesArchivoForm
	 * @param errors
	 * @return
	 */
	private ActionErrors validateNivelArchivoSelected(
			HttpServletRequest request, NivelesArchivoForm nivelesArchivoForm,
			ActionErrors errors) {
		if (nivelesArchivoForm.getOrden() == null) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_NIVELES_ARCHIVO_NINGUN_ELEMENTO_SELECCIONADO));
		}
		return errors;
	}

	/**
	 * Valida el formulario para saber si el nivel seleccionado no tiene archivo
	 * asociado, y caso contrario añade un error a la variable de errores
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @return
	 */
	private ActionErrors validateNivelArchivoSinArchivo(
			HttpServletRequest request, NivelArchivoVO nivelArchivoVO,
			ActionErrors errors) {
		if (nivelArchivoVO.isArchivoAsociado()) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_NIVELES_ARCHIVO_SIN_ARCHIVO_NO_SELECCIONADO));
		}
		return errors;
	}

	/**
	 * Valida el formulario para saber si el nivel seleccionado no es el
	 * primero, y caso contrario añade un error a la variable de errores
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @return
	 */
	private ActionErrors validateNivelArchivoNoFirstSelected(
			HttpServletRequest request, NivelArchivoVO nivelArchivoVO,
			ActionErrors errors) {
		if (nivelArchivoVO.getOrden().equals(new Integer(1))) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_NIVELES_ARCHIVO_PRIMER_ELEMENTO_SELECCIONADO));
		}
		return errors;
	}

	/**
	 * Valida el formulario para saber si el nivel seleccionado no es el último,
	 * y caso contrario añade un error a la variable de errores
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @param lastPostion
	 * @return
	 */
	private ActionErrors validateNivelArchivoNoLastSelected(
			HttpServletRequest request, NivelArchivoVO nivelArchivoVO,
			ActionErrors errors, int lastPostion) {
		if (nivelArchivoVO.getOrden().equals(new Integer(lastPostion))) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_NIVELES_ARCHIVO_ULTIMO_ELEMENTO_SELECCIONADO));
		}
		return errors;
	}

	/**
	 * comprueba que el nivel de archivo pasado por parámetro no tenga ningún
	 * archivo asociado. En caso contrario añade un error a la variable de
	 * errores
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @return
	 */
	private ActionErrors validateNivelEnNingunArchivo(
			HttpServletRequest request, NivelArchivoVO nivelArchivoVO,
			ActionErrors errors) {

		// Comprobar que el Nivel No está en ningún archivo
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionArchivosBI archivosBI = services.lookupGestionArchivosBI();

		List listaArchivoXNivel = archivosBI.getArchivosXNivel(nivelArchivoVO
				.getId());

		if (!ListUtils.isEmpty(listaArchivoXNivel)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_NO_SE_PUEDE_ELIMINAR_NIVEL));
			ErrorsTag.saveErrors(request, errors);
		}
		return errors;
	}

	/**
	 * Valida el formulario cuando se elimina un nivel de archivo
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @return
	 */
	private ActionErrors validateFormAlEliminar(HttpServletRequest request,
			NivelArchivoVO nivelArchivoVO, ActionErrors errors) {
		validateNivelEnNingunArchivo(request, nivelArchivoVO, errors);
		return errors;
	}

	/**
	 * Valida el formulario cuando se intenta subir de orden un nivel de archivo
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @return
	 */
	private ActionErrors validateFormAlSubir(HttpServletRequest request,
			NivelArchivoVO nivelArchivoVO, ActionErrors errors) {
		validateNivelArchivoSinArchivo(request, nivelArchivoVO, errors);
		validateNivelArchivoNoFirstSelected(request, nivelArchivoVO, errors);
		return errors;
	}

	/**
	 * Valida el formulario cuando se intenta bajar de orden un nivel de archivo
	 * 
	 * @param request
	 * @param nivelArchivoVO
	 * @param errors
	 * @param lastPosition
	 * @return
	 */
	private ActionErrors validateFormAlBajar(HttpServletRequest request,
			NivelArchivoVO nivelArchivoVO, ActionErrors errors, int lastPosition) {
		validateNivelArchivoSinArchivo(request, nivelArchivoVO, errors);
		validateNivelArchivoNoLastSelected(request, nivelArchivoVO, errors,
				lastPosition);
		return errors;
	}
}
