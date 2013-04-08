/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.utils;

import ieci.tecdoc.sgm.core.services.registro.BookInfo;
import ieci.tecdoc.sgm.core.services.registro.DistributionInfo;
import ieci.tecdoc.sgm.core.services.registro.Document;
import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.DocumentQuery;
import ieci.tecdoc.sgm.core.services.registro.FieldInfo;
import ieci.tecdoc.sgm.core.services.registro.FieldInfoQuery;
import ieci.tecdoc.sgm.core.services.registro.Interested;
import ieci.tecdoc.sgm.core.services.registro.OfficeInfo;
import ieci.tecdoc.sgm.core.services.registro.Page;
import ieci.tecdoc.sgm.core.services.registro.PersonInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterQueryInfo;
import ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo;
import ieci.tecdoc.sgm.core.services.registro.UserInfo;

import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.info.InfoBook;
import ieci.tecdoc.sgm.registropresencial.info.InfoDistribution;
import ieci.tecdoc.sgm.registropresencial.info.InfoOffice;
import ieci.tecdoc.sgm.registropresencial.info.InfoRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrDocument;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrField;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrFile;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrPage;

/**
 * @author 66575267
 * 
 */
public class SigemRegistroServiceAdapterUtil {

	public static BookInfo[] getBooksInfo(List bookList) {
		if (bookList == null) {
			return null;
		}

		BookInfo[] booksInfo = new BookInfo[bookList.size()];
		int i = 0;
		for (Iterator iterator = bookList.iterator(); iterator.hasNext();) {
			InfoBook bookInfoAPI = (InfoBook) iterator.next();
			booksInfo[i] = getBookInfo(bookInfoAPI);
			i++;
		}

		return booksInfo;
	}

	public static BookInfo getBookInfo(InfoBook bookInfoAPI) {
		if (bookInfoAPI == null) {
			return null;
		}

		BookInfo bookInfo = new BookInfo();
		bookInfo.setId(bookInfoAPI.getId());
		bookInfo.setIdocArchId(bookInfoAPI.getIdocArchId());
		bookInfo.setName(bookInfoAPI.getName());
		bookInfo.setRemarks(bookInfoAPI.getRemarks());
		bookInfo.setState(bookInfoAPI.getState());
		bookInfo.setType(bookInfoAPI.getType());

		return bookInfo;
	}

	public static DistributionInfo[] getDistributionInfo(List apiListDistInfo) {
		if (apiListDistInfo == null) {
			return null;
		}
		DistributionInfo dInfo[] = new DistributionInfo[apiListDistInfo.size()];
		int i = 0;
		for (Iterator it = apiListDistInfo.iterator(); it.hasNext();) {
			InfoDistribution wsDistributionInfo = (InfoDistribution) it.next();
			dInfo[i] = getDistributionInfo(wsDistributionInfo);
			i++;
		}
		return dInfo;
	}

	public static DocumentQuery getDocumentQuery(byte[] content) {
		if (content == null) {
			return null;
		}
		DocumentQuery documentQuery = new DocumentQuery();
		documentQuery.setContent(content);
		return documentQuery;
	}

	public static Map getDocumentsInfo(DocumentInfo[] documentsServicio) {
		if (documentsServicio == null) {
			return null;
		}
		String docName = "";
		List docItems = new ArrayList();
		Map documents = new HashMap();

		for (int i = 0; i < documentsServicio.length; i++) {
			DocumentInfo documentInfo = documentsServicio[i];
			if (!docName.equals(documentInfo.getDocumentName())) {
				FlushFdrDocument document = new FlushFdrDocument();
				document.setDocumentName(documentInfo.getDocumentName());
				docItems.add(document);
			}
			docName = documentInfo.getDocumentName();
		}

		for (Iterator it = docItems.iterator(); it.hasNext();) {
			FlushFdrDocument flushFdrDocument = (FlushFdrDocument) it.next();
			String clavedocument = flushFdrDocument.getDocumentName();
			for (int i = 0; i < documentsServicio.length; i++) {
				DocumentInfo documentInfo = documentsServicio[i];
				if (clavedocument.equals(documentInfo.getDocumentName())) {
					FlushFdrFile file = new FlushFdrFile();
					file.setFileNameFis(documentInfo.getFileName());
					file.setFileNameLog(documentInfo.getPageName());
					file.setBuffer(documentInfo.getDocumentContent());
					file.setExtension(documentInfo.getExtension());

					FlushFdrPage page = new FlushFdrPage();
					page.setPageName(documentInfo.getPageName());
					page.setFile(file);

					flushFdrDocument.addPage(page);
				}

			}
			documents.put(clavedocument, flushFdrDocument);
		}

		return documents;
	}

