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

import fondos.FondosConstants;
import fondos.model.NivelCF;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Papeletas para un préstamo.
 */
public class InformeMoverClasificadorSeriesPDFReportAction extends
		BasePDFReportAction {

	/** Nombre de la papeleta de entrega. */
	private final static String INFORME_REUBICACION_REPORT_NAME = ConfiguracionArchivoManager.INFORME_MOVER_ELEMENTO;

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
		ElementoCuadroClasificacionVO elementoOrigen = (ElementoCuadroClasificacionVO) getFromTemporalSession(
				request, FondosConstants.CLASIFICADOR_ORIGEN_KEY);
		ElementoCuadroClasificacionVO elementoDestino = (ElementoCuadroClasificacionVO) getFromTemporalSession(
				request, FondosConstants.CLASIFICADOR_DESTINO_KEY);
		NivelCF nivel = (NivelCF) getFromTemporalSession(request,
				FondosConstants.NIVEL_CF_KEY);

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(INFORME_REUBICACION_REPORT_NAME);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametros = getParametrosBasicos(request, Messages.getString(
				FondosConstants.LABEL_INFORMES_MOVER_CLASIFICADOR_TITULO,
				request.getLocale()));

		// Etiquetas
		parametros.put("LABEL_TITULO_ELEMENTO", Messages.getString(
				"archigest.archivo.cf.datosClasificador", request.getLocale()));
		parametros.put("LABEL_CODIGO_REFERENCIA_ANTIGUO", Messages.getString(
				"archigest.archivo.cf.codReferencia.antiguo",
				request.getLocale()));
		parametros.put("LABEL_CODIGO_REFERENCIA_NUEVO",
				Messages.getString("archigest.archivo.cf.codReferencia.nuevo",
						request.getLocale()));
		parametros.put(
				"LABEL_TIPO",
				Messages.getString("archigest.archivo.tipo",
						request.getLocale()));
		parametros.put(
				"LABEL_DENOMINACION",
				Messages.getString("archigest.archivo.cf.denominacion",
						request.getLocale()));
		parametros.put(
				"LABEL_ESTADO",
				Messages.getString("archigest.archivo.cf.estado",
						request.getLocale()));

		// Parámetros
		parametros.put("PARAM_CODIGO_ANTIGUO",
				elementoOrigen.getCodReferencia());
		parametros
				.put("PARAM_CODIGO_NUEVO", elementoDestino.getCodReferencia());
		parametros.put("PARAM_DENOMINACION", elementoDestino.getTitulo());
		parametros.put("PARAM_TIPO", nivel.getNombre());
		parametros.put(
				"PARAM_ESTADO",
				Messages.getString("archigest.archivo.cf.estadoElementoCF."
						+ elementoDestino.getEstado(), request.getLocale()));

		reportParameters.add(parametros);

		// Valores de las Unidades Documentales
		List dataSources = new ArrayList();
		// Almacenar la información en el request
		request.setAttribute(REPORT_NAMES,
				(String[]) reportNames.toArray(new String[reportNames.size()]));

		request.setAttribute(REPORT_PARAMETERS, (Map[]) reportParameters
				.toArray(new Map[reportParameters.size()]));

		Object[] obj = new Object[] { new JRBeanCollectionDataSource(
				dataSources) };

		request.setAttribute(DATA_SOURCES, obj);
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
