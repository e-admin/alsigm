/**
 *
 */
package com.ieci.tecdoc.isicres.session.utils;

import gnu.trove.THashMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.Type;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.entity.AxFdrhEntity;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.entity.AxXfEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesdoc.Idocfdrstat;
import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;
import com.ieci.tecdoc.common.invesdoc.Iusergrouphdr;
import com.ieci.tecdoc.common.invesdoc.Iusergroupuser;
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;
import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserobjperm;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;
import com.ieci.tecdoc.common.invesicres.ScrBookadmin;
import com.ieci.tecdoc.common.invesicres.ScrBookofic;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOficadm;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrUserfilter;
import com.ieci.tecdoc.common.invesicres.ScrUsrIdent;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.AxXfPK;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISDistribution;
import com.ieci.tecdoc.common.utils.ISOfficesValidator;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ISicresSaveQueries;
import com.ieci.tecdoc.idoc.authentication.LDAPAuthenticationPolicy;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.idoc.utils.ConfiguratorBook;
import com.ieci.tecdoc.idoc.utils.ConfiguratorBookType;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 * @date 30/06/2009
 *
 */
public class UtilsSession implements ServerKeys, Keys, HibernateKeys {

	protected static final String AMPERSAN = "#";
	protected static final String BARRA = "|";
	protected static final String FLD = "Fld";
	protected static final String IGUAL = "=";
	protected static final String COMILLA = "'";

	protected static final String DEFAULTSIMPLEDATEFORMAT = "dd-MM-yyyy";
	protected static final String LONGSIMPLEDATEFORMAT = "dd-MM-yyyy HH:mm:ss";

	protected static final int VALIDATE_TYPE_UA = 1;
	protected static final int VALIDATE_TYPE_SUBJECT = 3;
	protected static final int VALIDATE_ESTADO_REGISTRO = 6;
	protected static final int VALIDATE_TYPE_REGISTER_TYPE = 7;
	protected static final int VALIDATE_INVESDOC = 1000;

	private static final Logger log = Logger.getLogger(UtilsSession.class);

	/***************************************************************************
	 * PUBLIC METHOD
	 **************************************************************************/

	/**
	 * Metodo que nos dice con que base de datos estamos trabajando
	 *
	 * @param sessionID
	 * @return
	 * @throws Exception
	 */
	public static String getDataBaseType(String sessionID) throws Exception {
		String dataBase = null;
		try {
			dataBase = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDataBaseType();
		} catch (Exception e) {
			throw e;
		}
		return dataBase;
	}

	/**
	 * Metodo que nos devuelve la version de oracle con la que estamos
	 * trabajando
	 *
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	public static String getOracleVersion(String entidad) throws Exception {
		String oVersion = null;
		try {
			oVersion = DBEntityDAOFactory.getCurrentDBEntityDAO().getVersion(
					entidad);
			String aux1 = oVersion.substring(0, oVersion.indexOf("."));
			String aux = oVersion.substring(oVersion.indexOf(".") + 1, oVersion
					.length());
			if (new Integer(aux1).intValue() < 10) {
				oVersion = "1";
			} else {
				oVersion = aux.substring(0, aux.indexOf("."));
			}
		} catch (Exception e) {
			throw e;
		}
		return oVersion;
	}

	/**
	 * Metodo que nos dice si la base de datos contra la que estamos trabajando
	 * es CaseSensitive o no
	 *
	 * @param sessionID
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	public static boolean isDataBaseCaseSensitive(String entidad)
			throws Exception {
		String dataBaseType = getDataBaseType(null);

		if (dataBaseType.equals("DB2") || dataBaseType.equals("POSTGRESQL")
				|| dataBaseType.equals("ORACLE")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Obtenemos la fecha actual de la base de datos
	 *
	 * @param sessionID
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static Date getDBDateServer(String sessionID, String entidad)
			throws BookException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			Date currentDate = BBDDUtils
					.getDateFromTimestamp(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDBServerDate(entidad));

			HibernateUtil.commitTransaction(tran);
			return currentDate;

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible obtein DB Server Date for the session ["
					+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_OBTAIN_DBSERVER_DATE);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible obtein DB Server Date for the session ["
					+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_OBTAIN_DBSERVER_DATE);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Obtenemos los datos de informacion de la session
	 *
	 * @param sessionID
	 * @param entidad
	 * @return
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	public static SessionInformation getSessionInformation(String sessionID,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;

		boolean isLdap = LDAPAuthenticationPolicy.isLdap(entidad);

		try {
			SessionInformation sessionInformation = new SessionInformation();
			sessionInformation.setSessionId(sessionID);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			sessionInformation.setUser(user.getName());

			int otherOffice = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getOtherOffice(user.getId().intValue(), entidad);

			sessionInformation
					.setCaseSensitive(getDataBaseCaseSentitiveOrInsensitive(entidad));

			ScrUsrIdent scrUsrIdent = null;
			try {
				scrUsrIdent = (ScrUsrIdent) session.load(ScrUsrIdent.class,
						user.getId());
			} catch (HibernateException e) {
			}

			if (!isLdap) {
				sessionInformation.setUserName(getUserName(scrUsrIdent, user
						.getName()));

				sessionInformation.setOtherOffice(String
						.valueOf(getOtherOffices(session, sessionID, locale,
								entidad).size()));
			} else {
				sessionInformation.setUserName(getUserNameLdap(session, user,
						entidad));

				sessionInformation.setOtherOffice(getOtherOfficeLdap(user,
						entidad, otherOffice));

			}
			if (scrOfic != null) {
				sessionInformation.setOfficeCode(scrOfic.getCode());
				// sessionInformation.setOfficeName(scrOfic.getName());
				String oficName = "";
				if (scrOfic != null) {
					if (!locale.getLanguage().equals("es")) {
						oficName = DBEntityDAOFactory.getCurrentDBEntityDAO()
								.getDescriptionByLocale(scrOfic.getId(), false,
										false, locale.getLanguage(),
										EntityByLanguage.getTableName(5),
										entidad);
					} else {
						oficName = scrOfic.getName();
					}
				}
				sessionInformation.setOfficeName(oficName);
				sessionInformation.setOfficeEnabled(new Boolean(scrOfic.getDisableDate() == null).toString());
			}

			HibernateUtil.commitTransaction(tran);
			return sessionInformation;

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible obtein session information of user for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_OBTAIN_SESSION_INFORMATION);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error(
					"Impossible obtein session information of user for the session ["
							+ sessionID + "]", e);
			throw new BookException(
					BookException.ERROR_CANNOT_OBTAIN_SESSION_INFORMATION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Desbloquea un registro
	 *
	 * @param session
	 * @param bookID
	 * @param folderID
	 * @param user
	 * @return
	 * @throws HibernateException
	 */
	public static boolean unlock(Session session, Integer bookID, int folderID,
			AuthenticationUser user) throws HibernateException {
		List list = ISicresQueries.getIdocfdrstat(session, bookID, folderID);

		boolean result = false;
		Idocfdrstat idoc = null;
		if (list != null && !list.isEmpty()) {
			idoc = (Idocfdrstat) list.get(0);
			if (idoc.getUserid() == user.getId().intValue()
					&& idoc.getStat() == 1) {
				result = true;
				session.delete(idoc);

				if (log.isDebugEnabled()) {
					log.info("Libro [" + bookID + "] Carpeta [" + folderID
							+ "] desbloqueada.");
				}
			} else {
				result = false;
				if (log.isDebugEnabled()) {
					log.info("Libro [" + bookID + "] Carpeta [" + folderID
							+ "] no se puede desbloquear idoc.userid ["
							+ idoc.getUserid() + "] user [" + user.getId()
							+ "] stat [" + idoc.getStat() + "]");
				}
			}
		} else {
			result = true;
			if (log.isDebugEnabled()) {
				log.info("Libro [" + bookID + "] Carpeta [" + folderID
						+ "] no se puede desbloquearXXX.");
			}
		}

		return result;
	}
	
	

