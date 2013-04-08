package fondos.forms;

/**
 * Formulario para recogida de datos en la gestion dentro del cuadro de
 * clasificacion de elementos del tipo 'Clasificador de Series'
 */
public class ClasificadorFondosForm extends ClasificadorSeriesForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nombreNivel;
	private String idLCA;
	private int nivelAcceso;

	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}
}
