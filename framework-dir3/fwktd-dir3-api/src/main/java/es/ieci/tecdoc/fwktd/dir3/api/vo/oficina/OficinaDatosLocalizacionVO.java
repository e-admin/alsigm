package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Localizacion")
public class OficinaDatosLocalizacionVO {
	@XStreamAlias("Tipo_Via")
	private String tipoVia;
	@XStreamAlias("Nombre_Via")
	private String nombreVia;
	@XStreamAlias("Numero_Via")
	private String numeroVia;
	@XStreamAlias("Codigo_Postal")
	private String codigoPostal;
	@XStreamAlias("Resto_Direccion")
	private String restoDireccion;
	@XStreamAlias("Localidad")
	private String localidad;
	@XStreamAlias("Provincia")
	private String provincia;
	@XStreamAlias("Isla")
	private String isla;
	@XStreamAlias("Comunidad_Autonoma")
	private String comunidadAutonoma;
	@XStreamAlias("Pais")
	private String pais;
	@XStreamAlias("Direccion_Extranjera")
	private String direccionExtranjera;
	
	public String getTipoVia() {
		return tipoVia;
	}
	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}
	public String getNombreVia() {
		return nombreVia;
	}
	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}
	public String getNumeroVia() {
		return numeroVia;
	}
	public void setNumeroVia(String numeroVia) {
		this.numeroVia = numeroVia;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getRestoDireccion() {
		return restoDireccion;
	}
	public void setRestoDireccion(String restoDireccion) {
		this.restoDireccion = restoDireccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIsla() {
		return isla;
	}
	public void setIsla(String isla) {
		this.isla = isla;
	}
	public String getComunidadAutonoma() {
		return comunidadAutonoma;
	}
	public void setComunidadAutonoma(String comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getDireccionExtranjera() {
		return direccionExtranjera;
	}
	public void setDireccionExtranjera(String direccionExtranjera) {
		this.direccionExtranjera = direccionExtranjera;
	}
}
