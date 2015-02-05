package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: Documento.java,v 1.1.2.1 2008/01/25 12:25:06 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


/**
 * Clase que implementa la interfaz Documento.
 * 
 * @author IECISA
 *
 */
public class Documento extends RetornoServicio
{
	/**
	 * Constructor de la clase DocumentoImpl
	 *
	 */
   public Documento()
   {
      this.id = null;
      this.description = null;
      this.extension = null;
      this.signatureHook = null;
      this.validationHook = null;
   }
   
   /**
    * Establece el identificador del documento.
    * @param String Identificador de documento.
    */   
   public void setId(String id)
   {
      this.id = id;
   }
   
   /**
    * Establece la descripción del documento.
    * @param description Descripción del documento.
    */   
   public void setDescription(String description)
   {
      this.description = description;
   }
   
   /**
    * Establece la extensión del documento.
    * @param extension Extensión del documento.
    */   
   public void setExtension(String extension)
   {
      this.extension = extension;
   }
   
   /**
    * Conector de firma para el documento.
    * @param signatureHook Identificador del conector para la firma del documento.
    */   
   public void setSignatureHook(String signatureHook)
   {
      this.signatureHook = signatureHook;
   }
   
   /**
    * Establece el conector para la validación del contenido del documento.
    * @param validationHook Identificador del conector de validación de contenido.
    */   
   public void setValidationHook(String validationHook)
   {
      this.validationHook = validationHook;
   }
   
   /**
    * Recupera el identificador del documento.
    * @return String identificador de documento.
    */   
   public String getId()
   {
      return id;
   }
   
   /**
    * Recupera la descripción del documento.
    * @return String descripción del documento.
    */   
   public String getDescription()
   {
      return description;
   }
   
   /**
    * Recupera la extensión del documento.
    * @return String Extensión del documento.
    */   
   public String getExtension()
   {
      return extension;
   }
   
   /**
    * Recupera el identificador del conexión de firma del documento.
    * @return String Identificador del conector de firma del documento.
    */   
   public String getSignatureHook()
   {
      return signatureHook;
   }

   /**
    * Recupera el identificador del conector de validación de contenido.
    * @retunr String identificdor del conector de validación.
    */
   public String getValidationHook()
   {
      return validationHook;
   }
 
   
   protected String id;
   protected String description;
   protected String extension;
   protected String signatureHook;
   protected String validationHook;

}