package ieci.tecdoc.sgm.rde.datatypes;

import java.sql.Timestamp;


/**
 * Interfaz de comportamiento de un documento temporal asociado a un registro.
 * 
 * @author IECISA
 *
 */
public interface DocumentoTemporal 
{

    /**
     * Devuelve el identificador único de documento.
     * @return String Identificador único de documento.
     */
   public abstract String getGuid();
   
   /**
    * Devuelve el identificador de la sesión.
    * @return String Identificador de la sesión.
    */   
   public abstract String getSessionId();
   
   /**
    * Devuelve la fecha de entrada.
    * @return Timestamp Fecha de entrada.
    */   
   public abstract Timestamp getTimestamp();
    
   /**
    * Establece identificador único de documento.
    * @param guid Identificador único de documento.
    */  
   public abstract void setGuid(String guid);
   
   /**
    * Establece el identificador de la sesión.
    * @param sessionId Identificador de la sesión.
    */   
   public abstract void setSessionId(String sessionId);
   
   /**
    * Establece tiempo en el que se produce la entrada.
    * @param timestamp Instante de tiempo de la entrada.
    */   
   public abstract void setTimestamp(Timestamp timestamp);

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