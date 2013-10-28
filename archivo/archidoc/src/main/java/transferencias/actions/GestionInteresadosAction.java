package transferencias.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.terceros.GestorTerceros;
import se.terceros.GestorTercerosFactory;
import se.terceros.InfoTercero;
import se.terceros.TipoAtributo;
import se.terceros.exceptions.GestorTercerosException;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.forms.InteresadoForm;
import transferencias.vos.InteresadoVO;
import transferencias.vos.UnidadDocumentalVO;
import util.ErrorsTag;

import common.ConfigConstantsDescripcionSistemasExternos;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.BaseAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.IFValidator;
import common.util.ListUtils;
import common.util.NombresUtils;

/**
 * Action que maneja las acciones que pueden ser invocadas para la
 * especificacion de los interesados asociados a una unidad documental
 *
 */
public class GestionInteresadosAction extends BaseAction {

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(GestionInteresadosAction.class);

	public void editarInfoInteresadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String itemIndexParameter = (String) request.getParameter("itemIndex");
		if (itemIndexParameter != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) request.getSession()
					.getAttribute(TransferenciasConstants.UNIDAD_DOCUMENTAL);
			List interesados = udoc.getInteresados();
			InteresadoVO interesado = (InteresadoVO) interesados.get(Integer
					.parseInt(itemIndexParameter));
			request.setAttribute(TransferenciasConstants.INFO_INTERESADO,
					interesado);
			InteresadoForm interesadoForm = (InteresadoForm) form;
			interesadoForm.setNombre(interesado.getNombre());

			interesadoForm.setNumeroIdentificacion(interesado
					.getNumeroIdentificacion());

			if (esFormatoCif(interesado.getNumeroIdentificacion())) {
				interesadoForm
						.setTipoIdentificacion(Constants.TIPO_DOCUMENTO_CIF);
			} else if (esFormatoNif(interesado.getNumeroIdentificacion())) {
				interesadoForm
						.setTipoIdentificacion(Constants.TIPO_DOCUMENTO_NIF);
			} else {
				interesadoForm
						.setTipoIdentificacion(Constants.TIPO_DOCUMENTO_OTRO);
			}

