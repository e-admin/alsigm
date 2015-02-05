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
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.TipoDocumentoIdentificativoVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.Messages;
import common.actions.BasePDFReportAction;
import common.bi.GestionArchivosBI;
import common.bi.ServiceRepository;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Informe para obtener los usuarios de consulta.
 */
public class InformeUsuariosConsultaPDFReportAction extends BasePDFReportAction {

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
		return new String[] { ConfiguracionArchivoManager.INFORME_USUARIOS_CONSULTA };
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
				messages.getMessage(SalasConsultaConstants.LABEL_INFORME_USUARIOSCONSULTA_TITULO));

		parameters.put("LABEL_DOC_IDENTIFICATIVO", messages.getMessage(
				SalasConsultaConstants.LABEL_DOC_IDENTIFICATIVO,
				request.getLocale()));
		parameters.put("LABEL_NOMBRE", messages.getMessage(
				SalasConsultaConstants.LABEL_NOMBRE, request.getLocale()));
		parameters.put("LABEL_APELLIDOS", messages.getMessage(
				SalasConsultaConstants.LABEL_APELLIDOS, request.getLocale()));
		parameters.put("LABEL_VIGENCIA", messages.getMessage(
				SalasConsultaConstants.LABEL_VIGENCIA, request.getLocale()));
		parameters
				.put("LABEL_NACIONALIDAD", messages.getMessage(
						SalasConsultaConstants.LABEL_NACIONALIDAD,
						request.getLocale()));
		parameters.put("LABEL_FECHA_ALTA", messages.getMessage(
				SalasConsultaConstants.LABEL_FECHA_ALTA, request.getLocale()));

		BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsulta = (BusquedaUsuarioSalaConsultaVO) getFromTemporalSession(
				request, SalasConsultaConstants.BUSQUEDA_USUARIOS_KEY);

		parameters.put("LABEL_CRITERIOS_BUSQUEDA", messages.getMessage(
				SalasConsultaConstants.LABEL_CRITERIOS_BUSQUEDA,
				request.getLocale()));
		if (busquedaUsuarioSalaConsulta.getIdsArchivo() != null) {
			ArchivoVO archivo = archivoBI
					.getArchivoXId(busquedaUsuarioSalaConsulta.getIdsArchivo()[0]);
			String nombreArchivo = messages.getMessage(
					SalasConsultaConstants.LABEL_ARCHIVO, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ ((archivo != null) ? archivo.getNombre()
							: Constants.STRING_EMPTY);
			parameters.put("PARAM_ARCHIVO", nombreArchivo);
		}

		if (busquedaUsuarioSalaConsulta.getTipoDocIdentificacion() != null) {
			String keyTipoDoc = TipoDocumentoIdentificativoVO.getKey(String
					.valueOf(busquedaUsuarioSalaConsulta
							.getTipoDocIdentificacion()));
			String nombreTipoDoc = Messages.getString(keyTipoDoc,
					request.getLocale());
			String tipoDoc = messages.getMessage(
					SalasConsultaConstants.LABEL_TIPO_DOC, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ nombreTipoDoc;
			parameters.put("PARAM_TIPO_DOC", tipoDoc);
		}

		if (StringUtils.isNotEmpty(busquedaUsuarioSalaConsulta
				.getNumDocIdentificacion())) {
			String numDoc = messages.getMessage(
					SalasConsultaConstants.LABEL_NUM_DOC, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaUsuarioSalaConsulta.getNumDocIdentificacion();
			parameters.put("PARAM_NUM_DOC", numDoc);
		}

		if (StringUtils.isNotEmpty(busquedaUsuarioSalaConsulta.getNombre())) {
			String nombre = messages.getMessage(
					SalasConsultaConstants.LABEL_NOMBRE, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaUsuarioSalaConsulta.getNombre();
			parameters.put("PARAM_NOMBRE", nombre);
		}

		if (StringUtils.isNotEmpty(busquedaUsuarioSalaConsulta.getApellidos())) {
			String apellidos = messages
					.getMessage(SalasConsultaConstants.LABEL_APELLIDOS,
							request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ busquedaUsuarioSalaConsulta.getApellidos();
			parameters.put("PARAM_APELLIDOS", apellidos);
		}

		if (StringUtils.isNotEmpty(busquedaUsuarioSalaConsulta.getVigente())) {
			String vigencia = messages.getMessage(
					SalasConsultaConstants.LABEL_VIGENCIA, request.getLocale())
					+ Constants.DELIMITER_IDS
					+ Constants.STRING_SPACE
					+ ((Constants.TRUE_STRING
							.equals(busquedaUsuarioSalaConsulta.getVigente())) ? Constants.TRUE_FULL_STRING
							: Constants.FALSE_FULL_STRING);
			parameters.put("PARAM_VIGENCIA", vigencia);
		}

		String formato = (String) getFromTemporalSession(request,
				SalasConsultaConstants.FECHA_COMP_KEY);
		if (Constants.RANGO.equals(formato)
				&& busquedaUsuarioSalaConsulta.getFechaIniAlta() != null
				&& busquedaUsuarioSalaConsulta.getFechaFinAlta() != null) {
			String fechaIniAlta = DateUtils
					.formatDate(busquedaUsuarioSalaConsulta.getFechaIniAlta());
			String fechaFinAlta = DateUtils
					.formatDate(busquedaUsuarioSalaConsulta.getFechaFinAlta());
			String fechaAlta = messages.getMessage(
					SalasConsultaConstants.LABEL_FECHA_ALTA,
					request.getLocale())
					+ Constants.STRING_RANGO_INI
					+ fechaIniAlta
					+ Constants.STRING_RANGO_FIN + fechaFinAlta;
			parameters.put("PARAM_FECHA_ALTA", fechaAlta);
		} else {
			if (!Constants.RANGO.equals(formato)
					&& busquedaUsuarioSalaConsulta.getFechaIniAlta() != null) {
				String fechaIniAlta = DateUtils
						.formatDate(busquedaUsuarioSalaConsulta
								.getFechaIniAlta());
				String fechaAlta = messages.getMessage(
						SalasConsultaConstants.LABEL_FECHA_ALTA,
						request.getLocale())
						+ getNombreFormato(formato) + fechaIniAlta;
				parameters.put("PARAM_FECHA_ALTA", fechaAlta);
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

		List listaUsuarios = (List) getFromTemporalSession(request,
				SalasConsultaConstants.LISTA_USUARIOS_CONSULTA_KEY);

		return new Object[] { new JRBeanCollectionDataSource(listaUsuarios) };
	}
}