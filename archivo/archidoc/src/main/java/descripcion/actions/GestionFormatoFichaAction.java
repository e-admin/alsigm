package descripcion.actions;

import gcontrol.ControlAccesoConstants;
import gcontrol.model.TipoListaControlAcceso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import util.ErrorsTag;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.FileHelper;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.XmlFacade;

import descripcion.DescripcionConstants;
import descripcion.ErrorKeys;
import descripcion.forms.FormatoFichasForm;
import descripcion.model.xml.definition.DefTipos;
import descripcion.model.xml.format.DefFmtElementoSeccion;
import descripcion.model.xml.format.DefFmtFicha;
import descripcion.model.xml.format.DefFmtFichaFactory;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.FmtFichaVO;

public class GestionFormatoFichaAction extends BaseAction {
	Logger logger = Logger.getLogger(this.getClass());

	private static final String FMT_FICHA_VO = "fmtFichaVO";

	/**
	 * Introduce en la request la lista de fichas
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void listExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de listExecuteLogic");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List fmtFichas = descripcionService.getFmtFichas();
		loadListas(request);
		request.setAttribute(ControlAccesoConstants.LISTA_FORMATO_FICHAS,
				fmtFichas);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.FORMATO_FICHAS_LISTADO, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request, mapping.findForward("list"));
	}

	/**
	 * Inicializa la creación de una ficha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void newExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.FORMATO_FICHAS_EDICION,
				request);

		FormatoFichasForm formatoFichasForm = (FormatoFichasForm) form;
		formatoFichasForm.reset();

		formatoFichasForm.setDefinicion(formatoFichasForm
				.getDefinicionDefecto());
		loadListas(request);

		setReturnActionFordward(request, mapping.findForward("new"));
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	private boolean validate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = new ActionErrors();

		FormatoFichasForm fichasForm = (FormatoFichasForm) form;
		if (fichasForm.getNombre() == null
				|| fichasForm.getNombre().trim().equals("")) {
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, "Nombre"));

			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return false;
		}

		if (fichasForm.getDefinicion() == null
				|| fichasForm.getDefinicion().trim().equals("")) {
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, "Definicion"));

			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return false;

		}
		try {
			// XML bien formado
			new XmlFacade(fichasForm.getDefinicion(),
					ConfiguracionArchivoManager.getInstance().getPathXSD(
							ConfiguracionArchivoManager.XSD_FORMATOS_FICHAS));

			// Verificar que los campos Existen
			DefFmtFichaFactory fmtFichaFactory = DefFmtFichaFactory
					.getInstance(getServiceClient(request));
			DefFmtFicha defFtmFicha = fmtFichaFactory.createDefFmtFicha(
					fichasForm.getId(), 0, fichasForm.getDefinicion());

			DefFmtElementoSeccion[] defFmtElementosSeccion = defFtmFicha
					.getElementos();

			HashMap camposDato = new HashMap();
			HashMap camposTabla = new HashMap();

			if (!ArrayUtils.isEmpty(defFmtElementosSeccion)) {
				for (int pos = 0; pos < defFmtElementosSeccion.length; pos++) {
					DefFmtElementoSeccion defFmtElementoSeccion = defFtmFicha
							.getElemento(pos);
					camposDato.putAll(defFmtElementoSeccion
							.getIdsElementosCampoDato());
					camposTabla.putAll(defFmtElementoSeccion
							.getIdsElementosTabla());
				}
			}

			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			verificarCodigosCamposDato(descripcionService, camposDato,
					fichasForm.getIdFicha());
			verificarCodigosCamposTabla(descripcionService, camposTabla);

			if (!camposDato.isEmpty()) {
				Iterator it = camposDato.keySet().iterator();

				String idsCamposDato = "";
				while (it.hasNext()) {
					if (!StringUtils.isEmpty(idsCamposDato)) {
						idsCamposDato += ", ";
					}
					String id = (String) it.next();
					idsCamposDato += id;
				}
				errors.add(ErrorKeys.ERROR_NO_EXISTEN_CAMPOS_TIPO_DATO,
						new ActionError(
								ErrorKeys.ERROR_NO_EXISTEN_CAMPOS_TIPO_DATO,
								idsCamposDato));
			}

			if (!camposTabla.isEmpty()) {
				Iterator it = camposTabla.keySet().iterator();

				String idsCamposTabla = "";
				while (it.hasNext()) {
					if (!StringUtils.isEmpty(idsCamposTabla)) {
						idsCamposTabla += ", ";
					}
					String id = (String) it.next();
					idsCamposTabla += id;
				}

				errors.add(ErrorKeys.ERROR_NO_EXISTEN_CAMPOS_TIPO_TABLA,
						new ActionError(
								ErrorKeys.ERROR_NO_EXISTEN_CAMPOS_TIPO_TABLA,
								idsCamposTabla));
			}

			if (errors.size() > 0) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mapping.findForward("create-failed"));
				return false;
			}

		} catch (IllegalArgumentException ex) {
			logger.error(ex.getMessage());

			errors.add(
					ErrorKeys.ERROR_XML_DEFINICION_FORMATO_FICHA_NO_BIEN_FORMADO,
					new ActionError(
							ErrorKeys.ERROR_XML_DEFINICION_FORMATO_FICHA_NO_BIEN_FORMADO,
							ex.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return false;

		}
		return true;
	}

	/**
	 * Comprueba que los campos de tipo dato existen en la base de datos
	 * 
	 * @param descripcionService
	 * @param campos
	 */
	private void verificarCodigosCamposDato(
			GestionDescripcionBI descripcionService, HashMap camposDato,
			String idFicha) {

		List listaCamposDato = descripcionService.getCamposFicha(idFicha);
		logger.debug("Campos Dato:" + camposDato.toString());

		if (!ListUtils.isEmpty(listaCamposDato)) {
			ListIterator it = listaCamposDato.listIterator();
			while (it.hasNext()) {
				CampoDatoVO campoDatoVO = (CampoDatoVO) it.next();
				camposDato.remove(campoDatoVO.getId());
			}
		}

		String[] listaEspeciales = DefTipos.CAMPOS_ESPECIALES;
		for (int i = 0; i < listaEspeciales.length; i++) {
			String idEspecial = listaEspeciales[i];
			camposDato.remove(idEspecial);
		}
	}

