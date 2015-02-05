package es.ieci.tecdoc.isicres.legacy.ws.service.impl;

import java.util.List;

import junit.framework.Assert;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddress;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddressData;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSStakeholder;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSThirdPartyPhisical;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ISWebServiceThirdPartiesSoap;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.UsernameTokenClass;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCitiesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCity;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCityResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadCities;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadCitiesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadPostalAddressesByThirdParty;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadPostalAddressesByThirdPartyResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadProvinces;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadProvincesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSPostalAddress;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSPostalAddressData;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSPostalAddressesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvinceResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholder;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholderData;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisical;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisicalData;

public class ISWebServiceThirdPartiesSoapTest extends AbstractDependencyInjectionSpringContextTests {

	long bookId = 1;
	long folderId = 1;

	protected String[] getConfigLocations() {
		String[] result = new String[] { "beans/appContextTest.xml" };
		return result;
	}

	protected ISWebServiceThirdPartiesSoap getThirdPartiesClient() {

		ISWebServiceThirdPartiesSoap result = (ISWebServiceThirdPartiesSoap) this.applicationContext
				.getBean("clientWebServiceThirdParties");

		return result;
	}

	protected Security getSecurity() {
		Security result = new Security();
		UsernameTokenClass value = new UsernameTokenClass();
		value.setUsername("sigem");
		value.setPassword("sigem");
		value.setOfficeCode("001");
		result.setUsernameToken(value);
		return result;
	}

	public void testwsLoadCityByCode() {

		// Código de la ciudad "Alegria-Dulantzi", Id: 1
		String code = "L01001";
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		WSCityResponse wsResult = getThirdPartiesClient().wsLoadCityByCode(code, security);
		assertNotNull(wsResult);
		assertEquals(code, wsResult.getCityResult().getCode());
	}

	public void testwsLoadCityByCodeNotFound() {

		// Código de la ciudad "Alegria-Dulantzi", Id: 1
		String code = "L01001XX";
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		WSCityResponse wsResult = getThirdPartiesClient().wsLoadCityByCode(code, security);
		assertNull(wsResult);
		
	}

	public void testwsLoadCityById() {

		// Código de la ciudad "Alegria-Dulantzi", Id: 1
		long id = 1;
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		WSCityResponse wsResult = getThirdPartiesClient().wsLoadCityById(id, security);
		assertNotNull(wsResult);
		assertEquals(id, wsResult.getCityResult().getId());
	}

	public void testwsLoadCityByIdNotFound() {

		// Código de la ciudad "Alegria-Dulantzi", Id: 1
		long id = 1654654654;
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		WSCityResponse wsResult = getThirdPartiesClient().wsLoadCityById(id, security);
		assertNull(wsResult);		
	}

