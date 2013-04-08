package deposito.actions.hueco;

import common.Constants;
import common.bi.ServiceRepository;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HuecoVO;
import deposito.vos.UInsDepositoVO;

public class UInsDepositoPO extends UInsDepositoVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	ServiceRepository services = null;

	String huecoID = null;

	/**
	 * Indicador de si la Unidad Instalación Tiene un formato regular o
	 * irregular
	 */
	Boolean multiDoc = null;

	UInsDepositoPO(ServiceRepository services) {
		super();
		this.services = services;
	}

	public boolean isMultiDoc() {
		if (multiDoc == null) {
			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();
			FormatoHuecoVO formatoHuecoVO = depositoBI.getFormatoHueco(this
					.getIdformato());

			multiDoc = new Boolean(false);

			if (formatoHuecoVO != null) {
				if (formatoHuecoVO.isMultidoc()) {
					multiDoc = new Boolean(true);
				}
			}
		}
		return multiDoc.booleanValue();
	}

	public String getHuecoID() {
		if (huecoID == null) {
			GestorEstructuraDepositoBI depositoBI = services
					.lookupGestorEstructuraDepositoBI();
			HuecoVO huecoVO = depositoBI.getHuecoUInstalacion(this.getId());

			if (huecoVO != null) {
				huecoID = huecoVO.getIdElemAPadre() + Constants.DELIMITER_IDS
						+ huecoVO.getNumorden();
			}
		}
		return huecoID;
	}
}
