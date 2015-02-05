package deposito.vos;

public class HuecoID implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idpadre;
	int numorden;

	HuecoID() {
	}

	public HuecoID(String idPadre, int numOrden) {
		idpadre = idPadre;
		numorden = numOrden;
	}

	/**
	 * @return Returns the idpadre.
	 */
	public String getIdpadre() {
		return idpadre;
	}

	/**
	 * @param idpadre
	 *            The idpadre to set.
	 */
	public void setIdpadre(String iddelpadre) {
		this.idpadre = iddelpadre;
	}

	/**
	 * @return Returns the numorden.
	 */
	public int getNumorden() {
		return numorden;
	}

	/**
	 * @param numorden
	 *            The numorden to set.
	 */
	public void setNumorden(int numorden) {
		this.numorden = numorden;
	}

	public boolean equals(Object object) {
		if (object instanceof HuecoID) {
			HuecoID compareTo = (HuecoID) object;
			if (compareTo.getIdpadre().equals(this.idpadre)
					&& this.numorden == compareTo.getNumorden())
				return true;
		}
		return false;
	}

	public int hashCode() {
		return super.hashCode();
	}

	public String toString() {
		return "Padre: " + this.idpadre + " - orden: " + numorden;
	}
}
