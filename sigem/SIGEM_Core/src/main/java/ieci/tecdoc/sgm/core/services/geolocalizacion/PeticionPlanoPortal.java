/**
 * PeticionPlano.java
 *
 */

package ieci.tecdoc.sgm.core.services.geolocalizacion;

public class PeticionPlanoPortal extends PeticionPlano{

	private int portal;
	
	public PeticionPlanoPortal() {
		super();
		this.portal = 0;
	}
	
	public PeticionPlanoPortal(int idMapa, int ancho, int alto, int escala, int codigoINEMunicipio, int portal) {
		super(idMapa, ancho, alto, escala, codigoINEMunicipio);
		this.portal = portal;
	}

	public int getPortal() {
		return portal;
	}

	public void setPortal(int portal) {
		this.portal = portal;
	}
	
}
