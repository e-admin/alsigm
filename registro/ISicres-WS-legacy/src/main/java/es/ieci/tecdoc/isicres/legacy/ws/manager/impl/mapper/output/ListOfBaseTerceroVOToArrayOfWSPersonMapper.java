package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSPerson;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSPerson;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>BaseTerceroVO</code> en una instancia de
 * <code>WSPersonCollection</code>.
 * 
 * @see BaseTerceroVO
 * @see WSPersonCollection
 * 
 * @author IECISA
 * 
 */
public class ListOfBaseTerceroVOToArrayOfWSPersonMapper implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<BaseTerceroVO> terceros = (List<BaseTerceroVO>) obj;

		ArrayOfWSPerson result = new ArrayOfWSPerson();

		for (BaseTerceroVO tercero : terceros) {
			WSPerson p = new WSPerson();
			p.setId(Integer.valueOf(tercero.getId()));
			p.setName(tercero.getNombre());

			result.getWSPerson().add(p);
		}

		return result;
	}
}
