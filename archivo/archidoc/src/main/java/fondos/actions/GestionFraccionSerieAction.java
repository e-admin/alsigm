package fondos.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.vos.RangoVO;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoErrorCodes;

import common.ConfigConstants;
import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.StringUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UDocEnUiDepositoVO;
import descripcion.vos.CampoFechaVO;
import descripcion.vos.FichaVO;
import fondos.FondosConstants;
import fondos.forms.DivisionFraccionSerieForm;
import fondos.model.SubtipoNivelCF;
import fondos.model.TipoNivelCF;
import fondos.model.UnidadDocumental;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;

public class GestionFraccionSerieAction extends BaseAction {

	public void establecerNivel(ActionForm form, HttpServletRequest request) {

		DivisionFraccionSerieForm udocForm = (DivisionFraccionSerieForm) form;

		String idNivelDocumental = udocForm.getIdNivelDocumental();

		if (StringUtils.isNotEmpty(idNivelDocumental)) {
			boolean isSubtipoCaja = false;
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();

			INivelCFVO nivelUnidadDocumental = cuadroBI
					.getNivelCF(idNivelDocumental);
			if (nivelUnidadDocumental != null) {
				if (nivelUnidadDocumental.getSubtipo() == SubtipoNivelCF.CAJA
						.getIdentificador())
					isSubtipoCaja = true;
			}

			if (isSubtipoCaja) {
				setInTemporalSession(request,
						FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(
								true));
				// setInTemporalSession(request,
				// FondosConstants.LISTA_RANGOS_UDOC, new ArrayList());
			} else {
				setInTemporalSession(request,
						FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(
								false));
			}
		} else {
			removeInTemporalSession(request,
					FondosConstants.BANDERA_MOSTRAR_RANGOS);
			removeInTemporalSession(request, FondosConstants.LISTA_RANGOS_UDOC);
		}
	}

