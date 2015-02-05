package transferencias.actions;

import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import transferencias.TransferenciasConstants;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocEnUIReeaCRPO;
import transferencias.vos.UbicacionUnidadInstalacionVO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.ConfigConstants;
import common.Constants;
import common.OrganizationMessages;
import common.actions.BasePDFReportAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.util.IntervalOptions;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoVO;
import deposito.vos.TipoElementoVO;

/**
 * Impresión en PDF de las cartelas de una relación de entrega.
 */
public class CartelasPDFReportAction extends BasePDFReportAction {

	/**
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping
	 * @param form
	 *            org.apache.struts.action.ActionForm
	 * @param request
	 *            javax.servlet.http.HttpServletRequest
	 * @param response
	 *            javax.servlet.http.HttpServletResponse
	 */
	protected void preProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String idRelacion = request.getParameter("idRelacion");

		// Cajas a imprimir
		String cajas = request.getParameter("cajas");

		// Ubicaciones de las unidades de instalación de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();
		GestionArchivosBI archivoBI = service.lookupGestionArchivosBI();
		GestorEstructuraDepositoBI depositoBI = service
				.lookupGestorEstructuraDepositoBI();
		GestionRelacionesEACRBI relacionEABI = service
				.lookupGestionRelacionesEACRBI();

		// Obtenemos los mensajes
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(ConfiguracionArchivoManager.CARTELAS_ARCHIVO);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametrosUInst = new HashMap();

		// Obtener el código de Relacion
		RelacionEntregaVO relacionVO = relacionesBI
				.getRelacionXIdRelacion(idRelacion);
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(service).transform(relacionVO);

		if (relacionPO != null) {
			parametrosUInst.put(CODIGO_RELACION,
					relacionPO.getCodigoTransferencia());
		}
		addReportDir(parametrosUInst, request);
		IntervalOptions ordenes = IntervalOptions.parse(cajas);

		List cartelasCajas = relacionesBI.getUbicacionesRelacionOuterJoin(
				idRelacion, ordenes, relacionVO.getIddeposito());

		if (relacionVO.getEstado() == EstadoREntrega.SIGNATURIZADA
				.getIdentificador()
				|| relacionVO.getEstado() == EstadoREntrega.VALIDADA
						.getIdentificador()) {

			if (cartelasCajas != null && cartelasCajas.size() > 0) {
				Iterator it = cartelasCajas.iterator();
				while (it.hasNext()) {
					UbicacionUnidadInstalacionVO ubiUI = (UbicacionUnidadInstalacionVO) it
							.next();
					String idUIDeposito = StringUtils.isEmpty(ubiUI
							.getIduiubicada()) ? ubiUI.getId() : ubiUI
							.getIduiubicada();

					HuecoVO hueco = depositoBI.getHuecoUInstalacionXArchivo(
							idUIDeposito, relacionPO.getIdarchivoreceptor());
					if (hueco != null) {
						if (ordenes.isInOptions(ubiUI.getOrden())) {

							// Obtener su lista de padres y generar los valores
							// necesarios para el informe
							LinkedList ltPadres = depositoBI
									.getListaPadresHueco(hueco);
							HashMap mapElementosDepositoNombresElemento = getMapElementosDepositoNombresElemento(
									ltPadres, depositoBI);
							ubiUI.setMapElementosDepositoNombresElemento(mapElementosDepositoNombresElemento);
						}
					}
				}
			}
		}

		String nombreCortoArchivo = OrganizationMessages
				.getTitleCartelasArchivo();
		String codigoArchivo = OrganizationMessages.getTitleCartelasArchivo();

		// Si no se ha definido el título de las cartelas de archivo en el
		// fichero de organización, obtenemos el código de base de datos y esto
		// es lo que
		// utilizaremos como título
		if (StringUtils.isEmpty(nombreCortoArchivo)) {
			ArchivoVO archivo = archivoBI.getArchivoXId(relacionVO
					.getIdarchivoreceptor());
			if (archivo != null) {
				nombreCortoArchivo = archivo.getNombrecorto();
				codigoArchivo = archivo.getCodigo();
			}
		}

