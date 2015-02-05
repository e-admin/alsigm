package ieci.tecdoc.sgm.backoffice.form;

import ieci.tecdoc.sgm.backoffice.utils.Utilidades;

import java.util.Locale;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class CambioClaveForm extends ActionForm {
	
	private final static long serialVersionUID = 0;
	
	private String username = null;
	private String currentPassword = null;
	private String newPassword = null;
	private String newPasswordConfirm = null;
	private String idEntidad = null;
	private String url = null;
	

	public CambioClaveForm() {
		super();
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getCurrentPassword() {
		return currentPassword;
	}


	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}


	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	
	public String getIdEntidad() {
		return idEntidad;
	}


	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public ActionErrors validate(MessageResources resources, Locale locale) {
		ActionErrors errors = new ActionErrors();

    	if (Utilidades.isNuloOVacio(username)) {
    		errors.add(ActionErrors.GLOBAL_MESSAGE, 
    				new ActionError("errors.required", resources.getMessage(locale, "cambioClave.username")));
    		return errors;
    	}

    	if (Utilidades.isNuloOVacio(currentPassword)) {
    		errors.add(ActionErrors.GLOBAL_MESSAGE, 
    				new ActionError("errors.required", resources.getMessage(locale, "cambioClave.currentPassword")));
    		return errors;
    	}

    	if (Utilidades.isNuloOVacio(newPassword)) {
    		errors.add(ActionErrors.GLOBAL_MESSAGE, 
    				new ActionError("errors.required", resources.getMessage(locale, "cambioClave.newPassword")));
    		return errors;
    	}

    	if (Utilidades.isNuloOVacio(newPasswordConfirm)) {
    		errors.add(ActionErrors.GLOBAL_MESSAGE, 
    				new ActionError("errors.required", resources.getMessage(locale, "cambioClave.newPasswordConfirm")));
    		return errors;
    	}

    	if (!newPasswordConfirm.equals(newPassword)) {
    		errors.add(ActionErrors.GLOBAL_MESSAGE, 
    				new ActionError("cambioClave.error.clavesNoCoinciden"));
    		return errors;
    	}

    	return errors;
	}

}