	/**
	 * Obtenemos la configuracion del tipo de libro
	 *
	 * @param bookType
	 * @param entidad
	 * @return
	 */
	public static BookTypeConf bookTypeConf(int bookType, String entidad) {
		// Obtiene la información de configuración por tipo de libro
		BookTypeConf result = null;
		try {
			result = ConfiguratorBookType.getInstance(entidad).getBookTypeConf(
					bookType);
		} catch (Exception e) {
			log.error("Impossible to load invesicres configuration", e);
		}
		return result;
	}
	
	/**
	 * Obtenemos la configuracion del tipo de libro
	 *
	 * @param bookType
	 * @param entidad
	 * @return
	 */
	public static BookConf bookConf(int bookId, String entidad) {
		// Obtiene la información de configuración por tipo de libro
		BookConf result = null;
		try {
			result = ConfiguratorBook.getInstance(entidad).getBookConf(
					bookId);
		} catch (Exception e) {
			log.error("Impossible to load invesicres configuration", e);
		}
		return result;
	}


	public static String getDistributionSourceDescription(Session session,
			ScrDistreg scr, boolean isLdap) {

		String sourceDescription = "";

		switch (scr.getTypeOrig()) {
		case 1: {
			try {
				if (isLdap) {
					Iuserldapuserhdr userLdap = ISicresQueries.getUserLdapUser(
							session, new Integer(scr.getIdOrig()));
					sourceDescription = userLdap.getLdapfullname();
				} else {
					Iuseruserhdr userIdoc = (Iuseruserhdr) session.load(
							Iuseruserhdr.class, new Integer(scr.getIdOrig()));
					if ((userIdoc.getRemarks() == null)
							|| (StringUtils.isEmpty(userIdoc.getRemarks()))) {
						sourceDescription = userIdoc.getName();
					} else {
						sourceDescription = userIdoc.getRemarks();
					}
				}
			} catch (HibernateException e) {
				sourceDescription = " - ";
			}
			break;
		}
		case 2: {
			Iuserdepthdr idoc = null;
			try {
				idoc = (Iuserdepthdr) session.load(Iuserdepthdr.class,
						new Integer(scr.getIdOrig()));
				sourceDescription = idoc.getName();
			} catch (HibernateException e) {
				sourceDescription = " - ";
			}
			break;
		}
		case 3: {
			try {
				if (isLdap) {
					Iuserldapgrphdr ldapGrp = ISicresQueries.getUserLdapPgrp(
							session, new Integer(scr.getIdOrig()));
					sourceDescription = ldapGrp.getLdapfullname();
				} else {
					Iusergrouphdr idocGrp = (Iusergrouphdr) session.load(
							Iusergrouphdr.class, new Integer(scr.getIdOrig()));
					sourceDescription = idocGrp.getName();
				}
			} catch (HibernateException e) {
				sourceDescription = " - ";
			}
			break;
		}
		case 4: {
			ScrOrg idoc = null;
			try {
				idoc = (ScrOrg) session.load(ScrOrg.class, new Integer(scr
						.getIdOrig()));
				sourceDescription = idoc.getName();
			} catch (HibernateException e) {
				sourceDescription = " - ";
			}
			break;
		}
		default:
			break;
		}

		return sourceDescription;
	}

	public static String getDistributionTargetDescription(Session session,
			ScrDistreg scr, boolean isLdap) {

		String targetDescription = "";
		switch (scr.getTypeDest()) {
		case 1: {
			try {
				if (isLdap) {
					Iuserldapuserhdr userLdap = ISicresQueries.getUserLdapUser(
							session, new Integer(scr.getIdDest()));
					targetDescription = userLdap.getLdapfullname();
				} else {
					Iuseruserhdr userIdoc = (Iuseruserhdr) session.load(
							Iuseruserhdr.class, new Integer(scr.getIdDest()));
					if ((userIdoc.getRemarks() == null)
							|| (StringUtils.isEmpty(userIdoc.getRemarks()))) {
						targetDescription = userIdoc.getName();
					} else {
						targetDescription = userIdoc.getRemarks();
					}
				}
			} catch (HibernateException e) {
				targetDescription = " - ";
			}
			break;
		}
		case 2: {
			Iuserdepthdr idoc = null;
			try {
				idoc = (Iuserdepthdr) session.load(Iuserdepthdr.class,
						new Integer(scr.getIdDest()));
				targetDescription = idoc.getName();
			} catch (HibernateException e) {
				targetDescription = " - ";
			}
			break;
		}
		case 3: {

			try {
				if (isLdap) {
					Iuserldapgrphdr ldapGrp = ISicresQueries.getUserLdapPgrp(
							session, new Integer(scr.getIdDest()));
					targetDescription = ldapGrp.getLdapfullname();
				} else {
					Iusergrouphdr idoc = (Iusergrouphdr) session.load(
							Iusergrouphdr.class, new Integer(scr.getIdDest()));
					targetDescription = idoc.getName();
				}
			} catch (HibernateException e) {
				targetDescription = " - ";
			}
			break;
		}
		case 4: {
			ScrOrg idoc = null;
			try {
				idoc = (ScrOrg) session.load(ScrOrg.class, new Integer(scr
						.getIdDest()));
				targetDescription = idoc.getName();
			} catch (HibernateException e) {
				targetDescription = " - ";
			}
			break;
		}
		default:
			break;
		}

		return targetDescription;
	}

