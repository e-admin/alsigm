
package ieci.tecdoc.sgm.autenticacion.util.hook.signature;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.autenticacion.util.signature.FirmaExt;
import ieci.tecdoc.sgm.autenticacion.util.signature.ValidacionFirmasInfo;
import ieci.tecdoc.sgm.autenticacion.util.signature.VerificarFirmaExt;
//import ieci.rtn.core.common.Miscelanea;
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;

/**
 * Clase que encapsula el corportamiento de una firma XML.
 * 
 * @author IECISA.
 *
 */
public class XMLSignedSignature implements FirmaExt, VerificarFirmaExt
{
   public XMLSignedSignature()
   {
   }

   /**
    * Método de firma.
    * @param data Datos a firmar.
    * @param additionalInfo Información adicional necesaria para la firma.
    * @param certificate Certificado a utilizar para la firma.
    * @return ByteArrayOutputStream Con los datos de la firma.
    * @throws Exception En caso de producirse algún error.
    */
   public ByteArrayOutputStream sign(InputStream data, String additionalInfo, CertificadoFirmaX509Info certificate) throws Exception
   {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      ByteArrayOutputStream outputTmp;
      XmlDocument xmlDoc = new XmlDocument();
      XmlElement root, elem;
      String xmlText;
      byte[] tmp;
      SimpleCMSSignature signature = new SimpleCMSSignature();
      String sign;
      
      xmlText = new String(Goodies.getBytes(data));
      xmlDoc.createFromStringText(xmlText);
      root = xmlDoc.getRootElement();
      
      elem = root.getChildElement(SIGNED_DATA);
      sign = elem.getStringText(true);
      sign = sign.substring(TAG_B_SIGNED_DATA.length(), sign.length()-TAG_E_SIGNED_DATA.length());
      tmp = Goodies.fromStrToUTF8(sign);
      // OJO. Se supone que se ha construído sin saltos de línea
      outputTmp = signature.sign(tmp, additionalInfo, certificate);
      
      sign = Base64Util.encode(outputTmp.toByteArray());

      elem = root.getChildElement(SIGNATURE);
      elem.setValue(sign);
      
      output.write(xmlDoc.getStringText(false).getBytes());

      return output;
   }
   
   /**
    * Verificación de firma.
    * @param data Datos de la firma.
    * @param additionalInfo Información adicional necesaria para la validación.
    * @return SignaturesValidationInfo Información con el resultado de la validación.
    * @throws Exception En caso de producirse algún error.
    */
   public ValidacionFirmasInfo verifySign(InputStream data, String additionalInfo) throws Exception
   {
      ValidacionFirmasInfo infos = new ValidacionFirmasInfo();
      XmlDocument xmlDoc = new XmlDocument();
      XmlElement root, elem;
      String sign;
      byte[] tmp;
      SimpleCMSSignature signature = new SimpleCMSSignature();
      
      try
      {
         xmlDoc.createFromUtf8Text(Goodies.getBytes(data));
         root = xmlDoc.getRootElement();

         elem = root.getChildElement(SIGNED_DATA);
         sign = elem.getStringText(true);
         sign = sign.substring(TAG_B_SIGNED_DATA.length(), sign.length()-TAG_E_SIGNED_DATA.length());
         tmp = Goodies.fromStrToUTF8(sign);

         elem = root.getChildElement(SIGNATURE);
         sign = elem.getValue();

         infos = signature.verifySign(tmp, sign);
      }
      catch (Exception exc)
      {
         infos.setValid(false);
      }
      
      return infos;
   }

   /**
    * Método que obtiene el documento incluido en la firma.
    * @param data Datos de la firma.
    * @param additionalInfo Información adicional necesaria para la validación.
    * @return ByteArrayOutputStream Datos de la firma.
    * @throws Exception En caso de producirse algún error.
    */
   public ByteArrayOutputStream getDocument(InputStream data, String additionalInfo) throws Exception
   {
      XmlDocument xmlDoc = new XmlDocument();
      //XmlElement root;
      String doc, tmp;
      int index1, index2;
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      //SimpleCMSSignature signature = new SimpleCMSSignature();
      
      xmlDoc.createFromUtf8Text(Goodies.getBytes(data));
      //root = xmlDoc.getRootElement();
      doc = xmlDoc.getStringText(false);
      index1 = doc.lastIndexOf(SIGNATURE_BEGIN_TAG);
      index2 = doc.lastIndexOf(SIGNATURE_END_TAG);
      tmp = doc.substring(0, index1) + doc.substring(index2 + SIGNATURE_END_TAG.length(), doc.length());
      output.write(Goodies.fromStrToUTF8(tmp));

      return output;
   }
   
   private static final String SIGNED_DATA = "Datos_Firmados";
   private static final String SIGNATURE = "Firma";
   private static final String TAG_B_SIGNED_DATA = "<Datos_Firmados>";
   private static final String TAG_E_SIGNED_DATA = "</Datos_Firmados>";
   //private static final String REQUEST_HEADER = "Solicitud_Registro";
   private static final String SIGNATURE_BEGIN_TAG = "<Firma>";
   private static final String SIGNATURE_END_TAG = "</Firma>";
}