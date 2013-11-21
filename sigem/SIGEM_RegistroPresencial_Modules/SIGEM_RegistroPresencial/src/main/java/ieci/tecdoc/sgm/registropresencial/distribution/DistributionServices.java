package ieci.tecdoc.sgm.registropresencial.distribution;

import ieci.tecdoc.sgm.core.services.registro.Interested;
import ieci.tecdoc.sgm.registropresencial.autenticacion.Login;
import ieci.tecdoc.sgm.registropresencial.autenticacion.User;
import ieci.tecdoc.sgm.registropresencial.info.InfoDistribution;
import ieci.tecdoc.sgm.registropresencial.register.RegisterServicesUtil;
import ieci.tecdoc.sgm.registropresencial.utils.Keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;

import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.DistributionResults;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.idoc.utils.ConfiguratorInvesicres;
import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSessionEx;
import com.ieci.tecdoc.isicres.session.folder.FolderSession;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class DistributionServices implements Keys, ServerKeys {

	public static InfoDistribution getDistributionById(User user,
			Integer distributionId, String entidad) throws ValidationException,
			SecurityException, DistributionException, SessionException,
			BookException, TecDocException, Exception {

		String sessionID = null;
		InfoDistribution result = null;
		try {
			sessionID = Login.login(user, entidad);

			ScrDistreg scrDistreg = DistributionSessionEx
					.getDistributionByIdEx(sessionID, distributionId, entidad);

			if (scrDistreg != null) {
				Integer bookID = new Integer(scrDistreg.getIdArch());
				int fdrid = scrDistreg.getIdFdr();

				BookSession.openBook(sessionID, bookID, entidad);

				AxSf axsf = FolderSession.getBookFolder(sessionID, bookID,
						fdrid, user.getLocale(), entidad);

				if (axsf != null) {
					ScrRegstate scrRegstate = BookSession.getBook(sessionID,
							bookID);

					Interested[] interesados = RegisterServicesUtil
							.getInterestedForFolder(sessionID, bookID,
									new Integer(fdrid), entidad);

					result = ConsultDistribution.consultDistributionInfo(
							sessionID, user, axsf, scrDistreg, scrRegstate
									.getIdocarchhdr(), interesados, entidad);

				}

			}
		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}

		return result;
	}

	/**
	 * Devuelve un objeto distributionResults con la información de la bandeja
	 * de entrada para el usuario.
	 *
	 * @param user:
	 *            Usuario que realiza la consulta
	 * @param state:
	 *            Estado de la distribucion que buscamos
	 * @param firstRow
	 * @param maxResults
	 * @param typeDistr:
	 *            Tipo de Distribucion (1 de entrada y 2 de salida)
	 * @param typeBookRegisterDist:
	 *            Tipo del libro sobre los que buscamos los registros
	 *            distribuidos (0 de entrada y salida, 1 solo de entrada y 2
	 *            solo de salida)
	 * @param oficAsoc:
	 *            Este parametro indica si al usuario se le devuelven los
	 *            registros distribuidos a las oficinas asociadas o no
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 * @throws TecDocException
	 */
	public static List getDistribution(User user, int state, int firstRow,
			int maxResults, int typeDistr, int typeBookRegisterDist,
			boolean oficAsoc, String entidad) throws ValidationException,
			SecurityException, DistributionException, SessionException,
			BookException, TecDocException, Exception {

		String sessionID = null;
		List result = new ArrayList();
		// Map axsfs = new HashMap();

		try {
			sessionID = Login.login(user, entidad);

			List booksOpenList = BookSession.getBooksOpenByType(sessionID,
					typeBookRegisterDist, entidad);

			DistributionServicesUtil.validateUserDeptByOficAsoc(sessionID,
					oficAsoc);

			DistributionResults distributionResults = DistributionSession
					.getDistribution(sessionID, state, firstRow, maxResults,
							typeDistr, "STATE=" + state, "", oficAsoc, user
									.getLocale(), entidad, booksOpenList);

			for (Iterator it = distributionResults.getBooks().iterator(); it
					.hasNext();) {
				String key = it.next().toString();
				StringTokenizer tokenizer = new StringTokenizer(key, "_");
				Integer bookID = new Integer(tokenizer.nextToken());
				int fdrid = Integer.parseInt(tokenizer.nextToken());

				BookSession.openBook(sessionID, bookID, entidad);
				AxSf axsf = FolderSession.getBookFolder(sessionID, bookID,
						fdrid, user.getLocale(), entidad);

				// ScrDistreg distReg = null;
				for (Iterator it2 = distributionResults.getResults().keySet()
						.iterator(); it2.hasNext();) {
					Integer distId = (Integer) it2.next();
					Map aux = (Map) distributionResults.getResults()
							.get(distId);
					String id = (String) aux.keySet().iterator().next();
					ScrDistreg distReg = (ScrDistreg) aux.get(id);


					Idocarchhdr idocarch = new Idocarchhdr();
					BeanUtils.copyProperties(idocarch, distributionResults
							.getIdocarchhdr().get(
									new Integer(distReg.getIdArch())));

					if (distReg.getIdFdr() == fdrid) {
						Interested[] interesados = RegisterServicesUtil
								.getInterestedForFolder(sessionID, bookID,
										new Integer(fdrid), entidad);
						if (!ConsultDistribution.isDistributionInList(result,
								bookID, new Integer(fdrid), distReg.getId())) {
							InfoDistribution distInfo = ConsultDistribution
									.consultDistributionInfo(sessionID, user,
											axsf, distReg, idocarch,
											interesados, entidad);
							result.add(distInfo);
						}
					}
				}
			}

		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}

		return result;
	}

	public static Integer countDistribution(User user, int state,
			int typeDistr, int typeBookRegisterDist, boolean oficAsoc,
			String entidad) throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException,
			TecDocException {

		String sessionID = null;
		Integer result = null;

		try {
			sessionID = Login.login(user, entidad);

			List booksOpenList = BookSession.getBooksOpenByType(sessionID,
					typeBookRegisterDist, entidad);

			DistributionServicesUtil.validateUserDeptByOficAsoc(sessionID,
					oficAsoc);

			int distributionCount = DistributionSession.countDistribution(
					sessionID, state, typeDistr, "STATE=" + state, "",
					oficAsoc, entidad, booksOpenList);

			result = new Integer(distributionCount);
		} finally {
			CacheFactory.getCacheInterface().removeCacheEntry(sessionID);
		}

		return result;
	}

	/**
	 * Cambia el estado de la distribucion de un registro a Aceptado
	 *
	 * @param user
	 * @param distributionId
	 * @param entidad
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 * @throws TecDocException
	 */
	public static void acceptDistribution(User user, Integer distributionId,
			String entidad) throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException,
			TecDocException {
		String sessionID = null;

		try {

			sessionID = Login.login(user, entidad);

			List idsDist = new ArrayList();
			idsDist.add(distributionId);

			DistributionSessionEx.acceptDistributionEx(sessionID, idsDist,
					entidad);

		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
	}

	/**
	 *
	 * @param user
	 * @param registerNumber
	 * @param bookId
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 * @throws TecDocException
	 */
	public static Object acceptDistribution(User user, String registerNumber,
			Integer bookId, String entidad) throws ValidationException,
			SecurityException, DistributionException, SessionException,
			BookException, TecDocException {
		Object result = null;
		Integer bookID = null;
		String sessionID = null;

		try {
			if (bookId == null) {
				bookID = new Integer(0);
			} else {
				bookID = bookId;
			}
			sessionID = Login.login(user, entidad);

			List inList = BookSession.getInBooks(sessionID, user.getLocale(),
					entidad);
			BookSession.getOutBooks(sessionID, user.getLocale(), entidad);
			// ScrRegstate scrRegState = null;
			boolean canCreate = false;
			List createPermBooks = new ArrayList();
			Map createPermBooksInfo = new HashMap();
			for (Iterator it = inList.iterator(); it.hasNext();) {
				// scrRegState = (ScrRegstate) it.next();
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) it
						.next();
				// Integer id = scrRegState.getIdocarchhdr().getId();
				// String name = scrRegState.getIdocarchhdr().getName();
				Integer id = scrRegStateByLanguage.getIdocarchhdrId();
				String name = scrRegStateByLanguage.getIdocarchhdrName();
				BookSession.openBook(sessionID, id, entidad);
				// if (bookID.intValue() != 0
				// && bookID.intValue() == id.intValue()
				// && scrRegState.getState() == 0) {
				if (bookID.intValue() != 0
						&& bookID.intValue() == id.intValue()
						&& scrRegStateByLanguage.getScrregstateState() == 0) {
					if (!createPermBooks.isEmpty()) {
						createPermBooks.clear();
						createPermBooksInfo.clear();
					}
					break;
				}
				canCreate = SecuritySession.canCreate(sessionID, id);
				// if (canCreate && scrRegState.getState() == 0) {
				if (canCreate
						&& scrRegStateByLanguage.getScrregstateState() == 0) {
					createPermBooks.add(id);
					createPermBooksInfo.put(id, name);
				}
			}
			if (!createPermBooks.isEmpty() && createPermBooks.size() > 1) {
				throw new DistributionException(
						DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
			}

			DistributionResults distributionResults = DistributionSession
					.getDistribution(sessionID, 1, 0, 1, 1, "", "@FLD1='"
							+ registerNumber + "'", true, user.getLocale(),
							entidad);
			Map distIds = distributionResults.getResults();

			TreeMap treemap = new TreeMap();
			treemap.putAll(distIds);
			List dis = new ArrayList();
			ScrDistreg distRegFormFolder = null;
			for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
				Integer distId = (Integer) it.next();
				Map aux = (Map) treemap.get(distId);
				String id = (String) aux.keySet().iterator().next();
				ScrDistreg distReg = (ScrDistreg) aux.get(id);
				dis.add(distReg.getId());
				distRegFormFolder = distReg;
			}
			if (distIds.isEmpty()) {
				throw new BookException(
						BookException.ERROR_CANNOT_FIND_REGISTERS);
			}

			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			if (!cacheBag
					.containsKey(new Integer(distRegFormFolder.getIdArch()))) {
				BookSession.openBook(sessionID, new Integer(distRegFormFolder
						.getIdArch()), entidad);
			}

			result = DistributionSession.acceptDistribution(sessionID, dis, 1,
					0, 1, 1, new Integer(0), createPermBooks,
					distributionResults, "", "", new Integer(0), user
							.getLocale(), entidad);
		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
		return result;
	}

	/**
	 * Cambia el estado de la distribucion de un registro a Rechazado
	 *
	 * @param user
	 * @param distributionId
	 * @param remarks
	 * @param entidad
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 * @throws TecDocException
	 */
	public static void rejectDistribution(User user, Integer distributionId,
			String remarks, String entidad) throws ValidationException,
			SecurityException, DistributionException, SessionException,
			BookException, TecDocException {
		String sessionID = null;

		try {

			sessionID = Login.login(user, entidad);

			List idsDist = new ArrayList();
			idsDist.add(distributionId);

			DistributionSessionEx.rejectDistributionEx(sessionID, idsDist,
					remarks, entidad);

		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
	}

	/**
	 *
	 * @param user
	 * @param registerNumber
	 * @param remarks
	 * @param entidad
	 * @return
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws DistributionException
	 * @throws SessionException
	 * @throws BookException
	 * @throws TecDocException
	 */
	public static Object rejectDistribution(User user, String registerNumber,
			String remarks, String entidad) throws ValidationException,
			SecurityException, DistributionException, SessionException,
			BookException, TecDocException {
		Object result = null;
		String sessionID = null;
		ScrDistreg distRegFormFolder = null;

		try {
			sessionID = Login.login(user, entidad);
			ConfiguratorInvesicres.getInstance(entidad).getInvesicresConf();

			BookSession.getInBooks(sessionID, user.getLocale(), entidad);
			BookSession.getOutBooks(sessionID, user.getLocale(), entidad);
			// TODO ////////////
			DistributionResults distributionResults = DistributionSession
					.getDistribution(sessionID, 1, 0, 1, 1, "", "@FLD1='"
							+ registerNumber + "'", true, user.getLocale(),
							entidad);
			Map distIds = distributionResults.getResults();

			ScrDistreg distReg = null;
			Integer distId = null;
			String id = null;
			Map aux = null;
			TreeMap treemap = new TreeMap();
			treemap.putAll(distIds);
			List dis = new ArrayList();
			for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
				distId = (Integer) it.next();
				aux = (Map) treemap.get(distId);
				id = (String) aux.keySet().iterator().next();
				distReg = (ScrDistreg) aux.get(id);
				dis.add(distReg.getId());
				distRegFormFolder = distReg;
			}
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			if (!cacheBag
					.containsKey(new Integer(distRegFormFolder.getIdArch()))) {
				BookSession.openBook(sessionID, new Integer(distRegFormFolder
						.getIdArch()), entidad);
			}
			result = DistributionSession.rejectDistribution(sessionID, dis,
					remarks, 1, 0, 1, 1, "", "@FLD1='" + registerNumber + "'",
					user.getLocale(), entidad);
		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
		return result;
	}

	public static void saveDistribution(User user, Integer distributionId,
			String entidad) throws ValidationException, SecurityException,
			DistributionException, SessionException, BookException,
			TecDocException {
		String sessionID = null;

		try {

			sessionID = Login.login(user, entidad);

			List idsDist = new ArrayList();
			idsDist.add(distributionId);

			DistributionSessionEx.saveDistributionEx(sessionID, idsDist,
					entidad);

		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
	}

	public static Object saveDistribution(User user, String registerNumber,
			int state, String entidad) throws ValidationException,
			SecurityException, DistributionException, SessionException,
			BookException, TecDocException {
		Object result = null;
		String sessionID = null;
		ScrDistreg distRegFormFolder = null;

		try {
			sessionID = Login.login(user, entidad);
			ConfiguratorInvesicres.getInstance(entidad).getInvesicresConf();

			BookSession.getInBooks(sessionID, user.getLocale(), entidad);
			BookSession.getOutBooks(sessionID, user.getLocale(), entidad);
			DistributionResults distributionResults = DistributionSession

			// TODO ////////////
					.getDistribution(sessionID, 1, 0, 1, 1, "", "@FLD1='"
							+ registerNumber + "'", true, user.getLocale(),
							entidad);
			// ///////////////////////////////
			Map distIds = distributionResults.getResults();

			ScrDistreg distReg = null;
			Integer distId = null;
			String id = null;
			Map aux = null;
			TreeMap treemap = new TreeMap();
			treemap.putAll(distIds);
			List dis = new ArrayList();
			for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
				distId = (Integer) it.next();
				aux = (Map) treemap.get(distId);
				id = (String) aux.keySet().iterator().next();
				distReg = (ScrDistreg) aux.get(id);
				dis.add(distReg.getId());
				distRegFormFolder = distReg;
			}
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			if (!cacheBag
					.containsKey(new Integer(distRegFormFolder.getIdArch()))) {
				BookSession.openBook(sessionID, new Integer(distRegFormFolder
						.getIdArch()), entidad);
			}
			result = DistributionSession.saveDistribution(sessionID, dis,
					state, 0, 1, 1, "", "@FLD1='" + registerNumber + "'", user
							.getLocale(), entidad);
		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
		return result;
	}

	public static Object changeDistribution(User user, String registerNumber,
			String code, int typeDist, String entidad)
			throws ValidationException, DistributionException,
			SessionException, SecurityException, BookException, TecDocException {
		String sessionID = null;
		Object result = null;
		ScrDistreg distRegFormFolder = null;
		DistributionResults distributionResults = null;
		Integer launchDistOutRegister = new Integer(BookSession.invesicresConf(
				entidad).getDistSRegister());
		Integer canDestWithoutList = new Integer(BookSession.invesicresConf(
				entidad).getCanChangeDestWithoutList());

		try {
			sessionID = Login.login(user, entidad);
			BookSession.getInBooks(sessionID, user.getLocale(), entidad);
			BookSession.getOutBooks(sessionID, user.getLocale(), entidad);

			// TODO ///////////////
			distributionResults = DistributionSession.getDistribution(
					sessionID, 1, 0, 1, typeDist, "", "@FLD1='"
							+ registerNumber + "'", true, user.getLocale(),
					entidad);
			// ////////////////////

			Map distIds = distributionResults.getResults();

			ScrDistreg distReg = null;
			Integer distId = null;
			String id = null;
			Map aux = null;
			TreeMap treemap = new TreeMap();
			treemap.putAll(distIds);
			List dis = new ArrayList();
			for (Iterator it = treemap.keySet().iterator(); it.hasNext();) {
				distId = (Integer) it.next();
				aux = (Map) treemap.get(distId);
				id = (String) aux.keySet().iterator().next();
				distReg = (ScrDistreg) aux.get(id);
				dis.add(distReg.getId());
				distRegFormFolder = distReg;
			}
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			if (!cacheBag
					.containsKey(new Integer(distRegFormFolder.getIdArch()))) {
				BookSession.openBook(sessionID, new Integer(distRegFormFolder
						.getIdArch()), entidad);
			}

			DistributionSession.changeDistribution(sessionID, dis, code,
					typeDist, launchDistOutRegister, canDestWithoutList, user
							.getLocale(), entidad, Login.isAuthenticationLdap(entidad));

			result = distRegFormFolder;
		} finally {
			SecuritySession.logout(sessionID, entidad);
		}
		return result;
	}
}
