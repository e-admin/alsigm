package fondos.actions;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.HuecoVO;
import deposito.vos.UDocEnUiDepositoVO;

/**
 * Datos de presentación de una signatura de unidad documental. Pueden existir
 * varias signaturas para una misma unidad documental bien porque la unidad
 * documental tenga un tamaño que obligue a dividirla en varias unidades de
 * instalación o bien porque parte de la documentación haya sido incoporada
 * mediante una ampliación de documentación.
 */
public class SignaturaUdocPO extends UDocEnUiDepositoVO {
	GestorEstructuraDepositoBI depositoBI = null;

	SignaturaUdocPO(GestorEstructuraDepositoBI depositoBI) {
		this.depositoBI = depositoBI;
	}

	public HuecoVO getUbicacionFisica() {
		HuecoVO huecoUdoc = null;
		try {
			huecoUdoc = depositoBI.getHuecoUInstalacion(getIduinstalacion());
		} catch (Exception e) {
		}
		return huecoUdoc;
	}
}