	private void verificarCodigosCamposTabla(
			GestionDescripcionBI descripcionService, HashMap camposTabla) {
		List listaCamposTabla = descripcionService.getCamposTabla();
		logger.debug("Campos Tabla:" + camposTabla.toString());

		if (!ListUtils.isEmpty(listaCamposTabla)) {
			ListIterator it = listaCamposTabla.listIterator();
			while (it.hasNext()) {
				CampoTablaVO campoTablaVO = (CampoTablaVO) it.next();
				camposTabla.remove(campoTablaVO.getId());
			}
		}
	}

	/**
	 * Crea una nueva ficha, y un formato
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void createExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		if (validate(mapping, form, request, response) == false) {
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return;
		}

		try {
			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			FormatoFichasForm fichasForm = (FormatoFichasForm) form;
			FmtFichaVO fmtFichaVO = fichasForm.populate(new FmtFichaVO());

			fmtFichaVO = descripcionService.createFmtFicha(fmtFichaVO);
			goReturnPointExecuteLogic(mapping, form, request, response);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
		}
	}

	/**
	 * Actualiza una ficha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void updateExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		if (validate(mapping, form, request, response) == false) {
			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
			return;
		}

		try {

			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			FormatoFichasForm fmtFichasForm = (FormatoFichasForm) form;
			FmtFichaVO fmtFichaVO = fmtFichasForm.populate(new FmtFichaVO());

			// createFmtFicha(request,fmtFichaVO);

			// List
			// listaUsoObjeto=(List)request.getAttribute(Constants.LISTA_USO_OBJETO);
			fmtFichaVO = descripcionService.updateFmtFicha(fmtFichaVO);
			goReturnPointExecuteLogic(mapping, form, request, response);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
		}
	}

	/**
	 * Recupera una ficha desde la lista de fichas
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveFromListExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idFormato = request.getParameter("idFormato");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		FmtFichaVO fmtFichaVO = descripcionService.getFmtFicha(idFormato);

		setInTemporalSession(request, FMT_FICHA_VO, fmtFichaVO);
		setReturnActionFordward(request,
				mapping.findForward("retrieveFromList"));
	}

	/**
	 * Recupera una ficha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Guardar el enlace a la página
		saveCurrentInvocation(KeysClientsInvocations.FORMATO_FICHAS_EDICION,
				request);

		FmtFichaVO fmtFichaVO = (FmtFichaVO) getFromTemporalSession(request,
				FMT_FICHA_VO);
		FormatoFichasForm formatoFichasForm = (FormatoFichasForm) form;
		formatoFichasForm.set(fmtFichaVO);

		loadListas(request);

		setReturnActionFordward(request, mapping.findForward("retrieve"));
	}

	/**
	 * Elimina una ficha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void deleteExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = null;

		FormatoFichasForm formatoFichasForm = (FormatoFichasForm) form;
		if ((errors = validateFormParaEliminarFormatoFicha(formatoFichasForm)) == null) {
			String idFormatosABorrar[] = formatoFichasForm.getFormatosABorrar();
			GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);
			descripcionService.deleteFormatos(idFormatosABorrar);
		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Accede a la página que permite importar una definición de ficha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void importExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.FORMATO_FICHAS_IMPORTAR,
				request);
		setReturnActionFordward(request, mapping.findForward("import"));
	}

	/**
	 * Copia a la definición de una ficha, el contenido de un fichero de texto.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void importAcceptExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		FormatoFichasForm fichasForm = (FormatoFichasForm) form;

		try {
			FormFile file = fichasForm.getFichero();
			String definicion = FileHelper.getTextFileContent(file
					.getInputStream());
			fichasForm.setDefinicion(definicion);
		} catch (IOException ex) {
			logger.info(ex.getMessage());
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_LEER_EN_FICHERO, new ActionError(
					ErrorKeys.ERROR_LEER_EN_FICHERO));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
			return;
		}

		popLastInvocation(request);
		setReturnActionFordward(request, mapping.findForward("importAccept"));
	}

	/**
	 * Sale de la página que permite importar una definición de ficha
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void importCancelExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		popLastInvocation(request);
		setReturnActionFordward(request, mapping.findForward("importCancel"));

	}

	/**
	 * Accede a la página que permite exportar la definición de una ficha a un
	 * fichero
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void exportExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		/*
		 * FmtFichaVO fmtFichaVO = (FmtFichaVO)getFromTemporalSession(request,
		 * FMT_FICHA_VO);
		 * 
		 * if ((fmtFichaVO != null) && (fmtFichaVO.getDefinicion()!=null)) { try
		 * { String definicion = fmtFichaVO.getDefinicion();
		 * 
		 * download(response, fmtFichaVO.getNombre() +
		 * ".xml",definicion.getBytes()); } catch (Exception e) {
		 * obtenerErrores(request, true) .add(ActionErrors.GLOBAL_MESSAGE, new
		 * ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.toString())); } }
		 * else { obtenerErrores(request, true)
		 * .add(ActionErrors.GLOBAL_MESSAGE, new
		 * ActionError(ErrorKeys.ERROR_DESCRIPCION_EXPORTAR_DEF_NO_ENCONTRADA));
		 * }
		 */
		try {
			FormatoFichasForm formulario = (FormatoFichasForm) form;

			String definicion = formulario.getDefinicion();

			download(response, formulario.getNombre(), definicion.getBytes());

		} catch (Exception e) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.GLOBAL_ARCHIGEST_EXCEPTION, e
							.toString()));
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}

	/**
	 * Carga en sesión las listas de normas y de niveles
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @param FormatoFichasForm
	 *            fichasForm
	 */
	private void loadListas(HttpServletRequest request) {
		ServiceRepository services = getServiceRepository(request);

		// Listas de control de acceso
		setInTemporalSession(
				request,
				DescripcionConstants.LISTAS_CONTROL_ACCESO_KEY,
				getGestionControlUsuarios(request)
						.getListasControlAccesoByTipo(
								TipoListaControlAcceso.FORMATO_FICHA));

		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		setInTemporalSession(request, ControlAccesoConstants.LISTA_FICHAS,
				descripcionService.getFichas());

		// setInTemporalSession(request,DescripcionConstants.LISTA_TIPO_NORMAS_KEY,
		// descripcionService.makeListTipoNorma());
		// loadNiveles(formatoFichasForm,request);
	}

	/**
	 * Comprueba si hay alguna ficha seleccionada a borrar
	 * 
	 * @param FormatoFichasForm
	 *            frm
	 * @return
	 */
	private ActionErrors validateFormParaEliminarFormatoFicha(
			FormatoFichasForm frm) {

		ActionErrors errors = new ActionErrors();

		String[] idFormatosABorrar = frm.getFormatosABorrar();
		if (idFormatosABorrar == null || idFormatosABorrar.length == 0) {
			errors.add(
					ErrorKeys.ERROR_SELECCIONE_AL_MENOS_UN_FORMATO_FICHA,
					new ActionError(
							ErrorKeys.ERROR_SELECCIONE_AL_MENOS_UN_FORMATO_FICHA));
		}
		return errors.size() > 0 ? errors : null;
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		FormatoFichasForm formatoFichasForm = (FormatoFichasForm) form;
		formatoFichasForm.reset();
		loadListas(request);

		saveCurrentInvocation(KeysClientsInvocations.FORMATO_FICHAS_BUSQUEDA,
				request);
		setReturnActionFordward(request, mappings.findForward("findFormatos"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		FormatoFichasForm formatoFichasForm = (FormatoFichasForm) form;
		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		List fmtFichas = descripcionService.findFmtFichas(
				formatoFichasForm.getNombre(), formatoFichasForm.getIdFicha());

		request.setAttribute(ControlAccesoConstants.LISTA_FORMATO_FICHAS,
				fmtFichas);

		setReturnActionFordward(request, mappings.findForward("findFormatos"));
	}

	public void verFormatosByFichaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idFicha = request.getParameter("idFicha");

		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);
		List fmtFichas = descripcionService.findFmtFichas(null, idFicha);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.FORMATO_FICHAS_LISTADO, request);
		invocation.setAsReturnPoint(true);

		loadListas(request);
		request.setAttribute(ControlAccesoConstants.LISTA_FORMATO_FICHAS,
				fmtFichas);

		setReturnActionFordward(request, mappings.findForward("findFormatos"));
	}

}
