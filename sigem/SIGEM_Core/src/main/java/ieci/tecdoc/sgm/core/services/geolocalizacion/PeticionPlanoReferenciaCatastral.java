/**
 * PeticionPlano.java
 *
 */

package ieci.tecdoc.sgm.core.services.geolocalizacion;

public class PeticionPlanoReferenciaCatastral extends PeticionPlano{

	private String referenciaCatastral;
	
	public PeticionPlanoReferenciaCatastral() {
		super();
		this.referenciaCatastral = null;;
	}
	
	public PeticionPlanoReferenciaCatastral(int idMapa, int ancho, int alto, int escala, int codigoINEMunicipio, String referenciaCatastral) {
		super(idMapa, ancho, alto, escala, codigoINEMunicipio);
		this.referenciaCatastral = referenciaCatastral;
	}

	public String getReferenciaCatastral() {
		return referenciaCatastral;
	}

	public void setReferenciaCatastral(String referenciaCatastral) {
		this.referenciaCatastral = referenciaCatastral;
	}
	
}
