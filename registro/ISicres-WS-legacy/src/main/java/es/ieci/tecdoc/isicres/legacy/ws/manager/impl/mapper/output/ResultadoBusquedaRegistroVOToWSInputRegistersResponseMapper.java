package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegistersResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code></code> en una instancia de <code>WSInputRegistersResponse</code>.
 * 
 * @see WSInputRegistersResponse
 * 
 * @author IECISA
 * 
 */
public class ResultadoBusquedaRegistroVOToWSInputRegistersResponseMapper
		implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(ResultadoBusquedaRegistroVO.class, obj);

		ResultadoBusquedaRegistroVO resultadoBusqueda = (ResultadoBusquedaRegistroVO) obj;

		WSInputRegistersResponse result = new WSInputRegistersResponse();

		result
				.setList((ArrayOfWSInputRegister) new ListOfRegistroEntradaVOToArrayOfWSInputRegisterMapper()
						.map(resultadoBusqueda.getRegisters()));
		result.setTotal(resultadoBusqueda.getTotal());

		return result;
	}
}
