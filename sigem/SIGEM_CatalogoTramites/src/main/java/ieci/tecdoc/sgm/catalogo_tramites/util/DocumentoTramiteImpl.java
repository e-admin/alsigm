package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;

/**
 * Clase que representa a un documento asociado a un trámite.
 * 
 * @author IECISA
 *
 */
public class DocumentoTramiteImpl implements DocumentoTramite, Serializable
{
	/**
	 * Constructor de la clase DocuemtoTramiteImpl
	 *
	 */
   public DocumentoTramiteImpl()
   {
      this.procedureId = null;
      this.documentId = null;
      this.code = null;
      this.mandatory = false;
   }

   /**
    * Establece el identificador del procedimiento.
    * @param procedureId Identificador del procedimiento.
    */
   public void setProcedureId(String procedureId)
   {
      this.procedureId = procedureId;      
   }

   /**
    * Establece el identificador del documento.
    * @param documentId Identificador de documento.
    */
   public void setDocumentId(String documentId)
   {
      this.documentId = documentId;
   }

   /**
    * Establece el código del documento.
    * @param code código del documento.
    */
   public void setCode(String code)
   {
      this.code = code;
   }
   
   /**
    * Establece la obligatoriedad del documento en el procedimiento.
    * @param mandatory Obligatoriedad.
    */
   public void setMandatory(boolean mandatory)
   {
      this.mandatory = mandatory;
   }
   
   /**
    * Recupera el identificador del procedimiento.
    * @return String Identificador del procedimiento.
    */   
   public String getProcedureId()
   {
      return procedureId;
   }

   /**
    * Recupera el identificador del documento.
    * @return String identificador del documento.
    */
   public String getDocumentId()
   {
      return documentId;
   }

   /**
    * Recupera el código del documento.
    * @return String código del documento.
    */
   public String getCode()
   {
      return code;
   }
   
   /**
    * Recupera la obligatoriedad del documento en el procedimiento.
    * @return String obligatoriedad.
    */
   public boolean isMandatory()
   {
      return mandatory;
   }
      
   /**
    * Recoge los valores de la instancia en una cadena xml
    * 
    * @param header
    *           Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "Procedure_Document";
      String tmp;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      bdr.addSimpleElement("ProcedureId", procedureId);
      bdr.addSimpleElement("DocumentId", documentId);
      bdr.addSimpleElement("Code", code);

      if (mandatory)
         tmp = "1";
      else
         tmp = "0";
      bdr.addSimpleElement("Mandatory", tmp);
 
      bdr.addClosingTag(tagName);
      
      return bdr.getText();
      
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString()
   {
      return toXML(false);
   }
      
   protected String procedureId;
   protected String documentId;
   protected String code;
   protected boolean mandatory;
}