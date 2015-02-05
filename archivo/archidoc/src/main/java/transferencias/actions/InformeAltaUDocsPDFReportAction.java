package transferencias.actions;

import fondos.vos.SerieVO;
import gcontrol.vos.ArchivoVO;

import java.util.HashMap;
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

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionDescripcionBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;

import descripcion.vos.DescriptorVO;

/**
 * Informe para el Alta de Unidades Documentales.
 */
public class InformeAltaUDocsPDFReportAction extends BasePDFReportAction {

	/**
	 * Establece el nombre del report a generar en funci�n del tipo de report
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
		return new String[] { ConfiguracionArchivoManager.JUSTIFICANTE_ALTA_UDOCS };
	}

	/**
	 * Establece los parametros necesarios para la generaci�n del report.
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

		// Identificador de la relaci�n de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Informaci�n de la relaci�n de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();
		GestionDescripcionBI descripcionBI = service
				.lookupGestionDescripcionBI();

		RelacionEntregaVO relacion = relacionesBI
				.getRelacionXIdRelacion(idRelacion);

		GestionSistemaBI sistemaBI = service.lookupGestionSistemaBI();
		ArchivoVO archivo = sistemaBI.getArchivo(relacion
				.getIdarchivoreceptor());
		String codigoArchivo = (archivo != null ? archivo.getCodigo() : null);

		GestionSeriesBI serieBI = service.lookupGestionSeriesBI();

		// Informacion del organo remitente de la relacion
		// GestionControlUsuariosBI controlAccesoBI =
		// service.lookupGestionControlUsuariosBI();

		DescriptorVO descriptor = descripcionBI.getDescriptor(relacion
				.getIddescrorgproductor());

		String nombreRemitente = new String();

		if (descriptor != null) {
			nombreRemitente = descriptor.getNombre();
		}

		// CAOrganoVO organoRemitente =
		// controlAccesoBI.getCAOrgProductorVOXId(relacion.getIdorganoremitente());

		// Par�metros
		// Map con los parametros
		Map parameters = new HashMap();
		parameters = getParametrosBasicos(
				request,
				messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEALTAUDOCS_TITULO));

		parameters
				.put("LABEL_CODIGO_ALTA",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEALTAUDOCS_CODIGO));
		String codigoTransferencia = CodigoTransferenciaUtils
				.getCodigoTransferencia(relacion.getAno(), codigoArchivo,
						new Integer(relacion.getOrden()),
						TransferenciasConstants.FORMAT_ORDEN);
		parameters.put("idRelacion", codigoTransferencia);

		parameters
				.put("LABEL_UNIDAD_PRODUCTORA",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEALTAUDOCS_PRODUCTOR));
		// if (organoRemitente != null &&
		// organoRemitente.getNombreLargo()!=null)
		parameters.put("nombreRemitente", nombreRemitente);

		parameters.put("LABEL_SERIE", Messages.getString(
				TransferenciasConstants.LABEL_INFORME_INFORMEALTAUDOCS_SERIE,
				request.getLocale()));
		SerieVO serie = serieBI.getSerie(relacion.getIdseriedestino());
		parameters.put(
				"nombreSerie",
				(serie != null) ? serie.getCodReferencia() + " "
						+ serie.getDenominacion() : "");

		parameters.put(
				"LABEL_FECHA_ESTADO",
				messages.getMessage(Constants.ETIQUETA_FECHA,
						request.getLocale()));
		String fecha = DateUtils.formatDate(relacion.getFechaestado());
		parameters.put("fechaEstado", fecha);

		parameters.put(
				"LABEL_OBSERVACIONES",
				Messages.getString(Constants.ETIQUETA_OBSERVACIONES,
						request.getLocale()));
		parameters
				.put("observaciones",
						(relacion != null && relacion.getObservaciones() != null) ? relacion
								.getObservaciones() : "");

		parameters.put("LABEL_ARCHIVO_CUSTODIA", Messages.getString(
				TransferenciasConstants.LABEL_INFORME_INFORMEALTAUDOCS_ARCHIVO,
				request.getLocale()));
		parameters.put("archivoCustodia",
				(archivo != null) ? archivo.getNombre() : "");

		// Metemos los parametros en el array a devolver
		parameters.put(
				"LABEL_EXPEDIENTE",
				Messages.getString(Constants.ETIQUETA_EXPEDIENTE,
						request.getLocale()));
		parameters.put(
				"LABEL_ASUNTO",
				Messages.getString(Constants.ETIQUETA_TITULO,
						request.getLocale()));
		parameters
				.put("LABEL_FIRMA_ARCHIVO",
						Messages.getString(
								TransferenciasConstants.LABEL_INFORME_INFORMEALTAUDOCS_FIRMA_ARCHIVO,
								request.getLocale()));
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

		// Identificador de la relaci�n de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Ubicaciones de las unidades de instalaci�n de la relaci�n de
		// entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();

		List udocs = relacionesBI
				.getPartesUdocsXIdRelacionOficinaArchivo(idRelacion);

		// Lo a�adimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(udocs) };
	}
}
