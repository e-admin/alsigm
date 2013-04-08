package solicitudes.consultas.vos;

/**
 * VO para los motivos de consulta y préstamo
 */
public class MotivoVO {
	/** Tipo de Consulta.1-Directa o 2-Indirecta */
	private int tipoConsulta = 0;
	/** Texto del motivo */
	private String motivo = null;
	/** Tipo de la entidad.1-Investigador, 2-Ciudadano, 3-Org Externo */
	private int tipoEntidad = 0;

	public MotivoVO() {
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(int tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public int getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(int tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}
}