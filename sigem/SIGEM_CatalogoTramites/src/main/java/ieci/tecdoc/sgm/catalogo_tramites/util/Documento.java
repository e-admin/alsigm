package ieci.tecdoc.sgm.catalogo_tramites.util;

/**
 * Interfaz de corportamiento de un documento.
 * 
 * @author IECISA
 *
 */
public interface Documento 
{
   public abstract void setId(String id);
   public abstract void setDescription(String description);
   public abstract void setExtension(String extension);
   public abstract void setSignatureHook(String signatureHook);
   public abstract void setValidationHook(String validationHook);
   public abstract String getId();
   public abstract String getDescription();
   public abstract String getExtension();
   public abstract String getSignatureHook();
   public abstract String getValidationHook();
   
   /**
    * Recoge los valores de la instancia en una cadena xml
    * 
    * @param header
    *           Si se incluye la cabecera
    * @return los datos en formato xml
    */
   public abstract String toXML(boolean header);

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public abstract String toString();

}