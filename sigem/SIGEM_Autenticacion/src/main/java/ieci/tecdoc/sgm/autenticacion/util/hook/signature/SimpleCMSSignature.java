package ieci.tecdoc.sgm.autenticacion.util.hook.signature;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.autenticacion.util.signature.FirmaExt;
import ieci.tecdoc.sgm.autenticacion.util.signature.ValidacionFirmaInfo;
import ieci.tecdoc.sgm.autenticacion.util.signature.ValidacionFirmasInfo;
import ieci.tecdoc.sgm.autenticacion.util.signature.VerificarFirmaExt;
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
//import ieci.rtn.core.common.Miscelanea;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.security.cert.CertStore;
import java.util.Collection;
import java.util.Iterator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;


/**
 * Implementación de la firma CMS simple
 */
public class SimpleCMSSignature implements FirmaExt, VerificarFirmaExt
{
   public SimpleCMSSignature()
   {
   }

   /**
    * Método para realizar la firma.
    * @param data Datos a firmar.
    * @param additionalInfor Información adicional para la firma.
    * @param certificate Certificado que se utilizará para realizar la firma.
    * @return ByteArrayOutputStream Datos firmados.
    * @throws Exception En caso de producirse algún error.
    */
   public ByteArrayOutputStream sign(InputStream data, String additionalInfo, CertificadoFirmaX509Info certificate) throws Exception
   {
      return sign(Goodies.getBytes(data), additionalInfo, certificate);
   }

   /**
    * Método para realizar la firma.
    * @param data Datos a firmar.
    * @param additionalInfor Información adicional para la firma.
    * @param certificate Certificado que se utilizará para realizar la firma.
    * @return ByteArrayOutputStream Datos firmados.
    * @throws Exception En caso de producirse algún error.
    */
   public ByteArrayOutputStream sign(byte[] data, String additionalInfo, CertificadoFirmaX509Info certificate) throws Exception
   {
      CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
      CMSSignedData signedData; 
      CMSProcessable content = new CMSProcessableByteArray(data);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      
      signGen.addSigner(certificate.getPrivateKey(), certificate.getCertificate(), CMSSignedDataGenerator.DIGEST_SHA1);
      signGen.addCertificatesAndCRLs(certificate.getCertStore());

      signedData = signGen.generate(content, "BC");
      output.write(signedData.getEncoded());

      return output;
   }
   
   /**
    * Método de verificación de firma.
    * @param data Datos de la firma.
    * @param additionalInfo Información adicional para la validación.
    * @return SignaturesValidationInfo Información del resultado de la validación.
    */
   public ValidacionFirmasInfo verifySign(InputStream data, String additionalInfo) throws Exception
   {
      return verifySign(Goodies.getBytes(data), additionalInfo);
   }

   /**
    * Método de verificación de firma.
    * @param data Datos de la firma.
    * @param signature Cadena en base64 con la firma.
    * @return SignaturesValidationInfo Información del resultado de la validación.
    * @throws Exception En caso de producirse algún error.
    */ 
   public ValidacionFirmasInfo verifySign(byte[] data, String signature) throws Exception
   {
      ValidacionFirmasInfo infos = new ValidacionFirmasInfo();
      ValidacionFirmaInfo info;
      boolean valid = true;
      
      try
      {
         Security.addProvider(new BouncyCastleProvider());

         CMSSignedData s = new CMSSignedData(new CMSProcessableByteArray(data), 
              Base64Util.decode(signature));

         CertStore certs = s.getCertificatesAndCRLs("Collection", "BC");

         SignerInformationStore  signers = s.getSignerInfos();
         Collection              c = signers.getSigners();
         Iterator                it = c.iterator();

         while (it.hasNext())
         {
            SignerInformation   signer = (SignerInformation)it.next();
            Collection          certCollection = certs.getCertificates(signer.getSID());

            Iterator        certIt = certCollection.iterator();
            X509Certificate cert = (X509Certificate)certIt.next();

            valid = signer.verify(cert, "BC");
            if (!valid)
               break;

            info = new ValidacionFirmaInfo();
            info.setCertificate(cert);
            infos.add(info);
         }
      }
      catch (Exception exc)
      {
         valid = false;
      }
      
      infos.setValid(valid);
      
      return infos;
   }
   
   /**
    * Obtiene el documento incluido en la firma.
    * @param data Datos de la firma.
    * @additionalInfo Información adicional.
    * @return ByteArrayOutputStream Datos del documento. En este caso null.
    * @throws Exception En caso de producirse algún error.
    */
   public ByteArrayOutputStream getDocument(InputStream data, String additionalInfo) throws Exception
   {
      return null;
   }
}