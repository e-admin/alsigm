package salas.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import salas.SalasConsultaConstants;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.ResumenServiciosVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.util.DateUtils;

/**
 * Informe para obtener un resumen de servicios de consulta en sala
 */
public class InformeResumenServiciosPDFReportAction extends BasePDFReportAction {

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
		return new String[] { ConfiguracionArchivoManager.INFORME_RESUMEN_SERVICIOS };
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
				messages.getMessage(SalasConsultaConstants.LABEL_INFORME_RESUMENSERVICIOS_TITULO));

		parameters
				.put("LABEL_NUM_USUARIOS", messages.getMessage(
						SalasConsultaConstants.LABEL_NUM_USUARIOS,
						request.getLocale()));
		parameters.put("LABEL_NUM_REGISTROS",
				messages.getMessage(SalasConsultaConstants.LABEL_NUM_REGISTROS,
						request.getLocale()));
		parameters.put("LABEL_NUM_REGISTROS_ORDENADOR", messages.getMessage(
				SalasConsultaConstants.LABEL_NUM_REGISTROS_ORDENADOR,
				request.getLocale()));
		parameters
				.put("LABEL_NUM_UNIDADES", messages.getMessage(
						SalasConsultaConstants.LABEL_NUM_UNIDADES,
						request.getLocale()));

		ResumenServiciosVO resumenServicios = (ResumenServiciosVO) getFromTemporalSession(
				request, SalasConsultaConstants.RESUMEN_SERVICIOS_KEY);

		if (resumenServicios != null) {
			parameters.put("PARAM_NUM_USUARIOS",
					resumenServicios.getNumUsuarios());
			parameters.put("PARAM_NUM_REGISTROS",
					resumenServicios.getNumRegistros());
			parameters.put("PARAM_NUM_UNIDADES",
					resumenServicios.getNumUnidades());
		}

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsulta = (BusquedaUsuarioSalaConsultaVO) getFromTemporalSession(
				request, SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY);

		parameters.put("LABEL_CRITERIOS_BUSQUEDA", messages.getMessage(
				SalasConsultaConstants.LABEL_CRITERIOS_BUSQUEDA,
				request.getLocale()));
		String formato = (String) getFromTemporalSession(request,
				SalasConsultaConstants.FECHA_COMP_KEY);
		if (Constants.RANGO.equals(formato)
				&& busquedaUsuarioSalaConsulta.getFechaIniExp() != null
				&& busquedaUsuarioSalaConsulta.getFechaFinExp() != null) {
			String fechaIniExp = DateUtils
					.formatDate(busquedaUsuarioSalaConsulta.getFechaIniExp());
			String fechaFinExp = DateUtils
					.formatDate(busquedaUsuarioSalaConsulta.getFechaFinExp());
			String fechaExp = messages.getMessage(
					SalasConsultaConstants.LABEL_FECHA_ESTADO_EXP,
					request.getLocale())
					+ Constants.STRING_RANGO_INI
					+ fechaIniExp
					+ Constants.STRING_RANGO_FIN + fechaFinExp;
			parameters.put("PARAM_FECHA_EXP", fechaExp);
		} else {
			if (!Constants.RANGO.equals(formato)
					&& busquedaUsuarioSalaConsulta.getFechaIniExp() != null) {
				String fechaIniExp = DateUtils
						.formatDate(busquedaUsuarioSalaConsulta
								.getFechaIniExp());
				String fechaExp = messages.getMessage(
						SalasConsultaConstants.LABEL_FECHA_ESTADO_EXP,
						request.getLocale())
						+ getNombreFormato(formato) + fechaIniExp;
				parameters.put("PARAM_FECHA_EXP", fechaExp);
			}
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

		return new Object[] { new JREmptyDataSource() };
	}
}