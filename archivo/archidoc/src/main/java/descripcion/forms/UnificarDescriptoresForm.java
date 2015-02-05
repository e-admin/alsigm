package descripcion.forms;

import common.forms.CustomForm;

public class UnificarDescriptoresForm extends CustomForm {

	private static final long serialVersionUID = 243368340729468768L;
	private String idListaDescriptora = null;
	private String calificadorFiltro = null;
	private String filtro = null;

	private String idPrincipal = null;

	private int[] posSeleccionadas = new int[0];
	private int[] posReemplazar = new int[0];

	public void resetSeleccionados() {
		setPosReemplazar(new int[0]);
		setPosSeleccionadas(new int[0]);
	}

	public void reset() {
		idListaDescriptora = null;
		calificadorFiltro = null;
		filtro = null;
		idPrincipal = null;
		posSeleccionadas = new int[0];
		posReemplazar = new int[0];
	}

	/**
	 * @return the idListaDescriptora
	 */
	public String getIdListaDescriptora() {
		return idListaDescriptora;
	}

	/**
	 * @param idListaDescriptora
	 *            the idListaDescriptora to set
	 */
	public void setIdListaDescriptora(String idListaDescriptora) {
		this.idListaDescriptora = idListaDescriptora;
	}

	/**
	 * @return the posSeleccionadas
	 */
	public int[] getPosSeleccionadas() {
		return posSeleccionadas;
	}

	/**
	 * @param posSeleccionadas
	 *            the posSeleccionadas to set
	 */
	public void setPosSeleccionadas(int[] posSeleccionadas) {
		this.posSeleccionadas = posSeleccionadas;
	}

	/**
	 * @return the posReemplazar
	 */
	public int[] getPosReemplazar() {
		return posReemplazar;
	}

	/**
	 * @param posReemplazar
	 *            the posReemplazar to set
	 */
	public void setPosReemplazar(int[] posReemplazar) {
		this.posReemplazar = posReemplazar;
	}

	/**
	 * @return the calificadorFiltro
	 */
	public String getCalificadorFiltro() {
		return calificadorFiltro;
	}

	/**
	 * @param calificadorFiltro
	 *            the calificadorFiltro to set
	 */
	public void setCalificadorFiltro(String calificadorFiltro) {
		this.calificadorFiltro = calificadorFiltro;
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
	 */
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the idPrincipal
	 */
	public String getIdPrincipal() {
		return idPrincipal;
	}

	/**
	 * @param idPrincipal
	 *            the idPrincipal to set
	 */
	public void setIdPrincipal(String idPrincipal) {
		this.idPrincipal = idPrincipal;
	}
}
