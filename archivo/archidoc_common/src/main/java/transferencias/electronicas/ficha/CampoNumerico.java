package transferencias.electronicas.ficha;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;

public class CampoNumerico extends CampoFichaBase {
	private String tipoMedida;

	private String unidadMedida;

	/**
	 * @return el tipoMedida
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida el tipoMedida a fijar
	 */
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return el unidadMedida
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida el unidadMedida a fijar
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_NUMERICO;
	}


}
