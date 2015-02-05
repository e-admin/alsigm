package deposito.db;

import common.exceptions.CheckedArchivoException;

import deposito.vos.HuecoVO;

public class ConcurrentModificationException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	HuecoVO hueco = null;

	public ConcurrentModificationException(HuecoVO hueco) {
		super();
		this.hueco = hueco;
	}

	public HuecoVO getHueco() {
		return hueco;
	}

	public void setHueco(HuecoVO hueco) {
		this.hueco = hueco;
	}
}