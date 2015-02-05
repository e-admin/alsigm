package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input.WSPostalAddressDataToDireccionFisicaVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.CiudadVOToWSCityMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.InteresadoVOToWSStakeholderMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfCiudadVOToArrayOfWsCityMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfInteresadoVOToArrayOfWSStakeholderMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ListOfProvinciaVOToWSLoadProvincesResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.ProvinciaVOToWSProvinceResponseMapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.TerceroValidadoFisicoVOTOWSThirdPartyPhisicalMapper;
import es.ieci.tecdoc.isicres.legacy.ws.service.impl.adapter.UsuarioVOBuilder;
import es.ieci.tecdoc.isicres.terceros.business.manager.DireccionManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.InteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.MasterValuesManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.RepresentanteInteresadoManager;
import es.ieci.tecdoc.isicres.terceros.business.manager.TerceroManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSCity;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddress;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddressData;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSStakeholder;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSThirdPartyPhisical;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security;
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

public class ISWebServiceThirdPartiesManagerImpl implements ISWebServiceThirdPartiesManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ISWebServiceThirdPartiesManagerImpl.class);

	protected LoginManager loginManager;

	protected UsuarioVOBuilder usuarioBuilder;

	protected MasterValuesManager masterValuesManager;

	protected DireccionManager direccionManager;

	protected TerceroManager terceroManager;

	protected InteresadoManager interesadoManager;

	protected RepresentanteInteresadoManager representanteInteresadoManager;

	private int FIRST_COLLECTIONS_INIT_VALUE= 1;

	public ISWebServiceThirdPartiesManagerImpl(){
		FIRST_COLLECTIONS_INIT_VALUE = ISicresConfigurationHelper
				.getFirstCollectionsInitValue();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadCityByCode(java.lang.String,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSCityResponse loadCityByCode(String code, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		// Llamar al manager masterValuesManager
		CiudadVO ciudadVO = masterValuesManager.getCiudad(code);

		WSCityResponse result = null;
		
		if (ciudadVO != null) {
		CiudadVOToWSCityMapper mapper = new CiudadVOToWSCityMapper();
		WSCity city = (WSCity) mapper.map(ciudadVO);
			result = new WSCityResponse();
		result.setCityResult(city);
		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadCityById(long,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSCityResponse loadCityById(long id, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("loadCityById(long, Security) - start");
		}

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CiudadVO ciudadVO = null;
		try {
			ciudadVO = masterValuesManager.getCiudadById(Long.valueOf(id));
		} catch (Exception e) {
			logger.error("No se ha encontrado la ciudad con el identificador ["+id+"]", e);			
		}

		WSCityResponse result = null;
		if (ciudadVO!=null){
			CiudadVOToWSCityMapper mapper = new CiudadVOToWSCityMapper();
			WSCity city = (WSCity) mapper.map(ciudadVO);
			result = new WSCityResponse();
			result.setCityResult(city);
		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		if (logger.isDebugEnabled()) {
			logger.debug("loadCityById(long, Security) - end");
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadCityByIdProvNameProv(long,
	 *      java.lang.String, int, int,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSCitiesResponse loadCityByIdProvNameProv(long idProv, String nameProv, int initValue,
			int size, Security security) {

		Assert.isTrue(initValue >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
		Assert.isTrue(size >= 0,
				"Indique un número positivo.");

		//Si esta configurado para que comience por 1 hay que restarle uno
		if (FIRST_COLLECTIONS_INIT_VALUE==1){
			initValue--;
		}

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		ProvinciaVO provinciaVO = new ProvinciaVO();
		provinciaVO.setId(String.valueOf(idProv));
		provinciaVO.setNombre(nameProv);

		List<CiudadVO> listCiudadVO = masterValuesManager.getCiudades(provinciaVO);

		int from = initValue;
		int to = initValue + size;
		int listSize = listCiudadVO.size();
		if (to > listSize) {
			to = listSize;
		}

		if (from > listSize) {
			from = listSize;
		}

		if (from < 0) {
			from = 0;
		}
		if (to < from) {
			to = from;
		}

		listCiudadVO = listCiudadVO.subList(from, to);

		WSCitiesResponse wsCitiesResponse = new WSCitiesResponse();
		ListOfCiudadVOToArrayOfWsCityMapper mapper = new ListOfCiudadVOToArrayOfWsCityMapper();

		ArrayOfWSCity arrayOfWSCity = (ArrayOfWSCity) mapper.map(listCiudadVO);

		wsCitiesResponse.setCityResult(arrayOfWSCity);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsCitiesResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadCities(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadCities,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSLoadCitiesResponse loadCities(WSLoadCities parameters, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<CiudadVO> listaCiudades = masterValuesManager.getCiudades();

		if (parameters != null) {

			Assert.isTrue(parameters.getInitValue() >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
			Assert.isTrue(parameters.getSize() >= 0,
					"Indique un número positivo.");


			//Si esta configurado para que comience por 1 hay que restarle uno
			if (FIRST_COLLECTIONS_INIT_VALUE==1){
				parameters.setInitValue(parameters.getInitValue()-1);
			}


			int initValue = parameters.getInitValue();
			int size = parameters.getSize();
			int from = initValue;
			int to = initValue + size;
			int listSize = listaCiudades.size();
			if (to > listSize) {
				to = listSize;
			}
			if (from > listSize) {
				from = listSize;
			}

			if (from < 0) {
				from = 0;
			}
			if (to < from) {
				to = from;
			}
			/*
			 * Parameters:
			 *
			 * fromIndex - low endpoint (inclusive) of the subList toIndex -
			 * high endpoint (exclusive) of the subList
			 *
			 * Returns: a view of the specified range within this list
			 *
			 * Throws: IndexOutOfBoundsException - for an illegal endpoint index
			 * value (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
			 */
			listaCiudades = listaCiudades.subList(from, to);
		}

		WSLoadCitiesResponse wsLoadCitiesResponse = new WSLoadCitiesResponse();
		WSCitiesResponse wsCitiesResponse = new WSCitiesResponse();
		ListOfCiudadVOToArrayOfWsCityMapper mapper = new ListOfCiudadVOToArrayOfWsCityMapper();

		ArrayOfWSCity arrayOfWSCity = (ArrayOfWSCity) mapper.map(listaCiudades);

		wsCitiesResponse.setCityResult(arrayOfWSCity);
		wsLoadCitiesResponse.setList(wsCitiesResponse);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsLoadCitiesResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadCityByName(java.lang.String,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSCityResponse loadCityByName(String name, Security security) {
		WSCityResponse result;

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		CiudadVO ciudad = masterValuesManager.getCiudadByNombre(name);
		WSCityResponse wsCityResponse = new WSCityResponse();
		if (ciudad != null) {
			CiudadVOToWSCityMapper mapper = new CiudadVOToWSCityMapper();
			WSCity city = (WSCity) mapper.map(ciudad);
			
			wsCityResponse.setCityResult(city);
			result = wsCityResponse;
		} else {
			result = null;
		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadPostalAddressesByThirdParty(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadPostalAddressesByThirdParty,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	@SuppressWarnings("unchecked")
	public WSLoadPostalAddressesByThirdPartyResponse loadPostalAddressesByThirdParty(
			WSLoadPostalAddressesByThirdParty parameters, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		int idThirdParty = parameters.getIdThirdParty();

		TerceroValidadoVO terceroValidadoVO = terceroManager.get(String.valueOf(idThirdParty));

		// Devuelve las direcciones fisicas porque son las que se corresponden con la clase WSPostalAddress (Dirección Postal)

		List<DireccionFisicaVO> listaDirecciones = (List<DireccionFisicaVO>) direccionManager
				.getDirecciones(terceroValidadoVO, DireccionType.FISICA);


		ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper mapper = new ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper();
		ArrayOfWSPostalAddress arrayOfWSPostalAddress = (ArrayOfWSPostalAddress) mapper
				.map(listaDirecciones);


		WSPostalAddressesResponse wsPostalAddressesResponse = new WSPostalAddressesResponse();
		wsPostalAddressesResponse.setPostalAddressesResult(arrayOfWSPostalAddress);
		WSLoadPostalAddressesByThirdPartyResponse wsLoadPostalAddressesByThirdPartyResponse = new WSLoadPostalAddressesByThirdPartyResponse();

		wsLoadPostalAddressesByThirdPartyResponse.setList(wsPostalAddressesResponse);

		wsLoadPostalAddressesByThirdPartyResponse.setTotal(listaDirecciones.size());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsLoadPostalAddressesByThirdPartyResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadProvinceByCode(java.lang.String,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSProvinceResponse loadProvinceByCode(String code, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		WSProvinceResponse wsProvinceResponse = null;
		ProvinciaVO provinciaVO = masterValuesManager.getProvincia(code);

		if (provinciaVO!=null){
		ProvinciaVOToWSProvinceResponseMapper mapper = new ProvinciaVOToWSProvinceResponseMapper();
			wsProvinceResponse = (WSProvinceResponse) mapper.map(provinciaVO);
		}

		//realizamos logout del sistema
		getLoginManager().logout(usuario);

		return wsProvinceResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadProvinceById(long,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSProvinceResponse loadProvinceById(long id, Security security) {
		if (logger.isDebugEnabled()) {
			logger.debug("loadProvinceById(long, Security) - start");
		}

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		ProvinciaVO provinciaVO = null;
		WSProvinceResponse wsProvinceResponse = null;
		try {
			provinciaVO = masterValuesManager.getProvinciaById(id);
		} catch (Exception e) {
			logger.error("No se ha encontrado la provincia con el id ["+id+"]", e);
		}

		if (provinciaVO!=null){
		ProvinciaVOToWSProvinceResponseMapper mapper = new ProvinciaVOToWSProvinceResponseMapper();
			wsProvinceResponse = (WSProvinceResponse) mapper.map(provinciaVO);
		}
		//realizamos logout del sistema
		getLoginManager().logout(usuario);

		if (logger.isDebugEnabled()) {
			logger.debug("loadProvinceById(long, Security) - end");
		}
		return wsProvinceResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadProvinceByName(java.lang.String,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSProvinceResponse loadProvinceByName(String name, Security security) {
		WSProvinceResponse result;

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		ProvinciaVO provinciaVO = masterValuesManager.getProvinciaByNombre(name);

		if (provinciaVO != null) {
			ProvinciaVOToWSProvinceResponseMapper mapper = new ProvinciaVOToWSProvinceResponseMapper();
			WSProvinceResponse wsProvinceResponse = (WSProvinceResponse) mapper.map(provinciaVO);
			result = wsProvinceResponse;
		} else {
			result = null;
		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadProvinces(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadProvinces,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSLoadProvincesResponse loadProvinces(WSLoadProvinces parameters, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<ProvinciaVO> provincias = masterValuesManager.getProvincias();

		if (parameters != null) {

			Assert.isTrue(parameters.getInitValue() >= FIRST_COLLECTIONS_INIT_VALUE, "Indique un valor inicial positivo.");
			Assert.isTrue(parameters.getSize() >= 0,
					"Indique un número positivo.");

			//Si esta configurado para que comience por 1 hay que restarle uno
			if (FIRST_COLLECTIONS_INIT_VALUE==1){
				parameters.setInitValue(parameters.getInitValue()-1);
			}

			int listSize = provincias.size();
			int from = parameters.getInitValue();
			int size = parameters.getSize();
			int to = from + size;
			if (to > listSize) {
				to = listSize;
			}
			if (from > listSize) {
				from = listSize;
			}
			if (from < 0) {
				from = 0;
			}
			if (to < from) {
				to = from;
			}

			provincias = provincias.subList(from, to);

		}

		ListOfProvinciaVOToWSLoadProvincesResponseMapper mapper = new ListOfProvinciaVOToWSLoadProvincesResponseMapper();

		WSLoadProvincesResponse wsLoadProvincesResponse = (WSLoadProvincesResponse) mapper
				.map(provincias);
		wsLoadProvincesResponse.setTotal(provincias.size());

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsLoadProvincesResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#addPostalAddresses(int,
	 *      java.util.List,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSPostalAddressesResponse addPostalAddresses(int idThirdParty,
			ArrayOfWSPostalAddressData addressesToAdd, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<WSPostalAddressData> postalAddressDataList = addressesToAdd.getArrayOfWSCity();

		Iterator<WSPostalAddressData> iterPostalAddressDataList = postalAddressDataList.iterator();

		TerceroValidadoVO terceroValidadoVO = terceroManager.get(String.valueOf(idThirdParty));

		WSPostalAddressDataToDireccionFisicaVOMapper postalAddressmapper = new WSPostalAddressDataToDireccionFisicaVOMapper();
		while (iterPostalAddressDataList.hasNext()) {
			WSPostalAddressData wsPostalAddressData = (WSPostalAddressData) iterPostalAddressDataList
					.next();

			DireccionFisicaVO direccionVO = (DireccionFisicaVO) postalAddressmapper.map(wsPostalAddressData);
			direccionVO.setTercero(terceroValidadoVO);

			// Añadir la dirección actual
			direccionManager.addDireccion(direccionVO, terceroValidadoVO);

			logger.info("Dirección creada: " + direccionVO);
		}


		List<DireccionFisicaVO> listaDirecciones = (List<DireccionFisicaVO>) direccionManager
				.getDirecciones(terceroValidadoVO, DireccionType.FISICA);

		ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper mapper = new ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper();
		ArrayOfWSPostalAddress arrayOfWSPostalAddress = (ArrayOfWSPostalAddress) mapper
				.map(listaDirecciones);

		WSPostalAddressesResponse wsPostalAddressesResponse = new WSPostalAddressesResponse();
		wsPostalAddressesResponse.setPostalAddressesResult(arrayOfWSPostalAddress);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsPostalAddressesResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#addStakeholder(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholderData,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSStakeholder addStakeholder(WSStakeholderData stakeHolderData, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		TerceroValidadoVO terceroAsociado = terceroManager.get(String.valueOf(stakeHolderData
				.getIdThirdParty()));
		InteresadoVO interesado = new InteresadoVO();
		interesado.setIdLibro(String.valueOf(stakeHolderData.getBookId()));
		interesado.setIdRegistro(String.valueOf(stakeHolderData.getFolderId()));
		if (terceroAsociado != null) {
			interesado.setNombre(terceroAsociado.getDescripcion());
			interesado.setTercero(terceroAsociado);
		}
		// TODO: ¿Orden?
		// interesado.setOrden(stakeHolderData.getBookId());
		// TODO: ¿Principal?
		interesado.setPrincipal(true);

		BaseDireccionVO direccionNotificacion = direccionManager.get(String.valueOf(stakeHolderData
				.getIdAddress()));
		stakeHolderData.getIdAddress();

		interesado.setDireccionNotificacion(direccionNotificacion);

		// Guardar
		interesado = interesadoManager.save(interesado);
		InteresadoVOToWSStakeholderMapper mapper = new InteresadoVOToWSStakeholderMapper();
		WSStakeholder wsStakeholder = (WSStakeholder) mapper.map(interesado);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsStakeholder;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#loadStakeholders(long,
	 *      long,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public ArrayOfWSStakeholder loadStakeholders(long bookId, long folderId, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<InteresadoVO> interesados = interesadoManager.getAll(String.valueOf(bookId),
				String.valueOf(folderId));

		ListOfInteresadoVOToArrayOfWSStakeholderMapper mapper = new ListOfInteresadoVOToArrayOfWSStakeholderMapper();

		ArrayOfWSStakeholder arrayOfWSStakeholder = (ArrayOfWSStakeholder) mapper.map(interesados);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return arrayOfWSStakeholder;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#removeStakeholder(long,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public long removeStakeholder(long idStakeholder, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		interesadoManager.delete(String.valueOf(idStakeholder));
		// TODO: ¿Qué valor devuelve este método?

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#wsAddThirdPartyPhisical(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisicalData,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSThirdPartyPhisical addThirdPartyPhisical(WSThirdPartyPhisicalData thirdParty,
			Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		TerceroValidadoFisicoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setApellido1(thirdParty.getSurname1());
		tercero.setApellido2(thirdParty.getSurname2());
		tercero.setNombre(thirdParty.getName());
		tercero.setNumeroDocumento(thirdParty.getDocumentNumber());
		TipoDocumentoIdentificativoTerceroVO tipoDoc = new TipoDocumentoIdentificativoTerceroVO();

		// TODO: ¿El atributo thirdParty.getDocumentType() devuelve el
		// identificador del tipo de documento o el código?
		tipoDoc.setId(String.valueOf(thirdParty.getDocumentType()));

		tercero.setTipoDocumento(tipoDoc);

		// Guardar el tercero
		TerceroValidadoVO terceroSalvado = terceroManager.save(tercero);
		WSThirdPartyPhisical thirdPartyPhisical = new WSThirdPartyPhisical();
		thirdPartyPhisical.setDocumentNumber(terceroSalvado.getNumeroDocumento());
		thirdPartyPhisical.setDocumentType(Integer.valueOf(terceroSalvado.getTipoDocumento()
				.getId()));
		thirdPartyPhisical.setId(Long.valueOf(terceroSalvado.getId()));
		thirdPartyPhisical.setName(terceroSalvado.getNombre());
		if (terceroSalvado instanceof TerceroValidadoFisicoVO) {
			TerceroValidadoFisicoVO terceroFisico = (TerceroValidadoFisicoVO) terceroSalvado;
			thirdPartyPhisical.setSurname1(terceroFisico.getApellido1());
			thirdPartyPhisical.setSurname2(terceroFisico.getApellido2());
		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return thirdPartyPhisical;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#wsThirdPartyPhisicalByDocumentNumber(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisicalByDocumentNumber,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public ArrayOfWSThirdPartyPhisical wsThirdPartyPhisicalByDocumentNumber(long tipoDocumento,
			String documentNumber, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<TerceroValidadoFisicoVO> listaTerceros = terceroManager
				.findTerceroFisicoByDocumentNumber(documentNumber, (int) tipoDocumento);
		TerceroValidadoFisicoVOTOWSThirdPartyPhisicalMapper mapper = new TerceroValidadoFisicoVOTOWSThirdPartyPhisicalMapper();
		ArrayOfWSThirdPartyPhisical arrayOfWSThirdPartyPhisical = new ArrayOfWSThirdPartyPhisical();
		for (TerceroValidadoVO tercero : listaTerceros) {
			if (tercero instanceof TerceroValidadoFisicoVO) {
				TerceroValidadoFisicoVO terceroFisico = (TerceroValidadoFisicoVO) tercero;
				WSThirdPartyPhisical thirdParty = (WSThirdPartyPhisical) mapper.map(terceroFisico);
				arrayOfWSThirdPartyPhisical.getArrayOfWSThirdPartyPhisical().add(thirdParty);
			}

		}

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return arrayOfWSThirdPartyPhisical;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#updateNotifyAddressStakeholder(long,
	 *      long,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public long updateNotifyAddressStakeholder(long idStakeholder, long idAddress, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		BaseDireccionVO direccion = direccionManager.get(String.valueOf(idAddress));
		InteresadoVO interesado = interesadoManager.get(String.valueOf(idStakeholder));

		interesado.setDireccionNotificacion(direccion);
		interesadoManager.update(interesado);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		// TODO: ¿Qué valor devuelve la función?
		return 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#wsUpdatePostalAddresses(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSPostalAddress,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSPostalAddressesResponse wsUpdatePostalAddresses(ArrayOfWSPostalAddress addressesToMod,
			Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		List<WSPostalAddress> listWSPostalAddress = addressesToMod.getArrayOfWSCity();

		List<DireccionFisicaVO> listaDirecciones = new ArrayList<DireccionFisicaVO>();

		for (WSPostalAddress postalAddress : listWSPostalAddress) {
			DireccionFisicaVO direccionVO = new DireccionFisicaVO();
			direccionVO.setCodigoPostal(postalAddress.getZipCode());
			direccionVO.setDireccion(postalAddress.getAddress());
			direccionVO.setId(String.valueOf(postalAddress.getId()));
			direccionVO.setPrincipal(Boolean.valueOf(postalAddress.getPreferred()));

			// Se añade una dirección Física
			direccionVO.setTipo(DireccionType.FISICA);
			// TODO: Falta la ciudad

			//direccionVO.setCiudad(postalAddress.g);

			// El atributo getCountry es el nombre
			PaisVO pais = new PaisVO();
			pais.setNombre(postalAddress.getCountry());
			direccionVO.setPais(pais);
			// TODO: ¿El atributo getProvince() es el nombre de la ciudad?
			ProvinciaVO provincia = new ProvinciaVO();
			provincia.setNombre(postalAddress.getProvince());
			direccionVO.setProvincia(provincia);
			direccionVO.setCodigoPostal(postalAddress.getZipCode());

			// Actualizamos la dirección
			DireccionFisicaVO direccionFisicaModificada = (DireccionFisicaVO) direccionManager
					.update(direccionVO);
			listaDirecciones.add(direccionFisicaModificada);

		}

		ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper mapper = new ListOfDireccionFisicaVOToArrayOfWSPostalAddressVOMapper();
		WSPostalAddressesResponse wsPostalAddressesResponse = new WSPostalAddressesResponse();

		ArrayOfWSPostalAddress arrayOfWSPostalAddressVO = (ArrayOfWSPostalAddress) mapper
				.map(listaDirecciones);
		wsPostalAddressesResponse.setPostalAddressesResult(arrayOfWSPostalAddressVO);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return wsPostalAddressesResponse;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.ISWebServiceThirdPartiesManager#updateThirdPartyPhisical(es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisical,
	 *      es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.Security)
	 */
	public WSThirdPartyPhisical updateThirdPartyPhisical(
			WSThirdPartyPhisical thirdPartyPhisicalToAdd, Security security) {

		// Hay que logar al usuario antes de llamar al manager
		UsuarioVO usuario = getLoginManager().login(
				getUsuarioBuilder().fromSecurityToUsuarioVO(security));

		String documentNumber = thirdPartyPhisicalToAdd.getDocumentNumber();

		TerceroValidadoFisicoVO tercero = (TerceroValidadoFisicoVO) terceroManager.get(String
				.valueOf(thirdPartyPhisicalToAdd.getId()));
		tercero.setApellido1(thirdPartyPhisicalToAdd.getSurname1());
		tercero.setApellido2(thirdPartyPhisicalToAdd.getSurname2());
		tercero.setNombre(thirdPartyPhisicalToAdd.getName());
		tercero.setNumeroDocumento(documentNumber);
		TipoDocumentoIdentificativoTerceroVO tipoDoc = new TipoDocumentoIdentificativoTerceroVO();
		tipoDoc.setId(String.valueOf(thirdPartyPhisicalToAdd.getDocumentType()));
		tercero.setTipoDocumento(tipoDoc);

		// Actualizamos el tercero
		terceroManager.update(tercero);

		TerceroValidadoFisicoVOTOWSThirdPartyPhisicalMapper mapper = new TerceroValidadoFisicoVOTOWSThirdPartyPhisicalMapper();

		WSThirdPartyPhisical thirdPartyUpdated = (WSThirdPartyPhisical) mapper.map(tercero);

		//cerramos la sesion del usuario
		getLoginManager().logout(usuario);

		return thirdPartyUpdated;
	}

	public UsuarioVOBuilder getUsuarioBuilder() {
		return usuarioBuilder;
	}

	public void setUsuarioBuilder(UsuarioVOBuilder usuarioBuilder) {
		this.usuarioBuilder = usuarioBuilder;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	/**
	 * @return el terceroManager
	 */
	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	/**
	 * @param terceroManager
	 *            el terceroManager a fijar
	 */
	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	/**
	 * @return el interesadoManager
	 */
	public InteresadoManager getInteresadoManager() {
		return interesadoManager;
	}

	/**
	 * @param interesadoManager
	 *            el interesadoManager a fijar
	 */
	public void setInteresadoManager(InteresadoManager interesadoManager) {
		this.interesadoManager = interesadoManager;
	}

	/**
	 * @return el representanteInteresadoManager
	 */
	public RepresentanteInteresadoManager getRepresentanteInteresadoManager() {
		return representanteInteresadoManager;
	}

	/**
	 * @param representanteInteresadoManager
	 *            el representanteInteresadoManager a fijar
	 */
	public void setRepresentanteInteresadoManager(
			RepresentanteInteresadoManager representanteInteresadoManager) {
		this.representanteInteresadoManager = representanteInteresadoManager;
	}

	/**
	 * @return el masterValuesManager
	 */
	public MasterValuesManager getMasterValuesManager() {
		return masterValuesManager;
	}

	/**
	 * @param masterValuesManager
	 *            el masterValuesManager a fijar
	 */
	public void setMasterValuesManager(MasterValuesManager masterValuesManager) {
		this.masterValuesManager = masterValuesManager;
	}

	/**
	 * @return el direccionManager
	 */
	public DireccionManager getDireccionManager() {
		return direccionManager;
	}

	/**
	 * @param direccionManager
	 *            el direccionManager a fijar
	 */
	public void setDireccionManager(DireccionManager direccionManager) {
		this.direccionManager = direccionManager;
	}

}
