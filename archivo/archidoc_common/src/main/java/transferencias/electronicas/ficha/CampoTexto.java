package transferencias.electronicas.ficha;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;

public class CampoTexto extends CampoFichaBase {

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_TEXTO;
	}
}
