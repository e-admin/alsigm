/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSProvince;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSLoadProvincesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvince;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvinceResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSProvincesResponse;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class ListOfProvinciaVOToWSLoadProvincesResponseMapper implements Mapper {

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<ProvinciaVO> provincias = (List<ProvinciaVO>) obj;
		
		WSLoadProvincesResponse wsLoadProvincesResponse = new WSLoadProvincesResponse();
		WSProvincesResponse wsProvincesResponse = new WSProvincesResponse();
		ArrayOfWSProvince arraysOfWSProvince = new ArrayOfWSProvince();
		
		
		ProvinciaVOToWSProvinceResponseMapper mapper = new ProvinciaVOToWSProvinceResponseMapper();
		for(ProvinciaVO provinciaVO:provincias){
			WSProvinceResponse wsProvinceResponse = (WSProvinceResponse) mapper.map(provinciaVO);
			WSProvince wsProvince = wsProvinceResponse.getProvinceResult();
			arraysOfWSProvince.getArrayOfWSProvince().add(wsProvince);
		}
		
		wsProvincesResponse.setProvincesResult(arraysOfWSProvince);
		wsLoadProvincesResponse.setList(wsProvincesResponse);
		
		return wsLoadProvincesResponse;
	}

}
