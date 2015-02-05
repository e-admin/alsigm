package ieci.tecdoc.sgm.autenticacion.util.hook.cert;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;

import java.security.cert.X509Certificate;

public class CATecDocCertificate
{
   public CATecDocCertificate()
   {
   }

   /**
    * Verifica un certificado (validez, revocación).
    *
    * @param additionalInfo Información adicional (en formato xml). Normalmente
    * se utilizará si se quiere comprobar políticas.
    * @return La información necesaria extraída del certificado y si es válido.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public CertificadoAutenticacionX509Info verifyCertificate(X509Certificate cert,
                              String additionalInfo)
                              throws Exception
   {
      CertificadoAutenticacionX509Info certInfo = new CertificadoAutenticacionX509Info();
      String issuer, subject;
      int pos, pos1;

      issuer = cert.getIssuerDN().getName();
      if (issuer.compareTo(ISSUER) == 0)
      {
         certInfo.setValid(true);

         subject = cert.getSubjectDN().getName();
         pos = subject.indexOf("CN=");
         pos1 = subject.indexOf(", OU=");

         certInfo.setName(subject.substring(pos+3, pos1));
         certInfo.setFirstName("Nombre");
         certInfo.setSurName("Apellido1");
         certInfo.setSurName2("Apellido2");
         certInfo.setSerialNumber(Goodies.certGuion(cert.getSerialNumber().toString(16).toUpperCase()));
         certInfo.setIssuer(issuer.replaceAll("ST=", "S="));
         certInfo.setNIF(certInfo.getSerialNumber());
      }

      return certInfo;
   }

   private static final String ISSUER = "CN=CA TecDoc, OU=CEX Tecnologia Documental, O=IECISA, L=Madrid, ST=Madrid, C=ES";
}