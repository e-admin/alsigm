package solicitudes.prestamos.actions;

import gcontrol.vos.CAOrganoVO;

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

import se.usuarios.ServiceClient;
import solicitudes.SolicitudesConstants;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.view.DetallePrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.CodigoTransferenciaUtils;
import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Papeletas para la reclamación de unidades documentales.
 */
public class PapeletaReclamacionPDFReportAction extends BasePDFReportAction {

	/** Nombre de la papeleta de entrega. */
	private final static String REPORT_NAME = ConfiguracionArchivoManager.PAPELETA_RECLAMACION_PRESTAMO;

	/**
	 * Establece el nombre del report a generar en función del tipo de report
	 * solicitado en la request.
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

	private String getNombreLargoOrgano(HttpServletRequest request,
			String codigo) {
		GestionControlUsuariosBI userBI = ServiceRepository.getInstance(
				getServiceClient(request)).lookupGestionControlUsuariosBI();
		CAOrganoVO organo = userBI.getCAOrgProductorVOXId(codigo);
		if (organo != null) {
			return organo.getNombreLargo();
		}
		return null;
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

		// Identificador del préstamo
		Map params = new HashMap();
		String idPrestamo = request.getParameter("idPrestamo");
		if (StringUtils.isNotBlank(idPrestamo)) {
			// Servicio de Solicitudes
			GestionSolicitudesBI solicitudesBI = ServiceRepository.getInstance(
					getServiceClient(request)).lookupGestionSolicitudesBI();
			// Servicio de Préstamos
			GestionPrestamosBI prestamosBI = ServiceRepository.getInstance(
					getServiceClient(request)).lookupGestionPrestamosBI();

			String numReclamacion = request.getParameter("numReclamacion");
			if (StringUtils.isBlank(numReclamacion))
				numReclamacion = "1";

			// Préstamo
			PrestamoVO prestamo = prestamosBI.getPrestamo(idPrestamo);
			prestamo = (PrestamoVO) solicitudesBI
					.getAditionalSolicitudInformation(prestamo);

			String nombreLargo = getNombreLargoOrgano(request,
					prestamo.getIdorgsolicitante());
			if (nombreLargo != null)
				prestamo.setNorgsolicitante(nombreLargo);

			// Parámetros de los informes
			String titulo = Messages
					.getString(
							PrestamosConstants.LABEL_INFORMES_RECLAMACIONPRESTAMO_TITULO,
							request.getLocale());
			titulo = StringUtils.replace(titulo, "{0}", numReclamacion);

			params = getParametrosBasicos(request, titulo);

			params.put("LABEL_NUMERO_SOLICITUD", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_NUMERO_PRESTAMO,
					request.getLocale()));
			params.put("LABEL_FECHA_ENTREGA", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_FECHA_ENTREGA,
					request.getLocale()));
			params.put("LABEL_FECHA_DEVOLUCION", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_FECHA_DEVOLUCION,
					request.getLocale()));
			params.put(
					"LABEL_SOLICITANTE",
					Messages.getString(
							PrestamosConstants.LABEL_INFORMES_SOLICITUDPRESTAMO_SOLICITANTE,
							request.getLocale()));
			params.put("LABEL_ORGANISMO_INSTITUCION", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_ORGANISMO_INSTITUCION,
					request.getLocale()));
			params.put("LABEL_ORGANO_SOLICITANTE", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_ORGANISMO_SOLICITANTE,
					request.getLocale()));
			params.put("LABEL_OBSERVACIONES", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_OBSERVACIONES,
					request.getLocale()));
			params.put("LABEL_UNIDADES_RECLAMADAS", Messages.getString(
					PrestamosConstants.LABEL_INFORMES_UNIDADES_RECLAMADAS,
					request.getLocale()));
			params.put(
					"LABEL_NUMERO_EXPEDIENTE",
					Messages.getString(
							PrestamosConstants.LABEL_NUMERO_EXPEDIENTE,
							request.getLocale()).toUpperCase());

			params.put(
					"LABEL_SOLICITUD",
					Messages.getString(
							PrestamosConstants.LABEL_INFORMES_RECLAMACIONPRESTAMO_SOLICITUD,
							request.getLocale()));
			params.put(
					"LABEL_VISTO_BUENO",
					Messages.getString(
							PrestamosConstants.LABEL_INFORMES_RECLAMACIONPRESTAMO_VISTO_BUENO,
							request.getLocale()));
			params.put("PARAM_NUM_SOLICITUD", CodigoTransferenciaUtils
					.getCodigoTransferencia(prestamo.getAno(), prestamo
							.getArchivo().getCodigo(),
							new Integer(prestamo.getOrden()),
							SolicitudesConstants.FORMAT_ID_SOLICITUD));

			/*
			 * params.put("PARAM_NUM_SOLICITUD", prestamo.getAno() + "/" +
			 * StringUtils.leftPad(new Integer(prestamo.getOrden()) .toString(),
			 * 6, "0"));
			 */
			params.put("PARAM_FECHA_ENTREGA",
					DateUtils.formatDate(prestamo.getFentrega()));
			params.put("PARAM_FECHA_DEVOLUCION",
					DateUtils.formatDate(prestamo.getFmaxfinprestamo()));
			params.put("PARAM_SOLICITANTE", prestamo.getNusrsolicitante());
			params.put("PARAM_ORGANISMO", prestamo.getArchivo().getNombre());
			params.put("PARAM_ORGANISMO_SOLICITANTE",
					prestamo.getNorgsolicitante());

			if (!StringUtils.isEmpty(prestamo.getObservaciones())) {
				params.put("PARAM_OBSERVACIONES_SOLICITUD",
						prestamo.getObservaciones());
			}
		}

		return new Map[] { params };
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
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		List detalles = new ArrayList();

		// Identificador del préstamo
		String idPrestamo = request.getParameter("idPrestamo");

		// Unidades documentales entregadas
		if (StringUtils.isNotBlank(idPrestamo)) {
			// Servicio de Préstamos
			GestionPrestamosBI prestamosBI = ServiceRepository.getInstance(
					getServiceClient(request)).lookupGestionPrestamosBI();

			Collection detallesEntregados = prestamosBI
					.getDetallesPrestamoEntregadas(idPrestamo);

			CollectionUtils.transform(detallesEntregados,
					DetallePrestamoToPO.getInstance(services, true));

			detalles.addAll(detallesEntregados);
		}

		return new Object[] { new JRBeanCollectionDataSource(detalles) };
	}
}
