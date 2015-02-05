package descripcion.actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.actions.RelacionEntregaPO;
import transferencias.actions.RelacionToPO;
import transferencias.actions.TransferenciasUnidadDocumentalPO;
import transferencias.actions.TransferenciasUnidadDocumentalToPO;
import transferencias.vos.RangoVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadDocumentalVO;
import util.ErrorsTag;
import util.PaginatedList;
import util.ParamsSet;
import util.StrutsUtil;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.lock.exceptions.LockerException;
import common.lock.model.Locker;
import common.lock.model.LockerObjectTypes;
import common.lock.vos.LockVO;
import common.navigation.KeysClientsInvocations;
import common.session.vos.SessionVO;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.util.XmlFacade;
import common.view.IVisitedRowVO;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.DescripcionConstants;
import descripcion.ErrorKeys;
import descripcion.exceptions.NotDeclaredXSLFichaException;
import descripcion.exceptions.NotFoundXSLFichaException;
import descripcion.forms.FichaForm;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.format.DefFmtFichaFactory;
import descripcion.model.xsl.TemplateFactory;
import descripcion.vos.ElementoCFPO;
import descripcion.vos.FichaVO;
import descripcion.vos.FmtPrefFichaVO;
import fondos.FondosConstants;
import fondos.actions.DivisionFSToPOTransformer;
import fondos.actions.DivisionFraccionSeriePO;
import fondos.actions.UDocEnFraccionSeriePO;
import fondos.actions.UDocEnFraccionSerieToPO;
import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.UDocEnFraccionSerieVO;
import gcontrol.model.TipoAcceso;

/**
 * Acción base para la gestión de las fichas.
 */
public abstract class FichaBaseAction extends BaseAction {

	// /**
	// * Metodo que se ejecuta cuando se pulsa sobre el link de un descriptor
	// para acceder a la descripción
	// * de su ficha
	// * @param mapping
	// * @param form
	// * @param request
	// * @param response
	// */
	// protected void retrieveDescriptorExecuteLogic(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request, HttpServletResponse
	// response){
	//
	// saveCurrentInvocation(KeysClientsInvocations.DESCRIPCION_RETRIEVE_DESCRIPTOR,
	// request);
	// setReturnActionFordward(request,
	// mapping.findForward("retrieveDescriptor"));
	//
	// }

	/**
	 * Muestra la ficha de descripción.
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
	protected void retrieveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de retrieveExecuteLogic");

		setInTemporalSession(request, "usarCache", new Boolean(true));
		boolean hayErrores = false;

		if (getTipoFicha() == TipoFicha.FICHA_ELEMENTO_CF) {
			// Comprobar si el elemento tiene ficha asociada. si no la tiene
			// mostrar mensaje de error
			// Obtener la información del elemento del cuadro de clasificación
			String idElemento = request.getParameter(Constants.ID);
			if (!StringUtils.isEmpty(idElemento)) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionCuadroClasificacionBI cuadroBI = services
						.lookupGestionCuadroClasificacionBI();
				ElementoCuadroClasificacionVO elementoCF = cuadroBI
						.getElementoCuadroClasificacion(idElemento);
				if (elementoCF == null || elementoCF.getIdFichaDescr() == null) {
					obtenerErrores(request, true)
							.add(ErrorKeys.ERROR_DESCRIPCION_RETRIEVE_FICHA,
									new ActionError(
											ErrorKeys.ERROR_DESCRIPCION_RETRIEVE_FICHA));
					hayErrores = true;
					goLastClientExecuteLogic(mapping, form, request, response);
				}
			}
		}

		if (!hayErrores) {
			// Componer la ficha
			Ficha ficha = getFicha(request, TipoAcceso.CONSULTA, false);

			saveCurrentInvocation(request, TipoAcceso.CONSULTA);

			// Guardar la información del objeto descrito
			if (getTipoFicha() == TipoFicha.FICHA_ELEMENTO_CF) {

				setInTemporalSession(request,
						DescripcionConstants.OBJETO_DESCRITO_KEY,
						getGestionCuadroClasificacionBI(request)
								.getElementoCuadroClasificacion(ficha.getId()));
				/*
				 * TODO: Revisar por qué esto estaba así ¿? if(object!=null){
				 * object=getGestionCuadroClasificacionBI(request)
				 * .getElementoCuadroClasificacion(ficha.getId()); } else
				 * setInTemporalSession(request,
				 * DescripcionConstants.OBJETO_DESCRITO_KEY,
				 * getGestionCuadroClasificacionBI(request)
				 * .getElementoCuadroClasificacion(ficha.getId()));
				 */

				// Añadido para marcar los elementos visitados
				String id = request.getParameter(Constants.ID);
				// Obtener la lista de elementos resultado de la búsqueda para
				// marcar el seleccionado
				List elements = null;
				PaginatedList paginatedList = (PaginatedList) getFromTemporalSession(
						request, DescripcionConstants.ELEMENTOS_KEY);
				if (paginatedList != null) {
					elements = paginatedList.getList();
				}

