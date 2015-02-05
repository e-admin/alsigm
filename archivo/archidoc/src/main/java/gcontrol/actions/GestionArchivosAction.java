package gcontrol.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.forms.ArchivoForm;
import gcontrol.model.ArchivosException;
import gcontrol.view.ArchivoPO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.NivelArchivoVO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.model.TipoSignaturacion;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionNivelesArchivoBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.util.StringUtils;

import deposito.model.GestorEstructuraDepositoBI;

public class GestionArchivosAction extends BaseAction {

	/**
	 * Método Inicial de la pantalla
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void initialExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.ARCHIVOS, request);
		invocation.setAsReturnPoint(true);

		ArchivoForm archivoForm = (ArchivoForm) form;
		archivoForm.reset();

		removeInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS);
		removeInTemporalSession(request,
				ControlAccesoConstants.LISTA_TIPOS_SIGNATURACION_POSIBLES);
		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI archivosBI = service.lookupGestionArchivosBI();
		List listaArchivos = archivosBI.getListaArchivos();
		CollectionUtils.transform(listaArchivos,
				new TransformerArchivoVOToArchivoPO(service));

		setInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS,
				listaArchivos);
		setReturnActionFordward(request, mappings.findForward("lista_archivos"));
	}

	public class TransformerArchivoVOToArchivoPO implements Transformer {
		ServiceRepository services = null;

		TransformerArchivoVOToArchivoPO(ServiceRepository services) {
			this.services = services;
		}

		public Object transform(Object archivoVO) {
			return new ArchivoPO((ArchivoVO) archivoVO, services);
		}
	}

	/**
	 * Carga la página para la edición del registro seleccionado.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void editarArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.ARCHIVOS_EDICION, request);
		invocation.setAsReturnPoint(true);

		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI archivosBI = service.lookupGestionArchivosBI();

		String idArchivo = (String) request.getParameter("idArchivo");

		if (idArchivo != null) {
			ArchivoVO archivoVO = archivosBI.getArchivoXId(idArchivo);

			ArchivoForm formulario = (ArchivoForm) form;
			formulario.set(archivoVO);
			formulario.setIdniveloriginal(archivoVO.getIdnivel());

			// GestionFondosBI gestionFondosBI =
			// service.lookupGestionFondosBI();
			GestionCuadroClasificacionBI cuadroBI = service
					.lookupGestionCuadroClasificacionBI();
			boolean existenElementosCuadro = cuadroBI
					.existeArchivoEnCuadro(idArchivo);

			if (existenElementosCuadro)
				formulario
						.setPermitirEditarCodigoArchivo(Constants.FALSE_STRING);
			else
				formulario
						.setPermitirEditarCodigoArchivo(Constants.TRUE_STRING);

			/*
			 * Si se permite la seleccion de signaturacion se muestra la lista
			 * de posibles valores. Cuando estamos editando, solo es posible
			 * editar si no tiene huecos en depositos del archivo a editar
			 */
			if (ConfigConstants.getInstance()
					.getPermitirSeleccionSignaturacion()) {
				GestorEstructuraDepositoBI depositoBI = service
						.lookupGestorEstructuraDepositoBI();
				if (depositoBI.isEditableTipoSignaturacion(idArchivo)) {
					List listaTiposSignaturacion = TipoSignaturacion
							.getTiposSignaturacion();
					setInTemporalSession(
							request,
							ControlAccesoConstants.LISTA_TIPOS_SIGNATURACION_POSIBLES,
							listaTiposSignaturacion);
				} else
					formulario.setNombreSignaturacion(TipoSignaturacion
							.getTipoSignaturacion(
									archivoVO.getTiposignaturacion())
							.getNombre());
			}

			// Obtener la lista de Niveles
			GestionNivelesArchivoBI nivelesBI = service
					.lookupGestionNivelesArchivoBI();

			NivelArchivoVO nivelArchivoVO = nivelesBI
					.getNivelArchivoXId(archivoVO.getIdnivel());

