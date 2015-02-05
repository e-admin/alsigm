package deposito.actions.asignable;

import common.bi.ServiceRepository;
import common.util.DoubleUtils;
import common.view.POUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.FormatoHuecoVO;

public class ElementoAsignablePO extends ElementoAsignableVO {

	ServiceRepository services = null;

	FormatoHuecoVO formato = null;
	transient GestorEstructuraDepositoBI depositoBI = null;

	public ElementoAsignablePO(ElementoAsignableVO asignableVO,
			ServiceRepository services) {
		this.services = services;
		POUtils.copyVOProperties(this, asignableVO);
	}

	public FormatoHuecoVO getFormato() {
		if (formato == null)
			formato = getDepositoBI().getFormatoHueco(getIdFormato());
		return formato;
	}

	public double getEspacioSobrante() {
		double longitudSobrante = 0;
		FormatoHuecoVO formatoHueco = getFormato();
		if (formatoHueco != null && formatoHueco.isRegular())
			longitudSobrante = longitud - getNumhuecos()
					* formatoHueco.getLongitud().doubleValue();

		longitudSobrante = DoubleUtils.round(longitudSobrante, 2);
		return longitudSobrante;
	}

	private GestorEstructuraDepositoBI getDepositoBI() {
		if (depositoBI == null)
			depositoBI = services.lookupGestorEstructuraDepositoBI();
		return depositoBI;
	}

	public String getMenorNumeracionHueco() {

		if (depositoBI.getNumHuecosNumericos(this.getId()) == 0) {
			return getDepositoBI().getMenorNumeracionOrdenHueco(this.getId());
		} else {
			return String.valueOf(getDepositoBI().getMenorNumeracionHueco(
					this.getId()));
		}
	}

	public String getMayorNumeracionHueco() {

		if (depositoBI.getNumHuecosNumericos(this.getId()) == 0) {
			return getDepositoBI().getMayorNumeracionOrdenHueco(this.getId());
		} else {
			return String.valueOf(getDepositoBI().getMayorNumeracionHueco(
					this.getId()));
		}
	}

	public boolean isSignaturacionAHueco() {
		return getDepositoBI().isEditableNumeracion(this.getIddeposito());
	}
}