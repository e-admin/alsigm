package ieci.tecdoc.sgm.registropresencial.register;

import gnu.trove.THashMap;
import ieci.tecdoc.sgm.core.services.registro.Interested;
import ieci.tecdoc.sgm.registropresencial.autenticacion.Login;
import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.info.InfoOffice;
import ieci.tecdoc.sgm.registropresencial.info.InfoRegister;
import ieci.tecdoc.sgm.registropresencial.utils.Keys;
import ieci.tecdoc.sgm.registropresencial.utils.SigemFunctions;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.idoc.decoder.query.QueryFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderDataSession;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.session.validation.ValidationSessionEx;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class RegisterServices implements Keys, ServerKeys, HibernateKeys {

	 private static Logger _logger = Logger.getLogger(RegisterServices.class);

	 /**
	  *	Metodo que crea un registro
	  *
	  * @param user - Datos del usuario
	  * @param bookId - Id del libro
	  * @param atts - Campos con la informacion del nuevo registro
	  * @param inter - Listado de Interesados
	  * @param documents - Coleccion de Documentos anexados al registro
	  * @param entidad
	  * @param consolidacion - Indica si es un proceso de consolidacion
	  *
	  * @return {@link InfoRegister}
	  *
	  * @throws ValidationException
	  * @throws SecurityException
	  * @throws BookException
	  * @throws SessionException
	  * @throws TecDocException
	  * @throws ParseException
	  * @throws AttributesException
	  */
	public static InfoRegister createFolder(User user, Integer bookId,
			List atts, List inter, Map documents, String entidad,
			boolean consolidacion) throws ValidationException,
			SecurityException, BookException, SessionException,
			TecDocException, ParseException, AttributesException {

		InfoRegister result = new InfoRegister();
		String sessionID = new String();

		try {
			//logeamos al usuario
			sessionID = Login.login(user, entidad);

			// si nos llega la oficina
			// de registro para logear al usuario con dicha oficina
			setOfficeUserRegister(entidad, sessionID, atts);

			//abrimos el libro
			BookSession.openBook(sessionID, bookId, entidad);

			// obtenemos si se distribuye los registros de salida con destino a
			// una unidad administrativa de tipo propio (0 - NO / 1- SI)
			Integer launchDistOutRegister = RegisterServicesUtil
					.getInvesConfActions(entidad);

			//comprobamos los permisos
			RegisterServicesUtil.canCreateFolder(sessionID, bookId, documents,
					user, atts, entidad, consolidacion);

			//obtenemos el formato del registro
			AxSf axsfQ = BookSession.getFormFormat(sessionID, bookId, entidad);
			FieldFormat fieldFormat = RegisterServicesUtil.getFieldFormat(
					sessionID, bookId);

			// traducimos el valor de los campos validados de los codigos
			// introducidos a sus respectivos IDs
			Map translatedIds = RegisterServicesUtil.getFieldsWithSustitute(
					atts, axsfQ, sessionID, bookId, entidad);

			//generamos el objeto AxSF  segun el tipo de libro (ENTRADA/SALIDA)
			AxSf newAxSF = RegisterServicesUtil.initInOrOutFolder(user, axsfQ);
			//completamos los datos del registro
			newAxSF = RegisterServicesUtil
					.completeFolder(translatedIds, axsfQ, newAxSF, user
							.getLocale(), atts, fieldFormat, consolidacion);

			//se genera el nuevo registro
			FolderDataSession data = Register.createNewFolder(sessionID,
					bookId, newAxSF, inter, documents, launchDistOutRegister,
					user.getLocale(), entidad, consolidacion);

			//obtenemos la informacion del registro
			result = ConsultRegister.consultRegisterInfo(bookId, data
					.getAxsfNew(), data.getNewRegisterID(), data.getScrofic(),
					data.getUserName(), user.getLocale());

		} finally {
			BookSession.closeBook(sessionID, bookId);
			SecuritySession.logout(sessionID, entidad);
		}

		return result;
	}

	/**
	 * Metodo que comprueba si se pasa como parametro la oficina (FLD5), en caso afirmativo,
	 * dicha oficina será con la que el usuario registrará, siempre y cuando que tenga permisos para ello
	 *
	 * @param atts - Listado de Campos
	 * @param entidad
	 * @param sessionID
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private static void setOfficeUserRegister(String entidad,
			String sessionID, List atts) throws SessionException, ValidationException {
		for(Iterator it = atts.iterator();it.hasNext();){
			FlushFdrField flushFdrField = (FlushFdrField) it.next();

			if (flushFdrField.getFldid() == AxSf.FLD5_FIELD_ID
					&& StringUtils.isNotBlank(flushFdrField.getValue())) {
				//Cambiamos la oficina preferente por la oficina que llega como parametro
				ValidationSessionEx.validateOfficeCode(sessionID,
						flushFdrField.getValue(), entidad);
			}
		}
	}

	/**
	 * Metodo que actualiza un registro
	 *
	 * @param user - Usuario
	 * @param bookId - ID del libro
	 * @param folderId - ID del Registro
	 * @param changeFields - Campos que han sido modificados
	 * @param inter - Interesados
	 * @param documents - Documentos a guardar
	 * @param entidad
	 *
	 * @return {@link InfoRegister}
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 * @throws ParseException
	 * @throws AttributesException
	 */
	public static InfoRegister updateFolder(User user, Integer bookId,
			Integer folderId, List changeFields, List inter, Map documents,
			String entidad) throws ValidationException, SecurityException,
			BookException, SessionException, TecDocException, ParseException,
			AttributesException {

		InfoRegister result = new InfoRegister();
		String sessionID = new String();

		try {
			//logeamos al usuario
			sessionID = Login.login(user, entidad);

			//abrimos el libro
			BookSession.openBook(sessionID, bookId, entidad);

			// obtenemos si se distribuye los registros de salida con destino a
			// una unidad administrativa de tipo propio (0 - NO / 1- SI)
			Integer launchDistOutRegister = RegisterServicesUtil
					.getInvesConfActions(entidad);

			int folderIdInt = -1;
			if (folderId != null) {
				folderIdInt = folderId.intValue();
			}

			//comprobamos permisos de usuario
			RegisterServicesUtil.canUpdateFolder(sessionID, bookId,
					folderIdInt, user, changeFields, documents, entidad);

			//obtenemos el formato del registro
			AxSf axsfQ = BookSession.getFormFormat(sessionID, bookId, entidad);
			FieldFormat fieldFormat = RegisterServicesUtil.getFieldFormat(
					sessionID, bookId);

			// traducimos el valor de los campos validados de los codigos
			// introducidos a sus respectivos IDs
			Map translatedIds = RegisterServicesUtil.getFieldsWithSustitute(
					changeFields, axsfQ, sessionID, bookId, entidad);

			//generamos el objeto AxSF  segun el tipo de libro (ENTRADA/SALIDA)
			AxSf newAxSF = RegisterServicesUtil.initInOrOutFolder(user, axsfQ);
			//completamos los datos del registro
			newAxSF = RegisterServicesUtil
					.completeFolder(translatedIds, axsfQ, newAxSF, user
							.getLocale(), changeFields, fieldFormat, false);

			//comprobamos que ficheros se van a subir
			omitirFicherosSubidosAnt(bookId, folderId, documents, entidad,
					sessionID);

			//actualizamos el registro
			FolderDataSession data = Register.updateFolder(sessionID, bookId,
					newAxSF, folderId, inter, documents, launchDistOutRegister,
					user.getLocale(), entidad);

			//obtenemos la informacion del registro
			result = ConsultRegister.consultRegisterInfo(bookId, data
					.getAxsfOld(), folderId.intValue(), data.getScrofic(), data
					.getUserName(), user.getLocale());

		} finally {
			//cerramos la session del usuario
			BookSession.closeBook(sessionID, bookId);
			SecuritySession.logout(sessionID, entidad);
		}
		return result;
	}


	/**
	 * Metodo que elimina del array de documentos los ficheros que ya han sido subidos con anterioridad
	 *
	 * @param bookId - ID del libro
	 * @param folderId - ID del registro
	 * @param documents - Array de {@link FlushFdrDocument} con los documentos a comprobar
	 * @param entidad
	 * @param sessionID
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	private static void omitirFicherosSubidosAnt(Integer bookId,
			Integer folderId, Map documents, String entidad, String sessionID)
			throws BookException, SessionException, ValidationException {
		//obtenemos los ficheros del registro almacenados
		List docsFolder = FolderFileSession.getBookFolderDocsWithPages(sessionID, bookId, folderId.intValue(), entidad);

		//recorremos cada documento almacenado del registro
		for(Iterator it=docsFolder.iterator();it.hasNext();){
			AxDoch axdoch = (AxDoch) it.next();

			//obtenemos el documento a adjuntar al registro
			FlushFdrDocument documento = (FlushFdrDocument) documents.get(axdoch.getName());

			if(documento != null){
				//recorremos las paginas del documento almacenado
				for(Iterator pages = axdoch.getPages().iterator();pages.hasNext();){
					AxPageh page = (AxPageh) pages.next();

					//recorremos las paginas del documento pendiente de subir
					for(Iterator pagesDoc = documento.getPages().iterator(); pagesDoc.hasNext();){
						FlushFdrPage paginaDocumento = (FlushFdrPage) pagesDoc.next();
						//si existen ficheros identicos tanto subidos como pendientes de subir
						if(page.getName().equals(paginaDocumento.getPageName())){
							_logger.warn("El fichero ["
										+ paginaDocumento.getPageName()
										+ "] en el documento ["
										+ documento.getDocumentName()
										+ "] para el registro con ID["
										+ folderId
										+ "] y bookID ["
										+ bookId
										+ "] ya ha sido adjuntado con anterioridad");
							//omitimos el fichero a subir, el fichero ya ha sido subido con anterioridad
							pagesDoc.remove();
						}
					}
				}
			}
		}
	}

	/**
	 * Metodo que busca registros
	 *
	 * @param user - Datos del usuario
	 * @param bookId - Id del libro
	 * @param searchFields - Listado de {@link axSfQueryField} con la informacion con la que se realiza la busqueda
	 * @param entidad
	 *
	 * @return Array de objetos {@link InfoRegister}
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 * @throws ParseException
	 * @throws AttributesException
	 */
	public static InfoRegister[] findFolder(User user, Integer bookId,
			List searchFields, String entidad) throws ValidationException,
			SecurityException, BookException, SessionException,
			TecDocException, ParseException, AttributesException {

		InfoRegister[] result = null;
		String sessionID = new String();
		try {
			sessionID = Login.login(user, entidad);

			BookSession.openBook(sessionID, bookId, entidad);

			//obtenemos el formato de la consulta
			AxSf axsfQ = RegisterServicesUtil.getQueryFormat(sessionID, bookId,
					entidad);
			QueryFormat formatter = new QueryFormat(axsfQ.getFormat().getData());
			Collection formatterFields = formatter.getDlgDef().getCtrldefs()
					.values();

			//validamos que consulta a realizar sea correcta
			RegisterServicesUtil.validateQuery(sessionID, bookId, axsfQ,
					searchFields, entidad, user.getLocale(), formatterFields);

			// traducimos el valor de los campos validados de los codigos
			// introducidos a sus respectivos IDs
			Map translatedIds = RegisterServicesUtil
					.getQueryFieldsWithSustitute(sessionID, bookId, axsfQ,
							searchFields, entidad, user.getLocale(),
							formatterFields);

			// traducimos los operadores de la busqueda y los valores
			// introducido (si llevan caracteres reservados como %), se indica
			// la ordenacion
			// de los resultados y se comprueba si debemos traducir los valores
			// de busqueda a CS
			AxSfQuery axsfQuery = RegisterServicesUtil.getQueryFolder(
					sessionID, bookId, searchFields, axsfQ, translatedIds, user
							.getLocale(), entidad);

			//realizamos la busqueda
			result = Register.findFolder(sessionID, bookId, axsfQuery, user
					.getLocale(), entidad);

		} finally {
			BookSession.closeBook(sessionID, bookId);
			SecuritySession.logout(sessionID, entidad);
		}

		return result;
	}

	/**
	 * Metodo que importa un registro
	 *
	 * @param user - Datos del usuario
	 * @param bookId - Id del libro
	 * @param atts - Listado de campos con la informacion que compone el registro
	 * @param inter - Listado de los interesados del registro
	 * @param documents - Listado de documentos del registro
	 * @param entidad
	 *
	 * @return {@InfoRegister}
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 * @throws ParseException
	 * @throws AttributesException
	 */
	public static InfoRegister importFolder(User user, Integer bookId,
			List atts, List inter, Map documents, String entidad)
			throws ValidationException, SecurityException, BookException,
			SessionException, TecDocException, ParseException,
			AttributesException {

		InfoRegister result = null;
		String sessionID = null;

		try {
			sessionID = Login.login(user, entidad);

			BookSession.openBook(sessionID, bookId, entidad);

			// obtenemos si se distribuye los registros de salida con destino a
			// una unidad administrativa de tipo propio (0 - NO / 1- SI)
			Integer launchDistOutRegister = RegisterServicesUtil
					.getInvesConfActions(entidad);

			//comprobamos permisos de usuario
			RegisterServicesUtil.canCreateFolder(sessionID, bookId, documents,
					user, atts, entidad, false);

			//obtenemos el formato del registro
			AxSf axsfQ = BookSession.getFormFormat(sessionID, bookId, entidad);
			FieldFormat fieldFormat = RegisterServicesUtil.getFieldFormat(
					sessionID, bookId);

			Map translatedIds = RegisterServicesUtil.getFieldsWithSustitute(
					atts, axsfQ, sessionID, bookId, entidad);

			//incializamos el objeto AxSF segun el tipo de libro (ENTRADA / SALIDA)
			AxSf newAxSF = RegisterServicesUtil.initInOrOutFolder(user, axsfQ);
			//completamos los datos del registro a importar
			newAxSF = RegisterServicesUtil.completeFolderImport(translatedIds,
					axsfQ, newAxSF, user.getLocale(), atts, fieldFormat);

			//importamos el registro
			FolderDataSession data = Register.importNewFolder(sessionID,
					bookId, newAxSF, inter, documents, launchDistOutRegister,
					user.getLocale(), entidad);

			//obtenemos la informacion del registro importado
			result = ConsultRegister.consultRegisterInfo(bookId, data
					.getAxsfNew(), data.getNewRegisterID(), data.getScrofic(),
					data.getUserName(), user.getLocale());

		} finally {
			BookSession.closeBook(sessionID, bookId);
			SecuritySession.logout(sessionID, entidad);
		}
		return result;
	}

	/**
	 *
	 * Metodo que devuelve un objeto Map con toda la información del registro solicitado.
	 *
	 * @param user - Datos del usuario
	 * @param folderNumber - Numero del Registro
	 * @param type - indica el tipo de libro (1-Entrada / 2-Salida)
	 * @param oficAsoc - Oficina
	 * @param entidad
	 *
	 * @return Coleccion de objetos con los datos del Registro
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws AttributesException
	 * @throws TecDocException
	 */
	public static Map getFolderForNumber(User user, String folderNumber,
			int type, boolean oficAsoc, String entidad)
			throws ValidationException, SecurityException, BookException,
			SessionException, AttributesException, TecDocException {
		AxSfQueryResults queryResults = null;
		String sessionID = null;
		Map folderInfo = new HashMap();

		try {
			sessionID = Login.login(user, entidad);

			//generamos la consulta a realizar a partir del numero de registro
			AxSfQuery axsfQuery = RegisterServicesUtil
					.getAxSfQueryFolderNumber(folderNumber);

			//obtenemos el id de los libros en los que realizaremos la busqueda
			List bookIds = RegisterServicesUtil.getBookIdList(sessionID, type,
					oficAsoc, user.getLocale(), entidad);

			// Linea añadida para que se puedan consultar libros sin acceso
			// donde te han distribuido registros.
			bookIds.addAll(SigemFunctions.booksDistributed(sessionID,
					folderNumber, user.getLocale(), entidad, type));

			// se preparan la consulta a realizar y comprobamos si esta va a
			// tener resultados
			queryResults = RegisterServicesUtil.openRegistersQuery(sessionID,
					axsfQuery, bookIds, entidad);

			if (queryResults != null) {
				// si obtenemos resultados ejecutamos la busqueda para obtener
				// los datos del registro o registros
				folderInfo = RegisterServicesUtil.getFolderInfoByFolderNumber(
						sessionID, queryResults, axsfQuery, user.getLocale(),
						entidad);
			} else {
				// devolvemos una excepcion informando que la busqueda no
				// obtiene resultados
				throw new BookException(
						BookException.ERROR_CANNOT_FIND_REGISTERS);
			}
		} finally {
			BookSession.closeBook(sessionID, queryResults.getBookId());
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}
		return folderInfo;
	}

	/**
	 *
	 * Metodo que devuelve un array de bytes correspondiente al documento solicitado.
	 *
	 * @param user - Datos del usuario
	 * @param bookID - Id del libro
	 * @param folderId - Id del registro
	 * @param docID - Id del documento a buscar
	 * @param pageID - Id de la pagina a buscar
	 * @param entidad
	 *
	 * @return Array de bytes con el documento buscado
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public static byte[] getDocumentFromFolder(User user, Integer bookID,
			Integer folderId, int docID, int pageID, String entidad)
			throws ValidationException, SecurityException, BookException,
			SessionException, TecDocException {
		byte[] result = null;
		String sessionID = null;

		try {
			sessionID = Login.login(user, entidad);

			//abrimos el libro
			BookSession.openBook(sessionID, bookID, entidad);

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			//obtenemos la informacion del libro  de la cache
			THashMap bookInformation = (THashMap) cacheBag.get(bookID);

			//obtenemos los permisos del usuario sobre el libro
			ISicresAPerms aPerms = (ISicresAPerms) bookInformation
					.get(APERMS_USER);
			ConfiguratorInvesicres.getInstance(entidad).getInvesicresConf();

			// Segunda comparación añadida para que usuarios sin permiso pero a
			// quienes les han distribuido registros puedan consultarlos
			if (aPerms.canQuery()
					|| SigemFunctions.isDistributed(sessionID, bookID,
							folderId, user.getLocale(), entidad)) {

				//realizamos la busqueda del fichero
				result = FolderFileSession.getFile(sessionID, bookID, folderId,
						pageID, entidad);
			} else {
				throw new BookException(BookException.ERROR_CANNOT_LOAD_FILE);
			}
		} catch (Exception e) {
			throw new BookException(BookException.ERROR_CANNOT_LOAD_FILE);
		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}
		return result;
	}

	/**
	 * Metodo que añade un documento (y sus paginas) al registro indicado
	 *
	 * @param user - Datos del usuario
	 * @param bookId - Id del libro
	 * @param folderId - Id del registro
	 * @param documents - Coleccion de documentos para anexar al registro
	 * @param entidad
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 * @throws ParseException
	 * @throws AttributesException
	 */
	public static void addDocument(User user, Integer bookId, Integer folderId,
			Map documents, String entidad) throws ValidationException,
			SecurityException, BookException, SessionException,
			TecDocException, ParseException, AttributesException {

		String sessionID = new String();

		try {
			sessionID = Login.login(user, entidad);

			BookSession.openBook(sessionID, bookId, entidad);

			int folderIdInt = -1;
			if (folderId != null) {
				folderIdInt = folderId.intValue();
			}

			//comprobamos permisos de usuario
			RegisterServicesUtil.canUpdateFolder(sessionID, bookId,
					folderIdInt, user, null, documents, entidad);

			//obtenemos formato del registro
			AxSf axsfQ = BookSession.getFormFormat(sessionID, bookId, entidad);
			FieldFormat fieldFormat = RegisterServicesUtil.getFieldFormat(
					sessionID, bookId);

			Map translatedIds = RegisterServicesUtil.getFieldsWithSustitute(
					null, axsfQ, sessionID, bookId, entidad);

			//formamos el registro
			AxSf newAxSF = RegisterServicesUtil.initInOrOutFolder(user, axsfQ);
			newAxSF = RegisterServicesUtil.completeFolder(translatedIds, axsfQ,
					newAxSF, user.getLocale(), null, fieldFormat, false);

			//añadimos los documentos
			Register.addDocument(sessionID, bookId, folderId, documents,
					newAxSF, user.getLocale(), entidad);

		} finally {
			BookSession.closeBook(sessionID, bookId);
			SecuritySession.logout(sessionID, entidad);
		}

	}

	/**
	 * Metodo que obtiene los interesados de un registro
	 *
	 * @param user - Datos del Usuario
	 * @param bookId - Id del libro
	 * @param fdrId - Id del registro
	 * @param entidad
	 *
	 * @return Listado de interesados {@link Interested}
	 *
	 * @throws Exception
	 */
	public static Interested[] getInterestedRegister(User user, Integer bookId,
			Integer fdrId, String entidad) throws Exception {

		String sessionID = new String();

		try {
			sessionID = Login.login(user, entidad);

			BookSession.openBook(sessionID, bookId, entidad);

			if ((bookId != null) && (fdrId.intValue() > 0)) {
				//realizamos la busqueda de los interesados
				return RegisterServicesUtil.getInterestedForFolder(sessionID, bookId,
						fdrId, entidad);
			}

			return null;
		} finally {
			BookSession.closeBook(sessionID, bookId);
			SecuritySession.logout(sessionID, entidad);
		}

	}

	/**
	 * Metodo que comprueba los permisos de creaccion de registros
	 *
     * @param user - Datos del usuario
     * @param type - indica el tipo de libro (1-Entrada / 2-Salida)
     * @param oficAsoc - Oficina
     * @param onlyOpenBooks - Indica si el libro debe estar abierto (0-Abierto) o en otro estado (1-Cerrado)
     * @param entidad
     *
     * @return boolean - TRUE: Si tiene permisos para crear registros
     * 					FALSE: No tiene permisos para crear registros
     *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 * @throws TecDocException
	 */
	public static List getBooksCanCreateFolder(User user, int type,
			boolean oficAsoc, boolean onlyOpenBooks, String entidad)
			throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException,
			TecDocException {
		String sessionID = null;
		List result = null;

		try {
			sessionID = Login.login(user, entidad);

			//obtenemos el listado de libros
			List books = RegisterServicesUtil.getBookList(sessionID, type,
					oficAsoc, onlyOpenBooks, IDocKeys.IUSEROBJPERM_CREATE_PERM,
					user.getLocale(), entidad);

			//formateamos el listado de libros(ScrRegState) a un listado de objetos InfoBook
			result = ConsultRegister.consultBookInfo(books);

		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}

		return result;
	}

    /**
     * Metodo que obtiene las oficinas del usuario donde tiene permisos para crear registros
     *
     * @param user - Datos del usuario
     * @param bookID - Id del libro a buscar
     * @param entidad
     *
     * @return Listado de objetos {@link InfoOffice}
     *
	 * @throws BookException
	 * @throws SecurityException
	 * @throws SessionException
	 * @throws ValidationException
	 * @throws TecDocException
	 */
	public static List getOfficesCanCreateFolder(User user, Integer bookID,
			String entidad) throws BookException, SecurityException,
			SessionException, ValidationException, TecDocException {
		String sessionID = null;
		List result = null;

		try {
			sessionID = Login.login(user, entidad);

			//obtenemos el listado de oficinas
			List offices = RegisterServicesUtil.getOfficeCanCreateRegister(
					sessionID, bookID, entidad);

			//adaptamos el listado de oficina (ScrOfic) a un listado de objetos InfoOffice
			result = ConsultRegister.consultOfficeInfo(offices);

		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}

		return result;
	}

	public static boolean existMatterTypeInOffice(User user,
			String matterTypeCode, String officeCode, String entidad)
			throws SecurityException, SessionException, ValidationException, TecDocException {

		String sessionID = null;

		try {
			sessionID = Login.login(user, entidad);

			return RegisterServicesUtil.existMatterTypeInOffice(sessionID, matterTypeCode, officeCode, entidad);

		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}
	}

}
