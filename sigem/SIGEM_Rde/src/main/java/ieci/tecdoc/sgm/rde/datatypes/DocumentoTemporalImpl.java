package ieci.tecdoc.sgm.rde.datatypes;

import java.sql.Timestamp;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Bean con las propiedades de un documento temporal asociado a un registro.
 * 
 * @author IECISA
 *
 */
public class DocumentoTemporalImpl implements DocumentoTemporal
{
   public DocumentoTemporalImpl()
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

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "DocumentTmp";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("SessionId", sessionId);
      bdr.addSimpleElement("Guid", guid);
      bdr.addSimpleElement("Timestamp", timestamp.toString());

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString() {
      return toXML(false);
   }

   protected String sessionId; 
   protected String guid;
   protected Timestamp timestamp;

   
}