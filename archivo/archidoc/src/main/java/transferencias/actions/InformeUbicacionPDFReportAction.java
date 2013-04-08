package transferencias.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import transferencias.TransferenciasConstants;
import transferencias.model.TipoSignaturacion;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UbicacionUnidadInstalacionVO;
import xml.config.ConfiguracionArchivoManager;

import common.actions.BasePDFReportAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.IServiceBase;
import common.bi.ServiceRepository;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.model.DetalleUbicacion;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.HuecoVO;

/**
 * Informe para Ubicación.
 */
public class InformeUbicacionPDFReportAction extends BasePDFReportAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = ConfiguracionArchivoManager.INFORME_UBICACION;

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
		final Map parameters = getParametrosBasicos(request);

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

		String strTipoRelacion = null;
		if (relacion.getIsIngresoDirecto()) {
			strTipoRelacion = messages
					.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEUBICACION_TITULO_INGRESO_DIRECTO);
		} else {
			strTipoRelacion = messages
					.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEUBICACION_TITULO_REL_ENTREGA);
		}

		// Parámetros
		parameters
				.put("LABEL_TITULO_INFORME",
						messages.getMessage(
								TransferenciasConstants.LABEL_INFORME_INFORMEUBICACION_TITULO,
								new Object[] {
										strTipoRelacion,
										relacion.getCodigoRelacion(((IServiceBase) relacionesBI)
												.getServiceSession()) }));

		parameters
				.put("LABEL_SIGNATURA",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEUBICACION_SIGNATURA));
		parameters
				.put("LABEL_UBICACION",
						messages.getMessage(TransferenciasConstants.LABEL_INFORME_INFORMEUBICACION_UBICACION));

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
		GestorEstructuraDepositoBI depositoBI = service
				.lookupGestorEstructuraDepositoBI();
		RelacionEntregaVO relacion = relacionesBI
				.getRelacionXIdRelacion(idRelacion);

		List ubicaciones = null;

		if (!relacion.isRelacionConReencajado()) {
			relacionesBI.getUbicacionesRelacion(idRelacion);
		}

		// Si las ubicaciones no están guardadas en base de datos intentar
		// cogerlas de sesión
		if (ListUtils.isEmpty(ubicaciones)) {
			List ubicacionesInforme = (List) getFromTemporalSession(request,
					DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY);
			List ubicacionesResultadoInforme = new ArrayList();
			if (!ListUtils.isEmpty(ubicacionesInforme)) {
				ListIterator it = ubicacionesInforme.listIterator();
				while (it.hasNext()) {
					DetalleUbicacion detalleUbicacion = (DetalleUbicacion) it
							.next();
					HuecoVO huecoVO = (HuecoVO) detalleUbicacion.getHueco();
					UbicacionUnidadInstalacionVO ubicacionUI = new UbicacionUnidadInstalacionVO();
					if (huecoVO != null) {
						ubicacionUI.setPath(huecoVO.getPath());
					}

					DepositoVO depositoVO = (DepositoVO) depositoBI
							.getInfoElemento(huecoVO.getIddeposito(),
									DepositoVO.ID_TIPO_ELEMENTO_UBICACION);
					if (depositoVO.getTipoSignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
							.getIdentificador()) {
						ubicacionUI.setSignaturaUI(huecoVO.getNumeracion());
					} else {
						IUnidadInstalacionVO unidadInstalacionVO = (IUnidadInstalacionVO) detalleUbicacion
								.getUnidadInstalacion();
						if (unidadInstalacionVO != null) {
							ubicacionUI.setSignaturaUI(unidadInstalacionVO
									.getSignaturaUI());
						}
					}
					ubicacionesResultadoInforme.add(ubicacionUI);
				}
			}
			return new Object[] { new JRBeanCollectionDataSource(
					ubicacionesResultadoInforme) };
		} else {
			// Lo añadimos a los resultados
			return new Object[] { new JRBeanCollectionDataSource(ubicaciones) };
		}

	}

}