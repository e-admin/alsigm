/**
 *
 */
package com.ieci.tecdoc.isicres.session.folder;

import gnu.trove.THashMap;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.compulsa.vo.ISicresReturnCompulsaVO;
import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.entity.AxDochEntity;
import com.ieci.tecdoc.common.entity.AxPagehEntity;
import com.ieci.tecdoc.common.entity.AxXnidEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrPageInfo;
import com.ieci.tecdoc.common.invesicres.ScrPagerepository;
import com.ieci.tecdoc.common.invesicres.ScrRegorigdoc;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrTypeproc;
import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPKById;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.SaveOrigDocDataDocInput;
import com.ieci.tecdoc.common.isicres.SaveOrigDocDatasInput;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.repository.helper.ISRepositoryDocumentHelper;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryCreateDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISSignDocumentVO;
import com.ieci.tecdoc.common.utils.ISFileUtil;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.MiscFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.idoc.utils.CryptoUtils;
import com.ieci.tecdoc.isicres.compulsa.CompulsaFactory;
import com.ieci.tecdoc.isicres.repository.RepositoryFactory;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class FolderFileSession extends FolderSessionUtil implements ServerKeys,
		Keys, HibernateKeys {

	private static final Logger log = Logger.getLogger(FolderFileSession.class);

	/***************************************************************************
	 * PUBLIC METHOD
	 **************************************************************************/

	public static List getOrigDocFdr(String sessionID, Integer bookID,
			int folderID, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		List result = new ArrayList();
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			result = ISicresQueries.getScrRegOrigDoc(session, bookID, folderID);

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to close the book [" + bookID
					+ "] and fdrid [" + folderID + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_ORIG_DOC);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void saveOrigDoc(String sessionID, Integer bookID,
			int folderID, SaveOrigDocDatasInput datas, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			AxSf axsf = getAxSf(session, bookID, new Integer(folderID), idoc,
					locale.getLanguage(), entidad, true);

			// Validamos los documentos
			validateOrigDocs(session, datas);

			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_ScrRegorigdoc);
			query.append(" scr WHERE scr.idarch=? AND scr.idfdr=?");
			session.delete(query.toString(), new Integer[] { bookID,
					new Integer(folderID) }, new Type[] { Hibernate.INTEGER,
					Hibernate.INTEGER });

			saveOrigDocs(session, datas, user.getId(), bookID, folderID, axsf,
					scrofic, entidad);

			HibernateUtil.commitTransaction(tran);
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to get the origin docs for the book ["
					+ bookID + "] and fdrid [" + folderID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_SAVE_ORIG_DOC);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static List getBookFolderDocsWithPages(String sessionID,
			Integer bookID, int fdrid, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		List result = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			AxDochEntity axDochEntity = new AxDochEntity();
			AxPagehEntity axPagehEntity = new AxPagehEntity();

			Collection pages = null;
			Collection docs = axDochEntity.findByFdrid(bookID, fdrid, entidad);

			for (Iterator it = docs.iterator(); it.hasNext();) {
				AxPKById axPKById = (AxPKById) it.next();
				axDochEntity.load(axPKById, entidad);
				AxDoch axdoch = axDochEntity.getAxDoch();

				pages = axPagehEntity.findByFdridDocid(bookID, fdrid, axdoch
						.getId(), entidad);

				for (Iterator it2 = pages.iterator(); it2.hasNext();) {
					AxPKById axPKByIdPage = (AxPKById) it2.next();
					axPagehEntity.load(axPKByIdPage, entidad);
					axdoch.addPage(axPagehEntity.getAxPageh());
				}

				result.add(axdoch);
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the docs for book [" + bookID
					+ "] and fdrid [" + fdrid + "] for the session ["
					+ sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_DOCS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static byte[] getFile(String sessionID, Integer bookID,
			Integer regId, Integer pageId, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		byte[] file = null;
		Transaction tran = null;

		try {
			// Recuperamos la sesión
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			ISRepositoryRetrieveDocumentVO findVO = ISRepositoryDocumentHelper
					.getRepositoryRetrieveDocumentVO(bookID, regId, pageId,
							entidad, true);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			ISRepositoryRetrieveDocumentVO retrieveVO = RepositoryFactory
					.getCurrentPolicy().retrieveDocument(findVO);

			if (retrieveVO != null) {
				file = retrieveVO.getFileContent();
				InvesicresConf invesicresConf = ConfiguratorInvesicres
						.getInstance(entidad).getInvesicresConf();
				if (invesicresConf.getDocumentHashing() == 1) {
					String findHash = DBEntityDAOFactory
							.getCurrentDBEntityDAO().getHashDocument(bookID,
									regId.intValue(), pageId.intValue(), null,
									true, entidad);
					if (findHash != null) {
						String hash = CryptoUtils.encryptHashDocument(file);
						if (!findHash.equals(hash)) {
							throw new BookException(
									BookException.ERROR_CANNOT_ACCESS_DOCUMENT);
						}
					}
				}
			}

			HibernateUtil.commitTransaction(tran);

			return file;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load a file for the session [" + sessionID
					+ "] and pageID [" + pageId + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_LOAD_FILE);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Metodo que obtiene la extension del fichero pasado como parametro bien
	 * del propio String con el nombre del fichero o accediendo a la BBDD
	 *
	 * @param fileName
	 *            - Nombre del fichero
	 * @param bookId
	 *            - ID del libro
	 * @param regId
	 *            - ID del registro
	 * @param pageId
	 *            - ID de la pagina
	 * @param entidad
	 *
	 * @return extension del fichero (Ejemplo: pdf, doc, txt...)
	 */
	public static String getExtensionFile(String fileName, String bookId,
			int regId, int pageId, String entidad) {
		String result = null;

		String extension = FilenameUtils.getExtension(fileName);

		// si el nombre del fichero no lleva la extension se la añadimos
		if (StringUtils.isBlank(extension)) {
			try {
				// buscamos la extendion del fichero en BBDD
				AxPagehEntity axPagehEntity = new AxPagehEntity();
				AxPKById axPKById = new AxPKById(bookId.toString(), regId,
						pageId);
				axPagehEntity.load(axPKById, entidad);

				result = axPagehEntity.getLoc();

				result.toLowerCase();
			} catch (Exception e) {
				log.warn(
						"Se produce un error al obtener la extensión del fichero",
						e);
				return null;
			}
		} else {
			result = extension;
		}

		return result;
	}

	public static void saveCompulFiles(ISicresCreateCompulsaVO compulsa,
			String name) throws BookException {
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(compulsa
					.getEntidad());
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					compulsa.getSessionID());

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(compulsa.getBookId())) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(compulsa
					.getBookId());
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			Idocarchdet idocMisc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_MISC_ASOBJECT);

			MiscFormat miscFormat = new MiscFormat(idocMisc.getDetval());

			AxSf axsf = getAxSf(session, compulsa.getBookId(),
					compulsa.getFolderId(), idoc, compulsa.getLocale()
							.getLanguage(), compulsa.getEntidad(), true);

			Timestamp timestamp = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDBServerDate(compulsa.getEntidad());

			AxDochEntity axDochEntity = new AxDochEntity();
			int docID = axDochEntity.lookForName(compulsa.getBookId()
					.toString(), compulsa.getFolderId().intValue(), name,
					compulsa.getEntidad());

			if (log.isDebugEnabled()) {
				log.debug("docID recuperado => " + docID);
			}
			docID = createDoc(axDochEntity, compulsa.getBookId(),
					compulsa.getFolderId(), docID, user.getId(), timestamp,
					name, compulsa.getEntidad());

			// se crea pagina asociada al documento (docID) a isicres
			AxPagehEntity axPagehEntity = new AxPagehEntity();
			int order = axPagehEntity.getOrderID(compulsa.getBookId(), compulsa
					.getFolderId().intValue(), docID, compulsa.getEntidad());

			ISicresReturnCompulsaVO returnCompulsa = CompulsaFactory
					.getCurrentPolicy().compulsarDocuments(compulsa);

			List fileItems = returnCompulsa.getFilesInfo();
			for (Iterator it = fileItems.iterator(); it.hasNext();) {
				FlushFdrFile file = (FlushFdrFile) it.next();
				file.setOrder(order++);

				InputStream inputStream = ISFileUtil.getInputStream(
						file.getBuffer(), file.getFileNameFis());

				ISRepositoryCreateDocumentVO createVO = ISRepositoryDocumentHelper
						.getRepositoryCreateDocumentVO(compulsa.getBookId(),
								compulsa.getFolderId(), new Integer(docID),
								file.getFileNameLog(), file.getFileNameFis(),
								file.getExtension(), miscFormat.getId(), axsf,
								inputStream, compulsa.getEntidad(), false);

				tran = session.beginTransaction();

				ISRepositoryCreateDocumentVO documentCreatedVO = RepositoryFactory
						.getCurrentPolicy().createDocument(createVO);

				int fileID = 0;
				String fileId = null;
				try {
					if (documentCreatedVO != null) {
						fileId = documentCreatedVO.getIsicresDocUID();
						if (documentCreatedVO.getFileContent() != null) {
							file.setBuffer(documentCreatedVO.getFileContent());
						}
					}
					Integer fileIDAux = new Integer(fileId);
					fileID = fileIDAux.intValue();

					if (fileID == Integer.MAX_VALUE - 2) {
						throw new BookException(
								BookException.ERROR_SAVE_COMPUL_FILES);
					}
				} catch (NumberFormatException e) {
					fileID = Integer.MAX_VALUE - 2;
				}

				if (log.isDebugEnabled()) {
					log.debug("fileID creado => " + fileID);
					log.debug("creando file docID [" + docID + "] => " + file);
				}

				int pageID = createPage(file, compulsa.getBookId(),
						compulsa.getFolderId(), user.getId(), docID, fileID,
						timestamp, compulsa.getEntidad());
				savePageInfo(session, file, compulsa.getBookId(),
						compulsa.getFolderId(), fileId, pageID,
						compulsa.getEntidad());

				// comprueba si es el documento compulsado
				if (file.getFileNameLog().lastIndexOf("_C.pdf") > 0) {
					// relaciona el documento compulsado con el localizador
					DBEntityDAOFactory.getCurrentDBEntityDAO()
							.insertScrDocLocator(
									returnCompulsa.getBookId().intValue(),
									returnCompulsa.getFolderId().intValue(),
									pageID, returnCompulsa.getLocator(),
									returnCompulsa.getEntidad());
				}
			}
			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible to save compul files for the session ["
							+ compulsa.getSessionID() + "] and bookID ["
							+ compulsa.getBookId() + "]", e);
			throw new BookException(BookException.ERROR_SAVE_COMPUL_FILES);
		} finally {
			HibernateUtil.closeSession(compulsa.getEntidad());
		}
	}

	public static Map createDocuments(Integer bookID, int folderId,
			Map documents, Integer userId, String entidad) throws BookException {
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			Timestamp timestamp = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDBServerDate(entidad);
			for (Iterator it = documents.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				FlushFdrDocument document = (FlushFdrDocument) documents
						.get(key);

				if (log.isDebugEnabled()) {
					log.debug("analizando documento => " + document);
				}

				int docID = -1;
				try {
					AxDochEntity axDochEntity = new AxDochEntity();
					docID = axDochEntity.lookForName(bookID.toString(),
							folderId, document.getDocumentName(), entidad);

					if (log.isDebugEnabled()) {
						log.debug("docID recuperado => " + docID);
					}
					docID = createDoc(axDochEntity, bookID, new Integer(
							folderId), docID, userId, timestamp,
							document.getDocumentName(), entidad);
				} catch (Exception e) {
					log.warn("Error al crear documento", e);
				}

				document.setDocID(docID);
			}
			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for bookID [" + bookID
					+ "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return documents;
	}

	/**
	 * Método que borra un fichero adjuntado a un registro, solamente de BBDD no el fichero físico del gestor documental
	 *
	 * @param entidad - Entidad
	 * @param folderId - ID del registro
	 * @param docId - ID del documento
	 * @param pageId - ID de la pagina a borrar
	 * @param bookId - ID del libro
	 *
	 * @throws BookException
	 */
	public static void deleteFileOfRegister(String entidad, Integer folderId, Integer docId, Integer pageId, Integer bookId) throws BookException{
		try {
			if(log.isDebugEnabled()){
				StringBuffer sb = new StringBuffer();
				sb.append("Entra a borrar los datos del fichero con ID[").append(pageId)
						.append("] del registro [").append(folderId)
						.append("] que pertenece al documento ID[")
						.append(docId).append("]");

				log.debug(sb.toString());
			}

			// TODO desde aqui se podría invocar al borrado del fichero físico del
			// gestor documental correspondiente


			//borramos los datos en la tabla SCR_PAGEINFO
			DBEntityDAOFactory.getCurrentDBEntityDAO().deleteHashDocument(
					bookId.intValue(), folderId.intValue(), pageId.intValue(),
					entidad);
			if(log.isDebugEnabled()){
				log.debug("Borrados los datos de la tabla SCR_PAGEINFO");
			}

			//borramos los datos en la tabla SCR_PAGEREPOSITORY
			DBEntityDAOFactory.getCurrentDBEntityDAO().deleteScrPageRepository(
					bookId.intValue(), folderId.intValue(), pageId.intValue(),
					entidad);
			if(log.isDebugEnabled()){
				log.debug("Borrados los datos de la tabla SCR_PAGEREPOSITORY");
			}

			//y por último borramos los datos en la tabla SCR_AXPAGEH
			AxPagehEntity axPagehEntity = new AxPagehEntity();
			axPagehEntity.setFdrId(folderId);
			axPagehEntity.setType(bookId.toString());
		axPagehEntity.setDocId(docId);
		axPagehEntity.setId(pageId);
			axPagehEntity.remove(entidad);

			if(log.isDebugEnabled()){
				log.debug("Borrados los datos de la tabla SCR_AXPAGEH");
			}

		} catch (Exception e) {

			StringBuffer sb = new StringBuffer();
			sb.append("Imposible borrar el fichero ID[").append(pageId)
					.append("] del documento ID[").append(docId)
					.append("] para el registro ID[").append(folderId)
					.append("] en el libro ID[").append(bookId).append("]");

			log.error(sb.toString(), e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		}
	}

	/**
	 * Este método sustituye al que se usaba anteriormente
	 *
	 * <code>com.ieci.tecdoc.isicres.session.folder.FolderFileSession.saveDocuments(Integer, int, Map, BookTypeConf, Idocarchdet, Integer, String)</code>
	 *
	 * @param sessionID
	 * @param bookID
	 * @param folderId
	 * @param documents
	 * @param bookTypeConf
	 * @param userId
	 * @param entidad
	 * @return
	 * @throws BookException
	 */
	public static Map saveDocuments(String sessionID, Integer bookID,
			int folderId, Map documents, BookTypeConf bookTypeConf,
			BookConf bookConf, Integer userId, Locale locale, String entidad)
			throws BookException {

		Transaction tran = null;
		try {

			// obtenemos todos los documentos y ficheros del registro
			List docsRegistro = getBookFolderDocsWithPages(sessionID, bookID,
					folderId, entidad);

			Session session = HibernateUtil.currentSession(entidad);

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
			Idocarchdet idocMisc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_MISC_ASOBJECT);

			MiscFormat miscFormat = new MiscFormat(idocMisc.getDetval());

			AxSf axsf = getAxSf(session, bookID, new Integer(folderId), idoc,
					locale.getLanguage(), entidad, true);

			//Hay que recalcular el bookTypeConf porque el que nos llega le pueden faltar datos
			bookTypeConf = FolderSessionUtil.getBookTypeConf(bookID, entidad, axsf, axsf, user.getName(), scrofic.getCode());

			Timestamp timestamp = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDBServerDate(entidad);

			ScrRegstate regState = ISicresQueries.getScrRegstate(session,
					bookID);
			int imageAuth = regState.getImageAuth();
			AxPagehEntity axPagehEntity = new AxPagehEntity();

			for (Iterator it = documents.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				FlushFdrDocument document = (FlushFdrDocument) documents
						.get(key);
				List pages = document.getPages();
				int order = axPagehEntity.getOrderID(bookID, folderId,
						document.getDocID(), entidad);
				int totalNumPages = axPagehEntity.findByFdridDocid(bookID,
						folderId, document.getDocID(), entidad).size();

				for (Iterator it2 = pages.iterator(); it2.hasNext();) {
					FlushFdrPage page = (FlushFdrPage) it2.next();
					FlushFdrFile file = page.getFile();

					// comprobamos si el nombre del fichero ya existe en ese
					// documento
					if (comprobarNombreLogicoFicheroEnDocumento(docsRegistro,
							document.getDocID(), file.getFileNameLog())) {
						log.error("El nombre del fichero ["
								+ file.getFileNameLog()
								+ "] ya existe dentro del documento id ["
								+ document.getDocID() + "] nameDoc ["
								+ document.getDocumentName() + "]");
						throw new BookException(
								BookException.ERROR_CANNOT_SAVE_FILE);
					}

					file.setOrder(order++);
					totalNumPages++;

					String docUID = saveDocument(bookID, folderId, axsf, file,
							document, bookTypeConf, bookConf, miscFormat,
							imageAuth, totalNumPages, entidad);

					if (StringUtils.isBlank(docUID)) {
						throw new BookException(
								BookException.ERROR_CANNOT_SAVE_FILE);
					}

					int fileID = 0;
					try {
						fileID = Integer.parseInt(docUID);
					} catch (NumberFormatException e) {
						fileID = Integer.MAX_VALUE - 2;
					}

					tran = session.beginTransaction();

					file = savePageData(session, bookID, new Integer(folderId),
							userId, docUID, fileID, file, document, timestamp,
							entidad);

					HibernateUtil.commitTransaction(tran);
				}
			}
		} catch (BookException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for bookID [" + bookID
					+ "]", e);
			throw new BookException(BookException.ERROR_CANNOT_SAVE_FILE);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update a folder for bookID [" + bookID
					+ "]", e);
			throw new BookException(BookException.ERROR_UPDATE_FOLDER);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return documents;
	}

	/**
	 * Comprobamos si el nombre del fichero ya existe dentro del documento
	 *
	 * @param docsRegister
	 *            - Listado de los documentos con sus paginas/ficheros donde
	 *            buscaremos el nombre del fichero
	 * @param idDoc
	 *            - Id del documento donde queremos buscar
	 * @param name
	 *            - Nombre del fichero a buscar
	 *
	 * @return TRUE - si el nombre del fichero ya existe dentro del documento
	 *         FALSE - si el nombre del fichero no existe aun dentro del
	 *         documento
	 */
	private static boolean comprobarNombreLogicoFicheroEnDocumento(
			List docsRegister, int idDoc, String name) {
		for (Iterator itDocs = docsRegister.iterator(); itDocs.hasNext();) {
			AxDoch axdoch = (AxDoch) itDocs.next();
			if (axdoch.getId() == idDoc) {
				for (Iterator itPages = axdoch.getPages().iterator(); itPages
						.hasNext();) {
					AxPageh page = (AxPageh) itPages.next();
					if (page.getName().equals(name)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/***************************************************************************
	 * PRIVATE METHOD
	 **************************************************************************/

	private static int createDoc(AxDochEntity axDochEntity, Integer bookId,
			Integer folderId, int docID, Integer userId, Timestamp timestamp,
			String name, String entidad) throws SQLException, Exception {
		if (docID == -1) {
			docID = DBEntityDAOFactory.getCurrentDBEntityDAO().getNextDocID(
					bookId, entidad);
			AxPKById docPk = new AxPKById(bookId.toString(),
					folderId.intValue(), docID);
			axDochEntity.create(docPk, userId.intValue(), name, timestamp,
					entidad);
			if (log.isDebugEnabled()) {
				log.debug("docID creado => " + docID);
			}
		}

		return docID;
	}

	private static void validateOrigDocs(Session session,
			SaveOrigDocDatasInput datas) throws HibernateException {
		for (Iterator it = datas.getDocs().values().iterator(); it.hasNext();) {
			SaveOrigDocDataDocInput doc = (SaveOrigDocDataDocInput) it.next();
			if (doc.getProcId() != null && doc.getProcId().intValue() < 0) {
				List aux = ISicresQueries.getScrTypeproc(session,
						doc.getProcName());
				if (aux != null && !aux.isEmpty()) {
					ScrTypeproc proc = (ScrTypeproc) aux.get(0);
					doc.setProcId(proc.getId());
					doc.setProcName(proc.getName());
				} else {
					doc.setProcId(new Integer(-1));
				}
			}
		}
	}

	private static void saveOrigDocs(Session session,
			SaveOrigDocDatasInput datas, Integer userId, Integer bookID,
			int folderID, AxSf axsf, ScrOfic scrofic, String entidad)
			throws HibernateException, SQLException, Exception {
		for (Iterator it = datas.getDocs().values().iterator(); it.hasNext();) {
			SaveOrigDocDataDocInput doc = (SaveOrigDocDataDocInput) it.next();

			if (doc.getProcId().intValue() >= 0) {
				ScrRegorigdoc origDoc = new ScrRegorigdoc();
				origDoc.setId(new Integer(DBEntityDAOFactory
						.getCurrentDBEntityDAO().getContador4SCRREGORIGDOC(
								userId, entidad)));
				origDoc.setIdarch(bookID.intValue());
				origDoc.setIdfdr(folderID);
				if (doc.getProcId() != null && doc.getProcId().intValue() != 0) {
					origDoc.setScrTypeproc((ScrTypeproc) session.load(
							ScrTypeproc.class, doc.getProcId()));
				} else {
					origDoc.setScrTypeproc(null);
				}
				origDoc.setRegdate((Date) axsf.getAttributeValue("fld2"));
				origDoc.setScrOfic(scrofic);
				origDoc.setScrOrg(axsf.getFld8());
				origDoc.setSummary(doc.getProcName());
				session.save(origDoc);
			}
		}
	}

	private static int createPage(FlushFdrFile file, Integer bookId,
			Integer folderId, Integer userId, int docID, int fileID,
			Timestamp timestamp, String entidad) throws Exception {
		AxXnidEntity axXnidEntity = new AxXnidEntity();
		int pageID = axXnidEntity.getNextID(bookId, folderId.intValue(),
				entidad);

		if (log.isDebugEnabled()) {
			log.debug("creando page pageID  => " + pageID);
		}

		AxPKById pagePk = new AxPKById(bookId.toString(), folderId.intValue(),
				pageID);
		AxPagehEntity axPagehEntity = new AxPagehEntity();
		axPagehEntity.create(pagePk, userId.intValue(), file.getFileNameLog(),
				file.getOrder(), docID, fileID, file.getExtension(), timestamp,
				entidad);

		return pageID;
	}

	/**
	 * Método que almacena en las tablas de paginas (Scr_PageRepository y
	 * Scr_PageInfo) los datos del documento
	 *
	 * @param session
	 * @param file
	 * @param bookId
	 * @param folderId
	 * @param fileId
	 * @param pageID
	 * @param entidad
	 * @throws HibernateException
	 * @throws Exception
	 */
	private static void savePageInfo(Session session, FlushFdrFile file,
			Integer bookId, Integer folderId, String fileId, int pageID,
			String entidad) throws HibernateException, Exception {

		ScrPagerepository scrPagerepository = new ScrPagerepository();
		scrPagerepository.setBookid(bookId.intValue());
		scrPagerepository.setFdrid(folderId.intValue());
		scrPagerepository.setPageid(pageID);
		scrPagerepository.setDocuid(fileId);
		session.save(scrPagerepository);

		InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
				entidad).getInvesicresConf();
		if (invesicresConf.getDocumentHashing() == 1) {

			String hash = getHashDocument(file, bookId, folderId, pageID,
					entidad);

			ScrPageInfo pageInfo = new ScrPageInfo();
			pageInfo.setBookid(bookId.intValue());
			pageInfo.setRegid(folderId.intValue());
			pageInfo.setPageid(pageID);
			pageInfo.setHashVersion(1);
			pageInfo.setHash(hash);
			session.save(pageInfo);
		}
	}

	private static FlushFdrFile signDocument(FlushFdrDocument document,
			FlushFdrFile file, BookTypeConf bookTypeConf, BookConf bookConf,
			int imageAuth, int totalNumPages) throws TecDocException, Exception {

		/*
		 * No hace falta comprobar que sea de tipo 1,2 o 3 porque ya lo
		 * comprueba la clase SignTiff.java.
		 *
		 * Se firma si es un TIFF y si el libro está configurado para firmar
		 * las imágenes.
		 */
		if (isTiffExtension(file.getExtension())
				&& (bookTypeConf != null || bookConf != null) && imageAuth == 1) {
			String pageNumber = document.getDocumentName() + ":"
					+ file.getOrder() + "/" + totalNumPages;
			bookTypeConf.setPageNumber(pageNumber);

			InputStream inputStream = ISFileUtil.getInputStream(
					file.getBuffer(), file.getFileNameFis());
			ISSignDocumentVO signVO = ISRepositoryDocumentHelper
					.getSingDocumentVO(inputStream, bookTypeConf, bookConf);

			signVO = RepositoryFactory.getCurrentPolicy().signDocument(signVO);
			file.setBuffer(signVO.getFileContent());
		}

		return file;
	}

	private static boolean isTiffExtension(String extension){
		boolean isTiff = false;
		if (extension.equalsIgnoreCase("TIF") || extension.equalsIgnoreCase("TIFF")){
			isTiff = true;
		}
		return isTiff;
	}

	/**
	 * Método que almacena un documento y nos devuelve su DocumentUID en el
	 * gestor documental.
	 *
	 * En la caso de que el documento a adjuntar sea por referencia de su
	 * docuid, comprobamos que ese documento existe en el gestor documental
	 *
	 * @param bookID
	 * @param folderId
	 * @param axsf
	 * @param file
	 * @param document
	 * @param bookTypeConf
	 * @param miscFormat
	 * @param imageAuth
	 * @param totalNumPages
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	private static String saveDocument(Integer bookID, int folderId, AxSf axsf,
			FlushFdrFile file, FlushFdrDocument document,
			BookTypeConf bookTypeConf, BookConf bookConf,
			MiscFormat miscFormat, int imageAuth, int totalNumPages,
			String entidad) throws Exception {
		String docUID = null;
		if (ISFileUtil.isFileWithContent(file.getBuffer(),
				file.getFileNameFis(), file.getFileID())) {
			docUID = saveDocumentToRepository(bookID, folderId, axsf, document,
					file, bookTypeConf, bookConf, miscFormat, imageAuth,
					totalNumPages, entidad);
		} else {
			docUID = saveExistDocument(file, bookID, entidad, false);
		}

		return docUID;
	}

	/**
	 * Método que almacena en la base de datos los "metadatos" del documento que
	 * se almacenado
	 *
	 * @param session
	 * @param bookID
	 * @param folderId
	 * @param userId
	 * @param docUID
	 * @param fileID
	 * @param file
	 * @param document
	 * @param timestamp
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	private static FlushFdrFile savePageData(Session session, Integer bookID,
			Integer folderId, Integer userId, String docUID, int fileID,
			FlushFdrFile file, FlushFdrDocument document, Timestamp timestamp,
			String entidad) throws Exception {

		int pageID = createPage(file, bookID, folderId, userId,
				document.getDocID(), fileID, timestamp, entidad);
		savePageInfo(session, file, bookID, folderId, docUID, pageID, entidad);

		file.setFileID(docUID);
		file.setPageID(Integer.toString(pageID));

		return file;
	}

	/**
	 * Método que almacena un documento en un repositorio documental y nos
	 * devuelve su uid
	 *
	 * @param bookID
	 * @param folderId
	 * @param axsf
	 * @param document
	 * @param file
	 * @param bookTypeConf
	 * @param miscFormat
	 * @param imageAuth
	 * @param totalNumPages
	 * @param entidad
	 * @return
	 * @throws TecDocException
	 * @throws Exception
	 */
	private static String saveDocumentToRepository(Integer bookID,
			int folderId, AxSf axsf, FlushFdrDocument document,
			FlushFdrFile file, BookTypeConf bookTypeConf, BookConf bookConf,
			MiscFormat miscFormat, int imageAuth, int totalNumPages,
			String entidad) throws TecDocException, Exception {

		// BookConf bookConf =
		// ConfiguratorBook.getInstance(entidad).getBookConf(bookID);
		file = signDocument(document, file, bookTypeConf, bookConf, imageAuth,
				totalNumPages);

		InputStream inputStream = ISFileUtil.getInputStream(file.getBuffer(),
				file.getFileNameFis());

		ISRepositoryCreateDocumentVO createVO = ISRepositoryDocumentHelper
				.getRepositoryCreateDocumentVO(bookID, new Integer(folderId),
						new Integer(document.getDocID()),
						file.getFileNameLog(), file.getFileNameFis(),
						file.getExtension(), miscFormat.getId(), axsf,
						inputStream, entidad, false);

		ISRepositoryCreateDocumentVO documentCreatedVO = RepositoryFactory
				.getCurrentPolicy().createDocument(createVO);

		String docUID = null;
		if (documentCreatedVO != null) {
			docUID = documentCreatedVO.getIsicresDocUID();
			if (documentCreatedVO.getFileContent() != null) {
				file.setBuffer(documentCreatedVO.getFileContent());
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("fileId creado => " + docUID);
			log.debug("creando page docID => " + document.getDocID());
		}

		return docUID;
	}

	/**
	 * Método que almacena en base de datos un documento ya existente en el
	 * gestor documental
	 *
	 * @param file
	 * @param bookID
	 * @param entidad
	 * @param closeSession
	 * @return
	 * @throws TecDocException
	 * @throws Exception
	 */
	private static String saveExistDocument(FlushFdrFile file, Integer bookID,
			String entidad, boolean closeSession) throws TecDocException,
			Exception {

		ISRepositoryRetrieveDocumentVO saveExistVO = ISRepositoryDocumentHelper
				.getRepositorySaveExistDocument(bookID, file.getFileID(),
						entidad, closeSession);

		ISRepositoryRetrieveDocumentVO documentSavedVO = RepositoryFactory
				.getCurrentPolicy().saveExistDocument(saveExistVO);

		String docUID = null;
		if (documentSavedVO != null) {
			docUID = documentSavedVO.getDocumentUID();
			file.setBuffer(documentSavedVO.getFileContent());
		}

		if (log.isDebugEnabled()) {
			log.debug("fileID creado => " + docUID);
		}

		return docUID;
	}

	/**
	 * Método que obtiene el codigo hash de un documento
	 *
	 * @param file
	 * @param bookId
	 * @param folderId
	 * @param fileId
	 * @param pageId
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	private static String getHashDocument(FlushFdrFile file, Integer bookId,
			Integer folderId, int pageId, String entidad) throws Exception {

		byte[] fileContent = ISFileUtil.inputStream2byteArray(ISFileUtil
				.getInputStream(file.getBuffer(), file.getFileNameFis()));

		String hash = CryptoUtils.encryptHashDocument(fileContent);

		return hash;
	}
}
