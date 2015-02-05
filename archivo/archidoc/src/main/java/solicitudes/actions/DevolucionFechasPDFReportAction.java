package solicitudes.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import solicitudes.SolicitudesConstants;
import solicitudes.view.DetalleToPO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.actions.BasePDFReportAction;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;

/**
 * Informe de las unidades devueltas.
 */
public class DevolucionFechasPDFReportAction extends BasePDFReportAction {

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
		return new String[] { ConfiguracionArchivoManager.INFORME_DEVOLUCION_FECHAS };
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
		String fechaIni = request.getParameter("fechaIni");
		String fechaFin = request.getParameter("fechaFin");

		final Map parameters = getParametrosBasicos(
				request,
				messages.getMessage(
						SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONES_TITULO,
						fechaIni, fechaFin));

		// Parámetros
		parameters
				.put("LABEL_FECHA",
						messages.getMessage(SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_FECHA));
		parameters
				.put("LABEL_SIGNATURA",
						messages.getMessage(SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_SIGNATURA));
		parameters
				.put("LABEL_EXPEDIENTE",
						messages.getMessage(SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_EXPEDIENTE));
		parameters
				.put("LABEL_TITULO_UDOC",
						messages.getMessage(SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_TITULO_UDOC));
		parameters
				.put("LABEL_UBICACION",
						messages.getMessage(SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_UBICACION));
		parameters.put("LABEL_FECHA_ENTREGA",
				messages.getMessage(SolicitudesConstants.LABEL_FECHA_ENTREGA));

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
		// Ubicaciones de las unidades de instalación de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionSolicitudesBI solicitudesBI = service
				.lookupGestionSolicitudesBI();

		// Fecha inicial
		String fechaIni = request.getParameter("fechaIni");
		Date dFechaIni = DateUtils.getDate(fechaIni);
		if (logger.isDebugEnabled())
			logger.debug("fechaIni: " + fechaIni);

		// Fecha final
		String fechaFin = request.getParameter("fechaFin");
		Date dFechaFin = DateUtils.getDate(fechaFin);
		if (logger.isDebugEnabled())
			logger.debug("fechaFin: " + fechaFin);

		List detalles = solicitudesBI.getDetallesEntregadosByFechas(dFechaIni,
				dFechaFin);

		// Transformamos la lista de DetalleVO en lista de DetallePO con los
		// rangos rellenos
		CollectionUtils.transform(detalles,
				DetalleToPO.getInstance(request.getLocale(), service, true));

		// Lo añadimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(detalles) };
	}
}
