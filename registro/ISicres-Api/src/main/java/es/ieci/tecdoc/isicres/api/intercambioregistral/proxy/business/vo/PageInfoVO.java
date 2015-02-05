package es.ieci.tecdoc.isicres.api.intercambioregistral.proxy.business.vo;

/**
 * Mapea PageInfoDTO
 * @author Iecisa
 * @version $Revision$ 
 *
 */
public class PageInfoVO {
	 protected int maxNumItems;
	 protected int objectsPerPage;
	 protected int pageNumber;
	 
	public int getMaxNumItems() {
		return maxNumItems;
	}
	public void setMaxNumItems(int maxNumItems) {
		this.maxNumItems = maxNumItems;
	}
	public int getObjectsPerPage() {
		return objectsPerPage;
	}
	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
