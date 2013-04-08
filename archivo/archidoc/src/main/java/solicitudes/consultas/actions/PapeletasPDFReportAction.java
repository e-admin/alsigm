package solicitudes.consultas.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

import salas.SalasConsultaConstants;
import salas.model.GestionSalasConsultaBI;
import salas.vos.RegistroConsultaSalaVO;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.view.DetalleConsultaPO;
import solicitudes.consultas.view.DetalleConsultaToPO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.prestamos.PrestamosConstants;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.CodigoTransferenciaUtils;
import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionFondosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;

/**
 * Papeletas para una consulta.
 */
public class PapeletasPDFReportAction extends BasePDFReportAction {

	private void aniadirCabeceraConsulta(List reportNames,
			List reportParameters, MessageResources messages,
			HttpServletRequest request, ConsultaVO consulta,
			RegistroConsultaSalaVO registroConsultaSala) {

		reportNames.add(ConfiguracionArchivoManager.PAPELETA_ENTREGA_CONSULTA);

		Map parametrosEntrega = new HashMap();

		parametrosEntrega = getParametrosBasicos(request, Messages.getString(
				ConsultasConstants.INFORME_SOLICITUDESCONSULTA_TITULO_CONSULTA,
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
				PrestamosConstants.LABEL_INFORMES_NUMERO_CONSULTA,
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

		parametrosEntrega.put("LABEL_SOLICITUD", Messages.getString(
				ConsultasConstants.INFORME_SOLICITUDESCONSULTA_SOLICITUD,
				request.getLocale()));
		parametrosEntrega.put("PARAM_NUM_SOLICITUD", CodigoTransferenciaUtils
				.getCodigoTransferencia(consulta.getAno(), consulta
						.getArchivo().getCodigo(),
						new Integer(consulta.getOrden()),
						SolicitudesConstants.FORMAT_ID_SOLICITUD));

		parametrosEntrega.put("PARAM_FECHA_ENTREGA",
				DateUtils.formatDate(consulta.getFentrega()));
		parametrosEntrega.put("PARAM_FECHA_DEVOLUCION",
				DateUtils.formatDate(consulta.getFmaxfinconsulta()));
		parametrosEntrega.put("PARAM_SOLICITANTE", consulta.getNusrconsultor());
		parametrosEntrega.put("PARAM_ORGANISMO", consulta.getArchivo()
				.getNombre());
		parametrosEntrega.put("PARAM_ORGANISMO_SOLICITANTE",
				consulta.getNorgconsultor());
		parametrosEntrega.put("LABEL_VISTO_BUENO", Messages.getString(
				SolicitudesConstants.LABEL_INFORMES_SOLICITUDES_VISTO_BUENO,
				request.getLocale()));
		parametrosEntrega
				.put("PARAM_AUTORIZADO", consulta.getDatosautorizado());

		if (!StringUtils.isEmpty(consulta.getObservaciones())) {
			parametrosEntrega.put("PARAM_OBSERVACIONES_SOLICITUD",
					consulta.getObservaciones());
		}

		/* Etiquetas para las diferentes firmas de la papeleta */
		parametrosEntrega.put("LABEL_FIRMA1", Messages.getDefaultString(
				ConsultasConstants.LABEL_FIRMA1, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA2", Messages.getDefaultString(
				ConsultasConstants.LABEL_FIRMA2, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA3", Messages.getDefaultString(
				ConsultasConstants.LABEL_FIRMA3, request.getLocale()));
		parametrosEntrega.put("LABEL_FIRMA4", Messages.getDefaultString(
				ConsultasConstants.LABEL_FIRMA4, request.getLocale()));

		if (registroConsultaSala != null) {
			parametrosEntrega.put("LABEL_MESA_ASIGNADA", Messages
					.getDefaultString(
							SalasConsultaConstants.LABEL_MESA_ASIGNADA,
							request.getLocale()));
			parametrosEntrega.put("PARAM_MESA_ASIGNADA",
					registroConsultaSala.getMesaAsignada());
		}

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

		// Servicio de Solicitudes
		GestionSolicitudesBI solicitudesBI = service
				.lookupGestionSolicitudesBI();

		// Servicio de Consultas
		GestionConsultasBI consultasService = service
				.lookupGestionConsultasBI();

		// Identificador de la consulta
		String id = request.getParameter(Constants.ID);

		// Consulta
		ConsultaVO consulta = consultasService.getConsulta(id);
		consulta = (ConsultaVO) solicitudesBI
				.getAditionalSolicitudInformation(consulta);

		// Detalles
		Collection detalles = consultasService.obtenerDetallesSalida(consulta);
		CollectionUtils.transform(detalles,
				DetalleConsultaToPO.getInstance(service, true));

		// Nombres de los informes
		List reportNames = new ArrayList();

		// Parámetros de los informes
		List reportParameters = new ArrayList();

		// Orígenes de datos de los informes
		List dataSources = new ArrayList();

		// Obtenemos los mensajes
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Si esta activa la consulta en sala
		RegistroConsultaSalaVO registro = null;
		if (ConfigConstants.getInstance().getMostrarSalas()) {
			GestionSalasConsultaBI salasBI = service.lookupGestionSalasBI();
			RegistroConsultaSalaVO registroConsultaSala = new RegistroConsultaSalaVO();
			registroConsultaSala.setFechaEntrada(DateUtils.getFechaActual());
			registroConsultaSala.setIdArchivo(consulta.getIdarchivo());
			if (StringUtils.isNotEmpty(consulta.getIdusrcsala())) {
				registroConsultaSala.setIdUsrCSala(consulta.getIdusrcsala());
				registro = salasBI
						.getRegistroConsultaSala(registroConsultaSala);
			}
		}

		if (ConfigConstants.getInstance().getUDocsSolicitudesHojasSeparadas()) {
			Iterator itDet = detalles.iterator();
			while (itDet.hasNext()) {

				// Añadimos la cabecera de la consulta
				aniadirCabeceraConsulta(reportNames, reportParameters,
						messages, request, consulta, registro);

				// Transformamos el detalle
				DetalleConsultaPO detallePO = (DetalleConsultaPO) DetalleConsultaToPO
						.getInstance(service, true).transform(itDet.next());
				List detallesCol = new ArrayList();

				detallesCol.add(detallePO);

				// Añadimos el detalle a los datasources del informe
				dataSources.add(new JRBeanCollectionDataSource(detallesCol));
			}
		} else {

			// Añadimos la cabecera de la consulta
			aniadirCabeceraConsulta(reportNames, reportParameters, messages,
					request, consulta, registro);

			// Añadimos los detalles a los datasources del informe
			dataSources.add(new JRBeanCollectionDataSource(detalles));
		}

		// Añadir la información de las unidades documentales de la consulta
		if (!CollectionUtils.isEmpty(detalles)) {
			Iterator it = detalles.iterator();
			DetalleConsultaPO detalle;
			Map parametros;

			while (it.hasNext()) {
				detalle = (DetalleConsultaPO) it.next();

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
						PrestamosConstants.LABEL_INFORMES_NUMERO_CONSULTA,
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
				parametros
						.put("LABEL_TITULO_INFORME",
								Messages.getString(
										SolicitudesConstants.LABEL_INFORMES_SOLICITUDESCONSULTA_TITULO_TESTIGO,
										request.getLocale()));
				parametros
						.put("LABEL_SOLICITUD",
								Messages.getString(
										ConsultasConstants.INFORME_SOLICITUDESCONSULTA_SOLICITUD,
										request.getLocale()));
				parametros.put("LABEL_CONTIENE", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_CONTIENE,
						request.getLocale()));
				parametros.put("LABEL_FECHA_SOLICITUD", Messages.getString(
						PrestamosConstants.LABEL_FECHA_SOLICITUD,
						request.getLocale()));

				parametros.put("PARAM_NUM_SOLICITUD", CodigoTransferenciaUtils
						.getCodigoTransferencia(consulta.getAno(), consulta
								.getArchivo().getCodigo(),
								new Integer(consulta.getOrden()),
								SolicitudesConstants.FORMAT_ID_SOLICITUD));
				parametros.put("PARAM_FECHA_ENTREGA",
						DateUtils.formatDate(consulta.getFentrega()));
				parametros.put("PARAM_FECHA_DEVOLUCION",
						DateUtils.formatDate(consulta.getFmaxfinconsulta()));
				parametros
						.put("PARAM_SOLICITANTE", consulta.getNusrconsultor());
				parametros.put("PARAM_ORGANISMO", consulta.getArchivo()
						.getNombre());
				parametros.put("PARAM_ORGANISMO_SOLICITANTE",
						consulta.getNorgconsultor());
				parametros.put("PARAM_SIGNATURA", detalle.getSignaturaudoc());
				parametros.put("PARAM_TITULO_UDOC", detalle.getTitulo());
				parametros.put("PARAM_FECHA_INICIAL", detalle.getFechaini());
				parametros.put("PARAM_FECHA_FINAL", detalle.getFechafin());
				parametros.put("PARAM_UBICACION", detalle.getUbicacion());
				parametros.put("PARAM_OBSERVACIONES",
						detalle.getObservaciones());
				parametros
						.put("LABEL_VISTO_BUENO",
								Messages.getString(
										SolicitudesConstants.LABEL_INFORMES_SOLICITUDES_VISTO_BUENO,
										request.getLocale()));

				parametros.put("LABEL_SIGNATURA", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_SIGNATURA,
						request.getLocale()));
				parametros.put("LABEL_TITULO_UDOC", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_TITULO_UDOC,
						request.getLocale()));
				parametros.put("LABEL_FECHA_INICIAL", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_INICIAL,
						request.getLocale()));
				parametros.put("LABEL_FECHA_FINAL", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_FECHA_FINAL,
						request.getLocale()));
				parametros.put("LABEL_CODIGO_UDOC", Messages.getString(
						PrestamosConstants.LABEL_CODIGO_UDOC,
						request.getLocale()));
				parametros.put("LABEL_NUMERO_EXPEDIENTE", Messages.getString(
						PrestamosConstants.LABEL_NUMERO_EXPEDIENTE,
						request.getLocale()));
				parametros.put("LABEL_CONTENEDOR", Messages.getString(
						PrestamosConstants.LABEL_INFORMES_CONTENEDOR,
						request.getLocale()));

				if (!StringUtils.isEmpty(consulta.getObservaciones())) {
					parametros.put("PARAM_OBSERVACIONES_SOLICITUD",
							consulta.getObservaciones());
				}

				if (registro != null) {
					parametros.put("LABEL_MESA_ASIGNADA", Messages
							.getDefaultString(
									SalasConsultaConstants.LABEL_MESA_ASIGNADA,
									request.getLocale()));
					parametros.put("PARAM_MESA_ASIGNADA",
							registro.getMesaAsignada());
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
