package es.ieci.tecdoc.fwktd.dir3.api.vo.unidad;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Dependencia")
public class OrganismoDatosDependenciaVO {
	@XStreamAlias("Codigo_Unidad_Superior_Jerarquica")
	private String codigoUnidadSuperiorJerarquica;
	@XStreamAlias("Denominacion_Unidad_Superior_Jerarquica")
	private String denominacionUnidadSuperiorJerarquica;
	@XStreamAlias("Codigo_Unidad_Organica_Raiz")
	private String codigoUnidadOrganicaRaiz;
	@XStreamAlias("Denominacion_Unidad_Organica_Raiz")
	private String denominacionUnidadOrganicaRaiz;
	@XStreamAlias("Codigo_Unidad_Raiz_Entidad_Derecho_Publico")
	private String codigoUnidadRaizEntidadDerechoPublico;
	@XStreamAlias("Denominacion_Unidad_Raiz_Entidad_Derecho_Publico")
	private String denominacionUnidadRaizEntidadDerechoPublico;
	@XStreamAlias("Nivel_Jerarquico")
	private String nivelJerarquico;
	
	public String getCodigoUnidadSuperiorJerarquica() {
		return codigoUnidadSuperiorJerarquica;
	}
	public void setCodigoUnidadSuperiorJerarquica(
			String codigoUnidadSuperiorJerarquica) {
		this.codigoUnidadSuperiorJerarquica = codigoUnidadSuperiorJerarquica;
	}
	public String getDenominacionUnidadSuperiorJerarquica() {
		return denominacionUnidadSuperiorJerarquica;
	}
	public void setDenominacionUnidadSuperiorJerarquica(
			String denominacionUnidadSuperiorJerarquica) {
		this.denominacionUnidadSuperiorJerarquica = denominacionUnidadSuperiorJerarquica;
	}
	public String getCodigoUnidadOrganicaRaiz() {
		return codigoUnidadOrganicaRaiz;
	}
	public void setCodigoUnidadOrganicaRaiz(String codigoUnidadOrganicaRaiz) {
		this.codigoUnidadOrganicaRaiz = codigoUnidadOrganicaRaiz;
	}
	public String getDenominacionUnidadOrganicaRaiz() {
		return denominacionUnidadOrganicaRaiz;
	}
	public void setDenominacionUnidadOrganicaRaiz(
			String denominacionUnidadOrganicaRaiz) {
		this.denominacionUnidadOrganicaRaiz = denominacionUnidadOrganicaRaiz;
	}
	public String getCodigoUnidadRaizEntidadDerechoPublico() {
		return codigoUnidadRaizEntidadDerechoPublico;
	}
	public void setCodigoUnidadRaizEntidadDerechoPublico(
			String codigoUnidadRaizEntidadDerechoPublico) {
		this.codigoUnidadRaizEntidadDerechoPublico = codigoUnidadRaizEntidadDerechoPublico;
	}
	public String getDenominacionUnidadRaizEntidadDerechoPublico() {
		return denominacionUnidadRaizEntidadDerechoPublico;
	}
	public void setDenominacionUnidadRaizEntidadDerechoPublico(
			String denominacionUnidadRaizEntidadDerechoPublico) {
		this.denominacionUnidadRaizEntidadDerechoPublico = denominacionUnidadRaizEntidadDerechoPublico;
	}
	public String getNivelJerarquico() {
		return nivelJerarquico;
	}
	public void setNivelJerarquico(String nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}
}
