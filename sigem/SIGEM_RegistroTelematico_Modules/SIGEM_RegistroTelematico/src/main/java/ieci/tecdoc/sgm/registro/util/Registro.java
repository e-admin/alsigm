package ieci.tecdoc.sgm.registro.util;

import java.util.Date;

/**
 * Interfaz de comportamiento de un registro del sistema.
 * 
 * @author IECISA
 *
 */
public interface Registro 
{
	/**
	 * Establece el número de registro.
	 * @param registryNumber Número de registro.
	 */	
   public abstract void setRegistryNumber(String registryNumber);
   
   /**
    * Establece la fecha de registro.
    * @param registryDate Fecha de registro.
    */   
   public abstract void setRegistryDate(Date registryDate);
   
   /**
    * Establece la fecha efectiva de registro.
    * @param registryDate Fecha efectiva de registro.
    */   
   public abstract void setEffectiveDate(Date effectiveDate);
   
   
   /**
    * Establece el identificador (NIF, CIF, etc) del ciudadano.
    * @param senderId Identificador de usuario.
    */   
   public abstract void setSenderId(String senderId);
   
   /**
    * Establece el nombre de pila del ciudadano.
    * @param name Nombre de pila del ciudadano.
    */   
   public abstract void setName(String name);
   
   /**
    * Establece los apellidos del ciudadano.
    * @param surName Apellidos del ciudadano.
    */   
   //public abstract void setSurName(String surName);
   
   /**
    * Establece el email del cidadano.
    * @param eMail Email del ciudadano.
    */   
   public abstract void setEMail(String eMail);

   /**
    * Objeto del registro.
    * @param topic Objeto del registro.
    */
   public abstract void setTopic(String topic);
   
   /**
    * Establece el organismo destinatario del registro.
    * @param addressee Identificador de organismo destinatario.
    */   
   public abstract void setAddressee(String addressee);

   /**
    * Establece el estado del registro.
    * @param status Estado del registro.
    */   
   public abstract void setStatus(int status);
   
   /**
    * Establece el tipo de identificación del ciudadano.
    * @param senderIdType Tipo de identificación del ciudadano.
    */   
   public abstract void setSenderIdType(int senderIdType);
   
   /**
    * Establece la info adicional.
    * @param additionalInfo la info adicional.
    */   
   public abstract void setAdittionalInfo(byte[] additionalInfo);
   
   /**
    * Establece la oficina del registro.
    * @param oficina Identificador de la oficina.
    */   
   public abstract void setOficina(String oficina);
   
   /**
    * Establece el numero de expediente.
    * @param numexp Número del expediente.
    */   
   public abstract void setNumeroExpediente(String numexp);
   
   /**
    * Devuelve la info adicional.
    * @return byte[] La info adicional.
    */   
   public abstract byte[] getAdditionalInfo();
   
   /**
    * Devuelve el número de registro.
    * @return String número de registro.
    */   
   public abstract String getRegistryNumber();
   
   /**
    * Devuelve la fecha del registro.
    * @return Date fecha del registro.
    */   
   public abstract Date getRegistryDate();
   
   /**
    * Devuelve la fecha efectiva del registro.
    * @return Date fecha efectiva del registro.
    */   
   public abstract Date getEffectiveDate();
   
   
   /**
    * Devuevle el identificador del ciudadano.
    * @return String identificador del ciudadano.
    */   
   public abstract String getSenderId();
   
   /**
    * Devuelve el nombre de pila del ciudadano.
    * @return String Nombre de pila del ciudadano.
    */   
   public abstract String getName();
   
   /**
    * Devuelve los apellidos del ciudadano.
    * @return String Apellidos del ciudadano.
    */   
   //public abstract String getSurName();
   
   /**
    * Devuelve el email del ciudadano.
    * @return String Email del ciudadano.
    */   
   public abstract String getEMail();
   
   /**
    * Devuelve el objeto del registro.
    * @return String Objeto del registro.
    */
   public abstract String getTopic();
   
   /**
    * Devuelve el organismo destinatario del registro.
    * @return String identificador del organismo destinatario.
    */   
   public abstract String getAddressee();

   /**
    * Devuelve el estado del registro.
    * @return int Estado del registro.
    */   
   public abstract int getStatus();
   
   /**
    * Devuelve el tipo de identificador del ciudadano.
    * @return int Tipo de identificación del ciudadano.
    */   
   public abstract int getSenderIdType();

   /**
    * Devuelve la oficina.
    * @return String Oficina del registro.
    */
   public abstract String getOficina();
   
   /**
    * Devuelve el número de expediente.
    * @return String Número del expediente.
    */
   public abstract String getNumeroExpediente();   
   
   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();
   
}