	public void establecerNivelExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Determinamos si según el nivel deben aparecer rangos o número de
		// expediente
		establecerNivel(form, request);
		setReturnActionFordward(request,
				mapping.findForward("initDivision_fraccionSerie"));
	}

	public void cargarFichasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// UnidadDocumentalVO fraccionSerie =
		// (UnidadDocumentalVO)getFromTemporalSession(request,
		// FondosConstants.UDOC_A_DIVIDIR);
		UnidadDocumentalPO fraccionSerie = (UnidadDocumentalPO) getFromTemporalSession(
				request, FondosConstants.UDOC_A_DIVIDIR);
		DivisionFraccionSerieForm udocForm = (DivisionFraccionSerieForm) form;

		/*
		 * String idNivelDocumental = udocForm.getIdNivelDocumental();
		 * 
		 * if (StringUtils.isNotEmpty(idNivelDocumental)) { boolean
		 * isSubtipoCaja = false; ServiceRepository services =
		 * ServiceRepository.
		 * getInstance(ServiceClient.create(getAppUser(request)));
		 * GestionCuadroClasificacionBI cuadroBI =
		 * services.lookupGestionCuadroClasificacionBI();
		 * 
		 * NivelCFVO nivelUnidadDocumental =
		 * cuadroBI.getNivelCF(idNivelDocumental); if (nivelUnidadDocumental !=
		 * null) { if (nivelUnidadDocumental.getSubtipo() ==
		 * SubtipoNivelCF.CAJA.getIdentificador()) isSubtipoCaja = true; }
		 * 
		 * if (isSubtipoCaja) { setInTemporalSession(request,
		 * FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(true)); //
		 * setInTemporalSession(request, FondosConstants.LISTA_RANGOS_UDOC, new
		 * ArrayList()); } else { setInTemporalSession(request,
		 * FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(false)); } }
		 */

		// Determinamos si según el nivel deben aparecer rangos o número de
		// expediente
		establecerNivel(form, request);

		// Determinamos si es necesario cargar las fichas descriptivas al
		// cambiar el nivel seleccionado
		cargarFichas(fraccionSerie, udocForm, request);

		setReturnActionFordward(request,
				mapping.findForward("initDivision_fraccionSerie"));
	}

	// public void cargarFichas (UnidadDocumentalVO fraccionSerie,
	// UnidadDocumentalForm udocForm, HttpServletRequest request) {
	public void cargarFichas(UnidadDocumentalVO fraccionSerie,
			DivisionFraccionSerieForm udocForm, HttpServletRequest request) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		ArrayList fichas = new ArrayList();
		SerieVO serieVO = null;
		boolean necesarioNivel = false;
		String fichaDescripcionUdoc = null, idNivelDocumental = null, idSerieDestino = null;

		idNivelDocumental = udocForm.getIdNivelDocumental();

		// Si existe más de un nivel documental, la lista de niveles estará en
		// sesión y por tanto será necesario que se haya seleccionado un nivel
		// en el formulario para poder cargar las fichas correspondientes al
		// mismo. Si sólo hay un nivel definido, se coje por defecto el de
		// unidad documental
		// de subtipo simple
		ArrayList listaNiveles = (ArrayList) getFromTemporalSession(request,
				TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY);
		if (listaNiveles != null && listaNiveles.size() > 1)
			necesarioNivel = true;

		idSerieDestino = fraccionSerie.getIdSerie();

		if (StringUtils.isNotEmpty(idSerieDestino)
				&& (!necesarioNivel || StringUtils
						.isNotEmpty(idNivelDocumental))) {

			// Obtenemos la Serie de destino
			serieVO = seriesBI.getSerie(idSerieDestino);

			// Por defecto, se da de alta un nivel de tipo unidad documental y
			// subtipo unidad documental simple
			if (StringUtils.isEmpty(idNivelDocumental))
				idNivelDocumental = cuadroBI.getNivelCF(
						TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
						SubtipoNivelCF.UDOCSIMPLE.getIdentificador()).getId();

			// Obtenemos el nivel cuyo identificador es el indicado y a partir
			// del mismo obtenemos el identificador de su ficha preferente
			INivelCFVO nivelUnidadDocumental = cuadroBI
					.getNivelCF(idNivelDocumental);

			String fichaDescripcionPrefUDoc = serieVO
					.getIdFichaDescrPrefUdoc(nivelUnidadDocumental.getId());

			// Si la serie tiene definidos listas de volúmenes y ficha
			// preferente para las u.docs., usar estas, sino, las del nivel
			if (fichaDescripcionPrefUDoc != null)
				fichaDescripcionUdoc = fichaDescripcionPrefUDoc;
			else
				fichaDescripcionUdoc = nivelUnidadDocumental
						.getIdFichaDescrPref();

			FichaVO ficha = null;
			HashMap todasFichas = (HashMap) getFromTemporalSession(request,
					TransferenciasConstants.MAP_TODAS_FICHAS_KEY);
			if (todasFichas == null || todasFichas.isEmpty()) {
				List listaTodasFichas = descripcionBI.getFichas();
				if (listaTodasFichas != null && listaTodasFichas.size() > 0) {
					Iterator it = listaTodasFichas.iterator();
					todasFichas = new HashMap();
					while (it.hasNext()) {
						FichaVO fichaVO = (FichaVO) it.next();
						todasFichas.put(fichaVO.getId(), fichaVO);
					}
				}
				setInTemporalSession(request,
						TransferenciasConstants.MAP_TODAS_FICHAS_KEY,
						todasFichas);
			}

			if (todasFichas != null && !todasFichas.isEmpty()) {
				ficha = (FichaVO) todasFichas.get(fichaDescripcionUdoc);
				fichas.add(ficha);
			}
		}

		setInTemporalSession(request, TransferenciasConstants.LISTA_FICHAS_KEY,
				fichas);
	}

	public void divideExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();
		GestionDescripcionBI descBI = services.lookupGestionDescripcionBI();
		GestionSolicitudesBI solicitudesBI = services
				.lookupGestionSolicitudesBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		ClientInvocation invocation = null;

		// Obtenemos el formulario
		DivisionFraccionSerieForm udocForm = (DivisionFraccionSerieForm) form;

		/*
		 * String pUdocID = request.getParameter(Constants.ID);
		 * UnidadDocumentalVO udoc = udocBI.getUnidadDocumental(pUdocID);
		 */

		UnidadDocumentalPO udoc = (UnidadDocumentalPO) getFromTemporalSession(
				request, FondosConstants.UDOC_KEY);

		try {
			DivisionFraccionSerieVO divisionFSVO = fraccionSerieBI
					.searchDivisionFS(udoc.getId());

			// Si la fracción de serie aún no se ha comenzado a dividir, vamos a
			// la pantalla de inicialización
			if (divisionFSVO == null) {

				// Comprobamos que la fracción de serie no esté en ningún
				// préstamo ni en ninguna consulta que no esté devuelta
				// if (solicitudesBI.tieneDetallesNoDevueltos(pUdocID)) {
				if (solicitudesBI.tieneDetallesNoDevueltos(udoc.getId())) {
					throw new ActionNotAllowedException(
							null,
							ArchivoErrorCodes.ERROR_FRACCION_SERIE_NO_DIVISIBLE_XPRESTAMO_CONSULTA,
							ArchivoModules.FONDOS_MODULE);
				} else {
					// Guardar el enlace a la página
					invocation = saveCurrentInvocation(
							KeysClientsInvocations.CUADRO_DIVIDIR_FRACCION_SERIE,
							request);

					// Limpiamos de la sesión los posibles valores que puedan
					// quedar de otra operación anteriro
					removeInTemporalSession(request,
							FondosConstants.UDOCS_FRACCION_SERIE);

					// Almacenamos en sesión los datos de la unidad documental
					// que vamos a dividir
					setInTemporalSession(request,
							FondosConstants.UDOC_A_DIVIDIR, udoc);

					// Asignamos valores por defecto a los campos del formulario
					udocForm.setAsunto(udoc.getTitulo());
					udocForm.setNumExp(udoc.getNumExp());

					// Obtenemos las fechas Inicial y final del elemento
					// List fechasIni = descBI.getFechaElemento(pUdocID,
					// csa.getConfiguracionDescripcion().getFechaExtremaInicial());
					List fechasIni = descBI.getFechaElemento(udoc.getId(), csa
							.getConfiguracionDescripcion()
							.getFechaExtremaInicial());
					if (fechasIni != null && fechasIni.size() > 0) {
						CampoFechaVO campoFechaIni = (CampoFechaVO) fechasIni
								.get(0);
						udocForm.setFechaInicio(DateUtils
								.formatDate(campoFechaIni.getFechaIni()));
					}

					// List fechasFin = descBI.getFechaElemento(pUdocID,
					// csa.getConfiguracionDescripcion().getFechaExtremaFinal());
					List fechasFin = descBI.getFechaElemento(udoc.getId(), csa
							.getConfiguracionDescripcion()
							.getFechaExtremaFinal());

					if (fechasFin != null && fechasFin.size() > 0) {
						CampoFechaVO campoFechaFin = (CampoFechaVO) fechasFin
								.get(0);
						udocForm.setFechaFin(DateUtils.formatDate(campoFechaFin
								.getFechaIni()));
					}

					// Obtener los niveles documentales
					List nivelesDocumentales = cuadroBI.getNivelesByTipo(
							TipoNivelCF.UNIDAD_DOCUMENTAL, null);

					// Si sólo hay un nivel, dejamos que la aplicación siga
					// funcionando como hasta ahora
					if (nivelesDocumentales != null
							&& nivelesDocumentales.size() > 1) {
						setInTemporalSession(
								request,
								TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY,
								nivelesDocumentales);
					} else {
						if (ConfigConstants.getInstance()
								.getPermitirFichaAltaRelacion()) {
							cargarFichas(udoc, udocForm, request);
						}
					}

					setReturnActionFordward(request,
							mappings.findForward("initDivision_fraccionSerie"));

					// Marcar esta página como punto de retorno
					invocation.setAsReturnPoint(true);
				}
			}
			// Sino, vamos a la pantalla de añadir/eliminar unidades
			// documentales a división de fracción de serie
			else {

				// Guardar el enlace a la página
				invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_DATOS_DIVISION_FRACCION_SERIE,
						request);

				verDivisionFraccionSerie(mappings, request, services,
						fraccionSerieBI, divisionFSVO, udoc);
			}

			// Marcar esta página como punto de retorno
			// invocation.setAsReturnPoint(true);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void crearUDocsFraccionSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		boolean necesarioNivel = false, isSubtipoCaja = false;
		String idNivelDocumental = null, fichaDescripcionUdoc = null;
		List rangos = null;
		ActionErrors errors = null;

		DivisionFraccionSerieForm frm = (DivisionFraccionSerieForm) form;

		// Añadida condición de existencia de división de fracción de serie en
		// sesión por posible recarga de la página e intento de crear de nuevo
		// la fracción de serie
		// if (getFromTemporalSession(request,
		// FondosConstants.DIVISION_FRACCION_SERIE) == null) {

		ArrayList listaNiveles = (ArrayList) getFromTemporalSession(request,
				TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY);
		// Si existe más de un nivel documental, la lista de niveles estará en
		// sesión y por tanto será necesario que se haya seleccionado un nivel
		// en el formulario para poder cargar las fichas correspondientes al
		// mismo. Si sólo hay un nivel definido, se coje por defecto el de
		// unidad documental
		// de subtipo simple
		if (listaNiveles != null && listaNiveles.size() > 1)
			necesarioNivel = true;
		if (StringUtils.isNotEmpty(frm.getIdNivelDocumental())) {
			INivelCFVO nivelCF = cuadroBI
					.getNivelCF(frm.getIdNivelDocumental());
			if (nivelCF != null
					&& nivelCF.getSubtipo() == SubtipoNivelCF.CAJA
							.getIdentificador()) {
				isSubtipoCaja = true;
				rangos = obtenerRangos(request.getParameterValues("campo_201"),
						request.getParameterValues("campo_202"));
				errors = validate(rangos);
			}
		}

		errors = validate(frm, necesarioNivel, errors);

		if (errors.isEmpty()) {

			// Extraemos de la pila la invocación anterior que es dividir
			// fracción de serie y guardamos la de los datos de la división ya
			// creada
			popLastInvocation(request);
			// saveCurrentInvocation(KeysClientsInvocations.CUADRO_DATOS_DIVISION_FRACCION_SERIE,
			// request);

			UnidadDocumentalPO udoc = (UnidadDocumentalPO) getFromTemporalSession(
					request, FondosConstants.UDOC_A_DIVIDIR);

			// Obtenemos el formulario
			DivisionFraccionSerieForm udocForm = (DivisionFraccionSerieForm) form;

			// Integer nofUdocs = new Integer(0);
			idNivelDocumental = udocForm.getIdNivelDocumental();

			// Por defecto, se da de alta un nivel de tipo unidad documental y
			// subtipo unidad documental simple
			if (StringUtils.isEmpty(idNivelDocumental))
				idNivelDocumental = cuadroBI.getNivelCF(
						TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
						SubtipoNivelCF.UDOCSIMPLE.getIdentificador()).getId();

			fichaDescripcionUdoc = udocForm.getIdFicha();

			// Obtener el identificador de la fracción de serie en relación de
			// entrega
			List fsEnUIDeposito = depositoBI.getUDocsById(new String[] { udoc
					.getId() });
			String idFSEnREntrega = ((UDocEnUiDepositoVO) fsEnUIDeposito.get(0))
					.getIdudocre();
			String idUIDeposito = ((UDocEnUiDepositoVO) fsEnUIDeposito.get(0))
					.getIduinstalacion();

			// Dar de alta una nueva división de fracción de serie
			DivisionFraccionSerieVO divisionFSVO = new DivisionFraccionSerieVO();
			divisionFSVO.setIdFS(udoc.getId());
			divisionFSVO.setIdFicha(fichaDescripcionUdoc);
			divisionFSVO.setIdNivelDocumental(idNivelDocumental);
			divisionFSVO.setIdFSEnREntrega(idFSEnREntrega);
			divisionFSVO.setIdUIDeposito(idUIDeposito);
			divisionFSVO.setNombreArchivo(udoc.getArchivo().getNombre());
			divisionFSVO.setTituloSerie(udoc.getSerie().getTitulo());

			// divisionFSVO.setOrganoProductor(organoProductorVO);

			// Creamos la división de fracción de serie
			List udocsEnFraccionSerie = fraccionSerieBI.createDivisionFS(
					divisionFSVO, udoc, udocForm, fraccionSerieBI, rangos);

			DivisionFSToPOTransformer transformer = new DivisionFSToPOTransformer(
					services);
			DivisionFraccionSeriePO divisionFSPO = (DivisionFraccionSeriePO) transformer
					.transform(divisionFSVO);
			divisionFSPO.setFraccionSeriePO(udoc);

			setInTemporalSession(request,
					FondosConstants.DIVISION_FRACCION_SERIE, divisionFSPO);

			// List udocsEnFraccionSerie = initListaUDocsFraccionSerie(request,
			// divisionFSPO, udocForm, nofUdocs.intValue(), fraccionSerieBI,
			// rangos);

			CollectionUtils.transform(udocsEnFraccionSerie,
					UDocEnFraccionSerieToPO.getInstance(services));

			setInTemporalSession(request, FondosConstants.UDOCS_FRACCION_SERIE,
					udocsEnFraccionSerie);
			if (isSubtipoCaja)
				setInTemporalSession(request,
						FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(
								true));
			else
				setInTemporalSession(request,
						FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(
								false));

			ActionRedirect redirectAVistaDivision = new ActionRedirect(
					mappings.findForward("redirect_to_dividir_fraccionSerie"));
			redirectAVistaDivision.addParameter("iddivisionfsseleccionada",
					divisionFSVO.getIdFS());
			redirectAVistaDivision.setRedirect(true);
			setReturnActionFordward(request, redirectAVistaDivision);
		} else {
			if (isSubtipoCaja)
				setInTemporalSession(request,
						FondosConstants.LISTA_RANGOS_UDOC, rangos);
			obtenerErrores(request, true).add(errors);
			setReturnActionFordward(request,
					mappings.findForward("initDivision_fraccionSerie"));
		}
		/*
		 * } else { setReturnActionFordward(request,
		 * mappings.findForward("dividir_fraccionSerie")); }
		 */
	}

	public ActionErrors validate(List rangos) {

		ActionErrors errors = new ActionErrors();

		// Validar la introducción de rangos de expedientes
		if (rangos != null && rangos.size() > 0) {
			Iterator it = rangos.iterator();
			while (it.hasNext()) {
				RangoVO rango = (RangoVO) it.next();
				if (GenericValidator.isBlankOrNull(rango.getDesde())) {
					errors.add(
							TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_INICIAL,
							new ActionError(
									TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_INICIAL));
					break;
				}
				if (GenericValidator.isBlankOrNull(rango.getHasta())) {
					errors.add(
							TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_FINAL,
							new ActionError(
									TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_FINAL));
					break;
				}
			}
		}

		return errors;
	}

	public ActionErrors validate(DivisionFraccionSerieForm frm,
			boolean necesarioNivel, ActionErrors errors) {
		if (errors == null)
			errors = new ActionErrors();

		// Validar que el asunto no pueda ser vacío
		if (StringUtils.isEmpty(frm.getNofUDocs())) {
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ETIQUETA_NOF_UDOCS_EN_FRACCION_SERIE));
		}

		if (GenericValidator.isBlankOrNull(frm.getAsunto()))
			errors.add(Constants.ERROR_REQUIRED, new ActionError(
					Constants.ETIQUETA_TITULO_UDOC_FRACCION_SERIE));

		String fechaFinS = null, fechaInicioS = null;
		Date fechaInicio = null;
		try {
			fechaInicioS = frm.getFechaInicio();
			if (StringUtils.isEmpty(fechaInicioS)) {
				errors.add(Constants.ERROR_REQUIRED, new ActionError(
						Constants.ETIQUETA_FECHA_INICIO_UDOC_FRACCION_SERIE));
			} else {
				fechaInicio = DateUtils.getDate(fechaInicioS);
				if (!DateUtils.isDate(fechaInicioS)) {
					errors.add(
							Constants.ERROR_DATE,
							new ActionError(
									Constants.ETIQUETA_FECHA_INICIO_UDOC_FRACCION_SERIE));
				}
			}
		} catch (Exception e) {
			errors.add(Constants.ERROR_DATE, new ActionError(
					Constants.ETIQUETA_FECHA_INICIO_UDOC_FRACCION_SERIE));
		}

		Date fechaFin = null;
		try {
			fechaFinS = frm.getFechaFin();
			if (StringUtils.isEmpty(fechaFinS)) {
				errors.add(Constants.ERROR_REQUIRED, new ActionError(
						Constants.ETIQUETA_FECHA_FIN_UDOC_FRACCION_SERIE));
			} else {
				fechaFin = DateUtils.getDate(fechaFinS);
				if (!DateUtils.isDate(fechaFinS)) {
					errors.add(Constants.ERROR_DATE, new ActionError(
							Constants.ETIQUETA_FECHA_FIN_UDOC_FRACCION_SERIE));
				}
			}
		} catch (Exception e) {
			errors.add(Constants.ERROR_DATE, new ActionError(
					Constants.ETIQUETA_FECHA_FIN_UDOC_FRACCION_SERIE));
		}

		if (fechaFin != null) {
			if (fechaFin.compareTo(new Date()) > 0)
				errors.add(Constants.ERROR_DATE_AFTER_TODAY, new ActionError(
						Constants.ERROR_DATE_AFTER_TODAY_FRACCION_SERIE));
			if (fechaInicio != null && fechaInicio.compareTo(fechaFin) > 0) {
				errors.add(
						Constants.ERROR_INITDATE_AFTER_ENDDATE,
						new ActionError(
								Constants.ERROR_INITDATE_AFTER_ENDDATE_FRACCION_SERIE));
			}
		}

		if (necesarioNivel) {
			if (StringUtils.isEmpty(frm.getIdNivelDocumental()))
				errors.add(Constants.ERROR_REQUIRED, new ActionError(
						Constants.ERROR_NIVEL_REQUERIDO));
		}

		return errors;
	}

	public void saveInfoUDocsEnFraccionSerieExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		DivisionFraccionSeriePO divisionFS = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);
		List udocsEnFraccionSerie = (List) getFromTemporalSession(request,
				FondosConstants.UDOCS_FRACCION_SERIE);
		UnidadDocumental udoc = (UnidadDocumental) divisionFS
				.getFraccionSerie();

		GestionFraccionSerieBI gestionFraccionSerieBI = services
				.lookupGestionFraccionSerieBI();
		try {
			gestionFraccionSerieBI.createUDocsFraccionSerie(divisionFS, udoc,
					udocsEnFraccionSerie);
		} catch (SecurityException e) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									FondosConstants.ERROR_VALIDACION_UDOCS_SIN_PERMISOS));
		}

		// ClientInvocation
		// cli=getInvocationStack(request).getLastReturnPoint(request);
		// setReturnActionFordward(request,new
		// ActionForward(cli.getInvocationURI(), true));

		goReturnPointExecuteLogic(mappings, form, request, response);
	}

	public List obtenerRangos(String[] rangosIniciales, String[] rangosFinales) {
		List rangos = new ArrayList();

		if (rangosIniciales != null && rangosFinales != null) {
			for (int i = 0; i < rangosIniciales.length; i++) {
				rangos.add(new RangoVO(rangosIniciales[i], rangosFinales[i]));
			}
		}

		return rangos;
	}

	// private List initListaUDocsFraccionSerie (HttpServletRequest request,
	// UnidadDocumentalPO udoc, int nofUdocs)
	// private List initListaUDocsFraccionSerie (HttpServletRequest request,
	// UnidadDocumental udoc, int nofUdocs)
	/*
	 * private List initListaUDocsFraccionSerie (HttpServletRequest request,
	 * //UnidadDocumental udoc, DivisionFraccionSeriePO divisionFSPO,
	 * DivisionFraccionSerieForm udocForm, int nofUdocs, GestionFraccionSerieBI
	 * fraccionSerieBI, List rangos) { List ret = new ArrayList(); int posIni =
	 * 1; GestorEstructuraDepositoBI depositoBI =
	 * getGestionDespositoBI(request); String [] listaUnIdUDoc = new String
	 * []{divisionFSPO.getFraccionSerie().getId()}; String signaturaCaja =
	 * Constants.STRING_EMPTY; // List rangos = null;
	 * 
	 * List udocsEnDeposito = depositoBI.getUDocsById(listaUnIdUDoc); if
	 * (udocsEnDeposito != null && udocsEnDeposito.size()>0) {
	 * UDocEnUiDepositoVO udocEnUIVO =
	 * (UDocEnUiDepositoVO)udocsEnDeposito.get(0); posIni =
	 * udocEnUIVO.getPosudocenui(); UInsDepositoVO uInsDepositoVO =
	 * depositoBI.getUinsEnDeposito(udocEnUIVO.getIduinstalacion());
	 * signaturaCaja = uInsDepositoVO.getSignaturaui(); }
	 * 
	 * posIni = posIni == 0 ? 1 : posIni;
	 * 
	 * if (nofUdocs > 0) { for(int i=0; i<nofUdocs; i++) { UDocEnFraccionSerieVO
	 * udocEnFraccionSerieVO = new UDocEnFraccionSerieVO(); String
	 * nuevaSignatura = Constants.STRING_EMPTY;
	 * 
	 * // udocEnFraccionSerieVO.setIdUDoc(new Integer(i+1).toString());
	 * 
	 * udocEnFraccionSerieVO.setOrden(i+1);
	 * udocEnFraccionSerieVO.setIdFS(divisionFSPO.getFraccionSerie().getId());
	 * udocEnFraccionSerieVO.setAsunto(udocForm.getAsunto());
	 * udocEnFraccionSerieVO
	 * .setFechaExtIni(DateUtils.getDate(udocForm.getFechaInicio()));
	 * udocEnFraccionSerieVO
	 * .setFechaExtFin(DateUtils.getDate(udocForm.getFechaFin()));
	 * 
	 * if (divisionFSPO.getNivelDocumental() != null &&
	 * divisionFSPO.getNivelDocumental().getSubtipo() ==
	 * ElementoCuadroClasificacion.SUBTIPO_CAJA) { if (rangos != null &&
	 * rangos.size() > 0) { Iterator it = rangos.iterator(); while
	 * (it.hasNext()) { RangoVO rango = (RangoVO)it.next();
	 * udocEnFraccionSerieVO.addRango(rango); } } } else {
	 * udocEnFraccionSerieVO.setNumExp(udocForm.getNumExp()); }
	 * 
	 * nuevaSignatura = Constants.getSignaturaUnidadDocumental(signaturaCaja ,
	 * (new Integer(posIni)).toString());
	 * 
	 * udocEnFraccionSerieVO.setSignatura(nuevaSignatura); //
	 * udocEnFraccionSerieVO.setOrganoProductor(divisionFSPO.getProductor());
	 * 
	 * posIni = posIni + 1;
	 * 
	 * /*String nuevaSignatura = udoc.getCodigo(); String [] idUdoc = new
	 * String[] {udoc.getId()}; String [] signaturaUdoc = new String[]
	 * {udoc.getCodigo()}; List udocEnDeposito = depositoBI.getUDocsById(idUdoc,
	 * signaturaUdoc); int maxPos = 0;
	 * 
	 * if (udocEnDeposito != null && udocEnDeposito.size()>0) {
	 * UDocEnUiDepositoVO udocEnUIDeposito =
	 * (UDocEnUiDepositoVO)udocEnDeposito.get(0); if (udocEnUIDeposito != null)
	 * { UInsDepositoVO uIns =
	 * depositoBI.getUinsEnDeposito(udocEnUIDeposito.getIduinstalacion());
	 * 
	 * List udocsEnUI = depositoBI.getUDocsEnUInstalacion(uIns.getId());
	 * 
	 * if (udocsEnUI != null) { Iterator it = udocsEnUI.iterator();
	 * 
	 * while (it.hasNext()) { UDocEnUiDepositoVO udui =
	 * (UDocEnUiDepositoVO)it.next(); if (udui.getPosudocenui() > maxPos) maxPos
	 * = udui.getPosudocenui(); } int nofUDocsEnUIInt = new
	 * Integer(maxPos+1).intValue();
	 * 
	 * nuevaSignatura =
	 * udoc.getCodigo().substring(0,udoc.getCodigo().indexOf(Constants
	 * .SEPARADOR_PARTES_UNIDAD_INSTALACION))
	 * +Constants.SEPARADOR_PARTES_UNIDAD_INSTALACION+(nofUDocsEnUIInt+1);
	 * 
	 * } } }
	 * 
	 * udocEnFraccionSerieVO.setSignatura(nuevaSignatura);
	 */

	/*
	 * fraccionSerieBI.addUDocToDivisionFS(udocEnFraccionSerieVO, divisionFSPO,
	 * false); ret.add(udocEnFraccionSerieVO); } }
	 * 
	 * return ret; }
	 */

	public void listadodivisionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_LISTADO_DIVISIONES_FRACCIONSERIE,
				request);
		invocation.setAsReturnPoint(true);

		// try {
		List divisionesFS = fraccionSerieBI.getDivisionesFSEnElaboracion();

		CollectionUtils.transform(divisionesFS,
				DivisionFSToPOTransformer.getInstance(services));

		request.setAttribute(FondosConstants.LISTA_DIVISIONESFS_KEY,
				divisionesFS);
		request.setAttribute(FondosConstants.SON_DIVISIONESFS_FINALIZADAS,
				new Boolean(false));
		setReturnActionFordward(request,
				mappings.findForward("listado_divisionesFraccionSerie"));

		/*
		 * 
		 * } catch (ActionNotAllowedException anae) { guardarError(request,
		 * anae); goLastClientExecuteLogic(mappings, form, request, response); }
		 */
	}

	public void listadodivisionesFinalizadasExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		// try {
		List divisionesFS = fraccionSerieBI.getDivisionesFSFinalizadas();

		CollectionUtils.transform(divisionesFS,
				DivisionFSToPOTransformer.getInstance(services));

		request.setAttribute(FondosConstants.LISTA_DIVISIONESFS_KEY,
				divisionesFS);
		request.setAttribute(FondosConstants.SON_DIVISIONESFS_FINALIZADAS,
				new Boolean(true));
		setReturnActionFordward(request,
				mappings.findForward("listado_divisionesFraccionSerie"));

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_LISTADO_DIVISIONES_FRACCIONSERIE,
				request);
		invocation.setAsReturnPoint(true);

		/*
		 * } catch (ActionNotAllowedException anae) { guardarError(request,
		 * anae); goLastClientExecuteLogic(mappings, form, request, response); }
		 */
	}

	public void verDivisionFraccionSerie(ActionMapping mappings,
			HttpServletRequest request, ServiceRepository services,
			GestionFraccionSerieBI fraccionSerieBI,
			DivisionFraccionSerieVO divisionFSVO, UnidadDocumentalPO udocPO) {

		DivisionFSToPOTransformer transformer = new DivisionFSToPOTransformer(
				services);
		DivisionFraccionSeriePO divisionFSPO = (DivisionFraccionSeriePO) transformer
				.transform(divisionFSVO);
		divisionFSPO.setFraccionSerie(udocPO);

		boolean tieneDescripcion = StringUtils.isEmpty(divisionFSPO
				.getIdFicha()) ? false : true;
		boolean isSubtipoCaja = divisionFSPO.getNivelDocumental().getSubtipo() == SubtipoNivelCF.CAJA
				.getIdentificador();

		List udocsEnFraccionSerie = fraccionSerieBI.getUDocsEnDivisionFS(
				divisionFSVO.getIdFS(), tieneDescripcion);

		CollectionUtils.transform(udocsEnFraccionSerie,
				UDocEnFraccionSerieToPO.getInstance(services));

		if (isSubtipoCaja)
			setInTemporalSession(request,
					FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(true));
		else
			setInTemporalSession(request,
					FondosConstants.BANDERA_MOSTRAR_RANGOS, new Boolean(false));

		// Almacenamos en sesión los datos de la división y las unidades
		// documentales que contiene hasta el momento
		setInTemporalSession(request, FondosConstants.DIVISION_FRACCION_SERIE,
				divisionFSPO);
		setInTemporalSession(request, FondosConstants.UDOCS_FRACCION_SERIE,
				udocsEnFraccionSerie);

		// Eliminamos de sesión los valores innecesarios y que pueden estar de
		// una ejecución anterior
		removeInTemporalSession(request,
				FondosConstants.UNIDAD_DOCUMENTAL_EN_FS);
		removeInTemporalSession(request, FondosConstants.LISTA_RANGOS_UDOC);

		setReturnActionFordward(request,
				mappings.findForward("dividir_fraccionSerie"));

	}

	public void verDivisionFraccionSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		String idDivisionFS = request.getParameter("iddivisionfsseleccionada");
		DivisionFraccionSerieForm frm = (DivisionFraccionSerieForm) form;
		if (StringUtils.isNotEmpty(idDivisionFS))
			frm.setIddivisionfsseleccionada(idDivisionFS);
		else
			idDivisionFS = frm.getIddivisionfsseleccionada();

		// Guardar el enlace a la página
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_DATOS_DIVISION_FRACCION_SERIE,
				request);

		/*
		 * DivisionFraccionSerieVO divisionFSVO =
		 * (DivisionFraccionSerieVO)getFromTemporalSession(request,
		 * FondosConstants.DIVISION_FRACCION_SERIE); if (divisionFSVO == null ||
		 * ( StringUtils.isNotEmpty(idDivisionFS) &&
		 * !divisionFSVO.getIdFS().equals(idDivisionFS)) ) {
		 */
		DivisionFraccionSerieVO divisionFSVO = fraccionSerieBI
				.searchDivisionFS(idDivisionFS);
		verDivisionFraccionSerie(mappings, request, services, fraccionSerieBI,
				divisionFSVO, null);
		/*
		 * } else setReturnActionFordward(request,
		 * mappings.findForward("dividir_fraccionSerie"));
		 */

	}

	public void eliminardivisionesfsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DivisionFraccionSerieForm frm = (DivisionFraccionSerieForm) form;
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionFraccionSerieBI fraccionSerieBI = services
					.lookupGestionFraccionSerieBI();
			fraccionSerieBI.deleteFraccionesSerie(frm
					.getDivisionesseleccionadas());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}
}