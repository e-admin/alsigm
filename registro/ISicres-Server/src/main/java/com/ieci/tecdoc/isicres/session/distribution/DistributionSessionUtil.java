/**
 *
 */
package com.ieci.tecdoc.isicres.session.distribution;

import gnu.trove.THashMap;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.BookConf;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.entity.AxDochEntity;
import com.ieci.tecdoc.common.entity.AxPagehEntity;
import com.ieci.tecdoc.common.entity.AxXnidEntity;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Idocfdrstat;
import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;
import com.ieci.tecdoc.common.invesdoc.Iusergrouphdr;
import com.ieci.tecdoc.common.invesdoc.Iusergroupuser;
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;
import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrUsrIdent;
import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPKById;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.DistributionResults;
import com.ieci.tecdoc.common.keys.ConfigurationEventsKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.repository.helper.ISRepositoryDocumentHelper;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositorySignDocumentVO;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.ConfiguratorEvents;
import com.ieci.tecdoc.common.utils.EntityByLanguage;
import com.ieci.tecdoc.common.utils.ISDistribution;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ISicresSaveQueries;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.common.utils.ScrRegisterInter;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.MiscFormat;
import com.ieci.tecdoc.idoc.flushfdr.FlushFdrInter;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.idoc.utils.CryptoUtils;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.isicres.repository.RepositoryFactory;
import com.ieci.tecdoc.isicres.session.events.EventsSession;
import com.ieci.tecdoc.isicres.session.folder.FolderFileSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.folder.FolderSessionUtil;
import com.ieci.tecdoc.isicres.session.utils.UtilsSession;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 *
 */
public class DistributionSessionUtil extends UtilsSession {

	private static final Logger log = Logger
			.getLogger(DistributionSessionUtil.class);

