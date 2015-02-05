package common.vos;

public class ParteCodigoReferenciaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idParte = null;
	private String ocultar = null;

	/**
	 * @return the idParte
	 */
	public String getIdParte() {
		return idParte;
	}

	/**
	 * @param idParte
	 *            the idParte to set
	 */
	public void setIdParte(String idParte) {
		this.idParte = idParte;
	}

	/**
	 * @return the ocultar
	 */
	public String getOcultar() {
		return ocultar;
	}

	/**
	 * @param ocultar
	 *            the ocultar to set
	 */
	public void setOcultar(String ocultar) {
		this.ocultar = ocultar;
	}

}
