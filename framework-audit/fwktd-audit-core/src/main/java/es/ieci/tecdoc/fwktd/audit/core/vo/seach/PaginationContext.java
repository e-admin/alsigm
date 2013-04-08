package es.ieci.tecdoc.fwktd.audit.core.vo.seach;

/**
 * 
 * 
 * 
 * Ejemplo de uso:
 * 
 * int sizePage=10;
 * PaginationContext paginationContext = new PaginationContext(sizePage);  
 * 
 * while (paginationContext.hasMorePages()) {  
 * 		List products = productDAO.queryProducts(queryParameter,paginationContext);
 * 		paginationContext.nextPage();
 * }
 * 
 * 
 *   
 * 
 * @author Iecisa
 *
 */
public class PaginationContext {
  
    /**
     * constante que identifica que todavía no se ha realizado ninguna consulta
     */
    private static final int NO_TOTAL_COUNT = -1;  
  
  
    /**
     * numero de n primeros elementos a ignorar en la busqueda paginada 
     */
    private int skipResults = 0;  
    
    /**
     * pagina actual
     */
    private int pageNumber = 0;
    
    /**
     * numero de elementos en la pagina actual
     */
    private int pageElementsCount = 0;
  
      
    /**
     * Numero de resultados máximos a mostrar por pagina
     */
    private int pageSize = 10;  
  
  
    /**
     * numero de resultados totales de la consulta cuando se realiza la primera busqueda
     */
    private int totalCount = NO_TOTAL_COUNT;  
    
    
    /**
     * @param pageSize numero de elementos maximos a mostrar por pagina
     */
    public PaginationContext(int pageSize){
    	super();
    	assert pageSize >= 0;
    	this.pageSize=pageSize;
    }
    
    /**
     * @param pageSize pageSize numero de elementos maximos a mostrar por pagina
     * @param page numero de pagina a obtener
     */
    public PaginationContext(int pageSize, int page){
    	super();
    	assert pageSize >= 0;
    	this.pageSize=pageSize;
    	setPage(page);
    }
    
    /**
     * metodo que actualiza el numero total de resultados obtenidos por la consulta completa sin paginar
     * @param totalCount
     */
    public void updateTotalCount(int totalCount) {  
        assert totalCount >= 0;  
        this.totalCount = totalCount;  
    }  
    
    public int getPageElementsCount() {
		return pageElementsCount;
	}

	public void setPageElementsCount(int pageElementsCount) {
		this.pageElementsCount = pageElementsCount;
	}

	/**
	 * metodo que obtiene el numero de elementos obtenidos en la consulta actual
	 * @param pageElementsCount
	 */
	public void updateCurrentPageElementsCount(int pageElementsCount) {  
        assert totalCount >= 0;  
        this.pageElementsCount = pageElementsCount;  
    }  
  
    /**
     * metodo que verifica si hay mas paginas a devolver por la busqueda
     * @return
     */
    public boolean hasMorePages() {
    	
    	boolean result = false;
    	
        if (!hasTotalCount()){  
            // si todavía nos se ha realizado la busqueda paginada  
            result = true;  
        }else{
        	// miramos si la siguiente pagina es menor o igual que el resultado total más el tamaño de una pagina
	        if (skipResults + pageSize <= totalCount) {
	            result =  true;
	        }

        }
        
        return result;
    }  
  
    /**
     * metodo para obtener la siguiente pagina de la busqueda realizada previamente
     */
    public void nextPage() {  
        skipResults += pageSize;
        pageNumber += 1;
    }
    
    public void setPage(int page) {  
        skipResults = page*pageSize;
        pageNumber = 1;
    }  
  
    /**
     * metodo que verifica si se ha realizado la busqueda paginada la primera vez,
     * en caso de no haberse  realizado se deberá actualizar el totalCount
     * @return
     */
    public boolean hasTotalCount() {  
        return totalCount != NO_TOTAL_COUNT;  
    }  
  
    public int getSkipResults() {  
        return skipResults;  
    }  
  
    public void setSkipResults(int skipResults) {  
        assert skipResults >= 0;  
        this.skipResults = skipResults;  
    }  
  
    public int getMaxResultsByPage() {  
        return pageSize;  
    }  
  
    
  
    public int getTotalCount() {  
        return totalCount;  
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}  
 
}
