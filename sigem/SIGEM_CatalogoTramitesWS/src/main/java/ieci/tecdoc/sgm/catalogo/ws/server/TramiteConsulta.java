package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: TramiteConsulta.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


/**
 * DTO para los datos de un procedimiento.
 * 
 * @author IECISA
 *
 */
public class TramiteConsulta extends RetornoServicio
{
   private String id;
   private String topic;
   private String addressee;
   private String subject;
   private String type;
   private String subtype;

   /**
    * Constructor de la clase TramiteConsulta
    *
    */
   public TramiteConsulta()
   {
      id = null;
      topic = null;
      addressee = null;
      subject = null;
      type = null;
      subtype = null;
   }

   /**
    * Establece el identificador del procedimiento.
    * @param id identificador.
    */
   public void setId(String id)
   {
      this.id = id;
   }
   
   /**
    * Establece el objeto del procedimiento.
    * @param topic Objeto.
    */   
   public void setTopic(String topic)
   {
      this.topic = topic;
   }
   
   /**
    * Establece el destinatario del procedimiento.
    * @param addressee Destinatario.
    */   
   public void setAddressee(String addressee)
   {
      this.addressee = addressee;
   }
   
   /**
    * Establece el asunto del procedimiento.
    * @param subject Asunto del procedimiento.
    */   
   public void setSubject(String subject)
   {
      this.subject = subject;
   }
   
   /**
    * Establece el tipo del procedimiento.
    * @param type Tipo del procedimiento.
    */   
   public void setType(String type)
   {
      this.type = type;
   }
   
   /**
    * Establece el subtipo del procedimiento.
    * @param subtype Subtipo
    */   
   public void setSubtype(String subtype)
   {
      this.subtype = subtype;
   }
   
   /**
    * Recupera el identificador del procedimiento.
    * @return String identificador del procedimiento.
    */   
   public String getId()
   {
      return id;
   }
   
   /**
    * Recupera el objeto del procedimiento.
    * @return String objeto del procedimiento.
    */   
   public String getTopic()
   {
      return topic;
   }
   
   /**
    * Recupera el destinatario del procedimiento.
    * @return String Destinatario.
    */   
   public String getAddressee()
   {
      return addressee;
   }
   
   /**
    * Recupera el asunto del procedimiento.
    * @return String Asunto.
    */   
   public String getSubject()
   {
      return subject;
   }
   
   /**
    * Recupera el tipo del procedimiento.
    * @return String Tipo de procedimiento.
    */   
   public String getType()
   {
      return type;
   }
   
   /**
    * Recupera el subtipo del procedimiento.
    * @return String subtipo.
    */   
   public String getSubtype()
   {
      return subtype;
   }

}