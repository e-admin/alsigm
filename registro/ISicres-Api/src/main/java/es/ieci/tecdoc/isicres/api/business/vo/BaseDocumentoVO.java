package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class BaseDocumentoVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 686954775497105602L;

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
