package es.ieci.tecdoc.fwktd.dir3.api.vo.unidad;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Identificativos")
public class OrganismoDatosIdentificativosVO {
	@XStreamAlias("Codigo_Unidad_Organica")
	private String codigoUnidadOrganica;
	@XStreamAlias("Nombre_Unidad_Organica")
	private String nombreUnidadOrganica;
	@XStreamAlias("Nivel_Administracion")
	private String nivelAdministracion;
	@XStreamAlias("Indicador_Entidad_Derecho_Publico")
	private String indicadorEntidadDerechoPublico;
	@XStreamAlias("Codigo_Externo")
	private String codigoExterno;

	public String getCodigoUnidadOrganica() {
		return codigoUnidadOrganica;
	}
	public void setCodigoUnidadOrganica(String codigoUnidadOrganica) {
		this.codigoUnidadOrganica = codigoUnidadOrganica;
	}
	public String getNombreUnidadOrganica() {
		return nombreUnidadOrganica;
	}
	public void setNombreUnidadOrganica(String nombreUnidadOrganica) {
		this.nombreUnidadOrganica = nombreUnidadOrganica;
	}
	public String getNivelAdministracion() {
		return nivelAdministracion;
	}
	public void setNivelAdministracion(String nivelAdministracion) {
		this.nivelAdministracion = nivelAdministracion;
	}
	public String getIndicadorEntidadDerechoPublico() {
		return indicadorEntidadDerechoPublico;
	}
	public void setIndicadorEntidadDerechoPublico(
			String indicadorEntidadDerechoPublico) {
		this.indicadorEntidadDerechoPublico = indicadorEntidadDerechoPublico;
	}
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
}
