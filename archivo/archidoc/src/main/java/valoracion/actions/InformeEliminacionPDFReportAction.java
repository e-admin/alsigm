package valoracion.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import valoracion.ValoracionConstants;
import valoracion.vos.EliminacionSerieVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.bi.GestionEliminacionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;

import fondos.vos.SerieVO;

/**
 * Informe de eliminación.
 */
public class InformeEliminacionPDFReportAction extends BasePDFReportAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = ConfiguracionArchivoManager.INFORME_ELIMINACION;

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
		return new String[] { REPORT_NAME };
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
		// Obtenemos los mensajes
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Map con los parametros
		final Map parameters = getParametrosBasicos(request);

		// Identificador de la selección
		String id = request.getParameter(Constants.ID);

		// Obtener las selección
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionEliminacionBI eliminacionBI = service
				.lookupGestionEliminacionBI();
		GestionSeriesBI serieBI = service.lookupGestionSeriesBI();

		EliminacionSerieVO eliminacion = eliminacionBI.getEliminacion(id);
		SerieVO serie = serieBI.getSerie(eliminacion.getIdSerie());

		parameters.put("LABEL_TITULO_INFORME", messages.getMessage(
				ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_TITULO,
				new Object[] { (serie != null ? serie.getTitulo() : eliminacion
						.getTitulo()) }));

		parameters
				.put("LABEL_NUMERO",
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_NUMERO));
		parameters
				.put("LABEL_SIGNATURA",
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_SIGNATURA));
		parameters
				.put("LABEL_EXPEDIENTE",
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_EXPEDIENTE));
		parameters
				.put("LABEL_TITULO_SEL",
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_TITULO_SEL));
		parameters
				.put("LABEL_FECHA_INICIAL",
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_FECHA_INICIAL));
		parameters
				.put("LABEL_FECHA_FINAL",
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEELIMINACION_FECHA_FINAL));

		// Metemos los parametros en el array a devolver
		return new Map[] { parameters };
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

		// Identificador de la selección
		String id = request.getParameter(Constants.ID);
		if (logger.isDebugEnabled())
			logger.debug("Id Eliminación: " + id);

		// Obtener las unidades documentales de la selección
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionEliminacionBI eliminacionBI = service
				.lookupGestionEliminacionBI();
		List udocs = eliminacionBI.getUnidadesEliminadas(id);

		// Lo añadimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(udocs) };
	}

}
