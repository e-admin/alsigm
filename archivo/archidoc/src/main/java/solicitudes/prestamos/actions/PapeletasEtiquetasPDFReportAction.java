package solicitudes.prestamos.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.view.DetallePrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;

/**
 * Papeletas con Etiquetas de las unidades documentales de un préstamo
 */
public class PapeletasEtiquetasPDFReportAction extends BasePDFReportAction {

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

		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionPrestamosBI prestamosService = service
				.lookupGestionPrestamosBI();
		GestionSolicitudesBI solicitudesBI = service
				.lookupGestionSolicitudesBI();

		List reportNames = new ArrayList();
		List dataSources = new ArrayList();
		List reportParameters = new ArrayList();

		String id = request.getParameter(Constants.ID);
		PrestamoVO prestamo = prestamosService.getPrestamo(id);
		prestamo = (PrestamoVO) solicitudesBI
				.getAditionalSolicitudInformation(prestamo);

		Collection detalles = prestamosService.obtenerDetallesSalida(id);
		CollectionUtils.transform(detalles,
				DetallePrestamoToPO.getInstance(service, true));

		// Nombre del informe
		reportNames.add(ConfiguracionArchivoManager.PAPELETA_ETIQUETA_PRESTAMO);
		// Parametros
		Map parametros = new HashMap();
		parametros.put("LABEL_NUMERO_EXPEDIENTE",
				Messages.getString(PrestamosConstants.LABEL_NUMERO_EXPEDIENTE,
						request.getLocale()));
		parametros.put("PARAM_ARCHIVO", prestamo.getArchivo().getNombre());
		reportParameters.add(parametros);

		dataSources.add(new JRBeanCollectionDataSource(detalles));

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
		return false;
	}
}