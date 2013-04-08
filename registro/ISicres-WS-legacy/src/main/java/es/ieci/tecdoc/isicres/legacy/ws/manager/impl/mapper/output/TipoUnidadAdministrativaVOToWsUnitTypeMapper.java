package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitType;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>TipoUnidadAdministrativaVO</code> en uno de tipo
 * <code>WsUnitType</code>.
 * 
 * @author IECISA
 * 
 */
public class TipoUnidadAdministrativaVOToWsUnitTypeMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(TipoUnidadAdministrativaVO.class, obj);

		TipoUnidadAdministrativaVO tipo = (TipoUnidadAdministrativaVO) obj;

		WSUnitType result = new WSUnitType();
		result.setCode(tipo.getCodigo());
		result.setId(Long.valueOf(tipo.getId()));
		result.setName(tipo.getDescripcion());

		return result;
	}

}
