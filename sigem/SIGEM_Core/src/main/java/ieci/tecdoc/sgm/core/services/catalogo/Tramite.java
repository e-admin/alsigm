package ieci.tecdoc.sgm.core.services.catalogo;

/**
 * Clase que implementa la interfaz Tramite
 *
 */
public class Tramite
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
      this.limitDocs = true;
      this.firma = true;
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
    * Establece el id de procedimiento de expediente asociada al trámie.
    * @param idProcedimento Id de procedimiento de expediente.
    */   
   public void setIdProcedimiento(String idProcedimiento)
   {
      this.idProcedimiento = idProcedimiento;
   }
   
   /**
    * Establece si el procedimiento tiene limitado el número de documentos.
    * @param limit Límite
    */   
   public void setLimitDocs(boolean limit)
   {
      this.limitDocs = limit;
   }

   /**
    * Establece si para procedimiento se debe firmar la solicitud.
    * @param firma Firma
    */   
   public void setFirma(boolean firma)
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
    * Recupera el id del procedimiento de expediente asociada al trámite.
    * @return String Id del procedimiento de expediente.
    */   
   public String getIdProcedimiento()
   {
      return idProcedimiento;
   }
   
   /**
    * Indica si el procedimiento tiene limitado el número de documentos.
    * @return boolean Limitación del número de documentos.
    */   
   public boolean getLimitDocs()
   {
      return limitDocs;
   }
   
   /**
    * Indica si para el procedimiento se debe firmar la solicitud 
    * @return boolean Si se debe o no firmar la solicitud.
    */   
   public boolean getFirma()
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
   protected boolean limitDocs;
   protected boolean firma;
   protected Documentos documents;
   protected String oficina;
   protected String idProcedimiento;
}