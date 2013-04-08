package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitType;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>UnidadAdministrativaVO</code> en uno de tipo <code>WsUnit</code>.
 * 
 * @see UnidadAdministrativaVO
 * @see WSUnit
 * 
 * @author IECISA
 * 
 */
public class UnidadAdministrativaVOToWsUnitMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(UnidadAdministrativaVO.class, obj);

		UnidadAdministrativaVO unidad = (UnidadAdministrativaVO) obj;

		WSUnit result = new WSUnit();
		result.setCode(unidad.getCodigoUnidad());
		result.setEnabled(unidad.isActiva());

		// TODO falta confirmar si los que se almacena en el padre es el codigo
		// o el id
		result.setFather(unidad.getUnidadPadre().getCodigoUnidad());
		result.setId(Long.valueOf(unidad.getId()));
		result.setName(unidad.getName());

		WSUnitType type = (WSUnitType) new TipoUnidadAdministrativaVOToWsUnitTypeMapper()
				.map(unidad.getTipo());
		result.setType(type);

		return result;
	}

}
