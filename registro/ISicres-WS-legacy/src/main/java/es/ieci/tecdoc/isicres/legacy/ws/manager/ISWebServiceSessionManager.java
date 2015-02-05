package es.ieci.tecdoc.isicres.legacy.ws.manager;

import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.WSSession;

public interface ISWebServiceSessionManager {

	/**
	 * 
	 * @param anUsuarioVO
	 * @return
	 */
	public WSSession wsLogin(UsuarioVO anUsuarioVO);

}
