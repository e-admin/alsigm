package ieci.tecdoc.sgm.migration.utils;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;


import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentDto;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentPageDto;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentsPagesDto;
import ieci.tecdoc.sgm.registropresencial.ws.server.BookId;
import ieci.tecdoc.sgm.registropresencial.ws.server.Document;
import ieci.tecdoc.sgm.registropresencial.ws.server.Documents;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Fields;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Page;
import ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService;
import ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;

public class UtilsService extends Utils {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(UtilsService.class);

	/**
	 * Recupera los datos del usuario de registro de SIGEM Housing a 
	 * partir del fichero de configuración de la aplicación
	 * @return UserInfo - Objeto con los datos del usuario
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static UserInfo getOrigenUser() throws MigrationException {
		Config config = Config.getInstance();
		UserInfo oUser = new UserInfo();
		oUser.setUserName(config.getOrigenUser());
		oUser.setPassword(config.getOrigenPassword());
		oUser.setLocale(config.getOrigenIdioma());
		return oUser;
	}
	
	/**
	 * Recupera los datos del usuario de registro de SIGEM UAM a 
	 * partir del fichero de configuración de la aplicación
	 * @return UserInfo - Objeto con los datos del usuario
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static UserInfo getDestinoUser() throws MigrationException {
		Config config = Config.getInstance();
		UserInfo oUser = new UserInfo();
		oUser.setUserName(config.getDestinoUser());
		oUser.setPassword(config.getDestinoPassword());
		oUser.setLocale(config.getDestinoIdioma());
		return oUser;
	}

	/**
	 * Recupera la entidad de la aplicación
	 * @param idEntidad - Código de la entidad
	 * @return Entidad - Entidad cliente
	 */
	public static Entidad getEntidad(String idEntidad) {
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		return entidad;
	}
	
	/**
	 * Establece la carpeta del registro de entrada con el número de registro 
	 * @param registerNumber - Número de re gistro
	 * @return Folder - Carpeta del registro 
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static Folder getNumerRegisterEntrada(String registerNumber) throws MigrationException {
		Folder folder = new Folder();
		folder.setBookId(new BookId(Config.getInstance().getOrigenIdBookEntrada()));
		folder.setFolderNumber(registerNumber);
		return folder;
	}
	
	/**
	 * Establece la carpeta del registro de salida con el número de registro 
	 * @param registerNumber - Número de re gistro
	 * @return Folder - Carpeta del registro 
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static Folder getNumerRegisterSalida(String registerNumber) throws MigrationException {
		Folder folder = new Folder();
		folder.setBookId(new BookId(Config.getInstance().getOrigenIdBookSalida()));
		folder.setFolderNumber(registerNumber);
		return folder;
	}
	
	/**
	 * Establece los criterios de búsqueda de registros de entrada
	 * @return FolderSearchCriteria - Criterios de búsqueda
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static FolderSearchCriteria getFolderCriteriaEntrada() throws MigrationException {
		FolderSearchCriteria folderCriteria = new FolderSearchCriteria();
		Config config = Config.getInstance();
		BookId bookId = new BookId();
		bookId.setBookId(config.getOrigenIdBookEntrada());
		folderCriteria.setBookId(bookId);
		FieldsSearchCriteria fields = getFieldsCriteria();
		folderCriteria.setFields(fields);
		return folderCriteria;
	}
	
	/**
	 * Establece los criterios de búsuqeda de registros de salida
	 * @return FolderSearchCriteria - Criterios de búsqueda
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static FolderSearchCriteria getFolderCriteriaSalida() throws MigrationException {
		FolderSearchCriteria folderCriteria = new FolderSearchCriteria();
		Config config = Config.getInstance();
		BookId bookId = new BookId();
		bookId.setBookId(config.getOrigenIdBookSalida());
		folderCriteria.setBookId(bookId);
		FieldsSearchCriteria fields = getFieldsCriteria();
		folderCriteria.setFields(fields);
		return folderCriteria;
	}
	
	/**
	 * Devuelve true si el SW ha respondido satisfactoriamente, false en caso contrario
	 * @param errorCode - Códio de error
	 * @param returnCode - Código de respuesta del SW [OK, ERROR]
	 * @return (boolean) - True: correcto; False: error 
	 */
	public static boolean isResponeOK(String errorCode, String returnCode) {
		if(errorCode == null && returnCode.equals(Constantes.RETURN_CODE_OK))
			return true;
		else return false;
	}
	
	/**
	 * Establece un tipo BookId a partir del Identificador del libro
	 * @param bookId - Identificador del libro
	 * @return BookId - Libro de registro
	 */
	public static BookId getBookId(String bookId) {
		BookId bookID = new BookId();
		bookID.setBookId(bookId);
		return bookID;
	}
	
