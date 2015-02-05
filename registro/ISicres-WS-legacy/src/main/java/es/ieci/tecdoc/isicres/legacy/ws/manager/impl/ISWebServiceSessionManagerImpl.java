package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceSessionManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.UsuarioVOToWSSessionMapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.WSSession;

public class ISWebServiceSessionManagerImpl implements
		ISWebServiceSessionManager {

	public WSSession wsLogin(UsuarioVO anUsuarioVO) {

		//Seteamos los datos necesarios para la auditoria
		AuditInfoHelper.setInfoAuditContextHolder();

		UsuarioVO login = getLoginManager().login(anUsuarioVO);

		return (WSSession) new UsuarioVOToWSSessionMapper().map(login);
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	// Members

	protected LoginManager loginManager;

}
