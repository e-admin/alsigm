package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.NamingException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.safehaus.uuid.UUIDGenerator;
import org.springframework.util.Assert;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrReport;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.ReportResult;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.reports.ReportsSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;

import es.ieci.tecdoc.isicres.api.business.dao.ReportsDAO;
import es.ieci.tecdoc.isicres.api.business.exception.ReportException;
import es.ieci.tecdoc.isicres.api.business.manager.ReportManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PlantillaInformeVO;
import es.ieci.tecdoc.isicres.api.business.vo.ReportObjects;
import es.ieci.tecdoc.isicres.api.business.vo.ReportVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoPlantillaInformeEnum;

/**
 *
 * @author IECISA
 *
 */
public class ReportManagerImpl implements ReportManager {

	/**
	 * {@inheritDoc}
	 */
	public byte[] createInputCertificate(UsuarioVO usuario,
			IdentificadorRegistroVO identificadorRegistro, String templateId) {

		checkPrerrequisites(usuario, identificadorRegistro,
				TipoLibroEnum.ENTRADA, templateId);

		return createCertificate(usuario, identificadorRegistro, templateId);
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] createOutputCertificate(UsuarioVO usuario,
			IdentificadorRegistroVO identificadorRegistro, String templateId) {

		checkPrerrequisites(usuario, identificadorRegistro,
				TipoLibroEnum.SALIDA, templateId);

		return createCertificate(usuario, identificadorRegistro, templateId);
	}

	/**
	 * {@inheritDoc}
	 */
	public List getCertificates(UsuarioVO usuario, BaseLibroVO libro) {
		List result = new ArrayList();
		try {
			BookSession.openBook(usuario.getConfiguracionUsuario()
					.getSessionID(), Integer.valueOf(libro.getId()), usuario
					.getConfiguracionUsuario().getIdEntidad());

			List reportList = ReportsSession.getScrReports(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(libro.getId()),
					TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO.getValue(),
					usuario.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad());

			for (Iterator iterator = reportList.iterator(); iterator.hasNext();) {
				ScrReport report = (ScrReport) iterator.next();

				PlantillaInformeVO plantilla = new PlantillaInformeVO(String
						.valueOf(report.getId()), TipoPlantillaInformeEnum
						.getEnum(report.getTypeReport()), report.getReport());

				result.add(plantilla);
			}
		} catch (Exception e) {
			StringBuffer message = new StringBuffer(
					"No se ha podido recuperar las plantillas de certificados para el usuario [")
					.append(usuario.getLoginName()).append(
							"] asociadas al libro con identificador [").append(
							libro.getId()).append("]");
			logger.error(message.toString(), e);

			throw new ReportException(message.toString(), e);
		}

		return result;
	}

	public ReportsDAO getReportsDAO() {
		return reportsDAO;
	}

	public void setReportsDAO(ReportsDAO reportsDAO) {
		this.reportsDAO = reportsDAO;
	}

	/**
	 * Método que verifica los requisitos necesarios para la generación de un
	 * informe de certificado para un registro de un libro, ya sea de entrada o
	 * de salida. Los requisitos verificados son:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param usuario
	 *            usuario autenticado
	 * @param identificadorRegistro
	 * @param bookType
	 * @param reportId
	 *
	 */
	private void checkPrerrequisites(UsuarioVO usuario,
			IdentificadorRegistroVO identificadorRegistro,
			TipoLibroEnum bookType, String reportId) {
		AxSf register = null;
		ScrRegstate book = null;
		try {

			BookSession.openBook(usuario.getConfiguracionUsuario()
					.getSessionID(), Integer.valueOf(identificadorRegistro
					.getIdLibro()), usuario.getConfiguracionUsuario()
					.getIdEntidad());
			// Verificamos la existencia del libro
			book = BookSession.getBook(usuario.getConfiguracionUsuario()
					.getSessionID(), Integer.valueOf(identificadorRegistro
					.getIdLibro()));
			// Verificamos el tipo del libro
			if (TipoLibroEnum.ENTRADA.equals(bookType)) {
				Assert.isTrue(Repository.getInstance(
						usuario.getConfiguracionUsuario().getIdEntidad())
						.isInBook(identificadorRegistro.getIdLibro())
						.booleanValue(), "El libro con identificador ["
						+ identificadorRegistro.getIdLibro()
						+ "] no es de entrada.");
			} else if (TipoLibroEnum.SALIDA.equals(bookType)) {
				Assert.isTrue(Repository.getInstance(
						usuario.getConfiguracionUsuario().getIdEntidad())
						.isOutBook(identificadorRegistro.getIdLibro())
						.booleanValue(), "El libro con identificador ["
						+ identificadorRegistro.getIdLibro()
						+ "] no es de salida");
			}
			// Verificamos la existencia del registro
			register = FolderSession.getBookFolder(usuario
					.getConfiguracionUsuario().getSessionID(), Integer
					.valueOf(identificadorRegistro.getIdLibro()), Integer
					.valueOf(identificadorRegistro.getIdRegistro()).intValue(),
					usuario.getConfiguracionUsuario().getLocale(), usuario
							.getConfiguracionUsuario().getIdEntidad());

			// Verificamos la existencia del informe solicitado
			ReportVO report = getReportsDAO().getReportInfo(usuario, reportId);
			Assert.notNull(report, "No existe un informe con identificador ["
					+ reportId + "]");
			// El informe debe ser de tipo CERTIFICADO
			Assert
					.isTrue(TipoPlantillaInformeEnum.CERTIFICADO_REGISTRO
							.equals(report.getReportType()),
							"El informe solicitado no se corresponde a un informe de certificado.");
			// Verificamos que se puede usar el informe para el tipo de libro
			// indicado
			Assert.isTrue(report.isCanBeUsedByAllBooks()
					|| (bookType.equals(report.getBookType())), "El informe ["
					+ reportId + "] no puede ser usado para el libro ["
					+ identificadorRegistro.getIdLibro() + "]");
		} catch (IllegalArgumentException iae) {
			throw iae;
		} catch (Exception e) {
			Assert.notNull(book, "No existe un libro con identificador ["
					+ identificadorRegistro.getIdLibro() + "]");
			Assert.notNull(register, "No se ha encontrado el registro ["
					+ identificadorRegistro.getIdRegistro()
					+ "] para el libro [" + identificadorRegistro.getIdLibro()
					+ "]");
		}
	}

