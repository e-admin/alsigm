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
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoTables;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;

import descripcion.DescripcionConstants;
import descripcion.ErrorKeys;
import descripcion.TipoObjetoUsuario;
import descripcion.forms.CamposDatoForm;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.FichaVO;
import descripcion.vos.UsoObjetoVO;
import es.archigest.framework.core.vo.PropertyBean;
import gcontrol.ControlAccesoConstants;

public class GestionCampoDatoAction extends BaseAction {

	private static final String ERROR_SELECCIONE_AL_MENOS_UN_CAMPO = ErrorKeys.ERROR_DESCRIPCION_ELIMINACION_CAMPO_NO_SELECCIONADO;
	private static final String FICHA_USA_CAMPODATO = DescripcionConstants.DESCRIPCION_CAMPOSDATO_FORM_DELETE_FICHA;

	/**
	 * Inicializa la creación de un campo dato
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void newExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.CAMPOS_DATO_EDICION,
				request);

		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		camposDatoForm.reset();

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List listaTiposCampo = descripcionService.makeListTipoCampo();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPOS_CAMPO_KEY, listaTiposCampo);

		List listaTipoNorma = descripcionService.makeListTipoNorma();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY, listaTipoNorma);

		String tipoNorma = null;
		List listaAreas = null;

		if ("true".equalsIgnoreCase((String) request
				.getAttribute("fromCampoLista"))) {
			tipoNorma = (String) request.getAttribute("tipoNorma");
			listaAreas = descripcionService.getAreasByTipoNorma(Integer
					.parseInt(tipoNorma));

			camposDatoForm.setIdArea((String) request.getAttribute("idArea"));
			camposDatoForm.setTipoNorma(tipoNorma);
			camposDatoForm.setIdTblPadre((String) request
					.getAttribute("idTblPadre"));
			// request.setAttribute("fromCampoLista","true");
			setInTemporalSession(request, "fromCampoLista", "true");
		} else {
			tipoNorma = ((PropertyBean) listaTipoNorma.get(0)).getValue();
			listaAreas = descripcionService.getAreasByTipoNorma(Integer
					.parseInt(tipoNorma));
		}
		setInTemporalSession(request, DescripcionConstants.LISTA_AREAS_KEY,
				listaAreas);
		setReturnActionFordward(request, mapping.findForward("new"));
	}

	/**
	 * Metodo que se llama desde el menu general para presentar la lista de
	 * campos.
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void listFromMenuExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		camposDatoForm.reset();
		setReturnActionFordward(request, mapping.findForward("listFromMenu"));
	}

	/**
	 * Introduce en la request la lista de campos
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

		List camposDato = descripcionService.getCamposDatoSinTabla();
		request.setAttribute(ControlAccesoConstants.LISTA_CAMPOS, camposDato);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CAMPOS_DATO_LISTADO, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request, mapping.findForward("list"));
	}

	/**
	 * Introduce en sesión la lista de areas para la norma que contiene el
	 * formulario
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void changeTipoNormaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		camposDatoForm.setIdArea(null);
		List listaAreas = descripcionService.getAreasByTipoNorma(Integer
				.valueOf(camposDatoForm.getTipoNorma()).intValue());
		setInTemporalSession(request, DescripcionConstants.LISTA_AREAS_KEY,
				listaAreas);

		setReturnActionFordward(request, mapping.findForward("changeTipoNorma"));

	}

	/**
	 * Cambia el tipo
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void changeTipoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request, mapping.findForward("changeTipo"));
	}

	/**
	 * Crea un nuevo campoDato
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void createExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (validate(mapping, form, request, response) == false) {
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return;
		}

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		CamposDatoForm camposDatoForm = (CamposDatoForm) form;

		String guid = camposDatoForm.getGuid();

		CampoDatoVO campoDatoVO = camposDatoForm.populate(new CampoDatoVO());

		ActionErrors errors = comprobarDuplicados(descripcionService, request,
				null, campoDatoVO, guid);

		if (errors != null && !errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return;
		} else {
			campoDatoVO = descripcionService.createCampoDato(campoDatoVO);
		}

		goReturnPointExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Comprueba que no existan duplicados en la base de datos.
	 *
	 * @param descripcionService
	 * @param request
	 * @param errors
	 * @param campoDatoVO
	 * @param guid
	 * @return
	 */
	private ActionErrors comprobarDuplicados(
			GestionDescripcionBI descripcionService,
			HttpServletRequest request, ActionErrors errors,
			CampoDatoVO campoDatoVO, String guid) {

		if (errors == null) {
			errors = new ActionErrors();
		}
		// Comprobar GUID único
		comprobarExistenciaRegistroByKey(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPODATO_TABLE, guid,
				Constants.LABEL_CAMPO_DATO, Constants.ETIQUETA_GUID);

		// Comprobar Nombre único
		comprobarExistenciaRegistroByValue(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPODATO_TABLE, campoDatoVO.getId(),
				campoDatoVO.getNombre(), Constants.LABEL_CAMPO_DATO,
				Constants.ETIQUETA_NOMBRE);

		// Comprobar GUID único en tablas
		comprobarExistenciaRegistroByKey(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPOTBL_TABLE, guid, Constants.LABEL_CAMPO_TABLA,
				Constants.ETIQUETA_GUID);


		// Comprobar nombre único en tablas
		comprobarExistenciaRegistroByValue(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPOTBL_TABLE, campoDatoVO.getId(),
				campoDatoVO.getNombre(), Constants.LABEL_CAMPO_TABLA,
				Constants.ETIQUETA_NOMBRE);


		comprobarExistenciaEtiquetaXml(request, errors, campoDatoVO.getEtiquetaXml(), campoDatoVO.getId(),Constants.ETIQUETA_ETIQUETAXML  ,ArchivoTables.ADCAMPODATO_TABLE);

		return errors;
	}

