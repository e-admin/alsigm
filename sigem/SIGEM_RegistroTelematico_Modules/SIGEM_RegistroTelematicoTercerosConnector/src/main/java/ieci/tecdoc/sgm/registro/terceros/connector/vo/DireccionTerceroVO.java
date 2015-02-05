package ieci.tecdoc.sgm.registro.terceros.connector.vo;

public class DireccionTerceroVO {

	protected String idDireccion;
	protected String pais;
	protected String provincia;
	protected String codigoPostal;
	protected String localidad;
	protected String municipio;
	protected String tipoVia;
	protected String via;
	protected String portal;
	protected String piso;
	protected String puerta;
	/**
	 * En caso de no rellenar la dirección completa, se autocompletará con la siguiente estructura:
	 * <code>tipoVia</code> + <code>via</code>, <code>numero</code>, <code>piso</code><code>puerta</code>
	 * Ejemplos:
	 * Calle Asturcón, 22, 1C
	 * La Caleya, SN
	 */
	protected String direccionCompleta;
	protected boolean porDefecto;


	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public boolean isPorDefecto() {
		return porDefecto;
	}
	public void setPorDefecto(boolean porDefecto) {
		this.porDefecto = porDefecto;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getTipoVia() {
		return tipoVia;
	}
	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getPortal() {
		return portal;
	}
	public void setPortal(String portal) {
		this.portal = portal;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getPuerta() {
		return puerta;
	}
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public String getIdDireccion() {
		return idDireccion;
	}
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}


}
