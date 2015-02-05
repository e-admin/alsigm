package deposito.actions;

import common.bi.ServiceRepository;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.OcupacionElementoDeposito;

/**
 * Datos de presentación de los elementos del fondo físico de tipo 'Depósito'
 */
public class EspacioEnDepositoPO {

	OcupacionElementoDeposito ocupacionDeposito = null;
	ElementoNoAsignableVO deposito = null;
	String formato = null;

	ServiceRepository services = null;
	transient GestorEstructuraDepositoBI depositoBI = null;

	public EspacioEnDepositoPO(ServiceRepository services,
			ElementoNoAsignableVO deposito, String formato) {
		this.services = services;
		this.deposito = deposito;
		this.formato = formato;
	}

	public OcupacionElementoDeposito getOcupacionDeposito() {
		if (ocupacionDeposito == null)
			ocupacionDeposito = getDepositoBI().getDatosOcupacion(
					deposito.getId(), deposito.getIdTipoElemento(), formato);
		return ocupacionDeposito;
	}

	public String getNombre() {
		return deposito.getNombre();
	}

	public String getId() {
		return deposito.getId();
	}

	public String getTipoElemento() {
		return deposito.getIdTipoElemento();
	}

	public int getNumeroHuecos() {
		return getOcupacionDeposito().getNumeroHuecos();
	}

	public int getHuecosLibres() {
		return getOcupacionDeposito().getHuecosLibres();
	}

	private GestorEstructuraDepositoBI getDepositoBI() {
		if (depositoBI == null)
			depositoBI = services.lookupGestorEstructuraDepositoBI();
		return depositoBI;
	}
}