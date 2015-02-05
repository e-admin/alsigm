package transferencias.actions;

import fondos.vos.SerieVO;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.vos.RelacionEntregaVO;
import xml.config.ConfiguracionArchivoManager;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.actions.BasePDFReportAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;

import descripcion.vos.DescriptorVO;

/**
 * Informe para Relación de Entrega.
 */
public class InformeDetallesRelacionPDFReportAction extends BasePDFReportAction {
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
		return new String[] { ConfiguracionArchivoManager.INFORME_DETALLES_RELACION };
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
		GestionRelacionesEACRBI relacionEABI = service
				.lookupGestionRelacionesEACRBI();
		RelacionEntregaVO relacion = relacionesBI
				.getRelacionXIdRelacion(idRelacion);

		GestionSistemaBI sistemaBI = service.lookupGestionSistemaBI();
		ArchivoVO archivo = sistemaBI.getArchivo(relacion
				.getIdarchivoreceptor());
		String codigoArchivo = (archivo != null ? archivo.getCodigo() : null);

		// Información del órgano remitente de la relación
		GestionControlUsuariosBI controlAccesoBI = service
				.lookupGestionControlUsuariosBI();
		CAOrganoVO organoRemitente = controlAccesoBI
				.getCAOrgProductorVOXId(relacion.getIdorganoremitente());