	/**
	 * Recupera un campo desde la lista de campos
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveFromListExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idCampo = request.getParameter("idCampo");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		CampoDatoVO campoDatoVO = descripcionService.getCampoDato(idCampo);

		request.setAttribute("campoDatoVO", campoDatoVO);
		setInTemporalSession(request, "fromCampoLista",
				request.getParameter("fromCampoLista"));
		setReturnActionFordward(request,
				mapping.findForward("retrieveFromList"));
	}

	/**
	 * Recupera un campo
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.CAMPOS_DATO_EDICION,
				request);

		CampoDatoVO campoDatoVO = (CampoDatoVO) request
				.getAttribute("campoDatoVO");
		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		camposDatoForm.set(campoDatoVO);

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List listaTiposCampo = descripcionService.makeListTipoCampo();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPOS_CAMPO_KEY, listaTiposCampo);

		List listaTipoNorma = descripcionService.makeListTipoNorma();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY, listaTipoNorma);

		List listaAreas = descripcionService.getAreasByTipoNorma(Integer
				.parseInt(camposDatoForm.getTipoNorma()));
		setInTemporalSession(request, DescripcionConstants.LISTA_AREAS_KEY,
				listaAreas);

		setReturnActionFordward(request, mapping.findForward("retrieve"));
	}

	/**
	 * Actualiza un campo
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void updateExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (validate(mapping, form, request, response) == false) {
			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
			return;
		}

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		CampoDatoVO campoDatoVO = camposDatoForm.populate(new CampoDatoVO());

		ActionErrors errors = comprobarDuplicados(descripcionService, request,
				null, campoDatoVO, null);

		if (errors != null && !errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
			return;
		} else {
			campoDatoVO = descripcionService.updateCampoDato(campoDatoVO);
		}

		goReturnPointExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Comprueba is hay algún campo dato en uso
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param String
	 *            [] idCamposDato
	 */
	private boolean isCampoDatoEnUso(HttpServletRequest request,
			String[] idCamposDato) {
		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		List list = descripcionService.getElementosEnUsoXIdsObj(idCamposDato);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UsoObjetoVO usoObjetoVO = (UsoObjetoVO) list.get(i);
				String idCampoDato = usoObjetoVO.getIdObj();
				CampoDatoVO campoDatoVO = descripcionService
						.getCampoDato(idCampoDato);

				if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.FICHA) {
					FichaVO fichaVO = descripcionService.getFicha(usoObjetoVO
							.getIdObjUsuario());
					ActionErrors errors = new ActionErrors();
					errors.add(FICHA_USA_CAMPODATO, new ActionError(
							FICHA_USA_CAMPODATO, campoDatoVO.getNombre(),
							fichaVO.getNombre()));
					ErrorsTag.saveErrors(request, errors);

					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Elimina un campo
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void deleteExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = null;

		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		if ((errors = validateFormParaEliminarCampo(camposDatoForm)) == null) {
			GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);
			String idCamposABorrar[] = camposDatoForm.getCamposABorrar();

			if (!isCampoDatoEnUso(request, idCamposABorrar))
				descripcionService.deleteCamposDato(idCamposABorrar);
		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Comprueba si hay algún area seleccionado a borrar
	 *
	 * @param frm
	 * @return
	 */
	private ActionErrors validateFormParaEliminarCampo(
			CamposDatoForm camposDatoForm) {

		ActionErrors errors = new ActionErrors();

		String idCamposABorrar[] = camposDatoForm.getCamposABorrar();
		if (idCamposABorrar == null || idCamposABorrar.length == 0) {
			errors.add(ERROR_SELECCIONE_AL_MENOS_UN_CAMPO, new ActionError(
					ERROR_SELECCIONE_AL_MENOS_UN_CAMPO));
		}
		return errors.size() > 0 ? errors : null;
	}

	/**
	 * Valida un formulario
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean validate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CamposDatoForm camposDatoForm = (CamposDatoForm) form;
		if (camposDatoForm.getNombre() == null
				|| camposDatoForm.getNombre().trim().equals("")) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, "Nombre"));

			ErrorsTag.saveErrors(request, errors);
			return false;

		}

		if (camposDatoForm.getEtiquetaXml() == null
				|| camposDatoForm.getEtiquetaXml().trim().equals("")) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, "Etiqueta"));

			ErrorsTag.saveErrors(request, errors);
			return false;

		}

		return true;
	}
}
