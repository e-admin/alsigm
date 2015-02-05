package ieci.tecdoc.sgm.catalogo_tramites.form;

import org.apache.struts.action.ActionForm;

public class TramiteForm extends ActionForm {
   private String id;
   private String description;
   private String topic;
   private String addressee;
   private int documentLimit;
   private int firma;

   public TramiteForm(){
      this.id="";
      this.description="";
      this.topic="";
      this.addressee="";
      this.documentLimit=0;
      this.firma=1;
   }
   
   public TramiteForm(String id, String description){
      this.id = id;
      this.description = description;
   }
   
   public String getAddressee() {
      return addressee;
   }
   public void setAddressee(String addressee) {
      this.addressee = addressee;
   }

   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }

   public int getDocumentLimit() {
      return documentLimit;
   }
   
   public int getFirma() {
	  return firma;
   }
   
   public void setDocumentLimit(int documentLimit) {
      this.documentLimit = documentLimit;
   }
   
   public void setFirma(int firma) {
	   this.firma = firma;
   }
   
   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }
   
   public String getTopic() {
      return topic;
   }
   public void setTopic(String topic) {
      this.topic = topic;
   }
   
   private static final long serialVersionUID = 1L;
}
