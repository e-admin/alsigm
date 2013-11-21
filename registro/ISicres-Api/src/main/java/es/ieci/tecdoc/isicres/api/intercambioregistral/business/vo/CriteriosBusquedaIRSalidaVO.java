package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIRSalidaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIRSalidaEnum;


/**
 * Información de los criterios de búsqueda.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriteriosBusquedaIRSalidaVO extends BaseValueObject {

	private static final long serialVersionUID = -7831555035143536214L;

	/**
	 * Lista de criterios.
	 */
	private List<CriterioBusquedaIRSalidaVO> criterios = new ArrayList<CriterioBusquedaIRSalidaVO>();

	/**
	 * Criterios por los que se van a ordenar los resultados
	 */
	private List<CriterioBusquedaIRSalidaEnum> orderBy = new ArrayList<CriterioBusquedaIRSalidaEnum>();




	/**
	 * Constructor.
	 */
	public CriteriosBusquedaIRSalidaVO() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param criterios
	 *            Lista de criterios de búsqueda.
	 */
	public CriteriosBusquedaIRSalidaVO(List<CriterioBusquedaIRSalidaVO> criterios) {
		super();
		setCriterios(criterios);
	}

	public List<CriterioBusquedaIRSalidaVO> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioBusquedaIRSalidaVO> criterios) {
		this.criterios.clear();
		if (criterios != null) {
			this.criterios.addAll(criterios);
		}
	}

	public CriteriosBusquedaIRSalidaVO addCriterioVO(CriterioBusquedaIRSalidaVO criterio) {
		if (criterio != null) {
			criterios.add(criterio);
		}
		return this;
	}

	public List<CriterioBusquedaIRSalidaEnum> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<CriterioBusquedaIRSalidaEnum> orderBy) {
		this.orderBy.clear();
		if (orderBy != null) {
			this.orderBy.addAll(orderBy);
		}
	}

	public CriteriosBusquedaIRSalidaVO addOrderBy(CriterioBusquedaIRSalidaEnum criterio) {
		if (criterio != null) {
			orderBy.add(criterio);
		}
		return this;
	}

}
