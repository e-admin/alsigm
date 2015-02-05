package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: DocumentoTramite.java,v 1.1.2.1 2008/01/25 12:25:06 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Clase que representa a un documento asociado a un trámite.
 * 
 * @author IECISA
 *
 */
public class DocumentoTramite extends RetornoServicio
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
      this.mandatory = ConstantesServicios.LABEL_FALSE;
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
   public void setMandatory(String mandatory)
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
   public String isMandatory()
   {
      return mandatory;
   }

      
   protected String procedureId;
   protected String documentId;
   protected String code;
   protected String mandatory;
}