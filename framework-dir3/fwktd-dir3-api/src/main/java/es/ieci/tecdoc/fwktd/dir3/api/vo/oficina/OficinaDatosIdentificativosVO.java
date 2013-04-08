package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Identificativos")
public class OficinaDatosIdentificativosVO {
	@XStreamAlias("Codigo_Oficina")
	private String codigoOficina;
	@XStreamAlias("Denominacion_Oficina")
	private String denominacionOficina;
	@XStreamAlias("Externo_Fuente")
	private String externoFuente;
	
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
	public String getExternoFuente() {
		return externoFuente;
	}
	public void setExternoFuente(String externoFuente) {
		this.externoFuente = externoFuente;
	}

}
