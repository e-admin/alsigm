/*
 * Created on 07-mar-2005
 *
 */
package transferencias.db;

public class NSecUIVO {
	/**
	 * @param numsec
	 */
	public NSecUIVO(long numsec, String idArchivo) {
		this.numsec = numsec;
		this.idArchivo = idArchivo;
	}

	long numsec;
	String idArchivo;

	/**
	 * @return Returns the numsec.
	 */
	public long getNumsec() {
		return numsec;
	}

	/**
	 * @param numsec
	 *            The numsec to set.
	 */
	public void setNumsec(long numsec) {
		this.numsec = numsec;
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
