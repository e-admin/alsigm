
package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.catalogo_tramites.util.Documentos;
import ieci.tecdoc.sgm.catalogo_tramites.util.Tramite;

import java.io.Serializable;


/**
 * Clase que implementa la interfaz Tramite
 *
 */
public class TramiteImpl implements Tramite, Serializable 
{
	/**
	 * Constructor de la clase TramiteImpl
	 *
	 */
   public TramiteImpl()
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
    * Establece el identificador del procedimiento del expediente.
    * @param id Identificador del procedimiento del expediente.
    */	
   public void setIdProcedimiento(String idProcedimiento)
   {
      this.idProcedimiento = idProcedimiento;
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

   /**
    * Recupera el identificador de procedimiento del expediente.
    * @return String identificador del procedimiento del expedeinte.
    */   
   public String getIdProcedimiento()
   {
      return idProcedimiento;
   }
   
   /**
    * Devuelve una cadena XML con la información del procedimiento.
    * @param header Indica si el XML debe llevar cabecera.
    * @return String Cadena XML con los datos del procedimiento.
    */   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Procedure";
      String tmp;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Id", id);
      bdr.addSimpleElement("Topic", topic);
      bdr.addSimpleElement("Description", description);
      bdr.addSimpleElement("Addressee", addressee);
      if (limitDocs)
         tmp = "1";
      else
         tmp = "0";
      bdr.addSimpleElement("Limit_Documents", tmp);
      if (firma)
          tmp = "1";
       else
          tmp = "0";
       bdr.addSimpleElement("Sign", tmp);
       bdr.addSimpleElement("Oficina", oficina);
       bdr.addSimpleElement("IdProcedimiento", idProcedimiento);
      bdr.addFragment(documents.toXML(false));

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con los datos del procedimento.
    * @return String Cadena XML con los datos del procedimiento.
    */
	public String toString()
	{
      return toXML(false);
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