package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: Tramite.java,v 1.2.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Clase que implementa la interfaz Tramite
 *
 */
public class Tramite extends RetornoServicio
{
	/**
	 * Constructor de la clase TramiteImpl
	 *
	 */
   public Tramite()
   {
      this.id = null;
      this.topic = null;
      this.description = null;
      this.addressee = null;
      this.limitDocs = ConstantesServicios.LABEL_TRUE;
      this.firma = ConstantesServicios.LABEL_TRUE;
      this.documents = new Documentos();
      this.idProcedimiento = null;
   }
	
   /**
    * Establece el identificador del procedimiento.
    * @param id Identificador del procedimiento.
    */	
   public void setId(String id)
   {
      this.id = id;
   }
   
   /**
    * Establece el identificador del procedimiento de expediente.
    * @param id Identificador del procedimiento de expediente.
    */	
   public void setIdProcedimiento(String idProcedimiento)
   {
      this.idProcedimiento = idProcedimiento;
   }
   
   /**
    * Establece el asunto del procedimiento.
    * @param topic Asunto.
    */   
   public void setTopic(String topic)
   {
      this.topic = topic;
   }
   
   /**
    * Establece la descripción del procedimiento.
    * @param description descripción.
    */   
   public void setDescription(String description)
   {
      this.description = description;
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
    * Establece la oficina asociada al trámie.
    * @param oficina Oficina.
    */   
   public void setOficina(String oficina)
   {
      this.oficina = oficina;
   }
   
   /**
    * Establece si el procedimiento tiene limitado el número de documentos.
    * @param limit Límite
    */   
   public void setLimitDocs(String limit)
   {
      this.limitDocs = limit;
   }

   /**
    * Establece si para procedimiento se debe firmar la solicitud.
    * @param firma Firma
    */   
   public void setFirma(String firma)
   {
      this.firma = firma;
   }
   
   /**
    * Establece la colección de documentos del procedimiento.
    * @param documents documentos del procedimientos.
    */   
   public void setDocuments(Documentos documents)
   {
      this.documents = documents;
   }
   
   /**
    * Recupera el identificador de procedimiento.
    * @return String identificador.
    */   
   public String getId()
   {
      return id;
   }
   
   /**
    * Recupera el identificador de procedimiento de expediente.
    * @return String identificador de procedimiento de expediente.
    */   
   public String getIdProcedimiento()
   {
      return idProcedimiento;
   }
   
   /**
    * Recupera el asunto del procedimiento.
    * @return String Asunto.
    */   
   public String getTopic()
   {
      return topic;
   }
   
   /**
    * Recupera la descripción del procedimiento.
    * @return String Descripción.
    */   
   public String getDescription()
   {
      return description;
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
    * Recupera la oficina asociada al trámite.
    * @return String Oficina.
    */   
   public String getOficina()
   {
      return oficina;
   }
   
   /**
    * Indica si el procedimiento tiene limitado el número de documentos.
    * @return boolean Limitación del número de documentos.
    */   
   public String getLimitDocs()
   {
      return limitDocs;
   }
   
   /**
    * Indica si para el procedimiento se debe firmar la solicitud 
    * @return boolean Si se debe o no firmar la solicitud.
    */   
   public String getFirma()
   {
      return firma;
   }
   
   /**
    * Recupera la colección de documentos del procedimento.
    */   
   public Documentos getDocuments()
   {
      return documents;
   }

   protected String id;
   protected String topic;
   protected String description;
   protected String addressee;
   protected String limitDocs;
   protected String firma;
   protected Documentos documents;
   protected String oficina;
   protected String idProcedimiento;
}