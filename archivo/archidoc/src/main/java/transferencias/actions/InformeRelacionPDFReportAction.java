package transferencias.actions;

import gcontrol.vos.ArchivoVO;
import gcontrol.vos.CAOrganoVO;

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

import transferencias.TransferenciasConstants;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.RelacionEntregaVO;
import xml.config.ConfiguracionArchivoManager;

import common.CodigoTransferenciaUtils;
import common.actions.BasePDFReportAction;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSistemaBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;

/**
 * Informe para Relación de Entrega.
 */
public class InformeRelacionPDFReportAction extends BasePDFReportAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = ConfiguracionArchivoManager.INFORME_RELACION;

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

		// Map con los parametros
		final Map parameters = getParametrosBasicos(
				request,
				messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_TITULO));

		// Identificador de la relación de entrega
		String idRelacion = request.getParameter("idRelacion");
		if (logger.isDebugEnabled())
			logger.debug("Id Relacion: " + idRelacion);

		// Información de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionSistemaBI sistemaBI = service.lookupGestionSistemaBI();

		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacion = relacionesBI
				.getRelacionXIdRelacion(idRelacion);
		ArchivoVO archivo = sistemaBI.getArchivo(relacion
				.getIdarchivoreceptor());
		String codigoArchivo = (archivo != null ? archivo.getCodigo() : null);
		GestionControlUsuariosBI controlAccesoBI = service
				.lookupGestionControlUsuariosBI();

		// Información del órgano remitente de la relación
		CAOrganoVO organoRemitente = controlAccesoBI
				.getCAOrgProductorVOXId(relacion.getIdorganoremitente());

		parameters
				.put("LABEL_SIGNATURA",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_SIGNATURA));
		parameters
				.put("LABEL_CAJA",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_CAJA));
		parameters
				.put("LABEL_CODIGO_RELACION",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_RELACION));
		parameters
				.put("LABEL_ORGANO_REMITENTE",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_REMITENTE));
		parameters
				.put("LABEL_ARCHIVO_RECEPTOR",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_ARCHIVO_RECEPTOR));

		String codigoTransferencia = CodigoTransferenciaUtils
				.getCodigoTransferencia(relacion.getAno(), codigoArchivo,
						new Integer(relacion.getOrden()),
						TransferenciasConstants.FORMAT_ORDEN);

		parameters.put("idRelacion", codigoTransferencia);

		if (organoRemitente != null && organoRemitente.getNombreLargo() != null)
			parameters.put("nombreRemitente", organoRemitente.getNombreLargo());
		else
			parameters
					.put("nombreRemitente",
							messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_ARCHIVO_REMITENTE_NO_DEFINIDO));

		if (archivo != null && archivo.getNombre() != null)
			parameters.put("nombreArchivoReceptor", archivo.getNombre());
		else
			parameters
					.put("nombreArchivoReceptor",
							messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_ARCHIVO_RECEPTOR_NO_DEFINIDO));

		parameters
				.put("LABEL_FECHA_RECEPCION",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMERELACION_FECHA_RECEPCION));

		String fecha = DateUtils.formatDate(relacion.getFecharecepcion());
		parameters.put("fechaRecepcion", fecha);

		if (relacion.isEntreArchivos())
			parameters.put("IS_REL_ENTRE_ARCHIVOS", new Boolean(true));

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

		// Ubicaciones de las unidades de instalación de la relación de entrega
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();
		GestionRelacionesEACRBI relacionesCRBI = service
				.lookupGestionRelacionesEACRBI();

		RelacionEntregaVO relacionVO = relacionesBI
				.getRelacionXIdRelacion(idRelacion);

		List cajas = null;

		// List ubicaciones = relacionesBI.getUbicacionesRelacion(idRelacion);
		if (relacionVO != null && relacionVO.isEntreArchivos()) {

			if (relacionVO.isRelacionConReencajado()) {
				cajas = relacionesCRBI.getUIsReencajado(idRelacion);
			} else {
				cajas = relacionesBI
						.getUnidadesInstalacionEntreArchivos(idRelacion);
			}

			if (cajas != null) {
				Iterator it = cajas.iterator();
				while (it.hasNext()) {
					IUnidadInstalacionVO ui = (IUnidadInstalacionVO) it.next();
					ui.setSignaturaUI(ui.getSignaturaUIOrigen());
				}
			}
		} else {
			cajas = relacionesBI.getUnidadesInstalacion(idRelacion);
		}

		// Lo añadimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(cajas) };
	}

}
