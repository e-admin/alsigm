package deposito.actions.asignable;

import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.OrganizationMessages;
import common.actions.BasePDFReportAction;
import common.bi.GestionArchivosBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.actions.hueco.UdocEnUIToPO;
import deposito.vos.HuecoVO;

/**
 * Impresión en PDF de las cartelas de una relación de entrega.
 */
public class CartelasDepositoPDFReportAction extends BasePDFReportAction {

	/**
	 * 
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

		List cartelas = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_CARTELAS_KEY);

		HashMap udocsCartela = (HashMap) getFromTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_CARTELAS_KEY);

		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionArchivosBI archivoBI = service.lookupGestionArchivosBI();

		String idArchivo = (String) getFromTemporalSession(request,
				DepositoConstants.ID_ARCHIVO_KEY);

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(ConfiguracionArchivoManager.CARTELAS_DEPOSITO);

		// Obtenemos los mensajes
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametrosUInst = new HashMap();

		/*
		 * parametrosUInst.put("LABEL_CAJA", messages.getMessage(
		 * "archigest.archivo.informe.cartelas.deposito.caja"));
		 */

		String nombreCortoArchivo = OrganizationMessages
				.getTitleCartelasArchivo();
		String codigoArchivo = OrganizationMessages.getTitleCartelasArchivo();

		// Si no se ha definido el título de las cartelas de archivo en el
		// fichero de organización, obtenemos el código de base de datos y esto
		// es lo que
		// utilizaremos como título
		if (StringUtils.isEmpty(nombreCortoArchivo)) {
			ArchivoVO archivo = archivoBI.getArchivoXId(idArchivo);
			if (archivo != null) {
				nombreCortoArchivo = archivo.getNombrecorto();
				codigoArchivo = archivo.getCodigo();
			}
		}

		/*
		 * String nombreCortoArchivo = ""; if
		 * (ConfigConstants.getInstance().getPermitirTransferenciasEntreArchivos
		 * ()){ ServiceRepository service =
		 * ServiceRepository.getInstance(getServiceClient(request));
		 * GestionArchivosBI archivoBI = service.lookupGestionArchivosBI();
		 * 
		 * ArchivoVO archivo=null; if(StringUtils.isEmpty(idArchivo)) archivo =
		 * archivoBI.getArchivoXId(idArchivo);
		 * 
		 * if(archivo != null) { nombreCortoArchivo = archivo.getNombrecorto();
		 * } }else{ nombreCortoArchivo =
		 * OrganizationMessages.getTitleCartelasArchivo(); }
		 */

		parametrosUInst.put(LABEL_ORGANIZATION, messages.getMessage(
				DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA,
				OrganizationMessages.getOrganization()));

		parametrosUInst.put(LABEL_TITULO, messages.getMessage(
				DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA,
				nombreCortoArchivo));

		if (codigoArchivo == null)
			codigoArchivo = Constants.STRING_EMPTY;
		parametrosUInst.put(LABEL_CODIGO_ARCHIVO, codigoArchivo);

		addReportDir(parametrosUInst, request);

		reportParameters.add(parametrosUInst);

		// Orígenes de datos de los informes
		List dataSources = new ArrayList();
		dataSources.add(new JRBeanCollectionDataSource(cartelas));

		// Añadir la información de las unidades documentales de cada caja
		if (!CollectionUtils.isEmpty(cartelas)) {
			Iterator it = cartelas.iterator();
			HuecoVO hueco;
			Map parametrosUDoc;
			// Nombre del informe
			reportNames
					.add(ConfiguracionArchivoManager.CARTELAS_DEPOSITO_UDOCS);

			// Parámetros del informe
			parametrosUDoc = new HashMap();

			parametrosUDoc.put(LABEL_ORGANIZATION, messages.getMessage(
					DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA,
					OrganizationMessages.getOrganization()));

			parametrosUDoc.put(LABEL_TITULO, messages.getMessage(
					DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA,
					nombreCortoArchivo));

			String labelSerie = messages
					.getMessage(DepositoConstants.LABEL_SERIE_CARTELAS,
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
			String labelFecha = messages
					.getMessage(DepositoConstants.LABEL_FECHA_CARTELAS,
							request.getLocale())
					+ Constants.DELIMITER_IDS + Constants.STRING_SPACE;
			parametrosUDoc.put("LABEL_SERIE", labelSerie);
			parametrosUDoc.put("LABEL_NUM_EXP", labelNumExp);
			parametrosUDoc.put("LABEL_ASUNTO", labelAsunto);
			parametrosUDoc.put("LABEL_FECHA", labelFecha);

			addReportDir(parametrosUDoc, request);

			reportParameters.add(parametrosUDoc);

			List allUDocs = new ArrayList();

			while (it.hasNext()) {
				hueco = (HuecoVO) it.next();
				List uDocs = (List) udocsCartela.get(hueco.getHuecoID()
						.getIdpadre() + "_" + hueco.getHuecoID().getNumorden());

				CollectionUtils.transform(uDocs,
						UdocEnUIToPO.getInstance(service));

				allUDocs.addAll(uDocs);
			}

			// Orígen de datos
			dataSources.add(new JRBeanCollectionDataSource(allUDocs));

			/**
			 * Anterior: generaba las u.docs de cada caja en el inicio de una
			 * hoja while(it.hasNext()) { hueco = (HuecoVO) it.next();
			 * 
			 * // Nombre del informe reportNames.add(UDOCS_REPORT_NAME);
			 * 
			 * // Parámetros del informe parametrosUDoc = new HashMap();
			 * 
			 * parametrosUDoc.put("LABEL_ORGANIZATION", messages.getMessage(
			 * DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA,
			 * OrganizationMessages.getOrganization()));
			 * 
			 * parametrosUDoc.put("LABEL_TITULO", messages.getMessage(
			 * DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA));
			 * 
			 * reportParameters.add(parametrosUDoc);
			 * 
			 * List uDocs =
			 * (List)udocsCartela.get(hueco.getHuecoID().getIdpadre(
			 * )+"_"+hueco.getHuecoID().getNumorden());
			 * 
			 * // Orígen de datos dataSources.add(new
			 * JRBeanCollectionDataSource(uDocs)); }
			 **/
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
		return (String[]) request.getAttribute(REPORT_NAMES);
		// return new String [] { REPORT_NAME, UDOCS_REPORT_NAME };
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

		// Obtenemos los mensajes
		/*
		 * final MessageResources messages = (MessageResources)
		 * request.getAttribute(Globals.MESSAGES_KEY);
		 * 
		 * // Map con los parametros final Map parameters = new HashMap();
		 * 
		 * // Parámetros parameters.put("LABEL_CAJA", messages.getMessage(
		 * "archigest.archivo.informe.cartelas.deposito.caja"));
		 * 
		 * parameters.put("LABEL_ORGANIZATION", messages.getMessage(
		 * DepositoConstants.INFORME_CARTELAS_DEPOSITO_PA,
		 * OrganizationMessages.getOrganization()));
		 * 
		 * parameters.put("LABEL_TITULO", messages.getMessage(
		 * "archigest.archivo.informe.cartelas.deposito.titulo"));
		 * 
		 * return new Map [] { parameters };
		 */
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
		/*
		 * List cartelas = (List) getFromTemporalSession(request,
		 * DepositoConstants.LISTA_CARTELAS_KEY);
		 */

		// Lo añadimos a los resultados
		// return new Object [] { new JRBeanCollectionDataSource(cartelas)};
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

}
