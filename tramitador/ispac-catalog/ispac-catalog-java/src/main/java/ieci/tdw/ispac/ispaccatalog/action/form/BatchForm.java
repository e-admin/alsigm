package ieci.tdw.ispac.ispaccatalog.action.form;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class BatchForm extends ActionForm  {
	private String[] multibox;
	private String txtFiltro;
	private String itemId;
	
	/**
	 * Reset all properties to their default values.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param request The HTTP Request we are processing.
	*/
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    super.reset(mapping, request);
	    multibox = new String [0];
	    txtFiltro = "";
	}
	
	/**
	 * Validate all properties to their default values.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param request The HTTP Request we are processing.
	 * @return ActionErrors A list of all errors found.
	*/
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	    return super.validate(mapping, request);
	}
	
	public String[] getMultibox() {
	    return multibox;
	}
	public void setMultibox(String[] newMultibox) {
	    multibox = newMultibox;
	}
	
	public String getTxtFiltro() {
	   return txtFiltro;
	}
	public void setTxtFiltro(String txtFiltro) {
	    this.txtFiltro = txtFiltro;
	}
	
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}