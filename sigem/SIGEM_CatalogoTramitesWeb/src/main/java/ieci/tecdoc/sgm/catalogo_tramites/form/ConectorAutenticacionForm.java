
package ieci.tecdoc.sgm.catalogo_tramites.form;

import org.apache.struts.action.ActionForm;

/**
 * @author X72435FE
 *
 * TODO Para cambiar la plantilla de este comentario generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
public class ConectorAutenticacionForm extends ActionForm {
   private String hookId;
   private String hookDescription;
   private String selHookId;
   private String procedureId;
   private String procedureDescription;
   private String selProcedureId;
   private String userAction;
   private String oldHookId;
   
   public static final int PROCEDURE = 0;
   public static final int HOOK = 1;
   
   public ConectorAutenticacionForm(){
	   	  this.hookId = "";
	   	  this.hookDescription = "";
	   	  this.selHookId = "";
	   	  this.procedureId = "";
	   	  this.procedureDescription = "";
	   	  this.selProcedureId = "";
	   	  this.oldHookId = "";
   }
   
   public ConectorAutenticacionForm (String id, String description, int type){
	   if (type == PROCEDURE){
		   this.procedureId = id;
		   this.procedureDescription = description;
	   }else{
		   this.hookId = id;
		   this.hookDescription = description;
	   }
   }
   
   public String getProcedureId() {
   	  return procedureId;
   }
   
   public String getSelProcedureId() {
	  return selProcedureId;
   }
   
   public void setProcedureId(String procedureId) {
	  this.procedureId = procedureId;
   }
   
   public void setSelProcedureId(String selProcedureId) {
	  this.selProcedureId = selProcedureId;
   }
   
   public String getProcedureDescription() {
   	  return procedureDescription;
   }
   
   public void setProcedureDescription(String procedureDescription) {
	  this.procedureDescription = procedureDescription;
   }

   public String getHookId() {
	  return hookId;
   }

   public String getSelHookId() {
	  return selHookId;
   }
   
   public void setHookId(String hookId) {
   	  this.hookId = hookId;
   }
   
   public void setSelHookId(String selHookId) {
   	  this.selHookId = selHookId;
   }
   
   public String getHookDescription() {
	  return hookDescription;
   }
   
   public void setHookDescription(String hookDescription) {
   	  this.hookDescription = hookDescription;
   }
   
   public String getUserAction() {
	   return userAction;
   }

   public void setUserAction(String userAction) {
	   this.userAction = userAction;
   }

   public String getOldHookId() {
	   return oldHookId;
   }

   public void setOldHookId(String oldHookId) {
	   this.oldHookId = oldHookId;
   }
   
   private static final long serialVersionUID = 1L;
}