				Map mapVisitedElements = (Map) getFromTemporalSession(request,
						DescripcionConstants.MAP_ELEMENTOS_CF_VISITADOS);
				if ((elements != null) && (!elements.isEmpty())) {
					ListIterator it = elements.listIterator();
					while (it.hasNext()) {
						// ElementoCuadroClasificacion element =
						// (ElementoCuadroClasificacion) it.next();
						ElementoCFPO element = (ElementoCFPO) it.next();
						if (element.getId().equals(id)) {
							element.setRowStyle(IVisitedRowVO.CSS_FILA_CARGADA);
							if (mapVisitedElements != null)
								mapVisitedElements.put(id, id);
							break;
						}
					}
				}
			} else {
				if (getTipoFicha() == TipoFicha.FICHA_UDOCRE) {
					/*
					 * TODO: Revisar por qué esto estaba así ¿?
					 * object=getFromTemporalSession(request,
					 * DescripcionConstants.OBJETO_DESCRITO_KEY); if
					 * (object!=null) object=getGestionRelacionesBI(request)
					 * .getUnidadDocumentalConInfoDesc(ficha.getId());
					 */
					setInTemporalSession(
							request,
							DescripcionConstants.OBJETO_DESCRITO_KEY,
							getGestionRelacionesBI(request)
									.getUnidadDocumentalConInfoDesc(
											ficha.getId()));
				} else {
					if (getTipoFicha() == TipoFicha.FICHA_UDOCFS) {
						setInTemporalSession(request,
								DescripcionConstants.OBJETO_DESCRITO_KEY,
								getGestionFraccionSerieBI(request)
										.getUDocEnDivisionFS(ficha.getId()));
					} else {
						/*
						 * Object object = getFromTemporalSession(request,
						 * DescripcionConstants.OBJETO_DESCRITO_KEY);
						 * if(object!=null){
						 * object=getGestionDescripcionBI(request)
						 * .getDescriptor(ficha.getId()); } else{
						 */
						setInTemporalSession(
								request,
								DescripcionConstants.OBJETO_DESCRITO_KEY,
								getGestionDescripcionBI(request).getDescriptor(
										ficha.getId()));
						// }
					}
				}
			}

			// XML de la ficha para la presentación
			request.setAttribute(DescripcionConstants.FICHA_XML_KEY,
					ficha.toString());
			// setInTemporalSession(request, DescripcionConstants.FICHA_XML_KEY,
			// ficha.toString());

			// Plantilla XSL para presentar la ficha
			request.setAttribute(DescripcionConstants.FICHA_XSL_KEY,
					TemplateFactory.getInstance(getServiceClient(request))
							.getTemplate(TipoAcceso.CONSULTA));
			// setInTemporalSession(request, DescripcionConstants.FICHA_XSL_KEY,
			// TemplateFactory.getInstance(getServiceClient(request)).getTemplate(TipoAcceso.CONSULTA));

			// Modo de visualización
			// request.setAttribute(DescripcionConstants.MODO_VISUALIZACION_KEY,
			// DescripcionConstants.MODO_VISUALIZACION_SOLO_LECTURA);
			setInTemporalSession(request,
					DescripcionConstants.MODO_VISUALIZACION_KEY,
					DescripcionConstants.MODO_VISUALIZACION_SOLO_LECTURA);

			// Tipo de ficha
			request.setAttribute(DescripcionConstants.TIPO_FICHA_KEY,
					getTipoFichaDescriptora());

			// Boton de generar automáticos
			request.setAttribute(
					DescripcionConstants.MOSTRAR_BOTON_AUTOMATICOS_KEY,
					new Boolean(ficha.isAutomaticos()));

			// Generar el path de contexto para poder llamar desde Javascript
			FichaForm fichaForm = (FichaForm) form;
			fichaForm.setContextPath(request.getContextPath());
			fichaForm.setIdFormato(ficha.getIdFormato());
			fichaForm.setIdFicha(ficha.getIdFicha());
			fichaForm.setTipoAcceso(String.valueOf(ficha.getTipoAcceso()));

			setReturnActionFordward(request, mapping.findForward("ver_ficha"));
		}
	}

	private void checkBloqueo(Ficha ficha, Locker locker,
			ServiceRepository services, HttpServletRequest request) {
		boolean desbloquear = false;
		// buscar el idElemento en AGOBJBLOQUEO
		LockVO bloqueo = locker.getBloqueo(ficha.getId(),
				getLockerObjectType(), ArchivoModules.DESCRIPCION_MODULE);
		if (bloqueo == null)
			return;
		String idUser = bloqueo.getIdUsuario();
		if (StringUtils.isEmpty(idUser))
			desbloquear = true;

		SessionVO sesion = null;
		if (!desbloquear) {
			// si esta buscar con el id de usuario en AASESSION
			sesion = services.lookupGestionSessionBI().getSessionUser(idUser);
			if (sesion == null)
				desbloquear = true;
		}

		if (!desbloquear) {
			// comprobar que no haya pasado mas de 60000 milisegundos (1 min)
			long miliseconds = DateUtils.getFechaActual().getTime()
					- sesion.getKeepAlive().getTime();
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			String KEEP_ALIVE_PERIOD = csa.getConfiguracionGeneral()
					.getKeepAlive();
			if (StringUtils.isEmpty(KEEP_ALIVE_PERIOD))
				KEEP_ALIVE_PERIOD = "60000";
			long SESSION_EXPIRED_TIME = -1;
			try {
				SESSION_EXPIRED_TIME = Long.parseLong(KEEP_ALIVE_PERIOD, 10);
			} catch (RuntimeException re) {
				SESSION_EXPIRED_TIME = 60000;
				logger.error(
						"error al convertir el valor de KEEP_ALIVE_PERIOD: "
								+ KEEP_ALIVE_PERIOD, re);
			} catch (Exception e) {
				SESSION_EXPIRED_TIME = 60000;
			}
			if (miliseconds > SESSION_EXPIRED_TIME)
				desbloquear = true;
		}

		if (desbloquear) {
			// si es asi, quitar el bloqueo del elemento.
			locker.desbloquea(idUser, ficha.getId(), getLockerObjectType(),
					ArchivoModules.DESCRIPCION_MODULE);
		}
	}

	protected void saveCurrentInvocation(HttpServletRequest request,
			int tipoAcceso) {

		String currentInvocation = request
				.getParameter(Constants.CURRENT_INVOCATION);

		if (tipoAcceso == TipoAcceso.EDICION) {
			if (Constants.DESCRIPTOR.equals(currentInvocation)) {
				saveCurrentInvocation(
						KeysClientsInvocations.DESCRIPCION_EDIT_DESCRIPTOR,
						request);
			} else {
				if (Constants.ELEMENTO.equals(currentInvocation)) {
					saveCurrentInvocation(
							KeysClientsInvocations.DESCRIPCION_EDIT_ELEMENTO,
							request);
				} else {
					if (Constants.UDOCRE.equals(currentInvocation)) {
						saveCurrentInvocation(
								KeysClientsInvocations.DESCRIPCION_EDIT_UDOCRE,
								request);
					} else {
						if (Constants.FSRE.equals(currentInvocation)) {
							saveCurrentInvocation(
									KeysClientsInvocations.DESCRIPCION_EDIT_FSRE,
									request);
						} else {
							// desde la división de fracción de serie cuando se
							// accede desde los fondos
							// no debe hacerse así
							if (Constants.UDOCFS.equals(currentInvocation)
									|| Constants.FSFS.equals(currentInvocation)) {
								String keyInvocation = Constants.UDOCFS
										.equals(currentInvocation) ? KeysClientsInvocations.DESCRIPCION_EDIT_UDOCFS
										: KeysClientsInvocations.DESCRIPCION_EDIT_FSFS;
								if (getInvocationStack(request)
										.getIndexClientInvocation(
												KeysClientsInvocations.CUADRO_CONTENIDO_SERIE) == -1) {
									saveCurrentInvocation(keyInvocation,
											request);
								} else
									saveCurrentInvocation(keyInvocation,
											request);
							} else {
								if (getInvocationStack(request)
										.isPresent(
												KeysClientsInvocations.DESCRIPCION_EDIT)) {
									saveCurrentInvocation(
											KeysClientsInvocations.DESCRIPCION_EDITAR,
											request);
								} else
									saveCurrentInvocation(
											KeysClientsInvocations.DESCRIPCION_EDIT,
											request);
							}
						}
					}
				}
			}
		} else // TipoAcceso.CONSULTA
		{

			if (Constants.DESCRIPTOR.equals(currentInvocation)) {
				saveCurrentInvocation(
						KeysClientsInvocations.DESCRIPCION_RETRIEVE_DESCRIPTOR,
						request);
			} else {
				if (Constants.ELEMENTO.equals(currentInvocation)) {
					saveCurrentInvocation(
							KeysClientsInvocations.DESCRIPCION_RETRIEVE_ELEMENTO,
							request);
				} else {
					if (Constants.UDOCRE.equals(currentInvocation)) {
						saveCurrentInvocation(
								KeysClientsInvocations.DESCRIPCION_EDIT_UDOCRE,
								request);
					} else {
						if (Constants.FSRE.equals(currentInvocation)) {
							saveCurrentInvocation(
									KeysClientsInvocations.DESCRIPCION_EDIT_FSRE,
									request);
						} else {
							saveCurrentInvocation(
									KeysClientsInvocations.DESCRIPCION_RETRIEVE,
									request);
						}
					}
				}
			}
		}
	}

	/**
	 * Muestra la ficha de descripción para su edición.
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
	protected void editExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de editExecuteLogic");

		// Recuperar el cliente de servicio
		ServiceClient serviceClient = getServiceClient(request);

		// Guardar el enlace a la página con la etiqueta de navegación adecuada
		saveCurrentInvocation(request, TipoAcceso.EDICION);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		ActionErrors errores = new ActionErrors();
		boolean hayErrores = false;
		String id = request.getParameter(Constants.ID);

		// Hacer comprobación de bloqueo en depósito sólo para las unidades
		// documentales (elementos del cuadro)
		if (getTipoFicha() == TipoFicha.FICHA_ELEMENTO_CF) {

			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();
			List udocs = depositoBI.getUDocsById(new String[] { id });

			if (!ListUtils.isEmpty(udocs)) {
				for (int i = 0; i < udocs.size(); i++) {
					UDocEnUiDepositoVO udocEnUiDepositoVO = (UDocEnUiDepositoVO) udocs
							.get(i);
					UInsDepositoVO uinsDepositoVO = depositoBI
							.getUinsEnDeposito(udocEnUiDepositoVO
									.getIduinstalacion());
					if (uinsDepositoVO.getMarcasBloqueo() > 0) {
						errores.add(
								"errors.no.permitir.editar.udoc.bloqueada",
								new ActionError(
										"errors.no.permitir.editar.udoc.bloqueada"));
						ErrorsTag.saveErrors(request, errores);
						hayErrores = true;
					}
				}
			}

			GestionUnidadDocumentalBI unidadDocumentalBI = services
					.lookupGestionUnidadDocumentalBI();
			int marcasBloqueo = unidadDocumentalBI.getMarcasBloqueo(id);

			if (marcasBloqueo > 0) {
				errores.add("errors.no.permitir.editar.udoc.bloqueada",
						new ActionError(
								"errors.no.permitir.editar.udoc.bloqueada"));
				ErrorsTag.saveErrors(request, errores);
				hayErrores = true;
			}
		}

		if (!hayErrores) {
			try {

				// Componer la ficha ficha = ficha desde la cual duplicar los
				// datos
				// nuevaFicha ficha aun sin rellenar de la udoc recien creada
				Ficha ficha = getFicha(request, TipoAcceso.EDICION, false);

				// Bloquear la ficha en caso de edición
				Locker locker = new Locker(getServiceClient(request));
				checkBloqueo(ficha, locker, services, request);

				locker.bloquea(serviceClient, ficha.getId(),
						getLockerObjectType(),
						ArchivoModules.DESCRIPCION_MODULE);

				// XML de la ficha para la presentación
				request.setAttribute(DescripcionConstants.FICHA_XML_KEY,
						ficha.toString());
				// setInTemporalSession(request,
				// DescripcionConstants.FICHA_XML_KEY, ficha.toString());

				// Plantilla XSL para presentar la ficha
				request.setAttribute(
						DescripcionConstants.FICHA_XSL_KEY,
						TemplateFactory.getInstance(serviceClient).getTemplate(
								TipoAcceso.EDICION));
				// setInTemporalSession(request,
				// DescripcionConstants.FICHA_XSL_KEY,
				// TemplateFactory.getInstance(serviceClient)
				// .getTemplate(TipoAcceso.EDICION));

				// Guardar el formato en el formulario
				FichaForm fichaForm = (FichaForm) form;
				fichaForm.setIdFormato(ficha.getIdFormato());
				fichaForm.setIdFicha(ficha.getIdFicha());
				fichaForm.setTipoAcceso(String.valueOf(ficha.getTipoAcceso()));

				// Guardar la información del objeto descrito si no lo está ya
				Object object = getFromTemporalSession(request,
						DescripcionConstants.OBJETO_DESCRITO_KEY);
				// if(object==null) {

				if (getTipoFicha() == TipoFicha.FICHA_ELEMENTO_CF) {
					object = getGestionCuadroClasificacionBI(request)
							.getElementoCuadroClasificacion(ficha.getId());
				} else {
					if (getTipoFicha() == TipoFicha.FICHA_UDOCRE) {
						object = getGestionRelacionesBI(request)
								.getUnidadDocumentalConInfoDesc(ficha.getId());
					} else {
						if (getTipoFicha() == TipoFicha.FICHA_UDOCFS) {
							object = getGestionFraccionSerieBI(request)
									.getUDocEnDivisionFS(ficha.getId());
						} else {
							object = getGestionDescripcionBI(request)
									.getDescriptor(ficha.getId());
						}
					}
				}

				if (object != null)
					setInTemporalSession(request,
							DescripcionConstants.OBJETO_DESCRITO_KEY, object);
				// }
			} catch (LockerException e) {
				logger.warn("La ficha está bloqueada: "
						+ e.getLocalizedMessage());

				// Mostrar el error
				obtenerErrores(request, true)
						.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										getTipoFicha() == TipoFicha.FICHA_DESCRIPTOR ? ErrorKeys.DESCRIPCION_BLOQUEO_DESCRIPTOR
												: ErrorKeys.DESCRIPCION_BLOQUEO_ELEMENTO,
										new Object[] {
												e.getLockVO().getUsuario(),
												e.getLockVO().getTs() }));
			}

			// Modo de visualización
			// request.setAttribute(DescripcionConstants.MODO_VISUALIZACION_KEY,
			// DescripcionConstants.MODO_VISUALIZACION_EDICION);
			setInTemporalSession(request,
					DescripcionConstants.MODO_VISUALIZACION_KEY,
					DescripcionConstants.MODO_VISUALIZACION_EDICION);

			// Tipo de ficha
			request.setAttribute(DescripcionConstants.TIPO_FICHA_KEY,
					getTipoFichaDescriptora());

			// Generar el path de contexto para poder llamar desde Javascript
			FichaForm fichaForm = (FichaForm) form;
			fichaForm.setContextPath(request.getContextPath());

			setReturnActionFordward(request, mapping.findForward("ver_ficha"));

		} else {
			popLastInvocation(request);
			goLastClientExecuteLogic(mapping, form, request, response);
		}

	}

	private UnidadDocumentalVO rellenarInfoEspecial(
			GestionRelacionesEntregaBI relacionesBI, UnidadDocumentalVO udoc) {

		String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoInicial();
		String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoFinal();

		List rangos = relacionesBI.getRangosUDocRE(udoc.getId(),
				idCampoRangoInicial, idCampoRangoFinal);

		String numExpRangos = null;
		udoc.resetRangos();
		if (rangos != null && rangos.size() > 0) {

			Iterator it = rangos.iterator();
			while (it.hasNext()) {
				RangoVO rango = (RangoVO) it.next();

				numExpRangos = (numExpRangos == null ? "" : numExpRangos
						+ Constants.DELIMITADOR_RANGOS)
						+ rango.getDesde()
						+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
						+ rango.getHasta();

				udoc.addRango(rango);
			}
		}

		udoc.setNumeroExpediente(numExpRangos);

		return udoc;
	}

	private String getNumExpFraccionSerie(
			GestionUnidadDocumentalBI unidadDocumentalBI,
			fondos.vos.UnidadDocumentalVO udoc) {

		String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoInicial();
		String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoFinal();

		List rangos = unidadDocumentalBI.getRangosUdoc(udoc.getId(),
				idCampoRangoInicial, idCampoRangoFinal);

		String numExpRangos = null;
		if (rangos != null && rangos.size() > 0) {

			Iterator it = rangos.iterator();
			while (it.hasNext()) {
				RangoVO rango = (RangoVO) it.next();

				numExpRangos = (numExpRangos == null ? "" : numExpRangos
						+ Constants.DELIMITADOR_RANGOS)
						+ rango.getDesde()
						+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
						+ rango.getHasta();

			}
		}

		return numExpRangos;
	}

	private UDocEnFraccionSerieVO rellenarInfoEspecial(
			GestionFraccionSerieBI fraccionSerieBI,
			UDocEnFraccionSerieVO udocEnFSVO) {

		String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoInicial();
		String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoFinal();

		List rangos = fraccionSerieBI.getRangosUDoc(udocEnFSVO.getIdUDoc(),
				idCampoRangoInicial, idCampoRangoFinal);

		String numExpRangos = null;
		udocEnFSVO.resetRangos();
		if (rangos != null && rangos.size() > 0) {

			Iterator it = rangos.iterator();
			while (it.hasNext()) {
				RangoVO rango = (RangoVO) it.next();

				numExpRangos = (numExpRangos == null ? "" : numExpRangos
						+ Constants.DELIMITADOR_RANGOS)
						+ rango.getDesde()
						+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
						+ rango.getHasta();

				udocEnFSVO.addRango(rango);
			}
		}

		udocEnFSVO.setNumExp(numExpRangos);

		return udocEnFSVO;
	}

	/**
	 * Guarda los cambios en la ficha de descripción.
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
	protected void saveExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {
		logger.info("Inicio de saveExecuteLogic");

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();
		GestionUnidadDocumentalBI unidadDocumentalBI = services
				.lookupGestionUnidadDocumentalBI();

		// Guardar la ficha
		guardarFicha(form, request);

		// Modo de visualización
		// request.setAttribute(DescripcionConstants.MODO_VISUALIZACION_KEY,
		// DescripcionConstants.MODO_VISUALIZACION_EDICION);
		setInTemporalSession(request,
				DescripcionConstants.MODO_VISUALIZACION_KEY,
				DescripcionConstants.MODO_VISUALIZACION_EDICION);

		// Tipo de ficha
		request.setAttribute(DescripcionConstants.TIPO_FICHA_KEY,
				getTipoFichaDescriptora());

		ActionForward forward = mapping.findForward("ver_ficha");
		ActionErrors errores = obtenerErrores(request, false);

		if ((errores == null || (errores != null && errores.size() == 0))
				&& (getTipoFicha() == TipoFicha.FICHA_ELEMENTO_CF)) {

			String id = request.getParameter(Constants.ID);
			fondos.vos.UnidadDocumentalVO udoc = unidadDocumentalBI
					.getUnidadDocumental(id);

			// Actualizar la información de número de expediente en caso de
			// tratarse de una fracción de serie a rango ini .. rango fin
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			if (relacionEntrega != null && udoc != null) {

				RelacionEntregaPO rePO = (RelacionEntregaPO) RelacionToPO
						.getInstance(services).transform(relacionEntrega);

				if (udoc.isSubtipoCaja()
						|| rePO.isNivelDocumentalFraccionSerie()) {
					String numExp = getNumExpFraccionSerie(unidadDocumentalBI,
							udoc);
					unidadDocumentalBI.updateNumeroExpediente(id, numExp);
				}
			}
		}

		// Si no hay errores y estamos en un tipo de ficha de unidad documental
		// en relación de entrega,
		// después de guardar habría que ir a crear una nueva o a duplicar
		if ((errores == null || (errores != null && errores.size() == 0))
				&& (getTipoFicha() == TipoFicha.FICHA_UDOCRE || getTipoFicha() == TipoFicha.FICHA_UDOCFS)) {

			String mantenerInformacion = request
					.getParameter("mantenerInformacion");
			// Si este parámetro no existe, sabemos que simplemente tenemos que
			// guardar y seguir en la misma pantalla
			if (mantenerInformacion != null
					&& !mantenerInformacion.equals("-1")) {
				// redirigir a guardar y nueva unidad documental
				if (getTipoFicha() == TipoFicha.FICHA_UDOCRE)
					forward = redirectForwardMethod(request,
							"/gestionUdocsRelacion", "method",
							"guardarCambiosYNuevaConFicha");
				else
					// TipoFicha == FICHA_UDOCFS
					forward = redirectForwardMethod(request,
							"/gestionUDocsEnFS", "method",
							"guardarCambiosYNuevaConFicha");

				// ParamsSet params = new
				// ParamsSet().append("mantenerInformacion",fichaForm.getMantenerInformacion());
				ParamsSet params = new ParamsSet().append(
						"mantenerInformacion", mantenerInformacion);
				StrutsUtil.setParamsToForward(forward, params);

				// Sacar la última invocación de la pila porque volvemos atrás a
				// crear una unidad documental nueva

				/*
				 * if (mantenerInformacion.equals(Constants.STRING_CERO)) {
				 * popLastInvocation(request); } else {
				 */
				// Refrescamos la información acerca de la unidad documental en
				// sesión
				// Si hay que mantener la información, metemos en sesión la
				// unidad documental original de la que queremos copiar los
				// datos una vez que la hemos modificado

				String id = request.getParameter(Constants.ID);

				if (getTipoFicha() == TipoFicha.FICHA_UDOCRE) {
					UnidadDocumentalVO udoc = relacionesBI
							.getUnidadDocumental(id);

					// Actualizar la información de número de expediente en caso
					// de tratarse de una fracción de serie a rango ini .. rango
					// fin
					RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
							request, TransferenciasConstants.RELACION_KEY);
					RelacionEntregaPO rePO = (RelacionEntregaPO) RelacionToPO
							.getInstance(services).transform(relacionEntrega);

					if (rePO.isNivelDocumentalFraccionSerie()) {
						udoc = rellenarInfoEspecial(relacionesBI, udoc);
						relacionesBI.modificarInformacionUnidadDocumental(
								relacionEntrega, udoc);
					}

					TransferenciasUnidadDocumentalPO udocPO = (TransferenciasUnidadDocumentalPO) TransferenciasUnidadDocumentalToPO
							.getInstance(services).transform(udoc);
					setInTemporalSession(request,
							TransferenciasConstants.UNIDAD_DOCUMENTAL, udocPO);
				} else {
					if (getTipoFicha() == TipoFicha.FICHA_UDOCFS) {
						UDocEnFraccionSerieVO udocEnFSVO = fraccionSerieBI
								.getUDocEnDivisionFS(id);

						// Actualizar la información de número de expediente en
						// caso de tratarse de una fracción de serie rango ini
						// .. rango fin
						DivisionFraccionSerieVO divisionFS = (DivisionFraccionSerieVO) getFromTemporalSession(
								request,
								FondosConstants.DIVISION_FRACCION_SERIE);
						DivisionFraccionSeriePO dFSPO = (DivisionFraccionSeriePO) DivisionFSToPOTransformer
								.getInstance(services).transform(divisionFS);

						if (dFSPO.isNivelDocumentalFraccionSerie()) {
							udocEnFSVO = rellenarInfoEspecial(fraccionSerieBI,
									udocEnFSVO);
							fraccionSerieBI.updateUDocEnDivisionFS(udocEnFSVO);
						}

						// if
						// (mantenerInformacion.equals(Constants.STRING_CERO)) {
						// popLastInvocation(request);
						// }
						// else {
						UDocEnFraccionSeriePO udocEnFSPO = (UDocEnFraccionSeriePO) UDocEnFraccionSerieToPO
								.getInstance(services).transform(udocEnFSVO);
						setInTemporalSession(request,
								FondosConstants.UNIDAD_DOCUMENTAL_EN_FS,
								udocEnFSPO);
						// }
					}
				}

				// }
			} else { // Este caso se daría cuando se está modificando la
						// descripción en relación de entrega de una unidad
						// documental
						// o estando enviada la relación pero marcada la unidad
						// como con errores de cotejo

				String id = request.getParameter(Constants.ID);

				if (getTipoFicha() == TipoFicha.FICHA_UDOCRE) {
					UnidadDocumentalVO udoc = relacionesBI
							.getUnidadDocumental(id);
					RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
							request, TransferenciasConstants.RELACION_KEY);
					RelacionEntregaPO rePO = (RelacionEntregaPO) RelacionToPO
							.getInstance(services).transform(relacionEntrega);

					if (rePO.isNivelDocumentalFraccionSerie())
						udoc = rellenarInfoEspecial(relacionesBI, udoc);

					relacionesBI.modificarInformacionUnidadDocumental(
							relacionEntrega, udoc);
				} else { // TipoFicha == FICHA_UDOCFS
					UDocEnFraccionSerieVO udocEnFSVO = fraccionSerieBI
							.getUDocEnDivisionFS(id);
					DivisionFraccionSerieVO divisionFS = (DivisionFraccionSerieVO) getFromTemporalSession(
							request, FondosConstants.DIVISION_FRACCION_SERIE);
					DivisionFraccionSeriePO dFSPO = (DivisionFraccionSeriePO) DivisionFSToPOTransformer
							.getInstance(services).transform(divisionFS);

					if (dFSPO.isNivelDocumentalFraccionSerie()) {
						udocEnFSVO = rellenarInfoEspecial(fraccionSerieBI,
								udocEnFSVO);
						fraccionSerieBI.updateUDocEnDivisionFS(udocEnFSVO);
					}
				}
			}
		}

		setReturnActionFordward(request, forward);
	}

	/**
	 * Metodo encargado de guardar la ficha cuando estamos en modo edicion y
	 * cuando intentamos exportar la ficha a pdf en modo edicion
	 *
	 * @param form
	 * @param request
	 */
	private void guardarFicha(ActionForm form, HttpServletRequest request) {
		// Recuperar el cliente de servicio
		ServiceClient serviceClient = getServiceClient(request);

		// Componer la ficha
		Ficha ficha = getFicha(request, TipoAcceso.EDICION, false);
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Comprobar el bloqueo de la ficha por el usuario
		Locker locker = new Locker(getServiceClient(request));
		if (locker.bloqueadoPorUsuario(serviceClient, ficha.getId(),
				getLockerObjectType(), ArchivoModules.DESCRIPCION_MODULE)) {
			// Actualizar la copia de la ficha
			ActionErrors errores = descripcionBI.modificarValoresFicha(ficha,
					request.getParameterMap());
			if (errores.isEmpty()) {
				// Guardar la ficha
				descripcionBI.updateFicha(ficha);
				ficha = getFicha(request, TipoAcceso.EDICION, false);
			} else {
				// Mostrar errores
				obtenerErrores(request, true).add(errores);
			}

			// XML de la ficha para la presentación
			request.setAttribute(DescripcionConstants.FICHA_XML_KEY,
					ficha.toString());

			// Plantilla XSL para presentar la ficha
			request.setAttribute(
					DescripcionConstants.FICHA_XSL_KEY,
					TemplateFactory.getInstance(serviceClient).getTemplate(
							TipoAcceso.EDICION));

			// Guardar el id de formato de la ficha
			FichaForm fichaForm = (FichaForm) form;
			fichaForm.setIdFormato(ficha.getIdFormato());
			fichaForm.setIdFicha(ficha.getIdFicha());
			fichaForm.setTipoAcceso(String.valueOf(ficha.getTipoAcceso()));
		} else {
			// Mostrar error
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									getTipoFicha() == TipoFicha.FICHA_DESCRIPTOR ? ErrorKeys.DESCRIPCION_BLOQUEO_DESCRIPTOR_NO_BLOQUEADO
											: ErrorKeys.DESCRIPCION_BLOQUEO_ELEMENTO_NO_BLOQUEADO));
		}
	}

	/**
	 * Cancela la edición de la ficha de descripción.
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
	protected void cancelExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de cancelExecuteLogic");

		// Leer el identificador del objeto descrito
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id: " + id);

		// Desbloquear la ficha de descripción
		Locker locker = new Locker(getServiceClient(request));
		locker.desbloquea(getServiceClient(request), id, getLockerObjectType(),
				ArchivoModules.DESCRIPCION_MODULE);

		// Volver a la página anterior
		goBackExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Actualiza los valores automáticos de la ficha asociada al elemento.
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
	protected void updateExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inicio de updateExecuteLogic");

		// Leer el identificador del objeto descrito
		String id = request.getParameter(Constants.ID);

		// Actualiza los valores automáticos
		getGestionDescripcionBI(request).generarAutomaticos(id, getTipoFicha(),
				null);

		// Volver a la página anterior
		goLastClientExecuteLogic(mapping, form, request, response);
	}

	/**
	 * Se encarga de exportar la ficha tal y como esta a formato PDF
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
	protected void exportarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		ServiceClient serviceClient = getServiceClient(request);
		logger.info("Inicio de exportarExecuteLogic");
		try {
			Ficha ficha = getFicha(request, TipoAcceso.CONSULTA, true);
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			// Comprobar si estamos en modo consulta o edicion
			String modo = (String) getFromTemporalSession(request,
					DescripcionConstants.MODO_VISUALIZACION_KEY);
			if (DescripcionConstants.MODO_VISUALIZACION_EDICION.equals(modo)) {
				// No recupero los errores porque simplemente cojo lo que hay en
				// el formulario, no lo guardo en base de datos.
				descripcionBI.modificarValoresFicha(ficha,
						request.getParameterMap());
			}
			FichaVO fichaVO = descripcionBI.getFicha(ficha.getIdFicha());
			String xml = ficha.toString();
			XmlFacade xmlFacade = new XmlFacade(xml);
			Document doc = xmlFacade.getDocument();
			Node root = xmlFacade.getDocument().getDocumentElement();
			Node nameFicha = doc.createAttribute(Messages.getString(
					"archigest.archivo.ficha.exportar.nombreFicha",
					request.getLocale()));
			Text texto = doc.createTextNode(fichaVO.getNombre());
			nameFicha.appendChild(texto);
			root.getAttributes().setNamedItem(nameFicha);
			String xsl = TemplateFactory.getInstance(serviceClient)
					.getTemplate(TipoAcceso.EXPORTACION);
			OutputStream out = null;
			if (!StringUtils.isEmpty(xml) && !StringUtils.isEmpty(xsl)) {
				out = convertXML2PDF(root, xsl);
				download(response, "ficha.pdf",
						((ByteArrayOutputStream) out).toByteArray());
			}
			setReturnActionFordward(request, null);
		} catch (NotFoundXSLFichaException f) {
			logger.error("Error al intentar recuperar la XSL de la ficha");
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERRORS_NO_FOUND_XSL_FICHA_PDF));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (NotDeclaredXSLFichaException f) {
			logger.error("Error al intentar leer la XSL de la ficha desde el fichero de configuracion");
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERRORS_NO_DECLARED_XSL_FICHA_PDF));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (Exception e) {
			logger.error("Error al exportar a PDF: " + e);
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERRORS_EXPORTAR_FICHA_PDF));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Genera un PDF a partir de un XML y una XSL mediante XSL:FO
	 *
	 * @param xml
	 * @param xsl
	 * @return
	 * @throws IOException
	 * @throws FOPException
	 */
	private OutputStream convertXML2PDF(Node xml, String xsl)
			throws IOException, FOPException {
		FopFactory fopFactory = FopFactory.newInstance();
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		OutputStream out = new ByteArrayOutputStream();
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
					out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					new StringReader(xsl)));
			DOMSource src = new DOMSource(xml);
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			out.close();
		}
		return out;
	}

	/**
	 * Permite mostrar una nueva pantalla para cambiar el formato preferente de
	 * la ficha
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
	protected void formCambiarFormatoPreferenteExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de formCambiarFormatoPreferenteExecuteLogic");

		// Leer el identificador de la ficha
		String idFicha = request.getParameter(Constants.ID_FICHA);

		// Leer el identificador del formato
		String idFormato = request.getParameter(Constants.ID_FORMATO_FICHA);

		// Leer el tipo de acceso a la ficha
		String strTipoAcceso = request
				.getParameter(Constants.TIPO_ACCESO_FICHA);
		int tipoAcceso = TypeConverter.toInt(strTipoAcceso);

		// Guardar la invocación
		saveCurrentInvocation(
				KeysClientsInvocations.FORMATO_FICHAS_CAMBIO_FORMATO_PREFERENTE,
				request);

		// Obtener los posibles formatos con los que mostrar la ficha
		List ltFormatos = DefFmtFichaFactory.getInstance(
				getServiceClient(request)).findFmtFichaAccesibles(idFicha,
				tipoAcceso);

		// Guardar en la request el tipo de ficha
		request.setAttribute(DescripcionConstants.TIPO_FICHA_KEY,
				getTipoFichaDescriptora());

		// Guardar en la request los posibles formatos
		request.setAttribute(DescripcionConstants.LISTA_FORMATOS_FICHA_KEY,
				ltFormatos);

		// Guardar en el formulario el formato actual
		FichaForm fichaForm = (FichaForm) form;
		fichaForm.setIdFormato(idFormato);
		fichaForm.setIdFicha(idFicha);
		fichaForm.setTipoAcceso(strTipoAcceso);

		// Redirigir al forward para mostrar los formatos
		setReturnActionFordward(request,
				mapping.findForward("form_cambio_formato_preferente"));

	}

	/**
	 * Permite cambiar el formato preferente para el usuario conectado, la ficha
	 * actual y el tipo de formato actual
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
	protected void saveFormatoPreferenteExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Inicio de saveFormatoPreferenteExecuteLogic");

		// Leer el identificador de la ficha
		String idFicha = request.getParameter(Constants.ID_FICHA);

		// Leer el identificador del formato
		String idFormato = request.getParameter(Constants.ID_FORMATO_FICHA);
		String idNuevoFormato = request
				.getParameter(Constants.ID_NUEVO_FORMATO_FICHA);

		// Leer el tipo de acceso
		String strTipoAcceso = request
				.getParameter(Constants.TIPO_ACCESO_FICHA);
		int tipoAcceso = TypeConverter.toInt(strTipoAcceso);

		if (StringUtils.isNotEmpty(idFormato)
				&& StringUtils.isNotEmpty(idNuevoFormato)
				&& !idFormato.equals(idNuevoFormato)) {

			// Actualizar el formato preferente
			ServiceClient client = getServiceClient(request);
			GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

			FmtPrefFichaVO fmtPrefFicha = descripcionBI.getFmtPrefFicha(
					idFicha, client.getId(), tipoAcceso);
			fmtPrefFicha.setIdFmt(idNuevoFormato);

			descripcionBI.updateFmtPrefFicha(fmtPrefFicha);
		}

		// Volver a la invocación anterior
		goBackExecuteLogic(mapping, form, request, response);

	}

	/**
	 * Obtiene la ficha de descripción.
	 *
	 * @param request
	 *            {@link HttpServletRequest}.
	 * @param tipoAcceso
	 *            Tipo de acceso {@link TipoAcceso}.
	 * @param exportacion
	 * @return Ficha de descripción.
	 */
	protected Ficha getFicha(HttpServletRequest request, int tipoAcceso,
			boolean exportacion) {
		GestionDescripcionBI descripcionBI = getGestionDescripcionBI(request);

		// Leer el identificador del objeto descrito
		String id = request.getParameter(Constants.ID);
		if (logger.isDebugEnabled() && StringUtils.isNotEmpty(id))
			logger.debug("Id:" + id);

		Ficha ficha = null;
		String idFicha = null;

		if (getTipoFicha() == TipoFicha.FICHA_UDOCRE
				|| getTipoFicha() == TipoFicha.FICHA_UDOCFS)
			idFicha = request.getParameter(Constants.ID_FICHA);

		ficha = descripcionBI.componerFicha(id, getTipoFicha(), tipoAcceso,
				idFicha, exportacion);

		// Componer la ficha
		// Ficha ficha = getGestionDescripcionBI(request)
		// .componerFicha(id, getTipoFicha(), tipoAcceso);

		if (logger.isDebugEnabled())
			logger.debug("Ficha:" + Constants.NEWLINE + ficha.toString());

		return ficha;
	}

	/**
	 * Obtiene el tipo de objeto bloqueado.
	 *
	 * @return Tipo de objeto bloqueado {@link LockerObjectTypes}.
	 */
	protected int getLockerObjectType() {
		return (getTipoFicha() == TipoFicha.FICHA_DESCRIPTOR ? LockerObjectTypes.DESCRIPTOR
				: LockerObjectTypes.ELEMENTO);
	}

	/**
	 * Obtiene el tipo de ficha descriptora.
	 *
	 * @return Tipo de ficha descriptora {@link DescripcionConstants}.
	 */
	protected String getTipoFichaDescriptora() {
		String tipoFicha = null;

		if (getTipoFicha() == TipoFicha.FICHA_DESCRIPTOR)
			tipoFicha = DescripcionConstants.TIPO_FICHA_ISAAR;
		else if (getTipoFicha() == TipoFicha.FICHA_UDOCRE)
			tipoFicha = DescripcionConstants.TIPO_FICHA_UDOCRE;
		else if (getTipoFicha() == TipoFicha.FICHA_UDOCFS)
			tipoFicha = DescripcionConstants.TIPO_FICHA_UDOCFS;
		else
			tipoFicha = DescripcionConstants.TIPO_FICHA_ISADG;

		return tipoFicha;
	}

	/**
	 * Obtiene el tipo de ficha.
	 *
	 * @return Tipo de ficha {@link TipoFicha}.
	 */
	protected abstract int getTipoFicha();




}