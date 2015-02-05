package valoracion.actions;

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

import valoracion.ValoracionConstants;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.ResumenUinstEliminacionVO;
import valoracion.vos.UnidadesDocumentalesEliminacionVO;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.actions.BasePDFReportAction;
import common.bi.GestionEliminacionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.util.StringUtils;

import fondos.vos.SerieVO;

/**
 * Informe de selecciones.
 */
public class InformeSeleccionPDFReportAction extends BasePDFReportAction {

	private Map uinstParciales;

	private EliminacionSerieVO eliminacion;

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
		return new String[] { ConfiguracionArchivoManager.INFORME_SELECCION };
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

		// Identificador de la selección
		String id = request.getParameter(Constants.ID);

		// Obtener las selección
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionEliminacionBI eliminacionBI = service
				.lookupGestionEliminacionBI();
		GestionSeriesBI serieBI = service.lookupGestionSeriesBI();

		// EliminacionSerieVO eliminacion = getEliminacion();

		if (StringUtils.isNotEmpty(id)) {
			eliminacion = eliminacionBI.abrirEliminacion(id, true);
			setEliminacion(eliminacion);

			if (eliminacion != null) {
				SerieVO serie = serieBI.getSerie(eliminacion.getIdSerie());
				String tituloInforme = messages
						.getMessage(
								ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_TITULO,
								new Object[] { (serie != null ? serie
										.getTitulo() : eliminacion.getTitulo()) });

				// Parámetros

				parameters.put("LABEL_TITULO_INFORME", tituloInforme);

				parameters
						.put("LABEL_NUMERO",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_NUMERO));
				parameters
						.put("LABEL_CODIGO",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_CODIGO));
				parameters
						.put("LABEL_SIGNATURA",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_SIGNATURA));
				parameters
						.put("LABEL_EXPEDIENTE",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_EXPEDIENTE));
				parameters
						.put("LABEL_TITULO_SEL",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_TITULO_SEL));
				parameters
						.put("LABEL_UBICACION",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_UBICACION));
				parameters
						.put("LABEL_FECHA_INICIAL",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_FECHA_INICIAL));
				parameters
						.put("LABEL_FECHA_FINAL",
								messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMESELECCION_FECHA_FINAL));
				parameters
						.put("LABEL_FIRMA1",
								messages.getMessage(ValoracionConstants.LABEL_INFORME_SELECCION_FIRMA1));
				parameters
						.put("LABEL_FIRMA2",
								messages.getMessage(ValoracionConstants.LABEL_INFORME_SELECCION_FIRMA2));

				parameters
						.put("LABEL_UINSTALACION",
								messages.getMessage(ValoracionConstants.LABEL_INFORME_SELECCION_UINSTALACION));
				parameters
						.put("LABEL_NUMUDOCS",
								messages.getMessage(ValoracionConstants.LABEL_INFORME_SELECCION_NUMUDOCS));
				parameters
						.put("LABEL_COMPLETAS",
								messages.getMessage(ValoracionConstants.LABEL_INFORME_SELECCION_COMPLETAS));
				parameters
						.put("LABEL_PARCIALES",
								messages.getMessage(ValoracionConstants.LABEL_INFORME_SELECCION_PARCIALES));

				ResumenUinstEliminacionVO resumen = eliminacion
						.getResumenUInst();

				if (resumen != null) {
					parameters.put("VALOR_UINSTALACION",
							"" + resumen.getTotal());
					parameters
							.put("VALOR_NUMUDOCS", "" + resumen.getNumUdocs());
					parameters.put("VALOR_COMPLETAS",
							"" + resumen.getCompletas());
					parameters.put("VALOR_PARCIALES",
							"" + resumen.getParciales());
				}
			} else {
				logger.error("Eliminación inexistente con id" + id);
			}
		} else {
			logger.error("Identificador de eliminación es nulo");
		}

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
		// Identificador de la selección
		String id = request.getParameter(Constants.ID);
		String idArchivo = request.getParameter("idArchivo");
		if (logger.isDebugEnabled())
			logger.debug("Id Selección: " + id);

		// Obtener las unidades documentales de la selección
		ServiceRepository service = ServiceRepository
				.getInstance(getServiceClient(request));
		GestionEliminacionBI eliminacionBI = service
				.lookupGestionEliminacionBI();

		// if(getUinstParciales() == null){
		// setUinstParciales(eliminacionBI.getUInstalacionParciales(id,
		// idArchivo));
		// }

		UnidadesDocumentalesEliminacionVO unidadesDocumentalesEliminacionVO = eliminacionBI
				.getUnidadesEliminacion(id, idArchivo, null, true);
		List udocs = unidadesDocumentalesEliminacionVO.getListaUnidades();
		HashMap uinstParciales = unidadesDocumentalesEliminacionVO
				.getMapUInstParciales();
		setUinstParciales(uinstParciales);

		// Lo añadimos a los resultados
		return new Object[] { new JRBeanCollectionDataSource(udocs) };
	}

	public void setUinstParciales(Map uinstParciales) {
		this.uinstParciales = uinstParciales;
	}

	public Map getUinstParciales() {
		return uinstParciales;
	}

	public void setEliminacion(EliminacionSerieVO eliminacion) {
		this.eliminacion = eliminacion;
	}

	public EliminacionSerieVO getEliminacion() {
		return eliminacion;
	}

}
