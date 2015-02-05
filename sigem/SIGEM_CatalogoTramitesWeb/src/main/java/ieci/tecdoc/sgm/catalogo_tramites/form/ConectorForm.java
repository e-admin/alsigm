
package ieci.tecdoc.sgm.catalogo_tramites.form;

import org.apache.struts.action.ActionForm;

/**
 * @author X72435FE
 *
 * TODO Para cambiar la plantilla de este comentario generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
public class ConectorForm extends ActionForm{
   private String identifier;
   private String description;
   private String implementClass;
   private int typeId;
   private String typeDescription;
   private String info;
   private String userAction;
   private String hookId;
   private String hookDesc;
   
   public ConectorForm(){
   	  this.identifier = "";
   	  this.description="";
   	  this.implementClass="";
   	  this.info="";
   	  this.typeId=0;
   	  this.hookId="";
   	  this.hookDesc="";
   }
   
   public ConectorForm(String hookId, String hookDesc){
 	  this.hookId = hookId;
 	  this.hookDesc = hookDesc;
   }
   
   public ConectorForm(int typeId, String typeDescription){
   	  this.typeId = typeId;
   	  this.typeDescription = typeDescription;
   }
   
   public String getDescription() {
	  return description;
   }
   public void setDescription(String description) {
	  this.description = description;
   }
   
   public String getIdentifier() {
	  return identifier;
   }
   public void setIdentifier(String id) {
	  this.identifier = id;
   }

   public String getImplementClass() {
	  return implementClass;
   }
   public void setImplementClass(String implementClass) {
	  this.implementClass = implementClass;
   }

   public String getInfo() {
	  return info;
   }
   public void setInfo(String info) {
	  this.info = info;
   }

   public int getTypeId() {
	  return typeId;
   }
   public void setTypeId(int typeId) {
	  this.typeId = typeId;
   }
   
   public String getTypeDescription() {
	  return typeDescription;
   }
   public void setTypeDescription(String typeDescription) {
	  this.typeDescription = typeDescription;
   }
   
   public String getUserAction() {
	  return userAction;
   }
   public void setUserAction(String userAction) {
	  this.userAction = userAction;
   }
   
   public String getHookDesc() {
	  return hookDesc;
   }
   public void setHookDesc(String hookDesc) {
	  this.hookDesc = hookDesc;
   }

   public String getHookId() {
      return hookId;
   }
   public void setHookId(String hookId) {
	  this.hookId = hookId;
   }
   
   private static final long serialVersionUID = 1L;
}
