/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 * 
 *          Clase que contiene los datos del registro original
 * 
 */
public class RegistroOriginalVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -909669128949550753L;

	/**
	 * Es el numero del registro original
	 */
	private String numeroRegistroOriginal;

	/**
	 * Es el tipo del registro Original
	 * 
	 * 1.- Entrada; 2.- Salida
	 */
	private String tipoRegistroOriginal;

	/**
	 * Fecha de registro del registro original
	 */
	private Date fechaRegistroOriginal;

	/**
	 * Unidad Administrativa del tipo Entidad Registral del registro original
	 */
	private UnidadAdministrativaVO entidadRegistral;

	/**
	 * @return el numeroRegistroOriginal
	 */
	public String getNumeroRegistroOriginal() {
		return numeroRegistroOriginal;
	}

	/**
	 * @param numeroRegistroOriginal
	 *            el numeroRegistroOriginal a fijar
	 */
	public void setNumeroRegistroOriginal(String numeroRegistroOriginal) {
		this.numeroRegistroOriginal = numeroRegistroOriginal;
	}

	/**
	 * @return el tipoRegistroOriginal
	 */
	public String getTipoRegistroOriginal() {
		return tipoRegistroOriginal;
	}

	/**
	 * @param tipoRegistroOriginal
	 *            el tipoRegistroOriginal a fijar
	 */
	public void setTipoRegistroOriginal(String tipoRegistroOriginal) {
		this.tipoRegistroOriginal = tipoRegistroOriginal;
	}

	/**
	 * @return el fechaRegistroOriginal
	 */
	public Date getFechaRegistroOriginal() {
		return fechaRegistroOriginal;
	}

	/**
	 * @param fechaRegistroOriginal
	 *            el fechaRegistroOriginal a fijar
	 */
	public void setFechaRegistroOriginal(Date fechaRegistroOriginal) {
		this.fechaRegistroOriginal = fechaRegistroOriginal;
	}

	/**
	 * @return el entidadRegistral
	 */
	public UnidadAdministrativaVO getEntidadRegistral() {
		if (null == entidadRegistral) {
			entidadRegistral = new UnidadAdministrativaVO();
		}
		return entidadRegistral;
	}

	/**
	 * @param entidadRegistral
	 *            el entidadRegistral a fijar
	 */
	public void setEntidadRegistral(UnidadAdministrativaVO entidadRegistral) {
		this.entidadRegistral = entidadRegistral;
	}

}
