package fondos.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import xml.config.ConfiguracionArchivoManager;

import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionCuadroClasificacionBI;
import common.util.StringUtils;

import fondos.FondosConstants;
import fondos.vos.SerieVO;
import fondos.vos.TablaTemporalFondosVO;

/**
 * Papeletas para un préstamo.
 */
public class InformeMoverUDocsPDFReportAction extends BasePDFReportAction {

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

		GestionCuadroClasificacionBI cuadroBI = getServiceRepository(request)
				.lookupGestionCuadroClasificacionBI();

		SerieVO serieOrigenVO = (SerieVO) getFromTemporalSession(request,
				FondosConstants.ELEMENTO_CF_KEY);
		SerieVO serieDestinoVO = (SerieVO) getFromTemporalSession(request,
				FondosConstants.SERIE_DESTINO);
		TablaTemporalFondosVO tablaTemporal = (TablaTemporalFondosVO) getFromTemporalSession(
				request, FondosConstants.TABLA_TEMPORAL);
		List unidadesDocumentales = new ArrayList();

		if (tablaTemporal != null) {
			unidadesDocumentales = cuadroBI.getElementos(tablaTemporal);
		}

		// List = (List) getFromTemporalSession(request,
		// FondosConstants.UDOCS_A_MOVER);
		String nombreListaAcceso = (String) getFromTemporalSession(request,
				FondosConstants.NOMBRE_LISTA_ACCESO_KEY);

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(ConfiguracionArchivoManager.INFORME_MOVER_UDOCS);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametros = getParametrosBasicos(request, Messages.getString(
				FondosConstants.LABEL_INFORMES_MOVER_UDOCS_ENTRE_SERIES,
				request.getLocale()));

		// ETIQUETAS
		parametros.put("LABEL_TITULO_SERIES", Messages.getString(
				"archigest.archivo.cf.seriesMoverUDocs", request.getLocale()));
		parametros.put(
				"LABEL_SERIE_ORIGEN",
				Messages.getString("archigest.archivo.cf.serieOrigen",
						request.getLocale()));
		parametros.put(
				"LABEL_SERIE_DESTINO",
				Messages.getString("archigest.archivo.cf.serieDestino",
						request.getLocale()));

		parametros
				.put("LABEL_TITULO_UNIDADES_DOCUMENTALES",
						Messages.getString(
								"archigest.archivo.cf.listaDeUdocsSeleccionadasMovidas",
								request.getLocale()));
		parametros.put(
				"LABEL_CODIGO_UDOC",
				Messages.getString("archigest.archivo.codigo",
						request.getLocale()));
		parametros.put(
				"LABEL_TITULO_UDOC",
				Messages.getString("archigest.archivo.titulo",
						request.getLocale()));

		// PARAMETROS
		parametros.put("PARAM_SERIE_ORIGEN", serieOrigenVO.getCodReferencia());
		parametros
				.put("PARAM_SERIE_DESTINO", serieDestinoVO.getCodReferencia());

		if (StringUtils.isNotEmpty(nombreListaAcceso)) {
			parametros.put("LABEL_NUEVA_LISTA_ACCESO", Messages.getString(
					"archigest.archivo.mover.udocs.nueva.lista.acceso",
					request.getLocale()));
			parametros.put("PARAM_NUEVA_LISTA_ACCESO", nombreListaAcceso);
		}

		reportParameters.add(parametros);

		// Valores de las Unidades Documentales
		List dataSources = new ArrayList();
		dataSources.add(new JRBeanCollectionDataSource(unidadesDocumentales));

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

}
