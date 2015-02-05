package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamPerson;

/**
 *
 * @author IECISA
 *
 */
public class WSParamPersonToListOfInteresadoVOMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamPerson.class, obj);

		ArrayOfWSParamPerson arrayOfWSParamPerson = (ArrayOfWSParamPerson) obj;

		List<InteresadoVO> people = new ArrayList<InteresadoVO>();
		for (WSParamPerson paramPerson : arrayOfWSParamPerson
				.getWSParamPerson()) {
			InteresadoVO tercero = new InteresadoVO();
			tercero.setNombre(paramPerson.getPersonName());

			people.add(tercero);

		}
		return people;
	}

}
