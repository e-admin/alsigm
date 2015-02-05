package ieci.tecdoc.sgm.registro.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Bean con las propiedades de un número de secuencia de registro.
 * 
 * @author IECISA
 *
 */
public class RegistroDocumentoImpl implements RegistroDocumento 
{
   public RegistroDocumentoImpl()
   {
     registryNumber = null;
     code = null;
     guid = null;
   }
   
   /**
     * Devuelve el identificador único de documento.
     * @return String Identificador único de documento.
     */
   public String getGuid(){
     return guid;
   }
  
   /**
     * Establece identificador único de documento.
     * @param guid Identificador único de documento.
     */   
   public void setGuid(String guid){
     this.guid = guid;
   }
  
   /**
    * Devuelve el número del registro al que pertenece el documento.
    * @return String Identificador único de documento.
    */
    public String getRegistryNumber(){
      return registryNumber;
    }
   
    /**
      * Establece el número del registro al que pertenece el documento.
      * @param registryNumber Identificador único del registro.
      */   
    public void setRegistryNumber(String registryNumber){
      this.registryNumber = registryNumber;
    } 

    /**
     * Devuelve código de documento.
     * @return String Identificador único de documento.
     */
   public String getCode(){
     return code;
   }
  
   /**
     * Establece código de documento.
     * @param code Identificador único de documento.
     */   
   public void setCode(String code){
     this.code = code;
   }

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Document";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      bdr.addSimpleElement("RegistryNumber", registryNumber);
      bdr.addSimpleElement("Code", code);
      bdr.addSimpleElement("Guid", guid);
      
      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString() {
      return toXML(false);
   }

   protected String guid;
   protected String registryNumber;
   protected String code;

   
}