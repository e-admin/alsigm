package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Bean que representa a un registro del sistema.
 * 
 * @author IECISA
 *
 */
public class Registro extends RetornoServicio
{
   public Registro()
   {
      registryNumber = "";
      registryDate = null;
      effectiveDate = null;
      senderId = "";
      name = "";
      //surName = "";
      eMail = "a";
      topic = "a";
      addressee = "a";
      status = ""+RegistroEstado.STATUS_ERROR;
      senderIdType="0";
      additionalInfo = null;
   }

	/**
	 * Establece el número de registro.
	 * @param registryNumber Número de registro.
	 */
   public  void setRegistryNumber(String registryNumber)
   {
      this.registryNumber = registryNumber;
   }

   /**
    * Establece la fecha de registro.
    * @param registryDate Fecha de registro.
    */   
   public void setRegistryDate(String registryDate)
   {
      this.registryDate = registryDate;
   }
   
   /**
    * Establece la fecha de registro.
    * @param registryDate Fecha de registro.
    */   
   public void setEffectiveDate(String effectiveDate)
   {
      this.effectiveDate = effectiveDate;
   }

   /**
    * Establece el identificador (NIF, CIF, etc) del ciudadano.
    * @param senderId Identificador de usuario.
    */
   public void setSenderId(String senderId)
   {
   	  this.senderId = senderId;
   }

   /**
    * Establece el nombre de pila del ciudadano.
    * @param name Nombre de pila del ciudadano.
    */   
   public void setName(String name)
   {
      this.name = name;
   }
   
   /**
    * Establece los apellidos del ciudadano.
    * @param surName Apellidos del ciudadano.
    */      
   /*public void setSurName(String surName)
   {
      this.surName = surName;
   }*/
   
   /**
    * Establece el email del cidadano.
    * @param eMail Email del ciudadano.
    */      
   public void setEMail(String eMail)
   {
      this.eMail = eMail;
   }

   /**
    * Objeto del registro.
    * @param topic Objeto del registro.
    */
   public void setTopic(String topic)
   {
      this.topic = topic;
   }

   /**
    * Establece el organismo destinatario del registro.
    * @param addressee Identificador de organismo destinatario.
    */   
   public void setAddressee(String addressee)
   {
   	  this.addressee = addressee;
   }

   /**
    * Establece el estado del registro.
    * @param status Estado del registro.
    */   
   public void setStatus(String status)
   {
   	  this.status = status;
   }
   
   /**
    * Establece el tipo de identificación del ciudadano.
    * @param senderIdType Tipo de identificación del ciudadano.
    */      
   public void setSenderIdType(String senderIdType) {
   	  this.senderIdType = senderIdType;
   }

   /**
    * Establece el nº de documento de identificación del representado.
    * @param representedId Nº de documento de identificación del representado.
    */
   public void setRepresentedId(String representedId) {
     this.representedId = representedId;
   }

   /**
    * Establece el nombre del representado.
    * @param representedName Nombre del representado.
    */
   public void setRepresentedName(String representedName) {
      this.representedName = representedName;
   }
   
   /**
    * Establece la oficina del registro.
    * @param oficina Identificador de la oficina del registro.
    */
   public void setOficina(String oficina) {
      this.oficina = oficina;
   }
   
   /**
    * Establece el número de expediente asociado a un registro.
    * @param numeroExpediente Número del expediente.
    */
   public void setNumeroExpediente(String numeroExpediente) {
      this.numeroExpediente = numeroExpediente;
   }
   
   /**
    * Devuelve el número de registro.
    * @return String número de registro.
    */   
   public String getRegistryNumber()
   {
      return this.registryNumber;
   }

   /**
    * Devuelve la fecha del registro.
    * @return Timestamp fecha del registro.
    */
   public String getRegistryDate()
   {
   	  return this.registryDate;
   }
   
   /**
    * Devuelve la fecha del registro.
    * @return Timestamp fecha del registro.
    */
   public String getEffectiveDate()
   {
   	  return this.effectiveDate;
   }

   /**
    * Devuevle el identificador del ciudadano.
    * @return String identificador del ciudadano.
    */   
   public String getSenderId()
   {
   	  return this.senderId;
   }

   /**
    * Devuelve el nombre de pila del ciudadano.
    * @return String Nombre de pila del ciudadano.
    */   
   public String getName()
   {
      return name;
   }
   
   /**
    * Devuelve los apellidos del ciudadano.
    * @return String Apellidos del ciudadano.
    */      
   /*public String getSurName()
   {
      return surName;
   }*/
   
   /**
    * Devuelve el email del ciudadano.
    * @return String Email del ciudadano.
    */      
   public String getEMail()
   {
      return eMail;
   }

   /**
    * Devuelve el objeto del registro.
    * @return String Objeto del registro.
    */   
   public String getTopic()
   {
      return this.topic;
   }

   /**
    * Devuelve el organismo destinatario del registro.
    * @return String identificador del organismo destinatario.
    */   
   public String getAddressee()
   {
   	  return this.addressee;
   }

   /**
    * Devuelve el estado del registro.
    * @return int Estado del registro.
    */   
   public String getStatus()
   {
   	  return this.status;
   }

   /**
    * Devuelve el tipo de identificador del ciudadano.
    * @return int Tipo de identificación del ciudadano.
    */   
   public String getSenderIdType() {
      return senderIdType;
   }
   
   /**
    * Devuelve el nº de documento de identificación del representado.
    * @return String id de documento de identificación del representado.
    */
   public String getRepresentedId() {
     return representedId;
   }
   /**
    * Devuelve el nombre del representado.
    * @return String nombre del representado.
    */
   public String getRepresentedName() {
       return representedName;
   }
   
   /**
    * Establece la info adicional.
    * @param additionalInfo la info adicional.
    */   
   public void setAdittionalInfo(String additionalInfo){
	   this.additionalInfo = additionalInfo;
   }
   
   /**
    * Devuelve la oficina del registro.
    * @return String Identificador de la oficina.
    */
   public String getOficina() {
     return oficina;
   }
   
   /**
    * Devuelve el número de expediente asociado al registro.
    * @return String Número de expediente asociado al registro.
    */
   public String getNumeroExpediente() {
     return numeroExpediente;
   }
   
   /**
    * Devuelve la info adicional.
    * @return byte[] La info adicional.
    */   
   public String getAdditionalInfo(){
	   return additionalInfo;
   }

   protected String registryNumber;
   protected String registryDate;
   protected String effectiveDate;
   protected String senderId;
   protected String name;
   //protected String surName;
   protected String eMail;
   protected String topic;
   protected String addressee;
   protected String status;
   protected String senderIdType;
   protected String representedName;
   protected String representedId;
   protected String additionalInfo;
   protected String oficina;
   protected String numeroExpediente;

}