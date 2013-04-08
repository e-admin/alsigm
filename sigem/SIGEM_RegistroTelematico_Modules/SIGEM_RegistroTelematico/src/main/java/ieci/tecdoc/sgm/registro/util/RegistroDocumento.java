package ieci.tecdoc.sgm.registro.util;


/**
 * Interfaz de comportamiento de un número de secuencia de registro.
 * 
 * @author IECISA
 *
 */
public interface RegistroDocumento 
{

   /**
   * Devuelve el identificador único de documento.
   * @return String Identificador único de documento.
   */
   public abstract String getGuid();

   /**
   * Establece identificador único de documento.
   * @param guid Identificador único de documento.
   */   
   public abstract void setGuid(String guid);

   /**
   * Devuelve el número del registro al que pertenece el documento.
   * @return String Identificador único de documento.
   */
   public abstract String getRegistryNumber();
 
   /**
    * Establece el número del registro al que pertenece el documento.
    * @param registryNumber Identificador único de documento.
    */   
   public abstract void setRegistryNumber(String registryNumber); 

   /**
   * Devuelve código de documento.
   * @return String Identificador único de documento.
   */
   public abstract String getCode();

   /**
   * Establece código de documento.
   * @param code Identificador único de documento.
   */   
   public void setCode(String code);
   
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