package com.ieci.tecdoc.isicres.usecase.book;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrCaaux;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.RowQuerySearchAdvanced;
import com.ieci.tecdoc.common.isicres.SaveOrigDocDataDocInput;
import com.ieci.tecdoc.common.isicres.SaveOrigDocDatasInput;
import com.ieci.tecdoc.common.isicres.SearchOperator;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.isicres.ValidationResultCode;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.decoder.form.FCtrlDef;
import com.ieci.tecdoc.idoc.decoder.form.FPageDef;
import com.ieci.tecdoc.idoc.decoder.form.FormFormat;
import com.ieci.tecdoc.idoc.decoder.query.QCtrlDef;
import com.ieci.tecdoc.idoc.decoder.query.QueryFormat;
import com.ieci.tecdoc.idoc.decoder.table.TColDef;
import com.ieci.tecdoc.idoc.decoder.table.TableFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.isicres.audit.helper.ISicresAuditHelper;
import com.ieci.tecdoc.isicres.context.ISicresBeansProvider;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.folder.FolderAsocSession;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.session.folder.FolderHistSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.session.utils.UtilsSessionEx;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionEx;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.book.util.BookUseCaseAsocRegsUtil;
import com.ieci.tecdoc.isicres.usecase.book.xml.AsocRegsResults;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLAsocRegsFdr;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLAsocRegsResults;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLAsocRegsSearch;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLAsocRegsValidate;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLBook;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLBookTree;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLBooks;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLDistReg;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLFrmPersistFld;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLOrigDocFdr;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLQueryBook;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLTableBook;
import com.ieci.tecdoc.isicres.usecase.book.xml.XMLUpdHisReg;
import com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLValidationCode;

import es.ieci.tecdoc.isicres.terceros.util.InteresadosDecorator;

/**
 * @author LMVICENTE
 * @creationDate 29-abr-2004 16:25:17
 * @version
 * @since
 */
public class BookUseCase implements Keys {



	/***************************************************************************
	 * Attributes
	 **************************************************************************/


	private static Logger _logger = Logger.getLogger(BookUseCase.class);

	private static final String PUNTO_COMA = ";";

	private static final String AMPERSAN = "#";

	private static final String BARRA = "|";

	private static final String AND = " && ";

	private static final String SPACES = " ";

	private static final String PORCIENTO = "%";

	private static final int MASK_PERM_QUERY = 1;

	private static final int MASK_PERM_CREATE = 2;

	private static final int MASK_PERM_MODIFY = 4;

	private static final String COMPARE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	private ValidationListUseCase validationUseCase = null;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/
	public BookUseCase() {
		super();
		validationUseCase = new ValidationListUseCase();
	}

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public Date getDBDateServer(UseCaseConf useCaseConf)
			throws ValidationException, BookException, SessionException {
		return BookSession.getDBDateServer(useCaseConf.getSessionID(),
				useCaseConf.getEntidadId());
	}

	public void saveCompulFiles(ISicresCreateCompulsaVO compulsa)
			throws ValidationException, BookException, SessionException {
		String label = RBUtil.getInstance(compulsa.getLocale()).getProperty(
				Keys.I18N_LABEL_COMPUL_DOCUMENTS);
		FolderFileSession.saveCompulFiles(compulsa, label);
	}

	public AxSf getBookFolder(UseCaseConf useCaseConf, Integer bookID, int fdrid)
			throws ValidationException, BookException, SessionException {
		return FolderSession.getBookFolder(useCaseConf.getSessionID(), bookID,
				fdrid, useCaseConf.getLocale(), useCaseConf.getEntidadId());
	}

	public boolean lockFolder(UseCaseConf useCaseConf, Integer bookID, int fdrid)
			throws ValidationException, BookException, SessionException {
		return FolderSession.lockFolder(useCaseConf.getSessionID(), bookID,
				fdrid, useCaseConf.getEntidadId());
	}

	public Document getUserConfig(UseCaseConf useCaseConf, Integer bookId)
			throws ValidationException, BookException, SessionException,
			SecurityException {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		Document doc = null;

		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), bookId, axsfQ, false, useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		try {
			doc = XMLBookTree.createXMLUserConfig(useCaseConf, bookId, usrConf,
					useCaseConf.getLocale());
		} catch (Exception e) {
			throw new BookException(
					BookException.ERROR_CANNOT_LOAD_PERSIST_FIELDS);
		}

