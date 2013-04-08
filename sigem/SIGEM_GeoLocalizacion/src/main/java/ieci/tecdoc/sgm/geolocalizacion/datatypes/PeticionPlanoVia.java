/**
 * PeticionPlano.java
 *
 */

package ieci.tecdoc.sgm.geolocalizacion.datatypes;

public class PeticionPlanoVia extends PeticionPlano{

	private int via;
	
	public PeticionPlanoVia() {
		super();
		this.via = 0;
	}
	
	public PeticionPlanoVia(int idMapa, int ancho, int alto, int escala, int codigoINEMunicipio, int via) {
		super(idMapa, ancho, alto, escala, codigoINEMunicipio);
		this.via = via;
	}

	public int getVia() {
		return via;
	}

	public void setVia(int via) {
		this.via = via;
	}
	
}
