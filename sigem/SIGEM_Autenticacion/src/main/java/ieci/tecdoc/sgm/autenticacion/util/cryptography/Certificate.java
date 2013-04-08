package ieci.tecdoc.sgm.autenticacion.util.cryptography;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;

/**
 * Interfaz de manejo de certificados.
 */

public interface Certificate 
{

   /**
    * Carga un certificado. 
    * 
    * @param path Ruta del certificado.
    * @param password Contraseña del certificado.
    * @param moreInfo Información adicional (normalmente el alias del certificado.)
    * @return La información del certificado.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public CertificadoFirmaX509Info loadCertificateInfo(String path, String password, 
                              String moreInfo) throws Exception; 
}