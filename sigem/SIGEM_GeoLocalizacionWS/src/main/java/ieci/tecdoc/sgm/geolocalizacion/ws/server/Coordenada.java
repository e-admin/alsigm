package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Coordenada extends RetornoServicio{
	
	private double coordX;
	private double coordY;
	
	public Coordenada() {
		this.coordX = 0.0;
		this.coordY = 0.0;
	}
	
	public Coordenada(double coordX, double coordY) {
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
