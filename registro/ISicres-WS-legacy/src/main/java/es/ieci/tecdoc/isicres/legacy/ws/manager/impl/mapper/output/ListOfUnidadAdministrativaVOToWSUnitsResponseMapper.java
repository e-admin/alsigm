package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.ArrayOfWSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitsResponse;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>UnidadAdministrativaVO</code> en un objeto de tipo
 * <code>WsUnitsResponse</code>.
 * 
 * @see UnidadAdministrativaVO
 * @see WSUnitsResponse
 * 
 * @author IECISA
 * 
 */
public class ListOfUnidadAdministrativaVOToWSUnitsResponseMapper implements
		Mapper {

	public ListOfUnidadAdministrativaVOToWSUnitsResponseMapper(int aTotal) {
		setTotal(aTotal);
	}

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<UnidadAdministrativaVO> unidades = (List<UnidadAdministrativaVO>) obj;

		WSUnitsResponse result = new WSUnitsResponse();
		result.setList(new ArrayOfWSUnit());

		for (UnidadAdministrativaVO unidad : unidades) {
			WSUnit unit = (WSUnit) new UnidadAdministrativaVOToWsUnitMapper()
					.map(unidad);

			result.getList().getWSUnit().add(unit);
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
