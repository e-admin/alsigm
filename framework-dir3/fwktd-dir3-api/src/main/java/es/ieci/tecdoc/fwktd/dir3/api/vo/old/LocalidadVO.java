package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de una localidad.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class LocalidadVO extends CatalogoVO {

	private static final long serialVersionUID = 3570008522610292592L;

	private String idProvincia;
	private String idIsla;
	private String idEntidadGeografica;
	private String codigoRCP;
	private String codigoDIR2;

	/**
	 * Constructor.
	 */
	public LocalidadVO() {
		super();
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdIsla() {
		return idIsla;
	}

	public void setIdIsla(String idIsla) {
		this.idIsla = idIsla;
	}

	public String getIdEntidadGeografica() {
		return idEntidadGeografica;
	}

	public void setIdEntidadGeografica(String idEntidadGeografica) {
		this.idEntidadGeografica = idEntidadGeografica;
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
