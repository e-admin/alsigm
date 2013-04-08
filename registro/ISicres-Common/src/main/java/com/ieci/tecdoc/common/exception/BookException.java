//
// FileName: BookException.java
//
package com.ieci.tecdoc.common.exception;

import java.io.Serializable;

/**
 * @author lmvicente
 * @version @since @creationDate 02-abr-2004
 */

public class BookException extends TecDocException implements Serializable {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/

    public static final String ERROR_UDPATEFIELDS_NOT_VALID_CODE = "bookexception.notValidCode";
    public static final String ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED = "bookexception.distributionTypeNotSupported";
    public static final String ERROR_CANNOT_SHOW_REGISTER = "bookexception.error_cannot_show_register";
    public static final String ERROR_ASSOCIATEDBOOK_NOT_OPEN = "bookexception.associated_book_not_open";
    public static final String ERROR_ASSOCIATEDBOOK_NOT_IN_BOOK = "bookexception.associated_book_not_in_book";
	public static final String ERROR_BOOK_NOTFOUND = "bookexception.error_book_notfound";
	public static final String ERROR_CANNOT_OPEN_BOOK = "bookexception.error_cannot_open_book";
	public static final String ERROR_CANNOT_OBTAIN_DBSERVER_DATE = "bookexception.error_cannot_obtein_dbserver_date";
	public static final String ERROR_CANNOT_OBTAIN_SESSION_INFORMATION = "bookexception.error_cannot_session_information";
	public static final String ERROR_CANNOT_CLOSE_BOOK = "bookexception.error_cannot_close_book";
	public static final String ERROR_CANNOT_CREATE_BOOK_FORMAT = "bookexception.error_cannot_create_format_book";
	public static final String ERROR_BOOK_NOT_OPEN = "bookexception.error_book_not_open";
	public static final String ERROR_BOOK_HAS_NOT_FORMATS = "bookexception.error_book_has_not_formats";
	public static final String ERROR_CANNOT_FIND_REGISTERS = "bookexception.cannot_find_registers";
	public static final String ERROR_CANNOT_FIND_DOCS = "bookexception.cannot_find_docs";
	public static final String ERROR_CANNOT_LOCK_FOLDER = "bookexception.cannot_lock_folder";
	public static final String ERROR_CANNOT_CLOSE_FOLDER = "bookexception.cannot_close_folder";
	public static final String ERROR_CANNOT_FIND_DISTRIBUTION_HISTORY = "bookexception.cannot_find_distribution_history";
	public static final String ERROR_CANNOT_FIND_MODIFICATION_HISTORY = "bookexception.cannot_find_modification_history";
	public static final String ERROR_CANNOT_FIND_ASOC_FDR = "bookexception.cannot_find_asoc_fdr";
	public static final String ERROR_CANNOT_SAVE_PERSIST_FIELDS = "bookexception.cannot_save_persist_fields";
	public static final String ERROR_CANNOT_LOAD_PERSIST_FIELDS = "bookexception.cannot_load_persist_fields";
	public static final String ERROR_CANNOT_SAVE_ORIG_DOC = "bookexception.cannot_save_orig_doc";
	public static final String ERROR_CANNOT_SAVE_ORIG_DOC_INVALID_CODE = "bookexception.cannot_save_orig_doc_invalid_code";
	public static final String ERROR_CANNOT_FIND_ORIG_DOC = "bookexception.cannot_find_orig_doc";
	public static final String ERROR_CANNOT_ADD_PFIS = "bookexception.cannot_add_pfis";
	public static final String ERROR_CANNOT_ADD_PJUR = "bookexception.cannot_add_pjur";
	public static final String ERROR_CANNOT_UPDATE_PFIS = "bookexception.cannot_update_pfis";
	public static final String ERROR_CANNOT_UPDATE_PJUR = "bookexception.cannot_update_pjur";
	public static final String ERROR_CANNOT_DELETE_DIRECTION = "bookexception.cannot_delete_direcction";
	public static final String ERROR_CANNOT_LOAD_FILE = "bookexception.cannot_load_file";
	public static final String ERROR_CANNOT_ACCESS_DOCUMENT = "bookexception.cannot_access_document";
	public static final String ERROR_CANNOT_ADD_DOCUMENT = "bookexception.cannot_add_document";
	public static final String ERROR_CANNOT_SAVE_FILE = "bookexception.cannot_save_file";
	public static final String ERROR_CANNOT_FIND_INTEREST_LIST = "bookexception.cannot_find_interest_list";
	public static final String ERROR_CANNOT_FIND_ADDRESS = "bookexception.cannot_find_address";
	public static final String ERROR_CANNOT_FIND_DOM = "bookexception.cannot_find_dom";
	public static final String ERROR_CANNOT_FIND_ADDITIONAL_SUBJECT_INFO = "bookexception.cannot_find_additional_subject_info";
	public static final String ERROR_CANNOT_FIND_IDOCARCHDET = "bookexception.cannot_find_idocarchdet";
	public static final String ERROR_PAGE_SIZE = "bookexception.error_page_size";
	public static final String ERROR_QUERY_NOT_OPEN = "bookexception.query_not_open";
	public static final String ERROR_QUERY_ALREADY_OPEN = "bookexception.query_already_open";
	public static final String ERROR_ROW_OUTSIDE = "bookexception.row_outside";
	public static final String ERROR_CANNOT_CREATE_NEW_FOLDER = "bookexception.cannot_create_new_folder";
	public static final String ERROR_NUMERATION_NOT_SUPPORTED = "bookexception.numeration_not_supported";
	public static final String ERROR_UPDATE_FOLDER = "bookexception.update_folder";
	public static final String ERROR_SAVE_COMPUL_FILES = "bookexception.save_compul_files";
	public static final String ERROR_CANNOT_UPDATE_DISTRIBUTION = "bookexception.cannotChangeDistribution";
	public static final String ERROR_CANNOT_UPDATE_DISTRIBUTION_INVALIDCODE = "bookexception.cannotChangeDistributionInvalidCode";
	public static final String ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER = "bookexception.cannotChangeDistributionLockRegister";
	public static final String ERROR_CANNOT_GET_REGISTERS_TO_CLOSE_INVALID_CODE = "bookexception.cannotGetRegistersToCloseInvalidCode";
	public static final String ERROR_CANNOT_GET_REGISTERS_TO_CLOSE = "bookexception.cannotGetRegistersToClose";
	public static final String ERROR_ASOCREGS_VALIDATED_SEARCH = "bookexception.asocreg.validated.search";
	public static final String ERROR_ASOCREGS_VALIDATED_SELECTED = "bookexception.asocreg.validated.selected";
	public static final String ERROR_CANNOT_FIND_ADDRTEL = "bookexception.cannot_find_addrtel";

