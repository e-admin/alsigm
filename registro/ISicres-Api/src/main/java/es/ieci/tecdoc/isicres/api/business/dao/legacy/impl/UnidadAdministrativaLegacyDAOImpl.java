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

import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.invesicres.ScrOrgct;
import com.ieci.tecdoc.common.invesicres.ScrOrgeu;
import com.ieci.tecdoc.common.invesicres.ScrOrggl;
import com.ieci.tecdoc.common.invesicres.ScrTypeadm;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmct;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmeu;
import com.ieci.tecdoc.common.invesicres.ScrTypeadmgl;

import es.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO;
import es.ieci.tecdoc.isicres.api.business.exception.UnidadAdministrativaException;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;

public class UnidadAdministrativaLegacyDAOImpl extends
		IsicresBaseHibernateDAOImpl implements UnidadAdministrativaDAO {

	private static final Logger log = Logger
			.getLogger(UnidadAdministrativaLegacyDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findAllTipoUnidadesAdmin(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.business
	 * .vo.CriterioBusquedaTipoUnidadAdministrativaVO)
	 */
	public List findAllTipoUnidadesAdmin(Locale locale,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio) {
		List result = new ArrayList();
		TipoUnidadAdministrativaVO tipoUnidad = null;

		try {
			// obtenos el listado de tipo ScrTypeAdm
			List listado = getScrTypeAdm(locale, criterio);

			for (Iterator it = listado.iterator(); it.hasNext();) {
				// Creamos un objeto tipo ScrTypeadm
				ScrTypeadm typeAdm = new ScrTypeadm();
				// Copiamos las propiedades del objeto it.next (ScrTypeAdm) a
				// typeAdm
				BeanUtils.copyProperties(it.next(), typeAdm);

				// Parseamos el objeto a TipoUnidadAdministrativaVO
				tipoUnidad = adapterScrTypeAdmToTipoUnidadAdministrativaVO(typeAdm);

				result.add(tipoUnidad);
			}
		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.getTipoUnidadesAdmin no se pueden recuperar el tipo de unidades administrativas");
			throw new UnidadAdministrativaException(
					"No se puede recuperar el lista de tipo de unidades administrativas",
					e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findCountAllTipoUnidadesAdmin(java.util.Locale)
	 */
	public int findCountAllTipoUnidadesAdmin(Locale locale) {
		int result = 0;

		try {
			// obtenemos el numero de tipo de unidades administrativas
			result = getCountScrTypeAdm(locale);

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.getTipoUnidadesAdmin no se pueden recuperar el tipo de unidades administrativas");
			throw new UnidadAdministrativaException(
					"No se puede recuperar el lista de tipo de unidades administrativas",
					e);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#findTipoUnidadesAdminByCode(java.lang.String,
	 *      java.util.Locale,
	 *      es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO)
	 */
	public TipoUnidadAdministrativaVO getTipoUnidadesAdminByCode(String code, Locale locale,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio) {

		TipoUnidadAdministrativaVO tipoUnidad = null;

		try {
			// obtenos el listado de tipo ScrTypeAdm
			List listado = getScrTypeAdmByCode(code, locale, criterio);

			if (listado != null && listado.size() > 0) {

				// Creamos un objeto tipo ScrTypeadm
				ScrTypeadm typeAdm = new ScrTypeadm();
				// Copiamos las propiedades del objeto it.next (ScrTypeAdm)
				// a
				// typeAdm
				BeanUtils.copyProperties(listado.get(0), typeAdm);

				// Parseamos el objeto a TipoUnidadAdministrativaVO
				tipoUnidad = adapterScrTypeAdmToTipoUnidadAdministrativaVO(typeAdm);
				return tipoUnidad;

			}
		} catch (HibernateException e) {
			log.warn("UnidadAdministrativaLegacyDAOImpl.getTipoUnidadesAdmin no se pueden recuperar el tipo de unidades administrativas");
			throw new UnidadAdministrativaException(
					"No se puede recuperar el lista de tipo de unidades administrativas", e);
		}
		return tipoUnidad;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findUnidadesAdmByTipo(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.business
	 * .vo.CriterioBusquedaUnidadAdministrativaByTipoVO)
	 */
	public List findUnidadesAdmByTipo(Locale locale,
			CriterioBusquedaUnidadAdministrativaByTipoVO criterio) {
		List result = null;

		try {
			// generamos la consulta a ejecutar
			StringBuffer query = getQueryUnidadesAdmByTipo(criterio
					.getIdTipoUnidadAdministrativa());

			// generamos la acotacion de la consulta a ejecutar
			CriterioBusquedaTipoUnidadAdministrativaVO limitQuery = new CriterioBusquedaTipoUnidadAdministrativaVO();
			limitQuery.setLimit(criterio.getLimit());
			limitQuery.setOffset(criterio.getOffset());

			// ejecutamos la consulta
			result = executeCriteriaReturnListUnidadAdministrativaVO(locale,
					query, limitQuery);

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.getUnidadesAdmByTipo no se pueden recuperar el listado de unidades administrativas segun su tipo: "
							+ criterio.getIdTipoUnidadAdministrativa());
			// TODO falta poner bien la exception correcta
			throw new UnidadAdministrativaException(
					"No se puede recuperar el listado de unidades administrativas segun su tipo ["
							+ criterio.getIdTipoUnidadAdministrativa() + "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findCountUnidadesAdmByTipo(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.business
	 * .vo.CriterioBusquedaUnidadAdministrativaByTipoVO)
	 */
	public int findCountUnidadesAdmByTipo(Locale locale,
			CriterioBusquedaUnidadAdministrativaByTipoVO criterio) {
		int result = 0;

		try {
			// generamos la consulta a ejecutar
			StringBuffer query = getQueryUnidadesAdmByTipo(criterio
					.getIdTipoUnidadAdministrativa());

			// ejecutamos la consulta
			result = executeCriteriaReturnCountScrOrgsList(locale, query);

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.getCountUnidadesAdmByTipo no se pueden recuperar el numero de unidades administrativas segun su tipo: "
							+ criterio.getIdTipoUnidadAdministrativa());
			throw new UnidadAdministrativaException(
					"No se puede recuperar el numero de unidades administrativas segun su tipo ["
							+ criterio.getIdTipoUnidadAdministrativa() + "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findUnidadesAdmWhereSQL(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO)
	 */
	public List findUnidadesAdmWhereSQL(Locale locale,
			CriterioBusquedaWhereSqlVO criterio) {
		List result = null;

		try {
			// generamos la consulta
			StringBuffer query = getQueryUnidadByWhereSQL(criterio.getSql());

			// generamos la acotacion de la consulta a ejecutar
			CriterioBusquedaTipoUnidadAdministrativaVO limitQuery = new CriterioBusquedaTipoUnidadAdministrativaVO();
			limitQuery.setLimit(criterio.getLimit());
			limitQuery.setOffset(criterio.getOffset());

			// ejecutamos la consulta
			result = executeCriteriaReturnListUnidadAdministrativaVO(locale,
					query, limitQuery);

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.findUnidadesAdmWhereSQL no se pueden recuperar el listado de unidades administrativas segun el where: "
							+ criterio.getSql());
			throw new UnidadAdministrativaException(
					"No se puede recuperar el listado de unidades administrativas segun el where ["
							+ criterio.getSql() + "]", e);
		}

		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findCountUnidadesAdmWhereSQL(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO)
	 */
	public int findCountUnidadesAdmWhereSQL(Locale locale,
			CriterioBusquedaWhereSqlVO criterio) {
		int result = 0;

		try {
			// generamos la consulta a ejecutar
			StringBuffer query = getQueryUnidadByWhereSQL(criterio.getSql());

			// ejecutamos la consulta
			result = executeCriteriaReturnCountScrOrgsList(locale, query);

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.findCountUnidadesAdmWhereSQL no se pueden recuperar el numero de unidades administrativas segun su where: "
							+ criterio.getSql());
			throw new UnidadAdministrativaException(
					"No se puede recuperar el numero de unidades administrativas segun su where ["
							+ criterio.getSql() + "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * getUnidadByCode(java.util.Locale, java.lang.String)
	 */
	public UnidadAdministrativaVO findUnidadByCode(Locale locale,
			String codigoUnidad) {
		UnidadAdministrativaVO result = null;

		ScrOrg scrOrg = null;

		try {
			// obtenemos la consulta a ejecutar
			StringBuffer query = getQueryUnidadByCode(codigoUnidad);

			// ejecutamos la consulta
			scrOrg = executeCriteriaReturnScrOrg(locale, query);

			if (scrOrg != null) {
				// mediante el objeto scrOrg obtenemos UnidadAdministrativaVO
				result = fromScrOrgToUnidadAdministrativa(locale, scrOrg);
			}

		} catch (HibernateException e) {
			log.warn("UnidadAdministrativaLegacyDAOImpl.getUnidadByCode no se pueden recuperar la UnidadAdministrativa con codigo ["
							+ codigoUnidad + "]");
			throw new UnidadAdministrativaException(
					"No se puede recuperar la UnidadAdministrativa con codigo [" + codigoUnidad
							+ "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findUnidadesAdmByUnidad(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.business
	 * .vo.CriterioBusquedaUnidadAdministrativaByUnidadVO)
	 */
	public List findUnidadesAdmByUnidad(Locale locale,
			CriterioBusquedaUnidadAdministrativaByUnidadVO criterio) {
		List result = null;

		try {
			// generamos la consulta a ejecutar
			StringBuffer query = getQueryUnidadByUnidadPadre(criterio
					.getIdUnidadAdministrativaPadre());

			// generamos la acotacion de la consulta a ejecutar
			CriterioBusquedaTipoUnidadAdministrativaVO limitQuery = new CriterioBusquedaTipoUnidadAdministrativaVO();
			limitQuery.setLimit(criterio.getLimit());
			limitQuery.setOffset(criterio.getOffset());

			// ejecutamos la consulta
			result = executeCriteriaReturnListUnidadAdministrativaVO(locale, query, limitQuery);

		} catch (HibernateException e) {
			log.warn("UnidadAdministrativaLegacyDAOImpl.getUnidadesAdmByUnidad no se pueden recuperar el listado de unidades administrativas segun el id de unidad padre: "
							+ criterio.getIdUnidadAdministrativaPadre());
			throw new UnidadAdministrativaException(
					"No se puede recuperar el listado de unidades administrativas segun el id de la unidad padre ["
							+ criterio.getIdUnidadAdministrativaPadre() + "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findCountUnidadesAdmByUnidad(java.util.Locale,
	 * es.ieci.tecdoc.isicres.api.
	 * business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO)
	 */
	public int findCountUnidadesAdmByUnidad(Locale locale,
			CriterioBusquedaUnidadAdministrativaByUnidadVO criterio) {
		int result = 0;

		try {
			// generamos la consulta a ejecutar
			StringBuffer query = getQueryUnidadByUnidadPadre(criterio
					.getIdUnidadAdministrativaPadre());

			// ejecutamos la consulta
			result = executeCriteriaReturnCountScrOrgsList(locale, query);

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.getCountUnidadesAdmByUnidad no se pueden recuperar el numero de unidades administrativas segun su tipo: "
							+ criterio.getIdUnidadAdministrativaPadre());
			throw new UnidadAdministrativaException(
					"No se puede recuperar el numero de unidades administrativas segun su tipo ["
							+ criterio.getIdUnidadAdministrativaPadre() + "] ",
					e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seees.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO#
	 * findUnidadById(java.util.Locale, java.lang.Integer)
	 */
	public UnidadAdministrativaVO findUnidadById(Locale locale, Integer idUnidad) {
		UnidadAdministrativaVO result = null;

		ScrOrg scrOrg = null;

		try {
			// obtenemos la consulta a ejecutar
			StringBuffer query = getQueryUnidadById(idUnidad);

			// ejecutamos la consulta
			scrOrg = executeCriteriaReturnScrOrg(locale, query);

			if (scrOrg != null) {
				// mediante el objeto scrOrg obtenemos UnidadAdministrativaVO
				result = fromScrOrgToUnidadAdministrativa(locale, scrOrg);
			}

		} catch (HibernateException e) {
			log
					.warn("UnidadAdministrativaLegacyDAOImpl.findUnidadAdministrativaVOById no se pueden recuperar la UnidadAdministrativa con id ["
							+ idUnidad + "]");
			throw new UnidadAdministrativaException(
					"No se puede recuperar la UnidadAdministrativa con id ["
							+ idUnidad + "]", e);
		}

		return result;
	}

	/**
	 * Metodo que obtiene la unidad padre de la unidad pasada como parametro
	 * 
	 * @param locale
	 * @param scrOrg
	 *            unidad administrativa
	 * @return {@link BaseUnidadAdministrativaVO}
	 * @throws HibernateException
	 */
	protected BaseUnidadAdministrativaVO getPadreUnidadAdministrativa(
			Locale locale, ScrOrg scrOrg) throws HibernateException {
		BaseUnidadAdministrativaVO result = new BaseUnidadAdministrativaVO();
		ScrOrg scrOrgPadre = null;

		if ((scrOrg.getIdFather() != null)
				&& (scrOrg.getIdFather().intValue() != 0)) {
			// generamos la consulta segun el ID del padre
			StringBuffer query = getQueryUnidadById(scrOrg.getIdFather());

			// ejecutamos la consulta
			scrOrgPadre = executeCriteriaReturnScrOrg(locale, query);

			// mediante el objeto scrOrgPadre obtenemos un
			// BaseUnidadAdministrativaVO
			result = fromScrOrgToBaseUnidadAdministrativaVO(scrOrgPadre, null,
					null);
		}

		return result;
	}

	/**
	 * Metodo que obtiene todas los tipos de unidades administrativas de BBDD
	 * segun el idioma
	 * 
	 * @param locale
	 * @param criterio
	 *            indica la posicion inicial y el numero de registros a
	 *            recuperar
	 * @return list de objetos {@link ScrTypeadm}
	 * @throws HibernateException
	 */
	protected List getScrTypeAdm(Locale locale,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio)
			throws HibernateException {
		List result = null;
		Session session = null;

		try {
			session = getSession();
			// compone la consulta que debemos lanzar
			Criteria criteriaResults = session.createCriteria(getScrTypeAdmLanguage(locale
					.getLanguage()));

			if (criterio != null) {
				criteriaResults.setFirstResult(criterio.getOffset().intValue());
				criteriaResults.setMaxResults(criterio.getLimit().intValue());
			}

			// obtiene el listado de tipo de unidades adminitrativas
			result = criteriaResults.list();
		} finally {
			this.closeSession(session);
		}

		return result;
	}

	/**
	 * Metodo que obtiene todas los tipos de unidades administrativas de BBDD
	 * segun el idioma
	 * 
	 * @param locale
	 * @param criterio
	 *            indica la posicion inicial y el numero de registros a
	 *            recuperar
	 * @return list de objetos {@link ScrTypeadm}
	 * @throws HibernateException
	 */
	protected List getScrTypeAdmByCode(String code, Locale locale,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio) throws HibernateException {
		List result = null;
		Session session = null;

		String query = getQueryScrTypeByCode(code).toString();
		try {
			session = getSession();
			// compone la consulta que debemos lanzar
			Criteria criteriaResults = session.createCriteria(getScrTypeAdmLanguage(locale
					.getLanguage()));
			
			
			criteriaResults.add(Expression.eq("code", code));

			if (criterio != null) {
				criteriaResults.setFirstResult(criterio.getOffset().intValue());
				criteriaResults.setMaxResults(criterio.getLimit().intValue());
			}

			// obtiene el listado de tipo de unidades adminitrativas
			result = criteriaResults.list();
		} finally {
			this.closeSession(session);
		}

		return result;
	}

	/**
	 * Metodo que obtiene el numero total de tipos de unidades administrativas
	 * de BBDD segun el idioma
	 * 
	 * @param locale
	 * @return int numero de total de tipo de unidades administrativas
	 * @throws HibernateException
	 */
	protected int getCountScrTypeAdm(Locale locale) throws HibernateException {
		int result = 0;

		Session session = null;
		try {
			session = getSession();

			StringBuffer querySize = new StringBuffer();
			querySize.append("SELECT COUNT(*) FROM ");
			querySize.append((getScrTypeAdmLanguage(locale.getLanguage()))
					.getName());

			result = ((Integer) session.iterate(querySize.toString()).next())
					.intValue();

		} finally {
			this.closeSession(session);
		}
		return result;

	}

	/**
	 * Metodo que ejecuta la consulta y comprueba que la consulta solamente nos
	 * devuelve un objeto tipo {@link ScrOrg}
	 * 
	 * @param locale
	 * @param query
	 *            consulta a ejecutar
	 * @return {@link ScrOrg}
	 * @throws HibernateException
	 */
	protected ScrOrg executeCriteriaReturnScrOrg(Locale locale,
			StringBuffer query) throws HibernateException {
		ScrOrg result = null;
		List listado = null;

		// obtiene el listado de tipo de unidades adminitrativas
		listado = executeCriteriaReturnScrOrgsList(locale, query, null);

		Object scrOrg = DataAccessUtils.uniqueResult(listado);
		// comprobamos que el listado solo nos devuelve un objeto
		if (scrOrg != null) {
			result = new ScrOrg();
			// parseamos el objeto a un objeto tipo ScrCa()
			BeanUtils.copyProperties(scrOrg, result);
		}

		return result;
	}

	/**
	 * Metodo que ejecuta la consulta pasada como parametro obteniendo un
	 * listado de objetos {@link ScrOrg}
	 * 
	 * @param locale
	 * @param query
	 *            consulta a ejecutar
	 * @param criterio
	 *            indica la posicion inicial y numero maximo de datos a
	 *            recuperar
	 * @return listado de objetos {@link ScrOrg}
	 * @throws HibernateException
	 */
	protected List executeCriteriaReturnScrOrgsList(Locale locale,
			StringBuffer query,
			CriterioBusquedaTipoUnidadAdministrativaVO criterio)
			throws HibernateException {

		List result = null;
		Session session = null;
		try {
			session = getSession();
			// compone la consulta que debemos lanzar
			Criteria criteriaResults = session
					.createCriteria(getScrOrgLanguage(locale.getLanguage()));
			criteriaResults.add(Expression.sql(query.toString()));

			// si el criterio es distinto de null acotamos la lista segun sus
			// valores
			if (criterio != null) {
				criteriaResults.setFirstResult(criterio.getOffset().intValue());
				criteriaResults.setMaxResults(criterio.getLimit().intValue());
			}

			// obtiene el listado de tipo de unidades adminitrativas
			result = criteriaResults.list();
		} finally {
			this.closeSession(session);
		}
		return result;
	}

	/**
	 * Metodo que ejecuta la consulta pasada como parametro sobre la tabla
	 * ScrOrg obteniendo el numero de datos encontrados
	 * 
	 * @param locale
	 * @param query
	 *            es la los criterios con los que consultaremos
	 * @return int de objetos tipo {@link ScrOrg}
	 * @throws HibernateException
	 */
	protected int executeCriteriaReturnCountScrOrgsList(Locale locale,
			StringBuffer query) throws HibernateException {

		int result = 0;
		Session session = null;
		try {
			session = getSession();

			StringBuffer querySize = new StringBuffer();
			querySize.append("SELECT COUNT(*) FROM ");
			querySize.append((getScrOrgLanguage(locale.getLanguage()))
					.getName());
			querySize.append(" AS SCR WHERE ");
			querySize.append(query);

			result = ((Integer) session.iterate(querySize.toString()).next())
					.intValue();

		} finally {
			this.closeSession(session);
		}
		return result;
	}

	/**
	 * Metodo que ejecuta la consulta pasada como parametro y obtiene un listado
	 * de objetos {@link UnidadAdministrativaVO}
	 * 
	 * @param locale
	 * @param query
	 *            consulta a ejecutar
	 * @param limitQuery
	 *            limites de la consulta a ejecutar (posicion inicial y numero
	 *            maximo de datos a recuperar)
	 * @return listado de objetos {@link UnidadAdministrativaVO}
	 * @throws HibernateException
	 */
	protected List executeCriteriaReturnListUnidadAdministrativaVO(
			Locale locale, StringBuffer query,
			CriterioBusquedaTipoUnidadAdministrativaVO limitQuery)
			throws HibernateException {
		List result = new ArrayList();
		List listado = null;
		ScrOrg scrOrg = null;
		UnidadAdministrativaVO unidadAdministrativa = null;

		// ejecutamos la consulta
		listado = executeCriteriaReturnScrOrgsList(locale, query, limitQuery);

		for (Iterator it = listado.iterator(); it.hasNext();) {
			scrOrg = (ScrOrg) it.next();

			// adaptamos los datos que tenemos a un objeto
			// UnidadAdministrativaVO
			unidadAdministrativa = fromScrOrgToUnidadAdministrativa(locale,
					scrOrg);

			result.add(unidadAdministrativa);
		}

		return result;
	}

	/**
	 * Adaptamos el {@link ScrTypeadm} a {@link TipoUnidadAdministrativaVO}
	 * 
	 * @param scrtypeadm
	 * @return {@link TipoUnidadAdministrativaVO}
	 */
	protected TipoUnidadAdministrativaVO adapterScrTypeAdmToTipoUnidadAdministrativaVO(
			ScrTypeadm scrtypeadm) {
		TipoUnidadAdministrativaVO result = new TipoUnidadAdministrativaVO();
		result.setId(scrtypeadm.getId().toString());
		result.setCodigo(scrtypeadm.getCode());
		result.setDescripcion(scrtypeadm.getDescription());

		return result;
	}

	/**
	 * Metodo que adapta un objeto {@link ScrOrg} a un objeto
	 * {@link UnidadAdministrativaVO} obteniendo a su vez la unidad padre y el
	 * tipo de unidad administrativa
	 * 
	 * @param locale
	 * @param scrOrg
	 * @return {@link UnidadAdministrativaVO}
	 * @throws HibernateException
	 */
	protected UnidadAdministrativaVO fromScrOrgToUnidadAdministrativa(
			Locale locale, ScrOrg scrOrg) throws HibernateException {

		UnidadAdministrativaVO result = null;

		// obtenemos el tipo de unidad
		TipoUnidadAdministrativaVO tipoUnidad = null;
		tipoUnidad = adapterScrTypeAdmToTipoUnidadAdministrativaVO(scrOrg
				.getScrTypeadm());

		// obtenemos la Unidad Padre
		BaseUnidadAdministrativaVO unidadPadre = getPadreUnidadAdministrativa(
				locale, scrOrg);

		result = fromScrOrgToUnidadAdministrativa(scrOrg, tipoUnidad,
				unidadPadre);

		return result;
	}

	/**
	 * Metodo que adapta el objeto {@link ScrOrg} a
	 * {@link UnidadAdministrativaVO}
	 * 
	 * @param scrOrg
	 * @param tipoUnidad
	 * @param unidadPadre
	 * @return {@link UnidadAdministrativaVO}
	 */
	protected UnidadAdministrativaVO fromScrOrgToUnidadAdministrativa(
			ScrOrg scrOrg, TipoUnidadAdministrativaVO tipoUnidad,
			BaseUnidadAdministrativaVO unidadPadre) {
		UnidadAdministrativaVO result = new UnidadAdministrativaVO();

		result.setAcronimoUnidad(scrOrg.getAcron());
		if (scrOrg.getEnabled() == 1) {
			result.setActiva(true);
		} else {
			result.setActiva(false);
		}
		result.setCif(scrOrg.getCif());
		result.setCodigoUnidad(scrOrg.getCode());
		result.setFechaAlta(scrOrg.getCreationDate());
		result.setFechaBaja(scrOrg.getDisableDate());
		result.setId(scrOrg.getId().toString());
		result.setName(scrOrg.getName());
		result.setTipo(tipoUnidad);
		result.setUnidadPadre(unidadPadre);
		if (scrOrg.getEnabled() == 0) {
			result.setActiva(false);
		} else {
			result.setActiva(true);
		}

		return result;
	}

	/**
	 * Metodo que adapta el objeto {@link ScrOrg} a
	 * {@link BaseUnidadAdministrativaVO}
	 * 
	 * @param scrOrg
	 * @return {@link BaseUnidadAdministrativaVO}
	 */

	protected BaseUnidadAdministrativaVO fromScrOrgToBaseUnidadAdministrativaVO(
			ScrOrg scrOrg, TipoUnidadAdministrativaVO tipoUnidad,
			BaseUnidadAdministrativaVO unidadPadre) {
		BaseUnidadAdministrativaVO result = new BaseUnidadAdministrativaVO();
		result.setAcronimoUnidad(scrOrg.getAcron());

		if (scrOrg.getEnabled() == 1) {
			result.setActiva(true);
		} else {
			result.setActiva(false);
		}
		result.setCif(scrOrg.getCif());
		result.setCodigoUnidad(scrOrg.getCode());
		result.setFechaAlta(scrOrg.getCreationDate());
		result.setFechaBaja(scrOrg.getDisableDate());
		result.setId(scrOrg.getId().toString());
		result.setName(scrOrg.getName());
		result.setTipo(tipoUnidad);

		if ((scrOrg.getIdFather() != null)
				&& (scrOrg.getIdFather().intValue() != 0)) {
			result.setUnidadPadre(unidadPadre);
		}

		return result;
	}

	/**
	 * Consulta por tipo de Unidades Administrativas
	 * 
	 * @param tipoUnidadAdm
	 * @return {@link StringBuffer} con la consulta por tipo de Unidades
	 */
	protected StringBuffer getQueryUnidadesAdmByTipo(String tipoUnidadAdm) {
		StringBuffer result = new StringBuffer();
		result.append("TYPE = ");
		result.append(tipoUnidadAdm);
		result.append(" AND (ID_FATHER = 0 OR ID_FATHER IS NULL)");
		return result;
	}

	/**
	 * Consulta por codigo de Unidad Administrativa
	 * 
	 * @param codigoUnidad
	 * @return {@link StringBuffer} con la consulta por codigo de unidad
	 *         administrativa
	 */
	protected StringBuffer getQueryUnidadByCode(String codigoUnidad) {
		StringBuffer result = new StringBuffer();
		result.append("CODE = '");
		result.append(codigoUnidad);
		result.append("'");
		return result;
	}

	/**
	 * Consulta por codigo de Unidad Administrativa
	 * 
	 * @param codigoUnidad
	 * @return {@link StringBuffer} con la consulta por codigo de unidad
	 *         administrativa
	 */
	protected StringBuffer getQueryScrTypeByCode(String code) {
		StringBuffer result = new StringBuffer();
		result.append("CODE = '");
		result.append(code);
		result.append("'");
		return result;
	}

	/**
	 * Consulta por id de unidad administrativa
	 * 
	 * @param idUnidad
	 * @return {@link StringBuffer} con la consulta por id de la unidad
	 *         administrativa
	 */
	protected StringBuffer getQueryUnidadById(Integer idUnidad) {
		StringBuffer result = new StringBuffer();
		result.append("ID = ");
		result.append(idUnidad);
		return result;
	}

	/**
	 * Consulta por id de unidad administrativa padre
	 * 
	 * @param idUnidad
	 * @return {@link StringBuffer} con la consulta por id de la unidad
	 *         administrativa
	 */
	protected StringBuffer getQueryUnidadByUnidadPadre(String idUnidadPadre) {
		StringBuffer result = new StringBuffer();
		result.append("ID_FATHER = ");
		result.append(idUnidadPadre);
		return result;
	}

	/**
	 * Metodo que genera la consulta cuando llegan los criterios de busqueda en
	 * una sentencia sql como parametro
	 * 
	 * @param where
	 *            sentencia sql con los criterios de busqueda
	 * @return {@link StringBuffer} consulta a realizar
	 */
	protected StringBuffer getQueryUnidadByWhereSQL(String where) {

		StringBuffer result = new StringBuffer();
		result.append(where);

		return result;
	}

	/**
	 * Metodo que devuelve la tabla scrTypeAdm sobre la que realizaremos la
	 * consulta segun el idioma
	 * 
	 * @param language
	 * @return Class
	 */
	protected static Class getScrTypeAdmLanguage(String language) {
		Class scrTypeAdmClass = ScrTypeadm.class;
		if (language.equals("es")) {
			scrTypeAdmClass = ScrTypeadm.class;
		}
		if (language.equals("eu")) {
			scrTypeAdmClass = ScrTypeadmeu.class;
		}
		if (language.equals("gl")) {
			scrTypeAdmClass = ScrTypeadmgl.class;
		}
		if (language.equals("ca")) {
			scrTypeAdmClass = ScrTypeadmct.class;
		}

		return scrTypeAdmClass;
	}

	/**
	 * Metodo que devuelve la tabla ScrOrgs sobre la que realizaremos la
	 * consulta segun el idioma
	 * 
	 * @param language
	 * @return Class
	 */
	protected static Class getScrOrgLanguage(String language) {
		Class scrTypeAdmClass = ScrOrg.class;
		if (language.equals("es")) {
			scrTypeAdmClass = ScrOrg.class;
		}
		if (language.equals("eu")) {
			scrTypeAdmClass = ScrOrgeu.class;
		}
		if (language.equals("gl")) {
			scrTypeAdmClass = ScrOrggl.class;
		}
		if (language.equals("ca")) {
			scrTypeAdmClass = ScrOrgct.class;
		}

		return scrTypeAdmClass;
	}
}
