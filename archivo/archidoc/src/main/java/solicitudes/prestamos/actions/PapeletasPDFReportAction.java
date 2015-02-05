package solicitudes.prestamos.actions;

import gcontrol.vos.CAOrganoVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import solicitudes.SolicitudesConstants;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.view.DetallePrestamoPO;
import solicitudes.prestamos.view.DetallePrestamoToPO;
import solicitudes.prestamos.vos.PrestamoVO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.CodigoTransferenciaUtils;
import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;

/**
 * Papeletas para un préstamo.
 */
public class PapeletasPDFReportAction extends BasePDFReportAction {

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

	private void aniadirCabeceraPrestamo(List reportNames,
			List reportParameters, MessageResources messages,
			HttpServletRequest request, PrestamoVO prestamo) {

		reportNames.add(ConfiguracionArchivoManager.PAPELETA_ENTREGA_PRESTAMO);

		Map parametrosEntrega = getParametrosBasicos(
				request,
				Messages.getString(
						PrestamosConstants.LABEL_INFORMES_SOLICITUDPRESTAMO_TITULO,
						request.getLocale()));

		parametrosEntrega.put("LABEL_SOLICITUD", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_SOLICITUDPRESTAMO_SOLICITUD,
				request.getLocale()));
		parametrosEntrega.put("LABEL_VISTO_BUENO", Messages.getString(
				SolicitudesConstants.LABEL_INFORMES_SOLICITUDES_VISTO_BUENO,
				request.getLocale()));
		parametrosEntrega
				.put("LABEL_SOLICITANTE",
						Messages.getString(
								SolicitudesConstants.LABEL_INFORMES_ENTREGASOLICITUD_SOLICITANTE_AUTORIZADO,
								request.getLocale()));
		parametrosEntrega
				.put("LABEL_AUTORIZADO",
						Messages.getString(
								SolicitudesConstants.LABEL_INFORMES_ENTREGASOLICITUD_AUTORIZADO,
								request.getLocale()));