	public void testwsLoadCityByIdProvNameProv() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		WSCitiesResponse wsCitiesResponse = getThirdPartiesClient().wsLoadCityByIdProvNameProv(1,
				"Álava", 1, Integer.MAX_VALUE, security);
		List<WSCity> list = wsCitiesResponse.getCityResult().getArrayOfWSCity();
		assertTrue(list.size() > 0);
	}

	public void testwsLoadCities() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		WSLoadCities parameters = new WSLoadCities();
		parameters.setInitValue(1);
		parameters.setSize(Integer.MAX_VALUE);

		WSLoadCitiesResponse wsLoadCitiesResponse = getThirdPartiesClient().wsLoadCities(
				parameters, security);
		Assert.assertTrue(wsLoadCitiesResponse.getList().getCityResult().getArrayOfWSCity().size() > 0);
	}

	public void testwsLoadCityByName() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		String cityName = "Fuentealbilla";
		WSCityResponse wsCityResponse = getThirdPartiesClient()
				.wsLoadCityByName(cityName, security);
		Assert.assertEquals(cityName, wsCityResponse.getCityResult().getName());
	}

	public void testwsLoadCityByNameNotFound() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		String cityName = "City";
		WSCityResponse wsCityResponse = getThirdPartiesClient()
				.wsLoadCityByName(cityName, security);
		Assert.assertNull(wsCityResponse.getCityResult());
	}

	public void testwsLoadProvinceByCode() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		String code = "L33"; // Asturias
		WSProvinceResponse province = getThirdPartiesClient().wsLoadProvinceByCode(code, security);

		Assert.assertEquals(province.getProvinceResult().getCode(), code);
	}

	public void testwsLoadProvinceByCodeNotFound() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		String code = "XXXX"; 
		WSProvinceResponse province = getThirdPartiesClient().wsLoadProvinceByCode(code, security);

		Assert.assertNull(province);
	}

	public void testwsLoadProvinceById() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		WSProvinceResponse province = getThirdPartiesClient().wsLoadProvinceById(1, security);

		Assert.assertEquals(province.getProvinceResult().getId(), 1);
	}

	public void testwsLoadProvinceByIdNotFound() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		long id=165465464;

		WSProvinceResponse province = getThirdPartiesClient().wsLoadProvinceById(id, security);

		Assert.assertNull(province);

	}
	
	public void testwsLoadProvinceByName() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		String name = "Asturias";

		WSProvinceResponse province = getThirdPartiesClient().wsLoadProvinceByName(name, security);

		Assert.assertEquals(province.getProvinceResult().getName(), name);

	}

	public void testwsLoadProvinceByNameNotFound() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		String name = "XXXXXX";

		WSProvinceResponse province = getThirdPartiesClient().wsLoadProvinceByName(name, security);		
		Assert.assertNull(province);

	}

	public void testwsLoadProvinces() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		WSLoadProvinces parameters = new WSLoadProvinces();
		parameters.setInitValue(1);
		parameters.setSize(1);
		WSLoadProvincesResponse provinces = getThirdPartiesClient().wsLoadProvinces(parameters,
				security);

		Assert.assertEquals(1, provinces.getList().getProvincesResult().getArrayOfWSProvince()
				.size());

		Assert.assertEquals(1, provinces.getTotal());

		WSLoadProvinces parameters2 = new WSLoadProvinces();
		parameters2.setInitValue(1);
		parameters2.setSize(Integer.MAX_VALUE);

		provinces = getThirdPartiesClient().wsLoadProvinces(parameters2, security);

		Assert.assertEquals(52, provinces.getTotal());
	}

	public void testwsAddStakeholder() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		// Primero creamos el tercero para asociarlo al interesado
		WSThirdPartyPhisicalData thirdParty = crearTercero();

		WSThirdPartyPhisical thirdPartyCreated = getThirdPartiesClient().wsAddThirdPartyPhisical(
				thirdParty, security);
		
		WSPostalAddressData postalAddressData = new WSPostalAddressData();
		postalAddressData.setAddress("Dirección JUnit");
		postalAddressData.setCountry("España");
		postalAddressData.setPreferred(Boolean.toString(false));
		postalAddressData.setProvince("Asturias");
		postalAddressData.setZipCode("33950");
		postalAddressData.setCity("Blimea");

		ArrayOfWSPostalAddressData addressesToAdd = new ArrayOfWSPostalAddressData();
		addressesToAdd.getArrayOfWSCity().add(postalAddressData);
		WSPostalAddressesResponse postalAddresses = getThirdPartiesClient().wsAddPostalAddresses(
				(int) thirdPartyCreated.getId(), addressesToAdd, security);

		long postalAddressId = postalAddresses.getPostalAddressesResult().getArrayOfWSCity().get(0).getId();
		WSStakeholderData stakeHolder = new WSStakeholderData();

		stakeHolder.setBookId(bookId);
		stakeHolder.setFolderId(folderId);
		stakeHolder.setIdAddress(postalAddressId);
		stakeHolder.setIdThirdParty(thirdPartyCreated.getId());
		stakeHolder.setPreferred(true);

		WSStakeholder stakeHolderDevuelto = getThirdPartiesClient().wsAddStakeholder(stakeHolder,
				security);

		Assert.assertNotNull(stakeHolderDevuelto);
		Assert.assertEquals(stakeHolder.getIdThirdParty(), thirdPartyCreated.getId());
	}

	/**
	 * @return
	 */
	private WSThirdPartyPhisicalData crearTercero() {
		WSThirdPartyPhisicalData thirdParty = new WSThirdPartyPhisicalData();

		thirdParty.setDocumentNumber("0000000T");
		thirdParty.setDocumentType(2); // Tipo NIF
		thirdParty.setName("Nombre");
		thirdParty.setSurname1("Apellido1");
		thirdParty.setSurname2("Apellido2");
		return thirdParty;
	}

	public void testwsAddThirdPartyPhisical() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		WSThirdPartyPhisicalData thirdParty = crearTercero();

		WSThirdPartyPhisical thirdPartyCreated = getThirdPartiesClient().wsAddThirdPartyPhisical(
				thirdParty, security);

		Assert.assertNotNull(thirdPartyCreated);
		Assert.assertEquals(thirdParty.getDocumentNumber(), thirdPartyCreated.getDocumentNumber());
		Assert.assertEquals(thirdParty.getDocumentType(), thirdPartyCreated.getDocumentType());
		Assert.assertEquals(thirdParty.getName(), thirdPartyCreated.getName());
		Assert.assertEquals(thirdParty.getSurname1(), thirdPartyCreated.getSurname1());
		Assert.assertEquals(thirdParty.getSurname2(), thirdPartyCreated.getSurname2());

	}

	public void testwsLoadPostalAddressesByThirdParty() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		// 1.- Crear el tercero
		WSThirdPartyPhisicalData thirdParty = crearTercero();

		WSThirdPartyPhisical thirdPartyCreated = getThirdPartiesClient().wsAddThirdPartyPhisical(
				thirdParty, security);

		// 2.- Asociarle la dirección física a ete tercero
		ArrayOfWSPostalAddressData addressesToAdd = new ArrayOfWSPostalAddressData();

		String direccion = "Dirección";
		String country = "España";
		String province = "Asturias";
		String zipCode = "33950";
		String city = "Blimea";

		WSPostalAddressData wsPostalAddressData1 = createAddress(direccion, country, province,
				zipCode, city);

		wsPostalAddressData1.setPreferred(Boolean.TRUE.toString());
		addressesToAdd.getArrayOfWSCity().add(wsPostalAddressData1);
		WSPostalAddressesResponse postalAddresses = getThirdPartiesClient().wsAddPostalAddresses(
				(int) thirdPartyCreated.getId(), addressesToAdd, security);

		WSLoadPostalAddressesByThirdParty parameters = new WSLoadPostalAddressesByThirdParty();
		int idThirdParty = (int) thirdPartyCreated.getId();
		parameters.setIdThirdParty(idThirdParty);

		// 3.- Cargar las direcciones del tercero
		WSLoadPostalAddressesByThirdPartyResponse response = getThirdPartiesClient()
				.wsLoadPostalAddressesByThirdParty(parameters, security);
		List<WSPostalAddress> lista = response.getList().getPostalAddressesResult()
				.getArrayOfWSCity();

		Assert.assertEquals(lista.size(), 1);
		WSPostalAddress wsPostalAddress = lista.get(0);
		Assert.assertEquals(wsPostalAddress.getAddress(), direccion);

		// TODO: En esta versión no almacena el país en la tabla

		// Assert.assertEquals(wsPostalAddress.getCountry(), country);

		Assert.assertEquals(wsPostalAddress.getPreferred(), Boolean.TRUE.toString());
		Assert.assertEquals(wsPostalAddress.getProvince(), province);
		Assert.assertEquals(wsPostalAddress.getCity(), city);
	}

	/**
	 * @param direccion
	 * @param country
	 * @param province
	 * @param zipCode
	 * @return
	 */
	private WSPostalAddressData createAddress(String direccion, String country, String province,
			String zipCode, String city) {
		WSPostalAddressData wsPostalAddressData1 = new WSPostalAddressData();
		wsPostalAddressData1.setAddress(direccion);
		wsPostalAddressData1.setCountry(country);
		wsPostalAddressData1.setPreferred("false");
		wsPostalAddressData1.setProvince(province);
		wsPostalAddressData1.setZipCode(zipCode);
		wsPostalAddressData1.setCity(city);
		return wsPostalAddressData1;
	}

	public void testwsLoadStakeholders() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		ArrayOfWSStakeholder array = getThirdPartiesClient().wsLoadStakeholders(bookId, folderId,
				security);

		List<WSStakeholder> lista = array.getArrayOfWSStakeholder();

		Assert.assertTrue(lista.size() > 0);

	}

	public void testwsRemoveStakeholder() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		// 1.- Crear el tercero
		WSThirdPartyPhisicalData thirdParty = crearTercero();

		WSThirdPartyPhisical thirdPartyCreated = getThirdPartiesClient().wsAddThirdPartyPhisical(
				thirdParty, security);

		// 2.- Asociarle la dirección física a ete tercero
		ArrayOfWSPostalAddressData addressesToAdd = new ArrayOfWSPostalAddressData();

		String direccion = "Dirección";
		String country = "España";
		String province = "Asturias";
		String zipCode = "33950";
		String city = "Blimea";

		WSPostalAddressData wsPostalAddressData1 = createAddress(direccion, country, province,
				zipCode, city);

		addressesToAdd.getArrayOfWSCity().add(wsPostalAddressData1);
		WSPostalAddressesResponse postalAddresses = getThirdPartiesClient().wsAddPostalAddresses(
				(int) thirdPartyCreated.getId(), addressesToAdd, security);

		List<WSPostalAddress> postalAddressList = postalAddresses.getPostalAddressesResult()
				.getArrayOfWSCity();
		Assert.assertEquals(postalAddressList.size(), 1);
		WSPostalAddress postalAddress = postalAddressList.get(0);

		WSStakeholderData stakeHolder = new WSStakeholderData();

		stakeHolder.setBookId(bookId);
		stakeHolder.setFolderId(folderId);
		stakeHolder.setIdAddress(postalAddress.getId());
		stakeHolder.setIdThirdParty(thirdPartyCreated.getId());
		stakeHolder.setPreferred(true);

		WSStakeholder stakeHolderDevuelto = getThirdPartiesClient().wsAddStakeholder(stakeHolder,
				security);

		ArrayOfWSStakeholder arrayInicial = getThirdPartiesClient().wsLoadStakeholders(bookId,
				folderId, security);
		int stakeHoldersIniciales = arrayInicial.getArrayOfWSStakeholder().size();

		getThirdPartiesClient().wsRemoveStakeholder(stakeHolderDevuelto.getId(), security);

		ArrayOfWSStakeholder arrayFinal = getThirdPartiesClient().wsLoadStakeholders(bookId,
				folderId, security);
		int stakeHoldersFinales = arrayFinal.getArrayOfWSStakeholder().size();

		// TODO: No hay un método para obtener únicamente un stakeholder en
		// concreto a partir del identificador
		Assert.assertEquals(stakeHoldersFinales, stakeHoldersIniciales - 1);

		WSStakeholder stakeholderEliminado = getStakeholder(arrayFinal, stakeHolderDevuelto.getId());
		Assert.assertNull(stakeholderEliminado);

	}

	public void testwsThirdPartyPhisicalByDocumentNumber() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();
		long tipoDocumento = 2;
		String documentNumber = "0000000T";
		ArrayOfWSThirdPartyPhisical array = getThirdPartiesClient()
				.wsThirdPartyPhisicalByDocumentNumber(tipoDocumento, documentNumber, security);

		List<WSThirdPartyPhisical> thirdParties = array.getArrayOfWSThirdPartyPhisical();

		Assert.assertTrue(thirdParties.size() > 0);
		WSThirdPartyPhisical thirdParty1 = thirdParties.get(0);

		Assert.assertEquals(thirdParty1.getDocumentNumber(), documentNumber);

	}

	public void testwsUpdateNotifyAddressStakeholder() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		String direccion = "Dirección JUnit";
		String country = "España";
		String province = "Asturias";
		String zipCode = "33950";
		String city = "Blimea";

		WSPostalAddressData wsPostalAddressData1 = createAddress(direccion, country, province,
				zipCode, city);

		ArrayOfWSPostalAddressData addressesToAdd = new ArrayOfWSPostalAddressData();
		addressesToAdd.getArrayOfWSCity().add(wsPostalAddressData1);
		

		WSThirdPartyPhisicalData thirdParty = crearTercero();

		WSThirdPartyPhisical thirdPartyCreated = getThirdPartiesClient().wsAddThirdPartyPhisical(
				thirdParty, security);
		
		WSPostalAddressesResponse postalAddresses = getThirdPartiesClient().wsAddPostalAddresses(Long.valueOf(thirdPartyCreated.getId()).intValue(),
				addressesToAdd, security);
		List<WSPostalAddress> postalAddressList = postalAddresses.getPostalAddressesResult()
				.getArrayOfWSCity();
		WSPostalAddress postalAddres1 = postalAddressList.get(0);


		WSStakeholderData stakeHolder = new WSStakeholderData();

		stakeHolder.setBookId(bookId);
		stakeHolder.setFolderId(bookId);
		stakeHolder.setIdAddress(postalAddres1.getId());
		stakeHolder.setIdThirdParty(thirdPartyCreated.getId());
		stakeHolder.setPreferred(true);

		WSStakeholder stakeHolderDevuelto = getThirdPartiesClient().wsAddStakeholder(stakeHolder,
				security);
		getThirdPartiesClient().wsUpdateNotifyAddressStakeholder(stakeHolderDevuelto.getId(),
				postalAddres1.getId(), security);

		ArrayOfWSStakeholder arrayFinal = getThirdPartiesClient().wsLoadStakeholders(bookId,
				folderId, security);

		WSStakeholder stakeholderModificado = getStakeholder(arrayFinal,
				stakeHolderDevuelto.getId());

		Assert.assertEquals(stakeholderModificado.getIdAddress().longValue(), postalAddres1.getId());

	}

	/**
	 * 
	 * Devuelve un Stakeholder No hay método en el WS para obtener un
	 * stakeholder en concreto.
	 * 
	 * @param arrayOfWSStakeholder
	 * @param stakeHolderId
	 * @return
	 */
	private WSStakeholder getStakeholder(ArrayOfWSStakeholder arrayOfWSStakeholder,
			long stakeHolderId) {

		List<WSStakeholder> listaStakeHolders = arrayOfWSStakeholder.getArrayOfWSStakeholder();
		for (WSStakeholder stakeholder : listaStakeHolders) {
			if (stakeholder.getId() == stakeHolderId) {
				return stakeholder;
			}
		}
		return null;

	}

	public void testwsAddPostalAddresses() {
		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		// 1.- Crear el tercero
		WSThirdPartyPhisicalData thirdParty = crearTercero();

		WSThirdPartyPhisical thirdPartyCreated = getThirdPartiesClient().wsAddThirdPartyPhisical(
				thirdParty, security);

		ArrayOfWSPostalAddressData addressesToAdd = new ArrayOfWSPostalAddressData();

		WSPostalAddressData postalAddressData = new WSPostalAddressData();
		postalAddressData.setAddress("Dirección JUnit");
		postalAddressData.setCountry("España");
		postalAddressData.setPreferred(Boolean.toString(false));
		postalAddressData.setProvince("Asturias");
		postalAddressData.setZipCode("33950");
		postalAddressData.setCity("Blimea");

		addressesToAdd.getArrayOfWSCity().add(postalAddressData);
		WSPostalAddressesResponse postalAddresses = getThirdPartiesClient().wsAddPostalAddresses(
				(int) thirdPartyCreated.getId(), addressesToAdd, security);
		List<WSPostalAddress> postalAddressList = postalAddresses.getPostalAddressesResult()
				.getArrayOfWSCity();
		Assert.assertEquals(postalAddressList.size(), 1);
		WSPostalAddress postalAddress = postalAddressList.get(0);
		Assert.assertEquals(postalAddress.getAddress(), postalAddressData.getAddress());
	}

	public void testwsUpdatePostalAddresses() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		ArrayOfWSPostalAddress addressesToMod = new ArrayOfWSPostalAddress();

		WSPostalAddress wsPostalAddress = new WSPostalAddress();
		wsPostalAddress.setAddress("Dirección");
		wsPostalAddress.setCountry("España");
		wsPostalAddress.setPreferred("true");
		wsPostalAddress.setProvince("Asturias");
		wsPostalAddress.setZipCode("33950");
		wsPostalAddress.setCity("Blimea");

		addressesToMod.getArrayOfWSCity().add(wsPostalAddress);
		getThirdPartiesClient().wsUpdatePostalAddresses(addressesToMod, security);
	}

	public void testwsUpdateThirdPartyPhisical() {

		es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security security = getSecurity();

		WSThirdPartyPhisical thirdPartyPhisicalToAdd = new WSThirdPartyPhisical();
		long tipoDocumento = 2;
		String documentNumber = "0000000T";
		ArrayOfWSThirdPartyPhisical array = getThirdPartiesClient()
				.wsThirdPartyPhisicalByDocumentNumber(tipoDocumento, documentNumber, security);

		List<WSThirdPartyPhisical> thirdParties = array.getArrayOfWSThirdPartyPhisical();

		thirdPartyPhisicalToAdd = thirdParties.get(0);
		String nombre = "Modificado por JUnit";
		thirdPartyPhisicalToAdd.setName(nombre);

		WSThirdPartyPhisical thirdPartyModified = getThirdPartiesClient()
				.wsUpdateThirdPartyPhisical(thirdPartyPhisicalToAdd, security);

		Assert.assertEquals(thirdPartyModified.getName(), nombre);
	}

}
