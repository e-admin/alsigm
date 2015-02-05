package fondos.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.BaseAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.view.ErrorKeys;
import common.vos.UniqueGuid;

import fondos.FondosConstants;
import fondos.forms.IdentificacionSerieForm;
import fondos.model.IdentificacionSerie;
import fondos.utils.ProductoresUtils;
import fondos.vos.IInfoProductorSerie;
import fondos.vos.InfoOrganoProductorSerie;
import fondos.vos.InfoProdVigenteHistorico;
import fondos.vos.InfoProductorSerie;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import gcontrol.vos.CAOrganoVO;

public class BaseIdentificacionAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BaseIdentificacionAction.class);

	protected void fillFrmFromIdentificacionSerie(
			IdentificacionSerie identificacionSerie, IdentificacionSerieForm frm) {

		logger.debug("inicio fillFrmFromIdentificacionSerie");

		frm.setDefinicion(identificacionSerie.getDefinicion());
		frm.setTramites(identificacionSerie.getTramites());
		frm.setNormativa(identificacionSerie.getNormativa());
		frm.setDocumentosbasicos(identificacionSerie
				.getDocsBasicosUnidadDocumental());
		if (ConfigConstants.getInstance()
				.getMostrarInformacionIdentificacionExtendia()) {
			frm.setVolumenPrevisionAnual(identificacionSerie
					.getVolumenPrevisionAnual());
			frm.setSoportePrevisionAnual(identificacionSerie
					.getSoportePrevisionAnual());
			frm.setTipoDocumentacion(identificacionSerie.getTipoDocumentacion());
		}

		logger.debug("fin fillFrmFromIdentificacionSerie");
	}

	protected void excepcionIdentificacionProductores(ActionMapping mappings,
			HttpServletRequest request, Exception exception) {

		obtenerErrores(request, true).add(
				Constants.ERROR_GENERAL_MESSAGE,
				new ActionError(Constants.ERRORS_IDENTIFICACION_PRODUCTORES,
						exception.toString(), request.getLocale()));

		logger.error(exception);

		setReturnActionFordward(request,
				mappings.findForward("identificacion_serie"));

	}

	protected void darDeAltaNuevosOrganos(HttpServletRequest request,
			IdentificacionSerieForm form) throws GestorOrganismosException,
			NotAvailableException, ActionNotAllowedException {

		logger.debug("inicio darDeAltaNuevosOrganos");
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();

		List listaProductoresADarDeAlta = (List) getFromTemporalSession(
				request, FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY);
		if (!ListUtils.isEmpty(listaProductoresADarDeAlta)) {
			// Obtener información del usuario conectado
			AppUser appUser = getAppUser(request);
			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((appUser != null)
					&& (StringUtils.isNotEmpty(appUser.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						appUser.getEntity());
			}

			for (int i = 0; i < listaProductoresADarDeAlta.size(); i++) {
				InfoOrganoProductorSerie infoOrganoProductorSerie = (InfoOrganoProductorSerie) listaProductoresADarDeAlta
						.get(i);

				GestorOrganismos gestorOrganismos = GestorOrganismosFactory
						.getConnectorById(
								infoOrganoProductorSerie.getSistemaExterno(),
								params);
				InfoOrgano infoOrgano = gestorOrganismos.recuperarOrgano(
						TipoAtributo.IDENTIFICADOR_ORGANO,
						infoOrganoProductorSerie.getIdEnSistemaExterno());
				CAOrganoVO organo = new CAOrganoVO();
				organo.setCodigo(infoOrgano.getCodigo());
				organo.setNombre(form.getNombreProductor()[i]);
				organo.setNombreLargo(infoOrganoProductorSerie.getNombre());
				organo.setIdArchivoReceptor(form.getArchivoPorProductor()[i]);
				organo.setSistExtGestor(infoOrganoProductorSerie
						.getSistemaExterno());
				organo.setIdOrgSExtGestor(infoOrganoProductorSerie
						.getIdEnSistemaExterno());
				organo.setDescripcion(form.getDescripcionProductor()[i]);
				controlAccesoBI.saveOrgano(organo);
			}
		}

		logger.debug("fin darDeAltaNuevosOrganos");
	}

	protected void restablecerListaProductoresVigentesOriginales(
			HttpServletRequest request) {

		logger.debug("inicio restablecerListaProductoresVigentesOriginales");
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);

		if (identificacionSerie != null) {
			setInTemporalSession(
					request,
					FondosConstants.PRODUCTORES_ORIGINALES_VIGENTES_A_ELEGIR_KEY,
					identificacionSerie.getInfoProductoresVigentesAElegir());
		}

		logger.debug("fin restablecerListaProductoresVigentesOriginales");
	}

	protected void listProductoresADarDeAlta(HttpServletRequest request,
			List listaPosiblesProductoresADarDeAlta,
			List listaProductoresADarDeAlta,
			IdentificacionSerieForm identificacionSerieForm) {

		logger.debug("inicio listProductoresADarDeAlta");

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		if (!ListUtils.isEmpty(listaPosiblesProductoresADarDeAlta)) {
			for (int i = 0; i < listaPosiblesProductoresADarDeAlta.size(); i++) {
				IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) listaPosiblesProductoresADarDeAlta
						.get(i);
				if (infoProductorSerie.getClass().equals(
						InfoOrganoProductorSerie.class)) {
					InfoOrganoProductorSerie infoOrganoProductorSerie = (InfoOrganoProductorSerie) infoProductorSerie;
					CAOrganoVO caOrganoVO = serieBI
							.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
									infoOrganoProductorSerie
											.getSistemaExterno(),
									infoOrganoProductorSerie
											.getIdEnSistemaExterno(), Boolean.TRUE);
					if (caOrganoVO == null) {
						listaProductoresADarDeAlta
								.add(infoOrganoProductorSerie);
					}
				}
			}
		}

		if (!ListUtils.isEmpty(listaProductoresADarDeAlta)) {
			List nombreProductor = new ArrayList();
			List descripcionProductor = new ArrayList();

			for (int i = 0; i < listaProductoresADarDeAlta.size(); i++) {
				InfoOrganoProductorSerie infoOrganoProductorSerie = (InfoOrganoProductorSerie) listaProductoresADarDeAlta
						.get(i);
				nombreProductor.add(infoOrganoProductorSerie.getFirstOrgano());
				descripcionProductor.add(new String(""));

			}

			identificacionSerieForm.setNombreProductor(ArrayUtils
					.toString(nombreProductor.toArray()));
			identificacionSerieForm.setDescripcionProductor(ArrayUtils
					.toString(descripcionProductor.toArray()));
		}

		logger.debug("fin listProductoresADarDeAlta");
	}

	protected void establecerGuidsProductores(HttpServletRequest request,
			List listaProductores) {

		logger.debug("inicio establecerGuidsProductores");

		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);

		if (identificacionSerie != null) {
			if (ListUtils.isNotEmpty(listaProductores)) {
				for (Iterator iterator = listaProductores.iterator(); iterator
						.hasNext();) {
					UniqueGuid productor = (UniqueGuid) iterator.next();

					if (StringUtils.isEmpty(productor.getGuid())) {
						productor.setGuid(getGuid(request));
					}
				}
			}
		}
		logger.debug("fin establecerGuidsProductores");
	}

	protected void establecerGuidsInfoOrganoProductor(
			HttpServletRequest request, List listaProductores) {

		logger.debug("inicio establecerGuidsInfoOrganoProductor");
		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);

		if (identificacionSerie != null) {
			if (ListUtils.isNotEmpty(listaProductores)) {
				for (Iterator iterator = listaProductores.iterator(); iterator
						.hasNext();) {

					Object objetoProductor = iterator.next();

					if (objetoProductor instanceof InfoOrganoProductorSerie) {
						InfoOrganoProductorSerie productor = (InfoOrganoProductorSerie) objetoProductor;

						if (StringUtils.isEmpty(productor.getGuid())) {
							productor
									.setGuid(productor.getIdEnSistemaExterno());
						}
					} else if (objetoProductor instanceof InfoProductorSerie) {
						InfoProductorSerie productor = (InfoProductorSerie) objetoProductor;

						if (StringUtils.isEmpty(productor.getGuid())) {
							productor.setGuid(productor.getIdDescriptor());
						}

					}

				}
			}
		}
		logger.debug("fin establecerGuidsInfoOrganoProductor");
	}

	protected ProductorSerieVO getProductorSustituido(
			HttpServletRequest request, IInfoProductorSerie infoProductorSerie) {

		logger.debug("inicio getProductorSustituido");

		if (infoProductorSerie.getSustituyeAHistorico() != null) {
			List listaProductoresHistoricos = (List) getFromTemporalSession(
					request, FondosConstants.PRODUCTORES_HISTORICOS_KEY);
			for (int i = 0; i < listaProductoresHistoricos.size(); i++) {
				ProductorSerieVO productorHistoricoSerieVO = (ProductorSerieVO) listaProductoresHistoricos
						.get(i);
				if (productorHistoricoSerieVO.getIdProductor()
						.equalsIgnoreCase(
								infoProductorSerie.getSustituyeAHistorico()))
					return productorHistoricoSerieVO;
			}
		}
		List sustitucionVigenteHistorico = (List) getFromTemporalSession(
				request, FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY);
		if (!ListUtils.isEmpty(sustitucionVigenteHistorico)) {
			for (int i = 0; i < sustitucionVigenteHistorico.size(); i++) {
				InfoProdVigenteHistorico infoProdVigenteHistorico = (InfoProdVigenteHistorico) sustitucionVigenteHistorico
						.get(i);
				if (infoProductorSerie.equals(infoProdVigenteHistorico
						.getSustituidor())) {
					return infoProdVigenteHistorico
							.getProductorSerieSustituido();
				}

			}
		}
		logger.debug("fin getProductorSustituido: retorna null");
		return null;
	}

	protected InfoProdVigenteHistorico getInfoProdVigenteHistorico(
			List sustitucionVigenteHistorico,
			IInfoProductorSerie infoProductorSerie) {
		if (!ListUtils.isEmpty(sustitucionVigenteHistorico)) {
			for (int i = 0; i < sustitucionVigenteHistorico.size(); i++) {
				InfoProdVigenteHistorico infoProdVigenteHistorico = (InfoProdVigenteHistorico) sustitucionVigenteHistorico
						.get(i);
				if (infoProductorSerie.equals(infoProdVigenteHistorico
						.getSustituidor())) {
					return infoProdVigenteHistorico;
				}

			}
		}
		return null;
	}

	protected IInfoProductorSerie getProductorSustituidor(
			HttpServletRequest request, ProductorSerieVO productorSustituido) {
		IdentificacionSerie identificacionSerie = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);
		List productoresSerie = identificacionSerie
				.getListaInfoProductoresSerie();

		List sustitucionVigenteHistorico = (List) getFromTemporalSession(
				request, FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY);
		if (!ListUtils.isEmpty(sustitucionVigenteHistorico)) {
			for (int i = 0; i < sustitucionVigenteHistorico.size(); i++) {
				InfoProdVigenteHistorico infoProdVigenteHistorico = (InfoProdVigenteHistorico) sustitucionVigenteHistorico
						.get(i);
				if (productorSustituido.equals(infoProdVigenteHistorico
						.getProductorSerieSustituido())) {
					return infoProdVigenteHistorico.getSustituidor();
				}

			}
		}

		for (int i = 0; i < productoresSerie.size(); i++) {
			IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) productoresSerie
					.get(i);
			if (infoProductorSerie.getIdDescriptor() != null
					&& infoProductorSerie.getIdDescriptor().equalsIgnoreCase(
							productorSustituido.getSustituidoVigente()))
				return infoProductorSerie;
		}
		SerieVO serie = (SerieVO) getFromTemporalSession(request,
				FondosConstants.ELEMENTO_CF_KEY);
		GestionSeriesBI seriesService = getGestionSeriesBI(request);
		List listaProductoresVigentes = seriesService
				.getProductoresVigentesBySerie(serie.getId());

		if (!ListUtils.isEmpty(listaProductoresVigentes)) {
			for (int i = 0; i < listaProductoresVigentes.size(); i++) {
				ProductorSerieVO productorVigenteSerieVO = (ProductorSerieVO) listaProductoresVigentes
						.get(i);

				if (productorSustituido.getIdLCAPref().equalsIgnoreCase(
						productorVigenteSerieVO.getIdLCAPref())) {
					IInfoProductorSerie infoProductorSerie = identificacionSerie
							.createInfoProductorSerie(productorVigenteSerieVO);
					return infoProductorSerie;
				}
			}
		}
		return null;
	}

	/**
	 * Obtiene la lista de Productores Vigente Originales A Elegir
    *
	 * @param request
	 * @param crear
	 * @return Lista de {@link InfoProductorSerie}
	 */
	protected List getListaProductoresVigentesOriginalesAElegir(
			HttpServletRequest request) {
		return getListaFromSession(request,
				FondosConstants.PRODUCTORES_ORIGINALES_VIGENTES_A_ELEGIR_KEY,
				true);
	}

	protected List getListaProductoresADarDeAlta(HttpServletRequest request) {
		return getListaFromSession(request,
				FondosConstants.PRODUCTORES_A_DAR_DE_ALTA_KEY, true);
	}

	protected List getInfoProductoresSerie(HttpServletRequest request) {
		IdentificacionSerie identificacion = getIdentificacionSerie(request);

		if (identificacion != null) {
			return identificacion.getListaInfoProductoresSerie();
		}
		return null;
	}

	protected IdentificacionSerie getIdentificacionSerie(
			HttpServletRequest request) {
		return (IdentificacionSerie) getFromTemporalSession(request,
				FondosConstants.IDENTIFICACION_SERIE_KEY);
	}

	protected List getListaProductoresPasadosAHistoricos(
			HttpServletRequest request) {
		return getListaFromSession(request,
				FondosConstants.PRODUCTORES_PASADOS_A_HISTORICOS_KEY, true);
	}

	protected void addProductorPasadoAHistorico(HttpServletRequest request,
			ProductorSerieVO productorSerieVO) {
		getListaProductoresPasadosAHistoricos(request).add(productorSerieVO);
	}

	/**
    *
	 * @param request
	 * @param crear
	 * @return Lista de ProductorSerieVO
	 */
	protected List getListaProductoresVigentesFromSession(
			HttpServletRequest request) {
		return getListaFromSession(request,
				FondosConstants.PRODUCTORES_VIGENTES_KEY, true);
	}

	protected List getListaSustitucionVigenteHistorico(
			HttpServletRequest request) {
		return getListaFromSession(request,
				FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY, true);
	}

	protected void addSustitucionVigenteHistorico(HttpServletRequest request,
			InfoProdVigenteHistorico infoProdVigenteHistorico) {
		getListaSustitucionVigenteHistorico(request).add(
				infoProdVigenteHistorico);
	}

	protected InfoProdVigenteHistorico getInfoVigenteHistorico(
			HttpServletRequest request, String guidSustituido) {
		List lista = getListaSustitucionVigenteHistorico(request);

		if (ListUtils.isNotEmpty(lista)) {
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				InfoProdVigenteHistorico infoProdVigenteHistorico = (InfoProdVigenteHistorico) iterator
						.next();

				String guid = null;

				if (infoProdVigenteHistorico.getProductorSerieSustituido() != null) {
					guid = infoProdVigenteHistorico
							.getProductorSerieSustituido().getGuid();
				} else if (infoProdVigenteHistorico
						.getInfoProductorSerieSustituido() != null) {
					guid = infoProdVigenteHistorico
							.getInfoProductorSerieSustituido().getGuid();
				}

				if (guidSustituido.equals(guid)) {
					return infoProdVigenteHistorico;
				}
			}
		}

		return null;
	}

	protected List getListaFromSession(HttpServletRequest request, String key,
			boolean crear) {
		List lista = (List) getFromTemporalSession(request, key);
		if (lista == null && crear) {
			lista = new ArrayList();
			setInTemporalSession(request, key, lista);
		}
		return lista;
	}

	/**
	 * Elimina un productor o lo marca para eliminar
    *
	 * @param request
	 * @param infoProductorSerie
	 */
	protected void eliminarProductor(HttpServletRequest request,
			InfoProductorSerie infoProductorSerie) {

		logger.debug("inicio eliminarProductor");

		if (infoProductorSerie != null) {
			logger.debug("productor: " + infoProductorSerie.getDebug());

			if (infoProductorSerie.isNuevo()
					&& infoProductorSerie.isSinGuardar()) {
				logger.debug("productorSerie es nuevo o está sin guardar");
				removeProductor(request, infoProductorSerie.getGuid());
			} else {
				infoProductorSerie.setMarcas(FondosConstants.MARCA_ELIMINADO);

				if (infoProductorSerie instanceof InfoOrganoProductorSerie) {
					InfoOrganoProductorSerie infoOrganoProductorSerie = (InfoOrganoProductorSerie) infoProductorSerie;
					infoOrganoProductorSerie.setSustituidoVigente(null);
					infoOrganoProductorSerie.setSustituyeAHistorico(null);
				}

			}
		}
	}

	protected void establecerFlagSustituyeAHistorico(HttpServletRequest request) {
		List sustitucionVigenteHistorico = (List) getFromTemporalSession(
				request, FondosConstants.SUSTITUCION_VIGENTE_HISTORICO_KEY);
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);

		if (identificacionSerie != null) {
			List listaInfoProductoresVigentes = identificacionSerie
					.getInfoProductoresVigentes();

			List listaInfoProductoresHistoricos = identificacionSerie
					.getInfoProductoresSustituidos();

			for (Iterator iterator = listaInfoProductoresVigentes.iterator(); iterator
					.hasNext();) {
				InfoProductorSerie infoProductorSerieVigente = (InfoProductorSerie) iterator
						.next();

				if (infoProductorSerieVigente != null) {

					// Recorrer la lista de Historicos
					for (Iterator it = listaInfoProductoresHistoricos
							.iterator(); it.hasNext();) {
						IInfoProductorSerie infoProductorSerieHistorico = (IInfoProductorSerie) it
								.next();

						if (infoProductorSerieHistorico != null
								&& infoProductorSerieHistorico
										.isSustituidoPorVigente()) {
							if (StringUtils
									.isNotEmpty(infoProductorSerieHistorico
											.getIdLCA())
									&& infoProductorSerieHistorico.getIdLCA()
											.equals(infoProductorSerieVigente
													.getIdLCA())) {

								infoProductorSerieVigente
										.setSustituyeAHistorico(infoProductorSerieHistorico
												.getIdProductor());

								InfoProdVigenteHistorico infoProdVigenteHistorico = new InfoProdVigenteHistorico();
								infoProdVigenteHistorico
										.setSustituidor(infoProductorSerieVigente);
								infoProdVigenteHistorico
										.setProductorSerieSustituido(infoProductorSerieHistorico
												.getProductorSerieVO());
								sustitucionVigenteHistorico
										.add(infoProdVigenteHistorico);
								infoProductorSerieHistorico
										.setSustituidoVigente(infoProductorSerieVigente
												.getIdDescriptor());
								break;
							}
						}

					}

				}

			}

		}

	}

	protected InfoProductorSerie getInfoProductorSerie(
			HttpServletRequest request, String guid) {

		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);

		if (identificacionSerie != null) {
			return identificacionSerie.getInfoProductorSerie(guid);
		}
		return null;
	}

	protected void addProductorSerie(HttpServletRequest request,
			InfoProductorSerie infoProductorSerie) {
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);
		if (identificacionSerie != null) {
			identificacionSerie.addInfoProductorSerie(infoProductorSerie);
		}
	}

	protected void addInfoProductorSerie(HttpServletRequest request,
			IInfoProductorSerie infoProductorSerie) {
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);
		if (identificacionSerie != null) {
			infoProductorSerie.setIdSerie(identificacionSerie.getIdSerie());
			identificacionSerie.addInfoProductorSerie(infoProductorSerie);
		}
	}

	protected void removeProductor(HttpServletRequest request, String guid) {
		logger.debug("inicio removeProductor con guid:" + guid);
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);
		if (identificacionSerie != null) {
			logger.debug(identificacionSerie);
			identificacionSerie.removeProductor(guid);

			// Se elimina también de la lista de productores a dar de alta
			removeObjectFromListaByGuid(guid,
					getListaProductoresADarDeAlta(request));
		}

		logger.debug("fin removeProductor con guid:" + guid);
	}

	protected void fillIdentificacionSerie(IdentificacionSerie identificacion,
			IdentificacionSerieForm form) {
		identificacion.setDenominacion(form.getDenominacion());
		identificacion.setDefinicion(form.getDefinicion());
		identificacion.setTramites(form.getTramites());
		identificacion.setNormativa(form.getNormativa());
		identificacion.setDocsBasicosUnidadDocumental(form
				.getDocumentosbasicos());
		identificacion.setInfoExtendida(form.getTipoDocumentacion(),
				form.getVolumenPrevisionAnual(),
				form.getSoportePrevisionAnual());
	}

	protected ActionErrors validateFormInSaveIdentificacion(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		IdentificacionSerieForm frm = (IdentificacionSerieForm) form;
		if (GenericValidator.isBlankOrNull(frm.getDenominacion())) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					ErrorKeys.EL_TITULO_DE_LA_SERIE_ES_NECESARIO));
		}
		return errors.size() > 0 ? errors : null;
	}

	/**
	 * Valida las fechas de vigencia
    *
	 * @param request
	 * @param fechaInicioVigencia
	 *            Fecha del Inicio de la Vigencia
	 * @param fechaFinVigencia
	 *            Fecha del Fin dela Vigencia
	 * @return
	 */
	protected ActionErrors validateFechasVigencia(HttpServletRequest request,
			ActionErrors errors, Date fechaInicioVigencia,
			String fechaFinVigencia) {
		if (!DateUtils.isDate(fechaFinVigencia)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
							request.getLocale())));
		}
		// Comprobar que la fecha introducida no se anterior a la fecha de
		// inicio.
		else if (DateUtils.getDate(fechaFinVigencia).after(
				DateUtils.getFechaActual())) {
			errors.add(
					Constants.ERROR_DATE_AFTER_TODAY,
					new ActionError(
							Constants.ERROR_DATE_AFTER_TODAY,
							Messages.getString(
									Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
									request.getLocale())));
		} else if (DateUtils.getDate(fechaFinVigencia).compareTo(
				DateUtils.getFechaSinHora(fechaInicioVigencia)) == DateUtils.FECHA_MENOR) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_DATE_NO_BEFORE,
							Messages.getString(
									Constants.ETIQUETA_FECHA_FIN_VIGENCIA_PRODUCTOR,
									request.getLocale()), Messages.getString(
									Constants.ETIQUETA_FECHA_INICIO,
									request.getLocale())));
		}

		return errors;
	}

	protected void pasarAHistorico(HttpServletRequest request,
			String guidProductor, String fechaFinVigencia, int accion) {
		IInfoProductorSerie infoProductorSerie = getInfoProductorSerie(request,
				guidProductor);
		int marcas = ProductoresUtils.getMarcaForAction(
				infoProductorSerie.getMarcas(), accion);

		infoProductorSerie.setFechaFin(fechaFinVigencia);
		infoProductorSerie.setMarcas(marcas);

	}

	/**
	 * Reliza el proceso de pasar a vigente un productor histórico - Si el
	 * productor estaba susituido, quitar la referencia en el sustiuidor
    *
	 * @param request
	 *            Request
	 * @param guid
	 *            Guid del productor
	 */
	protected void pasarAVigente(HttpServletRequest request, String guid,
			boolean eliminarSustituidor) {
		IInfoProductorSerie infoProductorSerie = getInfoProductorSerie(request,
				guid);

		if (infoProductorSerie != null && !infoProductorSerie.isVigente()) {

			List listaInfoProdVigente = getListaSustitucionVigenteHistorico(request);

			InfoProdVigenteHistorico infoProdVigenteHistorico = getInfoVigenteHistorico(
					request, guid);

			if (infoProdVigenteHistorico != null) {
				InfoProductorSerie infoProductorSerieSustituidor = infoProdVigenteHistorico
						.getSustituidor();

				if (infoProductorSerieSustituidor != null) {
					infoProductorSerieSustituidor.setSustituyeAHistorico(null);

				}

				listaInfoProdVigente.remove(infoProdVigenteHistorico);

				if (eliminarSustituidor) {
					eliminarProductor(request, infoProductorSerieSustituidor);
				}

			}

			int marcas = ProductoresUtils.getMarcaForAction(
					infoProductorSerie.getMarcas(),
					ProductoresUtils.ACCION_PASAR_A_VIGENTE);

			infoProductorSerie.setMarcas(marcas);
			infoProductorSerie.setFechaFin((Date) null);
		}
	}

	protected String getGuid(HttpServletRequest request) {
		String guid;
		try {
			guid = getGestionSeriesBI(request).getGuid();
		} catch (Exception e) {
			guid = "";
		}
		return guid;
	}

	protected UniqueGuid getObjetoByGuid(String guid, List lista) {
		if (ListUtils.isNotEmpty(lista)) {
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				UniqueGuid uniqueGuid = (UniqueGuid) iterator.next();

				if (uniqueGuid != null && uniqueGuid.getGuid().equals(guid)) {
					return uniqueGuid;
				}
			}
		}
		return null;
	}

	protected void removeObjectFromListaByGuid(String guid, List lista) {
		logger.debug("inicio removeObjectFromListaByGuid guid: " + guid);
		UniqueGuid uniqueGuid = getObjetoByGuid(guid, lista);

		if (uniqueGuid != null) {
			logger.debug("UniqueGuid:" + uniqueGuid.getGuid());
			lista.remove(uniqueGuid);
			logger.debug("UniqueGuid eliminado de la lista:"
					+ uniqueGuid.getGuid());
		}

		logger.debug("inicio removeObjectFromListaByGuid guid: " + guid);
	}

	protected boolean isObjectInLista(UniqueGuid uniqueGuid, List lista) {
		if (ListUtils.isNotEmpty(lista)) {
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				UniqueGuid uniqueGuidLista = (UniqueGuid) iterator.next();

				if (uniqueGuid != null
						&& (uniqueGuid.getGuid().equals(
								uniqueGuidLista.getGuid()) || uniqueGuid
								.equals(uniqueGuidLista))) {
					return true;
				}
			}
		}

		return false;
	}

	protected void addObjectInLista(UniqueGuid uniqueGuid, List lista) {
		if (!isObjectInLista(uniqueGuid, lista)) {
			lista.add(uniqueGuid);
		}
	}

	/**
	 * Obtiene la Lista de Productores Históricos de la Serie
    *
	 * @return Lista de {@link ProductorSerieVO}
	 */
	protected List getListaProductoresHistoricosFromSession(
			HttpServletRequest request) {
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(request);
		if (identificacionSerie != null) {
			return identificacionSerie.getProductoresHistoricos();
		}
		return null;
	}

}
