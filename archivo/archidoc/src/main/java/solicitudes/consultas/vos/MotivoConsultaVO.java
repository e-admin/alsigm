package solicitudes.consultas.vos;

/**
 * Clase VO que encapsula los motivos de consultas
 */
public class MotivoConsultaVO {

	private String id = null;
	private String motivo = null;
	private Integer tipoEntidad = null;
	private Integer tipoConsulta = null;
	private Integer visibilidad = null;

	public MotivoConsultaVO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Integer getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(Integer tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public Integer getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(Integer tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Integer getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(Integer visibilidad) {
		this.visibilidad = visibilidad;
	}
}