package deposito.forms;

import common.forms.CustomForm;

import deposito.global.Constants;

public class NavegadorDepositoForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idtipopadre;
	String idpadre;
	String idseleccionado;
	String idtiposeleccionado;
	String seleccionado;
	String idseleccionadoinicial;
	String idtiposeleccionadoinicial;
	String seleccionadoinicial;
	String idAsignableSeleccionado;
	String idTipoAsignableSeleccionado;
	String asignableSeleccionado;
	String method;
	String root;
	String idroot;
	String idtiporoot;
	String filterByIdformato;
	String idTipoLastLevel;
	String hideHuecosLibres;
	boolean todosFormatos;
	String idArchivo;
	boolean allowAllFormats;
	int numHuecosLibres = 0;

	/**
	 * Número de Huecos Necesarios
	 */
	int numHuecosNecesarios = -1;

	/**
	 * Este es un valor true o false. Si tiene true, comprueba que el elemento
	 * que se va a seleccionar tenga algún hueco libre
	 */
	boolean checkHasHuecos;

	public String getHideHuecosLibres() {
		return hideHuecosLibres;
	}

	public void setHideHuecosLibres(String showHuecosLibres) {
		this.hideHuecosLibres = showHuecosLibres;
	}

	public String getIdTipoLastLevel() {
		return this.idTipoLastLevel;
	}

	public void setIdTipoLastLevel(String idFormatoLastLevel) {
		this.idTipoLastLevel = idFormatoLastLevel;
	}

	public String getFilterByIdformato() {
		return this.filterByIdformato;
	}

	public void setFilterByIdformato(String filterByIdformato) {
		this.filterByIdformato = filterByIdformato;
	}

	public String getRoot() {
		return this.root;
	}

	public void setRoot(String root) {
		this.root = root;
		if (root != null && root.length() > 0) {
			String ids[] = root.split(Constants.DELIMITER_IDS);
			setIdroot(ids[0].trim());
			setIdtiporoot(ids[1].trim());
		}
	}

	/**
	 * @return Returns the seleccionado.
	 */
	public String getSeleccionado() {
		return seleccionado;
	}

	/**
	 * @param seleccionado
	 *            The seleccionado to set.
	 */
	public void setSeleccionado(String seleccionado) {
		// el valor seleccionado esta compuesto de id+idtipo, para poder
		// identificarlos
		// de manera unica
		this.seleccionado = seleccionado;

		if (seleccionado == null) {
			setIdseleccionado(null);
			setIdtiposeleccionado(null);
		} else if (seleccionado.length() > 0) {
			String ids[] = seleccionado.split(Constants.DELIMITER_IDS);
			setIdseleccionado(ids[0].trim());
			setIdtiposeleccionado(ids[1].trim());
		}
	}

	/**
	 * @return el idseleccionadoinicial
	 */
	public String getIdseleccionadoinicial() {
		return idseleccionadoinicial;
	}

	/**
	 * @param idseleccionadoinicial
	 *            el idseleccionadoinicial a establecer
	 */
	public void setIdseleccionadoinicial(String idseleccionadoinicial) {
		this.idseleccionadoinicial = idseleccionadoinicial;
	}

	/**
	 * @return el idtiposeleccionadoinicial
	 */
	public String getIdtiposeleccionadoinicial() {
		return idtiposeleccionadoinicial;
	}

	/**
	 * @param idtiposeleccionadoinicial
	 *            el idtiposeleccionadoinicial a establecer
	 */
	public void setIdtiposeleccionadoinicial(String idtiposeleccionadoinicial) {
		this.idtiposeleccionadoinicial = idtiposeleccionadoinicial;
	}

	/**
	 * @return el seleccionadoInicial
	 */
	public String getSeleccionadoinicial() {
		return seleccionadoinicial;
	}

	/**
	 * @param seleccionado
	 *            The seleccionado to set.
	 */
	public void setSeleccionadoinicial(String seleccionadoinicial) {
		// el valor seleccionado esta compuesto de id+idtipo, para poder
		// identificarlos
		// de manera unica
		this.seleccionadoinicial = seleccionadoinicial;
		if (seleccionadoinicial != null && seleccionadoinicial.length() > 0) {
			String ids[] = seleccionadoinicial.split(Constants.DELIMITER_IDS);
			setIdseleccionadoinicial(ids[0].trim());
			setIdtiposeleccionadoinicial(ids[1].trim());
		} else {
			setIdseleccionadoinicial(null);
			setIdtiposeleccionadoinicial(null);
		}
	}

	/**
	 * @return the idAsignableSeleccionado
	 */
	public String getIdAsignableSeleccionado() {
		return idAsignableSeleccionado;
	}

	/**
	 * @param idAsignableSeleccionado
	 *            the idAsignableSeleccionado to set
	 */
	public void setIdAsignableSeleccionado(String idAsignableSeleccionado) {
		this.idAsignableSeleccionado = idAsignableSeleccionado;
	}

	/**
	 * @return the idTipoAsignableSeleccionado
	 */
	public String getIdTipoAsignableSeleccionado() {
		return idTipoAsignableSeleccionado;
	}

	/**
	 * @param idTipoAsignableSeleccionado
	 *            the idTipoAsignableSeleccionado to set
	 */
	public void setIdTipoAsignableSeleccionado(
			String idTipoAsignableSeleccionado) {
		this.idTipoAsignableSeleccionado = idTipoAsignableSeleccionado;
	}

	/**
	 * @return the asignableSeleccionado
	 */
	public String getAsignableSeleccionado() {
		return asignableSeleccionado;
	}

	/**
	 * @param asignableSeleccionado
	 *            the asignableSeleccionado to set
	 */
	public void setAsignableSeleccionado(String asignableSeleccionado) {
		// el valor seleccionado esta compuesto de id+idtipo, para poder
		// identificarlos
		// de manera unica
		this.asignableSeleccionado = asignableSeleccionado;
		if (asignableSeleccionado != null && asignableSeleccionado.length() > 0) {
			String ids[] = asignableSeleccionado.split(Constants.DELIMITER_IDS);
			setIdAsignableSeleccionado(ids[0].trim());
			setIdTipoAsignableSeleccionado(ids[1].trim());
		} else {
			setIdAsignableSeleccionado(null);
			setIdTipoAsignableSeleccionado(null);
		}
	}

	public void setIdroot(String idroot) {
		this.idroot = idroot;
	}

	public String getIdroot() {
		return this.idroot;
	}

	public String getIdtiporoot() {
		return this.idtiporoot;
	}

	public void setIdtiporoot(String idtiporoot) {
		this.idtiporoot = idtiporoot;
	}

	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return Returns the idpadre.
	 */
	public String getIdpadre() {
		return idpadre;
	}

	/**
	 * @param idpadre
	 *            The idpadre to set.
	 */
	public void setIdpadre(String idpadre) {
		this.idpadre = idpadre;
	}

	/**
	 * @return Returns the idseleccionado.
	 */
	public String getIdseleccionado() {
		return idseleccionado;
	}

	/**
	 * @param idseleccionado
	 *            The idseleccionado to set.
	 */
	private void setIdseleccionado(String idseleccionado) {
		this.idseleccionado = idseleccionado;
	}

	/**
	 * @return Returns the idtipopadre.
	 */
	public String getIdtipopadre() {
		return idtipopadre;
	}

	/**
	 * @param idtipopadre
	 *            The idtipopadre to set.
	 */
	public void setIdtipopadre(String idtipopadre) {
		this.idtipopadre = idtipopadre;
	}

	/**
	 * @return Returns the idtiposeleccionado.
	 */

	public String getIdtiposeleccionado() {
		return idtiposeleccionado;
	}

	/**
	 * @param idtiposeleccionado
	 *            The idtiposeleccionado to set.
	 */
	private void setIdtiposeleccionado(String idtiposeleccionado) {
		this.idtiposeleccionado = idtiposeleccionado;
	}

	/**
	 * @return the checkHasHuecos
	 */
	public boolean isCheckHasHuecos() {
		return checkHasHuecos;
	}

	/**
	 * @param checkHasHuecos
	 *            the checkHasHuecos to set
	 */
	public void setCheckHasHuecos(boolean checkHasHuecos) {
		this.checkHasHuecos = checkHasHuecos;
	}

	/**
	 * @return the idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            the idArchivo to set
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return the allowAllFormats
	 */
	public boolean isAllowAllFormats() {
		return allowAllFormats;
	}

	/**
	 * @param allowAllFormats
	 *            the allowAllFormats to set
	 */
	public void setAllowAllFormats(boolean allowAllFormats) {
		this.allowAllFormats = allowAllFormats;
	}

	/**
	 * @return the numHuecosLibres
	 */
	public int getNumHuecosLibres() {
		return numHuecosLibres;
	}

	/**
	 * @param numHuecosLibres
	 *            the numHuecosLibres to set
	 */
	public void setNumHuecosLibres(int numHuecosLibres) {
		this.numHuecosLibres = numHuecosLibres;
	}

	/**
	 * @return the numHuecosNecesarios
	 */
	public int getNumHuecosNecesarios() {
		return numHuecosNecesarios;
	}

	/**
	 * @param numHuecosNecesarios
	 *            the numHuecosNecesarios to set
	 */
	public void setNumHuecosNecesarios(int numHuecosNecesarios) {
		this.numHuecosNecesarios = numHuecosNecesarios;
	}

	/**
	 * @return the todosFormatos
	 */
	public boolean isTodosFormatos() {
		return todosFormatos;
	}

	/**
	 * @param todosFormatos
	 *            the todosFormatos to set
	 */
	public void setTodosFormatos(boolean todosFormatos) {
		this.todosFormatos = todosFormatos;
	}

}
