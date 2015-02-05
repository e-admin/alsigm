package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSPerson;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>BaseTerceroVO</code> en una instancia de
 * <code>WSPersonCollection</code>.
 *
 * @see InteresadoVO
 * @see WSPersonCollection
 *
 * @author IECISA
 *
 */
public class ListOfInteresadoVOToArrayOfWSPersonMapper implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<InteresadoVO> interesados = (List<InteresadoVO>) obj;

		ArrayOfWSPerson result = new ArrayOfWSPerson();

		for (InteresadoVO interesado : interesados) {
			WSPerson p = new WSPerson();
			p.setId(Integer.valueOf(interesado.getId()));
			p.setName(interesado.getNombre());

			result.getWSPerson().add(p);
		}

		return result;
	}
}
