/**
 * PeticionPlano.java
 *
 */

package ieci.tecdoc.sgm.core.services.geolocalizacion;

public class PeticionPlano {

	protected int idMapa;
	protected int ancho;
	protected int alto;
	protected int escala;
	protected int codigoINEMunicipio;
	
	public PeticionPlano() {
		this.idMapa = 0;
		this.ancho = 0;
		this.alto = 0;
		this.escala = 0;
		this.codigoINEMunicipio = 0;
	}
	
	public PeticionPlano(int idMapa, int ancho, int alto, int escala, int codigoINEMunicipio) {
		this.idMapa = idMapa;
		this.ancho = ancho;
		this.alto = alto;
		this.escala = escala;
		this.codigoINEMunicipio = codigoINEMunicipio;
	}
	
	public int getAlto() {
		return alto;
	}
	
	public void setAlto(int alto) {
		this.alto = alto;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	
	public int getCodigoINEMunicipio() {
		return codigoINEMunicipio;
	}
	
	public void setCodigoINEMunicipio(int codigoINEMunicipio) {
		this.codigoINEMunicipio = codigoINEMunicipio;
	}
	
	public int getEscala() {
		return escala;
	}
	
	public void setEscala(int escala) {
		this.escala = escala;
	}
	
	public int getIdMapa() {
		return idMapa;
	}
	
	public void setIdMapa(int idMapa) {
		this.idMapa = idMapa;
	}
	
}
