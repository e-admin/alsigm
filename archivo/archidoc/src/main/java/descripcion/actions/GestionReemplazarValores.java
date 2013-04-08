package descripcion.actions;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppPermissions;
import util.CollectionUtils;
import util.ErrorsTag;
import util.PaginatedList;
import xml.config.Busqueda;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.exceptions.ColumnNotIndexedException;
import common.exceptions.SecurityException;
import common.exceptions.SintaxErrorException;
import common.exceptions.TooManyResultsException;
import common.exceptions.WordOmittedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.DescripcionUtils;
import descripcion.ErrorKeys;
import descripcion.FormaReemplazo;
import descripcion.forms.BusquedaReemplazosForm;
import descripcion.vos.CampoDatoBusquedaVO;
import descripcion.vos.ElementoCFPO;
import descripcion.vos.ElementoCFVO;
import descripcion.vos.FichaVO;
import fondos.FondosConstants;
import fondos.actions.BusquedaElementosAction;
import fondos.forms.BusquedaElementosForm;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.model.NivelCF;
import fondos.model.PrecondicionesBusquedaFondosGenerica;
import fondos.utils.BusquedasHelper;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.TablaTemporalFondosVO;

public class GestionReemplazarValores extends BusquedaElementosAction {

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(GestionReemplazarValores.class);

	protected void loadListasBusqueda(Busqueda busqueda,
			BusquedaElementosForm form, HttpServletRequest request,
			PrecondicionesBusquedaFondosGenerica precondiciones) {
		// si ya estaba cargada la lista de fichas
		boolean cargarTodosLosCampos = false;
		if (getFromTemporalSession(request, FondosConstants.LISTA_FICHAS_KEY) != null) {
			BusquedaReemplazosForm formulario = (BusquedaReemplazosForm) form;
			if (StringUtils.isEmpty(formulario.getIdFicha()))
				cargarTodosLosCampos = true;
		}

		if (cargarTodosLosCampos) {
			GestionDescripcionBI descBI = getGestionDescripcionBI(request);
			// se obtienen todos los campos vinculados actualmente a fichas.
			List listaFichas = descBI.getFichas();
			String idFicha = null;
			HashSet camposInsertados = new HashSet();
			List listaTodosLosCampos = new ArrayList();
			String idCampo = null;
			for (Iterator it = listaFichas.iterator(); it.hasNext();) {
				idFicha = ((FichaVO) it.next()).getId();
				List listaCamposFicha = descBI
						.getCamposBusquedaAvanzada(idFicha);
				for (Iterator it2 = listaCamposFicha.iterator(); it2.hasNext();) {
					Object campo = it2.next();
					idCampo = ((CampoDatoBusquedaVO) campo).getId();
					if (!camposInsertados.contains(idCampo)) {
						// if(DescripcionUtils.isCampoReemplazable(idCampo))
						listaTodosLosCampos.add(campo);
						camposInsertados.add(idCampo);
					}
				}
			}
			// obtener todos los campos de las fichas, quitando los que no deben
			// aparecer
			request.setAttribute(FondosConstants.LISTA_CAMPOS_FICHA_KEY,
					listaTodosLosCampos);
			List fichas = (List) getFromTemporalSession(request,
					FondosConstants.LISTA_FICHAS_KEY);
			request.setAttribute(FondosConstants.LISTA_FICHAS_KEY, fichas);
			return;
		}
		BusquedasHelper.loadListasBusqueda(busqueda, form, request,
				precondiciones);
	}

	private PrecondicionesBusquedaFondosGenerica getPrecondicionesReemplazo() {
		PrecondicionesBusquedaFondosGenerica precondiciones = new PrecondicionesBusquedaFondosGenerica();
		// precondiciones.setFilePath(PrecondicionesBusquedaFondosGenerica.BUSQUEDA_GENERICA_REEMPLAZO_SIMPLE);
		precondiciones
				.setTipoBusqueda(ConfiguracionArchivoManager.REEMPLAZO_SIMPLE);
		precondiciones.setEstados(new String[] { String
				.valueOf(IElementoCuadroClasificacion.VIGENTE) });
		// precondiciones.setTipoElemento(String.valueOf(TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador()));
		// precondiciones.setTiposNivelFicha(new int []
		// {TipoNiveles.UNIDAD_DOCUMENTAL_VALUE});
		precondiciones.setForwardListado("listado");
		// precondiciones.setForwardRetorno("ver_udocs_afectadas_simple");
		return precondiciones;
	}

