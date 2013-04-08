package deposito.actions.navegador;

import common.bi.ServiceRepository;
import common.view.POUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoVO;
import deposito.vos.OcupacionElementoDeposito;

public class EspacioEnElemento extends ElementoVO {

	boolean isAsignable = false;
	ServiceRepository services = null;
	GestorEstructuraDepositoBI depositoBI = null;
	String idFormato = null;
	int huecosLibres = -1;
	boolean mostrarLink = false;
	boolean mostrarRadio = false;

	public EspacioEnElemento(ElementoVO elementoDeposito,
			ServiceRepository services, String idFormato, boolean checkHasHuecos) {
		POUtils.copyVOProperties(this, elementoDeposito);
		this.isAsignable = elementoDeposito.isAsignable();
		this.idFormato = idFormato;
		this.services = services;

		if (this.isAsignable) {
			if (!checkHasHuecos || (checkHasHuecos && getHuecosLibres() > 0)) {
				this.mostrarRadio = true;
			}
		} else {
			if (!checkHasHuecos || (checkHasHuecos && getHuecosLibres() > 0)) {
				this.mostrarLink = true;
			}
		}
	}

	public int getHuecosLibres() {
		if (huecosLibres < 0) {
			// huecosLibres = getDepositoBI().countHuecosLibres(getId(),
			// getIdTipoElemento(), getId(), getIdTipoElemento(), idFormato);
			OcupacionElementoDeposito ocupacion = getDepositoBI()
					.getDatosOcupacion(getId(), getIdTipoElemento(), idFormato);
			huecosLibres = (ocupacion != null ? ocupacion.getHuecosLibres() : 0);
		}
		return huecosLibres;
	}

	public boolean isAsignable() {
		return isAsignable;
	}

	GestorEstructuraDepositoBI getDepositoBI() {
		if (depositoBI == null)
			depositoBI = services.lookupGestorEstructuraDepositoBI();
		return depositoBI;
	}

	/**
	 * @return the mostrarLink
	 */
	public boolean isMostrarLink() {
		return mostrarLink;
	}

	/**
	 * @return the mostrarRadio
	 */
	public boolean isMostrarRadio() {
		return mostrarRadio;
	}

}