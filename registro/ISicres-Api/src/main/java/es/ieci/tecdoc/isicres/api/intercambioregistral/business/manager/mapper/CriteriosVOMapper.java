package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.mapper;

import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriterioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIREntradaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;

public interface CriteriosVOMapper {



	public CriteriosVO map(
			CriteriosBusquedaIREntradaVO criteriosBusquedaIntercambioRegistralVO);

	public CriterioVO map(
			CriterioBusquedaIREntradaVO criterioBusquedaIntercambioRegistralVO);

	public CriterioEnum map(CriterioBusquedaIREntradaEnum criterioBusquedaIREnum);
}
