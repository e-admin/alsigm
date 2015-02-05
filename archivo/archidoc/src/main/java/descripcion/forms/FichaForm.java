package descripcion.forms;

import common.forms.CustomForm;

/**
 * Formulario para la información de una ficha
 */
public class FichaForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String contextPath = null;
	private String idFormato = null;
	private String idFicha = null;
	private String tipoAcceso = null;
	private int mantenerInformacion = 0;

	/**
	 * Constructor.
	 */
	public FichaForm() {
		super();
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath
	 *            el contextPath a establecer
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * @return el idFormato
	 */
	public String getIdFormato() {
		return idFormato;
	}

	/**
	 * @param idFormato
	 *            el idFormato a establecer
	 */
	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	/**
	 * @return el idFicha
	 */
	public String getIdFicha() {
		return idFicha;
	}

	/**
	 * @param idFicha
	 *            el idFicha a establecer
	 */
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return el tipoAcceso
	 */
	public String getTipoAcceso() {
		return tipoAcceso;
	}

	/**
	 * @param tipoAcceso
	 *            el tipoAcceso a establecer
	 */
	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public String getMantenerInformacion() {
		return String.valueOf(mantenerInformacion);
	}

	public int getIntMantenerInformacion() {
		return mantenerInformacion;
	}

	public void setMantenerInformacion(String mantenerInformacion) {
		try {
			this.mantenerInformacion = Integer.parseInt(mantenerInformacion);
		} catch (Exception e) {
			this.mantenerInformacion = 0;
		}
	}

	public void setMantenerInformacion(int mantenerInformacion) {
		this.mantenerInformacion = mantenerInformacion;
	}
}