	/**
	 * Crea una carpeta de registro de entrada con sus campos y documentos
	 * @param folderRegisterOrigen - Carpeta de registro porcedente de Sigem Housing
	 * @param documentsPage - Documentos y páginas del registro 
	 * @return Folder - Carpeta del registro
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static Folder getFolderDestinoEntrada(Folder folderRegisterOrigen, DocumentsPagesDto documentsPage) throws MigrationException {
		
		Folder folderRegisterDestino = new Folder();
		Documents documentos = new Documents();
		Fields fields = new Fields();
		//folderRegisterDestino.setBookId(folderRegisterOrigen.getBookId());
		folderRegisterDestino.setBookId(getBookId(Config.getInstance().getDestinoIdBookEntrada()));
		FieldInfo[] atts = Utils.getFields(folderRegisterOrigen.getFields(), folderRegisterOrigen.getBookId().getBookId());
		fields.setFields(atts);
		folderRegisterDestino.setFields(fields);
		documentos.setDocuments(Utils.getDocuments(documentsPage));
		folderRegisterDestino.setDocumentos(documentos);
		return folderRegisterDestino;
	}
	
	
	/**
	 * Crea una carpeta de registro de salida con sus campos y documentos
	 * @param folderRegisterOrigen - Carpeta de registro porcedente de Sigem Housing
	 * @param documentsPage - Documentos y páginas del registro 
	 * @return Folder - Carpeta del registro
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static Folder getFolderDestinoSalida(Folder folderRegisterOrigen, DocumentsPagesDto documentsPage) throws MigrationException {
		
		Folder folderRegisterDestino = new Folder();
		Documents documentos = new Documents();
		Fields fields = new Fields();
		//folderRegisterDestino.setBookId(folderRegisterOrigen.getBookId());
		folderRegisterDestino.setBookId(getBookId(Config.getInstance().getDestinoIdBookSalida()));
		FieldInfo[] atts = Utils.getFields(folderRegisterOrigen.getFields(), folderRegisterOrigen.getBookId().getBookId());
		fields.setFields(atts);
		folderRegisterDestino.setFields(fields);
		documentos.setDocuments(Utils.getDocuments(documentsPage));
		folderRegisterDestino.setDocumentos(documentos);
		return folderRegisterDestino;
		
	}
	
	
	/**
	 * Crea una carpeta de registro de entrada con sus campos y documentos
	 * @param folderRegisterOrigen - Carpeta de registro porcedente de Sigem Housing
	 * @param documentsPage - Documentos y páginas del registro 
	 * @return Folder - Carpeta del registro
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static Folder getFolderOrigenEntrada(Folder folderRegisterOrigen, DocumentsPagesDto documentsPage) throws MigrationException {
		
		Folder folderRegisterOrigenUpdate = new Folder();
		Documents documentos = new Documents();
		Fields fields = new Fields();
		folderRegisterOrigenUpdate.setBookId(getBookId(Config.getInstance().getOrigenIdBookEntrada()));
		FieldInfo[] atts = Utils.getFields(folderRegisterOrigen.getFields(), folderRegisterOrigen.getBookId().getBookId());
		fields.setFields(atts);
		folderRegisterOrigenUpdate.setFields(fields);
		documentos.setDocuments(Utils.getDocuments(documentsPage));
		folderRegisterOrigenUpdate.setDocumentos(documentos);
		return folderRegisterOrigenUpdate;
	}
	
	
	/**
	 * Crea una carpeta de registro de salida con sus campos y documentos
	 * @param folderRegisterOrigen - Carpeta de registro porcedente de Sigem Housing
	 * @param documentsPage - Documentos y páginas del registro 
	 * @return Folder - Carpeta del registro
	 * @throws MigrationException - Se lanza en caso de que se produzca algún tipo de error
	 */
	public static Folder getFolderOrigenSalida(Folder folderRegisterOrigen, DocumentsPagesDto documentsPage) throws MigrationException {
		
		Folder folderRegisterOrigenUpdate = new Folder();
		Documents documentos = new Documents();
		Fields fields = new Fields();
		//folderRegisterDestino.setBookId(folderRegisterOrigen.getBookId());
		folderRegisterOrigenUpdate.setBookId(getBookId(Config.getInstance().getOrigenIdBookSalida()));
		FieldInfo[] atts = Utils.getFields(folderRegisterOrigen.getFields(), folderRegisterOrigen.getBookId().getBookId());
		fields.setFields(atts);
		folderRegisterOrigenUpdate.setFields(fields);
		documentos.setDocuments(Utils.getDocuments(documentsPage));
		folderRegisterOrigenUpdate.setDocumentos(documentos);
		return folderRegisterOrigenUpdate;
		
	}
	
	
	/**
	 * Castea los documentos que devuelve el SW de Registro en un Objeto de tipo DocumentsPagesDto
	 * @param oServicioOrigen - SW de registro desplegado en SIGEM Housing
	 * @param userOrigen - Usuario de registro de registro de SIGEM Housing
	 * @param entidadOrigen - Entidad de registro de SIGEM Housing
	 * @param bookId - Identificador de libro
	 * @param folderRegisterOrigen - Carpeta del registro de SIGEM Housing
	 * @return DocumentsPagesDto - Contiene los documentos y páginas del registro
	 * @throws RemoteException - Se lanza en caso de que se produzca algún error en la invocación al SW 
	 */
	public static DocumentsPagesDto getDocuments(ServicioRegistroWebService oServicioOrigen, UserInfo userOrigen, Entidad entidadOrigen, String bookId, Folder folderRegisterOrigen) throws RemoteException {
		
		DocumentsPagesDto documetsPages = new DocumentsPagesDto();
		DocumentPageDto[] documentPageDtoArray = null; 
		DocumentPageDto documentPageDto = null;
		DocumentDto[] documentDtoArray = null;
		
		ieci.tecdoc.sgm.registropresencial.ws.server.DocumentsWithPage[] documentsWithPageArray = folderRegisterOrigen.getDocWithPage();
		Folder folderDocument = new Folder();
		folderDocument.setFolderId(folderRegisterOrigen.getFolderId());
		folderDocument.setBookId(getBookId(bookId));
		
		if(documentsWithPageArray != null && documentsWithPageArray.length > 0) {
			
			documentPageDtoArray = new DocumentPageDto[documentsWithPageArray.length];
			
			for(int i = 0; i < documentsWithPageArray.length; i++) {
				ieci.tecdoc.sgm.registropresencial.ws.server.DocumentsWithPage documentWidthPage = documentsWithPageArray[i];
				ieci.tecdoc.sgm.registropresencial.ws.server.Document document = null;
				
				documentPageDto = new DocumentPageDto();
				documentPageDto.setDocumentName(documentWidthPage.getDocumentName());
				documentPageDtoArray[i] = documentPageDto;
				
				if(documentWidthPage.getPages() != null && documentWidthPage.getPages().length > 0) {
					documentDtoArray = new DocumentDto[documentWidthPage.getPages().length];
					for(int j = 0; j < documentWidthPage.getPages().length; j++) {
						document = getDocument(folderDocument, documentWidthPage.getPages()[j]);
						document = oServicioOrigen.getDocumentFolder(userOrigen, document, entidadOrigen);
						documentDtoArray[j] = getDocumentPage(document.getDocumentContentB64(), documentWidthPage.getPages()[j]);
						Utils.traceDocs(document.getDocumentContentB64(), documentWidthPage.getDocumentName(), documentWidthPage.getPages()[j]);
					}
					
				} else {
					Utils.trace("El registro '" + folderRegisterOrigen.getFolderNumber() + "' no posee páginas anexas");
				}
				
				documentPageDto.setDocumentDto(documentDtoArray);
			}
			
			documetsPages.setDocuments(documentPageDtoArray);
			
		} 
		return documetsPages;
	}
	
