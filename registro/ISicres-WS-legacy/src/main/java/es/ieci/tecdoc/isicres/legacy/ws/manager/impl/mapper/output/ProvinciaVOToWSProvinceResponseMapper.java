/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvince;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvinceResponse;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class ProvinciaVOToWSProvinceResponseMapper implements Mapper{

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(ProvinciaVO.class, obj);
		
		ProvinciaVO provinciaVO = (ProvinciaVO) obj;
		
		WSProvince wsProvince = new WSProvince();
		wsProvince.setCode(provinciaVO.getCodigo());
		wsProvince.setId(Long.valueOf(provinciaVO.getId()));
		wsProvince.setName(provinciaVO.getNombre());
		WSProvinceResponse wsProvinceResponse = new WSProvinceResponse();
		wsProvinceResponse.setProvinceResult(wsProvince);
		return wsProvinceResponse;
	}

}