		parametrosUInst
				.put(LABEL_CAJA,
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_CARTELAS_OFICINA_CAJA));
		parametrosUInst.put(LABEL_ORGANIZATION, messages.getMessage(
				TransferenciasConstants.LABEL_INFORME_CARTELAS_ARCHIVO_PA,
				OrganizationMessages.getOrganization()));

		parametrosUInst.put(LABEL_TITULO, messages.getMessage(
				TransferenciasConstants.LABEL_INFORME_CARTELAS_ARCHIVO_PA,
				nombreCortoArchivo));
		parametrosUInst.put(LABEL_RELACION,
				messages.getMessage(Constants.ETIQUETA_RELACION));

		if (codigoArchivo == null)
			codigoArchivo = Constants.STRING_EMPTY;
		parametrosUInst.put(LABEL_CODIGO_ARCHIVO, codigoArchivo);

		reportParameters.add(parametrosUInst);

		// Orígenes de datos de los informes
		List dataSources = new ArrayList();

		// Lo añadimos a los resultados
		dataSources.add(new JRBeanCollectionDataSource(cartelasCajas));

		if (relacionVO.getEstado() == EstadoREntrega.SIGNATURIZADA
				.getIdentificador()
				|| relacionVO.getEstado() == EstadoREntrega.VALIDADA
						.getIdentificador()) {

			// Añadir la información de las unidades documentales de cada caja
			// sólo si están signaturadas => la relación de entrega está
			// signaturada
			if (!CollectionUtils.isEmpty(cartelasCajas)) {
				Iterator it = cartelasCajas.iterator();
				UbicacionUnidadInstalacionVO ui;
				Map parametrosUDoc;

				// Nombre del informe
				reportNames
						.add(ConfiguracionArchivoManager.CARTELAS_ARCHIVO_UDOCS);

				// Parámetros del informe
				parametrosUDoc = new HashMap();

				parametrosUDoc
						.put(LABEL_CAJA,
								messages.getMessage(TransferenciasConstants.LABEL_INFORME_CARTELAS_OFICINA_CAJA));
				parametrosUDoc
						.put(LABEL_ORGANIZATION,
								messages.getMessage(
										TransferenciasConstants.LABEL_INFORME_CARTELAS_ARCHIVO_PA,
										OrganizationMessages.getOrganization()));
				parametrosUDoc
						.put(LABEL_TITULO,
								messages.getMessage(
										TransferenciasConstants.LABEL_INFORME_CARTELAS_ARCHIVO_PA,
										nombreCortoArchivo));

				String labelSerie = messages.getMessage(
						DepositoConstants.LABEL_SERIE_CARTELAS,
						request.getLocale())
						+ Constants.DELIMITER_IDS + Constants.STRING_SPACE;
				String labelNumExp = messages.getMessage(
						DepositoConstants.LABEL_NUM_EXP_CARTELAS,
						request.getLocale())
						+ Constants.DELIMITER_IDS + Constants.STRING_SPACE;
				String labelAsunto = messages.getMessage(
						DepositoConstants.LABEL_ASUNTO_CARTELAS,
						request.getLocale())
						+ Constants.DELIMITER_IDS + Constants.STRING_SPACE;
				String labelFecha = messages.getMessage(
						DepositoConstants.LABEL_FECHA_CARTELAS,
						request.getLocale())
						+ Constants.DELIMITER_IDS + Constants.STRING_SPACE;
				parametrosUDoc.put("LABEL_SERIE", labelSerie);
				parametrosUDoc.put("LABEL_NUM_EXP", labelNumExp);
				parametrosUDoc.put("LABEL_ASUNTO", labelAsunto);
				parametrosUDoc.put("LABEL_FECHA", labelFecha);

				addReportDir(parametrosUDoc, request);

				reportParameters.add(parametrosUDoc);

				List allUDocs = new ArrayList();

				// Si la relación está validada, añadimos la información de
				// código y código de referencia a los parámetros del informe
				while (it.hasNext()) {
					ui = (UbicacionUnidadInstalacionVO) it.next();
					List uDocsEnUI;
					if (relacionPO.isEntreArchivos()) {
						if (relacionPO.isRelacionConReencajado()) {
							uDocsEnUI = relacionEABI.getUDocsByUIReencajado(ui
									.getId());
						} else {
							uDocsEnUI = relacionesBI.getUdocsEnUIEA(ui.getId());
						}
					} else {
						uDocsEnUI = relacionesBI.getUdocsEnUI(ui.getId());
					}

					if (uDocsEnUI != null) {
						if (relacionPO.isRelacionConReencajado()) {
							CollectionUtils.transform(uDocsEnUI,
									UDocEnUIReeaCRPO.getInstance(service));
						} else {
							CollectionUtils.transform(uDocsEnUI,
									ParteUnidadDocumentalPO.getInstance(
											service,
											new Integer(relacionPO
													.getTipotransferencia())));
						}
					}

					allUDocs.addAll(uDocsEnUI);
				}

				// Orígen de datos
				dataSources.add(new JRBeanCollectionDataSource(allUDocs));
			}
		}

		// Almacenar la información en el request
		request.setAttribute(REPORT_NAMES,
				(String[]) reportNames.toArray(new String[reportNames.size()]));

		request.setAttribute(REPORT_PARAMETERS, (Map[]) reportParameters
				.toArray(new Map[reportParameters.size()]));

		request.setAttribute(DATA_SOURCES,
				(Object[]) dataSources.toArray(new Object[dataSources.size()]));
	}

	/**
	 * Establece el nombre del report a generar en función del tipo de report
	 * solicitado en la request
	 * 
	 * @param javax
	 *            .servlet.http.HttpServletRequest
	 * @param javax
	 *            .servlet.http.HttpServletResponse
	 * @param org
	 *            .apache.struts.action.ActionForm
	 * @param org
	 *            .apache.struts.action.ActionMapping
	 */
	protected String[] getReportNames(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {
		// Identificador de la relación de entrega
		String tipo = request.getParameter("tipo");
		if (logger.isDebugEnabled())
			logger.debug("Tipo: " + tipo);

		if (StringUtils.isBlank(tipo) || "1".equals(tipo))
			return new String[] { ConfiguracionArchivoManager.CARTELAS_OFICINA };
		else
			return (String[]) request.getAttribute(REPORT_NAMES);

	}

	/**
	 * Establece los parametros necesarios para la generación del report.
	 * 
	 * @param javax
	 *            .servlet.http.HttpServletRequest
	 * @param javax
	 *            .servlet.http.HttpServletResponse
	 * @param org
	 *            .apache.struts.action.ActionForm
	 * @param org
	 *            .apache.struts.action.ActionMapping
	 */
	protected Map[] getReportParameters(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {
		return (Map[]) request.getAttribute(REPORT_PARAMETERS);
	}

	/**
	 * Carga los datos necesarios para generar el informe.
	 * 
	 * @param javax
	 *            .servlet.http.HttpServletRequest
	 * @param javax
	 *            .servlet.http.HttpServletResponse
	 * @param org
	 *            .apache.struts.action.ActionForm
	 * @param org
	 *            .apache.struts.action.ActionMapping
	 */
	protected Object[] getDataSources(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {
		return (Object[]) request.getAttribute(DATA_SOURCES);
	}

	/**
	 * Indica si es un report multiple. False siempre.
	 * 
	 * @param javax
	 *            .servlet.http.HttpServletRequest
	 * @param javax
	 *            .servlet.http.HttpServletResponse
	 * @param org
	 *            .apache.struts.action.ActionForm
	 * @param org
	 *            .apache.struts.action.ActionMapping
	 */
	protected boolean isMultiReport(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {
		return true;
	}

	/**
	 * Permite obtener un HashMap con los valores necesarios para mostrar en el
	 * informe de cartelas de depósito
	 * 
	 * @param ltPadres
	 *            Lista de padres del hueco (inclusive)
	 * @param depositoBI
	 *            Gestor de Depósito
	 * @return
	 */
	private HashMap getMapElementosDepositoNombresElemento(LinkedList ltPadres,
			GestorEstructuraDepositoBI depositoBI) {
		HashMap mapCodOrden = new HashMap();

		if ((ltPadres != null) && (!ltPadres.isEmpty())) {
			ArrayList nivelesVisibles = ConfigConstants.getInstance()
					.getNivelesDepositoVisiblesCartelasAsList();
			TipoElementoVO tipoElementoVO = null;
			ElementoVO elementoVO = null;
			Object elemento = null;
			Iterator it = ltPadres.iterator();
			int i = 1;
			while (it.hasNext()) {
				elemento = it.next();
				if (elemento instanceof ElementoVO) {
					elementoVO = (ElementoVO) elemento;
					tipoElementoVO = depositoBI
							.getTipoElementoSingleton(elementoVO
									.getIdTipoElemento());

					if (nivelesVisibles.contains(tipoElementoVO
							.getCaracterorden())) {
						String nombreElemento = String.valueOf(elementoVO
								.getNumorden());
						String nombreCompleto = elementoVO.getNombre();
						if (StringUtils.isNotEmpty(nombreCompleto)) {
							nombreElemento = StringUtils.replace(
									nombreCompleto, tipoElementoVO.getNombre(),
									Constants.STRING_EMPTY);
							if (StringUtils.isNotEmpty(nombreElemento))
								nombreElemento = nombreElemento.trim();
						}

						mapCodOrden.put(String.valueOf(i), new String[] {
								tipoElementoVO.getCaracterorden(),
								nombreElemento });
					}
				} else if (elemento instanceof HuecoVO) {
					if (nivelesVisibles.contains(Constants.LETRA_HUECO)) {
						HuecoVO huecoVO = (HuecoVO) elemento;
						mapCodOrden
								.put(String.valueOf(i),
										new String[] {
												Constants.LETRA_HUECO,
												String.valueOf(huecoVO
														.getNumorden()) });
					}
				}

				// Incrementar el contador
				i++;
			}
		}

		return mapCodOrden;
	}

}
