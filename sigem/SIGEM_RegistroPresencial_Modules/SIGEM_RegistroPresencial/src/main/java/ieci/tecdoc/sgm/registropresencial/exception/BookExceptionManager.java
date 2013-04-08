package ieci.tecdoc.sgm.registropresencial.exception;

import java.util.HashMap;
import java.util.Map;

import com.ieci.tecdoc.common.exception.BookException;

public class BookExceptionManager {

	Map optionId = null;

	public BookExceptionManager() {
		optionId = new HashMap();
		mappingExceptionBookRT();
	}

	private Map mappingExceptionBookRT() {
		optionId.put(BookException.ERROR_UDPATEFIELDS_NOT_VALID_CODE, new Long(2100010001));
		optionId.put(BookException.ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED, new Long(2100010002));
		optionId.put(BookException.ERROR_ASSOCIATEDBOOK_NOT_OPEN, new Long(2100010003));
		optionId.put(BookException.ERROR_ASSOCIATEDBOOK_NOT_IN_BOOK, new Long(2100010004));
		optionId.put(BookException.ERROR_BOOK_NOTFOUND, new Long(2100010005));
		optionId.put(BookException.ERROR_CANNOT_OPEN_BOOK, new Long(2100010006));
		optionId.put(BookException.ERROR_CANNOT_OBTAIN_DBSERVER_DATE, new Long(2100010007));
		optionId
				.put(BookException.ERROR_CANNOT_OBTAIN_SESSION_INFORMATION, new Long(2100010008));
		optionId.put(BookException.ERROR_CANNOT_CLOSE_BOOK, new Long(2100010009));
		optionId.put(BookException.ERROR_CANNOT_CREATE_BOOK_FORMAT, new Long(2100010010));
		optionId.put(BookException.ERROR_BOOK_NOT_OPEN, new Long(2100010011));
		optionId.put(BookException.ERROR_BOOK_HAS_NOT_FORMATS, new Long(2100010012));
		optionId.put(BookException.ERROR_CANNOT_FIND_REGISTERS, new Long(2100010013));
		optionId.put(BookException.ERROR_CANNOT_FIND_DOCS, new Long(2100010014));
		optionId.put(BookException.ERROR_CANNOT_LOCK_FOLDER, new Long(2100010015));
		optionId.put(BookException.ERROR_CANNOT_CLOSE_FOLDER, new Long(2100010016));
		optionId
				.put(BookException.ERROR_CANNOT_FIND_DISTRIBUTION_HISTORY, new Long(2100010017));
		optionId.put(BookException.ERROR_CANNOT_FIND_ASOC_FDR, new Long(2100010018));
		optionId.put(BookException.ERROR_CANNOT_SAVE_PERSIST_FIELDS, new Long(2100010019));
		optionId.put(BookException.ERROR_CANNOT_LOAD_PERSIST_FIELDS, new Long(2100010020));
		optionId.put(BookException.ERROR_CANNOT_SAVE_ORIG_DOC, new Long(2100010021));
		optionId.put(BookException.ERROR_CANNOT_SAVE_ORIG_DOC_INVALID_CODE,
				new Long(2100010022));
		optionId.put(BookException.ERROR_CANNOT_FIND_ORIG_DOC, new Long(2100010023));
		optionId.put(BookException.ERROR_CANNOT_ADD_PFIS, new Long(2100010024));
		optionId.put(BookException.ERROR_CANNOT_ADD_PJUR, new Long(2100010025));
		optionId.put(BookException.ERROR_CANNOT_UPDATE_PFIS, new Long(2100010026));
		optionId.put(BookException.ERROR_CANNOT_UPDATE_PJUR, new Long(2100010027));
		optionId.put(BookException.ERROR_CANNOT_LOAD_FILE, new Long(2100010028));
		optionId.put(BookException.ERROR_CANNOT_ACCESS_DOCUMENT, new Long(2100010029));
		optionId.put(BookException.ERROR_CANNOT_SAVE_FILE, new Long(2100010030));
		optionId.put(BookException.ERROR_CANNOT_FIND_INTEREST_LIST, new Long(2100010031));
		optionId.put(BookException.ERROR_CANNOT_FIND_ADDRESS, new Long(2100010032));
		optionId.put(BookException.ERROR_CANNOT_FIND_DOM, new Long(2100010033));
		optionId.put(BookException.ERROR_CANNOT_FIND_ADDITIONAL_SUBJECT_INFO,
				new Long(2100010034));
		optionId.put(BookException.ERROR_CANNOT_FIND_IDOCARCHDET, new Long(2100010035));
		optionId.put(BookException.ERROR_PAGE_SIZE, new Long(2100010036));
		optionId.put(BookException.ERROR_QUERY_NOT_OPEN, new Long(2100010037));
		optionId.put(BookException.ERROR_QUERY_ALREADY_OPEN, new Long(2100010038));
		optionId.put(BookException.ERROR_ROW_OUTSIDE, new Long(2100010039));
		optionId.put(BookException.ERROR_CANNOT_CREATE_NEW_FOLDER, new Long(2100010040));
		optionId.put(BookException.ERROR_NUMERATION_NOT_SUPPORTED, new Long(2100010041));
		optionId.put(BookException.ERROR_UPDATE_FOLDER, new Long(2100010042));
		optionId.put(BookException.ERROR_SAVE_COMPUL_FILES, new Long(2100010043));
		optionId.put(BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION, new Long(2100010044));
		optionId.put(
				BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION_INVALIDCODE,
				new Long(2100010045));
		optionId.put(
				BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER,
				new Long(2100010046));

		return optionId;
	}

	public Map getOptionId() {
		return optionId;
	}
}