		parametrosEntrega.put("LABEL_NUMERO_SOLICITUD", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_NUMERO_PRESTAMO,
				request.getLocale()));
		parametrosEntrega.put("LABEL_FECHA_ENTREGA", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_FECHA_ENTREGA,
				request.getLocale()));
		parametrosEntrega.put("LABEL_FECHA_DEVOLUCION", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_FECHA_DEVOLUCION,
				request.getLocale()));
		parametrosEntrega
				.put("LABEL_SOLICITANTE",
						Messages.getString(
								PrestamosConstants.LABEL_INFORMES_SOLICITUDPRESTAMO_SOLICITANTE,
								request.getLocale()));
		parametrosEntrega
				.put("LABEL_ORGANISMO_INSTITUCION",
						Messages.getString(
								PrestamosConstants.LABEL_INFORMES_ORGANISMO_INSTITUCION,
								request.getLocale()));
		parametrosEntrega
				.put("LABEL_ORGANISMO_SOLICITANTE",
						Messages.getString(
								PrestamosConstants.LABEL_INFORMES_ORGANISMO_SOLICITANTE,
								request.getLocale()));
		parametrosEntrega.put("LABEL_OBSERVACIONES", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_OBSERVACIONES,
				request.getLocale()));
		if (ConfigConstants.getInstance().getUDocsSolicitudesHojasSeparadas())
			parametrosEntrega
					.put("LABEL_UNIDADES_DOCUMENTALES",
							Messages.getString(
									PrestamosConstants.LABEL_INFORMES_UNIDAD_DOCUMENTAL,
									request.getLocale()));
		else
			parametrosEntrega
					.put("LABEL_UNIDADES_DOCUMENTALES",
							Messages.getString(
									PrestamosConstants.LABEL_INFORMES_UNIDADES_DOCUMENTALES,
									request.getLocale()));
		parametrosEntrega.put(
				"LABEL_SIGNATURA",
				Messages.getString(PrestamosConstants.LABEL_INFORMES_SIGNATURA,
						request.getLocale()).toUpperCase());
		parametrosEntrega.put(
				"LABEL_TITULO_UDOC",
				Messages.getString(
						PrestamosConstants.LABEL_INFORMES_TITULO_UDOC,
						request.getLocale()).toUpperCase());
		parametrosEntrega.put(
				"LABEL_FECHA_INICIAL",
				Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_INICIAL,
						request.getLocale()).toUpperCase());
		parametrosEntrega.put(
				"LABEL_FECHA_FINAL",
				Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_FINAL,
						request.getLocale()).toUpperCase());
		parametrosEntrega.put(
				"LABEL_CODIGO_UDOC",
				Messages.getString(PrestamosConstants.LABEL_CODIGO_UDOC,
						request.getLocale()).toUpperCase());

		parametrosEntrega.put(
				"LABEL_NUMERO_EXPEDIENTE",
				Messages.getString(PrestamosConstants.LABEL_NUMERO_EXPEDIENTE,
						request.getLocale()).toUpperCase());
		parametrosEntrega.put("LABEL_FECHA_SOLICITUD", Messages.getString(
				PrestamosConstants.LABEL_FECHA_SOLICITUD, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA_SELLO", Messages.getString(
				PrestamosConstants.LABEL_FIRMA_SELLO, request.getLocale()));
		parametrosEntrega.put("LABEL_CONTIENE",
				Messages.getString(PrestamosConstants.LABEL_INFORMES_CONTIENE,
						request.getLocale()));
		parametrosEntrega.put("LABEL_CONTENEDOR", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_CONTENEDOR,
				request.getLocale()));

		parametrosEntrega.put("PARAM_NUM_SOLICITUD", CodigoTransferenciaUtils
				.getCodigoTransferencia(prestamo.getAno(), prestamo
						.getArchivo().getCodigo(),
						new Integer(prestamo.getOrden()),
						SolicitudesConstants.FORMAT_ID_SOLICITUD));
		// parametrosEntrega.put("PARAM_NUM_SOLICITUD", prestamo.getAno() + "/"
		// + StringUtils.leftPad(new Integer(prestamo.getOrden()).toString(), 6,
		// "0"));
		parametrosEntrega.put("PARAM_FECHA_ENTREGA",
				DateUtils.formatDate(prestamo.getFentrega()));
		parametrosEntrega.put("PARAM_FECHA_DEVOLUCION",
				DateUtils.formatDate(prestamo.getFmaxfinprestamo()));
		parametrosEntrega.put("PARAM_SOLICITANTE",
				prestamo.getNusrsolicitante());
		parametrosEntrega
				.put("PARAM_AUTORIZADO", prestamo.getDatosautorizado());
		parametrosEntrega.put("PARAM_ORGANISMO", prestamo.getArchivo()
				.getNombre());
		parametrosEntrega.put("PARAM_ORGANISMO_SOLICITANTE",
				prestamo.getNorgsolicitante());

		if (!StringUtils.isEmpty(prestamo.getObservaciones())) {
			parametrosEntrega.put("PARAM_OBSERVACIONES_SOLICITUD",
					prestamo.getObservaciones());
		}

		/* Etiquetas para las diferentes firmas de la papeleta */
		parametrosEntrega.put("LABEL_FIRMA1", Messages.getDefaultString(
				PrestamosConstants.LABEL_FIRMA1, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA2", Messages.getDefaultString(
				PrestamosConstants.LABEL_FIRMA2, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA3", Messages.getDefaultString(
				PrestamosConstants.LABEL_FIRMA3, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA4", Messages.getDefaultString(
				PrestamosConstants.LABEL_FIRMA4, request.getLocale()));

		reportParameters.add(parametrosEntrega);
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

		// Servicio de Préstamos
		GestionPrestamosBI prestamosService = service
				.lookupGestionPrestamosBI();

		// Servicio de Solicitudes
		GestionSolicitudesBI solicitudesBI = service
				.lookupGestionSolicitudesBI();

		// Nombres de los informes
		List reportNames = new ArrayList();

		// Orígenes de datos de los informes
		List dataSources = new ArrayList();

		// Parámetros de los informes
		List reportParameters = new ArrayList();

		// Identificador del préstamo
		String id = request.getParameter(Constants.ID);

		// Préstamo
		PrestamoVO prestamo = prestamosService.getPrestamo(id);
		prestamo = (PrestamoVO) solicitudesBI
				.getAditionalSolicitudInformation(prestamo);

		String nombreLargo = getNombreLargoOrgano(request,
				prestamo.getIdorgsolicitante());
		if (nombreLargo != null)
			prestamo.setNorgsolicitante(nombreLargo);

		Collection detalles = prestamosService.obtenerDetallesSalida(id);

		// Transformamos la lista de DetalleVO en lista de DetallePO con los
		// rangos rellenos
		CollectionUtils.transform(detalles,
				DetallePrestamoToPO.getInstance(service, true));

		// Obtenemos los mensajes
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		if (ConfigConstants.getInstance().getUDocsSolicitudesHojasSeparadas()) {
			Iterator itDet = detalles.iterator();
			while (itDet.hasNext()) {

				// Añadimos la cabecera del préstamo
				aniadirCabeceraPrestamo(reportNames, reportParameters,
						messages, request, prestamo);

				// Transformamos el detalle
				DetallePrestamoPO detallePO = (DetallePrestamoPO) DetallePrestamoToPO
						.getInstance(service, true).transform(itDet.next());
				List detallesCol = new ArrayList();

				detallesCol.add(detallePO);

				// Añadimos el detalle a los datasources del informe
				dataSources.add(new JRBeanCollectionDataSource(detallesCol));
			}
		} else {

			// Añadimos la cabecera del préstamo
			aniadirCabeceraPrestamo(reportNames, reportParameters, messages,
					request, prestamo);

			// Añadimos los detalles a los datasources del informe
			dataSources.add(new JRBeanCollectionDataSource(detalles));
		}

		// Añadir la información de las unidades documentales del préstamo
		if (!CollectionUtils.isEmpty(detalles)) {
			Iterator it = detalles.iterator();
			DetallePrestamoPO detalle;
			Map parametros;
			while (it.hasNext()) {
				detalle = (DetallePrestamoPO) it.next();

				// Nombre del informe
				reportNames
						.add(ConfiguracionArchivoManager.PAPELETA_TESTIGO_PRESTAMO);

				// Parámetros del informe
				parametros = getParametrosBasicos(
						request,
						Messages.getString(
								PrestamosConstants.LABEL_INFORMES_TESTIGOPRESTAMO_TITULO,
								request.getLocale()));

				parametros.put("LABEL_NUMERO_SOLICITUD", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_NUMERO_PRESTAMO,
						request.getLocale()));
				parametros.put("LABEL_FECHA_ENTREGA", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_ENTREGA,
						request.getLocale()));
				parametros.put("LABEL_FECHA_DEVOLUCION", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_DEVOLUCION,
						request.getLocale()));
				parametros
						.put("LABEL_SOLICITANTE",
								Messages.getString(
										PrestamosConstants.LABEL_INFORMES_SOLICITUDPRESTAMO_SOLICITANTE,
										request.getLocale()));
				parametros
						.put("LABEL_ORGANISMO_INSTITUCION",
								Messages.getString(
										PrestamosConstants.LABEL_INFORMES_ORGANISMO_INSTITUCION,
										request.getLocale()));
				parametros
						.put("LABEL_ORGANO_SOLICITANTE",
								Messages.getString(
										PrestamosConstants.LABEL_INFORMES_ORGANISMO_SOLICITANTE,
										request.getLocale()));
				parametros.put("LABEL_OBSERVACIONES", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_OBSERVACIONES,
						request.getLocale()));
				parametros.put("LABEL_UNIDAD_DOCUMENTAL", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_UNIDAD_DOCUMENTAL,
						request.getLocale()));
				parametros.put("LABEL_SIGNATURA", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_SIGNATURA,
						request.getLocale()));
				parametros.put("LABEL_TITULO_UDOC", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_TITULO_UDOC,
						request.getLocale()));
				parametros.put("LABEL_UBICACION", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_UBICACION,
						request.getLocale()));
				parametros.put("LABEL_FECHA_INICIAL", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_INICIAL,
						request.getLocale()));
				parametros.put("LABEL_FECHA_FINAL", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_FINAL,
						request.getLocale()));
				parametros.put("LABEL_NUMERO_EXPEDIENTE", Messages.getString(
						PrestamosConstants.LABEL_NUMERO_EXPEDIENTE,
						request.getLocale()));

				parametros
						.put("LABEL_SOLICITUD",
								Messages.getString(
										PrestamosConstants.LABEL_INFORMES_TESTIGOPRESTAMO_SOLICITUD,
										request.getLocale()));
				parametros
						.put("LABEL_VISTO_BUENO",
								Messages.getString(
										SolicitudesConstants.LABEL_INFORMES_SOLICITUDES_VISTO_BUENO,
										request.getLocale()));
				parametros.put("LABEL_CONTENEDOR", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_CONTENEDOR,
						request.getLocale()));
				parametros.put("LABEL_CONTIENE", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_CONTIENE,
						request.getLocale()));
				parametros.put("LABEL_FECHA_SOLICITUD", Messages.getString(
						PrestamosConstants.LABEL_FECHA_SOLICITUD,
						request.getLocale()));

				parametros.put("PARAM_NUM_SOLICITUD", CodigoTransferenciaUtils
						.getCodigoTransferencia(prestamo.getAno(), prestamo
								.getArchivo().getCodigo(),
								new Integer(prestamo.getOrden()),
								SolicitudesConstants.FORMAT_ID_SOLICITUD));

				parametros.put("PARAM_FECHA_ENTREGA",
						DateUtils.formatDate(prestamo.getFentrega()));
				parametros.put("PARAM_FECHA_DEVOLUCION",
						DateUtils.formatDate(prestamo.getFmaxfinprestamo()));
				parametros.put("PARAM_SOLICITANTE",
						prestamo.getNusrsolicitante());
				parametros.put("PARAM_ORGANISMO", prestamo.getArchivo()
						.getNombre());
				parametros.put("PARAM_ORGANISMO_SOLICITANTE",
						prestamo.getNorgsolicitante());
				parametros.put("PARAM_SIGNATURA", detalle.getSignaturaudoc());
				parametros.put("PARAM_TITULO_UDOC", detalle.getTitulo());
				parametros.put("PARAM_FECHA_INICIAL", detalle.getFechaini());
				parametros.put("PARAM_FECHA_FINAL", detalle.getFechafin());
				// parametros.put("PARAM_FECHA_INICIAL",
				// DateUtils.formatDate(detalle.getFinicialuso()));
				// parametros.put("PARAM_FECHA_FINAL",
				// DateUtils.formatDate(detalle.getFfinaluso()));
				parametros.put("PARAM_UBICACION", detalle.getUbicacion());
				parametros.put("PARAM_OBSERVACIONES",
						detalle.getObservaciones());

				if (!StringUtils.isEmpty(prestamo.getObservaciones())) {
					parametros.put("PARAM_OBSERVACIONES_SOLICITUD",
							prestamo.getObservaciones());
				}

				parametros.put("PARAM_CONTIENE",
						detalle.getDescripcionUdocDeposito());

				// Para incluir el fondo en la cabecera
				GestionFondosBI fondosBI = service.lookupGestionFondosBI();
				parametros.put("PARAM_FONDO",
						fondosBI.getFondoXId(detalle.getIdFondo())
								.getDenominacion());

				String numExp = Constants.STRING_EMPTY;
				if (StringUtils.isNotEmpty(detalle.getExpedienteudoc()))
					numExp = detalle.getExpedienteudoc();
				else
					numExp = detalle.getNombreRangos();

				String denominacion = detalle.getDenominacionExpediente();
				if (StringUtils.isNotEmpty(denominacion)) {
					numExp = denominacion + Constants.STRING_SPACE + numExp;
				}

				parametros.put("PARAM_NUMERO_EXPEDIENTE", numExp);

				parametros.put("PARAM_DENOMINACION_EXP", denominacion);
				parametros.put("PARAM_NUM_EXP", numExp);

				reportParameters.add(parametros);

				// Orígen de datos
				dataSources.add(new JRBeanArrayDataSource(new String[0]));
			}
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
		return true;
	}

}