		return doc;
	}

	/*
	 * Nuevo metodo con un parametro para saber si estamos creando o editando un
	 * registro
	 */
	public Document getUserConfig(UseCaseConf useCaseConf, Integer bookId,
			boolean nuevoRegistro) throws ValidationException, BookException,
			SessionException, SecurityException {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		Document doc = null;

		UserConf usrConf = BookSession
				.getUserConfig(useCaseConf.getSessionID(), bookId, axsfQ,
						false, useCaseConf.getLocale(), useCaseConf
								.getEntidadId(), nuevoRegistro);
		try {
			doc = XMLBookTree.createXMLUserConfig(useCaseConf, bookId, usrConf,
					useCaseConf.getLocale());
		} catch (Exception e) {
			throw new BookException(
					BookException.ERROR_CANNOT_LOAD_PERSIST_FIELDS);
		}

		return doc;
	}

	public String saveUserConfig(UseCaseConf useCaseConf, Integer bookId,
			String unitCode, Integer unitType) throws ValidationException,
			BookException, SessionException, SecurityException {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		String xml = null;
		Document doc = null;

		BookSession.saveUserConfig(useCaseConf.getSessionID(), bookId,
				unitCode, unitType, axsfQ, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), bookId, axsfQ, false, useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		try {
			doc = XMLBookTree.createXMLUserConfig(useCaseConf, bookId, usrConf,
					useCaseConf.getLocale());
		} catch (Exception e) {
			throw new BookException(
					BookException.ERROR_CANNOT_LOAD_PERSIST_FIELDS);
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		}
		xml = writer.toString();
		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	public String saveUserConfig(UseCaseConf useCaseConf, Integer bookId,
			String fields, String params) throws ValidationException,
			BookException, SessionException, SecurityException {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		String xml = null;
		Document doc = null;

		BookSession.saveUserConfig(useCaseConf.getSessionID(), bookId, fields,
				params, axsfQ, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), bookId, axsfQ, false, useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		try {
			doc = XMLBookTree.createXMLUserConfig(useCaseConf, bookId, usrConf,
					useCaseConf.getLocale());
		} catch (Exception e) {
			throw new BookException(
					BookException.ERROR_CANNOT_LOAD_PERSIST_FIELDS);
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS);
		}
		xml = writer.toString();
		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	/**
	 * Metodo que obtiene el xml con la información de un registro
	 * @param useCaseConf - Datos de conexión del usuario
	 * @param bookId - ID del libro
	 * @param readOnly - Indica si el registro es de lectura
	 * @param folderPId - Identificador del proceso
	 * @param folderId - Identificador del registro/carpeta
	 * @param row - numero de fila
	 * @param url
	 * @param openFolderDtr - Indica si se abre desde la distribución
	 * @param locale
	 * @return XML con los datos de un registro
	 *
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 * @throws SecurityException
	 */
	public Document getBookTree(UseCaseConf useCaseConf, Integer bookId,
			boolean readOnly, long folderPId, int folderId, int row,
			String url, boolean openFolderDtr, Locale locale)
			throws ValidationException, BookException, SessionException,
			SecurityException {
		Idocarchdet idocarchdet = BookSession.getIdocarchdetMisc(useCaseConf
				.getSessionID(), bookId);
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
				bookId, folderId, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		axsf.setFormat(axsfQ.getFormat());
		ScrRegstate scrregstate = BookSession.getBook(useCaseConf
				.getSessionID(), bookId);
		boolean permShowDocuments = SecuritySession.permisionShowDocuments(
				useCaseConf.getSessionID(), axsf);
		List docs = null;
		if (permShowDocuments || openFolderDtr) {
			docs = FolderFileSession.getBookFolderDocsWithPages(useCaseConf
					.getSessionID(), bookId, folderId, useCaseConf
					.getEntidadId());
		}
		boolean isBookAdmin = SecuritySession.isBookAdmin(useCaseConf
				.getSessionID(), bookId);
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), bookId, axsfQ, false, useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		String archiveName = BookSession.getBookName(
				useCaseConf.getSessionID(), bookId, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());

		//Tipo de Libro
		int bookType = getTypeBook(axsf);

		if (!readOnly) {
			// el metodo lockFolder devuelve:
			// true ha sido bloqueada
			// false ya estaba bloqueada
			readOnly = !(FolderSession.lockFolder(useCaseConf.getSessionID(),
					bookId, folderId, useCaseConf.getEntidadId()));

			// Si el estado del registro es CERRADO no se puede modificar, debe
			// aparecer como de sï¿½lo lectura
			String estado = axsf.getAttributeValueAsString(AxSf.FLD6_FIELD);
			// validamos si el estado es CERRADO o ANULADO si es asi el registro
			// no se puede modificar
			readOnly = validateStateFolderIfClosedOrCancel(estado);
		}

		Document doc = XMLBookTree.createXMLBookTree(useCaseConf, bookId,
				scrregstate, axsf, idocarchdet, readOnly, folderPId, folderId,
				row, docs, url, bookType, isBookAdmin, locale, 1,
				sessionInformation, usrConf, archiveName);

		//Auditamos el acceso al registro
		ISicresAuditHelper.auditarAccesoRegistro(useCaseConf.getSessionID(),
				bookId, folderId, axsf, archiveName, bookType);

		return doc;
	}

	/**
	 * Método que comprueba si el estado del registro es CERRADO o ANULADO
	 *
	 * @param estado
	 *            - ESTADO del registro
	 * @return boolean - TRUE: el registro esta anulado o cerrado / FALSE: el
	 *         registro se encuentra en otro estado diferente a CERRADO o
	 *         ANULADO
	 */
	public boolean validateStateFolderIfClosedOrCancel(String estado) {
		boolean result = false;

		//validamos que el estado no este vacio
		if (StringUtils.isNotEmpty(estado)) {
			//obtenemos el valor numerico del estado
			int numEstado = (new Integer(estado)).intValue();

			//validamos si el estado es CERRADO o ANULADO
			if ((numEstado == ISicresKeys.SCR_ESTADO_REGISTRO_CERRADO)
					|| (numEstado == ISicresKeys.SCR_ESTADO_REGISTRO_ANULADO)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Método que devuelve el tipo de libro
	 * @param axsf - {@link AxSf}
	 *
	 * @return int - Indica el tipo de libro: 1- Libro de Entrada / 2 - Libro de salida
	 */
	public int getTypeBook(AxSf axsf) {
		int bookType = 0;

		if (axsf instanceof AxSfIn) {
			bookType = 1;
		} else if (axsf instanceof AxSfOut) {
			bookType = 2;
		}
		return bookType;
	}

	/**
	 * Método que devuelve el nombre del documento
	 * @param useCaseConf - Datos del usuario
	 * @param bookId - Id del libro
	 * @param folderId - ID del registro
	 * @param id - ID del documento
	 *
	 * @return String - Nombre del documento
	 *
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 */
	public String getDocName(UseCaseConf useCaseConf, Integer bookId,
			int folderId, String id) throws ValidationException, BookException,
			SessionException {
		List docs = FolderFileSession.getBookFolderDocsWithPages(useCaseConf
				.getSessionID(), bookId, folderId, useCaseConf.getEntidadId());
		String docName = null;
		if (docs != null && !docs.isEmpty()) {
			AxDoch axdoch = null;
			for (Iterator it = docs.iterator(); it.hasNext() && docName == null;) {
				axdoch = (AxDoch) it.next();
				if (Integer.toString(axdoch.getId()).equals(id)) {
					docName = axdoch.getName();
				}
			}
		}
		return docName;
	}

	/**
	 * Metodo que devuelve los datos y formato que componen un nuevo registro
	 *
	 * @param useCaseConf
	 * @param bookId
	 * @param readOnly
	 * @param folderPId
	 * @param folderId
	 * @param row
	 * @param url
	 * @param locale
	 * @param nuevoRegistro - Indica si el registro es nuevo o si se modifica
	 * @return
	 * @throws ValidationException
	 * @throws BookException
	 * @throws SessionException
	 * @throws SecurityException
	 */
	public Document getEmptyBookTree(UseCaseConf useCaseConf, Integer bookId,
			boolean readOnly, long folderPId, int folderId, int row,
			String url, Locale locale, boolean nuevoRegistro) throws ValidationException,
			BookException, SessionException, SecurityException {
		Idocarchdet idocarchdet = BookSession.getIdocarchdetMisc(useCaseConf
				.getSessionID(), bookId);
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		ScrRegstate scrregstate = BookSession.getBook(useCaseConf
				.getSessionID(), bookId);
		boolean isBookAdmin = SecuritySession.isBookAdmin(useCaseConf
				.getSessionID(), bookId);
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), bookId, axsfQ, false, useCaseConf
						.getLocale(), useCaseConf.getEntidadId(),nuevoRegistro);
		String archiveName = BookSession.getBookName(
				useCaseConf.getSessionID(), bookId, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());

		int bookType = getTypeBook(axsfQ);

		Document doc = XMLBookTree.createXMLBookTree(useCaseConf, bookId,
				scrregstate, axsfQ, idocarchdet, readOnly, folderPId, folderId,
				row, null, url, bookType, isBookAdmin, locale, 0,
				sessionInformation, usrConf, archiveName);
		return doc;

	}

	public Document getBookFolderInitialPage(UseCaseConf useCaseConf,
			Integer bookId, int fldid, int page, boolean readOnly)
			throws ValidationException, AttributesException, SecurityException,
			BookException, SessionException, Exception {
		return getBookFolderPage(useCaseConf, bookId, fldid, 0, readOnly);
	}

	public Document getBookFolderInitialPageFromCopy(UseCaseConf useCaseConf,
			Integer bookId, int fldid, int page, boolean readOnly)
			throws ValidationException, AttributesException, SecurityException,
			BookException, SessionException, Exception {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		Map extendedValues = new HashMap();
		String origen = null;
		String destino = null;
		String additionalInfo = null;
		int updatePer = 0;
		int addPer = 0;
		int updateProField = 0;
		int setRegisterDate = 0;
		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
				bookId, fldid, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		axsf.setFormat(axsfQ.getFormat());
		String key = null;
		List toRemove = new ArrayList();
		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			key = (String) it.next();
			try {
				if (Integer.parseInt(key.substring(3, key.length())) < 7) {
					toRemove.add(key);
				}
			} catch (NumberFormatException e) {
				toRemove.add(key);
			}
		}
		for (Iterator it = toRemove.iterator(); it.hasNext();) {
			axsf.removeAttribute((String) it.next());
		}
		axsf.setFld5(null);
		extendedValues = getValidationFields(axsf, useCaseConf, bookId, page);
		if (!readOnly) {
			int[] perms = SecuritySession.getScrPermission(useCaseConf
					.getSessionID(), bookId);
			addPer = perms[0];
			updatePer = perms[1];
			updateProField = perms[4];
			setRegisterDate = perms[2];
		}
		origen = validationUseCase.getScrOrgName(useCaseConf, axsf.getFld7(),
				axsf.getFld7Name());
		destino = validationUseCase.getScrOrgName(useCaseConf, axsf.getFld8(),
				axsf.getFld8Name());
		ScrCaaux scr = null;
		if (axsf instanceof AxSfIn
				&& axsf.getAttributeValueAsString("fld16") != null) {
			scr = UtilsSessionEx.getScrCaaux(useCaseConf.getSessionID(),
					bookId, axsf.getAttributeValueAsString("fld16"),
					useCaseConf.getEntidadId());
		} else if (axsf instanceof AxSfOut
				&& axsf.getAttributeValueAsString("fld12") != null) {
			scr = UtilsSessionEx.getScrCaaux(useCaseConf.getSessionID(),
					bookId, axsf.getAttributeValueAsString("fld12"),
					useCaseConf.getEntidadId());
		}
		additionalInfo = "";
		if (scr != null) {
			additionalInfo = scr.getDatosAux();
		}
		axsf.setAttributeValue("fld9", getDest(useCaseConf, bookId, fldid));
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), bookId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		Document doc = XMLBook.createXMLBook(readOnly, updatePer, addPer,
				updateProField, setRegisterDate, axsf, fieldFormat, bookId,
				page, useCaseConf.getLocale(), extendedValues, origen, destino,
				additionalInfo, sessionInformation);
		return doc;
	}

	public Document getBookFolderPage(UseCaseConf useCaseConf, Integer bookId,
			int fldid, int page, boolean readOnly) throws ValidationException,
			SecurityException, AttributesException, BookException,
			SessionException, Exception {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		Map extendedValues = new HashMap();
		String origen = null;
		String destino = null;
		String additionalInfo = null;
		int updatePer = 0;
		int addPer = 0;
		int updateProField = 0;
		int updateRegisterDate = 0;
		AxSf axsf = null;
		if (fldid != -1) {
			axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
					bookId, fldid, useCaseConf.getLocale(), useCaseConf
							.getEntidadId());
			axsf.setFormat(axsfQ.getFormat());
			axsf.setLenFields(axsfQ.getLenFields());
			extendedValues = getValidationFields(axsf, useCaseConf, bookId,
					page);
			if (!readOnly) {
				// el metodo lockFolder devuelve:
				// true ha sido bloqueada
				// false ya estaba bloqueada
				readOnly = !(FolderSession.lockFolder(useCaseConf
						.getSessionID(), bookId, fldid, useCaseConf
						.getEntidadId()));
			}
			if (!readOnly) {
				int[] perms = SecuritySession.getScrPermission(useCaseConf
						.getSessionID(), bookId);
				addPer = perms[0];
				updatePer = perms[1];
				updateProField = perms[4];
				updateRegisterDate = perms[3];
			}
			origen = validationUseCase.getScrOrgName(useCaseConf, axsf
					.getFld7(), axsf.getFld7Name());
			destino = validationUseCase.getScrOrgName(useCaseConf, axsf
					.getFld8(), axsf.getFld8Name());
			ScrCaaux scr = null;
			if (axsf instanceof AxSfIn
					&& axsf.getAttributeValueAsString("fld16") != null) {
				scr = UtilsSessionEx.getScrCaaux(useCaseConf.getSessionID(),
						bookId, axsf.getAttributeValueAsString("fld16"),
						useCaseConf.getEntidadId());
			} else if (axsf instanceof AxSfOut
					&& axsf.getAttributeValueAsString("fld12") != null) {
				scr = UtilsSessionEx.getScrCaaux(useCaseConf.getSessionID(),
						bookId, axsf.getAttributeValueAsString("fld12"),
						useCaseConf.getEntidadId());
			}
			additionalInfo = "";
			if (scr != null) {
				additionalInfo = scr.getDatosAux();
			}
			axsf.setAttributeValue("fld9", getDest(useCaseConf, bookId, fldid));
		} else {
			axsf = axsfQ;
			// ////////////////////////
			int[] perms = SecuritySession.getScrPermission(useCaseConf
					.getSessionID(), bookId);
			addPer = perms[0];
			updatePer = perms[1];
			updateProField = perms[4];
			updateRegisterDate = perms[2];
			// ///////////////////////
			extendedValues = getValidationFields(axsf, useCaseConf, bookId,
					page);
		}
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), bookId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		Document doc = XMLBook.createXMLBook(readOnly, updatePer, addPer,
				updateProField, updateRegisterDate, axsf, fieldFormat, bookId,
				page, useCaseConf.getLocale(), extendedValues, origen, destino,
				additionalInfo, sessionInformation);
		return doc;
	}

	public Document getBooks(UseCaseConf useCaseConf)
			throws ValidationException, BookException, SessionException,
			DistributionException {
		int sizeNewDist = 0;
		int sizeRejectedDist = 0;

		Integer alarmOnNewDist = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getAlarmOnNewDist());
		if (alarmOnNewDist.intValue() != 0) {
			// sizeNewDist = DistributionSession.getNewFolderDist(useCaseConf
			// .getSessionID(), useCaseConf.getEntidadId());
			sizeNewDist = DistributionSession.getNewFolderDistByDeptId(useCaseConf
					.getSessionID(), useCaseConf.getEntidadId());
		}
		Integer alarmOnRejected = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getAlarmOnRejected());
		if (alarmOnRejected.intValue() != 0) {
			// sizeRejectedDist = DistributionSession.getRejectedFolderDist(
			// useCaseConf.getSessionID(), useCaseConf.getEntidadId());
			sizeRejectedDist = DistributionSession.getRejectedFolderDistByDeptId(
					useCaseConf.getSessionID(), useCaseConf.getEntidadId());
		}
		List inList = BookSession.getInBooks(useCaseConf.getSessionID(),
				useCaseConf.getLocale(), useCaseConf.getEntidadId());
		List outList = BookSession.getOutBooks(useCaseConf.getSessionID(),
				useCaseConf.getLocale(), useCaseConf.getEntidadId());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		Document doc = XMLBooks
				.createXMLBooks(inList, outList, useCaseConf.getLocale(),
						sizeNewDist, sizeRejectedDist, sessionInformation);
		return doc;
	}

	public Map translateQueryParams(UseCaseConf useCaseConf, Integer bookId,
			Map params) throws ValidationException, BookException,
			AttributesException, SessionException, SecurityException {
		// Devolvemos id del campo y valor traducido
		Map idsToValidate = new HashMap();
		Map controlsMemo = new HashMap();
		Map result = new HashMap();

		AxSf axsfQ = getQueryFormat(useCaseConf.getSessionID(), bookId,
				useCaseConf.getEntidadId());

		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
		Collection formatterFields = formatter.getDlgDef().getCtrldefs()
				.values();
		QCtrlDef ctrlDef = null;
		int fldid = 0;
		int id = 0;
		for (Iterator it = formatterFields.iterator(); it.hasNext();) {
			ctrlDef = (QCtrlDef) it.next();
			fldid = ctrlDef.getFldId();
			id = ctrlDef.getId();
			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				if (params.get(Integer.toString(id)) != null
						&& params.get(Integer.toString(id)).toString().length() > 0) {
					try {
						AxSfQueryField field = new AxSfQueryField();
						field.setFldId(XML_FLD_TEXT + ctrlDef.getFldId());
						field.setValue(getValue(axsfQ, field, params, ctrlDef,
								null, useCaseConf.getLocale()));
						// field.setOperator(params.getParameter(Integer.toString(ctrlDef.getRelCtrlId())));
						field.setOperator(translateOperator(getValue(params,
								Integer.toString(ctrlDef.getRelCtrlId())),
								useCaseConf.getLocale()));
						if (fldid == 5) {
							idsToValidate.put(new Integer(fldid), field
									.getValue());
							controlsMemo.put(new Integer(fldid),
									new Integer(id));
						}
						if ((fldid == 7 || fldid == 8)
								&& !field
										.getOperator()
										.equals(
												com.ieci.tecdoc.common.isicres.Keys.QUERY_DEPEND_OF_TEXT_VALUE)) {
							idsToValidate.put(new Integer(fldid), field
									.getValue());
							controlsMemo.put(new Integer(fldid),
									new Integer(id));
						}
						if (axsfQ instanceof AxSfIn) {
							if ((fldid == 13 || fldid == 16)) {
								idsToValidate.put(new Integer(fldid), field
										.getValue());
								controlsMemo.put(new Integer(fldid),
										new Integer(id));
							}
						} else {
							if ((fldid == 12))
								idsToValidate.put(new Integer(fldid), field
										.getValue());
							controlsMemo.put(new Integer(fldid),
									new Integer(id));
						}
					} catch (Exception e) {
					}
				}
			}
		}
		if (!idsToValidate.isEmpty()) {
			result = AttributesSession.translateFixedValues(useCaseConf
					.getSessionID(), bookId, idsToValidate, useCaseConf
					.getEntidadId());
		}
		return result;
	}

	public String validateQueryParam(UseCaseConf useCaseConf, Integer bookId,
			int fldId, String code) throws ValidationException, BookException,
			AttributesException, SessionException, SecurityException {

		ValidationResultCode validationResultCode = AttributesSession
				.validateAndReturnValidationCode(useCaseConf.getSessionID(),
						bookId, fldId, code, useCaseConf.getLocale(),
						useCaseConf.getEntidadId());
		Document xmlDocument = XMLValidationCode.createXMLValidationCode(
				validationResultCode, useCaseConf.getLocale());
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(xmlDocument);
		} catch (IOException e) {
			throw new ValidationException(
					ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);
		}
		String xml = writer.toString();

		return xml;
		// return xmlDocument.asXML();
		// return
		// AttributesSession.validateAndReturnDescriptionValue(useCaseConf.getSessionID(),
		// bookId,
		// fldId, code);
	}

	public List validateQueryParams(UseCaseConf useCaseConf, Integer bookId,
			Map params) throws ValidationException, BookException,
			AttributesException, SessionException, SecurityException {
		// Tenemos que devolver los controles que fallan
		List result = new ArrayList();
		Map idsToValidate = new HashMap();
		Map controlsMemo = new HashMap();
		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}

		AxSf axsfQ = getQueryFormat(useCaseConf.getSessionID(), bookId,
				useCaseConf.getEntidadId());

		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
		Collection formatterFields = formatter.getDlgDef().getCtrldefs()
				.values();
		QCtrlDef ctrlDef = null;
		int fldid = 0;
		int id = 0;

		for (Iterator it = formatterFields.iterator(); it.hasNext();) {
			ctrlDef = (QCtrlDef) it.next();
			fldid = ctrlDef.getFldId();
			id = ctrlDef.getId();

			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				String value = getValue(params, Integer.toString(id));
				if (value != null && value.toString().length() > 0) {
					try {
						AxSfQueryField field = new AxSfQueryField();
						field.setFldId(XML_FLD_TEXT + ctrlDef.getFldId());
						field.setValue(getValue(axsfQ, field, params, ctrlDef,
								null, useCaseConf.getLocale()));
						field.setOperator(translateOperator(getValue(params,
								Integer.toString(ctrlDef.getRelCtrlId())),
								useCaseConf.getLocale()));
						if (field.getValue().getClass().equals(ArrayList.class)) {
							if (((List) field.getValue()).size() != 2
									&& !field.getOperator().equals(BARRA)) {
								result.add(new Integer(id));
							}
						}

						if ((fldid == 2 || fldid == 4)
								&& field.getValue().getClass().equals(
										Date.class)) {
							String auxDate = getValue(params,
									Integer.toString(id)).toString();
							SimpleDateFormat shortFormatter = new SimpleDateFormat(
									RBUtil.getInstance(useCaseConf.getLocale())
											.getProperty(I18N_DATE_SHORTFORMAT));
							shortFormatter.setLenient(false);
							try {
								if (auxDate.length() > 10) {
									result.add(new Integer(id));
								}
								shortFormatter.parse(auxDate);
								if (shortFormatter.getCalendar().get(
										Calendar.YEAR) < 1970) {
									result.add(new Integer(id));
								}
							} catch (Exception e) {
								result.add(new Integer(id));
							}
						}
						if ((fldid == 5 || fldid == 7 || fldid == 8)) {

							if (!field.getValue().getClass().equals(List.class)) {
								idsToValidate.put(new Integer(fldid), field
										.getValue());
								controlsMemo.put(new Integer(fldid),
										new Integer(id));
							}
						}

						if (axsfQ instanceof AxSfIn) {
							if ((fldid == 13 || fldid == 16)) {
								if (!field.getValue().getClass().equals(
										List.class)) {
									idsToValidate.put(new Integer(fldid), field
											.getValue());
									controlsMemo.put(new Integer(fldid),
											new Integer(id));
								}
							}
							if (fldid > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
								if (AttributesSession
										.getExtendedValidationFieldValue(
												useCaseConf.getSessionID(),
												bookId, fldid, field.getValue()
														.toString(), locale
														.getLanguage(),
												useCaseConf.getEntidadId()) == null) {
									result.add(new Integer(id));
								}

							}
						} else {
							if ((fldid == 12)) {
								if (!field.getValue().getClass().equals(
										List.class)) {
									idsToValidate.put(new Integer(fldid), field
											.getValue());
									controlsMemo.put(new Integer(fldid),
											new Integer(id));
								}
							}
							if (fldid > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
								if (AttributesSession
										.getExtendedValidationFieldValue(
												useCaseConf.getSessionID(),
												bookId, fldid, field.getValue()
														.toString(), locale
														.getLanguage(),
												useCaseConf.getEntidadId()) == null) {
									result.add(new Integer(id));
								}

							}
						}
					} catch (Exception e) {
						result.add(new Integer(id));
					}
				}
			}
		}

		if (!idsToValidate.isEmpty()) {
			List aux = AttributesSession.validateFixedValues(useCaseConf
					.getSessionID(), bookId, idsToValidate, false, useCaseConf
					.getEntidadId());
			Integer auxFldid = null;

			for (Iterator it = aux.iterator(); it.hasNext();) {
				auxFldid = (Integer) it.next();
				result.add(controlsMemo.get(auxFldid));
			}
		}

		return result;
	}

	public List validateTableTextQueryParams(UseCaseConf useCaseConf,
			Integer bookId, Map params, List badCtrls)
			throws ValidationException, BookException, AttributesException,
			SessionException, SecurityException, ParseException {

		// Tenemos que devolver los controles que fallan
		AxSf axsfQ = BookSession.getQueryFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());

		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
		Collection formatterFields = formatter.getDlgDef().getCtrldefs()
				.values();

		List result = new ArrayList();

		for (Iterator it = formatterFields.iterator(); it.hasNext();) {
			QCtrlDef ctrlDef = (QCtrlDef) it.next();
			int fldid = ctrlDef.getFldId();
			int id = ctrlDef.getId();

			if (badCtrls.contains(new Integer(id))) {
				String value = getValue(params, Integer.toString(id));
				if ((ctrlDef.getName().startsWith(IDOC_EDIT))
						&& ((value != null)) && (value.toString().length() > 0)) {
					try {
						AxSfQueryField field = new AxSfQueryField();
						field.setFldId(XML_FLD_TEXT + ctrlDef.getFldId());

						field.setValue(getValue(axsfQ, field, params, ctrlDef,
								null, useCaseConf.getLocale()));
						field.setOperator(translateOperator(getValue(params,
								Integer.toString(ctrlDef.getRelCtrlId())),
								useCaseConf.getLocale()));
						if (axsfQ instanceof AxSfIn) {
							if (fldid > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
								if (AttributesSession
										.getExtendedValidationFieldValueForQuery(
												useCaseConf.getSessionID(),
												bookId, fldid, field
														.getOperator(),
												useCaseConf.getEntidadId())) {
									result.add(new Integer(id));
								}
							}
						} else {
							if (fldid > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
								if (AttributesSession
										.getExtendedValidationFieldValueForQuery(
												useCaseConf.getSessionID(),
												bookId, fldid, field
														.getOperator(),
												useCaseConf.getEntidadId())) {
									result.add(new Integer(id));
								}
							}
						}
					} catch (Exception e) {
						if (!badCtrls.contains(new Integer(id))) {
							badCtrls.add(new Integer(id));
						}
					}

				}
			}
		}

		if (!result.isEmpty()) {
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Integer idResult = (Integer) iterator.next();

				for (Iterator iter = badCtrls.iterator(); iter.hasNext();) {
					Integer idBadCtrls = (Integer) iter.next();

					if (idBadCtrls.intValue() == idResult.intValue()) {
						iter.remove();
						break;
					}
				}
			}
		}

		return badCtrls;
	}

	public void validateOfficeCode(UseCaseConf useCaseConf, String code)
			throws SessionException, ValidationException {
		ValidationSessionEx.validateOfficeCode(useCaseConf.getSessionID(),
				code, useCaseConf.getEntidadId());
	}

	public int openTableResults(UseCaseConf useCaseConf, Integer bookId,
			Map params, Map translations, Integer reportOption, String listOrder)
			throws ValidationException, BookException, SessionException,
			SecurityException {
		AxSf axsfQ = BookSession.getQueryFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		AxSfQuery axsfQuery = new AxSfQuery();
		axsfQuery.setOrderBy(listOrder);
		axsfQuery.setBookId(bookId);
		axsfQuery
				.setPageSize(Integer
						.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_TABLE_RESULTS_SIZE)));
		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
		Collection formatterFields = formatter.getDlgDef().getCtrldefs()
				.values();
		QCtrlDef ctrlDef = null;
		for (Iterator it = formatterFields.iterator(); it.hasNext();) {
			ctrlDef = (QCtrlDef) it.next();
			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				String value = getValue(params, Integer.toString(ctrlDef
						.getId()));
				if (value != null && value.toString().length() > 0) {
					try {
						AxSfQueryField field = new AxSfQueryField();
						field.setFldId(XML_FLD_TEXT + ctrlDef.getFldId());
						field.setValue(getValue(axsfQ, field, params, ctrlDef,
								translations, useCaseConf.getLocale()));
						// field.setOperator(params.getParameter(Integer.toString(ctrlDef.getRelCtrlId())));
						field.setOperator(translateOperator(getValue(params,
								Integer.toString(ctrlDef.getRelCtrlId())),
								useCaseConf.getLocale()));
						axsfQuery.addField(field);
					} catch (ParseException e) {
						_logger.fatal("Error al parsear", e);
					}
				}
			}
		}
		if (formatter.getSelectDef().getType() == 3) {
			axsfQuery.setSelectDefWhere2(formatter.getSelectDef().getWhere2());
		}
		List bookIds = new ArrayList();
		if (reportOption.intValue() != 0) {
			// ScrRegstate scrregstate = null;

			if (BookSession.isInBook(useCaseConf.getSessionID(), bookId,
					useCaseConf.getEntidadId())) {
				List inList = BookSession.getInBooks(
						useCaseConf.getSessionID(), useCaseConf.getLocale(),
						useCaseConf.getEntidadId());
				for (Iterator it = inList.iterator(); it.hasNext();) {
					// scrregstate = (ScrRegstate) it.next();
					// if (reportOption.intValue() == 2) {
					// bookIds.add(scrregstate.getIdocarchhdr().getId());
					// } else {
					// if (scrregstate.getState() == 0) {
					// bookIds.add(scrregstate.getIdocarchhdr().getId());
					// }
					// }
					ScrRegStateByLanguage scrregstate = (ScrRegStateByLanguage) it
							.next();
					if (reportOption.intValue() == 2
							|| scrregstate.getScrregstateState() == 0) {
						bookIds.add(scrregstate.getIdocarchhdrId());
					}
				}
			} else {
				List outList = BookSession.getOutBooks(useCaseConf
						.getSessionID(), useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
				for (Iterator it = outList.iterator(); it.hasNext();) {
					// scrregstate = (ScrRegstate) it.next();
					// if (reportOption.intValue() == 2) {
					// bookIds.add(scrregstate.getIdocarchhdr().getId());
					// } else {
					// if (scrregstate.getState() == 0) {
					// bookIds.add(scrregstate.getIdocarchhdr().getId());
					// }
					// }
					ScrRegStateByLanguage scrregstate = (ScrRegStateByLanguage) it
							.next();
					if (reportOption.intValue() == 2
							|| scrregstate.getScrregstateState() == 0) {
						bookIds.add(scrregstate.getIdocarchhdrId());
					}
				}
			}
		}

		if (axsfQuery.getPageSize() <= 0) {
			throw new BookException(BookException.ERROR_PAGE_SIZE);
		}

		int size = FolderSession.openRegistersQuery(useCaseConf.getSessionID(),
				axsfQuery, bookIds, reportOption, useCaseConf.getEntidadId());
		return size;
	}

	public void closeTableResults(UseCaseConf useCaseConf, Integer bookId)
			throws ValidationException, BookException, SessionException,
			SecurityException {
		BookSession.closeBook(useCaseConf.getSessionID(), bookId);
	}

	public Document getLastRegisterForUser(UseCaseConf useCaseConf,
			Integer bookID) throws AttributesException, ValidationException,
			BookException, SessionException, SecurityException {
		AxSf axsf = BookSession.getTableFormat(useCaseConf.getSessionID(),
				bookID, useCaseConf.getEntidadId());
		Integer autoDist = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getAutoDist());
		Integer distPerms[] = SecuritySession.getScrDistPermission(useCaseConf
				.getSessionID());

		boolean distributionManualBookOut = Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_SERVER_DISTRIBUTION_MANUAL_BOOK_OUT);

		if (axsf instanceof AxSfOut && !distributionManualBookOut) {
			distPerms[5] = new Integer(0);
		}
		int perm = SecuritySession.getScrPermission(useCaseConf.getSessionID(),
				bookID)[4];
		boolean canModify = SecuritySession.canModify(useCaseConf
				.getSessionID(), bookID);
		boolean canOpenReg = SecuritySession.canOpenCloseReg(useCaseConf
				.getSessionID());
		boolean canOperationIR = SecuritySession.canOperationIR(useCaseConf.getSessionID());

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		AxSfQueryResults queryResults = FolderSession.getLastRegisterForUser(
				useCaseConf.getSessionID(), bookID, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());

		if (queryResults.getTotalQuerySize() > 0) {
			Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
					.getSessionID(), bookID);
			FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

			assignValidationFields(axsf, queryResults, useCaseConf, bookID);
			return XMLTableBook.createXMLTable(axsf, queryResults, useCaseConf
					.getLocale(), autoDist, distPerms[5], canModify
					&& (perm == 1), canOpenReg, canOperationIR, sessionInformation
					.getCaseSensitive(), null, fieldFormat);
		} else {
			return null;
		}
	}

	public int getFrmNavigateFolderResults(UseCaseConf useCaseConf,
			Integer archiveId, Integer index) throws ValidationException,
			BookException, AttributesException, SessionException,
			SecurityException {
		AxSfQueryResults queryResults = FolderSession
				.navigateToRowRegistersQuery(useCaseConf.getSessionID(),
						archiveId, index.intValue(), useCaseConf.getLocale(),
						useCaseConf.getEntidadId(), null);
		AxSf axsf = null;
		axsf = (AxSf) queryResults.getResults().iterator().next();
		return Integer.parseInt(axsf.getAttributeValue("fdrid").toString());
	}

	public Document getTableResults(UseCaseConf useCaseConf, Integer archiveId,
			String navigationType, String orderByTable)
			throws ValidationException, BookException, AttributesException,
			SessionException, SecurityException {
		AxSf axsf = BookSession.getTableFormat(useCaseConf.getSessionID(),
				archiveId, useCaseConf.getEntidadId());
		Integer autoDist = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getAutoDist());
		Integer distPerms[] = SecuritySession.getScrDistPermission(useCaseConf
				.getSessionID());

		boolean distributionManualBookOut = Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_SERVER_DISTRIBUTION_MANUAL_BOOK_OUT);

		if (axsf instanceof AxSfOut && !distributionManualBookOut) {
			distPerms[5] = new Integer(0);
		}
		int perm = SecuritySession.getScrPermission(useCaseConf.getSessionID(),
				archiveId)[4];
		boolean canModify = SecuritySession.canModify(useCaseConf
				.getSessionID(), archiveId);
		boolean canOpenReg = SecuritySession.canOpenCloseReg(useCaseConf
				.getSessionID());
		boolean canOperationIR = SecuritySession.canOperationIR(useCaseConf.getSessionID());

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		AxSfQueryResults queryResults = FolderSession.navigateRegistersQuery(
				useCaseConf.getSessionID(), archiveId, navigationType,
				useCaseConf.getLocale(), useCaseConf.getEntidadId(),
				orderByTable);

		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), archiveId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		assignValidationFields(axsf, queryResults, useCaseConf, archiveId);
		return XMLTableBook
				.createXMLTable(axsf, queryResults, useCaseConf.getLocale(),
						autoDist, distPerms[5], canModify && (perm == 1),
						canOpenReg, canOperationIR, sessionInformation.getCaseSensitive(),
						orderByTable, fieldFormat);
	}

	public Document getTableResultsForRow(UseCaseConf useCaseConf,
			Integer archiveId, int row, String orderByTable)
			throws ValidationException, BookException, AttributesException,
			SessionException, SecurityException {
		AxSf axsf = BookSession.getTableFormat(useCaseConf.getSessionID(),
				archiveId, useCaseConf.getEntidadId());
		Integer autoDist = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getAutoDist());
		Integer distPerms[] = SecuritySession.getScrDistPermission(useCaseConf
				.getSessionID());
		if (axsf instanceof AxSfOut) {
			distPerms[5] = new Integer(0);
		}
		int perm = SecuritySession.getScrPermission(useCaseConf.getSessionID(),
				archiveId)[4];
		boolean canModify = SecuritySession.canModify(useCaseConf
				.getSessionID(), archiveId);
		boolean canOpenReg = SecuritySession.canOpenCloseReg(useCaseConf
				.getSessionID());

		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		AxSfQueryResults queryResults = FolderSession
				.navigateToRowRegistersQuery(useCaseConf.getSessionID(),
						archiveId, row, useCaseConf.getLocale(), useCaseConf
								.getEntidadId(), orderByTable);

		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), archiveId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		assignValidationFields(axsf, queryResults, useCaseConf, archiveId);
		return XMLTableBook
				.createXMLTable(axsf, queryResults, useCaseConf.getLocale(),
						autoDist, distPerms[5], canModify && (perm == 1),
						canOpenReg, sessionInformation.getCaseSensitive(),
						orderByTable, fieldFormat);
	}

	public Document getQueryFormatReports(UseCaseConf useCaseConf,
			Integer archiveId, long archivePId, long fdrqrypid, int opcion)
			throws ValidationException, BookException, AttributesException,
			SessionException, SecurityException {

		openBook(useCaseConf, archiveId);

		Document doc = null;
		int perms = 0;
		String dataBaseType = null;

		AxSf axsf = BookSession.getQueryFormat(useCaseConf.getSessionID(),
				archiveId, useCaseConf.getEntidadId());
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), archiveId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());
		String archiveName = BookSession.getBookName(
				useCaseConf.getSessionID(), archiveId, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());

		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), archiveId, axsf, true, useCaseConf
						.getLocale(), useCaseConf.getEntidadId());
		try {
			dataBaseType = BookSession.getDataBaseType(useCaseConf
					.getSessionID());
		} catch (Exception e) {
		}

		List bookIds = new ArrayList();
		// ScrRegstate scrregstate = null;

		if (axsf instanceof AxSfIn) {
			List inList = BookSession.getInBooks(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			for (Iterator it = inList.iterator(); it.hasNext();) {
				// scrregstate = (ScrRegstate) it.next();
				// if (opcion == 2) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// } else {
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				// }
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				if (opcion == 2
						|| scrRegStateByLanguage.getScrregstateState() == 0) {
					bookIds.add(scrRegStateByLanguage.getIdocarchhdrId());
				}
			}
		} else {
			List outList = BookSession.getOutBooks(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			for (Iterator it = outList.iterator(); it.hasNext();) {
				// scrregstate = (ScrRegstate) it.next();
				// if (opcion == 2) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// } else {
				// if (scrregstate.getState() == 0) {
				// bookIds.add(scrregstate.getIdocarchhdr().getId());
				// }
				// }
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				if (opcion == 2
						|| scrRegStateByLanguage.getScrregstateState() == 0) {
					bookIds.add(scrRegStateByLanguage.getIdocarchhdrId());
				}
			}
		}

		Map fieldsNotEqual = BookSession
				.compareBooks(useCaseConf.getSessionID(), bookIds, archiveId,
				useCaseConf.getEntidadId());

		//obtenemos los permisos del usuario sobre el libro
		perms = getPermisosLibro(useCaseConf, archiveId);

		boolean isBookAdmin = SecuritySession.isBookAdmin(useCaseConf
				.getSessionID(), archiveId);
		List validationFields = AttributesSession
				.getExtendedValidationFieldsForBook(useCaseConf.getSessionID(),
						archiveId, useCaseConf.getEntidadId());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());


		doc = XMLQueryBook.createXMLQueryFormat(axsf, fieldFormat, archiveId,
				archivePId, fdrqrypid, archiveName, useCaseConf.getLocale(),
				isBookAdmin, perms, validationFields, fieldsNotEqual, 0,
				usrConf, dataBaseType, sessionInformation);

		return doc;
	}

	/**
	 * Funcion que obtiene los permisos del usuario sobre un libro
	 *
	 * @param useCaseConf - Datos del usuario
	 * @param archiveId - ID del libro
	 * @return int - Permisos
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 */
	public int getPermisosLibro(UseCaseConf useCaseConf, Integer archiveId) throws ValidationException, SecurityException {
		int perms = 0;

		boolean CanQuery = SecuritySession.canQuery(useCaseConf.getSessionID(),
				archiveId);
		boolean CanCreate = SecuritySession.canCreate(useCaseConf
				.getSessionID(), archiveId);
		boolean canModify = SecuritySession.canModify(useCaseConf
				.getSessionID(), archiveId);

		perms += ((CanQuery) ? MASK_PERM_QUERY : 0);
		perms += ((CanCreate) ? MASK_PERM_CREATE : 0);
		perms += ((canModify) ? MASK_PERM_MODIFY : 0);

		return perms;
	}



	public Document getQueryFormat(UseCaseConf useCaseConf, Integer archiveId,
			long archivePId, long fdrqrypid) throws ValidationException,
			BookException, AttributesException, SessionException,
			SecurityException {

		openBook(useCaseConf, archiveId);

		int sizeIncompletRegiters = 0;
		String dataBaseType = null;
		// Integer alarmOnIncompletRegiters = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_BOOK_ALARM_INCOMPLET_REGISTER));
		Integer alarmOnIncompletRegiters = new Integer(BookSession
				.invesicresConf(useCaseConf.getEntidadId())
				.getAlarmOnIncompleteReg());
		if (alarmOnIncompletRegiters.intValue() != 0) {
			sizeIncompletRegiters = FolderSession.getIncompletRegisterSize(
					useCaseConf.getSessionID(), archiveId, useCaseConf
							.getEntidadId());
		}
		try {
			dataBaseType = BookSession.getDataBaseType(useCaseConf
					.getSessionID());
		} catch (Exception e) {
		}
		Document doc = null;
		int perms = 0;
		/*
		 * AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
		 * archiveId);
		 */

		AxSf axsf = BookSession.getQueryFormat(useCaseConf.getSessionID(),
				archiveId, useCaseConf.getEntidadId());
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), archiveId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());
		boolean CanQuery = SecuritySession.canQuery(useCaseConf.getSessionID(),
				archiveId);
		boolean CanCreate = SecuritySession.canCreate(useCaseConf
				.getSessionID(), archiveId);
		boolean canModify = SecuritySession.canModify(useCaseConf
				.getSessionID(), archiveId);
		boolean isBookAdmin = SecuritySession.isBookAdmin(useCaseConf
				.getSessionID(), archiveId);
		List validationFields = AttributesSession
				.getExtendedValidationFieldsForBook(useCaseConf.getSessionID(),
						archiveId, useCaseConf.getEntidadId());
		String archiveName = BookSession.getBookName(
				useCaseConf.getSessionID(), archiveId, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		UserConf usrConf = BookSession.getUserConfig(
				useCaseConf.getSessionID(), archiveId, axsf, true, useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		perms += ((CanQuery) ? MASK_PERM_QUERY : 0);
		perms += ((CanCreate) ? MASK_PERM_CREATE : 0);
		perms += ((canModify) ? MASK_PERM_MODIFY : 0);

		doc = XMLQueryBook.createXMLQueryFormat(axsf, fieldFormat, archiveId,
				archivePId, fdrqrypid, archiveName, useCaseConf.getLocale(),
				isBookAdmin, perms, validationFields, null,
				sizeIncompletRegiters, usrConf, dataBaseType,
				sessionInformation);

		return doc;
	}

	public void openBook(UseCaseConf useCaseConf, Integer bookID)
			throws ValidationException, BookException, SessionException {
		BookSession.openBook(useCaseConf.getSessionID(), bookID, useCaseConf
				.getEntidadId());
	}

	public void closeBook(UseCaseConf useCaseConf, Integer bookID)
			throws ValidationException, BookException, SessionException {
		BookSession.closeBook(useCaseConf.getSessionID(), bookID);
	}

	public void closeFolder(UseCaseConf useCaseConf, Integer bookID, int fdrid)
			throws ValidationException, BookException, SessionException {
		FolderSession.closeFolder(useCaseConf.getSessionID(), bookID, fdrid,
				useCaseConf.getEntidadId());
	}

	/**
	 * Metodo que obtiene el XML con el historial de distribucion de un registro
	 * @param useCaseConf
	 * @param bookID
	 * @param folderID
	 * @return
	 * @throws TecDocException
	 */
	public Document getDtrFdrResults(UseCaseConf useCaseConf, Integer bookID,
			int folderID) throws BookException, SessionException,
			ValidationException {
		//obtenemos el historial de distribucion
		List list = DistributionSession.getDtrFdrResults(useCaseConf
				.getSessionID(), bookID, folderID, useCaseConf.getEntidadId(),
				useCaseConf.getUseLdap().booleanValue());

		//generamos el xml
		return XMLDistReg.createXMLDistReg(list, bookID, folderID, useCaseConf
				.getLocale());
	}

	/**
	 * Metodo que obtiene un XML con el historial de distribucion de un registro
	 * además del motivo de cada distribucion
	 *
	 * @param useCaseConf
	 * @param bookID
	 * @param folderID
	 * @param isShowRegister
	 * @return
	 * @throws TecDocException
	 */
	public Document getDtrFdrResultsWithRemarkDistribution(UseCaseConf useCaseConf, Integer bookID,
			int folderID) throws BookException, SessionException,
			ValidationException {
		//obtenemos el historial de distribucion
		List list = DistributionSession.getDtrFdrResults(useCaseConf
				.getSessionID(), bookID, folderID, useCaseConf.getEntidadId(),
				useCaseConf.getUseLdap().booleanValue());

		//obtenemos el nombre del libro
		String bookName = BookSession.getBookName(
				useCaseConf.getSessionID(), bookID, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());

		//obtenemos la informacion del registro
		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
				bookID, folderID, useCaseConf.getLocale(), useCaseConf.getEntidadId());

		String numReg =  axsf.getAttributeValueAsString("fld1");

		//generamos el xml
		return XMLDistReg.createXMLDistRegWithRemarkDistribution(list, bookID,
				folderID, useCaseConf.getLocale(), bookName, numReg);
	}

	public Document getUpdHisFdrResults(UseCaseConf useCaseConf,
			Integer bookID, int folderId, AxSf axsf, String num_reg)
			throws BookException, SessionException, ValidationException {
		List list = FolderHistSession.getUpdHisFdrResults(useCaseConf
				.getSessionID(), useCaseConf.getLocale(), bookID, folderId,
				axsf, num_reg, useCaseConf.getEntidadId());
		return XMLUpdHisReg.createXMLUpdHisReg(list, bookID, folderId, num_reg,
				useCaseConf.getLocale());
	}

	public Document getOrigDocFdrBad(UseCaseConf useCaseConf, int openType,
			Integer bookID, int folderID, List datas) throws BookException,
			SessionException, SecurityException, ValidationException {
		int canAdd = 0;
		int canDel = 0;

		if (openType == 1) {
			// Comprobar si el libro estï¿½ cerrado
			canAdd = 1;
			canDel = 1;
		} else {
			boolean canModify = SecuritySession.canModify(useCaseConf
					.getSessionID(), bookID);
			boolean IsBookAdmin = SecuritySession.isBookAdmin(useCaseConf
					.getSessionID(), bookID);
			int[] perms = SecuritySession.getScrPermission(useCaseConf
					.getSessionID(), bookID);

			// Comprobar si el libro estï¿½ cerrado
			canAdd = ((perms[4] == 1) && canModify) ? 1 : 0;
			canDel = IsBookAdmin ? 1 : 0;
		}

		List list = datas;
		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
				bookID, folderID, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		int type = 1;

		if (axsf instanceof AxSfOut) {
			type = 2;
		}

		return XMLOrigDocFdr.createXMLOrigDocFdr(list, type, canAdd, canDel,
				axsf, useCaseConf.getLocale(), true);
	}

	public Document getOrigDocFdr(UseCaseConf useCaseConf, int openType,
			Integer bookID, int folderID) throws BookException,
			SessionException, SecurityException, ValidationException {
		int canAdd = 0;
		int canDel = 0;

		if (openType == 1) {
			// Comprobar si el libro estï¿½ cerrado
			canAdd = 1;
			canDel = 1;
		} else {
			boolean canModify = SecuritySession.canModify(useCaseConf
					.getSessionID(), bookID);
			boolean IsBookAdmin = SecuritySession.isBookAdmin(useCaseConf
					.getSessionID(), bookID);
			int[] perms = SecuritySession.getScrPermission(useCaseConf
					.getSessionID(), bookID);

			// Comprobar si el libro estï¿½ cerrado
			canAdd = ((perms[4] == 1) && canModify) ? 1 : 0;
			canDel = IsBookAdmin ? 1 : 0;
		}

		List list = FolderFileSession.getOrigDocFdr(useCaseConf.getSessionID(),
				bookID, folderID, useCaseConf.getEntidadId());
		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
				bookID, folderID, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		int type = 1;

		if (axsf instanceof AxSfOut) {
			type = 2;
		}

		return XMLOrigDocFdr.createXMLOrigDocFdr(list, type, canAdd, canDel,
				axsf, useCaseConf.getLocale(), false);
	}

	public Document getAsocRegsFdr(UseCaseConf useCaseConf, Integer bookID,
			int folderID) throws BookException, SessionException,
			SecurityException, ValidationException {
		Object[] result = FolderAsocSession.getAsocRegsFdr(useCaseConf
				.getSessionID(), bookID, folderID, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());
		Map idocs = (Map) result[0];
		Map axsfs = (Map) result[1];
		Map axsfPrim = (Map) result[2];
		return XMLAsocRegsFdr.createXMLAsocRegsFdr(idocs, axsfs, axsfPrim,
				useCaseConf.getLocale());
	}

	/**
	 *
	 * @param useCaseConf
	 * @return
	 * @throws Exception
	 */
	public Document getAsocRegsSearch(UseCaseConf useCaseConf) throws Exception {
		String dataBaseType = BookSession.getDataBaseType(useCaseConf
				.getSessionID());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		return XMLAsocRegsSearch.getXMLAsocRegsSearchList(useCaseConf
				.getLocale(), dataBaseType, sessionInformation
				.getCaseSensitive());
	}

	/**
	 *
	 * @param useCaseConf
	 * @param regWhere
	 * @param inicio
	 * @param registerBook
	 * @param folderId
	 * @param archiveId
	 * @return
	 * @throws Exception
	 */
	public String getValidateAsocRegsSearch(UseCaseConf useCaseConf,
			String regWhere, Integer inicio, String registerBook,
			Integer folderId, Integer archiveId) throws Exception {

		Integer bookIdSelect = new Integer(registerBook);
		// Integer rango = Integer
		// .valueOf(Configurator
		// .getInstance()
		// .getProperty(
		// ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_VALIDATION_LIST_SIZE));

		String dataBaseType = BookSession.getDataBaseType(useCaseConf
				.getSessionID());

		BookSession.openBook(useCaseConf.getSessionID(), bookIdSelect,
				useCaseConf.getEntidadId());

		AxSf axsfQ = BookSession.getQueryFormat(useCaseConf.getSessionID(),
				bookIdSelect, useCaseConf.getEntidadId());

		// aplicamos un orden para la consulta
		String orderBy = new String("FLD1 ASC");

		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());

		AxSfQuery axSfQuery = BookUseCaseAsocRegsUtil
				.getAsocRegsSearchCriteria(useCaseConf, regWhere, useCaseConf
						.getLocale(), dataBaseType, bookIdSelect, formatter);

		if (axSfQuery.getPageSize() <= 0) {
			throw new BookException(BookException.ERROR_PAGE_SIZE);
		}
		int size = FolderSession.openRegistersQuery(useCaseConf.getSessionID(),
				axSfQuery, null, new Integer(0), useCaseConf.getEntidadId());

		AxSfQueryResults queryResults = null;
		boolean isRegCurrentAsoc = false;
		if (size > 0) {
			queryResults = FolderSession.navigateRegistersQuery(useCaseConf
					.getSessionID(), bookIdSelect,
					com.ieci.tecdoc.common.isicres.Keys.QUERY_ALL, useCaseConf
							.getLocale(), useCaseConf.getEntidadId(), orderBy);

			if (BookUseCaseAsocRegsUtil.isAsocReg(useCaseConf, folderId,
					archiveId)) {
				isRegCurrentAsoc = true;
				queryResults = BookUseCaseAsocRegsUtil
						.filterRegsResultsByAsocRegs(queryResults, useCaseConf,
								bookIdSelect);
			}
		}

		List listaRegs = null;
		if (queryResults != null) {
			listaRegs = BookUseCaseAsocRegsUtil.getAsocRegsResults(queryResults
					.getResults(), queryResults.getBookId(), useCaseConf
					.getLocale());

			if (!isRegCurrentAsoc) {
				listaRegs = BookUseCaseAsocRegsUtil.filterRegsResultByCurrent(
						listaRegs, archiveId, folderId);
			}
		}

		Document doc = XMLAsocRegsResults.createXMLAsocRegsResult(listaRegs,
				inicio.intValue(), size);

		Document xmlDoc = XMLAsocRegsResults.createXMLValidateAsocRegsResult(
				doc, "SearchAsocRegs", inicio.intValue(), size, useCaseConf
						.getLocale(), registerBook, regWhere);

		// Document xmlDocument = XMLTypeAdm
		// .createXMLValidateIntListActionForm(doc, "ValidateInt",
		// inicio.intValue(), rango.intValue(), "",
		// new Integer(9).intValue(), useCaseConf.getLocale());
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(xmlDoc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_ASOCREGS_VALIDATED_SEARCH);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	/**
	 *
	 * @param useCaseConf
	 * @param bookId
	 * @return
	 * @throws Exception
	 */
	public String getValidatedAsocRegsBook(UseCaseConf useCaseConf,
			String bookIdString) throws Exception {
		String code = "10";

		try {
			Integer bookId = new Integer(bookIdString);
			if (!BookUseCaseAsocRegsUtil.isBookOpen(useCaseConf, bookId)) {
				code = "5";
			}
		} catch (Exception ex) {
			code = "5";
		}

		Document doc = XMLAsocRegsValidate.createXMLAsocRegsValidate(code, "");

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_ASOCREGS_VALIDATED_SELECTED);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	/**
	 * Validamos el registro actual respecto a los registros seleccionados para
	 * comprobar si se puede realizar la asociacion.
	 *
	 * @param useCaseConf
	 * @param asocRegsSelected
	 * @param folderId
	 * @param archiveId
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public String getValidateAsocRegsSelected(UseCaseConf useCaseConf,
			String asocRegsSelected, Integer folderId, Integer archiveId)
			throws BookException, SessionException, ValidationException {

		String code = "-1";

		if (BookUseCaseAsocRegsUtil.isAsocReg(useCaseConf, folderId, archiveId)) {
			/*
			 * Si el registro ya esta asociado a otros registros, al realizar la
			 * busqueda solo se muestran los registros que no estan asociados a
			 * ningun otro registro, por lo que la asociacion es valida
			 */
			code = "0";
		} else {
			/*
			 * Si el registro no esta asociado a ningun registro se muestran
			 * todos los registros que coinciden con los criterios de busqueda.
			 */
			String codeAux = BookUseCaseAsocRegsUtil.getAsocRegsSelectedCode(
					asocRegsSelected, useCaseConf);

			if (codeAux != null && codeAux.length() > 0) {
				code = codeAux;
			}
		}

		Document doc = XMLAsocRegsValidate.createXMLAsocRegsValidate(code,
				asocRegsSelected);

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_ASOCREGS_VALIDATED_SELECTED);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	/**
	 *
	 * @param useCaseConf
	 * @param asocRegsSelected
	 * @return
	 * @throws BookException
	 */
	public String getPrimaryAsocRegsSelected(UseCaseConf useCaseConf,
			String asocRegsSelected) throws BookException {
		List listaRegsSelected = BookUseCaseAsocRegsUtil.getAsocRegsResults(
				asocRegsSelected, useCaseConf.getLocale());

		Document doc = XMLAsocRegsResults.createXMLAsocRegsResult(
				listaRegsSelected, 0, 15);

		Document xmlDoc = XMLAsocRegsResults
				.createXMLValidateAsocRegsResult(doc, "SearchAsocRegs", 0, 15,
						useCaseConf.getLocale(), " ", " ");

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(xmlDoc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_ASOCREGS_VALIDATED_SEARCH);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	/**
	 * Asociamos los registros seleccionados
	 *
	 * @param useCaseConf
	 * @param asocRegsSelected
	 * @param code
	 * @param bookId
	 * @param folderId
	 * @return
	 * @throws BookException
	 */
	public String saveAsocRegs(UseCaseConf useCaseConf,
			String asocRegsSelected, String primaryReg, int code,
			Integer bookId, Integer folderId) throws BookException {
		Document doc = null;
		String resultCode = "10";
		switch (code) {
		case 0:
			try {
				Integer[] primario = BookUseCaseAsocRegsUtil
						.getAsocRegPrimario(useCaseConf, bookId, folderId);

				if (primario != null) {
					List listaRegs = BookUseCaseAsocRegsUtil
							.getAsocRegsResults(asocRegsSelected, useCaseConf
									.getLocale());

					if (listaRegs != null && !listaRegs.isEmpty()) {
						BookUseCaseAsocRegsUtil.saveAsocRegsSec(useCaseConf,
								listaRegs, primario[0], primario[1]);
					}
				}
			} catch (Exception e) {
				resultCode = "-1";
			}
			break;
		case 1:
			try {
				List listaRegs = BookUseCaseAsocRegsUtil.getAsocRegsResults(
						asocRegsSelected, useCaseConf.getLocale());

				if (listaRegs != null && !listaRegs.isEmpty()) {
					Integer[] primario = BookUseCaseAsocRegsUtil
							.getAsocRegPrimario(useCaseConf, listaRegs);

					if (primario != null) {
						BookUseCaseAsocRegsUtil.saveAsocRegsSec(useCaseConf,
								primario[0], primario[1], bookId, folderId);
					}
				}

			} catch (Exception e) {
				resultCode = "-1";
			}
			break;
		case 2:
			try {
				AsocRegsResults regCurrent = new AsocRegsResults(bookId,
						folderId, "", "", "");

				List listaRegs = BookUseCaseAsocRegsUtil.getAsocRegsResults(
						asocRegsSelected, useCaseConf.getLocale());

				if (listaRegs == null) {
					listaRegs = new ArrayList();
				}
				if (!listaRegs.isEmpty()) {
					Integer[] primario = BookUseCaseAsocRegsUtil
							.getAsocRegPrimario(useCaseConf, listaRegs);

					listaRegs = BookUseCaseAsocRegsUtil.getNoAsocRegsResults(
							useCaseConf, listaRegs);

					listaRegs.add(regCurrent);

					if (primario != null) {
						BookUseCaseAsocRegsUtil.saveAsocRegsSec(useCaseConf,
								listaRegs, primario[0], primario[1]);
					}
				}

			} catch (Exception e) {
				resultCode = "-1";
			}
			break;
		case 3:
			try {
				List listaRegs = BookUseCaseAsocRegsUtil.getAsocRegsResults(
						asocRegsSelected, useCaseConf.getLocale());

				if (listaRegs != null && !listaRegs.isEmpty()) {
					AsocRegsResults asocRegsResults = (AsocRegsResults) listaRegs
							.get(0);

					BookUseCaseAsocRegsUtil.saveAsocRegsSec(useCaseConf,
							asocRegsResults.getBookId(), asocRegsResults
									.getFolderId(), bookId, folderId);
				}

			} catch (Exception e) {
				resultCode = "-1";
			}
			break;
		case 4:
			try {
				List listaPrimaryRegs = BookUseCaseAsocRegsUtil
						.getAsocRegsResults(primaryReg, useCaseConf.getLocale());

				if (listaPrimaryRegs != null && !listaPrimaryRegs.isEmpty()) {
					AsocRegsResults asocRegsResultsPrimary = (AsocRegsResults) listaPrimaryRegs
							.get(0);

					List listaRegs = BookUseCaseAsocRegsUtil
							.getAsocRegsResults(asocRegsSelected, useCaseConf
									.getLocale());

					listaRegs = BookUseCaseAsocRegsUtil
							.filterRegsResultByCurrent(listaRegs,
									asocRegsResultsPrimary.getBookId(),
									asocRegsResultsPrimary.getFolderId());

					if (listaRegs == null) {
						listaRegs = new ArrayList();
					}

					AsocRegsResults regCurrent = new AsocRegsResults(bookId,
							folderId, "", "", "");

					listaRegs.add(regCurrent);

					BookUseCaseAsocRegsUtil.saveAsocRegsSec(useCaseConf,
							listaRegs, asocRegsResultsPrimary.getBookId(),
							asocRegsResultsPrimary.getFolderId());

				}

			} catch (Exception e) {
				resultCode = "-1";
			}
			break;
		default:
			break;
		}

		doc = XMLAsocRegsValidate.createXMLAsocRegsValidate(resultCode,
				asocRegsSelected);

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_ASOCREGS_VALIDATED_SELECTED);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;

	}

	/**
	 * Eliminamos un registro asociado
	 *
	 * @param useCaseConf
	 * @param bookId
	 * @param folderId
	 * @return
	 * @throws Exception
	 */
	public String deleteAsocRegs(UseCaseConf useCaseConf, Integer bookId,
			Integer folderId) throws Exception {
		BookUseCaseAsocRegsUtil
				.deleteAsocRegsSec(useCaseConf, bookId, folderId);

		Document doc = XMLAsocRegsValidate.createXMLAsocRegsValidate("10", "");

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			throw new BookException(
					BookException.ERROR_ASOCREGS_VALIDATED_SELECTED);
		}
		String xml = writer.toString();

		if (_logger.isDebugEnabled()) {
			_logger.debug(xml);
		}

		return xml;
	}

	public Integer vldInterSave(UseCaseConf useCaseConf, String strDoc,
			String strName, String strApe1, String strApe2, int strTipoDoc,
			String strDirecciones, String strDireccionesTel, int strTipoPer,
			Integer idPerson) throws ValidationException, SecurityException,
			AttributesException, BookException, SessionException {
		if (idPerson.intValue() == 0) {
			int id = UtilsSessionEx.addScrPfiPjur(useCaseConf.getSessionID(),
					strDoc, strName, strApe1, strApe2, strTipoDoc,
					strDirecciones, strDireccionesTel, strTipoPer, idPerson,
					useCaseConf.getEntidadId());
			return new Integer(id);
		} else {
			UtilsSessionEx.updateScrPfiPjur(useCaseConf.getSessionID(), strDoc,
					strName, strApe1, strApe2, strTipoDoc, strDirecciones,
					strDireccionesTel, strTipoPer, idPerson, useCaseConf
							.getEntidadId());
			return idPerson;
		}
	}

	public Document saveOrigDocs(UseCaseConf useCaseConf, Integer bookID,
			Integer folderID, String datas, Integer openType)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException {
		SaveOrigDocDataDocInput doc = null;
		SaveOrigDocDatasInput datasInput = new SaveOrigDocDatasInput(datas);
		datasInput.analize();
		FolderFileSession.saveOrigDoc(useCaseConf.getSessionID(), bookID,
				folderID.intValue(), datasInput, useCaseConf.getLocale(),
				useCaseConf.getEntidadId());
		boolean badProc = false;
		List datasOutput = new ArrayList();
		for (Iterator it = datasInput.getDocs().values().iterator(); it
				.hasNext();) {
			doc = (SaveOrigDocDataDocInput) it.next();
			datasOutput.add(doc);
			if (doc.getProcId().intValue() < 0) {
				badProc = true;
			}
		}
		if (badProc) {
			return getOrigDocFdrBad(useCaseConf, openType.intValue(), bookID,
					folderID.intValue(), datasOutput);
		} else {
			return getOrigDocFdr(useCaseConf, openType.intValue(), bookID,
					folderID.intValue());
		}

		// return getOrigDocFdr(useCaseConf, openType.intValue(), bookID,
		// folderID.intValue());
	}

	public void savePersistFields(UseCaseConf useCaseConf, String cadena)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException {
		BookSession.savePersistFields(useCaseConf.getSessionID(), cadena,
				useCaseConf.getEntidadId());
	}

	public String getPersistFields(UseCaseConf useCaseConf, Integer bookId)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException {
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());

		return BookSession.getPersistFields(useCaseConf.getSessionID(), bookId,
				axsfQ, useCaseConf.getLocale(), useCaseConf.getEntidadId());
	}

	public Document getFieldsForPersistence(UseCaseConf useCaseConf,
			Integer bookID) throws ValidationException, SecurityException,
			AttributesException, BookException, SessionException {
		int updateDate = SecuritySession.getScrPermission(useCaseConf
				.getSessionID(), bookID)[2];
		boolean includeDate = false;
		if (updateDate == 1) {
			includeDate = true;
		}
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookID, useCaseConf.getEntidadId());
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), bookID);
		return XMLFrmPersistFld.createXMLFrmPersistFld(idocarchdet, bookID,
				axsfQ, includeDate, useCaseConf.getLocale());
	}

	public byte[] getFile(UseCaseConf useCaseConf, Integer bookId,
			Integer regId, Integer docId, Integer pageId)
			throws ValidationException, BookException, SessionException {
		return FolderFileSession.getFile(useCaseConf.getSessionID(), bookId,
				regId, pageId, useCaseConf.getEntidadId());
	}

	public List validateFolder(UseCaseConf useCaseConf, Integer bookId,
			int fdrid, List files, List atts, Map documents)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException {
		FlushFdrField flushFdrField = null;
		Map ctrlIds = new HashMap();
		for (Iterator it = atts.iterator(); it.hasNext();) {
			flushFdrField = (FlushFdrField) it.next();
			ctrlIds.put(new Integer(flushFdrField.getFldid()), new Integer(
					flushFdrField.getCtrlid()));
		}
		AxSf axsfQ = BookSession.getFormFormat(useCaseConf.getSessionID(),
				bookId, useCaseConf.getEntidadId());
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), bookId);
		Date dbDate = BookSession.getDBDateServer(useCaseConf.getSessionID(),
				useCaseConf.getEntidadId());
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		// obtenemos la fecha maxima de cierre de los registros
		Date dateMaxRegClose = BookSession.getMaxDateRegClose(useCaseConf
				.getSessionID(), useCaseConf.getEntidadId(), bookId);

		if (_logger.isDebugEnabled()) {
			_logger.debug(fieldFormat);
		}

		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}

		Map idsToValidate = new HashMap();
		List preResult = new ArrayList();
		List result = new ArrayList();
		SimpleDateFormat longFormatter = new SimpleDateFormat(RBUtil
				.getInstance(useCaseConf.getLocale()).getProperty(
						I18N_DATE_LONGFORMAT));
		longFormatter.setLenient(false);
		SimpleDateFormat shortFormatter = new SimpleDateFormat(RBUtil
				.getInstance(useCaseConf.getLocale()).getProperty(
						I18N_DATE_SHORTFORMAT));
		shortFormatter.setLenient(false);
		boolean dateError = fdrid != -1;
		AttributesSession.getExtendedValidationFieldsForBook(useCaseConf
				.getSessionID(), bookId, useCaseConf.getEntidadId());
		for (Iterator it = atts.iterator(); it.hasNext();) {
			flushFdrField = (FlushFdrField) it.next();

			if (flushFdrField.getValue() != null
					&& !flushFdrField.getValue().equals("")) {
				if (flushFdrField.getFldid() == 2) {
					try {
						if (_logger.isDebugEnabled()) {
							_logger
									.debug("****************FORMATEANDO FECHAS***************************");
							_logger.debug("longFormatter: "
									+ RBUtil.getInstance(
											useCaseConf.getLocale())
											.getProperty(I18N_DATE_LONGFORMAT));
							_logger.debug("value: " + flushFdrField.getValue());
						}
						Date date = null;
						if (flushFdrField.getValue().length() > 19) {
							dateError = true;
						} else {
							date = longFormatter
									.parse(flushFdrField.getValue());
						}
						if (_logger.isDebugEnabled()) {
							_logger.debug("date: " + date);
							_logger.debug("date.after(dbDate): "
									+ date.after(dbDate));
							_logger
									.debug("longFormatter.getCalendar().get(Calendar.YEAR): "
									+ longFormatter.getCalendar().get(
											Calendar.YEAR));
						}
						if (date.after(dbDate)
								|| longFormatter.getCalendar().get(
										Calendar.YEAR) < 1970
								|| shortFormatter.getCalendar().get(
										Calendar.YEAR) > 2040) {
							dateError = true;
						} else {
							//validamos la fecha de registro respecto a la fecha maxima de cierre
							if (!validateDateRegisterWithDateMaxClose(date,
									dateMaxRegClose)) {
								_logger.warn("Error en la validacion de Fecha Registro: La fecha del registro ["
										+ date
										+ "] es anterior a la fecha maxima de cierre ["
										+ dateMaxRegClose + "] de registros");
								dateError = true;
							} else {
								dateError = false;
							}
						}
						if (_logger.isDebugEnabled()) {
							_logger
									.debug("*******************************************");
						}
						// dateError = false;
					} catch (Exception e) {
						dateError = true;
					}
				} else if (flushFdrField.getFldid() == 11
						&& axsfQ instanceof AxSfIn) {
					try {
						Integer.parseInt(RBUtil.getInstance(
								useCaseConf.getLocale()).getProperty(
								"book.fld11." + flushFdrField.getValue()));
					} catch (Exception e) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} else if (flushFdrField.getFldid() == 4) {
					try {
						if (flushFdrField.getValue().length() > 10) {
							new Exception("Fecha mal formada");
						}
						shortFormatter.parse(flushFdrField.getValue());
					} catch (Exception e) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} else if (flushFdrField.getFldid() == 5
						|| flushFdrField.getFldid() == 7
						|| flushFdrField.getFldid() == 8) {
					idsToValidate.put(new Integer(flushFdrField.getFldid()),
							flushFdrField.getValue());
				} else if (axsfQ instanceof AxSfIn
						&& (flushFdrField.getFldid() == 12)) {
					try {
						if (flushFdrField.getValue().length() > 10) {
							new Exception("Fecha mal formada");
						}
						shortFormatter.parse(flushFdrField.getValue());
						if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
								|| shortFormatter.getCalendar().get(
										Calendar.YEAR) > 2040) {
							preResult
									.add(new Integer(flushFdrField.getFldid()));
						}
					} catch (Exception e) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} else if (axsfQ instanceof AxSfIn
						&& (flushFdrField.getFldid() == 13 || flushFdrField
								.getFldid() == 16)) {
					idsToValidate.put(new Integer(flushFdrField.getFldid()),
							flushFdrField.getValue());
				} else if (axsfQ instanceof AxSfOut
						&& (flushFdrField.getFldid() == 12)) {
					idsToValidate.put(new Integer(flushFdrField.getFldid()),
							flushFdrField.getValue());
				} else if (axsfQ instanceof AxSfIn
						&& (flushFdrField.getFldid() == 14)) {
					// if
					// (getValidationSRemote(useCaseConf).getOneScrtt(useCaseConf.getSessionID(),
					// flushFdrField.getValue()) == null) {
					// preResult.add(new Integer(flushFdrField.getFldid()));
					// }
				} else if (axsfQ instanceof AxSfOut
						&& (flushFdrField.getFldid() == 10)) {
					// if
					// (getValidationSRemote(useCaseConf).getOneScrtt(useCaseConf.getSessionID(),
					// flushFdrField.getValue()) == null) {
					// preResult.add(new Integer(flushFdrField.getFldid()));
					// }
				} else if (axsfQ instanceof AxSfIn
						&& flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
					if (!axsfQ.getProposedExtendedFields().contains(
							new Integer(flushFdrField.getFldid()))) {
						if (AttributesSession.getExtendedValidationFieldValue(
								useCaseConf.getSessionID(), bookId,
								flushFdrField.getFldid(), flushFdrField
										.getValue(), locale.getLanguage(),
								useCaseConf.getEntidadId()) != null) {
							// preResult.add(new
							// Integer(flushFdrField.getFldid()));
							checkValue(flushFdrField, shortFormatter,
									longFormatter, fieldFormat, preResult);
						} else {
							preResult
									.add(new Integer(flushFdrField.getFldid()));
						}
					}
				} else if (axsfQ instanceof AxSfOut
						&& flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
					if (!axsfQ.getProposedExtendedFields().contains(
							new Integer(flushFdrField.getFldid()))) {
						if (AttributesSession.getExtendedValidationFieldValue(
								useCaseConf.getSessionID(), bookId,
								flushFdrField.getFldid(), flushFdrField
										.getValue(), locale.getLanguage(),
								useCaseConf.getEntidadId()) != null) {
							// preResult.add(new
							// Integer(flushFdrField.getFldid()));
							checkValue(flushFdrField, shortFormatter,
									longFormatter, fieldFormat, preResult);
						} else {
							preResult
									.add(new Integer(flushFdrField.getFldid()));
						}
					}
				} else {
					if (flushFdrField.getFldid() != 6) {
						checkValue(flushFdrField, shortFormatter,
								longFormatter, fieldFormat, preResult);
					}
				}
			}
		}

		// validamos la fecha del registro
		// si el campo fecha no ha sido modificado - FldId = 2
		if (ctrlIds.get(new Integer(AxSf.FLD2_FIELD_ID)) == null) {
			Date date = null;
			AxSf axSf = null;

			if (fdrid != -1) {
				// si el registro es modificado y NO se ha modificado la
				// fecha de registro obtenemos el valor del campo almacenado
				// en BBDD
				axSf = FolderSession.getBookFolder(useCaseConf.getSessionID(), bookId,
						fdrid, locale, useCaseConf.getEntidadId());
				if (axSf.getAttributeValue(AxSf.FLD2_FIELD) != null) {
					date = ((Date) axSf.getAttributeValue(AxSf.FLD2_FIELD));
				}

				//validamos la fecha de registro respecto a la fecha maxima de cierre
				if (!validateDateRegisterWithDateMaxClose(date, dateMaxRegClose) && date != null) {
					//obtenemos la informacion del campo de fecha de registro FldId = 2
					FCtrlDef fechaRegistro = getCtrlDefByFldId(axsfQ, AxSf.FLD2_FIELD_ID);
					_logger.warn("Error en la validacion de Fecha Registro: La fecha del registro ["
							+ date
							+ "] es anterior a la fecha maxima de cierre ["
							+ dateMaxRegClose + "] de registros");
					// almacenamos el id del campo erroneo, en esta caso la
					// fecha de registro (Ejemplo Id = 1004)
					result.add(new Integer(fechaRegistro.getId()));
				}
			}
		}

		if (dateError) {
			preResult.add(new Integer(2));
		}
		if (!idsToValidate.isEmpty()) {
			preResult.addAll(AttributesSession.validateFixedValues(useCaseConf
					.getSessionID(), bookId, idsToValidate, true, useCaseConf
					.getEntidadId()));
		}
		for (Iterator it = preResult.iterator(); it.hasNext();) {
			Integer f = (Integer) it.next();
			Integer c = (Integer) ctrlIds.get(f);
			if (_logger.isDebugEnabled()) {
				_logger.debug("Añadiendo error en : " + f
						+ " para el control :" + c);
			}
			if (c != null) {
				result.add(c);
			}
		}
		return result;
	}

	/**
	 * Metodo que obtiene la informacion de un campo a partir de su FLDId
	 * @param {@link AxSf} - axsfQ
	 * @return {@link FCtrlDef}
	 */
	public FCtrlDef getCtrlDefByFldId(AxSf axsfQ, int fldId) {
		if (_logger.isDebugEnabled()) {
			_logger.debug("getCtrlDefByFldId: " + fldId);
		}
		FormFormat formFormat = new FormFormat(axsfQ.getFormat()
				.getData());
		// paginas que tiene el formulario de registro
		TreeMap pages = formFormat.getDlgDef().getPagedefs();

		// recorremos el array de paginas
		for (Iterator it = pages.values().iterator(); it.hasNext();) {
			FPageDef pageDef = (FPageDef) it.next();
			TreeMap ctrls = pageDef.getCtrldefs();
			FCtrlDef ctrlDef = null;
			for (Iterator it2 = ctrls.values().iterator(); it2.hasNext();) {
				// informacion del campo
				ctrlDef = (FCtrlDef) it2.next();

				if (ctrlDef.getFldId() == fldId) {
					if (_logger.isDebugEnabled()) {
						_logger.debug("getCtrlDefByFldId FldId: " + fldId + " ctrlDef: " + ctrlDef.toString());
					}
					// si el campo es de FldId igual al que se pasa por
					// parametro retornamos el valor
					return ctrlDef;
				}
			}
		}
		return null;
	}

	public int saveOrUpdateFolder(String idEntidad, String sessionId,
			Locale locale, Integer bookId, int fldid, List files, List atts,
			List inter, Map documents) throws ValidationException,
			SecurityException, AttributesException, BookException,
			SessionException, ParseException {

		/*
		 * String idEntidad=null; String sessionId=null; Locale locale=null;
		 */

		boolean updateDate = false;
		// Integer modifySystemDate = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_UPDATEDATE));
		// Integer launchDistOutRegister = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_DISTRIBUTION_LAUNCH_DIST_OUT_REGISTER));

		Integer modifySystemDate = new Integer(BookSession.invesicresConf(
				idEntidad).getModifySystemDate());
		Integer launchDistOutRegister = new Integer(BookSession.invesicresConf(
				idEntidad).getDistSRegister());
		if (modifySystemDate.intValue() == 1) {
			updateDate = true;
		}
		AxSf axsfQ = BookSession.getFormFormat(sessionId, bookId, idEntidad);
		Map idsToTranslate = new HashMap();
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(sessionId,
				bookId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());
		FlushFdrField flushFdrField = null;
		for (Iterator it = atts.iterator(); it.hasNext();) {
			flushFdrField = (FlushFdrField) it.next();
			if (flushFdrField.getFldid() == 5 || flushFdrField.getFldid() == 7
					|| flushFdrField.getFldid() == 8) {
				idsToTranslate.put(new Integer(flushFdrField.getFldid()),
						flushFdrField.getValue());
			}
			if (axsfQ instanceof AxSfIn
					&& (flushFdrField.getFldid() == 13 || flushFdrField
							.getFldid() == 16)) {
				idsToTranslate.put(new Integer(flushFdrField.getFldid()),
						flushFdrField.getValue());
			}
			if (axsfQ instanceof AxSfOut && (flushFdrField.getFldid() == 12)) {
				idsToTranslate.put(new Integer(flushFdrField.getFldid()),
						flushFdrField.getValue());
			}
		}
		Map translatedIds = AttributesSession
				.translateFixedValuesForSaveOrUpdate(sessionId, bookId,
						idsToTranslate, idEntidad);
		AxSf newAxSF = null;

		if (axsfQ instanceof AxSfIn) {
			newAxSF = new AxSfIn();
			// /////////////////////////////////////TODO
			// BookTypeConf/////////////////////////////////////////////////
			newAxSF.setLiteralBookType(RBUtil.getInstance(locale).getProperty(
					I18N_BOOKUSECASE_NODE_INBOOK_NAME));
			// //////////////////////////////////////////////////////////////////////////////////////
		} else {
			newAxSF = new AxSfOut();
			// /////////////////////////////////////TODO
			// BookTypeConf/////////////////////////////////////////////////
			newAxSF.setLiteralBookType(RBUtil.getInstance(locale).getProperty(
					I18N_BOOKUSECASE_NODE_OUTBOOK_NAME));
			// //////////////////////////////////////////////////////////////////////////////////////
		}
		Integer id = null;
		for (Iterator it = translatedIds.keySet().iterator(); it.hasNext();) {
			id = (Integer) it.next();
			newAxSF.addAttributeName("fld" + id.toString());
			newAxSF.addAttributeValue("fld" + id.toString(), translatedIds
					.get(id));
		}
		SimpleDateFormat longFormatter = new SimpleDateFormat(RBUtil
				.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));
		SimpleDateFormat shortFormatter = new SimpleDateFormat(RBUtil
				.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));
		for (Iterator it = atts.iterator(); it.hasNext();) {
			flushFdrField = (FlushFdrField) it.next();
			if (flushFdrField.getFldid() == 17 && axsfQ instanceof AxSfIn) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
			if ((flushFdrField.getFldid() == 14 || flushFdrField.getFldid() == 15)
					&& axsfQ instanceof AxSfIn) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
			if ((flushFdrField.getFldid() == 10 || flushFdrField.getFldid() == 11)
					&& axsfQ instanceof AxSfOut) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
			if (flushFdrField.getFldid() == 13 && axsfQ instanceof AxSfOut) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
			if (flushFdrField.getFldid() == 10 && axsfQ instanceof AxSfIn) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						flushFdrField.getValue());
			}
			if (flushFdrField.getFldid() == 11 && axsfQ instanceof AxSfIn) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				if (flushFdrField.getValue() != null) {
					newAxSF
							.addAttributeValue(
							"fld" + flushFdrField.getFldid(),
									new BigDecimal(
											RBUtil
													.getInstance(locale)
									.getProperty(
											"book.fld11."
																	+ flushFdrField
																			.getValue())));
				} else {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							flushFdrField.getValue());
				}
			}
			if (flushFdrField.getFldid() == 2) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				if (flushFdrField.getValue() != null) {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							longFormatter.parse(flushFdrField.getValue()));
				} else {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							flushFdrField.getValue());
				}
			}
			if (flushFdrField.getFldid() == 4) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				if (flushFdrField.getValue() != null) {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							shortFormatter.parse(flushFdrField.getValue()));
				} else {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							flushFdrField.getValue());
				}
			}
			if (axsfQ instanceof AxSfIn && (flushFdrField.getFldid() == 12)) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				if (flushFdrField.getValue() != null) {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							shortFormatter.parse(flushFdrField.getValue()));
				} else {
					newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
							flushFdrField.getValue());
				}
			}

			// Campos Extendidos
			getExtendsFields(axsfQ, fieldFormat,
					flushFdrField, newAxSF, longFormatter, shortFormatter);

			// Si es el campo estado, lo aï¿½adimos tal cual
			if (flushFdrField.getFldid() == 6
					&& flushFdrField.getValue() != null) {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						new Integer(flushFdrField.getValue()));
			}
		}
		newAxSF.setLocale(locale);

		if (fldid == -1) {
			return FolderSession.createNewFolder(sessionId, bookId, newAxSF,
					inter, launchDistOutRegister, locale, idEntidad);
		} else {
			FolderSession.updateFolder(sessionId, bookId, fldid, newAxSF,
					inter, documents, updateDate, launchDistOutRegister,
					locale, idEntidad);
			return fldid;
		}
	}

	/**
	 * Metodo que valida que la fecha de registro sea mayor que la fecha maxima
	 * de cierre, devolvemos TRUE si la validacion es correcta y
	 * FALSE si la fecha no pasa la validacion
	 *
 	 * @param date - Fecha de registro
	 * @param dateMaxRegClose - Fecha máxima de cierre de los registros
	 * @return boolean
	 * @throws BookException
	 */
	public boolean validateDateRegisterWithDateMaxClose(Date date,
			Date dateMaxRegClose) throws BookException {
		boolean result = true;
		if (_logger.isDebugEnabled()) {
			_logger.debug("Entra a validar fecha registro: validateDateRegisterWithDateMaxClose()");
		}
		//generamos un formato para las fechas yyyy-MM-dd HH:mm:ss
			SimpleDateFormat compareFormatter = new SimpleDateFormat(
					COMPARE_FORMATTER);

		if ((dateMaxRegClose != null) && (date != null)) {
			String strDateReg = compareFormatter.format(date);

			String strDateMaxClose = compareFormatter.format(dateMaxRegClose);

			if (_logger.isDebugEnabled()) {
				_logger.debug("Fecha del Registro: [" + strDateReg
						+ "] - Fecha Maxima de Cierre : [" + strDateMaxClose
						+ "]");
			}

			if (strDateReg.compareTo(strDateMaxClose) <= 0) {
				//el valor del campo fecha de registro es anterior a la fecha maxima de cierre
				result = false;
			} else {
				//la fecha de registro es posterior que la fecha maxima de cierre
				result = true;
			}
		}
		return result;
	}



	/**
	 * Metodo que obtiene los campos extendidos
	 * @param axsfQ
	 * @param fieldFormat
	 * @param flushFdrField
	 * @param newAxSF
	 * @param longFormatter
	 * @param shortFormatter
	 * @return
	 */
	public void getExtendsFields(AxSf axsfQ, FieldFormat fieldFormat,
			FlushFdrField flushFdrField, AxSf newAxSF,
			SimpleDateFormat longFormatter, SimpleDateFormat shortFormatter) {

		AxXf extendFiel = null;
		if ((axsfQ instanceof AxSfIn
				&& flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) ||
			(axsfQ instanceof AxSfOut
				&& flushFdrField.getFldid() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER)) {

			if (axsfQ.getProposedExtendedFields().contains(
					new Integer(flushFdrField.getFldid()))
					&& flushFdrField.getValue() == null) {
				flushFdrField.setValue(StringUtils.EMPTY);
			}
			//comprobamos si el campo es extendido lo insertamos en campos extendidos
			if (axsfQ.getProposedExtendedFields().contains(
					new Integer(flushFdrField.getFldid()))) {
				//Si es un Libro de Entrada y el campo ademas de ser extendido es el comentario creamos otro objeto AxXf
				if((axsfQ instanceof AxSfIn) && (flushFdrField.getFldid()==AxSf.FLD18_FIELD_ID)){
					// obtenemos el comentario
					AxXf axxf = fromFlushFdrFieldToAxXF(flushFdrField);
					newAxSF.setAxxf(axxf);
				}

				// obtenemos el campo extendido
				extendFiel = fromFlushFdrFieldToAxXF(flushFdrField);
				// añadimos campo extendido al map
				newAxSF.getExtendedFields().put(new Integer(extendFiel.getFldId()), extendFiel);
				// result.put(new Integer(extendFiel.getFldId()), extendFiel);

			} else {
				newAxSF.addAttributeName("fld" + flushFdrField.getFldid());
				newAxSF.addAttributeValue("fld" + flushFdrField.getFldid(),
						convertValue(flushFdrField, shortFormatter,
								longFormatter, fieldFormat));
			}
		}
	}

	/**
	 * Metodo que a partir de {@link FlushFdrField} obtiene un objeto {@link AxXf}
	 * @param flushFdrField
	 * @return {@link AxXf}
	 */
	public static AxXf fromFlushFdrFieldToAxXF(FlushFdrField flushFdrField) {
		AxXf result = null;

		result = new AxXf();
		result.setFdrId(flushFdrField.getCtrlid());
		result.setFldId(flushFdrField.getFldid());
		result.setId(flushFdrField.getFldid());
		result.setText(flushFdrField.getValue());

		return result;
	}

	public int saveOrUpdateFolder(UseCaseConf useCaseConf, Integer bookId,
			int fldid, List files, List atts, List inter, Map documents)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException, ParseException {

		String idEntidad = useCaseConf.getEntidadId();
		Locale locale = useCaseConf.getLocale();
		String sessionId = useCaseConf.getSessionID();

		return saveOrUpdateFolder(idEntidad, sessionId, locale, bookId, fldid,
				files, atts, inter, documents);

	}

	public String updateFieldOrg(UseCaseConf useCaseConf, Integer bookId,
			Integer fldid, String code, List listIdsRegister)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException, ParseException {

		FlushFdrField field = new FlushFdrField();
		field.setFldid(fldid.intValue());
		field.setValue(code);
		List atts = new ArrayList(1);
		atts.add(field);

		ScrOrg scrOrg = BookSession.preUpdateFieldOrg(useCaseConf
				.getSessionID(), bookId, code, listIdsRegister, useCaseConf
				.getEntidadId());

		Integer folderID = null;
		for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
			folderID = (Integer) it.next();
			saveOrUpdateFolder(useCaseConf, bookId, folderID.intValue(), null,
					atts, null, null);
		}

		List list = BookSession.postUpdateFieldOrg(useCaseConf.getSessionID(),
				bookId, listIdsRegister, useCaseConf.getLocale(), useCaseConf
						.getEntidadId());

		StringBuffer result = new StringBuffer();
		result.append(getScrOrgDescription(scrOrg));
		result.append("#");
		result.append(RBUtil.getInstance(useCaseConf.getLocale()).getProperty(
				"book.fld6.0"));
		result.append("#");
		for (Iterator it = list.iterator(); it.hasNext();) {
			result.append(it.next().toString());
			result.append("_");
		}

		return result.toString();
	}

	public void getPermsRegisterDistPen(UseCaseConf useCaseConf,
			Integer bookId, Integer fdrid) throws BookException,
			DistributionException, ValidationException, SessionException {
		AxSf axsf = FolderSession.getBookFolder(useCaseConf.getSessionID(),
				bookId, fdrid.intValue(), useCaseConf.getLocale(), useCaseConf
						.getEntidadId());
		String regNumberString = (String) axsf.getAttributeValue("fld1");
		AxSfQuery axsfQuery = new AxSfQuery();
		axsfQuery.setBookId(bookId);
		axsfQuery
				.setPageSize(Integer
						.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_TABLE_RESULTS_SIZE)));
		AxSfQueryField field = new AxSfQueryField();
		field.setFldId(XML_FLD_TEXT + 1);
		field.setValue(regNumberString);
		field
				.setOperator(com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE);
		axsfQuery.addField(field);
		boolean perms = false;
		int distResults = 0;
		distResults = DistributionSession.getDistribution(useCaseConf
				.getSessionID(), 1, bookId, fdrid, useCaseConf.getEntidadId());
		if (distResults == 0) {
			perms = false;
		} else {
			perms = true;
		}
		if (!perms) {
			throw new BookException(BookException.ERROR_CANNOT_SHOW_REGISTER);
		}
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/
	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private String getScrOrgDescription(ScrOrg org) {
		String text = "";
		if (org != null) {
			if (Configurator
					.getInstance()
					.getPropertyBoolean(
							ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_CODE)) {
				text = org.getCode();
			} else if (Configurator
					.getInstance()
					.getPropertyBoolean(
							ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_ABBV)) {
				text = org.getAcron();
			} else if (Configurator
					.getInstance()
					.getPropertyBoolean(
							ConfigurationKeys.KEY_DESKTOP_QUERYRESULTSTABLEREPRESENTATION_ADMINUNITS_NAME)) {
				text = org.getName();
			}
			if (text == null) {
				text = "";
			}
		}
		return text;
	}

	private String translateOperator(String operator, Locale locale) {

		String result = null;
		if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_NOT_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_GREATER_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_GREATER_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LESSER_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LESSER_EQUAL_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_BETWEEN_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_LIKE_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_OR_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_ABC_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_IN_AND_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_IN_OR_TEXT_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_TEXT_VALUE;
		} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_DEPEND_OF_VALUE))) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_DEPEND_OF_TEXT_VALUE;
		}
		return result;
	}

	private String translateOperator(int idOperator, Locale locale) {

		String result = null;
		if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_TEXT_VALUE;
		} else if (idOperator == com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_VALUE) {
			result = com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_TEXT_VALUE;
		} /*
		 * else if (operator.equals(RBUtil.getInstance(locale).getProperty(
		 * Keys.I18N_QUERY_DEPEND_OF_VALUE))) { result =
		 * com.ieci.tecdoc.common.isicres.Keys.QUERY_DEPEND_OF_TEXT_VALUE; }
		 */
		return result;
	}

	private String translateNexo(String nexo) {

		String result = AbstractDBEntityDAO.AND;

		if (StringUtils.isNotEmpty(nexo)) {
			if (nexo.equals(com.ieci.tecdoc.common.isicres.Keys.QUERY_NEXO_OR)) {
				result = AbstractDBEntityDAO.OR;
			} else if (nexo
					.equals(com.ieci.tecdoc.common.isicres.Keys.QUERY_NEXO_AND)) {
				result = AbstractDBEntityDAO.AND;
			}
		}

		return result;
	}

	private Object getValue(AxSf axsfQ, AxSfQueryField field, Map params,
			QCtrlDef ctrlDef, Map translations, Locale locale)
			throws ParseException {

		String param = getValue(params, Integer.toString(ctrlDef.getId()));

		String operator = getValue(params, Integer.toString(ctrlDef
				.getRelCtrlId()));

		// Si el campo esta en la traducciï¿½n perfecto
		if (translations != null
				&& translations.containsKey(new Integer(ctrlDef.getFldId()))) {
			return translations.get(new Integer(ctrlDef.getFldId()));
		} else {
			// Si viene el parametro xxx;xxx;xxxx
			// Se convierte a una lista de elementos
			if (operator.equals(RBUtil.getInstance(locale).getProperty(
					Keys.I18N_QUERY_OR_TEXT_VALUE))
					|| operator.equals(RBUtil.getInstance(locale).getProperty(
							Keys.I18N_QUERY_BETWEEN_TEXT_VALUE))) {
				StringTokenizer tokenizer = new StringTokenizer(param,
						PUNTO_COMA);
				List list = new ArrayList(tokenizer.countTokens());
				int i = 0;
				while (tokenizer.hasMoreTokens()) {
					list.add(getValue(axsfQ, field, tokenizer.nextToken(),
							ctrlDef, locale));
					i++;
				}
				return list;
			} else if (operator.equals(RBUtil.getInstance(locale).getProperty(
					Keys.I18N_QUERY_LIKE_TEXT_VALUE))) {
				String aux = param;
				if (StringUtils.startsWith(param, PORCIENTO)||StringUtils.endsWith(param, PORCIENTO)){
					_logger.debug("Realizando la busqueda simple 'empieza con o termina con' utilizando el parametro: " + aux);
				}else{
					aux = param.replaceAll(PORCIENTO, "");
					aux = PORCIENTO + aux + PORCIENTO;
					_logger.debug("Realizando la busqueda simple 'contiene con', utilizando el parametro: " + aux);
				}

				return getValue(axsfQ, field, aux, ctrlDef, locale);
			} else {
				return getValue(axsfQ, field, param, ctrlDef, locale);
			}
		}
	}

	/**
	 * Devuelve el valor asociado a la clave <code>key</code> en el mapa
	 * <code>params</code>. En caso de que haya más de un valor se devuelve el
	 * primero. Esto se hace así para replicar el comportamiento legado que
	 * hacía uso del método
	 * <code>javax.servlet.ServletRequest.getParameter(String)</code>
	 *
	 * @param params
	 * @param key
	 * @return
	 */
	private String getValue(Map params, String key) {
		Object object = params.get(key);
		if (object instanceof String[]) {
			return ((String[]) object)[0];
		}
		return (String) object;
	}

	private Object getValue(AxSf axsfQ, AxSfQueryField field, String param,
			QCtrlDef ctrlDef, Locale locale) throws ParseException {
		if (field.getFldId().equals(AxSf.FLD6_FIELD)) {
			// En el fichero de recursos aparecen las claves de estas etiquetas
			// en mayï¿½scula, sino hacemos esto no los va a encontrar
			if (StringUtils.isNotEmpty(param)) {
				param = param.toUpperCase();
			}
			return new Integer(RBUtil.getInstance(locale).getProperty(
					"book.fld6." + param));
		} else if (field.getFldId().equals(AxSf.FLD11_FIELD) && (axsfQ instanceof AxSfIn)) {
			return new Integer(RBUtil.getInstance(locale).getProperty(
					"book.fld11." + param));
		} else if (axsfQ.getAttributeClass(field.getFldId()).equals(Date.class)) {
			SimpleDateFormat SDF = new SimpleDateFormat(RBUtil.getInstance(
					locale).getProperty(I18N_DATE_SHORTFORMAT));
			SDF.setLenient(false);
			return SDF.parse(param);
		} else if (axsfQ.getAttributeClass(field.getFldId()).equals(
				Integer.class)) {
			return new Integer(param);
		} else if (axsfQ.getAttributeClass(field.getFldId()).equals(
				BigDecimal.class)) {
			return new Integer(param);
		} else {
			return param;
		}
	}

	private List getAttributesForValidationForTable(AxSf axsf) {
		String data = axsf.getFormat().getData();
		TableFormat tableFormat = new TableFormat(data);
		List result = new ArrayList();
		List flds = axsf.getAttributesNames();
		String key = null;
		int aux = 0;
		// Sobre los nombre de atributos sacamos los que sean mayores que
		// EREG... Y SREG
		// y que ademas existan en el formato de tabla
		for (Iterator it = flds.iterator(); it.hasNext();) {
			key = ((String) it.next());
			if (key.startsWith(XML_FLD_TEXT)) {
				aux = Integer.parseInt(key.substring(XML_FLD_TEXT.length(), key
						.length()));
				if (axsf instanceof AxSfIn) {
					if (aux > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
						if (existAttributeInQueryFormat(tableFormat, aux)) {
							result.add(new Integer(aux));
						}
					}
				} else {
					if (aux > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
						if (existAttributeInQueryFormat(tableFormat, aux)) {
							result.add(new Integer(aux));
						}
					}
				}
			}
		}
		return result;
	}

	private List getAttributesForValidationForForm(UseCaseConf useCaseConf,
			Integer bookID, AxSf axsf, int page) throws ValidationException,
			AttributesException, BookException, SessionException {
		String data = axsf.getFormat().getData();
		FormFormat formFormat = new FormFormat(data);
		List result = new ArrayList();
		// List flds = axsf.getAttributesNames();
		List flds = AttributesSession.getExtendedValidationFieldsForBook(
				useCaseConf.getSessionID(), bookID, useCaseConf.getEntidadId());

		int aux = 0;
		// Sobre los nombre de atributos sacamos los que sean mayores que
		// EREG... Y SREG
		// y que ademas existan en el formato de tabla
		for (Iterator it = flds.iterator(); it.hasNext();) {
			aux = Integer.parseInt(it.next().toString());

			if (axsf instanceof AxSfIn) {
				if (aux > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
					if (existAttributeInQueryFormat(formFormat, aux, page)) {
						result.add(new Integer(aux));
					}
				}
			} else {
				if (aux > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
					if (existAttributeInQueryFormat(formFormat, aux, page)) {
						result.add(new Integer(aux));
					}
				}
			}
			// }
		}
		return result;
	}

	private boolean existAttributeInQueryFormat(TableFormat tf, int fldid) {
		// Recorremos el formato de tabla para ver si el atributo aparece en el
		// resultado
		// Sino aparece no buscamos su valor de validaciï¿½n
		TColDef colDef = null;
		for (Iterator it = tf.getDlgDef().getColdefs().values().iterator(); it
				.hasNext();) {
			colDef = (TColDef) it.next();
			if (colDef.getFldId() == fldid) {
				return true;
			}
		}
		return false;
	}

	private boolean existAttributeInQueryFormat(FormFormat ff, int fldid,
			int page) {
		// Recorremos el formato de tabla para ver si el atributo aparece en el
		// resultado
		// Sino aparece no buscamos su valor de validaciï¿½n
		TreeMap pages = ff.getDlgDef().getPagedefs();
		FPageDef pageDef = (FPageDef) pages.get(new Integer(page));
		TreeMap ctrls = pageDef.getCtrldefs();
		FCtrlDef ctrlDef = null;
		for (Iterator it = ctrls.values().iterator(); it.hasNext();) {
			ctrlDef = (FCtrlDef) it.next();
			if (ctrlDef.getFldId() == fldid) {
				return true;
			}
		}
		return false;
	}

	private void assignValidationFields(AxSf axsf,
			AxSfQueryResults queryResults, UseCaseConf useCaseConf,
			Integer bookID) throws ValidationException, AttributesException,
			BookException, SessionException, SecurityException {
		List list = getAttributesForValidationForTable(axsf);
		if (!list.isEmpty()) {
			Map fldids = new HashMap(list.size());
			Integer fldid = null;
			String fldName = null;
			List fldidsValues = null;
			AxSf auxAxsf = null;
			// Recogemos los valores que hay que validar
			for (Iterator it = list.iterator(); it.hasNext();) {
				fldid = (Integer) it.next();
				fldName = XML_FLD_TEXT + fldid.toString();
				fldidsValues = new ArrayList();
				for (Iterator it2 = queryResults.getResults().iterator(); it2
						.hasNext();) {
					auxAxsf = (AxSf) it2.next();
					fldidsValues
							.add(auxAxsf.getAttributeValueAsString(fldName));
				}
				fldids.put(fldid, fldidsValues);
			}
			Map result = AttributesSession.getExtendedValidationFieldValues(
					useCaseConf.getSessionID(), bookID, fldids, useCaseConf
							.getLocale(), useCaseConf.getEntidadId());
			// El servidor nos devuelve una estructura con los fldid, y para
			// cada uno un
			// map con el valor de validacion y el valor validado
			// Entonces sustituimos los valores en los objetos axsf
			String oldValue = null;
			String newValue = null;
			for (Iterator it = result.keySet().iterator(); it.hasNext();) {
				fldid = (Integer) it.next();
				fldName = XML_FLD_TEXT + fldid.toString();
				fldids = (Map) result.get(fldid);
				for (Iterator it2 = fldids.keySet().iterator(); it2.hasNext();) {
					oldValue = (String) it2.next();
					newValue = (String) fldids.get(oldValue);
					for (Iterator it3 = queryResults.getResults().iterator(); it3
							.hasNext();) {
						auxAxsf = (AxSf) it3.next();
						if (auxAxsf.getAttributeValueAsString(fldName) != null
								&& auxAxsf.getAttributeValueAsString(fldName)
										.length() > 0) {
							if (auxAxsf.getAttributeValueAsString(fldName)
									.equals(oldValue)) {
								auxAxsf.setAttributeValue(fldName, newValue);
							}
						}
					}
				}
			}
		}
	}

	private Map getValidationFields(AxSf axsf, UseCaseConf useCaseConf,
			Integer bookID, int page) throws ValidationException,
			AttributesException, BookException, SessionException {
		List list = getAttributesForValidationForForm(useCaseConf, bookID,
				axsf, page);

		Map result = new HashMap();
		if (!list.isEmpty()) {
			Map fldids = new HashMap(list.size());
			Integer fldid = null;
			String fldName = null;
			List values = null;
			// Recogemos los valores que hay que validar
			for (Iterator it = list.iterator(); it.hasNext();) {
				fldid = (Integer) it.next();
				fldName = XML_FLD_TEXT + fldid.toString();
				values = new ArrayList();
				values.add(axsf.getAttributeValueAsString(fldName));
				fldids.put(fldid, values);
			}
			result = AttributesSession.getExtendedValidationFieldValues(
					useCaseConf.getSessionID(), bookID, fldids, useCaseConf
							.getLocale(), useCaseConf.getEntidadId());

		}
		return result;
	}

	private String getDest(UseCaseConf useCaseConf, Integer bookId, int fldid)
			throws ValidationException, SecurityException, AttributesException,
			BookException, SessionException, Exception {
		StringBuffer buffer = new StringBuffer();

		InteresadosDecorator interesadosDecorator = ISicresBeansProvider.getInstance().getInteresadosDecorator();

//		InteresadosDecorator interesadosDecorator = (InteresadosDecorator) SpringApplicationContext
//				.getBean("interesadosDecorator");

		return interesadosDecorator.getInteresados(bookId.toString(),
				String.valueOf(fldid));
	}

	private void checkValue(FlushFdrField flushFdrField,
			SimpleDateFormat shortFormatter, SimpleDateFormat longFormatter,
			FieldFormat fieldFormat, List preResult) {
		FFldDef fldDef = fieldFormat.getFFldDef(flushFdrField.getFldid());
		if (_logger.isDebugEnabled()) {
			_logger.debug("comprobando errores det en :"
					+ flushFdrField.getFldid());
			_logger.debug("\tvalue :" + flushFdrField.getValue());
			_logger.debug("\tvalue :" + flushFdrField.getValue().length());
			_logger.debug("\ttype :" + fldDef.getType());
			_logger.debug("\tlen :" + fldDef.getLen());
		}
		switch (fldDef.getType()) {
		case 1:
		case 2: {
			// if (flushFdrField.getValue().length() > fldDef.getLen()) {
			// preResult.add(new Integer(flushFdrField.getFldid()));
			// }
			break;
		}
		case 3:
		case 4: {
			try {
				Long.parseLong(flushFdrField.getValue());
			} catch (Exception e) {
				preResult.add(new Integer(flushFdrField.getFldid()));
			}
			break;
		}
		case 5:
		case 6: {
			try {
				new BigDecimal(flushFdrField.getValue());
			} catch (Exception e) {
				preResult.add(new Integer(flushFdrField.getFldid()));
			}
			break;
		}
		case 7: {
			try {
				if (flushFdrField.getValue().length() > 10) {
					new Exception("Fecha mal formada");
				}
				shortFormatter.parse(flushFdrField.getValue());
				if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			} catch (Exception e) {
				try {
					if (flushFdrField.getValue().length() > 10) {
						new Exception("Fecha mal formada");
					}
					shortFormatter.parse(flushFdrField.getValue());
					if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
							|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} catch (Exception e1) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			}
			break;
		}
		case 8:
		case 9: {
			try {
				longFormatter.parse(flushFdrField.getValue());
				if (longFormatter.getCalendar().get(Calendar.YEAR) < 1970
						|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			} catch (Exception e) {
				try {
					shortFormatter.parse(flushFdrField.getValue());
					if (shortFormatter.getCalendar().get(Calendar.YEAR) < 1970
							|| shortFormatter.getCalendar().get(Calendar.YEAR) > 2040) {
						preResult.add(new Integer(flushFdrField.getFldid()));
					}
				} catch (Exception e1) {
					preResult.add(new Integer(flushFdrField.getFldid()));
				}
			}
			break;
		}
		}
	}

	private Object convertValue(FlushFdrField flushFdrField,
			SimpleDateFormat shortFormatter, SimpleDateFormat longFormatter,
			FieldFormat fieldFormat) {
		FFldDef fldDef = fieldFormat.getFFldDef(flushFdrField.getFldid());
		switch (fldDef.getType()) {
		case 1:
		case 2: {
			return flushFdrField.getValue();
		}
		case 3:
		case 4: {
			if (flushFdrField.getValue() != null) {
				return new Integer(flushFdrField.getValue());
			} else {
				return flushFdrField.getValue();
			}
		}
		case 5:
		case 6: {
			if (flushFdrField.getValue() != null) {
				BigDecimal result = null;
				try {
					result = new BigDecimal(flushFdrField.getValue());
				} catch (NumberFormatException e) {
				}
				return result;
			} else {
				return flushFdrField.getValue();
			}
		}
		case 7: {
			try {
				if (flushFdrField.getValue() != null) {
					return shortFormatter.parse(flushFdrField.getValue());
				} else {
					return flushFdrField.getValue();
				}
			} catch (ParseException e) {
			}
		}
		case 8:
		case 9: {
			try {
				if (flushFdrField.getValue() != null) {
					return longFormatter.parse(flushFdrField.getValue());
				} else {
					return flushFdrField.getValue();
				}
			} catch (ParseException e) {
			}
		}
		}
		return null;
	}

	/***************************************************************************
	 * * Cambiar los registros que cumplan las condiciones indicadas desde la
	 * pantalla de relaciones al estado CERRADO Los Registros ademï¿½s sï¿½lo
	 * podrï¿½n ser cerrados si estï¿½n en estado COMPLETO
	 **************************************************************************/
	public int closeRegisters(UseCaseConf useCaseConf, Integer bookID,
			Date beginDate, Date endDate, String unit, boolean isTarget)
			throws BookException, SessionException, SQLException,
			SecurityException, ParseException, AttributesException,
			ValidationException {

		int nofUpdatedRegs = 0;

		FlushFdrField field = new FlushFdrField();
		field.setFldid(AxSf.FLD6_FIELD_ID); // TODO: Buscar en constantes: id
		// del campo estado
		field.setValue(new Integer(ISicresKeys.SCR_ESTADO_REGISTRO_CERRADO)
				.toString());
		List fields = new ArrayList(1);
		fields.add(field);

		// comprobacion de permisos de cierre
		String sessionID = useCaseConf.getSessionID();
		boolean canCloseRegs=SecuritySession.canOpenCloseReg(sessionID,bookID);

		if (!canCloseRegs) {
			throw new SecurityException(SecurityException.ERROR_CAN_NOT_OPEN_CLOSE_REG);
		}

		List listIdsRegister = FolderSession.getRegistersToClose(useCaseConf
				.getSessionID(), bookID, beginDate, endDate, unit, isTarget,
				useCaseConf.getEntidadId());

		if (listIdsRegister != null && listIdsRegister.size() > 0) {
			nofUpdatedRegs = listIdsRegister.size();
			updateRegisterToClose(useCaseConf, bookID, fields, listIdsRegister);
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("closeRegisters");
		}

		return nofUpdatedRegs;
	}

	/** Abrir los registros que se hayan seleccionado * */
	public void openRegisters(UseCaseConf useCaseConf, Integer bookID,
			List listIdsRegister) throws BookException, SessionException,
			ValidationException, ParseException, AttributesException,
			SecurityException {

		FlushFdrField field = new FlushFdrField();
		field.setFldid(AxSf.FLD6_FIELD_ID);
		field.setValue(new Integer(ISicresKeys.SCR_ESTADO_REGISTRO_COMPLETO)
				.toString());
		List fields = new ArrayList(1);
		fields.add(field);

		// verificacion de poder hacer operacion de abrir/cerrar registros.
		String sessionID = useCaseConf.getSessionID();
		boolean canCloseRegs=SecuritySession.canOpenCloseReg(sessionID, bookID);

		//actualizamos el estado a completo ya que pare cerrar un registro deberia haber estado completo
		updateFields(useCaseConf, bookID, fields, listIdsRegister);

		if (_logger.isDebugEnabled()) {
			_logger.debug("openRegisters");
		}

	}

	/**
	 * Metodo que prepara y actualiza el estado de los registros a CERRADO
	 * @param useCaseConf
	 * @param bookId
	 * @param fields
	 * @param listIdsRegister
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws AttributesException
	 * @throws BookException
	 * @throws SessionException
	 * @throws ParseException
	 */
	public void updateRegisterToClose(UseCaseConf useCaseConf, Integer bookId,
			List fields, List listIdsRegister) throws ValidationException,
			SecurityException, AttributesException, BookException,
			SessionException, ParseException {

		BookSession.preUpdateRegisterToClose(useCaseConf.getSessionID(), bookId,
				listIdsRegister, useCaseConf.getEntidadId());

		Integer folderID = null;
		for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
			folderID = (Integer) it.next();
			saveOrUpdateFolder(useCaseConf, bookId, folderID.intValue(), null,
					fields, null, null);
		}

		BookSession.postUpdateFields(useCaseConf.getSessionID(), bookId,
				listIdsRegister, useCaseConf.getEntidadId());

	}

	public void updateFields(UseCaseConf useCaseConf, Integer bookId,
			List fields, List listIdsRegister) throws ValidationException,
			SecurityException, AttributesException, BookException,
			SessionException, ParseException {

		BookSession.preUpdateFields(useCaseConf.getSessionID(), bookId,
				listIdsRegister, useCaseConf.getEntidadId());

		Integer folderID = null;
		for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
			folderID = (Integer) it.next();
			saveOrUpdateFolder(useCaseConf, bookId, folderID.intValue(), null,
					fields, null, null);
		}

		BookSession.postUpdateFields(useCaseConf.getSessionID(), bookId,
				listIdsRegister, useCaseConf.getEntidadId());

	}

	public Map getOperadoresCampos(UseCaseConf useCaseConf, Integer archiveId,
			long archivePId) throws ValidationException, BookException,
			AttributesException, SessionException, SecurityException {

		AxSf axsf = BookSession.getQueryFormat(useCaseConf.getSessionID(),
				archiveId, useCaseConf.getEntidadId());
		Idocarchdet idocarchdet = BookSession.getIdocarchdetFld(useCaseConf
				.getSessionID(), archiveId);
		FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

		String dataBaseType = null;

		try {
			dataBaseType = BookSession.getDataBaseType(useCaseConf
					.getSessionID());
		} catch (Exception e) {
		}

		return getOperadores(axsf, fieldFormat, archiveId, archivePId, null,
				useCaseConf.getLocale(), dataBaseType);

	}

	public Map getOperadores(AxSf axsf, FieldFormat fieldFormat,
			Integer archiveId, long archivePId, Map fieldsNotEqual,
			Locale locale, String dataBaseType) {

		String data = axsf.getFormat().getData();
		QueryFormat queryFormat = new QueryFormat(data);
		Integer ctrlId = null;
		Integer fldId = null;
		QCtrlDef ctrlDef = null;

		Map ctrBoxDisabled = new HashMap();
		Map ctrBoxOfFldId = new HashMap();

		Map operadoresCampos = new HashMap();

		if (fieldsNotEqual != null && !fieldsNotEqual.isEmpty()) {
			for (Iterator it = queryFormat.getDlgDef().getCtrldefs().keySet()
					.iterator(); it.hasNext();) {
				ctrlId = (Integer) it.next();
				ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs().get(
						ctrlId);
				if (fieldsNotEqual.containsKey("FLD" + ctrlDef.getFldId())) {
					if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
						ctrBoxDisabled.put("CTR" + (ctrlId.intValue() - 1),
								new Integer(ctrlId.intValue() - 1));
					}
				}

			}
		}

		for (Iterator it = queryFormat.getDlgDef().getCtrldefs().keySet()
				.iterator(); it.hasNext();) {
			ctrlId = (Integer) it.next();
			ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs().get(
					ctrlId);
			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				ctrBoxOfFldId.put(new Integer(ctrlDef.getRelCtrlId()),
						new Integer(ctrlDef.getFldId()));
			}
		}

		for (Iterator it = queryFormat.getDlgDef().getCtrldefs().keySet()
				.iterator(); it.hasNext();) {
			ctrlId = (Integer) it.next();
			ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs().get(
					ctrlId);
			List operators = null;

			fldId = new Integer(ctrlDef.getId());

			if (ctrlDef.getName().startsWith(IDOC_CBOX)) {
				if (ctrBoxOfFldId.containsKey(new Integer(ctrlDef.getId()))) {
					fldId = (Integer) ctrBoxOfFldId.get(new Integer(ctrlDef
							.getId()));
				}

				operators = addOperators(ctrlDef.getOprs(), ctrlDef.getText(),
						locale, dataBaseType, fldId);

				if (it.hasNext()) {
					ctrlId = (Integer) it.next();
					ctrlDef = (QCtrlDef) queryFormat.getDlgDef().getCtrldefs()
							.get(ctrlId);
					if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
						// con este paso se obtiene el FldID del campo
						fldId = new Integer(ctrlDef.getFldId());
						operadoresCampos
								.put(
										"FLD"
												+ Integer.toString(fldId
														.intValue()), operators);
					}
				}
			}
		}
		return operadoresCampos;
	}

	private List addOperators(int oprs, String text, Locale locale,
			String dataBaseType, Integer fldId) {

		SearchOperator searchOperator = null;
		List arrayOperators = new ArrayList();

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_EQUAL_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_NOT_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_NOT_EQUAL_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_GREATER_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_GREATER_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_GREATER_EQUAL_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_LESSER_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(
				oprs,
				text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LESSER_EQUAL_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_LESSER_EQUAL_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_BETWEEN_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_LIKE_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_TEXT_VALUE, RBUtil
						.getInstance(locale).getProperty(
						Keys.I18N_QUERY_OR_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_ABC_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_ABC_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}
		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_AND_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_IN_AND_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		searchOperator = addOperator(oprs, text,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_VALUE,
				com.ieci.tecdoc.common.isicres.Keys.QUERY_IN_OR_TEXT_VALUE,
				RBUtil.getInstance(locale).getProperty(
						Keys.I18N_QUERY_IN_OR_TEXT_VALUE), fldId);
		if (searchOperator.getIdOperator() != null) {
			arrayOperators.add(searchOperator);
		}

		if (fldId != null && dataBaseType.equals("ORACLE")
				&& (fldId.intValue() == 7 || fldId.intValue() == 8)) {
			searchOperator.setIdOperator(new Integer(100));
			searchOperator.setLiteral(RBUtil.getInstance(locale).getProperty(
					Keys.I18N_QUERY_DEPEND_OF_VALUE));
			arrayOperators.add(searchOperator);
		}

		return arrayOperators;
	}

	private SearchOperator addOperator(int oprs, String text, int operator,
			String operatorSimbol, String operatorText, Integer fldId) {
		SearchOperator searchOperator = new SearchOperator();
		// Compara una mï¿½scara de bits. Ej: 511 & 32 = 32 (true)
		// = 0 (false)
		if ((oprs & operator) == operator) {
			searchOperator.setIdOperator(new Integer(operator));
			searchOperator.setLiteral(operatorText);
			searchOperator.setOperatorSimbol(operatorSimbol);
		}
		return searchOperator;
	}

	private AxSf getQueryFormat(String sessionID, Integer bookId,
			String idEntidad) throws ValidationException, BookException,
			SessionException {

		AxSf axsfQ = BookSession.getQueryFormat(sessionID, bookId, idEntidad);

		// Cambiamos los tipos de datos a String porque nos vienen codes y no id
		axsfQ.addAttributeClass(XML_FLD_TEXT + Integer.toString(5),
				String.class.getName());
		axsfQ.addAttributeClass(XML_FLD_TEXT + Integer.toString(7),
				String.class.getName());
		axsfQ.addAttributeClass(XML_FLD_TEXT + Integer.toString(8),
				String.class.getName());

		if (axsfQ instanceof AxSfIn) {
			axsfQ.addAttributeClass(XML_FLD_TEXT + Integer.toString(13),
					String.class.getName());
			axsfQ.addAttributeClass(XML_FLD_TEXT + Integer.toString(16),
					String.class.getName());
		} else {
			axsfQ.addAttributeClass(XML_FLD_TEXT + Integer.toString(12),
					String.class.getName());
		}

		return axsfQ;

	}

	public List validateAdvancedQueryParams(UseCaseConf useCaseConf,
			Integer bookId, Map fieldSearchAdvancedValues)
			throws ValidationException, BookException, AttributesException,
			SessionException, SecurityException {

		// Tenemos que devolver los campos que fallan
		List result = new ArrayList();

		Locale locale = useCaseConf.getLocale();
		if (locale == null) {
			locale = new Locale("es");
		}

		AxSf axsfQ = getQueryFormat(useCaseConf.getSessionID(), bookId,
				useCaseConf.getEntidadId());

		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
		Collection formatterFields = formatter.getDlgDef().getCtrldefs()
				.values();
		QCtrlDef ctrlDef = null;
		int fldid = 0;
		String fld = null;
		for (Iterator itFmt = formatterFields.iterator(); itFmt.hasNext();) {
			ctrlDef = (QCtrlDef) itFmt.next();
			fldid = ctrlDef.getFldId();
			fld = IDocKeys.IDOC_FLD_PREFIX + new Integer(fldid).toString();

			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				if (fieldSearchAdvancedValues.get(fld) != null) {
					List fieldValuesList = (List) fieldSearchAdvancedValues
							.get(fld);
					if (fieldValuesList != null && fieldValuesList.size() > 0) {
						Iterator itFld = fieldValuesList.iterator();
						while (itFld.hasNext()) {
							RowQuerySearchAdvanced rowSearchAdv = (RowQuerySearchAdvanced) itFld
									.next();
							Integer rowId = new Integer(rowSearchAdv
									.getFieldSearchAvanced().getRowId());
							try {
								AxSfQueryField field = new AxSfQueryField();
								field.setFldId(XML_FLD_TEXT
										+ ctrlDef.getFldId());
								field.setValue(getValue(axsfQ, field,
										rowSearchAdv, ctrlDef, null,
										useCaseConf.getLocale()));
								field
										.setOperator(translateOperator(
												rowSearchAdv.getIdoperator()
														.intValue(),
										useCaseConf.getLocale()));
								field.setNexo(translateNexo(rowSearchAdv
										.getNexo()));

								if (field.getValue().getClass().equals(
										ArrayList.class)) {
									// Si el campo de bï¿½squeda tiene varios
									// valores pero su tamaï¿½o no es 2, hay un
									// error
									if (((List) field.getValue()).size() != 2
											&& !field.getOperator().equals(
													BARRA)) {
										result.add(rowId);
									}
								}

								// Si estamos en un campo de tipo fecha
								if ((fldid == AxSf.FLD2_FIELD_ID || fldid == AxSf.FLD4_FIELD_ID)
										&& field.getValue().getClass().equals(
												Date.class)) {
									String auxDate = rowSearchAdv
											.getValueWhere();
									SimpleDateFormat shortFormatter = new SimpleDateFormat(
											RBUtil
													.getInstance(
															useCaseConf
																	.getLocale())
													.getProperty(
															I18N_DATE_SHORTFORMAT));
									shortFormatter.setLenient(false);
									try {
										if (auxDate.length() > 10) {
											result.add(rowId);
										}
										shortFormatter.parse(auxDate);
										if (shortFormatter.getCalendar().get(
												Calendar.YEAR) < 1970) {
											result.add(rowId);
										}
									} catch (Exception e) {
										result.add(rowId);
									}
								}
								// Si los campos son identificadores validados
								// (unidad administrativa u oficina)
								if (fldid == AxSf.FLD5_FIELD_ID
										|| fldid == AxSf.FLD7_FIELD_ID
										|| fldid == AxSf.FLD8_FIELD_ID) {
									if (!field.getValue().getClass().equals(
											List.class)) {
										Map idToTranslate = new HashMap();
										idToTranslate.put(new Integer(fldid),
												rowSearchAdv.getValueWhere());
										Map idTranslated = AttributesSession
												.translateFixedValues(
														useCaseConf
																.getSessionID(),
														bookId, idToTranslate,
														useCaseConf
																.getEntidadId());
										if (idTranslated == null
												|| idTranslated.size() == 0) {
											result.add(rowId);
										}
									}
								}

								// Si estamos en el libro de entrada
								if (axsfQ instanceof AxSfIn) {
									// Si el campo es el de entidad registral o
									// de tipo de asunto en el libro de entrada
									if ((fldid == AxSf.FLD13_FIELD_ID || fldid == AxSf.FLD16_FIELD_ID)) {
										if (!field.getValue().getClass()
												.equals(List.class)) {
											Map idToTranslate = new HashMap();
											idToTranslate.put(
													new Integer(fldid),
													rowSearchAdv
															.getValueWhere());
											Map idTranslated = AttributesSession
													.translateFixedValues(
															useCaseConf
																	.getSessionID(),
															bookId,
															idToTranslate,
															useCaseConf
																	.getEntidadId());
											if (idTranslated == null
													|| idTranslated.size() == 0) {
												result.add(rowId);
											}
										}
									}
									if (fldid > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
										if (AttributesSession
												.getExtendedValidationFieldValue(
														useCaseConf
																.getSessionID(),
														bookId, fldid, field
																.getValue()
																.toString(),
														locale.getLanguage(),
														useCaseConf
																.getEntidadId()) == null) {
											result.add(rowId);
										}

									}
								}
								// Si estamos en el libro de salida
								else {
									// Si estamos en el campo de tipo de asunto
									if ((fldid == AxSf.FLD12_FIELD_ID)) {
										Map idToTranslate = new HashMap();
										idToTranslate.put(new Integer(fldid),
												rowSearchAdv.getValueWhere());
										Map idTranslated = AttributesSession
												.translateFixedValues(
														useCaseConf
																.getSessionID(),
														bookId, idToTranslate,
														useCaseConf
																.getEntidadId());
										if (idTranslated == null
												|| idTranslated.size() == 0) {
											result.add(rowId);
										}
									}
									if (fldid > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
										if (AttributesSession
												.getExtendedValidationFieldValue(
														useCaseConf
																.getSessionID(),
														bookId, fldid, field
																.getValue()
																.toString(),
														locale.getLanguage(),
														useCaseConf
																.getEntidadId()) == null) {
											result.add(rowId);
										}

									}
								}
							} catch (Exception e) {
								result.add(rowId);
							}
						}
					}
				}
			}
		}

		return result;
	}

	public List translateAdvancedQueryParams(UseCaseConf useCaseConf,
			Integer bookId, Map fieldSearchAdvancedValues)
			throws ValidationException, BookException, AttributesException,
			SessionException, ParseException, SecurityException {

		// Devolvemos id del campo y valor traducido
		Map resultMap = new HashMap();
		AxSf axsfQ = getQueryFormat(useCaseConf.getSessionID(), bookId,
				useCaseConf.getEntidadId());

		QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
		Collection formatterFields = formatter.getDlgDef().getCtrldefs()
				.values();
		QCtrlDef ctrlDef = null;
		int fldid = 0;
		String fld = null;
		for (Iterator itCtrlDef = formatterFields.iterator(); itCtrlDef
				.hasNext();) {
			ctrlDef = (QCtrlDef) itCtrlDef.next();
			fldid = ctrlDef.getFldId();
			fld = IDocKeys.IDOC_FLD_PREFIX + new Integer(fldid).toString();
			if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
				if (fieldSearchAdvancedValues.get(fld) != null) {
					List fieldValuesList = (List) fieldSearchAdvancedValues
							.get(fld);
					if (fieldValuesList != null && fieldValuesList.size() > 0) {
						Iterator itFld = fieldValuesList.iterator();

						while (itFld.hasNext()) {
							RowQuerySearchAdvanced rowSearchAdv = (RowQuerySearchAdvanced) itFld
									.next();
							AxSfQueryField field = new AxSfQueryField();
							field.setFldId(XML_FLD_TEXT + ctrlDef.getFldId());
							field.setOperator(translateOperator(rowSearchAdv
									.getIdoperator().intValue(), useCaseConf
									.getLocale()));
							field
									.setNexo(translateNexo(rowSearchAdv
											.getNexo()));
							// El idoficina es un campo a validar
							if (fldid == AxSf.FLD5_FIELD_ID) {
								Map idToTranslate = new HashMap();
								idToTranslate.put(new Integer(fldid),
										rowSearchAdv.getValueWhere());
								Map idTranslated = AttributesSession
										.translateFixedValues(useCaseConf
												.getSessionID(), bookId,
												idToTranslate, useCaseConf
														.getEntidadId());

								field.setValue(getValue(axsfQ, field,
										rowSearchAdv, ctrlDef, idTranslated,
										useCaseConf.getLocale()));
							} else {
								// Las unidades adm origen y destino son campos
								// a validar si no se usa el operador
								// QUERY_DEPEND_OF_TEXT_VALUE
								if ((fldid == AxSf.FLD7_FIELD_ID || fldid == AxSf.FLD8_FIELD_ID)
										&& !field
												.getOperator()
												.equals(
														com.ieci.tecdoc.common.isicres.Keys.QUERY_DEPEND_OF_TEXT_VALUE)) {
									Map idToTranslate = new HashMap();
									idToTranslate.put(new Integer(fldid),
											rowSearchAdv.getValueWhere());
									Map idTranslated = AttributesSession
											.translateFixedValues(useCaseConf
													.getSessionID(), bookId,
													idToTranslate, useCaseConf
															.getEntidadId());

									field.setValue(getValue(axsfQ, field,
											rowSearchAdv, ctrlDef,
											idTranslated, useCaseConf
													.getLocale()));

								} else {
									// El identificador de entidad registral y
									// de tipo de asunto son campos a validar
									if (axsfQ instanceof AxSfIn
											&& (fldid == AxSf.FLD13_FIELD_ID || fldid == AxSf.FLD16_FIELD_ID)) {
										Map idToTranslate = new HashMap();
										idToTranslate.put(new Integer(fldid),
												rowSearchAdv.getValueWhere());
										Map idTranslated = AttributesSession
												.translateFixedValues(
														useCaseConf
																.getSessionID(),
														bookId, idToTranslate,
														useCaseConf
																.getEntidadId());

										field.setValue(getValue(axsfQ, field,
												rowSearchAdv, ctrlDef,
												idTranslated, useCaseConf
														.getLocale()));

									} else {
										// El identificador del tipo de asunto
										// del libro de salida tambiï¿½n es un
										// campo a validar
										if (axsfQ instanceof AxSfOut
												&& fldid == AxSf.FLD12_FIELD_ID) {
											Map idToTranslate = new HashMap();
											idToTranslate.put(
													new Integer(fldid),
													rowSearchAdv
															.getValueWhere());
											Map idTranslated = AttributesSession
													.translateFixedValues(
															useCaseConf
																	.getSessionID(),
															bookId,
															idToTranslate,
															useCaseConf
																	.getEntidadId());

											field.setValue(getValue(axsfQ,
													field, rowSearchAdv,
													ctrlDef, idTranslated,
													useCaseConf.getLocale()));
										} else {
											field.setValue(getValue(axsfQ,
													field, rowSearchAdv,
													ctrlDef, null, useCaseConf
															.getLocale()));
										}
									}
								}
							}

							resultMap.put(rowSearchAdv.getRowId(), field);
						}
					}
				}
			}
		}

		List result = new ArrayList();
		if (!resultMap.isEmpty()) {
			for (int i = 0; i < resultMap.size(); i++) {
				AxSfQueryField queryField = (AxSfQueryField) resultMap
						.get(new Integer(i));
				result.add(queryField);
			}
		}

		return result;
	}

	private Object getValue(AxSf axsfQ, AxSfQueryField field,
			RowQuerySearchAdvanced rowQuerySearchAdvanced, QCtrlDef ctrlDef,
			Map translations, Locale locale) throws ParseException {

		String valueWhere = rowQuerySearchAdvanced.getValueWhere();
		int operator = rowQuerySearchAdvanced.getIdoperator().intValue();

		// Si el campo esta en la traducciï¿½n perfecto
		if (translations != null
				&& translations.containsKey(new Integer(ctrlDef.getFldId()))) {
			return translations.get(new Integer(ctrlDef.getFldId()));
		} else {
			// Si viene el parametro xxx;xxx;xxxx
			// Se convierte a una lista de elementos

			if (operator == com.ieci.tecdoc.common.isicres.Keys.QUERY_OR_VALUE
					|| operator == com.ieci.tecdoc.common.isicres.Keys.QUERY_BETWEEN_VALUE) {
				StringTokenizer tokenizer = new StringTokenizer(valueWhere,
						PUNTO_COMA);
				List list = new ArrayList(tokenizer.countTokens());
				int i = 0;
				while (tokenizer.hasMoreTokens()) {
					list.add(getValue(axsfQ, field, tokenizer.nextToken(),
							ctrlDef, locale));
					i++;
				}
				return list;
			} else if (operator == com.ieci.tecdoc.common.isicres.Keys.QUERY_LIKE_VALUE) {
				String aux = valueWhere;
				if (StringUtils.startsWith(valueWhere, PORCIENTO)||StringUtils.endsWith(valueWhere, PORCIENTO)){
					_logger.debug("Realizando la busqueda avanzada 'empieza con o termina con' utilizando el parametro: " + aux);
				}else{
					aux = valueWhere.replaceAll(PORCIENTO, "");
					aux = PORCIENTO + aux + PORCIENTO;
					_logger.debug("Realizando la busqueda avanzada 'contiene con', utilizando el parametro: " + aux);
				}
				return getValue(axsfQ, field, aux, ctrlDef, locale);
			} else {
				return getValue(axsfQ, field, valueWhere, ctrlDef, locale);
			}
		}
	}

	public int openTableResults(UseCaseConf useCaseConf, Integer bookId,
			List fieldList, String listOrder) throws ValidationException,
			BookException, SessionException, SecurityException {

		AxSfQuery axsfQuery = new AxSfQuery();
		axsfQuery.setOrderBy(listOrder);
		axsfQuery.setBookId(bookId);
		axsfQuery
				.setPageSize(Integer
						.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_TABLE_RESULTS_SIZE)));

		if (fieldList != null && fieldList.size() > 0) {
			Iterator it = fieldList.iterator();
			while (it.hasNext()) {
				AxSfQueryField field = (AxSfQueryField) it.next();
				axsfQuery.addField(field);
			}
		}

		if (axsfQuery.getPageSize() <= 0) {
			throw new BookException(BookException.ERROR_PAGE_SIZE);
		}

		int size = FolderSession.openRegistersQuery(useCaseConf.getSessionID(),
				axsfQuery, null, new Integer(0), useCaseConf.getEntidadId());

		return size;
	}

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/
	/***************************************************************************
	 * Test brench
	 **************************************************************************/
}