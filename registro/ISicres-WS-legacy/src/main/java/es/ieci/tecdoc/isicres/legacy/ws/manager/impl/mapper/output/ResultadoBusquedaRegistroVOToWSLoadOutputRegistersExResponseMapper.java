package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.Iterator;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadOutputRegistersExResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegister;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSOutputRegistersResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>RegistroSalidaVO</code> en un objeto de tipo
 * <code>WSLoadOutputRegistersExResponse</code>.
 * 
 * @see RegistroSalidaVO
 * @see WSLoadOutputRegistersExResponse
 * 
 * @author IECISA
 * 
 */
public class ResultadoBusquedaRegistroVOToWSLoadOutputRegistersExResponseMapper
		implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(ResultadoBusquedaRegistroVO.class, obj);

		ResultadoBusquedaRegistroVO resultadoBusquedaRegistroVO = (ResultadoBusquedaRegistroVO) obj;

		WSLoadOutputRegistersExResponse result = new WSLoadOutputRegistersExResponse();
		result
				.setWSLoadOutputRegistersExResult(new WSOutputRegistersResponse());
		result.getWSLoadOutputRegistersExResult().setList(
				new ArrayOfWSOutputRegister());

		Iterator iterator = resultadoBusquedaRegistroVO.getRegisters()
				.iterator();
		while (iterator.hasNext()) {
			RegistroSalidaVO registro = (RegistroSalidaVO) iterator.next();
			result
					.getWSLoadOutputRegistersExResult()
					.getList()
					.getWSOutputRegister()
					.add(
							(WSOutputRegister) new RegistroSalidaVOToWSOutputRegisterMapper()
									.map(registro));
		}

		result.getWSLoadOutputRegistersExResult().setTotal(
				resultadoBusquedaRegistroVO.getTotal());

		return result;
	}

}
