package solicitudes.vos;

/**
 * Clase VO que encapsula los motivos de rechazo
 */
public class MotivoRechazoVO {
	private String id = null;
	/**
	 * Tipo de la solicitud a la que esta asociado el motivo.1-Préstamo,
	 * 2-Consulta, 3-Prorroga
	 */
	private Integer tipoSolicitud = null;
	/** Texto con el motivo de rechazo */
	private String motivo = null;

	public MotivoRechazoVO() {
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

	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
}