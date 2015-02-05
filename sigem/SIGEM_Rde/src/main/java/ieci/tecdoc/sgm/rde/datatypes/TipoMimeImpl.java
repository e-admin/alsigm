package ieci.tecdoc.sgm.rde.datatypes;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Bean con las propiedades de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class TipoMimeImpl implements TipoMime 
{
   public TipoMimeImpl()
   {
   }
   
    /**
     * Devuelve el la extensión asociada al tipo mime.
     * @return String extensión del tipo mime.
     */  
   public String getExtension()
   {
      return extension;
   }
	
   /**
    * Devuelve el tipo mime.
    * @return String Tipo mime.
    */      
   public String getMimeType()
   {
      return mimeType;
   }
	
   /**
    * Establece la extensión asociada al tipo mime
    * @param extension Extensión asociada al tipo mime.
    */   	
   public void setExtension(String extension)
   {
      this.extension = extension;
   }
   
   /**
    * Establece el tipo mime.
    * @param mimeType Tipo mime.
    */        
   public void setMimeType(String mimeType)
   {
      this.mimeType = mimeType;
   }

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "MimeType";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("Extension", extension);
      bdr.addSimpleElement("Type", mimeType);

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString() {
      return toXML(false);
   }

   protected String extension;
   protected String mimeType;
   
}