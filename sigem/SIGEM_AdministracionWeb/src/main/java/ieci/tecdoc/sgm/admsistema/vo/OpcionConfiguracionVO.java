package ieci.tecdoc.sgm.admsistema.vo;

import java.io.Serializable;

/**
 * Opcion de configuracion de una accion de multientidad
 * @author IECISA
 *
 */
public class OpcionConfiguracionVO implements Serializable{

	/**
	 * @param id
	 * @param label
	 */
	public OpcionConfiguracionVO(String id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	
	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return el label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label el label a fijar
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	private String id;
	private String label;

	private static final long serialVersionUID = -3394691634454203323L;
}
