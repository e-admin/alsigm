package ieci.tecdoc.sgm.rde.datatypes;

/**
 * Interfaz de comportamiento de un elemento mime.
 * 
 * @author IECISA
 *
 */
public interface TipoMime 
{

	/**
	 * Devuelve el la extensión asociada al tipo mime.
	 * @return String extensión del tipo mime.
	 */
   public abstract String getExtension();
   
   /**
    * Devuelve el tipo mime.
    * @return String Tipo mime.
    */   
   public abstract String getMimeType();
   
   /**
    * Establece la extensión asociada al tipo mime
    * @param extension Extensión asociada al tipo mime.
    */	
   public abstract void setExtension(String extension);
   
   /**
    * Establece el tipo mime.
    * @param mimeType Tipo mime.
    */   
   public abstract void setMimeType(String mimeType);

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();

}