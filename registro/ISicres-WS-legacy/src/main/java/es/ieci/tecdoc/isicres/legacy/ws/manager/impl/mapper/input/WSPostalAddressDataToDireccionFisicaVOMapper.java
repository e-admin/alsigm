/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSPostalAddressData;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class WSPostalAddressDataToDireccionFisicaVOMapper implements Mapper{

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(WSPostalAddressData.class, obj);
		WSPostalAddressData wsPostalAddressData = (WSPostalAddressData)obj;
		DireccionFisicaVO direccionVO = new DireccionFisicaVO();
		direccionVO.setDireccion(wsPostalAddressData.getAddress());
		// Supongo que el atributo "principal" significa si es la
		// dirección principal del tercero.
		String preferred = wsPostalAddressData.getPreferred();
		direccionVO.setPrincipal(Boolean.valueOf(preferred));
		direccionVO.setCodigoPostal(wsPostalAddressData.getZipCode());
		PaisVO pais = new PaisVO();
		//getCountry es el nombre del país
		pais.setNombre(wsPostalAddressData.getCountry());
		direccionVO.setPais(pais);
		ProvinciaVO provincia = new ProvinciaVO();
		
		//getProvince es el nombre de la provincia???
		provincia.setNombre(wsPostalAddressData.getProvince());
		direccionVO.setProvincia(provincia);
		CiudadVO ciudad = new CiudadVO();
		//getCity es el nombre de la ciudad
		ciudad.setNombre(wsPostalAddressData.getCity());
		direccionVO.setCiudad(ciudad);
		direccionVO.setTipo(DireccionType.FISICA);
		return direccionVO;
	}

}
