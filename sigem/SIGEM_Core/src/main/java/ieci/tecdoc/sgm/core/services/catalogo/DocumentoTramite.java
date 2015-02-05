package ieci.tecdoc.sgm.core.services.catalogo;

/**
 * Clase que representa a un documento asociado a un trámite.
 * 
 * @author IECISA
 *
 */
public class DocumentoTramite
{
	/**
	 * Constructor de la clase DocuemtoTramiteImpl
	 *
	 */
   public DocumentoTramite()
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

      
   protected String procedureId;
   protected String documentId;
   protected String code;
   protected boolean mandatory;
}