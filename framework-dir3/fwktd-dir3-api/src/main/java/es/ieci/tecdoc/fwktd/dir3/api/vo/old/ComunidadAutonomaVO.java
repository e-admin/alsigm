package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de una comunidad autónoma.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ComunidadAutonomaVO extends CatalogoVO {

	private static final long serialVersionUID = -6985922302533428113L;

	private String idPais;
	private String codigoRCP;
	private String codigoDIR2;

	/**
	 * Constructor.
	 */
	public ComunidadAutonomaVO() {
		super();
	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getCodigoRCP() {
		return codigoRCP;
	}

	public void setCodigoRCP(String codigoRCP) {
		this.codigoRCP = codigoRCP;
	}

	public String getCodigoDIR2() {
		return codigoDIR2;
	}

	public void setCodigoDIR2(String codigoDIR2) {
		this.codigoDIR2 = codigoDIR2;
	}
}
