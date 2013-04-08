package transferencias.db;

/**
 * VO para el número secuencial de Unidad documental
 */
public class NSecUDocVO {

	/**
	 * Número secuencial
	 */
	int numsec;

	/**
	 * Constructor
	 */
	public NSecUDocVO(int numsec) {
		this.numsec = numsec;
	}

	/**
	 * Permite obtener el número secuencial
	 * 
	 * @return Número secuencial
	 */
	public int getNumsec() {
		return numsec;
	}

	/**
	 * Permite establecer el número secuencial
	 * 
	 * @param numsec
	 *            Número secuencial
	 */
	public void setNumsec(int numsec) {
		this.numsec = numsec;
	}

}
