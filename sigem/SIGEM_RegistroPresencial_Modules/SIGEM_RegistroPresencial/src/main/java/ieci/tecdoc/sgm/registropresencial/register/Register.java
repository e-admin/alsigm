package ieci.tecdoc.sgm.registropresencial.register;

import gnu.trove.THashMap;
import ieci.tecdoc.sgm.registropresencial.info.InfoRegister;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.idoc.decoder.form.FCtrlDef;
import com.ieci.tecdoc.idoc.decoder.form.FPageDef;
import com.ieci.tecdoc.idoc.decoder.form.FormFormat;
import com.ieci.tecdoc.isicres.session.attributes.AttributesSession;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.folder.FolderDataSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author lmvicente
 * @version
 * @since
 * @creationDate 24-mar-2004
 */

public class Register extends FolderSession implements ServerKeys, Keys,
		HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	// private static final String PAR_IZQ = "(";
	//
	// private static final String PAR_DER = ")";
	//
	// private static final String GUION = "-";
	private static final Logger log = Logger.getLogger(Register.class);

	protected static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy");

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static FolderDataSession createNewFolder(String sessionID,
			Integer bookID, AxSf axsfNew, List inter, Map documents,
			Integer launchDistOutRegister, Locale locale, String entidad,
			boolean consolidacion) throws BookException, SessionException,
			ValidationException {

		FolderDataSession data = createFolderWithDocuments(sessionID, bookID,
				axsfNew, inter, documents, launchDistOutRegister, locale,
				entidad, consolidacion, false);

		return data;
	}

	/**
	 * Metodo que actualiza un registro
	 *
	 * @param sessionID
	 * @param bookID - ID del libro
	 * @param axsfNew - {@link AxSf} Objeto con los cambios a realizar en el registro
	 * @param folderId - ID del registro
	 * @param inter - Interesados
	 * @param documents - Documentos
	 * @param launchDistOutRegister - Parametro de configuracion para la distribución automática
	 * @param locale
	 * @param entidad
	 *
	 * @return {@link FolderDataSession}
	 *
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static FolderDataSession updateFolder(String sessionID,
			Integer bookID, AxSf axsfNew, Integer folderId, List inter,
			Map documents, Integer launchDistOutRegister, Locale locale,
			String entidad) throws BookException, SessionException,
			ValidationException {


		boolean updateDate = false;
		// se buscan en la config si se debe o no actualizar la fecha de trabajo (FLD4)
		Integer modifySystemDate = new Integer(BookSession.invesicresConf(
				entidad).getModifySystemDate());
		if (modifySystemDate.intValue() == 1) {
			updateDate = true;
		}

		//actualizamos el registro
		return updateFolder(sessionID, bookID, folderId.intValue(), axsfNew,
				inter, documents, updateDate, launchDistOutRegister, locale, entidad);
	}

	public static FolderDataSession addDocument(String sessionID,
			Integer bookID, Integer folderId, Map documents, AxSf axsfNew,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		FolderDataSession data = new FolderDataSession(false, false, axsfNew,
				null, documents, false);

		data = FolderSession.preparationOfFolder(sessionID, bookID, folderId
				.intValue(), false, new Integer(0), locale.getLanguage(),
				entidad, data);

		data.setDocumentsUpdate(updateDocumentsImages(sessionID, bookID, data
				.getUser(), folderId.intValue(), data.getDocumentsUpdate(),
				data.getAxsfNew(), data.getAxsfNew(), data.getScrofic(),
				locale, entidad));

		return data;

	}

	public static FolderDataSession importNewFolder(String sessionID,
			Integer bookID, AxSf axsfNew, List inter, Map documents,
			Integer launchDistOutRegister, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {

		FolderDataSession data = createFolderWithDocuments(sessionID, bookID,
				axsfNew, inter, documents, launchDistOutRegister, locale,
				entidad, false, true);

		return data;
	}

	public static InfoRegister[] findFolder(String sessionID, Integer bookID,
			AxSfQuery axsfQuery, Locale locale, String entidad)
			throws BookException, SessionException, TecDocException,
			ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		InfoRegister[] registersInfo = null;

		List bookIds = new ArrayList();
		bookIds.add(bookID);

		axsfQuery = axsfQueryPreparateToQuery(axsfQuery);

		AxSfQueryResults queryResults = RegisterServicesUtil
				.openRegistersQuery(sessionID, axsfQuery, bookIds, entidad);
		if (queryResults != null) {
			BookSession.openBook(sessionID, bookID, entidad);

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag
					.get(HibernateKeys.HIBERNATE_ScrOfic);

			queryResults.setPageSize(queryResults.getTotalQuerySize());
			axsfQuery.setPageSize(queryResults.getTotalQuerySize());

			queryResults = Register.navigateRegistersQuery(sessionID,
					queryResults, axsfQuery,
					com.ieci.tecdoc.common.isicres.Keys.QUERY_FIRST_PAGE,
					locale, entidad);

			registersInfo = getRegistersInfo(sessionID, bookID, entidad,
					queryResults, user, scrofic);

		} else {
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		}

		return registersInfo;

	}

	public static AxSfQueryResults navigateRegistersQuery(String sessionID,
			AxSfQueryResults queryResults, AxSfQuery axsfQuery,
			String queryNavigationType, Locale locale, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_NotNull(queryNavigationType,
				ValidationException.ATTRIBUTE_NAVIGATIONTYPE);

		Transaction tran = null;
		Integer bookID = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			if (queryResults == null) {
				throw new BookException(BookException.ERROR_QUERY_NOT_OPEN);
			}
			bookID = queryResults.getBookId();
			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			AxSf axsf = (AxSf) bookInformation.get(AXSF);

			/*
			 * Filtro eliminado para que no compruebe la oficina de registro del
			 * usuario. Ejemplo: (fld5=6)
			 */
			// String filter = (String) bookInformation.get(QUERY_FILTER);
			String filter = null;

			Idocarchdet idoc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

			Map result = new TreeMap();
			AxSfEntity axSfEntity = new AxSfEntity();
			Collection col = axSfEntity.findByAxSfQuery(axsfQuery, axsf,
					queryResults, queryNavigationType, filter, entidad);
			extractRegistersFromQuery(col, result, session, idoc, axSfEntity,
					locale.getLanguage(), entidad);
			if (result.isEmpty()) {
				throw new BookException(
						BookException.ERROR_CANNOT_FIND_REGISTERS);
			}
			HibernateUtil.commitTransaction(tran);

			return queryResults.clone(result.values());
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to execute the book query for [" + bookID
					+ "] for the session [" + sessionID + "]", e);
			throw new BookException(BookException.ERROR_CANNOT_FIND_REGISTERS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	// public static String getScrOrgName(String sessionID, ScrOrg scr,
	// Locale locale, String entidad) throws AttributesException,
	// ValidationException,
	// SessionException {
	// if (scr == null) {
	// return "";
	// }
	// String name = scr.getName();
	// // Obtener el padre de nivel superior
	// String aux = "";
	// if (scr.getIdFather() != null) {
	// ScrOrg father = ValidationSessionEx.getScrOrg(sessionID, scr
	// .getIdFather().intValue(), entidad);
	//
	// ScrOrg parent = ValidationSessionEx.getTopLevelParentScrOrg(
	// sessionID, scr.getIdFather().intValue(), entidad);
	//
	// if (father != null && parent != null) {
	// if (father.getId().equals(parent.getId())) {
	// aux = PAR_IZQ + parent.getName() + PAR_DER;
	// } else {
	// aux = PAR_IZQ + father.getName() + GUION
	// + parent.getAcron() + PAR_DER;
	// }
	// }
	// }
	// return name + aux;
	// }

	public static Map getValidationFields(AxSf axsf, String sessionID,
			Integer bookID, int page, Locale locale, String entidad)
			throws ValidationException, AttributesException, BookException,
			SessionException {
		List list = getAttributesForValidationForForm(sessionID, bookID, axsf,
				page, entidad);

		Map result = new HashMap();
		if (!list.isEmpty()) {
			Map fldids = new HashMap(list.size());
			Integer fldid = null;
			String fldName = null;
			List values = null;
			// Recogemos los valores que hay que validar
			for (Iterator it = list.iterator(); it.hasNext();) {
				fldid = (Integer) it.next();
				fldName = "fld" + fldid.toString();
				values = new ArrayList();
				values.add(axsf.getAttributeValueAsString(fldName));
				fldids.put(fldid, values);
			}
			result = AttributesSession.getExtendedValidationFieldValues(
					sessionID, bookID, fldids, locale, entidad);

		}
		return result;
	}

	private static List getAttributesForValidationForForm(String sessionID,
			Integer bookID, AxSf axsf, int page, String entidad)
			throws ValidationException, AttributesException, BookException,
			SessionException {
		String data = axsf.getFormat().getData();
		FormFormat formFormat = new FormFormat(data);
		List result = new ArrayList();
		List flds = AttributesSession.getExtendedValidationFieldsForBook(
				sessionID, bookID, entidad);

		int aux = 0;
		// Sobre los nombre de atributos sacamos los que sean mayores que
		// EREG... Y SREG
		// y que ademas existan en el formato de tabla
		for (Iterator it = flds.iterator(); it.hasNext();) {
			aux = Integer.parseInt(it.next().toString());

			if (axsf instanceof AxSfIn) {
				if (aux > Keys.EREG_FDR_MATTER) {
					if (existAttributeInQueryFormat(formFormat, aux, page)) {
						result.add(new Integer(aux));
					}
				}
			} else {
				if (aux > Keys.SREG_FDR_MATTER) {
					if (existAttributeInQueryFormat(formFormat, aux, page)) {
						result.add(new Integer(aux));
					}
				}
			}
		}
		return result;
	}

	private static boolean existAttributeInQueryFormat(FormFormat ff,
			int fldid, int page) {
		// Recorremos el formato de tabla para ver si el atributo aparece en el
		// resultado
		// Sino aparece no buscamos su valor de validación
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

	/**
	 *
	 * @param axsfQuery
	 * @return
	 */
	private static AxSfQuery axsfQueryPreparateToQuery(AxSfQuery axsfQuery) {
		if (axsfQuery != null && axsfQuery.getFields() != null) {
			for (Iterator it = axsfQuery.getFields().iterator(); it.hasNext();) {
				AxSfQueryField field = (AxSfQueryField) it.next();
				if (field.getFldId() != null
						&& (!field.getFldId().startsWith("fld"))) {
					field.setFldId("fld" + field.getFldId());
				}
			}
		}

		return axsfQuery;
	}

	/**
	 *
	 * @param sessionID
	 * @param bookId
	 * @param entidad
	 * @param queryResults
	 * @param user
	 * @param scrOfic
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws TecDocException
	 */
	private static InfoRegister[] getRegistersInfo(String sessionID,
			Integer bookId, String entidad, AxSfQueryResults queryResults,
			AuthenticationUser user, ScrOfic scrOfic) throws BookException,
			SessionException, TecDocException {
		InfoRegister[] registersInfo = null;
		if ((queryResults != null) && (queryResults.getResults() != null)
				&& (!queryResults.getResults().isEmpty())) {

			int size = queryResults.getResults().size();
			registersInfo = new InfoRegister[size];

			int i = 0;
			for (Iterator it = queryResults.getResults().iterator(); it
					.hasNext();) {
				AxSf axsf = (AxSf) it.next();

				InfoRegister wsRegisterInfo = ConsultRegister
						.consultRegisterInfo(bookId, scrOfic, user, axsf, axsf
								.getLocale());

				if (i < size) {
					registersInfo[i] = wsRegisterInfo;
				}
				i++;
			}
		}

		return registersInfo;
	}

}