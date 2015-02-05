package ieci.tecdoc.sgm.autenticacion.util.signature;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.security.cert.X509Certificate;

/**
 * Clase que encapsula la información asociada al resultado de la validación de una firma.
 * 
 * @author IECISA
 *
 */
public class ValidacionFirmaInfo implements Serializable {

   private X509Certificate certificate;

   /**
    * Método que establece el certificado de la firma.
    * 
    * @param certificate
    */
   public void setCertificate(X509Certificate certificate)
   {
      this.certificate = certificate;
   }
   
   /** 
    * Método que obtiene el certificado de una firma.
    * @return X509Certificate
    */
   public X509Certificate getCertificate()
   {
      return certificate;
   }
   
   /**
    * Método que obtiene una cadena XML con la información de la validación.
    * @param header Establece si el XML deberá llevar cabecera.
    * @return String XML con la información.
    */   
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Certificate";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addSimpleElement(tagName, "<![CDATA[" + certificate.toString() + "]]>");

      return bdr.getText();
   }
   
   /**
    * 
    * @return
    */
   public String toString() 
   {
      return null;
   }
   
}

