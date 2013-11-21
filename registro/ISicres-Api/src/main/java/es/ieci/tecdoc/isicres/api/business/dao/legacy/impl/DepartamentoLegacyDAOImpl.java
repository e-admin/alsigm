package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.Type;

import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.utils.ISicresQueries;

import es.ieci.tecdoc.isicres.api.business.dao.DepartamentoDAO;
import es.ieci.tecdoc.isicres.api.business.exception.DepartamentoException;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.BaseDepartamentoVOMapper;
import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class DepartamentoLegacyDAOImpl extends IsicresBaseHibernateDAOImpl
		implements DepartamentoDAO {

	public DepartamentoUsuarioVO getDepartamentoUsuario(UsuarioVO usuario) {

		DepartamentoUsuarioVO result = null;

		String idUsuario = usuario.getId();
		ScrOfic scrOfic = null;
		try {

			// obtenemos el departamento del usuario
			scrOfic = getDepartamentoUsuario(idUsuario);

			if (scrOfic != null) {
				result = new DepartamentoUsuarioVO();
				String idDepartamento = Integer.toString(scrOfic.getDeptid());
				result.setId(idDepartamento);
			}

		} catch (HibernateException e) {
			throw new DepartamentoException("No se puede recuperar el departamento del usuario ",e);
		}

		return result;
	}

	public DepartamentoOficinaVO getDepartamentoOficina(OficinaVO oficina) {
		DepartamentoOficinaVO result = null;

		String idOficina = oficina.getId();

		try {
			// obtenemos el departamento de la oficina
			ScrOfic scrOfic = getDepartamentoOficina(idOficina);
			if (scrOfic != null) {
				result = new DepartamentoOficinaVO();
				String idDepartamento = Integer.toString(scrOfic.getDeptid());
				result.setId(idDepartamento);
			}
		} catch (HibernateException e) {
			throw new DepartamentoException("No se puede recuperar el departamento de la oficina ",e);
		}

		return result;
	}

	public List<BaseDepartamentoVO> getDepartamentosGrupoLdap(
			Integer idGrupoLdap) {

		List<BaseDepartamentoVO> results = new LinkedList<BaseDepartamentoVO>();
		BaseDepartamentoVOMapper mapper = new BaseDepartamentoVOMapper();
		try {
			List<Iuserdepthdr> list = ISicresQueries.getUserDeptHdrByCrtrId(
					getSession(), idGrupoLdap);

			for (Iuserdepthdr iuserdepthdr : list) {
				BaseDepartamentoVO departamentoVO = mapper.map(iuserdepthdr);
				results.add(departamentoVO);
			}
		} catch (HibernateException e) {
			throw new DepartamentoException(
					"No se ha podido recuperar la lista de departamentos asaociados al grupo ldap: ["
							+ idGrupoLdap + "]", e);
		}

		return results;

	}

	public List<BaseDepartamentoVO> findDepartamentos() {

		List<BaseDepartamentoVO> results = new LinkedList<BaseDepartamentoVO>();
		try {
			BaseDepartamentoVOMapper mapper = new BaseDepartamentoVOMapper();
			List<Iuserdepthdr> depts = ISicresQueries.getDepts(getSession(),
					null);
			if (depts != null) {
				for (Iuserdepthdr iuserdepthdr : depts) {
					BaseDepartamentoVO departamento = mapper.map(iuserdepthdr);
					results.add(departamento);
				}
			}
		} catch (HibernateException e) {
			throw new DepartamentoException(
					"No se puede recuperar el listado de departamentos", e);
		}
		return results;
	}

	public BaseDepartamentoVO getDepartamentoById(Integer idDepartamento) {

		BaseDepartamentoVO departamento = null;
		BaseDepartamentoVOMapper mapper = new BaseDepartamentoVOMapper();
		try {
			Iuserdepthdr dept = ISicresQueries.getDept(getSession(),
					idDepartamento);

			departamento = mapper.map(dept);

		} catch (HibernateException e) {
			throw new DepartamentoException(
					"No se puede recuperar el departamento con el identificador: ["
							+ idDepartamento + "]", e);
		}
		return departamento;
	}


	protected ScrOfic getDepartamentoOficina(String idOficina)
			throws HibernateException {

		ScrOfic result = null;

		StringBuffer query = new StringBuffer();
		query.append("FROM ");
		query.append(HibernateKeys.HIBERNATE_ScrOfic);
		query.append(" scr WHERE scr.id=?");
		List list = getSession().find(query.toString(),
				new Object[] { idOficina }, new Type[] { Hibernate.INTEGER });

		if (list != null && !list.isEmpty()) {
			result = (ScrOfic) list.get(0);
		}
		return result;
	}

	protected ScrOfic getDepartamentoUsuario(String idUsuario)
			throws HibernateException {

		ScrOfic result = null;

		// query para sacar el departamento del usuario
		StringBuffer queryIuseruserhdr = new StringBuffer();
		queryIuseruserhdr
				.append("deptid in (select deptid from iuseruserhdr where id = ");
		queryIuseruserhdr.append(idUsuario);
		queryIuseruserhdr.append(")");

		Criteria criteria = getSession().createCriteria(ScrOfic.class);
		criteria.add(Expression.sql(queryIuseruserhdr.toString()));
		List oficinasDepartamentoList = criteria.list();

		if (oficinasDepartamentoList != null
				&& !oficinasDepartamentoList.isEmpty()) {
			result = (ScrOfic) oficinasDepartamentoList.get(0);

		}
		return result;

	}

}
