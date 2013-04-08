package salas.form;

import common.forms.CustomForm;

/**
 * Formulario para la gestión de estados de la Mesa
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class EstadoMesaForm extends CustomForm {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la Sala
	 */
	private String idSala = null;

	/**
	 * Identificadores de las Mesas
	 */
	private String[] idsMesa = null;

	/**
	 * Estado que se quiere establecer
	 */
	private String estadoAEstablecer = null;

	/**
	 * Estado que podemos seleccionar
	 */
	private String estadoASeleccionar = null;

	/**
	 * Indica si hay elementos a seleccionar
	 */
	private boolean hayElementoASeleccionar = false;

	public String[] getIdsMesa() {
		return idsMesa;
	}

	public void setIdsMesa(String[] idsMesa) {
		this.idsMesa = idsMesa;
	}

	public String getIdSala() {
		return idSala;
	}

	public void setIdSala(String idSala) {
		this.idSala = idSala;
	}

	public String getEstadoAEstablecer() {
		return estadoAEstablecer;
	}

	public void setEstadoAEstablecer(String estadoAEstablecer) {
		this.estadoAEstablecer = estadoAEstablecer;
	}

	public String getEstadoASeleccionar() {
		return estadoASeleccionar;
	}

	public void setEstadoASeleccionar(String estadoASeleccionar) {
		this.estadoASeleccionar = estadoASeleccionar;
	}

	public boolean isHayElementoASeleccionar() {
		return hayElementoASeleccionar;
	}

	public void setHayElementoASeleccionar(boolean hayElementoASeleccionar) {
		this.hayElementoASeleccionar = hayElementoASeleccionar;
	}
}