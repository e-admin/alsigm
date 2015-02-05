package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de un tipo de entidad pública.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoEntidadPublicaVO extends CatalogoVO {

	private static final long serialVersionUID = -1166855771260275701L;

	private String entidadDerechoPublico;
	private Long idTipoEntidadDIR2;

	/**
	 * Constructor.
	 */
	public TipoEntidadPublicaVO() {
		super();
	}

	public String getEntidadDerechoPublico() {
		return entidadDerechoPublico;
	}

	public void setEntidadDerechoPublico(String entidadDerechoPublico) {
		this.entidadDerechoPublico = entidadDerechoPublico;
	}

	public Long getIdTipoEntidadDIR2() {
		return idTipoEntidadDIR2;
	}

	public void setIdTipoEntidadDIR2(Long idTipoEntidadDIR2) {
		this.idTipoEntidadDIR2 = idTipoEntidadDIR2;
	}
}
