package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceMattersManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.ISWebServiceMattersSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterType;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterTypesResponse;

public class ISWebServiceMattersSoapImpl implements ISWebServiceMattersSoap {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.ISWebServiceMattersSoap
	 * #wsLoadMatterTypes(int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security)
	 */
	public WSMatterTypesResponse wsLoadMatterTypes(int initValue, int size,
			Security security) {

		return getIsWebServiceMattersManager().loadMatterTypes(initValue, size,
				security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.ISWebServiceMattersSoap
	 * #wsMatterTypesFromCondition(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security)
	 */
	public WSMatterTypesResponse wsMatterTypesFromCondition(String condition,
			int initValue, int size, Security security) {

		return getIsWebServiceMattersManager().matterTypesFromCondition(
				condition, initValue, size, security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.ISWebServiceMattersSoap
	 * #wsValidateMatterTypeCode(java.lang.String,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security)
	 */
	public WSMatterType wsValidateMatterTypeCode(String matterTypeCode,
			Security security) {

		return getIsWebServiceMattersManager().validateMatterTypeCode(
				matterTypeCode, security);
	}

	public ISWebServiceMattersManager getIsWebServiceMattersManager() {
		return isWebServiceMattersManager;
	}

	public void setIsWebServiceMattersManager(
			ISWebServiceMattersManager isWebServiceMattersManager) {
		this.isWebServiceMattersManager = isWebServiceMattersManager;
	}

	protected LoginManager getLoginManager() {
		return loginManager;
	}

	protected void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	// Members
	protected ISWebServiceMattersManager isWebServiceMattersManager;

	protected LoginManager loginManager;

}