	/**
	 * Metodo que comprueba el estado de un registro para cambiar su estado a cerrado
	 * @param session
	 * @param bookID
	 * @param fdrid
	 * @param user
	 * @param scrOfic
	 * @param entidad
	 * @return boolean
	 * @throws HibernateException
	 * @throws Exception
	 */
	public static boolean validateLockForCloseReg(Session session, Integer bookID, int fdrid,
			AuthenticationUser user, ScrOfic scrOfic, String entidad)
			throws HibernateException, Exception {
		boolean result = false;

		//Obtenemos el valor de tiempo de la configuracion para expirar el bloqueo
		int timeLockRegisterUser = 0;
		try{
			timeLockRegisterUser = Integer
					.parseInt(Configurator
							.getInstance()
							.getProperty(
									ConfigurationKeys.KEY_DESKTOP_TIME_LOCK_REGISTER_USER)) * 1000;
		}catch(Exception e){
			log.error("Error al obtener el parametro de configuracion: timeLockRegisterUser");
			timeLockRegisterUser = 80640000;
		}

		// List list = null;
		// ScrDistreg scrDistReg = null;
		AxFdrhEntity axFdrhEntity = null;

		List listIdocfdrstat = ISicresQueries.getIdocfdrstat(session, bookID,
				fdrid);

		Timestamp currentDate = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDBServerDate(entidad);
		if (listIdocfdrstat != null && !listIdocfdrstat.isEmpty()) {
			//comprobamos si el registro esta bloqueado por el usuario
			result = getLockRegByUser(session, user, timeLockRegisterUser,
					listIdocfdrstat, currentDate);
		}else{
			//no esta bloqueado
			result = true;
		}

		axFdrhEntity = new AxFdrhEntity();
		axFdrhEntity.updateAccessControl(bookID, user.getId().intValue(),
				currentDate, fdrid, entidad);

		return result;
	}

	public static boolean lock(Session session, Integer bookID, int fdrid,
			AuthenticationUser user, ScrOfic scrOfic, String entidad)
			throws HibernateException, Exception {
		boolean result = false;

		//Obtenemos el valor de tiempo de la configuracion para expirar el bloqueo
		int timeLockRegisterUser = 0;
		try{
			timeLockRegisterUser = Integer
					.parseInt(Configurator
							.getInstance()
							.getProperty(
									ConfigurationKeys.KEY_DESKTOP_TIME_LOCK_REGISTER_USER)) * 1000;
		}catch(Exception e){
			log.error("Error al obtener el parametro de configuracion: timeLockRegisterUser");
			timeLockRegisterUser = 80640000;
		}

		// List list = null;
		// ScrDistreg scrDistReg = null;
		AxFdrhEntity axFdrhEntity = null;

		List listIdocfdrstat = ISicresQueries.getIdocfdrstat(session, bookID,
				fdrid);

		Timestamp currentDate = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDBServerDate(entidad);
		if (listIdocfdrstat != null && !listIdocfdrstat.isEmpty()) {
			//comprobamos si el registro esta bloqueado por el usuario
			result = getLockRegByUser(session, user, timeLockRegisterUser,
					listIdocfdrstat, currentDate);
		} else {
			// Se comprueba si esta bloqueado por haber sido aceptado tras una
			// distribución
			boolean lockAccept = false;
			if (scrOfic != null) {
				lockAccept = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDistAccept(bookID, fdrid, scrOfic.getId(), entidad);
			}
			if (!lockAccept) {
				List listScrDistReg = ISicresQueries.getScrDistReg(session,
						bookID, fdrid);
				boolean lockDistReg = false;
				if (listScrDistReg != null && !listScrDistReg.isEmpty()) {
					lockDistReg = isLockDistReg(listScrDistReg, user
							.getDeptid());

					List iUserGroupUser = ISicresQueries.getIUserGroupUser(
							session, user.getId());
					List distReg = getDistRegsFromLock(user, listScrDistReg,
							iUserGroupUser);

					if (distReg.size() != 0) {
						result = isLockForDist(session, bookID, fdrid, user,
								currentDate, distReg, lockDistReg);
					} else if (lockDistReg) {
						result = false;
					} else {
						ISicresSaveQueries.saveIDocFdrStat(session, bookID,
								fdrid, user, BBDDUtils.getDate(currentDate));
						result = true;
					}
				} else {
					// no bloqueada, entonces bloquearla
					ISicresSaveQueries.saveIDocFdrStat(session, bookID, fdrid,
							user, BBDDUtils.getDate(currentDate));
					result = true;
				}
			} else {
				result = false;
			}
		}

		axFdrhEntity = new AxFdrhEntity();
		axFdrhEntity.updateAccessControl(bookID, user.getId().intValue(),
				currentDate, fdrid, entidad);

		return result;
	}

	/**
	 * Metodo que comprueba si un registro esta bloqueado por el usuario logeado
	 * o por otro usuario
	 *
	 * @param session
	 * @param user
	 * @param timeLockRegisterUser
	 * @param listIdocfdrstat
	 * @param currentDate
	 * @return boolean
	 * @throws HibernateException
	 */
	private static boolean getLockRegByUser(Session session,
			AuthenticationUser user, int timeLockRegisterUser,
			List listIdocfdrstat, Timestamp currentDate)
			throws HibernateException {
		boolean result = false;
		// mirar si bloqueada por otro usuario
		Idocfdrstat idoc = (Idocfdrstat) listIdocfdrstat.get(0);
		if (idoc.getUserid() != user.getId().intValue()
				&& idoc.getStat() == 1) {
			//si el registro lleva bloqueado mas de 24 horas por el mismo usuario se desbloquea
			if ((currentDate.getTime() - idoc.getTimestamp().getTime())>timeLockRegisterUser){
				//borramos el bloqueo de ese usuario
				session.delete(idoc);

				//desbloqueado el registro por tiempo muerto
				result = true;
			}
			else{
				// bloqueada por otro usuario
				result = false;
			}
		} else {
			// bloqueada por este mismo usuario
			result = true;
		}
		return result;
	}

