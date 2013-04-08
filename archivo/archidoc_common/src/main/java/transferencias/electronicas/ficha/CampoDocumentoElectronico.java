package transferencias.electronicas.ficha;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;
import transferencias.electronicas.documentos.Almacenamiento;
import transferencias.electronicas.documentos.Ubicacion;

public class CampoDocumentoElectronico extends CampoFichaBase{
	/**
	 *
	 */
	private static final long serialVersionUID = 3804757073582069450L;
	private Ubicacion ubicacion;
	private Almacenamiento almacenamiento;

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_DOCUMENTO_ELECTRONICO;
	}

	/**
	 * @return el ubicacion
	 */
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	/**
	 * @param ubicacion el ubicacion a fijar
	 */
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	/**
	 * @return el almacenamiento
	 */
	public Almacenamiento getAlmacenamiento() {
		return almacenamiento;
	}
	/**
	 * @param almacenamiento el almacenamiento a fijar
	 */
	public void setAlmacenamiento(Almacenamiento almacenamiento) {
		this.almacenamiento = almacenamiento;
	}
}
