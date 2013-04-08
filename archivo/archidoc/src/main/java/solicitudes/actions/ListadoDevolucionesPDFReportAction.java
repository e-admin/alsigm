package solicitudes.actions;

import java.util.ArrayList;
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

import solicitudes.SolicitudesConstants;
import solicitudes.consultas.view.DetalleConsultaPO;
import solicitudes.consultas.view.DetalleConsultaToPO;
import solicitudes.prestamos.view.DetallePrestamoPO;
import solicitudes.prestamos.view.DetallePrestamoToPO;
import solicitudes.view.DetalleToPO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.ConfigConstants;
import common.actions.BasePDFReportAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionPrestamosBI;
import common.bi.ServiceRepository;

/**
 * Informe de las unidades devueltas.
 */
public class ListadoDevolucionesPDFReportAction extends BasePDFReportAction {

	private void aniadirCabeceraInforme(HttpServletRequest request,
			List reportNames, List reportParameters, MessageResources messages) {

		reportNames.add(ConfiguracionArchivoManager.INFORME_DEVOLUCION_FECHAS);

		Map parametrosListado = getParametrosBasicos(
				request,
				messages.getMessage(
						request.getLocale(),
						SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONES_TITULO));

		// Parámetros
		parametrosListado
				.put("LABEL_FECHA",
						messages.getMessage(
								request.getLocale(),
								SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_FECHA));
		parametrosListado
				.put("LABEL_SIGNATURA",
						messages.getMessage(
								request.getLocale(),
								SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_SIGNATURA));
		parametrosListado
				.put("LABEL_EXPEDIENTE",
						messages.getMessage(
								request.getLocale(),
								SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_EXPEDIENTE));
		parametrosListado
				.put("LABEL_TITULO_UDOC",
						messages.getMessage(
								request.getLocale(),
								SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_TITULO_UDOC));
		parametrosListado
				.put("LABEL_UBICACION",
						messages.getMessage(
								request.getLocale(),
								SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_UBICACION));
		parametrosListado.put("LABEL_FECHA_ENTREGA", messages.getMessage(
				request.getLocale(), SolicitudesConstants.LABEL_FECHA_ENTREGA));

		reportParameters.add(parametrosListado);
	}

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

		GestionPrestamosBI prestamosBI = service.lookupGestionPrestamosBI();
		GestionConsultasBI consultasBI = service.lookupGestionConsultasBI();

		boolean isPrestamo = true;
		String idSolicitud = request.getParameter("idPrestamo");
		if (idSolicitud == null) {
			idSolicitud = request.getParameter("idConsulta");
			isPrestamo = false;
		}

		// Obtenemos los mensajes
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Nombres de los informes
		List reportNames = new ArrayList();

		// Orígenes de datos de los informes
		List dataSources = new ArrayList();

		List detalles = null;
		if (isPrestamo)
			detalles = (List) prestamosBI
					.getDetallesPrestamoDevueltas(idSolicitud);
		else
			detalles = (List) consultasBI
					.getDetallesConsultaDevueltas(idSolicitud);

		// Transformamos la lista de DetalleVO en lista de DetallePO con los
		// rangos rellenos
		CollectionUtils.transform(detalles,
				DetalleToPO.getInstance(request.getLocale(), service, true));

		// Parámetros de los informes
		List reportParameters = new ArrayList();

		// Añadimos la cabecera del informe
		aniadirCabeceraInforme(request, reportNames, reportParameters, messages);

