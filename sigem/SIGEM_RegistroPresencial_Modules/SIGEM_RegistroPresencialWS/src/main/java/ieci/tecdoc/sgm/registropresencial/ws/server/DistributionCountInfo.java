/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena información referente a la busqueda de distribuciones
 * 
 */
public class DistributionCountInfo extends RetornoServicio {

	/**
	 * Número de Distribuciones que coinciden con los criterios de busqueda
	 */
	private Integer count = null;

	/**
	 * Estado de la distribucion
	 * 
	 * @value: 1.-Pendiente
	 * @value: 2.-Aceptador
	 * @value: 3.-Archivado
	 * @value: 4.-Rechazado
	 * @value: 5.-Redistribuido
	 */
	private Integer state = null;

	/**
	 * @value: 0.- Registros de entrada y de salida que son distribuciones de
	 *         entrada
	 * @value: 1.- Registros de entrada que son distribuciones de entrada
	 * @value: 2.- Registros de salida que son distribuciones de entrada
	 */
	private Integer typeBookRegisterDist = null;

	/**
	 * Este atributo indica si se muestran los registros distribuidos a los
	 * departamentos de las oficinas asociadas al usuario. - true: se muestran;
	 * false: no se muestran;
	 */
	private Boolean oficAsoc = null;

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the typeBookRegisterDist
	 */
	public Integer getTypeBookRegisterDist() {
		return typeBookRegisterDist;
	}

	/**
	 * @param typeBookRegisterDist
	 *            the typeBookRegisterDist to set
	 */
	public void setTypeBookRegisterDist(Integer typeBookRegisterDist) {
		this.typeBookRegisterDist = typeBookRegisterDist;
	}

	/**
	 * @return the oficAsoc
	 */
	public Boolean getOficAsoc() {
		return oficAsoc;
	}

	/**
	 * @param oficAsoc
	 *            the oficAsoc to set
	 */
	public void setOficAsoc(Boolean oficAsoc) {
		this.oficAsoc = oficAsoc;
	}

}
