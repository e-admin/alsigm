/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;

import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoUsuarioEnum;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class UsuarioVOMapper {

	public BaseUsuarioVO map(Iuseruserhdr user) {
		UsuarioVO usuarioVO = null;

		if (user != null) {
			usuarioVO = new UsuarioVO();
			usuarioVO.setFullName(user.getRemarks());
			usuarioVO.setId(String.valueOf(user.getId()));
			usuarioVO.setLoginName(user.getName());
			usuarioVO.setPassword(user.getPassword());
			usuarioVO.setLdapUser(false);
			EstadoUsuarioEnum estado = EstadoUsuarioEnum.getEnum(user.getStat());
			usuarioVO.setEstado(estado);
		}
		return usuarioVO;
	}

	public BaseUsuarioVO map(Iuserldapuserhdr user) {

		UsuarioVO usuarioVO = null;
		if (user != null) {
			usuarioVO = new UsuarioVO();
			usuarioVO.setId(String.valueOf(user.getId()));
			usuarioVO.setFullName(user.getLdapfullname());
			usuarioVO.setLoginName(user.getLdapfullname());
			usuarioVO.setLdapGUID(user.getLdapguid());
			usuarioVO.setLdapUser(true);
			usuarioVO.setEstado(EstadoUsuarioEnum.DESBLOQUEADO);
		}
		return usuarioVO;
	}



}
