package ieci.tecdoc.sgm.catalogo_tramites.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;


/**
 * Clase que implementa la interfaz Documento.
 * 
 * @author IECISA
 *
 */
public class DocumentoImpl implements Documento, Serializable
{
	/**
	 * Constructor de la clase DocumentoImpl
	 *
	 */
   public DocumentoImpl()
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
   
   /**
    * Devuelve una cadena XMl con la información asociada al documento.
    * @param header Establece si el XML debe llevar cabecera.
    * @return String Cadena XML.
    */   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Catalogo_Tramite";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      bdr.addSimpleElement("Id", id);
      bdr.addSimpleElement("Description", description);
      bdr.addSimpleElement("Extension", extension);
      bdr.addSimpleElement("Signature_Hook", signatureHook);
      bdr.addSimpleElement("Validation_Hook", validationHook);
 
      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Devuelve una cadena con la información asociada a un documento.
    * @return String Cadena XML con la información del documento.
    */
	public String toString()
	{
      return toXML(false);
   }
   
   protected String id;
   protected String description;
   protected String extension;
   protected String signatureHook;
   protected String validationHook;

}