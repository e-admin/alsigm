package es.ieci.tecdoc.fwktd.csv.core.vo;


/**
 * Información de una aplicación externa.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionCSV extends AplicacionCSVForm {

	private static final long serialVersionUID = 7002279450867615665L;

	/**
	 * Identificador de la aplicación externa.
	 */
	private String id = null;

	/**
	 * Constructor.
	 */
	public AplicacionCSV() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
