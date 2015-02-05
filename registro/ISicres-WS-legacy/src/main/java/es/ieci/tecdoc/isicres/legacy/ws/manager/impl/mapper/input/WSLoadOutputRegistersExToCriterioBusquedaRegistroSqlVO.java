package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSLoadOutputRegistersEx;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>WSLoadOutputRegistersEx</code> en uno de tipo
 * <code>CriterioBusquedaRegistroSqlVO</code>.
 *
 * @author IECISA
 *
 */
public class WSLoadOutputRegistersExToCriterioBusquedaRegistroSqlVO implements
		Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(WSLoadOutputRegistersEx.class, obj);

		WSLoadOutputRegistersEx params = (WSLoadOutputRegistersEx) obj;

		CriterioBusquedaRegistroSqlVO result = new CriterioBusquedaRegistroSqlVO();
		result.setLimit(Long.valueOf(params.getSize()));
		result.setOffset(Long.valueOf(params.getInitValue()));
		result.setSql(params.getCondition());

		return result;
	}

}
