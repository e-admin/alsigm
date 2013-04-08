package com.ieci.tecdoc.isicres.session.reports;

import gnu.trove.THashMap;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.ReportException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserobjperm;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrBookadmin;
import com.ieci.tecdoc.common.invesicres.ScrLockrelation;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.ReportResult;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class ReportsSessionUtil extends UtilsSession implements ServerKeys,
		HibernateKeys {

	private static final Logger log = Logger
			.getLogger(ReportsSessionUtil.class);

	protected static final String IGUAL = "=";

	protected static final String COMILLA = "'";

	public static final String FIELD_LIST_IN = "fld1, fld2, fld3, fld4, fld5, fld6, fld7, fld8, fld9, fld10, fld11, fld12, fld13, fld14, fld15, fld16, fld17";

	public static final String FIELD_LIST_OUT = "fld1, fld2, fld3, fld4, fld5, fld6, fld7, fld8, fld9, fld10, fld11, fld12, fld13";

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	protected static ReportResult getOptionA(String sessionID, Integer bookID,
			List idList, int opcion, Integer maxReportRegister, String entidad,
			boolean isMultiple) throws BookException, SessionException,
			ReportException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		dropTable(sessionID, entidad, bookID);

		ReportResult result = new ReportResult();
		Transaction tran = null;
		int bookType = 0;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			THashMap bookInformation = (THashMap) cacheBag.get(bookID);
			AxSf axsf = (AxSf) bookInformation.get(AXSF);
			AxSfQuery axsfQuery = (AxSfQuery) bookInformation.get(AXSF_QUERY);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			if (scrofic != null) {
				result.setUserOfic(scrofic.getName());
				result.setIdOfic(scrofic.getId().intValue());
			} else {
				result.setUserOfic("");
			}

			boolean isInBook = Repository.getInstance(entidad).isInBook(bookID)
					.booleanValue();
			if (isInBook) {
				bookType = 1;
				result.setTypeBook(bookType);
			} else {
				bookType = 2;
				result.setTypeBook(bookType);
			}

			String tableName = getTableName(bookID, user, result, entidad);
			String aditionalFields = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getAditionFields(bookType);

			String createSentence = null;
			int queryType = -1;
			if (isMultiple) {
				queryType = 1;
				Iuserusertype userusertype = (Iuserusertype) cacheBag
						.get(HIBERNATE_Iuserusertype);
				createSentence = getCreateSentenceAMultiple(session, user,
						axsf, axsfQuery, bookID, userusertype, scrofic, idList,
						isInBook, tableName, aditionalFields, bookType,
						maxReportRegister, entidad);
			} else {
				queryType = 0;
				String filter = (String) bookInformation.get(QUERY_FILTER);
				createSentence = getCreateSentenceA(user, axsf, axsfQuery,
						bookID, bookType, tableName, aditionalFields, filter,
						idList, maxReportRegister, entidad);
			}

			DBEntityDAOFactory.getCurrentDBEntityDAO().createTableOrView(
					createSentence, entidad);

			if ((opcion == 4 || opcion == 5 || opcion == 6 || opcion == 7)
					&& scrofic != null) {
				generateRelationsNum(opcion, session, tableName, bookType,
						scrofic, entidad);
			}
			doFinal(result, tableName, queryType, maxReportRegister.intValue(),
					entidad);
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
			log.error("Impossible to getOptionAQuery for the session ["
					+ sessionID + "]", e);
			throw new ReportException(
					ReportException.ERROR_CANNOT_GENERATE_REPORT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static ReportResult getOptionC(String sessionID, Integer bookID,
			Date beginDate, Date endDate, String unit, boolean isTarget,
			List bookIds, Integer maxReportRegister, String entidad,
			boolean isMultiple) throws BookException, SessionException,
			ReportException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		dropTable(sessionID, entidad, bookID);

		ReportResult result = new ReportResult();
		Transaction tran = null;
		int bookType = 0;
		List privOrgs = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getPrivOrgs(scrofic.getId().intValue(), entidad);
			}

			if (scrofic != null) {
				result.setUserOfic(scrofic.getName());
				result.setIdOfic(scrofic.getId().intValue());
			} else {
				result.setUserOfic("");
			}

			boolean isInBook = Repository.getInstance(entidad).isInBook(bookID)
					.booleanValue();
			if (isInBook) {
				bookType = 1;
				result.setTypeBook(bookType);
			} else {
				bookType = 2;
				result.setTypeBook(bookType);
			}

			String where = getWhereOptionC(session, unit, scrofic, beginDate,
					endDate, privOrgs, isTarget);
			String tableName = getTableName(bookID, user, result, entidad);
			String createSentence = null;
			int queryType = -1;
			if (isMultiple) {
				queryType = 1;
				createSentence = getCreateSentenceCMultiple(session, user,
						userusertype, isInBook, bookID, bookIds, bookType,
						tableName, where, maxReportRegister, entidad);
			} else {
				queryType = 0;
				createSentence = getCreateSentenceC(bookID, bookType,
						tableName, where, maxReportRegister, entidad);
			}

			DBEntityDAOFactory.getCurrentDBEntityDAO().createTableOrView(
					createSentence.toString(), entidad);

			generateRelationsNumOptionC(session, scrofic, bookType, tableName,
					queryType, isTarget, result, maxReportRegister, entidad);

			HibernateUtil.commitTransaction(tran);
			return result;
		} catch (ReportException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to getOptionC for the session [" + sessionID
					+ "]", e);
			throw new ReportException(
					ReportException.ERROR_CANNOT_GENERATE_REPORT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static void generateRelationsNum(int opcion, Session session,
			String tableName, int bookType, ScrOfic scrofic, String entidad)
			throws SQLException, HibernateException, Exception {
		int typeRel = (opcion == 4 || opcion == 6) ? 1 : 2;

		try {
			Object[][] tupla = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getRelationsTupla(tableName, opcion, entidad);

			lockRelations(session, bookType, typeRel, scrofic, entidad);

			for (int i = 0; i < tupla.length; i++) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime((Date) tupla[i][0]);
				if ((tupla[i][1] != null)
						&& (getNumRelationsList(session, bookType, typeRel,
								scrofic, tupla[i]))) {

					int newNumRel = DBEntityDAOFactory.getCurrentDBEntityDAO()
							.getNewNumRelations(bookType, typeRel,
									scrofic.getId().intValue(),
									((Integer) tupla[i][1]).intValue(),
									gc.get(Calendar.YEAR), entidad);
					if (newNumRel == 0) {
						newNumRel = 1;
					} else {
						newNumRel++;
					}
					ScrOrg scrOrg = (ScrOrg) session.load(ScrOrg.class,
							new Integer(((Integer) tupla[i][1]).intValue()));
					DBEntityDAOFactory.getCurrentDBEntityDAO()
							.insertScrRelations(bookType, typeRel,
									gc.get(Calendar.YEAR),
									(gc.get(Calendar.MONTH)) + 1,
									gc.get(Calendar.DATE),
									scrofic.getId().intValue(),
									(Date) tupla[i][0],
									scrOrg.getId().intValue(), newNumRel,
									entidad);
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (HibernateException e) {
			throw e;
		}
	}

	protected static void dropTable(String sessionID, String entidad,
			Integer bookID) throws ReportException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			// Recuperamos la sesión

			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			String tableName = getTableName(bookID, user, new ReportResult(),
					entidad);

			DBEntityDAOFactory.getCurrentDBEntityDAO().dropTableOrView(
					tableName, entidad);

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to dropTable for the session [" + sessionID
					+ "]", e);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static String getTableName(Integer bookID,
			AuthenticationUser user, ReportResult result, String entidad) {
		String tableName = null;
		if (Repository.getInstance(entidad).isOutBook(bookID).booleanValue()) {
			tableName = " SCR_SREG" + user.getId() + " ";
			result.setOldTableName("SCR_SRPTAUX");
		} else {
			tableName = " SCR_EREG" + user.getId() + " ";
			result.setOldTableName("SCR_ERPTAUX");
		}
		return tableName;
	}

	protected static StringBuffer getFieldList(List list) {
		StringBuffer fieldList = new StringBuffer();

		Object[] fields = list.toArray();
		for (int i = 0; i < fields.length; i++) {
			fieldList.append(fields[i]);
			if (i != fields.length - 1) {
				fieldList.append(",");
			}
		}
		return fieldList;
	}

	protected static String getFieldList(boolean isInBook, Integer bookID,
			List bookIds, String entidad) throws SQLException {
		String fieldList = null;
		if (isInBook) {
			fieldList = FIELD_LIST_IN;
		} else {
			fieldList = FIELD_LIST_OUT;
		}

		boolean updateFieldList = true;
		AxSf axsf = BBDDUtils.getTableSchemaFromDatabaseWithSQLName(bookID
				.toString(), entidad);

		// String fieldName = null;
		for (Iterator it = bookIds.iterator(); it.hasNext() && updateFieldList;) {
			Integer auxBookId = (Integer) it.next();
			AxSf auxAxsf = BBDDUtils.getTableSchemaFromDatabaseWithSQLName(
					auxBookId.toString(), entidad);
			updateFieldList = axsf.getAttributesNames().size() == auxAxsf
					.getAttributesNames().size();
			if (updateFieldList) {
				for (Iterator it2 = axsf.getAttributesNames().iterator(); it2
						.hasNext()
						&& updateFieldList;) {
					String fieldName = (String) it2.next();

					updateFieldList = auxAxsf.getAttributesNames().contains(
							fieldName)
							&& axsf.getAttributeSQLType(fieldName).equals(
									auxAxsf.getAttributeSQLType(fieldName))
							&& axsf.getAttributeSQLScale(fieldName).equals(
									auxAxsf.getAttributeSQLScale(fieldName))
							&& axsf.getAttributesSQLNames().get(fieldName)
									.equals(
											auxAxsf.getAttributesSQLNames()
													.get(fieldName));
				}
			}
		}

		if (updateFieldList) {
			fieldList = getFieldList(axsf.getAttributesNames()).toString();
		}

		return fieldList;
	}

	protected static void doFinal(ReportResult result, String tableName,
			int type, int maxReportRegister, String entidad)
			throws SQLException, Exception {

		String dataBase = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDataBaseType();
		if (dataBase.equals("DB2")) {
			int size = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getTableOrViewSize(tableName, entidad);
			if (size >= maxReportRegister) {
				result.setSize(maxReportRegister);
				result.setTableName(tableName + "FETCH FIRST "
						+ maxReportRegister + " ROWS ONLY");
			} else {
				result.setSize(size);
				result.setTableName(tableName);
			}
		} else {
			result.setSize(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getTableOrViewSize(tableName, entidad));
			result.setTableName(tableName);
		}
		if (log.isDebugEnabled()) {
			log.debug("Resultado de getOptionAQuery: " + result);
		}
	}

	protected static void getAPerms(Session session, Integer bookId,
			Integer userId, int userType, Integer deptId, ISicresAPerms aPerms,
			Idocarchhdr idocarchhdr) throws HibernateException {
		aPerms.setIsBookAdmin(false);
		aPerms.setCanQuery(false);
		aPerms.setCanCreate(false);
		aPerms.setCanModify(false);

		if (userType == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
			// Si es superadministrador, ya no hay que hacer nada
			aPerms.setIsBookAdmin(true);
			aPerms.setCanQuery(true);
			aPerms.setCanCreate(true);
			aPerms.setCanModify(true);
		} else if (userType == IDocKeys.IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN) {
			// Si es administrador de libro
			ScrBookadmin scrBookadmin = ISicresQueries.getScrBookadmin(session,
					bookId, userId);

			if (scrBookadmin != null) {
				// el usuario es administrador de libro
				aPerms.setIsBookAdmin(true);
				aPerms.setCanQuery(true);
				aPerms.setCanCreate(true);
				aPerms.setCanModify(true);
			} else if (idocarchhdr != null
					&& idocarchhdr.getCrtrid() == userId.intValue()) {
				// el usuario es creador de libro
				aPerms.setIsBookAdmin(true);
				aPerms.setCanQuery(true);
				aPerms.setCanCreate(true);
				aPerms.setCanModify(true);
			}
		}

		if (!aPerms.isBookAdmin()) {
			// Si el usuario no es administrador de libro o superusuario
			List listPermsUser = ISicresQueries.getIuserobjpermForUser(session,
					bookId, userId);
			List listPermsDept = ISicresQueries.getIuserobjpermForDept(session,
					bookId, deptId);
			List listPerms = null;

			if ((listPermsUser != null) || !listPermsUser.isEmpty()) {
				listPerms = listPermsUser;
			}

			if ((listPermsDept != null) || !listPermsDept.isEmpty()) {
				if (listPerms != null) {
					listPerms.addAll(listPermsDept);
				} else {
					listPerms = listPermsDept;
				}
			}

			if ((listPerms != null) || !listPerms.isEmpty()) {

				for (int i = 0; i < listPerms.size(); i++) {
					Iuserobjperm iuser = (Iuserobjperm) listPerms.get(i);
					int APerm = iuser.getAperm();

					switch (APerm) {
					case (IDocKeys.IUSEROBJPERM_QUERY_PERM): {
						aPerms.setCanQuery(true);
						break;
					}
					case (IDocKeys.IUSEROBJPERM_CREATE_PERM): {
						aPerms.setCanCreate(true);
						break;
					}
					case (IDocKeys.IUSEROBJPERM_MODIFY_PERM): {
						aPerms.setCanModify(true);
						break;
					}
					}
				}
			}
		}
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private static void lockRelations(Session session, int bookType,
			int typeRel, ScrOfic scrofic, String entidad)
			throws HibernateException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_ScrLockrelation);
		query
				.append(" scr WHERE scr.typebook=? AND scr.typerel=? AND scr.scrOfic.id=?");

		List scrRelationList = session.find(query.toString(), new Object[] {
				new Integer(bookType), new Integer(typeRel),
				new Integer(scrofic.getId().intValue()) }, new Type[] {
				Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER });

		if (scrRelationList != null && !scrRelationList.isEmpty()) {
			DBEntityDAOFactory.getCurrentDBEntityDAO().lockScrRelations(
					bookType, typeRel, scrofic.getId().intValue(), entidad);
		} else {
			ScrLockrelation scrLockrelation = new ScrLockrelation();
			scrLockrelation.setTypebook(bookType);
			scrLockrelation.setTyperel(typeRel);
			scrLockrelation.setScrOfic(scrofic);
			session.save(scrLockrelation);
			DBEntityDAOFactory.getCurrentDBEntityDAO().lockScrRelations(
					bookType, typeRel, scrofic.getId().intValue(), entidad);
		}
	}

	private static boolean getNumRelationsList(Session session, int bookType,
			int typeRel, ScrOfic scrofic, Object[] fila)
			throws HibernateException {
		StringBuffer queryTupla = new StringBuffer();
		queryTupla.append("FROM ");
		queryTupla.append(HIBERNATE_ScrRelation);
		queryTupla
				.append(" scr WHERE scr.typebook=? AND scr.typerel=? AND scr.scrOfic.id=?");
		queryTupla
				.append(" AND scr.scrOrg.id=? AND scr.relyear=? AND scr.relmonth=? AND scr.relday=?");

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime((Date) fila[0]);

		List nrel = session.find(queryTupla.toString(), new Object[] {
				new Integer(bookType), new Integer(typeRel),
				new Integer(scrofic.getId().intValue()),
				new Integer(((Integer) fila[1]).intValue()),
				new Integer(gc.get(Calendar.YEAR)),
				new Integer(gc.get(Calendar.MONTH) + 1),
				new Integer(gc.get(Calendar.DAY_OF_MONTH)) }, new Type[] {
				Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER,
				Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER,
				Hibernate.INTEGER });

		if (nrel != null && !nrel.isEmpty()) {
			return false;
		}

		return true;
	}

	private static String getCreateSentenceC(Integer bookID, int bookType,
			String tableName, String where, Integer maxReportRegister,
			String entidad) throws SQLException, Exception {
		StringBuffer fieldList = getFieldList(BBDDUtils
				.getTableSchemaFromDatabase(bookID.toString(), entidad)
				.getAttributesNames());
		String aditionalFields = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getAditionFields(bookType);
		String createSentence1 = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getReportCreateSelect1(tableName, bookID, where.toString(),
						fieldList.toString(), aditionalFields, bookType,
						maxReportRegister);
		log.info("getOptionCQuery Create sentence: " + createSentence1);

		return createSentence1;
	}

	private static String getCreateSentenceCMultiple(Session session,
			AuthenticationUser user, Iuserusertype userusertype,
			boolean isInBook, Integer bookID, List bookIds, int bookType,
			String tableName, String where, Integer maxReportRegister,
			String entidad) throws HibernateException, SQLException, Exception {
		String aditionalFields = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getAditionFields(bookType);
		String fieldList = getFieldList(isInBook, bookID, bookIds, entidad);

		StringBuffer createSentence1 = new StringBuffer();
		int index = 0;
		for (Iterator it = bookIds.iterator(); it.hasNext();) {
			Integer auxBookId = (Integer) it.next();
			ISicresAPerms aPerms = new ISicresAPerms();
			ScrRegstate scrRegstate = ISicresQueries.getScrRegstate(session,
					auxBookId);
			getAPerms(session, auxBookId, user.getId(), userusertype.getType(),
					user.getDeptid(), aPerms, scrRegstate.getIdocarchhdr());
			createSentence1.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getReportCreateSelectMultiple1(tableName, auxBookId,
							where, fieldList.toString(), index,
							aditionalFields, bookType, maxReportRegister));
			index++;
		}
		createSentence1.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getFinalQuery(maxReportRegister));
		log.info("getOptionCMultipleQuery Create createSentence1: "
				+ createSentence1.toString());

		return createSentence1.toString();
	}

	private static String getCreateSentenceA(AuthenticationUser user,
			AxSf axsf, AxSfQuery axsfQuery, Integer bookID, int bookType,
			String tableName, String aditionalFields, String filter,
			List fdrid, Integer maxReportRegister, String entidad)
			throws SQLException, Exception {
		String where = getWhereOptionA(filter, axsf, axsfQuery, user, fdrid,
				entidad);
		StringBuffer fieldList = getFieldList(BBDDUtils
				.getTableSchemaFromDatabase(bookID.toString(), entidad)
				.getAttributesNames());

		String createSentence1 = null;
		if (!fdrid.isEmpty() || axsfQuery != null) {
			createSentence1 = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getReportCreateSelect1(tableName, bookID,
							where.toString(), fieldList.toString(),
							aditionalFields, bookType, maxReportRegister);
		} else {
			int lastFdrid = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.lastRegister(null, user.getId(), bookID, entidad);
			if (lastFdrid == 0) {
				createSentence1 = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getReportCreateSelectLastRegister(tableName, bookID,
								where.toString(), fieldList.toString(),
								aditionalFields, bookType);
			} else {
				createSentence1 = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getReportCreateSelectLastRegister1(tableName, bookID,
								lastFdrid, fieldList.toString(),
								aditionalFields, bookType);
			}
		}

		log.info("getOptionAQuery Create createSelectSentence1: "
				+ createSentence1);

		return createSentence1;
	}

	private static String getCreateSentenceAMultiple(Session session,
			AuthenticationUser user, AxSf axsf, AxSfQuery axsfQuery,
			Integer bookID, Iuserusertype userusertype, ScrOfic scrofic,
			List bookIds, boolean isInBook, String tableName,
			String aditionalFields, int bookType, Integer maxReportRegister,
			String entidad) throws HibernateException, SQLException, Exception {
		String fieldList = getFieldList(isInBook, bookID, bookIds, entidad);

		StringBuffer createSentence1 = new StringBuffer();
		int index = 0;
		for (Iterator it = bookIds.iterator(); it.hasNext();) {
			Integer auxBookId = (Integer) it.next();
			ISicresAPerms aPerms = new ISicresAPerms();
			ScrRegstate scrRegstate = ISicresQueries.getScrRegstate(session,
					auxBookId);
			getAPerms(session, auxBookId, user.getId(), userusertype.getType(),
					user.getDeptid(), aPerms, scrRegstate.getIdocarchhdr());
			axsfQuery.setBookId(auxBookId);
			String filter = null;
			if (!aPerms.isBookAdmin()) {
				filter = getQueryFilter(session, auxBookId, user.getId(),
						scrofic);
			}
			String where = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.createWhereClausuleReport(axsf, axsfQuery, filter);
			createSentence1.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getReportCreateSelectMultiple1(tableName, auxBookId,
							where.toString(), fieldList.toString(), index,
							aditionalFields, bookType, maxReportRegister));

			index++;
		}
		createSentence1.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getFinalQuery(maxReportRegister));
		axsfQuery.setBookId(bookID);
		log.info("getOptionAMultipleQuery Create createSentence1: "
				+ createSentence1.toString());

		return createSentence1.toString();
	}

	private static String getWhereOptionA(String filter, AxSf axsf,
			AxSfQuery axsfQuery, AuthenticationUser user, List fdrid,
			String entidad) throws Exception {

		if (axsfQuery == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("fld3");
			buffer.append(IGUAL);
			buffer.append(COMILLA);
			if (isDataBaseCaseSensitive(entidad)) {
				buffer.append(user.getName().toUpperCase());
			} else {
				buffer.append(user.getName());
			}
			buffer.append(COMILLA);

			if (filter != null) {
				filter = filter.concat(" AND ");
				filter = filter + buffer.toString();
			} else {
				filter = buffer.toString();
			}
		}

		StringBuffer where = new StringBuffer();
		if (!fdrid.isEmpty()) {
			where.append(" WHERE FDRID IN (");
			Object[] ids = fdrid.toArray();
			for (int i = 0; i < ids.length; i++) {
				where.append(ids[i]);
				if (i != ids.length - 1) {
					where.append(",");
				}
			}
			where.append(")");
		} else {
			where.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.createWhereClausuleReport(axsf, axsfQuery, filter));
		}

		return where.toString();
	}

	private static String getWhereOptionC(Session session, String unit,
			ScrOfic scrofic, Date beginDate, Date endDate, List privOrgs,
			boolean isTarget) throws HibernateException, ReportException,
			Exception {
		StringBuffer where = new StringBuffer();
		if (scrofic != null) {
			where.append(" WHERE FLD5=");
			where.append(scrofic.getId());
			where.append(" AND FLD6 IN ("
					+ ISicresKeys.SCR_ESTADO_REGISTRO_CERRADO + ","
					+ ISicresKeys.SCR_ESTADO_REGISTRO_COMPLETO + ","
					+ ISicresKeys.SCR_ESTADO_REGISTRO_INCOMPLETO + ")");
		} else {
			where.append(" WHERE FLD6 IN ("
					+ ISicresKeys.SCR_ESTADO_REGISTRO_CERRADO + ","
					+ ISicresKeys.SCR_ESTADO_REGISTRO_COMPLETO + ","
					+ ISicresKeys.SCR_ESTADO_REGISTRO_INCOMPLETO + ")");
		}
		where.append(" AND FLD2>="
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getTimeStampField(
						beginDate, 0));
		where.append(" AND FLD2<="
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getTimeStampField(
						endDate, 1));

		int unitId = -1;
		if (unit != null) {
			try {
				unitId = ISUnitsValidator
						.getUnit(session, unit, true, privOrgs).getId()
						.intValue();
			} catch (ValidationException e) {
				throw new ReportException(
						ReportException.ERROR_CANNOT_GENERATE_REPORT_INVALID_CODE);
			}
		}
		if (unit != null) {
			if (isTarget) {
				where.append(" AND FLD8=" + unitId);
			} else {
				where.append(" AND FLD7=" + unitId);
			}
		}

		return where.toString();
	}

	private static void generateRelationsNumOptionC(Session session,
			ScrOfic scrofic, int bookType, String tableName, int queryType,
			boolean isTarget, ReportResult result, Integer maxReportRegister,
			String entidad) throws HibernateException, SQLException, Exception {

		doFinal(result, tableName, queryType, maxReportRegister.intValue(),
				entidad);

		if (result.getSize() > 0 && scrofic != null) {
			if (isTarget) {
				generateRelationsNum(4, session, tableName, bookType, scrofic,
						entidad);
			} else {
				generateRelationsNum(5, session, tableName, bookType, scrofic,
						entidad);
			}
		}
	}
}
