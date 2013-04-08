package common.vos;

import org.apache.struts.action.ActionErrors;

/**
 * Value Object que contiene la información del tratamiento realizado a una
 * lista de registros (inserción, modificación o borrado).
 */
public class ResultadoRegistrosVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Número de registros de la lista. */
	private int numRegistros = 0;

	/** Número de registros de la lista que han sido tratados con éxito. */
	private int numRegistrosTratados = 0;

	/** Lista de mensajes de error. */
	private ActionErrors errores = null;

	/**
	 * Constructor.
	 */
	public ResultadoRegistrosVO() {
		super();
		errores = new ActionErrors();
	}

	/**
	 * Devuelve la lista de mensajes de error.
	 * 
	 * @return Lista de mensajes de error.
	 */
	public ActionErrors getErrores() {
		return errores;
	}

	/**
	 * Establece la lista de mensajes de error.
	 * 
	 * @param errores
	 *            Lista de mensajes de error.
	 */
	public void setErrores(ActionErrors errores) {
		this.errores = errores;
	}

	/**
	 * Devuelve el número de registros de la lista.
	 * 
	 * @return Número de registros de la lista.
	 */
	public int getNumRegistros() {
		return numRegistros;
	}

	/**
	 * Establece el número de registros de la lista.
	 * 
	 * @param numRegistros
	 *            Número de registros de la lista.
	 */
	public void setNumRegistros(int numRegistros) {
		this.numRegistros = numRegistros;
	}

	/**
	 * Devuelve el número de registros de la lista que han sido tratados con
	 * éxito.
	 * 
	 * @return Número de registros de la lista que han sido tratados con éxito.
	 */
	public int getNumRegistrosTratados() {
		return numRegistrosTratados;
	}

	/**
	 * Establece el número de registros de la lista que han sido tratados con
	 * éxito.
	 * 
	 * @param numRegistrosTratados
	 *            Número de registros de la lista que han sido tratados con
	 *            éxito.
	 */
	public void setNumRegistrosTratados(int numRegistrosTratados) {
		this.numRegistrosTratados = numRegistrosTratados;
	}
}