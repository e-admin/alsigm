package deposito.db;

public class NSecSigNumHuecoVO {

	private long sigNumHueco;
	private String idArchivo;

	/**
	 * @param sigNumHueco
	 */
	public NSecSigNumHuecoVO(long sigNumHueco, String idArchivo) {
		this.sigNumHueco = sigNumHueco;
		this.idArchivo = idArchivo;
	}

	/**
	 * @return Returns the sigNumHueco.
	 */
	public long getSigNumHueco() {
		return sigNumHueco;
	}

	/**
	 * @param sigNumHueco
	 *            The sigNumHueco to set.
	 */
	public void setSigNumHueco(long numsec) {
		this.sigNumHueco = numsec;
	}

	/**
	 * @return el idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            el idArchivo a establecer
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}
}