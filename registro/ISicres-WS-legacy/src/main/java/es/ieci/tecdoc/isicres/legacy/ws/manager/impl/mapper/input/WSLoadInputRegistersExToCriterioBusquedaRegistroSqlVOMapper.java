package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadInputRegistersEx;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>WSLoadInputRegistersEx</code> en uno de tipo
 * <code>CriterioBusquedaRegistroSqlVO</code>.
 * 
 * @see WSLoadInputRegistersEx
 * @see CriterioBusquedaRegistroSqlVO
 * 
 * @author IECISA
 * 
 */
public class WSLoadInputRegistersExToCriterioBusquedaRegistroSqlVOMapper
		implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(WSLoadInputRegistersEx.class, obj);

		WSLoadInputRegistersEx params = (WSLoadInputRegistersEx) obj;

		CriterioBusquedaRegistroSqlVO result = new CriterioBusquedaRegistroSqlVO();
		result.setLimit(Long.valueOf(params.getSize()));
		result.setOffset(Long.valueOf(params.getInitValue()));
		result.setSql(params.getCondition());

		return result;
	}

}
