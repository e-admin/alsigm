package salas.actions;

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

import salas.SalasConsultaConstants;
import salas.vos.BusquedaRegistroConsultaSalaVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.bi.GestionArchivosBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Informe para obtener los usuarios de consulta por asistencia.
 */
public class InformeUsuariosAsistenciaPDFReportAction extends
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
		return new String[] { ConfiguracionArchivoManager.INFORME_USUARIOS_ASISTENCIA };
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

		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionArchivosBI archivoBI = service.lookupGestionArchivosBI();

		Map parameters = new HashMap();
		parameters = getParametrosBasicos(
				request,
				messages.getMessage(SalasConsultaConstants.LABEL_INFORME_USUARIOSASISTENCIA_TITULO));

		parameters.put("LABEL_DOC_IDENTIFICATIVO", messages.getMessage(
				SalasConsultaConstants.LABEL_DOC_IDENTIFICATIVO,
				request.getLocale()));
		parameters.put("LABEL_NOMBRE_APELLIDOS", messages.getMessage(
				SalasConsultaConstants.LABEL_NOMBRE_APELLIDOS,
				request.getLocale()));
		parameters.put("LABEL_MESA_ASIGNADA",
				messages.getMessage(SalasConsultaConstants.LABEL_MESA_ASIGNADA,
						request.getLocale()));
		parameters.put("LABEL_FECHA_REGISTRO", messages.getMessage(
				SalasConsultaConstants.LABEL_FECHA_REGISTRO,
				request.getLocale()));
		parameters.put("LABEL_HORA_ENTRADA_SALIDA", messages.getMessage(
				SalasConsultaConstants.LABEL_HORA_ENTRADA_SALIDA,
				request.getLocale()));
		parameters.put("LABEL_REGISTRO_CERRADO", messages.getMessage(
				SalasConsultaConstants.LABEL_R_CERRADO, request.getLocale()));

		BusquedaRegistroConsultaSalaVO busquedaRegistroConsulta = (BusquedaRegistroConsultaSalaVO) getFromTemporalSession(
				request, SalasConsultaConstants.BUSQUEDA_REGISTROS_KEY);

		parameters.put("LABEL_CRITERIOS_BUSQUEDA", messages.getMessage(
				SalasConsultaConstants.LABEL_CRITERIOS_BUSQUEDA,
				request.getLocale()));
		if (StringUtils.isNotEmpty(busquedaRegistroConsulta.getIdArchivo())) {
			ArchivoVO archivo = archivoBI
					.getArchivoXId(busquedaRegistroConsulta.getIdArchivo());
			String nombreArchivo = messages.getMessage(
					SalasConsultaConstants.LABEL_ARCHIVO, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ ((archivo != null) ? archivo.getNombre()
							: Constants.STRING_EMPTY);
			parameters.put("PARAM_ARCHIVO", nombreArchivo);
		}

		if (StringUtils.isNotEmpty(busquedaRegistroConsulta
				.getNumDocIdentificacion())) {
			String numDocLike = (String) getFromTemporalSession(request,
					SalasConsultaConstants.NUM_DOC_LIKE_KEY);
			numDocLike = (Constants.STRING_IGUAL.equals(numDocLike)) ? Constants.STRING_EQUAL
					: Constants.STRING_CONTIENE;
			String numDoc = messages.getMessage(
					SalasConsultaConstants.LABEL_NUM_DOC, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ numDocLike
					+ busquedaRegistroConsulta.getNumDocIdentificacion();
			parameters.put("PARAM_NUM_DOC", numDoc);
		}

		if (StringUtils.isNotEmpty(busquedaRegistroConsulta
				.getNombreApellidos())) {
			String nombreApellidos = messages.getMessage(
					SalasConsultaConstants.LABEL_NOMBRE_APELLIDOS,
					request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaRegistroConsulta.getNombreApellidos();
			parameters.put("PARAM_NOMBRE_APELLIDOS", nombreApellidos);
		}

		if (StringUtils.isNotEmpty(busquedaRegistroConsulta.getMesaAsignada())) {
			String mesaAsignada = messages.getMessage(
					SalasConsultaConstants.LABEL_MESA_ASIGNADA,
					request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaRegistroConsulta.getMesaAsignada();
			parameters.put("PARAM_MESA_ASIGNADA", mesaAsignada);
		}

		if (busquedaRegistroConsulta.isRegistrado()) {
			String registroCerrado = messages.getMessage(
					SalasConsultaConstants.LABEL_REGISTRO_CERRADO,
					request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ ((busquedaRegistroConsulta.isRegistrado()) ? Constants.TRUE_FULL_STRING
							: Constants.FALSE_FULL_STRING);
			parameters.put("PARAM_REGISTRO_CERRADO", registroCerrado);
		}

		String formatoEntrada = (String) getFromTemporalSession(request,
				SalasConsultaConstants.FECHA_COMP_ENTRADA_KEY);
		if (Constants.RANGO.equals(formatoEntrada)
				&& busquedaRegistroConsulta.getFechaIniEntrada() != null
				&& busquedaRegistroConsulta.getFechaFinEntrada() != null) {
			String fechaIniEntrada = DateUtils
					.formatDate(busquedaRegistroConsulta.getFechaIniEntrada());
			String fechaFinEntrada = DateUtils
					.formatDate(busquedaRegistroConsulta.getFechaFinEntrada());
			String fechaEntrada = messages.getMessage(
					SalasConsultaConstants.LABEL_FECHA_ENTRADA,
					request.getLocale())
					+ Constants.STRING_RANGO_INI
					+ fechaIniEntrada
					+ Constants.STRING_RANGO_FIN + fechaFinEntrada;
			parameters.put("PARAM_FECHA_ENTRADA", fechaEntrada);
		} else {
			if (!Constants.RANGO.equals(formatoEntrada)
					&& busquedaRegistroConsulta.getFechaIniEntrada() != null) {
				String fechaIniEntrada = DateUtils
						.formatDate(busquedaRegistroConsulta
								.getFechaIniEntrada());
				String fechaEntrada = messages.getMessage(
						SalasConsultaConstants.LABEL_FECHA_ENTRADA,
						request.getLocale())
						+ getNombreFormato(formatoEntrada) + fechaIniEntrada;
				parameters.put("PARAM_FECHA_ENTRADA", fechaEntrada);
			}
		}

		String formatoSalida = (String) getFromTemporalSession(request,
				SalasConsultaConstants.FECHA_COMP_SALIDA_KEY);
		if (Constants.RANGO.equals(formatoSalida)
				&& busquedaRegistroConsulta.getFechaIniSalida() != null
				&& busquedaRegistroConsulta.getFechaFinSalida() != null) {
			String fechaIniSalida = DateUtils
					.formatDate(busquedaRegistroConsulta.getFechaIniSalida());
			String fechaFinSalida = DateUtils
					.formatDate(busquedaRegistroConsulta.getFechaFinSalida());
			String fechaSalida = messages.getMessage(
					SalasConsultaConstants.LABEL_FECHA_SALIDA,
					request.getLocale())
					+ Constants.STRING_RANGO_INI
					+ fechaIniSalida
					+ Constants.STRING_RANGO_FIN + fechaFinSalida;
			parameters.put("PARAM_FECHA_SALIDA", fechaSalida);
		} else {
			if (!Constants.RANGO.equals(formatoSalida)
					&& busquedaRegistroConsulta.getFechaIniSalida() != null) {
				String fechaIniSalida = DateUtils
						.formatDate(busquedaRegistroConsulta
								.getFechaIniSalida());
				String fechaSalida = messages.getMessage(
						SalasConsultaConstants.LABEL_FECHA_SALIDA,
						request.getLocale())
						+ getNombreFormato(formatoSalida) + fechaIniSalida;
				parameters.put("PARAM_FECHA_SALIDA", fechaSalida);
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

		List listaRegistros = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_REGISTRADOS_KEY);

		return new Object[] { new JRBeanCollectionDataSource(listaRegistros) };
	}
}