	public static List getPersonsInfo(PersonInfo[] personsServicio) {
		if (personsServicio == null) {
			return null;
		}

		List inter = new ArrayList();

		for (int i = 0; i < personsServicio.length; i++) {
			PersonInfo personInfo = personsServicio[i];
			FlushFdrInter person = new FlushFdrInter();
			person.setDirection(personInfo.getDirection());
			if (personInfo.getDomId() != null
					&& !personInfo.getDomId().equals("")) {
				person.setDomId(new Integer(personInfo.getDomId()).intValue());
			}
			if (personInfo.getPersonId() != null
					&& !personInfo.getPersonId().equals("")) {
				person.setInterId(new Integer(personInfo.getPersonId())
						.intValue());
			}
			person.setInterName(personInfo.getPersonName());
			inter.add(person);
		}
		return inter;
	}

	public static RegisterInfo getRegisterInfo(InfoRegister apiRInfo) {
		if (apiRInfo == null) {
			return null;
		}
		RegisterInfo rInfo = new RegisterInfo();
		rInfo.setBookId(apiRInfo.getBookId());
		rInfo.setDate(apiRInfo.getDate());
		rInfo.setFolderId(apiRInfo.getFolderId());
		rInfo.setNumber(apiRInfo.getNumber());
		rInfo.setOffice(apiRInfo.getOffice());
		rInfo.setOfficeName(apiRInfo.getOfficeName());
		rInfo.setState(apiRInfo.getState());
		rInfo.setUserName(apiRInfo.getUserName());
		rInfo.setWorkDate(apiRInfo.getWorkDate());
		return rInfo;
	}

	public static RegisterInfo[] getRegistersInfo(InfoRegister[] apiRsInfo) {

		if ((apiRsInfo == null) || (apiRsInfo.length == 0)) {
			return null;
		}

		RegisterInfo[] rsInfo = new RegisterInfo[apiRsInfo.length];

		for (int i = 0; i < rsInfo.length; i++) {
			RegisterInfo rInfo = getRegisterInfo(apiRsInfo[i]);
			rsInfo[i] = rInfo;
		}

		return rsInfo;
	}

	public static List getFieldQueryInfo(FieldInfoQuery[] fieldsServicio) {
		if (fieldsServicio == null) {
			return null;
		}

		List atts = new ArrayList();
		for (int i = 0; i < fieldsServicio.length; i++) {
			FieldInfoQuery fieldInfoQuery = fieldsServicio[i];
			AxSfQueryField field = new AxSfQueryField();
			if ((fieldInfoQuery.getFieldId() != null)
					&& (!fieldInfoQuery.getFieldId().equals(""))) {
				field.setFldId(fieldInfoQuery.getFieldId());
			}
			field.setValue(fieldInfoQuery.getValue());
			field.setOperator(fieldInfoQuery.getOperator());
			atts.add(field);
		}

		return atts;
	}

