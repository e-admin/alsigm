package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.Iterator;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSInputRegistersResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersExResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>RegistroEntradaVO</code> en un objeto de tipo
 * <code>WSLoadInputRegistersExResponse</code>.
 * 
 * @see RegistroEntradaVO
 * @see WSLoadInputRegistersExResponse
 * 
 * @author IECISA
 * 
 */
public class ResultadoBusquedaRegistroVOToWSLoadInputRegistersExResponseMapper
		implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(ResultadoBusquedaRegistroVO.class, obj);

		ResultadoBusquedaRegistroVO resultadoBusquedaRegistroVO = (ResultadoBusquedaRegistroVO) obj;

		WSLoadInputRegistersExResponse result = new WSLoadInputRegistersExResponse();
		result.setWSLoadInputRegistersExResult(new WSInputRegistersResponse());
		result.getWSLoadInputRegistersExResult().setList(
				new ArrayOfWSInputRegister());

		Iterator iterator = resultadoBusquedaRegistroVO.getRegisters()
				.iterator();
		while (iterator.hasNext()){
			RegistroEntradaVO registro = (RegistroEntradaVO) iterator.next();

			result
					.getWSLoadInputRegistersExResult()
					.getList()
					.getWSInputRegister()
					.add(
							(WSInputRegister) new RegistroEntradaVOToWSInputRegisterMapper()
									.map(registro));
		}

		result.getWSLoadInputRegistersExResult().setTotal(
				resultadoBusquedaRegistroVO.getTotal());

		return result;
	}
}
