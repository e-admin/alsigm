/**
 *
 * @author jcebrien
 *
 */
package com.ieci.tecdoc.isicres.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DocumentSource;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.BookUseCase;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLSaveFdrErrors;

import es.ieci.tecdoc.fwktd.core.config.web.ContextUtil;

/**
 * Este servlet se invoca cuando se salva una carpeta modificada.
 *
 * @author jcebrien
 *
 */
public class FlushFdr extends HttpServlet implements Keys {

	private static Logger _logger = Logger.getLogger(FlushFdr.class);

	private static final String FIELD_TYPE_STRING_TREEUPDATE = "TreeUpdate";

	private static final String FIELD_TYPE_STRING_FLDDATA = "FldData";

	private static final String FIELD_TYPE_STRING_SESSIONPID = "SessionPId";

	private static final String FIELD_TYPE_STRING_FOLDERPID = "FolderPId";

	private static final String FIELD_TYPE_STRING_ARCHIVEPID = "ArchivePId";

	private static final String FIELD_TYPE_STRING_FOLDERID = "FolderId";

	private static final String FIELD_TYPE_STRING_FOLDERIDAUX = "FolderIdAux";

	private static final String FIELD_TYPE_STRING_ARCHIVEID = "ArchiveId";

	private static final String FIELD_TYPE_STRING_FILESCAN = "FileScan";

	private static final String FILEDATA = "FileData";

	private static final int FIELD_TYPE_TREEUPDATE = 1;

	private static final int FIELD_TYPE_FLDDATA = 2;

	private static final int FIELD_TYPE_SESSIONPID = 3;

	private static final int FIELD_TYPE_FOLDERPID = 4;

	private static final int FIELD_TYPE_ARCHIVEPID = 5;

	private static final int FIELD_TYPE_FOLDERID = 6;

	private static final int FIELD_TYPE_ARCHIVEID = 7;

	private static final int FIELD_TYPE_FILESCAN = 8;

	private static final String PUNTO_COMA = ";";

	private static final String BARRA = "|";

	private static final String DOSBARRA = "||";

	private static final String DOSPATH = "\\";

	private static final String PUNTO = ".";

	private static final String INTERROGACION = "?";

	private static final String ALMOHADILLA = "#";

	private static final String IGUAL = "=";

	private static final String AMPERSAN = "&";

	private static final String AMPERSANDOBLE = "&&";

	private static final String GUIONBAJO = "_";

	private BookUseCase bookUseCase = null;

	private TransformerFactory factory = null;

