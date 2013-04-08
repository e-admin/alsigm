package descripcion.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import transferencias.TipoTransferencia;
import util.ErrorsTag;
import util.StringOwnTokenizer;
import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.ConfiguracionBusquedas;
import xml.config.ConfiguracionBusquedasFactory;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ListaProductores;
import xml.config.RestriccionCampoBusqueda;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionDescripcionBI;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.util.ListUtils;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.exceptions.DescriptorDuplicadoException;
import descripcion.exceptions.ListaDescriptoraCerradaException;
import descripcion.forms.BusquedaDescriptoresForm;
import descripcion.model.EstadoDescriptor;
import descripcion.model.TipoDescriptor;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.ListaDescrVO;
import fondos.FondosConstants;
import fondos.model.CamposBusquedas;
import fondos.model.TipoProductor;

/**
 * Acción para la selección de descriptores.
 */
public class DescriptoresAction extends BaseAction {

	/**
	 * Muestra el formulario para la búsqueda de descriptores.
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
	protected void formBusqDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Tipo de referencia
		String tipo = request.getParameter("tipo");

		// Comprobar el tipo de referencia
		if (StringUtils.isNotBlank(tipo)
				&& tipo.equals("" + DefTipos.TIPO_REFERENCIA_ENTIDAD_PRODUCTORA)) {
			// Obtener las listas descriptoras de tipo entidad
			setInTemporalSession(
					request,
					DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
					getGestionDescripcionBI(request)
							.getListasDescrByTipoDescriptor(
									TipoDescriptor.ENTIDAD));
		} else {
			// Identificadores de listas de descriptores
			String idsListas = request.getParameter("idsListas");
			if (StringUtils.isNotBlank(idsListas)) {
				// Obtener las listas descriptoras
				setInTemporalSession(
						request,
						DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
						getGestionDescripcionBI(request).getListasDescriptoras(
								StringUtils.split(idsListas, ",")));
			} else {
				// Obtener las listas descriptoras
				setInTemporalSession(request,
						DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
						getGestionDescripcionBI(request)
								.getListasDescriptoras());
			}
		}

		setReturnActionFordward(request,
				mapping.findForward("busqueda_descriptores"));
	}

	/**
	 * Busca los descriptores en función de unos criterios.
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
		// Información del formulario
		BusquedaDescriptoresForm formDescr = (BusquedaDescriptoresForm) form;

		// Información de la paginación
		PageInfo pageInfo = new PageInfo(request, 10, "nombre");
		pageInfo.setDefautMaxNumItems();

		// Criterios de búsqueda
		BusquedaDescriptoresVO busquedaVO = formDescr
				.getBusquedaDescriptoresVO();
		busquedaVO.setEstado(EstadoDescriptor.VALIDADO);
		busquedaVO.setPageInfo(pageInfo);

		try {
			// Descriptores de la lista
			request.setAttribute(DescripcionConstants.DESCRIPTORES_LISTA_KEY,
					getGestionDescripcionBI(request)
							.getDescriptores(busquedaVO));
		} catch (TooManyResultsException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
							new Object[] { new Integer(e.getCount()),
									new Integer(e.getMaxNumResults()) }));
		}

		setReturnActionFordward(request,
				mapping.findForward("busqueda_descriptores"));
	}

	/**
	 * Muestra la lista de descriptores permitidos para seleccionar uno de
	 * ellos.
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
		// Información del formulario
		BusquedaDescriptoresForm formDescr = (BusquedaDescriptoresForm) form;

		// Obtener las listas descriptoras
		setInTemporalSession(request,
				DescripcionConstants.LISTAS_DESCRIPTORAS_KEY,
				getListasDescriptoras(formDescr, request));

		// Selección simple
		setInTemporalSession(request,
				DescripcionConstants.SELECCION_MULTIPLE_KEY, new Boolean(false));

		setReturnActionFordward(request,
				mapping.findForward("ver_descriptores"));
	}

	/**
	 * Muestra la lista de descriptores para el formulario de búsquedas.
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
		// Interfaz de acceso a la lógica de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Listas descriptoras
		List listasDescr = descripcionBI.getListasDescriptoras();
		setInTemporalSession(request,
				DescripcionConstants.LISTAS_DESCRIPTORAS_KEY, listasDescr);

		// Selección simple
		setInTemporalSession(request,
				DescripcionConstants.SELECCION_MULTIPLE_KEY, new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("ver_descriptores"));
	}

	/**
	 * Muestra la lista de descriptores productores para el formulario de
	 * búsquedas.
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
	protected void showBusquedaProductoresExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Interfaz de acceso a la lógica de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Obtener la configuración de búsquedas
		ConfiguracionBusquedas cbs = ConfiguracionBusquedasFactory
				.getConfiguracionBusquedas();
		List listasDescr = new ArrayList();
		try {
			// Obtener el campo de entrada productor
			Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
					FondosConstants.CFG_BUSQUEDA_KEY);

			ListIterator it = null;
			int sizeDescriptoras = 0;

			String[] ids = null;

			if (busqueda != null) {
				CampoBusqueda campoProductor = (CampoBusqueda) busqueda
						.getMapEntrada()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR);

				// Obtener de la búsqueda las listas descriptoras en las que se
				// busca
				RestriccionCampoBusqueda restriccion = (RestriccionCampoBusqueda) campoProductor
						.getRestricciones()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR);
				it = restriccion.getListasDescriptoras().listIterator();
				sizeDescriptoras = restriccion.getListasDescriptoras().size();

				ids = new String[sizeDescriptoras];
				int i = 0;
				while (it.hasNext()) {
					String lista = (String) it.next();
					ids[i++] = cbs.getListaDescriptora(lista).getNombre();
				}

			} else {
				// Obtener las lista por defecto de la configuración. Solo en
				// caso de que no se esté utilizando en una búsqueda.
				List listaDescriptoras = new ArrayList();

				ListaProductores listaProductoresInstitucion = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral()
						.getListaDescriptoresEntidad(
								TipoProductor.INSTITUCION.getIdentificador());

				if (listaProductoresInstitucion != null) {
					listaDescriptoras.add(listaProductoresInstitucion.getId());
				}

				ListaProductores listaProductoresFamilia = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral()
						.getListaDescriptoresEntidad(
								TipoProductor.FAMILIA.getIdentificador());
				if (listaProductoresFamilia != null) {
					listaDescriptoras.add(listaProductoresFamilia.getId());
				}

				ListaProductores listaProductoresOrgano = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral()
						.getListaDescriptoresEntidad(
								TipoProductor.ORGANO.getIdentificador());
				if (listaProductoresOrgano != null) {
					listaDescriptoras.add(listaProductoresOrgano.getId());
				}

				ListaProductores listaProductoresPersona = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral()
						.getListaDescriptoresEntidad(
								TipoProductor.PERSONA.getIdentificador());
				if (listaProductoresPersona != null) {
					listaDescriptoras.add(listaProductoresPersona.getId());
				}

				ListaProductores listaProductoresBdOrganizacion = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.getConfiguracionGeneral()
						.getListaDescriptoresEntidad(
								TipoProductor.BDORGANIZACION.getIdentificador());
				if (listaProductoresBdOrganizacion != null) {
					listaDescriptoras.add(listaProductoresBdOrganizacion
							.getId());
				}

				if (ListUtils.isNotEmpty(listaDescriptoras)) {
					ids = new String[listaDescriptoras.size()];

					for (int i = 0; i < listaDescriptoras.size(); i++) {
						ids[i] = (String) listaDescriptoras.get(i);
					}
				}
			}

			listasDescr = descripcionBI.getListasDescriptoras(ids);
		} catch (Exception e) {
			logger.error("Error al Mostrar la búsqueda de productores", e);
		}

		if (listasDescr.isEmpty()) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_NO_ENCONTRADAS_LISTAS_PRODUCTORAS));
		}

		setInTemporalSession(request,
				DescripcionConstants.LISTAS_DESCRIPTORAS_KEY, listasDescr);

		// Selección simple
		setInTemporalSession(request,
				DescripcionConstants.SELECCION_MULTIPLE_KEY, new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("ver_descriptores"));
	}

	/**
	 * Busca los descriptores en función de unos criterios.
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
	protected void searchExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Información del formulario
		BusquedaDescriptoresForm formDescr = (BusquedaDescriptoresForm) form;
		// Criterios de búsqueda
		BusquedaDescriptoresVO busquedaVO = formDescr
				.getBusquedaDescriptoresVO();
		searchCodeLogic(mapping, form, request, response, busquedaVO);

	}

	protected void searchCodeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			BusquedaDescriptoresVO busquedaVO) {

		// Información de la paginación
		PageInfo pageInfo = new PageInfo(request, 10, "nombre");
		pageInfo.setDefautMaxNumItems();

		// busquedaVO.setEstado(EstadoDescriptor.VALIDADO);
		busquedaVO.setPageInfo(pageInfo);

		try {
			// Descriptores de la lista
			request.setAttribute(DescripcionConstants.DESCRIPTORES_LISTA_KEY,
					getGestionDescripcionBI(request)
							.getDescriptores(busquedaVO));
		} catch (TooManyResultsException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
							new Object[] { new Integer(e.getCount()),
									new Integer(e.getMaxNumResults()) }));
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_descriptores"));

	}

	/**
	 * Muestra la lista de descriptores productores para el formulario de
	 * búsquedas.
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
	protected void showBusquedaProductoresRelacionExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedaDescriptoresForm formDescr = (BusquedaDescriptoresForm) form;

		formDescr.reset(mapping, request);

		// Selección simple
		setInTemporalSession(request,
				DescripcionConstants.SELECCION_MULTIPLE_KEY, new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("ver_productores_relacion"));
	}

	protected void searchProductoresRelacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BusquedaDescriptoresForm formDescr = (BusquedaDescriptoresForm) form;
			BusquedaDescriptoresVO busquedaVO = new BusquedaDescriptoresVO();
			busquedaVO.setNombre(formDescr.getNombre());

			Integer[] tiposRelacion = new Integer[] { new Integer(
					TipoTransferencia.INGRESO_DIRECTO.getIdentificador()) };
			List listaProductores = getGestionDescripcionBI(request)
					.getDescriptoresProductoresRelacion(busquedaVO,
							tiposRelacion);

			request.setAttribute(
					DescripcionConstants.LISTA_PRODUCTORES_RELACION,
					listaProductores);

		} catch (TooManyResultsException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
							new Object[] { new Integer(e.getCount()),
									new Integer(e.getMaxNumResults()) }));
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_productores_relacion"));

	}

	/**
	 * Obtiene las listas descriptoras correspondientes.
	 *
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	private List getListasDescriptoras(BusquedaDescriptoresForm form,
			HttpServletRequest request) {
		List listas = new ArrayList();

		// Interfaz de acceso a la lógica de descripción
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Comprobar el tipo de referencia
		if (StringUtils.isNotBlank(form.getRefType())
				&& form.getRefType().equals(
						"" + DefTipos.TIPO_REFERENCIA_ENTIDAD_PRODUCTORA)) {
			// Obtener las listas descriptoras de tipo entidad
			listas.addAll(descripcionBI
					.getListasDescrByTipoDescriptor(TipoDescriptor.ENTIDAD));
		} else {
			// Obtener las listas descriptoras configuradas
			String refIdsLists = form.getRefIdsLists();
			if (StringUtils.isNotBlank(refIdsLists)) {
				// Listas
				StringOwnTokenizer tok = new StringOwnTokenizer(refIdsLists,
						",");
				String[] refListIds = tok.toArray();

				if (refListIds.length == 1) {
					listas.add(descripcionBI.getListaDescriptora(refListIds[0]));
				} else if (refListIds.length > 1) {
					List listasDescr = descripcionBI.getListasDescriptoras();
					refIdsLists += ",";
					for (int i = listasDescr.size() - 1; i >= 0; i--)
						if (refIdsLists.indexOf(((ListaDescrVO) listasDescr
								.get(i)).getId() + ",") < 0)
							listasDescr.remove(i);

					listas.addAll(listasDescr);
				}
			} else
				listas.addAll(descripcionBI.getListasDescriptoras());
		}

		return listas;
	}

	protected void crearDescriptorExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		BusquedaDescriptoresForm formDescr = (BusquedaDescriptoresForm) form;

		ActionErrors errores = formDescr.validateCreate(mapping, request);

		if (errores == null || errores.isEmpty()) {
			BusquedaDescriptoresVO busquedaVO = formDescr
					.getBusquedaDescriptoresVO();
			try {
				getGestionDescripcionBI(request).addDescriptor(
						formDescr.getIdLista(), formDescr.getNombre());
				// Criterios de búsqueda

				searchCodeLogic(mapping, form, request, response, busquedaVO);
			} catch (ListaDescriptoraCerradaException e) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_LISTA_DESCRIPTORES_CERRADA));
				gestionarError(request, mapping, null, null);
			} catch (DescriptorDuplicadoException e) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_DESCRIPTOR_DUPLICADO));
				searchCodeLogic(mapping, formDescr, request, response,
						busquedaVO);
			} catch (Exception e) {
				gestionarError(request, mapping, e, null);
			}
		} else {
			gestionarError(request, mapping, null, errores);
		}
	}

	private void gestionarError(HttpServletRequest request,
			ActionMapping mapping, Exception e, ActionErrors errores) {

		if (e != null) {
			logger.error(e);
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_ELEMENTO_NO_VIGENTE));
		}

		if (errores != null && !errores.isEmpty()) {
			ErrorsTag.saveErrors(request, errores);
		}

		setReturnActionFordward(request,
				mapping.findForward("ver_descriptores"));
	}

}