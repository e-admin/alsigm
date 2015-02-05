package descripcion.actions;

import gcontrol.ControlAccesoConstants;

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
import descripcion.TipoObjetoUsado;
import descripcion.TipoObjetoUsuario;
import descripcion.forms.AreasForm;
import descripcion.vos.AreaVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.FichaVO;
import descripcion.vos.UsoObjetoVO;

public class GestionAreaAction extends BaseAction {

	private static final String LABEL_AREA = "archigest.archivo.menu.admin.gestionCampo.area";

	/**
	 * Introduce en la request la lista Areas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void listFromMenuExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AreasForm areasForm = (AreasForm) form;
		areasForm.setAreasABorrar(null);

		setReturnActionFordward(request, mapping.findForward("listFromMenu"));
	}

	/**
	 * Introduce en la request la lista Areas
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

		List areas = descripcionService.getAreas();
		request.setAttribute(ControlAccesoConstants.LISTA_AREAS, areas);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.AREAS_LISTADO, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request, mapping.findForward("list"));
	}

	/**
	 * Inicializa la creación de un area
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void newExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.AREAS_EDICION, request);

		AreasForm areasForm = (AreasForm) form;
		areasForm.reset();

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY,
				descripcionService.makeListTipoNorma());
		setReturnActionFordward(request, mapping.findForward("new"));
	}

	/**
	 * Crea un nuevo area
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void createExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		AreasForm areasForm = (AreasForm) form;

		String guid = areasForm.getGuid();

		AreaVO areaVO = areasForm.populate(new AreaVO());

		ActionErrors errors = comprobarDuplicados(descripcionService, request,
				null, areaVO, guid);

		if (errors != null && !errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return;
		} else {
			areaVO = descripcionService.createArea(areaVO);
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
			HttpServletRequest request, ActionErrors errors, AreaVO areaVO,
			String guid) {

		if (errors == null) {
			errors = new ActionErrors();
		}
		// Comprobar GUID único
		comprobarExistenciaRegistroByKey(request, errors,
				ArchivoModules.DESCRIPCION_MODULE, ArchivoTables.ADAREA_TABLE,
				guid, LABEL_AREA, Constants.ETIQUETA_GUID);

		comprobarExistenciaRegistroByValue(request, errors,
				ArchivoModules.DESCRIPCION_MODULE, ArchivoTables.ADAREA_TABLE,
				areaVO.getId(), areaVO.getNombre(), LABEL_AREA,
				Constants.ETIQUETA_NOMBRE);

		return errors;
	}

	/**
	 * Recupera un area desde la lista de areas
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveFromListExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idArea = request.getParameter("idArea");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		AreaVO areaVO = descripcionService.getArea(idArea);

		request.setAttribute("areaVO", areaVO);

		setReturnActionFordward(request,
				mapping.findForward("retrieveFromList"));
	}

	/**
	 * Recupera un area
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.AREAS_EDICION, request);

		AreaVO areaVO = (AreaVO) request.getAttribute("areaVO");
		AreasForm areasForm = (AreasForm) form;
		areasForm.set(areaVO);

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY,
				descripcionService.makeListTipoNorma());

		List camposDato = descripcionService.getCamposByArea(
				areaVO.getId(), areaVO.getNombre());
		setInTemporalSession(request, ControlAccesoConstants.LISTA_CAMPOS_DATO,
				camposDato);

		setReturnActionFordward(request, mapping.findForward("retrieve"));
	}

	/**
	 * Comprueba is hay algún área en uso
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param String
	 *            [] idAreas
	 */
	private boolean isAreaEnUso(HttpServletRequest request, String[] idsAreas) {
		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		List list = descripcionService.getElementosEnUsoXIdObjYTipo(idsAreas,
				TipoObjetoUsado.AREA);

		if (list != null && list.size() > 0) {
			UsoObjetoVO usoObjetoVO = (UsoObjetoVO) list.get(0);
			String idArea = usoObjetoVO.getIdObj();
			AreaVO areaVO = descripcionService.getArea(idArea);

			if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.CAMPO_DATO) {
				CampoDatoVO campoDatoVO = descripcionService
						.getCampoDato(usoObjetoVO.getIdObjUsuario());
				ActionErrors errors = new ActionErrors();
				errors.add(DescripcionConstants.CAMPO_DATO_USA_AREA,
						new ActionError(
								DescripcionConstants.CAMPO_DATO_USA_AREA,
								areaVO.getNombre(), campoDatoVO.getNombre()));
				ErrorsTag.saveErrors(request, errors);

				return true;
			}

			if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.CAMPO_TABLA) {
				CampoTablaVO campoTablaVO = descripcionService
						.getCampoTabla(usoObjetoVO.getIdObjUsuario());
				ActionErrors errors = new ActionErrors();
				errors.add(DescripcionConstants.CAMPO_TABLA_USA_AREA,
						new ActionError(
								DescripcionConstants.CAMPO_TABLA_USA_AREA,
								areaVO.getNombre(), campoTablaVO.getNombre()));
				ErrorsTag.saveErrors(request, errors);

				return true;
			}
			if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.FICHA) {
				FichaVO fichaVO = descripcionService.getFicha(usoObjetoVO
						.getIdObjUsuario());
				ActionErrors errors = new ActionErrors();
				errors.add(DescripcionConstants.FICHA_USA_AREA,
						new ActionError(DescripcionConstants.FICHA_USA_AREA,
								areaVO.getNombre(), fichaVO.getNombre()));
				ErrorsTag.saveErrors(request, errors);

				return true;

			}
		}
		return false;
	}

	/**
	 * Elimina un area
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void deleteExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionErrors errors = null;

		AreasForm areasForm = (AreasForm) form;
		if ((errors = validateFormParaEliminarArea(areasForm)) == null) {
			GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

			String idAreasABorrar[] = areasForm.getAreasABorrar();

			if (!isAreaEnUso(request, idAreasABorrar))
				descripcionService.deleteAreas(idAreasABorrar);
		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Actualiza un area
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void updateExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		AreasForm areasForm = (AreasForm) form;
		AreaVO areaVO = areasForm.populate(new AreaVO());

		ActionErrors errors = comprobarDuplicados(descripcionService, request,
				null, areaVO, null);

		if (errors != null && !errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("create-failed"));
			return;
		} else {
			areaVO = descripcionService.updateArea(areaVO);
		}

		goReturnPointExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Comprueba si hay algún area seleccionado a borrar
	 *
	 * @param frm
	 * @return
	 */
	private ActionErrors validateFormParaEliminarArea(AreasForm form) {

		ActionErrors errors = new ActionErrors();

		String idAreasABorrar[] = form.getAreasABorrar();
		if (idAreasABorrar == null || idAreasABorrar.length == 0) {
			errors.add(
					ErrorKeys.ERROR_SELECCIONE_AL_MENOS_UN_AREA,
					new ActionError(ErrorKeys.ERROR_SELECCIONE_AL_MENOS_UN_AREA));
		}
		return errors.size() > 0 ? errors : null;
	}

}
