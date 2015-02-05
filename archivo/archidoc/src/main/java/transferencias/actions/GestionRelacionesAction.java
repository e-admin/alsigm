package transferencias.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import auditoria.ArchivoErrorCodes;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.EstadoCotejo;
import transferencias.ReservaPrevision;
import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.exceptions.RelacionEntregaConUDocsSinAsingarAUIException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasFSException;
import transferencias.exceptions.RelacionEntregaNoEnviableUIsConVariasUDocsException;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import transferencias.forms.RelacionForm;
import transferencias.model.EstadoREntrega;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.PrevisionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionReeaVO;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ConfiguracionTransferencias;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.SecurityException;
import common.exceptions.TooManyResultsException;
import common.lock.exceptions.LockerException;
import common.lock.model.Locker;
import common.lock.model.LockerObjectTypes;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.IntervalOptions;
import common.util.ListUtils;
import common.util.TypeConverter;
import common.view.ErrorKeys;

import deposito.db.ConcurrentModificationException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.UDocEnUiDepositoVO;
import descripcion.model.xml.definition.DefCampoDato;
import descripcion.model.xml.definition.DefCampoEspecial;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.FichaVO;
import fondos.FondosConstants;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.SubtipoNivelCF;
import fondos.model.TipoNivelCF;
import fondos.vos.INivelCFVO;
import fondos.vos.SerieVO;

/**
 * Action que lleva a cabo las diferentes acciones que pueden ser realizadas
 * sobre relaciones de entrega
 *
 */
public class GestionRelacionesAction extends BaseAction {

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(GestionRelacionesAction.class);

	protected void listadorelacionesarchivoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		try {
			Collection relaciones = relacionesService.getRelacionesAGestionar();

			CollectionUtils.transform(relaciones,
					RelacionToPO.getInstance(services));

			request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
					relaciones);

			boolean mostrarEliminar = false;
			for (Iterator iter = relaciones.iterator(); iter.hasNext();) {
				RelacionEntregaPO relacion = (RelacionEntregaPO) iter.next();
				if (relacion != null && relacion.getPuedeSerEliminada()) {
					mostrarEliminar = true;
					break;
				}
			}
			if (mostrarEliminar) {
				request.setAttribute(
						TransferenciasConstants.EXISTEN_RELACIONES_A_ELIMINAR,
						new Boolean(true));
			}

