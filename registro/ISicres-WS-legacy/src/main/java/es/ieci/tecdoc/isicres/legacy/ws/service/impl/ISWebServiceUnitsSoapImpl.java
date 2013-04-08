package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceUnitsManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitTypesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitsResponse;

public class ISWebServiceUnitsSoapImpl implements ISWebServiceUnitsSoap {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap#
	 * wsLoadUnitTypes(int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	public WSUnitTypesResponse wsLoadUnitTypes(int initValue, int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security) {

		return getIsWebServiceUnitsManager().loadUnitTypes(initValue, size,
				security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap#
	 * wsLoadUnitsFromCondition(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	public WSUnitsResponse wsLoadUnitsFromCondition(String condition,
			int initValue, int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security) {

		return getIsWebServiceUnitsManager().loadUnitsFromCondition(condition,
				initValue, size, security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap#
	 * wsLoadUnitsFromType(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	public WSUnitsResponse wsLoadUnitsFromType(String typeCode, int initValue,
			int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security) {

		return getIsWebServiceUnitsManager().loadUnitsFromType(typeCode,
				initValue, size, security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap#
	 * wsLoadUnitsFromUnit(java.lang.String, int, int,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	public WSUnitsResponse wsLoadUnitsFromUnit(String unitCode, int initValue,
			int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security) {

		return getIsWebServiceUnitsManager().loadUnitsFromUnit(unitCode,
				initValue, size, security);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.ISWebServiceUnitsSoap#
	 * wsValidateUnitCode(java.lang.String,
	 * es.ieci.tecdoc.isicres.ws.legacy.service.units.Security)
	 */
	public WSUnit wsValidateUnitCode(String unitCode,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security) {

		return getIsWebServiceUnitsManager().validateUnitCode(unitCode,
				security);
	}

	public ISWebServiceUnitsManager getIsWebServiceUnitsManager() {
		return isWebServiceUnitsManager;
	}

	public void setIsWebServiceUnitsManager(
			ISWebServiceUnitsManager isWebServiceUnitsManager) {
		this.isWebServiceUnitsManager = isWebServiceUnitsManager;
	}

	// Members
	protected ISWebServiceUnitsManager isWebServiceUnitsManager;
}
