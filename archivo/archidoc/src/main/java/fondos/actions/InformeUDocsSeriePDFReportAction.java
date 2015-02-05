/**
 *
 */
package fondos.actions;

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

import xml.config.ConfiguracionArchivoManager;

import common.actions.BasePDFReportAction;

import fondos.FondosConstants;
import fondos.vos.SerieVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class InformeUDocsSeriePDFReportAction extends BasePDFReportAction {

	private static final String LABEL_SERIE = "archigest.archivo.serie";
	private static final String LABEL_CODIGO_KEY = "archigest.archivo.codigo";
	private static final String LABEL_DENOMINACION_KEY = "archigest.archivo.cf.denominacion";
	private static final String LABEL_NIVEL_KEY = "archigest.archivo.cf.nivel";
	private static final String LABEL_CONSERVADA_KEY = "archigest.archivo.cf.conservada";
	private static final String LABEL_FECHA_INICIAL = "archigest.archivo.busqueda.form.fecha.inicial";
	private static final String LABEL_FECHA_FINAL = "archigest.archivo.busqueda.form.fecha.final";
	private static final String LABEL_PRODUCTOR = "archigest.archivo.cf.productor";
	private static final String LABEL_BLOQUEADA = "archigest.archivo.solicitudes.detalle.estado.bloqueada";
	private static final String LABEL_NUMERO_EXPEDIENTE = "archigest.archivo.num.exp";
	private static final String LABEL_INTERESADOS = "archigest.archivo.transferencias.interesados";
	private static final String LABEL_NOMBRE = "archigest.archivo.nombre";
	private static final String LABEL_PRINCIPAL = "archigest.archivo.transferencias.principal";
	private static final String LABEL_NUMERO_IDENTIFICACION = "archigest.archivo.transferencias.interesadoPrincipal";
	private static final String LABEL_VALIDADO = "archigest.archivo.transferencias.validado";
	private static final String LABEL_ROL = "archigest.archivo.rol";

	private static final String LABEL_SI = "archigest.archivo.si";
	private static final String LABEL_NO = "archigest.archivo.no";

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.archigest.framework.web.action.ArchigestPDFReportAction#getDataSources(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      org.apache.struts.action.ActionForm,
	 *      org.apache.struts.action.ActionMapping)
	 */
	protected Object[] getDataSources(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {

		List udocs = (List) getFromTemporalSession(request,
				FondosConstants.LISTA_UNIDADES_DOCUMENTALES_INFORME_SERIE);

		return new Object[] { new JRBeanCollectionDataSource(udocs) };
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.archigest.framework.web.action.ArchigestPDFReportAction#getReportNames(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      org.apache.struts.action.ActionForm,
	 *      org.apache.struts.action.ActionMapping)
	 */
	protected String[] getReportNames(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, ActionForm actionform,
			ActionMapping actionmapping) {
		return new String[] { ConfiguracionArchivoManager.INFORME_UNIDADES_SERIE };
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.archigest.framework.web.action.ArchigestPDFReportAction#getReportParameters(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      org.apache.struts.action.ActionForm,
	 *      org.apache.struts.action.ActionMapping)
	 */
	protected Map[] getReportParameters(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {

		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		// Map con los parametros
		Map parameters = new HashMap();

		SerieVO serieVO = (SerieVO) getFromTemporalSession(request,
				FondosConstants.SERIE_KEY);

		parameters = getParametrosBasicos(
				request,
				messages.getMessage(FondosConstants.LABEL_INFORME_UDOCS_SERIE_TITULO));

		parameters.put("LABEL_SERIE", messages.getMessage(LABEL_SERIE));

		String serie = "";

		if (serieVO != null) {
			serie = serieVO.getCodigo() + " " + serieVO.getTitulo();
		}

		parameters.put("PARAM_SERIE", serie);

		parameters.put("LABEL_CODIGO", messages.getMessage(LABEL_CODIGO_KEY));
		parameters.put("LABEL_DENOMINACION",
				messages.getMessage(LABEL_DENOMINACION_KEY));
		parameters.put("LABEL_NIVEL", messages.getMessage(LABEL_NIVEL_KEY));
		parameters.put("LABEL_CONSERVADA",
				messages.getMessage(LABEL_CONSERVADA_KEY));
		parameters.put("LABEL_FECHA_INICIAL",
				messages.getMessage(LABEL_FECHA_INICIAL));
		parameters.put("LABEL_FECHA_FINAL",
				messages.getMessage(LABEL_FECHA_FINAL));
		parameters.put("LABEL_PRODUCTOR", messages.getMessage(LABEL_PRODUCTOR));
		parameters.put("LABEL_BLOQUEADA", messages.getMessage(LABEL_BLOQUEADA));
		parameters.put("LABEL_NUMERO_EXPEDIENTE",
				messages.getMessage(LABEL_NUMERO_EXPEDIENTE));
		parameters.put("LABEL_INTERESADOS",
				messages.getMessage(LABEL_INTERESADOS));
		parameters.put("LABEL_SI", messages.getMessage(LABEL_SI));
		parameters.put("LABEL_NO", messages.getMessage(LABEL_NO));

		// Parametros del Interesado.
		parameters.put("LABEL_PRINCIPAL", messages.getMessage(LABEL_PRINCIPAL));
		parameters.put("LABEL_NUMERO_IDENTIFICACION",
				messages.getMessage(LABEL_NUMERO_IDENTIFICACION));
		parameters.put("LABEL_NOMBRE", messages.getMessage(LABEL_NOMBRE));
		parameters.put("LABEL_ROL", messages.getMessage(LABEL_ROL));
		parameters.put("LABEL_VALIDADO", messages.getMessage(LABEL_VALIDADO));

		return new Map[] { parameters };
	}

}
