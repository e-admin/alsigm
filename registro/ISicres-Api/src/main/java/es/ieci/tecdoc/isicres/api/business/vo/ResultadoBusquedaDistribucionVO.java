package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.List;

/**
 * Clase que recoge el resultado de la busqueda de distribuciones, ya sea de
 * entrada o de salida, teniendo en cuenta unos criterios de búsqueda.
 * 
 * @author IECISA
 * 
 */
public class ResultadoBusquedaDistribucionVO {

	public ResultadoBusquedaDistribucionVO(List distributions, int total) {
		this.distributions = distributions;
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public List getDistributions() {
		return distributions;
	}

	// Members
	protected int total;

	protected List distributions;
}