	/**
	 *
	 * @param usuario
	 *            usuario autenticado
	 * @param identificadorRegistro
	 *            identificadores internos del registro y libro asociado al
	 *            informe a generar
	 * @param templateId
	 *            identificador interno del informe
	 * @return
	 */
	private byte[] createCertificate(UsuarioVO usuario,
			IdentificadorRegistroVO identificadorRegistro, String templateId) {
		int opcion = 0;
		int typeUnit = 0;
		Integer reportOption = new Integer(0);

		File tempDir = null;
		try {
			// Creamos un directorio temporal que se usa como apoyo a la
			// generación del informe
			tempDir = createTemporaryDirectory();

			// Recuperamos el ZIP con la plantilla del informe y subinformes
			// desde la base de datos
			File reportDataZipFile = new File(tempDir, templateId);
			ZipInputStream reportData = DBEntityDAOFactory
					.getCurrentDBEntityDAO().getReportData(
							Integer.valueOf(templateId).intValue(),
							reportDataZipFile.getAbsolutePath(),
							usuario.getConfiguracionUsuario().getIdEntidad());
			reportDataZipFile.delete();

			// Transformamos la información recuperada a objetos de la
			// aplicación
			ReportObjects reportObjects = extractReportObjects(reportData);

			// Se genera el informe
			return generateCertificateReport(usuario, reportObjects, opcion,
					identificadorRegistro, typeUnit, reportOption, tempDir);

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"No se ha podido generar el certificado para la plantilla de informe con identificador [")
					.append(templateId).append("]");
			logger.error(sb.toString(), e);

			throw new ReportException(sb.toString(), e);
		} finally {
			// Eliminamos el directorio temporal y todos los ficheros generados
			// en él
			if (null != tempDir) {
				File[] files = tempDir.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
				tempDir.delete();
			}
		}
	}

	/**
	 * Extrae la plantilla de informe y subreports del ZIP almacenado en base de
	 * datos <code>reportData</code> y los devuelve como
	 * <code>InputStream</code> envueltos en una instancia de
	 * <code>ReportObjects</code>.
	 *
	 * @see ReportObjects
	 * @see ZipInputStream
	 *
	 * @param reportData
	 * @return
	 * @throws IOException
	 */
	private ReportObjects extractReportObjects(ZipInputStream reportData)
			throws IOException {
		ReportObjects reportObjects = new ReportObjects();
		try {

			ZipEntry entry = null;
			while ((entry = reportData.getNextEntry()) != null) {
				byte[] buffer = new byte[1024];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int len;
				while ((len = reportData.read(buffer)) > 0) {
					baos.write(buffer, 0, len);
				}

				// Subreport
				if (entry.getName().lastIndexOf("_") != -1) {
					reportObjects.getSubreports().put(entry.getName(),
							new ByteArrayInputStream(baos.toByteArray()));
				} else {
					// Plantilla de informe
					reportObjects.setReportTemplate(new ByteArrayInputStream(
							baos.toByteArray()));
				}
				// Se cierra la Entry para que el stream se posicione en la
				// siguiente
				reportData.closeEntry();
			}
		} finally {
			if (null != reportData) {
				reportData.close();
			}
		}

		return reportObjects;
	}

	/**
	 * Crea un directorio temporal de nombre aleatorio bajo la ruta indicada por
	 * la propiedad del sistema <i>java.io.tmpdir</i>. Será usado para la
	 * creación de ficheros temporales necesarios para la generación del
	 * informe.
	 *
	 * @return
	 */
	private File createTemporaryDirectory() {

		final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));

		String dirName = UUIDGenerator.getInstance().generateTimeBasedUUID()
				.toString();
		File newTempDir = new File(sysTempDir, dirName);
		newTempDir.mkdir();

		return newTempDir;
	}

	/**
	 *
	 * @param usuario
	 *            usuario autenticado
	 * @param reportObjects
	 * @param opcion
	 * @param bookID
	 *            identificador del libro asociado al informe a generar
	 * @param typeUnit
	 * @param reportOption
	 * @param tempDir
	 *            directorio temporal empleado para la generación del informe
	 * @return
	 */
	private byte[] generateCertificateReport(UsuarioVO usuario,
			ReportObjects reportObjects, int opcion,
			IdentificadorRegistroVO identificadorRegistro, int typeUnit,
			Integer reportOption, File tempDir) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		UseCaseConf useCaseConf = null;
		ReportResult reportResult = null;
		try {

			useCaseConf = new UseCaseConf();
			useCaseConf.setSessionID(usuario.getConfiguracionUsuario()
					.getSessionID());
			useCaseConf.setEntidadId(ISicresKeys.IS_INVESICRES);
			useCaseConf
					.setLocale(usuario.getConfiguracionUsuario().getLocale());

			// Se replica llamada hecha desde la aplicación Web. Sin ella no
			// funciona.
			new BookUseCase().openTableResults(useCaseConf, Integer
					.valueOf(identificadorRegistro.getIdLibro()),
					new HashMap(), new HashMap(), reportOption,
					StringUtils.EMPTY);

			// Llamada hecha por la aplicación para la generación de
			// certificados, ya sea de libros de entrada o de salida.
			// Determinados parámetros toman valores constantes para replicar el
			// escenario de uso.
			reportResult = ReportsSession
					.getOptionAQuery(
							usuario.getConfiguracionUsuario().getSessionID(),
							Integer.valueOf(identificadorRegistro.getIdLibro()),
							Arrays.asList(new Integer[] {Integer.valueOf(identificadorRegistro.getIdRegistro())}),
							opcion,
							Integer
									.valueOf(Configurator
											.getInstance()
											.getProperty(
													ConfigurationKeys.KEY_DESKTOP_MAX_REPORT_REGISTERS)),
							ISicresKeys.IS_INVESICRES);
			// Se filtra la query sobre la tabla temporal para que tenga en
			// cuenta solo el registro indicado
			StringBuffer query = new StringBuffer(reportResult.getReportQuery())
					.append("WHERE FDRID=").append(
							identificadorRegistro.getIdRegistro()).append(
							" ORDER BY FLD1");
			reportResult.setReportQuery(query.toString());

			// Se modifica la plantilla con los datos recuperados de la base de
			// datos
			Document processReportTemplate = processReportTemplate(
					reportResult, typeUnit, reportObjects.getReportTemplate());

			// Se genera el report
			JasperPrint fillReport = composeReport(reportObjects, tempDir,
					processReportTemplate);

			// Exportamos el informe a un stream
			JasperExportManager.exportReportToPdfStream(fillReport, baos);

			return baos.toByteArray();

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"Se ha producido un error en la generación del certificado para el registro [")
					.append(identificadorRegistro.getIdRegistro()).append(
							"] del libro [").append(
							identificadorRegistro.getIdLibro()).append("]");
			logger.error(sb.toString(), e);

			throw new ReportException(sb.toString(), e);
		} finally {
			try {
				baos.flush();
				baos.close();
				if (reportResult != null) {
					ReportsSession.dropTable(usuario.getConfiguracionUsuario()
							.getSessionID(), reportResult,
							ISicresKeys.IS_INVESICRES);
				}
			} catch (IOException e) {
				if(logger.isDebugEnabled()){
					logger.debug(e);
				}
				logger.warn("No se ha podido el stream de generacion del PDF");
			} catch (com.ieci.tecdoc.common.exception.ReportException e) {
				if(logger.isDebugEnabled()){
					logger.debug(e);
				}
				logger.warn("No se ha podido el stream de generacion del PDF");
			} catch (ValidationException e) {
				if(logger.isDebugEnabled()){
					logger.debug(e);
				}
				logger.warn("No se ha podido el stream de generacion del PDF");
			}

		}

	}

	/**
	 *
	 * @param reportObjects
	 * @param tempDir
	 * @param processReportTemplate
	 * @return
	 * @throws JRException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws NamingException
	 * @throws SQLException
	 */
	private JasperPrint composeReport(ReportObjects reportObjects,
			File tempDir, Document processReportTemplate) throws JRException,
			UnsupportedEncodingException, IOException, FileNotFoundException,
			NamingException, SQLException {
		JasperReport compileReport = JasperCompileManager
				.compileReport(new ByteArrayInputStream(processReportTemplate
						.asXML().getBytes("UTF-8")));

		// Se compilan los subreports y se pasan como parámetros de nombre
		Map parameters = new HashMap();
		Iterator iterator = reportObjects.getSubreports().keySet().iterator();
		while (iterator.hasNext()) {
			String subreportName = (String) iterator.next();

			File file = new File(tempDir, subreportName);
			file.createNewFile();
			FileOutputStream tempSubreport = new FileOutputStream(file);

			JasperCompileManager.compileReportToStream(
					(InputStream) reportObjects.getSubreports().get(
							subreportName), tempSubreport);

			parameters.put(subreportName.substring(0, subreportName
					.lastIndexOf("."))
					+ "_File", file);
		}

		// Se compone el report
		JasperPrint fillReport = JasperFillManager.fillReport(compileReport,
				parameters, BBDDUtils.getConnection(ISicresKeys.IS_INVESICRES));

		return fillReport;
	}

	/**
	 *
	 * @param reportResult
	 * @param typeUnit
	 * @param sourceXmlReport
	 * @param resolver
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private Document processReportTemplate(ReportResult reportResult,
			int typeUnit, InputStream sourceXmlReport)
			throws DocumentException, UnsupportedEncodingException,
			FileNotFoundException {

		SAXReader saxReader = new SAXReader();
		saxReader.setValidation(false);
		saxReader.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(java.lang.String publicId,
					java.lang.String systemId) throws SAXException,
					java.io.IOException {
				if (systemId.endsWith(".dtd"))
					// this deactivates all DTDs by giving empty XML docs
					return new InputSource(
							new ByteArrayInputStream(
									"<?xml version='1.0' encoding='UTF-8'?>"
											.getBytes()));
				else
					return null;
			}
		});

		Document xmlDocument = saxReader.read(new InputStreamReader(
				new BufferedInputStream(sourceXmlReport), "UTF-8"));
		Node node = xmlDocument.getRootElement().selectSingleNode(
				"/jasperReport/queryString");
		String query = node.getText();
		if (query.indexOf(reportResult.getOldTableName()) != -1) {
			node.setText(reportResult.getReportQuery());
		}

		List nodeList = xmlDocument.getRootElement().selectNodes(
				"/jasperReport/pageHeader/band/staticText/text");
		for (Iterator it = nodeList.iterator(); it.hasNext();) {
			Node node1 = (Node) it.next();
			if (node1.getText().equals("Oficina")) {
				node1.setText(reportResult.getUserOfic());
				break;
			}
		}

		// Parámetros a los subinformes en la parte pageHeader
		List nodeListTypeBook = xmlDocument
				.getRootElement()
				.selectNodes(
						"/jasperReport/pageHeader/band/subreport/subreportParameter/subreportParameterExpression");

		if (nodeListTypeBook != null && !nodeListTypeBook.isEmpty()) {
			for (Iterator it = nodeListTypeBook.iterator(); it.hasNext();) {
				Node node2 = (Node) it.next();
				if (node2.getParent().attributeValue("name").equals("typebook")) {
					node2.setText("new Double(" + reportResult.getTypeBook()
							+ ")");
				}
				if (node2.getParent().attributeValue("name").equals("typerel")) {
					node2.setText("new Double(" + typeUnit + ")");
				}
				if (node2.getParent().attributeValue("name").equals("idofic")) {
					node2.setText("new Double(" + reportResult.getIdOfic()
							+ ")");
				}
			}
		}

		// Parámetros a los subinformes en la parte detail
		nodeListTypeBook = xmlDocument
				.getRootElement()
				.selectNodes(
						"/jasperReport/detail/band/subreport/subreportParameter/subreportParameterExpression");

		if (nodeListTypeBook != null && !nodeListTypeBook.isEmpty()) {
			for (Iterator it = nodeListTypeBook.iterator(); it.hasNext();) {
				Node node2 = (Node) it.next();
				if (node2.getParent().attributeValue("name").equals("typebook")) {
					node2.setText("new Double(" + reportResult.getTypeBook()
							+ ")");
				}
				if (node2.getParent().attributeValue("name").equals("typerel")) {
					node2.setText("new Double(" + typeUnit + ")");
				}
				if (node2.getParent().attributeValue("name").equals("idofic")) {
					node2.setText("new Double(" + reportResult.getIdOfic()
							+ ")");
				}
			}
		}
		return xmlDocument;
	}

	// Members
	protected static final Logger logger = Logger
			.getLogger(ReportManagerImpl.class);

	protected ReportsDAO reportsDAO;

}
