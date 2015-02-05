package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de una provincia.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ProvinciaVO extends CatalogoVO {

	private static final long serialVersionUID = -4303192537759755926L;

	private String idComunidadAutonoma;

	/**
	 * Constructor.
	 */
	public ProvinciaVO() {
		super();
	}

	public String getIdComunidadAutonoma() {
		return idComunidadAutonoma;
	}

	public void setIdComunidadAutonoma(String idComunidadAutonoma) {
		this.idComunidadAutonoma = idComunidadAutonoma;
	}
}
