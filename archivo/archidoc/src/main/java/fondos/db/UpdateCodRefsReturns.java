package fondos.db;

public class UpdateCodRefsReturns {
	String finalCodRefPadre;
	String codReferencia;

	public UpdateCodRefsReturns(String finalCodRefPadre, String codReferencia) {
		this.finalCodRefPadre = finalCodRefPadre;
		this.codReferencia = codReferencia;
	}

	/**
	 * @return Returns the codReferencia.
	 */
	public String getCodReferencia() {
		return codReferencia;
	}

	/**
	 * @param codReferencia
	 *            The codReferencia to set.
	 */
	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	/**
	 * @return Returns the finalCodRefPadre.
	 */
	public String getFinalCodRefPadre() {
		return finalCodRefPadre;
	}

	/**
	 * @param finalCodRefPadre
	 *            The finalCodRefPadre to set.
	 */
	public void setFinalCodRefPadre(String finalCodRefPadre) {
		this.finalCodRefPadre = finalCodRefPadre;
	}
}