	private void initReemplazoGenerico(HttpServletRequest request,
			PrecondicionesBusquedaFondosGenerica precondiciones, ActionForm form)
			throws Exception {
		// Guardar las precondiciones de la búsqueda
		setInTemporalSession(request,
				FondosConstants.PRECONDICIONES_BUSQUEDA_KEY, precondiciones);

		removeInTemporalSession(request, FondosConstants.LISTA_ELEMENTOS_CF);

		// Busqueda busqueda = getCfgBusquedaFondosGenerica(request,
		// precondiciones.getFilePath());
		Busqueda busqueda = getCfgBusquedaFondosGenerica(request,
				precondiciones.getTipoBusqueda());
		setInTemporalSession(request, FondosConstants.CFG_BUSQUEDA_KEY,
				busqueda);
		loadListasBusqueda(busqueda, (BusquedaElementosForm) form, request,
				precondiciones);
		busqueda.setResultadoSinElementosBloqueados(true);

		List listaCampos = (List) request
				.getAttribute(FondosConstants.LISTA_CAMPOS_FICHA_KEY);
		// eliminar los campos que no pueden ser reemplazados
		if (listaCampos != null) {
			listaCampos = DescripcionUtils
					.getCamposReemplazables((List) request
							.getAttribute(FondosConstants.LISTA_CAMPOS_FICHA_KEY));
			setInTemporalSession(request,
					FondosConstants.LISTA_CAMPOS_FICHA_KEY, listaCampos);
		}

		List listaFichas = (List) request
				.getAttribute(FondosConstants.LISTA_FICHAS_KEY);
		if (listaFichas != null)
			setInTemporalSession(request, FondosConstants.LISTA_FICHAS_KEY,
					listaFichas);

		// Obtener todos los tipos de reemplazo
		setInTemporalSession(request,
				FondosConstants.LISTA_FORMAS_REEMPLAZO_SIMPLE_KEY,
				FormaReemplazo.getLista(request.getLocale()));

	}

	public void initReemplazoSimpleExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			// Establecer las precondiciones de la búsqueda
			PrecondicionesBusquedaFondosGenerica precondiciones = getPrecondicionesReemplazo();
			precondiciones
					.setEntradaParaMigaPan(KeysClientsInvocations.LISTADO_REEMPLAZO_SIMPLE);

