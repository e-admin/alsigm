package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Dependencia_Jerarquica")
public class OficinaDatosDependenciaJerarquicaVO {
	@XStreamAlias("Codigo_Unidad_Responsable")
	private String codigoUnidadResponsable;
	@XStreamAlias("Nivel_Administracion")
	private String nivelAdministracion;
	
	public String getCodigoUnidadResponsable() {
		return codigoUnidadResponsable;
	}
	public void setCodigoUnidadResponsable(String codigoUnidadResponsable) {
		this.codigoUnidadResponsable = codigoUnidadResponsable;
	}
	public String getNivelAdministracion() {
		return nivelAdministracion;
	}
	public void setNivelAdministracion(String nivelAdministracion) {
		this.nivelAdministracion = nivelAdministracion;
	}
}
