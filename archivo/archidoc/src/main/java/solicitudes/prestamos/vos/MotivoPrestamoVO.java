package solicitudes.prestamos.vos;

/**
 * Clase VO que encapsula los motivos de prestamos
 */
public class MotivoPrestamoVO {

	private String id = null;
	private String motivo = null;
	private Integer tipoUsuario = null;
	private Integer visibilidad = null;

	public MotivoPrestamoVO() {
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

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(Integer visibilidad) {
		this.visibilidad = visibilidad;
	}
}