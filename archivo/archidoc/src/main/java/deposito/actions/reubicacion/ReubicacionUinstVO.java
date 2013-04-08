package deposito.actions.reubicacion;

import common.vos.BaseVO;

import deposito.vos.HuecoVO;

public class ReubicacionUinstVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	HuecoVO huecoOrigen = null;
	HuecoVO huecoDestino = null;

	public ReubicacionUinstVO(HuecoVO _huecoOrigen, HuecoVO _huecoDestino) {
		this.huecoOrigen = _huecoOrigen;
		this.huecoDestino = _huecoDestino;
	}

	public HuecoVO getHuecoOrigen() {
		return huecoOrigen;
	}

	public void setHuecoOrigen(HuecoVO huecoOrigen) {
		this.huecoOrigen = huecoOrigen;
	}

	public HuecoVO getHuecoDestino() {
		return huecoDestino;
	}

	public void setHuecoDestino(HuecoVO huecoDestino) {
		this.huecoDestino = huecoDestino;
	}
}
