package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.support.DataAccessUtils;

import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrCact;
import com.ieci.tecdoc.common.invesicres.ScrCaeu;
import com.ieci.tecdoc.common.invesicres.ScrCagl;
import com.ieci.tecdoc.common.keys.HibernateKeys;

import es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO;
import es.ieci.tecdoc.isicres.api.business.exception.LibroException;
import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;

public class TipoAsuntoLegacyDAOImpl extends IsicresBaseHibernateDAOImpl
		implements TipoAsuntoDAO {

	private static final Logger log = Logger
			.getLogger(TipoAsuntoLegacyDAOImpl.class);

	private static final int AsuntoValidoAllOficinas = 1;
	private static final Integer Asunto_Enabled = new Integer(1);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#findAllAsuntos(
	 * java.util.Locale, java.lang.String,
	 * es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO)
	 */
	public List findAllAsuntos(Locale locale, String idOficina,
			BaseCriterioBusquedaVO baseCriterioBusqueda) {
		List result = new ArrayList();

		try {
			// Obtenemos la query a ejecutar segun la disponibilidad del asunto
			// y la oficina del usuario
			StringBuffer query = getAllAsuntosQuery(Asunto_Enabled, idOficina);

			// ejecutamos la consulta
			List asuntos = executeCriteriaReturnScrCaList(locale, query,
					baseCriterioBusqueda);

			TipoAsuntoVO tipoAsunto = null;
			ScrCa scrCa = null;

			for (Iterator it = asuntos.iterator(); it.hasNext();) {
				scrCa = new ScrCa();

				// Copiamos las propiedades del objeto it a un objeto tipo scrCa
				BeanUtils.copyProperties(it.next(), scrCa);

				// adaptamos el objeto scrCa a un tipoAsunto
				tipoAsunto = tipoAsuntoAdapter(scrCa);

				result.add(tipoAsunto);
			}

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.findAllAsuntos: no se puede recuperar todos los asuntos disponibles");
			throw new LibroException(
					"No se puede recuperar todos los tipos de asunto disponibles ",
					e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#findAllAsuntosCount
	 * (java.util.Locale, java.lang.String)
	 */
	public int findAllAsuntosCount(Locale locale, String idOficina) {
		int result = 0;

		try {
			// Obtenemos la query a ejecutar segun la disponibilidad del asunto
			// y la oficina del usuario
			StringBuffer query = new StringBuffer();

			query.append("ENABLED = ");
			query.append(Asunto_Enabled);
			query
					.append(" AND ((ALL_OFICS = 1) OR ((ALL_OFICS = 0)  AND (ID IN (SELECT SCR.idMatter FROM "
							+ HibernateKeys.HIBERNATE_ScrCaofic
							+ " AS SCR WHERE SCR.scrOfic.id = ");
			query.append(idOficina);
			query.append("))))");

			result = executeCriteriaReturnCountList(locale, query);

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.findAllAsuntosCount: no se puede recuperar el contador de todos los asuntos disponibles");
			throw new LibroException(
					"No se puede recuperar el contador de todos los asuntos disponibles ",
					e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#
	 * getTipoAsuntoAllOficOrIdOfic(java.util.Locale, java.lang.String,
	 * java.lang.String)
	 */
	public TipoAsuntoVO getTipoAsuntoAllOficOrIdOfic(Locale locale,
			String codigoAsunto, String idOficina) {
		TipoAsuntoVO result = null;
		ScrCa scrCa = null;

		try {
			// llamamos al metodo que compone y ejecuta la consulta
			scrCa = getAsuntosByCodigo(locale, codigoAsunto);

			if (scrCa != null) {
				// obtenemos un Asunto si es valido para todas las oficinas o al
				// menos para
				// la que se pasa por parametro
				result = validateAsuntoByOficina(locale, scrCa, idOficina);
			}

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.getTipoAsuntoAllOficOrIdOfic: no se puede recuperar el asunto ["
							+ codigoAsunto
							+ "] para la oficina ["
							+ idOficina
							+ "]");
			throw new LibroException("No se puede recuperar el asunto ["
					+ codigoAsunto + "] para la oficina [" + idOficina + "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#getTipoAsuntoByCodigo
	 * (java.util.Locale, java.lang.String, java.lang.String)
	 */
	public TipoAsuntoVO getTipoAsuntoByCodigo(Locale locale, String idOficina,
			String codigoAsunto) {
		TipoAsuntoVO result = null;
		try {
			// componemos y ejecutamos la consulta
			ScrCa scrCa = getAsuntosByCodigo(locale, codigoAsunto);

			if (scrCa != null) {
				// comprobamos que el asunto es valido para todas las oficinas o
				// al menos para
				// la que se pasa por parametro
				result = validateAsuntoByOficina(locale, scrCa, idOficina);
			}

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.getTipoAsuntoByCodigo: no se puede recuperar el tipo de asunto con codigo: "
							+ codigoAsunto
							+ "] para la oficina ["
							+ idOficina
							+ "]");
			throw new LibroException(
					"No se puede recuperar el tipo de asunto con codigo ["
							+ codigoAsunto + "] para la oficina [" + idOficina
							+ "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#getTipoAsuntoById
	 * (java.util.Locale, java.lang.String)
	 */
	public TipoAsuntoVO getTipoAsuntoById(Locale locale, String id) {
		TipoAsuntoVO result = null;
		try {
			// componemos y ejecutamos la consulta
			ScrCa scrCa = getAsuntosById(locale, new Integer(id));

			if (scrCa != null) {
				result = tipoAsuntoAdapter(scrCa);
			}

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.getTipoAsuntoById: no se puede recuperar el tipo de asunto con id: "
							+ id);
			throw new LibroException(
					"No se puede recuperar el tipo de asunto con idAsunto ["
							+ id + "] ", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#
	 * findTipoAsuntoByCriterioWhereSql(java.util.Locale, java.lang.String,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO)
	 */
	public List findTipoAsuntoByCriterioWhereSql(Locale locale,
			String idOficina, CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda) {
		List result = new ArrayList();

		try {
			// obtenemos la consulta a ejecutar
			StringBuffer query = getAsuntosWhereQuery(locale, criterioBusqueda
					.getSql());

			// generamos el baseCriteriaBusqueda para cotar la consultar
			BaseCriterioBusquedaVO baseCriterioBusquedaVO = new BaseCriterioBusquedaVO();
			baseCriterioBusquedaVO.setLimit(criterioBusqueda.getLimit());
			baseCriterioBusquedaVO.setOffset(criterioBusqueda.getOffset());

			// buscamos los tipos de asuntos que no estan dados de baja
			List asuntos = executeCriteriaReturnScrCaList(locale, query,
					baseCriterioBusquedaVO);

			ScrCa scrCa = null;
			TipoAsuntoVO tipoAsunto = null;
			for (Iterator it = asuntos.iterator(); it.hasNext();) {
				scrCa = new ScrCa();
				// Copiamos las propiedades del objeto it a un objeto tipo scrCa
				BeanUtils.copyProperties(it.next(), scrCa);

				// comprobamos si el asunto es valido para todas las oficinas o
				// por lo menos para la oficina pasada como parametro
				tipoAsunto = validateAsuntoByOficina(locale, scrCa, idOficina);

				if (tipoAsunto != null) {
					result.add(tipoAsunto);
				}
			}

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.findTipoAsuntoByCriterioWhereSql: no se puede recuperar los asuntos con la condicion: "
							+ criterioBusqueda.getSql());
			throw new LibroException(
					"No se puede recuperar los asuntos con la condicion: "
							+ criterioBusqueda.getSql(), e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO#
	 * findTipoAsuntoByCriterioWhereSqlCount(java.util.Locale, java.lang.String,
	 * java.lang.String)
	 */
	public int findTipoAsuntoByCriterioWhereSqlCount(Locale locale,
			String idOficina, String where) {
		int result = 0;
		try {
			// obtenemos la consulta a ejecutar
			StringBuffer query = getAsuntosWhereQuery(locale, where);

			// obtenemos el valor de la consulta
			result = executeCriteriaReturnCountList(locale, query);

		} catch (HibernateException e) {
			log
					.warn("TipoAsuntoLegacyDAOImpl.findTipoAsuntoByCriterioWhereSqlCount: No se puede recuperar el contador de los asuntos con la condicion: "
							+ where);
			throw new LibroException(
					"No se puede recuperar el contador los asuntos con es el where indicado: "
							+ where, e);
		}

		return result;
	}

	/**
	 * Metodo que comprueba si el asunto es valido para todas las oficinas y si
	 * no es asi, comprueba para la oficina pasada como parametro
	 * 
	 * @param locale
	 * @param scrCa
	 * @param idOficina
	 * @return {@link TipoAsuntoVO}
	 * @throws HibernateException
	 */
	protected TipoAsuntoVO validateAsuntoByOficina(Locale locale, ScrCa scrCa,
			String idOficina) throws HibernateException {
		TipoAsuntoVO result = null;

		// comprobamos si el asunto es valido para todas las oficinas
		if (scrCa.getAllOfics().intValue() != AsuntoValidoAllOficinas) {
			// no esta habilitado para todas las oficinas con lo que comprobamos
			// si la oficina es valida para el asunto
			result = getCaoficByOficinayAsunto(locale,
					scrCa.getId().toString(), idOficina);
		} else {
			// obtenemos el asunto parseado
			result = tipoAsuntoAdapter(scrCa);
		}
		return result;
	}

	/**
	 * Obtiene un listado de asuntos segun el where pasado como parametro y
	 * ademas no esten dados de baja
	 * 
	 * @param locale
	 * @param where
	 * @return {@link StringBuffer} sentencia a ejecutar
	 * @throws HibernateException
	 */
	protected StringBuffer getAsuntosWhereQuery(Locale locale, String where)
			throws HibernateException {
		// Generamos la expresion por la consultamos los asuntos
		// que esten disponibles
		StringBuffer result = new StringBuffer();
		result.append("ENABLED = 1 AND ");
		result.append(where);

		return result;
	}

	/**
	 * Se genera la query que obtiene todos los tipos de asunto segun su
	 * disponibilidad y la oficina del usuario
	 * 
	 * @param asuntoEnabled
	 * @param idOficina
	 * @return {@link StringBuffer} la sentencia a ejecutar
	 */
	protected StringBuffer getAllAsuntosQuery(Integer asuntoEnabled,
			String idOficina) {
		StringBuffer result = new StringBuffer();

		result.append("ENABLED = ");
		result.append(asuntoEnabled);
		result
				.append(" AND (ALL_OFICS = 1 OR (ALL_OFICS = 0  AND ID IN (SELECT ID_MATTER FROM SCR_CAOFIC WHERE ID_OFIC = ");
		result.append(idOficina);
		result.append(")))");

		return result;
	}

	/**
	 * Metodo que obtiene el tipo de asunto segun su codigo
	 * 
	 * @param locale
	 * @param codigo
	 * @return {@link ScrCa}
	 * @throws HibernateException
	 */
	protected ScrCa getAsuntosByCodigo(Locale locale, String codigo)
			throws HibernateException {
		ScrCa result = null;
		// List listado = null;

		// Generamos la expresion por la consultamos los asuntos
		// que tengan el codigo
		StringBuffer query = new StringBuffer();
		query.append("ENABLED = 1 AND CODE = '");
		query.append(codigo);
		query.append("'");

		// ejecutamos la consulta
		result = executeCriteriaReturnScrCa(locale, query);

		return result;
	}

	/**
	 * Metodo que obtiene el tipo de asunto segun su id
	 * 
	 * @param locale
	 * @param idAsunto
	 * @return {@link ScrCa}
	 * @throws HibernateException
	 */
	protected ScrCa getAsuntosById(Locale locale, Integer idAsunto)
			throws HibernateException {

		ScrCa result = null;

		// generamos la consulta a ejecutar
		StringBuffer query = new StringBuffer();
		query.append("ENABLED = 1 AND ID = ");
		query.append(idAsunto);

		// ejecutamos la consulta
		result = executeCriteriaReturnScrCa(locale, query);

		return result;
	}

	/**
	 * Metodo que obtiene el valor de scr_caofic segun id oficina e id asunto
	 * pasado como parametro
	 * 
	 * @param locale
	 * @param idAsunto
	 * @param idOficina
	 * @return {@link TipoAsuntoVO}
	 * @throws HibernateException
	 */
	protected TipoAsuntoVO getCaoficByOficinayAsunto(Locale locale,
			String idAsunto, String idOficina) throws HibernateException {
		TipoAsuntoVO result = null;
		ScrCa scrCa = null;

		// componemos la consulta a ejecutar
		StringBuffer query = new StringBuffer();
		query
				.append("ENABLED = 1 AND ID IN (SELECT ID_MATTER FROM SCR_CAOFIC WHERE ID_MATTER = ");
		query.append(idAsunto + " AND ID_OFIC=" + idOficina + ")");

		// ejecutamos la consulta
		scrCa = executeCriteriaReturnScrCa(locale, query);
		if (scrCa != null) {
			result = tipoAsuntoAdapter(scrCa);
		}

		return result;
	}

	/**
	 * Metodo adapta Vos del sistema legado a {@link TipoAsuntoVO}
	 * 
	 * @param ScrCa
	 *            informacion de los tipos de asunto
	 * @return {@link TipoAsuntoVO}
	 */
	protected TipoAsuntoVO tipoAsuntoAdapter(ScrCa scrCa) {
		TipoAsuntoVO result = new TipoAsuntoVO();
		result.setId(scrCa.getId().toString());
		result.setCodigo(scrCa.getCode());
		result.setDescripcion(scrCa.getMatter());

		if (scrCa.getEnabled().intValue() == 1) {
			result.setHabilitado(true);
		} else {
			result.setHabilitado(false);
		}

		if (scrCa.getForEreg().intValue() == 1) {
			result.setDisponibleLibroEntrada(true);
		} else {
			result.setDisponibleLibroEntrada(false);
		}

		if (scrCa.getForSreg().intValue() == 1) {
			result.setDisponibleLibroSalida(true);
		} else {
			result.setDisponibleLibroSalida(false);
		}

		return result;
	}

	/**
	 * Metodo que ejecuta la consulta y comprueba que la consulta solamente nos
	 * devuelve un objeto tipo ScrCa
	 * 
	 * @param locale
	 * @param query
	 * @return {@link ScrCa}
	 * @throws HibernateException
	 */
	protected ScrCa executeCriteriaReturnScrCa(Locale locale, StringBuffer query)
			throws HibernateException {
		ScrCa result = null;
		List listado = null;

		// obtiene el listado de tipo de unidades adminitrativas
		listado = executeCriteriaReturnScrCaList(locale, query, null);

		// comprobamos que el listado solo nos devuelve un objeto
		Object scrCa = DataAccessUtils.uniqueResult(listado);
		if (scrCa != null) {
			result = new ScrCa();
			// parseamos el objeto a un objeto tipo ScrCa()
			BeanUtils.copyProperties(scrCa, result);
		}

		return result;
	}

	/**
	 * Metodo que genera una consulta mediante Criteria de Hibernate y lo
	 * ejecuta, obteniendo la consulta que se pasa como parametro a la tabla que
	 * corresponde segun el idioma
	 * 
	 * @param locale
	 * @param query
	 * @return listado de {@link ScrCa}
	 * @throws HibernateException
	 */
	protected List executeCriteriaReturnScrCaList(Locale locale,
			StringBuffer query, BaseCriterioBusquedaVO baseCriterioBusqueda)
			throws HibernateException {
		List result = null;
		Session session = null;
		try {
			session = getSession();
			// compone la consulta que debemos lanzar
			Criteria criteriaResults = session
					.createCriteria(getScrCaLanguage(locale.getLanguage()));
			criteriaResults.add(Expression.sql(query.toString()));

			if (baseCriterioBusqueda != null) {
				criteriaResults.setFirstResult(baseCriterioBusqueda.getOffset()
						.intValue());
				criteriaResults.setMaxResults(baseCriterioBusqueda.getLimit()
						.intValue());
			}

			// obtiene el listado de tipo de unidades adminitrativas
			result = criteriaResults.list();
		} finally {
			this.closeSession(session);
		}
		return result;
	}

	/**
	 * Metodo que genera una consulta mediante Criteria de Hibernate y lo
	 * ejecuta, obteniendo el numero de registros de la consulta que se pasa
	 * como parametro a la tabla que corresponde segun el idioma
	 * 
	 * @param locale
	 * @param query
	 * @return int numero de valores devueltos por la consulta
	 * @throws HibernateException
	 */
	protected int executeCriteriaReturnCountList(Locale locale,
			StringBuffer query) throws HibernateException {
		int result = 0;
		Session session = null;
		try {
			session = getSession();

			/*
			 * StringBuffer querySize = new StringBuffer();
			 * querySize.append("SELECT COUNT(*) FROM "); Class classQuery =
			 * getScrCaLanguage(locale.getLanguage());
			 * querySize.append(classQuery.getName());
			 * querySize.append(" AS SCR WHERE "); querySize.append(query);
			 * 
			 * result = ((Integer) session.iterate(querySize.toString()).next())
			 * .intValue();
			 */
			List listado = session.find("SELECT COUNT(*) FROM "
					+ getScrCaLanguage(locale.getLanguage()).getName()
					+ " WHERE " + query.toString());

			if (!listado.isEmpty()) {
				result = ((Integer) (listado.get(0))).intValue();
			}

		} finally {
			this.closeSession(session);
		}
		return result;
	}

	/**
	 * Metodo que obtiene la tabla con la que se trabaja pasando el idioma
	 * 
	 * @param language
	 * @return Class
	 */
	protected static Class getScrCaLanguage(String language) {
		Class scrCaClass = ScrCa.class;
		if (language.equals("es")) {
			scrCaClass = ScrCa.class;
		}
		if (language.equals("eu")) {
			scrCaClass = ScrCaeu.class;
		}
		if (language.equals("gl")) {
			scrCaClass = ScrCagl.class;
		}
		if (language.equals("ca")) {
			scrCaClass = ScrCact.class;
		}

		return scrCaClass;
	}

}
