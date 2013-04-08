package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;

public class ListOfCampoAdicionalRegistroVOToArrayOfWSAddFieldMapper implements
		Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<CampoAdicionalRegistroVO> campos = (List<CampoAdicionalRegistroVO>) obj;

		ArrayOfWSAddField result = new ArrayOfWSAddField();

		for (CampoAdicionalRegistroVO car : campos) {

			result
					.getWSAddField()
					.add(
							(WSAddField) new CampoAdicionalRegistroVOToWSAddFieldMapper()
									.map(car));
		}

		return result;
	}

}
