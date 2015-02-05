package ieci.tecdoc.sgm.autenticacion.util.hook.cert;
import java.security.PrivateKey;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Clase que encapsula la información asociada a un firma digital X509.
 * 
 * @author IECISA
 *
 */
public class CertificadoFirmaX509Info 
{
   public CertificadoFirmaX509Info()
   {
      cert = null;
      privKey = null;
      certs = null;
   }
   
   public X509Certificate getCertificate()
   {
      return cert;
   }
   
   public PrivateKey getPrivateKey()
   {
      return privKey;
   }
   
   public CertStore getCertStore()
   {
      return certStore;
   }
   
   public Certificate[] getCertificates()
   {
      return certs;
   }
   
   protected X509Certificate cert;
   protected PrivateKey privKey;
   protected CertStore certStore;
   protected Certificate[] certs;
}