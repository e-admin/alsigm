package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddress;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddressData;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSStakeholder;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSThirdPartyPhisical;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ISWebServiceThirdPartiesSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCitiesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCityResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadCities;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadCitiesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadPostalAddressesByThirdParty;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadPostalAddressesByThirdPartyResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadProvinces;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadProvincesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSPostalAddressesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvinceResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholder;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholderData;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisical;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisicalData;

public class ISWebServiceThirdPartiesSoapImpl implements ISWebServiceThirdPartiesSoap {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ISWebServiceThirdPartiesSoapImpl.class);

	protected ISWebServiceThirdPartiesManager isWebServiceThirdPartiesManager;

	public ISWebServiceThirdPartiesManager getIsWebServiceThirdPartiesManager() {
		if (logger.isDebugEnabled()) {
			logger.debug("getIsWebServiceThirdPartiesManager() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getIsWebServiceThirdPartiesManager() - end");
		}
		return isWebServiceThirdPartiesManager;
	}

	public void setIsWebServiceThirdPartiesManager(
			ISWebServiceThirdPartiesManager isWebServiceThirdPartiesManager) {
		if (logger.isDebugEnabled()) {
			logger.debug("setIsWebServiceThirdPartiesManager(ISWebServiceThirdPartiesManager) - start");
		}

		this.isWebServiceThirdPartiesManager = isWebServiceThirdPartiesManager;

		if (logger.isDebugEnabled()) {
			logger.debug("setIsWebServiceThirdPartiesManager(ISWebServiceThirdPartiesManager) - end");
		}
	}

	public WSCityResponse wsLoadCityByCode(String code, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityByCode(String, Security) - start");
		}

		WSCityResponse returnWSCityResponse = isWebServiceThirdPartiesManager.loadCityByCode(code,
				security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityByCode(String, Security) - end");
		}
		return returnWSCityResponse;
	}

	public WSPostalAddressesResponse wsAddPostalAddresses(int idThirdParty,
			ArrayOfWSPostalAddressData addressesToAdd, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsAddPostalAddresses(int, ArrayOfWSPostalAddressData, Security) - start");
		}

		WSPostalAddressesResponse returnWSPostalAddressesResponse = getIsWebServiceThirdPartiesManager()
				.addPostalAddresses(idThirdParty, addressesToAdd, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsAddPostalAddresses(int, ArrayOfWSPostalAddressData, Security) - end");
		}
		return returnWSPostalAddressesResponse;
	}

	public WSStakeholder wsAddStakeholder(WSStakeholderData thirdPartyPhisicalToAdd,
			Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsAddStakeholder(WSStakeholderData, Security) - start");
		}

		WSStakeholder returnWSStakeholder = isWebServiceThirdPartiesManager.addStakeholder(
				thirdPartyPhisicalToAdd, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsAddStakeholder(WSStakeholderData, Security) - end");
		}
		return returnWSStakeholder;
	}

	public WSThirdPartyPhisical wsAddThirdPartyPhisical(
			WSThirdPartyPhisicalData thirdPartyPhisicalToAdd, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsAddThirdPartyPhisical(WSThirdPartyPhisicalData, Security) - start");
		}

		WSThirdPartyPhisical returnWSThirdPartyPhisical = isWebServiceThirdPartiesManager
				.addThirdPartyPhisical(thirdPartyPhisicalToAdd, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsAddThirdPartyPhisical(WSThirdPartyPhisicalData, Security) - end");
		}
		return returnWSThirdPartyPhisical;
	}

	public WSLoadCitiesResponse wsLoadCities(WSLoadCities parameters, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCities(WSLoadCities, Security) - start");
		}

		WSLoadCitiesResponse returnWSLoadCitiesResponse = getIsWebServiceThirdPartiesManager()
				.loadCities(parameters, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCities(WSLoadCities, Security) - end");
		}
		return returnWSLoadCitiesResponse;
	}

	public WSCityResponse wsLoadCityById(long id, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityById(long, Security) - start");
		}

		WSCityResponse returnWSCityResponse = getIsWebServiceThirdPartiesManager().loadCityById(id,
				security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityById(long, Security) - end");
		}
		return returnWSCityResponse;
	}

	public WSCitiesResponse wsLoadCityByIdProvNameProv(long idProv, String nameProv, int initValue,
			int size, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityByIdProvNameProv(long, String, int, int, Security) - start");
		}

		WSCitiesResponse returnWSCitiesResponse = getIsWebServiceThirdPartiesManager()
				.loadCityByIdProvNameProv(idProv, nameProv, initValue, size, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityByIdProvNameProv(long, String, int, int, Security) - end");
		}
		return returnWSCitiesResponse;
	}

	public WSCityResponse wsLoadCityByName(String name, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityByName(String, Security) - start");
		}

		WSCityResponse returnWSCityResponse = getIsWebServiceThirdPartiesManager().loadCityByName(
				name, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadCityByName(String, Security) - end");
		}
		return returnWSCityResponse;
	}

	public WSLoadPostalAddressesByThirdPartyResponse wsLoadPostalAddressesByThirdParty(
			WSLoadPostalAddressesByThirdParty parameters, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadPostalAddressesByThirdParty(WSLoadPostalAddressesByThirdParty, Security) - start");
		}

		WSLoadPostalAddressesByThirdPartyResponse returnWSLoadPostalAddressesByThirdPartyResponse = getIsWebServiceThirdPartiesManager()
				.loadPostalAddressesByThirdParty(parameters, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadPostalAddressesByThirdParty(WSLoadPostalAddressesByThirdParty, Security) - end");
		}
		return returnWSLoadPostalAddressesByThirdPartyResponse;
	}

	public WSProvinceResponse wsLoadProvinceByCode(String code, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinceByCode(String, Security) - start");
		}

		WSProvinceResponse returnWSProvinceResponse = getIsWebServiceThirdPartiesManager()
				.loadProvinceByCode(code, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinceByCode(String, Security) - end");
		}
		return returnWSProvinceResponse;
	}

	public WSProvinceResponse wsLoadProvinceById(long id, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinceById(long, Security) - start");
		}

		WSProvinceResponse returnWSProvinceResponse = getIsWebServiceThirdPartiesManager()
				.loadProvinceById(id, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinceById(long, Security) - end");
		}
		return returnWSProvinceResponse;
	}

	public WSProvinceResponse wsLoadProvinceByName(String name, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinceByName(String, Security) - start");
		}

		WSProvinceResponse returnWSProvinceResponse = getIsWebServiceThirdPartiesManager()
				.loadProvinceByName(name, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinceByName(String, Security) - end");
		}
		return returnWSProvinceResponse;
	}

	public WSLoadProvincesResponse wsLoadProvinces(WSLoadProvinces parameters, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinces(WSLoadProvinces, Security) - start");
		}

		WSLoadProvincesResponse returnWSLoadProvincesResponse = getIsWebServiceThirdPartiesManager()
				.loadProvinces(parameters, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadProvinces(WSLoadProvinces, Security) - end");
		}
		return returnWSLoadProvincesResponse;
	}

	public ArrayOfWSStakeholder wsLoadStakeholders(long bookId, long folderId, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadStakeholders(long, long, Security) - start");
		}

		ArrayOfWSStakeholder returnArrayOfWSStakeholder = isWebServiceThirdPartiesManager
				.loadStakeholders(bookId, folderId, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsLoadStakeholders(long, long, Security) - end");
		}
		return returnArrayOfWSStakeholder;
	}

	public long wsRemoveStakeholder(long idStakeholder, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsRemoveStakeholder(long, Security) - start");
		}

		long returnlong = isWebServiceThirdPartiesManager
				.removeStakeholder(idStakeholder, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsRemoveStakeholder(long, Security) - end");
		}
		return returnlong;
	}


	public long wsUpdateNotifyAddressStakeholder(long idStakeholder, long idAddress,
			Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsUpdateNotifyAddressStakeholder(long, long, Security) - start");
		}
		
		long returnlong = isWebServiceThirdPartiesManager.updateNotifyAddressStakeholder(
				idStakeholder, idAddress, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsUpdateNotifyAddressStakeholder(long, long, Security) - end");
		}
		return returnlong;
	}

	public WSPostalAddressesResponse wsUpdatePostalAddresses(ArrayOfWSPostalAddress addressesToMod,
			Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsUpdatePostalAddresses(ArrayOfWSPostalAddress, Security) - start");
		}
			
		WSPostalAddressesResponse returnWSPostalAddressesResponse = isWebServiceThirdPartiesManager
				.wsUpdatePostalAddresses(addressesToMod, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsUpdatePostalAddresses(ArrayOfWSPostalAddress, Security) - end");
		}
		return returnWSPostalAddressesResponse;
	}

	public WSThirdPartyPhisical wsUpdateThirdPartyPhisical(
			WSThirdPartyPhisical thirdPartyPhisicalToAdd, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsUpdateThirdPartyPhisical(WSThirdPartyPhisical, Security) - start");
		}
		
		WSThirdPartyPhisical returnWSThirdPartyPhisical = isWebServiceThirdPartiesManager
				.updateThirdPartyPhisical(thirdPartyPhisicalToAdd, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsUpdateThirdPartyPhisical(WSThirdPartyPhisical, Security) - end");
		}
		return returnWSThirdPartyPhisical;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ISWebServiceThirdPartiesSoap#wsThirdPartyPhisicalByDocumentNumber(long, java.lang.String, es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public ArrayOfWSThirdPartyPhisical wsThirdPartyPhisicalByDocumentNumber(long tipoDocumento,
			String documentNumber, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("wsThirdPartyPhisicalByDocumentNumber(long, String, Security) - start");
		}
		
		ArrayOfWSThirdPartyPhisical returnArrayOfWSThirdPartyPhisical = isWebServiceThirdPartiesManager
				.wsThirdPartyPhisicalByDocumentNumber(tipoDocumento, documentNumber, security);
		if (logger.isDebugEnabled()) {
			logger.debug("wsThirdPartyPhisicalByDocumentNumber(long, String, Security) - end");
		}
		return returnArrayOfWSThirdPartyPhisical;
	}

}