	public static final String ERROR_AUDIT_CHANGES = "bookexception.audit.changes";
	public static final String ERROR_CANNOT_SIGN_DOCUMENT = "bookexception.cannot_sign_document";
	public static final String ERROR_BOOK_CLOSE = "bookexception.error_book_close";

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	/**
	 * @param key
	 */
	public BookException(String key) {
		super(key);
	}

	/**
	 * @param exception
	 */
	public BookException(TecDocException exception) {
		super(exception);
	}

	/**
	 * @param key
	 * @param obj
	 */
	public BookException(String key, Object obj) {
		super(key, obj);
	}

	/**
	 * @param key
	 * @param objs
	 */
	public BookException(String key, Object[] objs) {
		super(key, objs);
	}

	/**
	 * @param key
	 * @param nested
	 */
	public BookException(String key, Throwable nested) {
		super(key, nested);
	}

	/**
	 * @param nested
	 */
	public BookException(Throwable nested) {
		super(nested);
	}

	/**
	 * @param key
	 * @param obj
	 * @param nested
	 */
	public BookException(String key, Object obj, Throwable nested) {
		super(key, obj, nested);
	}

	/**
	 * @param key
	 * @param objs
	 * @param nested
	 */
	public BookException(String key, Object[] objs, Throwable nested) {
		super(key, objs, nested);
	}

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/

}
