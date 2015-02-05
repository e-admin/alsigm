/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.server.util;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.services.registro.BookInfo;
import ieci.tecdoc.sgm.core.services.registro.DistributionInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.FieldInfoQuery;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterQueryInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfoPersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegistroException;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.BookId;
import ieci.tecdoc.sgm.registropresencial.ws.server.BooksInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.DistributionsInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Document;
import ieci.tecdoc.sgm.registropresencial.ws.server.DocumentsWithPage;
import ieci.tecdoc.sgm.registropresencial.ws.server.Fields;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.server.FolderWithPersonInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.OfficesInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.Page;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegisterInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.RegistersInfo;

/**
 * Clase de utilidadates que convierte los datos obtenidos de la API en datos
 * del servicio web. Y viceversa
 * 
 * @author 66575267
 * 
 */
public class UtilsWebService {

	/**
	 * Transformamos un array de Libros en una lista de libros
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param booksUser
	 * @return
	 */
	public static BooksInfo getBooksInfoWS(BookInfo[] booksUser) {
		BooksInfo result = null;
		if (booksUser == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo bookInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo[booksUser.length];

		for (int i = 0; i < booksUser.length; i++) {
			bookInfo[i] = getBookInfoWS(booksUser[i]);
		}
		result = new BooksInfo();
		result.setBooksInfo(bookInfo);

		return result;
	}

	/**
	 * Tranformamos un libro
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param bookUser
	 * @return
	 */
	public static ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo getBookInfoWS(
			BookInfo bookUser) {
		if (bookUser == null) {
			return null;
		}

		ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo bookS = new ieci.tecdoc.sgm.registropresencial.ws.server.BookInfo();
		if (bookUser.getId() != null) {
			bookS.setId(bookUser.getId().toString());
		}
		if (bookUser.getIdocArchId() != null) {
			bookS.setIdocArchId(bookUser.getIdocArchId().toString());
		}

		bookS.setName(bookUser.getName());
		bookS.setRemarks(bookS.getRemarks());
		bookS.setState(String.valueOf(bookUser.getState()));
		bookS.setType(String.valueOf(bookUser.getType()));

		return bookS;

	}

	/**
	 * Obtenemos el identificador de un libro de los datos de registro definidos
	 * en el web service
	 * 
	 * @param folder
	 * @return
	 */
	public static Integer getBookId(Folder folder) {
		if (folder == null) {
			return null;
		}
		Integer bookId = new Integer(folder.getBookId().getBookId());
		return bookId;
	}

	/**
	 * Obtenemos el identificador de un libro de los datos de criteros de
	 * busqueda de registro definidos en el web service
	 * 
	 * @param folderQuery
	 * @return
	 */
	public static Integer getBookId(FolderSearchCriteria folderQuery) {
		if (folderQuery == null) {
			return null;
		}
		Integer bookId = new Integer(folderQuery.getBookId().getBookId());
		return bookId;
	}

	/**
	 * Obtenemos el identificador de una distribución de los datos de una
	 * distribucion definidos en el web service
	 * 
	 * @param distributionInfo
	 * @return
	 * @throws RegistroException
	 */
	public static Integer getDistributionInfoId(
			ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distributionInfo)
			throws RegistroException {
		Integer distId = null;
		if (distributionInfo != null && distributionInfo.getDtrId() != null) {
			distId = new Integer(distributionInfo.getDtrId());
		}

		if (distId != null && distId.intValue() != 0) {
			return distId;
		}

		throw new RegistroException(
				"Error al obtener el identificador de la distribucion");

	}

	/**
	 * Transformamos un array de distribuciones en una lista de distribuciones
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param distUser
	 * @return
	 */
	public static DistributionsInfo getDistributionsInfoWS(
			DistributionInfo[] distUser) {
		DistributionsInfo result = null;
		if (distUser == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo[distUser.length];

		for (int i = 0; i < distUser.length; i++) {
			distInfo[i] = getDistributionInfoWS(distUser[i]);
		}
		result = new DistributionsInfo();
		result.setDistributions(distInfo);

		return result;
	}

	/**
	 * Transformamos una distribucion
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param distUser
	 * @return
	 */
	public static ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo getDistributionInfoWS(
			DistributionInfo distUser) {
		if (distUser == null) {
			return null;
		}

		ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo distS = new ieci.tecdoc.sgm.registropresencial.ws.server.DistributionInfo();
		if (distUser.getBookId() != null) {
			distS.setBookId(distUser.getBookId().toString());
		}
		distS.setBookName(distUser.getBookName());
		if (distUser.getBookType() != null) {
			distS.setBookType(distUser.getBookType().toString());
		}
		if (distUser.getDestinationId() != null) {
			distS.setDestinationId(distUser.getDestinationId().toString());
		}
		distS.setDestinationName(distUser.getDestinationName());
		if (distUser.getDestinationType() != null) {
			distS.setDestinationType(distUser.getDestinationType().toString());
		}
		distS.setDistributionDate(distUser.getDistributionDate());
		if (distUser.getDtrId() != null) {
			distS.setDtrId(distUser.getDtrId().toString());
		}
		if (distUser.getFolderId() != null) {
			distS.setFolderId(distUser.getFolderId().toString());
		}
		distS.setMessage(distUser.getMessage());
		distS.setRegisterDate(distUser.getRegisterDate());
		distS.setRegisterDestinationName(distUser.getRegisterDestinationName());
		distS.setRegisterMatter(distUser.getRegisterMatter());
		distS.setRegisterMatterTypeName(distUser.getRegisterMatterTypeName());
		distS.setRegisterNumber(distUser.getRegisterNumber());
		if (distUser.getSenderId() != null) {
			distS.setSenderId(distUser.getSenderId().toString());
		}
		distS.setSenderName(distUser.getSenderName());
		if (distUser.getSenderType() != null) {
			distS.setSenderType(distUser.getSenderType().toString());
		}
		if (distUser.getState() != null) {
			distS.setState(distUser.getState().toString());
		}
		distS.setStateDate(distUser.getStateDate());
		distS.setStateDescription(distUser.getStateDescription());
		distS.setUser(distUser.getUser());
		distS.setSenderName(distUser.getSenderName());
		distS.setRegisterOffice(distUser.getRegisterOffice());
		distS.setSendersOrReceivers(getPersonInfoWS(distUser
				.getSendersOrReceivers()));

		return distS;

	}

	/**
	 * Obtenemos el identidificador de un documento de los datos de un documento
	 * definidos en el web service
	 * 
	 * @param docReg
	 * @return
	 */
	public static Integer getDocId(Document docReg) {
		if (docReg == null) {
			return null;
		}
		Integer docId = new Integer(docReg.getDocID());
		return docId;
	}

	/**
	 * Transformamos los datos de un documento
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param dqDoc
	 * @return
	 */
	public static Document getDocumentInfoWS(DocumentQuery dqDoc) {
		if (dqDoc == null) {
			return null;
		}
		Document doc = new Document();
		doc.setDocumentContentB64(Base64.encodeBytes(dqDoc.getContent()));

		return doc;
	}

	/**
	 * Transformamos los datos de los documentos que contiene un registro , en
	 * un array de documentos
	 * 
	 * Web Service -> Sigem Core
	 * 
	 * @param fldInfo
	 * @return
	 */
	public static DocumentInfo[] getDocumentsInfo(
			ieci.tecdoc.sgm.registropresencial.ws.server.Folder fldInfo) {
		if (fldInfo == null || fldInfo.getDocumentos() == null) {
			return null;
		}
		int size = fldInfo.getDocumentos().getDocuments().length;
		Document docS[] = fldInfo.getDocumentos().getDocuments();
		DocumentInfo docCo[] = new DocumentInfo[size];
		DocumentInfo docIf = null;
		for (int i = 0; i < size; i++) {
			docIf = new DocumentInfo();
			docIf.setDocumentContent(Base64.decode(docS[i]
					.getDocumentContentB64()));
			docIf.setDocumentName(docS[i].getDocumentName());
			docIf.setExtension(docS[i].getExtension());
			docIf.setFileName(docS[i].getFileName());
			docIf.setPageName(docS[i].getPageName());
			docCo[i] = docIf;
		}

		return docCo;
	}

	/**
	 * Transformamos los datos de un registro en un array de atributos
	 * 
	 * Web Service -> Sigem Core
	 * 
	 * @param fldInfo
	 * @return
	 */
	public static FieldInfo[] getFieldsInfo(
			ieci.tecdoc.sgm.registropresencial.ws.server.Folder fldInfo) {
		if (fldInfo == null || fldInfo.getFields().getFields() == null) {
			return null;
		}
		int size = fldInfo.getFields().getFields().length;
		ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo fieldS[] = fldInfo
				.getFields().getFields();
		FieldInfo fieldCo[] = new FieldInfo[size];
		FieldInfo fieldIf = null;
		for (int i = 0; i < size; i++) {
			fieldIf = new FieldInfo();
			fieldIf.setFieldId(fieldS[i].getFieldId());
			fieldIf.setValue(fieldS[i].getValue());
			fieldCo[i] = fieldIf;
		}
		return fieldCo;
	}

	/**
	 * Transformamos los datos de un registro que contienen informacion de
	 * personas
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param inputRegister
	 * @return
	 */
	public static FolderWithPersonInfo getFieldsInfoPersonInfoWS(
			RegisterWithPagesInfoPersonInfo inputRegister) {

		FolderWithPersonInfo result = new FolderWithPersonInfo(
				getFieldsInfoWS(inputRegister));

		ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] personInfo = getPersonInfoWS(inputRegister
				.getPersons());
		result.setPersons(personInfo);
		return result;
	}

	/**
	 * Transformamos los atributos de busqueda de registros
	 * 
	 * Web Services -> Sigem Core
	 * 
	 * @param folderQuery
	 * @return
	 */
	public static FieldInfoQuery[] getFieldsInfoQueries(
			ieci.tecdoc.sgm.registropresencial.ws.server.FolderSearchCriteria folderQuery) {
		if (folderQuery == null || folderQuery.getFields() == null
				|| folderQuery.getFields().getFields() == null
				|| folderQuery.getFields().getFields().length == 0) {
			return null;
		}
		int size = folderQuery.getFields().getFields().length;
		ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfoSearchCriteria fieldS[] = folderQuery
				.getFields().getFields();
		FieldInfoQuery fieldCo[] = new FieldInfoQuery[size];
		FieldInfoQuery fieldIf = null;
		for (int i = 0; i < size; i++) {
			if (fieldS[i].getFieldId() != null
					&& fieldS[i].getOperator() != null
					&& fieldS[i].getValue() != null) {
				fieldIf = new FieldInfoQuery();
				fieldIf.setFieldId(fieldS[i].getFieldId());
				fieldIf.setValue(fieldS[i].getValue());
				fieldIf.setOperator(fieldS[i].getOperator());
				fieldCo[i] = fieldIf;
			}
		}
		return fieldCo;
	}

	/**
	 * Transformamos los datos de un registro y sus documentos
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param rqReg
	 * @return
	 */
	public static Folder getFieldsInfoWS(RegisterWithPagesInfo rqReg) {
		Folder result = null;
		if (rqReg == null) {
			return null;
		}
		RegisterQueryInfo rqInfo[] = rqReg.getRqInfo();
		ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo fieldInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo[rqInfo.length];
		ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo fieldS = null;

		String bookId = null;
		String folderId = null;
		String folderNumber = null;
		for (int i = 0; i < rqInfo.length; i++) {
			fieldS = new ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo();
			if (rqInfo[i].getFolderName().equals("bookId")) {
				bookId = rqInfo[i].getValue();
			}
			if (rqInfo[i].getFolderName().equals("fdrid")) {
				folderId = rqInfo[i].getValue();
			}
			if (rqInfo[i].getFolderName().equals("Fld1")) {
				folderNumber = rqInfo[i].getValue();
			}
			fieldS.setFieldId(rqInfo[i].getFolderName());
			fieldS.setValue(rqInfo[i].getValue());
			fieldInfo[i] = fieldS;
		}
		Fields fields = new Fields();
		fields.setFields(fieldInfo);
		result = new Folder();
		result.setFields(fields);
		BookId bookID = new BookId();
		bookID.setBookId(bookId);
		result.setBookId(bookID);
		result.setFolderId(folderId);
		result.setFolderNumber(folderNumber);

		DocumentsWithPage docsWithPages[] = null;
		Page pagesArray[] = null;
		if (rqReg.getDocInfo() != null) {
			ieci.tecdoc.sgm.core.services.registro.Document docs[] = rqReg
					.getDocInfo();
			docsWithPages = new DocumentsWithPage[docs.length];
			DocumentsWithPage docsWPages = null;
			for (int i = 0; i < docs.length; i++) {
				docsWPages = new DocumentsWithPage();
				docsWPages.setBookId(docs[i].getBookId());
				docsWPages.setDocID(docs[i].getDocID());
				docsWPages.setDocumentName(docs[i].getDocumentName());
				docsWPages.setFolderId(docs[i].getFolderId());
				ieci.tecdoc.sgm.core.services.registro.Page pageC = null;
				Page pageWs = null;
				List pages = docs[i].getPages();
				pagesArray = new Page[pages.size()];
				int j = 0;
				for (Iterator it2 = pages.iterator(); it2.hasNext();) {
					pageC = (ieci.tecdoc.sgm.core.services.registro.Page) it2
							.next();
					pageWs = new Page();
					pageWs.setDocID(pageC.getDocID());
					pageWs.setFileID(pageC.getFileID());
					pageWs.setFolderId(pageC.getFolderId());
					pageWs.setLoc(pageC.getLoc());
					pageWs.setPageID(pageC.getPageID());
					pageWs.setPageName(pageC.getPageName());
					pageWs.setVolidID(pageC.getVolidID());
					pagesArray[j] = pageWs;
					j++;
				}
				docsWPages.setPages(pagesArray);
				docsWithPages[i] = docsWPages;
			}
		}

		result.setDocWithPage(docsWithPages);
		return result;
	}

	/**
	 * Obtenemos el identificador de un registro de los datos de un registro
	 * 
	 * @param folder
	 * @return
	 */
	public static Integer getFolderId(Folder folder) {
		if (folder == null) {
			return null;
		}
		Integer folderId = new Integer(folder.getFolderId());
		return folderId;
	}

	/**
	 * Transformamos un array de oficinas en una lista de oficinas
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param officesUser
	 * @return
	 */
	public static OfficesInfo getOfficesInfoWS(OfficeInfo[] officesUser) {
		OfficesInfo result = null;
		if (officesUser == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo officeInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo[officesUser.length];

		for (int i = 0; i < officesUser.length; i++) {
			officeInfo[i] = getOfficeInfoWS(officesUser[i]);
		}
		result = new OfficesInfo();
		result.setOfficesInfo(officeInfo);

		return result;
	}

	/**
	 * Tranformamos una oficina
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param officeUser
	 * @return
	 */
	public static ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo getOfficeInfoWS(
			OfficeInfo officeUser) {
		if (officeUser == null) {
			return null;
		}

		ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo officeS = new ieci.tecdoc.sgm.registropresencial.ws.server.OfficeInfo();
		if (officeUser.getId() != null) {
			officeS.setId(officeUser.getId().toString());
		}
		officeS.setAcron(officeUser.getAcron());
		officeS.setCode(officeUser.getCode());
		officeS.setDeptid(String.valueOf(officeUser.getDeptid()));
		officeS.setName(officeUser.getName());

		return officeS;

	}

	/**
	 * Obtenemos el identificador de una pagina de un documento de los datos de
	 * este
	 * 
	 * @param docReg
	 * @return
	 */
	public static Integer getPageId(Document docReg) {
		if (docReg == null) {
			return null;
		}
		Integer pageId = new Integer(docReg.getPageID());
		return pageId;
	}

	/**
	 * Tranformamos un array de interesados
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param psInfo
	 * @return
	 */
	public static ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] getPersonInfoWS(
			ieci.tecdoc.sgm.core.services.registro.PersonInfo[] psInfo) {
		if (psInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo persInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[psInfo.length];
		for (int i = 0; i < psInfo.length; i++) {
			ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo persCo = getPersonInfo(psInfo[i]);
			persInfo[i] = persCo;
		}
		return persInfo;
	}

	/**
	 * Transformamos un array de interesados
	 * 
	 * Web Service -> Sigem Core
	 * 
	 * @param psInfo
	 * @return
	 */
	public static PersonInfo[] getPersonInfo(
			ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo[] psInfo) {
		if (psInfo == null) {
			return null;
		}
		PersonInfo persCo = null;
		PersonInfo persInfo[] = new PersonInfo[psInfo.length];
		for (int i = 0; i < psInfo.length; i++) {
			persCo = new PersonInfo();
			persCo.setDirection(psInfo[i].getDirection());
			persCo.setDomId(psInfo[i].getDomId());
			persCo.setPersonId(psInfo[i].getPersonId());
			persCo.setPersonName(psInfo[i].getPersonName());
			persInfo[i] = persCo;
		}
		return persInfo;
	}

	/**
	 * Transformamos los datos de un registro
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param poReg
	 * @return
	 */
	public static RegisterInfo getRegisterInfoWS(
			ieci.tecdoc.sgm.core.services.registro.RegisterInfo poReg) {
		if (poReg == null) {
			return null;
		}

		RegisterInfo oReg = new RegisterInfo();
		oReg.setBookId(poReg.getBookId());
		oReg.setDate(poReg.getDate());
		oReg.setFolderId(poReg.getFolderId());
		oReg.setNumber(poReg.getNumber());
		oReg.setOffice(poReg.getOffice());
		oReg.setOfficeName(poReg.getOfficeName());
		oReg.setState(poReg.getState());
		oReg.setUserName(poReg.getUserName());
		oReg.setWorkDate(poReg.getWorkDate());

		return oReg;
	}

	/**
	 * Transformamos un array de registros en una lista de registros
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param poRegs
	 * @return
	 */
	public static RegistersInfo getRegistersInfoWS(
			ieci.tecdoc.sgm.core.services.registro.RegisterInfo[] poRegs) {
		if (poRegs == null || poRegs.length == 0) {
			return null;
		}

		RegisterInfo[] oRegs = new RegisterInfo[poRegs.length];
		for (int i = 0; i < poRegs.length; i++) {
			ieci.tecdoc.sgm.core.services.registro.RegisterInfo poReg = poRegs[i];
			RegisterInfo oReg = new RegisterInfo();
			oReg.setBookId(poReg.getBookId());
			oReg.setDate(poReg.getDate());
			oReg.setFolderId(poReg.getFolderId());
			oReg.setNumber(poReg.getNumber());
			oReg.setOffice(poReg.getOffice());
			oReg.setOfficeName(poReg.getOfficeName());
			oReg.setState(poReg.getState());
			oReg.setUserName(poReg.getUserName());
			oReg.setWorkDate(poReg.getWorkDate());

			oRegs[i] = oReg;
		}

		if (oRegs != null && oRegs.length > 0) {
			RegistersInfo registersInfo = new RegistersInfo();
			registersInfo.setRegisters(oRegs);

			return registersInfo;
		}

		return null;
	}

	/**
	 * Transformamos los datos de un usuario
	 * 
	 * Web Service -> Sigem Core
	 * 
	 * @param usrInfo
	 * @return
	 */
	public static UserInfo getUserInfo(
			ieci.tecdoc.sgm.registropresencial.ws.server.UserInfo usrInfo) {
		if (usrInfo == null) {
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setLocale(new Locale(usrInfo.getLocale()));
		userInfo.setPassword(usrInfo.getPassword());
		userInfo.setUserName(usrInfo.getUserName());
		return userInfo;
	}

	/**
	 * Transformamos los datos de un interesado
	 * 
	 * Sigem Core -> Web Service
	 * 
	 * @param personInfo
	 * @return
	 */
	private static ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo getPersonInfo(
			PersonInfo personInfo) {
		ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo result = new ieci.tecdoc.sgm.registropresencial.ws.server.PersonInfo();
		result.setDirection(personInfo.getDirection());
		result.setDomId(personInfo.getDomId());
		result.setPersonId(personInfo.getPersonId());
		result.setPersonName(personInfo.getPersonName());
		return result;
	}

}