	public static List getFieldsInfo(FieldInfo[] fieldsServicio) {
		if (fieldsServicio == null) {
			return null;
		}

		List atts = new ArrayList();
		for (int i = 0; i < fieldsServicio.length; i++) {
			FieldInfo fieldInfo = fieldsServicio[i];
			FlushFdrField field = new FlushFdrField();
			if (fieldInfo.getFieldId() != null
					&& !fieldInfo.getFieldId().equals("")) {
				field.setFldid(new Integer(fieldInfo.getFieldId()).intValue());
			}
			field.setValue(fieldInfo.getValue());
			atts.add(field);
		}
		return atts;
	}

	public static RegisterWithPagesInfo getRegisterQueryInfo(Map wsRQueryInfo) {
		if (wsRQueryInfo == null) {
			return null;
		}

		RegisterWithPagesInfo rwPageInfo = new RegisterWithPagesInfo();
		int size = 0;
		if (wsRQueryInfo.keySet().contains("documents")) {
			size = wsRQueryInfo.size() - 1;
		} else {
			size = wsRQueryInfo.size();
		}
		RegisterQueryInfo registerQueryInfo[] = new RegisterQueryInfo[size];
		Document doc[] = null;
		int i = 0;
		for (Iterator it = wsRQueryInfo.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (!key.equals("documents")) {
				String value = (String) wsRQueryInfo.get(key);
				RegisterQueryInfo rQueryInfo = new RegisterQueryInfo();
				rQueryInfo.setFolderName(key);
				rQueryInfo.setValue(value);
				registerQueryInfo[i] = rQueryInfo;
				i++;
			} else {
				List docs = (List) wsRQueryInfo.get(key);
				if (docs != null && !docs.isEmpty()) {
					int j = 0;
					doc = new Document[docs.size()];
					for (Iterator it1 = docs.iterator(); it1.hasNext();) {
						AxDoch axdoch = (AxDoch) it1.next();
						Document docInfo = new Document();
						docInfo.setDocumentName(axdoch.getName());
						docInfo.setBookId(new Integer(axdoch.getArchId())
								.toString());
						docInfo.setFolderId(new Integer(axdoch.getFdrId())
								.toString());
						docInfo
								.setDocID(new Integer(axdoch.getId())
										.toString());
						Page pageInfo = null;
						for (Iterator it2 = axdoch.getPages().iterator(); it2
								.hasNext();) {
							AxPageh axpageh = (AxPageh) it2.next();
							pageInfo = new Page();
							pageInfo.setDocID(new Integer(axpageh.getDocId())
									.toString());
							pageInfo.setFileID(new Integer(axpageh.getFileId())
									.toString());
							pageInfo
									.setFolderId(new Integer(axpageh.getFdrId())
											.toString());
							pageInfo.setLoc(axpageh.getLoc());
							pageInfo.setPageID(new Integer(axpageh.getId())
									.toString());
							pageInfo.setPageName(axpageh.getName());
							pageInfo.setVolidID(new Integer(axpageh.getVolId())
									.toString());
							docInfo.addPage(pageInfo);
						}
						doc[j] = docInfo;
						j++;
					}
				}
			}
		}
		rwPageInfo.setDocInfo(doc);
		rwPageInfo.setRqInfo(registerQueryInfo);
		return rwPageInfo;
	}

	public static PersonInfo[] getPersonsInfo(Interested[] interestedRegister) {
		if (interestedRegister == null) {
			return null;
		}
		PersonInfo persCo = null;
		PersonInfo persInfo[] = new PersonInfo[interestedRegister.length];
		for (int i = 0; i < interestedRegister.length; i++) {
			persCo = new PersonInfo();
			persCo.setDomId(interestedRegister[i].getAddressId());
			persCo.setPersonId(interestedRegister[i].getId());
			persCo.setPersonName(interestedRegister[i].getName());
			persCo.setDirection(interestedRegister[i].getAddress());
			persInfo[i] = persCo;
		}
		return persInfo;
	}

