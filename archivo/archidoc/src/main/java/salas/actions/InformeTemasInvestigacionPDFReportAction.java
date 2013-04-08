package salas.actions;

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

import salas.SalasConsultaConstants;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.util.StringUtils;

/**
 * Informe para obtener los temas de investigacion por usuario de consulta en
 * sala.
 */
public class InformeTemasInvestigacionPDFReportAction extends
		BasePDFReportAction {

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
		return new String[] { ConfiguracionArchivoManager.INFORME_TEMAS_INVESTIGACION };
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
		final MessageResources messages = (MessageResources) request
				.getAttribute(Globals.MESSAGES_KEY);

		Map parameters = new HashMap();
		parameters = getParametrosBasicos(
				request,
				messages.getMessage(SalasConsultaConstants.LABEL_INFORME_TEMASINVESTIGACION_TITULO));

		parameters.put("LABEL_NOMBRE", messages.getMessage(
				SalasConsultaConstants.LABEL_NOMBRE, request.getLocale()));
		parameters.put("LABEL_DOC_IDENTIFICATIVO", messages.getMessage(
				SalasConsultaConstants.LABEL_DOCUMENTO_IDENTIFICATIVO,
				request.getLocale()));
		parameters.put(
				"LABEL_TEMA",
				messages.getMessage(SalasConsultaConstants.LABEL_TEMA,
						request.getLocale()));

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsulta = (BusquedaUsuarioSalaConsultaVO) getFromTemporalSession(
				request, SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY);

		parameters.put("LABEL_CRITERIOS_BUSQUEDA", messages.getMessage(
				SalasConsultaConstants.LABEL_CRITERIOS_BUSQUEDA,
				request.getLocale()));
		if (StringUtils.isNotEmpty(busquedaUsuarioSalaConsulta.getId())) {
			String nombreUsuario = messages.getMessage(
					SalasConsultaConstants.LABEL_NOMBRE_USUARIO,
					request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaUsuarioSalaConsulta.getNombre();
			parameters.put("PARAM_NOMBRE_USUARIO", nombreUsuario);
		}

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

		List listaTemas = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_TEMAS_KEY);

		return new Object[] { new JRBeanCollectionDataSource(listaTemas) };
	}
}