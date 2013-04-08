package deposito.forms;

import common.forms.CustomForm;

import deposito.global.Constants;

public class NavegadorReservaDepositoForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idseleccionadoinicial;
	String idtiposeleccionadoinicial;
	String seleccionadoinicial;
	String iddepositoseleccionado;
	String idtipodepositoseleccionado;
	String depositoseleccionado;
	String idtipopadre;
	String idpadre;
	String idseleccionado;
	String idtiposeleccionado;
	String seleccionado;
	String method;
	String root;
	String idroot;
	String idtiporoot;
	String filterByIdformato;
	String idTipoLastLevel;
	String hideHuecosLibres;
	String allowSelectAnyElement;
	int numHuecosLibres = 0;
	int numHuecosNecesarios = 0;
	String existeReserva;
	String recorrerDepositos;
	boolean checkHasHuecos = false;

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
		} else {
			setIdroot(null);
			setIdtiporoot(null);
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
		if (seleccionado != null && seleccionado.length() > 0) {
			String ids[] = seleccionado.split(Constants.DELIMITER_IDS);
			setIdseleccionado(ids[0].trim());
			setIdtiposeleccionado(ids[1].trim());
		} else {
			setIdseleccionado(null);
			setIdtiposeleccionado(null);
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
	 * @return el allowSelectAnyElement
	 */
	public String getAllowSelectAnyElement() {
		return allowSelectAnyElement;
	}

	/**
	 * @param allowSelectAnyElement
	 *            el allowSelectAnyElement a establecer
	 */
	public void setAllowSelectAnyElement(String allowSelectAnyElement) {
		this.allowSelectAnyElement = allowSelectAnyElement;
	}

	/**
	 * @return el numHuecosLibres
	 */
	public int getNumHuecosLibres() {
		return numHuecosLibres;
	}

	/**
	 * @param numHuecosLibres
	 *            el numHuecosLibres a establecer
	 */
	public void setNumHuecosLibres(int numHuecosLibres) {
		this.numHuecosLibres = numHuecosLibres;
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
	 * @return el numHuecosNecesarios
	 */
	public int getNumHuecosNecesarios() {
		return numHuecosNecesarios;
	}

	/**
	 * @param numHuecosNecesarios
	 *            el numHuecosNecesarios a establecer
	 */
	public void setNumHuecosNecesarios(int numHuecosNecesarios) {
		this.numHuecosNecesarios = numHuecosNecesarios;
	}

	/**
	 * @return el depositoseleccionado
	 */
	public String getDepositoseleccionado() {
		return depositoseleccionado;
	}

	/**
	 * @param depositoseleccionado
	 *            el depositoseleccionado a establecer
	 */
	public void setDepositoseleccionado(String depositoseleccionado) {
		// el valor seleccionado esta compuesto de id+idtipo, para poder
		// identificarlos
		// de manera unica
		this.depositoseleccionado = depositoseleccionado;
		if (depositoseleccionado != null && depositoseleccionado.length() > 0) {
			String ids[] = depositoseleccionado.split(Constants.DELIMITER_IDS);
			setIddepositoseleccionado(ids[0].trim());
			setIdtipodepositoseleccionado(ids[1].trim());
		} else {
			setIddepositoseleccionado(null);
			setIdtipodepositoseleccionado(null);
		}
	}

	/**
	 * @return el iddepositoseleccionado
	 */
	public String getIddepositoseleccionado() {
		return iddepositoseleccionado;
	}

	/**
	 * @param iddepositoseleccionado
	 *            el iddepositoseleccionado a establecer
	 */
	public void setIddepositoseleccionado(String iddepositoseleccionado) {
		this.iddepositoseleccionado = iddepositoseleccionado;
	}

	/**
	 * @return el idtipodepositoseleccionado
	 */
	public String getIdtipodepositoseleccionado() {
		return idtipodepositoseleccionado;
	}

	/**
	 * @param idtipodepositoseleccionado
	 *            el idtipodepositoseleccionado a establecer
	 */
	public void setIdtipodepositoseleccionado(String idtipodepositoseleccionado) {
		this.idtipodepositoseleccionado = idtipodepositoseleccionado;
	}

	/**
	 * @return el existeReserva
	 */
	public String getExisteReserva() {
		return existeReserva;
	}

	/**
	 * @param existeReserva
	 *            el existeReserva a establecer
	 */
	public void setExisteReserva(String existeReserva) {
		this.existeReserva = existeReserva;
	}

	/**
	 * @return el recorrerDepositos
	 */
	public String getRecorrerDepositos() {
		return recorrerDepositos;
	}

	/**
	 * @param recorrerDepositos
	 *            el recorrerDepositos a establecer
	 */
	public void setRecorrerDepositos(String recorrerDepositos) {
		this.recorrerDepositos = recorrerDepositos;
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

}
