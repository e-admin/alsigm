/**
 * PeticionPlano.java
 *
 */

package ieci.tecdoc.sgm.geolocalizacion.ws.server;

public class PeticionPlanoCoordenadas extends PeticionPlano {

	private double coordX;
	private double coordY;
	
	public PeticionPlanoCoordenadas() {
		super();
		this.coordX = 0.0;
		this.coordY = 0.0;
	}
	
	public PeticionPlanoCoordenadas(int idMapa, double coordX, double coordY, int ancho, int alto, int escala, int codigoINEMunicipio) {
		super(idMapa, ancho, alto, escala, codigoINEMunicipio);
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	public double getCoordX() {
		return coordX;
	}
	
	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}
	
	public double getCoordY() {
		return coordY;
	}
	
	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}
}
