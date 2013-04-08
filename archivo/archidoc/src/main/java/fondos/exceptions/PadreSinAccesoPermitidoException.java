package fondos.exceptions;

/**
 * Motivos por los que es posible que no se permite llevar a cabo alguna de las
 * operaciones disponibles en el módulo de gestión de fondos documentales
 */
public class PadreSinAccesoPermitidoException extends
		common.exceptions.SecurityException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idNivel;
	int tipoElemento;
	String titulo;

	public PadreSinAccesoPermitidoException(String idNivel, int tipoElemento,
			String titulo) {
		super("");
		this.idNivel = idNivel;
		this.tipoElemento = tipoElemento;
		this.titulo = titulo;
	}

	public int getTipoElemento() {

		return tipoElemento;
	}

	public void setTipoElemento(int tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}
}
