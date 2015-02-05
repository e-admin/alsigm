package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * 
 * Alamacena una lista de oficinas
 * 
 */
public class OfficesInfo extends RetornoServicio {

	/**
	 * Conjunto de oficinas
	 */
	private OfficeInfo[] officesInfo;

	/**
	 * @return the officesInfo
	 */
	public OfficeInfo[] getOfficesInfo() {
		return officesInfo;
	}

	/**
	 * @param officesInfo
	 *            the officesInfo to set
	 */
	public void setOfficesInfo(OfficeInfo[] officesInfo) {
		this.officesInfo = officesInfo;
	}

}
