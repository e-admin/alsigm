package es.ieci.tecdoc.fwktd.dir3.api.vo.old;


/**
 * Información de un ámbito territorial.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AmbitoTerritorialVO extends CatalogoVO {


	private static final long serialVersionUID = -4087836080384710496L;

	private String idNivelAdministracion;
	private String idAmbitoGeografico;
	private String tipoEntidadGeografica;
	private Long idAmbitoDIR2;

	/**
	 * Constructor.
	 */
	public AmbitoTerritorialVO() {
		super();
	}

	public String getIdNivelAdministracion() {
		return idNivelAdministracion;
	}

	public void setIdNivelAdministracion(String idNivelAdministracion) {
		this.idNivelAdministracion = idNivelAdministracion;
	}

	public String getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}

	public void setIdAmbitoGeografico(String idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}

	public Long getIdAmbitoDIR2() {
		return idAmbitoDIR2;
	}

	public void setIdAmbitoDIR2(Long idAmbitoDIR2) {
		this.idAmbitoDIR2 = idAmbitoDIR2;
	}

	public String getTipoEntidadGeografica() {
		return tipoEntidadGeografica;
	}

	public void setTipoEntidadGeografica(String tipoEntidadGeografica) {
		this.tipoEntidadGeografica = tipoEntidadGeografica;
	}

}
