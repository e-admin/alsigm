package es.ieci.tecdoc.isicres.api.intercambioregistral.proxy.business.vo;

import java.util.List;

//TODO mapea CriteriosDTO
public class CriteriosVO {
	protected List<CriterioVO> criterios;
	protected List<String> orderBy;
	protected PageInfoVO pageInfo;

	public List<CriterioVO> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioVO> criterios) {
		this.criterios = criterios;
	}

	public List<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<String> orderBy) {
		this.orderBy = orderBy;
	}

	public PageInfoVO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoVO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
