package es.ieci.tecdoc.fwktd.dir3.api.vo.unidad;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Vigencia")
public class OrganismoDatosVigenciaVO {
	@XStreamAlias("Estado")
	private String estado;
	@XStreamAlias("Fecha_Alta")
	private String fechaAlta;
	@XStreamAlias("Fecha_Baja")
	private String fechaBaja;
	@XStreamAlias("Fecha_Anulacion")
	private String fechaAnulacion;
	@XStreamAlias("Fecha_Extincion")
	private String fechaExtincion;

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getFechaAnulacion() {
		return fechaAnulacion;
	}
	public void setFechaAnulacion(String fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
	public String getFechaExtincion() {
		return fechaExtincion;
	}
	public void setFechaExtincion(String fechaExtincion) {
		this.fechaExtincion = fechaExtincion;
	}
}
