package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceDistributionsManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ISWebServiceDistributionsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionEx;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSAcceptDistributionExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistribution;
import es.ieci.tecdoc.isicres.ws.legacy.service.distributions.WSDistributionsResponse;

public class ISWebServiceDistributionsSoapImpl implements
		ISWebServiceDistributionsSoap {

	/**
	 * {@inheritDoc}
	 */
	public void wsAcceptDistribution(String numReg, Security security) {

		getIsWebServiceDistributionsManager().acceptDistribution(numReg,
				security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSAcceptDistributionExResponse wsAcceptDistributionExOp(
			WSAcceptDistributionEx parameters, Security security) {

		return getIsWebServiceDistributionsManager()
				.acceptDistributionExResponse(parameters, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsArchiveDistribution(String numReg, Security security) {

		getIsWebServiceDistributionsManager().archiveDistribution(numReg,
				security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSDistributionsResponse wsLoadInputDistributions(int state,
			int initValue, int size, Security security) {

		return getIsWebServiceDistributionsManager().loadInputDistributions(
				state, initValue, size, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSDistributionsResponse wsLoadInputDistributionsByUserId(int state,
			int userId, int initValue, int size, Security security) {

		return getIsWebServiceDistributionsManager()
				.loadInputDistributionsByUserId(state, userId, initValue, size,
						security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSDistributionsResponse wsLoadOutputDistributions(int state,
			int initValue, int size, Security security) {

		return getIsWebServiceDistributionsManager().loadOutputDistributions(
				state, initValue, size, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public WSDistributionsResponse wsLoadOutputDistributionsByUserId(int state,
			int userId, int initValue, int size, Security security) {

		return getIsWebServiceDistributionsManager()
				.loadOutputDistributionsByUserId(state, userId, initValue,
						size, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsRedistributeInputDistribution(String numReg, String codeDest,
			Security security) {

		getIsWebServiceDistributionsManager().redistributeInputDistribution(
				numReg, codeDest, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsRedistributeOutputDistribution(String numReg,
			String codeDest, Security security) {

		getIsWebServiceDistributionsManager().redistributeOutputDistribution(
				numReg, codeDest, security);
	}

	/**
	 * {@inheritDoc}
	 */
	public void wsRejectDistribution(String numReg, String matter,
			Security security) {

		getIsWebServiceDistributionsManager().rejectDistribution(numReg,
				matter, security);
	}

	
	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ISWebServiceDistributionsSoap#wsLoadDistributionsByCondition(java.lang.String, int, int, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistributionsResponse wsLoadInputDistributionsByCondition(
			String condition, int initValue, int size, Security security) {
		
		return getIsWebServiceDistributionsManager().loadInputDistributionsByCondition(condition, initValue, size, security);
	}

	/* (non-Javadoc)
	 * @see es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ISWebServiceDistributionsSoap#wsRegisterDistribute(java.lang.String, int, int, int, java.lang.String, int, int, java.lang.String, java.lang.String, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistribution wsRegisterDistribute(String numReg, int bookId,
			int senderType, int senderId, String senderName,
			int destinationType, int destinationId, String destinationName,
			String matter, Security security) {
		
		return this.isWebServiceDistributionsManager.registerDistribute(numReg, bookId, senderType, senderId, senderName, destinationType, destinationId, destinationName, matter, security);
	}

	public void wsRedistributeInputDistributionManual(String numReg,
			int destinationType, int destinationId, String destinationName,
			String matter, Security security) {
		getIsWebServiceDistributionsManager().redistributeInputDistributionManual(numReg, destinationType, destinationId, destinationName, matter, security);
		
	}

	public void wsRedistributeOutputDistributionManual(String numReg,
			int destinationType, int destinationId, String destinationName,
			String matter, Security security) {
		getIsWebServiceDistributionsManager().redistributeOutputDistributionManual(numReg, destinationType, destinationId, destinationName, matter, security);
		
	}
	
	

	public ISWebServiceDistributionsManager getIsWebServiceDistributionsManager() {

		return isWebServiceDistributionsManager;
	}

	public void setIsWebServiceDistributionsManager(
			ISWebServiceDistributionsManager isWebServiceDistributionsManager) {
		this.isWebServiceDistributionsManager = isWebServiceDistributionsManager;
	}

	// Members
	protected ISWebServiceDistributionsManager isWebServiceDistributionsManager;


	public WSDistributionsResponse wsLoadDistributionsByConditionEx(
			String query, int initValue, int size, Security security) {
		
		return getIsWebServiceDistributionsManager().loadDistributionsByConditionEx(query, initValue, size, security);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.ws.legacy.service.distributions.ISWebServiceDistributionsSoap#wsLoadOutputDistributionsByCondition(java.lang.String, int, int, es.ieci.tecdoc.isicres.ws.legacy.service.distributions.Security)
	 */
	public WSDistributionsResponse wsLoadOutputDistributionsByCondition(
			String condition, int initValue, int size, Security security) {
		return getIsWebServiceDistributionsManager().loadOutputDistributionsByCondition(condition, initValue, size, security);
	}


	


	

	


	
}
