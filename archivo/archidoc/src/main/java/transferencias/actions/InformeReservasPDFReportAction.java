package transferencias.actions;

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
import transferencias.vos.RelacionEntregaVO;
import xml.config.ConfiguracionArchivoManager;

import common.actions.BasePDFReportAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.IServiceBase;
import common.bi.ServiceRepository;

import deposito.model.GestorEstructuraDepositoBI;

/**
 * Informe de reservas de huecos en la relación de entrega.
 */
public class InformeReservasPDFReportAction extends BasePDFReportAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = ConfiguracionArchivoManager.INFORME_RESERVAS;

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

		// Identificador de la relación de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Información de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacion = relacionesBI
				.getRelacionXIdRelacion(idRelacion);

		// Map con los parametros
		final Map parameters = getParametrosBasicos(
				request,
				messages.getMessage(
						TransferenciasConstants.LABEL_INFORME_INFORMERESERVAS_TITULO,
						new Object[] { relacion
								.getCodigoRelacion(((IServiceBase) relacionesBI)
										.getServiceSession()) }));

		parameters
				.put("LABEL_HUECO",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERESERVAS_HUECO));

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
		// Identificador de la relación de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Huevos reservados de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestorEstructuraDepositoBI depositoBI = service
				.lookupGestorEstructuraDepositoBI();
		List huecos = depositoBI
				.getHuecosReservadosXIdRelacionEntrega(idRelacion);

		// Lo añadimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(huecos) };
	}

}
