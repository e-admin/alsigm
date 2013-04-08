package docelectronicos.forms;

import common.forms.CustomForm;

import docelectronicos.DocumentosConstants;

public class CesionControlTareasForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String referencia = null;
	private String titulo = null;
	private String gestor = null;
	private String nuevoGestor = null;

	private int tipo = 0;
	private int tipoBusqueda = DocumentosConstants.TIPO_BUSQUEDA_POR_TAREA;
	private boolean resultado = false;
	private String[] tareasSeleccionadas = new String[0];

	public String getNuevoGestor() {
		return nuevoGestor;
	}

	public void setNuevoGestor(String nuevoGestor) {
		this.nuevoGestor = nuevoGestor;
	}

	public static int getTIPO_BUSQUEDA_POR_TAREA() {
		return DocumentosConstants.TIPO_BUSQUEDA_POR_TAREA;
	}

	public static int getTIPO_BUSQUEDA_POR_USUARIO_CAPTURA() {
		return DocumentosConstants.TIPO_BUSQUEDA_POR_USUARIO_CAPTURA;
	}

	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public String[] getTareasSeleccionadas() {
		return tareasSeleccionadas;
	}

	public void setTareasSeleccionadas(String[] tareasSeleccionadas) {
		this.tareasSeleccionadas = tareasSeleccionadas;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
