package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.Iterator;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegistersResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>RegistroSalidaVO</code> en una instancia de
 * <code>WSOutputRegistersResponse</code>.
 * 
 * @see RegistroSalidaVO
 * @see WSOutputRegistersResponse
 * 
 * @author IECISA
 * 
 */
public class ResultadoBusquedaRegistroVOToWSOutputRegistersResponseMapper
		implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(ResultadoBusquedaRegistroVO.class, obj);

		ResultadoBusquedaRegistroVO resultadoBusquedaRegistroVO = (ResultadoBusquedaRegistroVO) obj;

		WSOutputRegistersResponse result = new WSOutputRegistersResponse();
		result.setList(new ArrayOfWSOutputRegister());

		Iterator iterator = resultadoBusquedaRegistroVO.getRegisters()
				.iterator();
		while (iterator.hasNext()) {
			RegistroSalidaVO rs = (RegistroSalidaVO) iterator.next();
			result
					.getList()
					.getWSOutputRegister()
					.add(
							(WSOutputRegister) new RegistroSalidaVOToWSOutputRegisterMapper()
									.map(rs));
		}
		result.setTotal(resultadoBusquedaRegistroVO.getTotal());

		return result;
	}

}
