package ieci.tdw.ispac.ispacmgr.action.form;

import org.apache.struts.action.ActionForm;

/**
 * ActionForm para el asistente de selección de productores.
 * 
 */
public class ProducersWizardForm extends ActionForm {

	private static final long serialVersionUID = -1703908170171630780L;

	/**
	 * Identificador del productor seleccionado al finalizar el asistente.
	 */
	private String finalId = null;

	/**
	 * Identificador del productor seleccionado para la navegación.
	 */
	private String navigationId = null;

	/**
	 * Constructor.
	 */
	public ProducersWizardForm() {
		super();
	}

	public String getFinalId() {
		return finalId;
	}

	public void setFinalId(String finalId) {
		this.finalId = finalId;
	}

	public String getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(String navigationId) {
		this.navigationId = navigationId;
	}

}