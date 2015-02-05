package es.ieci.tecdoc.fwktd.server.pagination;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;

/**
 * Información de una página del listado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class PageInfo extends BaseValueObject {

	private static final long serialVersionUID = 3453986001873009595L;

	private static final int DEFAULT_PAGE_NUMBER = 0;
	private static final int DEFAULT_OBJECTS_PER_PAGE = 0;
	private static final int DEFAULT_MAX_NUM_ITEMS = 0;

	/**
	 * Número de página.
	 */
	private int pageNumber = DEFAULT_PAGE_NUMBER;

	/**
	 * Número de registros por página.
	 */
	private int objectsPerPage = DEFAULT_OBJECTS_PER_PAGE;

	/**
	 * Número máximo de resultados.
	 */
	private int maxNumItems = DEFAULT_MAX_NUM_ITEMS;

	/**
	 * Constructor.
	 */
	public PageInfo() {
		super();
	}

	/**
	 * Constructor.
	 * @param maxNumItems Número máximo de resultados.
	 */
	public PageInfo(int maxNumItems) {
		super();
		setMaxNumItems(maxNumItems);
	}

	/**
	 * Constructor.
	 * @param pageNumber Número de página.
	 * @param objectsPerPage Número de registros por página.
	 * @param maxNumItems Número máximo de resultados.
	 */
	public PageInfo(int pageNumber, int objectsPerPage) {
		super();
		setPageNumber(pageNumber);
		setObjectsPerPage(objectsPerPage);
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public int getMaxNumItems() {
		return maxNumItems;
	}

	public void setMaxNumItems(int maxNumItems) {
		this.maxNumItems = maxNumItems;
	}

}
