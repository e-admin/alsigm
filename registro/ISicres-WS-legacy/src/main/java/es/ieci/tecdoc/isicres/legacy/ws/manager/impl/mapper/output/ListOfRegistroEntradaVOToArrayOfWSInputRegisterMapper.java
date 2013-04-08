package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegister;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>RegistroEntradaVO</code> en una instancia de
 * <code>ArrayOfWSInputRegister</code>.
 * 
 * @see RegistroEntradaVO
 * @see ArrayOfWSInputRegister
 * 
 * @author IECISA
 * 
 */
public class ListOfRegistroEntradaVOToArrayOfWSInputRegisterMapper implements
		Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<BaseRegistroVO> registros = (List<BaseRegistroVO>) obj;

		ArrayOfWSInputRegister result = new ArrayOfWSInputRegister();

		for (BaseRegistroVO reg : registros) {
			result
					.getWSInputRegister()
					.add(
							(WSInputRegister) new RegistroEntradaVOToWSInputRegisterMapper()
									.map(reg));
		}

		return result;
	}

}
