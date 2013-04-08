package valoracion.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Clase que encapusla los datos de la información de boletin de una valoracion
 * de serie
 */
public class BoletinInfoVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaDictamen = null;
	private int valorDictamen = 0;
	private Date fechaResolucionDictamen = null;
	private String boletinDictamen = null;
	private Date fechaBoletinDictamen = null;

	public String getBoletinDictamen() {
		return boletinDictamen;
	}

	public void setBoletinDictamen(String boletinDictamen) {
		this.boletinDictamen = boletinDictamen;
	}

	public Date getFechaBoletinDictamen() {
		return fechaBoletinDictamen;
	}

	public void setFechaBoletinDictamen(Date fechaBoletinDictamen) {
		this.fechaBoletinDictamen = fechaBoletinDictamen;
	}

	public Date getFechaDictamen() {
		return fechaDictamen;
	}

	public void setFechaDictamen(Date fechaDictamen) {
		this.fechaDictamen = fechaDictamen;
	}

	public Date getFechaResolucionDictamen() {
		return fechaResolucionDictamen;
	}

	public void setFechaResolucionDictamen(Date fechaResolucionDictamen) {
		this.fechaResolucionDictamen = fechaResolucionDictamen;
	}

	public int getValorDictamen() {
		return valorDictamen;
	}

	public void setValorDictamen(int valorDictamen) {
		this.valorDictamen = valorDictamen;
	}
}
