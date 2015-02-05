/*
 * Created on 28-sep-2005
 *
 */
package deposito.forms;

import es.archigest.framework.web.action.ArchigestActionForm;

public class VerHuecoForm extends ArchigestActionForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idHueco;

	public String getIdHueco() {
		return this.idHueco;
	}

	public void setIdHueco(String idHueco) {
		this.idHueco = idHueco;
	}
}
