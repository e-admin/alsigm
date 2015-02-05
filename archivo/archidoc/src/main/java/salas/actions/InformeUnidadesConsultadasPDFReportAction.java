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
import solicitudes.consultas.vos.DetalleConsultaVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.util.DateUtils;

/**
 * Informe para obtener unidades consultadas más de N veces por usuarios de
 * consulta en sala
 */
public class InformeUnidadesConsultadasPDFReportAction extends
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
		return new String[] { ConfiguracionArchivoManager.INFORME_UNIDADES_CONSULTADAS };
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
				messages.getMessage(SalasConsultaConstants.LABEL_INFORME_UNIDADES_CONSULTADAS_TITULO));

		parameters.put("LABEL_TITULO_UNIDADES", messages.getMessage(
				SalasConsultaConstants.LABEL_TITULO, request.getLocale()));
		parameters.put("LABEL_SIGNATURA", messages.getMessage(
				SalasConsultaConstants.LABEL_SIGNATURA, request.getLocale()));
		parameters.put("LABEL_NUM_VECES", messages.getMessage(
				SalasConsultaConstants.LABEL_NUM_VECES, request.getLocale()));
		parameters.put("LABEL_TOTAL_UNIDADES", messages.getMessage(
				SalasConsultaConstants.LABEL_TOTAL_UNIDADES,
				request.getLocale()));
		parameters.put("LABEL_TOTAL_VECES", messages.getMessage(
				SalasConsultaConstants.LABEL_TOTAL_VECES, request.getLocale()));

		List listaUnidades = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_UNIDADES_CONSULTADAS_KEY);
		if (listaUnidades != null && !listaUnidades.isEmpty()) {
			int totalVeces = 0;
			for (int i = 0; i < listaUnidades.size(); i++) {
				DetalleConsultaVO detalle = (DetalleConsultaVO) listaUnidades
						.get(i);
				totalVeces += detalle.getNumVeces().intValue();
			}
			parameters.put("PARAM_TOTAL_UNIDADES",
					new Integer(listaUnidades.size()));
			parameters.put("PARAM_TOTAL_VECES", new Integer(totalVeces));
		}

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsulta = (BusquedaUsuarioSalaConsultaVO) getFromTemporalSession(
				request, SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY);

		parameters.put("LABEL_CRITERIOS_BUSQUEDA", messages.getMessage(
				SalasConsultaConstants.LABEL_CRITERIOS_BUSQUEDA,
				request.getLocale()));
		if (busquedaUsuarioSalaConsulta.getNumVeces() != null) {
			String numVeces = messages
					.getMessage(SalasConsultaConstants.PARAM_NUM_VECES,
							request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaUsuarioSalaConsulta.getNumVeces();
			parameters.put("PARAM_NUM_VECES", numVeces);
		}

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
					+ getNombreFormato(formato)
					+ fechaIniExp + Constants.STRING_RANGO_FIN + fechaFinExp;
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

		List listaUnidades = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_UNIDADES_CONSULTADAS_KEY);

		return new Object[] { new JRBeanCollectionDataSource(listaUnidades) };
	}
}