package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class IdentificadorRegistroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -192816978089473826L;

	protected String idRegistro;

	protected String idLibro;

	public IdentificadorRegistroVO() {
		super();
	}

	public IdentificadorRegistroVO(String anIdRegistro, String anIdLibro) {
		super();
		this.idRegistro = anIdRegistro;
		this.idLibro = anIdLibro;
	}

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}
}
