package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceSessionManager;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.ISWebServiceSessionSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.session.WSSession;

/**
 * 
 * @author IECISA
 * 
 */
public class ISWebServiceSessionSoapImpl implements ISWebServiceSessionSoap {

	public WSSession wsLogin(Security security) {

		return getIsWebServiceSessionManager().wsLogin(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioBuilder) {
		this.usuarioBuilder = usuarioBuilder;
	}

	public ISWebServiceSessionManager getIsWebServiceSessionManager() {
		return isWebServiceSessionManager;
	}

	public void setIsWebServiceSessionManager(
			ISWebServiceSessionManager isWebServiceSessionManager) {
		this.isWebServiceSessionManager = isWebServiceSessionManager;
	}

	// Members
	protected UsuarioVOBuilder usuarioBuilder;

	protected ISWebServiceSessionManager isWebServiceSessionManager;
}
