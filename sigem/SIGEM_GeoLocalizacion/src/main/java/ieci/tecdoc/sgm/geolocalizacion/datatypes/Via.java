package ieci.tecdoc.sgm.geolocalizacion.datatypes;

public class Via {
	private String nombreVia;
	private int idVia;
	private String tipoVia;
	private String claseNombre;
	private String idioma;
	private String estatus;
	private String fuente;
	private Coordenada[][] coordenadas;
	private Portal[] portales;
	private int codigoINEMunicipio;
	private int provincia;
	
	public Via() {
		this.nombreVia = null;
		this.idVia = 0;
		this.tipoVia = null;
		this.claseNombre = null;
		this.idioma = null;
		this.estatus = null;
		this.fuente = null;
		this.coordenadas = null;
		this.portales = null;
		this.codigoINEMunicipio = 0;
		this.provincia = 0;
	}
	
	public Via(String nombreVia, int idVia, String tipoVia, String claseNombre, String idioma, String estatus, String fuente, Coordenada[][] coordenadas, Portal[] portales, int codigoINEMunicipio, int provincia) {
		this.nombreVia = nombreVia;
		this.idVia = idVia;
		this.tipoVia = tipoVia;
		this.claseNombre = claseNombre;
		this.idioma = idioma;
		this.estatus = estatus;
		this.fuente = fuente;
		this.coordenadas = coordenadas;
		this.portales = portales;
		this.codigoINEMunicipio = codigoINEMunicipio;
		this.provincia = provincia;
	}

	public String getClaseNombre() {
		return claseNombre;
	}

	public void setClaseNombre(String claseNombre) {
		this.claseNombre = claseNombre;
	}

	public int getCodigoINEMunicipio() {
		return codigoINEMunicipio;
	}

	public void setCodigoINEMunicipio(int codigoINEMunicipio) {
		this.codigoINEMunicipio = codigoINEMunicipio;
	}

	public Coordenada[][] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenada[][] coordenadas) {
		this.coordenadas = coordenadas;
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

	public int getIdVia() {
		return idVia;
	}

	public void setIdVia(int idVia) {
		this.idVia = idVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public Portal[] getPortales() {
		return portales;
	}

	public void setPortales(Portal[] portales) {
		this.portales = portales;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public int getProvincia() {
		return provincia;
	}

	public void setProvincia(int provincia) {
		this.provincia = provincia;
	}
	
}
