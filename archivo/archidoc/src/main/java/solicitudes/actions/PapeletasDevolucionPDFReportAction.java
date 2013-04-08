package solicitudes.actions;

import gcontrol.vos.CAOrganoVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import solicitudes.ConsultaUnidadesDocumentalesConstants;
import solicitudes.SolicitudesConstants;
import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.view.DetalleConsultaPO;
import solicitudes.consultas.view.DetalleConsultaToPO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.consultas.vos.DetalleConsultaVO;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.view.DetallePrestamoPO;
import solicitudes.prestamos.view.DetallePrestamoToPO;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import solicitudes.prestamos.vos.PrestamoVO;
import solicitudes.vos.BusquedaDetalleVO;
import solicitudes.vos.DetalleVO;
import solicitudes.vos.SolicitudReportVO;
import solicitudes.vos.SolicitudVO;
import xml.config.ConfiguracionArchivoManager;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.Messages;
import common.OrganizationMessages;
import common.actions.BasePDFReportAction;
import common.bi.GestionConsultasBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionSolicitudesBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;
import common.util.ListUtils;

/**
 * Papeletas para la devolución de unidades documentales en consulta
 */
public class PapeletasDevolucionPDFReportAction extends BasePDFReportAction {

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
		return new String[] { ConfiguracionArchivoManager.JUSTIFICANTE_DEVOLUCION };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.actions.BasePDFReportAction#isMultiReport(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.apache.struts.action.ActionForm,
	 * org.apache.struts.action.ActionMapping)
	 */
	protected boolean isMultiReport(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {

		return true;
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
		// Parámetros de los informes
		Map params = getParametrosBasicos(request, Messages.getString(
				SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCION_TITULO,
				request.getLocale()));

		params.put("LABEL_OBSERVACIONES", Messages.getString(
				PrestamosConstants.LABEL_INFORMES_OBSERVACIONES,
				request.getLocale()));

		params.put(
				"LABEL_VISTO_BUENO",
				Messages.getString(
						SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_VISTOBUENO,
						request.getLocale()));
		params.put(
				"LABEL_SOLICITANTE",
				Messages.getString(
						SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_SOLICITANTE,
						request.getLocale()));
		params.put(
				"LABEL_DATOS_CABECERA",
				Messages.getString(
						SolicitudesConstants.LABEL_INFORMES_CONSULTA_DATOS_CABECERA,
						request.getLocale()).toUpperCase());
		// Esta es la unica label que no es comun
		params.put(
				"LABEL_SOLICITUD",
				Messages.getString(
						SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_SOLICITUD,
						request.getLocale()));

		params.put(
				"LABEL_REPRESENTANTE",
				Messages.getString(
						SolicitudesConstants.LABEL_INFORMES_INFORMEDEVOLUCIONCONSULTA_REPRESENTANTE,
						request.getLocale()));
		params.put(
				"LABEL_SIGNATURA",
				Messages.getString(Constants.ETIQUETA_SIGNATURA,
						request.getLocale()).toUpperCase());
		params.put(
				"LABEL_TITULO_PRESTAMO",
				Messages.getString(
						Messages.getString(SolicitudesConstants.LABEL_TITULO,
								request.getLocale()), request.getLocale())
						.toUpperCase());
		params.put(
				"LABEL_FECHA_INICIAL",
				Messages.getString(
						Messages.getString(SolicitudesConstants.LABEL_F_INICAL,
								request.getLocale()), request.getLocale())
						.toUpperCase());
		params.put(
				"LABEL_FECHA_FINAL",
				Messages.getString(
						Messages.getString(SolicitudesConstants.LABEL_F_FINAL,
								request.getLocale()), request.getLocale())
						.toUpperCase());
		params.put("FECHA_IMPRESION",
				Messages.getString(SolicitudesConstants.LABEL_FECHA_IMPRESION,
						request.getLocale()));
		params.put(
				"LABEL_NUMERO_EXPEDIENTE",
				Messages.getString(PrestamosConstants.LABEL_NUMERO_EXPEDIENTE,
						request.getLocale()).toUpperCase());

		params.put(
				"LABEL_F_DEVOLUCION_CABECERA",
				Messages.getString(SolicitudesConstants.LABEL_FECHA_DEVOLUCION,
						request.getLocale()).toUpperCase());
		params.put("LABEL_F_DEVOLUCION", Messages.getString(
				SolicitudesConstants.LABEL_FECHA_DEVOLUCION,
				request.getLocale()));
		params.put("LABEL_F_ENTREGA", Messages.getString(
				SolicitudesConstants.LABEL_FECHA_ENTREGA, request.getLocale()));
		params.put("LABEL_ORG_INSTITUCION",
				Messages.getString(SolicitudesConstants.LABEL_ORG_INSTITUCION,
						request.getLocale()));
		params.put("LABEL_ORG_SOLICITANTE",
				Messages.getString(SolicitudesConstants.LABEL_ORG_SOLICITANTE,
						request.getLocale()));
		params.put("LABEL_UNIDADES_DOCUMENTALES", Messages.getString(
				SolicitudesConstants.LABEL_UNIDADES_DOCUMENTALES,
				request.getLocale()));

		params.put("LABEL_DATOS_CONSULTA", Messages.getString(
				SolicitudesConstants.LABEL_DATOS_CONSULTA, request.getLocale()));
		params.put("LABEL_DATOS_PRESTAMO", Messages.getString(
				SolicitudesConstants.LABEL_DATOS_PRESTAMO, request.getLocale()));
		params.put("LABEL_NUMERO_CONSULTA",
				Messages.getString(SolicitudesConstants.LABEL_NUMERO_CONSULTA,
						request.getLocale()));
		params.put("LABEL_NUMERO_PRESTAMO",
				Messages.getString(SolicitudesConstants.LABEL_NUMERO_PRESTAMO,
						request.getLocale()));

		params.put("TIPO_REPRESENTANTE",
				ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR);
		params.put("LABEL_RECIBI",
				OrganizationMessages.getString(OrganizationMessages.RECIBI));
		params.put(
				"LABEL_CONTENEDOR",
				Messages.getString(
						PrestamosConstants.LABEL_INFORMES_CONTENEDOR,
						request.getLocale()).toUpperCase());
		params.put("LABEL_CONTIENE",
				Messages.getString(PrestamosConstants.LABEL_INFORMES_CONTIENE,
						request.getLocale()));

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

		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));

		// Servicio de Solicitudes
		GestionSolicitudesBI solicitudesBI = service
				.lookupGestionSolicitudesBI();

		List listaSolicitudes = new ArrayList();

		String observacionesSolicitud = null;

		// Identificadores de las unidades documentales
		// se va a utilizar para detectar las udoc selecionadas. Es un conjunto
		// para que sea mas optimo
		HashSet idsUdocs = new HashSet();
		if (request.getParameterValues("idUdoc") != null)
			idsUdocs.addAll(Arrays.asList(request.getParameterValues("idUdoc")));

		// La lista de unidades documentales
		List listaUDocs = (List) getFromTemporalSession(request,
				ConsultaUnidadesDocumentalesConstants.DETALLES_UDOCS_KEY);

		HashMap mapDetalles = new LinkedHashMap();

		if (ListUtils.isNotEmpty(listaUDocs)) {
			for (Iterator iterator = listaUDocs.iterator(); iterator.hasNext();) {
				DetallePrestamoVO detallePrestamo = (DetallePrestamoVO) iterator
						.next();

				List listaDetalles = (List) mapDetalles.get(detallePrestamo
						.getIdsolicitud());

				if (ListUtils.isEmpty(listaDetalles))
					listaDetalles = new ArrayList();

				listaDetalles.add(detallePrestamo);

				mapDetalles
						.put(detallePrestamo.getIdsolicitud(), listaDetalles);
			}
		}

		// for (Iterator iterator = mapDetalles.keySet().iterator();
		// iterator.hasNext();) {
		// DetallePrestamoVO detallePrestamo = (DetallePrestamoVO)
		// iterator.next();
		//
		//
		//
		//
		//
		//
		//
		//
		// }

		String paramIdSolicitud = (String) request.getParameter("idPrestamo");
		if (paramIdSolicitud == null)
			paramIdSolicitud = (String) request.getParameter("idConsulta");

		// La action va a manejar ambos tipos solicitudes
		GestionPrestamosBI prestamosBI = ServiceRepository.getInstance(
				getServiceClient(request)).lookupGestionPrestamosBI();
		GestionConsultasBI consultasBI = ServiceRepository.getInstance(
				getServiceClient(request)).lookupGestionConsultasBI();

		// hay dos formas de llegar a esta action. Bien al pulsar imprimir en
		// devolver unidades de un prestamo o consulta devuelto,
		// o bien tras buscar unidades documentales
		// la diferencia es que en el primer caso sí vienen rellenos idPrestamo
		// o idConsulta

		// la gestion se complica porque la aplicacion maneja dos tipos de VOs
		// DetallePrestamo y BusquedaDetalleVO
		if (paramIdSolicitud != null) {
			// a partir de paramIdSolicitud obtener todas las udocs. y quedarse
			// solo con las seleccionadas
			List udocsSolicitud = null;

			boolean esPrestamo = false;
			if (request.getParameter("idPrestamo") != null) { // Prestamos
				udocsSolicitud = (List) prestamosBI
						.getDetallesPrestamo(paramIdSolicitud);
				observacionesSolicitud = getObservacionesPrestamo(request,
						paramIdSolicitud);
				esPrestamo = true;
			} else { // Consultas
				udocsSolicitud = (List) consultasBI
						.getDetallesConsulta(paramIdSolicitud);
				observacionesSolicitud = getObservacionesConsulta(request,
						paramIdSolicitud);
			}

			listaUDocs = new ArrayList();

			if (esPrestamo) {
				for (Iterator it = udocsSolicitud.iterator(); it.hasNext();) {
					DetallePrestamoVO detallePrestamoVO = (DetallePrestamoVO) it
							.next();
					if (idsUdocs.contains(detallePrestamoVO.getIdudoc() + "."
							+ detallePrestamoVO.getSignaturaudoc())) {
						listaUDocs.add(detallePrestamoVO);
					}
				}
			} else { // es consulta
				for (Iterator it = udocsSolicitud.iterator(); it.hasNext();) {
					DetalleConsultaVO detalleConsultaVO = (DetalleConsultaVO) it
							.next();
					if (idsUdocs.contains(detalleConsultaVO.getIdudoc() + "."
							+ detalleConsultaVO.getSignaturaudoc())) {
						listaUDocs.add(detalleConsultaVO);
					}
				}
			}
		} else {
			if (listaUDocs != null) {
				List detallesConvertidos = new ArrayList(); // contendra solo
															// las validas. Con
															// objetos de tipo
															// DetalleVO
				// recorremos las unidades documentales
				for (Iterator it = listaUDocs.iterator(); it.hasNext();) {
					Object obj = it.next();
					/*
					 * if(obj instanceof DetalleVO){ //solo son validas las
					 * seleccionadas DetalleVO detalleSolicitud=(DetalleVO)obj;
					 * if (idsUdocs.contains(detalleSolicitud.getIdudoc()+"."+
					 * detalleSolicitud.getSignaturaudoc()))
					 * detallesConvertidos.add(detalleSolicitud);
					 * 
					 * }else
					 */
					if (obj instanceof BusquedaDetalleVO) {
						// convertir de BusquedaDetalleVO a DetalleVO
						BusquedaDetalleVO busquedaDetalle = (BusquedaDetalleVO) obj;
						detallesConvertidos
								.add(convertirADetalleVO(busquedaDetalle));
					}
				}
				if (detallesConvertidos.size() > 0)
					listaUDocs = detallesConvertidos;
			}
		}

		if (listaUDocs != null && listaUDocs.size() > 0) {
			// hashmap para detectar si la consulta ya existe
			// como key se usa el id de la consulta
			// como value es la referencia al SolicitudReportVO
			// que tambien estara referenciado en la lista de solitudes
			// con la cual se genera el JRBeanCollectionDataSource

			HashMap solicitudes = new HashMap();
			for (Iterator it = listaUDocs.iterator(); it.hasNext();) {
				boolean esPrestamo = false;
				DetalleVO udoc = (DetalleVO) it.next();
				String idSolicitud = udoc.getIdsolicitud();
				SolicitudReportVO solicitudReport = null;
				// ahora se trata de crear una lista de listas: en el primer
				// nivel las solicitudes. y como descendientes sus udocs
				if (!solicitudes.containsKey(idSolicitud)) {
					// pueden llegar prestamos o consultas
					// se toma como si fuera un prestamo
					// si devuelve null -> entonces es una consulta
					SolicitudVO solicitud = prestamosBI
							.getPrestamo(idSolicitud);
					// int tipoSolicitud =
					// SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO; //1
					// Prestamo, 2 Consulta
					if (solicitud == null) {
						// llegó un id de consulta
						solicitud = consultasBI.getConsulta(idSolicitud);

						// tipoSolicitud =
						// SolicitudesConstants.TIPO_SOLICITUD_CONSULTA;
						if (solicitud == null)
							continue;
						solicitud = (ConsultaVO) solicitudesBI
								.getAditionalSolicitudInformation(solicitud);
					} else {
						esPrestamo = true;

						// llegó un id de prestamo
						solicitud = (PrestamoVO) solicitudesBI
								.getAditionalSolicitudInformation(solicitud);

						String nombreLargo = getNombreLargoOrgano(request,
								((PrestamoVO) solicitud).getIdorgsolicitante());
						if (nombreLargo != null)
							((PrestamoVO) solicitud)
									.setNorgsolicitante(nombreLargo);
					}

					// el informe va a utilizar un tipo especial de VO (contiene
					// un atributo para la lista de udocs asociada a esa
					// solicitud)
					solicitudReport = new SolicitudReportVO(
							request.getLocale(), solicitud);
					String numSolicitud = CodigoTransferenciaUtils
							.getCodigoTransferencia(solicitud.getAno(),
									solicitud.getArchivo().getCodigo(),
									new Integer(solicitud.getOrden()),
									SolicitudesConstants.FORMAT_ID_SOLICITUD);
					solicitudReport.setStrNumSolicitud(numSolicitud);
					solicitudReport.setStrFechaEntrega(DateUtils
							.formatDate(solicitud.getFentrega()));
					solicitudReport.setOrganismo(solicitud.getArchivo()
							.getNombre());

					solicitudes.put(idSolicitud, solicitudReport);

					listaSolicitudes.add(solicitudReport);
				} else {
					// si ya existia, se consigue el VO para insertar la udoc
					solicitudReport = (SolicitudReportVO) solicitudes
							.get(idSolicitud);

					if (solicitudReport.getTipoSolicitud().equals(
							SolicitudesConstants.TIPO_SOLICITUD_PRESTAMO))
						esPrestamo = true;
				}
				solicitudReport.setObservaciones(observacionesSolicitud);

				if (esPrestamo) {
					DetallePrestamoPO detallePrestamoPO = (DetallePrestamoPO) DetallePrestamoToPO
							.getInstance(service, true).transform(udoc);
					solicitudReport.addUdoc(detallePrestamoPO);
				} else {
					DetalleConsultaPO detalleConsultaPO = (DetalleConsultaPO) DetalleConsultaToPO
							.getInstance(service, true).transform(udoc);
					solicitudReport.addUdoc(detalleConsultaPO);
				}
			}
		}

		return new Object[] { new JRBeanCollectionDataSource(listaSolicitudes) };
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

	private String getObservacionesPrestamo(HttpServletRequest request,
			String codPrestamo) {
		GestionPrestamosBI prestamosBI = ServiceRepository.getInstance(
				getServiceClient(request)).lookupGestionPrestamosBI();
		PrestamoVO prestamoVO = prestamosBI.getPrestamo(codPrestamo);

		if (prestamoVO != null
				&& !StringUtils.isEmpty(prestamoVO.getObservaciones())) {
			return prestamoVO.getObservaciones();
		}
		return null;
	}

	private String getObservacionesConsulta(HttpServletRequest request,
			String codConsulta) {
		GestionConsultasBI consultasBI = ServiceRepository.getInstance(
				getServiceClient(request)).lookupGestionConsultasBI();
		ConsultaVO consultaVO = consultasBI.getConsulta(codConsulta);

		if (consultaVO != null
				&& !StringUtils.isEmpty(consultaVO.getObservaciones())) {
			return consultaVO.getObservaciones();
		}
		return null;
	}

	private DetalleVO convertirADetalleVO(BusquedaDetalleVO busqueda) {
		// las propiedades realmente utilizadas son festado, fechaIni, fechaFin,
		// signaturaudoc, titulo e idsolicitud

		DetalleVO vo = new DetalleVO();

		vo.setExpedienteudoc(busqueda.getExpedienteudoc());
		vo.setFechafin(DateUtils.formatDate(busqueda.getFechafin()));
		vo.setFechaini(DateUtils.formatDate(busqueda.getFechaini()));
		vo.setFestado(busqueda.getFestado());
		vo.setFondo(busqueda.getFondo());
		vo.setIdentificacion(busqueda.getIdentificacion());
		vo.setIdFondo(busqueda.getIdfondo());
		vo.setIdsolicitud(busqueda.getIdSolicitud());
		vo.setIdudoc(busqueda.getIdudoc());
		vo.setSignaturaudoc(busqueda.getSignaturaudoc());
		vo.setSistemaProductor(busqueda.getCodsistproductor());
		vo.setTiposolicitud(busqueda.getTiposolicitud());
		vo.setTitulo(busqueda.getTitulo());
		vo.setUbicacion(busqueda.getUbicacion());
		vo.setFentrega(busqueda.getFentrega());
		vo.setStrNumSolicitud(busqueda.getCodigoSolicitud());

		return vo;
	}
}
