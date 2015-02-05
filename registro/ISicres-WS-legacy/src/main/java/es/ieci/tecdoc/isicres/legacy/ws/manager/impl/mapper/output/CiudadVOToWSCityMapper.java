/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSCity;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class CiudadVOToWSCityMapper implements Mapper {

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(CiudadVO.class, obj);
		CiudadVO ciudadVO = (CiudadVO) obj;
		WSCity wsCity = new WSCity();
		
		wsCity.setCode(ciudadVO.getCodigo());
		wsCity.setId(Long.valueOf(ciudadVO.getId()));
		wsCity.setName(ciudadVO.getNombre());
		
		return wsCity;
	}

}
