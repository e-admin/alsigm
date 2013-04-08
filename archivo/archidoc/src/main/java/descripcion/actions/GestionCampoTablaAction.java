package descripcion.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionDescripcionBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoTables;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.ErrorKeys;
import descripcion.TipoObjetoUsuario;
import descripcion.forms.CamposTablaForm;
import descripcion.vos.AreaVO;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.FichaVO;
import descripcion.vos.UsoObjetoVO;
import es.archigest.framework.core.vo.PropertyBean;
import gcontrol.ControlAccesoConstants;

public class GestionCampoTablaAction extends BaseAction {

	private static final String ERROR_SELECCIONE_AL_MENOS_UN_CAMPO = ErrorKeys.ERROR_DESCRIPCION_ELIMINACION_CAMPO_NO_SELECCIONADO;



	/**
	 * Inicializa la creación de un campo tabla
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void newExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.CAMPOS_TABLA_EDICION,
				request);

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		camposTablaForm.reset();

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List listaTipoNorma = descripcionService.makeListTipoNorma();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY, listaTipoNorma);

		String tipoNorma = ((PropertyBean) listaTipoNorma.get(0)).getValue();
		List listaAreas = descripcionService.getAreasByTipoNorma(Integer
				.parseInt(tipoNorma));

		setInTemporalSession(request, DescripcionConstants.LISTA_AREAS_KEY,
				listaAreas);

		setReturnActionFordward(request, mapping.findForward("new"));
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

		List camposTabla = descripcionService.getCamposTabla();
		request.setAttribute(ControlAccesoConstants.LISTA_CAMPOS, camposTabla);

		List tipoCampoEntidad = descripcionService.makeListTipoCampoEntidad();
		request.setAttribute(
				DescripcionConstants.LISTA_TIPOS_CAMPO_ENTIDAD_KEY,
				tipoCampoEntidad);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CAMPOS_TABLA_LISTADO, request);
		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request, mapping.findForward("list"));
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

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		camposTablaForm.setIdArea(null);
		List listaAreas = descripcionService.getAreasByTipoNorma(Integer
				.valueOf(camposTablaForm.getTipoNorma()).intValue());
		setInTemporalSession(request, DescripcionConstants.LISTA_AREAS_KEY,
				listaAreas);

		setReturnActionFordward(request, mapping.findForward("changeTipoNorma"));

	}

	/**
	 * Actualiza la información de un campo tabla
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void updateInformacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {


		ActionErrors errors = validate(mapping, form, request, response);

		if(errors != null && !errors.isEmpty()){
			ErrorsTag.saveErrors(request, errors);

			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
			return;
		}

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;

		boolean isNuevo = false;
		String guid = null;
		if (StringUtils.isEmpty(camposTablaForm.getId())) {
			isNuevo = true;
			guid = camposTablaForm.getGuid();
		}

		CampoTablaVO campoTablaVO = camposTablaForm
				.populate(new CampoTablaVO());

		if (campoTablaVO.getIdArea() != null) {
			AreaVO areaVO = descripcionService
					.getArea(campoTablaVO.getIdArea());
			camposTablaForm.setArea(areaVO.getNombre());
		}

		errors = comprobarDuplicados(descripcionService, request,
				null, campoTablaVO, guid);

		if (errors != null && !errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mapping.findForward("update-failed"));
			return;
		} else {

			camposTablaForm.setNorma(descripcionService
					.getTipoNormaText(campoTablaVO.getTipoNorma()));

			if (isNuevo) {
				campoTablaVO = descripcionService
						.createCampoTabla(campoTablaVO);
			} else {
				descripcionService.updateCampoTabla(campoTablaVO);
			}
		}

		setReturnActionFordward(
				request,
				redirectForwardMethod(request, "method",
						"retrieveFromList&idCampo=" + campoTablaVO.getId()));

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
			CampoTablaVO campoTablaVO, String guid) {

		if (errors == null) {
			errors = new ActionErrors();
		}
		// Comprobar GUID único
		comprobarExistenciaRegistroByKey(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPOTBL_TABLE, guid, Constants.LABEL_CAMPO_TABLA,
				Constants.ETIQUETA_GUID);


		// Comprobar nombre único
		comprobarExistenciaRegistroByValue(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPOTBL_TABLE, campoTablaVO.getId(),
				campoTablaVO.getNombre(), Constants.LABEL_CAMPO_TABLA,
				Constants.ETIQUETA_NOMBRE);

		// Comprobar GUID único en campos dato
		comprobarExistenciaRegistroByKey(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPODATO_TABLE, guid,
				Constants.LABEL_CAMPO_DATO, Constants.ETIQUETA_GUID);

		// Comprobar Nombre único en campos dato
		comprobarExistenciaRegistroByValue(request, errors,
				ArchivoModules.DESCRIPCION_MODULE,
				ArchivoTables.ADCAMPODATO_TABLE, campoTablaVO.getId(),
				campoTablaVO.getNombre(), Constants.LABEL_CAMPO_DATO,
				Constants.ETIQUETA_NOMBRE);

		// Comprobar EtiquetaXml única
		comprobarExistenciaEtiquetaXml(request, errors, campoTablaVO.getEtiquetaXml(), campoTablaVO.getId(),Constants.ETIQUETA_ETIQUETAXML ,ArchivoTables.ADCAMPOTBL_TABLE);

		// Comprobar EtiquetaXmlFila única
		comprobarExistenciaEtiquetaXml(request, errors, campoTablaVO.getEtiqXmlFila(), campoTablaVO.getId(),Constants.ETIQUETA_ETIQUETAXMLFILA,ArchivoTables.ADCAMPOTBL_TABLE);

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

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CAMPOS_TABLA_EDICION, request);
		invocation.setAsReturnPoint(true);

		String idCampo = request.getParameter("idCampo");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		CampoTablaVO campoTablaVO = descripcionService.getCampoTabla(idCampo);
		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		camposTablaForm.setCamposABorrar(null);
		request.setAttribute("campoTablaVO", campoTablaVO);

		setReturnActionFordward(request,
				mapping.findForward("retrieveFromList"));
	}

	/**
	 * Edita un campo de tabla
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void editExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.CAMPOS_TABLA_EDIT_INFORMACION, request);

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List listaTipoNorma = descripcionService.makeListTipoNorma();
		setInTemporalSession(request,
				DescripcionConstants.LISTA_TIPO_NORMAS_KEY, listaTipoNorma);

		List listaAreas = descripcionService.getAreasByTipoNorma(Integer
				.parseInt(camposTablaForm.getTipoNorma()));
		setInTemporalSession(request, DescripcionConstants.LISTA_AREAS_KEY,
				listaAreas);

		setReturnActionFordward(request, mapping.findForward("edit"));
	}

	/**
	 * Sube los campos seleccionados de la lista de campos
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void subirCampoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		String[] camposABorrar = camposTablaForm.getCamposABorrar();

		if (camposABorrar != null && camposABorrar.length > 0) {

			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			List list = new ArrayList();
			int pos = 0;
			int posAnteriorAlPrimero = 0;
			for (int i = 0; i < camposABorrar.length; i++) {
				String id = camposABorrar[i];
				CampoDatoVO campoDatoVO = descripcionService.getCampoDato(id);

				if (i == 0 && campoDatoVO.getPosEnTbl() == 1) // Si el primero
																// esta
																// seleccionado
																// lanzamos un
																// error
				{
					ActionErrors errors = new ActionErrors();
					errors.add(ErrorKeys.ERROR_MOVER_PRIMERO, new ActionError(
							ErrorKeys.ERROR_MOVER_PRIMERO));

					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("subirCampo"));
					return;
				} else if (pos == 0) {
					pos = campoDatoVO.getPosEnTbl();
					posAnteriorAlPrimero = pos - 1;
					campoDatoVO.setPosEnTbl(campoDatoVO.getPosEnTbl() - 1);
					list.add(campoDatoVO);

				} else if (campoDatoVO.getPosEnTbl() == pos + 1) {
					campoDatoVO.setPosEnTbl(campoDatoVO.getPosEnTbl() - 1);
					list.add(campoDatoVO);

					pos++;
				} else // Las posiciones no son consecutivas
				{
					ActionErrors errors = new ActionErrors();
					errors.add(ErrorKeys.ERROR_POSICIONES_NO_CONSECUTIVAS,
							new ActionError(
									ErrorKeys.ERROR_POSICIONES_NO_CONSECUTIVAS));

					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("subirCampo"));
					return;
				}
			}

			CampoDatoVO campoDatoVO = descripcionService
					.getCampoDatoByPosEnTbl(camposTablaForm.getId(),
							posAnteriorAlPrimero);
			campoDatoVO.setPosEnTbl(pos);
			descripcionService.updateCampoDato(campoDatoVO);

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					campoDatoVO = (CampoDatoVO) list.get(i);
					descripcionService.updateCampoDato(campoDatoVO);

				}
			}
			List listaCamposDato = descripcionService
					.getCamposDatoOrderByPosEnTbl(camposTablaForm.getId());

			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_CAMPOS_DATO, listaCamposDato);
		}
		setReturnActionFordward(request, mapping.findForward("subirCampo"));
	}

	/**
	 * Baja los campos seleccionados de la lista de campos
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void bajarCampoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		String[] camposABorrar = camposTablaForm.getCamposABorrar();

		if (camposABorrar != null && camposABorrar.length > 0) {

			ServiceRepository services = getServiceRepository(request);
			GestionDescripcionBI descripcionService = services
					.lookupGestionDescripcionBI();

			List list = new ArrayList();
			List listaCheckBox = descripcionService
					.getCamposDatoOrderByPosEnTbl(camposTablaForm.getId());
			int posUltimoElemento = 0;

			if (listaCheckBox != null && listaCheckBox.size() > 0) {
				CampoDatoVO campoDatoVO = (CampoDatoVO) listaCheckBox
						.get(listaCheckBox.size() - 1);
				posUltimoElemento = campoDatoVO.getPosEnTbl();
			}

			int pos = camposABorrar.length;
			int posPosteriorAlUltimo = 0;
			for (int i = camposABorrar.length - 1; i >= 0; i--) {
				String id = camposABorrar[i];
				CampoDatoVO campoDatoVO = descripcionService.getCampoDato(id);

				if (i == camposABorrar.length - 1
						&& campoDatoVO.getPosEnTbl() == posUltimoElemento) // Si
																			// el
																			// ultimo
																			// está
																			// seleccionado
																			// lanzamos
																			// un
																			// error
				{
					ActionErrors errors = new ActionErrors();
					errors.add(ErrorKeys.ERROR_MOVER_ULTIMO, new ActionError(
							ErrorKeys.ERROR_MOVER_ULTIMO));

					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("bajarCampo"));
					return;
				} else if (pos == camposABorrar.length
						&& posPosteriorAlUltimo == 0) {
					pos = campoDatoVO.getPosEnTbl();
					posPosteriorAlUltimo = pos + 1;
					campoDatoVO.setPosEnTbl(campoDatoVO.getPosEnTbl() + 1);
					list.add(campoDatoVO);

				} else if (campoDatoVO.getPosEnTbl() == pos - 1) {
					campoDatoVO.setPosEnTbl(campoDatoVO.getPosEnTbl() + 1);
					list.add(campoDatoVO);

					pos--;
				} else // Las posiciones no son consecutivas
				{
					ActionErrors errors = new ActionErrors();
					errors.add(ErrorKeys.ERROR_POSICIONES_NO_CONSECUTIVAS,
							new ActionError(
									ErrorKeys.ERROR_POSICIONES_NO_CONSECUTIVAS));

					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mapping.findForward("bajarCampo"));
					return;
				}
			}

			CampoDatoVO campoDatoVO = descripcionService
					.getCampoDatoByPosEnTbl(camposTablaForm.getId(),
							posPosteriorAlUltimo);
			campoDatoVO.setPosEnTbl(pos);
			descripcionService.updateCampoDato(campoDatoVO);

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					campoDatoVO = (CampoDatoVO) list.get(i);
					descripcionService.updateCampoDato(campoDatoVO);

				}
			}

			List listaCamposDato = descripcionService
					.getCamposDatoOrderByPosEnTbl(camposTablaForm.getId());

			setInTemporalSession(request,
					ControlAccesoConstants.LISTA_CAMPOS_DATO, listaCamposDato);
		}

		setReturnActionFordward(request, mapping.findForward("bajarCampo"));
	}

	/**
	 * Recupera un campo desde la lista de campos
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CampoTablaVO campoTablaVO = (CampoTablaVO) request
				.getAttribute("campoTablaVO");

		ServiceRepository services = getServiceRepository(request);
		GestionDescripcionBI descripcionService = services
				.lookupGestionDescripcionBI();

		List listaCamposDato = descripcionService
				.getCamposDatoOrderByPosEnTbl(campoTablaVO.getId());

		setInTemporalSession(request, ControlAccesoConstants.LISTA_CAMPOS_DATO,
				listaCamposDato);

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		if (campoTablaVO.getIdArea() != null) {
			AreaVO areaVO = descripcionService
					.getArea(campoTablaVO.getIdArea());
			camposTablaForm.setArea(areaVO.getNombre());
		}

		camposTablaForm.set(campoTablaVO);
		camposTablaForm.setNorma(descripcionService.getTipoNormaText(Integer
				.parseInt(camposTablaForm.getTipoNorma())));

		setReturnActionFordward(request, mapping.findForward("retrieve"));
	}

	/**
	 * Comprueba is hay algún campo dato en uso
	 *
	 * @param HttpServletRequest
	 *            request
	 * @param String
	 *            [] idCamposDato
	 */
	private boolean isCampoTablaEnUso(HttpServletRequest request,
			String[] idCamposTabla) {
		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		List list = descripcionService.getElementosEnUsoXIdsObj(idCamposTabla);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				UsoObjetoVO usoObjetoVO = (UsoObjetoVO) list.get(i);
				String idCampoTabla = usoObjetoVO.getIdObj();
				CampoTablaVO campoTablaVO = descripcionService
						.getCampoTabla(idCampoTabla);

				if (usoObjetoVO.getTipoObjUsuario() == TipoObjetoUsuario.FICHA) {
					FichaVO fichaVO = descripcionService.getFicha(usoObjetoVO
							.getIdObjUsuario());
					ActionErrors errors = new ActionErrors();
					errors.add(
							DescripcionConstants.DESCRIPCION_CAMPOSTABLA_FORM_DELETE_FICHA,
							new ActionError(
									DescripcionConstants.DESCRIPCION_CAMPOSTABLA_FORM_DELETE_FICHA,
									campoTablaVO.getNombre(), fichaVO
											.getNombre()));
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

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;

		if ((errors = validateFormParaEliminarCampo(camposTablaForm)) == null) {
			GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);
			String idCamposABorrar[] = camposTablaForm.getCamposABorrar();
			if (!isCampoTablaEnUso(request, idCamposABorrar))
				descripcionService.deleteCamposTabla(idCamposABorrar);
		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Introduce en la request información de un campo dato
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	protected void newCampoDatoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		request.setAttribute("tipoNorma", camposTablaForm.getTipoNorma());
		request.setAttribute("idArea", camposTablaForm.getIdArea());
		request.setAttribute("idTblPadre", camposTablaForm.getId());
		request.setAttribute("fromCampoLista", "true");
		setReturnActionFordward(request, mapping.findForward("newCampoDato"));

	}

	/**
	 * Valida el formulario
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	private ActionErrors validate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionErrors errors = getErrors(request, true);

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;
		if (StringUtils.isBlank(camposTablaForm.getNombre())) {
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED,
					Messages.getString(Constants.ETIQUETA_NOMBRE, request.getLocale())

			));
		}

		if (StringUtils.isBlank(camposTablaForm.getEtiquetaXml())){
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ERROR_REQUIRED, Messages.getString(Constants.ETIQUETA_ETIQUETAXML, request.getLocale())));

		}
		else if(camposTablaForm.getEtiquetaXml().equalsIgnoreCase(camposTablaForm.getEtiqXmlFila())){
			errors.add(Constants.ERROR_ELEMENTOS_DISTINTOS, new ActionError(
					Constants.ERROR_ELEMENTOS_DISTINTOS,
					Messages.getString(Constants.ETIQUETA_ETIQUETAXML, request.getLocale()),
					Messages.getString(Constants.ETIQUETA_ETIQUETAXMLFILA, request.getLocale())

			));

		}

		return errors;
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
					errors.add(
							DescripcionConstants.DESCRIPCION_CAMPOSTABLA_FORM_DELETE_FICHA,
							new ActionError(
									DescripcionConstants.DESCRIPCION_CAMPOSTABLA_FORM_DELETE_FICHA,
									campoDatoVO.getNombre(), fichaVO
											.getNombre()));
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
	protected void deleteCamposDatoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionErrors errors = null;

		CamposTablaForm camposTablaForm = (CamposTablaForm) form;

		if ((errors = validateFormParaEliminarCampo(camposTablaForm)) == null) {
			String idCamposABorrar[] = camposTablaForm.getCamposABorrar();
			GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);
			if (!isCampoDatoEnUso(request, idCamposABorrar)) {
				descripcionService.deleteCamposDato(idCamposABorrar);

				// se actualiza el campo posicion de los que quedan
				List list = descripcionService
						.getCamposDatoOrderByPosEnTbl(camposTablaForm.getId());
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						CampoDatoVO campoDatoVO = (CampoDatoVO) list.get(i);
						campoDatoVO.setPosEnTbl(i + 1);
						descripcionService.updateCampoDato(campoDatoVO);
					}
				}
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Comprueba si hay algún campo seleccionado a borrar
	 *
	 * @param frm
	 * @return
	 */
	private ActionErrors validateFormParaEliminarCampo(
			CamposTablaForm camposTablaForm) {

		ActionErrors errors = new ActionErrors();

		String idCamposABorrar[] = camposTablaForm.getCamposABorrar();
		if (idCamposABorrar == null || idCamposABorrar.length == 0) {
			errors.add(ERROR_SELECCIONE_AL_MENOS_UN_CAMPO, new ActionError(
					ERROR_SELECCIONE_AL_MENOS_UN_CAMPO));
		}
		return errors.size() > 0 ? errors : null;
	}
}
