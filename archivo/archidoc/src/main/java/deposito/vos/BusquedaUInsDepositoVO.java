package deposito.vos;

/**
 * Value Object con la información referente a las unidades de instalación en
 * las que se almacena la documentación en el depósito físico
 */
public class BusquedaUInsDepositoVO extends UInsDepositoVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private String tituloFondo;
	private String idPadreHueco;
	private String numOrdenHueco;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTituloFondo() {
		return tituloFondo;
	}

	public void setTituloFondo(String titulofondo) {
		this.tituloFondo = titulofondo;
	}

	public String getIdPadreHueco() {
		return idPadreHueco;
	}

	public void setIdPadreHueco(String idHueco) {
		this.idPadreHueco = idHueco;
	}

	public String getNumOrdenHueco() {
		return numOrdenHueco;
	}

	public void setNumOrdenHueco(String numOrdenHueco) {
		this.numOrdenHueco = numOrdenHueco;
	}

	public String getIdHueco() {
		return idPadreHueco + ":" + numOrdenHueco;
	}

}