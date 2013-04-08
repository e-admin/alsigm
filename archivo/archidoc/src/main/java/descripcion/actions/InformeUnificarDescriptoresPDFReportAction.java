package descripcion.actions;

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

import descripcion.DescripcionConstants;

public class InformeUnificarDescriptoresPDFReportAction extends
		BasePDFReportAction {

	/** Nombre de la papeleta de entrega. */
	private final static String INFORME_REPORT_NAME = ConfiguracionArchivoManager.INFORME_UNIFICAR_DESCRIPTORES;

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

		List listaIdPrincipal = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTOR_PRINCIPAL);
		List listaAReeemplazar = (List) getFromTemporalSession(request,
				DescripcionConstants.LISTA_DESCRIPTORES_A_UNIFICAR);

		DescriptorPO descriptorPrincipal = (DescriptorPO) listaIdPrincipal
				.get(0);

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(INFORME_REPORT_NAME);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametros = getParametrosBasicos(request, Messages.getString(
				DescripcionConstants.INFORME_UNIFICAR_DESCRIPTORES_TITULO,
				request.getLocale()));

		parametros
				.put("LABEL_TITULO_DESCRIPTOR_FINAL",
						Messages.getString(
								DescripcionConstants.INFORME_UNIFICAR_DESCRIPTORES_DESCRIPTOR_FINAL,
								request.getLocale()));
		parametros
				.put("LABEL_TITULO_LISTA_REEMPLAZADOS",
						Messages.getString(
								DescripcionConstants.INFORME_UNIFICAR_DESCRIPTORES_LISTA_REEMPLAZADOS,
								request.getLocale()));

		parametros.put("PARAM_DESCRIPTOR_FINAL",
				descriptorPrincipal.getNombre());

		parametros.put("LABEL_RUTA_ORIGEN", Messages.getString(
				"archigest.archivo.deposito.rutaOrigen", request.getLocale()));
		parametros.put("LABEL_RUTA_DESTINO", Messages.getString(
				"archigest.archivo.deposito.rutaDestino", request.getLocale()));
		parametros.put(
				"LABEL_SIGNATURA",
				Messages.getString("archigest.archivo.signatura",
						request.getLocale()));
		parametros.put("LABEL_FORMATO",
				Messages.getString("archigest.archivo.transferencias.formato",
						request.getLocale()));

		reportParameters.add(parametros);

		// Valores de las Unidades Documentales
		List dataSources = new ArrayList();
		dataSources.add(new JRBeanCollectionDataSource(listaAReeemplazar));

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
