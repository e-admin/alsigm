package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de una entidad geográfica.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class EntidadGeograficaVO extends CatalogoVO {

	private static final long serialVersionUID = -5973291934280932355L;

	private Long idAmbitoDIR2;

	/**
	 * Constructor.
	 */
	public EntidadGeograficaVO() {
		super();
	}

	public Long getIdAmbitoDIR2() {
		return idAmbitoDIR2;
	}

	public void setIdAmbitoDIR2(Long idAmbitoDIR2) {
		this.idAmbitoDIR2 = idAmbitoDIR2;
	}
}
