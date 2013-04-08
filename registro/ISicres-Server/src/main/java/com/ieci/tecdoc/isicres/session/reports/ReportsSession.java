package com.ieci.tecdoc.isicres.session.reports;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.ReportException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrReport;
import com.ieci.tecdoc.common.invesicres.ScrReportperf;
import com.ieci.tecdoc.common.isicres.ReportResult;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class ReportsSession extends ReportsSessionUtil implements ServerKeys,
		HibernateKeys {

	private static final Logger log = Logger.getLogger(ReportsSession.class);

	public static List getScrReports(String sessionID, Integer bookID,
			int reportType, Locale locale, String entidad)
			throws BookException, SessionException, ReportException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		List scrList = new ArrayList();
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			int bookType = ISicresKeys.SCRREGSTATE_IN_BOOK;
			if (Repository.getInstance(entidad).isOutBook(bookID)
					.booleanValue()) {
				bookType = ISicresKeys.SCRREGSTATE_OUT_BOOK;
			}

			StringBuffer query = new StringBuffer();
			query.append("FROM ");
			query.append(HIBERNATE_ScrReport);
			query
					.append(" scr WHERE scr.typeReport=? AND scr.typeArch=? ORDER BY scr.id");

			// Iterator iterator = session.iterate(query.toString(), new
			// Object[] {
			// new Integer(reportType), new Integer(bookType) },
			// new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
			Iterator iterator = null;
			if (!locale.getLanguage().equals("es")) {
				iterator = (DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getReportsListByLocale(reportType, bookType, locale
								.getLanguage(), EntityByLanguage
								.getTableName(20), entidad)).iterator();
			} else {
				iterator = session.iterate(query.toString(), new Object[] {
						new Integer(reportType), new Integer(bookType) },
						new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
			}
			while (iterator.hasNext()) {
				ScrReport scrReport = (ScrReport) iterator.next();
				boolean include = scrReport.getAllArch() == 1
						&& scrReport.getAllOfics() == 1
						&& scrReport.getAllPerfs() == 1;

				if (isInclude(session, bookID, userusertype, scrofic,
						scrReport, include)) {
					scrList.add(scrReport);
					include = false;
				}
			}

			HibernateUtil.commitTransaction(tran);

			return scrList;
		} catch (BookException bE) {
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to load the reports for [" + bookID
					+ "] reportType [" + reportType + "] for the session ["
					+ sessionID + "]", e);
			throw new ReportException(ReportException.ERROR_CANNOT_FIND_REPORTS);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * @deprecated
	 * @param sessionID
	 * @param bookID
	 * @param reportResult
	 * @param entidad
	 * @throws BookException
	 * @throws SessionException
	 * @throws ReportException
	 * @throws ValidationException
	 */
	public static void linkReportResult(String sessionID, Integer bookID,
			ReportResult reportResult, String entidad) throws BookException,
			SessionException, ReportException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			// Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			String tableName = reportResult.getTableName().trim();
			if (Repository.getInstance(entidad).isOutBook(bookID)
					.booleanValue()) {
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.alterTableAddColumnString(
								tableName,
								new String[] { "FLD5_TEXT", "FLD7_TEXT",
										"FLD8_TEXT", "FLD12_TEXT" }, entidad);

				DBEntityDAOFactory
						.getCurrentDBEntityDAO()
						.updateFromTable(
								tableName,
								new String[] { "FLD5", "FLD7", "FLD8", "FLD12" },
								new String[] { "FLD5_TEXT", "FLD7_TEXT",
										"FLD8_TEXT", "FLD12_TEXT" },
								new String[] { "SCR_OFIC", "SCR_ORGS",
										"SCR_ORGS", "SCR_CA" },
								new String[] { "NAME", "CODE || '-' || NAME",
										"CODE || '-' || NAME",
										"CODE || '-' || MATTER" }, entidad);
			} else {
				DBEntityDAOFactory
						.getCurrentDBEntityDAO()
						.alterTableAddColumnString(
								tableName,
								new String[] { "FLD5_TEXT", "FLD7_TEXT",
										"FLD8_TEXT", "FLD13_TEXT", "FLD16_TEXT" },
								entidad);

				DBEntityDAOFactory
						.getCurrentDBEntityDAO()
						.updateFromTable(
								tableName,
								new String[] { "FLD5", "FLD7", "FLD8", "FLD13",
										"FLD16" },
								new String[] { "FLD5_TEXT", "FLD7_TEXT",
										"FLD8_TEXT", "FLD13_TEXT", "FLD16_TEXT" },
								new String[] { "SCR_OFIC", "SCR_ORGS",
										"SCR_ORGS", "SCR_ORGS", "SCR_CA" },
								new String[] { "NAME", "CODE || '-' || NAME",
										"CODE || '-' || NAME", "CODE",
										"CODE || '-' || MATTER" }, entidad);
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
			log.error("Impossible to getOptionAQuery for the session ["
					+ sessionID + "]", e);
			throw new ReportException(
					ReportException.ERROR_CANNOT_GENERATE_REPORT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Parámetro opcion dice si el tipo de informe es de relación o no
	 */
	public static ReportResult getOptionAQuery(String sessionID,
			Integer bookID, List fdrid, int opcion, Integer maxReportRegister,
			String entidad) throws BookException, SessionException,
			ReportException, ValidationException {
		return getOptionA(sessionID, bookID, fdrid, opcion, maxReportRegister,
				entidad, false);
	}

	/**
	 * Parámetro opcion dice si el tipo de informe es de relación o no
	 *
	 */
	public static ReportResult getOptionAMultipleQuery(String sessionID,
			Integer bookID, List bookIds, int opcion,
			Integer maxReportRegister, String entidad) throws BookException,
			SessionException, ReportException, ValidationException {
		return getOptionA(sessionID, bookID, bookIds, opcion,
				maxReportRegister, entidad, true);
	}

	/**
	 * Método exclusivo de infomes de relación. Variable isTarget=true destino,
	 * false origen *
	 */
	public static ReportResult getOptionCQuery(String sessionID,
			Integer bookID, Date beginDate, Date endDate, String unit,
			boolean isTarget, Integer maxReportRegister, String entidad)
			throws BookException, SessionException, ReportException,
			ValidationException {
		return getOptionC(sessionID, bookID, beginDate, endDate, unit,
				isTarget, null, maxReportRegister, entidad, false);
	}

	/**
	 * Método exclusivo de infomes de relación. Variable isTarget=true destino,
	 * false origen
	 */
	public static ReportResult getOptionCMultipleQuery(String sessionID,
			Integer bookID, Date beginDate, Date endDate, String unit,
			boolean isTarget, List bookIds, Integer maxReportRegister,
			String entidad) throws BookException, SessionException,
			ReportException, ValidationException {

		return getOptionC(sessionID, bookID, beginDate, endDate, unit,
				isTarget, bookIds, maxReportRegister, entidad, true);
	}

	public static void dropTable(String sessionID, ReportResult reportResult,
			String entidad) throws ReportException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			// Recuperamos la sesión

			tran = session.beginTransaction();

			// Es necesario tener el libro abierto para consultar su contenido.
			DBEntityDAOFactory.getCurrentDBEntityDAO().dropTableOrView(
					reportResult.getTableName(), entidad);

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to dropTable [" + reportResult.getTableName()
					+ "] for the session [" + sessionID + "]", e);
			throw new ReportException(
					ReportException.ERROR_CANNOT_GENERATE_REPORT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void getTemplateReport(String sessionID, int reportId,
			String temporalDirectory, String entidad) throws ReportException {
		try {
			DBEntityDAOFactory.getCurrentDBEntityDAO().getReportData(reportId,
					temporalDirectory, entidad);
		} catch (SQLException e) {
			log.error("Impossible to getTemplateReports [" + reportId
					+ "] for the session [" + sessionID
					+ "] on temporal directory [" + temporalDirectory + "]", e);
			throw new ReportException(
					ReportException.ERROR_CANNOT_GET_TEMPLATEREPORT);
		} catch (Exception e) {
			log.error("Impossible to getTemplateReports [" + reportId
					+ "] for the session [" + sessionID
					+ "] on temporal directory [" + temporalDirectory + "]", e);
			throw new ReportException(
					ReportException.ERROR_CANNOT_GET_TEMPLATEREPORT);
		}
	}

	private static boolean isInclude(Session session, Integer bookID,
			Iuserusertype userusertype, ScrOfic scrofic, ScrReport scrReport,
			boolean include) throws HibernateException {
		if (!include) {
			if (scrReport.getAllArch() == 0) {
				List aux = ISicresQueries.getScrReportArch(session, bookID,
						scrReport.getId());
				include = aux != null && !aux.isEmpty();
			}
			if (scrReport.getAllOfics() == 0 && !include) {
				List aux = ISicresQueries.getScrReportOfic(session, scrofic
						.getId(), scrReport.getId());
				include = aux != null && !aux.isEmpty();
			}
			if (scrReport.getAllPerfs() == 0 && !include) {
				List aux = ISicresQueries.getScrReportPerf(session, scrReport
						.getId());
				int rol = 0;
				if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
					rol = 4;
				} else if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN) {
					rol = 2;
				} else if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_OPERATOR) {
					rol = 1;
				}

				for (Iterator it = aux.iterator(); it.hasNext() && !include;) {
					ScrReportperf scrReportPerf = (ScrReportperf) it.next();
					include = ((scrReportPerf.getIdPerf() & rol) == rol);
				}
			}
		}

		return include;
	}

}