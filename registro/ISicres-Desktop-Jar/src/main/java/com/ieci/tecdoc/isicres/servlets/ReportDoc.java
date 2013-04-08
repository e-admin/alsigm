/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.ieci.tecdoc.common.exception.ReportException;
import com.ieci.tecdoc.common.isicres.ReportResult;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.BBDDUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.IOUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.Utils;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.reports.ReportsUseCase;
import com.ieci.tecdoc.isicres.usecase.reports.utils.ReportsHelper;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * @author jcebrien
 *
 *	Servlet encargado de generar y mostrar los informes
 */
public class ReportDoc extends HttpServlet implements Keys {

	private static final String UTF_8 = "UTF-8";

	private static final String STRING_NEW_DOUBLE_PAR_IZQ = "new Double(";

	private static final String NAME = "name";

	private static final String PUNTO = ".";

	//Variable que identifica al nodo: /jasperReport/detail/band/subreport/subreportParameter/subreportParameterExpression
	private static final String NODO_DETAIL_SUBREPORT_PARAMETER_EXPRESSION = "/*[name()='jasperReport']/*[name()='detail']/*[name()='band']/*[name()='subreport']/*[name()='subreportParameter']/*[name()='subreportParameterExpression']";

	//Variable que identifica al nodo: /jasperReport/pageHeader/band/subreport/subreportParameter/subreportParameterExpression
	private static final String NODO_PAGEHEADER_SUBREPORT_PARAMETER_EXPRESSION = "/*[name()='jasperReport']/*[name()='pageHeader']/*[name()='band']/*[name()='subreport']/*[name()='subreportParameter']/*[name()='subreportParameterExpression']";

	//Variable que identifica al nodo: /jasperReport/pageHeader/band/staticText/text
	private static final String NODO_PAGEHEADER_OFICINA = "/*[local-name()='jasperReport']/*[local-name()='pageHeader']/*[local-name()='band']/*[local-name()='staticText']/*[local-name()='text']";

	private static Logger _logger = Logger.getLogger(ReportDoc.class);

	private static final String OPTION_TYPE_STRING_C1 = "C1";

	private static final String OPTION_TYPE_STRING_CM = "CM";

	private static final String OPTION_TYPE_STRING_LM = "LM";

	private static final String OPTION_TYPE_STRING_RMD = "RMD";

	private static final String OPTION_TYPE_STRING_RMO = "RMO";

	private static final String OPTION_TYPE_STRING_RMDS = "RMDS";

	private static final String OPTION_TYPE_STRING_RMOS = "RMOS";

	private static final String ORDERBYFLD1 = " ORDER BY FLD1";

	private static final String ORDERBYDES = " ORDER BY FLD4,FLD8,FLD1";

	private static final String ORDERBYORG = " ORDER BY FLD4,FLD7,FLD1";

	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm");

	private static final int OPTION_TYPE_C1 = 1;

	private static final int OPTION_TYPE_CM = 2;

	private static final int OPTION_TYPE_LM = 3;

	private static final int OPTION_TYPE_RMD = 4;

	private static final int OPTION_TYPE_RMO = 5;

	private static final int OPTION_TYPE_RMDS = 6;

	private static final int OPTION_TYPE_RMOS = 7;

	private static final String HORA_CERO = " 00:00";
	private static final String HORA_V3 = " 23:59";

	private ReportsUseCase reportsUseCase = null;

	//nombre del informe
	private String nameReport = null;

	public void init() throws ServletException {
		super.init();

		reportsUseCase = new ReportsUseCase();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doWork(request, response);
	}

	private void doWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/html; charset=UTF-8");

		Integer folderId = RequestUtils.parseRequestParameterAsInteger(request,
				"FolderId");
		// Identificador de informe.
		Integer reportId = RequestUtils.parseRequestParameterAsInteger(request,
				"ReportId");

		int opcion = mappingOptionToInt(RequestUtils
				.parseRequestParameterAsString(request, "Opcion"));
		String fecha = RequestUtils.parseRequestParameterAsString(request,
				"Fecha");
		String hora1 = RequestUtils.parseRequestParameterAsString(request,
				"Hora1");
		String hora2 = RequestUtils.parseRequestParameterAsString(request,
				"Hora2");
		String unidad = RequestUtils.parseRequestParameterAsString(request,
				"Unit");
		String list = RequestUtils.parseRequestParameterAsString(request,
				"List");
		int printOpc = RequestUtils.parseRequestParameterAsint(request,
				"PrintOpc");
		int typeUnit = 0;
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();

