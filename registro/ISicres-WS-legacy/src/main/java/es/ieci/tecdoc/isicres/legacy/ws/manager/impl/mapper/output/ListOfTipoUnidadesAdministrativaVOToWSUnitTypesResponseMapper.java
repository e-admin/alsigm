package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.ArrayOfWSUnitType;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitType;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitTypesResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>TipoUnidadAdministrativaVO</code> en un objeto de tipo
 * <code>WsUnitType</code>.
 * 
 * @see TipoUnidadAdministrativaVO
 * @see WSUnitType
 * 
 * @author IECISA
 * 
 */
public class ListOfTipoUnidadesAdministrativaVOToWSUnitTypesResponseMapper
		implements Mapper {

	public ListOfTipoUnidadesAdministrativaVOToWSUnitTypesResponseMapper(
			int aTotal) {
		setTotal(aTotal);
	}

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<TipoUnidadAdministrativaVO> tipos = (List<TipoUnidadAdministrativaVO>) obj;

		WSUnitTypesResponse result = new WSUnitTypesResponse();
		result.setList(new ArrayOfWSUnitType());

		for (TipoUnidadAdministrativaVO tipo : tipos) {
			result
					.getList()
					.getWSUnitType()
					.add(
							(WSUnitType) new TipoUnidadAdministrativaVOToWsUnitTypeMapper()
									.map(tipo));
		}
		result.setTotal(getTotal());

		return result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	// Members
	protected int total;
}
