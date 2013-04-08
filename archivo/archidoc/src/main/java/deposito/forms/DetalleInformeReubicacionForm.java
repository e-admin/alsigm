/*
 * Created on 20-ene-2005
 *
 */
package deposito.forms;

import deposito.vos.HuecoID;

/**
 * @author ABELRL
 * 
 */
public class DetalleInformeReubicacionForm {
	HuecoID idHuecoOrigen;
	HuecoID idHuecoDestino;
	String pathorigen;
	String pathdestino;
	String uinsorigen;
	String signaturauinsorigen;

	public String getSignaturauinsorigen() {
		return this.signaturauinsorigen;
	}

	public void setSignaturauinsorigen(String signaturauinsorigen) {
		this.signaturauinsorigen = signaturauinsorigen;
	}

	/**
	 * @return Returns the idHuecoDestino.
	 */
	public HuecoID getIdHuecoDestino() {
		return idHuecoDestino;
	}

	/**
	 * @param idHuecoDestino
	 *            The idHuecoDestino to set.
	 */
	public void setIdHuecoDestino(HuecoID idHuecoDestino) {
		this.idHuecoDestino = idHuecoDestino;
	}

	/**
	 * @return Returns the idHuecoOrigen.
	 */
	public HuecoID getIdHuecoOrigen() {
		return idHuecoOrigen;
	}

	/**
	 * @param idHuecoOrigen
	 *            The idHuecoOrigen to set.
	 */
	public void setIdHuecoOrigen(HuecoID idHuecoOrigen) {
		this.idHuecoOrigen = idHuecoOrigen;
	}

	/**
	 * @return Returns the pathdestino.
	 */
	public String getPathdestino() {
		return pathdestino;
	}

	/**
	 * @param pathdestino
	 *            The pathdestino to set.
	 */
	public void setPathdestino(String pathdestino) {
		this.pathdestino = pathdestino;
	}

	/**
	 * @return Returns the pathorigen.
	 */
	public String getPathorigen() {
		return pathorigen;
	}

	/**
	 * @param pathorigen
	 *            The pathorigen to set.
	 */
	public void setPathorigen(String pathorigen) {
		this.pathorigen = pathorigen;
	}

	/**
	 * @return Returns the uinsdestino.
	 */
	// public String getUinsdestino() {
	// return uinsdestino;
	// }
	/**
	 * @param uinsdestino
	 *            The uinsdestino to set.
	 */
	// public void setUinsdestino(String uinsdestino) {
	// this.uinsdestino = uinsdestino;
	// }
	/**
	 * @return Returns the uinsorigen.
	 */
	public String getUinsorigen() {
		return uinsorigen;
	}

	/**
	 * @param uinsorigen
	 *            The uinsorigen to set.
	 */
	public void setUinsorigen(String uinsorigen) {
		this.uinsorigen = uinsorigen;
	}
}
