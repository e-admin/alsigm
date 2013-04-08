package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamPerson;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>ArrayOfWSParamPerson</code> en una <code>List</code> de
 * <code>InteresadoVO</code>.
 *
 * @see ArrayOfWSParamPerson
 * @see InteresadoVO
 *
 * @author IECISA
 *
 */
public class ArrayOfWSParamPersonToListOfInteresadoVOMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamPerson.class, obj);

		ArrayOfWSParamPerson people = (ArrayOfWSParamPerson) obj;

		List<InteresadoVO> result = new ArrayList<InteresadoVO>();

		for (WSParamPerson person : people.getWSParamPerson()) {
			InteresadoVO inter = new InteresadoVO();
			inter.setNombre(person.getPersonName());

			result.add(inter);
		}

		return result;
	}
}
