package com.ieci.tecdoc.isicres.usecase.distribution;

import gnu.trove.THashMap;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.DistributionResults;
import com.ieci.tecdoc.common.isicres.ScrDistRegResults;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISDistribution;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.distribution.xml.DistributionFields;
import com.ieci.tecdoc.isicres.usecase.distribution.xml.DistributionSearchFields;
import com.ieci.tecdoc.isicres.usecase.distribution.xml.XMLDistributionList;
import com.ieci.tecdoc.isicres.usecase.distribution.xml.XMLDistributionList.IntegerComparator;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/*
 * @author LMVICENTE @creationDate 25-oct-2004 9:42:05
 *
 * @version @since
 */
public class DistributionUseCase {

	private static Logger _logger = Logger.getLogger(DistributionUseCase.class);

	private static final String ORACLE = "ORACLE";
	private static final String SQLSERVER = "SQLSERVER";
	private static final String POSTGRESQL = "POSTGRESQL";
	private static final String DB2 = "DB2";

	private static final String GUION = "-";
	private static final String ALMOHADILLA = "#";

	private static final String PUNTO_COMA = ";";

	private static final String COMILLA = "'";

	private static final String COMA = ",";

	private static final String PER_CENT = "%";

	private static final String PAR_IZQ = "(";

	private static final String PAR_DER = ")";

	private static final String OR = " OR ";
	private static final String IS_NULL = "is null";
	private static final String ESPACIO = " ";

	private static final String TO_CONVERT_DATE = "convert(datetime";
	private static final String TO_CONVERT_DATE_FORMAT = "0";
	private static final String TO_DATE = "to_date(";
	private static final String TO_TIMESTAMP = "to_timestamp(";

	private static final String TO_CHAR = "to_char(";

	private static final String TIME_INITIAL = " 00:00:00";

	private static final String TIME_FINAL = " 23:59:59";

	private static final String DATE_FORMAT = "DD-MM-YYYY HH24:MI:SS";
	private static final String DATE_FORMAT_DB2 = "YYYY-MM-DD HH24:MI:SS";

	private static final String MONTH_FORMAT = "MM";

	private static final String YEAR_FORMAT = "YYYY";

	private static final String CONTENT = " LIKE ";

	private static final String MONTH = "MONTH(";

	private static final String YEAR = "YEAR(";

	private static final String SCRORGS = " IN (SELECT ID FROM SCR_ORGS WHERE CODE LIKE ";

	private static final String SCRORGSCONNECT = " IN (SELECT ID FROM SCR_ORGS WHERE TYPE <> 0 START WITH CODE= ";

	private static final String BYPRIOR = " CONNECT BY PRIOR ID = ID_FATHER) ";

	private static final String SCRCA = " IN (SELECT ID FROM SCR_CA WHERE CODE LIKE ";

	private static final String TYPE = " AND TYPE <> 0)";

	public DistributionUseCase() {
		super();
	}