		if (ConfigConstants.getInstance().getUDocsSolicitudesHojasSeparadas()) {
			Iterator itDet = detalles.iterator();
			while (itDet.hasNext()) {

				List detallesCol = new ArrayList();
				// Transformamos el detalle
				if (isPrestamo) {
					DetallePrestamoPO detallePO = (DetallePrestamoPO) DetallePrestamoToPO
							.getInstance(service, true).transform(itDet.next());
					detallesCol.add(detallePO);
				} else {
					DetalleConsultaPO detallePO = (DetalleConsultaPO) DetalleConsultaToPO
							.getInstance(service, true).transform(itDet.next());
					detallesCol.add(detallePO);
				}

				// Añadimos el detalle a los datasources del informe
				dataSources.add(new JRBeanCollectionDataSource(detallesCol));
			}
		} else {

			dataSources.add((new JRBeanCollectionDataSource(detalles)));
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

		// return new String [] { REPORT_NAME };
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
		 * // Parámetros parameters.put("LABEL_TITULO",
		 * messages.getMessage(Constants
		 * .TITULO_INFORME,OrganizationMessages.getString
		 * (OrganizationMessages.TITLE))); parameters.put("LABEL_SUBTITULO1",
		 * messages
		 * .getMessage(Constants.INFORME_SUBTITULO_1,OrganizationMessages
		 * .getString(OrganizationMessages.SUBTITLE1)));
		 * parameters.put("LABEL_SUBTITULO2",
		 * messages.getMessage(Constants.INFORME_SUBTITULO_2
		 * ,OrganizationMessages.getString(OrganizationMessages.SUBTITLE2)));
		 * parameters.put("LABEL_SUBTITULO3",
		 * messages.getMessage(Constants.INFORME_SUBTITULO_3
		 * ,OrganizationMessages.getString(OrganizationMessages.SUBTITLE3)));
		 * parameters.put("LABEL_DIRECCION_POSTAL",
		 * messages.getMessage(Constants
		 * .INFORME_DIRECCION,OrganizationMessages.getString
		 * (OrganizationMessages.POSTAL_ADDRESS)));
		 * 
		 * parameters.put("LABEL_TITULO_INFORME",
		 * messages.getMessage(SolicitudesConstants
		 * .LABEL_INFORMES_INFORMEDEVOLUCIONES_TITULO));
		 * parameters.put("LABEL_FECHA",
		 * messages.getMessage(SolicitudesConstants
		 * .LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_FECHA));
		 * parameters.put("LABEL_SIGNATURA",
		 * messages.getMessage(SolicitudesConstants
		 * .LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_SIGNATURA));
		 * parameters.put("LABEL_EXPEDIENTE",
		 * messages.getMessage(SolicitudesConstants
		 * .LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_EXPEDIENTE));
		 * parameters.put("LABEL_TITULO_UDOC",
		 * messages.getMessage(SolicitudesConstants
		 * .LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_TITULO_UDOC));
		 * parameters.put("LABEL_UBICACION",
		 * messages.getMessage(SolicitudesConstants
		 * .LABEL_INFORMES_INFORMEDEVOLUCIONFECHAS_UBICACION));
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
		return (Object[]) request.getAttribute(DATA_SOURCES);

		// Ubicaciones de las unidades de instalación de la relación de entrega
		/*
		 * ServiceRepository service =
		 * ServiceRepository.getInstance(getServiceClient(request));
		 * GestionPrestamosBI prestamosBI = service.lookupGestionPrestamosBI();
		 * GestionConsultasBI consultasBI = service.lookupGestionConsultasBI();
		 * 
		 * boolean isPrestamo=true; String
		 * idSolicitud=request.getParameter("idPrestamo");
		 * if(idSolicitud==null){
		 * idSolicitud=request.getParameter("idConsulta"); isPrestamo=false; }
		 * 
		 * List detalles=null; if(isPrestamo) detalles =
		 * (List)prestamosBI.getDetallesPrestamoDevueltas(idSolicitud); else
		 * detalles =
		 * (List)consultasBI.getDetallesConsultaDevueltas(idSolicitud);
		 * 
		 * // Transformamos la lista de DetalleVO en lista de DetallePO con los
		 * rangos rellenos CollectionUtils.transform(detalles,
		 * DetalleToPO.getInstance(service, true));
		 * 
		 * // Lo añadimos a los resultados return new Object [] { new
		 * JRBeanCollectionDataSource(detalles) };
		 */
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
