package deposito.vos;

/**
 * Value Object para la información de los elementos asignables del depósito
 * físico
 */
public class ElementoAsignableVO extends ElementoVO {

	protected double longitud;
	protected String idfmhueco;
	protected int numhuecos;
	// protected int longitudUsable;

	// Para el calculo del volumen de la serie
	protected int numHijos;

	public ElementoAsignableVO() {
	}

	public ElementoAsignableVO(String idFormato, int longitud, int numeroHuecos) {
		this.idfmhueco = idFormato;
		this.longitud = longitud;
		this.numhuecos = numeroHuecos;
	}

	public int getNumhuecos() {
		return numhuecos;
	}

	public void setNumhuecos(int numhuecos) {
		this.numhuecos = numhuecos;
	}

	public String getIdFormato() {
		return idfmhueco;
	}

	public void setIdFormato(String idfmhueco) {
		this.idfmhueco = idfmhueco;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public boolean isAsignable() {
		return true;
	}

	// public int getLongitudUsable() {
	// return longitudUsable;
	// }
	// public void setLongitudUsable(int longitudUsable) {
	// this.longitudUsable = longitudUsable;
	// }

	public int getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}
}