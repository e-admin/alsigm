package es.ieci.tecdoc.fwktd.dir3.api.vo.relacion;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("OficinaOrganismo")
public class RelacionUnidOrgOficinaVO {

	@XStreamAlias("Codigo_Unidad_Organica")
	private String codigoUnidadOrganica;
	@XStreamAlias("Denominacion_Unidad_Organica")
	private String denominacionUnidadOrganica;
	@XStreamAlias("Codigo_Oficina")
	private String codigoOficina;
	@XStreamAlias("Denominacion_Oficina")
	private String denominacionOficina;
	@XStreamAlias("Estado_Relacion")
	private String estadoRelacion;
	public String getCodigoUnidadOrganica() {
		return codigoUnidadOrganica;
	}
	public void setCodigoUnidadOrganica(String codigoUnidadOrganica) {
		this.codigoUnidadOrganica = codigoUnidadOrganica;
	}
	public String getDenominacionUnidadOrganica() {
		return denominacionUnidadOrganica;
	}
	public void setDenominacionUnidadOrganica(String denominacionUnidadOrganica) {
		this.denominacionUnidadOrganica = denominacionUnidadOrganica;
	}
	public String getCodigoOficina() {
		return codigoOficina;
	}
	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}
	public String getDenominacionOficina() {
		return denominacionOficina;
	}
	public void setDenominacionOficina(String denominacionOficina) {
		this.denominacionOficina = denominacionOficina;
	}
	public String getEstadoRelacion() {
		return estadoRelacion;
	}
	public void setEstadoRelacion(String estadoRelacion) {
		this.estadoRelacion = estadoRelacion;
	}


}