			interesadoForm.setRol(interesado.getRol());
			interesadoForm.setValidado(interesado.isValidado());
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_INTERESADO, request);
			invocation.setAsReturnPoint(true);
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionEntregaBI = services
					.lookupGestionRelacionesBI();
			List rolesInteresado = relacionEntregaBI.getListaRolesInteresado();
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_ROLES_INTERESADO,
					rolesInteresado);
			setReturnActionFordward(request,
					mappings.findForward("edicion_info_interesado"));
		}
	}

	public void guardarInteresadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String itemIndexParameter = (String) request.getParameter("itemIndex");
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		List interesados = udoc.getInteresados();
		InteresadoVO interesado = (InteresadoVO) interesados.get(Integer
				.parseInt(itemIndexParameter));
		InteresadoForm interesadoForm = (InteresadoForm) form;

		ActionErrors errores = new ActionErrors();

		if (!interesado.isValidado()) {
			// Comprobar el NIF/CIF es correcto
			errores = validarInteresadoForm(request, interesadoForm);

			interesado.setNombre(interesadoForm.getNombre());
			interesado.setNumeroIdentificacion(interesadoForm
					.getNumeroIdentificacion());

		}

		if (errores.size() > 0) {
			ErrorsTag.saveErrors(request, errores);
			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			if (!StringUtils.isEmpty(interesadoForm.getRol()))
				interesado.setRol(interesadoForm.getRol());
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
			popLastInvocation(request);
			setReturnActionFordward(request, mappings.findForward("info_udoc"));

		}
	}

	public void eliminarInteresadosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InteresadoForm interesadoForm = (InteresadoForm) form;
		String[] interesadosSeleccionados = interesadoForm
				.getSeleccionInteresado();
		if (interesadosSeleccionados != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			List interesados = udoc.getInteresados();
			List interesadosToRemove = new ArrayList();
			for (int i = 0; i < interesadosSeleccionados.length; i++)
				interesadosToRemove.add(interesados.get(Integer
						.parseInt(interesadosSeleccionados[i]) - 1));
			for (Iterator i = interesadosToRemove.iterator(); i.hasNext();)
				interesados.remove(i.next());
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	public void nuevoInteresadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EDICION_INTERESADOS,
				request);
		invocation.setAsReturnPoint(true);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionEntregaBI = services
				.lookupGestionRelacionesBI();
		List rolesInteresado = relacionEntregaBI.getListaRolesInteresado();
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_ROLES_INTERESADO, rolesInteresado);
		setReturnActionFordward(request,
				mappings.findForward("edicion_interesado"));
	}

	/**
	 * Permite guardar los valores necesarios para crear un geografico en la
	 * ficha
	 *
	 * @param request
	 *            Petición actual
	 * @param form
	 *            Formulario de la petición
	 */
	protected void leerReferenciasDescripcion(HttpServletRequest request,
			InteresadoForm form) {

		// Guardar los valores de los campos para seleccionar el interesado
		ConfigConstantsDescripcionSistemasExternos ccdse = ConfigConstantsDescripcionSistemasExternos
				.getInstance();
		Map mapIdRefLists = ConfigConstantsDescripcionSistemasExternos
				.getInstance().processRefLine(form.getRef());

		// Campo Identidad
		String identidad = ccdse
				.getValorCampo(
						ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
						ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_IDENTIDAD);
		if (StringUtils.isNotEmpty(identidad)) {
			Map map = (Map) mapIdRefLists.get(identidad);
			if (map != null) {
				String ref = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
				if (StringUtils.isNotEmpty(ref)) {
					form.setRefDescripcionIdentidad(ref);
				}
				String listas = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_LISTAS);
				if (StringUtils.isNotEmpty(listas)) {
					form.setListasDescripcionIdentidad(listas);
				}
				String tipoReferencia = (String) map
						.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REFTYPE);
				if (StringUtils.isNotEmpty(tipoReferencia)) {
					form.setRefTypeDescripcionIdentidad(tipoReferencia);
				}
			}
		}
	}

	public void nuevoInteresadoDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener posibles roles
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionEntregaBI = services
				.lookupGestionRelacionesBI();

		List rolesInteresado = relacionEntregaBI.getListaRolesInteresado();

		setInTemporalSession(request,
				TransferenciasConstants.LISTA_ROLES_INTERESADO, rolesInteresado);

		// Generar el path de contexto para poder llamar desde Javascript
		InteresadoForm interesadoForm = (InteresadoForm) form;
		interesadoForm.setContextPath(request.getContextPath());

		// Eliminar los interesados de una invocación anterior
		removeInTemporalSession(request,
				TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS);

		// Meter las listas y tipos de referencia
		leerReferenciasDescripcion(request, interesadoForm);

		setReturnActionFordward(request,
				mappings.findForward("edicion_interesado_descripcion"));
	}

	public void crearInteresadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InteresadoForm interesadoForm = (InteresadoForm) form;

		ActionErrors errores = validarInteresadoForm(request, interesadoForm);
		if (errores.size() > 0) {

			ErrorsTag.saveErrors(request, errores);

			setReturnActionFordward(request,
					mappings.findForward("edicion_interesado"));
		} else {
			InteresadoVO interesado = new InteresadoVO(
					interesadoForm.getNombre(),
					interesadoForm.getNumeroIdentificacion(),
					interesadoForm.isValidado());
			if (!StringUtils.isEmpty(interesadoForm.getRol()))
				interesado.setRol(interesadoForm.getRol());

			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			udoc.addInteresado(interesado);
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
			interesadoForm.clear();
			popLastInvocation(request);
			setReturnActionFordward(request, mappings.findForward("info_udoc"));
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
	}

	public void crearInteresadoDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Obtener el formulario
		InteresadoForm interesadoForm = (InteresadoForm) form;

		// Generar el path de contexto para poder llamar desde Javascript
		interesadoForm.setContextPath(request.getContextPath());

		ActionErrors errores = validarInteresadoForm(request, interesadoForm);
		if (errores.size() > 0) {

			ErrorsTag.saveErrors(request, errores);

			setReturnActionFordward(request,
					mappings.findForward("edicion_interesado_descripcion"));
		} else {
			InteresadoVO interesado = new InteresadoVO();
			interesado.setNombre(interesadoForm.getNombre());
			interesado.setNumeroIdentificacion(interesadoForm
					.getNumeroIdentificacion());
			interesado.setRol(interesadoForm.getRol());
			interesado.setValidado(false);
			guardarInteresadoDescripcion(request, interesadoForm, interesado);
			setReturnActionFordward(request,
					mappings.findForward("edicion_interesado_descripcion"));
		}
	}

	public void verPaginaListadoTercerosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request,
				mappings.findForward("edicion_interesado"));
	}

	private List buscarTercerosComunLogic(HttpServletRequest request,
			InteresadoForm dataForm) throws GestorTercerosException,
			NotAvailableException {

		// Obtener información del usuario conectado
		AppUser appUser = getAppUser(request);

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((appUser != null) && (StringUtils.isNotEmpty(appUser.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM, appUser.getEntity());
		}

		GestorTerceros bdTerceros = GestorTercerosFactory.getConnector(params);
		List resultadosBusqueda = null;
		if (StringUtils
				.equals(dataForm.getBuscarPor(),
						InteresadoForm.TIPOS_BUSQUEDA[InteresadoForm.BUSQUEDA_POR_NOMBRE])) {
			if (logger.isDebugEnabled())
				logger.debug("Solicitada busqueda por nombre");
			resultadosBusqueda = bdTerceros.recuperarTerceros(
					dataForm.getNameSearchToken(),
					dataForm.getSurname1SearchToken(),
					dataForm.getSurname2SearchToken());
		} else {
			short tipoBusqueda = 0;
			String queryString = null;
			switch (Arrays.binarySearch(InteresadoForm.TIPOS_BUSQUEDA,
					dataForm.getBuscarPor())) {
			case InteresadoForm.BUSQUEDA_POR_RAZON_SOCIAL:
				if (logger.isDebugEnabled())
					logger.debug("Solicitada busqueda por razón social");
				tipoBusqueda = TipoAtributo.RAZON_SOCIAL;
				queryString = dataForm.getCompanySearchToken();
				break;
			case InteresadoForm.BUSQUEDA_POR_IF:
				if (logger.isDebugEnabled())
					logger.debug("Solicitada busqueda por identificador fiscal");
				tipoBusqueda = (short)dataForm.getTipoNumeroIdentificacion();
				queryString = dataForm.getNiSearchToken();
				break;
			}
			resultadosBusqueda = bdTerceros.recuperarTerceros(tipoBusqueda,
					queryString);
		}
		if (!ListUtils.isEmpty(resultadosBusqueda)) {
			request.setAttribute(TransferenciasConstants.LISTADO_TERCEROS_SIZE,
					String.valueOf(resultadosBusqueda.size()));
		}
		return resultadosBusqueda;
	}

	public void buscarTercerosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el formulario
		InteresadoForm dataForm = (InteresadoForm) form;

		removeInTemporalSession(request,
				TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS);


		try {

			ActionErrors errors = validarBusquedaInteresadoForm(request, dataForm);

			if(errors.isEmpty()){
				List resultadosBusqueda = buscarTercerosComunLogic(request,
						dataForm);

				setInTemporalSession(request,
						TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS,
						resultadosBusqueda);
			}
			else{
				ErrorsTag.saveErrors(request, errors);
			}
		} catch (GestorTercerosException gte) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_TERCEROS, gte.getCause()));
		} catch (NotAvailableException nae) {
			logger.error("Funcionalidad no presente en la implementacion del gestor de terceros");
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_BUSQUEDA_TERECEROS_NO_DISPONIBLE));
		}
		setReturnActionFordward(request,
				mappings.findForward("edicion_interesado"));
	}

	public void buscarTercerosDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el formulario
		InteresadoForm dataForm = (InteresadoForm) form;

		// Generar el path de contexto para poder llamar desde Javascript
		InteresadoForm interesadoForm = (InteresadoForm) form;
		interesadoForm.setContextPath(request.getContextPath());

		removeInTemporalSession(request,
						TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS);

		try {
			ActionErrors errors = validarBusquedaInteresadoForm(request, interesadoForm);

			if(errors.isEmpty()){
				List resultadosBusqueda = buscarTercerosComunLogic(request,
						dataForm);

				setInTemporalSession(request,
						TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS,
						resultadosBusqueda);
			}
			else{
				ErrorsTag.saveErrors(request, errors);
			}

		} catch (GestorTercerosException gte) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_GESTOR_TERCEROS, gte.getCause()));
		} catch (NotAvailableException nae) {
			logger.error("Funcionalidad no presente en la implementacion del gestor de terceros");
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_BUSQUEDA_TERECEROS_NO_DISPONIBLE));
		}
		setReturnActionFordward(request,
				mappings.findForward("edicion_interesado_descripcion"));
	}

	public void incorporarInteresadosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InteresadoForm dataForm = (InteresadoForm) form;
		List terceros = (List) getFromTemporalSession(request,
				TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS);
		String[] indicesTercerosSeleccionados = dataForm.getSeleccionTerceros();
		if (indicesTercerosSeleccionados != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			List tercerosSeleccionables = new ArrayList(terceros);
			InteresadoVO aIncorporar = null;
			for (int i = 0; i < indicesTercerosSeleccionados.length; i++) {
				InfoTercero tercero = (InfoTercero) tercerosSeleccionables
						.get(Integer.parseInt(indicesTercerosSeleccionados[i]));
				aIncorporar = new InteresadoVO(NombresUtils.getNombreCompleto(
						tercero.getNombre(), tercero.getPrimerApellido(),
						tercero.getSegundoApellido()),
						tercero.getIdentificacion(), true);
				aIncorporar.setIdEnTerceros(tercero.getId());
				terceros.remove(tercero);
				udoc.addInteresado(aIncorporar);
			}
		}
		popLastInvocation(request);
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	/**
	 * Permite guardar los valores necesarios para crear un interesado en la
	 * ficha
	 *
	 * @param request
	 *            Petición actual
	 * @param interesadoForm
	 *            Formulario de la petición
	 * @param validado
	 *            Indica si el interesado es o no validado
	 */
	private void guardarInteresadoDescripcion(HttpServletRequest request,
			InteresadoForm interesadoForm, InteresadoVO interesado) {

		if (interesado != null) {
			// Guardar los valores de los campos para seleccionar el interesado
			ConfigConstantsDescripcionSistemasExternos ccdse = ConfigConstantsDescripcionSistemasExternos
					.getInstance();
			Map mapIdRefLists = ConfigConstantsDescripcionSistemasExternos
					.getInstance().processRefLine(interesadoForm.getRef());

			// Campo Identidad
			String identidad = ccdse
					.getValorCampo(
							ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
							ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_IDENTIDAD);
			if (StringUtils.isNotEmpty(identidad)) {
				Map map = (Map) mapIdRefLists.get(identidad);
				if (map != null) {
					String ref = (String) map
							.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
					if (StringUtils.isNotEmpty(ref)
							&& (StringUtils.isNotEmpty(interesado.getNombre()))) {
						interesadoForm.setRefDescripcionIdentidad(ref);
						interesadoForm.setDescripcionIdentidad(interesado
								.getNombre());
					}
				}
			}

			// Campo Num Identidad
			String numIdentidad = ccdse
					.getValorCampo(
							ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
							ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_NUM_IDENTIDAD);
			if (StringUtils.isNotEmpty(numIdentidad)) {
				Map map = (Map) mapIdRefLists.get(numIdentidad);
				if (map != null) {
					String ref = (String) map
							.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
					if (StringUtils.isNotEmpty(ref)
							&& (StringUtils.isNotEmpty(interesado
									.getNumeroIdentificacion()))) {
						interesadoForm.setRefDescripcionNumIdentidad(ref);
						interesadoForm.setDescripcionNumIdentidad(interesado
								.getNumeroIdentificacion());
					}
				}
			}

			// Campo Id Tercero
			String idTercero = ccdse
					.getValorCampo(
							ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
							ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_ID_TERCERO);
			if (StringUtils.isNotEmpty(idTercero)) {
				Map map = (Map) mapIdRefLists.get(idTercero);
				if (map != null) {
					String ref = (String) map
							.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
					if (StringUtils.isNotEmpty(ref)
							&& (StringUtils.isNotEmpty(interesado
									.getIdEnTerceros()))) {
						interesadoForm.setRefDescripcionIdTercero(ref);
						interesadoForm.setDescripcionIdTercero(interesado
								.getIdEnTerceros());
					}
				}
			}

			// Campo Rol
			String rol = ccdse
					.getValorCampo(
							ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
							ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_ROL);
			if (StringUtils.isNotEmpty(rol)) {
				Map map = (Map) mapIdRefLists.get(rol);
				if (map != null) {
					String ref = (String) map
							.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
					if (StringUtils.isNotEmpty(ref)
							&& (StringUtils.isNotEmpty(interesado.getRol()))) {

						// Obtener la lista de roles y el indice del
						// seleccionado

						List rolesInteresado = (List) getFromTemporalSession(
								request,
								TransferenciasConstants.LISTA_ROLES_INTERESADO);
						int indice = rolesInteresado.indexOf(interesado
								.getRol());
						if (indice != -1) {
							interesadoForm.setRefDescripcionRol(ref);
							interesadoForm.setDescripcionRol(String
									.valueOf(indice + 1));
						}
					}
				}
			}

			// Validado
			String validado = ccdse
					.getValorCampo(
							ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
							ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_VALIDADO);
			if (StringUtils.isNotEmpty(validado)) {
				Map map = (Map) mapIdRefLists.get(validado);
				if (map != null) {
					String ref = (String) map
							.get(ConfigConstantsDescripcionSistemasExternos.VALOR_REF);
					if (StringUtils.isNotEmpty(ref)) {
						interesadoForm.setRefDescripcionValidado(ref);
						if (interesado.isValidado()) {
							String textSi = ccdse
									.getValorIndice(
											ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
											ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_VALIDADO_INDICE_SI);
							interesadoForm.setDescripcionValidado(textSi);
						} else {
							String textNo = ccdse
									.getValorIndice(
											ConfigConstantsDescripcionSistemasExternos.SISTEMA_INTERESADOS,
											ConfigConstantsDescripcionSistemasExternos.CAMPO_INTERESADOS_VALIDADO_INDICE_NO);
							interesadoForm.setDescripcionValidado(textNo);
						}
					}
				}
			}
		}

		// Guardar que la validación fue correcta
		interesadoForm.setValidacionCorrecta(true);
	}

	public void incorporarInteresadosDescripcionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Generar el path de contexto para poder llamar desde Javascript
		InteresadoForm interesadoForm = (InteresadoForm) form;
		interesadoForm.setContextPath(request.getContextPath());

		List terceros = (List) getFromTemporalSession(request,
				TransferenciasConstants.RESULTADOS_BUSQUEDA_INTERESADOS);

		String[] indicesTercerosSeleccionados = interesadoForm
				.getSeleccionTerceros();
		InteresadoVO aIncorporar = null;
		if (indicesTercerosSeleccionados != null) {
			List tercerosSeleccionables = new ArrayList(terceros);
			for (int i = 0; i < indicesTercerosSeleccionados.length; i++) {
				InfoTercero tercero = (InfoTercero) tercerosSeleccionables
						.get(Integer.parseInt(indicesTercerosSeleccionados[i]));
				aIncorporar = new InteresadoVO(NombresUtils.getNombreCompleto(
						tercero.getNombre(), tercero.getPrimerApellido(),
						tercero.getSegundoApellido()),
						tercero.getIdentificacion(), true);
				aIncorporar.setIdEnTerceros(tercero.getId());
			}
		}

		// Guardar los datos necesarios
		guardarInteresadoDescripcion(request, interesadoForm, aIncorporar);

		setReturnActionFordward(request,
				mappings.findForward("edicion_interesado_descripcion"));
	}

	public void seleccionarInteresadoPrincipalExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		// La lista de interesados se obtiene a partir de la informacion de
		// unidad documental mantenida en la sesion
		String paramNumInteresado = request.getParameter("itemIndex");
		if (paramNumInteresado != null
				&& GenericValidator.isInt(paramNumInteresado)) {
			List interesados = udoc.getInteresados();
			InteresadoVO interesado = (InteresadoVO) interesados.get(Integer
					.parseInt(paramNumInteresado) - 1);
			udoc.setInteresadoPrincipal(interesado);
		}
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	public void volverAvistaUnidadDocumentalExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		popLastInvocation(request);
		setReturnActionFordward(request, mappings.findForward("info_udoc"));
	}

	/**
	 * Realiza las validaciones del Interesado No Validado
	 *
	 * @param interesadoForm
	 *            Formulario
	 * @return ActionErrors Errores detectados.
	 */
	protected ActionErrors validarInteresadoForm(HttpServletRequest request,
			InteresadoForm interesadoForm) {
		ActionErrors errors = new ActionErrors();
		String documento = interesadoForm.getNumeroIdentificacion();

		if (StringUtils.isEmpty(interesadoForm.getNombre())) {
			errors.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBREAPELLIDOS,
									request.getLocale())));
		}

		// Solo se valida el NIF/CIF si está relleno.
		if (!StringUtils.isBlank(documento)) {
			if (interesadoForm.getTipoIdentificacion() == Constants.TIPO_DOCUMENTO_CIF) {
				if (!esFormatoCif(documento)) {
					errors.add(
							Constants.ERROR_NUM_IDENTIFICACION_NO_VALIDO,
							new ActionError(
									Constants.ERROR_NUM_IDENTIFICACION_NO_VALIDO,
									Messages.getString(
											Constants.ETIQUETA_NUMIDENTIDAD,
											request.getLocale())));
				} else {
					if (!esCifValido(documento)) {
						errors.add(Constants.ERROR_NIF_CIF_INCORRECTO,
								new ActionError(
										Constants.ERROR_NIF_CIF_INCORRECTO,
										getCifCorrecto(documento)));
					}
				}
			} else if (interesadoForm.getTipoIdentificacion() == Constants.TIPO_DOCUMENTO_NIF) {
				// Comprobar si sólo es número.
				String digitos = null;
				String letra = null;
				if (esNumeroNif(documento)) {
					digitos = addCeros(documento, 8);
					letra = getLetraNif(digitos);
					documento = digitos + letra;
					interesadoForm.setNumeroIdentificacion(documento);
				}

				else {
					documento = documento.toUpperCase();

					if (!esFormatoNif(documento)) {
						errors.add(
								Constants.ERROR_NUM_IDENTIFICACION_NO_VALIDO,
								new ActionError(
										Constants.ERROR_NUM_IDENTIFICACION_NO_VALIDO,
										Messages.getString(
												Constants.ETIQUETA_NUMIDENTIDAD,
												request.getLocale())));
					} else {
						// Añadir Ceros
						documento = addCeros(documento, 9);

						if (!esNifValido(documento)) {
							errors.add(Constants.ERROR_NIF_CIF_INCORRECTO,
									new ActionError(
											Constants.ERROR_NIF_CIF_INCORRECTO,
											getNifCorrecto(documento)));
						}
					}
				}
				interesadoForm.setNumeroIdentificacion(documento);
			}
		}
		return errors;
	}

	protected ActionErrors validarBusquedaInteresadoForm(HttpServletRequest request,
			InteresadoForm interesadoForm) {
		ActionErrors errors = new ActionErrors();


		if(StringUtils.isNotBlank(interesadoForm.getBuscarPor())){
			switch (Arrays.binarySearch(InteresadoForm.TIPOS_BUSQUEDA,
					interesadoForm.getBuscarPor())) {

			case InteresadoForm.BUSQUEDA_POR_NOMBRE:

				if(StringUtils.isBlank(interesadoForm.getNameSearchToken())
					&& StringUtils.isBlank(interesadoForm.getSurname1SearchToken())
					&& StringUtils.isBlank(interesadoForm.getSurname2SearchToken())
					){
					errors.add(
							Constants.ERROR_GENERAL_MESSAGE,
							new ActionError("errors.noSearchToken"));
				}
				break;
			case InteresadoForm.BUSQUEDA_POR_RAZON_SOCIAL:
				if(StringUtils.isBlank(interesadoForm.getCompanySearchToken())){
					errors.add(
							Constants.ERROR_GENERAL_MESSAGE,
							new ActionError("errors.noSearchToken"));
				}
				break;
			case InteresadoForm.BUSQUEDA_POR_IF:
				if(StringUtils.isBlank(interesadoForm.getNiSearchToken())){
					errors.add(
							Constants.ERROR_GENERAL_MESSAGE,
							new ActionError("errors.noSearchToken"));
				}
				else{
					String valorAtrib = interesadoForm.getNiSearchToken();

					switch(interesadoForm.getTipoNumeroIdentificacion()){
					case TipoAtributo.IDENTIFICACION_CIF:

						if (!IFValidator
								.isValidIF(valorAtrib, IFValidator.DOCUMENTO_CIF)) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(Constants.ERROR_INVALID, Messages
											.getString("archigest.archivo.transferencias.terceros." + TipoAtributo.IDENTIFICACION_CIF,
													request.getLocale())));
						}

						break;

					case TipoAtributo.IDENTIFICACION_NIF:
						if (!IFValidator
								.isValidIF(valorAtrib, IFValidator.DOCUMENTO_NIF)) {

							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(Constants.ERROR_INVALID, Messages
											.getString("archigest.archivo.transferencias.terceros." + TipoAtributo.IDENTIFICACION_NIF,
													request.getLocale())));
						}

						break;


					case TipoAtributo.IDENTIFICACION_NIE:
						if (!IFValidator
								.isValidIF(valorAtrib, IFValidator.DOCUMENTO_NIE)) {


							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(Constants.ERROR_INVALID, Messages
											.getString("archigest.archivo.transferencias.terceros." + TipoAtributo.IDENTIFICACION_NIE,
													request.getLocale())));
						}

						break;

					case TipoAtributo.IDENTIFICACION_PASAPORTE:
						break;
					}
				}

				break;
			}
		}
		else{
			errors.add(
					Constants.ERROR_GENERAL_MESSAGE,
					new ActionError("errors.noSearchToken"));
		}


		return errors;
	}




	/**
	 * Comprueba si el documento introducido tiene el siguiente formato
	 * XNNNNNNNN (Letra + 8 Números)
	 *
	 * @param documento
	 * @return true si cumple el formato false si no lo cumple
	 */
	private static boolean esFormatoCif(String documento) {

		// Comprobar si el formato es NNNNNNNN
		Pattern mask = Pattern.compile(Constants.FORMATO_CIF);
		Matcher matcher = mask.matcher(documento);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si el Documento introducido es de 8 Digitos Ej. Válidos: 123
	 * 12345678
	 *
	 * @param documento
	 * @return true si el formano de NIF coincide. false si no coincide.
	 */
	private static boolean esNumeroNif(String documento) {
		Pattern mask = Pattern.compile(Constants.FORMATO_NUMERO_NIF);
		Matcher matcher = mask.matcher(documento);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si el Documento introducido es de 8 Digitos o de 8 Digitos +
	 * Letra Documentos Validos: NNNNNNNNX
	 *
	 * @param documento
	 * @return true si el formano de NIF coincide. false si no coincide.
	 */
	private static boolean esFormatoNif(String documento) {

		// Comprobar si cumple el formato NNNNNNNNX
		Pattern mask = Pattern.compile(Constants.FORMATO_NIF);
		Matcher matcher = mask.matcher(documento);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Obtiene la letra del NIF
	 *
	 * @param numeroNif
	 *            Número de DNI
	 * @return Letra del NIF correspondiente
	 */
	private static String getLetraNif(String numeroNif) {

		// Calcular la letra de control
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		int posicion_modulo = Integer.parseInt(numeroNif) % 23;
		String letra = letras.substring(posicion_modulo, posicion_modulo + 1);

		return letra;
	}

	/**
	 * Comprueba si el Nif introducido es válido.
	 *
	 * @param nif
	 *            NIF que se desea comprobar
	 * @return true si el nif es válido. false en caso contrario.
	 */
	private static boolean esNifValido(String nif) {
		String nifCalculado = getNifCorrecto(nif.substring(0, 8));

		if (nif.equals(nifCalculado)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A partir de un nif o un número de DNI, devuelve la letra correcta.
	 *
	 * @param nif
	 * @return Si el número de NIF tiene 8 digitos u 8 Digitos + Letra, el Nif
	 *         Correcto. "" Si no tiene almenos 8 digitos.
	 */
	private static String getNifCorrecto(String documento) {
		documento = documento.toUpperCase();

		if (esFormatoNif(documento)) {
			documento = documento.substring(0, 8);
		}
		documento += getLetraNif(documento);

		return documento;
	}

	/**
	 * Comprueba si el Cif es correcto
	 *
	 * @param cif
	 *            CIF a comprobar
	 * @return true si el cif es correcto. false en caso contrario.
	 */
	private static String getCifCorrecto(String documento) {

		if (esFormatoCif(documento)) {

			String numero = documento.substring(1, 8);
			String letra = documento.substring(0, 1);
			int digitoControl = getDigitoCif(numero);
			documento = letra + numero + digitoControl;
		}

		return documento;
	}

	/**
	 * Obtiene la letra correspondiente al CIF introducido
	 *
	 * @param numeroCif
	 *            Número del CIF del que desea calcular su letra
	 * @return La letra correspondiente al CIF introducido.
	 */
	private static int getDigitoCif(String numeroCif) {
		int A = 0;
		int B = 0;
		int C = 0;
		int D = 0;
		int temp = 0;
		for (int i = 1; i < numeroCif.length(); i += 2)
			A += Integer.valueOf(numeroCif.substring(i, i + 1)).intValue();

		for (int i = 0; i < numeroCif.length(); i += 2) {
			temp = Integer.valueOf(numeroCif.substring(i, i + 1)).intValue() * 2;
			if (temp >= 10) {
				String b = String.valueOf(temp);
				B += Integer.valueOf(b.substring(0, 1)).intValue()
						+ Integer.valueOf(b.substring(1)).intValue();
			} else {
				B += temp;
			}
		}

		C = A + B;
		if (C >= 10)
			C = Integer.valueOf(String.valueOf(C).substring(1)).intValue();
		D = (10 - C) % 10;
		return D;
	}

	/**
	 * Comprueba si el Cif introducido es válido.
	 *
	 * @param nif
	 *            CIF que se desea comprobar
	 * @return true si el cif es válido. false en caso contrario.
	 */
	private static boolean esCifValido(String cif) {

		String cifCalculado = getCifCorrecto(cif);

		if (cif.equals(cifCalculado)) {
			return true;
		} else {
			return false;
		}
	}

	private String addCeros(String cadena, int longitud) {
		for (int i = cadena.length(); i < longitud; i++) {
			cadena = "0" + cadena;
		}
		return cadena;
	}

}