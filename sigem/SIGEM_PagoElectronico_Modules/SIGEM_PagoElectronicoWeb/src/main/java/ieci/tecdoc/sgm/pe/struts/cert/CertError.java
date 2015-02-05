
package ieci.tecdoc.sgm.pe.struts.cert;

public class CertError 
{
   private static final int EC_ROOT = 1104000;

   /** Certificado cliente no encontrado. */     
   public static final int EC_NO_CERT_FOUND = EC_ROOT + 1;
   public static final String EM_NO_CERT_FOUND = 
   "No se puede recuperar el certificado cliente";
}