	public static User getWSUser(UserInfo userServicio) {
		if (userServicio == null) {
			return null;
		}
		User wsUser = new User();
		if (userServicio.getLocale() != null
				&& !"".equals(userServicio.getLocale().getLanguage())) {
			wsUser.setLocale(userServicio.getLocale());
		} else {
			wsUser.setLocale(new Locale("es"));
		}
		wsUser.setPassword(userServicio.getPassword());
		wsUser.setUserName(userServicio.getUserName());
		return wsUser;
	}

	public static boolean isOficAsoc(Integer oficAsoc) {
		return !(oficAsoc != null && oficAsoc.intValue() != 0);
	}

	public static boolean isOnlyOpenBooks(Integer onlyOpenBooks) {
		return !(onlyOpenBooks != null && onlyOpenBooks.intValue() == 0);
	}

	public static OfficeInfo[] getOfficesInfo(List officeList) {
		if (officeList == null) {
			return null;
		}

		OfficeInfo[] officesInfo = new OfficeInfo[officeList.size()];
		int i = 0;
		for (Iterator iterator = officeList.iterator(); iterator.hasNext();) {
			InfoOffice officeInfoAPI = (InfoOffice) iterator.next();
			officesInfo[i] = getOfficeInfo(officeInfoAPI);
			i++;
		}

		return officesInfo;
	}

	public static OfficeInfo getOfficeInfo(InfoOffice officeInfoAPI) {
		if (officeInfoAPI == null) {
			return null;
		}

		OfficeInfo officeInfo = new OfficeInfo();
		officeInfo.setId(officeInfoAPI.getId());
		officeInfo.setAcron(officeInfoAPI.getAcron());
		officeInfo.setName(officeInfoAPI.getName());
		officeInfo.setDeptid(officeInfoAPI.getDeptid());
		officeInfo.setCode(officeInfoAPI.getCode());

		return officeInfo;
	}

	public static DistributionInfo getDistributionInfo(
			InfoDistribution apiDistInfo) {
		if (apiDistInfo == null) {
			return null;
		}
		DistributionInfo dInfo = new DistributionInfo();
		dInfo.setBookId(apiDistInfo.getBookId());
		dInfo.setBookName(apiDistInfo.getBookName());
		dInfo.setBookType(apiDistInfo.getBookType());

		dInfo.setDestinationId(apiDistInfo.getDestinationId());
		dInfo.setDestinationName(apiDistInfo.getDestinationName());
		dInfo.setDestinationType(apiDistInfo.getDestinationType());
		dInfo.setDistributionDate(apiDistInfo.getDistributionDate());
		dInfo.setDtrId(apiDistInfo.getDtrId());

		dInfo.setFolderId(apiDistInfo.getFolderId());
		dInfo.setMessage(apiDistInfo.getMessage());

		dInfo.setRegisterDate(apiDistInfo.getRegisterDate());
		dInfo.setRegisterDestinationName(apiDistInfo
				.getRegisterDestinationName());
		dInfo.setRegisterMatter(apiDistInfo.getRegisterMatter());
		dInfo
				.setRegisterMatterTypeName(apiDistInfo
						.getRegisterMatterTypeName());
		dInfo.setRegisterNumber(apiDistInfo.getRegisterNumber());
		dInfo.setRegisterOffice(apiDistInfo.getRegisterOffice());
		dInfo.setRegisterSenderName(apiDistInfo.getRegisterSenderName());
		dInfo.setRegisterType(apiDistInfo.getRegisterType());

		dInfo.setSenderId(apiDistInfo.getSenderId());
		dInfo.setSenderName(apiDistInfo.getSenderName());
		dInfo.setSenderType(apiDistInfo.getSenderType());
		dInfo.setSendersOrReceivers(getPersonsInfo(apiDistInfo
				.getSendersOrReceivers()));
		dInfo.setState(apiDistInfo.getState());
		dInfo.setStateDate(apiDistInfo.getStateDate());
		dInfo.setStateDescription(apiDistInfo.getStateDescription());

		dInfo.setUser(apiDistInfo.getUser());

		return dInfo;
	}

}
