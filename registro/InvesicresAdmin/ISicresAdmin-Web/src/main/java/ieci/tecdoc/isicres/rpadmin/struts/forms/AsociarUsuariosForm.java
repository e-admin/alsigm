package ieci.tecdoc.isicres.rpadmin.struts.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class AsociarUsuariosForm extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;
	private String[] idsUser;
	
	public String[] getIdsUser() {
		return idsUser;
	}
	
	public void setIdsUser(String[] idsUser) {
		this.idsUser = idsUser;
	}	
}