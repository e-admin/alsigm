package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIREntradaEnum;


/**
 * Información de los criterios de búsqueda.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriteriosBusquedaIREntradaVO extends BaseValueObject {

	private static final long serialVersionUID = -7831555035143536214L;

	/**
	 * Lista de criterios.
	 */
	private List<CriterioBusquedaIREntradaVO> criterios = new ArrayList<CriterioBusquedaIREntradaVO>();

	/**
	 * Criterios por los que se van a ordenar los resultados
	 */
	private List<CriterioBusquedaIREntradaEnum> orderBy = new ArrayList<CriterioBusquedaIREntradaEnum>();




	/**
	 * Constructor.
	 */
	public CriteriosBusquedaIREntradaVO() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param criterios
	 *            Lista de criterios de búsqueda.
	 */
	public CriteriosBusquedaIREntradaVO(List<CriterioBusquedaIREntradaVO> criterios) {
		super();
		setCriterios(criterios);
	}

	public List<CriterioBusquedaIREntradaVO> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioBusquedaIREntradaVO> criterios) {
		this.criterios.clear();
		if (criterios != null) {
			this.criterios.addAll(criterios);
		}
	}

	public CriteriosBusquedaIREntradaVO addCriterioVO(CriterioBusquedaIREntradaVO criterio) {
		if (criterio != null) {
			criterios.add(criterio);
		}
		return this;
	}

	public List<CriterioBusquedaIREntradaEnum> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<CriterioBusquedaIREntradaEnum> orderBy) {
		this.orderBy.clear();
		if (orderBy != null) {
			this.orderBy.addAll(orderBy);
		}
	}

	public CriteriosBusquedaIREntradaVO addOrderBy(CriterioBusquedaIREntradaEnum criterio) {
		if (criterio != null) {
			orderBy.add(criterio);
		}
		return this;
	}

}