		// Parámetros
		// Map con los parametros
		Map parameters = new HashMap();
		parameters = getParametrosBasicos(
				request,
				messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_TITULO));

		parameters
				.put("LABEL_CODIGO_RELACION",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_RELACION));
		String codigoTransferencia = CodigoTransferenciaUtils
				.getCodigoTransferencia(relacion.getAno(), codigoArchivo,
						new Integer(relacion.getOrden()),
						TransferenciasConstants.FORMAT_ORDEN);
		parameters.put("idRelacion", codigoTransferencia);

		parameters
				.put("LABEL_ORGANO_REMITENTE",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_REMITENTE));
		if (organoRemitente != null && organoRemitente.getNombreLargo() != null)
			parameters.put("nombreRemitente", organoRemitente.getNombreLargo());
		else
			parameters
					.put("nombreRemitente",
							messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_ARCHIVO_REMITENTE_NO_DEFINIDO));

		parameters
				.put("LABEL_FECHA_ESTADO",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_FECHA_ESTADO));
		String fecha = DateUtils.formatDate(relacion.getFechaestado());
		parameters.put("fechaEstado", fecha);

		parameters.put(
				"LABEL_ESTADO_RELACION",
				Messages.getString(Constants.ETIQUETA_ESTADO,
						request.getLocale()));
		String nombreEstado = Messages.getString(
				TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION + "."
						+ relacion.getEstado(), request.getLocale());
		parameters.put("estadoRelacion", nombreEstado);

		parameters
				.put("LABEL_NUM_CAJAS",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_NUM_CAJAS,
								request.getLocale()));
		parameters.put(
				"numCajas",
				""
						+ relacionesBI.getCountUnidadesInstalacion(idRelacion,
								relacion.getTipotransferencia()));

		// Reencajado
		if (relacion.isEntreArchivos() && relacion.isRelacionConReencajado()) {
			parameters.put("isReencajado",
					new Boolean(relacion.isRelacionConReencajado()));

			parameters
					.put("LABEL_REL_REENCAJADA",
							Messages.getString(
									TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_REL_REENCAJADA,
									request.getLocale()));
			parameters
					.put("relReencajada",
							relacion.isRelacionConReencajado() ? Constants.TRUE_FULL_STRING
									: Constants.FALSE_FULL_STRING);

			parameters
					.put("LABEL_NUM_CAJAS_REENCAJADO",
							Messages.getString(
									TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_NUM_CAJAS_REENCAJADAS,
									request.getLocale()));
			parameters.put("numCajasReencajado",
					"" + relacionEABI.getNumUIsReeaCr(relacion.getId()));
		}
		// Metemos los parametros en el array a devolver

		// Se añade a la cabecera la consejeria, el productor y la serie
		// (solamente para SPERIA)
		ServiceClient serviceClient = getServiceClient(request);
		Properties params = null;
		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		String consejeria = "";
		try {
			ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			ConfiguracionFondos configuracionFondos = config
					.getConfiguracionFondos();
			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(configuracionFondos.getIdSistGestorOrg(),
							params);
			List organismos = gestorOrganismos.recuperarOrganosAntecesores(
					organoRemitente.getIdOrgSExtGestor(), 0);
			if (organismos != null && organismos.size() > 1) {
				InfoOrgano organismo = (InfoOrgano) organismos.get(1);
				consejeria = organismo.getNombre();
			}
		} catch (GestorOrganismosException e) {
			logger.error(e);
		} catch (NotAvailableException e) {
			logger.error(e);
		}
		if (archivo != null) {
			parameters.put("LABEL_CONSEJERIA", consejeria);
		}
		GestionDescripcionBI descripcionBI = service
				.lookupGestionDescripcionBI();
		DescriptorVO descriptor = descripcionBI.getDescriptor(relacion
				.getIddescrorgproductor());
		if (descriptor != null) {
			parameters.put("LABEL_PRODUCTOR", descriptor.getNombre());
		}
		GestionSeriesBI serieBI = service.lookupGestionSeriesBI();
		SerieVO serie = serieBI.getSerie(relacion.getIdseriedestino());
		if (serie != null) {
			String nombreSerie = serie.getCodigo() + Constants.STRING_SPACE
					+ serie.getTitulo();
			parameters.put("LABEL_SERIE", nombreSerie);
		}

		parameters.put(
				"LABEL_OBSERVACIONES",
				Messages.getString(Constants.ETIQUETA_OBSERVACIONES,
						request.getLocale()));
		parameters
				.put("LABEL_ASUNTO",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_ASUNTO,
								request.getLocale()));
		parameters
				.put("LABEL_FECHA_INICIAL",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_FECHA_INICIAL,
								request.getLocale()));
		parameters
				.put("LABEL_SIGNATURA",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_SIGNATURA,
								request.getLocale()));
		parameters.put(
				"LABEL_EXPEDIENTE",
				Messages.getString(Constants.ETIQUETA_EXPEDIENTE,
						request.getLocale()));
		parameters
				.put("LABEL_FECHA_FINAL",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_FECHA_FINAL,
								request.getLocale()));

		parameters
				.put("LABEL_FIRMA_REMITENTE",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_FIRMA_REMITENTE,
								request.getLocale()));
		parameters
				.put("LABEL_FIRMA_ARCHIVO",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEDETALLESRELACION_FIRMA_ARCHIVO,
								request.getLocale()));

		// RelacionEntregaVO relacionVO =
		// relacionesBI.abrirRelacionEntrega(idRelacion);

		// Mostrar información de las unidades electrónicas
		List udocsElectronicas = null;
		if (relacion.isEntreArchivos()) {
			udocsElectronicas = relacionesBI
					.getUDocsElectronicasByIdRelacionEntreArchivosConFechas(idRelacion);
			parameters.put("IS_REL_ENTRE_ARCHIVOS", Boolean.TRUE);
		} else {
			udocsElectronicas = relacionesBI
					.getUDocsElectronicasByIdRelacion(idRelacion);
		}
		parameters.put("udocsElectronicas", udocsElectronicas);

		parameters.put("LABEL_DOCS_ELECTRONICOS", Messages.getString(
				Constants.ETIQUETA_UNIDADES_ELECTRONICAS, request.getLocale()));
		parameters.put("LABEL_DOCS_FISICOS", Messages.getString(
				Constants.ETIQUETA_UNIDADES_DOCUMENTALES, request.getLocale()));
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

		// Ubicaciones de las unidades de instalación de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();
		GestionRelacionesEACRBI relacionesEACRBI = service
				.lookupGestionRelacionesEACRBI();

		RelacionEntregaVO relacionVO = relacionesBI
				.getRelacionXIdRelacion(idRelacion);
		List udocs = null;
		if (relacionVO != null && relacionVO.isEntreArchivos()) {
			if (relacionVO.isRelacionConReencajado()) {
				udocs = relacionesEACRBI.getUDocsByIdRelacion(idRelacion);
			} else {
				udocs = relacionesBI
						.getPartesUdocsXIdRelacionEntreArchivos(idRelacion);
			}

		} else {
			udocs = relacionesBI
					.getPartesUdocsXIdRelacionOficinaArchivo(idRelacion);
		}

		// Lo añadimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(udocs) };
	}
}
