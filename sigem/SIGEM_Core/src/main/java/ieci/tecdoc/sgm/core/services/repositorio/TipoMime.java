package ieci.tecdoc.sgm.core.services.repositorio;

/**
 * Bean con las propiedades de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class TipoMime
{
   public TipoMime()
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

   protected String extension;
   protected String mimeType;
   
}