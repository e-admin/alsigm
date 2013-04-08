package valoracion.actions;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import se.procedimientos.InfoBProcedimiento;
import se.usuarios.AppUser;
import util.CollectionUtils;
import valoracion.ValoracionConstants;
import valoracion.model.ValoracionSerie;
import valoracion.view.InfoSerie;
import valoracion.view.SeriePO;
import valoracion.view.ValoracionSeriePO;
import valoracion.vos.PlazoValoracionVO;
import valoracion.vos.ValoracionSerieVO;

import com.lowagie.text.Chunk;
import com.lowagie.text.Paragraph;
import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionNivelesArchivoBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.reports.PdfReport;
import common.reports.PdfReportTable;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.ResponseUtil;
import common.util.StringUtils;

import configuracion.bi.GestionInfoSistemaBI;
import es.archigest.framework.web.action.ArchigestActionProcessException;
import fondos.model.IdentificacionSerie;
import gcontrol.vos.NivelArchivoVO;

/**
 * Informe de valoración.
 */
public class InformeValoracionPDFReportAction extends BaseAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = "informeValoracion";

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

		// Recursos de mensajes de la aplicación
		MessageResources messages = getMessageResources(request);

		// Información de la valoración
		ValoracionSeriePO valoracion = (ValoracionSeriePO) getFromTemporalSession(
				request, ValoracionConstants.VALORACION_KEY);

		try {
			SeriePO serie = valoracion.getSerie();
			InfoBProcedimiento procedimiento = null;
			String definicion = null, tramites = null, normativa = null, documentosBasicos = null, nUDocs = null, nUInst = null, volumen = null, contexto = null;
			String tipoDocumentacion = null;
			String volumenPrevisionAnual = null;
			String soportePrevisionAnual = null;

			Date fechaInicial = null, fechaFinal = null;
			List listaProductoresVigentes = null, listaProductoresHistoricos = null;

			ValoracionSerie identificacionValoracion = null;

			// En aquellos estados en los que la valoración se encuentra cerrada
			// en cuanto a información de la serie, se
			// lee la información de la misma del campo infoSerie, en otro caso,
			// se tomará de la serie en el momento actual
			if (StringUtils.isNotEmpty(valoracion.getInfoSerie())
					&& (valoracion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA
							|| valoracion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION || valoracion
							.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA))

			{
				identificacionValoracion = valoracion
						.getSerieExtendidaValoracion();

				contexto = valoracion.getContextoSerieValoracion();
				procedimiento = identificacionValoracion.getProcedimiento();
				definicion = identificacionValoracion.getDefinicion();
				tramites = identificacionValoracion.getTramites();
				normativa = identificacionValoracion.getNormativa();
				documentosBasicos = identificacionValoracion
						.getDocsBasicosUnidadDocumental();
				nUDocs = identificacionValoracion
						.getNumeroUnidadesDocumentales();
				nUInst = identificacionValoracion
						.getNumeroUnidadesInstalacion();
				volumen = identificacionValoracion.getVolumenEnMetros();
				fechaInicial = identificacionValoracion.getFechaInicial();
				fechaFinal = identificacionValoracion.getFechaFinal();
				listaProductoresVigentes = identificacionValoracion
						.getProductoresvigentes();
				listaProductoresHistoricos = identificacionValoracion
						.getProductoreshistoricos();

				if (ConfigConstants.getInstance()
						.getMostrarInformacionIdentificacionExtendia()) {
					tipoDocumentacion = identificacionValoracion
							.getTipoDocumentacion();
					volumenPrevisionAnual = identificacionValoracion
							.getVolumenPrevisionAnual();
					soportePrevisionAnual = identificacionValoracion
							.getSoportePrevisionAnual();
				}

			}
			/*
			 * else {
			 */
			// Aquí puede ser null si no se ha rellenado la valoración o si el
			// estado indica que aún no se ha rellenado el campo a leer
			if (identificacionValoracion == null) {
				GestionSeriesBI serviceSeries = getGestionSeriesBI(request);
				IdentificacionSerie identificacion = valoracion
						.getSerieExtendida();

				contexto = serie.getContexto();
				procedimiento = identificacion.getProcedimiento();
				definicion = identificacion.getDefinicion();
				tramites = identificacion.getTramites();
				normativa = identificacion.getNormativa();
				documentosBasicos = identificacion
						.getDocsBasicosUnidadDocumental();
				nUDocs = serie.getNumunidaddoc() == 0 ? Constants.STRING_CERO
						: new Integer(serie.getNumunidaddoc()).toString();
				nUInst = serie.getNumuinstalacion() == 0 ? Constants.STRING_CERO
						: new Integer(serie.getNumuinstalacion()).toString();
				volumen = serie.getVolumen() == 0 ? Constants.STRING_CERO
						: serie.getVolumenEnMetros();
				fechaInicial = serie.getFextremainicial();
				fechaFinal = serie.getFextremafinal();
				listaProductoresVigentes = ValoracionSerie
						.productorSerieVOToListaNombreTipo(serviceSeries
								.getProductoresVigentesBySerie(serie.getId()));
				listaProductoresHistoricos = ValoracionSerie
						.productorSerieVOToListaNombreTipo(serviceSeries
								.getProductoresHistoricosBySerie(serie.getId()));

				if (ConfigConstants.getInstance()
						.getMostrarInformacionIdentificacionExtendia()) {
					tipoDocumentacion = identificacion.getTipoDocumentacion();
					volumenPrevisionAnual = identificacion
							.getVolumenPrevisionAnual();
					soportePrevisionAnual = identificacion
							.getSoportePrevisionAnual();
				}

			}
			// }

			List seriesPrecedentes = valoracion.getListaSeriesPrecedentes();
			List seriesRelacionadas = valoracion.getListaSeriesRelacionadas();

			ServiceRepository services = getServiceRepository(request);
			GestionInfoSistemaBI infoSistema = services.lookupInfoSistemaBI();
			PdfReport report = new PdfReport(infoSistema.getManejadorIText(),
					appUser.getEntity());

			// report.addTitle("Informe de la valoración " +
			// valoracion.getTitulo());
			report.addTitle(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_PREFIJO_TITULO)
					+ valoracion.getTitulo());
			report.addAuthor(appUser.getNombreCompleto());
			// report.addSubject("Informe de Valoración");
			report.addSubject(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_ASUNTO));

			// step 3: we open the document
			report.open();

			// report.addReportTitle("INFORME DE VALORACIÓN: " +
			// valoracion.getTitulo());
			report.addReportTitle(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TITULO)
					+ valoracion.getTitulo());

			// Datos de la serie
			// report.addSection("DATOS DE LA SERIE");
			report.addSection(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_DATOS_SERIE));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_CONTEXTO),
					contexto);
			// report.addData("Contexto", contexto);
			// report.addData("Contexto", serie.getContexto());
			// report.addData("Serie", serie.getCodigo() + " " +
			// serie.getDenominacion());
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_SERIE),
					serie.getCodigo() + " " + serie.getDenominacion());

			// Datos del procedimiento de la serie
			if (procedimiento != null) {
				// report.addSection("DATOS DEL PROCEDIMIENTO");
				report.addSection(messages
						.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_DATOS_PROCEDIMIENTO));
				// report.addData("Código", procedimiento.getCodigo());
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_CODIGO_PROCEDIMIENTO),
						procedimiento.getCodigo());
				// report.addData("Título", procedimiento.getNombre());
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TITULO_PROCEDIMIENTO),
						procedimiento.getNombre());
			}

			// Contenido de la serie
			// report.addSection("CONTENIDO DE LA SERIE");
			report.addSection(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_CONTENIDO_SERIE));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_DEFINICION_SERIE),
					definicion);
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TRAMITES_SERIE),
					tramites);
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NORMATIVA_SERIE),
					normativa);
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_DOCUMENTOS_BASICOS_SERIE),
					documentosBasicos);

			if (ConfigConstants.getInstance()
					.getMostrarInformacionIdentificacionExtendia()) {
				report.addData(Messages.getString(
						"archigest.archivo.series.tipo.documentacion",
						request.getLocale()), tipoDocumentacion);
				report.addData(Messages.getString(
						"archigest.archivo.series.prevision.anual.volumen",
						request.getLocale()), volumenPrevisionAnual);
				report.addData(Messages.getString(
						"archigest.archivo.series.prevision.anual.soporte",
						request.getLocale()), soportePrevisionAnual);
			}

			/*
			 * report.addData("Definición", definicion);
			 * report.addData("Trámites", tramites); report.addData("Normativa",
			 * normativa); report.addData("Documentos Básicos",
			 * documentosBasicos);
			 */
			if (StringUtils.isNotEmpty(nUDocs)
					&& StringUtils.isNotEmpty(nUInst)
					&& StringUtils.isNotEmpty(volumen)) {
				report.add(new Paragraph("\t"));
				report.addSubSection(messages
						.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_DESCRIPCION_FISICA));
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NUM_UDOCS),
						nUDocs);
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NUM_UINST),
						nUInst);
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_VOLUMEN),
						volumen);
				/*
				 * report.addSubSection("DESCRIPCIÓN FÍSICA");
				 * report.addData("Nº Uni. Documentales", nUDocs);
				 * report.addData("Nº Uni. Instalación", nUInst);
				 * report.addData("Volumen (m/l)", new
				 * Integer(volumen).toString());
				 */
			}

			if (fechaInicial != null && fechaFinal != null) {
				report.add(new Paragraph("\t"));
				report.addSubSection(messages
						.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_FECHAS_EXTREMAS));
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_FECHA_INICIAL),
						DateUtils.formatDate(fechaInicial));
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_FECHA_FINAL),
						DateUtils.formatDate(fechaFinal));
				/*
				 * report.addSubSection("FECHAS EXTREMAS");
				 * report.addData("F. Inicial",
				 * DateUtils.formatDate(fechaInicial));
				 * report.addData("F. Final", DateUtils.formatDate(fechaFinal));
				 */
			}

			// Productores Vigentes de la serie
			// report.addSection("PRODUCTORES VIGENTES DE LA SERIE");
			report.addSection(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_PRODUCTORES_VIGENTES));
			// GestionSeriesBI serviceSeries = getGestionSeriesBI(request);
			// List
			// listaProductoresVigentes=identificacionValoracion.getProductoresvigentes();
			if (!ListUtils.isEmpty(listaProductoresVigentes)) {
				PdfReportTable table = report
						.createTable(new float[] { 80, 20 });
				// table.addColumnTitles(new String [] { "NOMBRE", "TIPO" });
				table.addColumnTitles(new String[] {
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NOMBRE_PRODUCTOR),
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR) });

				// InfoProductorSerie productor;
				for (int i = 0; i < listaProductoresVigentes.size(); i++) {
					List productorSerie = (List) listaProductoresVigentes
							.get(i);
					String nombre = (String) productorSerie.get(0);
					table.addRowValue(nombre);

					String tipo = (String) productorSerie.get(1);
					if (tipo == new Integer(
							fondos.vos.ProductorSerieVO.TIPO_ENTIDAD)
							.toString())
						// table.addRowValue("Entidad Productora");
						table.addRowValue(messages
								.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR_E));
					else
						// table.addRowValue("Órgano");
						table.addRowValue(messages
								.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR_O));
				}

				report.add(table);
			} else {
				// report.addMessage("No se ha vinculado a la serie con ningún productor vigente");
				report.addMessage(messages
						.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NO_HAY_PRODUCTORES_V));
			}

			// Productores Historicos de la serie
			// report.addSection("PRODUCTORES HISTORICOS DE LA SERIE");
			report.addSection(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_PRODUCTORES_HISTORICOS));

			// List
			// listaProductoresHistoricos=serviceSeries.getProductoresHistoricosBySerie(serie.getId());
			// List
			// listaProductoresHistoricos=identificacionValoracion.getProductoreshistoricos();
			if (!ListUtils.isEmpty(listaProductoresHistoricos)) {
				PdfReportTable table = report
						.createTable(new float[] { 80, 20 });
				// table.addColumnTitles(new String [] { "NOMBRE", "TIPO" });
				table.addColumnTitles(new String[] {
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NOMBRE_PRODUCTOR),
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR) });

				// InfoProductorSerie productor;
				for (int i = 0; i < listaProductoresHistoricos.size(); i++) {

					List productorSerie = (List) listaProductoresHistoricos
							.get(i);
					String nombre = (String) productorSerie.get(0);
					table.addRowValue(nombre);

					String tipo = (String) productorSerie.get(1);
					if (tipo == new Integer(
							fondos.vos.ProductorSerieVO.TIPO_ENTIDAD)
							.toString())
						// table.addRowValue("Entidad Productora");
						table.addRowValue(messages
								.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR_E));
					else
						// table.addRowValue("Órgano");
						table.addRowValue(messages
								.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TIPO_PRODUCTOR_O));
				}

				report.add(table);
			} else
				// report.addMessage("No se ha vinculado a la serie con ningún productor histórico");
				report.addMessage(messages
						.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_NO_HAY_PRODUCTORES_H));

			// Datos de la Valoración
			report.addSection(messages
					.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_DATOS_VALORACION));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TITULO_VALORACION),
					valoracion.getTitulo());
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_ESTADO_VALORACION),
					Messages.getString(
							ValoracionConstants.PREFIX_KEY_VALORACIONES
									+ valoracion.getEstado(),
							request.getLocale()));
			/*
			 * report.addSection("DATOS DE LA VALORACIÓN");
			 * report.addData("Título", valoracion.getTitulo());
			 * report.addData("Estado",
			 * Messages.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES +
			 * valoracion.getEstado()));
			 */

			if (valoracion.getFechaValidacion() != null) {
				report.add(new Paragraph("\t"));
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_FECHA_VALIDACION),
						DateUtils.formatDate(valoracion.getFechaValidacion()));
				// report.addData("Fecha de Validación",
				// DateUtils.formatDate(valoracion.getFechaValidacion()));
			}

			if (valoracion.getFechaEvaluacion() != null) {
				report.add(new Paragraph("\t"));
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACIONES_FECHA_EVALUACION),
						DateUtils.formatDate(valoracion.getFechaEvaluacion()));
				// report.addData("Fecha de Evaluación",
				// DateUtils.formatDate(valoracion.getFechaEvaluacion()));
			}

			if (valoracion.getFechaDictamen() != null) {
				report.add(new Paragraph("\t"));
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_FECHA_DICTAMEN),
						DateUtils.formatDate(valoracion.getFechaDictamen()));
				// report.addData("Fecha de Dictamen",
				// DateUtils.formatDate(valoracion.getFechaDictamen()));

				if (valoracion.getFechaResolucionDictamen() != null) {
					report.addData(
							messages.getMessage(ValoracionConstants.LABEL_VALORACION_FECHA_RESOLUCION_DICTAMEN),
							DateUtils.formatDate(valoracion
									.getFechaResolucionDictamen()));
					report.addData(
							messages.getMessage(ValoracionConstants.LABEL_VALORACION_BOLETIN_DICTAMEN),
							valoracion.getBoletinDictamen());
					report.addData(
							messages.getMessage(ValoracionConstants.LABEL_VALORACION_FECHA_BOLETIN_DICTAMEN),
							DateUtils.formatDate(valoracion
									.getFechaBoletinDictamen()));

					/*
					 * report.addData("Fecha de Resolución",
					 * DateUtils.formatDate
					 * (valoracion.getFechaResolucionDictamen()));
					 * report.addData("Boletín del Dictamen",
					 * valoracion.getBoletinDictamen());
					 * report.addData("Fecha del Boletín",
					 * DateUtils.formatDate(valoracion
					 * .getFechaBoletinDictamen()));
					 */
				}

				if (StringUtils.isNotBlank(valoracion.getMotivoRechazo()))
					// report.addData("Motivo de rechazo",
					// valoracion.getMotivoRechazo());
					report.addData(
							messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_MOTIVO_RECHAZO),
							valoracion.getMotivoRechazo());
			}

			report.add(new Paragraph("\t"));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_VALORACION_ORDENACION_PRIMER_NIVEL),
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ valoracion.getOrdenacionSerie1(),
							request.getLocale()));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_VALORACION_ORDENACION_SEGUNDO_NIVEL),
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ valoracion.getOrdenacionSerie2(),
							request.getLocale()));

			/*
			 * report.addData("Ordenación Serie Primer Nivel",
			 * Messages.getString
			 * (ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_ORDENACION
			 * + valoracion.getOrdenacionSerie1()));
			 * report.addData("Ordenación Serie Segundo Nivel",
			 * Messages.getString
			 * (ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_ORDENACION
			 * + valoracion.getOrdenacionSerie2()));
			 */

			if (StringUtils
					.isNotBlank(valoracion.getDocumentosRecopilatorios())) {
				report.add(new Paragraph("\t"));
				// report.addData("Documentos Recopilatorios",
				// valoracion.getDocumentosRecopilatorios());
				report.addData(
						messages.getMessage(Constants.VALORACION_DOCUMENTOSRECOPILATORIOS),
						valoracion.getDocumentosRecopilatorios());
			}

			report.add(new Paragraph("\t"));
			// report.addData("Valor Administrativo",
			// Messages.getString(ValoracionConstants.LABEL_VALORACION_VALOR_ADMINISTRTIVO
			// + valoracion.getTipoValorAdministrativo()));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_VALORACION_VALOR_ADMINISTRTIVO),
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_VALOR_ADMINISTRTIVO
									+ valoracion.getTipoValorAdministrativo(),
							request.getLocale()));
			if (valoracion.getTipoValorAdministrativo() == ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL)
				report.addData(
						messages.getMessage(Constants.VALORACION_PERIODOVIGENCIA_ADM),
						valoracion.getAnosVigenciaAdministrativa()
								+ messages
										.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_PERIODO_VIGENCIA_ANIOS));
			// report.addData("Período Vigencia Administrativa",
			// valoracion.getAnosVigenciaAdministrativa() + " años");
			if (StringUtils.isNotBlank(valoracion.getValorAdministrativo()))
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_JUSTIFICACION),
						valoracion.getValorAdministrativo());
			// report.addData("Justificación",
			// valoracion.getValorAdministrativo());

			report.add(new Paragraph("\t"));
			// report.addData("Valor Legal",
			// Messages.getString(ValoracionConstants.LABEL_VALORACION_VALOR_LEGAL
			// + valoracion.getTipoValorLegal()));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_VALORACION_VALOR_LEGAL),
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_VALOR_LEGAL
									+ valoracion.getTipoValorLegal(),
							request.getLocale()));
			if (valoracion.getTipoValorLegal() == ValoracionSerieVO.VALOR_LEGAL_TEMPORAL)
				// report.addData("Período Vigencia Legal",
				// valoracion.getAnosVigenciaLegal() + " años");
				report.addData(
						messages.getMessage(Constants.VALORACION_PERIODOVIGENCIA_LEGAL),
						valoracion.getAnosVigenciaLegal()
								+ messages
										.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_PERIODO_VIGENCIA_ANIOS));
			if (StringUtils.isNotBlank(valoracion.getValorLegal()))
				// report.addData("Justificación", valoracion.getValorLegal());
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_JUSTIFICACION),
						valoracion.getValorLegal());

			report.add(new Paragraph("\t"));
			// report.addData("Valor Informativo",
			// Messages.getString(ValoracionConstants.LABEL_VALORACION_VALOR_INFORMATIVO
			// + valoracion.getTipoValorInformativo()));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_VALORACION_VALOR_INFORMATIVO),
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_VALOR_INFORMATIVO
									+ valoracion.getTipoValorInformativo(),
							request.getLocale()));
			if (valoracion.getTipoValorInformativo() == ValoracionSerieVO.VALOR_INFORMATIVO_EXISTE) {
				// report.addData("Valor Cientifico",
				report.addData(
						messages.getMessage(Constants.VALORACION_VALORCIENTIFICO),
						valoracion.getTipoValorCientifico() == ValoracionSerieVO.VALOR_CIENTIFICO_EXISTE ? Messages
								.getString(Constants.ETIQUETA_SI,
										request.getLocale()) : Messages
								.getString(Constants.ETIQUETA_NO,
										request.getLocale()));
				if (StringUtils.isNotBlank(valoracion.getValorCientifico()))
					report.addData(null, valoracion.getValorCientifico());

				// report.addData("Valor Tecnológico",
				report.addData(
						messages.getMessage(Constants.VALORACION_VALORTECNOLOGICO),
						valoracion.getTipoValorTecnologico() == ValoracionSerieVO.VALOR_TECNOLOGICO_EXISTE ? Messages
								.getString(Constants.ETIQUETA_SI,
										request.getLocale()) : Messages
								.getString(Constants.ETIQUETA_NO,
										request.getLocale()));
				if (StringUtils.isNotBlank(valoracion.getValorTecnologico()))
					report.addData(null, valoracion.getValorTecnologico());

				// report.addData("Valor Cultural",
				report.addData(
						messages.getMessage(Constants.VALORACION_VALORCULTURAL),
						valoracion.getTipoValorCultural() == ValoracionSerieVO.VALOR_CULTURAL_EXISTE ? Messages
								.getString(Constants.ETIQUETA_SI,
										request.getLocale()) : Messages
								.getString(Constants.ETIQUETA_NO,
										request.getLocale()));
				if (StringUtils.isNotBlank(valoracion.getValorCultural()))
					report.addData(null, valoracion.getValorCultural());
			} else if (StringUtils.isNotBlank(valoracion.getValorInformativo()))
				// report.addData("Justificación",
				// valoracion.getValorInformativo());
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_JUSTIFICACION),
						valoracion.getValorInformativo());

			report.add(new Paragraph("\t"));
			// report.addData("Régimen de Acceso",
			// Messages.getString(Constants.VALORACION_REGIMENACCESO +
			// valoracion.getTipoRegimenAcceso()));

			String messageRegimenAcceso = Messages.getString(
					Constants.VALORACION_REGIMENACCESO
							+ valoracion.getTipoRegimenAcceso(),
					request.getLocale());

			if (valoracion.getTipoRegimenAccesoTemporal() == ValoracionSerieVO.SUBTIPO_REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL_PARCIAL) {
				messageRegimenAcceso += Constants.STRING_SPACE
						+ messages
								.getMessage(ValoracionConstants.LABEL_VALORACION_REGIMEN_ACCESO_TEMPORAL_PARCIAL);
			} else if (valoracion.getTipoRegimenAccesoTemporal() == ValoracionSerieVO.SUBTIPO_REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL_TOTAL) {
				messageRegimenAcceso += Constants.STRING_SPACE
						+ messages
								.getMessage(ValoracionConstants.LABEL_VALORACION_REGIMEN_ACCESO_TEMPORAL_TOTAL);
			}

			report.addData(
					messages.getMessage(Constants.VALORACION_REGIMENACCESO),
					messageRegimenAcceso);
			if (valoracion.getTipoRegimenAcceso() == ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL)
				// report.addData("Período de Restricción",
				// valoracion.getAnosRegimenAcceso() + " años");
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_PERIODO_REGIMEN_ACCESO),
						valoracion.getAnosRegimenAcceso()
								+ messages
										.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_PERIODO_VIGENCIA_ANIOS));
			if (StringUtils.isNotBlank(valoracion.getRegimenAcceso()))
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_JUSTIFICACION),
						valoracion.getRegimenAcceso());

			report.add(new Paragraph("\t"));
			// report.addData("Propuesta de Dictamen",
			// Messages.getString(ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN
			// + valoracion.getValorDictamen()));
			report.addData(
					messages.getMessage(ValoracionConstants.LABEL_VALORACION_VALORES_DICTAMEN),
					Messages.getString(
							ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN
									+ valoracion.getValorDictamen(),
							request.getLocale()));
			if (StringUtils.isNotBlank(valoracion.getValorDictamenValue()))
				report.addData(
						messages.getMessage(ValoracionConstants.LABEL_VALORACION_JUSTIFICACION),
						valoracion.getValorDictamenValue());

			if (valoracion.getValorDictamen() == ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_PARCIAL)
				// report.addData("Técnica de Muestreo",
				// Messages.getString(ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO
				// + valoracion.getTecnicaMuestreo()));
				report.addData(Messages.getString(
						ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO,
						request.getLocale()), Messages.getString(
						ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO
								+ valoracion.getTecnicaMuestreo(),
						request.getLocale()));

			// datos de plazos de transferencias.
			List listaPlazos = valoracion.getListaPlazos();
			if (listaPlazos != null && listaPlazos.size() > 0) {
				report.add(new Paragraph("\t"));
				report.addSection(messages.getMessage(
						ValoracionConstants.LABEL_VALORACION_PLAZOS_TITULO)
						.toUpperCase());
				if (!CollectionUtils.isEmpty(listaPlazos)) {
					PdfReportTable table = report.createTable(new float[] { 40,
							40, 20 });
					table.addColumnTitles(new String[] {
							messages.getMessage(ValoracionConstants.LABEL_VALORACION_PLAZOS_NIVELORIGEN),
							messages.getMessage(ValoracionConstants.LABEL_VALORACION_PLAZOS_NIVELDESTINO),
							messages.getMessage(ValoracionConstants.LABEL_VALORACION_PLAZOS_PLAZOANIOS) });

					GestionNivelesArchivoBI nivelesBI = services
							.lookupGestionNivelesArchivoBI();
					List listaNivelesArchivo = nivelesBI
							.getListaNivelesArchivo();
					HashMap nivelesHashMap = new HashMap();
					for (Iterator it = listaNivelesArchivo.iterator(); it
							.hasNext();) {
						NivelArchivoVO nivelVO = (NivelArchivoVO) it.next();
						nivelesHashMap
								.put(nivelVO.getId(), nivelVO.getNombre());
					}

					PlazoValoracionVO plazo;
					for (Iterator it = listaPlazos.iterator(); it.hasNext();) {
						plazo = (PlazoValoracionVO) it.next();
						table.addRowValue((String) (nivelesHashMap.get(plazo
								.getIdNivelOrigen())));
						table.addRowValue((String) (nivelesHashMap.get(plazo
								.getIdNivelDestino())));
						table.addRowValue("" + plazo.getPlazo());
					}
					report.add(table);
				}
			}

			// Series Precedentes
			// report.addSection("SERIES PRECEDENTES");
			report.addSection(messages.getMessage(
					ValoracionConstants.LABEL_VALORACION_SERIES_PRECEDENTES)
					.toUpperCase());
			if (!CollectionUtils.isEmpty(seriesPrecedentes)) {
				PdfReportTable table = report
						.createTable(new float[] { 40, 60 });
				// table.addColumnTitles(new String [] { "CÓDIGO", "TÍTULO" });
				table.addColumnTitles(new String[] {
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_CODIGO_SERIE),
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TITULO_SERIE) });

				InfoSerie seriePrecedente;
				for (int i = 0; i < seriesPrecedentes.size(); i++) {
					seriePrecedente = (InfoSerie) seriesPrecedentes.get(i);
					table.addRowValue(seriePrecedente.getCodigo());
					table.addRowValue(seriePrecedente.getTitulo());
				}

				report.add(table);
			} else
				report.addMessage(messages
						.getMessage(ValoracionConstants.LABEL_VALORACION_MSG_NO_SERIES_PRECEDENTES));

			// Series Relacionadas
			// report.addSection("SERIES RELACIONADAS");
			report.addSection(messages.getMessage(
					ValoracionConstants.LABEL_VALORACION_SERIES_RELACIONADAS)
					.toUpperCase());
			if (!CollectionUtils.isEmpty(seriesRelacionadas)) {
				PdfReportTable table = report
						.createTable(new float[] { 40, 60 });
				table.addColumnTitles(new String[] {
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_CODIGO_SERIE),
						messages.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_TITULO_SERIE) });

				InfoSerie serieRelacionada;
				for (int i = 0; i < seriesRelacionadas.size(); i++) {
					serieRelacionada = (InfoSerie) seriesRelacionadas.get(i);
					table.addRowValue(serieRelacionada.getCodigo());
					table.addRowValue(serieRelacionada.getTitulo());
				}

				report.add(table);
			} else
				report.addMessage(messages
						.getMessage(ValoracionConstants.LABEL_VALORACION_MSG_NO_SERIES_RELACIONADAS));

			// Visto Bueno
			report.add(new Paragraph("\t"));
			report.add(new Paragraph("\t"));
			report.add(new Paragraph(
					new Chunk(
							"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
									+ messages
											.getMessage(ValoracionConstants.LABEL_INFORMES_INFORMEVALORACION_VISTO_BUENO),
							// "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tVº Bº Secretario/a",
							PdfReport.DATA_VALUE_FONT)));

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
	/*
	 * 
	 * CÓDIGO ANTERIOR
	 * 
	 * protected void executeLogic(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) { // Usuario
	 * conectado AppUser appUser = getAppUser(request);
	 * 
	 * // Información de la valoración ValoracionSeriePO valoracion =
	 * (ValoracionSeriePO) getFromTemporalSession(request,
	 * ValoracionConstants.VALORACION_KEY);
	 * 
	 * try { SeriePO serie = valoracion.getSerie();
	 * 
	 * IdentificacionSerie identificacion = valoracion.getSerieExtendida();
	 * InfoBProcedimiento procedimiento = identificacion.getProcedimiento();
	 * //List productores = identificacion.getProductoresSerie(); List
	 * seriesPrecedentes = valoracion.getListaSeriesPrecedentes(); List
	 * seriesRelacionadas = valoracion.getListaSeriesRelacionadas();
	 * 
	 * PdfReport report = new PdfReport();
	 * 
	 * report.addTitle("Informe de la valoración " + valoracion.getTitulo());
	 * report.addAuthor(appUser.getNombreCompleto());
	 * report.addSubject("Informe de Valoración");
	 * 
	 * // step 3: we open the document report.open();
	 * 
	 * report.addReportTitle("INFORME DE VALORACIÓN: " +
	 * valoracion.getTitulo());
	 * 
	 * // Datos de la serie report.addSection("DATOS DE LA SERIE");
	 * report.addData("Contexto", serie.getContexto()); report.addData("Serie",
	 * serie.getCodigo() + " " + serie.getDenominacion());
	 * 
	 * // Datos del procedimiento de la serie if (procedimiento != null) {
	 * report.addSection("DATOS DEL PROCEDIMIENTO"); report.addData("Código",
	 * procedimiento.getCodigo()); report.addData("Título",
	 * procedimiento.getNombre()); }
	 * 
	 * // Contenido de la serie report.addSection("CONTENIDO DE LA SERIE");
	 * report.addData("Definición", identificacion.getDefinicion());
	 * report.addData("Trámites", identificacion.getTramites());
	 * report.addData("Normativa", identificacion.getNormativa());
	 * report.addData("Documentos Básicos",
	 * identificacion.getDocsBasicosUnidadDocumental()); if
	 * (serie.getNumunidaddoc() > 0) { report.add(new Paragraph("\t"));
	 * report.addSubSection("DESCRIPCIÓN FÍSICA");
	 * report.addData("Nº Uni. Documentales", new
	 * Integer(serie.getNumunidaddoc()).toString());
	 * report.addData("Nº Uni. Instalación", new
	 * Integer(serie.getNumuinstalacion()).toString());
	 * report.addData("Volumen (m/l)", new
	 * Integer(serie.getVolumen()).toString());
	 * 
	 * report.add(new Paragraph("\t")); report.addSubSection("FECHAS EXTREMAS");
	 * report.addData("F. Inicial",
	 * DateUtils.formatDate(serie.getFextremainicial()));
	 * report.addData("F. Final",
	 * DateUtils.formatDate(serie.getFextremafinal())); }
	 * 
	 * // Productores Vigentes de la serie
	 * report.addSection("PRODUCTORES VIGENTES DE LA SERIE"); GestionSeriesBI
	 * serviceSeries = getGestionSeriesBI(request); List
	 * listaProductoresVigentes
	 * =serviceSeries.getProductoresVigentesBySerie(serie.getId()); if
	 * (!ListUtils.isEmpty(listaProductoresVigentes)) { PdfReportTable table =
	 * report.createTable(new float[] {80, 20}); table.addColumnTitles(new
	 * String [] { "NOMBRE", "TIPO" });
	 * 
	 * //InfoProductorSerie productor; for (int i = 0; i <
	 * listaProductoresVigentes.size(); i++) {
	 * 
	 * ProductorSerieVO productorSerieVO = (ProductorSerieVO)
	 * listaProductoresVigentes.get(i);
	 * table.addRowValue(productorSerieVO.getNombre());
	 * 
	 * if (productorSerieVO.getTipoProductor() ==
	 * fondos.vos.ProductorSerieVO.TIPO_ENTIDAD)
	 * table.addRowValue("Entidad Productora"); else
	 * table.addRowValue("Órgano"); }
	 * 
	 * report.add(table); } else report.addMessage(
	 * "No se ha vinculado a la serie con ningún productor vigente");
	 * 
	 * 
	 * //Productores Historicos de la serie
	 * report.addSection("PRODUCTORES HISTORICOS DE LA SERIE");
	 * 
	 * List
	 * listaProductoresHistoricos=serviceSeries.getProductoresHistoricosBySerie
	 * (serie.getId()); if (!ListUtils.isEmpty(listaProductoresHistoricos)) {
	 * PdfReportTable table = report.createTable(new float[] {80, 20});
	 * table.addColumnTitles(new String [] { "NOMBRE", "TIPO" });
	 * 
	 * //InfoProductorSerie productor; for (int i = 0; i <
	 * listaProductoresHistoricos.size(); i++) {
	 * 
	 * ProductorSerieVO productorSerieVO = (ProductorSerieVO)
	 * listaProductoresHistoricos.get(i);
	 * table.addRowValue(productorSerieVO.getNombre());
	 * 
	 * if (productorSerieVO.getTipoProductor() ==
	 * fondos.vos.ProductorSerieVO.TIPO_ENTIDAD)
	 * table.addRowValue("Entidad Productora"); else
	 * table.addRowValue("Órgano"); }
	 * 
	 * report.add(table); } else report.addMessage(
	 * "No se ha vinculado a la serie con ningún productor histórico");
	 * 
	 * // Datos de la Valoración report.addSection("DATOS DE LA VALORACIÓN");
	 * report.addData("Título", valoracion.getTitulo());
	 * report.addData("Estado",
	 * Messages.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES +
	 * valoracion.getEstado()));
	 * 
	 * if (valoracion.getFechaValidacion() != null) { report.add(new
	 * Paragraph("\t")); report.addData("Fecha de Validación",
	 * DateUtils.formatDate(valoracion.getFechaValidacion())); }
	 * 
	 * if (valoracion.getFechaEvaluacion() != null) { report.add(new
	 * Paragraph("\t")); report.addData("Fecha de Evaluación",
	 * DateUtils.formatDate(valoracion.getFechaEvaluacion())); }
	 * 
	 * if (valoracion.getFechaDictamen() != null) { report.add(new
	 * Paragraph("\t")); report.addData("Fecha de Dictamen",
	 * DateUtils.formatDate(valoracion.getFechaDictamen()));
	 * 
	 * if (valoracion.getFechaResolucionDictamen() != null) {
	 * report.addData("Fecha de Resolución",
	 * DateUtils.formatDate(valoracion.getFechaResolucionDictamen()));
	 * report.addData("Boletín del Dictamen", valoracion.getBoletinDictamen());
	 * report.addData("Fecha del Boletín",
	 * DateUtils.formatDate(valoracion.getFechaBoletinDictamen())); }
	 * 
	 * if (StringUtils.isNotBlank(valoracion.getMotivoRechazo()))
	 * report.addData("Motivo de rechazo", valoracion.getMotivoRechazo()); }
	 * 
	 * report.add(new Paragraph("\t"));
	 * report.addData("Ordenación Serie Primer Nivel",
	 * Messages.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION +
	 * valoracion.getOrdenacionSerie1()));
	 * report.addData("Ordenación Serie Segundo Nivel",
	 * Messages.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION +
	 * valoracion.getOrdenacionSerie2()));
	 * 
	 * if (StringUtils.isNotBlank(valoracion.getDocumentosRecopilatorios())) {
	 * report.add(new Paragraph("\t"));
	 * report.addData("Documentos Recopilatorios",
	 * valoracion.getDocumentosRecopilatorios()); }
	 * 
	 * report.add(new Paragraph("\t")); report.addData("Valor Administrativo",
	 * Messages
	 * .getString(ValoracionConstants.LABEL_VALORACION_VALOR_ADMINISTRTIVO +
	 * valoracion.getTipoValorAdministrativo())); if
	 * (valoracion.getTipoValorAdministrativo() ==
	 * ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL)
	 * report.addData("Período Vigencia Administrativa",
	 * valoracion.getAnosVigenciaAdministrativa() + " años"); if
	 * (StringUtils.isNotBlank(valoracion.getValorAdministrativo()))
	 * report.addData("Justificación", valoracion.getValorAdministrativo());
	 * 
	 * report.add(new Paragraph("\t")); report.addData("Valor Legal",
	 * Messages.getString(ValoracionConstants.LABEL_VALORACION_VALOR_LEGAL +
	 * valoracion.getTipoValorLegal())); if (valoracion.getTipoValorLegal() ==
	 * ValoracionSerieVO.VALOR_LEGAL_TEMPORAL)
	 * report.addData("Período Vigencia Legal",
	 * valoracion.getAnosVigenciaLegal() + " años"); if
	 * (StringUtils.isNotBlank(valoracion.getValorLegal()))
	 * report.addData("Justificación", valoracion.getValorLegal());
	 * 
	 * report.add(new Paragraph("\t")); report.addData("Valor Informativo",
	 * Messages.getString(ValoracionConstants.LABEL_VALORACION_VALOR_INFORMATIVO
	 * + valoracion.getTipoValorInformativo())); if
	 * (valoracion.getTipoValorInformativo() ==
	 * ValoracionSerieVO.VALOR_INFORMATIVO_EXISTE) {
	 * report.addData("Valor Cientifico", valoracion.getTipoValorCientifico() ==
	 * ValoracionSerieVO.VALOR_CIENTIFICO_EXISTE ?
	 * Messages.getString(Constants.ETIQUETA_SI) :
	 * Messages.getString(Constants.ETIQUETA_NO) ); if
	 * (StringUtils.isNotBlank(valoracion.getValorCientifico()))
	 * report.addData(null, valoracion.getValorCientifico());
	 * 
	 * report.addData("Valor Tecnológico", valoracion.getTipoValorTecnologico()
	 * == ValoracionSerieVO.VALOR_TECNOLOGICO_EXISTE ?
	 * Messages.getString(Constants.ETIQUETA_SI) :
	 * Messages.getString(Constants.ETIQUETA_NO) ); if
	 * (StringUtils.isNotBlank(valoracion.getValorTecnologico()))
	 * report.addData(null, valoracion.getValorTecnologico());
	 * 
	 * report.addData("Valor Cultural", valoracion.getTipoValorCultural() ==
	 * ValoracionSerieVO.VALOR_CULTURAL_EXISTE ?
	 * Messages.getString(Constants.ETIQUETA_SI) :
	 * Messages.getString(Constants.ETIQUETA_NO) ); if
	 * (StringUtils.isNotBlank(valoracion.getValorCultural()))
	 * report.addData(null, valoracion.getValorCultural()); } else if
	 * (StringUtils.isNotBlank(valoracion.getValorInformativo()))
	 * report.addData("Justificación", valoracion.getValorInformativo());
	 * 
	 * report.add(new Paragraph("\t")); report.addData("Régimen de Acceso",
	 * Messages.getString(Constants.VALORACION_REGIMENACCESO +
	 * valoracion.getTipoRegimenAcceso())); if(valoracion.getTipoRegimenAcceso()
	 * == ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL)
	 * report.addData("Período de Restricción",
	 * valoracion.getAnosRegimenAcceso() + " años"); if
	 * (StringUtils.isNotBlank(valoracion.getRegimenAcceso()))
	 * report.addData("Justificación", valoracion.getRegimenAcceso());
	 * 
	 * report.add(new Paragraph("\t")); report.addData("Propuesta de Dictamen",
	 * Messages.getString(ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN +
	 * valoracion.getValorDictamen())); if (valoracion.getValorDictamen() ==
	 * ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_PARCIAL)
	 * report.addData("Técnica de Muestreo",
	 * Messages.getString(ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO
	 * + valoracion.getTecnicaMuestreo()));
	 * 
	 * // Series Precedentes report.addSection("SERIES PRECEDENTES"); if
	 * (!CollectionUtils.isEmpty(seriesPrecedentes)) { PdfReportTable table =
	 * report.createTable(new float[] {40, 60}); table.addColumnTitles(new
	 * String [] { "CÓDIGO", "TÍTULO" });
	 * 
	 * InfoSerie seriePrecedente; for (int i = 0; i < seriesPrecedentes.size();
	 * i++) { seriePrecedente = (InfoSerie) seriesPrecedentes.get(i);
	 * table.addRowValue(seriePrecedente.getCodigo());
	 * table.addRowValue(seriePrecedente.getTitulo()); }
	 * 
	 * report.add(table); } else
	 * report.addMessage("No se han definido series precedentes");
	 * 
	 * // Series Relacionadas report.addSection("SERIES RELACIONADAS"); if
	 * (!CollectionUtils.isEmpty(seriesRelacionadas)) { PdfReportTable table =
	 * report.createTable(new float[] {40, 60}); table.addColumnTitles(new
	 * String [] { "CÓDIGO", "TÍTULO" });
	 * 
	 * InfoSerie serieRelacionada; for (int i = 0; i <
	 * seriesRelacionadas.size(); i++) { serieRelacionada = (InfoSerie)
	 * seriesRelacionadas.get(i);
	 * table.addRowValue(serieRelacionada.getCodigo());
	 * table.addRowValue(serieRelacionada.getTitulo()); }
	 * 
	 * report.add(table); } else
	 * report.addMessage("No se han definido series relacionadas");
	 * 
	 * // Visto Bueno report.add(new Paragraph("\t")); report.add(new
	 * Paragraph("\t")); report.add(new Paragraph(new Chunk(
	 * "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tVº Bº Secretario/a",
	 * PdfReport.DATA_VALUE_FONT)));
	 * 
	 * report.close();
	 * 
	 * // Visualizar el informe outReport(report.getPDFContent(), REPORT_NAME,
	 * response); } catch (Exception e) {
	 * logger.error("No se ha podido generar informe " + REPORT_NAME, e); throw
	 * new ArchigestActionProcessException(e, "Generando informe " +
	 * REPORT_NAME); } }
	 */

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