			String orden = null;
			if (nivelArchivoVO != null) {
				orden = nivelArchivoVO.getOrden().toString();
			}

			List listaArchivos = null;
			if (!StringUtils.isBlank(orden)) {
				listaArchivos = archivosBI.getPosiblesArchivosReceptores(orden);

				if (ListUtils.isEmpty(listaArchivos)) {
					formulario.setIdreceptordefecto(null);
				}

				ArchivoVO archivoVacioVO = new ArchivoVO();
				archivoVacioVO.setNombre(Messages.getString(
						Constants.ETIQUETA_SIN_ARCHIVO_RECEPTOR_DEFECTO,
						request.getLocale()));

				listaArchivos.add(0, archivoVacioVO);
			}

			List listaNivelesArchivo = nivelesBI.getListaNivelesArchivo();
			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_NIVELES_ARCHIVO_POSIBLES,
					listaNivelesArchivo);

			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_ARCHIVOS, listaArchivos);
		}
		setReturnActionFordward(request,
				mappings.findForward("edicion_archivo"));
	}

	/**
	 * Carga la página para dar de alta un nuevo nivel de archivo.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void nuevoArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.ARCHIVOS_ALTA, request);
		invocation.setAsReturnPoint(true);

		ArchivoForm archivoForm = (ArchivoForm) form;
		archivoForm.reset();

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionNivelesArchivoBI nivelesArchivoBI = services
				.lookupGestionNivelesArchivoBI();
		List listaNivelesArchivo = nivelesArchivoBI.getListaNivelesArchivo();

		/*
		 * Si se permite la seleccion de signaturacion se muestra la lista de
		 * posibles valores
		 */
		if (ConfigConstants.getInstance().getPermitirSeleccionSignaturacion()) {
			List listaTiposSignaturacion = TipoSignaturacion
					.getTiposSignaturacion();
			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_TIPOS_SIGNATURACION_POSIBLES,
					listaTiposSignaturacion);
		}

		List listaArchivos = (List) getFromTemporalSession(request,
				ControlAccesoConstants.LISTA_ARCHIVOS);
		listaArchivos.removeAll(listaArchivos);

		setInTemporalSession(request,
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO_POSIBLES,
				listaNivelesArchivo);

		setReturnActionFordward(request, mappings.findForward("alta_archivo"));
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
	public void insertarArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI gestionArchivosBI = service.lookupGestionArchivosBI();

		ArchivoForm formulario = (ArchivoForm) form;

		ActionErrors errors = validateForm(formulario, request);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("alta_archivo"));
		} else {
			ArchivoVO archivoVO = new ArchivoVO();
			formulario.populate(archivoVO);

			gestionArchivosBI.insertarArchivo(archivoVO);
			setReturnActionFordward(request, mappings.findForward("initial"));
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
	public void actualizarArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI gestionArchivosBI = service.lookupGestionArchivosBI();

		ArchivoForm formulario = (ArchivoForm) form;

		ActionErrors errors = validateForm(formulario, request);

		if (errors.size() > 0) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_archivo"));
		} else {
			ArchivoVO archivoVO = new ArchivoVO();
			formulario.populate(archivoVO);

			gestionArchivosBI.actualizarArchivo(archivoVO);
			setReturnActionFordward(request, mappings.findForward("initial"));

		}
	}

	/**
	 * Carga la página para la edición del registro seleccionado.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void eliminarArchivoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI gestionArchivosBI = service.lookupGestionArchivosBI();

		ArchivoVO archivoVO = new ArchivoVO();
		ArchivoForm formulario = (ArchivoForm) form;
		formulario.populate(archivoVO);

		try {
			gestionArchivosBI.eliminarArchivo(archivoVO);
			setReturnActionFordward(request, mappings.findForward("initial"));
		} catch (ArchivosException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Carga la página para dar de alta un nuevo nivel de archivo.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void cargarArchivosReceptoresEdicionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		cargarArchivosReceptores(mappings, form, request, response);
		setReturnActionFordward(request,
				mappings.findForward("edicion_archivo"));
	}

	/**
	 * Carga la lista de Archivos Receptores
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void cargarArchivosReceptoresAltaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		cargarArchivosReceptores(mappings, form, request, response);
		setReturnActionFordward(request, mappings.findForward("alta_archivo"));
	}

	/**
	 * Carga la página para dar de alta un nuevo nivel de archivo.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	private void cargarArchivosReceptores(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ArchivoForm archivoForm = (ArchivoForm) form;
		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI archivosBI = service.lookupGestionArchivosBI();
		GestionNivelesArchivoBI nivelesBI = service
				.lookupGestionNivelesArchivoBI();

		// Establecer el receptor por defecto a NULO
		archivoForm.setIdreceptordefecto(null);

		NivelArchivoVO nivelArchivoVO = nivelesBI
				.getNivelArchivoXId(archivoForm.getIdnivel());

		String orden = null;
		if (nivelArchivoVO != null) {
			orden = nivelArchivoVO.getOrden().toString();
		}

		List listaArchivos = null;

		if (!StringUtils.isBlank(orden)) {
			listaArchivos = archivosBI.getPosiblesArchivosReceptores(orden);

			ArchivoVO archivoVO = new ArchivoVO();
			archivoVO.setNombre(Messages.getString(
					Constants.ETIQUETA_SIN_ARCHIVO_RECEPTOR_DEFECTO,
					request.getLocale()));
			listaArchivos.add(0, archivoVO);

		}
		setInTemporalSession(request, ControlAccesoConstants.LISTA_ARCHIVOS,
				listaArchivos);
	}

	/**
	 * Comprueba que los datos introducidos en el formulario sean correctos. -
	 * Código Obligatorio - Nombre Obligatorio - IdNivel Obligatorio
	 * 
	 * @return
	 */
	public ActionErrors validateForm(ArchivoForm archivoForm,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionArchivosBI gestionArchivosBI = service.lookupGestionArchivosBI();

		// Comprobar que se ha introducido el código
		if (StringUtils.isEmpty(archivoForm.getCodigo())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_CODIGO,
									request.getLocale())));
		}

		// Comprobar que se ha introducido el nombre Corto
		if (StringUtils.isEmpty(archivoForm.getNombrecorto())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE_CORTO,
									request.getLocale())));
		}

		// Comprobar que se ha introducido el nombre
		if (StringUtils.isEmpty(archivoForm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		// Comprobar que se ha seleccionado el nivel.
		if (StringUtils.isEmpty(archivoForm.getIdnivel())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NIVEL,
									request.getLocale())));
		}

		String separador = String.valueOf(ConfigConstants.getInstance()
				.getSeparadorCodigoTransferencia());
		if (archivoForm.getCodigo().indexOf(separador) != -1)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CARACTER_EN_CAMPO_NO_PERMITIDO,
							Messages.getString(Constants.ETIQUETA_CODIGO,
									request.getLocale()), separador));

		ArchivoVO archivoVO = gestionArchivosBI
				.getArchivosXCodArchivo(archivoForm.getCodigo());

		if (archivoVO != null
				&& !archivoVO.getId().equalsIgnoreCase(archivoForm.getId()))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CODIGO_ARCHIVO_DUPLICADO,
							Messages.getString(Constants.ETIQUETA_CODIGO,
									request.getLocale())));

		if (ConfigConstants.getInstance().getPermitirSeleccionSignaturacion()) {
			if (!ConfigConstants.getInstance().getSignaturacionPorArchivo()
					&& !StringUtils.isEmpty(archivoForm.getTiposignaturacion())) {
				List archivos = gestionArchivosBI
						.getArchivosXTipoSignaturacion(
								archivoForm.getTiposignaturacion(),
								archivoForm.getId());
				if (archivos != null && archivos.size() > 0)
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_TIPO_SIGNATURACION_ARCHIVO_NO_PERMITIDO));
			}
		}

		return errors;
	}

}