			String modificados = (String) getFromTemporalSession(request,
					DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS);
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.REEMPLAZO_SIMPLE, request);
			invocation.setAsReturnPoint(true);
			setInTemporalSession(request,
					DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS,
					modificados);

			initReemplazoGenerico(request, precondiciones, form);

			// inicializarBusqueda(request,form);
			setReturnActionFordward(request,
					mappings.findForward("pantalla_reemplazo_simple"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mappings, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mappings, form, request, response);
		}

	}

	public void initReemplazoAvanzadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			// Establecer las precondiciones de la búsqueda
			PrecondicionesBusquedaFondosGenerica precondiciones = getPrecondicionesReemplazo();
			precondiciones
					.setEntradaParaMigaPan(KeysClientsInvocations.LISTADO_REEMPLAZO_AVANZADO);

			String modificados = (String) getFromTemporalSession(request,
					DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS);
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.REEMPLAZO_AVANZADO, request);
			invocation.setAsReturnPoint(true);
			setInTemporalSession(request,
					DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS,
					modificados);

			initReemplazoGenerico(request, precondiciones, form);

			setReturnActionFordward(request,
					mappings.findForward("pantalla_reemplazo_avanzado"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mappings, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mappings, form, request, response);
		}
	}

	public void cargarCamposReemplazoAvanzadoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			// Establecer las precondiciones de la búsqueda
			PrecondicionesBusquedaFondosGenerica precondiciones = getPrecondicionesReemplazo();

			initReemplazoGenerico(request, precondiciones, form);

			setReturnActionFordward(request,
					mappings.findForward("listado_reemplazo_avanzado"));
		} catch (FileNotFoundException flne) {
			logger.error(
					"NO SE HA ENCONTRADO EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					flne);
			getErrors(request, false).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_CONFIGURACION_FILE_NOT_FOUND));
			goBackExecuteLogic(mappings, form, request, response);
		}

		catch (Exception e) {
			logger.error(
					"ERROR AL OBTENER EL FICHERO DE CONFIGURACION DE BUSQUEDA",
					e);
			getErrors(request, false).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CONFIGURACION_FILE));
			goBackExecuteLogic(mappings, form, request, response);
		}

	}

	public void selListadoReemplazoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedaReemplazosForm formulario = (BusquedaReemplazosForm) form;
		if (formulario.isReemplazoSimple()) {
			setReturnActionFordward(request,
					mappings.findForward("ver_elementos_afectados"));
		} else {
			setReturnActionFordward(request,
					mappings.findForward("listado_reemplazo_avanzado"));
		}
	}

	public void verElementosAfectadosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Object obj=getFromTemporalSession(request, "BusquedaReemplazosForm");
		// if(obj!=null) form=(ActionForm)obj;

		removeInTemporalSession(request,
				DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS);

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaReemplazosForm) form)
				.getMapClassAndSuperClassObject(form));
		cli.addParameter("selectedIds", null);
		BusquedaReemplazosForm formulario = (BusquedaReemplazosForm) form;
		formulario.setPostBack(true);

		formulario.checkNulosAndResetValor1();

		if (obtenerErrores(request, true).size() > 0) {
			if (formulario.isReemplazoSimple()) {
				setReturnActionFordward(request,
						mappings.findForward("ver_elementos_afectados"));
			} else {
				setReturnActionFordward(request,
						mappings.findForward("listado_reemplazo_avanzado"));
			}
			return;
		}

		buscarExecuteLogic(mappings, form, request, response);
		if (formulario.isReemplazoSimple()) {
			modificarMensajeErrorValidacionForm(request);
		}
		// List
		// resultadosBusqueda=(List)getFromTemporalSession(request,FondosConstants.LISTA_ELEMENTOS_CF);
		List resultadosBusqueda = getListFromSessionAttributeLISTA_ELEMENTOS_CF(request);
		int tamResultados = 0;
		if (resultadosBusqueda != null && resultadosBusqueda.size() > 0)
			tamResultados = resultadosBusqueda.size();
		setInTemporalSession(request,
				FondosConstants.NUM_RESULTADOS_LISTA_ELEMENTOS_CF, ""
						+ tamResultados);

		// setReturnActionFordward(request, mappings.findForward("listado"));
		if (obtenerErrores(request, true).size() > 0) {
			setReturnActionFordward(request,
					new ActionForward(cli.getInvocationURI(), true));
			return;
		}

		if (formulario.isReemplazoSimple()) {
			setReturnActionFordward(request,
					mappings.findForward("ver_elementos_afectados"));
		} else {
			setReturnActionFordward(request,
					mappings.findForward("listado_reemplazo_avanzado"));
		}
	}

	public void modificarMensajeErrorValidacionForm(HttpServletRequest request) {
		ActionErrors errors = obtenerErrores(request, true);
		if (errors.size() > 0) {
			int i = 0;
			for (Iterator it = errors.properties(); it.hasNext();) {
				String property = (String) it.next();
				for (Iterator it2 = errors.get(property); it2.hasNext();) {
					ActionError error = (ActionError) it2.next();
					if (error
							.getKey()
							.equals(Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO_SOLOUNO)) {
						it2.remove();
						errors.add(
								property,
								new ActionError(
										DescripcionConstants.ERROR_REEMPLAZOS_VALORACTUAL_VACIO,
										error.getValues()));
						return;
					}
				}
				i++;
			}
		}
	}

	public void verSeriesAfectadasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BusquedaReemplazosForm formulario = (BusquedaReemplazosForm) form;

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaReemplazosForm) form)
				.getMapClassAndSuperClassObject(form));

		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);
		BusquedaElementosVO busquedaElementosVO = formulario
				.getBusquedaElementosVOReemplazo(busqueda, precondiciones);

		String idCampo = busquedaElementosVO.getCampoCambio();
		GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);
		List seriesAfectadas = service.getSeriesAfectadas(busquedaElementosVO,
				busqueda, DescripcionUtils.isCampoDescripcionFechaIni(idCampo));

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.REEMPLAZO_SERIES_AFECTADAS, request);
		invocation.setAsReturnPoint(true);

		request.setAttribute(
				DescripcionConstants.DESCRIPCION_LISTA_SERIES_AFECTADAS,
				seriesAfectadas);
		setReturnActionFordward(request,
				mappings.findForward("series_afectadas"));
	}

	private void complementaFormulario(BusquedaReemplazosForm formulario,
			HttpServletRequest request, Busqueda busqueda) {
		// Si estamos ante una búsqueda donde no hace falta rellenar los
		// niveles, es necesario establecer que se busque en todos los niveles
		// excepto el 1
		if (!busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {
			if (formulario.getNiveles() == null
					|| (formulario.getNiveles() != null && formulario
							.getNiveles().length == 0)) {
				// Si se hace una búsqueda reducida, se limita la búsqueda a
				// elementos mayor que el 1
				List niveles = getGestionCuadroClasificacionBI(request)
						.getNivelesCF();
				// List niveles =
				// (List)request.getAttribute(DescripcionConstants.NIVELES_KEY);
				String[] idsNiveles = new String[niveles.size()];
				for (int i = 0; i < niveles.size(); i++)
					idsNiveles[i] = ((NivelCF) niveles.get(i)).getId();
				formulario.setNiveles(idsNiveles);
			}
		}

		// Si estamos ante una búsqueda donde no hace falta rellenar el estado
		// es necesario establecer que se busque en elementos vigentes
		if (!busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)) {
			if (formulario.getEstados() == null
					|| (formulario.getEstados() != null && formulario
							.getEstados().length == 0))
				formulario.setEstados(new String[] { new Integer(
						IElementoCuadroClasificacion.VIGENTE).toString() });
		}

		if (!getServiceClient(request).hasPermission(
				AppPermissions.CREACION_CUADRO_CLASIFICACION))
			formulario.setEstados(new String[] { ""
					+ IElementoCuadroClasificacion.VIGENTE });
	}

	public void checkReemplazoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		removeInTemporalSession(request,
				DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS);

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((BusquedaReemplazosForm) form)
				.getMapClassAndSuperClassObject(form));
		// cli.addParameters(((BusquedaElementosForm) form).getMap());

		BusquedaReemplazosForm formulario = (BusquedaReemplazosForm) form;

		// Obtener las precondiciones por si es una búsqueda genérica
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);
		// GestionCuadroClasificacionBI service =
		// getGestionCuadroClasificacionBI(request);
		// Obtener la configuración de la búsqueda
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);

		formulario.checkNulosAndResetValor1();

		if (busqueda == null) {
			// Busqueda no esta en la session
			if (formulario.isReemplazoSimple())
				setReturnActionFordward(request,
						mapping.findForward("pantalla_reemplazo_simple"));
			else
				setReturnActionFordward(request,
						mapping.findForward("reemplazo_avanzado"));
			return;
		}

		// Validar el formulario
		ActionErrors errores = formulario.validateCampos(request,
				formulario.isReemplazoSimple() ? 1 : 0);
		if ((errores == null) || errores.isEmpty()) {
			logger.info("Formulario validado");
			// loadListasBusqueda(busqueda, (BusquedaElementosForm) form,
			// request, precondiciones);
			complementaFormulario(formulario, request, busqueda);

			BusquedaElementosVO busquedaElementosVO = formulario
					.getBusquedaElementosVOReemplazo(busqueda, precondiciones);

			// si es reemplazo avanzado y seleccionado el checkbox de solo
			// valores vacios de ficha
			// realizar consulta para obtener los ids seleccionados en cuya
			// ficha del elemento no tiene ese campo
			// rellenar el BusquedaElementoVO con esos ids.
			String[] vIdsCampoFichaVacio = null;
			if (formulario.isReemplazoValoresNulos()
					&& busquedaElementosVO.getSelectedIds() != null
					&& busquedaElementosVO.getSelectedIds().length > 0) {
				List idsCampoFichaVacio = getGestionCuadroClasificacionBI(
						request).getIdsElementosCampoVacioFicha(
						CollectionUtils.createList(busquedaElementosVO
								.getSelectedIds()),
						formulario.getTipoCampoCambio().intValue(),
						formulario.getCampoCambio());
				vIdsCampoFichaVacio = new String[idsCampoFichaVacio.size()];
				for (int i = 0; i < idsCampoFichaVacio.size(); i++)
					vIdsCampoFichaVacio[i] = (String) idsCampoFichaVacio.get(i);
				busquedaElementosVO.setSelectedIds(vIdsCampoFichaVacio);
			}

			// comprobar que tras el cambio la fecha inicial es correcta con
			// respecto a la final.
			if (!checkElementosAfectados(request, busquedaElementosVO,
					busqueda, formulario)
					|| !checkUpdateFechas(request, busquedaElementosVO,
							busqueda)
					|| !checkPermitidoCambioFechaExtremaEnSeriesAfectadas(
							request, busquedaElementosVO, busqueda)) {
				if (formulario.isReemplazoSimple())
					setReturnActionFordward(request,
							mapping.findForward("reemplazo_simple"));
				else
					setReturnActionFordward(request,
							mapping.findForward("listado_reemplazo_avanzado"));
				return;
			}

			// se setean en este punto los nuevos ids, para tener un
			// discriminador para poder diferenciar
			// el caso en el que se quiere reemplaza r valores vacios en la
			// ficha, hay elementos seleccionados
			// pero ninguno de ellos tiene vacio el campo seleccionado.
			if (vIdsCampoFichaVacio != null) {
				formulario.setSelectedIds(vIdsCampoFichaVacio);
			}

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CONFIRMACION_REEMPLAZO, request);
			invocation.setAsReturnPoint(true);

			if (!formulario.isReemplazoSimple()) {
				removeNotSelectedNodesFromListInTemporalSession(request,
						busquedaElementosVO.getSelectedIds(),
						FondosConstants.LISTA_ELEMENTOS_CF);

				try {
					TablaTemporalFondosVO tablaTemporal = getGestionCuadroClasificacionBI(
							request).getTablaTemporal(
							getAppUser(request).getId(), "R",
							busquedaElementosVO.getSelectedIds());
					setTablaTemporales(request, tablaTemporal);
					setInTemporalSession(request,
							FondosConstants.NUM_RESULTADOS_LISTA_ELEMENTOS_CF,
							"" + busquedaElementosVO.getSelectedIds().length);
				} catch (Exception e) {
					if (errores == null)
						errores = new ActionErrors();
					errores = new ActionErrors();
					errores.add(
							Constants.ERROR_GENERAL_MESSAGE,
							new ActionError(Constants.ERROR_GENERAL_MESSAGE, e
									.getMessage()));
					ErrorsTag.saveErrors(request, errores);
				}

			}

			setReturnActionFordward(request,
					mapping.findForward("confirmacion_reemplazo"));

			// setInTemporalSession(request, "BusquedaReemplazosForm", form);
		} else {
			logger.info("Formulario inv\u00E1lido");
			// Añadir los errores al request
			obtenerErrores(request, true).add(errores);

			if (formulario.isReemplazoSimple()) {
				modificarMensajeErrorValidacionForm(request);
			}

			// para no eliminar la entrada de la pila de invocaciones
			// setReturnActionFordward(request,
			// mapping.findForward("elementos_afectados"));
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	public boolean checkElementosAfectados(HttpServletRequest request,
			BusquedaElementosVO vo, Busqueda busqueda,
			BusquedaReemplazosForm formulario) {
		int elementosAfectados = 0;
		if (vo.getSelectedIds() != null)
			elementosAfectados = vo.getSelectedIds().length;
		else {
			List listaElementosAfectados = getListFromSessionAttributeLISTA_ELEMENTOS_CF(request);
			if (listaElementosAfectados == null) {
				GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);
				elementosAfectados = service.getCountElementosAfectados(vo,
						busqueda);
			} else
				elementosAfectados = listaElementosAfectados.size();
		}

		if (elementosAfectados == 0) {
			if (vo.getSelectedIds() == null) {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										DescripcionConstants.ERROR_DESCRIPCION_REEMPLAZO_SIN_EFECTO));
			} else {
				if (formulario.getSelectedIds() != null
						&& formulario.getSelectedIds().length > 0)
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											DescripcionConstants.ERROR_REEMPLAZO_AVANZADO_NO_ELEMENTOS_CON_CAMPOS_FICHA_VACIOS));
				else
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											DescripcionConstants.ERROR_DESCRIPCION_REEMPLAZO_ERROR_SIN_SELECCIONAR_ELEMS));
			}

			return false;
		} else if (elementosAfectados == -1) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DescripcionConstants.ERROR_DESCRIPCION_REEMPLAZO_ERROR_GENERICO));
			return false;
		}
		return true;
	}

	public boolean checkPermitidoCambioFechaExtremaEnSeriesAfectadas(
			HttpServletRequest request, BusquedaElementosVO vo,
			Busqueda busqueda) {
		GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);

		// comprobar si el id del campo a cambiar es una fecha extrema.
		boolean checkFechas = false;
		boolean isFechaIni = false;

		if (vo == null || vo.getCampoCambio() == null)
			return true;

		String idCampo = null;

		idCampo = vo.getCampoCambio();
		if (DescripcionUtils.isCampoDescripcionFechaIni(idCampo)) {
			checkFechas = true;
			isFechaIni = true; // hasFechaIni=true;
		} else if (DescripcionUtils.isCampoDescripcionFechaFin(idCampo)) {
			checkFechas = true; // hasFechaFin=true;
		}

		if (!checkFechas)
			return true;

		busqueda.setTemporalyConElementosBloqueados(true);
		List udocsFueraRango = service.checkUdocsInRangeNewFechasSeries(vo,
				busqueda, isFechaIni);
		busqueda.setTemporalyConElementosBloqueados(false);
		if (udocsFueraRango != null && udocsFueraRango.size() > 0) {
			// en el nuevo rango para las series hay alguna udoc de esas series
			// fuera de ese rango
			// List
			// elementos=(List)request.getSession().getAttribute(FondosConstants.LISTA_ELEMENTOS_CF);
			List elementos = getListFromSessionAttributeLISTA_ELEMENTOS_CF(request);
			if (elementos == null) {
				elementos = service.getElementosAfectados(vo, busqueda);
			}
			HashMap elems = new HashMap();
			for (Iterator it = elementos.iterator(); it.hasNext();) {
				ElementoCFVO elemCF = (ElementoCFVO) it.next();
				elems.put(elemCF.getId(), elemCF);
			}

			List listaFinalSeries = new ArrayList();
			for (Iterator it = udocsFueraRango.iterator(); it.hasNext();) {
				ElementoCuadroClasificacion udoc = (ElementoCuadroClasificacion) it
						.next();
				Object obj = elems.get(udoc.getIdPadre());
				listaFinalSeries.add(((ElementoCFVO) obj).getCodReferencia());
			}

			String message = DescripcionConstants.ERROR_DESCRIPCION_UDOC_FUERA_NUEVO_RANGO_SERIE;
			Map mapElementosError = new HashMap();
			mapElementosError.put(message, listaFinalSeries);
			request.setAttribute(Constants.LISTA_ELEMENTOS_ERROR_KEY,
					mapElementosError);

			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DescripcionConstants.ERROR_DESCRIPCION_UDOC_FUERA_NUEVO_RANGO_SERIE));
			return false;
		}
		return true;
	}

	public void reemplazarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Pendiente comprobar campos aun no comprobados. campos con el valor a
		// utilizar en la modificacion
		// tratar de usar los ids de los elementos
		BusquedaReemplazosForm formulario = (BusquedaReemplazosForm) form;
		// BusquedaReemplazosForm formulario =
		// (BusquedaReemplazosForm)getFromTemporalSession(request,
		// "BusquedaReemplazosForm");

		// Obtener las precondiciones por si es una búsqueda genérica
		PrecondicionesBusquedaFondosGenerica precondiciones = (PrecondicionesBusquedaFondosGenerica) getFromTemporalSession(
				request, FondosConstants.PRECONDICIONES_BUSQUEDA_KEY);

		GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);

		// Obtener la configuración de la búsqueda
		Busqueda busqueda = (Busqueda) getFromTemporalSession(request,
				FondosConstants.CFG_BUSQUEDA_KEY);

		// loadListasBusqueda(busqueda, (BusquedaElementosForm) form, request,
		// precondiciones);
		try {
			BusquedaElementosVO busquedaElementosVO = formulario
					.getBusquedaElementosVOReemplazo(busqueda, precondiciones);

			int filasAfectadas = -1;
			String nombreCampoCambio = getNombreCampoAActualizar(
					busquedaElementosVO.getCampoCambio(), request);

			filasAfectadas = service
					.reemplazoElementosCF(busquedaElementosVO, busqueda,
							nombreCampoCambio, formulario.getFormaReemplazo());
			if (formulario.isReemplazoSimple()) {
				setReturnActionFordward(request,
						mapping.findForward("pantalla_reemplazo_simple"));
			} else {
				setReturnActionFordward(request,
						mapping.findForward("pantalla_reemplazo_avanzado"));
			}
			// if(filasAfectadas>0)
			setInTemporalSession(request,
					DescripcionConstants.DESCRIPCION_NUM_REG_MODIFICADOS, ""
							+ filasAfectadas);
			removeInTemporalSession(request, FondosConstants.LISTA_ELEMENTOS_CF);
		} catch (ColumnNotIndexedException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_COLUMNA_NO_INDEXADA));

			goBackExecuteLogic(mapping, form, request, response);
		} catch (WordOmittedException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_PALABRA_OMITIDA));

			goBackExecuteLogic(mapping, form, request, response);
		} catch (SintaxErrorException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_SINTAXIS_INCORRECTA));

			goBackExecuteLogic(mapping, form, request, response);
		} catch (SecurityException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_SIN_PERMISOS));

			goBackExecuteLogic(mapping, form, request, response);
		} catch (TooManyResultsException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
							new Object[] { new Integer(e.getCount()),
									new Integer(e.getMaxNumResults()) }));
			goBackExecuteLogic(mapping, form, request, response);
		} catch (Exception e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_GENERICO_REEMPLAZO, e
							.getMessage()));

			goBackExecuteLogic(mapping, form, request, response);
		}
	}

	public String getNombreCampoAActualizar(String idCampo,
			HttpServletRequest request) {
		List listaCampos = (List) getFromTemporalSession(request,
				FondosConstants.LISTA_CAMPOS_FICHA_KEY);
		HashMap idsNombresCampos = new HashMap();
		for (Iterator it = listaCampos.iterator(); it.hasNext();) {
			CampoDatoBusquedaVO campo = (CampoDatoBusquedaVO) it.next();
			idsNombresCampos.put(campo.getId(), campo.getNombre());
		}
		return (String) idsNombresCampos.get(idCampo);
	}

	public boolean checkUpdateFechas(HttpServletRequest request,
			BusquedaElementosVO vo, Busqueda busqueda) {
		GestionCuadroClasificacionBI service = getGestionCuadroClasificacionBI(request);

		// este metodo esta pensado en principio para si en la busqueda solo se
		// utiliza un campo
		boolean checkFechas = false;
		boolean isFechaIni = false;

		if (vo == null || vo.getCampo() == null || vo.getCampo().length == 0
				|| vo.getCampo().length > 1)
			return true;

		String idCampo = null;

		idCampo = vo.getCampoCambio();
		if (DescripcionUtils.isCampoDescripcionFechaIni(idCampo)) {
			checkFechas = true;
			isFechaIni = true; // hasFechaIni=true;
		} else if (DescripcionUtils.isCampoDescripcionFechaFin(idCampo)) {
			checkFechas = true; // hasFechaFin=true;
		}

		if (checkFechas) {
			if (!service.checkUpdateFechasElementos(vo, busqueda, isFechaIni)) {
				ActionErrors errores = obtenerErrores(request, true);
				if (isFechaIni) {
					errores.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									ErrorKeys.ERROR_DES_REEMPLAZO_FFIN_ELEM_MENOR_NUEVA_FINI));
				} else if (DescripcionUtils.isCampoDescripcionFechaIni(idCampo)) {
					errores.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									ErrorKeys.ERROR_DES_REEMPLAZO_FFIN_ELEM_MENOR_NUEVA_FINI));
				}

				return false;
			}

			if (!service.checkUpdateFechasSeries(vo, busqueda, isFechaIni)) {
				// ActionErrors errores=obtenerErrores(request, true);
				// if(isFechaIni){
				// errores.add(
				// ActionErrors.GLOBAL_MESSAGE,
				// new
				// ActionError(ErrorKeys.ERROR_DES_REEMPLAZO_FINI_SERIE_MAYOR_NUEVA_FINI));
				// }else
				// if(DescripcionUtils.isCampoDescripcionFechaIni(idCampo)){
				// errores.add(
				// ActionErrors.GLOBAL_MESSAGE,
				// new
				// ActionError(ErrorKeys.ERROR_DES_REEMPLAZO_FFIN_SERIE_MENOR_NUEVA_FFIN));
				// }
				request.setAttribute("seriesAfectadas", "1");
				// return true;
			}
		}
		return true;
	}

	public void removeNotSelectedNodesFromListInTemporalSession(
			HttpServletRequest request, String[] selectedIds,
			String keyInTemporalSession) {
		HashSet conjuntoIdsSeleccionados = new HashSet();
		for (int i = 0; i < selectedIds.length; i++)
			conjuntoIdsSeleccionados.add(selectedIds[i]);

		// Object obj=getFromTemporalSession(request, keyInTemporalSession);
		// List listaFullElementosVO=null;
		// if(obj instanceof PaginatedList)
		// listaFullElementosVO=((PaginatedList)obj).getList();
		// else
		// listaFullElementosVO=(List)obj;
		List listaFullElementosVO = getListFromSessionAttributeLISTA_ELEMENTOS_CF(request);

		// ArrayList listaOnlySelected=new ArrayList();
		for (Iterator it = listaFullElementosVO.iterator(); it.hasNext();) {
			ElementoCFPO elem = (ElementoCFPO) it.next();
			if (!conjuntoIdsSeleccionados.contains(elem.getId()))
				it.remove();
		}
		// setInTemporalSession(request, keyInTemporalSession,
		// listaOnlySelected);
	}

	public void refrescarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	public List getListFromSessionAttributeLISTA_ELEMENTOS_CF(
			HttpServletRequest request) {
		Object lista = getFromTemporalSession(request,
				FondosConstants.LISTA_ELEMENTOS_CF);
		List listaADevolver = null;
		if (lista instanceof PaginatedList) {
			listaADevolver = ((PaginatedList) lista).getList();
		} else if (lista instanceof List) {
			listaADevolver = ((List) lista);
		}
		return listaADevolver;

	}
}