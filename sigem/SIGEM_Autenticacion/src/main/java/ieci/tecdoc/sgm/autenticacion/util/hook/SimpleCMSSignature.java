package ieci.tecdoc.sgm.autenticacion.util.hook;

//import ieci.rtn.core.common.Miscelanea;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;


/**
 * Implementación de la firma CMS simple
 */
public class SimpleCMSSignature implements Firma 
{
   public SimpleCMSSignature()
   {
   }

   public ByteArrayOutputStream sign(InputStream data, CertificadoFirmaX509Info certificate) throws Exception
   {
   /*
      CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
      CMSSignedData signedData; 
      CMSProcessable content = new CMSProcessableByteArray(Miscelanea.getBytes(data));
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      
      signGen.addSigner(certificate.getPrivateKey(), certificate.getCertificate(), CMSSignedDataGenerator.DIGEST_SHA1);
      signGen.addCertificatesAndCRLs(certificate.getCertStore());

      signedData = signGen.generate(content, "BC");
      output.write(signedData.getEncoded());

      return output;
   */
   
      return sign(Goodies.getBytes(data), certificate);
   }

   public ByteArrayOutputStream sign(byte[] data, CertificadoFirmaX509Info certificate) throws Exception
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
}