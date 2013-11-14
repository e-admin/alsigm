//
//FileName: SecuritySSession.java
//
package com.ieci.tecdoc.isicres.session.security;

import gnu.trove.THashMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.Type;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserobjperm;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;
import com.ieci.tecdoc.idoc.repository.RepositoryLDAP;
import com.ieci.tecdoc.isicres.audit.helper.ISicresAuditHelper;
import com.ieci.tecdoc.isicres.session.security.policies.AuthenticationFactory;
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

public class SecuritySession extends SecuritySessionUtil implements ServerKeys,
		HibernateKeys {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(SecuritySession.class);

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static String login(String login, String password, String userDn,
			Boolean useLdap, Boolean usingOSAuth, Locale locale, String entidad)
			throws ValidationException, SecurityException {

		if (!useLdap.booleanValue() || !usingOSAuth.booleanValue()) {
			Validator.validate_String_NotNull_LengthMayorZero(login,
					ValidationException.ATTRIBUTE_USER);
			Validator.validate_String_NotNull(password,
					ValidationException.ATTRIBUTE_PASSWORD);
		}

		Transaction tran = null;
		AuthenticationUser user = null;
		try {
			// Validamos el usuario
			user = validateUser(login, password, userDn, useLdap, usingOSAuth,
					entidad);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			String sessionID = completarDatosLogin(locale, entidad, user,
					session, null, true);

			HibernateUtil.commitTransaction(tran);
			return sessionID;
		} catch (ValidationException vE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw vE;
		} catch (SecurityException sE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (TecDocException iE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (HibernateException hE) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + login + "] password ["
					+ password + "]", hE);
			throw new SecurityException(SecurityException.ERROR_SQL);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + login + "] password ["
					+ password + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally {
			if (tran != null) {
				HibernateUtil.closeSession(entidad);
			}
		}
	}

	public static String login(String login, String password, Locale locale,
			String entidad) throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(login,
				ValidationException.ATTRIBUTE_USER);
		Validator.validate_String_NotNull(password,
				ValidationException.ATTRIBUTE_PASSWORD);

		Transaction tran = null;
		AuthenticationUser user = null;
		try {
			// Validamos el usuario
			user = validateUser(login, password, "", Boolean.FALSE,
					Boolean.FALSE, entidad);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			String sessionID = completarDatosLogin(locale, entidad, user,
					session, null, false);

			HibernateUtil.commitTransaction(tran);
			return sessionID;
		} catch (ValidationException vE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw vE;
		} catch (SecurityException sE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (TecDocException iE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (HibernateException hE) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + login + "] password ["
					+ password + "]", hE);
			throw new SecurityException(SecurityException.ERROR_SQL);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + login + "] password ["
					+ password + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Logue al usuario en la oficina indicada.
	 *
	 * Si no se especifica una oficina se coge la oficina por defecto del usuario
	 *
	 * @param login
	 * @param password
	 * @param codigoOficina
	 * @param locale
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws SecurityException
	 */
	public static String login(String login, String password,
			String codigoOficina, Locale locale, String entidad)
			throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(login,
				ValidationException.ATTRIBUTE_USER);
		Validator.validate_String_NotNull(password,
				ValidationException.ATTRIBUTE_PASSWORD);

		Transaction tran = null;
		AuthenticationUser user = null;
		try {
			// Validamos el usuario
			user = validateUser(login, password, "", Boolean.FALSE,
					Boolean.FALSE, entidad);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			ScrOfic scrofic = null;
			if (StringUtils.isEmpty(codigoOficina)){
				// Chequeamos si el usuario tiene definida una oficina
				// preferente y en caso afirmativo, almacenarla como dato del
				// usuario
				scrofic = checkScrOficPref(session, user, locale.getLanguage(),
						entidad);
			} else{
				//obtenemos la oficina
				scrofic = getOfficeByCodeOfUser(codigoOficina, locale, user, session);
				//si no se recupera información para la oficina indicada
				if (scrofic == null) {
					StringBuffer sb = new StringBuffer("El usuario [")
							.append(login)
							.append("] no ha podido hacer login en la oficina con codigo [")
							.append(codigoOficina).append("]");
					log.error(sb.toString());
					throw new SecurityException(SecurityException.ERROR_SCROFIC_NOT_FOUND);

				}
				// si se ha indicado oficina para el usuario, se setea el valor
				// al usuario autenticado
				user.setDeptid(scrofic.getDeptid());
			}

			String sessionID = completarDatosLogin(locale, entidad, user,
					session, codigoOficina, false);

			HibernateUtil.commitTransaction(tran);
			return sessionID;
		} catch (ValidationException vE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw vE;
		} catch (SecurityException sE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (TecDocException iE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (HibernateException hE) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + login + "] password ["
					+ password + "]", hE);
			throw new SecurityException(SecurityException.ERROR_SQL);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + login + "] password ["
					+ password + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Método que completa los datos del usuario pasado como parámetro
	 * @param user - Información del usuario logado
	 * @param entidad - Entidad
	 * @param locale - Idioma
	 *
	 * @return String - SessionID, identificador del usuario logeado
	 *
	 * @throws HibernateException
	 * @throws Exception
	 * @throws SQLException
	 * @throws SecurityException
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public static String completarDatosLogin(AuthenticationUser user,
			String entidad, Locale locale) throws HibernateException,
			Exception, SQLException, SecurityException, SessionException,
			TecDocException {
		Transaction tran = null;
		String sessionID = null;
		try{
			//Se crea la sesion de hibernate
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			//Se completan los datos
			sessionID = completarDatosLogin(locale, entidad, user, session,
					null, true);

			HibernateUtil.commitTransaction(tran);

			//retornamos la session
			return sessionID;

		} catch (ValidationException vE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw vE;
		} catch (SecurityException sE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw sE;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (TecDocException iE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (HibernateException hE) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + user.getName() + "]", hE);
			throw new SecurityException(SecurityException.ERROR_SQL);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible validar el usuario [" + user.getName() + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally {
			//Cerramos la session de Hibernate
			HibernateUtil.closeSession(entidad);
		}
	}

	/**
	 * Método que completa los datos del proceso del login: obtiene datos
	 * oficina, permisos del usuario, almacena los datos recuperados en la cache
	 *
	 * @param locale
	 *            - Idioma
	 * @param entidad
	 *            - Entidad
	 * @param user
	 *            - Usuario logado
	 * @param session
	 *            - Datos session
	 * @param codigoOficina
	 *            - Código de la oficina, si el valor es nulo asigna la oficina
	 *            preferente del usuario
	 * @param valiteUserType
	 *            - Boolean: True si se ha de validar el tipo de usuario, FALSE
	 *            para no validar el tipo de usuario
	 *
	 * @return String sessionID
	 *
	 * @throws HibernateException
	 * @throws Exception
	 * @throws SQLException
	 * @throws SecurityException
	 * @throws SessionException
	 * @throws TecDocException
	 */
	private static String completarDatosLogin(Locale locale, String entidad,
			AuthenticationUser user, Session session, String codigoOficina, boolean valiteUserType)
			throws HibernateException, Exception, SQLException,
			SecurityException, SessionException, TecDocException {

		// Obtenemos los datos de la oficina (si el código es nulo o no devuelve
		// nada, obtiene los datos de la oficina preferente)
		ScrOfic scrofic = getOficina(codigoOficina, locale, entidad, user,
				session);

		//Obtenemos el nombre de la oficina
		String oficName = getNameOficina(locale, entidad, scrofic);

		//Obtenemos los datos horarios de la oficina
		ScrTmzofic scrtmzofic = getDatosHorariosOficina(session, scrofic);

		// Recuperamos el perfil
		Iuserusertype userType = getIuserusertype(session, user);
		//si se indica que se debe validar el tipo de usuario
		if(valiteUserType){
			//validamos el tipo de usuario
			validateUserType(userType);
		}

		//Obtenemos los permisos del usuario
		ISicresGenPerms genPerms = getGenPerms(session, user.getId(),
				userType.getType());
		//Obtenemos los datos de la ultima conexión
		Date userLastConnection = getUserLastConnection(user.getId(),
				entidad);

		// Creamos la sesion
		String sessionID = CacheFactory.getCacheInterface()
				.createCacheEntry();

		// Almacena los datos en la cache
		sessionPutObjects(sessionID, user, scrofic, scrtmzofic, genPerms,
				userType, userLastConnection, oficName);

		//Auditamos el acceso a la aplicación
		ISicresAuditHelper.auditarLogin(user, scrofic);

		if (log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("El usuario [").append(user.getName())
					.append("] login en el sistema con id [")
					.append(user.getId()).append("]");
			log.debug(sb.toString());
		}
		return sessionID;
	}


	/**
	 *
	 * Devuelve la oficina asociada al usuario a partir del código de la oficina.
	 *
	 * Si el usuario no pertenece a esa oficina devuelve nulo.
	 *
	 * @param codigoOficina
	 * @param locale
	 * @param user
	 * @param session
	 */
	private static ScrOfic getOfficeByCodeOfUser(String codigoOficina,
			Locale locale, AuthenticationUser user, Session session) {

		ScrOfic scrOfic = getScrOficByCode(session, locale.getLanguage(),
				codigoOficina);

		/*
		 * Obtener el objeto ScrOfic a partir del codigo de la oficina
		 * y después comprobar que esa oficna está en el listado de oficinas
		 * del usuario o en la oficina del departamento del usuario.
		 *
		 */
		if (null != scrOfic) {
			if (user.getDeptList() != null) {
				for (Iterator it = user.getDeptList().iterator(); it.hasNext();) {
					Integer idDept = (Integer) it.next();
					if (idDept.intValue() == scrOfic.getDeptid()) {
						return scrOfic;
					}
				}
			} else{
				log.warn("El usuario ["+user.getName()+"] tiene la lista de departamentos vacía");
				try {
					ScrOfic scroficDept = ISicresQueries.getScrOficByDeptId(session, user
							.getDeptid());
					if (scroficDept!=null){
						if (scrOfic.getId()==scroficDept.getId()){
							return scrOfic;
						}
					}
				} catch (HibernateException e) {
					log.error("No se ha podido obtener la oficina del departamento ["+user.getDeptid()+"]", e);
				}
			}
		}

		return null;
	}

	/**
	 * Método que obtiene los datos horarios de la oficina
	 * @param session - Session
	 * @param scrofic - Datos de la oficina
	 *
	 * @return ScrTmzofic - Datos horarios de la oficina
	 *
	 * @throws HibernateException
	 */
	private static ScrTmzofic getDatosHorariosOficina(Session session,
			ScrOfic scrofic) throws HibernateException {
		ScrTmzofic scrtmzofic = null;
		if (scrofic != null) {
			scrtmzofic = getScrTmzofic(session, scrofic.getId());
		}
		return scrtmzofic;
	}

	/**
	 * Método que obtiene la oficina según el codigo indicado, si el código es
	 * vacio o no devuelve ningun dato, se asigna la oficina por defecto del
	 * usuario
	 *
	 * @param codigoOficina - Código de la oficina
	 * @param locale - Idioma
	 * @param entidad - Entidad
	 * @param user - Datos del usuario
	 * @param session - Session
	 *
	 * @return ScrOfic - Datos de la oficina
	 *
	 * @throws HibernateException
	 * @throws Exception
	 */
	private static ScrOfic getOficina(String codigoOficina, Locale locale,
			String entidad, AuthenticationUser user, Session session)
			throws HibernateException, Exception {
		//Buscamos la oficina por su código
		ScrOfic scrofic = getScrOficByCode(session, locale
				.getLanguage(), codigoOficina);

		//si la oficina indica por su código es null buscamos la oficina preferente
		if (scrofic == null) {
			// Chequeamos si el usuario tiene definida una oficina
			// preferente y en caso afirmativo, almacenarla como dato del
			// usuario
			scrofic = checkScrOficPref(session, user, locale.getLanguage(),
					entidad);
		}
		return scrofic;
	}

	/**
	 * Método que obtiene el nombre de la oficina internacionalizado
	 *
	 * @param locale - Idioma
	 * @param entidad - Entidad
	 * @param scrofic - Datos de la oficina
	 *
	 * @return String - Nombre de la oficina
	 *
	 * @throws SQLException
	 * @throws Exception
	 */
	private static String getNameOficina(Locale locale, String entidad,
			ScrOfic scrofic) throws SQLException, Exception {
		String oficName = "";
		if (scrofic != null) {
			if (!("es").equals(locale.getLanguage())) {
				//Buscamos el nombre segun el idioma
				oficName = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDescriptionByLocale(scrofic.getId(), false,
								false, locale.getLanguage(),
								EntityByLanguage.getTableName(5), entidad);
			} else {
				//Obtenemos el nombre por defecto
				oficName = scrofic.getName();
			}
		}
		return oficName;
	}



	public static void changePassword(String login, String oldPassword,
			String newPassword, String entidad) throws ValidationException,
			SecurityException {
		Validator.validate_String_NotNull_LengthMayorZero(login,
				ValidationException.ATTRIBUTE_USER);
		Validator.validate_String_NotNull(oldPassword,
				ValidationException.ATTRIBUTE_PASSWORD);
		Validator.validate_String_NotNull(newPassword,
				ValidationException.ATTRIBUTE_PASSWORD);

		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			AuthenticationFactory.getCurrentPolicy().changePassword(login,
					newPassword, oldPassword, entidad);

			HibernateUtil.commitTransaction(tran);
		} catch (ValidationException vE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw vE;
		} catch (SecurityException sE) {
			try {
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException hE) {
				log.warn("Error al realizar el commit de la transacción", hE);
			}
			throw sE;
		} catch (TecDocException iE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible cambiar el password el usuario [" + login
					+ "] password [" + oldPassword + "]", e);
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static void logout(String sessionID, String entidad)
			throws SecurityException {
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			for (Iterator it = cacheBag.keySet().iterator(); it.hasNext();) {
				Object o = it.next();
				if (o instanceof Integer) {
					Integer bookID = (Integer) o;
					StringBuffer query = new StringBuffer();
					query.append("FROM ");
					query.append(HIBERNATE_Idocfdrstat);
					query
							.append(" scr WHERE scr.archid=? AND scr.userid=? and scr.stat=1");
					int result = session.delete(query.toString(), new Object[] {
							bookID, user.getId() }, new Type[] {
							Hibernate.INTEGER, Hibernate.INTEGER });

					if (log.isDebugEnabled()) {
						log.debug("Desbloqueos  [" + result + "]");
					}
				}
			}

			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);

			HibernateUtil.commitTransaction(tran);

			if (log.isDebugEnabled()) {
				log.debug("El usuario [" + user.getName()
						+ "] logout en el sistema.");
			}
		} catch (SessionException e) {
			HibernateUtil.rollbackTransaction(tran);
		} catch (Exception tE) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible hacer logout de la sesion [" + sessionID
					+ "].", tE);
			throw new SecurityException(
					SecurityException.ERROR_IMPOSSIBLE_LOGOUT);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static boolean isAlive(String sessionID) {
		// A parte de comprobar si esta viva la conexion, tambien la refresca
		try {
			CacheFactory.getCacheInterface().getCacheEntry(sessionID);
			return true;
		} catch (SessionException sE) {
			return false;
		} catch (Exception e) {
			log.error("Impossible to determinate if sessionID [" + sessionID
					+ "] is alive.", e);
			return false;
		}
	}

	/**
	 * metodo que comprueba si un registro puede ser cerrado por el usuario en curso
	 * No podra cerrar el registro si solo tiene el permiso de consulta
	 * @param sessionID
	 * @param bookID
	 * @return
	 * @throws ValidationException
	 * @throws SecurityException
	 */
	public static boolean canOpenCloseReg(String sessionID, Integer bookID)
	throws ValidationException, SecurityException {
		boolean result=false;
		try {
			result = canOpenCloseReg(sessionID);
		} catch (SessionException e) {
			log.error("No se puede obtener los permisos para abrir/cerrar registro",e);
		}
		return result;

	}
	public static boolean canOpenCloseReg(String sessionID) throws ValidationException, SecurityException, SessionException
	{

		boolean result=false;

		result=SecuritySession.isSuperuser(sessionID);

		return result;

	}

	public static boolean canQuery(String sessionID, Integer bookID)
			throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		boolean bRet = false;
		ISicresAPerms aPerms = getBookAPerms(sessionID, bookID);

		if (aPerms != null) {
			bRet = aPerms.canQuery();
		}

		return bRet;
	}

	public static boolean canCreate(String sessionID, Integer bookID)
			throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		boolean bRet = false;
		ISicresAPerms aPerms = getBookAPerms(sessionID, bookID);

		if (aPerms != null) {
			bRet = aPerms.canCreate();
		}

		return bRet;
	}

	public static AuthenticationUser getUserLogin(String sessionID)
			throws SessionException, TecDocException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		// Recuperamos la sesión
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);

		AuthenticationUser user = (AuthenticationUser) cacheBag
				.get(HIBERNATE_Iuseruserhdr);

		return user;
	}

	public static boolean canCreate(String sessionID, Integer bookID,
			boolean isUserPerm, Integer idObj, String entidad)
			throws BookException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

		Transaction tran = null;
		boolean result = false;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			List listPerms = null;
			if (isUserPerm) {
				listPerms = ISicresQueries.getIuserobjpermForUser(session,
						bookID, idObj);
			} else {
				listPerms = ISicresQueries.getIuserobjpermForDept(session,
						bookID, idObj);
			}

			if ((listPerms != null) && !listPerms.isEmpty()) {

				for (int i = 0; i < listPerms.size(); i++) {
					Iuserobjperm iuser = (Iuserobjperm) listPerms.get(i);
					int APerm = iuser.getAperm();

					switch (APerm) {
					case (IDocKeys.IUSEROBJPERM_CREATE_PERM): {
						return true;
					}
					}
				}
			}

			HibernateUtil.commitTransaction(tran);
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			throw new BookException(BookException.ERROR_CANNOT_OPEN_BOOK);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

		return result;
	}

	public static boolean canModify(String sessionID, Integer bookID)
			throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		boolean bRet = false;
		ISicresAPerms aPerms = getBookAPerms(sessionID, bookID);

		if (aPerms != null) {
			bRet = aPerms.canModify();
		}

		return bRet;
	}

	public static boolean isBookAdmin(String sessionID, Integer bookID)
			throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		boolean bRet = false;
		ISicresAPerms aPerms = getBookAPerms(sessionID, bookID);

		if (aPerms != null) {
			bRet = aPerms.isBookAdmin();
		}

		return bRet;
	}

	/**
	 * permisos genericos no ligados a un objeto [0] Permiso para añadir
	 * personas [1] Permiso para modificar personas [2] Permiso para modificar
	 * campos protegidos [3] Permiso para modificar fecha de registro [4]
	 * Permiso para establecer fecha de registro
	 *
	 * @param sessionID
	 * @param bookID
	 * @return
	 * @throws ValidationException
	 * @throws SessionException
	 * @throws SecurityException
	 */
	public static int[] getScrPermission(String sessionID, Integer bookID)
			throws ValidationException, SessionException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		int[] perm = new int[5];

		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			THashMap bookInformation = (THashMap) cacheBag.get(bookID);

			if (userusertype == null) {
				throw new SecurityException(
						SecurityException.ERROR_SCRUSRPERM_NOT_FOUND);
			}

			if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
				// Si es superadministrador, tiene todos los permisos
				perm[0] = ISicresKeys.SCR_USRPERM_ALL;
				perm[1] = ISicresKeys.SCR_USRPERM_ALL;
				perm[2] = ISicresKeys.SCR_USRPERM_ALL;
				perm[3] = ISicresKeys.SCR_USRPERM_ALL;
				perm[4] = ISicresKeys.SCR_USRPERM_ALL;
			} else {
				ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag
						.get(GENPERMS_USER);

				if (genPerms == null) {
					throw new SecurityException(
							SecurityException.ERROR_SCRUSRPERM_NOT_FOUND);
				} else {
					boolean isAdmin = false;
					ScrRegstate regState = (ScrRegstate) bookInformation
							.get(HIBERNATE_ScrRegstate);

					if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_BOOK_ADMIN) {
						isAdmin = isBookAdmin(sessionID, bookID);
					}

					perm[0] = genPerms.canCreatePersons() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING;
					perm[1] = genPerms.canUpdatePersons() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING;
					perm[4] = ISicresKeys.SCR_USRPERM_NOTHING;

					if (!isAdmin) {
						perm[2] = genPerms.canIntroRegDate() ? ISicresKeys.SCR_USRPERM_ALL
								: ISicresKeys.SCR_USRPERM_NOTHING;
						perm[3] = genPerms.canUpdateRegDate() ? ISicresKeys.SCR_USRPERM_ALL
								: ISicresKeys.SCR_USRPERM_NOTHING;

						if ((regState.getState() != ISicresKeys.BOOK_STATE_CLOSED)
								&& genPerms.canUpdateProtectedFields()) {
							perm[4] = ISicresKeys.SCR_USRPERM_ALL;
						}
					} else {
						perm[2] = ISicresKeys.SCR_USRPERM_ALL;
						perm[3] = ISicresKeys.SCR_USRPERM_ALL;

						if (regState.getState() != ISicresKeys.BOOK_STATE_CLOSED) {
							perm[4] = ISicresKeys.SCR_USRPERM_ALL;
						}
					}
				}
			}
		} catch (TecDocException tE) {
			log.error("Impossible to obtain ScrUsrperm for the session ["
					+ sessionID + "]", tE);
			throw new SecurityException(
					SecurityException.ERROR_SCRUSRPERM_NOT_FOUND);
		}

		return perm;
	}

	public static Integer[] getScrDistPermission(String sessionID)
			throws ValidationException, SessionException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);
		Integer[] perm = new Integer[6];

		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			ISicresGenPerms genDistPerms = (ISicresGenPerms) cacheBag
					.get(GENPERMS_USER);

			perm[0] = new Integer(
					genDistPerms.isCanAcceptRegisters() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING);
			perm[1] = new Integer(
					genDistPerms.isCanRejectRegisters() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING);
			perm[2] = new Integer(
					genDistPerms.isCanArchiveRegisters() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING);
			perm[3] = new Integer(
					genDistPerms.isCanChangeDestRegisters() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING);
			perm[4] = new Integer(
					genDistPerms.isCanChangeDestRejectRegisters() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING);
			perm[5] = new Integer(
					genDistPerms.isCanDistRegisters() ? ISicresKeys.SCR_USRPERM_ALL
							: ISicresKeys.SCR_USRPERM_NOTHING);

		} catch (TecDocException tE) {
			log.error(
					"Impossible to obtain ScrUsrperm (Distribution) for the session ["
							+ sessionID + "]", tE);
			throw new SecurityException(
					SecurityException.ERROR_SCRUSRPERM_NOT_FOUND);
		}

		return perm;
	}

	public static boolean canOperationIR(String sessionID)
		throws ValidationException, SessionException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag
					.get(GENPERMS_USER);
			return genPerms.canAccessRegInterchange();

		} catch (TecDocException tE) {
			log.error(
					"Impossible to obtain ScrUsrperm (OperationIR) for the session ["
							+ sessionID + "]", tE);
			throw new SecurityException(
					SecurityException.ERROR_SCRUSRPERM_NOT_FOUND);
		}

	}

	public static ScrOfic getCurrentUserOfic(String sessionID, Locale locale,
			String entidad) throws ValidationException, SecurityException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ScrOfic ofic = null;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);

			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			// Obtenemos los objetos para mantener en la sesion
			ofic = ISicresQueries.getScrOficByDeptId(session, user.getDeptid());
			if (ofic != null && !locale.getLanguage().equals("es")) {
				ofic.setName(DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDescriptionByLocale(ofic.getId(), false, false,
								locale.getLanguage(),
								EntityByLanguage.getTableName(5), entidad));
			}

			HibernateUtil.commitTransaction(tran);

			return ofic;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(
					SecurityException.ERROR_SCROFIC_NOT_FOUND);
		} catch (TecDocException iE) {
			HibernateUtil.rollbackTransaction(tran);
			throw new SecurityException(
					SecurityException.ERROR_SCROFIC_NOT_FOUND);
		} catch (Exception e) {
			// Por aqui pasa la HibernateException tambien.
			HibernateUtil.rollbackTransaction(tran);
			log.error("Imposible obtener la oficina del usuario", e);
			throw new SecurityException(
					SecurityException.ERROR_SCROFIC_NOT_FOUND);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	public static String getCurrentUserOficName(String sessionID,
			Locale locale, String entidad) throws ValidationException,
			SecurityException {
		ScrOfic ofic = getCurrentUserOfic(sessionID, locale, entidad);
		if (ofic != null) {
			return ofic.getName();
		}

		return "";
	}

	public static ISicresAPerms getBookAPerms(String sessionID, Integer bookID)
			throws ValidationException, SecurityException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		ISicresAPerms aPerms = null;

		try {
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);
			THashMap bookInformation = (THashMap) cacheBag.get(bookID);

			if (userusertype == null) {
				throw new SecurityException(
						SecurityException.ERROR_IUSERUSERTYPE_NOT_FOUND);
			}

			if (bookInformation.containsKey(APERMS_USER)) {
				aPerms = (ISicresAPerms) bookInformation.get(APERMS_USER);
			} else {
				throw new SecurityException(
						SecurityException.ERROR_ISICRESAPERMS_NOT_FOUND);
			}
		} catch (TecDocException tE) {
			log.error("Impossible to obtain ISicresAPerms for the session ["
					+ sessionID + "]", tE);
			throw new SecurityException(
					SecurityException.ERROR_ISICRESAPERMS_NOT_FOUND);
		}

		return aPerms;
	}

	public static LDAPDef ldapInfo(String entidad) throws Exception {
		// Obtiene la información de configuración del LDAP
		LDAPDef result = null;
		try {
			result = RepositoryLDAP.getInstance(entidad).getLDAPInfo();
		} catch (Exception e) {
			log.error("Impossible to load LDAP information", e);
		}
		return result;
	}

	public static boolean isSuperuser(String sessionID)
			throws ValidationException, SecurityException, SessionException {

		boolean isSuperuser = false;

		try {
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			Iuserusertype userusertype = (Iuserusertype) cacheBag
					.get(HIBERNATE_Iuserusertype);

			if (userusertype.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN)
				isSuperuser = true;

		} catch (TecDocException tE) {
			log.error("Impossible to obtain ISicresAPerms for the session ["
					+ sessionID + "]", tE);
			throw new SecurityException(
					SecurityException.ERROR_ISICRESAPERMS_NOT_FOUND);
		}

		return isSuperuser;
	}

	/**
	  * Nos dice si el usuario conectado tiene permisos para ver los documentos
	  * anexos de un registro que pertenezcan a una oficina diferente a la suya
	  */
	 public static boolean permisionShowDocuments(String sessionID, AxSf axsf)
				throws SessionException, BookException {
			boolean result = true;
			try {
				// Recuperamos la sesión
				CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
						sessionID);
				ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag
						.get(GENPERMS_USER);

				ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
				if (axsf.getAttributeValueAsString(AxSf.FLD5_FIELD) != null
						&& scrofic != null) {
					Integer idOficReg = new Integer(axsf
							.getAttributeValueAsString(AxSf.FLD5_FIELD));
					Integer idOficUser = scrofic.getId();
					if (!genPerms.isCanShowDocuments()) {
						if (idOficReg.intValue() != idOficUser.intValue()) {
							result = false;
						}
					}
				}
			} catch (SessionException sE) {
				throw sE;
			} catch (Exception e) {
			}
			return result;
	 }

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}