package ieci.tecdoc.sgm.certificacion.datatype;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

/**
 * Bean con las propiedades de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class CertificacionImpl implements Certificacion 
{
   public CertificacionImpl()
   {
   }
   
	/**
	 * Devuelve el identifcador del documento.
	 * @return String Identificador del documento.
	 */
   public String getIdFichero(){
	   return idFichero;
   }
  
  /**
   * Devuelve el identificador de usuario.
   * @return String Identificador de usuario.
   */   
   public String getIdUsuario(){
	   return idUsuario;
   }
  
  /**
   * Establece el identificador del documento
   * @param idFichero Identificador del documento.
   */	
   public void setIdFichero(String idFichero){
	   this.idFichero = idFichero;
   }
  
  /**
   * Establece el identificador del usuario.
   * @param idUsuario Identificador del usuario.
   */   
   public void setIdUsuario(String idUsuario){
	   this.idUsuario = idUsuario;
   }
  

   /**
    * Recoge los valores de la instancia en una cadena xml
    * @param header Si se incluye la cabecera
    * @return los datos en formato xml
    */   
   public String toXML(boolean header) {
      XmlTextBuilder bdr;
      String tagName = "Certificacion";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      bdr.addSimpleElement("IdFichero", idFichero);
      bdr.addSimpleElement("IdUsuario", idUsuario);

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Devuelve los valores de la instancia en una cadena de caracteres.
    */
   public String toString() {
      return toXML(false);
   }

   protected String idUsuario;
   protected String idFichero;
   
}