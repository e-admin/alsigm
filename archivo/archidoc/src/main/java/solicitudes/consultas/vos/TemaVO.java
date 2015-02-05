package solicitudes.consultas.vos;

/**
 * VO que encapsula los campos de un tema.
 */
public class TemaVO {
	/** Identificador del usuario que tiene asociado el tema */
	private String idUsuario = null;
	/** Tema de una consulta */
	private String tema = null;
	/**
	 * Tipo de entidad consultora que tiene asocido el tema.1-Investigador,
	 * 2-Ciudadano, 3-Organo externo
	 */
	private int tipoEntidad = 0;

	private String idUsrCSala = null;

	/**
	 * Constructor por defecto.
	 */
	public TemaVO() {
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(int tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public void setIdUsrCSala(String idUsrCSala) {
		this.idUsrCSala = idUsrCSala;
	}

	public String getIdUsrCSala() {
		return idUsrCSala;
	}
}