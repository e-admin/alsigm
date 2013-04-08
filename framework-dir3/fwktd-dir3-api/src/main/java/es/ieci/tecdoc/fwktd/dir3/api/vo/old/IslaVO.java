package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de una isla.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class IslaVO extends CatalogoVO {

	private static final long serialVersionUID = -384653745302026190L;

	private String idProvincia;

	/**
	 * Constructor.
	 */
	public IslaVO() {
		super();
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
}
