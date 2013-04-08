package ieci.tdw.ispac.ispacmgr.action.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadForm extends ActionForm {
	
	private FormFile theFile;
	private String theFileName;
	
	/**
	 * @return Returns the theFile.
	 */
	public FormFile getTheFile() {
		return theFile;
	}
	/**
	 * @param theFile The theFile to set.
	 */
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	
	/**
	 * @return Returns the theFileName.
	 */
	public String getTheFileName() {
		return theFileName;
	}
	/**
	 * @param theFileName The theFileName to set.
	 */
	public void setTheFileName(String theFileName) {
		this.theFileName = theFileName;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		
		return errors;
	}
	
}