/**
 *
 */
package ieci.tecdoc.sgm.registropresencial.ws.client;

import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.registro.BookInfo;
import ieci.tecdoc.sgm.core.services.registro.DistributionInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.FieldInfoQuery;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterQueryInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfoPersonInfo;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderWithPersonInfo;

/**
 * @author 66575267
 *
 */
public class RegistroWebServiceRemoteClientUtil {

	public static BookInfo[] getBookInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.BooksInfo oBooksInfo) {
		if (oBooksInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookInfo wsBookInfo[] = oBooksInfo
				.getBooksInfo();
		BookInfo[] coBookInfo = new BookInfo[wsBookInfo.length];

		for (int i = 0; i < wsBookInfo.length; i++) {
			BookInfo bookInfo = new BookInfo();

			if (wsBookInfo[i].getId() != null
					&& !wsBookInfo[i].getId().equals("")) {
				bookInfo.setId(new Integer(wsBookInfo[i].getId()));
			}
			if (wsBookInfo[i].getIdocArchId() != null
					&& !wsBookInfo[i].getIdocArchId().equals("")) {
				bookInfo.setIdocArchId(new Integer(wsBookInfo[i]
						.getIdocArchId()));
			}
			bookInfo.setName(wsBookInfo[i].getName());
			bookInfo.setRemarks(wsBookInfo[i].getRemarks());

			if (wsBookInfo[i].getState() != null
					&& !wsBookInfo[i].getState().equals("")) {
				bookInfo.setState(new Integer(wsBookInfo[i].getState())
						.intValue());
			}
			if (wsBookInfo[i].getType() != null
					&& !wsBookInfo[i].getType().equals("")) {
				bookInfo.setType(new Integer(wsBookInfo[i].getType())
						.intValue());
			}

			coBookInfo[i] = bookInfo;
		}

		return coBookInfo;

	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo getDistributionInfoWS(
			Integer distributionId) {
		if (distributionId == null) {
			return null;
		}

		ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo distributionInfo = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo();
		distributionInfo.setDtrId(distributionId.toString());

		return distributionInfo;
	}

	public static DistributionInfo getDistributionInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo wsDistInfo) {
		if (wsDistInfo == null) {
			return null;
		}

		DistributionInfo distInfo = new DistributionInfo();
		if (wsDistInfo.getBookId() != null
				&& !wsDistInfo.getBookId().equals("")) {
			distInfo.setBookId(new Integer(wsDistInfo.getBookId()));
		}
		distInfo.setBookName(wsDistInfo.getBookName());
		if (wsDistInfo.getBookType() != null
				&& !wsDistInfo.getBookType().equals("")) {
			distInfo.setBookType(new Integer(wsDistInfo.getBookType()));
		}
		if (wsDistInfo.getDestinationId() != null
				&& !wsDistInfo.getDestinationId().equals("")) {
			distInfo
					.setDestinationId(new Integer(wsDistInfo.getDestinationId()));
		}
		distInfo.setDestinationName(wsDistInfo.getDestinationName());
		if (wsDistInfo.getDestinationType() != null
				&& !wsDistInfo.getDestinationType().equals("")) {
			distInfo.setDestinationType(new Integer(wsDistInfo
					.getDestinationType()));
		}
		distInfo.setDistributionDate(wsDistInfo.getDistributionDate());
		if (wsDistInfo.getDtrId() != null && !wsDistInfo.getDtrId().equals("")) {
			distInfo.setDtrId(new Integer(wsDistInfo.getDtrId()));
		}
		if (wsDistInfo.getFolderId() != null
				&& !wsDistInfo.getFolderId().equals("")) {
			distInfo.setFolderId(new Integer(wsDistInfo.getFolderId()));
		}
		distInfo.setMessage(wsDistInfo.getMessage());
		distInfo.setRegisterDate(wsDistInfo.getRegisterDate());
		distInfo.setRegisterDestinationName(wsDistInfo
				.getRegisterDestinationName());
		distInfo.setRegisterMatter(wsDistInfo.getRegisterMatter());
		distInfo.setRegisterMatterTypeName(wsDistInfo
				.getRegisterMatterTypeName());
		distInfo.setRegisterNumber(wsDistInfo.getRegisterNumber());
		if (wsDistInfo.getSenderId() != null
				&& !wsDistInfo.getSenderId().equals("")) {
			distInfo.setSenderId(new Integer(wsDistInfo.getSenderId()));
		}
		distInfo.setSenderName(wsDistInfo.getSenderName());
		if (wsDistInfo.getSenderType() != null
				&& !wsDistInfo.getSenderType().equals("")) {
			distInfo.setSenderType(new Integer(wsDistInfo.getSenderType()));
		}
		if (wsDistInfo.getState() != null && !wsDistInfo.getState().equals("")) {
			distInfo.setState(new Integer(wsDistInfo.getState()));
		}
		distInfo.setStateDate(wsDistInfo.getStateDate());
		distInfo.setStateDescription(wsDistInfo.getStateDescription());
		distInfo.setUser(wsDistInfo.getUser());
		distInfo.setRegisterSenderName(wsDistInfo.getRegisterSenderName());
		distInfo.setRegisterOffice(wsDistInfo.getRegisterOffice());
		distInfo.setSendersOrReceivers(getPersonInfo(wsDistInfo
				.getSendersOrReceivers()));

		return distInfo;
	}

	public static DistributionInfo[] getDistributionsInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionsInfo oDistInfo) {
		if (oDistInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionInfo wsDistInfo[] = oDistInfo
				.getDistributions();
		DistributionInfo coDistInfo[] = new DistributionInfo[wsDistInfo.length];

		for (int i = 0; i < wsDistInfo.length; i++) {
			DistributionInfo distInfo = getDistributionInfo(wsDistInfo[i]);

			coDistInfo[i] = distInfo;

		}

		return coDistInfo;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions getDistributionOptionsWS(
			String option, int type) {
		if (option == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions options = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionOptions();

		if (type == 1) {
			options.setCode(option);
		} else {
			options.setRemarks(option);
		}
		return options;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria getDistributionSearchCriteriaWS(
			Integer state, Integer firstRow, Integer maxResults) {
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria criteria = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria();

		if (state == null) {
			criteria.setState("1");
		} else {
			criteria.setState(state.toString());
		}
		if (firstRow == null) {
			criteria.setFirstRow("0");
		} else {
			criteria.setFirstRow(firstRow.toString());
		}
		if (maxResults == null) {
			criteria.setMaxResults("10");
		} else {
			criteria.setMaxResults(maxResults.toString());
		}
		return criteria;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria getDistributionSearchCriteriaWS(
			Integer typeBookRegisterDist, Boolean oficAsoc) {
		if (typeBookRegisterDist == null) {
			typeBookRegisterDist = new Integer(0);
		}
		if (oficAsoc == null) {
			oficAsoc = Boolean.FALSE;
		}

		ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria wsDistributionSearchCriteria = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.DistributionSearchCriteria();
		wsDistributionSearchCriteria.setOficAsoc(oficAsoc.toString());
		wsDistributionSearchCriteria
				.setTypeBookRegisterDist(typeBookRegisterDist.toString());

		return wsDistributionSearchCriteria;
	}

	public static DocumentQuery getDocumentQuery(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document wsDocument) {
		if (wsDocument == null) {
			return null;
		}
		DocumentQuery coDocQuery = new DocumentQuery();
		coDocQuery
				.setContent(Base64.decode(wsDocument.getDocumentContentB64()));
		return coDocQuery;
	}

	public static Document getDocumentWS(Integer bookId, Integer folderId,
			Integer docId, Integer pageId) {
		if (bookId == null || folderId == null || docId == null
				|| pageId == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document wsDocument = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document();
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder wsFolder = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder();
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId wsBookid = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId();
		wsBookid.setBookId(bookId.toString());
		wsFolder.setBookId(wsBookid);
		wsFolder.setFolderId(folderId.toString());
		wsDocument.setFolder(wsFolder);
		wsDocument.setDocID(docId.toString());
		wsDocument.setPageID(pageId.toString());
		return wsDocument;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad getEntidadWS(
			Entidad poEntidad) {
		if (poEntidad == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad oEntidad = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		return oEntidad;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria getFolderSearchCriteriaWS(
			Integer bookId, FieldInfoQuery[] atts) {
		if (bookId == null) {
			return null;
		}

		ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria wsFolderSearchCriteria = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.FolderSearchCriteria();
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId wsBookId = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId();
		wsBookId.setBookId(bookId.toString());
		wsFolderSearchCriteria.setBookId(wsBookId);
		if (atts != null) {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfoSearchCriteria wsFieldInfoSearchCriteria[] = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfoSearchCriteria[atts.length];
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfoSearchCriteria wsFieldSearchCriteria = null;
			for (int i = 0; i < atts.length; i++) {
				wsFieldSearchCriteria = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfoSearchCriteria();
				wsFieldSearchCriteria.setFieldId(atts[i].getFieldId());
				wsFieldSearchCriteria.setOperator(atts[i].getOperator());
				wsFieldSearchCriteria.setValue(atts[i].getValue());
				wsFieldInfoSearchCriteria[i] = wsFieldSearchCriteria;
			}
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldsSearchCriteria wsFieldsSearchCriteria = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldsSearchCriteria();
			wsFieldsSearchCriteria.setFields(wsFieldInfoSearchCriteria);
			wsFolderSearchCriteria.setFields(wsFieldsSearchCriteria);
		}

		return wsFolderSearchCriteria;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getFolderWS(
			Integer bookId, DocumentInfo[] documents) {
		if (bookId == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder wsFolder = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder();
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId wsBookId = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId();
		wsBookId.setBookId(bookId.toString());
		wsFolder.setBookId(wsBookId);

		if (documents != null) {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Documents wsDocuments = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Documents();
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document wsDocument[] = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document[documents.length];
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document wsDoc = null;
			for (int i = 0; i < documents.length; i++) {
				wsDoc = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document();
				wsDoc.setDocumentName(documents[i].getDocumentName());
				wsDoc.setDocumentContentB64(Base64.encodeBytes(documents[i]
						.getDocumentContent()));
				wsDoc.setExtension(documents[i].getExtension());
				wsDoc.setFileName(documents[i].getFileName());
				wsDoc.setPageName(documents[i].getPageName());
				wsDocument[i] = wsDoc;
			}

			wsDocuments.setDocuments(wsDocument);
			wsFolder.setDocumentos(wsDocuments);
		}

		return wsFolder;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getFolderWS(
			Integer bookId, FieldInfo[] atts, DocumentInfo[] documents) {
		if (bookId == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder wsFolder = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder();
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId wsBookId = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.BookId();
		wsBookId.setBookId(bookId.toString());
		wsFolder.setBookId(wsBookId);
		if (atts != null) {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo wsFieldInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo[atts.length];
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo wsField = null;
			for (int i = 0; i < atts.length; i++) {
				wsField = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo();
				wsField.setFieldId(atts[i].getFieldId());
				wsField.setValue(atts[i].getValue());
				wsFieldInfo[i] = wsField;
			}
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Fields wsFields = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Fields();
			wsFields.setFields(wsFieldInfo);
			wsFolder.setFields(wsFields);
		}
		if (documents != null) {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Documents wsDocuments = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Documents();
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document wsDocument[] = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document[documents.length];
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document wsDoc = null;
			for (int i = 0; i < documents.length; i++) {
				wsDoc = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document();
				wsDoc.setDocumentName(documents[i].getDocumentName());
				wsDoc.setDocumentContentB64(Base64.encodeBytes(documents[i]
						.getDocumentContent()));
				wsDoc.setExtension(documents[i].getExtension());
				wsDoc.setFileName(documents[i].getFileName());
				wsDoc.setPageName(documents[i].getPageName());
				wsDocument[i] = wsDoc;
			}

			wsDocuments.setDocuments(wsDocument);
			wsFolder.setDocumentos(wsDocuments);
		}

		return wsFolder;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder getFolderWS(
			String folderNumber, Integer bookId) {
		if (folderNumber == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder folder = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder();
		folder.setFolderNumber(folderNumber);
		if (bookId != null) {
			BookId bookID = new BookId();
			bookID.setBookId(bookId.toString());
			folder.setBookId(bookID);
		}
		return folder;
	}

	public static OfficeInfo[] getOfficeInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficesInfo oOfficesInfo) {
		if (oOfficesInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.OfficeInfo wsOfficeInfo[] = oOfficesInfo
				.getOfficesInfo();

		OfficeInfo[] coOfficeInfo = new OfficeInfo[wsOfficeInfo.length];

		for (int i = 0; i < wsOfficeInfo.length; i++) {
			OfficeInfo officeInfo = new OfficeInfo();

			officeInfo.setAcron(wsOfficeInfo[i].getAcron());
			officeInfo.setCode(wsOfficeInfo[i].getCode());
			officeInfo.setName(wsOfficeInfo[i].getName());

			if (wsOfficeInfo[i].getDeptid() != null
					&& !wsOfficeInfo[i].getDeptid().equals("")) {
				officeInfo.setDeptid(new Integer(wsOfficeInfo[i].getDeptid())
						.intValue());
			}
			if (wsOfficeInfo[i].getId() != null
					&& !wsOfficeInfo[i].getId().equals("")) {
				officeInfo.setId(new Integer(wsOfficeInfo[i].getId()));
			}

			coOfficeInfo[i] = officeInfo;
		}

		return coOfficeInfo;

	}

	public static PersonInfo[] getPersonInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] psInfo) {

		if (psInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.core.services.registro.PersonInfo clientPers = null;
		ieci.tecdoc.sgm.core.services.registro.PersonInfo clientPersInfo[] = new ieci.tecdoc.sgm.core.services.registro.PersonInfo[psInfo.length];
		for (int i = 0; i < psInfo.length; i++) {
			clientPers = new ieci.tecdoc.sgm.core.services.registro.PersonInfo();
			clientPers.setDirection(psInfo[i].getDirection());
			clientPers.setDomId(psInfo[i].getDomId());
			clientPers.setPersonId(psInfo[i].getPersonId());
			clientPers.setPersonName(psInfo[i].getPersonName());
			clientPersInfo[i] = clientPers;
		}
		return clientPersInfo;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[] getPersonInfoWS(
			PersonInfo[] psInfo) {
		if (psInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo wsPers = null;
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo wsPersInfo[] = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo[psInfo.length];
		for (int i = 0; i < psInfo.length; i++) {
			wsPers = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.PersonInfo();
			wsPers.setDirection(psInfo[i].getDirection());
			wsPers.setDomId(psInfo[i].getDomId());
			wsPers.setPersonId(psInfo[i].getPersonId());
			wsPers.setPersonName(psInfo[i].getPersonName());
			wsPersInfo[i] = wsPers;
		}
		return wsPersInfo;
	}

	public static RegisterInfo getRegisterInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo wsRegInfo) {

		if (wsRegInfo == null) {
			return null;
		}
		RegisterInfo regInfo = new RegisterInfo();
		regInfo.setBookId(wsRegInfo.getBookId());
		regInfo.setDate(wsRegInfo.getDate());
		regInfo.setFolderId(wsRegInfo.getFolderId());
		regInfo.setNumber(wsRegInfo.getNumber());
		regInfo.setOffice(wsRegInfo.getOffice());
		regInfo.setOfficeName(wsRegInfo.getOfficeName());
		regInfo.setState(wsRegInfo.getState());
		regInfo.setUserName(wsRegInfo.getUserName());
		regInfo.setWorkDate(wsRegInfo.getWorkDate());
		return regInfo;
	}

	public static RegisterWithPagesInfo getRegisterQueryInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.Folder oFolder) {
		if (oFolder == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.FieldInfo wsFieldInfo[] = oFolder
				.getFields().getFields();
		RegisterQueryInfo coRegQweryInfo[] = new RegisterQueryInfo[wsFieldInfo.length];
		RegisterQueryInfo regQueryInfo = null;
		for (int i = 0; i < wsFieldInfo.length; i++) {
			regQueryInfo = new RegisterQueryInfo();
			regQueryInfo.setFolderName(wsFieldInfo[i].getFieldId());
			regQueryInfo.setValue(wsFieldInfo[i].getValue());
			coRegQweryInfo[i] = regQueryInfo;
		}
		RegisterWithPagesInfo sd = new RegisterWithPagesInfo();
		sd.setRqInfo(coRegQweryInfo);
		if (oFolder.getDocWithPage() != null) {
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.DocumentsWithPage docsWithPage[] = oFolder
					.getDocWithPage();
			ieci.tecdoc.sgm.core.services.registro.Document docs[] = new ieci.tecdoc.sgm.core.services.registro.Document[docsWithPage.length];
			ieci.tecdoc.sgm.core.services.registro.Document oDocs = null;
			for (int i = 0; i < docsWithPage.length; i++) {
				oDocs = new ieci.tecdoc.sgm.core.services.registro.Document();
				oDocs.setBookId(docsWithPage[i].getBookId());
				oDocs.setDocID(docsWithPage[i].getDocID());
				oDocs.setDocumentName(docsWithPage[i].getDocumentName());
				oDocs.setFolderId(docsWithPage[i].getFolderId());
				ieci.tecdoc.sgm.registropresencial.ws.client.axis.Page oPage[] = docsWithPage[i]
						.getPages();
				ieci.tecdoc.sgm.core.services.registro.Page cPage = null;
				for (int j = 0; j < oPage.length; j++) {
					cPage = new ieci.tecdoc.sgm.core.services.registro.Page();
					cPage.setDocID(oPage[j].getDocID());
					cPage.setFileID(oPage[j].getFileID());
					cPage.setFolderId(oPage[j].getFileID());
					cPage.setLoc(oPage[j].getLoc());
					cPage.setPageID(oPage[j].getPageID());
					cPage.setPageName(oPage[j].getPageName());
					cPage.setVolidID(oPage[j].getVolidID());
					oDocs.addPage(cPage);
				}
				docs[i] = oDocs;
			}
			sd.setDocInfo(docs);
		}

		return sd;
	}

	public static RegisterWithPagesInfoPersonInfo getRegisterQueryInfoPersonInfo(
			FolderWithPersonInfo folder) {
		RegisterWithPagesInfoPersonInfo register = new RegisterWithPagesInfoPersonInfo(
				RegistroWebServiceRemoteClientUtil.getRegisterQueryInfo(folder));

		register.setPersons(getPersonInfo(folder.getPersons()));
		// register.setPersons(getPersonInfoWS(folder.getPersons()));

		return register;
	}

	public static RegisterInfo[] getRegistersInfo(
			ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegistersInfo wsRegsInfo) {

		if ((wsRegsInfo == null) || (wsRegsInfo.getRegisters() == null)
				|| (wsRegsInfo.getRegisters().length == 0)) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo[] wsRegsInfoArray = wsRegsInfo
				.getRegisters();
		RegisterInfo[] regsInfo = new RegisterInfo[wsRegsInfoArray.length];

		for (int i = 0; i < wsRegsInfo.getRegisters().length; i++) {
			RegisterInfo regInfo = RegistroWebServiceRemoteClientUtil
					.getRegisterInfo(wsRegsInfoArray[i]);
			regsInfo[i] = regInfo;
		}

		return regsInfo;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria getSearchCriteriaWS(
			Integer bookType, Integer oficAsoc, Integer onlyOpenBooks) {
		if (bookType == null) {
			bookType = new Integer(0);
		}
		if (oficAsoc == null) {
			oficAsoc = new Integer(0);
		}
		if (onlyOpenBooks == null) {
			onlyOpenBooks = new Integer(0);
		}

		ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria wsSearchCriteria = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.SearchCriteria();
		wsSearchCriteria.setOficAsoc(oficAsoc.toString());
		wsSearchCriteria.setOnlyOpenBooks(onlyOpenBooks.toString());
		wsSearchCriteria.setTypeBookRegister(bookType.toString());

		return wsSearchCriteria;
	}

	public static ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo getUserInfoWS(
			UserInfo usrInfo) {
		if (usrInfo == null) {
			return null;
		}
		ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo userInfo = new ieci.tecdoc.sgm.registropresencial.ws.client.axis.UserInfo();
		if (usrInfo.getLocale() != null) {
			userInfo.setLocale(usrInfo.getLocale().getLanguage());
		}
		userInfo.setPassword(usrInfo.getPassword());
		userInfo.setUserName(usrInfo.getUserName());
		return userInfo;
	}

}
