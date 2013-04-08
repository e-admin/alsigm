package transferencias.forms;

import org.apache.log4j.Logger;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos durante la aceptacion o rechazo de
 * previsiones de transferecia
 */
public class RecepcionPrevisionesForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Logger de la clase */
	protected final static Logger logger = Logger
			.getLogger(RecepcionPrevisionesForm.class);

	// SimpleDateFormat dateFormat = new
	// SimpleDateFormat(Constants.FORMATO_FECHA);

	String idPrevision;
	String fechainitrans;
	String fechafintrans;
	String motivorechazo;
	String methodToPerform;
	String mes;
	String idArchivo;

	public String getMotivorechazo() {
		return motivorechazo;
	}

	public void setMotivorechazo(String observaciones) {
		this.motivorechazo = observaciones;
	}

	public String getIdPrevision() {
		return idPrevision;
	}

	public void setIdPrevision(String idPrevision) {
		this.idPrevision = idPrevision;
	}

	public String getFechafintrans() {
		return fechafintrans;
	}

	public void setFechafintrans(String fechafintrans) {
		this.fechafintrans = fechafintrans;
	}

	public String getFechainitrans() {
		return fechainitrans;
	}

	public void setFechainitrans(String fechainitrans) {
		this.fechainitrans = fechainitrans;
	}

	public String getMethodToPerform() {
		return methodToPerform;
	}

	public void setMethodToPerform(String methodToPerform) {
		this.methodToPerform = methodToPerform;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

}