	/**
	 * Metodo que obtiene los campos EXTENDIDOS de un registro
	 *
	 * @param idoc - Objeto de tipo @{Idocarchdet}
	 * @param limit - Indice a partir del cual se considera como campo adicional o extendido
	 * @return Listado de fldIds
	 */
	public static List getExtendedFields(Idocarchdet idoc, int limit) {
		List result = new ArrayList();
		FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

		for (Iterator it = fieldFormat.getFlddefs().values().iterator(); it
				.hasNext();) {
			FFldDef fldDef = (FFldDef) it.next();
			int fldid = Integer.parseInt(fldDef.getColname().substring(
					FLD.length(), fldDef.getColname().length()));
			if (fldid > limit
					&& fldDef.getType() == IDocKeys.IDOCARCHDET_FLD_TYPE_LONG_STR) {
				result.add(new Integer(fldid));
			}
		}

		return result;
	}

	/**
	 * Metodo que obtiene los campos adicionales del registro
	 *
	 * @param idoc - Objeto de tipo @{Idocarchdet}
	 * @param limit - Indice a partir del cual se considera como campo adicional o extendido
	 * @return Listado de fldIds
	 */
	public static List getAdditionalFields(Idocarchdet idoc, int limit){
		List result = new ArrayList();
		FieldFormat fieldFormat = new FieldFormat(idoc.getDetval());

		for (Iterator it = fieldFormat.getFlddefs().values().iterator(); it
				.hasNext();) {
			FFldDef fldDef = (FFldDef) it.next();
			int fldid = Integer.parseInt(fldDef.getColname().substring(
					FLD.length(), fldDef.getColname().length()));
			if (fldid > limit && fldDef.getType() != IDocKeys.IDOCARCHDET_FLD_TYPE_LONG_STR) {
					result.add(new Integer(fldid));
			}
		}

		return result;
	}


	public static int getNumeration(Session session, Integer bookId,
			ScrOfic scrOfic, ScrRegstate scrregstate) throws HibernateException {
		List list = null;
		int numeration = 0;

		if (scrOfic != null) {
			list = ISicresQueries.getScrBookofic(session, bookId, scrOfic
					.getId());
		}

		if (list != null && !list.isEmpty()) {
			ScrBookofic scrbookofic = (ScrBookofic) list.get(0);
			numeration = scrbookofic.getNumeration();
		} else {
			if (scrregstate != null) {
				numeration = scrregstate.getNumerationType();
			} else {
				throw new HibernateException(
						"There is no ScrBookofic and ScrRegstate.");
			}
		}

		return numeration;
	}

	public static void extractRegistersFromQuery(Collection col, Map result,
			Session session, Idocarchdet idoc, AxSfEntity axSfEntity,
			String language, String entidad) throws Exception {
		int index = 0;
		Map cache = new HashMap();
		for (Iterator it = col.iterator(); it.hasNext();) {
			AxPK axPK = (AxPK) it.next();
			axSfEntity.load(axPK, entidad);
			AxSf axsfAux = axSfEntity.getAxSf(entidad);
			axSfEntity.isNotLoaded();

			loadAxSf(session, axPK, axsfAux, cache, idoc, language, entidad);

			result.put(new Integer(index++), axsfAux);
		}
	}

	/**
	 * Cargamos los datos del registro
	 *
	 * @param session
	 * @param axPK
	 * @param axsfAux
	 * @param map
	 * @param idoc
	 * @param entidad
	 * @throws Exception
	 */
	public static void loadAxSf(Session session, AxPK axPK, AxSf axsfAux,
			Map map, Idocarchdet idoc, String language, String entidad)
			throws Exception {

		AxXfPK pk = null;

		try {
			if (axsfAux instanceof AxSfIn) {
				pk = new AxXfPK(axPK.getType(), axPK.getId(),
						AxXf.DEFAULT_AXXF_IN_COMMENT_FIELD);
			} else {
				pk = new AxXfPK(axPK.getType(), axPK.getId(),
						AxXf.DEFAULT_AXXF_OUT_COMMENT_FIELD);
			}
			AxXfEntity axXfEntity = new AxXfEntity();
			axXfEntity.findByPrimaryKey(pk, entidad);
			axXfEntity.load(entidad);
			axsfAux.setAxxf(axXfEntity.getAxXf());
		} catch (Exception fE) {
		}

		List extendedFields = null;
		if (axsfAux instanceof AxSfIn) {
			extendedFields = getExtendedFields(idoc, Keys.EREG_FDR_MATTER);
		} else {
			extendedFields = getExtendedFields(idoc, Keys.SREG_FDR_MATTER);
		}
		for (Iterator it = extendedFields.iterator(); it.hasNext();) {
			Integer id = (Integer) it.next();

			axsfAux.addProposedExtendedFiels(id);
			try {
				if (axsfAux instanceof AxSfIn) {
					pk = new AxXfPK(axPK.getType(), axPK.getId(), id.intValue());
				} else {
					pk = new AxXfPK(axPK.getType(), axPK.getId(), id.intValue());
				}

				AxXfEntity axXfEntity = new AxXfEntity();
				axXfEntity.findByPrimaryKey(pk, entidad);
				axXfEntity.load(entidad);
				axsfAux.addExtendedField(id, axXfEntity.getAxXf());
			} catch (Exception fE) {
			}
		}

		axsfAux = loadAxSfAttributes(session, axsfAux, map, language, entidad);

	}

