package valoracion.vos;

/**
 * Clase que encapsula los numeros de secuencia de las valoraciones
 */
public class NSec {
	private int numsec = 0;

	public NSec(int numsec) {
		this.numsec = numsec;
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
