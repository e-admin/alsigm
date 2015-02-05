package ieci.tecdoc.sgm.core.services.catalogo;

/**
 * Especialización de un documento para su inclusión en un procedimiento.
 * 
 * @author IECISA
 *
 */
public class DocumentoExtendido extends Documento
{
	/**
	 * Constructor de la clase DocumentoExtendido
	 *
	 */
   public DocumentoExtendido()
   {
      code = null;
      mandatory = false;
   }
   
   /**
    * Constructor de la clase DocumentoExtendido a partir de un objeto Documento
    * @param document Datos de inicialización
    */
   public DocumentoExtendido(Documento document) {
      this.setId(document.getId());
      this.setDescription(document.getDescription());
      this.setExtension(document.getExtension());
      this.setSignatureHook(document.getSignatureHook());
      this.setValidationHook(document.getValidationHook());
      code = null;
      mandatory = false;
   }
   
   /**
    * Establece el código del documento.
    * @param code Código del documento.
    */   
   public void setCode(String code)
   {
      this.code = code;
   }
   
   /**
    * Establece la obligatoriedad del documento.
    * @param mandatory Obligatoriedad.
    */   
   public void setMandatory(boolean mandatory)
   {
      this.mandatory = mandatory;
   }

   /**
    * Recupera el código del documento.
    * @return String Código de documento.
    */
   public String getCode()
   {
      return code;      
   }
   
   /**
    * Indica la obligatoriedad de un documento.
    * @return boolean Obligatoriedad.
    */   
   public boolean isMandatory()
   {
      return mandatory;
   }

  
   protected String code;
   protected boolean mandatory;
}