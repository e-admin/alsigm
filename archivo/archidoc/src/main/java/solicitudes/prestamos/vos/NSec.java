package solicitudes.prestamos.vos;

/**
 * @author ABELRL
 */
public class NSec {
	private String ano = null;

	private int numsec = 0;

	private int tipo = 0;

	public NSec(String ano, int numsec, int tipo) {
		this.ano = ano;
		this.numsec = numsec;
		this.tipo = tipo;
	}

	/**
	 * @return Returns the ano.
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            The ano to set.
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param idorgremitente
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Returns the numsec.
	 */
	public int getNumsec() {
		return numsec;
	}

	/**
	 * @param numsec
	 *            The numsec to set.
	 */
	public void setNumsec(int numsec) {
		this.numsec = numsec;
	}
}