	public Document getDistribution(UseCaseConf useCaseConf, int state,
			int firstRow, int typeDist, String distWhere, String regWhere,
			String orderBy) throws ValidationException, DistributionException,
			SessionException, BookException, SecurityException {

		if (_logger.isDebugEnabled()) {
			_logger.debug("getDistribution state [" + state + "] firstRow ["
					+ firstRow + "] typeDist [" + typeDist + "]");
		}

		// Integer timeout = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_DISTRIBUTION_TIMEOUT));
		Integer timeout = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getTimeOut());
		Integer distPerms[] = SecuritySession.getScrDistPermission(useCaseConf
				.getSessionID());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		boolean isOfficeAsoc = Boolean
				.valueOf(
						Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC))
				.booleanValue();

		DistributionResults distributionResults = DistributionSession
				.getDistribution(
						useCaseConf.getSessionID(),
						state,
						firstRow,
						Integer.parseInt(Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE)),
						typeDist, distWhere, regWhere, isOfficeAsoc,
						useCaseConf.getLocale(), useCaseConf.getEntidadId(),
						useCaseConf.getUseLdap().booleanValue(), orderBy);

		Map booksReg = descompBookIDRegs(distributionResults);
		Map axsfs = new HashMap();
		Map vldAttribMap = new HashMap();
		for (Iterator it = booksReg.keySet().iterator(); it.hasNext();) {
			Integer bookID = (Integer) it.next();
			if ((bookID.intValue() != 0) && (booksReg.get(bookID) != null)) {

				BookSession.openBook(useCaseConf.getSessionID(), bookID,
						useCaseConf.getEntidadId());

				Validator.validate_String_NotNull_LengthMayorZero(useCaseConf
						.getSessionID(), ValidationException.ATTRIBUTE_SESSION);
				Validator.validate_Integer(bookID,
						ValidationException.ATTRIBUTE_BOOK);

				AxSf axsf = null;
				Transaction tran = null;
				try {
					Session session = HibernateUtil.currentSession(useCaseConf
							.getEntidadId());
					tran = session.beginTransaction();

					// Recuperamos la sesión
					CacheBag cacheBag = CacheFactory.getCacheInterface()
							.getCacheEntry(useCaseConf.getSessionID());

					// Es necesario tener el libro abierto para consultar su
					// contenido.
					if (!cacheBag.containsKey(bookID)) {
						throw new BookException(
								BookException.ERROR_BOOK_NOT_OPEN);
					}

					THashMap bookInformation = (THashMap) cacheBag.get(bookID);
					if (!cacheBag.containsKey(bookID)) {
						throw new BookException(
								BookException.ERROR_BOOK_NOT_OPEN);
					}
					// AxSf axsfP = (AxSf) bookInformation.get(AXSF);
					Idocarchdet idoc = (Idocarchdet) bookInformation
							.get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);

					List regs = (List) booksReg.get(bookID);
					AxSfEntity axSfEntity = null;
					for (Iterator iter = regs.iterator(); iter.hasNext();) {
						axSfEntity = new AxSfEntity();
						Integer fdrid = (Integer) iter.next();
						axsf = FolderSession.getBookFolder(session,
								bookInformation, useCaseConf.getSessionID(),
								bookID, fdrid.intValue(), useCaseConf
										.getLocale(), useCaseConf
										.getEntidadId(), idoc, axSfEntity,
								vldAttribMap);
						String key = bookID + "_" + fdrid.intValue();
						axsfs.put(key, axsf);
					}

					HibernateUtil.commitTransaction(tran);

				} catch (BookException bE) {
					HibernateUtil.rollbackTransaction(tran);
					throw bE;
				} catch (SessionException sE) {
					HibernateUtil.rollbackTransaction(tran);
					throw sE;
				} catch (Exception e) {
					HibernateUtil.rollbackTransaction(tran);
					throw new BookException(
							BookException.ERROR_CANNOT_FIND_REGISTERS);
				} finally {
					HibernateUtil.closeSession(useCaseConf.getEntidadId());
				}
			}

		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("distributionResults.getTotalSize():"
					+ distributionResults.getTotalSize());
			_logger.debug("distributionResults.getIdocarchhdr():"
					+ distributionResults.getIdocarchhdr());
			_logger.debug("axsfs:" + axsfs);
			_logger.debug("distributionResults:" + distributionResults);
		}

		int paso = Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE));

		//Se obtienen los datos a mostrar en pantalla
		List listScrDistRegResults = getScrDistRegResults(useCaseConf,
				distributionResults, axsfs);

		return XMLDistributionList.getXMLDistributionList(firstRow, paso,
				distributionResults.getTotalSize(), useCaseConf.getLocale(),
				listScrDistRegResults, typeDist, timeout, distPerms,
				distributionResults.getActualDate(), sessionInformation);
	}

	public Map descompBookIDRegs(DistributionResults distributionResults) {
		Map booksReg = new HashMap();
		List regs = new ArrayList();

		for (Iterator it = distributionResults.getBooks().iterator(); it
				.hasNext();) {
			String key = it.next().toString();
			StringTokenizer tokenizer = new StringTokenizer(key, "_");
			Integer bookID = new Integer(tokenizer.nextToken());
			int fdrid = Integer.parseInt(tokenizer.nextToken());

			if (_logger.isDebugEnabled()) {
				_logger.debug("bookID:" + bookID + "fdrid:" + fdrid);
			}
			if (booksReg.containsKey(bookID)) {
				List aux = (List) booksReg.get(bookID);
				aux.add(new Integer(fdrid));
				booksReg.put(bookID, aux);
			} else {
				regs = new ArrayList();
				regs.add(new Integer(fdrid));
				booksReg.put(bookID, regs);
			}
		}
		if (_logger.isDebugEnabled()) {
			_logger.debug("bookID:" + booksReg);
		}
		return booksReg;
	}

	public Document acceptDistribution(UseCaseConf useCaseConf, List dis,
			int state, int firstRow, int typeDist, Integer bookId,
			String distWhere, String regWhere, String orderBy) throws ValidationException,
			DistributionException, SessionException, BookException,
			SecurityException {

		List createPermBooks = null;
		Map createPermBooksInfo = null;
		if (bookId.intValue() == 0) {
			List inList = BookSession.getInBooks(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			// ScrRegstate scrRegState = null;
			boolean canCreate = false;
			createPermBooks = new ArrayList();
			createPermBooksInfo = new HashMap();
			for (Iterator it = inList.iterator(); it.hasNext();) {
				// scrRegState = (ScrRegstate) it.next();
				// Integer id = scrRegState.getIdocarchhdr().getId();
				// String name = scrRegState.getIdocarchhdr().getName();
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				Integer id = scrRegStateByLanguage.getIdocarchhdrId();
				String name = scrRegStateByLanguage.getIdocarchhdrName();
				BookSession.openBook(useCaseConf.getSessionID(), id,
						useCaseConf.getEntidadId());
				canCreate = SecuritySession.canCreate(useCaseConf
						.getSessionID(), id);
				if (canCreate) {
					createPermBooks.add(id);
					createPermBooksInfo.put(id, name);
				}
			}
		}
		List archidFdr = null;
		ScrDistreg distReg = null;
		Object result = null;
		int maxResults = Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE));
		// Integer launchDistOutRegister = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_DISTRIBUTION_LAUNCH_DIST_OUT_REGISTER));
		Integer launchDistOutRegister = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getDistSRegister());

		boolean isOfficeAsoc = Boolean
				.valueOf(
						Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC))
				.booleanValue();

		DistributionResults distributionResults = DistributionSession
				.getDistribution(useCaseConf.getSessionID(), state, firstRow,
						maxResults, typeDist, distWhere, regWhere,
						isOfficeAsoc, useCaseConf.getLocale(), useCaseConf
								.getEntidadId(), useCaseConf.getUseLdap()
								.booleanValue(), orderBy);
		try {
			result = DistributionSession.acceptDistribution(useCaseConf
					.getSessionID(), dis, state, firstRow, maxResults,
					typeDist, bookId, createPermBooks, distributionResults,
					distWhere, regWhere, launchDistOutRegister, useCaseConf
							.getLocale(), useCaseConf.getEntidadId());
			if (result instanceof List) {
				archidFdr = (List) result;
				return null;
			} else {
				archidFdr = (List) ((Map) result).get(new Integer(-1));
				return XMLDistributionList
						.getXMLDistributionVldBooks(createPermBooksInfo);
			}
		} finally {
			if (archidFdr != null && !archidFdr.isEmpty()) {
				for (Iterator it4 = archidFdr.iterator(); it4.hasNext();) {
					distReg = (ScrDistreg) it4.next();
					FolderSession.closeFolder(useCaseConf.getSessionID(),
							new Integer(distReg.getIdArch()), distReg
									.getIdFdr(), useCaseConf.getEntidadId());
				}
			}
		}
	}

	public Document acceptDistributionEx(UseCaseConf useCaseConf, List dis,
			int state, int firstRow, int typeDist, Integer bookId,
			String distWhere, String regWhere, String orderBy) throws ValidationException,
			DistributionException, SessionException, BookException,
			SecurityException {

		List createPermBooks = null;
		Map createPermBooksInfo = null;
		if (bookId.intValue() == 0) {
			List inList = BookSession.getInBooks(useCaseConf.getSessionID(),
					useCaseConf.getLocale(), useCaseConf.getEntidadId());
			// ScrRegstate scrRegState = null;
			boolean canCreate = false;
			createPermBooks = new ArrayList();
			createPermBooksInfo = new HashMap();
			for (Iterator it = inList.iterator(); it.hasNext();) {
				// scrRegState = (ScrRegstate) it.next();
				// Integer id = scrRegState.getIdocarchhdr().getId();
				// String name = scrRegState.getIdocarchhdr().getName();
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				Integer id = scrRegStateByLanguage.getIdocarchhdrId();
				String name = scrRegStateByLanguage.getIdocarchhdrName();
				BookSession.openBook(useCaseConf.getSessionID(), id,
						useCaseConf.getEntidadId());
				canCreate = SecuritySession.canCreate(useCaseConf
						.getSessionID(), id);
				if (canCreate) {
					createPermBooks.add(id);
					createPermBooksInfo.put(id, name);
				}
			}
		}
		List archidFdr = null;
		ScrDistreg distReg = null;
		Object result = null;
		int maxResults = Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE));
		// Integer launchDistOutRegister = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_DISTRIBUTION_LAUNCH_DIST_OUT_REGISTER));
		Integer launchDistOutRegister = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getDistSRegister());

		boolean isOfficeAsoc = Boolean
				.valueOf(
						Configurator
								.getInstance()
								.getProperty(
										ConfigurationKeys.KEY_DESKTOP_DISTRIBUTION_OFFICE_ASOC))
				.booleanValue();

		DistributionResults distributionResults = DistributionSession
				.getDistribution(useCaseConf.getSessionID(), state, firstRow,
						maxResults, typeDist, distWhere, regWhere,
						isOfficeAsoc, useCaseConf.getLocale(), useCaseConf
								.getEntidadId(), useCaseConf.getUseLdap()
								.booleanValue(), orderBy);
		try {
			result = DistributionSession.acceptDistribution(useCaseConf
					.getSessionID(), dis, state, firstRow, maxResults,
					typeDist, bookId, createPermBooks, distributionResults,
					distWhere, regWhere, launchDistOutRegister, useCaseConf
							.getLocale(), useCaseConf.getEntidadId());
			if (result instanceof List) {
				archidFdr = (List) result;
				return null;
			} else {
				archidFdr = (List) ((Map) result).get(new Integer(-1));
				return XMLDistributionList
						.getXMLDistributionVldBooks(createPermBooksInfo);
			}
		} finally {
			if (archidFdr != null && !archidFdr.isEmpty()) {
				for (Iterator it4 = archidFdr.iterator(); it4.hasNext();) {
					distReg = (ScrDistreg) it4.next();
					FolderSession.closeFolder(useCaseConf.getSessionID(),
							new Integer(distReg.getIdArch()), distReg
									.getIdFdr(), useCaseConf.getEntidadId());
				}
			}
		}
	}

	/**
	 * Metodo que redistribuye una distribucion cuando se cambia el destino
	 * (FLD8) del registro desde la bandeja de distribucion
	 *
	 * @param sessionId
	 * @param idEntidad
	 * @param isLdap
	 * @param locale
	 * @param dis
	 * @param state
	 * @param firstRow
	 * @param typeDist
	 * @param remarks
	 * @param distWhere
	 * @param regWhere
	 * @throws ValidationException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 */
	public void rejectDistribution(String sessionId, String idEntidad,
			boolean isLdap, Locale locale, List dis, int state, int firstRow,
			int typeDist, String remarks, String distWhere, String regWhere)
			throws ValidationException, DistributionException,
			SessionException, BookException {
		UseCaseConf useCaseConf = new UseCaseConf();
		useCaseConf.setEntidadId(idEntidad);
		useCaseConf.setSessionID(sessionId);
		useCaseConf.setUseLdap(Boolean.valueOf(isLdap));
		useCaseConf.setLocale(locale);

		rejectDistribution(useCaseConf, dis, state, firstRow, typeDist,
				remarks, distWhere, regWhere, null);
	}

	public void rejectDistribution(UseCaseConf useCaseConf, List dis,
			int state, int firstRow, int typeDist, String remarks,
			String distWhere, String regWhere, String orderBy) throws ValidationException,
			DistributionException, SessionException, BookException {
		List archidFdr = new ArrayList();
		ScrDistreg distReg = null;
		int maxResults = Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE));

		try {
			archidFdr = DistributionSession.rejectDistribution(useCaseConf
					.getSessionID(), dis, remarks, state, firstRow, maxResults,
					typeDist, distWhere, regWhere, useCaseConf.getLocale(),
					useCaseConf.getEntidadId(), useCaseConf.getUseLdap()
							.booleanValue(), orderBy);
		} finally {
			for (Iterator it4 = archidFdr.iterator(); it4.hasNext();) {
				distReg = (ScrDistreg) it4.next();
				FolderSession.closeFolder(useCaseConf.getSessionID(),
						new Integer(distReg.getIdArch()), distReg.getIdFdr(),
						useCaseConf.getEntidadId());
			}
		}
	}

	public void saveDistribution(UseCaseConf useCaseConf, List dis, int state,
			int firstRow, int typeDist, String distWhere, String regWhere, String orderBy, String remarks)
			throws ValidationException, DistributionException,
			SessionException, BookException {
		List archidFdr = new ArrayList();
		ScrDistreg distReg = null;
		int maxResults = Integer
				.parseInt(Configurator
						.getInstance()
						.getProperty(
								ConfigurationKeys.KEY_DESKTOP_DEFAULT_PAGE_DISTRIBUTION_MINUTA_SIZE));
		try {
			// DistributionSession.saveDistribution(useCaseConf.getSessionID(),
			// dis, distWhere, regWhere);
			archidFdr = DistributionSession.saveDistribution(useCaseConf
					.getSessionID(), dis, state, firstRow, maxResults,
					typeDist, distWhere, regWhere, useCaseConf.getLocale(),
					useCaseConf.getEntidadId(), useCaseConf.getUseLdap()
							.booleanValue(), orderBy, remarks);
		} finally {
			for (Iterator it4 = archidFdr.iterator(); it4.hasNext();) {
				distReg = (ScrDistreg) it4.next();
				FolderSession.closeFolder(useCaseConf.getSessionID(),
						new Integer(distReg.getIdArch()), distReg.getIdFdr(),
						useCaseConf.getEntidadId());
			}
		}
	}

	public void changeDistribution(UseCaseConf useCaseConf, List dis,
			int state, int firstRow, int typeDist, String code)
			throws ValidationException, DistributionException,
			SessionException, BookException {
		// Integer launchDistOutRegister = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_DISTRIBUTION_LAUNCH_DIST_OUT_REGISTER));
		// Integer canDestWithoutList = new
		// Integer(Configurator.getInstance().getProperty(Keys.KEY_DISTRIBUTION_CAN_DEST_WITHOUT_LIST));
		Integer launchDistOutRegister = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getDistSRegister());
		Integer canDestWithoutList = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getCanChangeDestWithoutList());

		DistributionSession.changeDistribution(useCaseConf.getSessionID(), dis,
				code, typeDist, launchDistOutRegister, canDestWithoutList,
				useCaseConf.getLocale(), useCaseConf.getEntidadId(), useCaseConf.getUseLdap()
				.booleanValue());
	}

	public String createDistribution(UseCaseConf useCaseConf, Integer bookId,
			List listIdsRegister, Integer userType, Integer userId,
			String messageForUser) throws BookException, DistributionException,
			ValidationException, SessionException {
		Integer folderID = null;
		try {
			return DistributionSession.createDistribution(useCaseConf
					.getSessionID(), bookId, listIdsRegister, userType, userId,
					messageForUser, useCaseConf.getLocale(), useCaseConf
							.getEntidadId());
		} finally {
			for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
				folderID = (Integer) it.next();
				FolderSession.closeFolder(useCaseConf.getSessionID(), bookId,
						folderID.intValue(), useCaseConf.getEntidadId());
			}
		}

	}

	public String saveRemarks(UseCaseConf useCaseConf, int id, String remarks)
			throws ValidationException, DistributionException,
			SessionException, BookException {
		List archidFdr = new ArrayList();
		ScrDistreg distReg = null;

		try {
			archidFdr = DistributionSession.saveRemarks(useCaseConf
					.getSessionID(), id, remarks, useCaseConf.getEntidadId());

			Document xmlDocument = XMLDistributionList.getXMLSaveRemarks(id,
					remarks, useCaseConf.getLocale());
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
		} finally {
			for (Iterator it4 = archidFdr.iterator(); it4.hasNext();) {
				distReg = (ScrDistreg) it4.next();
				FolderSession.closeFolder(useCaseConf.getSessionID(),
						new Integer(distReg.getIdArch()), distReg.getIdFdr(),
						useCaseConf.getEntidadId());
			}
		}
	}

	public Document getDistributionSearch(UseCaseConf useCaseConf, int typeDist)
			throws Exception {

		String dataBaseType = DistributionSession.getDataBaseType(useCaseConf
				.getSessionID());
		SessionInformation sessionInformation = BookSession
				.getSessionInformation(useCaseConf.getSessionID(), useCaseConf
						.getLocale(), useCaseConf.getEntidadId());

		return XMLDistributionList.getXMLDistributionSearchList(typeDist,
				useCaseConf.getLocale(), dataBaseType, sessionInformation
						.getCaseSensitive());
	}

	public String getValidateDistributionSearch(UseCaseConf useCaseConf,
			Integer typeDist, String distWhere, String regWhere)
			throws Exception {
		Object whereDist = null;
		Object whereReg = null;

		String dataBaseType = DistributionSession.getDataBaseType(useCaseConf
				.getSessionID());
		// Procesamiento distWhere
		if (!distWhere.equals("")) {
			whereDist = getSearchCriteria(useCaseConf, distWhere, 1, typeDist,
					useCaseConf.getLocale(), dataBaseType);
		} else {
			whereDist = distWhere;
		}
		// Procesamiento regWhere
		if (!regWhere.equals("")) {
			whereReg = getSearchCriteria(useCaseConf, regWhere, 2, typeDist,
					useCaseConf.getLocale(), dataBaseType);
		} else {
			whereReg = regWhere;
		}
		Document xmlDocument = XMLDistributionList
				.getXMLDistributionClausuleWhere(whereDist, whereReg,
						useCaseConf.getLocale());
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
	}

	private Object getSearchCriteria(UseCaseConf useCaseConf,
			String distRegWhere, int searchType, Integer typeDist,
			Locale locale, String dataBaseType) throws Exception {

		DistributionSearchFields distributionSearchFields = null;
		DistributionFields distributionFields = null;
		distributionSearchFields = new DistributionSearchFields(new Integer(
				searchType), typeDist, locale, dataBaseType);
		List fieldSearch = distributionSearchFields.getResult();
		StringTokenizer tokens = new StringTokenizer(distRegWhere, ALMOHADILLA);
		String token = null;
		String token1 = null;
		String result = "";
		Integer validation = null;
		Integer type = null;
		Map operators = null;
		String operator = null;
		String value = null;
		String fieldName = null;
		List validationError = new ArrayList();
		int index = 0;
		String operatorNoValidate = RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_BEGIN_BY_VALUE);
		String operatorDepend = RBUtil.getInstance(locale).getProperty(
				Keys.I18N_QUERY_DEPEND_OF_VALUE);

		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			StringTokenizer tokens1 = new StringTokenizer(token, PUNTO_COMA);
			while (tokens1.hasMoreTokens()) {
				token1 = tokens1.nextToken();
				for (int i = 0; i < fieldSearch.size(); i++) {
					distributionFields = (DistributionFields) fieldSearch
							.get(i);
					if (distributionFields.getFieldName().equals(token1)) {
						validation = distributionFields.getFieldValidation();
						type = distributionFields.getFieldType();
						operators = distributionFields.getOperators();
						fieldName = token1;
						break;
					}

				}
				operator = tokens1.nextToken();
				value = tokens1.nextToken();
				if ((validation.intValue() == 1 && !operator
						.equals(operatorNoValidate))
						|| validation.intValue() == 9
						|| validation.intValue() == 10
						|| validation.intValue() == 6) {
					Integer id = DistributionSession.validateQueryDistribution(
							useCaseConf.getSessionID(), value, validation,
							useCaseConf.getEntidadId());
					if (id != null) {
						if (validation.intValue() == 9
								|| validation.intValue() == 10) {
							value = new Long(id.intValue() | 0x40000000)
									.toString();
						}
						if (validation.intValue() == 1) {
							if (!operator.equals(operatorDepend)) {
								value = new Integer(id.intValue()).toString();
							}
						}
						if (validation.intValue() == 6) {
							value = new Integer(id.intValue()).toString();
						}
					} else {
						validationError.add(fieldName);
						return validationError;
					}
				}
				if (index == 0) {
					result = getWhere(fieldName, type, operator, value,
							operators, locale, dataBaseType);
				} else {
					result = result
							+ " AND "
							+ getWhere(fieldName, type, operator, value,
									operators, locale, dataBaseType);
				}

			}
			index++;

		}

		if ((typeDist.intValue() == 0) && (searchType == 0)) {
			// No incluir pendiente de distribución en búsqueda de bandejas de
			// entrada
			result = result + " AND (STATE != 6)";
		}

		return result;
	}

	private String getWhere(String fieldName, Integer type, String operator,
			String value, Map operators, Locale locale, String dataBaseType)
			throws Exception {
		String conversionOperator = null;
		StringBuffer buffer = new StringBuffer();

		for (Iterator it = operators.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String aux = key.substring(1, key.length());
			if (aux.equals(operator)) {
				conversionOperator = (String) operators.get(key);
				break;
			}

		}

		if (type.intValue() == 5) {
			buffer.append(fieldName);
			buffer.append(conversionOperator);
			buffer.append(value);
		}
		if (type.intValue() == 0) {
			if (conversionOperator == "=" || conversionOperator == "<>"
					|| conversionOperator == ">" || conversionOperator == "<"
					|| conversionOperator == ">=" || conversionOperator == "<=") {
				if(conversionOperator == "<>"){
					// cuando se realiza una busqueda por distinto a un valor, se debe tener en
					// cuenta tambien los datos nulos para dicho campo
					buffer.append(PAR_IZQ);
					buffer.append(fieldName);
					buffer.append(ESPACIO);
					buffer.append(IS_NULL);
					buffer.append(OR);
					buffer.append(fieldName);
					buffer.append(conversionOperator);
					buffer.append(COMILLA);
					buffer.append(value);
					buffer.append(COMILLA);
					buffer.append(PAR_DER);
				}else{
					buffer.append(fieldName);
					buffer.append(conversionOperator);
					buffer.append(COMILLA);
					buffer.append(value);
					buffer.append(COMILLA);
				}
			} else if (conversionOperator.equals("LIKE1")) {
				buffer.append(fieldName);
				buffer.append(CONTENT);
				buffer.append(COMILLA);
				buffer.append(value);
				buffer.append(PER_CENT);
				buffer.append(COMILLA);
			} else if (conversionOperator.equals("LIKE2")) {
				buffer.append(fieldName);
				buffer.append(CONTENT);
				buffer.append(COMILLA);
				buffer.append(PER_CENT);
				buffer.append(value);
				buffer.append(COMILLA);
			} else {
				buffer.append(fieldName);
				buffer.append(CONTENT);
				buffer.append(COMILLA);
				buffer.append(PER_CENT);
				buffer.append(value);
				buffer.append(PER_CENT);
				buffer.append(COMILLA);
			}

		}
		if (type.intValue() == 2) {
			List monthYear = null;
			if (conversionOperator.equals("MOUNTH")
					|| conversionOperator.equals("YEAR")) {
				monthYear = getMonthYear(value);
			}
			if (conversionOperator == ">=AND<=") {
				buffer.append(getWhereByDataBaseRest(1, fieldName, value,
						dataBaseType));
			} else if (conversionOperator.equals("<OR>")) {
				buffer.append(getWhereByDataBaseRest(2, fieldName, value,
						dataBaseType));
			} else if (conversionOperator.equals(">23")) {
				buffer.append(getWhereByDataBaseRest(3, fieldName, value,
						dataBaseType));
			} else if (conversionOperator.equals("<00")) {
				buffer.append(getWhereByDataBaseRest(4, fieldName, value,
						dataBaseType));
			} else if (conversionOperator.equals("MOUNTH")) {
				buffer.append(getWhereByDataBaseMonthYear(1, fieldName,
						dataBaseType, monthYear));
			} else if (conversionOperator.equals("YEAR")) {
				buffer.append(getWhereByDataBaseMonthYear(2, fieldName,
						dataBaseType, monthYear));
			}
		}
		if (type.intValue() == 1) {
			if (conversionOperator == "=") {
				buffer.append(fieldName);
				buffer.append(conversionOperator);
				buffer.append(value);
			} else if (conversionOperator == "<>"){
				// cuando se realiza una busqueda por distinto a un valor, se debe tener en
				// cuenta tambien los datos nulos para dicho campo
				buffer.append(PAR_IZQ);
				buffer.append(fieldName);
				buffer.append(ESPACIO);
				buffer.append(IS_NULL);
				buffer.append(OR);
				buffer.append(fieldName);
				buffer.append(conversionOperator);
				buffer.append(value);
				buffer.append(PAR_DER);
			} else if (conversionOperator == "SCR_ORGS") {
				buffer.append(fieldName);
				buffer.append(SCRORGS);
				buffer.append(COMILLA);
				buffer.append(PER_CENT);
				buffer.append(value);
				buffer.append(PER_CENT);
				buffer.append(COMILLA);
				buffer.append(TYPE);
			} else if (conversionOperator == "SCR_ORGS_CONNECT") {
				buffer.append(fieldName);
				buffer.append(SCRORGSCONNECT);
				buffer.append(COMILLA);
				buffer.append(value);
				buffer.append(COMILLA);
				buffer.append(BYPRIOR);
			} else {
				buffer.append(fieldName);
				buffer.append(SCRCA);
				buffer.append(COMILLA);
				buffer.append(PER_CENT);
				buffer.append(value);
				buffer.append(PER_CENT);
				buffer.append(COMILLA);
				buffer.append(PAR_DER);
			}

		}

		return buffer.toString();
	}

	private List getMonthYear(String value) throws Exception {
		List result = new ArrayList();
		String month = value.substring(value.indexOf("-") + 1, value
				.lastIndexOf("-"));
		String year = value.substring(value.lastIndexOf("-") + 1, value
				.length());
		result.add(month);
		result.add(year);
		return result;
	}

	private String getWhereByDataBaseMonthYear(int type, String fieldName,
			String dataBaseType, List monthYear) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (dataBaseType.equals(ORACLE) || dataBaseType.equals(POSTGRESQL)) {
			if (type == 1) {
				buffer.append(TO_CHAR);
				buffer.append(fieldName);
				buffer.append(COMA);
				buffer.append(COMILLA);
				buffer.append(MONTH_FORMAT);
				buffer.append(COMILLA);
				buffer.append(PAR_DER);
				buffer.append("=");
				buffer.append(COMILLA);
				buffer.append((String) monthYear.get(0));
				buffer.append(COMILLA);
				buffer.append(" AND ");
				buffer.append(TO_CHAR);
				buffer.append(fieldName);
				buffer.append(COMA);
				buffer.append(COMILLA);
				buffer.append(YEAR_FORMAT);
				buffer.append(COMILLA);
				buffer.append(PAR_DER);
				buffer.append("=");
				buffer.append(COMILLA);
				buffer.append((String) monthYear.get(1));
				buffer.append(COMILLA);
			} else {
				buffer.append(TO_CHAR);
				buffer.append(fieldName);
				buffer.append(COMA);
				buffer.append(COMILLA);
				buffer.append(YEAR_FORMAT);
				buffer.append(COMILLA);
				buffer.append(PAR_DER);
				buffer.append("=");
				buffer.append(COMILLA);
				buffer.append((String) monthYear.get(1));
				buffer.append(COMILLA);
			}

		}
		if (dataBaseType.equals(SQLSERVER) || dataBaseType.equals(DB2)) {
			if (type == 1) {
				buffer.append(MONTH);
				buffer.append(fieldName);
				buffer.append(PAR_DER);
				buffer.append("=");
				buffer
						.append(new Integer((String) monthYear.get(0))
								.toString());
				buffer.append(" AND ");
				buffer.append(YEAR);
				buffer.append(fieldName);
				buffer.append(PAR_DER);
				buffer.append("=");
				buffer
						.append(new Integer((String) monthYear.get(1))
								.toString());
			} else {
				buffer.append(YEAR);
				buffer.append(fieldName);
				buffer.append(PAR_DER);
				buffer.append("=");
				buffer
						.append(new Integer((String) monthYear.get(1))
								.toString());

			}
		}
		return buffer.toString();
	}

	private String getWhereByDataBaseRest(int type, String fieldName,
			String value, String dataBaseType) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (dataBaseType.equals(ORACLE)) {
			if (type == 1) {
				buffer.append(fieldName);
				buffer.append(">=").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT).append(COMILLA)
						.append(PAR_DER);
				buffer.append(" AND ");
				buffer.append(fieldName);
				buffer.append("<=").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT).append(COMILLA).append(
						PAR_DER);
			}
			if (type == 2) {
				buffer.append(PAR_IZQ);
				buffer.append(fieldName);
				buffer.append("<").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT).append(COMILLA)
						.append(PAR_DER);
				buffer.append(" OR ");
				buffer.append(fieldName);
				buffer.append(">").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT).append(COMILLA).append(
						PAR_DER).append(PAR_DER);
			}
			if (type == 3) {
				buffer.append(fieldName);
				buffer.append(">").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT).append(COMILLA).append(
						PAR_DER);
			}
			if (type == 4) {
				buffer.append(fieldName);
				buffer.append("<").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT).append(COMILLA)
						.append(PAR_DER);
			}
		}
		if (dataBaseType.equals(SQLSERVER)) {
			if (type == 1) {
				buffer.append(fieldName);
				buffer.append(">=").append(TO_CONVERT_DATE).append(COMA)
						.append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(TO_CONVERT_DATE_FORMAT).append(PAR_DER);
				buffer.append(" AND ");
				buffer.append(fieldName);
				buffer.append("<=").append(TO_CONVERT_DATE).append(COMA)
						.append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						TO_CONVERT_DATE_FORMAT).append(PAR_DER);
			}
			if (type == 2) {
				buffer.append(PAR_IZQ);
				buffer.append(fieldName);
				buffer.append("<").append(TO_CONVERT_DATE).append(COMA).append(
						COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(TO_CONVERT_DATE_FORMAT).append(PAR_DER);
				buffer.append(" OR ");
				buffer.append(fieldName);
				buffer.append(">").append(TO_CONVERT_DATE).append(COMA).append(
						COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						TO_CONVERT_DATE_FORMAT).append(PAR_DER).append(PAR_DER);
			}
			if (type == 3) {
				buffer.append(fieldName);
				buffer.append(">").append(TO_CONVERT_DATE).append(COMA).append(
						COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						TO_CONVERT_DATE_FORMAT).append(PAR_DER);
			}
			if (type == 4) {
				buffer.append(fieldName);
				buffer.append("<").append(TO_CONVERT_DATE).append(COMA).append(
						COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(TO_CONVERT_DATE_FORMAT).append(PAR_DER);
			}
		}
		if (dataBaseType.equals(POSTGRESQL)) {
			if (type == 1) {
				buffer.append(fieldName);
				buffer.append(">=").append(TO_TIMESTAMP).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT).append(COMILLA)
						.append(PAR_DER);
				buffer.append(" AND ");
				buffer.append(fieldName);
				buffer.append("<=").append(TO_TIMESTAMP).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT).append(COMILLA).append(
						PAR_DER);
			}
			if (type == 2) {
				buffer.append(PAR_IZQ);
				buffer.append(fieldName);
				buffer.append("<").append(TO_TIMESTAMP).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT).append(COMILLA)
						.append(PAR_DER);
				buffer.append(" OR ");
				buffer.append(fieldName);
				buffer.append(">").append(TO_TIMESTAMP).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT).append(COMILLA).append(
						PAR_DER).append(PAR_DER);
			}
			if (type == 3) {
				buffer.append(fieldName);
				buffer.append(">").append(TO_TIMESTAMP).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT).append(COMILLA).append(
						PAR_DER);
			}
			if (type == 4) {
				buffer.append(fieldName);
				buffer.append("<").append(TO_TIMESTAMP).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT).append(COMILLA)
						.append(PAR_DER);
			}
		}
		if (dataBaseType.equals(DB2)) {
			if (type == 1) {
				buffer.append(fieldName);
				buffer.append(">=").append(TO_DATE).append(COMILLA);
				buffer.append(getDateFormated(value));
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT_DB2)
						.append(COMILLA).append(PAR_DER);
				buffer.append(" AND ");
				buffer.append(fieldName);
				buffer.append("<=").append(TO_DATE).append(COMILLA);
				buffer.append(getDateFormated(value));
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT_DB2).append(COMILLA)
						.append(PAR_DER);
			}
			if (type == 2) {
				buffer.append(PAR_IZQ);
				buffer.append(fieldName);
				buffer.append("<").append(TO_DATE).append(COMILLA);
				buffer.append(getDateFormated(value));
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT_DB2)
						.append(COMILLA).append(PAR_DER).append(" OR ");
				buffer.append(fieldName);
				buffer.append(">").append(TO_DATE).append(COMILLA);
				buffer.append(getDateFormated(value));
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT_DB2).append(COMILLA)
						.append(PAR_DER).append(PAR_DER);
			}
			if (type == 3) {
				buffer.append(fieldName);
				buffer.append(">").append(TO_DATE).append(COMILLA);
				buffer.append(getDateFormated(value));
				buffer.append(TIME_FINAL).append(COMILLA).append(COMA).append(
						COMILLA).append(DATE_FORMAT_DB2).append(COMILLA)
						.append(PAR_DER);
			}
			if (type == 4) {
				buffer.append(fieldName);
				buffer.append("<").append(TO_DATE).append(COMILLA);
				buffer.append(value);
				buffer.append(TIME_INITIAL).append(COMILLA).append(COMA)
						.append(COMILLA).append(DATE_FORMAT_DB2)
						.append(COMILLA).append(PAR_DER);
			}
		}
		return buffer.toString();
	}

	private List getScrDistRegResults(UseCaseConf useCaseConf,
			DistributionResults distributionResults, Map axsfs)
			throws ValidationException, DistributionException {

		Map distType = distributionResults.getDistType();
		TreeMap treemap = new TreeMap(new IntegerComparator());
		treemap.putAll(distributionResults.getResults());

		List listScrDistRegResults = new ArrayList();
		for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
			Integer distId = (Integer) it.next();
			Map aux = (Map) treemap.get(distId);
			if(aux != null && aux.size()>0 ){
				String id = (String) aux.keySet().iterator().next();
				ScrDistreg distReg = (ScrDistreg) aux.get(id);
				Integer dType = (Integer) distType.get(distReg.getId());

				String sourceDesc = DistributionSession.getOrigDestDescription(
						useCaseConf.getSessionID(), distReg, true, useCaseConf
								.getEntidadId(), useCaseConf.getUseLdap()
								.booleanValue());
				String targetDesc = DistributionSession.getOrigDestDescription(
						useCaseConf.getSessionID(), distReg, false, useCaseConf
								.getEntidadId(), useCaseConf.getUseLdap()
								.booleanValue());
				AxSf axsf = (AxSf) axsfs.get(distReg.getIdArch() + "_"
						+ distReg.getIdFdr());
				Object idocarch = distributionResults.getIdocarchhdr().get(
						new Integer(distReg.getIdArch()));

				// Si la distribucion esta en estado redistribuido buscaremos la
				// información del destino actual
				String targetActualDist = null;
				if (distReg.getState() == ISDistribution.STATE_REDISTRIBUIDO) {
					//Obtenemos el destino actual de la distribucion
					targetActualDist = DistributionSession
							.getDestinoActualDistribucion(
									useCaseConf.getSessionID(), distReg.getId(),
									useCaseConf.getEntidadId());
				}

				//Se genera el objeto con la información a mostrar
				ScrDistRegResults scrDistRegResult = new ScrDistRegResults(distReg,
						axsf, idocarch, sourceDesc, targetDesc, dType, targetActualDist);

				listScrDistRegResults.add(scrDistRegResult);
			}
		}

		return listScrDistRegResults;
	}

	protected String getDateFormated(String formatedField) {
		String where = formatedField;
		String day = where.substring(0, 2);
		String month = where.substring(3, 5);
		String year = where.substring(6, 10);
		StringBuffer buffer = new StringBuffer();
		buffer.append(year);
		buffer.append(GUION);
		buffer.append(month);
		buffer.append(GUION);
		buffer.append(day);
		return buffer.toString();
	}

	/**
	 *  Metodo que redirecciona una distribucion
	 * @param useCaseConf
	 * @param dis - Ids de las distribuciones a modificar
	 * @param state - Estado de la distribucion
	 * @param typeDist - Tipo de distribucion (Entrada/Salida)
	 * @param messageForUser - Mensaje de la distribucion
	 * @param userType - Tipo de Destino de la distribucion
	 * @param userId - Id del destino de la distribucion
	 * @throws Exception
	 */
	public void redistributionDistribution(UseCaseConf useCaseConf, List dis,
			int typeDist, String messageForUser, Integer userType,
			Integer userId) throws ValidationException, DistributionException,
			SessionException, BookException {
		//obtenemos los datos de configuracion
		Integer canDestWithoutList = new Integer(BookSession.invesicresConf(
				useCaseConf.getEntidadId()).getCanChangeDestWithoutList());

		//Generamos la distribucion rechazada
		DistributionSession.redistributionDistribution(
				useCaseConf.getSessionID(), useCaseConf.getLocale(),
				useCaseConf.getEntidadId(), dis, userId, typeDist,
				canDestWithoutList, messageForUser,
				userType, useCaseConf.getUseLdap().booleanValue());

	}

}