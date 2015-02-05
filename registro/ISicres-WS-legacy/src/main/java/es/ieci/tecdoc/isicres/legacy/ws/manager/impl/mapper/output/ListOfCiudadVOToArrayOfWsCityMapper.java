/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSCity;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCity;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class ListOfCiudadVOToArrayOfWsCityMapper implements Mapper {

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object map(Object obj) {

		Assert.isInstanceOf(List.class, obj);
		
		List<CiudadVO>listCiudadVO = (List<CiudadVO>) obj;
		
		CiudadVOToWSCityMapper ciudadVOToWSCityMapper = new CiudadVOToWSCityMapper();
		ArrayOfWSCity arrayOfWSCity = new ArrayOfWSCity();
		
		for (CiudadVO ciudadVO: listCiudadVO) {
			WSCity wsCity = (WSCity) ciudadVOToWSCityMapper.map(ciudadVO);
			
			arrayOfWSCity.getArrayOfWSCity().add(wsCity);
			
		}
		
		
		
		return arrayOfWSCity;
	}

}
