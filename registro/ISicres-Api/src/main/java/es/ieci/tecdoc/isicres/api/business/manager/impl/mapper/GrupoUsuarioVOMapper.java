/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import com.ieci.tecdoc.common.invesdoc.Iusergrouphdr;
import com.ieci.tecdoc.common.invesdoc.Iusergroupuser;
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;

import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GrupoUsuarioVOMapper {

	public GrupoUsuarioVO map(Iusergroupuser groupUser) {
		GrupoUsuarioVO grupoUsuarioVO = null;

		if (groupUser != null) {
			grupoUsuarioVO = new GrupoUsuarioVO();
			grupoUsuarioVO.setId(String.valueOf(groupUser.getGroupid()));
		}
		return grupoUsuarioVO;

	}



	public GrupoUsuarioVO map(Iusergrouphdr iusergrouphdr) {
		GrupoUsuarioVO grupoUsuarioVO = null;

		if (iusergrouphdr != null) {
			grupoUsuarioVO = new GrupoUsuarioVO();
			grupoUsuarioVO.setId(String.valueOf(iusergrouphdr.getId()));
		}
		return grupoUsuarioVO;

	}

	public GrupoUsuarioVO map(Iuserldapgrphdr iuserldapgrphdr) {
		GrupoUsuarioVO grupoUsuarioVO = null;

		if (iuserldapgrphdr != null) {
			grupoUsuarioVO = new GrupoUsuarioVO();
			grupoUsuarioVO.setId(String.valueOf(iuserldapgrphdr.getId()));
		}
		return grupoUsuarioVO;

	}


}