	protected static void lockListFolderDist(Session session, Integer bookId,
			List listIdsRegister, AuthenticationUser user, String entidad)
			throws HibernateException, BookException, Exception {
		for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
			Integer folderID = (Integer) it.next();
			if (!lockFolderDist(session, user, bookId, folderID.intValue(),
					entidad)) {
				throw new BookException(BookException.ERROR_CANNOT_LOCK_FOLDER);
			}
		}
	}

	/**
	 * Bloquea el registro que se pasa como parametro
	 *
	 * @param session
	 * @param user
	 * @param bookID
	 * @param fdrid
	 * @param entidad
	 * @return true ha sido bloqueada false ya estaba bloqueada
	 * @throws HibernateException
	 * @throws Exception
	 */
	protected static boolean lockFolderDist(Session session,
			AuthenticationUser user, Integer bookID, int fdrid, String entidad)
			throws HibernateException, Exception {

		boolean result = false;
		List list = ISicresQueries.getIdocfdrstat(session, bookID, fdrid);
		Timestamp currentDate = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDBServerDate(entidad);

		if (log.isDebugEnabled())
			log.info("Libro [" + bookID + "] Carpeta [" + fdrid + "] Usuario ["
					+ user.getId() + "]");

		if (list != null && !list.isEmpty()) {
			// mirar si bloqueada por otro usuario
			Idocfdrstat idoc = (Idocfdrstat) list.get(0);
			if (idoc.getUserid() != user.getId().intValue()
					&& idoc.getStat() == 1) {
				// bloqueada por otro usuario
				result = false;
				if (log.isDebugEnabled())
					log.info("Bloqueada por otro usuario.");
			} else {
				// bloqueada por este mismo usuario
				result = true;
				if (log.isDebugEnabled())
					log.info("Ya bloqueada por este usuario.");
			}
		} else {
			ISicresSaveQueries.saveIDocFdrStat(session, bookID, fdrid, user,
					BBDDUtils.getDate(currentDate));
			result = true;

			if (log.isDebugEnabled())
				log.info("Bloqueo producido.");
		}

		return result;
	}

	protected static void lockScrDistReg(List ids, String entidad)
			throws SQLException, Exception {
		Integer id = null;
		for (Iterator it = ids.iterator(); it.hasNext();) {
			id = (Integer) it.next();
			DBEntityDAOFactory.getCurrentDBEntityDAO().lockScrDistReg(
					id.intValue(), entidad);
		}
	}

	protected static List lockFolderDistById(Session session,
			AuthenticationUser user, List ids, Map distIds, List archidFdr,
			boolean isLockScrDistReg, String entidad)
			throws DistributionException, HibernateException, SQLException,
			Exception {
		TreeMap treemap = new TreeMap();
		treemap.putAll(distIds);
		for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
			Integer distId = (Integer) it.next();
			Map aux = (Map) treemap.get(distId);
			String id = (String) aux.keySet().iterator().next();
			ScrDistreg distReg = (ScrDistreg) aux.get(id);
			if (ids.contains(distReg.getId())) {
				archidFdr.add(distReg);
				if (!lockFolderDist(session, user,
						new Integer(distReg.getIdArch()), distReg.getIdFdr(),
						entidad)) {
					throw new DistributionException(
							DistributionException.ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER);
				}
			}
		}

		if (isLockScrDistReg) {
			lockScrDistReg(ids, entidad);
		}

		return archidFdr;
	}

	protected static String createDistribution(Session session,
			String sessionID, Integer bookId, Integer senderType,
			Integer senderId, Integer userId, Integer userType,
			String messageForUser, List listIdsRegister,
			AuthenticationUser user, ScrOfic scrOfic, Locale locale,
			String entidad, Integer idDistFather) throws ValidationException, SessionException,
			DistributionException, HibernateException, BookException,
			SQLException, Exception {

		StringBuffer notDistributedRegisters = new StringBuffer();
		StringBuffer distributedRegisters = new StringBuffer();
		String field = null;

		for (Iterator it = listIdsRegister.iterator(); it.hasNext();) {
			Integer folderID = (Integer) it.next();
			AxSf axsf = FolderSessionUtil.getAxSf(session, bookId, folderID,
					null, locale.getLanguage(), entidad, false);

			if (isPropOrg(session, axsf)) {
				Object object = axsf.getAttributeValue("fld6");
				int stateReg = 0;
				boolean isValidObject = false;
				if (object != null && object instanceof BigDecimal) {
					stateReg = ((BigDecimal) object).intValue();
					isValidObject = true;
				} else if (object != null && object instanceof Integer) {
					stateReg = ((Integer) object).intValue();
					isValidObject = true;
				}

				if (isValidObject) {
					String[] resultArray = createDistribute(session, user,
							sessionID, bookId, folderID, senderType, senderId,
							userId, userType, scrOfic, stateReg,
							messageForUser, axsf, entidad, idDistFather);

					field = resultArray[0];
					notDistributedRegisters.append(resultArray[1]);
					distributedRegisters.append(resultArray[2]);
				}
			} else {
				notDistributedRegisters.append(axsf.getAttributeValue("fld1"));
				notDistributedRegisters.append(";");
			}
		}

		return createDistributionResponse(field,
				distributedRegisters.toString(),
				notDistributedRegisters.toString());
	}

	protected static List getUserGroups(Session session,
			AuthenticationUser user, boolean isLdap) throws HibernateException {
		List groupIds = new ArrayList();
		if (isLdap) {
			groupIds = user.getGroupList();
		} else {
			List iUserGroupUser = ISicresQueries.getIUserGroupUser(session,
					user.getId());
			if (iUserGroupUser != null && iUserGroupUser.size() > 0) {
				for (Iterator it = iUserGroupUser.listIterator(); it.hasNext();) {
					Iusergroupuser iuserGroup = (Iusergroupuser) it.next();
					groupIds.add(new Integer(iuserGroup.getGroupid()));
				}
			}
		}
		return groupIds;
	}

	/**
	 * Nombre de la tabla temporal para obtener la información de la distribución
	 * @param user - {@link AuthenticationUser}
	 * @param idArchs - Listado de libros
	 * @return Nombre de la tabla temporal
	 */
	protected static String getTableName(AuthenticationUser user, List idArchs) {
		String tableName = null;

		if (idArchs != null && !idArchs.isEmpty()) {
			tableName = " SCR_TMPREG" + user.getId() + " ";
		}

		return tableName;
	}

	protected static StringBuffer getDistributionQuerySize(Session session,
			AuthenticationUser user, List iUserGroupUser, int typeDist,
			String distWhere, List bookList, boolean oficAsoc, String entidad)
			throws DistributionException, HibernateException {
		StringBuffer querySize = new StringBuffer();

		String processDistWhere = "";
		if (distWhere != null && !distWhere.equals("")) {
			processDistWhere = getDistWhere(distWhere, typeDist);
		}

		InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
				entidad).getInvesicresConf();
		int autoArchiv = invesicresConf.getAutoArchiving();

		switch (typeDist) {
		case DISTRIBUCION_IN_DIST: {
			querySize.append(getInDistQuery(user, iUserGroupUser, bookList,
					oficAsoc));

			if (autoArchiv != 0) {
				try {
					autoArchiving(session, querySize.toString(), autoArchiv,
							user, entidad);
				} catch (Exception e) {
					throw new DistributionException(
							DistributionException.ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED);
				}
			}
			if (!processDistWhere.equals("")) {
				querySize.append("and " + processDistWhere);
			}
			break;
		}
		case DISTRIBUCION_OUT_DIST: {
			querySize.append(getOutDistQuery(user, iUserGroupUser, bookList,
					oficAsoc));

			if (!processDistWhere.equals("")) {
				querySize.append("and " + processDistWhere);
			}

			break;
		}
		default:
			throw new DistributionException(
					DistributionException.ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED);
		}

		return querySize;
	}

	/**
	 * Método que crea la tabla temporal de distribución a partir de los libros
	 * y la consulta realizada
	 *
	 * @param idArchs - Listado de libros
	 * @param regWhere - Consulta a realizar
	 * @param querySize - Parte de la consulta a realizar
	 * @param tableName - Nombre de la tabla temporal
	 * @param entidad - Entidad
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws Exception
	 */
	protected static String getDistributionFinalWhere(List idArchs,
			String regWhere, String querySize, String tableName, String entidad)
			throws HibernateException, SQLException, Exception {
		StringBuffer finalWhere = new StringBuffer();

		//Si el listado de libros es distinto de vacio
		if (idArchs != null && !idArchs.isEmpty()) {
			StringBuffer createSentence = new StringBuffer();
			Integer auxBookId = null;
			int index = 0;
			for (Iterator it = idArchs.iterator(); it.hasNext();) {
				auxBookId = (Integer) it.next();
				boolean isInBook = Repository.getInstance(entidad)
						.isInBook(auxBookId).booleanValue();

				String processRegWhere = getRegWhere(regWhere, isInBook);

				createSentence.append(DBEntityDAOFactory
						.getCurrentDBEntityDAO()
						.getTemporalTableDistributionQuerySentence(tableName,
								auxBookId, processRegWhere, index));
				index++;
			}

			// Se crea la tabla temporal a partir de los datos de los registros
			// en los diferentes libros que han sio distribuidos
			DBEntityDAOFactory.getCurrentDBEntityDAO().createTableOrView(
					createSentence.toString(), entidad);

			finalWhere.append(querySize.toString());
			finalWhere.append(" and id_fdr in (select fdrid from ");
			finalWhere.append(tableName);
			finalWhere.append(" where bookid = id_arch)");
		} else {
			//No hay libros, por tanto la consulta se realiza sobre la tabla SCR_DISTREG
			finalWhere.append(querySize.toString());
		}
		finalWhere.append(" order by id");

		return finalWhere.toString();
	}

	protected static int getDistributionCount(String finalWhere, String entidad)
			throws SQLException, Exception {
		StringBuffer selectCount = new StringBuffer();
		selectCount.append("select count(*) from scr_distreg where ");
		if (finalWhere.indexOf("order") != -1) {
			selectCount.append(finalWhere.substring(0,
					finalWhere.indexOf("order")));
		} else {
			selectCount.append(finalWhere);
		}
		int distributionCount = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDistributionSize(selectCount.toString(), entidad);

		if (log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("getDistributionCount distributionCount: ").append(distributionCount);
			log.debug(sb.toString());
		}

		return distributionCount;
	}

	protected static DistributionResults getDistributionResults(
			Session session, List list, int distributionCount,
			Date currentDate, int typeDist, Locale locale, String entidad,
			boolean isLdap) throws HibernateException {
		DistributionResults distributionResults = new DistributionResults();

		distributionResults.setTotalSize(distributionCount);
		distributionResults.setActualDate(currentDate);

		Map result = new HashMap();
		Map distType = new HashMap();
		if (distributionResults.getTotalSize() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				ScrDistreg distReg = (ScrDistreg) it.next();

				Map aux = new HashMap();
				result.put(distReg.getId(), aux);

				distType.put(
						distReg.getId(),
						getDistributionResultDistType(session, distReg, entidad));
				String id = getDistributionResultId(distReg, typeDist, entidad,
						isLdap);

				if (!id.equals("")) {
					aux.put(id, distReg);

					distributionResults.getBooks().add(
							distReg.getIdArch() + "_" + distReg.getIdFdr());

					Integer auxID = new Integer(distReg.getIdArch());
					if (!distributionResults.getIdocarchhdr()
							.containsKey(auxID)) {
						// distributionResults.getIdocarchhdr().put(auxID,
						// session.load(Idocarchhdr.class, auxID));
						distributionResults.getIdocarchhdr().put(
								auxID,
								session.load(EntityByLanguage
										.getIdocarchhdrLanguage(locale
												.getLanguage()), auxID));
					}
				} else {
					result.remove(distReg.getId());
					distType.remove(distReg.getId());
					distributionResults.setTotalSize(distributionResults
							.getTotalSize() - 1);
				}
			}
		}
		distributionResults.setResults(result);
		distributionResults.setDistType(distType);

		if (log.isDebugEnabled()) {
			log.debug("Recuperada una lista de distribucion [" + result.size()
					+ "/" + distributionResults.getTotalSize() + "].");
		}

		return distributionResults;
	}

	/**
	 * Método que adapta los datos del listado de objetos {@link ScrDistreg} al
	 * objeto {@link DistributionResults} en caso de indicar la ordenación en la
	 * distribución
	 *
	 * @param session
	 * @param list
	 * @param distributionCount
	 * @param currentDate
	 * @param typeDist
	 * @param locale
	 * @param entidad
	 * @param isLdap
	 * @return {@link DistributionResults}
	 *
	 * @throws HibernateException
	 */
	protected static DistributionResults getDistributionResultsOrderBy(
			Session session, List list, int distributionCount,
			Date currentDate, int typeDist, Locale locale, String entidad,
			boolean isLdap) throws HibernateException {
		DistributionResults distributionResults = new DistributionResults();

		distributionResults.setTotalSize(distributionCount);
		distributionResults.setActualDate(currentDate);

		Map result = new HashMap();
		Map distType = new HashMap();
		int positionElement = 0;
		if (distributionResults.getTotalSize() > 0) {
			for (Iterator it = list.iterator(); it.hasNext();) {

				ScrDistreg distReg = (ScrDistreg) it.next();

				Map aux = new HashMap();
				result.put(positionElement, aux);

				distType.put(
						distReg.getId(),
						getDistributionResultDistType(session, distReg, entidad));
				String id = getDistributionResultId(distReg, typeDist, entidad,
						isLdap);

				positionElement++;
				if (!id.equals("")) {
					aux.put(id, distReg);

					distributionResults.getBooks().add(
							distReg.getIdArch() + "_" + distReg.getIdFdr());

					Integer auxID = new Integer(distReg.getIdArch());
					if (!distributionResults.getIdocarchhdr()
							.containsKey(auxID)) {
						// distributionResults.getIdocarchhdr().put(auxID,
						// session.load(Idocarchhdr.class, auxID));
						distributionResults.getIdocarchhdr().put(
								auxID,
								session.load(EntityByLanguage
										.getIdocarchhdrLanguage(locale
												.getLanguage()), auxID));
					}
				} else {
					result.remove(positionElement);
					distType.remove(distReg.getId());
					distributionResults.setTotalSize(distributionResults
							.getTotalSize() - 1);
					positionElement--;
				}
			}
		}
		distributionResults.setResults(result);
		distributionResults.setDistType(distType);

		if (log.isDebugEnabled()) {
			log.debug("Recuperada una lista de distribucion [" + result.size()
					+ "/" + distributionResults.getTotalSize() + "].");
		}

		return distributionResults;
	}

	/**
	 * Metodo que lanza la consulta para obtener el numero de registros
	 * distribuidos segun su estado, el usuario, los grupos a los que pertenece
	 * el usuario y su departamento
	 *
	 * @param sessionID
	 * @param entidad
	 * @param state
	 * @param isLdap
	 * @return
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	protected static int getSizeFolderDistByStateByDeptId(String sessionID,
			String entidad, int state, boolean isLdap)
			throws DistributionException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		int size = 0;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			List iUserGroupUser = getUserGroups(session, user, isLdap);

			Date dateUserLastConnection = (Date) cacheBag
					.get(LAST_CONNECTION_USER);

			String querySize = getFolderDistQuerySizeByStateAndByDeptId(user,
					iUserGroupUser, dateUserLastConnection, state);

			size = ((Integer) session.iterate(querySize).next()).intValue();

			HibernateUtil.commitTransaction(tran);
			return size;
		} catch (DistributionException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to obtain the distribution for the session ["
					+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static int getSizeFolderDistByState(String sessionID,
			String entidad, int state, boolean isLdap)
			throws DistributionException, SessionException, ValidationException {
		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		int size = 0;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			List iUserGroupUser = getUserGroups(session, user, isLdap);

			Date dateUserLastConnection = (Date) cacheBag
					.get(LAST_CONNECTION_USER);

			String querySize = getFolderDistQuerySizeByState(user,
					iUserGroupUser, dateUserLastConnection, state);

			size = ((Integer) session.iterate(querySize).next()).intValue();

			HibernateUtil.commitTransaction(tran);
			return size;
		} catch (DistributionException e) {
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (SessionException sE) {
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to obtain the distribution for the session ["
					+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static void unlockDistRegs(Session session, Transaction tran,
			AuthenticationUser user, List archidFdr) throws HibernateException,
			DistributionException {
		if (archidFdr != null && !archidFdr.isEmpty()) {
			for (Iterator it4 = archidFdr.iterator(); it4.hasNext();) {
				ScrDistreg distReg = (ScrDistreg) it4.next();
				unlock(session, new Integer(distReg.getIdArch()),
						distReg.getIdFdr(), user);
			}
		}
		HibernateUtil.commitTransaction(tran);
		throw new DistributionException(
				DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_IN_STATE);
	}

	protected static void unlockDistRegsById(String sessionID, List list,
			String entidad) throws BookException {
		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			for (Iterator it = list.iterator(); it.hasNext();) {
				Integer id = (Integer) it.next();
				ScrDistreg scrDistReg = (ScrDistreg) session.load(
						ScrDistreg.class, id);

				unlock(session, new Integer(scrDistReg.getIdArch()),
						scrDistReg.getIdFdr(), user);
			}

			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	protected static ScrOrg getScrOrg(Session session,
			Iuserusertype userusertype, ScrOfic scrOfic, String code,
			String entidad) throws BookException, HibernateException,
			SQLException, Exception {

		ScrOrg scrOrg = null;

		List privOrgs = null;
		if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
			privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO().getPrivOrgs(
					scrOfic.getId().intValue(), entidad);
		}

		try {
			scrOrg = ISUnitsValidator.getUnit(session, code, true, privOrgs);
		} catch (ValidationException e) {
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION_INVALIDCODE);
		}

		if (log.isDebugEnabled()) {
			log.debug("changeDistribution =========> + " + scrOrg);
		}

		return scrOrg;
	}

	protected static void closeDistRegs(String sessionID, List list, int error,
			String entidad) throws BookException, SessionException,
			ValidationException {
		if (list != null && !list.isEmpty() && error == 1) {
			for (Iterator it4 = list.iterator(); it4.hasNext();) {
				ScrDistreg distReg = (ScrDistreg) it4.next();
				FolderSession.closeFolderFromDistribution(sessionID,
						new Integer(distReg.getIdArch()), distReg.getIdFdr(),
						entidad);
			}
		}
	}

	protected static ScrDistreg updateDistReg(Session session,
			AuthenticationUser user, String sessionID, String event,
			ScrDistreg scrDistReg, int distState, Integer ids, String remarks,
			String entidad) throws DistributionException, SessionException,
			ValidationException, HibernateException, SQLException, Exception {
		if (event != null) {
			String eventsManagerClassName = ConfiguratorEvents.getInstance(
					event).getProperty(
					ConfigurationEventsKeys.KEY_EVENTS_IMPLEMENTATION);
			if (eventsManagerClassName != null
					&& !eventsManagerClassName.equals("")) {

				if (DISTRIBUTION_ACCEPT_EVENT.equals(event)) {
					EventsSession.distAceptEvent(sessionID, event, scrDistReg,
							entidad);
				} else if (DISTRIBUTION_ARCHIVE_EVENT.equals(event)) {
					EventsSession.distArchiveEvent(sessionID, event,
							scrDistReg, entidad);
				} else if (DISTRIBUTION_REJECT_EVENT.equals(event)) {
					EventsSession.distRejectEvent(sessionID, event, scrDistReg,
							entidad);
				} else if (DISTRIBUTION_REDISTRIBUTE_EVENT.equals(event)) {
					EventsSession.distDestChangeEvent(sessionID, event,
							scrDistReg, entidad);
				} else {
					throw new EventException(
							EventException.ERROR_EVENT_NOT_SUPPORTED);
				}
			}
		}

		Date currentDate = new Date(DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDBServerDate(entidad).getTime());
		boolean isDBCaseSensitive = isDataBaseCaseSensitive(entidad);
		scrDistReg.setState(distState);
		scrDistReg.setStateDate(currentDate);
		if (remarks != null) {
			if (isDBCaseSensitive) {
				scrDistReg.setMessage(remarks.toUpperCase());
			} else {
				scrDistReg.setMessage(remarks);
			}
		}
		session.update(scrDistReg);

		ISDistribution iSDist = new ISDistribution();
		iSDist.setDistState(session, ids.intValue(), distState, currentDate,
				user.getName(), user.getId(), entidad, remarks, isDBCaseSensitive);

		return scrDistReg;
	}

	protected static void updateAcceptDistribution(String sessionID, List ids,
			List archidFdr, String entidad) throws BookException,
			SessionException, ValidationException, DistributionException {
		Transaction tran = null;
		int error = 0;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			Date currentDate = new Date(DBEntityDAOFactory
					.getCurrentDBEntityDAO().getDBServerDate(entidad).getTime());
			for (Iterator it = ids.iterator(); it.hasNext();) {
				Integer ids1 = (Integer) it.next();
				ScrDistreg scrDistReg = (ScrDistreg) session.load(
						ScrDistreg.class, ids1);
				if (scrDistReg.getState() != ISDistribution.STATE_PENDIENTE) {
					throw new DistributionException(
							DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_IN_STATE);
				} else {

					scrDistReg = updateDistReg(session, user, sessionID,
							DISTRIBUTION_ACCEPT_EVENT, scrDistReg,
							ISDistribution.STATE_ACEPTADO, ids1, null, entidad);

					updateInsertDistAccept(scrOfic, scrDistReg, currentDate, 0,
							user.getName(), entidad);

				}
			}

			HibernateUtil.commitTransaction(tran);

		} catch (DistributionException e) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (SessionException sE) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (EventException eE) {
			HibernateUtil.rollbackTransaction(tran);
			throw eE;
		} catch (Exception e) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to accept the distribution for the session ["
					+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
			closeDistRegs(sessionID, archidFdr, error, entidad);
		}
	}

	/**
	 * Metodo que actualiza una distribucion
	 *
	 * @param sessionID
	 * @param scrs
	 *            - Lista de ScrDistReg
	 * @param distList
	 *            - Lista con los id del destino de la distribucion
	 * @param typeDist
	 *            - Tipo de distribucion
	 * @param id
	 *            - Id de la distribucion
	 * @param canDestWithoutList
	 * @param entidad
	 * @throws DistributionException
	 * @throws BookException
	 * @throws SessionException
	 * @throws ValidationException
	 */
	protected static void updateChangeDistribution(String sessionID, List scrs,
			List distList, int typeDist, Integer id,
			Integer canDestWithoutList, String entidad)
			throws DistributionException, BookException, SessionException,
			ValidationException {
		Transaction tran = null;
		int error = 0;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			Date currentDate = new Date(DBEntityDAOFactory
					.getCurrentDBEntityDAO().getDBServerDate(entidad).getTime());
			for (Iterator it = scrs.iterator(); it.hasNext();) {
				ScrDistreg scrDistReg = (ScrDistreg) it.next();


				scrDistReg = updateDistRegByTypeFromChangeDistribution(session,
						user, sessionID, scrDistReg, typeDist, distList,
						canDestWithoutList, id, entidad, null);

				int state = -1;
				if ((distList == null || distList.isEmpty())
						&& canDestWithoutList.intValue() == 1) {
					state = 0;
				} else {
					state = 1;
				}
				updateInsertDistAccept(scrOfic, scrDistReg, currentDate, state,
						user.getName(), entidad);
			}
			HibernateUtil.commitTransaction(tran);
		} catch (DistributionException dE) {
			error = 1;
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", dE);
			HibernateUtil.rollbackTransaction(tran);
			throw dE;
		} catch (EventException eE) {
			error = 1;
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", eE);
			HibernateUtil.rollbackTransaction(tran);
			throw eE;
		} catch (Exception e) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
			closeDistRegs(sessionID, scrs, error, entidad);
		}
	}

	protected static void updateFolderChangeDistribution(String sessionID,
			List scrs, ScrOrg scrOrg, Integer launchDistOutRegister,
			Locale locale, String entidad) throws BookException,
			SessionException, ValidationException {
		int error = 0;
		try {

			for (Iterator it = scrs.iterator(); it.hasNext();) {
				ScrDistreg scrDistReg = (ScrDistreg) it.next();

				AxSf axsf = new AxSf();
				axsf.setAttributeValue("fld8", scrOrg.getId());
				FolderSession.updateFolderEx(sessionID,
						new Integer(scrDistReg.getIdArch()),
						scrDistReg.getIdFdr(), axsf, null, null, false,
						launchDistOutRegister, locale, entidad, scrDistReg.getId());

				if (log.isDebugEnabled()) {
					log.debug("changeDistribution =========> + " + axsf);
				}
			}
		} catch (BookException bE) {
			error = 1;
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", bE);
			throw bE;
		} catch (SessionException sE) {
			error = 1;
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", sE);
			throw sE;
		} catch (EventException eE) {
			error = 1;
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", eE);
			throw eE;
		} catch (Exception e) {
			error = 1;
			log.error("Impossible to update the distribution for the session ["
					+ sessionID + "].", e);
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		} finally {
			closeDistRegs(sessionID, scrs, error, entidad);
		}
	}

	/**
	 * Para cada distribución indica en un mapa si el registro asociado es de
	 * entrada o de salida.
	 *
	 * Si el destino de la distribución es un departamento hay que comprobar que
	 * la oficina de destino no sea la misma que la de origen. Si fuera la misma
	 * se elimina del mapa para que no se cree un nuevo registro.
	 *
	 * Si el destino de la distribución es un usuario o un grupo hay que
	 * comprobar que la oficina del usuario actualmente selecionada no sea la
	 * misma que la de origen. Si fuera la misma se elimina del mapa para que no
	 * se cree un nuevo registro.
	 *
	 *
	 * @param session
	 * @param entidad
	 * @param ids
	 * @param distIds
	 * @param outputInputBookType
	 * @return
	 * @throws HibernateException
	 */
	protected static Map getOutputInputBookType(Session session,
			String entidad, List ids, Map distIds, Map outputInputBookType,
			AuthenticationUser user) throws HibernateException {

		TreeMap treemap = new TreeMap();
		treemap.putAll(distIds);
		for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
			Integer distId = (Integer) it.next();
			Map aux = (Map) treemap.get(distId);
			String id = (String) aux.keySet().iterator().next();
			ScrDistreg distReg = (ScrDistreg) aux.get(id);
			boolean isOutBook = Repository.getInstance(entidad)
					.isOutBook(new Integer(distReg.getIdArch())).booleanValue();
			if (ids.contains(distReg.getId()) && isOutBook) {
				outputInputBookType
						.put(distReg.getId(), new Boolean(isOutBook));
			} else if (ids.contains(distReg.getId()) && !isOutBook) {
				// Si el destino es una oficina hay que comprobar que no sea la
				// misma oficina que la del origen de la distribución
				if (distReg.getTypeDest() == 2) {
				ScrOfic scrOficIdOrig = ISicresQueries.getScrOficByDeptId(
						session, new Integer(distReg.getIdOrig()));
				ScrOfic scrOficIdDest = ISicresQueries.getScrOficByDeptId(
						session, new Integer(distReg.getIdDest()));
				if ((scrOficIdDest == null)
						|| (scrOficIdOrig.getScrOrg().getId().intValue() != scrOficIdDest
								.getScrOrg().getId().intValue())) {
					outputInputBookType.put(distReg.getId(), new Boolean(
							isOutBook));

						log.debug("La distribucion ["
								+ distReg.getId()
								+ "] generará un nuevo registro para la oficina ["
								+ distReg.getIdDest() + "]");
				}
					// Si el destino es un usuario o grupo hay que comprobar que
					// la
					// oficina en la que está actualmente trabajando no sea la
					// misma
					// que la oficina del origen de la distribución
				} else if (distReg.getTypeDest() == 1
						|| distReg.getTypeDest() == 3) {

					ScrOfic scrOficIdOrig = ISicresQueries.getScrOficByDeptId(
							session, new Integer(distReg.getIdOrig()));
					Integer userDeptId = user.getDeptid();
					ScrOfic scrOficIdDest = ISicresQueries.getScrOficByDeptId(
							session, userDeptId);

					if ((scrOficIdDest == null)
							|| (scrOficIdOrig.getScrOrg().getId().intValue() != scrOficIdDest
									.getScrOrg().getId().intValue())) {
						outputInputBookType.put(distReg.getId(), new Boolean(
								isOutBook));
						log.debug("La distribucion ["
								+ distReg.getId()
								+ "] generará un nuevo registro para el usuario ["
								+ distReg.getIdDest() + "]");
					}
			}
		}
		}

		return outputInputBookType;
	}

	protected static Map generateNewRegsId(Session session, Integer idBook,
			Map outputInputBookType, List ids, Integer userId, String entidad)
			throws SQLException, Exception {
		Map newRegisterIdOutInBook = new HashMap();

		if (!outputInputBookType.isEmpty()) {
			for (Iterator it = ids.iterator(); it.hasNext();) {
				Integer id = (Integer) it.next();
				if (outputInputBookType.containsKey(id)) {
					int newRegisterID = DBEntityDAOFactory
							.getCurrentDBEntityDAO().getNextIdForRegister(
									idBook, entidad);
					newRegisterIdOutInBook.put(id, new Integer(newRegisterID));
					DBEntityDAOFactory.getCurrentDBEntityDAO()
							.lastRegister(new Integer(newRegisterID), userId,
									idBook, entidad);
				} else {
					ScrDistreg scrDistReg = (ScrDistreg) session.load(
							ScrDistreg.class, id);
					DBEntityDAOFactory.getCurrentDBEntityDAO().lastRegister(
							new Integer(scrDistReg.getIdFdr()), userId,
							new Integer(scrDistReg.getIdArch()), entidad);
				}
			}
		}

		return newRegisterIdOutInBook;
	}

	protected static void createNewRegister(String sessionID, Integer idBook,
			Integer launchDistOutRegister, Locale locale,
			Map newRegisterIdOutInBook, Map outputInputBookType,
			Map scrDistRegIds, Map regStates, List archidFdr, String entidad)
			throws BookException, SessionException, ValidationException,
			DistributionException {

		Transaction tran = null;
		int error = 0;
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			if (newRegisterIdOutInBook != null
					&& !newRegisterIdOutInBook.isEmpty()) {

				MiscFormat miscFormat = getMiscFormat(session, idBook,
						outputInputBookType);

				BookTypeConf bookTypeConf = bookTypeConf(1, entidad);
				BookConf bookConf = bookConf(idBook, entidad);

				Timestamp timestamp = DBEntityDAOFactory
						.getCurrentDBEntityDAO().getDBServerDate(entidad);

				for (Iterator it = scrDistRegIds.keySet().iterator(); it
						.hasNext();) {
					Integer ids1 = (Integer) it.next();
					if (newRegisterIdOutInBook.containsKey(ids1)) {
						ScrDistreg scrDistReg = (ScrDistreg) scrDistRegIds
								.get(ids1);

						int imageAuth = ((Integer) regStates.get(new Integer(
								scrDistReg.getIdArch()))).intValue();

						AxSf axsf = FolderSession.getBookFolder(sessionID,
								new Integer(scrDistReg.getIdArch()),
								scrDistReg.getIdFdr(), locale, entidad);

						AxSf newInputRegister = getNewInputRegister(session,
								axsf, entidad);

						int registerId = ((Integer) newRegisterIdOutInBook
								.get(ids1)).intValue();
						if (scrOfic != null) {

							List interList = getNewRegisterInterList(
									new Integer(scrDistReg.getIdArch()),
									scrDistReg.getIdFdr(), entidad);

							FolderSession.createNewFolderFromDistribution(
									sessionID, idBook, newInputRegister,
									interList, registerId,
									launchDistOutRegister, bookTypeConf,
									locale, entidad);
						}

						bookTypeConf = completeBookTypeConf(bookTypeConf, user,
								axsf, scrOfic, locale);

						createNewRegisterDocs(sessionID, user, idBook,
								scrDistReg, registerId, timestamp, miscFormat,
								bookTypeConf, bookConf, imageAuth, entidad);
					}
				}
			}
		} catch (BookException bE) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw bE;
		} catch (SessionException sE) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (EventException eE) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw eE;
		} catch (Exception e) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
			closeDistRegs(sessionID, archidFdr, error, entidad);
		}
	}

	protected static void createRegsAsoc(String sessionID,
			Map newRegisterIdOutInBook, List ids, List archidFdr,
			Integer idBook, String entidad) throws BookException,
			SessionException, ValidationException, DistributionException {
		Transaction tran = null;
		int error = 0;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HIBERNATE_Iuseruserhdr);

			if (newRegisterIdOutInBook != null
					&& !newRegisterIdOutInBook.isEmpty()) {
				for (Iterator it = ids.iterator(); it.hasNext();) {
					Integer ids1 = (Integer) it.next();
					ScrDistreg scrDistReg = (ScrDistreg) session.load(
							ScrDistreg.class, ids1);
					if (newRegisterIdOutInBook.containsKey(ids1)) {
						int assocId = DBEntityDAOFactory
								.getCurrentDBEntityDAO()
								.getNextIdForScrRegAsoc(user.getId(), entidad);

						int idSec = ((Integer) newRegisterIdOutInBook.get(ids1))
								.intValue();

						ISicresSaveQueries.saveScrRegAsoc(session, assocId,
								scrDistReg.getIdArch(), idBook.intValue(),
								scrDistReg.getIdFdr(), idSec);
					}
				}
			}

			HibernateUtil.commitTransaction(tran);
		} catch (DistributionException e) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw e;
		} catch (SessionException sE) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw sE;
		} catch (Exception e) {
			error = 1;
			HibernateUtil.rollbackTransaction(tran);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
			closeDistRegs(sessionID, archidFdr, error, entidad);
		}
	}

	/**
	 * Inserta una distribucion en Base de Datos. Asume que el tipo de origen es
	 * una oficina.
	 *
	 * @param session
	 * @param bookID
	 * @param fdrid
	 * @param typeDest
	 * @param idDest
	 * @param currentDate
	 * @param deptId
	 * @param userName
	 * @param userId
	 * @param distributionType
	 * @param messageForUser
	 * @param entidad
	 * @return Identificador de la distribucion
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws BookException
	 * @throws Exception
	 */
	private static Integer insertDistribute(Session session, Integer bookID,
			int fdrid, int typeDest, int idDest, Timestamp currentDate,
			int deptId, String userName, Integer userId, int distributionType,
			String messageForUser, String entidad) throws HibernateException,
			SQLException, BookException, Exception {

		boolean distribute = true;
		String message = null;
		int distributionID = 0;

		List distReg = ISicresQueries.getScrDistreg(session, bookID, fdrid,
				typeDest, idDest);
		for (Iterator it = distReg.iterator(); it.hasNext();) {
			ScrDistreg scr = (ScrDistreg) it.next();
			if (scr.getState() != ISDistribution.STATE_RECHAZADO
					&& scr.getState() != ISDistribution.STATE_REDISTRIBUIDO) {
				distribute = false;
			}
			message = scr.getMessage();
		}

		if (distribute) {
			distributionID = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getNextIdForScrDistReg(userId, entidad);

			Date dateState = BBDDUtils.getDateFromTimestamp(currentDate);

			ISicresSaveQueries.saveScrDistreg(session, distributionID, bookID,
					fdrid, dateState, 2, deptId, typeDest, idDest, 1,
					dateState, messageForUser,0);

			ISDistribution isDist = new ISDistribution();
			isDist.setDistState(session, distributionID,
					ISDistribution.STATE_PENDIENTE, dateState, userName,
					userId, entidad, messageForUser, isDataBaseCaseSensitive(entidad));
			isDist.changeStateAcceptRedis(session, bookID, fdrid, deptId,
					userName, userId, distributionType, currentDate, entidad,
					isDataBaseCaseSensitive(entidad));
		}
		return new Integer(distributionID);
	}

	/**
	 * Inserta una distribucion en BD
	 *
	 * @param session
	 * @param bookID
	 * @param fdrid
	 * @param typeDest
	 * @param idDest
	 * @param currentDate
	 * @param typeOrig
	 * @param idOrig
	 * @param userName
	 * @param userId
	 * @param distributionType
	 * @param messageForUser
	 * @param entidad
	 * @return Identificador de la distribucion
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws BookException
	 * @throws Exception
	 */
	protected static Integer insertDistribute(Session session, Integer bookID,
			int fdrid, int typeDest, int idDest, Timestamp currentDate,
			int typeOrig, int idOrig, String userName, Integer userId,
			int distributionType, String messageForUser, String entidad, Integer idDistFather)
			throws HibernateException, SQLException, BookException, Exception {

		boolean distribute = true;
		int distributionID = 0;

		List distReg = ISicresQueries.getScrDistreg(session, bookID, fdrid,
				typeDest, idDest);
		for (Iterator it = distReg.iterator(); it.hasNext();) {
			ScrDistreg scr = (ScrDistreg) it.next();
			if (scr.getState() != ISDistribution.STATE_RECHAZADO
					&& scr.getState() != ISDistribution.STATE_REDISTRIBUIDO) {
				distribute = false;
			}
		}

		// comprobamos si se distribuye
		if (distribute) {
			distributionID = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getNextIdForScrDistReg(userId, entidad);

			Date dateState = BBDDUtils.getDateFromTimestamp(currentDate);

			boolean isDBCaseSensitive = isDataBaseCaseSensitive(entidad);
			if (isDBCaseSensitive){
				if (messageForUser!=null){
					messageForUser= messageForUser.toUpperCase();
				}
			}

			int idDistribucionRelacionada = 0;
			if(idDistFather != null){
				idDistribucionRelacionada = idDistFather.intValue();
			}

			ISicresSaveQueries.saveScrDistreg(session, distributionID, bookID,
					fdrid, dateState, typeOrig, idOrig, typeDest, idDest, 1,
					dateState, messageForUser, idDistribucionRelacionada);

			ISDistribution isDist = new ISDistribution();
			isDist.setDistState(session, distributionID,
					ISDistribution.STATE_PENDIENTE, dateState, userName,
					userId, entidad, messageForUser, isDataBaseCaseSensitive(entidad));
			isDist.changeStateAcceptRedis(session, bookID, fdrid, idOrig,
					userName, userId, distributionType, currentDate, entidad,
					isDataBaseCaseSensitive(entidad));
		}
		return new Integer(distributionID);
	}

	/**
	 * Metodo que redistribuye una distribucion
	 *
	 * @param session
	 *            - Sesion hibernate
	 * @param sessionID
	 * @param locale
	 *            - Idioma
	 * @param scrDistReg
	 *            - Informacion de la distribucion a modificar
	 * @param user
	 *            - Usuario
	 * @param canDestWithoutList
	 *            - Indica si se puede distribuir a un destino sin lista de
	 *            distribucion
	 * @param typeDist
	 *            - Tipo de distribucion (Entrada/Salida)
	 * @param scrOfic
	 *            - Oficina del usuario logeado
	 * @param currentDate
	 *            - Fecha actual de
	 * @param userType
	 *            - Tipo de destino
	 * @param distList
	 *            - Listado con los Ids de los nuevos destinos
	 * @param messageForUser
	 *            - Mensaje de la distribucion
	 * @param entidad
	 *
	 * @throws ValidationException
	 * @throws SessionException
	 * @throws DistributionException
	 * @throws HibernateException
	 * @throws BookException
	 * @throws SQLException
	 * @throws Exception
	 */
	protected static void redistributionDistribution(Session session,
			String sessionID, Locale locale, ScrDistreg scrDistReg,
			AuthenticationUser user, Integer canDestWithoutList, int typeDist,
			ScrOfic scrOfic, Date currentDate, Integer userType, List distList,
			String messageForUser, String entidad, boolean isLdap)
			throws DistributionException, SessionException,
			ValidationException, HibernateException, Exception {

		// actualizamos el estado de la distribucion
		scrDistReg = updateDistRegByTypeFromChangeDistribution(session, user,
				sessionID, scrDistReg, typeDist, distList, canDestWithoutList,
				scrDistReg.getId(), entidad, messageForUser);

		int state = -1;
		if ((distList == null || distList.isEmpty())
				&& canDestWithoutList.intValue() == 1) {
			state = 0;
		} else {
			state = 1;
		}

		updateInsertDistAccept(scrOfic, scrDistReg, currentDate, state,
				user.getName(), entidad);

		// array auxiliar con el id de los registros afectados por la
		// redistribucion, en esta caso siempre sera uno
		List disIdFolder = new ArrayList();
		disIdFolder.add(scrDistReg.getIdFdr());

		// recorremos el array de los ids de los nuevos destinos y generamos la
		// distribucion para cada uno
		for (Iterator it = distList.iterator(); it.hasNext();) {
			Integer userId = (Integer) it.next();
			// generamos la nueva distribucion
			createDistribution(session, sessionID, scrDistReg.getIdArch(), 2,
					scrOfic.getDeptid(), userId, userType, messageForUser,
					disIdFolder, user, scrOfic, locale, entidad, scrDistReg.getId());
		}

		//una vez generada las nuevas distribuciones, insertamos los datos de los destinos
		insertDestActualRedistribution(entidad, isLdap,
				scrDistReg.getId(), session);

	}

	protected static Map getScrDistRegIds(Session session, List ids)
			throws HibernateException {
		Map scrDistRegIds = new HashMap();
		for (Iterator it = ids.iterator(); it.hasNext();) {
			Integer ids1 = (Integer) it.next();
			ScrDistreg scrDistReg = (ScrDistreg) session.load(ScrDistreg.class,
					ids1);
			scrDistRegIds.put(ids1, scrDistReg);
		}

		return scrDistRegIds;
	}

	protected static Map getRegStates(Session session, List ids, Map regStates)
			throws HibernateException {
		for (Iterator it = ids.iterator(); it.hasNext();) {
			Integer ids1 = (Integer) it.next();
			ScrDistreg scrDistReg = (ScrDistreg) session.load(ScrDistreg.class,
					ids1);
			if (!regStates.containsKey(new Integer(scrDistReg.getIdArch()))) {
				ScrRegstate regState = ISicresQueries.getScrRegstate(session,
						new Integer(scrDistReg.getIdArch()));
				regStates.put(new Integer(scrDistReg.getIdArch()), new Integer(
						regState.getImageAuth()));
			}
		}

		return regStates;
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/
	/**
	 * Metodo que obtiene el string de consulta de registros distribuido segun
	 * un estado y el deptId del usuario
	 *
	 * @param user
	 * @param iUserGroupUser
	 * @param dateUserLastConnection
	 * @param state
	 * @return
	 * @throws Exception
	 */
	private static String getFolderDistQuerySizeByStateAndByDeptId(
			AuthenticationUser user, List iUserGroupUser,
			Date dateUserLastConnection, int state) throws Exception {

		StringBuffer querySize = new StringBuffer();
		querySize.append("select count(*) from ");
		querySize.append(HIBERNATE_ScrDistreg);
		querySize.append(" as scr where ");
		querySize.append(" scr.state = ");
		querySize.append(state);

		switch (state) {
		case ISDistribution.STATE_PENDIENTE:
			querySize.append(getFolderDistQuerySizeByStatePendienteByDeptId(
					user, iUserGroupUser));
			break;
		case ISDistribution.STATE_RECHAZADO:
			querySize.append(getFolderDistQuerySizeByStateRechazadoByDeptId(
					user, iUserGroupUser));
			break;
		default:
			break;
		}

		querySize.append(" and scr.distDate > "
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getTimeStampField(
						dateUserLastConnection, 2));

		return querySize.toString();
	}

	/**
	 *
	 * @param user
	 * @param iUserGroupUser
	 * @param dateUserLastConnection
	 * @param state
	 * @return
	 * @throws Exception
	 */
	private static String getFolderDistQuerySizeByState(
			AuthenticationUser user, List iUserGroupUser,
			Date dateUserLastConnection, int state) throws Exception {

		StringBuffer querySize = new StringBuffer();
		querySize.append("select count(*) from ");
		querySize.append(HIBERNATE_ScrDistreg);
		querySize.append(" as scr where ");
		querySize.append(" scr.state = ");
		querySize.append(state);

		switch (state) {
		case ISDistribution.STATE_PENDIENTE:
			querySize.append(getFolderDistQuerySizeByStatePendiente(user,
					iUserGroupUser));
			break;
		case ISDistribution.STATE_RECHAZADO:
			querySize.append(getFolderDistQuerySizeByStateRechazado(user,
					iUserGroupUser));
			break;
		default:
			break;
		}

		querySize.append(" and scr.distDate > "
				+ DBEntityDAOFactory.getCurrentDBEntityDAO().getTimeStampField(
						dateUserLastConnection, 2));

		return querySize.toString();
	}

	private static String getFolderDistQuerySizeByStatePendiente(
			AuthenticationUser user, List iUserGroupUser) throws Exception {
		StringBuffer querySize = new StringBuffer();
		querySize.append(" and (   ( scr.typeDest=1 and scr.idDest= ");
		querySize.append(user.getId());
		querySize.append(") or ( scr.typeDest=2 and scr.idDest=");
		querySize.append(user.getDeptid());
		querySize.append(") or ( scr.typeDest=2 and scr.idDest=");
		querySize.append(user.getDeptIdOriginal());

		if (user.getDeptList() != null && !user.getDeptList().isEmpty()) {
			for (Iterator iterator = user.getDeptList().iterator(); iterator
					.hasNext();) {
				Integer deptIdCurrent = (Integer) iterator.next();
				querySize.append(") or ( scr.typeDest=2 and scr.idDest=");
				querySize.append(deptIdCurrent);
			}
		}

		if (iUserGroupUser.size() > 0) {
			for (int i = 0; i < iUserGroupUser.size(); i++) {
				Integer idGroup = (Integer) iUserGroupUser.get(i);
				querySize.append(") or ( scr.typeDest=3 and scr.idDest=");
				querySize.append(idGroup);

			}
		}

		querySize.append(") ) ");

		return querySize.toString();
	}

	/**
	 * Metodo que genera el string de consulta para obtener los registros
	 * pendientes mediante el usuario y el id del departamento actual del
	 * usuario
	 *
	 * @param user
	 * @param iUserGroupUser
	 * @return
	 * @throws Exception
	 */
	private static String getFolderDistQuerySizeByStatePendienteByDeptId(
			AuthenticationUser user, List iUserGroupUser) throws Exception {
		StringBuffer querySize = new StringBuffer();
		querySize.append(" and (   ( scr.typeDest=1 and scr.idDest= ");
		querySize.append(user.getId());
		querySize.append(") or ( scr.typeDest=2 and scr.idDest=");
		querySize.append(user.getDeptid());

		if (iUserGroupUser.size() > 0) {
			for (int i = 0; i < iUserGroupUser.size(); i++) {
				Integer idGroup = (Integer) iUserGroupUser.get(i);
				querySize.append(") or ( scr.typeDest=3 and scr.idDest=");
				querySize.append(idGroup);

			}
		}

		querySize.append(") ) ");

		return querySize.toString();
	}

	private static String getFolderDistQuerySizeByStateRechazado(
			AuthenticationUser user, List iUserGroupUser) throws Exception {
		StringBuffer querySize = new StringBuffer();

		querySize.append(" and (   ( scr.typeOrig=1 and scr.idOrig= ");
		querySize.append(user.getId());
		querySize.append(") or ( scr.typeOrig=2 and scr.idOrig=");
		querySize.append(user.getDeptid());
		querySize.append(") or ( scr.typeOrig=2 and scr.idOrig=");
		querySize.append(user.getDeptIdOriginal());

		if (user.getDeptList() != null && !user.getDeptList().isEmpty()) {
			for (Iterator iterator = user.getDeptList().iterator(); iterator
					.hasNext();) {
				Integer deptIdCurrent = (Integer) iterator.next();
				querySize.append(") or ( scr.typeOrig=2 and scr.idOrig=");
				querySize.append(deptIdCurrent);
			}
		}

		if (iUserGroupUser.size() > 0) {
			for (int i = 0; i < iUserGroupUser.size(); i++) {
				Integer idGroup = (Integer) iUserGroupUser.get(i);
				querySize.append(") or ( scr.typeOrig=3 and scr.idOrig=");
				querySize.append(idGroup);

			}
		}

		querySize.append(") ) ");

		return querySize.toString();
	}

	/**
	 * Metodo que obtiene el string de consulta de registros rechazados de un
	 * usuario y el departamento actual
	 *
	 * @param user
	 * @param iUserGroupUser
	 * @return
	 * @throws Exception
	 */
	private static String getFolderDistQuerySizeByStateRechazadoByDeptId(
			AuthenticationUser user, List iUserGroupUser) throws Exception {
		StringBuffer querySize = new StringBuffer();

		querySize.append(" and (   ( scr.typeOrig=1 and scr.idOrig= ");
		querySize.append(user.getId());
		querySize.append(") or ( scr.typeOrig=2 and scr.idOrig=");
		querySize.append(user.getDeptid());

		if (iUserGroupUser.size() > 0) {
			for (int i = 0; i < iUserGroupUser.size(); i++) {
				Integer idGroup = (Integer) iUserGroupUser.get(i);
				querySize.append(") or ( scr.typeOrig=3 and scr.idOrig=");
				querySize.append(idGroup);

			}
		}

		querySize.append(") ) ");

		return querySize.toString();
	}

	/**
	 *
	 * @param session
	 * @param querySize
	 * @param autoArchiv
	 * @param user
	 * @param entidad
	 * @throws Exception
	 */
	private static void autoArchiving(Session session, String querySize,
			int autoArchiv, AuthenticationUser user, String entidad)
			throws Exception {
		ScrDistreg scrDistReg = null;
		String query = "state = 2 and " + querySize;
		Date currentDate = new Date(DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDBServerDate(entidad).getTime());
		Criteria criteriaResults = session.createCriteria(ScrDistreg.class);
		criteriaResults.add(Expression.sql(query.toString()));
		List list = criteriaResults.list();

		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				scrDistReg = (ScrDistreg) it.next();

				Date stateDate = new Date(scrDistReg.getStateDate().getTime());
				Calendar gc = Calendar.getInstance();
				gc.setTime(stateDate);
				gc.add(Calendar.HOUR, autoArchiv);
				Date timeOutDate = gc.getTime();
				if (timeOutDate.before(currentDate)) {
					scrDistReg.setState(ISDistribution.STATE_ARCHIVADO);
					scrDistReg.setStateDate(currentDate);
					session.update(scrDistReg);
					ISDistribution isDist = new ISDistribution();

					isDist.setDistState(session, scrDistReg.getId().intValue(),
							ISDistribution.STATE_ARCHIVADO, currentDate,
							user.getName(), user.getId(), entidad,
							null, isDataBaseCaseSensitive(entidad));

				}
			}
		}
	}

	/**
	 *
	 * @param user
	 * @param iUserGroupUser
	 * @return
	 */
	private static String getInDistQuery(AuthenticationUser user,
			List iUserGroupUser, List bookList, boolean oficAsoc) {
		StringBuffer query = new StringBuffer();

		query.append(" (   ( type_Dest=1 and id_Dest= ");
		query.append(user.getId());

		query.append(") or ( type_Dest=2 and id_Dest=");
		query.append(user.getDeptid());

		if (oficAsoc) {
			query.append(") or ( type_Dest=2 and id_Dest=");
			query.append(user.getDeptIdOriginal());

			if (user.getDeptList() != null && !user.getDeptList().isEmpty()) {
				for (Iterator iterator = user.getDeptList().iterator(); iterator
						.hasNext();) {
					Integer deptIdCurrent = (Integer) iterator.next();
					query.append(") or ( type_Dest=2 and id_Dest=");
					query.append(deptIdCurrent);
				}
			}
		}

		if (iUserGroupUser.size() > 0) {
			for (int i = 0; i < iUserGroupUser.size(); i++) {
				Integer idGroup = (Integer) iUserGroupUser.get(i);
				query.append(") or ( type_Dest=3 and id_Dest=");
				query.append(idGroup);

			}
		}

		query.append(") ) ");

		if ((bookList != null) && (!bookList.isEmpty())) {
			query.append(" and (");

			int count = 0;
			for (Iterator iterator = bookList.iterator(); iterator.hasNext();) {
				ScrRegstate book = (ScrRegstate) iterator.next();
				if (count > 0) {
					query.append(" or ");
				}
				if (book.getState() == 0) {
					query.append(" id_arch=");
					query.append(book.getIdocarchhdr().getId());
					count++;
				}
			}

			query.append(") ");
		}

		return query.toString();
	}

	/**
	 *
	 * @param user
	 * @param iUserGroupUser
	 * @return
	 */
	private static String getOutDistQuery(AuthenticationUser user,
			List iUserGroupUser, List bookList, boolean oficAsoc) {
		StringBuffer query = new StringBuffer();
		query.append(" (   ( type_Orig=1 and id_Orig= ");
		query.append(user.getId());

		query.append(") or ( type_Orig=2 and id_Orig=");
		query.append(user.getDeptid());

		if (oficAsoc) {
			query.append(") or ( type_Orig=2 and id_Orig=");
			query.append(user.getDeptIdOriginal());

			if (user.getDeptList() != null && !user.getDeptList().isEmpty()) {
				for (Iterator iterator = user.getDeptList().iterator(); iterator
						.hasNext();) {
					Integer deptIdCurrent = (Integer) iterator.next();
					query.append(") or ( type_Orig=2 and id_Orig=");
					query.append(deptIdCurrent);
				}
			}
		}

		if (iUserGroupUser.size() > 0) {
			for (int i = 0; i < iUserGroupUser.size(); i++) {
				Integer idGroup = (Integer) iUserGroupUser.get(i);
				query.append(") or ( type_Orig=3 and id_Orig=");
				query.append(idGroup);
			}
		}

		query.append("))");

		if ((bookList != null) && (!bookList.isEmpty())) {
			query.append(" and (");

			int count = 0;
			for (Iterator iterator = bookList.iterator(); iterator.hasNext();) {
				ScrRegstate book = (ScrRegstate) iterator.next();
				if (count > 0) {
					query.append(" or ");
				}
				if (book.getState() == 0) {
					query.append(" id_arch=");
					query.append(book.getIdocarchhdr().getId());
					count++;
				}
			}

			query.append(") ");
		}

		return query.toString();
	}

	private static String getDistWhere(String distWhere, int typeDist)
			throws HibernateException {

		String auxDistWhere = distWhere;
		String distWhereOperator = null;
		String cadenaDestOrg = null;

		int pos = -1;
		int pos1 = -1;

		if (typeDist == 1) {
			pos = auxDistWhere.indexOf("@ORIG");
		} else {
			pos = auxDistWhere.indexOf("@DEST");
		}
		if (pos >= 0) {
			auxDistWhere = auxDistWhere.substring(pos, auxDistWhere.length());
			pos1 = auxDistWhere.indexOf("and");
			if (pos1 > 0) {
				cadenaDestOrg = auxDistWhere.substring(0, pos1);
			} else {
				cadenaDestOrg = auxDistWhere
						.substring(0, auxDistWhere.length());
			}
			auxDistWhere = cadenaDestOrg;
			auxDistWhere = auxDistWhere.substring(5, auxDistWhere.length())
					.trim();
			boolean digit = Character.isDigit(auxDistWhere.charAt(1));
			if (digit) {
				distWhereOperator = auxDistWhere.substring(0, 1);
				auxDistWhere = auxDistWhere.substring(1, auxDistWhere.length());
			} else {
				distWhereOperator = auxDistWhere.substring(0, 2);
				auxDistWhere = auxDistWhere.substring(2, auxDistWhere.length());
			}
		} else {
			auxDistWhere = "";
		}

		return getDistWhere(auxDistWhere, distWhere, typeDist,
				distWhereOperator, cadenaDestOrg);
	}

	private static String getDistWhere(String auxDistWhere, String distWhere,
			int typeDist, String distWhereOperator, String cadenaDestOrg) {
		String result = "";
		if (auxDistWhere.length() > 0) {
			Long valor = new Long(auxDistWhere);
			Long idDestOrig = null;
			Integer destType = null;
			long operador1 = 0x10000000;
			long operador2 = 0x20000000;
			long operador3 = 0x40000000;
			if ((valor.longValue() & operador1) == operador1) {
				destType = new Integer(1);
				idDestOrig = new Long(valor.longValue() & (~operador1));
			}
			if ((valor.longValue() & operador2) == operador2) {
				destType = new Integer(3);
				idDestOrig = new Long(valor.longValue() & (~operador2));
			}
			if ((valor.longValue() & operador3) == operador3) {
				destType = new Integer(2);
				idDestOrig = new Long(valor.longValue() & (~operador3));
			}
			if (typeDist == 2) {
				if (destType != null && idDestOrig != null) {
					auxDistWhere = "(TYPE_DEST =" + destType + " AND ID_DEST"
							+ distWhereOperator + idDestOrig.toString() + ") ";
					result = distWhere.replaceAll(cadenaDestOrg, auxDistWhere);
				} else {
					result = distWhere;
				}
			} else {
				if (destType != null && idDestOrig != null) {
					auxDistWhere = "(TYPE_ORIG =" + destType + " AND ID_ORIG"
							+ distWhereOperator + idDestOrig.toString() + ") ";
					result = distWhere.replaceAll(cadenaDestOrg, auxDistWhere);
				} else {
					result = distWhere;
				}
			}
		} else {
			result = distWhere;
		}

		return result;
	}

	protected static String getRegWhere(String regWhere, boolean typeBook)
			throws HibernateException {
		String result = "";
		String aux = regWhere;

		if (typeBook) {
			if (aux.indexOf("@FLD17") >= 0) {
				aux = aux.replaceAll("@FLD17", "FLD17");
			}
			if (aux.indexOf("@FLD16") >= 0) {
				aux = aux.replaceAll("@FLD16", "FLD16");
			}
		} else {
			if (aux.indexOf("@FLD17") >= 0) {
				aux = aux.replaceAll("@FLD17", "FLD13");
			}
			if (aux.indexOf("@FLD16") >= 0) {
				aux = aux.replaceAll("@FLD16", "FLD12");
			}
		}
		if (aux.indexOf("@FLD8") >= 0) {
			aux = aux.replaceAll("@FLD8", "FLD8");
		}
		if (aux.indexOf("@FLD2") >= 0) {
			aux = aux.replaceAll("@FLD2", "FLD2");
		}
		if (aux.indexOf("@FLD1") >= 0) {
			aux = aux.replaceAll("@FLD1", "FLD1");
		}
		if (aux.indexOf("@FLD9") >= 0) {
			aux = aux.replaceAll("@FLD9", "FLD9");
		}

		if (aux.indexOf("@FLD7") >= 0) {
			aux = aux.replaceAll("@FLD7", "FLD7");
		}

		result = aux;
		return result;
	}

	private static String getIUserDeptHdrInfo(String id, String entidad)
			throws HibernateException {

		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			Iuserdepthdr iuser = (Iuserdepthdr) session.load(
					Iuserdepthdr.class, new Integer(id));

			if (iuser != null) {
				result = iuser.getName();
			}
		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	private static String getIUserGroupHdrInfo(String id, String entidad)
			throws HibernateException {
		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			Iusergrouphdr iuser = (Iusergrouphdr) session.load(
					Iusergrouphdr.class, new Integer(id));

			if (iuser != null) {
				result = iuser.getName();
			}
		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	private static String getIUserUserHdrInfo(String id, String entidad)
			throws HibernateException {
		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			Iuseruserhdr iuser = (Iuseruserhdr) session.load(
					Iuseruserhdr.class, new Integer(id));

			if (iuser != null) {
				if (iuser.getRemarks() != null
						&& iuser.getRemarks().length() > 0) {
					result = iuser.getRemarks();
				} else {
					result = iuser.getName();
				}
			}
		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	private static String getScrOrgInfo(String id, String entidad)
			throws HibernateException {
		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			ScrOrg iuser = (ScrOrg) session.load(ScrOrg.class, new Integer(id));

			if (iuser != null) {
				result = iuser.getName();
			}
		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	private static String getScrUsrIdentInfo(String userId, String entidad)
			throws HibernateException {
		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			ScrUsrIdent scrUsrIdent = (ScrUsrIdent) session.load(
					ScrUsrIdent.class, new Integer(userId));

			if (scrUsrIdent != null) {
				result = scrUsrIdent.getFirstName() + " "
						+ scrUsrIdent.getSecondName() + ","
						+ scrUsrIdent.getSurName();
			}
		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	/**
	 * Método que indica si se puede actualizar o no la distribucion
	 *
	 * @param state
	 *            - Estado de la distribución
	 * @param distList
	 *            - Lista de distribución asociada
	 * @param canDestWithoutList
	 *            - Indica si se puede indicar un destino sin lista de
	 *            distribución asociada
	 *
	 * @return boolean - TRUE: Se puede actualizar distribución / FALSE: No se
	 *         puede actualizar distribución
	 */
	private static boolean validateUpdateDistribution(int state, List distList,
			Integer canDestWithoutList) {
		boolean result = false;

		// Se valida (si el estado es PENDIENTE o ACEPTADO y la lista de
		// validacion tiene destino asociado) O (si el estado es PENDIENTE o
		// ACEPTADO y se indica una lista de distribución vacia pero por
		// configuración se permite dicha opción (CanChangeDestWithoutList de la
		// tabla SCR_CONFIGURATION, ISSysConfig.exe)
		if ((state == ISDistribution.STATE_PENDIENTE && !distList.isEmpty())
				|| (state == ISDistribution.STATE_PENDIENTE
						&& distList.isEmpty() && canDestWithoutList.intValue() != 0)
				|| (state == ISDistribution.STATE_ACEPTADO && !distList
						.isEmpty())
				|| (state == ISDistribution.STATE_ACEPTADO
						&& distList.isEmpty() && canDestWithoutList.intValue() != 0)) {
			//se puede actualizar la distribucon
			result = true;
		}

		return result;
	}

	/**
	 * Método que actualiza la distribución cuando se realiza un cambio de
	 * destino (desde la bandeja de distribucion)
	 *
	 * @param session
	 *            - Sesion de hibernate
	 * @param user
	 *            - Usuario
	 * @param sessionID
	 *            - ID de sesion
	 * @param scrDistReg
	 *            - Distribución
	 * @param typeDist
	 *            - Tipo de distribucón
	 * @param distList
	 *            - Lista de distribución asociada
	 * @param canDestWithoutList
	 *            - Indica si se puede indicar un destino sin lista de
	 *            distribución asociada
	 * @param id
	 *
	 * @param entidad
	 *
	 * @param mensaje
	 *
	 * @return {@link ScrDistreg}
	 *
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws ValidationException
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws Exception
	 */
	private static ScrDistreg updateDistRegByTypeFromChangeDistribution(
			Session session, AuthenticationUser user, String sessionID,
			ScrDistreg scrDistReg, int typeDist, List distList,
            Integer canDestWithoutList, Integer id, String entidad, String mensaje)
			throws DistributionException, SessionException,
			ValidationException, HibernateException, SQLException, Exception {
	//Si tipo de distribucion es de entrada
		if (typeDist == 1) {
		//validamos si se puede actualizar
			if (validateUpdateDistribution(scrDistReg.getState(), distList,
					canDestWithoutList)) {
				//se puede actualizar la distribucion y se procede a ello
				scrDistReg = updateDistReg(session, user, sessionID,
						DISTRIBUTION_REDISTRIBUTE_EVENT, scrDistReg,
						ISDistribution.STATE_REDISTRIBUIDO, id, mensaje, entidad);

			} else if (scrDistReg.getState() != ISDistribution.STATE_PENDIENTE
					&& scrDistReg.getState() != ISDistribution.STATE_ACEPTADO) {
				// Cuando se intenta actualizar una distribucion en un estado
				// diferente a pendiente o aceptado, elevamos excepcion
				throw new DistributionException(
						DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_IN_STATE);
			} else {
				// Si la distribucion no cumple con los criterios del validateUpdateDistribution
				throw new DistributionException(
						DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_DIST_LIST);
			}
		} else {
		//Si la distribución es de salida se realizan las siguientes validaciones
			if ((scrDistReg.getState() == ISDistribution.STATE_RECHAZADO && !distList
					.isEmpty())
					|| (scrDistReg.getState() == ISDistribution.STATE_RECHAZADO
							&& distList.isEmpty() && canDestWithoutList
							.intValue() != 0)) {
		//se puede actualizar la distribucion y se procede a ello
				scrDistReg = updateDistReg(session, user, sessionID, null,
						scrDistReg, ISDistribution.STATE_REDISTRIBUIDO, id,
						mensaje, entidad);
			} else if (scrDistReg.getState() != ISDistribution.STATE_RECHAZADO) {
				// Cuando la distribucion que se intenta actualizar tiene un
				// estado diferente a rechazado se eleva la excepcion correspondiente
				throw new DistributionException(
						DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_IN_STATE);
			} else {
		//Si no se cumple ninguna de las anteriores validaciones se eleva la excepción
				throw new DistributionException(
						DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_DIST_LIST);
			}
		}

		return scrDistReg;
	}

	private static AxSf getNewInputRegister(Session session, AxSf axsf,
			String entidad) throws NumberFormatException, HibernateException {
		AxSf newInputRegister = new AxSfIn();
		if (axsf instanceof AxSfOut) {
			setAxSfAttributesFromAxSfOut(axsf, newInputRegister);
		} else {
			setAxSfAttributesFromAxSfIn(session, axsf, newInputRegister,
					entidad);
		}

		return newInputRegister;
	}

	private static AxSf setAxSfAttributesFromAxSfIn(Session session, AxSf axsf,
			AxSf newInputRegister, String entidad)
			throws NumberFormatException, HibernateException {
		session = HibernateUtil.currentSession(entidad);
		if (axsf.getAttributeValue("fld8") != null) {
			newInputRegister.setAttributeValue("fld8",
					new Integer(axsf.getAttributeValueAsString("fld8")));
		} else {
			newInputRegister.setAttributeValue("fld8",
					axsf.getAttributeValue("fld8"));
		}
		if (axsf.getAttributeValue("fld7") != null) {
			newInputRegister.setAttributeValue("fld7",
					new Integer(axsf.getAttributeValueAsString("fld7")));
		} else {
			newInputRegister.setAttributeValue("fld7",
					axsf.getAttributeValue("fld7"));
		}
		newInputRegister.setAttributeValue("fld9",
				axsf.getAttributeValue("fld9"));
		if (axsf.getAttributeValue("fld13") != null) {
			newInputRegister.setAttributeValue("fld13",
					axsf.getAttributeValue("fld13"));
		} else {
			ScrOfic scrOficOrigin = ISicresQueries.getScrOficById(session,
					new Integer(axsf.getAttributeValueAsString("fld5")));
			newInputRegister.setAttributeValue("fld13", scrOficOrigin
					.getScrOrg().getId());
		}
		newInputRegister.setAttributeValue("fld14",
				axsf.getAttributeValue("fld14"));
		newInputRegister.setAttributeValue("fld15",
				axsf.getAttributeValue("fld15"));
		if (axsf.getAttributeValue("fld16") != null) {
			newInputRegister.setAttributeValue("fld16",
					new Integer(axsf.getAttributeValueAsString("fld16")));
		} else {
			newInputRegister.setAttributeValue("fld16",
					axsf.getAttributeValue("fld16"));
		}
		newInputRegister.setAttributeValue("fld17",
				axsf.getAttributeValue("fld17"));
		if (axsf.getAttributeValue("fld10") != null) {
			newInputRegister.setAttributeValue("fld10",
					axsf.getAttributeValue("fld10"));
		} else {
			newInputRegister.setAttributeValue("fld10",
					axsf.getAttributeValue("fld1"));
		}
		if (axsf.getAttributeValue("fld11") != null) {
			newInputRegister.setAttributeValue("fld11",
					axsf.getAttributeValue("fld11"));
		} else {
			newInputRegister.setAttributeValue("fld11", new Integer(1));
		}
		if (axsf.getAttributeValue("fld12") != null) {
			newInputRegister.setAttributeValue("fld12",
					axsf.getAttributeValue("fld12"));
		} else {
			newInputRegister.setAttributeValue("fld12",
					axsf.getAttributeValue("fld2"));
		}

		// Referencia de Expediente: FLD19
		newInputRegister.setAttributeValue("fld19",
				axsf.getAttributeValue("fld19"));

		// TODO: ¿Hay que copiar el campo
		// "Fecha de instancia del documento: FLD20"?
		// newInputRegister.setAttributeValue("fld20",
		// axsf.getAttributeValue("fld20"));

		return newInputRegister;
	}

	private static AxSf setAxSfAttributesFromAxSfOut(AxSf axsf,
			AxSf newInputRegister) {
		if (axsf.getAttributeValue("fld8") != null) {
			newInputRegister.setAttributeValue("fld8",
					new Integer(axsf.getAttributeValueAsString("fld8")));
		} else {
			newInputRegister.setAttributeValue("fld8",
					axsf.getAttributeValue("fld8"));
		}
		if (axsf.getAttributeValue("fld7") != null) {
			newInputRegister.setAttributeValue("fld7",
					new Integer(axsf.getAttributeValueAsString("fld7")));
		} else {
			newInputRegister.setAttributeValue("fld7",
					axsf.getAttributeValue("fld7"));
		}
		newInputRegister.setAttributeValue("fld10",
				axsf.getAttributeValue("fld1"));
		newInputRegister.setAttributeValue("fld11", new Integer(2));
		newInputRegister.setAttributeValue("fld12",
				axsf.getAttributeValue("fld2"));
		newInputRegister.setAttributeValue("fld14",
				axsf.getAttributeValue("fld10"));
		newInputRegister.setAttributeValue("fld15",
				axsf.getAttributeValue("fld11"));
		if (axsf.getAttributeValue("fld12") != null) {
			newInputRegister.setAttributeValue("fld16",
					new Integer(axsf.getAttributeValueAsString("fld12")));
		} else {
			newInputRegister.setAttributeValue("fld16",
					axsf.getAttributeValue("fld12"));
		}
		newInputRegister.setAttributeValue("fld17",
				axsf.getAttributeValue("fld13"));
		// axsfs.put(newRegisterIdOutInBook.get(ids1),
		// newInputRegister);

		return newInputRegister;
	}

	private static void updateInsertDistAccept(ScrOfic scrOfic,
			ScrDistreg scrDistReg, Date currentDate, int state,
			String userName, String entidad) throws Exception {
		boolean exist = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getDistAcceptExist(new Integer(scrDistReg.getIdArch()),
						scrDistReg.getIdFdr(), entidad);
		if (scrOfic != null) {
			DBEntityDAOFactory.getCurrentDBEntityDAO().updateInsertDistAccept(
					exist, new Integer(scrDistReg.getIdArch()),
					scrDistReg.getIdFdr(), scrOfic.getId().intValue(), state,
					userName, currentDate, scrDistReg.getDistDate(), entidad);
		} else {
			DBEntityDAOFactory.getCurrentDBEntityDAO().updateInsertDistAccept(
					exist, new Integer(scrDistReg.getIdArch()),
					scrDistReg.getIdFdr(), -1, state, userName, currentDate,
					scrDistReg.getDistDate(), entidad);
		}
	}

	private static BookTypeConf completeBookTypeConf(BookTypeConf bookTypeConf,
			AuthenticationUser user, AxSf axsf, ScrOfic scrOfic, Locale locale) {
		if (bookTypeConf != null) {
			bookTypeConf.setStringBookType(axsf
					.getLocaleAttributeLiteralTypeBook(locale, new Integer(1)));
			SimpleDateFormat shortformatter = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");
			String dateString = shortformatter.format((Date) axsf
					.getAttributeValue("fld2"));
			bookTypeConf.setRegisterDate(dateString);
			bookTypeConf.setUser(user.getName());
			if (scrOfic != null) {
				bookTypeConf.setCodeOfic(scrOfic.getCode());
			}
		}

		return bookTypeConf;
	}

	private static String[] createDistribute(Session session,
			AuthenticationUser user, String sessionID, Integer bookId,
			Integer folderID, Integer senderType, Integer senderId,
			Integer userId, Integer userType, ScrOfic scrOfic, int stateReg,
			String messageForUser, AxSf axsf, String entidad, Integer idDistFather)
			throws ValidationException, SessionException,
			DistributionException, HibernateException, BookException,
			SQLException, Exception {

		String field = null;
		StringBuffer notDistributedRegisters = new StringBuffer();
		StringBuffer distributedRegisters = new StringBuffer();

		// El registro se puede distribuir si está en estado
		// completo o cerrado
		if (stateReg == ISicresKeys.SCR_ESTADO_REGISTRO_COMPLETO
				|| stateReg == ISicresKeys.SCR_ESTADO_REGISTRO_CERRADO) {

			Timestamp currentDate = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getDBServerDate(entidad);

			// TODO: Modificar el 2 por una constante de oficina
			Integer distId = insertDistribute(session, bookId,
					folderID.intValue(), userType.intValue(),
					userId.intValue(), currentDate, senderType, senderId,
					user.getName(), user.getId(), 1, messageForUser, entidad, idDistFather);
			if (distId.intValue() != 0) {
				String eventsManagerClassName = ConfiguratorEvents.getInstance(
						DISTRIBUTION_MANUAL_EVENT).getProperty(
						ConfigurationEventsKeys.KEY_EVENTS_IMPLEMENTATION);
				if (eventsManagerClassName != null
						&& !eventsManagerClassName.equals("")) {
					field = (String) EventsSession.distributionEvent(sessionID,
							DISTRIBUTION_MANUAL_EVENT, bookId, folderID, axsf,
							userType, userId, distId, messageForUser, entidad);
					distributedRegisters.append(folderID);
					distributedRegisters.append("_");
				}
			} else {
				notDistributedRegisters.append(axsf.getAttributeValue("fld1"));
				notDistributedRegisters.append(";");
			}

		} else {
			notDistributedRegisters.append(axsf.getAttributeValue("fld1"));
			notDistributedRegisters.append(";");
		}

		return new String[] { field, notDistributedRegisters.toString(),
				distributedRegisters.toString() };
	}

	private static String createDistributionResponse(String field,
			String distributedRegisters, String notDistributedRegisters) {
		StringBuffer resp = new StringBuffer();
		if (field != null && !distributedRegisters.equals("")) {
			resp.append(field);
			resp.append("#");
			resp.append(distributedRegisters);
		}
		if (!notDistributedRegisters.toString().equals("")) {
			if (!resp.toString().equals("")) {
				resp.append("|");
			}
			resp.append(new DistributionException(
					DistributionException.ERROR_REGISTERS_NOT_DISTRIBUTED)
					.getMessage());
			resp.append(" ");
			resp.append(notDistributedRegisters.toString());
		}

		if (resp.length() > 0) {
			return resp.toString();
		} else {
			return "";
		}
	}

	private static void createNewRegisterDocs(String sessionID,
			AuthenticationUser user, Integer idBook, ScrDistreg scrDistReg,
			int registerId, Timestamp timestamp, MiscFormat miscFormat,
			BookTypeConf bookTypeConf, BookConf bookConf, int imageAuth,
			String entidad) throws DistributionException, SessionException,
			Exception {

		List docs = FolderFileSession.getBookFolderDocsWithPages(sessionID,
				new Integer(scrDistReg.getIdArch()), scrDistReg.getIdFdr(),
				entidad);

		// BookConf bookConf = bookConf(idBook, entidad);

		Integer bookIdIn = idBook;
		for (Iterator it1 = docs.iterator(); it1.hasNext();) {
			AxDoch axDoch = (AxDoch) it1.next();
			int docID = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getNextDocID(bookIdIn, entidad);
			AxPKById docPk = new AxPKById(bookIdIn.toString(), registerId,
					docID);
			AxDochEntity axDochEntity = new AxDochEntity();
			axDochEntity.create(docPk, user.getId().intValue(),
					axDoch.getName(), timestamp, entidad);
			List pages = axDoch.getPages();
			int totalNumPages = pages.size();

			boolean isSign = false;
			for (Iterator it2 = pages.iterator(); it2.hasNext();) {
				AxPageh axPageh = (AxPageh) it2.next();
				if ((axPageh.getLoc().toUpperCase().equals("TIF") || axPageh
						.getLoc().toUpperCase().equals("TIFF"))
						&& bookTypeConf != null && imageAuth == 1) {
					String pageNumber = axDoch.getName() + ":"
							+ axPageh.getSortOrder() + "/" + totalNumPages;
					bookTypeConf.setPageNumber(pageNumber);

					ISRepositorySignDocumentVO signVO = ISRepositoryDocumentHelper
							.getRepositorySingDocumentVO(
									scrDistReg.getIdArch(),
									scrDistReg.getIdFdr(), axPageh.getDocId(),
									axPageh.getId(), miscFormat.getId(),
									bookTypeConf, bookConf, entidad, true);

					RepositoryFactory.getCurrentPolicy().signDocument(signVO);

					isSign = true;
				}

				AxPagehEntity axPagehEntity = new AxPagehEntity();
				int order = axPagehEntity.getOrderID(bookIdIn, registerId,
						docID, entidad);

				AxXnidEntity axXnidEntity = new AxXnidEntity();
				int pageID = axXnidEntity.getNextID(bookIdIn, registerId,
						entidad);

				AxPKById pagePk = new AxPKById(bookIdIn.toString(), registerId,
						pageID);

				axPagehEntity.create(pagePk, user.getId().intValue(),
						axPageh.getName(), order++, docID, axPageh.getFileId(),
						axPageh.getLoc(), timestamp, entidad);
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.insertScrSharedFiles(axPageh.getFileId(),
								scrDistReg.getIdArch(), scrDistReg.getIdFdr(),
								bookIdIn.intValue(), registerId, entidad);

				// REFERENCIA AL DOCUMENTO EN SCR_PAGEREPOSITORY CON LOS NUEVOS
				// BOOKID, FDRID, PAGEID
				ISRepositoryRetrieveDocumentVO findVO = ISRepositoryDocumentHelper
						.getRepositoryRetrieveDocumentVO(
								new Integer(scrDistReg.getIdArch()),
								new Integer(scrDistReg.getIdFdr()),
								new Integer(axPageh.getId()), entidad, true);

				ISRepositoryRetrieveDocumentVO retrieveVO = RepositoryFactory
						.getCurrentPolicy().getDocUID(findVO);

				String docUID = retrieveVO.getDocumentUID();

				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.insertScrPageRepository(bookIdIn.intValue(),
								registerId, pageID, docUID, entidad);

				generateHashInOut(sessionID, scrDistReg.getIdArch(),
						scrDistReg.getIdFdr(), bookIdIn.intValue(), registerId,
						axPageh.getId(), pageID, axPageh.getFileId(), isSign,
						entidad);
			}
		}
	}

	private static void generateHashInOut(String sessionID, int bookIdOut,
			int fdrIdOut, int bookIdIn, int fdrIdIn, int pageIdOut,
			int pageIdIn, int fileId, boolean isSign, String entidad)
			throws DistributionException {

		Transaction tran = null;
		try {

			InvesicresConf invesicresConf = ConfiguratorInvesicres.getInstance(
					entidad).getInvesicresConf();

			ISRepositoryRetrieveDocumentVO findVO = ISRepositoryDocumentHelper
					.getRepositoryRetrieveDocumentVO(new Integer(bookIdOut),
							new Integer(fdrIdOut), new Integer(pageIdOut),
							entidad, true);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			if (invesicresConf.getDocumentHashing() == 1) {

				ISRepositoryRetrieveDocumentVO documentRetrievedVO = RepositoryFactory
						.getCurrentPolicy().retrieveDocument(findVO);
				String findHash = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.getHashDocument(new Integer(bookIdOut), fdrIdOut,
								pageIdOut, null, true, entidad);

				String hash = null;

				if (!isSign && findHash != null) {
					hash = findHash;
				} else {
					hash = CryptoUtils.encryptHashDocument(documentRetrievedVO
							.getFileContent());
				}

				ISicresSaveQueries.saveScrPageInfo(session, new Integer(
						bookIdIn), fdrIdIn, pageIdIn, 1, hash);

			}

			HibernateUtil.commitTransaction(tran);
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to accept the distribution for the session ["
					+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			log.error("Impossible to accept the distribution for the session ["
					+ sessionID + "]", e);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
		}

	}

	/**
	 * Método que obtiene los interesados del registro que se ha aceptado para
	 * añadirlos al registro que se genera
	 *
	 * @param bookId
	 * @param fdrid
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	private static List getNewRegisterInterList(Integer bookId, int fdrid,
			String entidad) throws Exception {

		List aScrRegInt = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.getScrRegisterInter(bookId, fdrid, true, entidad);

		List inter = new ArrayList();

		if (aScrRegInt != null && !aScrRegInt.isEmpty()) {
			for (Iterator iter = aScrRegInt.iterator(); iter.hasNext();) {
				ScrRegisterInter scrRegInt = (ScrRegisterInter) iter.next();

				FlushFdrInter flushFdrInter = new FlushFdrInter();
				flushFdrInter.setInterName(scrRegInt.getName());
				flushFdrInter.setInterId(scrRegInt.getPersonId().intValue());

				if (scrRegInt.getAddressId() != null) {
					flushFdrInter.setDomId(scrRegInt.getAddressId().intValue());
				}

				inter.add(flushFdrInter);
			}
		}

		return inter;
	}

	private static MiscFormat getMiscFormat(Session session, Integer idBook,
			Map outputInputBookType) throws HibernateException {
		MiscFormat miscFormat = null;
		if (!outputInputBookType.isEmpty()) {
			THashMap bookInformation = new THashMap(10);
			getIdocarchdet(session, idBook, bookInformation);
			Idocarchdet idocMisc = (Idocarchdet) bookInformation
					.get(IDocKeys.IDOCARCHDET_MISC_ASOBJECT);
			miscFormat = new MiscFormat(idocMisc.getDetval());
		}

		return miscFormat;
	}

	private static Integer getDistributionResultDistType(Session session,
			ScrDistreg distReg, String entidad) throws HibernateException {
		if (Repository.getInstance(entidad)
				.isInBook(new Integer(distReg.getIdArch())).booleanValue()) {
			if (distReg.getTypeDest() == 2) {
				ScrOfic scrOficIdOrig = ISicresQueries.getScrOficByDeptId(
						session, new Integer(distReg.getIdOrig()));
				ScrOfic scrOficIdDest = ISicresQueries.getScrOficByDeptId(
						session, new Integer(distReg.getIdDest()));
				if ((scrOficIdDest == null)
						|| (scrOficIdOrig.getScrOrg().getId() != scrOficIdDest
								.getScrOrg().getId())) {
					return new Integer(1);
				} else {
					return new Integer(0);
				}
			} else {
				return new Integer(0);
			}
		} else {
			return new Integer(1);
		}
	}

	private static String getDistributionResultId(ScrDistreg distReg,
			int typeDist, String entidad, boolean isLdap)
			throws HibernateException {
		Integer type = null;
		String id = null;
		if (typeDist == DISTRIBUCION_IN_DIST) {
			type = new Integer(distReg.getTypeOrig());
			id = Integer.toString(distReg.getIdOrig());
		} else {
			type = new Integer(distReg.getTypeDest());
			id = Integer.toString(distReg.getIdDest());
		}

		if (type.intValue() == 1) {
			String idAux = getScrUsrIdentInfo(id, entidad);
			if (idAux.equals("")) {
				id = getIUserNameorRemarksInfo(isLdap, id, entidad);
			} else {
				id = idAux;
			}
		} else if (type.intValue() == 2) {
			id = getIUserDeptHdrInfo(id, entidad);
		} else if (type.intValue() == 3) {
			id = getIUserGroupInfo(isLdap, id, entidad);
		} else if (type.intValue() == 4) {
			id = getScrOrgInfo(id, entidad);
		}

		return id;
	}

	private static boolean isPropOrg(Session session, AxSf axsf)
			throws HibernateException {
		boolean launchDistribution = false;
		ScrOrg scrorg = null;
		if (axsf.getAttributeValue("fld8") != null) {
			scrorg = (ScrOrg) session.load(ScrOrg.class,
					new Integer(axsf.getAttributeValueAsString("fld8")));
		}
		launchDistribution = scrorg != null && scrorg.getScrTypeadm() != null
				&& scrorg.getScrTypeadm().getId().equals(new Integer(1));
		return launchDistribution;
	}

	// metodo que nos devuelve el nombre o descripcion de un usuario segun su
	// autenticacion
	private static String getIUserNameorRemarksInfo(boolean isLdap, String id,
			String entidad) throws HibernateException {
		String result = "";
		if (isLdap) {
			result = getIUserLdapUserHdrInfo(id, entidad);
		} else {
			result = getIUserUserHdrInfo(id, entidad);
		}
		return result;
	}

	private static String getIUserLdapUserHdrInfo(String id, String entidad)
			throws HibernateException {
		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			Iuserldapuserhdr iuser = (Iuserldapuserhdr) session.load(
					Iuserldapuserhdr.class, new Integer(id));

			if (iuser != null) {
				result = iuser.getLdapfullname();
			}

		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	// obtenemos la denominacion del grupo del id pasado como parametro
	private static String getIUserGroupInfo(boolean isLdap, String id,
			String entidad) throws HibernateException {
		String result = "";
		if (isLdap) {
			result = getIUserLdapGroupHdrInfo(id, entidad);
		} else {
			result = getIUserGroupHdrInfo(id, entidad);
		}
		return result;
	}

	private static String getIUserLdapGroupHdrInfo(String id, String entidad)
			throws HibernateException {
		String result = "";

		try {
			Session session = HibernateUtil.currentSession(entidad);
			Iuserldapgrphdr iuser = (Iuserldapgrphdr) session.load(
					Iuserldapgrphdr.class, new Integer(id));

			if (iuser != null) {
				result = iuser.getLdapfullname();
			}
		} catch (ObjectNotFoundException e) {
			result = "";
		}

		return result;
	}

	protected static void insertDestinoActualChangeDistribution(
			String sessionID, String entidad, boolean isLdap, List scrs)
			throws ValidationException, DistributionException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;

		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			// Insertamos el destino actual de la distribución en BBDD
			for (Iterator it = scrs.iterator(); it.hasNext();) {
				// Recorremos las distribuciones
				ScrDistreg scrDistReg = (ScrDistreg) it.next();

				// Por cada distribución, comprobamos si hay alguna
				// redistribución para obtener la información sobre el
				// destino actual
				List targetActualDist = null;
				if (scrDistReg.getState() == ISDistribution.STATE_REDISTRIBUIDO) {
					insertDestActualRedistribution(entidad, isLdap, scrDistReg.getId(),
							session);
				}
			}

			HibernateUtil.commitTransaction(tran);

		} catch (Exception e) {
			log.error(e);
			HibernateUtil.rollbackTransaction(tran);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

	private static void insertDestActualRedistribution(String entidad,
			boolean isLdap, Integer idDistribucion, Session session)
			throws HibernateException, SQLException, Exception {

		StringBuffer listadoDestinos = new StringBuffer();

		List list = ISicresQueries.getScrDistregByIdDistFather(session, idDistribucion);
		// Si existe alguna distribución hija, tratamos cada una de ella
		// para obtener el destino que se tratará como destino actual de la
		// distribución padre
		if ((list != null) && !(list.isEmpty())) {
			ScrDistreg distReg = null;
			for (Iterator it = list.iterator(); it.hasNext();) {
				distReg = (ScrDistreg) it.next();
				// obtenemos el destino de la distribución indicada y lo
				// añadimos al listado de destinos actuales
				if(StringUtils.isNotEmpty(listadoDestinos.toString())){
					listadoDestinos.append("\n");
				}

				listadoDestinos.append(getDistributionTargetDescription(session,
						distReg, isLdap));
			}
		}

		// Insertar los datos en la nueva tabla
		ISicresSaveQueries.saveScrDistribucionActual(session, idDistribucion,
				listadoDestinos.toString());
	}
}