	public void init() throws ServletException {
		super.init();

		bookUseCase = new BookUseCase();
		factory = TransformerFactory.newInstance();
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

		// Información de los ficheros escaneados.
		String strFilesScan = RequestUtils
				.parseRequestParameterAsStringWithEmpty(request,
						FIELD_TYPE_STRING_FILESCAN);
		// Información de cada documento y página anexados.
		String treeUpdate = RequestUtils
				.parseRequestParameterAsStringWithEmpty(request,
						FIELD_TYPE_STRING_TREEUPDATE);
		// Información sobre los campos que se han modificado.
		String fldData = RequestUtils.parseRequestParameterAsStringWithEmpty(
				request, FIELD_TYPE_STRING_FLDDATA);
		// Nombre y contenido de los ficheros anexados.
		String fileDataReq = RequestUtils
				.parseRequestParameterAsStringWithEmpty(request, FILEDATA);
		// Identificador del registro.
		Integer folderIdAux = RequestUtils.parseRequestParameterAsInteger(
				request, FIELD_TYPE_STRING_FOLDERIDAUX);
		Integer bookID = RequestUtils.parseRequestParameterAsInteger(request,
				FIELD_TYPE_STRING_ARCHIVEID);
		Integer newFolderID = new Integer(-1);
		// Identificador de registro.
		Integer folderIDFromRequest = RequestUtils
				.parseRequestParameterAsInteger(request,
						FIELD_TYPE_STRING_FOLDERID);
		// Identificador de carpeta.
		String sessionPId = RequestUtils
				.parseRequestParameterAsStringWithEmpty(request,
						FIELD_TYPE_STRING_SESSIONPID);
		Integer folderPId = RequestUtils.parseRequestParameterAsInteger(
				request, FIELD_TYPE_STRING_FOLDERPID);
		Integer folderID = RequestUtils.parseRequestParameterAsInteger(request,
				FIELD_TYPE_STRING_FOLDERID);
		boolean errorPageName = false;
		// Obtenemos la sesión asociada al usuario.
		HttpSession session = request.getSession();
		// Texto del idioma. Ej: EU_
		String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
		// Número del idioma. Ej: 10
		Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
		// Obtenemos el objeto de configuración del servidor de aplicaciones y
		// el identificador
		// de sesión para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session
				.getAttribute(J_USECASECONF);
		// Recuperamos la fila de la carpeta
		Integer row = RequestUtils.parseRequestParameterAsInteger(request,
				"Row", new Integer(-1));
		Long maxUploadFileSize = new Long(Configurator.getInstance()
				.getProperty(ConfigurationKeys.KEY_DESKTOP_MAXUPLOADFILESIZE));
		File fNew = null;
		PrintWriter writer = response.getWriter();
		Document xmlDocument = null;

		try {
			//validaciones de seguridad
			validationSecurityUser(session, bookID, folderIDFromRequest,
					useCaseConf);

			// obtener y validar información actualizar la bbdd
			// si no error
			// iNumFields = oUpload.GetNumFields
			// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			// Check that we have a file upload request
			String allFields = "";
			boolean isMultipart = FileUpload.isMultipartContent(request);

			if (_logger.isDebugEnabled()) {
				_logger.debug("Content Type =" + request.getContentType());
			}

			DiskFileUpload fu = new DiskFileUpload();
			// If file size exceeds, a FileUploadException will be thrown
			fu.setSizeMax(maxUploadFileSize.longValue());

			List fileItems = fu.parseRequest(request);
			Iterator itr = fileItems.iterator();
			StringBuffer fileData = new StringBuffer();
			List fieldItems = null;
			List docItems = new ArrayList();
			List pageItems = new ArrayList();
			List keys = new ArrayList();
			List inter = new ArrayList();
			List filesInfo = new ArrayList();
			List filesScanInfo = new ArrayList();

			while (itr.hasNext()) {
				FileItem fi = (FileItem) itr.next();

				//si es un fichero el FileItem
				if (!fi.isFormField()) {
					Object[] obj = new Object[2];
					String aux = fi.getName();
					if (!aux.equals("")) {
						String fileNameFis = null;
						String extension = getExtension(fi.getName());
						//String order = fi.getFieldName();
						int order = getOrderFileScan(fi.getFieldName());
						if (folderIDFromRequest == null) {
							Integer folderIdCurrent = (Integer) session
									.getAttribute(Keys.J_REGISTER);
							fileNameFis = getFileNameFis(
									useCaseConf.getSessionID(),
									folderIdCurrent.toString(),
											order, fi.getName());
						} else {
							fileNameFis = getFileNameFis(
									useCaseConf.getSessionID(),
									folderIDFromRequest.toString(),
									order, fi.getName());
						}

						File newDir = null;
						if (Configurator
								.getInstance()
								.getPropertyBoolean(
										ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
							newDir = new File(
									ContextUtil.getRealPath(
											session.getServletContext(),
											Configurator
													.getInstance()
													.getProperty(
															ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)));
						} else {
							newDir = new File(
									Configurator
											.getInstance()
											.getProperty(
													ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME));
						}

						if (!newDir.exists()) {
							newDir.mkdir();
						}

						if (Configurator
								.getInstance()
								.getPropertyBoolean(
										ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
							fNew = new File(
									ContextUtil
											.getRealPath(
													session.getServletContext(),
													Configurator
															.getInstance()
															.getProperty(
																	ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME)),
									fileNameFis);
						} else {
							fNew = new File(
									Configurator
											.getInstance()
											.getProperty(
													ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME),
									fileNameFis);
						}

						fNew.deleteOnExit();

						if (_logger.isDebugEnabled()) {
							_logger.debug("fNew.getName(): " + fNew.getName());
							_logger.debug("fNew.getPath(): " + fNew.getPath());
						}

						FlushFdrFile flushFdrFile = new FlushFdrFile();

						flushFdrFile.setOrder(new Integer(order).intValue());
						flushFdrFile.setExtension(extension);
						flushFdrFile.setFileNameFis(fNew.getAbsolutePath());
						flushFdrFile.setFileNameLog(aux.substring(
								aux.lastIndexOf(DOSPATH) + 1, aux.length()));
						filesInfo.add(flushFdrFile);
						fi.write(fNew);

						fileData.append(fNew.getAbsolutePath());
						fileData.append(BARRA);
						fileData.append(fNew.getName());
						fileData.append(DOSBARRA);
					}
				} else {
					//si no es un fichero el FileItem es un parametro de la request
					if (_logger.isDebugEnabled()) {
						_logger.debug("Field =" + fi.getFieldName());
						_logger.debug("Value =" + fi.getString("UTF-8"));
					}
					int intField = 0;
					StringBuffer params = new StringBuffer();
					intField = mappingStrFieldToInt(fi.getFieldName());

					switch (intField) {
					case FIELD_TYPE_TREEUPDATE: {
						String treeUpdateForParse = "";
						treeUpdateForParse = fi.getString("UTF-8");

						if (_logger.isDebugEnabled()) {
							_logger.debug("treeUpdateForParse: "
									+ treeUpdateForParse);
						}
						String type = parseTree(treeUpdateForParse);

						//nivel documento
						if (type.equals("CL2")) {
							FlushFdrDocument flushFdrDocument = parseDoc(treeUpdateForParse);
							String claveDoc = flushFdrDocument.getTreeId();

							keys.add(claveDoc);
							docItems.add(flushFdrDocument);
						}

						//nivel pagina
						if (type.equals("CL3")) {
							FlushFdrPage flushFdrPage = parsePage(treeUpdateForParse);
							String ext = null;
							String pageName = flushFdrPage.getPageName();

							String clave = flushFdrPage.getFatherId();

							if (!keys.contains(clave)) {
								FlushFdrDocument flushFdrDocument = new FlushFdrDocument();

								flushFdrDocument.setTreeId(clave);
								flushFdrDocument.setDocumentName(bookUseCase
										.getDocName(useCaseConf, bookID,
												folderID.intValue(),
												flushFdrPage.getFatherId()));

								docItems.add(flushFdrDocument);
								keys.add(clave);
							}

							pageItems.add(flushFdrPage);
						}
						break;
					}
					case FIELD_TYPE_FLDDATA: {
						String fldDataForParse = "";

						fldDataForParse = fi.getString("UTF-8");

						fldDataForParse = fldDataForParse.replaceAll("\r\n",
								"\n");

						fieldItems = parseData(fldDataForParse);

						params.append(AMPERSAN);
						params.append(FIELD_TYPE_STRING_FLDDATA);
						params.append(IGUAL);
						params.append(fi.getString("UTF-8"));

						break;
					}
					case FIELD_TYPE_FILESCAN: {
						StringTokenizer tokens = new StringTokenizer(
								fi.getString("UTF-8"), BARRA);
						String idFile = null;
						String fileScanName = null;
						int orderFScan;

						while (tokens.hasMoreTokens()) {
							idFile = tokens.nextToken();
							fileScanName = tokens.nextToken();
							fileScanName = fileScanName.substring(1,
									fileScanName.length());

							if (_logger.isDebugEnabled()) {
								_logger.debug("idFile FILESCAN: " + idFile);
								_logger.debug("fileScanName FILESCAN: "
										+ fileScanName);
							}
						}
						//Obtenemos el orden del fichero
						orderFScan = getOrderFileScan(idFile);

						FlushFdrFile flushFdrFile = new FlushFdrFile();
						flushFdrFile.setOrder(orderFScan);
						flushFdrFile.setExtension(getExtension(fileScanName));
						String fileName = null;

						if (folderIDFromRequest == null) {
							Integer folderIdCurrent = (Integer) session
									.getAttribute(Keys.J_REGISTER);

							fileName = getFileNameFis(
									useCaseConf.getSessionID(),
									folderIdCurrent.toString(),
											orderFScan,
									fileScanName);
						} else {
							fileName = getFileNameFis(
									useCaseConf.getSessionID(),
									folderIDFromRequest.toString(),
									orderFScan,
									fileScanName);
						}

						String beginPath = null;

						if (Configurator
								.getInstance()
								.getPropertyBoolean(
										ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
							beginPath = ContextUtil
									.getRealPath(
											session.getServletContext(),
											Configurator
													.getInstance()
													.getProperty(
															ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME));
						} else {
							beginPath = Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME);
						}

						flushFdrFile.setFileNameFis(beginPath + File.separator
								+ fileName);
						flushFdrFile.setFileNameLog(fileScanName);
						filesInfo.add(flushFdrFile);

						if (_logger.isDebugEnabled()) {
							_logger.debug("############filesScanInfo: "
									+ flushFdrFile.toString());
						}

						break;
					}
					default: {
						break;
					}
					}
					// 'Conseguimos las cadenas que hay en los arrays de
					// Inputs y los
					// ' valores de los campos del formulario
					allFields = allFields + params.toString();
				}//fin de si no es un fichero
			}

			// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			if (!sessionPId.equals(useCaseConf.getSessionID())) {
				throw new SessionException(
						SessionException.ERROR_SESSION_EXPIRED);
			}

			if (!strFilesScan.equals("")) {
				fileData.insert(0, strFilesScan);
			}
			//se parsean los fieldItems
			for (Iterator it = fieldItems.iterator(); it.hasNext();) {
				FlushFdrField flushFdrField = (FlushFdrField) it.next();

				if (_logger.isDebugEnabled()) {
					_logger.debug(flushFdrField);
				}
				//se parsea el interesado
				if (flushFdrField.getFldid() == 9) {
					inter = parseInter(flushFdrField.getValue());
					flushFdrField.setValue(((FlushFdrInter) inter.get(0))
							.getInterName());
				}
			}

			if (_logger.isDebugEnabled()) {
				for (Iterator it = filesInfo.iterator(); it.hasNext();) {
					FlushFdrFile flI = (FlushFdrFile) it.next();
					_logger.debug("===================> filesInfo "
							+ flI.toString());
				}

				for (Iterator it = keys.iterator(); it.hasNext();) {
					String clv = (String) it.next();
					_logger.debug("===================> keys " + clv);
				}
			}

			Map documents = generateTreeUpdateMap(docItems, pageItems, keys);
			FlushFdrDocument flushFdrDocument = null;
			FlushFdrPage flushFdrPage = null;
			FlushFdrFile flushFdrFile = null;

			for (Iterator it = documents.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();

				if (_logger.isDebugEnabled()) {
					_logger.debug("===================> Map keysdocument "
							+ key);
				}

				flushFdrDocument = (FlushFdrDocument) documents.get(key);

				if (_logger.isDebugEnabled()) {
					_logger.debug("===================> Map flushFdrDocument "
							+ flushFdrDocument.toString());
				}

				for (Iterator it1 = flushFdrDocument.getPages().iterator(); it1
						.hasNext();) {
					flushFdrPage = (FlushFdrPage) it1.next();

					if (_logger.isDebugEnabled()) {
						_logger.debug("===================> Map flushFdrPage "
								+ flushFdrPage.toString());
					}

					for (Iterator it2 = filesInfo.iterator(); it2.hasNext()
							&& flushFdrPage.getFile() == null;) {
						flushFdrFile = (FlushFdrFile) it2.next();

						if (_logger.isDebugEnabled()) {
							_logger.debug("===================> Map flushFdrFile "
									+ flushFdrFile.toString());
						}

						String fileNameLog = flushFdrFile.getFileNameLog();
						String pageName = flushFdrPage.getPageName();
						String fileOrder = String.valueOf(flushFdrFile
								.getOrder());
						String pageCode = flushFdrPage.getTreeId().substring(
								flushFdrPage.getTreeId().length() - 1,
								flushFdrPage.getTreeId().length());

						if (fileNameLog.equals(pageName)
								|| fileOrder.equals(pageCode)) {
							if (_logger.isDebugEnabled()) {
								_logger.debug("===================> entra en flushFdrFile.getFileNameLog().equals(flushFdrPage.getPageName()) "
										+ flushFdrFile.toString());
							}
							String pageExtension = getExtension(pageName);
							String fileExtension = flushFdrFile.getExtension();

							if (StringUtils.isEmpty(pageExtension)
									|| EXTENSION_DEFECTO
											.equalsIgnoreCase(pageExtension)
									|| !fileExtension
											.equalsIgnoreCase(pageExtension)) {
								pageName = changeExtension(pageName,
										fileExtension, pageExtension);

							}
							//validamos que el tamaño del nombre de la pagina (nombre + extension) no exceda de 64 caracteres
							if (pageName.length() > 64) {
								errorPageName = true;
								_logger.error("El nombre de la pagina [" + pageName + "] excede los 64 caracteres");
								throw new ValidationException(
										ValidationException.ERROR_PAGE_NAME_LENGTH);
							}

							flushFdrFile.setFileNameLog(pageName);
							flushFdrPage.setFile(flushFdrFile);
						}
					}

					flushFdrPage.getFile().loadFile();
				}
			}

			if (bookID == null) {
				throw new SessionException(
						SessionException.ERROR_SESSION_EXPIRED);
			}

			//se valida todo antes de actualizar o guardar
			List badCtrls = bookUseCase.validateFolder(useCaseConf, bookID,
					folderID.intValue(), filesInfo, fieldItems, documents);

			if (badCtrls.isEmpty()) {
				folderIdAux = new Integer(bookUseCase.saveOrUpdateFolder(
						useCaseConf, bookID, folderID.intValue(), filesInfo,
						fieldItems, inter, documents));
				FlushFdrFile file = null;

				for (Iterator it = filesInfo.iterator(); it.hasNext();) {
					file = (FlushFdrFile) it.next();

					try {
						new File(file.getFileNameFis()).delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (folderID.intValue() == -1) {
					String archFolder = (String) session
							.getAttribute(Keys.J_STRCARPETA);
					StringBuffer strCarpeta = new StringBuffer();

					strCarpeta.append(bookID.toString());
					strCarpeta.append(BARRA);
					strCarpeta.append(folderIdAux.toString());
					strCarpeta.append(PUNTO_COMA);

					if (archFolder != null) {
						strCarpeta.insert(0, archFolder);
					}

					session.setAttribute(Keys.J_STRCARPETA,
							strCarpeta.toString());
				}

				session.setAttribute(Keys.J_REGISTER, folderIdAux);

				xmlDocument = bookUseCase.getBookTree(useCaseConf, bookID,
						false, folderPId.intValue(), folderIdAux.intValue(),
						row.intValue(),
						getBeforeEncondedResponseURL(request, response), false,
						useCaseConf.getLocale());
			} else {
				// XMLSaveFdrErrors xmlErrors = new XMLSaveFdrErrors();

				xmlDocument = XMLSaveFdrErrors.createXMLErrors(useCaseConf,
						badCtrls);
			}

			String xslPath = ContextUtil.getRealPath(
					session.getServletContext(), XSL_FRMT_RELATIVE_PATH);
			StreamSource s = new StreamSource(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(xslPath))));
			Templates cachedXSLT = factory.newTemplates(s);
			Transformer transformer = cachedXSLT.newTransformer();
			DocumentSource source = new DocumentSource(xmlDocument);
			StreamResult result = new StreamResult(writer);

			transformer.transform(source, result);
		} catch (FileUploadException e) {

			if (_logger.isDebugEnabled()) {
				_logger.debug("FileUploadException...............1 "
						+ System.currentTimeMillis());
			}
			_logger.fatal("Error cargando ficheros", e);

			if (_logger.isDebugEnabled()) {
				_logger.debug("FileUploadException...............2 "
						+ System.currentTimeMillis());
			}
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);

			if (_logger.isDebugEnabled()) {
				_logger.debug("FileUploadException...............3 "
						+ System.currentTimeMillis());
			}
			ResponseUtils.generateJavaScriptErrorForSave(writer);

			if (_logger.isDebugEnabled()) {
				_logger.debug("FileUploadException...............4 "
						+ System.currentTimeMillis());
			}
			String msg = MessageFormat.format(
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_EXCEPTION_MAXUPLOADFILESIZE),
					new String[] { maxUploadFileSize.toString() });

			if (_logger.isDebugEnabled()) {
				_logger.debug("FileUploadException...............5 "
						+ System.currentTimeMillis());
			}

			ResponseUtils.generateJavaScriptLog(writer, msg);

			if (_logger.isDebugEnabled()) {
				_logger.debug("FileUploadException...............6 "
						+ System.currentTimeMillis());
			}
		} catch (RemoteException e) {
			_logger.fatal("Error de comunicaciones", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptLogForSave(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
		} catch (AttributesException e) {
			_logger.fatal("Error en los atributos", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptError(writer, e,
					useCaseConf.getLocale());
		} catch (SecurityException e) {
			_logger.fatal("Error de seguridad", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptError(writer, e,
					useCaseConf.getLocale());
		} catch (ValidationException e) {
			_logger.fatal("Error de validacion", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			if (errorPageName) {
				ResponseUtils.generateJavaScriptLogForSave(writer,
						e.getMessage());
			} else {
				ResponseUtils
						.generateJavaScriptLogForSave(
								writer,
								RBUtil.getInstance(useCaseConf.getLocale())
										.getProperty(
												Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
			}
		} catch (BookException e) {
			_logger.fatal("Error en el libro", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (EventException e) {
			_logger.fatal("Error en el evento", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptError(writer, e);
		} catch (SessionException e) {
			_logger.fatal("Error en la sesion", e);
			ResponseUtils.generateJavaScriptLogSessionExpiredDtrfdr(writer,
					e.getMessage(), idioma, numIdioma);
		} catch (TransformerConfigurationException e) {
			_logger.fatal("Error al guardar", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptLogForSave(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
		} catch (TransformerFactoryConfigurationError e) {
			_logger.fatal("Error al guardar", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptLogForSave(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
		} catch (TransformerException e) {
			_logger.fatal("Error al guardar", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptLogForSave(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT));
		} catch (Exception e) {
			_logger.fatal("Error al guardar", e);
			ResponseUtils
					.generateJavaScriptErrorForUpdateFolderNoBadCtrls(writer);
			ResponseUtils.generateJavaScriptErrorForSave(writer);
			ResponseUtils.generateJavaScriptLogForSave(
					writer,
					RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
							Keys.I18N_ISICRESSRV_ERR_CREATING_FDRSAVE_OBJ));
		}
	}

	/**
	 * Método que obtiene el orden del fichero
	 * @param idFile - Codigo del fichero LI[X]
	 * @return orden del fichero
	 */
	private int getOrderFileScan(String idFile) {
		int result;
		try {
			//Obtenemos el identificador del fichero a partir del nombre
			String codigoFichero = idFile.substring("LI".length(),
					idFile.length());
			// lo pasamos a un valor integer
			result = Integer.parseInt(codigoFichero);
		} catch (Exception e) {
			// Si se produce alguna excepción durante el proceso anterior, asignamos como
			// codigo del fichero la posición de este, dentro del array de
			// ficheros
			_logger.warn("No se ha podido obtener el orden del documento: " + idFile);

			result = Integer.parseInt(idFile.substring(idFile.length() - 1, idFile.length()));
		}
		return result;

//		int orderFScan;
//
//		orderFScan = Integer.parseInt(idFile.substring(idFile.length() - 1,
//				idFile.length()));
//		return orderFScan;
	}

	/**
	 * Metodo que realiza diferentes validaciones de seguridad para que el usuario pueda editar un registro:
	 *  	<br/> - Comprueba que el libro esta abierto
	 *  	<br/> - Comprueba que el usuario o su oficina tengan acceso al registro
	 *
 	 * @param session
	 * @param bookID - ID del libro
	 * @param folderID - ID del registro
	 * @param useCaseConf
	 *
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private void validationSecurityUser(HttpSession session, Integer bookID,
			Integer folderID, UseCaseConf useCaseConf)
			throws BookException, SessionException, ValidationException {

		// comprobamos si el libro de registro esta abierto antes de seguir con
		// el proceso
		ScrRegstate scrregstate = BookSession.getBook(
				useCaseConf.getSessionID(), bookID);

		if (scrregstate.getState() == ISicresKeys.BOOK_STATE_CLOSED) {
			// el libro esta cerrado no se puede modificar los datos del registro
			_logger.warn("El libro [" + bookID + "] esta cerrado no se puede modificar el registro [" + folderID + "]");
			throw new BookException(BookException.ERROR_BOOK_CLOSE);
		}

		// ahora comprobaremos si el usuario tiene acceso al registro

		Integer idFolderAux = null;
		//obtenemos el ID del registro
		if (folderID == null) {
			idFolderAux = (Integer) session.getAttribute(Keys.J_REGISTER);
		} else {
			idFolderAux = folderID;
		}

		if ((idFolderAux != null) && (idFolderAux.intValue() !=-1)) {
			//buscamos el registro por id de registro e id de libro
			int size = FolderSession.getCountRegisterByIdReg(
					useCaseConf.getSessionID(), useCaseConf.getEntidadId(),
					bookID, idFolderAux);

			//si no se encuentran datos
			if (size == 0) {
				//el usuario no tiene acceso al registro con lo que no puede modificarlo
				_logger.warn("El usuario [" + useCaseConf.getUserName()
						+ "] no tiene acceso al registro [" + idFolderAux
						+ "] del Libro [" + bookID
						+ "] con lo que no puede modificarlo");
				throw new BookException(BookException.ERROR_UPDATE_FOLDER);
			}
		}
	}


	private int mappingStrFieldToInt(String field) {
		int result = 0;

		Map optionId = new HashMap();
		optionId.put(FIELD_TYPE_STRING_TREEUPDATE, new Integer(1));
		optionId.put(FIELD_TYPE_STRING_FLDDATA, new Integer(2));
		optionId.put(FIELD_TYPE_STRING_SESSIONPID, new Integer(3));
		optionId.put(FIELD_TYPE_STRING_FOLDERPID, new Integer(4));
		optionId.put(FIELD_TYPE_STRING_ARCHIVEPID, new Integer(5));
		optionId.put(FIELD_TYPE_STRING_FOLDERID, new Integer(6));
		optionId.put(FIELD_TYPE_STRING_ARCHIVEID, new Integer(7));
		optionId.put(FIELD_TYPE_STRING_FILESCAN, new Integer(8));
		if ((Integer) optionId.get(field) != null) {
			result = ((Integer) optionId.get(field)).intValue();
		}
		return result;
	}

	private String getBeforeEncondedResponseURL(HttpServletRequest request,
			HttpServletResponse response) {
		String query = request.getQueryString();
		query = query.replaceFirst(J_OPENFOLDER_TYPE + IGUAL
				+ J_OPENFOLDER_TYPE_XMLBOOKTREE_UDPATE_BUTTON,
				J_OPENFOLDER_TYPE + IGUAL + J_OPENFOLDER_TYPE_XMLBOOK);

		String path = request.getServletPath();
		path = path.substring(5, path.length());

		StringBuffer buffer = new StringBuffer();
		buffer.append(path);
		buffer.append(INTERROGACION);
		buffer.append(query);
		buffer.append(AMPERSAN);
		buffer.append(J_OPENFOLDER_TYPE);
		buffer.append(IGUAL);
		buffer.append(J_OPENFOLDER_TYPE_XMLBOOK);
		if (_logger.isDebugEnabled()) {
			_logger.debug("URL2: " + buffer.toString());
		}
		return response.encodeURL(buffer.toString());
	}

	private List parseData(String data) {
		List result = new ArrayList();
		FlushFdrField dataField = null;
		String token = null;
		if (_logger.isDebugEnabled()) {
			_logger.debug("********************* data : " + data);
		}
		StringTokenizer doblesBarras = new StringTokenizer(data, "|", true);
		while (doblesBarras.hasMoreTokens()) {
			dataField = new FlushFdrField();
			token = doblesBarras.nextToken();
			dataField.setFldid(new Integer(token).intValue());
			token = doblesBarras.nextToken();
			token = doblesBarras.nextToken();
			if (!(token.equals("|"))) {
				// el valor viene codificado para permitir caracteres especiales
				dataField.setValue(decodeValue(token));
				token = doblesBarras.nextToken();
			} else {
				dataField.setValue(null);
			}
			token = doblesBarras.nextToken();
			dataField.setCtrlid(new Integer(token).intValue());
			token = doblesBarras.nextToken();
			token = doblesBarras.nextToken();
			result.add(dataField);
		}

		return result;
	}

	/**
	 * metodo para decodificar los parametros que nos llegan desde el navegador.
	 * Vienen codificados para permitir caracteres especiales
	 * @param value
	 * @return
	 */
	private String decodeValue(String value){
		String result=null;

		try {
			result=URLDecoder.decode(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			_logger.error("Excepción decodificando valor:"+value + "en decodeValue");
			throw new RuntimeException(e);
		}

		return result;
	}

	private List parseInter(String inter) {
		List result = new ArrayList();
		FlushFdrInter dataInter = null;
		if (inter != null) {
			String[] interesados = StringUtils.split(inter, AMPERSANDOBLE);
			for (String interesado : interesados) {
				parseDataInter(interesado, result);
			}
		} else {
			dataInter = new FlushFdrInter();
			dataInter.setInterId(-1);
			result.add(dataInter);
		}
		return result;
	}

	private void parseDataInter(String interesado, List result) {
		FlushFdrInter dataInter = null;
		FlushFdrInter representante = null;
		String[] tokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(
				interesado, ALMOHADILLA);
		for (int i = 0; i < tokens.length; i++) {
			String valorIt= tokens[i];
			switch (i) {
			case 0:
				dataInter = new FlushFdrInter();
				Integer dataInterId = new Integer(tokens[i].trim());
				dataInter.setInterId(dataInterId.intValue());
				break;
			case 1:
				dataInter.setInterName(decodeValue(valorIt));
				break;
			case 2:
				if (!StringUtils.isEmpty(tokens[i])) {
					try {
						Integer dataDomId = new Integer(tokens[i]);
						dataInter.setDomId(dataDomId.intValue());
					} catch (NumberFormatException e) {
					}
				}
				break;
			case 3:
				dataInter.setDirection(tokens[i]);
				break;
			case 5:
				if (!StringUtils.isBlank(tokens[i])) {
					representante = new FlushFdrInter();
					try {
						representante.setInterId(Integer.valueOf(tokens[i]));
					} catch (NumberFormatException nfe) {
					}
				}
				break;
			case 6:
				if (null != representante) {
					representante.setInterName(tokens[i]);
				}
				break;
			case 7:
				if (null != representante) {
					if (!StringUtils.isBlank(tokens[i])) {
						try {
							representante.setDomId(Integer.valueOf(tokens[i]));
						} catch (NumberFormatException nfe) {
						}
					}
				}

				break;
			case 8:
				if (null != representante) {
					representante.setDirection(tokens[i]);
					dataInter.setRepresentante(representante);
				}
				break;
			}

		}
		result.add(dataInter);
	}

	private FlushFdrDocument parseDoc(String treeData) {
		FlushFdrDocument result = new FlushFdrDocument();
		StringTokenizer tokens = new StringTokenizer(treeData, BARRA);
		int i = 0;
		String mostrar = "";
		while (tokens.hasMoreTokens()) {
			mostrar = tokens.nextToken();
			switch (i) {
			case 2:
				result.setTreeId(mostrar);
				break;
			case 3:
				result.setDocumentName(mostrar);
				break;
			case 4:
				result.setFatherId(mostrar);
				break;
			case 5:
				result.setFatherClassName(mostrar);
				break;
			}
			i++;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("result FlushFdrDocument : " + result);
		}

		return result;
	}

	private FlushFdrPage parsePage(String treeData) {
		FlushFdrPage result = new FlushFdrPage();
		StringTokenizer tokens = new StringTokenizer(treeData, BARRA);
		int i = 0;
		String mostrar = "";
		while (tokens.hasMoreTokens()) {
			mostrar = tokens.nextToken();
			if (i == 2) {
				result.setTreeId(mostrar);
			}
			if (i == 3) {
				result.setPageName(mostrar);
			}
			if (i == 4) {
				result.setFatherId(mostrar);
			}
			if (i == 5) {
				result.setFatherClassName(mostrar);
			}
			i++;
		}
		if (_logger.isDebugEnabled()) {
			_logger.debug("result FlushFdrPage : " + result);
		}

		return result;
	}

	private String parseTree(String treeData) {

		StringTokenizer tokens = new StringTokenizer(treeData, BARRA);
		String mostrar = "";
		while (tokens.hasMoreTokens()) {
			mostrar = tokens.nextToken();
			if (mostrar.equals("CL2") || mostrar.equals("CL3")) {
				break;
			}

		}
		return mostrar;
	}

	private Map generateTreeUpdateMap(List docItems, List pageItems, List claves) {
		Map documents = new HashMap();
		List resultPages = null;
		FlushFdrDocument docExist = null;
		String clavedocument = null;
		for (Iterator it = docItems.iterator(); it.hasNext();) {
			FlushFdrDocument flushFdrDocument = (FlushFdrDocument) it.next();
			clavedocument = flushFdrDocument.getTreeId();
			if (claves.contains(clavedocument)) {
				resultPages = new ArrayList();
				for (Iterator it1 = pageItems.iterator(); it1.hasNext();) {
					FlushFdrPage flushFdrPage = (FlushFdrPage) it1.next();
					if (flushFdrPage.getFatherId().equals(clavedocument)) {
						resultPages.add(flushFdrPage);
					}
				}
				flushFdrDocument.setPages(resultPages);
			}
			documents.put(clavedocument, flushFdrDocument);
		}
		return documents;
	}

	private String getFileNameFis(String sessionId, String folderId, int order,
			String name) {
		String extension = getExtension(name);
		StringBuffer buffer = new StringBuffer();
		buffer.append(sessionId);
		buffer.append(GUIONBAJO);
		buffer.append(folderId);
		buffer.append(GUIONBAJO);
		buffer.append(order);
		if (!extension.equals("-")) {
			buffer.append(PUNTO);
			buffer.append(extension);
		}
		if (_logger.isDebugEnabled()) {
			_logger.debug("getFileName: " + buffer.toString());
		}

		return buffer.toString();
	}

	private String getExtension(String name) {
		String extension = name.substring(name.lastIndexOf(DOSPATH) + 1,
				name.length());
		if (extension.indexOf(PUNTO) == -1) {
			extension = EXTENSION_DEFECTO;
		} else {
			extension = extension.substring(extension.lastIndexOf(PUNTO) + 1,
					extension.length());
		}
		return extension;
	}

	private String changeExtension(String fileName, String extensionNew,
			String extensionOld) {
		if (EXTENSION_DEFECTO.equalsIgnoreCase(extensionOld)) {
			if (!EXTENSION_DEFECTO.equalsIgnoreCase(extensionNew)) {
				fileName = fileName + PUNTO + extensionNew;
			}
		} else if (!EXTENSION_DEFECTO.equalsIgnoreCase(extensionNew)) {
			if (StringUtils.isBlank(extensionOld)) {
				fileName = fileName + extensionNew;
			} else {
				fileName = fileName.replaceFirst(PUNTO + extensionOld, PUNTO
						+ extensionNew);
			}
		} else {
			fileName = fileName.replaceFirst(PUNTO + extensionOld, "");
		}
		return fileName;
	}
}