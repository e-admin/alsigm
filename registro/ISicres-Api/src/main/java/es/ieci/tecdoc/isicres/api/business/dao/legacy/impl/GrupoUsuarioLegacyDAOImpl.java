/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.invesdoc.Iusergrouphdr;
import com.ieci.tecdoc.common.invesdoc.Iusergroupuser;
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;
import com.ieci.tecdoc.common.utils.ISicresQueries;

import es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO;
import es.ieci.tecdoc.isicres.api.business.exception.GrupoUsuarioException;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.GrupoUsuarioVOMapper;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.UsuarioVOMapper;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GrupoUsuarioLegacyDAOImpl extends IsicresBaseHibernateDAOImpl
		implements GrupoUsuarioDAO {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GrupoUsuarioLegacyDAOImpl.class);

	GrupoUsuarioVOMapper grupoUsuarioVOMapper = new GrupoUsuarioVOMapper();
	UsuarioVOMapper usuarioVOMapper = new UsuarioVOMapper();

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO#getGrupoById(java.lang.String)
	 */
	public GrupoUsuarioVO getGrupoById(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug("getGrupoById(Integer) - start");
		}

		GrupoUsuarioVO grupoUsuarioVO = null;

		try {
			Iusergrouphdr iusergrouphdr = (Iusergrouphdr) getSession().load(
					Iusergrouphdr.class, id);
			grupoUsuarioVO = grupoUsuarioVOMapper.map(iusergrouphdr);
		} catch(ObjectNotFoundException e){
			logger.error("getGrupoById(Integer)", e);

		} catch (HibernateException e) {
			logger.error("getGrupoById(Integer)", e);

			throw new GrupoUsuarioException(
					"No se puede obtener el grupo con el identificador [" + id
							+ "]", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getGrupoById(Integer) - end");
		}
		return grupoUsuarioVO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO#getGrupos()
	 */
	public List<GrupoUsuarioVO> getGrupos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getGrupos() - start");
		}

		List<GrupoUsuarioVO> result = new LinkedList<GrupoUsuarioVO>();
		try {
			List<Iusergrouphdr> grupos = ISicresQueries.getGroups(getSession(),
					null);
			for (Iusergrouphdr iusergrouphdr : grupos) {
				GrupoUsuarioVO grupo = grupoUsuarioVOMapper.map(iusergrouphdr);
				result.add(grupo);
			}

		} catch (HibernateException e) {
			logger.error("getGrupos()", e);

			throw new GrupoUsuarioException(
					"No se puede obtener la lista de grupos", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getGrupos() - end");
		}
		return result;
	}

	public List<GrupoUsuarioVO> getGruposLdap() {
		if (logger.isDebugEnabled()) {
			logger.debug("getGruposLdap() - start");
		}

		List<GrupoUsuarioVO> result = new LinkedList<GrupoUsuarioVO>();
		try {
			List<Iuserldapgrphdr> grupos = ISicresQueries.getLdapGroups(
					getSession(), null);
			for (Iuserldapgrphdr iuserldapgrphdr : grupos) {
				GrupoUsuarioVO grupo = grupoUsuarioVOMapper
						.map(iuserldapgrphdr);
				result.add(grupo);
			}

		} catch (HibernateException e) {
			logger.error("getGruposLdap()", e);

			throw new GrupoUsuarioException(
					"No se puede obtener la lista de grupos", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getGruposLdap() - end");
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO#getUsuariosDelGrupo(es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO)
	 */
	public List<BaseUsuarioVO> getUsuariosDelGrupo(
			Integer idGrupo) {
		if (logger.isDebugEnabled()) {
			logger.debug("findUsuariosDelGrupo(GrupoUsuarioVO) - start");
		}

		List<BaseUsuarioVO> result = new LinkedList<BaseUsuarioVO>();
		try {
			List<Iusergroupuser> lista = ISicresQueries.getIUserGroupUserByGroupId(
					getSession(), idGrupo);

			for (Iusergroupuser iusergroupuser : lista) {

				Iuseruserhdr iuseruserhdr = ISicresQueries.getUserUserHdr(getSession(), iusergroupuser.getUserid());

				result.add(usuarioVOMapper.map(iuseruserhdr));
			}
		} catch (HibernateException e) {
			logger.error("getGruposPertenecientesUsuario(Integer)", e);

			throw new GrupoUsuarioException(
					"No se puede obtener la lista de grupos del usuario", e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO#getGruposPertenecientesUsuario(es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO)
	 */
	public List<GrupoUsuarioVO> getGruposPertenecientesUsuario(
			Integer idUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("getGruposPertenecientesUsuario(Integer) - start");
		}

		List<GrupoUsuarioVO> result = new LinkedList<GrupoUsuarioVO>();
		try {
			List<Iusergroupuser> lista = ISicresQueries.getIUserGroupUser(
					getSession(), idUsuario);

			for (Iusergroupuser iusergroupuser : lista) {
				GrupoUsuarioVO grupoUsuarioVO = grupoUsuarioVOMapper
						.map(iusergroupuser);
				result.add(grupoUsuarioVO);
			}
		} catch (HibernateException e) {
			logger.error("getGruposPertenecientesUsuario(Integer)", e);

			throw new GrupoUsuarioException(
					"No se puede obtener la lista de grupos del usuario", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getGruposPertenecientesUsuario(Integer) - end");
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.GrupoUsuarioDAO#getGruposNoPertenecientesUsuario(es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO)
	 */
	public List<GrupoUsuarioVO> getGruposNoPertenecientesUsuario(
			Integer idUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("findGruposNoPertenecientesUsuario(Integer) - start");
		}

		List<GrupoUsuarioVO> result = new LinkedList<GrupoUsuarioVO>();

		GrupoUsuarioVOMapper grupoUsuarioVOMapper = new GrupoUsuarioVOMapper();
		try {
			List<Iusergroupuser> gruposUsuario = ISicresQueries
					.getIUserGroupUser(getSession(), idUsuario);
			List<Iusergrouphdr> gruposNoPertenecientesUsuario = ISicresQueries
					.getGroups(getSession(), gruposUsuario);

			for (Iusergrouphdr iusergrouphdr : gruposNoPertenecientesUsuario) {
				GrupoUsuarioVO grupoUsuarioVO = grupoUsuarioVOMapper
						.map(iusergrouphdr);
				result.add(grupoUsuarioVO);
			}
		} catch (HibernateException e) {
			logger.error("getGruposNoPertenecientesUsuario(Integer)", e);

			throw new GrupoUsuarioException(
					"No se puede obtener la lista de grupos a los que no pertenece el usuario",
					e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getGruposNoPertenecientesUsuario(Integer) - end");
		}
		return result;
	}

}
