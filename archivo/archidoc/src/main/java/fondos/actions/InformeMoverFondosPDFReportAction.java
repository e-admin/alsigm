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

/**
 * Papeletas para un préstamo.
 */
public class InformeMoverFondosPDFReportAction extends BasePDFReportAction {

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
		FondoPO fondoOrigen = (FondoPO) getFromTemporalSession(request,
				FondosConstants.FONDO_ORIGEN_KEY);
		FondoPO fondoDestino = (FondoPO) getFromTemporalSession(request,
				FondosConstants.FONDO_DESTINO_KEY);

		// Nombres de los informes
		List reportNames = new ArrayList();
		reportNames.add(INFORME_REUBICACION_REPORT_NAME);

		// Parámetros de los informes
		List reportParameters = new ArrayList();
		Map parametros = getParametrosBasicos(request, Messages.getString(
				FondosConstants.LABEL_INFORMES_MOVER_FONDOS_TITULO,
				request.getLocale()));

		// Etiquetas
		parametros.put(
				"LABEL_TITULO_ELEMENTO",
				Messages.getString("archigest.archivo.cf.datosFondo",
						request.getLocale()));
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
		parametros.put("PARAM_CODIGO_ANTIGUO", fondoOrigen.getCodReferencia());
		parametros.put("PARAM_CODIGO_NUEVO", fondoDestino.getCodReferencia());
		parametros.put(
				"PARAM_TIPO",
				Messages.getString("archigest.archivo.fondo",
						request.getLocale()));
		parametros.put("PARAM_DENOMINACION", fondoDestino.getDenominacion());
		parametros.put(
				"PARAM_ESTADO",
				Messages.getString("archigest.archivo.cf.estadoElementoCF."
						+ fondoDestino.getEstado(), request.getLocale()));

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
