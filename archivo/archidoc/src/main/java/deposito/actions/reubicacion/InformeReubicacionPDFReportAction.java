package deposito.actions.reubicacion;

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

import deposito.DepositoConstants;
import deposito.actions.hueco.HuecoPO;
import deposito.vos.ReubicacionUDocsVO;
import deposito.vos.UDocEnUiDepositoVO;

/**
 * Papeletas para un préstamo.
 */
public class InformeReubicacionPDFReportAction extends BasePDFReportAction {

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

		HuecoPO huecoOrigenPO = (HuecoPO) getFromTemporalSession(request,
				DepositoConstants.HUECO_ORIGEN_COMPACTAR_KEY);

		HuecoPO huecoDestinoPO = (HuecoPO) getFromTemporalSession(request,
				DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY);

		List unidadesDocumentalesOrigen = (List) getFromTemporalSession(
				request, DepositoConstants.DEPOSITO_UDOCS_A_REUBICAR);
		List unidadesDocumentalesDestino = (List) getFromTemporalSession(
				request, DepositoConstants.DEPOSITO_UDOCS_REUBICADAS);

		List unidadesDocumentales = new ArrayList();

		for (int i = 0; i < unidadesDocumentalesOrigen.size(); i++) {
			UDocEnUiDepositoVO udocOrigen = (UDocEnUiDepositoVO) unidadesDocumentalesOrigen
					.get(i);
			UDocEnUiDepositoVO udocDestino = (UDocEnUiDepositoVO) unidadesDocumentalesDestino
					.get(i);

			ReubicacionUDocsVO reubicacionUDoc = new ReubicacionUDocsVO(
					udocOrigen, udocDestino);

			unidadesDocumentales.add(reubicacionUDoc);
		}

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(ConfiguracionArchivoManager.INFORME_REUBICACION);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametros = getParametrosBasicos(request, Messages.getString(
				DepositoConstants.LABEL_INFORMES_REUBICACION_TITULO,
				request.getLocale()));

		parametros.put("LABEL_UINST_ORIGEN", Messages.getString(
				"archigest.archivo.deposito.unidadInstalacionOrigen",
				request.getLocale()));
		parametros.put(
				"LABEL_RUTA",
				Messages.getString("archigest.archivo.ruta",
						request.getLocale()));
		parametros.put(
				"LABEL_SIGNATURA",
				Messages.getString("archigest.archivo.signatura",
						request.getLocale()));
		parametros.put("LABEL_FORMATO",
				Messages.getString("archigest.archivo.transferencias.formato",
						request.getLocale()));
		parametros.put("LABEL_UINST_DESTINO", Messages.getString(
				"archigest.archivo.deposito.unidadInstalacionDestino",
				request.getLocale()));
		parametros.put(
				"LABEL_TITULO_UDOC",
				Messages.getString("archigest.archivo.titulo",
						request.getLocale()));

		parametros.put("LABEL_POSICION_ORIGEN", Messages.getString(
				"archigest.archivo.informe.informeReubicacion.posicion.origen",
				request.getLocale()));
		parametros
				.put("LABEL_POSICION_DESTINO",
						Messages.getString(
								"archigest.archivo.informe.informeReubicacion.posicion.destino",
								request.getLocale()));
		parametros
				.put("LABEL_SIGNATURA_ORIGEN",
						Messages.getString(
								"archigest.archivo.informe.informeReubicacion.signatura.origen",
								request.getLocale()));
		parametros
				.put("LABEL_SIGNATURA_DESTINO",
						Messages.getString(
								"archigest.archivo.informe.informeReubicacion.signatura.destino",
								request.getLocale()));
		parametros.put("LABEL_UDOCS", Messages.getString(
				"archigest.archivo.unidadesDocumentales", request.getLocale()));

		// PARAMETROS DE DATOS
		parametros.put("PARAM_RUTA_ORIGEN", huecoOrigenPO.getPath());
		parametros
				.put("PARAM_FORMATO_ORIGEN", huecoOrigenPO.getNombreformato());
		parametros.put("PARAM_SIGNATURA_ORIGEN", huecoOrigenPO
				.getUnidInstalacion().getSignaturaui());

		parametros.put("PARAM_RUTA_DESTINO", huecoDestinoPO.getPath());
		parametros.put("PARAM_FORMATO_DESTINO",
				huecoDestinoPO.getNombreformato());
		parametros.put("PARAM_SIGNATURA_DESTINO", huecoDestinoPO
				.getUnidInstalacion().getSignaturaui());

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
