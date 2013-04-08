package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Vigencia")
public class OficinaDatosVigenciaVO {
	
	@XStreamAlias("Estado_Oficina")
	private String estadoOficina;
	@XStreamAlias("Fecha_Alta")
	private String fechaAlta;
	@XStreamAlias("Fecha_Anulacion")
	private String fechaAnulacion;
	@XStreamAlias("Fecha_Extincion")
	private String fechaExtincion;
	
	public void setEstadoOficina(String estadoOficina) {
		this.estadoOficina = estadoOficina;
	}
	public String getEstadoOficina() {
		return estadoOficina;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAnulacion(String fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
	public String getFechaAnulacion() {
		return fechaAnulacion;
	}
	public void setFechaExtincion(String fechaExtincion) {
		this.fechaExtincion = fechaExtincion;
	}
	public String getFechaExtincion() {
		return fechaExtincion;
	}
}
