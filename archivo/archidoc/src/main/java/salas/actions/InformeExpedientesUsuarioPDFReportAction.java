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
import salas.vos.UsuarioSalasConsultaVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.util.DateUtils;

/**
 * Informe para obtener los expedientes de usuario de consulta en sala.
 */
public class InformeExpedientesUsuarioPDFReportAction extends
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
		return new String[] { ConfiguracionArchivoManager.INFORME_EXPEDIENTES_USUARIO };
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
				messages.getMessage(SalasConsultaConstants.LABEL_INFORME_EXPEDIENTESUSUARIO_TITULO));

		parameters.put("LABEL_DATOS_USUARIO",
				messages.getMessage(SalasConsultaConstants.LABEL_DATOS_USUARIO,
						request.getLocale()));
		parameters.put("LABEL_DOC_IDENTIFICATIVO", messages.getMessage(
				SalasConsultaConstants.LABEL_DOCUMENTO_IDENTIFICATIVO,
				request.getLocale()));
		parameters.put("LABEL_NOMBRE_APELLIDOS", messages.getMessage(
				SalasConsultaConstants.LABEL_NOMBRE_APELLIDOS,
				request.getLocale()));
		parameters
				.put("LABEL_NACIONALIDAD", messages.getMessage(
						SalasConsultaConstants.LABEL_NACIONALIDAD,
						request.getLocale()));
		parameters.put("LABEL_DIRECCION", messages.getMessage(
				SalasConsultaConstants.LABEL_DIRECCION, request.getLocale()));
		parameters.put("LABEL_REGISTRO_CERRADO", messages.getMessage(
				SalasConsultaConstants.LABEL_REGISTRO_CERRADO,
				request.getLocale()));

		parameters.put(
				"LABEL_FECHA",
				messages.getMessage(SalasConsultaConstants.LABEL_FECHA,
						request.getLocale()));
		parameters.put("LABEL_SIGNATURA", messages.getMessage(
				SalasConsultaConstants.LABEL_SIGNATURA, request.getLocale()));
		parameters.put("LABEL_MOTIVO", messages.getMessage(
				SalasConsultaConstants.LABEL_MOTIVO, request.getLocale()));

		UsuarioSalasConsultaVO usuarioSalaConsulta = (UsuarioSalasConsultaVO) getFromTemporalSession(
				request, SalasConsultaConstants.USUARIO_CONSULTA_KEY);

		if (usuarioSalaConsulta != null) {
			parameters.put("PARAM_NUM_DOC",
					usuarioSalaConsulta.getNumDocIdentificacion());
			parameters.put("PARAM_NOMBRE_APELLIDOS",
					usuarioSalaConsulta.getNombre() + Constants.STRING_SPACE
							+ usuarioSalaConsulta.getApellidos());
			parameters.put("PARAM_NACIONALIDAD",
					usuarioSalaConsulta.getNacionalidad());
			parameters.put("PARAM_DIRECCION",
					usuarioSalaConsulta.getDireccion());
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

		List listaExpedientes = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_EXPEDIENTES_KEY);

		return new Object[] { new JRBeanCollectionDataSource(listaExpedientes) };
	}
}