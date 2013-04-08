package ieci.tecdoc.isicres.rpadmin.struts.forms;

import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

public abstract class RPAdminActionForm extends RPAdminBaseForm {

	private static final long serialVersionUID = 1L;

	private Integer cambios = null;

	public void setCambios(Integer cambios) {
		this.cambios = cambios;
	}

	public Integer getCambios() {
		return cambios;
	}

	public void change() {
		this.cambios = new Integer(Utils.getIntValue(true));
	}

}
