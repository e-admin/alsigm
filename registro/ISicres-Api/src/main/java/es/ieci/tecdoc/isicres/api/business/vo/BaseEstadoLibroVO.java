package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class BaseEstadoLibroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -3703711214043451834L;

	/**
	 * identificador único del vo
	 */
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
