package fondos.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.procedimientos.InfoBProcedimiento;
import se.usuarios.AppUser;

import com.lowagie.text.Paragraph;
import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.reports.PdfReport;
import common.reports.PdfReportTable;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.ResponseUtil;

import configuracion.bi.GestionInfoSistemaBI;
import es.archigest.framework.web.action.ArchigestActionProcessException;
import fondos.FondosConstants;
import fondos.model.IdentificacionSerie;
import fondos.view.SeriePO;
import fondos.vos.ProductorSerieVO;

public class InformeIdentificacionPDFReportAction extends BaseAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = "informeIdentificacion";

	/**
	 * Ejecuta la lógica de la acción.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Usuario conectado
		AppUser appUser = getAppUser(request);

		// Información de la valoración
		IdentificacionSerie identificacion = (IdentificacionSerie) getFromTemporalSession(
				request, FondosConstants.IDENTIFICACION_SERIE_KEY);

		try {
			SeriePO serie = (SeriePO) getFromTemporalSession(request,
					FondosConstants.ELEMENTO_CF_KEY);

			InfoBProcedimiento procedimiento = identificacion
					.getProcedimiento();
			// List productores = identificacion.getProductoresSerie();

			ServiceRepository services = getServiceRepository(request);
			GestionInfoSistemaBI infoSistema = services.lookupInfoSistemaBI();
			PdfReport report = new PdfReport(infoSistema.getManejadorIText(),
					appUser.getEntity());

			report.addTitle(Messages.getString(
					Constants.INFORME_IDENTIFICACION_SERIE_TITULO,
					request.getLocale())
					+ serie.getTitulo());
			report.addAuthor(appUser.getNombreCompleto());
			report.addSubject(Messages.getString(
					Constants.INFORME_IDENTIFICACION_SERIE_TITULO,
					request.getLocale()));

			// step 3: we open the document
			report.open();

			report.addReportTitle(Messages.getString(
					Constants.INFORME_IDENTIFICACION_SERIE_TITULO,
					request.getLocale()).toUpperCase()
					+ ": " + serie.getTitulo());

			// Datos de la serie
			report.addSection(Messages.getString(
					"archigest.archivo.informeValoracion.datosSerie",
					request.getLocale()));
			report.addData(Messages.getString(
					"archigest.archivo.informeValoracion.contexto",
					request.getLocale()), serie.getContexto());
			report.addData(Messages.getString(
					"archigest.archivo.informeValoracion.serie",
					request.getLocale()),
					serie.getCodigo() + " " + serie.getDenominacion());

			// Datos del procedimiento de la serie
			if (procedimiento != null) {
				report.addSection(Messages
						.getString(
								"archigest.archivo.informeValoracion.datosProcedimiento",
								request.getLocale()));
				report.addData(
						Messages.getString(
								"archigest.archivo.informeValoracion.codigoProcedimiento",
								request.getLocale()), procedimiento.getCodigo());
				report.addData(
						Messages.getString(
								"archigest.archivo.informeValoracion.tituloProcedimiento",
								request.getLocale()), procedimiento.getNombre());
			}

			// Contenido de la serie
			report.addSection(Messages.getString(
					"archigest.archivo.informeValoracion.contenidoSerie",
					request.getLocale()));
			report.addData(Messages.getString(
					"archigest.archivo.identificacion.definicion",
					request.getLocale()), identificacion.getDefinicion());
			report.addData(Messages.getString(
					"archigest.archivo.identificacion.tramites",
					request.getLocale()), identificacion.getTramites());
			report.addData(Messages.getString(
					"archigest.archivo.identificacion.normativa",
					request.getLocale()), identificacion.getNormativa());
			report.addData(
					Messages.getString(
							"archigest.archivo.informeValoracion.documentosBasicosSerie",
							request.getLocale()), identificacion
							.getDocsBasicosUnidadDocumental());

			if (ConfigConstants.getInstance()
					.getMostrarInformacionIdentificacionExtendia()) {
				report.addData(Messages.getString(
						"archigest.archivo.series.tipo.documentacion",
						request.getLocale()), identificacion
						.getTipoDocumentacion());
				report.addData(Messages.getString(
						"archigest.archivo.series.prevision.anual.volumen",
						request.getLocale()), identificacion
						.getVolumenPrevisionAnual());
				report.addData(Messages.getString(
						"archigest.archivo.series.prevision.anual.soporte",
						request.getLocale()), identificacion
						.getSoportePrevisionAnual());
			}

			if (serie.getNumunidaddoc() > 0) {
				report.add(new Paragraph("\t"));
				report.addSubSection(Messages
						.getString(
								"archigest.archivo.informeValoracion.descripcionFisica",
								request.getLocale()));
				report.addData(Messages.getString(
						"archigest.archivo.informeValoracion.nudocs",
						request.getLocale()),
						new Integer(serie.getNumunidaddoc()).toString());
				report.addData(Messages.getString(
						"archigest.archivo.informeValoracion.nuinst",
						request.getLocale()),
						new Integer(serie.getNumuinstalacion()).toString());
				report.addData(Messages.getString(
						"archigest.archivo.informeValoracion.volumen",
						request.getLocale()), serie.getVolumenEnMetros()
						.toString());

				report.add(new Paragraph("\t"));
				report.addSubSection(Messages.getString(
						"archigest.archivo.informeValoracion.fechasExtremas",
						request.getLocale()));
				report.addData(Messages.getString(
						"archigest.archivo.informeValoracion.fechaInicial",
						request.getLocale()), DateUtils.formatDate(serie
						.getFextremainicial()));
				report.addData(Messages.getString(
						"archigest.archivo.informeValoracion.fechaFinal",
						request.getLocale()), DateUtils.formatDate(serie
						.getFextremafinal()));
			}

			// Productores Vigentes de la serie
			report.addSection(Messages.getString(
					"archigest.archivo.informeValoracion.productoresVigentes",
					request.getLocale()));
			GestionSeriesBI serviceSeries = getGestionSeriesBI(request);
			List listaProductoresVigentes = serviceSeries
					.getProductoresVigentesBySerie(serie.getId());
			if (!ListUtils.isEmpty(listaProductoresVigentes)) {
				PdfReportTable table = report
						.createTable(new float[] { 80, 20 });
				table.addColumnTitles(new String[] {
						Messages.getString(
								"archigest.archivo.informeValoracion.nombreProductor",
								request.getLocale()),
						Messages.getString(
								"archigest.archivo.informeValoracion.tipoProductor",
								request.getLocale()) });

				// InfoProductorSerie productor;
				for (int i = 0; i < listaProductoresVigentes.size(); i++) {

					ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductoresVigentes
							.get(i);
					table.addRowValue(productorSerieVO.getNombre());

					if (productorSerieVO.getTipoProductor() == fondos.vos.ProductorSerieVO.TIPO_ENTIDAD)
						table.addRowValue(Messages
								.getString(
										"archigest.archivo.informeValoracion.tipoProductorE",
										request.getLocale()));
					else
						table.addRowValue(Messages
								.getString(
										"archigest.archivo.informeValoracion.tipoProductorO",
										request.getLocale()));
				}

				report.add(table);
			} else
				report.addMessage(Messages
						.getString(
								"archigest.archivo.informeValoracion.noHayProductoresV",
								request.getLocale()));

			// Productores Historicos de la serie
			report.addSection(Messages
					.getString(
							"archigest.archivo.informeValoracion.productoresHistoricos",
							request.getLocale()));

			List listaProductoresHistoricos = serviceSeries
					.getProductoresHistoricosBySerie(serie.getId());
			if (!ListUtils.isEmpty(listaProductoresHistoricos)) {
				PdfReportTable table = report
						.createTable(new float[] { 80, 20 });
				table.addColumnTitles(new String[] {
						Messages.getString(
								"archigest.archivo.informeValoracion.nombreProductor",
								request.getLocale()),
						Messages.getString(
								"archigest.archivo.informeValoracion.tipoProductor",
								request.getLocale()) });

				// InfoProductorSerie productor;
				for (int i = 0; i < listaProductoresHistoricos.size(); i++) {

					ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductoresHistoricos
							.get(i);
					table.addRowValue(productorSerieVO.getNombre());

					if (productorSerieVO.getTipoProductor() == fondos.vos.ProductorSerieVO.TIPO_ENTIDAD)
						table.addRowValue(Messages
								.getString(
										"archigest.archivo.informeValoracion.tipoProductorE",
										request.getLocale()));
					else
						table.addRowValue(Messages
								.getString(
										"archigest.archivo.informeValoracion.tipoProductorO",
										request.getLocale()));
				}

				report.add(table);
			} else
				report.addMessage(Messages
						.getString(
								"archigest.archivo.informeValoracion.noHayProductoresH",
								request.getLocale()));

			report.close();

			// Visualizar el informe
			outReport(report.getPDFContent(), REPORT_NAME, response);
		} catch (Exception e) {
			logger.error("No se ha podido generar informe " + REPORT_NAME, e);
			throw new ArchigestActionProcessException(e, "Generando informe "
					+ REPORT_NAME);
		}
	}

	/**
	 * Muestra el informe.
	 * 
	 * @param report
	 *            Contenido del informe.
	 * @param reportName
	 *            Nombre del informe.
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws IOException
	 *             si ocurre algún error.
	 */
	protected void outReport(byte report[], String reportName,
			HttpServletResponse response) throws IOException {
		if (report != null && report.length > 0) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename="
					+ reportName + ".pdf");
			ResponseUtil.getInstance().agregarCabecerasHTTP(response);
			response.setContentLength(report.length);

			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(report, 0, report.length);
			ouputStream.flush();
			ouputStream.close();
		}
	}
}
