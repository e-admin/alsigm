package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.List;

import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;

/**
 * Clase base de los vos de intercambio registral
 * 
 */
public abstract class BaseIntercambioRegistralVO {

	private List<TrazabilidadVO> trazas;

	/**
	 * @return el trazas
	 */
	public List<TrazabilidadVO> getTrazas() {
		return trazas;
	}

	/**
	 * @param trazas el trazas a fijar
	 */
	public void setTrazas(List<TrazabilidadVO> trazas) {
		this.trazas = trazas;
	}
	
	

}
