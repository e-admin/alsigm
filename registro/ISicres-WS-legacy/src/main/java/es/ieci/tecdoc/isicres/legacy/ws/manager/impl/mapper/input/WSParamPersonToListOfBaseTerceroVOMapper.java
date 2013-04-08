package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamPerson;

/**
 * 
 * @author IECISA
 * 
 */
public class WSParamPersonToListOfBaseTerceroVOMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamPerson.class, obj);

		ArrayOfWSParamPerson arrayOfWSParamPerson = (ArrayOfWSParamPerson) obj;

		List<BaseTerceroVO> people = new ArrayList<BaseTerceroVO>();
		for (WSParamPerson paramPerson : arrayOfWSParamPerson
				.getWSParamPerson()) {
			BaseTerceroVO tercero = new BaseTerceroVO();
			tercero.setNombre(paramPerson.getPersonName());

			people.add(tercero);

		}
		return people;
	}

}
