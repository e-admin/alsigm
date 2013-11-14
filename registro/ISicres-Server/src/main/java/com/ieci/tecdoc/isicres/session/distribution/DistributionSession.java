package com.ieci.tecdoc.isicres.session.distribution;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrDistribucionActual;
import com.ieci.tecdoc.common.invesicres.ScrDistlist;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.isicres.DistributionResults;
import com.ieci.tecdoc.common.isicres.DtrFdrResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISDistribution;
import com.ieci.tecdoc.common.utils.ISOfficesValidator;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.common.utils.ISicresSaveQueries;
import com.ieci.tecdoc.common.utils.RBUtil;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.idoc.authentication.LDAPAuthenticationPolicy;
import com.ieci.tecdoc.isicres.events.exception.EventException;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class DistributionSession extends DistributionSessionUtil implements
        ServerKeys, Keys, HibernateKeys {

    private static final Logger log = Logger
            .getLogger(DistributionSession.class);

    public static int getNewFolderDist(String sessionID, String entidad)
            throws DistributionException, SessionException, ValidationException {

        return getSizeFolderDistByState(sessionID, entidad,
                ISDistribution.STATE_PENDIENTE,
                LDAPAuthenticationPolicy.isLdap(entidad));
    }

    /**
     * Obtiene el numero de registros pendientes para un usuario, los grupos a
     * los que pertene y su departamento
     *
     * @param sessionID
     * @param entidad
     * @return
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static int getNewFolderDistByDeptId(String sessionID, String entidad)
            throws DistributionException, SessionException, ValidationException {

        return getSizeFolderDistByStateByDeptId(sessionID, entidad,
                ISDistribution.STATE_PENDIENTE,
                LDAPAuthenticationPolicy.isLdap(entidad));
    }

    public static int getRejectedFolderDist(String sessionID, String entidad)
            throws DistributionException, SessionException, ValidationException {

        return getSizeFolderDistByState(sessionID, entidad,
                ISDistribution.STATE_RECHAZADO,
                LDAPAuthenticationPolicy.isLdap(entidad));
    }

    /**
     * Obtiene el numero de registros rechazados para un usuario, los grupos a
     * los que pertenece y su departamento
     *
     * @param sessionID
     * @param entidad
     * @return
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static int getRejectedFolderDistByDeptId(String sessionID,
            String entidad) throws DistributionException, SessionException,
            ValidationException {

        return getSizeFolderDistByStateByDeptId(sessionID, entidad,
                ISDistribution.STATE_RECHAZADO,
                LDAPAuthenticationPolicy.isLdap(entidad));
    }

    public static DistributionResults getDistribution(String sessionID,
            int state, int firstRow, int maxResults, int typeDist,
            String distWhere, String regWhere, boolean oficAsoc, Locale locale,
            String entidad) throws DistributionException, SessionException,
            ValidationException {

        return getDistribution(sessionID, state, firstRow, maxResults,
                typeDist, distWhere, regWhere, oficAsoc, locale, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad), null);
    }

    public static DistributionResults getDistribution(String sessionID,
            int state, int firstRow, int maxResults, int typeDist,
            String distWhere, String regWhere, boolean oficAsoc, Locale locale,
            String entidad, List bookList) throws DistributionException,
            SessionException, ValidationException {

        return getDistribution(sessionID, state, firstRow, maxResults,
                typeDist, distWhere, regWhere, oficAsoc, locale, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad), bookList, null);
    }

    public static DistributionResults getDistribution(String sessionID,
            int state, int firstRow, int maxResults, int typeDist,
            String distWhere, String regWhere, boolean oficAsoc, Locale locale,
            String entidad, boolean isLdap, String orderBy) throws DistributionException,
            SessionException, ValidationException {

        return getDistribution(sessionID, state, firstRow, maxResults,
                typeDist, distWhere, regWhere, oficAsoc, locale, entidad,
                isLdap, null, orderBy);
    }

    /**
     * Devuelve las distribuciones para un usuario dado
     *
     * @param sessionID
     * @param state
     * @param firstRow
     * @param maxResults
     * @param typeDist
     * @param distWhere
     * @param regWhere
     * @param oficAsoc
     * @param locale
     * @param entidad
     * @param isLdap
     * @param bookList
     * @return
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static DistributionResults getDistribution(String sessionID,
            int state, int firstRow, int maxResults, int typeDist,
            String distWhere, String regWhere, boolean oficAsoc, Locale locale,
            String entidad, boolean isLdap, List bookList, String orderBy)
            throws DistributionException, SessionException, ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            Date currentDate = BBDDUtils
                    .getDateFromTimestamp(DBEntityDAOFactory
                            .getCurrentDBEntityDAO().getDBServerDate(entidad));

            //obtenemos los grupos a los que pertenece el usuario
            List iUserGroupUser = getUserGroups(session, user, isLdap);

            //Se genera la consulta
            StringBuffer querySize = getDistributionQuerySize(session, user,
                    iUserGroupUser, typeDist, distWhere, bookList, oficAsoc,
                    entidad);

            StringBuffer finalWhere = new StringBuffer();
            String tableName = null;

            //Obtenemos los libros en los que existe una distribución
            String selectCriteria = "select distinct id_arch from scr_distreg where ";

            //Listado de libros en los que existe una distribucion sobre el usuario
            List idArchs = DBEntityDAOFactory.getCurrentDBEntityDAO()
                    .getIdArchDistribution(
                            selectCriteria + querySize.toString(), entidad);

			// Cuando se realizan busquedas con criterios de registro y no se ha
			// indicado ordenación se genera una tabla temporal con los datos
            if ((!"".equals(regWhere)) && (StringUtils.isBlank(orderBy))) {
				if (log.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer();
					sb.append(
							"Se crea la tabla temporal para la distribución: ")
							.append(tableName);
					log.debug(sb.toString());
				}

		//obtenemos el nombre de la tabla temporal
                tableName = getTableName(user, idArchs);
                //generamos la consulta en relación a la tabla temporal
                finalWhere.append(getDistributionFinalWhere(idArchs, regWhere,
                        querySize.toString(), tableName, entidad));
            } else {
		//se setea la consulta
                finalWhere.append(querySize.toString());
            }

			// Obtenemos los datos de las distribuciones que cumplen los
			// criterios indicados
			DistributionResults distributionResults = getDistributionResults(
					sessionID, user, firstRow, maxResults, typeDist, locale,
					entidad, isLdap, orderBy, session, currentDate, regWhere,
					finalWhere, idArchs);

            //se comprueba si se ha creado la tabla temporal
            if (tableName != null) {
		//se borra la tabla temporal
                DBEntityDAOFactory.getCurrentDBEntityDAO().dropTableOrView(
                        tableName, entidad);
            }

            HibernateUtil.commitTransaction(tran);

            return distributionResults;
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

    /**
     * Método que obtiene las distribuciones a mostrar
     * @param sessionID - Id de session
     * @param user - Información del usuario
     * @param firstRow - Primera posición de elementos a mostrar
     * @param maxResults - Máximos resultados a mostrar
     * @param typeDist - Tipo de distribución
     * @param locale - Idioma
     * @param entidad - Entidad
     * @param isLdap - Indica si se utiliza LDAP
     * @param orderBy - Ordenacion
     * @param session - Session de hibernate
     * @param currentDate - Fecha
     * @param regWhere - Consulta por los criterios del registro
	 * @param finalWhere - Consulta génerica de la distribución
     * @param idArchs - Ids de los libros afectados con distribución
     *
     * @return {@link DistributionResults}
     *
     * @throws Exception
     * @throws SQLException
     * @throws HibernateException
     */
	private static DistributionResults getDistributionResults(String sessionID,
			AuthenticationUser user, int firstRow, int maxResults,
			int typeDist, Locale locale, String entidad, boolean isLdap,
			String orderBy, Session session, Date currentDate, String regWhere,
			StringBuffer finalWhere, List idArchs) throws Exception,
			SQLException, HibernateException {

		DistributionResults result;

		// Si existen elementos a mostrar y se ha indicado ordenacion
		if (StringUtils.isNotBlank(orderBy)) {
			if (log.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				sb.append("Recupera distribución con ordenación: ").append(
						orderBy);
				log.debug(sb.toString());
			}
			// PROCESO QUE SE EJECUTA SI SE HA INDICADO ORDENACION
			result = getDistributionByOrderBy(sessionID, user, regWhere,
					finalWhere.toString(), firstRow, maxResults, typeDist,
					locale, entidad, isLdap, orderBy, session, currentDate,
					idArchs);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Recupera distribución sin ordenación");
			}

			// obtenemos el numero de distribuciones a mostrar por pantalla
			int distributionCount = getDistributionCount(finalWhere.toString(),
					entidad);

			// PROCESO SIN INDICAR ORDENACION (proceso viejo)
			result = getDistributionWithOutOrder(firstRow, maxResults,
					typeDist, locale, entidad, isLdap, session, currentDate,
					finalWhere, distributionCount);
		}
		return result;
	}

	/**
	 * Método que recupera la información de las distribuciones a partir de una condición y una ordenación indicada
	 * @param sessionID - Id de sesion
	 * @param user - Información del usuario autenticado
	 * @param regWhere - Consulta por los campos del registro
	 * @param finalWhere - Consulta génerica de la distribución
     * @param firstRow - Primera posición de elementos a mostrar
     * @param maxResults - Máximos resultados a mostrar
     * @param typeDist - Tipo de distribución
     * @param locale - Idioma
	 * @param entidad - Entidad
     * @param isLdap - Indica si se utiliza LDAP
     * @param orderBy - Ordenacion
     * @param session - Session de hibernate
	 * @param currentDate - Fecha
     * @param idArchs - Ids de los libros afectados con distribución
	 *
	 * @return {@link DistributionResults}
	 *
	 * @throws Exception
	 * @throws SQLException
	 * @throws HibernateException
	 */
	private static DistributionResults getDistributionByOrderBy(
			String sessionID, AuthenticationUser user, String regWhere,
			String finalWhere, int firstRow, int maxResults, int typeDist,
			Locale locale, String entidad, boolean isLdap, String orderBy,
			Session session, Date currentDate, List idArchs) throws Exception,
			SQLException, HibernateException {

		List listadoDistributionView;
		//iniciamos la variable por si no entra a recuperar datos
		DistributionResults distributionResults = new DistributionResults();;

		if (idArchs != null && !idArchs.isEmpty()) {
			// Generamos la tabla/vista temporal con la información de las
			// distribuciones
			String tableName = createTableTempOrderQuery(entidad, locale, user,
					regWhere, finalWhere, idArchs);

			// Obtenemos el número de elementos
			int distributionCount = DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getTableOrViewSize(tableName, entidad);

			if (log.isDebugEnabled()) {
				StringBuffer sb = new StringBuffer();
				sb.append("getDistributionByOrderBy distributionCount: ")
						.append(distributionCount);
				log.debug(sb.toString());
			}

			// obtenemos el listado de objetos a mostrar en pantalla, según
			// la paginación indicada y el orden
			listadoDistributionView = DBEntityDAOFactory
					.getCurrentDBEntityDAO().getListDistributionOrderBy(
							firstRow, maxResults, entidad, orderBy, tableName);

			// consulta final que se mostrará por pantalla
			distributionResults = getDistributionResultsOrderBy(session,
					listadoDistributionView, distributionCount, currentDate,
					typeDist, locale, entidad, isLdap);

			// se borra la tabla temporal
			DBEntityDAOFactory.getCurrentDBEntityDAO().dropTableOrView(
					tableName, entidad);
		}

		return distributionResults;
	}

    /**
     * Métoo que obtiene la distribución, sin indicar una ordenación
     *
     * @param firstRow - Posición inicial de la consulta
     * @param maxResults - Posición final de la consulta
     * @param typeDist - Tipo de distribucion
     * @param locale - Idioma
     * @param entidad - Entidad
     * @param isLdap - Si es un sistema Ldap
     * @param session - Sesion de hibernate
     * @param currentDate - Fecha
     * @param finalWhere - Consulta a ejecutar
     * @param distributionCount - Elementos totales a recuperar
     *
     * @return Listado de objetos {@link ScrDistreg}
     *
     * @throws HibernateException
     */
	private static DistributionResults getDistributionWithOutOrder(
			int firstRow, int maxResults, int typeDist, Locale locale,
			String entidad, boolean isLdap, Session session, Date currentDate,
			StringBuffer finalWhere, int distributionCount)
			throws HibernateException {

		// Se genera la consulta sobre la tabla scrDistreg
		Criteria criteriaResults = session.createCriteria(ScrDistreg.class);
		criteriaResults.setFirstResult(firstRow);
		criteriaResults.setMaxResults(maxResults);
		criteriaResults.add(Expression.sql(finalWhere.toString()));

		// Se ejecuta la consulta
		List listadoDistributionView = criteriaResults.list();

		// Se tratan el resultado de la búsqueda para obtener el objeto
		// DistributionResults
		DistributionResults distributionResults = getDistributionResults(
				session, listadoDistributionView, distributionCount,
				currentDate, typeDist, locale, entidad, isLdap);

		// Retornamos el objeto
		return distributionResults;
	}

	/**
	 * Método para generar la tabla temporal con las distribuciones y la ordenacion indicada
	 * @param entidad - Entidad
	 * @param user -  Usuario
	 * @param finalWhere - Criterios de búsqueda con la ordenación
	 * @param idArchs - IDs de los libros
	 *
	 * @return String - Cadena con la creación de la tabla temporal
	 *
	 * @throws Exception
	 * @throws SQLException
	 */

	private static String createTableTempOrderQuery(String entidad, Locale locale,
			AuthenticationUser user, String regWhere, String finalWhere, List idArchs)
			throws Exception, SQLException {

		StringBuffer createSentence = new StringBuffer();
		Integer auxBookId;

		// obtenemos el nombre temporal de la tabla/vista a utilizar para
		// almacenar la información de la distribución
		String tableName = getTableName(user, idArchs);

		if (log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("Nombre de la tabla temporal para la distribución: ")
					.append(tableName);
			log.debug(sb.toString());
		}

		// variable si se crea la tabla o se le concatenan los datos
		boolean isCreateTable = true;
		// recorremos cada uno de los libros que tienen distribuciones que
		// cumplen el criterio
		for (Iterator it = idArchs.iterator(); it.hasNext();) {
			auxBookId = (Integer) it.next();
			// Obtenemos la información sobre el tipo de libro (si es de entrada
			// o de salida)
			boolean isInBook = Repository.getInstance(entidad)
					.isInBook(auxBookId).booleanValue();

			// creamos la tabla temporal con los datos correspondientes a cada
			// archivador y distribucion
			createSentence.append(DBEntityDAOFactory.getCurrentDBEntityDAO()
					.getTemporalTableDistributionQuerySentenceOrderBy(
							tableName, auxBookId, finalWhere, getRegWhere(regWhere, isInBook),
							isCreateTable, isInBook, locale.getLanguage()));

			// sumamos al contador, para indicar que ya no es un create sino
			// añadir más información la próxima vez que entra
			isCreateTable = false;
		}

		if (log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("Consulta para generar tabla temporal distribución: ").append(
					createSentence.toString());
			log.debug(sb.toString());
		}

		// consulta para crear tabla/vista
		DBEntityDAOFactory.getCurrentDBEntityDAO().createTableOrView(
				createSentence.toString(), entidad);

		return tableName;
	}

    /**
     * Devuelve las distribuciones a partir de la consulta sql.
     *
     * No tiene en cuenta los usuarios
     *
     * @param sessionID
     *            Sesión del usuario que ejecuta la consulta
     * @param firstRow
     *            Primera fila devuelta
     * @param maxResults
     *            Número máximo de resultados
     * @param typeDist
     *            Tipo de distribución (Entrada o Salida)
     * @param sql
     *            Consulta SQL
     * @param oficAsoc
     * @param locale
     *            Locale
     * @param entidad
     * @param isLdap
     * @return
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static DistributionResults getDistribution(String sessionID,
            int firstRow, int maxResults, int typeDist, String sql,
            Locale locale, String entidad, boolean isLdap)
            throws DistributionException, SessionException, ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            Date currentDate = BBDDUtils
                    .getDateFromTimestamp(DBEntityDAOFactory
                            .getCurrentDBEntityDAO().getDBServerDate(entidad));

            List iUserGroupUser = getUserGroups(session, user, isLdap);

            String tableName = null;

            Criteria criteriaResults = session.createCriteria(ScrDistreg.class);
            criteriaResults.setFirstResult(firstRow);
            criteriaResults.setMaxResults(maxResults);
            criteriaResults.add(Expression.sql(sql));
            List list = criteriaResults.list();

            int distributionCount = getDistributionCount(sql,entidad);

            DistributionResults distributionResults = getDistributionResults(
                    session, list, distributionCount, currentDate, typeDist,
                    locale, entidad, isLdap);

            if (tableName != null) {
                DBEntityDAOFactory.getCurrentDBEntityDAO().dropTableOrView(
                        tableName, entidad);
            }

            HibernateUtil.commitTransaction(tran);

            return distributionResults;
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

    public static int getDistribution(String sessionID, int typeDist,
            Integer bookId, Integer folderId, String entidad)
            throws DistributionException, SessionException, ValidationException {

        if (log.isDebugEnabled()) {
            log.debug("getDistribution for bookId [" + bookId + "] folderId ["
                    + folderId + "]");
        }

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            StringBuffer querySize = new StringBuffer();
            StringBuffer selectCount = new StringBuffer();
            selectCount.append("select count(*) from scr_distreg where ");
            switch (typeDist) {
            case DISTRIBUCION_IN_DIST: {
                querySize.append("id_arch = ");
                querySize.append(bookId);
                querySize.append(" and ");
                querySize.append("id_fdr = ");
                querySize.append(folderId);
                querySize.append(" and state = 1 and ");
                querySize.append(" (   ( type_Dest=1 and id_Dest= ");
                querySize.append(user.getId());
                querySize.append(") or ( type_Dest=2 and id_Dest=");
                querySize.append(user.getDeptid());
                querySize.append(") )");
                break;
            }
            case DISTRIBUCION_OUT_DIST: {
                querySize.append("id_arch = ");
                querySize.append(bookId);
                querySize.append(" and ");
                querySize.append("id_fdr = ");
                querySize.append(folderId);
                querySize.append(" and state = 1 and ");
                querySize.append(" (   ( type_Orig=1 and id_Orig= ");
                querySize.append(user.getId());
                querySize.append(") or ( type_Orig=2 and id_Orig=");
                querySize.append(user.getDeptid());
                querySize.append(") ) ");
                break;
            }
            default:
                throw new DistributionException(
                        DistributionException.ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED);
            }
            selectCount.append(querySize);
            int distributionCount = DBEntityDAOFactory.getCurrentDBEntityDAO()
                    .getDistributionSize(selectCount.toString(), entidad);

            HibernateUtil.commitTransaction(tran);

            return distributionCount;
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

    /**
     * Metodo que obtiene el numero de distribuciones en las que se ha visto
     * afectado el registro pasado como parametro y que ademas esta implicado el
     * usuario logeado (bien sea este como origen o destino de la distribucion)
     *
     * @param sessionID
     * @param bookId
     *            - ID del libro
     * @param folderId
     *            - ID del registro
     * @param entidad
     *
     * @return int - Numero de distribuciones para el registro indicado y el
     *         usuario logeado
     *
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static int getAllDistributionByRegisterAndUser(String sessionID,
            Integer bookId, Integer folderId, String entidad)
            throws DistributionException, SessionException, ValidationException {

        if (log.isDebugEnabled()) {
            log.debug("getDistribution for bookId [" + bookId + "] folderId ["
                    + folderId + "]");
        }

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            // obtenemos los grupos a los que pertenece el usuario
            List iUserGroupUser = getUserGroups(session, user,
                    LDAPAuthenticationPolicy.isLdap(entidad));

            // generamos la consulta
            StringBuffer selectCount = new StringBuffer();
            selectCount.append("select count(*) from scr_distreg where ");
            // obtenemos el criterio de busqueda
            StringBuffer querySize = getStringQueryAllDistributionByUserRegBook(
                    bookId, folderId, user, iUserGroupUser);

            selectCount.append(querySize);
            int distributionCount = DBEntityDAOFactory.getCurrentDBEntityDAO()
                    .getDistributionSize(selectCount.toString(), entidad);

            HibernateUtil.commitTransaction(tran);

            return distributionCount;
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

    /**
     * Metodo que genera un string con el where para obtener todas las
     * distribuciones de un registro en la que esta implicado el un usuario que
     * se pasa como parametro, bien sea como origen de la distribución o como
     * destino de la misma
     *
     * @param bookId
     *            - ID del libro
     * @param folderId
     *            - ID del registro
     * @param user
     *            - Datos del usuario
     * @param iiUserGroupUser
     *            - Listado de grupos a los que pertene el usuario
     * @return String - Where de la consulta
     */
    private static StringBuffer getStringQueryAllDistributionByUserRegBook(
            Integer bookId, Integer folderId, AuthenticationUser user,
            List iUserGroupUser) {
        StringBuffer querySize = new StringBuffer();
        querySize.append("id_arch = ");
        // buscamos por id del libro
        querySize.append(bookId);
        querySize.append(" and ");
        querySize.append("id_fdr = ");
        // por id del registro
        querySize.append(folderId);
        querySize.append(" and ");
        // y ademas de el id de usuario bien sea como origen o destino de la
        // distribucion
        querySize.append(" (   ( type_Dest=1 and id_Dest= ");
        querySize.append(user.getId());
        querySize.append(") or ( type_Dest=2 and id_Dest=");
        querySize.append(user.getDeptid());
        querySize.append(") or ( type_Orig=1 and id_Orig= ");
        querySize.append(user.getId());
        querySize.append(") or ( type_Orig=2 and id_Orig=");
        querySize.append(user.getDeptid());

        // ademas buscamos tambien por los grupos a los que pertenece el usuario
        for (Iterator it = iUserGroupUser.iterator(); it.hasNext();) {
            Integer idgrupo = (Integer) it.next();
            querySize.append(") or ( type_Dest=3 and id_Dest=");
            querySize.append(idgrupo);
            querySize.append(") or ( type_Orig=3 and id_Orig=");
            querySize.append(idgrupo);
        }

        querySize.append(") ) ");
        return querySize;
    }

    public static int countDistribution(String sessionID, int state,
            int typeDist, String distWhere, String regWhere, boolean oficAsoc,
            String entidad) throws DistributionException, SessionException,
            ValidationException {

        return countDistribution(sessionID, state, typeDist, distWhere,
                regWhere, oficAsoc, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad));
    }

    public static int countDistribution(String sessionID, int state,
            int typeDist, String distWhere, String regWhere, boolean oficAsoc,
            String entidad, List bookList) throws DistributionException,
            SessionException, ValidationException {

        return countDistribution(sessionID, state, typeDist, distWhere,
                regWhere, oficAsoc, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad), bookList, null);
    }

    public static int countDistribution(String sessionID, int state,
            int typeDist, String distWhere, String regWhere, boolean oficAsoc,
            String entidad, boolean isLdap) throws DistributionException,
            SessionException, ValidationException {

        return countDistribution(sessionID, state, typeDist, distWhere,
                regWhere, oficAsoc, entidad, isLdap, null, null);
    }

    public static int countDistribution(String sessionID, int state,
            int typeDist, String distWhere, String regWhere, boolean oficAsoc,
            String entidad, boolean isLdap, List bookList, String orderBy)
            throws DistributionException, SessionException, ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            List iUserGroupUser = getUserGroups(session, user, isLdap);

            StringBuffer querySize = getDistributionQuerySize(session, user,
                    iUserGroupUser, typeDist, distWhere, bookList, oficAsoc,
                    entidad);

            StringBuffer finalWhere = new StringBuffer();
            String tableName = null;

            //Cuando se realizan busquedas con criterios de registro
            if (!regWhere.equals("") || (StringUtils.isNotBlank(orderBy))) {
                String selectCriteria = "select distinct id_arch from scr_distreg where ";
                List idArchs = DBEntityDAOFactory.getCurrentDBEntityDAO()
                        .getIdArchDistribution(
                                selectCriteria + querySize.toString(), entidad);
                tableName = getTableName(user, idArchs);

                finalWhere.append(getDistributionFinalWhere(idArchs, regWhere,
                        querySize.toString(), tableName, entidad));
            } else {
                finalWhere.append(querySize.toString());
                finalWhere.append(" order by id");
            }

            StringBuffer selectCount = new StringBuffer();
            selectCount.append("select count(*) from scr_distreg where ");
            selectCount.append(finalWhere.substring(0,
                    finalWhere.indexOf("order")));
            int distributionCount = DBEntityDAOFactory.getCurrentDBEntityDAO()
                    .getDistributionSize(selectCount.toString(), entidad);

            return distributionCount;
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

    /**
     * Metodo que genera una nueva distribucion
     *
     * @param sessionID
     * @param bookId
     *            - Id del libro
     * @param listIdsRegister
     *            - Listado de id de Registros
     * @param userType
     *            - Tipo de destino de la distribucion
     * @param userId
     *            - Id del destino de la distribucin
     * @param messageForUser
     *            - Mensaje de la distribucion
     * @param locale
     * @param entidad
     * @return
     * @throws BookException
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static String createDistribution(String sessionID, Integer bookId,
            List listIdsRegister, Integer userType, Integer userId,
            String messageForUser, Locale locale, String entidad)
            throws BookException, DistributionException, SessionException,
            ValidationException {
        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Validator.validate_Integer(bookId, ValidationException.ATTRIBUTE_BOOK);

        StringBuffer result = new StringBuffer();

        Transaction tran = null;
        try {

            boolean distributionManualBookOut = Configurator
                    .getInstance()
                    .getPropertyBoolean(
                            ConfigurationKeys.KEY_SERVER_DISTRIBUTION_MANUAL_BOOK_OUT);

            if (Repository.getInstance(entidad).isInBook(bookId).booleanValue()
                    || (Repository.getInstance(entidad).isOutBook(bookId)
                            .booleanValue() && distributionManualBookOut)) {
                Session session = HibernateUtil.currentSession(entidad);
                tran = session.beginTransaction();

                // Recuperamos la sesión
                CacheBag cacheBag = CacheFactory.getCacheInterface()
                        .getCacheEntry(sessionID);

                // Es necesario tener el libro abierto para consultar su
                // contenido.
                if (!cacheBag.containsKey(bookId)) {
                    throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
                }
                AuthenticationUser user = (AuthenticationUser) cacheBag
                        .get(HIBERNATE_Iuseruserhdr);
                ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

                lockListFolderDist(session, bookId, listIdsRegister, user,
                        entidad);

                result.append(createDistribution(session, sessionID, bookId, 2,
                        scrOfic.getDeptid(), userId, userType, messageForUser,
                        listIdsRegister, user, scrOfic, locale, entidad, 0));

                HibernateUtil.commitTransaction(tran);
            }
        } catch (BookException bE) {
            HibernateUtil.rollbackTransaction(tran);
            throw bE;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (EventException eE) {
            HibernateUtil.rollbackTransaction(tran);
            throw eE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            log.error("Impossible to create distribution for the session ["
                    + sessionID + "] and bookID [" + bookId + "]", e);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }

        return result.toString();

    }

    /**
     * Metodo que genera una nueva distribucion
     *
     * @param sessionID
     * @param bookId
     *            - Id del libro
     * @param listIdsRegister
     *            - Listado de id de Registros
     * @param userType
     *            - Tipo de destino de la distribucion
     * @param userId
     *            - Id del destino de la distribucin
     * @param messageForUser
     *            - Mensaje de la distribucion
     * @param locale
     * @param entidad
     * @return
     * @throws BookException
     * @throws DistributionException
     * @throws SessionException
     * @throws ValidationException
     */
    public static String createDistribution(String sessionID, Integer bookId,
            List listIdsRegister, Integer senderType, Integer senderId,
            Integer userType, Integer userId, String messageForUser,
            Locale locale, String entidad) throws BookException,
            DistributionException, SessionException, ValidationException {
        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Validator.validate_Integer(bookId, ValidationException.ATTRIBUTE_BOOK);

        StringBuffer result = new StringBuffer();

        Transaction tran = null;
        try {

            boolean distributionManualBookOut = Configurator
                    .getInstance()
                    .getPropertyBoolean(
                            ConfigurationKeys.KEY_SERVER_DISTRIBUTION_MANUAL_BOOK_OUT);

            if (Repository.getInstance(entidad).isInBook(bookId).booleanValue()
                    || (Repository.getInstance(entidad).isOutBook(bookId)
                            .booleanValue() && distributionManualBookOut)) {
                Session session = HibernateUtil.currentSession(entidad);
                tran = session.beginTransaction();

                // Recuperamos la sesión
                CacheBag cacheBag = CacheFactory.getCacheInterface()
                        .getCacheEntry(sessionID);

                // Es necesario tener el libro abierto para consultar su
                // contenido.
                if (!cacheBag.containsKey(bookId)) {
                    throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
                }
                AuthenticationUser user = (AuthenticationUser) cacheBag
                        .get(HIBERNATE_Iuseruserhdr);
                ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

                lockListFolderDist(session, bookId, listIdsRegister, user,
                        entidad);

                result.append(createDistribution(session, sessionID, bookId,
                        senderType, senderId, userId, userType, messageForUser,
                        listIdsRegister, user, scrOfic, locale, entidad, 0));

                HibernateUtil.commitTransaction(tran);
            }
        } catch (BookException bE) {
            HibernateUtil.rollbackTransaction(tran);
            throw bE;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (EventException eE) {
            HibernateUtil.rollbackTransaction(tran);
            throw eE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            log.error("Impossible to create distribution for the session ["
                    + sessionID + "] and bookID [" + bookId + "]", e);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }

        return result.toString();

    }

    public static Integer validateQueryDistribution(String sessionID,
            String code, Integer validation, String entidad)
            throws DistributionException, SessionException, ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Transaction tran = null;
        Integer id = null;
        List privOrgs = null;

        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            Iuserusertype userusertype = (Iuserusertype) cacheBag
                    .get(HIBERNATE_Iuserusertype);
            ScrOfic scrofic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
            if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
                privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO()
                        .getPrivOrgs(scrofic.getId().intValue(), entidad);
            }
            if (validation.intValue() == 9 || validation.intValue() == 10) {
                try {
                    ScrOfic scrOfic = ISOfficesValidator.getOffice(session,
                            code);
                    id = new Integer(scrOfic.getDeptid());
                } catch (ValidationException e) {
                }
            }
            if (validation.intValue() == 1) {
                try {
                    ScrOrg scrOrg = ISUnitsValidator.getUnit(session, code,
                            false, privOrgs);
                    id = scrOrg.getId();
                } catch (ValidationException e) {
                }
            }
            if (validation.intValue() == 6) {
                try {
                    ScrCa scrCa = ISSubjectsValidator.getSubject(session, code,
                            false);
                    id = scrCa.getId();
                } catch (ValidationException e) {
                }
            }

            HibernateUtil.commitTransaction(tran);
            return id;
        } catch (DistributionException e) {
            HibernateUtil.rollbackTransaction(tran);
            throw e;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            log.error("Impossible to validate distribution query [" + sessionID
                    + "]", e);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }
    }

    public static List rejectDistribution(String sessionID, List ids,
            String remarks, int state, int firstRow, int maxResults,
            int typeDist, String distWhere, String regWhere, Locale locale,
            String entidad) throws DistributionException, SessionException,
            ValidationException {

        return rejectDistribution(sessionID, ids, remarks, state, firstRow,
                maxResults, typeDist, distWhere, regWhere, locale, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad), null);
    }

    public static List rejectDistribution(String sessionID, List ids,
            String remarks, int state, int firstRow, int maxResults,
            int typeDist, String distWhere, String regWhere, Locale locale,
            String entidad, boolean isLdap, String orderBy) throws DistributionException,
            SessionException, ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Transaction tran = null;

        try {
            DistributionResults distributionResults = getDistribution(
                    sessionID, state, firstRow, maxResults, typeDist,
                    distWhere, regWhere, true, locale, entidad, isLdap, orderBy);

            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            List archidFdr = new ArrayList();
            archidFdr
                    .addAll(lockFolderDistById(session, user, ids,
                            distributionResults.getResults(), archidFdr, true,
                            entidad));

            for (Iterator it = ids.iterator(); it.hasNext();) {
                Integer ids1 = (Integer) it.next();
                ScrDistreg scrDistReg = (ScrDistreg) session.load(
                        ScrDistreg.class, ids1);
                if (scrDistReg.getState() == ISDistribution.STATE_PENDIENTE
                        || scrDistReg.getState() == ISDistribution.STATE_ACEPTADO) {

                    scrDistReg = updateDistReg(session, user, sessionID,
                            DISTRIBUTION_REJECT_EVENT, scrDistReg,
                            ISDistribution.STATE_RECHAZADO, ids1, remarks,
                            entidad);

                    DBEntityDAOFactory.getCurrentDBEntityDAO()
                            .deleteDistAccept(
                                    new Integer(scrDistReg.getIdArch()),
                                    scrDistReg.getIdFdr(), entidad);
                } else {
                    unlockDistRegs(session, tran, user, archidFdr);
                }
            }

            HibernateUtil.commitTransaction(tran);
            return archidFdr;
        } catch (DistributionException e) {
            HibernateUtil.rollbackTransaction(tran);
            throw e;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (EventException eE) {
            HibernateUtil.rollbackTransaction(tran);
            throw eE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            log.error("Impossible to reject the distribution for the session ["
                    + sessionID + "]", e);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }
    }

    public static List saveRemarks(String sessionID, int id, String remarks,
            String entidad) throws DistributionException, SessionException,
            ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Transaction tran = null;

        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            ScrDistreg distReg = (ScrDistreg) session.load(ScrDistreg.class,
                    new Integer(id));

            if (!lockFolderDist(session, user,
                    new Integer(distReg.getIdArch()), distReg.getIdFdr(),
                    entidad)) {
                throw new DistributionException(
                        DistributionException.ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER);
            }

            List ids = new ArrayList();
            ids.add(new Integer(id));
            lockScrDistReg(ids, entidad);

            distReg.setMessage(remarks);
            session.update(distReg);

            List archidFdr = new ArrayList();
            archidFdr.add(distReg);

            HibernateUtil.commitTransaction(tran);
            return archidFdr;
        } catch (DistributionException e) {
            HibernateUtil.rollbackTransaction(tran);
            throw e;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            log.error(
                    "Impossible to save message the distribution for the session ["
                            + sessionID + "]", e);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }
    }

    public static List saveDistribution(String sessionID, List ids, int state,
            int firstRow, int maxResults, int typeDist, String distWhere,
            String regWhere, Locale locale, String entidad)
            throws DistributionException, SessionException, ValidationException {

        return saveDistribution(sessionID, ids, state, firstRow, maxResults,
                typeDist, distWhere, regWhere, locale, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad),null);
    }

    public static List saveDistribution(String sessionID, List ids, int state,
            int firstRow, int maxResults, int typeDist, String distWhere,
            String regWhere, Locale locale, String entidad, boolean isLdap, String orderBy)throws DistributionException, SessionException, ValidationException{
	return saveDistribution(sessionID, ids, state, firstRow, maxResults, typeDist, distWhere, regWhere, locale, entidad, isLdap, orderBy, null);
    }

    public static List saveDistribution(String sessionID, List ids, int state,
            int firstRow, int maxResults, int typeDist, String distWhere,
            String regWhere, Locale locale, String entidad, boolean isLdap, String orderBy, String remarks)
            throws DistributionException, SessionException, ValidationException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Transaction tran = null;

        try {
            DistributionResults distributionResults = getDistribution(
                    sessionID, state, firstRow, maxResults, typeDist,
                    distWhere, regWhere, true, locale, entidad, isLdap, orderBy);

            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            List archidFdr = new ArrayList();
            archidFdr
                    .addAll(lockFolderDistById(session, user, ids,
                            distributionResults.getResults(), archidFdr, true,
                            entidad));

            for (Iterator it = ids.iterator(); it.hasNext();) {
                Integer ids1 = (Integer) it.next();
                ScrDistreg scrDistReg = (ScrDistreg) session.load(
                        ScrDistreg.class, ids1);
                if (scrDistReg.getState() != ISDistribution.STATE_ACEPTADO) {
                    unlockDistRegs(session, tran, user, archidFdr);
                } else {
                    scrDistReg = updateDistReg(session, user, sessionID,
                            DISTRIBUTION_ARCHIVE_EVENT, scrDistReg,
                            ISDistribution.STATE_ARCHIVADO, ids1, remarks, entidad);
                }
            }

            HibernateUtil.commitTransaction(tran);
            return archidFdr;
        } catch (DistributionException e) {
            HibernateUtil.rollbackTransaction(tran);
            throw e;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (EventException eE) {
            HibernateUtil.rollbackTransaction(tran);
            throw eE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            log.error("Impossible to save the distribution for the session ["
                    + sessionID + "]", e);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_SAVE_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }
    }

    public static Object acceptDistribution(String sessionID, List ids,
            int state, int firstRow, int maxResults, int typeDist,
            Integer bookId, List createPermBooks,
            DistributionResults distributionResults, String distWhere,
            String regWhere, Integer launchDistOutRegister, Locale locale,
            String entidad) throws BookException, DistributionException,
            SessionException, ValidationException {
        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        Integer idBook = new Integer(-1);
        List archidFdr = new ArrayList();
        AuthenticationUser user = null;
        int error = 0;

        Map outputInputBookType = new HashMap();
        Map newRegisterIdOutInBook = new HashMap();
        Map distIds = distributionResults.getResults();
        Map scrDistRegIds = new HashMap();
        Map regStates = new HashMap();

        try {
            Map result = new HashMap();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            user = (AuthenticationUser) cacheBag.get(HIBERNATE_Iuseruserhdr);

            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            archidFdr.addAll(lockFolderDistById(session, user, ids, distIds,
                    archidFdr, false, entidad));

            outputInputBookType = getOutputInputBookType(session, entidad, ids,
                    distIds, outputInputBookType, user);

            // Comprobamos los permisos
            if ((bookId.intValue() == 0)) {
                if (!outputInputBookType.isEmpty()) {
                    if (createPermBooks.isEmpty()) {
                        throw new DistributionException(
                                DistributionException.ERROR_NO_REGISTER_PERMISSION_ABOUT_ANY_BOOK);
                    } else if (createPermBooks.size() == 1) {
                        idBook = (Integer) createPermBooks.get(0);
                    } else {
                        result.put(idBook, archidFdr);
                        return result;
                    }
                }
            } else {
                idBook = bookId;
            }

            // Generación ID de registro
            newRegisterIdOutInBook = generateNewRegsId(session, idBook,
                    outputInputBookType, ids, user.getId(), entidad);

            HibernateUtil.commitTransaction(tran);
            tran = session.beginTransaction();

            lockScrDistReg(ids, entidad);

            scrDistRegIds = getScrDistRegIds(session, ids);
            regStates.putAll(getRegStates(session, ids, regStates));

            HibernateUtil.commitTransaction(tran);
        } catch (Exception e) {
            log.warn(e);
            error = 1;
            HibernateUtil.rollbackTransaction(tran);
            throw new DistributionException(
                    DistributionException.ERROR_CANNOT_ACCEPT_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
            closeDistRegs(sessionID, archidFdr, error, entidad);
        }

        createNewRegister(sessionID, idBook, launchDistOutRegister, locale,
                newRegisterIdOutInBook, outputInputBookType, scrDistRegIds,
                regStates, archidFdr, entidad);

        createRegsAsoc(sessionID, newRegisterIdOutInBook, ids, archidFdr,
                idBook, entidad);

        updateAcceptDistribution(sessionID, ids, archidFdr, entidad);

        unlockDistRegsById(sessionID, ids, entidad);

        return archidFdr;
    }

    public static void changeDistribution(String sessionID, List dis,
            String code, int typeDist, Integer launchDistOutRegister,
            Integer canDestWithoutList, Locale locale, String entidad, boolean isLdap)
            throws BookException, SessionException, ValidationException,
            DistributionException {
        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        ScrOrg scrOrg = null;
        Integer id = null;
        Transaction tran = null;
        List scrs = new ArrayList();
        List distList = null;

        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos la sesión
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);
            ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

            Iuserusertype userusertype = (Iuserusertype) cacheBag
                    .get(HIBERNATE_Iuserusertype);

            scrOrg = getScrOrg(session, userusertype, scrOfic, code, entidad);

            // se busca las listas de distribucion asociadas a la unidad
            // administrativa destino
            // que no tengan como destino al propio usuario o al departamento
            // del propio usuario
            Criteria criteria = session.createCriteria(ScrDistlist.class);
            criteria.add(Expression.eq("idOrgs", scrOrg.getId()));
            criteria.add(Expression.not(Expression.and(
                    Expression.eq("typeDest", new Integer(1)),
                    Expression.eq("idDest", user.getId()))));
            criteria.add(Expression.not(Expression.and(Expression.eq(
                    "typeDest", new Integer(2)), Expression.eq("idDest",
                    new Integer(user.getDeptid().intValue())))));
            distList = criteria.list();

            for (Iterator it = dis.iterator(); it.hasNext();) {
                id = (Integer) it.next();
                ScrDistreg scrDistReg = (ScrDistreg) session.load(
                        ScrDistreg.class, id);

                scrs.add(scrDistReg);
                if (!lockFolderDist(session, user,
                        new Integer(scrDistReg.getIdArch()),
                        scrDistReg.getIdFdr(), entidad)) {
                    throw new BookException(
                            BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER);
                }
                DBEntityDAOFactory.getCurrentDBEntityDAO().lockScrDistReg(
                        id.intValue(), entidad);
            }
            HibernateUtil.commitTransaction(tran);
        } catch (BookException bE) {
            HibernateUtil.rollbackTransaction(tran);
            throw bE;
        } catch (SessionException sE) {
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (DistributionException dE) {
            HibernateUtil.rollbackTransaction(tran);
            throw dE;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            throw new BookException(
                    BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }

        updateChangeDistribution(sessionID, scrs, distList, typeDist, id,
                canDestWithoutList, entidad);

        updateFolderChangeDistribution(sessionID, scrs, scrOrg,
                launchDistOutRegister, locale, entidad);

        //Insertamos el destino actual de la distribución en BBDD
        insertDestinoActualChangeDistribution(sessionID, entidad, isLdap, scrs);

        unlockDistRegsById(sessionID, dis, entidad);
    }


    /**
     * Elimina una distribución.
     *
     * Método transaccional.
     *
     * @param session
     * @param distributionId
     * @throws HibernateException
     */
    public static void deleteDistribution(String entidad, Integer distributionId)
            throws HibernateException {
        Session session = HibernateUtil.currentSession(entidad);
        Transaction tran = session.beginTransaction();

        ScrDistreg scrDistReg = (ScrDistreg) session.load(ScrDistreg.class,
                distributionId);

        session.delete(scrDistReg);
        HibernateUtil.commitTransaction(tran);

        log.info("Eliminada la distribución [" + distributionId + "] de BD");
    }

    public static void distribute(boolean isUpdate, Session session,
            Integer bookID, int fdrid, int distributionType, Integer fld8,
            Integer userID, Integer deptId, String userName, String entidad,
            Locale locale, Integer idDistFather) throws HibernateException, BookException,
            SQLException, Exception {

        if (isUpdate) {
            DBEntityDAOFactory.getCurrentDBEntityDAO()
                    .deleteDistributeForUpdate(bookID.intValue(), fdrid,
                            deptId.intValue(), entidad,
                            ISDistribution.STATE_PENDIENTE);

        }

        Timestamp currentDate = DBEntityDAOFactory.getCurrentDBEntityDAO()
                .getDBServerDate(entidad);
        String message = RBUtil.getInstance(locale).getProperty(
                ISicresKeys.DISTRIBUCION_AUTOMATICA_MENSAJE);
        boolean isCaseSensitive = isDataBaseCaseSensitive(entidad);
        if (isCaseSensitive) {
            message = message.toUpperCase();
        }
        switch (distributionType) {
        case 1: { // Distribución propia

            // se busca las listas de distribucion asociadas a la unidad
            // administrativa destino
            // que no tengan como destino al propio usuario o al departamento
            // del propio usuario
            Criteria criteria = session.createCriteria(ScrDistlist.class);
            criteria.add(Expression.eq("idOrgs", fld8));
            criteria.add(Expression.not(Expression.and(
                    Expression.eq("typeDest", new Integer(1)),
                    Expression.eq("idDest", userID))));
            criteria.add(Expression.not(Expression.and(
                    Expression.eq("typeDest", new Integer(2)),
                    Expression.eq("idDest", deptId))));
            List distList = criteria.list();
            if (distList != null && !distList.isEmpty()) {
                ScrDistlist dirList = null;
                for (Iterator it = distList.iterator(); it.hasNext();) {
                    dirList = (ScrDistlist) it.next();
                    insertDistribute(session, bookID, fdrid,
                            dirList.getTypeDest(), dirList.getIdDest(),
                            currentDate, 2, deptId.intValue(), userName,
                            userID, distributionType, message, entidad,idDistFather);
                }
            }
            break;
        }
        case 2: { // Distribución externa
            insertDistribute(session, bookID, fdrid, 4, fld8.intValue(),
                    currentDate, 2, deptId.intValue(), userName, userID,
                    distributionType, message, entidad,idDistFather);
            break;
        }
        default: {
            throw new BookException(
                    BookException.ERROR_DISTRIBUTION_TYPE_NOT_SUPPORTED);
        }
        }
    }

    public static String getOrigDestDescription(String sessionID,
            ScrDistreg scr, boolean isOrigDist, String entidad)
            throws ValidationException, DistributionException {

        return getOrigDestDescription(sessionID, scr, isOrigDist, entidad,
                LDAPAuthenticationPolicy.isLdap(entidad));
    }

    public static String getOrigDestDescription(String sessionID,
            ScrDistreg scr, boolean isOrigDist, String entidad, boolean isLdap)
            throws ValidationException, DistributionException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        String result = "";
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            if (isOrigDist) {
                result = getDistributionSourceDescription(session, scr, isLdap);
            } else {
                result = getDistributionTargetDescription(session, scr, isLdap);
            }

            HibernateUtil.commitTransaction(tran);

            return result;
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction(tran);
            throw new DistributionException(
                    DistributionException.ERROR_DISTRIBUTION_REGISTER_NOT_DIST_LIST);
        } finally {
            HibernateUtil.closeSession(entidad);
        }
    }

    /**
     * Método que obtiene el destino actual de la distribución
     * @param sessionID - Id de la sesión
     * @param idDist - ID de la distribución
     * @param entidad
     * @return String - Literal con los destinos de la distribución
     * @throws ValidationException
     * @throws DistributionException
     */
	public static String getDestinoActualDistribucion(String sessionID,
			Integer idDist, String entidad) throws ValidationException,
			DistributionException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID,
				ValidationException.ATTRIBUTE_SESSION);

		Transaction tran = null;
		String result = "";
		try {
			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();

			ScrDistribucionActual scrDistribucionActual = (ScrDistribucionActual) ISicresQueries
					.getScrDistribucionActual(session, idDist);

			if (scrDistribucionActual != null) {
				result = scrDistribucionActual.getDist_actual();
			}

			HibernateUtil.commitTransaction(tran);

			return result;
		} catch (Exception e) {
			log.error(e);
			HibernateUtil.rollbackTransaction(tran);
			throw new DistributionException(
					DistributionException.ERROR_CANNOT_OBTAIN_DISTRIBUTION);
		} finally {
			HibernateUtil.closeSession(entidad);
		}
	}

    public static List getDtrFdrResults(String sessionID, Integer bookID,
            int folderID, String entidad, boolean isLdap) throws BookException,
            SessionException, ValidationException {
        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);
        Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);

        Transaction tran = null;
        List result = new ArrayList();
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

            List list = ISicresQueries.getScrDistReg(session, bookID, folderID);

            ScrDistreg scr = null;
            DtrFdrResults dtrFdrResults = null;
            if (list != null && !list.isEmpty()) {
                for (Iterator it = list.iterator(); it.hasNext();) {
                    scr = (ScrDistreg) it.next();
                    dtrFdrResults = new DtrFdrResults();
                    dtrFdrResults.setScrDistReg(scr);
                    dtrFdrResults
                            .setSourceDescription(getDistributionSourceDescription(
                                    session, scr, isLdap));
                    dtrFdrResults
                            .setTargetDescription(getDistributionTargetDescription(
                                    session, scr, isLdap));

                    dtrFdrResults.setFlowProcess(!ISicresQueries.getScrProcReg(
                            session, scr.getId()).isEmpty());
                    dtrFdrResults.setScrDistRegState(ISicresQueries
                            .getScrDistregstate(session, scr.getId()));
                    result.add(dtrFdrResults);
                }
            }
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
            log.error("Impossible to close the book [" + bookID
                    + "] and fdrid [" + folderID + "] for the session ["
                    + sessionID + "]", e);
            throw new BookException(
                    BookException.ERROR_CANNOT_FIND_DISTRIBUTION_HISTORY);
        } finally {
            HibernateUtil.closeSession(entidad);
        }
    }

    /**
     * Metodo que redistribuye una distribución
     *
     * @param sessionID
     * @param dis
     *            - Listado de id de la distribuciones seleccionadas para
     *            redistribuir
     * @param userId
     *            - Id del nuevo destino de la distribucion
     * @param typeDist
     *            - Tipo de la distribucion (Entrada/Salida)
     * @param canDestWithoutList
     *            - Indica si se puede distribuir a un destino sin lista de
     *            distribución
     * @param locale
     *            - Idioma
     * @param entidad
     * @param messageForUser
     *            - Mensaje/motivo de la distribucion
     * @param userType
     *            - Tipo del nuevo destino de la distribucion (grupo, usuario,
     *            departamento)
     * @param isLdap - Identifica si la autenticación utilizada es ldap
     *
     * @throws Exception
     */
    public static void redistributionDistribution(String sessionID,
            Locale locale, String entidad, List dis, Integer userId,
            int typeDist, Integer canDestWithoutList, String messageForUser,
            Integer userType, boolean isLdap) throws ValidationException,
            DistributionException, SessionException, BookException {

        Validator.validate_String_NotNull_LengthMayorZero(sessionID,
                ValidationException.ATTRIBUTE_SESSION);

        Transaction tran = null;
        Integer id = null;

        try {
            // Recuperamos la sesión
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // obtenemos datos de la cache
            CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
                    sessionID);
            AuthenticationUser user = (AuthenticationUser) cacheBag
                    .get(HIBERNATE_Iuseruserhdr);

            ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);
            // validamos que la oficina del usuario no sea nula
            if (scrOfic == null) {
                log.error("La oficina del usuario es nula");
                throw new DistributionException(
                        DistributionException.ERROR_CANNOT_REJECT_DISTRIBUTION);
            }

            Date currentDate = new Date(DBEntityDAOFactory
                    .getCurrentDBEntityDAO().getDBServerDate(entidad).getTime());

            // generamos un array auxiliar con el destino de la nueva
            // distribucion
            List distList = new ArrayList();
            distList.add(userId);

            // recorremos el listado de los id de las distribuciones y
            // almacenamos el resto de datos de la distribucion en un listado
            // con objetos
            // scrDistReg - Se realiza de esta forma para evitar problemas con
            // el cierre de la session
            for (Iterator it = dis.iterator(); it.hasNext();) {
                id = (Integer) it.next();
                ScrDistreg scrDistReg = (ScrDistreg) session.load(
                        ScrDistreg.class, id);
                try {
                    // bloqueamos el registro
                    if (!lockFolderDist(session, user,
                            new Integer(scrDistReg.getIdArch()),
                            scrDistReg.getIdFdr(), entidad)) {
                        throw new BookException(
                                BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION_LOCKREGISTER);
                    }

                    // invocamos al metodo que redistribuye la distribucion
                    redistributionDistribution(session, sessionID, locale,
                            scrDistReg, user, canDestWithoutList, typeDist,
                            scrOfic, currentDate, userType, distList,
                            messageForUser, entidad, isLdap);

                } finally {
                    // desbloqueamos los registros bloqueados
                    unlock(session, scrDistReg.getIdArch(),
                            scrDistReg.getIdFdr(), user);
                }

            }
            HibernateUtil.commitTransaction(tran);
        } catch (BookException bE) {
            log.error("Impossible create the redistribution for the session ["
                    + sessionID + "].", bE);
            HibernateUtil.rollbackTransaction(tran);
            throw bE;
        } catch (SessionException sE) {
            log.error("Impossible create the redistribution for the session ["
                    + sessionID + "].", sE);
            HibernateUtil.rollbackTransaction(tran);
            throw sE;
        } catch (DistributionException dE) {
            log.error("Impossible create the redistribution for the session ["
                    + sessionID + "].", dE);
            HibernateUtil.rollbackTransaction(tran);
            throw dE;
        } catch (Exception e) {
            log.error("Impossible create the redistribution for the session ["
                    + sessionID + "].", e);
            HibernateUtil.rollbackTransaction(tran);
            throw new BookException(
                    BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
        } finally {
            HibernateUtil.closeSession(entidad);
        }

    }

}