	/**
	 * Crea un objeto de tipo DocumentDto a partir de los datos que se pasan como parámetros
	 * @param contentB64 - Contenido del documento en Base64
	 * @param page - Página del documento
	 * @return DocumentDto - Documento del registro
	 */
	private static DocumentDto getDocumentPage(String contentB64, Page page) {
		DocumentDto documentDto = new DocumentDto();
		documentDto.setContentB64(contentB64);
		documentDto.setExtension(page.getLoc());
		documentDto.setPageName(page.getPageName());
		return documentDto;
	}
	
	/**
	 * Crea un objeto de tipo Document a partir de los datos que se pasan como parámetros
	 * @param folderDocument - Carpeta del documento
	 * @param page - Página del documento
	 * @return Document - - Documento del registro
	 */
	private static Document getDocument(Folder folderDocument, Page page) {
		Document document = new Document();
		document.setFolder(folderDocument);
		document.setDocID(page.getDocID());
		document.setPageID(page.getPageID());
		return document;
	}
	
	/**
	 * Devuelve un array de remitentes del registro
	 * @param folderRegisterOrigen - Carpeta del registro de SIGEM Housing
	 * @param personInfo - Carpeta con los remietentes
	 * @return PersonInfo[] - Listado con los remitentes del registro
	 */
	public static PersonInfo[] getPersonInfo(Folder folderRegisterOrigen, FolderWithPersonInfo personInfo) {
		if(UtilsService.isResponeOK(folderRegisterOrigen.getErrorCode(), folderRegisterOrigen.getReturnCode())) return personInfo.getPersons(); 	
	    else return new PersonInfo[0];
	}
}
