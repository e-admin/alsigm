/*
 * Created on 09-jun-2005
 *
 */
package deposito.model;

import transferencias.vos.IUnidadInstalacionVO;
import deposito.vos.HuecoVO;

/**
 * @author ABELRL
 * 
 */
public class DetalleUbicacion {
	HuecoVO hueco;
	IUnidadInstalacionVO unidadInstalacion;

	public HuecoVO getHueco() {
		return this.hueco;
	}

	public void setHueco(HuecoVO hueco) {
		this.hueco = hueco;
	}

	public IUnidadInstalacionVO getUnidadInstalacion() {
		return this.unidadInstalacion;
	}

	public void setUnidadInstalacion(IUnidadInstalacionVO unidadInstalacion) {
		this.unidadInstalacion = unidadInstalacion;
	}
}
