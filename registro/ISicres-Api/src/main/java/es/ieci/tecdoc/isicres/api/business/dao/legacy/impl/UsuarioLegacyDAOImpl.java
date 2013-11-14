/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;
import com.ieci.tecdoc.common.utils.ISicresQueries;

import es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO;
import es.ieci.tecdoc.isicres.api.business.exception.UsuarioException;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.UsuarioVOMapper;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosUsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class UsuarioLegacyDAOImpl extends IsicresBaseHibernateDAOImpl implements
		UsuarioDAO {

	private static final Logger logger = Logger
			.getLogger(UsuarioLegacyDAOImpl.class);

	UsuarioVOMapper usuarioVOMapper = new UsuarioVOMapper();


	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO#getUsuarioById(java.lang.String)
	 */
	public BaseUsuarioVO getUsuarioById(Integer id) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioById(String) - start");
		}

		BaseUsuarioVO usuarioVO = null;

		try {

			try {
				Iuseruserhdr user = ISicresQueries.getUserUserHdr(getSession(),
						id);

				usuarioVO = usuarioVOMapper.map(user);
			} catch (ObjectNotFoundException e) {
				Iuserldapuserhdr ldapUser = ISicresQueries.getUserLdapUser(
						getSession(), id);
				usuarioVO = usuarioVOMapper.map(ldapUser);
			}

		} catch (HibernateException e) {
			throw new UsuarioException(
					"No se puede recuperar el usuario con el identificador: ["
							+ id + "]", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioById(String) - end");
		}
		return usuarioVO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO#getUsuarioByLogin(java.lang.String)
	 */
	public BaseUsuarioVO getUsuarioByLogin(String login) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioByName(String) - start");
		}

		BaseUsuarioVO usuarioVO = null;
		try {
			List<Iuseruserhdr> users = (List<Iuseruserhdr>) ISicresQueries
					.getUserUserHdrByName(getSession(), login);
			if (CollectionUtils.isNotEmpty(users)) {
				Iuseruserhdr user = users.get(0);
				usuarioVO = usuarioVOMapper.map(user);
			} else {
				List<Iuserldapuserhdr> ldapUsers = ISicresQueries
						.getUserLdapUserByFullName(getSession(), login);
				if (CollectionUtils.isNotEmpty(ldapUsers)) {
					Iuserldapuserhdr user = ldapUsers.get(0);
					usuarioVO = usuarioVOMapper.map(user);
				}
			}
		} catch (HibernateException e) {
			throw new UsuarioException(
					"No se puede recuperar el usuario con el login: [" + login
							+ "]", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioByName(String) - end");
		}
		return usuarioVO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO#getUsuarios()
	 */
	public List<BaseUsuarioVO> getUsuarios() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarios() - start");
		}

		UsuarioVOMapper usuarioVOMapper = new UsuarioVOMapper();
		List<BaseUsuarioVO> lista = new LinkedList<BaseUsuarioVO>();
		try {
			List<Iuseruserhdr> users = (List<Iuseruserhdr>) ISicresQueries
					.getUsers(getSession());
			if (CollectionUtils.isNotEmpty(users)) {
				for (Iuseruserhdr iuseruserhdr : users) {
					BaseUsuarioVO usuarioVO = usuarioVOMapper.map(iuseruserhdr);
					if (usuarioVO != null) {
						lista.add(usuarioVO);
					}
				}
			}
		} catch (HibernateException e) {
			throw new UsuarioException(
					"No se puede recuperar la lista de todos los usuarios", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarios() - end");
		}
		return lista;
	}

	public List<BaseUsuarioVO> getUsuariosLdap() {

		UsuarioVOMapper usuarioVOMapper = new UsuarioVOMapper();
		List<BaseUsuarioVO> lista = new LinkedList<BaseUsuarioVO>();
		try {
			List<Iuserldapuserhdr> users = ISicresQueries
					.getLdapUsers(getSession());
			if (CollectionUtils.isNotEmpty(users)) {
				for (Iuserldapuserhdr iuseruserhdr : users) {
					BaseUsuarioVO usuarioVO = usuarioVOMapper.map(iuseruserhdr);
					if (usuarioVO != null) {
						lista.add(usuarioVO);
					}
				}
			}
		} catch (HibernateException e) {
			throw new UsuarioException(
					"No se puede recuperar la lista de todos los usuarios ldap",
					e);
		}

		return lista;
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO#getConfiguracionUsuario(java.lang.String)
	 */
	public ConfiguracionUsuarioVO getConfiguracionUsuario(Integer idUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("getConfiguracionUsuario(String) - start");
		}

		// TODO Plantilla de método auto-generado

		if (logger.isDebugEnabled()) {
			logger.debug("getConfiguracionUsuario(String) - end");
		}
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.business.dao.UsuarioDAO#getPermisosUsuario(java.lang.Integer)
	 */
	public PermisosUsuarioVO getPermisosUsuario(Integer idUsuario) {
		// TODO Plantilla de método auto-generado
		throw new UnsupportedOperationException();
	}



}
