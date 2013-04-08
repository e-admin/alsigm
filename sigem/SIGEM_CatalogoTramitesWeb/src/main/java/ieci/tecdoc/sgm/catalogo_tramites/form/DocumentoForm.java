
package ieci.tecdoc.sgm.catalogo_tramites.form;

import org.apache.struts.action.ActionForm;

/**
 * @author X72435FE
 *
 * TODO Para cambiar la plantilla de este comentario generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */
public class DocumentoForm extends ActionForm{
   private String id;
   private String description;
   private String signature;
   private String content;
   private String extension;
   /*private String userAction;
   private String docId;
   private String docDesc;*/
   
   public DocumentoForm(){
   	 id = "";
   	 description = "";
   	 signature = "";
   	 content = "";
   	 extension = "";   	 
   }
   
   /*public DocumentForm(String docId, String docDesc){
   	  this.docId = docId;
   	  this.docDesc = docDesc;
   }*/
   
   public String getContent() {
	  return content;
   }
   public void setContent(String content) {
	  this.content = content;
   }
   
   public String getDescription() {
	  return description;
   }
   public void setDescription(String description) {
	  this.description = description;
   }
   
   /*public String getDocDesc() {
	  return docDesc;
   }
   public void setDocDesc(String docDesc) {
	  this.docDesc = docDesc;
   }
   
   public String getDocId() {
	  return docId;
   }
   public void setDocId(String docId) {
   	 this.docId = docId;
   }*/
   
   public String getExtension() {
	  return extension;
   }
   public void setExtension(String extension) {
	  this.extension = extension;
   }
   
   public String getId() {
	  return id;
   }
   public void setId(String id) {
	  this.id = id;
   }

   public String getSignature() {
	  return signature;
   }
   public void setSignature(String signature) {
      this.signature = signature;
   }

   /*public String getUserAction() {
	  return userAction;
   }
   public void setUserAction(String userAction) {
	  this.userAction = userAction;
   }*/
   
   private static final long serialVersionUID = 1L;
}
