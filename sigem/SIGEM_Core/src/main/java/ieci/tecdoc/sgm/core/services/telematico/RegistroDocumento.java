package ieci.tecdoc.sgm.core.services.telematico;

/**
 * Bean con las propiedades de un número de secuencia de registro.
 * 
 * @author IECISA
 *
 */
public class RegistroDocumento
{
   public RegistroDocumento()
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

   protected String guid;
   protected String registryNumber;
   protected String code;

   
}