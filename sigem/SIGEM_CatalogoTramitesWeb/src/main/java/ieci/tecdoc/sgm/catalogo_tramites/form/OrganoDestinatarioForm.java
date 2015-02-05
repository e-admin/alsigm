package ieci.tecdoc.sgm.catalogo_tramites.form;

import org.apache.struts.action.ActionForm;

public class OrganoDestinatarioForm extends ActionForm {
  private String code;
    private String description;
    private String userAction;
    private String newCode;
    private String newDescription;
    
    private boolean importSelectedDept;
    
    public OrganoDestinatarioForm(){
        this.description = "";
        this.code = "";
        this.userAction = "";
        this.newCode = "";
        this.newDescription = "";
        this.importSelectedDept = false;
    }
    
    public OrganoDestinatarioForm(String description, String code) {
        this.description = description;
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
       
   public String getUserAction() {
        return userAction;
   }
   public void setUserAction(String userAction) {
        this.userAction = userAction;
   }
   
   public String getNewCode() {
      return newCode;
   }  
   public void setNewCode(String newCode) {
      this.newCode = newCode;
   }
  
   public String getNewDescription() {
      return newDescription;
   }
   public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
   }

   public boolean isImportSelectedDept() {
	   return importSelectedDept;
   }
   public void setImportSelectedDept(boolean importSelectedDept) {
	   this.importSelectedDept = importSelectedDept;
   }

   private static final long serialVersionUID = 1L;
}