		Integer sizeReport = (Integer) session.getAttribute(Keys.J_SIZE_REPORT);
		if (sizeReport == null) {
			sizeReport = new Integer(0);
		}
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		Integer bookID = (Integer) session.getAttribute(Keys.J_BOOK);

        OutputStream output = response.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(output);

        ReportResult reportResult = null;
		String pathTemplateReport = null;
		try {
			pathTemplateReport = getPathTemplateReportFile(session,
					useCaseConf, reportId);
			reportsUseCase.getTemplateReport(useCaseConf, reportId.intValue(),
					pathTemplateReport);

			Integer maxReportRegister = new Integer(Configurator.getInstance()
					.getProperty(
							ConfigurationKeys.KEY_DESKTOP_MAX_REPORT_REGISTERS));
			String find = findFileTemplate(pathTemplateReport);
			// Integer find = findFile(session, reportId);
			if (opcion == 4 || opcion == 6) {
				typeUnit = 1;
			} else if (opcion == 5 || opcion == 7) {
				typeUnit = 2;
			}
			if (find != null) {
				String defaultOrderBy = null;

				switch (opcion) {
					case OPTION_TYPE_C1: {
						if (_logger.isDebugEnabled()) {
							_logger.debug("Option C1 for reports bookID [" + bookID
									+ "] fdrid [" + folderId + "]");
						}
						List idsList = new ArrayList();
						idsList.add(folderId);
						reportResult = reportsUseCase.getOptionAQuery(useCaseConf,
								bookID, idsList, opcion, maxReportRegister);
						break;
					}
					case OPTION_TYPE_LM:
					case OPTION_TYPE_CM: {
						List idsList = getIdsList(list);
						if (_logger.isDebugEnabled()) {
							_logger.debug("Option CM/LM for reports bookID ["
									+ bookID + "] list [" + list + "] printOpc ["
									+ printOpc + "]");
						}

						switch (printOpc) {
						case 0: {
							reportResult = reportsUseCase.getOptionAQuery(
									useCaseConf, bookID, idsList, opcion,
									maxReportRegister);
									reportResult.setOrderBy(ORDERBYFLD1);
							break;
						}
						case 1: {
							reportResult = reportsUseCase.getOptionAMultipleQuery(
									useCaseConf, bookID, false, opcion,
									maxReportRegister);
									reportResult.setOrderBy(ORDERBYFLD1);
							break;
						}
						case 2: {
							reportResult = reportsUseCase.getOptionAMultipleQuery(
									useCaseConf, bookID, true, opcion,
									maxReportRegister);
									reportResult.setOrderBy(ORDERBYFLD1);
							break;
						}
						}
						break;
					}
					case OPTION_TYPE_RMD:
					case OPTION_TYPE_RMO: {
						Date beginDate = null;
						Date finalDate = null;
						if (hora1 != null && hora2 != null) {
							beginDate = DATE_FORMATTER.parse(fecha + SPACE + hora1);
							finalDate = DATE_FORMATTER.parse(fecha + SPACE + hora2);
						} else {
							beginDate = DATE_FORMATTER.parse(fecha + HORA_CERO);
							finalDate = DATE_FORMATTER.parse(fecha + HORA_V3);
						}
						// TODO: Esto está bien? No debería cojer el valor del
						// parámetro?
						printOpc = 1;
						if (unidad != null) {
							if (unidad.length() == 0) {
								unidad = null;
							}
						}

						if (_logger.isDebugEnabled()) {
							_logger.debug("Option RMD/RMO for reports bookID ["
									+ bookID + "] beginDate [" + beginDate
									+ "] finalDate [" + finalDate + "] unit ["
									+ unidad + "] printOpc [" + printOpc + "]");
						}
						switch (printOpc) {
							case 0: {
								reportResult = reportsUseCase.getOptionCQuery(
										useCaseConf, bookID, beginDate, finalDate,
										unidad, (opcion == OPTION_TYPE_RMD),
										maxReportRegister);
								if (opcion == OPTION_TYPE_RMD) {
											defaultOrderBy = ORDERBYDES;
								} else {
											defaultOrderBy = ORDERBYORG;
								}
										reportResult.setOrderBy(defaultOrderBy);

								break;
							}
							case 1: {
								reportResult = reportsUseCase.getOptionCMultipleQuery(
										useCaseConf, bookID, beginDate, finalDate,
										unidad, (opcion == OPTION_TYPE_RMD), false,
										maxReportRegister);
								if (opcion == OPTION_TYPE_RMD) {
											defaultOrderBy = ORDERBYDES;
								} else {
											defaultOrderBy = ORDERBYORG;
								}
										reportResult.setOrderBy(defaultOrderBy);

								break;
							}
							case 2: {
								reportResult = reportsUseCase.getOptionCMultipleQuery(
										useCaseConf, bookID, beginDate, finalDate,
										unidad, (opcion == OPTION_TYPE_RMD), true,
										maxReportRegister);
								if (opcion == OPTION_TYPE_RMD) {
											defaultOrderBy = ORDERBYDES;
								} else {
											defaultOrderBy = ORDERBYORG;
								}
										reportResult.setOrderBy(defaultOrderBy);

								break;
							}
						}
						break;
					}
					case OPTION_TYPE_RMDS:
					case OPTION_TYPE_RMOS: {
						List idsList = getIdsList(list);
						if (_logger.isDebugEnabled()) {
							_logger.debug("Option RMOS/RMDS for reports bookID ["
									+ bookID + "] list [" + list + "] printOpc ["
									+ printOpc + "]");
						}
						String query = null;
						switch (printOpc) {
							case 0: {
								reportResult = reportsUseCase.getOptionAQuery(
										useCaseConf, bookID, idsList, opcion,
										maxReportRegister);
								if (opcion == OPTION_TYPE_RMDS) {
											defaultOrderBy = ORDERBYDES;
								} else {
											defaultOrderBy = ORDERBYORG;
								}
										reportResult.setOrderBy(defaultOrderBy);

								break;
							}
							case 1: {
								reportResult = reportsUseCase.getOptionAMultipleQuery(
										useCaseConf, bookID, false, opcion,
										maxReportRegister);
								if (opcion == OPTION_TYPE_RMDS) {
											defaultOrderBy = ORDERBYDES;
								} else {
											defaultOrderBy = ORDERBYORG;
								}
										reportResult.setOrderBy(defaultOrderBy);

								break;
							}
							case 2: {
								reportResult = reportsUseCase.getOptionAMultipleQuery(
										useCaseConf, bookID, true, opcion,
										maxReportRegister);
								if (opcion == OPTION_TYPE_RMDS) {
											defaultOrderBy = ORDERBYDES;
								} else {
											defaultOrderBy = ORDERBYORG;
								}
										reportResult.setOrderBy(defaultOrderBy);

								break;
							}
						}
						break;
					}
					default: {
						_logger
								.warn("Informe no disponible. Comprobar los identificadores de la base de datos.");
						showError(RBUtil
								.getInstance(useCaseConf.getLocale()).getProperty(
										Keys.I18N_EXCEPTION_FEATURE_NOT_AVAILABLE), output);
						break;
					}
				}
				if (reportResult != null) {
					if (reportResult.getSize() == 0) {
						showError(RBUtil
								.getInstance(useCaseConf.getLocale())
								.getProperty(
										Keys.I18N_EXCEPTION_NO_DATA_TO_REPORT), output);
					} else {
						String msg = "";
						if (sizeReport.intValue() == 0) {
							if (reportResult.getSize() >= maxReportRegister
									.intValue()) {
								msg = MessageFormat
										.format(RBUtil
												.getInstance(
														useCaseConf.getLocale())
												.getProperty(
														Keys.I18N_EXCEPTION_MAXREPORTREGISTERS),
												new String[] { maxReportRegister
														.toString() });
								showError(msg, output);
							}

						} else if (sizeReport.intValue() >= maxReportRegister
								.intValue()) {
							msg = MessageFormat
									.format(RBUtil
											.getInstance(
													useCaseConf.getLocale())
											.getProperty(
													Keys.I18N_EXCEPTION_MAXREPORTREGISTERS),
											new String[] { maxReportRegister
													.toString() });
							showError(msg, output);
						}
						//obtenemos el array de bytes que forman el informe
						byte[] report = generateReportFromTemplate(
								reportResult, session, reportId, find,
								typeUnit, Utils.getEntidad(request));

						if (report != null) {
							//enviamos el informe como respuesta del servlet
							getReportFileSendResponse(response, output, report);
						} else {
							_logger
									.warn("Informe no disponible. Comprobar los identificadores de la base de datos.");
							showError(RBUtil.getInstance(
									useCaseConf.getLocale()).getProperty(
											Keys.I18N_EXCEPTION_FEATURE_NOT_AVAILABLE), output);
						}
					}
				}
				session.setAttribute(Keys.J_SIZE_REPORT, new Integer(0));
			} else {
				deleteTemplateReport(pathTemplateReport);
				_logger
						.warn("Informe no disponible. Comprobar los identificadores de la base de datos.");
				showError(RBUtil.getInstance(
						useCaseConf.getLocale()).getProperty(
								Keys.I18N_EXCEPTION_FEATURE_NOT_AVAILABLE), output);
			}
		} catch (ReportException e) {
			if (!e.getCode().equals(
					ReportException.ERROR_CANNOT_GENERATE_REPORT_INVALID_CODE)) {
				_logger.fatal("Error generando un informe.", e);
				showError(RBUtil.getInstance(
						useCaseConf.getLocale()).getProperty(
								Keys.I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ), output);
			}
		} catch (Throwable e) {
			_logger.fatal("Error generando un informe.", e);
			showError(RBUtil.getInstance(
					useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_REPORT_OBJ), output);
		} finally {
			//cerramos el OutputStream
			output.flush();
			output.close();
			try {
				if (reportResult != null) {
					reportsUseCase.dropTable(useCaseConf, reportResult);
				}
				deleteTemplateReport(pathTemplateReport);
			} catch (Exception e) {
				_logger.fatal("Error generando un informe.", e);
			}
		}
	}

	/**
	 * Metodo que obtiene el informe en formato pdf y lo envia a la respuesta del Servlet
	 *
	 * @param response
	 * @param output
	 * @param report
	 * @throws IOException
	 */
	private void getReportFileSendResponse(HttpServletResponse response,
			OutputStream output, byte[] report) throws IOException {
		//generamos la cabecera para el documento
		response.reset();
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		response.setContentType("application/pdf");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "inline; filename=\""+nameReport+"\"");

		//generamos el informe como respuesta
		IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(report)),
				output);
	}

	/**
	 * Metodo que genera el error para mostrar en pantalla
	 * @param message
	 * @param output
	 */
	private void showError(String message, OutputStream output) {
		try {
			//obtenemos el formato de mensaje
			byte[] buffer = ResponseUtils.generateJavaScriptLogReportDoc(message).getBytes(UTF_8);

			IOUtils.copy(new BufferedInputStream(new ByteArrayInputStream(
					buffer)), output);

		} catch (IOException e) {
			_logger.fatal("Error en ShowError", e);
		}
	}

	private List getIdsList(String ids) {
		ArrayList list = new ArrayList();

		boolean first = true;
		if (ids != null && ids.length() > 0) {
			StringTokenizer tokenizer = new StringTokenizer(ids, GUION_BAJO);
			while (tokenizer.hasMoreTokens()) {
				if (first) {
					tokenizer.nextToken();
					first = false;
				}
				list.add(tokenizer.nextToken());
			}
		}
		return list;
	}

	private void deleteTemplateReport(String pathTemplateReport)
			throws Exception {
		File zipTemplate = new File(pathTemplateReport);
		if (zipTemplate.exists()) {
			zipTemplate.delete();
		}

	}

	private int mappingOptionToInt(String opcion) {
		int result = 0;

		Map optionId = new HashMap();
		optionId.put(OPTION_TYPE_STRING_C1, new Integer(1));
		optionId.put(OPTION_TYPE_STRING_CM, new Integer(2));
		optionId.put(OPTION_TYPE_STRING_LM, new Integer(3));
		optionId.put(OPTION_TYPE_STRING_RMD, new Integer(4));
		optionId.put(OPTION_TYPE_STRING_RMO, new Integer(5));
		optionId.put(OPTION_TYPE_STRING_RMDS, new Integer(6));
		optionId.put(OPTION_TYPE_STRING_RMOS, new Integer(7));

		result = ((Integer) optionId.get(opcion)).intValue();

		return result;
	}

	/**
	 * Metodo que forma el informe a partir de la plantilla indicada
	 * @param reportResult
	 * @param session
	 * @param reportId
	 * @param fileName
	 * @param typeUnit
	 * @param entidad
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NamingException
	 * @throws SQLException
	 * @throws DocumentException
	 * @throws JRException
	 * @throws ReportException
	 */
	private byte[] generateReportFromTemplate(ReportResult reportResult,
			HttpSession session, Integer reportId, String fileName,
			int typeUnit, String entidad) throws IOException,
			FileNotFoundException, NamingException, SQLException,
			DocumentException, JRException, ReportException {
		Object[] reports = getSourceReportFromTemplate(session, reportId,
				fileName);

		byte[] result = null;
		Connection connection = null;
		if (reports != null) {
			File sourceXmlReport = (File) reports[0];
			List subreports = (List) reports[1];
			File zipReport = (File) reports[2];
			File compiledReport = getCompiledReport(sourceXmlReport);
			File pdfReport = getFinalReport(sourceXmlReport);

			final String jasperDTD = getJasperDTD(session);

			if (_logger.isDebugEnabled()) {
				_logger.debug("DTD location : " + jasperDTD);
			}

			String jasperReportLib = Configurator.getInstance().getProperty(
					ConfigurationKeys.KEY_DESKTOP_REPORTS_JASPERREPORTS_LIB);
			File jasperFile = getJasperFile(session, jasperReportLib);
			if (!jasperFile.exists()) {
				// Aqui se encuentra el jasperreports.jar para IAS 10g
				File auxJasperFile = new File(jasperDTD);
				jasperFile = new File(auxJasperFile.getParent() + "/../../lib/"
						+ jasperReportLib);
			}
			System.setProperty("jasper.reports.compile.class.path", jasperFile
					.getAbsolutePath()
					+ System.getProperty("path.separator")
					+ ContextUtil.getRealPath(session.getServletContext(),
							"/WEB-INF/classes/"));
			System.setProperty("jasper.reports.compile.temp", sourceXmlReport
					.getParent());

			if (_logger.isDebugEnabled()) {
				_logger.debug("jasper.reports.compile.class.path : "
						+ jasperFile.getAbsolutePath());
				_logger.debug("path.separator : "
						+ ContextUtil.getRealPath(session.getServletContext(),
								"/WEB-INF/classes/"));

				_logger.debug("jasper.reports.compile.temp : "
						+ sourceXmlReport.getParent());
			}


			EntityResolver resolver = new EntityResolver() {
				public InputSource resolveEntity(String publicId,
						String systemId) {
					try {
						return new InputSource(new BufferedInputStream(
								new FileInputStream(jasperDTD)));
					} catch (FileNotFoundException e) {
						_logger.error("DTD not found", e);
						return null;
					}
				}
			};

			OutputStream pdfOutputStream = null;
			Map parameters = null;
			try {
				SAXReader saxReader = new SAXReader();
				saxReader.setEntityResolver(resolver);


				Document xmlDocument = saxReader.read(new InputStreamReader(
						new BufferedInputStream(new FileInputStream(
								sourceXmlReport)), UTF_8));

				//asignamos la consulta al informe
				setQueryReport(reportResult, xmlDocument);

				//se asigna al nodo oficina de la cabecera la información de la oficina
				setOficinaCabeceraInforme(reportResult, xmlDocument);

				if (_logger.isDebugEnabled()) {
					_logger.debug("==========> idofic : "
							+ reportResult.getIdOfic());
					_logger.debug("==========> typeBook : "
							+ reportResult.getTypeBook());
					_logger.debug("==========> typeUnit : " + typeUnit);
				}

				// Parámetros a los subinformes en la parte pageHeader
				setParametersByNodo(
						NODO_PAGEHEADER_SUBREPORT_PARAMETER_EXPRESSION,
						reportResult, typeUnit, xmlDocument);

				// Parámetros a los subinformes en la parte detail
				setParametersByNodo(NODO_DETAIL_SUBREPORT_PARAMETER_EXPRESSION,
						reportResult, typeUnit, xmlDocument);


				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding(UTF_8);
				XMLWriter writer = new XMLWriter(new OutputStreamWriter(
						new BufferedOutputStream(new FileOutputStream(
								sourceXmlReport)), UTF_8), format);
				writer.write(xmlDocument);

				IOUtils.close(writer);

				File subreport = null;
				File compiledSubreport = null;
				parameters = new HashMap();
				for (Iterator it = subreports.iterator(); it.hasNext();) {
					subreport = (File) it.next();
					compiledSubreport = getCompiledSubReport(subreport);
					if (!compiledSubreport.exists()) {
						if (_logger.isDebugEnabled()) {
							_logger.debug("Compilando: " + subreport);
						}

						JasperCompileManager.compileReportToStream(
								new BufferedInputStream(new FileInputStream(
										subreport.getAbsolutePath())),
								new BufferedOutputStream(new FileOutputStream(
										compiledSubreport)));

						if (_logger.isDebugEnabled()) {
							_logger.debug("Compilado: " + compiledSubreport);
						}
					}

					parameters.put(subreport.getName().substring(0,
							subreport.getName().lastIndexOf(PUNTO))
							+ "_File", compiledSubreport);
				}

				if (_logger.isDebugEnabled()) {
					_logger.debug("Compilando: " + sourceXmlReport);
				}

				JasperCompileManager.compileReportToStream(
						new BufferedInputStream(new FileInputStream(
								sourceXmlReport.getAbsolutePath())),
						new BufferedOutputStream(new FileOutputStream(
								compiledReport)));

				if (_logger.isDebugEnabled()) {
					_logger.debug("Compilado: " + compiledReport);
				}

				connection = BBDDUtils.getConnection(entidad);

				pdfOutputStream = new BufferedOutputStream(
						new FileOutputStream(pdfReport));
				JasperRunManager.runReportToPdfStream(new BufferedInputStream(
						new BufferedInputStream(new FileInputStream(
								compiledReport.getAbsolutePath()))),
						pdfOutputStream, parameters, connection);

				result = JasperRunManager.runReportToPdf(
						new BufferedInputStream(new BufferedInputStream(
								new FileInputStream(compiledReport
										.getAbsolutePath()))), parameters,
						connection);


				if (_logger.isDebugEnabled()) {
					_logger.debug("Creado: " + pdfReport);
				}

				nameReport = getFinalReportName(sourceXmlReport);
				if (_logger.isDebugEnabled()) {
					_logger.debug("nameReport: " + nameReport);
				}
			} finally {
				IOUtils.close(pdfOutputStream);
				sourceXmlReport.delete();
				compiledReport.delete();
				zipReport.delete();
				BBDDUtils.close(connection);

			}
		}

		return result;
	}

	/**
	 * Metodo que asigna los parametros (TIPO DE LIBRO, TIPO UNIDAD, ID OFICINA) a los nodos que se indican
	 * @param nodoList - NODOS a aplicar los parametros
	 * @param reportResult - Datos del informe
	 * @param typeUnit - Tipo de unidad
	 * @param xmlDocument - XML del informe
	 */
	private void setParametersByNodo(String nodoList,ReportResult reportResult, int typeUnit,
			Document xmlDocument) {
		//Obtenemos la información de los nodos
		List nodeListTypeBook = xmlDocument.getRootElement()
				.selectNodes(nodoList);

		//asignamos los parametros a los nodos
		setParametersDefaultReport(reportResult, typeUnit,
				nodeListTypeBook);
	}

	/**
	 * Metodo que asigna al nodo OFICINA de la cabecera (si existe), el nombre de la oficina.
	 *
	 * @param reportResult - Datos del informe
	 * @param xmlDocument - XML del informe
	 */
	private void setOficinaCabeceraInforme(ReportResult reportResult,
			Document xmlDocument) {
		List nodeList = xmlDocument.getRootElement().selectNodes(
				NODO_PAGEHEADER_OFICINA);
		for (Iterator it = nodeList.iterator(); it.hasNext();) {
			Node node1 = (Node) it.next();
			if (node1.getText().equals("Oficina")) {
				node1.setText(reportResult.getUserOfic());
				break;
			}
		}
	}

	/**
	 * Metodo que asigna al informe los parametros que se indiquen, por defecto
	 * son: TIPO DE LIBRO, TIPO UNIDAD, ID OFICINA, estos parametros pueden
	 * estar en la cabecera como en el detalle del informe
	 *
	 * @param reportResult
	 * @param typeUnit
	 * @param nodeListTypeBook
	 */
	private void setParametersDefaultReport(ReportResult reportResult,
			int typeUnit, List nodeListTypeBook) {
		if (nodeListTypeBook != null && !nodeListTypeBook.isEmpty()) {
			for (Iterator it = nodeListTypeBook.iterator(); it
					.hasNext();) {
				Node node2 = (Node) it.next();
				if (node2.getParent().attributeValue(NAME).equals(
						"typebook")) {
					node2.setText(STRING_NEW_DOUBLE_PAR_IZQ
							+ reportResult.getTypeBook() + PAR_DER);
				}
				if (node2.getParent().attributeValue(NAME).equals(
						"typerel")) {
					node2.setText(STRING_NEW_DOUBLE_PAR_IZQ + typeUnit + PAR_DER);
				}
				if (node2.getParent().attributeValue(NAME).equals(
						"idofic")) {
					node2.setText(STRING_NEW_DOUBLE_PAR_IZQ
							+ reportResult.getIdOfic() + PAR_DER);
				}
			}
		}
	}

	/**
	 * Metodo que asigna la consulta a ejecutar al Informe
	 *
	 * @param reportResult - Datos de la consulta
	 * @param xmlDocument - XML del Informe
	 */
	private void setQueryReport(ReportResult reportResult, Document xmlDocument) {
		ReportsHelper sicresReportsUtils = new ReportsHelper(xmlDocument);
		if(_logger.isDebugEnabled()){
			_logger.debug("VALORES INICIALES INFORME");
			_logger.debug("<queryString>"+ sicresReportsUtils.getQueryStringReport() +"</queryString>");
		}

		String query = sicresReportsUtils.getQueryStringReport();
		if (query.indexOf(reportResult.getOldTableName()) != -1) {

			//obtenemos la ordenación siempre que venga en el informe
			String defaultOrderByReport = sicresReportsUtils.getOrderByDefaultValue();
			if(defaultOrderByReport != null){
				//la ordenación se indica en el informe
				sicresReportsUtils.setQueryStringReport(reportResult.getReportQuery(), defaultOrderByReport,null);
			}
			else{
				//se aplica la ordenacion que ha dado por defecto en la aplicacion
				sicresReportsUtils.setQueryStringReport(reportResult.getReportQuery(), reportResult.getOrderBy(),null);
			}


			if (_logger.isDebugEnabled()) {
				_logger.debug("VALORES MODIFICADOS INFORME");
				_logger.debug("<queryString>"
						+ sicresReportsUtils.getQueryStringReport()
						+ "</queryString>");
				_logger.debug("QUERYSTRING REPORT: " + query);
			}
		}
	}

	private String getFinalReportName(File sourceReport) {
		String fileName = sourceReport.getName().substring(0,
				sourceReport.getName().lastIndexOf(PUNTO));

		return fileName + ".pdf";
	}

	private File getFinalReport(File sourceReport) {
		String fileName = sourceReport.getName().substring(0,
				sourceReport.getName().lastIndexOf(PUNTO));

		File temp = new File(sourceReport.getParent(), fileName + ".pdf");

		return temp;
	}

	private File getCompiledReport(File sourceReport) {
		String fileName = sourceReport.getName().substring(0,
				sourceReport.getName().lastIndexOf(PUNTO));

		File temp = new File(sourceReport.getParent(), fileName + ".jasper");
		temp.deleteOnExit();

		return temp;
	}

	private File getCompiledSubReport(File sourceReport) {
		String fileName = sourceReport.getName().substring(0,
				sourceReport.getName().lastIndexOf(PUNTO));

		File temp = new File(sourceReport.getParent(), fileName + ".jasper");
		temp.deleteOnExit();

		return temp;
	}

	private String findFileTemplate(String pathTemplateReport) {
		String result = null;
		String aux = null;
		File buscFile = new File(pathTemplateReport);
		if (buscFile.exists()) {
			aux = buscFile.getName();
			if (aux.indexOf(PUNTO) != -1) {
				aux = aux.substring(aux.indexOf(PUNTO), aux.length());
				if (_logger.isDebugEnabled()) {
					_logger.debug("aux: " + aux);
				}
				if (aux.equals(".zip")) {
					aux = buscFile.getName();
					if (_logger.isDebugEnabled()) {
						_logger.debug("aux: " + aux);
					}
				}
			} else {
				aux = null;
			}
			result = aux;
		}
		return result;
	}

	private Object[] getSourceReportFromTemplate(HttpSession session,
			Integer reportId, String fileName) throws IOException,
			FileNotFoundException {
		//obtenemos la plantilla del informe
		File zipReport = null;
		//comprobamos si el path es relativo
		zipReport = new File(getPathDirectoryTemplateForReports() + "/" + fileName);


		Object[] result = null;

		File temporalXmlReport = null;
		if (zipReport.exists()) {
			result = new Object[3];
			List list = new ArrayList();

			//obtenemos el directorio temporal para generar el informe
			File temporalDirectory = new File(getPathDirectoryNameForReports(session));

			if (!temporalDirectory.exists()) {
				temporalDirectory.mkdir();
			}
			temporalXmlReport = new File(temporalDirectory, reportId + GUION_BAJO
					+ session.getId() + GUION_BAJO + GUION_BAJO
					+ session.getLastAccessedTime() + GUION_BAJO
					+ System.currentTimeMillis() + ".jrxml");

			if (temporalXmlReport.exists()) {
				temporalXmlReport.delete();
			}

			File tempFile = null;
			ZipFile zipFile = new ZipFile(zipReport);
			Enumeration enumeration = zipFile.entries();
			while (enumeration.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) enumeration.nextElement();
				tempFile = new File(temporalDirectory, entry.getName());
				if (entry.getName().lastIndexOf(GUION_BAJO) != -1) {
					if (!tempFile.exists()) {
						IOUtils.copy(zipFile.getInputStream(entry),
								new BufferedOutputStream(new FileOutputStream(
										tempFile)));
					}
					list.add(tempFile);
				} else {
					IOUtils.copy(zipFile.getInputStream(entry),
							new BufferedOutputStream(new FileOutputStream(
									temporalXmlReport)));

				}
			}
			zipFile.close();

			result[0] = temporalXmlReport;
			result[1] = list;
			result[2] = zipReport;
		}

		return result;
	}

	/**
	 * Metodo que devuelve el path del directorio temporal para los informes
	 * @return String - Ruta del directorio
	 */
	private String getPathDirectoryNameForReports(HttpSession session) {
		String result = null;
		//comprobamos si el path que se indica por configuración es relativo
		if (Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_DIRECTORYNAMEFORREPORTS)) {
			//directorio indicado con ruta relativa
			result =
					ContextUtil.getRealPath(
							session.getServletContext(),
							Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYNAMEFORREPORTS));
		} else {
			//directorio indicado con ruta absoluta
			result =
					Configurator
							.getInstance()
							.getProperty(
									ConfigurationKeys.KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYNAMEFORREPORTS);
		}

		return result;
	}

	/**
	 * Metodo que devuelve el path del directorio temporal para las plantillas de los informes
	 * @return String - Ruta del directorio
	 */
	private String getPathDirectoryTemplateForReports() {
		String result = null;
		//comprobamos si el directorio es relativo o no a la aplicación.
		if (Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORALRELATIVEDIRECTORYTEMPLATEFORREPORTS)) {
			//el directorio es relativo a la aplicacion
			result = ContextUtil
					.getRealPath(
							getServletContext(),
							Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYTEMPLATEFORREPORTS));
		} else {
			//el directorio no es relativo a la aplicacion
			result = Configurator
					.getInstance()
					.getProperty(
							ConfigurationKeys.KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYTEMPLATEFORREPORTS);
		}
		return result;
	}

	private String getPathTemplateReportFile(HttpSession session,
			UseCaseConf useCaseConf, Integer reportId) throws IOException,
			FileNotFoundException {
		String result = null;

		//obtenemos el directorio de las plantillas de los informes
		File temporalDirectory = new File(getPathDirectoryTemplateForReports());

		if (!temporalDirectory.exists()) {
			temporalDirectory.mkdir();
		}
		result = temporalDirectory.getAbsolutePath() + "/" + reportId + GUION_BAJO
				+ useCaseConf.getSessionID() + GUION_BAJO
				+ session.getLastAccessedTime() + GUION_BAJO
				+ System.currentTimeMillis() + ".zip";
		return result;
	}

	private static String getJasperDTD(HttpSession session) {
		String dtdPath = Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_DESKTOP_REPORTS_DTD_PATH);
		String dtdJasper = Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_DESKTOP_REPORTS_JASPERREPORTS_PATH);
		boolean pathRelative = new Boolean(
				Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_REPORTS_DTD_PATH_RELATIVE))
				.booleanValue();

		String jasperDTDAux = "";
		if (pathRelative) {
			jasperDTDAux = ContextUtil.getRealPath(session.getServletContext(),
					dtdPath + dtdJasper);
		} else {
			jasperDTDAux = dtdPath + dtdJasper;
		}

		return jasperDTDAux;
	}

	private static File getJasperFile(HttpSession session, String jasperLib) {
		String jasperRerportLibPath = Configurator.getInstance().getProperty(
				ConfigurationKeys.KEY_DESKTOP_REPORTS_LIB_PATH);
		boolean isRelative = new Boolean(
				Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_REPORTS_LIB_PATH_RELATIVE))
				.booleanValue();
		File jasperFile = null;
		if (isRelative) {
			jasperFile = new File(ContextUtil.getRealPath(session
					.getServletContext(), jasperRerportLibPath + jasperLib));
		} else {
			jasperFile = new File(jasperRerportLibPath + jasperLib);
		}

		return jasperFile;
	}

}