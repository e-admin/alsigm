package transferencias.electronicas.ficha;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;


public class CampoDescriptor extends CampoFichaBase{
	private String idLista;

	/**
	 * @return el idLista
	 */
	public String getIdLista() {
		return idLista;
	}
	/**
	 * @param idLista el idLista a fijar
	 */
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_DESCRIPTOR;
	}

}
