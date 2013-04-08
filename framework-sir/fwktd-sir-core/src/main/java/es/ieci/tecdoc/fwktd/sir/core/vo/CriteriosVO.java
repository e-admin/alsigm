package es.ieci.tecdoc.fwktd.sir.core.vo;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.sir.core.types.CriterioEnum;

/**
 * Información de los criterios de búsqueda.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriteriosVO extends BaseValueObject {

	private static final long serialVersionUID = -7831555035143536214L;

	/**
	 * Lista de criterios.
	 */
	private List<CriterioVO> criterios = new ArrayList<CriterioVO>();

	/**
	 * Criterios por los que se van a ordenar los resultados
	 */
	private List<CriterioEnum> orderBy = new ArrayList<CriterioEnum>();

	/**
	 * Información de la página para la paginación de resultados
	 */
	private PageInfo pageInfo = null;


	/**
	 * Constructor.
	 */
	public CriteriosVO() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param criterios
	 *            Lista de criterios de búsqueda.
	 */
	public CriteriosVO(List<CriterioVO> criterios) {
		super();
		setCriterios(criterios);
	}

	public List<CriterioVO> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioVO> criterios) {
		this.criterios.clear();
		if (criterios != null) {
			this.criterios.addAll(criterios);
		}
	}

	public CriteriosVO addCriterioVO(CriterioVO criterio) {
		if (criterio != null) {
			criterios.add(criterio);
		}
		return this;
	}

	public List<CriterioEnum> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<CriterioEnum> orderBy) {
		this.orderBy.clear();
		if (orderBy != null) {
			this.orderBy.addAll(orderBy);
		}
	}

	public CriteriosVO addOrderBy(CriterioEnum criterio) {
		if (criterio != null) {
			orderBy.add(criterio);
		}
		return this;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public CriteriosVO setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
		return this;
	}
}
