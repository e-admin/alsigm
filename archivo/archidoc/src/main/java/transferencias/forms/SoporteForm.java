package transferencias.forms;

import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en la edición de los soportes en los que
 * se presenta la documentación que integra una unidad documental
 */
public class SoporteForm extends ArchigestActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String formato = null;
	String soporte = null;
	String numeroDocumentos = null;
	private int[] posSoporteSeleccionado;

	public String getNumeroDocumentos() {
		return numeroDocumentos;
	}

	public void setNumeroDocumentos(String numeroDocumentos) {
		this.numeroDocumentos = numeroDocumentos;
	}

	public String getSoporte() {
		return soporte;
	}

	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int[] getPosSoporteSeleccionado() {
		return posSoporteSeleccionado;
	}

	public void setPosSoporteSeleccionado(int[] posSoporteSeleccionado) {
		this.posSoporteSeleccionado = posSoporteSeleccionado;
	}
}