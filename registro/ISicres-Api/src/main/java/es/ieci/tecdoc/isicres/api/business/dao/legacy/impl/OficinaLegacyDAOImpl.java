package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.expression.Expression;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.support.DataAccessUtils;

import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOficct;
import com.ieci.tecdoc.common.invesicres.ScrOficeu;
import com.ieci.tecdoc.common.invesicres.ScrOficgl;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;

import es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO;
import es.ieci.tecdoc.isicres.api.business.exception.OficinaException;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.ZonaHorariaVO;

public class OficinaLegacyDAOImpl extends IsicresBaseHibernateDAOImpl implements
		OficinaDAO {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO#getOficinaByCodigo
	 * (java.util.Locale, java.lang.String)
	 */
	public OficinaVO getOficinaByCodigo(Locale locale, String codOficina) {
		OficinaVO result = null;
		ScrOfic scrOfic = null;
		try {
			// obtenemos la informacion de la oficina mediante el codigo de
			// oficina
			scrOfic = getScrOficByCode(locale, codOficina);

			if (scrOfic != null) {
				// obtenemos la zona horaria de la oficina mediante el id de
				// oficina
				ScrTmzofic scrTmzofic = getScrTmzofic(scrOfic.getId());
				// obtenemos el objeto OficinaVO
				result = oficinaAdapter(scrOfic, scrTmzofic);
			}

		} catch (HibernateException e) {
			throw new OficinaException(
					"No se puede recuperar la informacion del tipo de la oficina con codigo ["
							+ codOficina + "] ", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO#getOficinaById(java
	 * .util.Locale, java.lang.String)
	 */
	public OficinaVO getOficinaById(Locale locale, String idOficina) {
		OficinaVO result = null;
		ScrOfic scrOfic = null;
		try {
			// obtenemos la informacion de la oficina mediante el id de oficina
			scrOfic = getScrOficById(locale, idOficina);

			if (scrOfic != null) {
				// obtenemos la zona horaria de la oficina mediante el id de
				// oficina
				ScrTmzofic scrTmzofic = getScrTmzofic(scrOfic.getId());
				// obtenemos el objeto OficinaVO
				result = oficinaAdapter(scrOfic, scrTmzofic);
			}

		} catch (HibernateException e) {
			throw new OficinaException(
					"No se puede recuperar la informacion del tipo de la oficina con id ["
							+ idOficina + "] ", e);
		}
		return result;
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO#getOficinas(java.util.Locale)
	 */
	public List<OficinaVO> getOficinas(Locale locale) {

		List result = new ArrayList();

		List scrOficList;
		try {
			scrOficList = findScrOfic(locale);

			for (Iterator iterator = scrOficList.iterator(); iterator.hasNext();) {
				ScrOfic scrOfic = (ScrOfic) iterator.next();
				ScrTmzofic scrTmzofic = getScrTmzofic(scrOfic.getId());
				OficinaVO oficina = oficinaAdapter(scrOfic, scrTmzofic);
				result.add(oficina);
			}
		} catch (HibernateException e) {
			throw new OficinaException(
					"Error recuperando las oficinas", e);
		}

		return result;
	}

	protected ScrOfic getScrOficById(Locale locale, String id)
			throws HibernateException {

		ScrOfic result = null;
		// creamos la consulta por codigo de oficina
		StringBuffer query = new StringBuffer();
		query.append(" id = ");
		query.append(id);

		// ejecutamos la consulta
		result = executeCriteriaReturnScrOfic(locale, query);

		return result;

	}

	public OficinaVO getOficinaByIdDepartamento(Locale locale,
			String idDepartamento) {
		OficinaVO result = null;
		ScrOfic scrOfic = null;
		try {
			// obtenemos la informacion de la oficina mediante el id del
			// departamento
			scrOfic = getScrOficByIdDepartamento(locale, idDepartamento);

			if (scrOfic != null) {
				// obtenemos la zona horaria de la oficina mediante el id de
				// oficina
				ScrTmzofic scrTmzofic = getScrTmzofic(scrOfic.getId());
				// obtenemos el objeto OficinaVO
				result = oficinaAdapter(scrOfic, scrTmzofic);
			}

		} catch (HibernateException e) {
			throw new OficinaException(
					"No se puede recuperar la informacion del tipo de la oficina con id departamento ["
							+ idDepartamento + "] ", e);
		}
		return result;
	}

	protected ScrOfic getScrOficByIdDepartamento(Locale locale,
			String idDepartamento) throws HibernateException {

		ScrOfic result = null;
		// creamos la consulta por codigo de oficina
		StringBuffer query = new StringBuffer();
		query.append(" deptid = ");
		query.append(idDepartamento);

		// ejecutamos la consulta
		result = executeCriteriaReturnScrOfic(locale, query);

		return result;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * es.ieci.tecdoc.isicres.api.business.dao.OficinaDAO#findOficinaByUsuario
	 * (java.util.Locale, es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO)
	 */
	public List<OficinaVO> getOficinasByUsuario(Locale locale, UsuarioVO usuario) {

		List result = new ArrayList();
		String idUsuario = usuario.getId();

		List scrOficList;
		try {
			scrOficList = findScrOficByUsuario(locale, idUsuario);

			for (Iterator iterator = scrOficList.iterator(); iterator.hasNext();) {
				ScrOfic scrOfic = (ScrOfic) iterator.next();
				ScrTmzofic scrTmzofic = getScrTmzofic(scrOfic.getId());
				OficinaVO oficina = oficinaAdapter(scrOfic, scrTmzofic);
				result.add(oficina);
			}
		} catch (HibernateException e) {
			throw new OficinaException(
					"Error recuperando las oficinas para el usuario con id ["
							+ usuario.getId() + "-" + usuario.getLoginName()
							+ "] ", e);
		}

		return result;
	}

	/**
	 * Obtiene la informacion de la oficina a partir del codigo de la misma
	 *
	 * @param code
	 * @return ScrOfic
	 * @throws HibernateException
	 */
	protected ScrOfic getScrOficByCode(Locale locale, String code)
			throws HibernateException {

		ScrOfic result = null;
		// creamos la consulta por codigo de oficina
		StringBuffer query = new StringBuffer();
		query.append(" code = '");
		query.append(code);
		query.append("'");

		// ejecutamos la consulta
		result = executeCriteriaReturnScrOfic(locale, query);

		return result;

	}

	/**
	 * Obtiene la informacion de la zona horaria de la oficina pasada como
	 * parametro
	 *
	 * @param oficId
	 * @return ScrTmzofic
	 * @throws HibernateException
	 */
	protected ScrTmzofic getScrTmzofic(Integer oficId)
			throws HibernateException {
		ScrTmzofic scrTmzofic = null;
		try {
			scrTmzofic = (ScrTmzofic) getSession().load(ScrTmzofic.class,
					oficId);
		} catch (ObjectNotFoundException onF) {
			// Puede ser que una oficina no tenga cambios horarios
		}
		return scrTmzofic;
	}

	/**
	 * Metodo adapta Vos del sistema legado a {@link ZonaHorariaVO}
	 *
	 * @param scrTmzofic
	 *            informacion de la zona horaria de la oficina
	 * @return {@link ZonaHorariaVO}
	 */
	protected ZonaHorariaVO zonaHorariaAdapter(ScrTmzofic scrTmzofic) {
		ZonaHorariaVO result = new ZonaHorariaVO();
		if (scrTmzofic != null) {
			result.setTimezone(scrTmzofic.getTmz());
		}
		return result;
	}

	/**
	 * Metodo adapta Vos del sistema legado a {@link OficinaVO}
	 *
	 * @param scrOfic
	 *            informacion de la oficina
	 * @param zonaHoraria
	 *            informacion de la zona horaria de la oficina
	 * @return {@link OficinaVO}
	 */
	protected OficinaVO oficinaAdapter(ScrOfic scrOfic, ScrTmzofic scrTmzofic) {
		OficinaVO result = new OficinaVO();

		result.setId(scrOfic.getId().toString());
		result.setAcronimoOficina(scrOfic.getAcron());
		result.setCodigoOficina(scrOfic.getCode());
		result.setName(scrOfic.getName());
		result.setIdDepartamento(Integer.toString(scrOfic.getDeptid()));

		if (scrTmzofic != null) {
			ZonaHorariaVO zonaHoraria = zonaHorariaAdapter(scrTmzofic);
			result.setZonaHoraria(zonaHoraria);
		}

		return result;
	}

	/**
	 * Metodo que devuelve todas las oficinas vinculadas al usuario
	 *
	 * @param idUsuario
	 * @return lista de objetos de tipo {@link ScrOfic}
	 * @throws HibernateException
	 */
	protected List findScrOficByUsuario(Locale locale, String idUsuario)
			throws HibernateException {

		List result = null;
		// query para sacar los oficinas del usuario
		StringBuffer queryUsrOfic = new StringBuffer();
		String sqlUsrOfic = "id in (select idofic from scr_usrofic where iduser = ";
		queryUsrOfic.append(sqlUsrOfic);
		queryUsrOfic.append(idUsuario);
		queryUsrOfic.append(")");

		result = executeCriteriaReturnScrOficList(locale, queryUsrOfic);

		return result;
	}

	/**
	 * Metodo que devuelve todas las oficinas vinculadas al usuario
	 *
	 * @param idUsuario
	 * @return lista de objetos de tipo {@link ScrOfic}
	 * @throws HibernateException
	 */
	protected List findScrOfic(Locale locale)
			throws HibernateException {

		List result = null;
		// query para sacar los oficinas del usuario
		StringBuffer queryUsrOfic = new StringBuffer();
		String sqlUsrOfic = "id in (select idofic from scr_usrofic) ";
		queryUsrOfic.append(sqlUsrOfic);
		result = executeCriteriaReturnScrOficList(locale, queryUsrOfic);

		return result;
	}

	/**
	 * Metodo que ejecuta la consulta y comprueba que la consulta solamente nos
	 * devuelve un objeto tipo ScrOfic
	 *
	 * @param locale
	 * @param query
	 * @return
	 * @throws HibernateException
	 */
	protected ScrOfic executeCriteriaReturnScrOfic(Locale locale,
			StringBuffer query) throws HibernateException {
		ScrOfic result = null;
		List listado = null;

		// obtiene el listado de tipo de unidades adminitrativas
		listado = executeCriteriaReturnScrOficList(locale, query);

		// comprobamos que el listado solo nos devuelve un objeto
		Object scrOfic = DataAccessUtils.uniqueResult(listado);
		if (scrOfic != null) {
			result = new ScrOfic();
			// parseamos el objeto a un objeto tipo ScrOfic()
			BeanUtils.copyProperties(scrOfic, result);
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
	 * @return
	 * @throws HibernateException
	 */
	protected List executeCriteriaReturnScrOficList(Locale locale,
			StringBuffer query) throws HibernateException {
		List result = null;
		// compone la consulta que debemos lanzar
		Criteria criteriaResults = getSession().createCriteria(
				getScrOficLanguage(locale.getLanguage()));
		criteriaResults.add(Expression.sql(query.toString()));

		// obtiene el listado de tipo de unidades adminitrativas
		result = criteriaResults.list();

		return result;
	}

	/**
	 * Metodo que obtiene la tabla con la que se trabaja pasando el idioma
	 *
	 * @param language
	 * @return
	 */
	protected static Class getScrOficLanguage(String language) {
		Class scrOficClass = ScrOfic.class;
		if (language.equals("es")) {
			scrOficClass = ScrOfic.class;
		}
		if (language.equals("eu")) {
			scrOficClass = ScrOficeu.class;
		}
		if (language.equals("gl")) {
			scrOficClass = ScrOficgl.class;
		}
		if (language.equals("ca")) {
			scrOficClass = ScrOficct.class;
		}
		return scrOficClass;
	}

}
