package transferencias.actions;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.EstadoCotejo;
import transferencias.TransferenciasConstants;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocElectronicaVO;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.Messages;
import common.actions.BaseAction;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.reports.PdfReport;
import common.reports.PdfReportTable;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.ResponseUtil;

import configuracion.bi.GestionInfoSistemaBI;
import es.archigest.framework.web.action.ArchigestActionProcessException;

public class InformeCotejoPDFReportAction extends BaseAction {

	/** Nombre del informe. */
	private final static String REPORT_NAME = ConfiguracionArchivoManager.INFORME_COTEJO;

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
		// IdentificacionSerie identificacion = (IdentificacionSerie)
		// getFromTemporalSession(request,
		// FondosConstants.IDENTIFICACION_SERIE_KEY);

		String idRelacion = request.getParameter("idRelacion");

		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(appUser));
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();

			GestionRelacionesEACRBI relacionCRBI = getGestionRelacionesEACRBI(request);

			RelacionEntregaVO relacionVO = relacionBI
					.abrirRelacionEntrega(idRelacion);
			RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
					.getInstance(services).transform(relacionVO);

			String tituloInforme = Messages.getString(
					Constants.TITULO_INFORME_COTEJO, request.getLocale());

			GestionInfoSistemaBI infoSistema = services.lookupInfoSistemaBI();

			PdfReport report = new PdfReport(infoSistema.getManejadorIText(),
					appUser.getEntity());

			report.addTitle(tituloInforme);
			report.addAuthor(appUser.getNombreCompleto());
			report.addSubject(Messages.getString(Constants.ETIQUETA_RELACION,
					request.getLocale())
					+ Constants.STRING_SPACE
					+ relacionPO.getCodigoTransferencia());

			// step 3: we open the document
			report.open();

			report.addReportTitle(tituloInforme);

			PdfReportTable table = report.createTable(new float[] { 40, 40, 20,
					60 });
			table.addColumnTitles(new String[] {
					Messages.getString(Constants.ETIQUETA_RELACION,
							request.getLocale()),
					Messages.getString(Constants.ETIQUETA_ESTADO,
							request.getLocale()),
					Messages.getString(Constants.ETIQUETA_F_ESTADO,
							request.getLocale()),
					Messages.getString(Constants.ETIQUETA_GESTOR,
							request.getLocale()) });
			table.addRowValue(relacionPO.getCodigoTransferencia());
			String nombreEstado = Messages
					.getString(
							TransferenciasConstants.TRANSFERENCIAS_ESTADO_RELACION
									+ "." + relacionPO.getEstado(),
							request.getLocale());
			table.addRowValue(nombreEstado);
			table.addRowValue(DateUtils.formatDate(relacionPO.getFechaestado()));
			table.addRowValue(relacionPO.getGestorEnArchivo()
					.getNombreCompleto());
			report.add(table);

			// Mostrar información de las unidades electrónicas
			List udocsElectronicas = null;
			if (relacionVO.isEntreArchivos()) {
				udocsElectronicas = relacionBI
						.getUDocsElectronicasByIdRelacionEntreArchivos(idRelacion);
			} else {
				udocsElectronicas = relacionBI
						.getUDocsElectronicasByIdRelacion(idRelacion);
			}

			if (!ListUtils.isEmpty(udocsElectronicas)) {
				PdfReportTable tableUdocsElectronicas = report
						.createTable(new float[] { 20, 40, 15, 25 });
				tableUdocsElectronicas.addColumnTitles(new String[] {
						Messages.getString(Constants.ETIQUETA_EXPEDIENTE,
								request.getLocale()),
						Messages.getString(Constants.ETIQUETA_ASUNTO,
								request.getLocale()),
						Messages.getString(Constants.ETIQUETA_ESTADO,
								request.getLocale()),
						Messages.getString(Constants.ETIQUETA_OBSERVACIONES,
								request.getLocale()) });

				report.addSection(Messages.getString(
						Constants.ETIQUETA_UNIDADES_ELECTRONICAS,
						request.getLocale()));
				if (relacionVO.isRelacionConReencajado()) {
					report.addData(Messages.getString(
							Constants.ETIQUETA_FORMATO_REENCAJADO,
							request.getLocale()), relacionPO.getFormatoRE()
							.getNombre());
				}

				ListIterator it = udocsElectronicas.listIterator();
				while (it.hasNext()) {
					UDocElectronicaVO udocElectronica = (UDocElectronicaVO) it
							.next();

					if (udocElectronica.getExpediente() == null) {
						udocElectronica.setExpediente(Constants.BLANK);
					}

					if (udocElectronica.getAsunto() == null) {
						udocElectronica.setAsunto(Constants.BLANK);
					}

					if (udocElectronica.getNotasCotejo() == null) {
						udocElectronica.setNotasCotejo(Constants.BLANK);
					}
					if (udocElectronica.getEstadoCotejo() == 0) {
						udocElectronica.setEstadoCotejo(EstadoCotejo.PENDIENTE
								.getIdentificador());
					}

					tableUdocsElectronicas.addRowValue(udocElectronica
							.getExpediente());
					tableUdocsElectronicas.addRowValue(udocElectronica
							.getAsunto());
					tableUdocsElectronicas
							.addRowValue(Messages.getString(
									EstadoCotejo.getEstadoCotejo(
											udocElectronica.getEstadoCotejo())
											.getKey(), request.getLocale()));
					tableUdocsElectronicas.addRowValue(udocElectronica
							.getNotasCotejo());
				}
				report.add(tableUdocsElectronicas);
			}

			boolean mostrarEstadoUdocs = true;

			// Obtiene las cajas que componen la relación de entrega.
			List cajas = null;

			if (relacionVO.isEntreArchivos()) {
				if (relacionVO.isRelacionConReencajado()) {
					cajas = relacionCRBI.getUIsReencajado(idRelacion);
				} else {
					cajas = relacionBI
							.getUnidadesInstalacionEntreArchivos(idRelacion);
					mostrarEstadoUdocs = false;
				}
			} else {
				cajas = relacionBI.getUnidadesInstalacion(idRelacion);
			}

			if (!CollectionUtils.isEmpty(cajas)) {
				String texto_si = Messages.getString(Constants.ETIQUETA_SI,
						request.getLocale());
				String texto_no = Messages.getString(Constants.ETIQUETA_NO,
						request.getLocale());

				for (int i = 0; i < cajas.size(); i++) {
					// Se obtiene la caja correspondiente para mostrar su
					// contenido.
					IUnidadInstalacionVO ui = (IUnidadInstalacionVO) cajas
							.get(i);

					report.addSection(Messages.getString(
							Constants.ETIQUETA_CAJA, request.getLocale())
							+ Constants.DOS_PUNTOS + ui.getOrden());

					report.addData(Messages.getString(
							Constants.ETIQUETA_SIGNATURA, request.getLocale()),
							ui.getSignaturaUI());
					report.addData(Messages.getString(
							Constants.ETIQUETA_ESTADO, request.getLocale()),
							Messages.getString(
									EstadoCotejo.getEstadoCotejo(
											ui.getEstadoCotejo()).getKey(),
									request.getLocale()));
					report.addData(Messages.getString(
							Constants.ETIQUETA_CAJA_DEVUELTA,
							request.getLocale()), ui.isDevolver() ? texto_si
							: texto_no);
					report.addData(Messages.getString(
							Constants.ETIQUETA_OBSERVACIONES,
							request.getLocale()), ui.getNotasCotejo());
					report.addData(Messages.getString(
							Constants.ETIQUETA_CONTENIDO, request.getLocale()),
							Constants.BLANK);

					UnidadInstalacionPO2 uInstalacion = (UnidadInstalacionPO2) UnidadInstalacionToPO2
							.getInstance(getServiceRepository(request))
							.transform(
									getGestionRelacionesBI(request)
											.getUnidadInstalacion(ui.getId()));

					// Se obtiene el contenido de la caja
					List listaElementosCaja = null;

					if (relacionVO.isRelacionConReencajado()) {
						listaElementosCaja = uInstalacion
								.getContenidoReencajado(ui.getId());
					} else {
						listaElementosCaja = uInstalacion.getContenido();
					}

					PdfReportTable tableUdocs = null;

					if (!CollectionUtils.isEmpty(listaElementosCaja)) {

						if (mostrarEstadoUdocs) {
							tableUdocs = report.createTable(new float[] { 20,
									40, 20, 20, 20, 15, 25 });

							tableUdocs
									.addColumnTitles(new String[] {
											Messages.getString(
													Constants.ETIQUETA_EXPEDIENTE,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_ASUNTO,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_FECHA_INICIO_CORTA,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_FECHA_FIN_CORTA,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_SIGNATURA,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_ESTADO,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_OBSERVACIONES,
													request.getLocale()) });
						} else {
							tableUdocs = report.createTable(new float[] { 20,
									80, 20, 20, 20 });

							tableUdocs
									.addColumnTitles(new String[] {
											Messages.getString(
													Constants.ETIQUETA_EXPEDIENTE,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_ASUNTO,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_FECHA_INICIO_CORTA,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_FECHA_FIN_CORTA,
													request.getLocale()),
											Messages.getString(
													Constants.ETIQUETA_SIGNATURA,
													request.getLocale())

									});

						}

						if ((listaElementosCaja != null)
								&& (!listaElementosCaja.isEmpty())) {
							ListIterator it = listaElementosCaja.listIterator();
							while (it.hasNext()) {
								// Se obtiene la parteDocumental correspondiente
								IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) it
										.next();

								if (relacionVO.isRelacionConReencajado()) {
									parteUDoc.setEstadoCotejo(ui
											.getEstadoCotejo());
								}

								// Obtener la unidad documental correspondiente
								// UnidadDocumentalVO unidadDocumentalVO =
								// relacionBI.getUnidadDocumental(parteUDoc.getIdUnidadDoc());

								if (parteUDoc.getExpediente() == null) {
									parteUDoc.setExpediente(Constants.BLANK);
								}

								if (parteUDoc.getSignaturaUdoc() == null) {
									parteUDoc.setSignaturaUdoc(Constants.BLANK);
								}

								if (parteUDoc.getAsunto() == null) {
									parteUDoc.setAsunto(Constants.BLANK);
								}

								if (parteUDoc.getNotasCotejo() == null) {
									parteUDoc.setNotasCotejo(Constants.BLANK);
								}

								// Obtener si es parte de un expediente
								// String parteExp = Constants.STRING_EMPTY;
								// if (parteUDoc.getTotalPartes()>1)
								// parteExp = " ("+parteUDoc.getNumParteUdoc()+
								// Constants.SLASH
								// +parteUDoc.getTotalPartes()+")";

								tableUdocs.addRowValue(parteUDoc
										.getExpediente()
										+ parteUDoc.getParteExp());
								tableUdocs.addRowValue(parteUDoc.getAsunto());
								tableUdocs.addRowValue(parteUDoc
										.getValorFechaInicio());
								tableUdocs.addRowValue(parteUDoc
										.getValorFechaFin());

								String signatura = ui.getSignaturaUI();
								if ((signatura != null)
										&& (!signatura
												.equals(Constants.STRING_EMPTY))) {
									String postUdocEnUI = String
											.valueOf(parteUDoc.getPosUdocEnUI());
									if ((postUdocEnUI != null)
											&& (!postUdocEnUI
													.equals(Constants.STRING_EMPTY))) {
										signatura += Constants.SEPARADOR_SIGNATURA_UNIDAD_DOCUMENTAL
												+ postUdocEnUI;
									}
								}
								tableUdocs.addRowValue(signatura);

								String estadoCotejo = Constants.STRING_EMPTY;

								if (mostrarEstadoUdocs) {
									estadoCotejo = Messages
											.getString(
													EstadoCotejo
															.getEstadoCotejo(
																	parteUDoc
																			.getEstadoCotejo())
															.getKey(), request
															.getLocale());
									tableUdocs.addRowValue(estadoCotejo);
									tableUdocs.addRowValue(parteUDoc
											.getNotasCotejo());
								}
							}
						}

						report.add(tableUdocs);
					}
				}
			}

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
			response.setContentType(Constants.TIPO_APLICACION_PDF);
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
