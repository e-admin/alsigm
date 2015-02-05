package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Portal extends RetornoServicio{
	private int codigoINEMunicipio;
	private int idPortal;
	private int idVia;
	private Coordenada coords;
	private String numPortal;
	private String idioma;
	private String claseNombre;
	private String estatus;
	private String fuente;
	private String tipoPortal;
	private int provincia;
	
	public Portal() {
		this.codigoINEMunicipio = 0;
		this.idPortal = 0;
		this.idVia = 0;
		this.coords = null;
		this.numPortal = null;
		this.idioma = null;
		this.claseNombre = null;
		this.estatus = null;
		this.fuente = null;
		this.tipoPortal = null;
		this.provincia = 0;
	}
	
	public Portal(int codigoINEMunicipio, int idPortal, int idVia, Coordenada coords, String numPortal, String idioma, String claseNombre, String estatus, String fuente, String tipoPortal, int provincia) {
		this.codigoINEMunicipio = codigoINEMunicipio;
		this.idPortal = idPortal;
		this.idVia = idVia;
		this.coords = coords;
		this.numPortal = numPortal;
		this.idioma = idioma;
		this.claseNombre = claseNombre;
		this.estatus = estatus;
		this.fuente = fuente;
		this.tipoPortal = tipoPortal;
		this.provincia = provincia;
	}

	public int getCodigoINEMunicipio() {
		return codigoINEMunicipio;
	}

	public void setCodigoINEMunicipio(int codigoINEMunicipio) {
		this.codigoINEMunicipio = codigoINEMunicipio;
	}

	public int getIdPortal() {
		return idPortal;
	}

	public void setIdPortal(int idPortal) {
		this.idPortal = idPortal;
	}

	public int getIdVia() {
		return idVia;
	}

	public void setIdVia(int idVia) {
		this.idVia = idVia;
	}

	public Coordenada getCoords() {
		return coords;
	}

	public void setCoords(Coordenada coords) {
		this.coords = coords;
	}

	public String getNumPortal() {
		return numPortal;
	}

	public void setNumPortal(String numPortal) {
		this.numPortal = numPortal;
	}

	public String getClaseNombre() {
		return claseNombre;
	}

	public void setClaseNombre(String claseNombre) {
		this.claseNombre = claseNombre;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getTipoPortal() {
		return tipoPortal;
	}

	public void setTipoPortal(String tipoPortal) {
		this.tipoPortal = tipoPortal;
	}

	public int getProvincia() {
		return provincia;
	}

	public void setProvincia(int provincia) {
		this.provincia = provincia;
	}

}
