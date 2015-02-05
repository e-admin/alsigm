package ieci.tecdoc.sgm.core.services.repositorio;

import java.sql.Timestamp;

/**
 * Bean con las propiedades de un documento temporal asociado a un registro.
 * 
 * @author IECISA
 *
 */
public class DocumentoTemporal
{
   public DocumentoTemporal()
   {
     sessionId = null;
     guid = null;
     timestamp = null;
   }
   
   /**
    * Devuelve el identificador único de documento.
    * @return String Identificador único de documento.
    */
  public String getGuid(){
    return guid;
  }
  
  /**
   * Devuelve el identificador de la sesión.
   * @return String Identificador de la sesión.
   */   
  public String getSessionId(){
    return sessionId;
  }
  
  /**
   * Devuelve la fecha de entrada.
   * @return Timestamp Fecha de entrada.
   */   
  public Timestamp getTimestamp(){
    return timestamp;
  }
   
  /**
   * Establece identificador único de documento.
   * @param guid Identificador único de documento.
   */  
  public void setGuid(String guid){
    this.guid = guid;
  }
  
  /**
   * Establece el identificador de la sesión.
   * @param sessionId Identificador de la sesión.
   */   
  public void setSessionId(String sessionId){
    this.sessionId = sessionId;
  }
  
  /**
   * Establece tiempo en el que se produce la entrada.
   * @param timestamp Instante de tiempo de la entrada.
   */   
  public void setTimestamp(Timestamp timestamp){
    this.timestamp = timestamp;
  }

   protected String sessionId; 
   protected String guid;
   protected Timestamp timestamp;

   
}