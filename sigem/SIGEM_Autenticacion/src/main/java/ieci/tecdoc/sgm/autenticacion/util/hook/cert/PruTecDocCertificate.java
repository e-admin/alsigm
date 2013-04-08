package ieci.tecdoc.sgm.autenticacion.util.hook.cert;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;

import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

/**
 * Clase para la verificación de un certificado.
 * 
 * @author IECISA
 *
 */
public class PruTecDocCertificate
{
   private static final Logger logger = Logger.getLogger(PruTecDocCertificate.class);
    
   private static final String ISSUER = "CN=PruTecDoc";
    
   public PruTecDocCertificate()
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
                              String info, String additionalInfo)
                              throws Exception
   {
      logger.debug("Entra en metodo: verifyCertificate(X509Certificate cert, String info, String additionalInfo)");
       
      CertificadoAutenticacionX509Info certInfo = new CertificadoAutenticacionX509Info();
      String issuer, subject;
      int pos, pos1;
      
      issuer = cert.getIssuerDN().getName();
      if (issuer.compareTo(ISSUER) == 0)
      {
        certInfo.setValid(true);

        subject = cert.getSubjectDN().getName();
        
        pos = subject.indexOf("CN=NOMBRE ");
        pos1 = subject.indexOf(" ", pos+10);
        certInfo.setName(subject.substring(pos+10, pos1));
        pos = subject.indexOf("- NIF");
        certInfo.setSurName(subject.substring(pos1+1, pos-1));
        pos1 = subject.indexOf(", OU=");
        certInfo.setNIF(subject.substring(pos+6, pos1));
        certInfo.setSerialNumber(Goodies.certGuion(cert.getSerialNumber().toString(16).toUpperCase()));
        certInfo.setIssuer(issuer.replaceAll("ST=", "S="));
      }
      
      logger.debug("Sale del metodo: verifyCertificate(X509Certificate cert, String info, String additionalInfo)");
      return certInfo;
   }

}