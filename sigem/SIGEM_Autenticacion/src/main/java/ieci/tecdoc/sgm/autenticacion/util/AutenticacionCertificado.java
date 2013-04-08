package ieci.tecdoc.sgm.autenticacion.util;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;

import java.security.cert.X509Certificate;

public interface AutenticacionCertificado 
{
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
                              throws Exception; 

   /**
    * Verifica un certificado (validez, revocación). 
    * 
    * @param info Información asociada a un conector (en formato xml). Esta
    * información se utiliza siempre que esté implicado este conector.
    * @param additionalInfo Información adicional (en formato xml). Normalmente 
    * se utilizará si se quiere comprobar políticas. Esta información está
    * asociada a una autenticación concreta.
    * @return La información necesaria extraída del certificado y si es válido.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public CertificadoAutenticacionX509Info verifyCertificate(X509Certificate cert,
                              String info, String additionalInfo)
                              throws Exception; 
}