			setReturnActionFordward(request,
					mapping.findForward("listado_relacion_archivo"));

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_LISTADO_RELACIONES_ARCHIVO,
					request);
			invocation.setAsReturnPoint(true);

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	protected void listadorelacionesFinalizadasExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		Collection relaciones = relacionesService
				.getRelacionesFinalizadasXUser(appUser.getId());
		CollectionUtils.transform(relaciones,
				RelacionToPO.getInstance(services));

		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relaciones);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_LISTADO_RELACIONES_OFICINA,
				request);
		invocation.setAsReturnPoint(true);

		request.setAttribute(
				TransferenciasConstants.SON_RELACIONES_FINALIZADAS,
				new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("listado_relacion"));
	}

	protected void listadoRelacionesRechazadasExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		Collection relaciones = relacionesService
				.getRelacionesRechazadasXUser(appUser.getId());
		CollectionUtils.transform(relaciones,
				RelacionToPO.getInstance(services));

		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relaciones);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_LISTADO_RELACIONES_OFICINA,
				request);
		invocation.setAsReturnPoint(true);

		request.setAttribute(TransferenciasConstants.SON_RELACIONES_RECHAZADAS,
				new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("listado_relacion"));
	}

	protected void listadoingresosFinalizadosExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		Collection ingresos = relacionesService
				.getIngresosFinalizadosXUser(appUser.getId());
		CollectionUtils.transform(ingresos, RelacionToPO.getInstance(services));

		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				ingresos);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_LISTADO_INGRESOS_DIRECTOS,
				request);
		invocation.setAsReturnPoint(true);

		request.setAttribute(
				TransferenciasConstants.SON_RELACIONES_FINALIZADAS,
				new Boolean(true));

		setReturnActionFordward(request,
				mapping.findForward("listado_ingresos"));
	}

	protected void listadorelacionesoficinaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		Collection relaciones = relacionesService
				.getRelacionesEnElaboracionXUser(appUser.getId());
		CollectionUtils.transform(relaciones,
				RelacionToPO.getInstance(services));

		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relaciones);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_LISTADO_RELACIONES_OFICINA,
				request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mapping.findForward("listado_relacion"));
	}

	protected void listadoingresosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		Collection ingresos = relacionesService
				.getIngresosEnElaboracionXUser(appUser.getId());
		CollectionUtils.transform(ingresos, RelacionToPO.getInstance(services));

		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				ingresos);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_LISTADO_INGRESOS_DIRECTOS,
				request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mapping.findForward("listado_ingresos"));
	}

	public void verPosiblesProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		String idSerie = ((RelacionForm) form).getIdseriemostrarproductores();
		GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
		List posiblesProductores = seriesBI.getProductoresSerie(idSerie, true);
		request.setAttribute(TransferenciasConstants.LISTA_PRODUCTORES,
				posiblesProductores);

		/*
		 * ClientInvocation invocation =
		 * saveCurrentInvocation(KeysClientsInvocations
		 * .TRANSFERENCIAS_ALTA_DETALLE_PREVISION, request);
		 * invocation.setAsReturnPoint(true);
		 * setInTemporalSession(request,TransferenciasConstants
		 * .LISTA_FORMATOS_KEY, depositoBI.getFormatos());
		 */
		setReturnActionFordward(request, mappings.findForward("nueva_relacion"));
		/*
		 * saveCurrentInvocation(KeysClientsInvocations.
		 * TRANSFERENCIAS_NUEVA_UNIDAD_DOCUMENTAL, request);
		 * setReturnActionFordward(request,
		 * mappings.findForward("edicion_udoc_relacion"));
		 */

	}

	public void cargarFichasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		cargarFichas(form, request);
		setReturnActionFordward(request, mapping.findForward("nueva_relacion"));
	}

	public void cargarFichas(ActionForm form, HttpServletRequest request) {

		RelacionForm relacionForm = (RelacionForm) form;
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
		String fichaDescripcionUdoc = null, idNivelDocumental = null, idSerieDestino = null, idDetalleSeleccionado = null;

		idNivelDocumental = relacionForm.getIdNivelDocumental();

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

		PrevisionPO previsionPO = (PrevisionPO) getFromTemporalSession(request,
				TransferenciasConstants.PREVISION_KEY);
		if (previsionPO != null && previsionPO.isDetallada()) {
			idDetalleSeleccionado = relacionForm.getIddetalleseleccionado();
			if (StringUtils.isNotEmpty(idDetalleSeleccionado)) {
				Collection detallesPrevision = (Collection) getFromTemporalSession(
						request, TransferenciasConstants.DETALLEPREVISION_KEY);
				if (detallesPrevision != null) {
					Iterator it = detallesPrevision.iterator();
					while (it.hasNext()) {
						DetallePrevisionVO detalle = (DetallePrevisionVO) it
								.next();
						if (detalle.getId().equals(idDetalleSeleccionado)) {
							idSerieDestino = detalle.getIdSerieDestino();
							break;
						}
					}
				}
			}
		} else {
			idSerieDestino = relacionForm.getIdserieseleccionada();
		}

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

	protected void nuevaExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RelacionForm frm = (RelacionForm) form;

		boolean isEntreArchivos = false;

		if (!StringUtils.isEmpty(frm.getIdprevisionseleccionada())) {
			String idPrevisionSeleccionada = frm.getIdprevisionseleccionada();
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionPrevisionesBI previsionesService = services
					.lookupGestionPrevisionesBI();
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();

			ConfiguracionTransferencias config = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionTransferencias();
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			List formasDocumentales = descripcionBI.getValoresValidacion(config
					.getIdTablaValidacionFormaDocumental());

			// Obtener los formatos
			GestorEstructuraDepositoBI serviceDeposito = services
					.lookupGestorEstructuraDepositoBI();
			List formatosVO = serviceDeposito.getFormatosVigentes();

			// obtener cabecera de prevision
			PrevisionVO previsionVO = previsionesService
					.getPrevision(idPrevisionSeleccionada);
			PrevisionPO previsionPO = (PrevisionPO) PrevisionToPO.getInstance(
					services).transform(previsionVO);

			// Obtener los niveles documentales
			List nivelesDocumentales = cuadroBI.getNivelesByTipo(
					TipoNivelCF.UNIDAD_DOCUMENTAL, null);

			if (previsionVO.isEntreArchivos()) {
				isEntreArchivos = true;
			}

			// obtener detalle de prevision(DetallePrevisionVO)
			Collection detallesPrevision = null;
			if (previsionVO.isDetallada()) {
				detallesPrevision = previsionesService
						.getDetallesPrevision(idPrevisionSeleccionada);
				CollectionUtils.transform(detallesPrevision,
						DetallePrevisionToPO.getInstance(services));

				// seleccionar primera si no hay ninguna selecc
				if (detallesPrevision != null) {
					if (frm.getIddetalleseleccionado() == null) {
						DetallePrevisionVO detalleVO = (DetallePrevisionVO) detallesPrevision
								.iterator().next();

						frm.setIddetalleseleccionado(detalleVO.getId());
						if (!isEntreArchivos) {
							frm.setIdserieseleccionada(detalleVO
									.getIdSerieDestino());
							frm.setIdseriemostrarproductores(detalleVO
									.getIdSerieDestino());
							frm.setIdformatoseleccionado(detalleVO
									.getIdFormatoUI());
						} else {
							// Convertir el Objeto en PO
							DetallePrevisionToPO detallePrevisionToPO = DetallePrevisionToPO
									.getInstance(services);
							DetallePrevisionPO detallePrevisionPO = (DetallePrevisionPO) detallePrevisionToPO
									.transform(detalleVO);

							frm.setIdfondoorigen(detallePrevisionPO
									.getSerieOrigen().getIdFondo());

							setInTemporalSession(
									request,
									TransferenciasConstants.DETALLEPREVISION_SELECCIONADO_KEY,
									detallePrevisionPO);
						}
					}
				}
			}
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_NUEVA_RELACION,
					request);
			invocation.setAsReturnPoint(true);

			setInTemporalSession(request,
					TransferenciasConstants.PREVISION_KEY, previsionPO);
			if (detallesPrevision != null) {
				setInTemporalSession(request,
						TransferenciasConstants.DETALLEPREVISION_KEY,
						detallesPrevision);
			}
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_FORMAS_DOCUMENTALES_KEY,
					formasDocumentales);
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_FORMATOS_KEY, formatosVO);
			// Si sólo hay un nivel, dejamos que la aplicación siga funcionando
			// como hasta ahora
			if (nivelesDocumentales != null && nivelesDocumentales.size() > 1) {
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY,
						nivelesDocumentales);
			} else {
				cargarFichas(frm, request);
			}

			if (isEntreArchivos)
				setReturnActionFordward(request,
						mapping.findForward("nueva_relacion_entre_archivos"));
			else
				setReturnActionFordward(request,
						mapping.findForward("nueva_relacion"));
		} else {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_SELECTION_NOT_PERFORMED,
							"prevision"));
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	protected void buscarSerieExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pFondo = request.getParameter("fondo");
		String pCodigo = request.getParameter("codigo");
		String pTitulo = request.getParameter("titulo");
		if (StringUtils.isBlank(pCodigo) && StringUtils.isBlank(pTitulo))
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.NECESARIOS_CRITERIOS_BUSQUEDA));
		else {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
			List series = serieBI.getSeriesActivas(pFondo, pCodigo, pTitulo);
			CollectionUtils.transform(series, SerieToPO.getInstance(services));
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_SERIES_KEY, series);

			// Desmarcar todas las opciones
			RelacionForm frm = (RelacionForm) form;
			frm.setIdseriemostrarproductores(Constants.STRING_EMPTY);
			frm.setIdserieseleccionada(Constants.STRING_EMPTY);
			frm.setIddescriptorproductor(Constants.STRING_EMPTY);
			frm.setNombreproductor(Constants.STRING_EMPTY);
		}
		setReturnActionFordward(request, mapping.findForward("nueva_relacion"));
	}

	protected void datosRelacionEntreArchivosExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_RELACION, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mapping.findForward("ver_relacion_entre_archivos"));
	}

	protected void validarRelacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI serviceRelaciones = services
				.lookupGestionRelacionesBI();

		String idRelacion = request.getParameter("idRelacion");
		RelacionEntregaVO relacionVO = serviceRelaciones
				.getRelacionXIdRelacion(idRelacion);

		// obtener el tipo y el id del destino
		String idDestino = null, idTipoDestino = null;
		if (relacionVO.getReservadeposito() == ReservaPrevision.RESERVADA
				.getIdentificador()) {
			idDestino = relacionVO.getIdelmtodreserva();
			idTipoDestino = relacionVO.getIdtipoelmtodreserva();
		} else {
			idDestino = relacionVO.getIddeposito();
			idTipoDestino = DepositoVO.ID_TIPO_ELEMENTO_UBICACION;
		}

		try {

			if (relacionVO.getIsIngresoDirecto()) {
				serviceRelaciones.signaturarUbicarValidarIngreso(relacionVO,
						null, idDestino, idTipoDestino,
						relacionVO.getIdNivelDocumental());
			}

			ActionRedirect forwardVistaRelacion = new ActionRedirect(
					mapping.findForward("redirect_to_view_relacion"));
			forwardVistaRelacion.setRedirect(true);
			forwardVistaRelacion.addParameter("idrelacionseleccionada",
					relacionVO.getId());
			setReturnActionFordward(request, forwardVistaRelacion);
		} catch (ConcurrentModificationException cme) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.ESTADO_HUECO_ALTERADO, cme
							.getHueco().getPath()));
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (ArchivoModelException ame) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ame.getMessage()));
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (SecurityException e) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									FondosConstants.ERROR_VALIDACION_UDOCS_SIN_PERMISOS));
			goLastClientExecuteLogic(mapping, form, request, response);
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (Exception e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(e.getMessage()));
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	protected void crearrelacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			RelacionForm frm = ((RelacionForm) form);
			ServiceRepository services = getServiceRepository(request);
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();

			PrevisionVO prevision = (PrevisionVO) getFromTemporalSession(
					request, TransferenciasConstants.PREVISION_KEY);

			boolean isEntreArchivos = false;
			if (prevision.isEntreArchivos()) {
				isEntreArchivos = true;
			}

			// Establecemos el nivel documental para la relación
			// Por defecto, se da de alta un nivel de tipo unidad documental y
			// subtipo unidad documental simple
			String idNivelDocumental = cuadroBI.getNivelCF(
					TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
					SubtipoNivelCF.UDOCSIMPLE.getIdentificador()).getId();

			if (StringUtils.isNotEmpty(frm.getIdNivelDocumental()))
				idNivelDocumental = frm.getIdNivelDocumental();

			if (isEntreArchivos) {
				ActionErrors errors = validarCreacionRelacionEntreArchivos(
						prevision, frm, request, cuadroBI);

				if (errors == null) {
					RelacionEntregaVO relacion = serviceRelaciones
							.insertRelacion(frm.getIdprevisionseleccionada(),
									frm.getIddetalleseleccionado(),
									frm.getIdserieseleccionada(),
									frm.getIdformatoseleccionado(),
									frm.getFormaDocumental(),
									frm.getObservaciones(),
									frm.getIddescriptorproductor(),
									frm.getIdfondoorigen(), idNivelDocumental,
									null);

					goReturnPointExecuteLogic(mapping, form, request, response);
					frm.setIdrelacionseleccionada(relacion.getId());
					verrelacionCodeLogic(mapping, form, request, response);
					ClientInvocation invocation = saveCurrentInvocation(
							KeysClientsInvocations.TRANSFERENCIAS_VER_RELACION,
							request);
					invocation.setAsReturnPoint(true);
					setReturnActionFordward(
							request,
							mapping.findForward("datos_relacion_entre_archivos"));
				} else {
					obtenerErrores(request, true).add(errors);
					setReturnActionFordward(
							request,
							mapping.findForward("nueva_relacion_entre_archivos"));
				}

			} else {
				ActionErrors errors = validarCreacionRelacion(prevision, frm,
						request, cuadroBI, descripcionBI);
				if (errors == null) {

					RelacionEntregaVO relacion = serviceRelaciones
							.insertRelacion(frm.getIdprevisionseleccionada(),
									frm.getIddetalleseleccionado(),
									frm.getIdserieseleccionada(),
									frm.getIdformatoseleccionado(),
									frm.getFormaDocumental(),
									frm.getObservaciones(),
									frm.getIddescriptorproductor(), null,
									idNivelDocumental, frm.getIdFicha());
					goReturnPointExecuteLogic(mapping, form, request, response);
					ActionRedirect redirectAVistaRelacion = new ActionRedirect(
							mapping.findForward("redirect_to_view_relacion"));
					redirectAVistaRelacion.setRedirect(true);
					redirectAVistaRelacion.addParameter(
							"idrelacionseleccionada", relacion.getId());
					setReturnActionFordward(request, redirectAVistaRelacion);

				} else {
					obtenerErrores(request, true).add(errors);
					setReturnActionFordward(request,
							mapping.findForward("nueva_relacion"));
				}
			}

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("nueva_relacion"));
		}
	}

	private boolean masDeUnNivelDocumental(HttpServletRequest request,
			GestionCuadroClasificacionBI cuadroBI) {
		boolean ret = false;

		Object obj = getFromTemporalSession(request,
				TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY);
		if (obj == null)
			obj = cuadroBI
					.getNivelesByTipo(TipoNivelCF.UNIDAD_DOCUMENTAL, null);

		if (obj != null) {
			List nivelesDocumentales = (List) obj;
			if (nivelesDocumentales.size() > 1)
				ret = true;
		}

		return ret;
	}

	private ActionErrors validarCreacionRelacion(PrevisionVO prevision,
			RelacionForm form, HttpServletRequest request,
			GestionCuadroClasificacionBI cuadroBI,
			GestionDescripcionBI descripcionBI) {
		ActionErrors errors = new ActionErrors();

		if (prevision.isDetallada()) {
			if (StringUtils.isBlank(form.getIddetalleseleccionado())) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.NECESARIO_SELECCIONAR_LINEA_DETALLE));
			}
		} else {
			if (StringUtils.isBlank(form.getIdserieseleccionada())) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.NECESARIO_SELECCIONAR_SERIE));
			}
		}
		if (prevision.getTipotransferencia() != TipoTransferencia.ORDINARIA
				.getIdentificador()) {
			// validar que se haya introducido un productor por defecto
			if (StringUtils.isBlank(form.getIddescriptorproductor())) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.RELACION_NECESARIO_SELECCIONAR_PRODUCTOR));
			}
		}

		// Si existe más de un nivel documental y la relación no es ordinaria y
		// no se ha seleccionado ninguno => error
		if (masDeUnNivelDocumental(request, cuadroBI)
				&& prevision.getTipotransferencia() != TipoTransferencia.ORDINARIA
						.getIdentificador()) {
			if (StringUtils.isBlank(form.getIdNivelDocumental())) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ErrorKeys.RELACION_NECESARIO_SELECCIONAR_NIVEL_DOCUMENTAL));
			}
		}

		// Si se ha seleccionado una ficha porque se trate de una relación de
		// entrega con ficha, validar que la ficha seleccionada tiene
		// y son obligatorios y editables Título y Fechas Extremas (Inicial y
		// Final)
		if (StringUtils.isNotEmpty(form.getIdFicha())) {
			boolean tituloOK = false, fechaIniOK = false, fechaFinOK = false;

			DefFicha defFicha = descripcionBI
					.getDefFichaById(form.getIdFicha());

			DefCampoEspecial defCampoEspecialTitulo = defFicha
					.getDatoEspecial(DefTipos.CAMPO_ESPECIAL_ID_TITULO);

			if (defCampoEspecialTitulo != null) {
				if (defCampoEspecialTitulo.isEditable()
						&& defCampoEspecialTitulo.isObligatorio())
					tituloOK = true;
			}

			List datosObligatorios = defFicha.getDatosObligatorios();
			if (datosObligatorios != null) {
				Iterator it = datosObligatorios.iterator();
				while (it.hasNext()) {
					DefCampoDato defCampoDato = (DefCampoDato) it.next();
					if (ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaInicial()
							.equals(defCampoDato.getId())) {
						if (defCampoDato.isEditable()) {
							fechaIniOK = true;
						}
					} else {
						if (ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getFechaExtremaFinal()
								.equals(defCampoDato.getId())) {
							if (defCampoDato.isEditable()) {
								fechaFinOK = true;
							}
						}
					}
					if (tituloOK && fechaIniOK && fechaFinOK)
						break;
				}
			}
			if (!tituloOK)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.RELACION_FICHA_TITULO_MAL_DEFINIDO));

			if (!fechaIniOK)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.RELACION_FICHA_FECHA_INICIAL_MAL_DEFINIDA));

			if (!fechaFinOK)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						ErrorKeys.RELACION_FICHA_FECHA_FINAL_MAL_DEFINIDA));
		}

		return errors.size() > 0 ? errors : null;
	}

	private ActionErrors validarCreacionRelacionEntreArchivos(
			PrevisionVO prevision, RelacionForm form,
			HttpServletRequest request, GestionCuadroClasificacionBI cuadroBI) {
		ActionErrors errors = new ActionErrors();

		// Si existe más de un nivel documental y no se ha seleccionado ninguno
		// => error
		if (masDeUnNivelDocumental(request, cuadroBI)) {
			if (StringUtils.isBlank(form.getIdNivelDocumental())) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ErrorKeys.RELACION_NECESARIO_SELECCIONAR_NIVEL_DOCUMENTAL));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	private RelacionEntregaPO verrelacionCodeLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm frm = ((RelacionForm) form);
		String idrelacionseleccionada = frm.getIdrelacionseleccionada();
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();

			RelacionEntregaVO relacionVO = serviceRelaciones
					.abrirRelacionEntrega(idrelacionseleccionada);
			RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
					.getInstance(services).transform(relacionVO);

			// Comprobar si la relación de entrega tiene documentos electronicos
			if (relacionVO.isAbierta()
					&& relacionVO.isRechazada()
					&& relacionVO.isEntreArchivos()
					&& !serviceRelaciones
							.hayUDocsElectronicasParaRelacionEntreArchivos(relacionVO)) {
				relacionPO.setPermitidaAdicionDocsElectronicos(false);
			}

			if (!relacionPO.getIsIngresoDirecto()) {
				PrevisionVO prevision = relacionPO.getPrevision();
				PrevisionPO previsionPO = (PrevisionPO) PrevisionToPO
						.getInstance(services).transform(prevision);
				relacionPO.setPrevision(previsionPO);
			}

			// REENCAJADO
			if (ConfigConstants.getInstance().getPermitirReencajado()) {
				request.setAttribute(
						TransferenciasConstants.CON_REENCAJADO_KEY,
						Boolean.TRUE);
				if (relacionPO.isEntreArchivos()
						&& Constants.TRUE_STRING.equals(relacionPO
								.getConReencajado())) {
					InfoUDocReeacr infoUDocReeacr = new InfoUDocReeacr(
							relacionVO, getGestionRelacionesEACRBI(request));
					setInTemporalSession(request,
							TransferenciasConstants.INFO_UDOCREEACR,
							infoUDocReeacr);
				}
			}

			if (relacionPO.isConReencajadoValidada()) {
				List listaUDocs = getGestionRelacionesEACRBI(request)
						.getUDocsByIdRelacionGroupByUnidadDoc(
								relacionPO.getId());
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_UDOCS_REENCAJADO_KEY,
						listaUDocs);

			}

			// Rellenar el estado inicial de existencia de los campos de la
			// ficha.
			relacionPO.checkCamposEnFicha();

			setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
					relacionPO);

			InfoAsignacionUdocs infoAsignacionUdocs = new InfoAsignacionUdocs(
					relacionVO, serviceRelaciones, null);
			setInTemporalSession(request,
					TransferenciasConstants.ASIGNACION_UDOC2UI,
					infoAsignacionUdocs);

			return relacionPO;
		} catch (TooManyResultsException e) {
			// TODO PREPARAR LAS CHEKED PARA Q PUEDAN SER GESTIONADAS
			guardarError(request, e);
			goBackExecuteLogic(mapping, form, request, response);
		}
		return null;

	}

	protected void verrelaciondesdedepositoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_RELACION, request);
		RelacionEntregaPO relacionPO = verrelacionCodeLogic(mapping, form,
				request, response);
		if (!relacionPO.isEntreArchivos())
			setReturnActionFordward(request,
					mapping.findForward("ver_relacion"));
		else
			setReturnActionFordward(request,
					mapping.findForward("ver_relacion_entre_archivos"));
	}

	protected void verrelacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionEntregaVO relacionVO;
		RelacionEntregaPO relacionPO;
		ClientInvocation invocation = null;
		RelacionForm frm = ((RelacionForm) form);
		String idrelacionseleccionada = frm.getIdrelacionseleccionada();

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI serviceRelaciones = services
				.lookupGestionRelacionesBI();

		relacionVO = serviceRelaciones
				.getRelacionXIdRelacion(idrelacionseleccionada);

		if (relacionVO.getIsIngresoDirecto()) {

			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			if (cuadroBI.isSubtipoCaja(relacionVO.getIdNivelDocumental())) {
				invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_INGRESO_FRACCION_SERIE,
						request);
			} else {
				invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_INGRESO, request);
			}
		} else
			invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_VER_RELACION, request);
		invocation.setAsReturnPoint(true);

		relacionPO = verrelacionCodeLogic(mapping, form, request, response);

		if (!relacionPO.isEntreArchivos())
			setReturnActionFordward(request,
					mapping.findForward("ver_relacion"));
		else
			setReturnActionFordward(request,
					mapping.findForward("ver_relacion_entre_archivos"));
	}

	protected void veringresoExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm frm = ((RelacionForm) form);
		String idingresoseleccionado = frm.getIdingresoseleccionado();
		frm.setIdrelacionseleccionada(idingresoseleccionado);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI serviceRelaciones = services
				.lookupGestionRelacionesBI();

		RelacionEntregaVO ingresoVO = serviceRelaciones
				.getRelacionXIdRelacion(idingresoseleccionado);
		ClientInvocation invocation = null;
		if (ingresoVO.getIsIngresoDirecto()) {
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			if (cuadroBI.isSubtipoCaja(ingresoVO.getIdNivelDocumental())) {
				invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_INGRESO_FRACCION_SERIE,
						request);
			} else {
				invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_INGRESO, request);
			}
		} else
			invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_VER_RELACION, request);
		invocation.setAsReturnPoint(true);

		RelacionEntregaPO relacionPO = verrelacionCodeLogic(mapping, form,
				request, response);
		if (!relacionPO.isEntreArchivos())
			setReturnActionFordward(request,
					mapping.findForward("ver_relacion"));
		else
			setReturnActionFordward(request,
					mapping.findForward("ver_relacion_entre_archivos"));
	}

	protected void edicionExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RelacionForm frm = ((RelacionForm) form);

		RelacionEntregaVO relacionVO = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		if (relacionVO.getTipotransferencia() == TipoTransferencia.INGRESO_DIRECTO
				.getIdentificador()) {
			ActionRedirect redirectAVistaIngreso = new ActionRedirect(
					mapping.findForward("redirect_to_edicion_ingreso"));
			redirectAVistaIngreso.setRedirect(true);
			redirectAVistaIngreso.addParameter("idIngreso", relacionVO.getId());
			setReturnActionFordward(request, redirectAVistaIngreso);
		} else {
			frm.setObservaciones(relacionVO.getObservaciones());
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_EDICION_RELACION,
					request);
			setReturnActionFordward(request,
					mapping.findForward("edicion_relacion"));
		}
	}

	protected void guardaredicionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		RelacionForm frm = ((RelacionForm) form);
		try {
			RelacionEntregaVO relacionVO = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);
			relacionVO.setObservaciones(frm.getObservaciones());
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			if (relacionVO.isRechazada())
				relacionVO.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
			serviceRelaciones.updateRelacion(relacionVO);
			goBackExecuteLogic(mapping, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("edicion_relacion"));
		}
	}

	public void eliminarrelacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			serviceRelaciones
					.eliminarRelaciones(new String[] { ((RelacionForm) form)
							.getIdrelacionseleccionada() });
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void enviarIngresoSeleccionUbicacionExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ServiceClient serviceClient = ServiceClient
					.create(getAppUser(request));
			ServiceRepository services = ServiceRepository
					.getInstance(serviceClient);

			// datos para la vista
			// RelacionForm frm = (RelacionForm) form;
			GestionRelacionesEntregaBI relacionesBI = services
					.lookupGestionRelacionesBI();

			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			relacionEntrega = relacionesBI
					.enviarSeleccionarUbicacionIngresoDirecto(relacionEntrega
							.getId());

			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();

			// Obtener las ubicaciones a partir del Id de archivo receptor de la
			// relación de entrega
			Collection edificios = null;

			edificios = depositoBI
					.getUbicacionesXIdsArchivo(new String[] { relacionEntrega
							.getIdarchivoreceptor() });

			CollectionUtils.transform(edificios,
					TransformerToEspacioEnUbicacionPO.getInstance(services,
							relacionEntrega.getIdformatoui()));
			int nUnidadesInstalacion = relacionesBI
					.getNUnidadesInstalacion(relacionEntrega.getId());
			boolean noExisteEspacioDisponible = false;
			if (edificios != null) {
				for (Iterator i = edificios.iterator(); i.hasNext();) {
					EspacioEnUbicacionPO ubicacion = (EspacioEnUbicacionPO) i
							.next();
					if (ubicacion.getNumeroHuecosDisponibles() >= nUnidadesInstalacion)
						noExisteEspacioDisponible = false;
				}
			}

			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_MODIFICAR_UBICACION,
					request);
			invocation.setAsReturnPoint(true);
			setInTemporalSession(request,
					TransferenciasConstants.NO_EXISTE_ESPACIO_DISPONIBLE,
					new Boolean(noExisteEspacioDisponible));
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_EDIFICIOS_KEY, edificios);

			setReturnActionFordward(request,
					mappings.findForward("modificar_ubicacion"));
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void eliminarrelacionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RelacionForm frm = (RelacionForm) form;
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			serviceRelaciones.eliminarRelaciones(frm
					.getRelacionesseleccionadas());

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void eliminaringresosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RelacionForm frm = (RelacionForm) form;
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			serviceRelaciones.eliminarIngresosDirectos(frm
					.getRelacionesseleccionadas());
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	protected void listadoprevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();

		List previsiones = previsionesService
				.getPrevisionesAceptanRelaciones(appUser.getId());
		CollectionUtils.transform(previsiones,
				PrevisionToPO.getInstance(services));
		request.setAttribute(TransferenciasConstants.LISTA_PREVISIONES_KEY,
				previsiones);

		setReturnActionFordward(request,
				mapping.findForward("listado_prevision"));

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_LISTADO_PREVISIONES_CREACION_RELACION,
				request);
	}

	protected void finalizarCorreccionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionesBI = getGestionRelacionesBI(request);
			GestionRelacionesEACRBI relacionEABI = getGestionRelacionesEACRBI(request);
			RelacionEntregaVO relacionVO = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			// Comprobar que todas las unidades que tienen partes, están
			// completas.
			ActionErrors errors = null;
			if (relacionVO.isEntreArchivos()) {
				// Comprobar que todas los expedientes están completos, es decir
				// que los expedientes que están partidos, en la relación,
				// deberán ir todas las cajas que los contienen.

				// Obtener las lista de unidades de instalación
				List listaUinst = relacionesBI
						.getUnidadesInstalacionEntreArchivos(relacionVO.getId());
				List listaIdsUInst = new ArrayList();
				if (!ListUtils.isEmpty(listaUinst)) {
					Iterator it = listaUinst.iterator();
					while (it.hasNext()) {
						UnidadInstalacionReeaVO uInst = (UnidadInstalacionReeaVO) it
								.next();
						listaIdsUInst.add(uInst.getId());
					}
				}

				// Obtener la lista de unidades documentales
				List listaUdocs = relacionesBI
						.getUDocsXidRelacionEntreArchivos(relacionVO.getId());

				List listaIdsUdocs = new ArrayList();
				if (!ListUtils.isEmpty(listaUdocs)) {
					Iterator it = listaUdocs.iterator();
					while (it.hasNext()) {
						UnidadDocumentalVO udoc = (UnidadDocumentalVO) it
								.next();
						listaIdsUdocs.add(udoc.getId());
					}
				}

				List listaPartesUDocsNoSeleccionadas = relacionesBI
						.getPartesUdocsNoIncluidasEnRelacion(
								relacionVO.getId(), listaIdsUdocs,
								listaIdsUInst);

				if (!ListUtils.isEmpty(listaPartesUDocsNoSeleccionadas)) {
					Iterator it = listaPartesUDocsNoSeleccionadas.iterator();
					errors = new ActionErrors();

					List listaPartesUDocsIncompletas = new ArrayList();

					while (it.hasNext()) {
						UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) it
								.next();
						listaPartesUDocsIncompletas.add(udoc.getIdunidaddoc());
					}

					List listaUnidadesIncompletas = relacionesBI
							.getPartesUdocsIncompletasEnRelacion(
									listaPartesUDocsIncompletas, listaIdsUInst);

					if (!ListUtils.isEmpty(listaUnidadesIncompletas)) {
						Iterator iter = listaUnidadesIncompletas.iterator();
						errors = new ActionErrors();
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
								Constants.ERROR_RELACION_UNIDADES_A_ELIMINAR));

						while (iter.hasNext()) {
							UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) iter
									.next();
							errors.add(
									ActionErrors.GLOBAL_ERROR,
									new ActionError(
											Constants.ETIQUETA_SIGNATURA_UNIDAD_DOCUMENTAL,
											udoc.getSignaturaudoc()));
						}

					}
				}

				if (relacionVO.isRelacionConReencajado()) {
					try {
						relacionEABI.checkPermtirEnviarRelacionEACR(relacionVO
								.getId());
					} catch (RelacionEntregaConUDocsSinAsingarAUIException e) {
						throw new RelacionOperacionNoPermitidaException(
								ArchivoErrorCodes.RELACION_UDOC_CON_DOC_FISICOS_SIN_CAJA);
					} catch (RelacionEntregaNoEnviableUIsConVariasUDocsException e) {
						throw new RelacionOperacionNoPermitidaException(
								ArchivoErrorCodes.REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_UDOCS);
					} catch (RelacionEntregaNoEnviableUIsConVariasFSException e) {
						throw new RelacionOperacionNoPermitidaException(
								ArchivoErrorCodes.REEA_NO_MULTIDOC_CON_UIS_CON_VARIAS_FS);
					}

					// Eliminar las unidades de instalación vacias
					relacionesBI.eliminarUIsReeaCRVacias(relacionVO.getId());
				}
			}

			RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
					.getInstance(services).transform(relacionVO);
			// Comprobar si alguno de los huecos asociados a la signatura ha
			// sido reutilizado
			if (relacionPO.isSignaturacionAsociadaHuecoYSignaturaSolicitable())
				relacionesBI
						.checkCorreccionHuecosAsociadosRelacionLibres(relacionVO
								.getId());

			if (errors == null) {
				GestionRelacionesEntregaBI bi = services
						.lookupGestionRelacionesBI();
				bi.finalizarCorreccionErrores(relacionVO.getId());
			} else {
				obtenerErrores(request, true).add(errors);
			}
		} catch (ActionNotAllowedException anae) {
			logger.error("Error finalizando correccion de errores " + anae);
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	protected void validarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI bi = services.lookupGestionRelacionesBI();
		RelacionEntregaVO infoRelacion = (RelacionEntregaVO) request
				.getSession()
				.getAttribute(TransferenciasConstants.RELACION_KEY);

		String idNivelUnidadDocumental = infoRelacion.getIdNivelDocumental();

		boolean comprobarPermisos = true;

		// Si el usuario es el gestor en archivo de la relación y tiene el
		// permisos
		ServiceClient serviceClient = ServiceClient.create(getAppUser(request));

		if ((infoRelacion.getIdusrgestorarchivorec().equals(
				getServiceClient(request)) && serviceClient
				.hasPermission(AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR))
				|| serviceClient
						.hasPermission(AppPermissions.GESTION_TRANSFERENCIAS_ARCHIVO_RECEPTOR)
				|| infoRelacion.getIsIngresoDirecto()) {
			comprobarPermisos = false;
		}

		Locker locker = new Locker(getServiceClient(request));
		try {
			// Bloquear la Relación
			locker.bloqueaCualquiera(serviceClient, infoRelacion.getId(),
					LockerObjectTypes.VALIDACION_RELACION,
					ArchivoModules.TRANSFERENCIAS_MODULE);

			try {
				bi.validarRelacion(infoRelacion, idNivelUnidadDocumental,
						comprobarPermisos,null);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			} catch (SecurityException e) {
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										FondosConstants.ERROR_VALIDACION_UDOCS_SIN_PERMISOS));

			} finally {
				// Desbloquear la Relación
				locker.desbloquea(serviceClient, infoRelacion.getId(),
						LockerObjectTypes.VALIDACION_RELACION,
						ArchivoModules.TRANSFERENCIAS_MODULE);
			}

		} catch (LockerException e) {
			// Mostrar el error
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							ErrorKeys.DESCRIPCION_BLOQUEO_VALIDACION_RELACION,
							new Object[] { e.getLockVO().getUsuario(),
									e.getLockVO().getTs() }));
			setReturnActionFordward(request, mapping.findForward("globalerror"));
			return;
		}

		goLastClientExecuteLogic(mapping, form, request, response);
	}

	protected void verEstadoValidacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idRelacion = request.getParameter("idRelacion");
		if (idRelacion != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI managerRelaciones = services
					.lookupGestionRelacionesBI();

			RelacionEntregaVO relacionVO = managerRelaciones
					.getRelacionXIdRelacion(idRelacion);

			List udocsRelacion = null;
			List udocsElectronicas = null;

			if (relacionVO.isEntreArchivos()) {
				udocsRelacion = managerRelaciones
						.getUDocsXidRelacionEntreArchivos(idRelacion);
				udocsElectronicas = managerRelaciones
						.getUDocsElectronicasByIdRelacionEntreArchivos(idRelacion);
			} else {
				udocsRelacion = managerRelaciones
						.getUnidadesDocumentalesFisicas(idRelacion);
				udocsElectronicas = managerRelaciones
						.getUDocsElectronicasByIdRelacion(idRelacion);
			}

			request.setAttribute(
					TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_ENTRE_ARCHIVOS_KEY,
					udocsElectronicas);
			request.setAttribute(
					TransferenciasConstants.LISTADO_UNIDADES_DOCUMENTALES_KEY,
					udocsRelacion);

			GestionCuadroClasificacionBI cuadroClasificacionBI = services
					.lookupGestionCuadroClasificacionBI();
			request.setAttribute(
					FondosConstants.LISTA_NIVELES_TIPO_UNIDAD_DOCUMENTAL,
					cuadroClasificacionBI.getNivelesByTipo(
							TipoNivelCF.UNIDAD_DOCUMENTAL, null));
			setReturnActionFordward(request,
					mapping.findForward("lista_udocs_a_validar"));
		}
	}

	protected void verRelacionesPrevisionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String idPrevision = request.getParameter("idPrevision");
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI bi = services.lookupGestionRelacionesBI();
		List relaciones = null;
		String idDetalle = request.getParameter("idDetalle");

		if (idDetalle != null) {
			relaciones = bi.findByDetallePrevision(idPrevision, idDetalle);
			GestionPrevisionesBI previsionBI = services
					.lookupGestionPrevisionesBI();
			DetallePrevisionVO detallePrevisionVO = previsionBI
					.getDetallePrevision(idDetalle);
			DetallePrevisionPO detallePrevision = (DetallePrevisionPO) DetallePrevisionToPO
					.getInstance(services).transform(detallePrevisionVO);
			request.setAttribute(TransferenciasConstants.DETALLEPREVISION_KEY,
					detallePrevision);
		} else
			relaciones = bi.findByPrevision(idPrevision);
		CollectionUtils.transform(relaciones,
				RelacionToPO.getInstance(services));
		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relaciones);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_RELACIONES_PREVISION,
				request);
		setReturnActionFordward(request,
				mapping.findForward("relaciones_prevision"));
	}

	public void selCartelasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_IMPRESION_CARTELAS,
				request);

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		Integer numUInstalacion = new Integer(0);
		Integer numUInstalacionAsignadas = new Integer(0);

		if (relacionEntrega.isRelacionConReencajado()) {
			numUInstalacion = new Integer(getGestionRelacionesEACRBI(request)
					.getCountUIs(relacionEntrega.getId()));
		} else {
			numUInstalacion = new Integer(getGestionRelacionesBI(request)
					.getNUnidadesInstalacion(relacionEntrega.getId(),
							relacionEntrega.getTipotransferencia()));
			numUInstalacionAsignadas = new Integer(getGestionRelacionesBI(
					request).getNUnidadesInstalacionAsignadas(
					relacionEntrega.getId(),
					relacionEntrega.getTipotransferencia()));
		}

		request.setAttribute(TransferenciasConstants.NUM_UNIDADES_INSTALACION,
				new Integer(numUInstalacion.intValue()
						+ numUInstalacionAsignadas.intValue()));

		setReturnActionFordward(request,
				mapping.findForward("seleccionar_cartelas"));
	}

	public void verCartelasExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		String cajas = request.getParameter("cajas");
		// cajas admite el siguiente formato 1,2,3,4,5-7,8, 9, 10, 12-3, ...
		// usar una expresion regular
		if (((RelacionForm) form).getSelCajas().equals("2")
				&& !(Pattern.matches(
						Constants.EXPRESION_REGULAR_VAL_SEL_CARTELAS, cajas))) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_CAMPO_NO_NUMERICO, Messages
							.getString(TransferenciasConstants.TEXTO_CAJAS,
									request.getLocale())));
			setReturnActionFordward(request,
					mapping.findForward("redirect_to_seleccionar_cartelas"));
			return;
		}

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_IMPRESION_CARTELAS,
				request);

		List listaCartelas = null;
		if (relacionEntrega.isEntreArchivos()) {
			if (relacionEntrega.isRelacionConReencajado()) {
				listaCartelas = getGestionRelacionesEACRBI(request)
						.getUnidadesInstalacion(relacionEntrega.getId(),
								IntervalOptions.parse(cajas));
			} else {
				listaCartelas = getGestionRelacionesBI(request)
						.getUnidadesInstalacionEntreArchivos(
								relacionEntrega.getId(),
								IntervalOptions.parse(cajas));
			}
		} else {
			listaCartelas = getGestionRelacionesBI(request)
					.getUnidadesInstalacion(relacionEntrega.getId(),
							IntervalOptions.parse(cajas));
		}

		request.setAttribute(TransferenciasConstants.LISTA_CARTELAS,
				listaCartelas);

		setReturnActionFordward(request, mapping.findForward("ver_cartelas"));
	}

	public void modificarubicacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceClient serviceClient = ServiceClient.create(getAppUser(request));
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);

		// datos para la vista
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		// Obtener las ubicaciones a partir del Id de archivo receptor de la
		// relación de entrega
		Collection edificios = null;

		edificios = depositoBI
				.getUbicacionesXIdsArchivo(new String[] { relacionEntrega
						.getIdarchivoreceptor() });

		CollectionUtils.transform(edificios, TransformerToEspacioEnUbicacionPO
				.getInstance(services, relacionEntrega.getIdFormatoDestino()));
		int nUnidadesInstalacion = relacionesBI
				.getNUnidadesInstalacion(relacionEntrega.getId());
		boolean noExisteEspacioDisponible = false;
		if (edificios != null) {
			for (Iterator i = edificios.iterator(); i.hasNext();) {
				EspacioEnUbicacionPO ubicacion = (EspacioEnUbicacionPO) i
						.next();
				if (ubicacion.getNumeroHuecosDisponibles() >= nUnidadesInstalacion)
					noExisteEspacioDisponible = false;
			}
		}

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_MODIFICAR_UBICACION,
				request);
		invocation.setAsReturnPoint(true);
		setInTemporalSession(request,
				TransferenciasConstants.NO_EXISTE_ESPACIO_DISPONIBLE,
				new Boolean(noExisteEspacioDisponible));
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_EDIFICIOS_KEY, edificios);

		setReturnActionFordward(request,
				mapping.findForward("modificar_ubicacion"));
	}

	public void aceptarmodificarubicacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		RelacionForm frm = (RelacionForm) form;
		try {
			relacionesService.updateDestinoRelacion(relacionEntrega,
					frm.getIdubicacionseleccionada());

			goBackExecuteLogic(mapping, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("modificar_ubicacion"));
		}
	}

	protected void verInformeReservasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (logger.isInfoEnabled())
			logger.info("Entrada en verInformeReservasExecuteLogic");

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_INFORME_RESERVAS,
				request);

		// Identificador de la relación de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Huevos reservados de la relación de entrega
		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);
		request.setAttribute(TransferenciasConstants.LISTA_HUEVOS_RESERVADOS,
				depositoBI.getHuecosReservadosXIdRelacionEntrega(idRelacion));

		setReturnActionFordward(request,
				mapping.findForward("ver_informe_reservas"));
	}

	protected void verInformeUbicacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (logger.isInfoEnabled())
			logger.info("Entrada en verInformeUbicacionExecuteLogic");

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_INFORME_UBICACION,
				request);

		// Identificador de la relación de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Ubicaciones de las unidades de instalación de la relación de entrega
		GestionRelacionesEntregaBI relacionesBI = getGestionRelacionesBI(request);
		request.setAttribute(TransferenciasConstants.LISTA_UBICACIONES,
				relacionesBI.getUbicacionesRelacion(idRelacion));

		setReturnActionFordward(request,
				mapping.findForward("ver_informe_ubicacion"));
	}

	protected void relacionesUbicacionExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		ServiceRepository services = getServiceRepository(request);
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		String idUbicacion = request.getParameter("ubicacion");
		List relacionesUbicacion = relacionBI.getRelacionesPendientesDeUbicar(
				idUbicacion, relacion.getIdformatoui());
		CollectionUtils.transform(relacionesUbicacion,
				RelacionToPO.getInstance(services));
		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relacionesUbicacion);
		setReturnActionFordward(request,
				mapping.findForward("modificar_ubicacion"));
	}

	protected void homeRelacionesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		RelacionToPO transformer = RelacionToPO.getInstance(services);
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		boolean tieneRelacionesAGestionar = true;
		try {
			Collection relacionesAGestionar = relacionesService
					.getRelacionesAGestionar();
			if (relacionesAGestionar.size() > 5)
				relacionesAGestionar = new ArrayList(relacionesAGestionar)
						.subList(0, 5);

			CollectionUtils.transform(relacionesAGestionar, transformer);
			request.setAttribute(
					TransferenciasConstants.LISTA_RELACIONES_A_GESTIONAR_KEY,
					relacionesAGestionar);
		} catch (ActionNotAllowedException anae) {
			tieneRelacionesAGestionar = false;
		}
		boolean tieneReservasAGestionar = true;
		try {
			Collection reservasDeEspacio = relacionesService
					.getRelacionesAReservar();
			if (reservasDeEspacio.size() > 5)
				reservasDeEspacio = new ArrayList(reservasDeEspacio).subList(0,
						5);
			CollectionUtils.transform(reservasDeEspacio, transformer);
			request.setAttribute(
					TransferenciasConstants.LISTA_RESERVAS_ESPACION_KEY,
					reservasDeEspacio);
		} catch (ActionNotAllowedException anae) {
			tieneReservasAGestionar = false;
		}

		Collection relacionesEnElaboracion = relacionesService
				.getRelacionesEnElaboracionXUser(appUser.getId());
		if (relacionesEnElaboracion.size() > 5)
			relacionesEnElaboracion = new ArrayList(relacionesEnElaboracion)
					.subList(0, 5);
		CollectionUtils.transform(relacionesEnElaboracion, transformer);

		if (!tieneRelacionesAGestionar && !tieneReservasAGestionar) {
			request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
					relacionesEnElaboracion);
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_LISTADO_RELACIONES_OFICINA,
					request).setAsReturnPoint(true);
			setReturnActionFordward(request,
					mapping.findForward("listado_relacion"));
		} else {
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_GESTION_RELACIONES,
					request).setAsReturnPoint(true);
			request.setAttribute(
					TransferenciasConstants.LISTA_RELACIONES_EN_ELABORACION_KEY,
					relacionesEnElaboracion);
			setReturnActionFordward(request,
					mapping.findForward("home_relaciones"));
		}

	}

	/**
	 * Muestra las relaciones de entrega relacionadas con un elemento del cuadro
	 * de clasificación.
	 */
	protected void verRelacionesExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de verRelacionesExecuteLogic");

		// Guardar el enlace a la página
		String id = request.getParameter(Constants.ID);
		// Leer el identificador del elemento del cuadro

		if (logger.isInfoEnabled())
			logger.info("Id: " + id);

		// Leer el identificador del elemento del cuadro
		int tipo = TypeConverter.toInt(request.getParameter("tipo"),
				ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL);
		if (logger.isInfoEnabled())
			logger.info("Tipo: " + tipo);

		try {
			ServiceRepository service = getServiceRepository(request);

			GestionRelacionesEntregaBI relacionesBI = service
					.lookupGestionRelacionesBI();
			List relaciones = relacionesBI.getRelacionesByElemento(id, tipo,
					null);

			// Si no se han encontrado relaciones de origen del elemento de tipo
			// unidad documental
			// buscaremos si éste proviene de una fracción de serie que fue
			// dividida y a su vez provenía de una relación de entrega
			// o si se está visualizando la ficha de unidad documental en
			// relación o en fracción de serie
			if (tipo == ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL) {
				if (ListUtils.isEmpty(relaciones)) {
					relaciones = relacionesBI.getRelacionesByUDocRE(id);
				}

				if (ListUtils.isEmpty(relaciones)) {
					relaciones = relacionesBI.getRelacionesByUDocFS(id);
				}

				if (ListUtils.isEmpty(relaciones)) {
					relaciones = relacionesBI.getRelacionesByFSDividida(id);
				}
			}

			if (!ListUtils.isEmpty(relaciones)) {
				CollectionUtils.transform(relaciones,
						RelacionToPO.getInstance(service));
			}

			// Relaciones de entrega asociadas al elemento
			request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
					relaciones);
		} catch (TooManyResultsException e) {
		}

		setReturnActionFordward(request, mapping.findForward("ver_relaciones"));
	}

	/**
	 * Muestra las relaciones de entrega relacionadas con un elemento del cuadro
	 * de clasificación.
	 */
	protected void cambioDetalleExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm frm = (RelacionForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesService = services
				.lookupGestionPrevisionesBI();

		DetallePrevisionVO detalleVO = previsionesService
				.getDetallePrevision(frm.getIddetalleseleccionado());
		frm.setIddetalleseleccionado(detalleVO.getId());

		// Convertir el Objeto en PO
		DetallePrevisionToPO detallePrevisionToPO = DetallePrevisionToPO
				.getInstance(services);
		DetallePrevisionPO detallePrevisionPO = (DetallePrevisionPO) detallePrevisionToPO
				.transform(detalleVO);

		frm.setIdfondoorigen(detallePrevisionPO.getSerieOrigen().getIdFondo());

		setInTemporalSession(request,
				TransferenciasConstants.DETALLEPREVISION_SELECCIONADO_KEY,
				detallePrevisionPO);
		setReturnActionFordward(request,
				mapping.findForward("nueva_relacion_entre_archivos"));
	}

	protected void eliminarUInstReeaExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm frm = (RelacionForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		if (frm.getElementosseleccionados() != null
				&& frm.getElementosseleccionados().length > 0) {
			relacionesBI.eliminarUinstReea(frm.getIdrelacionseleccionada(),
					frm.getElementosseleccionados());
		}

		verrelacionCodeLogic(mapping, form, request, response);
		setReturnActionFordward(request,
				mapping.findForward("ver_relacion_entre_archivos"));
	}

	protected void eliminarUDocsElectronicasReeaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm frm = (RelacionForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		if (!ArrayUtils.isEmpty(frm.getElementosElectronicosSel())) {
			relacionesBI.eliminarUDocsElectronicasReea(
					frm.getIdrelacionseleccionada(),
					frm.getElementosElectronicosSel());
			updateEstadoRelacionRechazadaInSession(request);
		}

		verrelacionCodeLogic(mapping, form, request, response);
		setReturnActionFordward(request,
				mapping.findForward("ver_relacion_entre_archivos"));
	}

	protected void marcarCajaRevisadaEntreArchivosExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm frm = (RelacionForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		GestionRelacionesEACRBI relacionEABI = services
				.lookupGestionRelacionesEACRBI();

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (frm.getElementosseleccionados() != null
				&& frm.getElementosseleccionados().length > 0) {
			if (relacionEntrega.isRelacionConReencajado()) {
				relacionEABI
						.marcarUIsRevisadas(frm.getElementosseleccionados());
			} else {
				relacionesBI.marcarUinstReeaRevisadas(frm
						.getElementosseleccionados());
			}
		}

		if (!ArrayUtils.isEmpty(frm.getElementosElectronicosSel())) {
			// Modificar el estado de las unidades de instalación
			getGestionRelacionesBI(request).updateEstadoUDocsElectronicas(
					relacionEntrega.getId(), frm.getElementosElectronicosSel(),
					EstadoCotejo.REVISADA.getIdentificador());
		}

		verrelacionCodeLogic(mapping, form, request, response);
		setReturnActionFordward(request,
				mapping.findForward("ver_relacion_entre_archivos"));
	}

	private void updateEstadoRelacionRechazadaInSession(
			HttpServletRequest request) {
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (relacionEntrega.isRechazada())
			relacionEntrega
					.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
	}

	/**
	 * Logica ejecutada cuan el action es invocado con parametro method con
	 * valor 'lockUdocs'. Se encarga de bloquear las unidades documentales
	 * seleccionadas
	 */
	private void lockUdocsCodeLogic(String[] udocsToLock,
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		if (ArrayUtils.isNotEmpty(udocsToLock)) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionFondosBI fondosBI = services.lookupGestionFondosBI();
			fondosBI.lockUnidadesDocumentales(udocsToLock);
		}
	}

	public void accionLockUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] udocsToLock = (String[]) getFromTemporalSession(request,
				FondosConstants.ACCION_ELEMENTOS_KEY);

		try {
			lockUdocsCodeLogic(udocsToLock, mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void lockUdocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String[] udocsToLock = ((RelacionForm) form)
				.getElementosUdocSeleccionados();
		try {
			lockUdocsCodeLogic(udocsToLock, mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);

	}

	/**
	 * Logica ejecutada cuan el action es invocado con parametro method con
	 * valor 'unlockUdocs'. Se encarga de desbloquear las unidades documentales
	 * seleccionadas
	 */
	private void unlockUdocsCodeLogic(String[] udocsToUnlock,
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {
		if (udocsToUnlock != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionFondosBI fondosBI = services.lookupGestionFondosBI();
			fondosBI.unlockUnidadesDocumentales(udocsToUnlock);
		}
	}

	public void accionUnlockUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] udocsToUnlock = (String[]) getFromTemporalSession(request,
				FondosConstants.ACCION_ELEMENTOS_KEY);
		try {
			unlockUdocsCodeLogic(udocsToUnlock, mappings, form, request,
					response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void unlockUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String[] udocsToUnlock = ((RelacionForm) form)
				.getElementosUdocSeleccionados();
		try {
			unlockUdocsCodeLogic(udocsToUnlock, mappings, form, request,
					response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	public void activarReencajadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestorEstructuraDepositoBI serviceDeposito = getGestionDespositoBI(request);
		List listaFormatos = serviceDeposito.getFormatosVigentes();
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_FORMATOS_KEY, listaFormatos);

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_FORMATO_REENCAJADO,
				request);

		setReturnActionFordward(request,
				mappings.findForward("formato_reencajado"));
	}

	public void cancelarReencajadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm relacionForm = (RelacionForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		GestionRelacionesEntregaBI serviceRelaciones = getGestionRelacionesBI(request);

		relacionEACRBI.cancelarReencajadoTransaccional(relacionForm
				.getIdrelacionseleccionada());
		RelacionEntregaVO relacionVO = serviceRelaciones
				.abrirRelacionEntrega(relacionForm.getIdrelacionseleccionada());
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(services).transform(relacionVO);

		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				relacionPO);

		updateRelacionRechazada(request);
		setReturnActionFordward(request,
				mappings.findForward("redirect_to_view_relacion"));
	}

	public void seleccionFormatoReencajadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionForm relacionForm = (RelacionForm) form;
		GestionRelacionesEACRBI relacionEACRBI = getGestionRelacionesEACRBI(request);
		if (StringUtils.isNotEmpty(relacionForm.getIdformatoseleccionado())) {
			relacionEACRBI.activarReencajado(
					relacionForm.getIdrelacionseleccionada(),
					relacionForm.getIdformatoseleccionado());
			popLastInvocation(request);
			setReturnActionFordward(request,
					mappings.findForward("redirect_to_view_relacion"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Messages.getString(
									TransferenciasConstants.NECESARIO_SELECCIONAR_FORMATO_REENCAJADO,
									request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("formato_reencajado"));
		}
	}

	private void updateRelacionRechazada(HttpServletRequest request) {
		RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		if (relacion != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();
			if (relacion.isRechazada()) {
				relacion.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
				try {
					relacionBI.updateRelacion(relacion);
				} catch (ActionNotAllowedException e) {
					guardarError(request, e);
				}
			}
		}
	}
}