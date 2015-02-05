package descripcion.vos;

/**
 * Información de las preferencias de un usuario en cuanto a las definiciones de
 * formatos de fichas.
 */
public class FmtPrefFichaVO extends FmtFichaVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idFicha = null;
	private String idUsuario = null;
	private int tipoFmt = -1;
	private String idFmt = null;

	/**
	 * Constructor.
	 */
	public FmtPrefFichaVO() {
		super();
	}

	/**
	 * @return Returns the idFicha.
	 */
	public String getIdFicha() {
		return idFicha;
	}

	/**
	 * @param idFicha
	 *            The idFicha to set.
	 */
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return Returns the idFmt.
	 */
	public String getIdFmt() {
		return idFmt;
	}

	/**
	 * @param idFmt
	 *            The idFmt to set.
	 */
	public void setIdFmt(String idFmt) {
		this.idFmt = idFmt;
	}

	/**
	 * @return Returns the idUsuario.
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            The idUsuario to set.
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return Returns the tipoFmt.
	 */
	public int getTipoFmt() {
		return tipoFmt;
	}

	/**
	 * @param tipoFmt
	 *            The tipoFmt to set.
	 */
	public void setTipoFmt(int tipoFmt) {
		this.tipoFmt = tipoFmt;
	}
}
