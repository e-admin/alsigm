
package ieci.tecdoc.sgm.autenticacion.util.hook.signature;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.autenticacion.util.signature.FirmaExt;
import ieci.tecdoc.sgm.autenticacion.util.signature.ValidacionFirmaInfo;
import ieci.tecdoc.sgm.autenticacion.util.signature.ValidacionFirmasInfo;
import ieci.tecdoc.sgm.autenticacion.util.signature.VerificarFirmaExt;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
//import ieci.rtn.core.common.Miscelanea;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Clase encapsula el corportamiento de una firma digital FullCMS.
 * 
 * @author IECISA.
 *
 */
public class FullCMSSignature implements FirmaExt, VerificarFirmaExt {
   
   public FullCMSSignature() {
   }
   
   /**
    * Método para realizar un firma de contenido.
    * @param data Datos a firmar.
    * @param additionalInfo Información adicional necesaria para la firma.
    * @param certificate Certificado a utilizar para realizar la firma.
    * @return ByteArrayOutputStream Con la información firmada.
    * @throws Exception En caso de producirse algún error.
    */   
   public ByteArrayOutputStream sign(InputStream data, String additionalInfo, CertificadoFirmaX509Info certificate) throws Exception
   {
      return sign(Goodies.getBytes(data), additionalInfo, certificate);
   }

   /**
    * Método para realizar una firma de contenido.
    * @param data Datos a firmar.
    * @param additionalInfo Información adicional para la firma.
    * @param certificate Certificado a utilizar para la firma.
    * @return ByteArrayOutputStream Datos firmados.
    * @throws Exception En caso de producirse algún error.
    */
   private ByteArrayOutputStream sign(byte[] data, String additionalInfo, CertificadoFirmaX509Info certificate) throws Exception
   {
      CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
      CMSSignedData signedData; 
      CMSProcessable content = new CMSProcessableByteArray(data);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      
      signGen.addSigner(certificate.getPrivateKey(), certificate.getCertificate(), CMSSignedDataGenerator.DIGEST_SHA1);
      signGen.addCertificatesAndCRLs(certificate.getCertStore());

      signedData = signGen.generate(content, true, "BC");
      output.write(signedData.getEncoded());

      return output;
   }
   
   /**
    * Método de verificación de firma.
    * @param data Datos a verficiar.
    * @param additionalInfo Información adicional a utilizar para la verificación.
    * @return SignatruesValidationInfo Información sobre el resultado de la validación.
    * @throws Exception En caso de producirse algún error.
    */
   public ValidacionFirmasInfo verifySign(InputStream data, String additionalInfo) throws Exception
   {
      return verifySign(Goodies.getBytes(data), additionalInfo);
   }

   /**
    * Método de verificación de firma.
    * @param data Datos a verficiar.
    * @param additionalInfo Información adicional a utilizar para la verificación.
    * @return SignatruesValidationInfo Información sobre el resultado de la validación.
    * @throws Exception En caso de producirse algún error.
    */
   public ValidacionFirmasInfo verifySign(byte[] data, String additionalInfo) throws Exception
   {
      ValidacionFirmasInfo infos = new ValidacionFirmasInfo();
      ValidacionFirmaInfo info;
      boolean valid = true;
      
      try
      {
         Security.addProvider(new BouncyCastleProvider());

         CMSSignedData s = new CMSSignedData(data);

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
    * Método que obtiene el documento incluido en la firma.
    * @param data Datos de la firma.
    * @param additionalInfo Información adicional necesaria para la validación.
    * @return ByteArrayOutputStream Datos del documento.
    * @throws Exception En caso de producirse algún error.
    */
   public ByteArrayOutputStream getDocument(InputStream data, String additionalInfo) throws Exception
   {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      
      Security.addProvider(new BouncyCastleProvider());

      CMSSignedData s = new CMSSignedData(Goodies.getBytes(data));
      CMSProcessable doc = s.getSignedContent();
      doc.write(output);
      
      return output;
      
   }
   
}