	public static void getAPerms(Session session, Integer bookId,
			Integer userId, int userType, Integer deptId, ISicresAPerms aPerms,
			ScrRegstate scrRegstate, ScrOfic scrofic) throws HibernateException {

		Idocarchhdr idocarchhdr = scrRegstate.getIdocarchhdr();

		aPerms.setIsBookAdmin(false);
		aPerms.setCanQuery(false);
		aPerms.setCanCreate(false);
		aPerms.setCanModify(false);

		if (userType == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
			// Si es superadministrador, ya no hay que hacer nada
			getAPermsSuperAdmin(aPerms, scrofic, scrRegstate.getState());
		} else if (userType == IDocKeys.IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN) {
			// Si es administrador de libro
			getAPermsBookAdmin(session, bookId, aPerms, idocarchhdr, scrofic,
					userId, scrRegstate.getState());
		}

		if (!aPerms.isBookAdmin()) {
			getAPermsUser(session, bookId, userId, deptId, aPerms, scrofic,
					scrRegstate.getState());
		}
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/**
	 *
	 * @param session
	 * @param bookId
	 * @param bookInformation
	 * @throws HibernateException
	 */
	protected static void getIdocarchdet(Session session, Integer bookId,
			Map bookInformation) throws HibernateException {
		List list = ISicresQueries.getIdocarchdet(session, bookId);

		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				Idocarchdet idoc = (Idocarchdet) it.next();
				if (idoc.getDettype() == IDocKeys.IDOCARCHDET_FLD_DEF) {
					bookInformation.put(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT,
							idoc);
				} else if (idoc.getDettype() == IDocKeys.IDOCARCHDET_MISC) {
					bookInformation.put(IDocKeys.IDOCARCHDET_MISC_ASOBJECT,
							idoc);
				} else if (idoc.getDettype() == IDocKeys.IDOCARCHDET_VLD_DEF) {
					bookInformation.put(IDocKeys.IDOCARCHDET_VLD_DEF_ASOBJECT,
							idoc);
				}
			}
		} else {
			throw new HibernateException("There is no Idocarchdet.");
		}
	}

	protected static void getQueryFilter(Session session,
			THashMap bookInformation, Integer bookId, Integer userId,
			ScrOfic scrofic) throws HibernateException {
		String queryFilter = getQueryFilter(session, bookId, userId, scrofic);

		if (queryFilter != null && queryFilter.length() > 0) {
			bookInformation.put(QUERY_FILTER, "(" + queryFilter + ")");
		}
	}

	protected static String getQueryFilter(Session session, Integer bookId,
			Integer userId, ScrOfic scrofic) throws HibernateException {
		StringBuffer queryFilter = new StringBuffer();

		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_ScrOficadm);
		query.append(" scr WHERE scr.iduser=?");

		List listOficadm = session.find(query.toString(),
				new Object[] { userId }, new Type[] { Hibernate.INTEGER });
		if (listOficadm != null && !listOficadm.isEmpty()) {
			// es administrador de oficina
			queryFilter.append(getQueryFilterOficAdmin(listOficadm, scrofic));
		} else {
			// no es administrador de oficina
			queryFilter.append(getQueryFilterUser(session, bookId, userId,
					scrofic));
		}
		if (queryFilter.length() > 0) {
			return queryFilter.toString();
		} else {
			return null;
		}
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/**
	 * Cargamos los atributos del registro
	 *
	 * @param session
	 * @param axsfAux
	 * @param map
	 * @param language
	 * @param entidad
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws Exception
	 */
	private static AxSf loadAxSfAttributes(Session session, AxSf axsfAux,
			Map map, String language, String entidad)
			throws HibernateException, SQLException, Exception {
		if (axsfAux.getAttributeValueAsString(AxSf.FLD5_FIELD) != null) {
			try {
				Integer id = new Integer(axsfAux
						.getAttributeValueAsString(AxSf.FLD5_FIELD));

				ScrOfic ofic = null;

				if (map.containsKey(HIBERNATE_ScrOfic + id)) {
					ofic = (ScrOfic) map.get(HIBERNATE_ScrOfic + id);
				} else {
					ofic = ISOfficesValidator.getOfficeById(session, id);
					map.put(HIBERNATE_ScrOfic + id, ofic);
				}
				if (!"es".equals(language)) {
					axsfAux.setFld5Name(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDescriptionByLocale(
									ofic.getId(), false, false, language,
									EntityByLanguage.getTableName(5), entidad));
				} else {
					axsfAux.setFld5Name(ofic.getName());
				}
				axsfAux.setFld5(ofic);
			} catch (ValidationException vE) {
			}
		}

		if (axsfAux.getAttributeValueAsString(AxSf.FLD7_FIELD) != null) {
			try {
				Integer id = new Integer(axsfAux
						.getAttributeValueAsString(AxSf.FLD7_FIELD));
				ScrOrg org = null;
				if (map.containsKey(HIBERNATE_ScrOrg + id)) {
					org = (ScrOrg) map.get(HIBERNATE_ScrOrg + id);
				} else {
					org = ISUnitsValidator.getUnitById(session, id);
					map.put(HIBERNATE_ScrOrg + id, org);
				}
				if (!language.equals("es")) {
					axsfAux.setFld7Name(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDescriptionByLocale(
									org.getId(), false, false, language,
									EntityByLanguage.getTableName(7), entidad));
				} else {
					axsfAux.setFld7Name(org.getName());
				}
				axsfAux.setFld7(org);
			} catch (ValidationException vE) {
			}
		}

		if (axsfAux.getAttributeValueAsString(AxSf.FLD8_FIELD) != null) {
			try {
				Integer id = new Integer(axsfAux
						.getAttributeValueAsString(AxSf.FLD8_FIELD));
				ScrOrg org = null;
				if (map.containsKey(HIBERNATE_ScrOrg + id)) {
					org = (ScrOrg) map.get(HIBERNATE_ScrOrg + id);
				} else {
					org = ISUnitsValidator.getUnitById(session, id);
					map.put(HIBERNATE_ScrOrg + id, org);
				}
				if (!language.equals("es")) {
					axsfAux.setFld8Name(DBEntityDAOFactory
							.getCurrentDBEntityDAO().getDescriptionByLocale(
									org.getId(), false, false, language,
									EntityByLanguage.getTableName(8), entidad));
				} else {
					axsfAux.setFld8Name(org.getName());
				}
				axsfAux.setFld8(org);
			} catch (ValidationException vE) {
			}
		}

		axsfAux = loadAxSfInOutAttributes(session, axsfAux, map, language,
				entidad);

		return axsfAux;
	}

	/**
	 * Cargamos los atributos del registro atendiendo a si es un registro de
	 * entrada o de salida
	 *
	 * @param session
	 * @param axsfAux
	 * @param map
	 * @param language
	 * @param entidad
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws Exception
	 */
	private static AxSf loadAxSfInOutAttributes(Session session, AxSf axsfAux,
			Map map, String language, String entidad)
			throws HibernateException, SQLException, Exception {
		if (axsfAux instanceof AxSfIn) {
			if (axsfAux.getAttributeValueAsString(AxSf.FLD13_FIELD) != null) {

				try {
					Integer id = new Integer(axsfAux
							.getAttributeValueAsString(AxSf.FLD13_FIELD));
					ScrOrg org = null;
					if (map.containsKey(HIBERNATE_ScrOrg + id)) {
						org = (ScrOrg) map.get(HIBERNATE_ScrOrg + id);
					} else {
						org = ISUnitsValidator.getEntRegById(session, id);
						map.put(HIBERNATE_ScrOrg + id, org);
					}
					if (!language.equals("es")) {
						((AxSfIn) axsfAux).setFld13Name(DBEntityDAOFactory
								.getCurrentDBEntityDAO()
								.getDescriptionByLocale(org.getId(), false,
										false, language,
										EntityByLanguage.getTableName(13),
										entidad));
					} else {
						((AxSfIn) axsfAux).setFld13Name(org.getName());
					}
					((AxSfIn) axsfAux).setFld13(org);
				} catch (ValidationException VE) {
				}
			}

			if (axsfAux.getAttributeValueAsString(AxSf.FLD16_FIELD) != null) {
				try {
					Integer id = new Integer(axsfAux
							.getAttributeValueAsString(AxSf.FLD16_FIELD));
					ScrCa ca = null;
					if (map.containsKey(HIBERNATE_ScrCa + id)) {
						ca = (ScrCa) map.get(HIBERNATE_ScrCa + id);
					} else {
						ca = ISSubjectsValidator.getSubjectById(session, id);
						map.put(HIBERNATE_ScrCa + id, ca);
					}
					if (!language.equals("es")) {
						((AxSfIn) axsfAux).setFld16Name(DBEntityDAOFactory
								.getCurrentDBEntityDAO()
								.getDescriptionByLocale(ca.getId(), false,
										true, language,
										EntityByLanguage.getTableName(16),
										entidad));
					} else {
						((AxSfIn) axsfAux).setFld16Name(ca.getMatter());
					}
					((AxSfIn) axsfAux).setFld16(ca);
				} catch (ValidationException VE) {
				}
			}
		} else {
			if (axsfAux.getAttributeValueAsString(AxSf.FLD12_FIELD) != null) {
				try {
					Integer id = new Integer(axsfAux
							.getAttributeValueAsString(AxSf.FLD12_FIELD));
					ScrCa ca = null;
					if (map.containsKey(HIBERNATE_ScrCa + id)) {
						ca = (ScrCa) map.get(HIBERNATE_ScrCa + id);
					} else {
						ca = ISSubjectsValidator.getSubjectById(session, id);
						map.put(HIBERNATE_ScrCa + id, ca);
					}
					if (!language.equals("es")) {
						((AxSfOut) axsfAux).setFld12Name(DBEntityDAOFactory
								.getCurrentDBEntityDAO()
								.getDescriptionByLocale(ca.getId(), false,
										true, language,
										EntityByLanguage.getTableName(12),
										entidad));
					} else {
						((AxSfOut) axsfAux).setFld12Name(ca.getMatter());
					}
					((AxSfOut) axsfAux).setFld12(ca);
				} catch (ValidationException VE) {
				}
			}
		}

		return axsfAux;
	}

	private static List getUserLdapUserHdr(Integer id, String entidad)
			throws SQLException {

		Statement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		List listIUserLdap = new ArrayList();
		String query = "SELECT iuser.id, iuser.ldapguid, iuser.ldapfullname "
				+ "FROM iuserldapuserhdr iuser " + "WHERE iuser.id = "
				+ String.valueOf(id.intValue());

		try {

			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Iuserldapuserhdr iUserLdap = new Iuserldapuserhdr();
				iUserLdap.setId((new Integer(resultSet.getInt(1))).intValue());
				iUserLdap.setLdapguid(new String(resultSet.getString(2)));
				iUserLdap.setLdapfullname(new String(resultSet.getString(3)));

				listIUserLdap.add(iUserLdap);
			}

		} catch (SQLException e) {
			log.warn("Error ejecutando [" + query + "]", e);
			throw e;
		} catch (Throwable e) {
			log.warn("Error ejecutando [" + query + "]", e);
			throw new SQLException("Error ejecutando [" + query.toString()
					+ "]");
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(resultSet);
			BBDDUtils.close(connection);
		}

		return listIUserLdap;

	}

	/**
	 * Metodo que comprueba si alguna de las distribuciones que se pasan como
	 * parametro cumple la siguiente condición, si esta se cumple el registro se
	 * considera bloqueado.
	 *
	 * Si el tipo de Origen de la distribucion es de tipo Departamento(2) y el
	 * id del departamento es distinto al departamnento que se pasa como
	 * parametro, ademas del estado de la distribucion es distinto de pendiente,
	 * se considerara como bloqueado.
	 *
	 * @param listScrDistReg
	 * @param deptId
	 * @return
	 */
	private static boolean isLockDistReg(List listScrDistReg, Integer deptId) {
		for (int i = 0; i < listScrDistReg.size(); i++) {
			ScrDistreg scrDistReg = (ScrDistreg) listScrDistReg.get(i);

			if (scrDistReg.getTypeOrig() == 2
					&& scrDistReg.getIdOrig() != deptId.intValue()
					&& scrDistReg.getState() != ISDistribution.STATE_PENDIENTE
					&& scrDistReg.getState() != ISDistribution.STATE_PENDIENTEDIST) {
				return true;
			}
		}

		return false;
	}

	private static List getDistRegsFromLock(AuthenticationUser user,
			List listScrDistReg, List iUserGroupUser) {
		List distReg = new ArrayList();
		for (int i = 0; i < listScrDistReg.size(); i++) {
			ScrDistreg scrDistReg = (ScrDistreg) listScrDistReg.get(i);
			if (iUserGroupUser.size() > 0) {
				for (int j = 0; j < iUserGroupUser.size(); j++) {
					Integer idGroup = new Integer(
							((Iusergroupuser) iUserGroupUser.get(j))
									.getGroupid());

					if ((scrDistReg.getTypeDest() == 1 && scrDistReg
							.getIdDest() == user.getId().intValue())
							|| (scrDistReg.getTypeDest() == 2 && scrDistReg
									.getIdDest() == user.getDeptid().intValue())
							|| (scrDistReg.getTypeDest() == 3 && scrDistReg
									.getIdDest() == idGroup.intValue())) {
						distReg.add(scrDistReg);
						break;
					}
				}
			} else {
				if ((scrDistReg.getTypeDest() == 1 && scrDistReg.getIdDest() == user
						.getId().intValue())
						|| (scrDistReg.getTypeDest() == 2 && scrDistReg
								.getIdDest() == user.getDeptid().intValue())) {
					distReg.add(scrDistReg);
				}

			}
		}

		return distReg;
	}

	private static boolean isLockForDist(Session session, Integer bookID,
			int fdrid, AuthenticationUser user, Timestamp currentDate,
			List distReg, boolean lockDistReg) throws HibernateException,
			Exception {
		ScrDistreg scrDistRegAux = (ScrDistreg) distReg.get(0);
		for (int k = 1; k < distReg.size(); k++) {
			ScrDistreg scrDistRegAux1 = (ScrDistreg) distReg.get(k);
			if (scrDistRegAux.getId().intValue() < scrDistRegAux1.getId()
					.intValue()) {
				scrDistRegAux = scrDistRegAux1;
			}
		}

		if (lockDistReg) {
			if (scrDistRegAux.getState() == 2 || scrDistRegAux.getState() == 3) {
				ISicresSaveQueries.saveIDocFdrStat(session, bookID, fdrid,
						user, BBDDUtils.getDate(currentDate));
				return true;
			} else {
				return false;
			}
		} else if (scrDistRegAux.getState() != 4) {
			ISicresSaveQueries.saveIDocFdrStat(session, bookID, fdrid, user,
					BBDDUtils.getDate(currentDate));
			return true;
		} else {
			return false;
		}
	}

	private static void getAPermsSuperAdmin(ISicresAPerms aPerms,
			ScrOfic scrofic, int regState) {
		aPerms.setIsBookAdmin(true);
		aPerms.setCanQuery(true);
		if ((scrofic != null) && (scrofic.getDeptid() != 0) && (regState == 0)) {
			aPerms.setCanCreate(true);
			aPerms.setCanModify(true);
		}
	}

	private static void getAPermsBookAdmin(Session session, Integer bookId,
			ISicresAPerms aPerms, Idocarchhdr idocarchhdr, ScrOfic scrofic,
			Integer userId, int regState) throws HibernateException {
		ScrBookadmin scrBookadmin = ISicresQueries.getScrBookadmin(session,
				bookId, userId);

		if (scrBookadmin != null) {
			// el usuario es administrador de libro
			aPerms.setIsBookAdmin(true);
			aPerms.setCanQuery(true);
			if ((scrofic != null) && (scrofic.getDeptid() != 0)
					&& (regState == 0)) {
				aPerms.setCanCreate(true);
				aPerms.setCanModify(true);
			}
		} else if (idocarchhdr != null
				&& idocarchhdr.getCrtrid() == userId.intValue()) {
			// el usuario es creado de libro
			aPerms.setIsBookAdmin(true);
			aPerms.setCanQuery(true);
			if ((scrofic != null) && (scrofic.getDeptid() != 0)
					&& (regState == 0)) {
				aPerms.setCanCreate(true);
				aPerms.setCanModify(true);
			}
		}
	}

	private static void getAPermsUser(Session session, Integer bookId,
			Integer userId, Integer deptId, ISicresAPerms aPerms,
			ScrOfic scrofic, int regState) throws HibernateException {
		List listPermsUser = ISicresQueries.getIuserobjpermForUser(session,
				bookId, userId);
		List listPermsDept = ISicresQueries.getIuserobjpermForDept(session,
				bookId, deptId);
		List listPerms = null;

		if ((listPermsUser != null) && !listPermsUser.isEmpty()) {
			listPerms = listPermsUser;
		}

		if ((listPermsDept != null) && !listPermsDept.isEmpty()) {
			if (listPerms != null) {
				listPerms.addAll(listPermsDept);
			} else {
				listPerms = listPermsDept;
			}
		}

		if ((listPerms != null) && !listPerms.isEmpty()) {

			for (int i = 0; i < listPerms.size(); i++) {
				Iuserobjperm iuser = (Iuserobjperm) listPerms.get(i);
				int APerm = iuser.getAperm();

				switch (APerm) {
				case (IDocKeys.IUSEROBJPERM_QUERY_PERM): {
					aPerms.setCanQuery(true);
					break;
				}
				case (IDocKeys.IUSEROBJPERM_CREATE_PERM): {
					if ((scrofic != null) && (regState == 0)) {
						aPerms.setCanCreate(true);
					}
					break;
				}
				case (IDocKeys.IUSEROBJPERM_MODIFY_PERM): {
					if ((scrofic != null) && (regState == 0)) {
						aPerms.setCanModify(true);
					}
					break;
				}
				}
			}
		}
	}

	private static String getQueryFilterOficAdmin(List listOficadm,
			ScrOfic scrofic) {
		List idofics = new ArrayList();
		StringBuffer queryFilter = new StringBuffer();

		for (Iterator it = listOficadm.iterator(); it.hasNext();) {
			ScrOficadm scr = (ScrOficadm) it.next();
			idofics.add(scr.getScrOfic().getId());
		}

		if (!idofics.isEmpty() && scrofic != null && scrofic.getId() != null) {
			queryFilter.append(" (FLD5=");
			queryFilter.append(scrofic.getId().toString());
			queryFilter.append(" OR FLD5 IN (");
			int size = idofics.size();
			int i = 0;
			for (Iterator it = idofics.iterator(); it.hasNext();) {
				queryFilter.append(it.next().toString());
				if (i < size - 1) {
					queryFilter.append(",");
				}
				i++;
			}
			queryFilter.append("))");
		}

		if (queryFilter.length() > 0) {
			return queryFilter.toString();
		}

		return "";
	}

	private static String getQueryFilterUser(Session session, Integer bookId,
			Integer userId, ScrOfic scrofic) throws HibernateException {

		StringBuffer queryFilter = new StringBuffer();

		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_ScrUserfilter);
		query
				.append(" scr WHERE scr.iduser=? AND scr.typeObj=1 AND scr.idarch=?");

		List listUserfilter = session.find(query.toString(), new Object[] {
				userId, bookId }, new Type[] { Hibernate.INTEGER,
				Hibernate.INTEGER });
		if (listUserfilter != null && !listUserfilter.isEmpty()) {
			// tiene filtro asignado
			ScrUserfilter scr = (ScrUserfilter) listUserfilter.get(0);
			queryFilter.append(scr.getFilterdef().substring(0,
					scr.getFilterdef().indexOf("$")));
		} else if (scrofic != null && scrofic.getId() != null) {
			// el usuario no tiene asignado un filtro, ver si su oficina
			// tiene filtro asignado
			queryFilter.append(getQueryFilterUserOfic(session, bookId, scrofic
					.getId()));
		}

		if (queryFilter.length() > 0) {
			return queryFilter.toString();
		} else {
			return "";
		}
	}

	private static String getQueryFilterUserOfic(Session session,
			Integer bookId, Integer scrOficId) throws HibernateException {

		StringBuffer queryFilter = new StringBuffer();

		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HIBERNATE_ScrUserfilter);
		query
				.append(" scr WHERE scr.iduser=? AND scr.typeObj=2 AND scr.idarch=?");

		List listUserfilter = session.find(query.toString(), new Object[] {
				scrOficId, bookId }, new Type[] { Hibernate.INTEGER,
				Hibernate.INTEGER });
		if (listUserfilter != null && !listUserfilter.isEmpty()) {
			// la oficina tiene un filtro asignado
			ScrUserfilter scr = (ScrUserfilter) listUserfilter.get(0);
			queryFilter.append(scr.getFilterdef().substring(0,
					scr.getFilterdef().indexOf("$")));
		} else {
			// la oficina no tiene asignado un filtro, se ponde uno
			// por defecto
			queryFilter.append(" FLD5=");
			queryFilter.append(scrOficId.toString());
		}

		if (queryFilter.length() > 0) {
			return queryFilter.toString();
		} else {
			return "";
		}
	}

	private static String getDataBaseCaseSentitiveOrInsensitive(String entidad)
			throws Exception {
		if (isDataBaseCaseSensitive(entidad)) {
			return "CS";
		} else {
			return "CI";
		}
	}

	private static String getUserName(ScrUsrIdent scrUsrIdent,
			String userNameOrig) {
		String userName = "";
		boolean isUserNameOk = false;
		if(scrUsrIdent!=null){
			if ((StringUtils.isNotEmpty(scrUsrIdent.getFirstName()))
					&& (StringUtils.isNotEmpty(scrUsrIdent.getFirstName().trim()))) {
				userName = userName + scrUsrIdent.getFirstName();
				isUserNameOk = true;
			}

			if ((StringUtils.isNotEmpty(scrUsrIdent.getSecondName()))
					&& (StringUtils.isNotEmpty(scrUsrIdent.getSecondName().trim()))) {
				userName = userName + " " + scrUsrIdent.getSecondName();
				isUserNameOk = true;
			}

			if ((StringUtils.isNotEmpty(userName))
					&& (StringUtils.isNotEmpty(userName.trim()))) {
				userName = userName + ", " + scrUsrIdent.getSurName();
				isUserNameOk = true;
			} else {
				userName = userName + scrUsrIdent.getSurName();
				isUserNameOk = true;
			}
		}
		if (isUserNameOk) {
			return userName;
		} else {
			return userNameOrig;
		}
	}

	private static String getUserNameLdap(Session session,
			AuthenticationUser user, String entidad) {
		String userName = "";
		boolean isUserNameOk = false;
		Iuserldapuserhdr iUserLdap = null;
		try {
			List list = getUserLdapUserHdr(user.getId(), entidad);
			if (list != null && list.size() == 1) {
				iUserLdap = (Iuserldapuserhdr) list.get(0);
			}
		} catch (SQLException e) {
		}

		if ((iUserLdap != null) && (iUserLdap.getLdapfullname() != null)) {
			userName = iUserLdap.getLdapfullname();
			isUserNameOk = true;
		} else {
			Iuseruserhdr iuser = null;
			try {
				iuser = (Iuseruserhdr) session.load(Iuseruserhdr.class, user
						.getId());
			} catch (HibernateException e) {
			}

			if (iuser != null && iuser.getRemarks() != null
					&& iuser.getRemarks().length() > 0) {
				userName = iuser.getRemarks();
				isUserNameOk = true;
			}
		}

		if (isUserNameOk) {
			return userName;
		} else {
			return user.getName();
		}
	}

	private static String getOtherOfficeLdap(AuthenticationUser user,
			String entidad, int otherOffice) {
		try {
			List list = getUserLdapUserHdr(user.getId(), entidad);
			if (list != null && list.size() == 1) {
				Iuserldapuserhdr iUserLdap = (Iuserldapuserhdr) list.get(0);

				if ((iUserLdap != null)
						&& (iUserLdap.getLdapfullname() != null)) {
					List oficinasUser = new ArrayList();
					if (user.getDeptList() != null) {
						oficinasUser.addAll(user.getDeptList());
					}
					oficinasUser.remove(user.getDeptid());
					otherOffice = oficinasUser.size();
				}
			}
		} catch (SQLException e) {
		}

		return new Integer(otherOffice).toString();

	}

	private static List getOtherOffices(Session session, String sessionID, Locale locale,
			String entidad) throws AttributesException, SessionException,
			ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		String sqlUsrOfic = "id in (select idofic from scr_usrofic where iduser = ";
		String sqlIuseruserhdr = "deptid in (select deptid from iuseruserhdr where id = ";
		StringBuffer queryUsrOfic = new StringBuffer();
		StringBuffer queryIuseruserhdr = new StringBuffer();
		try {

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			queryUsrOfic.append(sqlUsrOfic);
			queryUsrOfic.append(user.getId());
			queryUsrOfic.append(")");

			queryIuseruserhdr.append(sqlIuseruserhdr);
			queryIuseruserhdr.append(user.getId());
			queryIuseruserhdr.append(")");

			// Criteria criteriaResults = session.createCriteria(ScrOfic.class);
			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrOficLanguage(locale.getLanguage()));
			criteriaResults.add(Expression.sql(queryUsrOfic.toString()));
			List list = criteriaResults.list();

			// Criteria criteriaResults1 =
			// session.createCriteria(ScrOfic.class);
			Criteria criteriaResults1 = session.createCriteria(EntityByLanguage
					.getScrOficLanguage(locale.getLanguage()));
			criteriaResults1.add(Expression.sql(queryIuseruserhdr.toString()));
			List list1 = criteriaResults1.list();

			EntityByLanguage.removeOfic(scrOfic, list, list1);

			return list;
		} catch (SessionException sE) {
			throw sE;
		} catch (Exception e) {
			log.error("Impossible to load other offices for user for session ["
					+ sessionID + "]", e);
			throw new AttributesException(
					AttributesException.ERROR_CANNOT_FIND_OTHER_OFFICE_USER);
		}
	}
}
