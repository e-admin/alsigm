package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class BaseEstadoRegistroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -8308270554695264311L;

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
