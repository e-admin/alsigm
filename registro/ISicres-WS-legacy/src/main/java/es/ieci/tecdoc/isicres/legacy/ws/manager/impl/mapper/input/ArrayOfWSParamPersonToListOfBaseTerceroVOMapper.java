package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroNoValidadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamPerson;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>ArrayOfWSParamPerson</code> en una <code>List</code> de
 * <code>BaseTerceroVO</code>.
 * 
 * @see ArrayOfWSParamPerson
 * @see BaseTerceroVO
 * 
 * @author IECISA
 * 
 */
public class ArrayOfWSParamPersonToListOfBaseTerceroVOMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamPerson.class, obj);

		ArrayOfWSParamPerson people = (ArrayOfWSParamPerson) obj;

		List<TerceroNoValidadoVO> result = new ArrayList<TerceroNoValidadoVO>();

		for (WSParamPerson person : people.getWSParamPerson()) {
			TerceroNoValidadoVO t = new TerceroNoValidadoVO();
			t.setNombre(person.getPersonName());

			result.add(t);
		}

		return result;
	}
}
