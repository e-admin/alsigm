/**
 *
 */
package com.ieci.tecdoc.isicres.session.security;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;
import com.ieci.tecdoc.common.invesicres.ScrUsrperm;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.utils.ConfiguratorUser;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.security.policies.AuthenticationFactory;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class SecuritySessionUtil implements HibernateKeys, ServerKeys {

	private static final Logger log = Logger
			.getLogger(SecuritySessionUtil.class);

	protected static AuthenticationUser validateUser(String login,
			String password, String userDn, Boolean useLdap,
			Boolean usingOSAuth, String entidad) throws SecurityException,
			ValidationException, TecDocException {
		AuthenticationUser user = null;
		if (useLdap.booleanValue() && usingOSAuth.booleanValue()) {
			user = AuthenticationFactory.getCurrentPolicy().validate(userDn,
					entidad);
			if (log.isDebugEnabled()) {
				log.debug("Validando usuario [" + userDn + "] con el log ["
						+ log + "]");
			}
		} else {
			user = AuthenticationFactory.getCurrentPolicy().validate(login,
					password, entidad);
			if (log.isDebugEnabled()) {
				log.debug("Validando usuario [" + login + "] con el log ["
						+ log + "]");
			}
		}

		return user;
	}

	protected static ScrOfic checkScrOficPref(Session session,
			AuthenticationUser user, String language, String entidad)
			throws HibernateException, Exception {
		ConfiguratorUser configuratorUser = new ConfiguratorUser();
		UserConf usrConf = configuratorUser.getUsrConf(user, entidad);
		ScrOfic scrofic = null;

		if (usrConf != null && usrConf.getIdOficPref() != 0) {
			boolean clearIdOficPref = true;
			if (user.getDeptList() != null && user.getDeptList().size() > 0) {
				scrofic = ISicresQueries.getScrOficById(session, new Integer(
						usrConf.getIdOficPref()));
				if (scrofic != null) {
					// Si la oficina preferente del usuario está entre su
					// lista de oficinas, ésta es la que le asignamos
					if (user.getDeptList().contains(
							new Integer(scrofic.getDeptid()))) {
						user.setDeptid(new Integer(scrofic.getDeptid()));
						clearIdOficPref = false;
					}
				}
			}
			if (clearIdOficPref) {
				BookSession.saveUserConfigIdOficPref(user.getId(), null,
						entidad);
				scrofic = null;
			}
		}

		// Si la oficina no se ha rellenado aún, obtenerla a partir del
		// depto
		if (scrofic == null) {
			scrofic = ISicresQueries.getScrOficByDeptId(session, user
					.getDeptid());
			if ((scrofic != null) && (!language.equals("es"))) {
				scrofic.setName(DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getDescriptionByLocale(scrofic.getId(), false, false,
								language, EntityByLanguage.getTableName(5),
								entidad));
			}
		}

		return scrofic;
	}

	protected static Iuserusertype getIuserusertype(Session session,
			AuthenticationUser user) throws HibernateException {
		List list = null;
		Iuserusertype userType = null;

		if (user.getId().equals(new Integer(IDocKeys.IUSERUSERTYPE_ADMIN_ID))) {
			// Si es superadministrador recuperamos directamente el perfil
			// de superadministrador
			list = ISicresQueries.getUserUserType(session, user.getId()
					.intValue(), IDocKeys.IUSERUSERTYPE_INVESDOC_TYPE);
		} else {
			// Sino es superadministrador, recuperamos su perfil dentro de
			// invesicres
			list = ISicresQueries.getUserUserType(session, user.getId()
					.intValue(), IDocKeys.IUSERUSERTYPE_SICRES_TYPE);
		}

		if (list != null && list.size() > 0) {
			userType = (Iuserusertype) list.get(0);
		}

		return userType;
	}

	protected static void validateUserType(Iuserusertype userType)
			throws SecurityException {
		if (userType == null || userType.getType() == 0) {
			throw new SecurityException(SecurityException.ERROR_USER_APLICATION);
		}
	}

	protected static int[] getPerm(ScrUsrperm scrPerm) {
		int[] perm = new int[5];

		perm[0] = ISicresKeys.SCR_USRPERM_NOTHING;
		if ((scrPerm.getPerms() & 1) == 1) {
			perm[0] = ISicresKeys.SCR_USRPERM_ALL;
		}
		perm[1] = ISicresKeys.SCR_USRPERM_NOTHING;
		if ((scrPerm.getPerms() & 2) == 2) {
			perm[1] = ISicresKeys.SCR_USRPERM_ALL;
		}
		perm[2] = ISicresKeys.SCR_USRPERM_NOTHING;
		if ((scrPerm.getPerms() & 4) == 4) {
			perm[2] = ISicresKeys.SCR_USRPERM_ALL;
		}
		perm[3] = ISicresKeys.SCR_USRPERM_NOTHING;
		if ((scrPerm.getPerms() & 8) == 8) {
			perm[3] = ISicresKeys.SCR_USRPERM_ALL;
		}
		perm[4] = ISicresKeys.SCR_USRPERM_NOTHING;
		if ((scrPerm.getPerms() & 32) == 32) {
			perm[4] = ISicresKeys.SCR_USRPERM_ALL;
		}

		return perm;
	}

	// /// TODO scr_lastconnection //////////
	protected static Date getUserLastConnection(Integer userId, String entidad)
			throws HibernateException, Exception {
		Date currentDate = BBDDUtils
				.getDateFromTimestamp(DBEntityDAOFactory
						.getCurrentDBEntityDAO().getUserLastConnection(userId,
								entidad));
		return currentDate;

	}

	protected static ISicresGenPerms getGenPerms(Session session,
			Integer userId, int userType) throws HibernateException {

		ISicresGenPerms genPerms = new ISicresGenPerms();

		if (userId.equals(new Integer(IDocKeys.IUSERUSERTYPE_ADMIN_ID))
				|| userType == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
			genPerms.setCanCreatePersons(true);
			genPerms.setCanUpdatePersons(true);
			genPerms.setCanIntroRegDate(true);
			genPerms.setCanUpdateRegDate(true);
			genPerms.setCanUpdateProtectedFields(true);
			genPerms.setCanAccessRegInterchange(true);
			genPerms.setCanAcceptRegisters(true);
			genPerms.setCanRejectRegisters(true);
			genPerms.setCanArchiveRegisters(true);
			genPerms.setCanChangeDestRegisters(true);
			genPerms.setCanChangeDestRejectRegisters(true);
			genPerms.setCanDistRegisters(true);
			genPerms.setCanShowDocuments(true);
			genPerms.setCanModifyAdminUnits(true);
			genPerms.setCanModifyIssueTypes(true);
			genPerms.setCanModifyReports(true);
			genPerms.setCanModifyTransportTypes(true);
			genPerms.setCanModifyUsers(true);
			genPerms.setCanDeleteDocuments(true);
		} else {
			ScrUsrperm scrUsrperm = null;

			try {
				scrUsrperm = (ScrUsrperm) session
						.load(ScrUsrperm.class, userId);
			} catch (ObjectNotFoundException onF) {
				genPerms.setCanCreatePersons(false);
				genPerms.setCanUpdatePersons(false);
				genPerms.setCanIntroRegDate(false);
				genPerms.setCanUpdateRegDate(false);
				genPerms.setCanUpdateProtectedFields(false);
				genPerms.setCanAccessRegInterchange(false);
				genPerms.setCanAcceptRegisters(false);
				genPerms.setCanRejectRegisters(false);
				genPerms.setCanArchiveRegisters(false);
				genPerms.setCanChangeDestRegisters(false);
				genPerms.setCanChangeDestRejectRegisters(false);
				genPerms.setCanDistRegisters(false);
				genPerms.setCanShowDocuments(false);
				genPerms.setCanModifyAdminUnits(false);
				genPerms.setCanModifyIssueTypes(false);
				genPerms.setCanModifyReports(false);
				genPerms.setCanModifyTransportTypes(false);
				genPerms.setCanModifyUsers(false);
				genPerms.setCanDeleteDocuments(false);
			}

			if (scrUsrperm != null) {
				genPerms
						.setCanCreatePersons((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_CREATE_INTERESTED) == ISicresGenPerms.ISUSER_PERM_CAN_CREATE_INTERESTED);
				genPerms
						.setCanUpdatePersons((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_INTERESTED) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_INTERESTED);
				genPerms
						.setCanIntroRegDate((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_SET_DATEREG) == ISicresGenPerms.ISUSER_PERM_CAN_SET_DATEREG);
				genPerms
						.setCanUpdateRegDate((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_DATEREG) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_DATEREG);
				genPerms
						.setCanUpdateProtectedFields((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_PROTECTEDFIELDS);
				genPerms
						.setCanAccessRegInterchange((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE) == ISicresGenPerms.ISUSER_PERM_CAN_ACCESS_REGINTERCHANGE);
				genPerms
						.setCanAcceptRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_ACCEPT_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_ACCEPT_REGISTERS);
				genPerms
						.setCanRejectRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_REJECT_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_REJECT_REGISTERS);
				genPerms
						.setCanArchiveRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_ARCHIVE_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_ARCHIVE_REGISTERS);
				genPerms
						.setCanChangeDestRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REGISTERS);
				genPerms
						.setCanChangeDestRejectRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REJECT_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_CHANGE_DEST_REJECT_REGISTERS);
				genPerms
						.setCanDistRegisters((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_DIST_REGISTERS) == ISicresGenPerms.ISUSER_PERM_CAN_DIST_REGISTERS);
				genPerms
						.setCanShowDocuments((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_SHOW_DOCUMENTS) == ISicresGenPerms.ISUSER_PERM_CAN_SHOW_DOCUMENTS);


				genPerms
				.setCanModifyAdminUnits((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ADMINUNITS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ADMINUNITS);
				genPerms
				.setCanModifyIssueTypes((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ISSUETYPES) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_ISSUETYPES);

				genPerms
				.setCanModifyReports((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_REPORTS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_REPORTS);

				genPerms
				.setCanModifyTransportTypes((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_TRANSPORTTYPES);

				genPerms
				.setCanModifyUsers((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_USERS) == ISicresGenPerms.ISUSER_PERM_CAN_MODIFY_USERS);

				genPerms
						.setCanDeleteDocuments((scrUsrperm.getPerms() & ISicresGenPerms.ISUSER_PERM_CAN_DELETE_DOCUMENTS) == ISicresGenPerms.ISUSER_PERM_CAN_DELETE_DOCUMENTS);

			}
		}

		return genPerms;
	}

	protected static ScrTmzofic getScrTmzofic(Session session, Integer oficId)
			throws HibernateException {
		ScrTmzofic scrTmzofic = null;
		try {
			scrTmzofic = (ScrTmzofic) session.load(ScrTmzofic.class, oficId);
		} catch (ObjectNotFoundException onF) {
			// Puede ser que una oficina no tenga cambios horarios
		}
		return scrTmzofic;
	}

	/**
	 * Método que almacena en cache los datos relacionados con el usuario,
	 * oficina, permisos de usuario, nombre de oficina, datos de la última
	 * conexión del usuario, zona horaria de la oficina...
	 *
	 * @param sessionID
	 *            - ID de session
	 * @param user
	 *            - Datos de usuario
	 * @param scrofic
	 *            - Datos de la oficina
	 * @param scrtmzofic
	 *            - Datos horarios de la oficina
	 * @param genPerms
	 *            - Permisos del usuario
	 * @param userType
	 *            - Tipo de usuario
	 * @param userLastConnection
	 *            - Datos de ultima conexión del usuario
	 * @param oficName
	 *            - Nombre de oficina
	 *
	 * @throws SessionException
	 * @throws TecDocException
	 */
	protected static void sessionPutObjects(String sessionID,
			AuthenticationUser user, ScrOfic scrofic, ScrTmzofic scrtmzofic,
			ISicresGenPerms genPerms, Iuserusertype userType,
			Date userLastConnection, String oficName) throws SessionException,
			TecDocException {

		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
				sessionID);

		cacheBag.put(HIBERNATE_Iuseruserhdr, user);
		cacheBag.put(HIBERNATE_ScrOfic, scrofic);
		cacheBag.put(GENPERMS_USER, genPerms);
		cacheBag.put(OFIC_NAME, oficName);

		// /// TODO scr_lastconnection /////
		cacheBag.put(LAST_CONNECTION_USER, userLastConnection);

		if (scrtmzofic != null) {
			cacheBag.put(HIBERNATE_ScrTmzofic, scrtmzofic);
		}
		cacheBag.put(HIBERNATE_Iuserusertype, userType);
	}

	/**
	 * Método que obtiene los datos de la oficina indicada por su código, si no se indica código retorna null
	 *
	 * @param session - Session
	 * @param userId - Id del usuario
	 * @param language - Idioma
	 * @param codigoOficina - Código de la oficina
	 *
	 * @return ScrOfic - Datos de la oficina
	 */
	protected static ScrOfic getScrOficByCode(Session session,
			String language, String codigoOficina) {

		if (StringUtils.isBlank(codigoOficina)) {
			return null;
		}

		try {
			String sqlUsrOfic = "code = '";
			StringBuffer queryUsrOfic = new StringBuffer();
			queryUsrOfic.append(sqlUsrOfic);
			queryUsrOfic.append(codigoOficina);
			queryUsrOfic.append("'");

			Criteria criteriaResults = session.createCriteria(EntityByLanguage
					.getScrOficLanguage(language));
			criteriaResults.add(Expression.sql(queryUsrOfic.toString()));
			List listaOficinasUsuario = criteriaResults.list();

			if (listaOficinasUsuario != null && !listaOficinasUsuario.isEmpty()) {
				for (Iterator iterator = listaOficinasUsuario.iterator(); iterator
						.hasNext();) {
					ScrOfic scrofic = (ScrOfic) iterator.next();

					if (scrofic.getCode().equalsIgnoreCase(codigoOficina)) {
						return scrofic;
					}
				}
			}
		} catch (HibernateException e) {
			log.error("Error intentando recuperar la oficina con el codigo: "+codigoOficina);
			return null;
		}

		return null;
	}

}
