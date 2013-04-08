package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena una lista de distribuciones de registro
 * 
 */
public class DistributionsInfo extends RetornoServicio {

	/**
	 * Lista de distribuciones de registro
	 */
	private DistributionInfo[] distributions;

	public DistributionInfo[] getDistributions() {
		return distributions;
	}

	public void setDistributions(DistributionInfo[] distributions) {
		this.distributions = distributions;
	}

}
