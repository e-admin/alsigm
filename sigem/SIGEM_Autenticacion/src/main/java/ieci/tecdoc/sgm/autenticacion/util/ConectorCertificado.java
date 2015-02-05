package ieci.tecdoc.sgm.autenticacion.util;

import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.base.miscelanea.ClassLoader;

import java.lang.reflect.Method;
import java.security.cert.X509Certificate;

/**
 * Clase utilizada para la carga y verificación de un certificado.
 * 
 * @author IECISA
 *
 */
public class ConectorCertificado
{
   private ConectorCertificado()
   {
   }

   /**
    * Utiliza el nombre de la clase a utilizar para manejar el tipo de certificado
    * e invoca al método encargado de realizar la carga.
    * 
    * @param className Nombre de la clase que implementa el interfaz de
    * certificados.
    * @param path Ruta del certificado.
    * @param password Contraseña del certificado.
    * @param moreInfo Información adicional (normalmente el alias del certificado.)
    * @return El certificado
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static CertificadoFirmaX509Info loadCertificateInfo(String className,
                              String path, String password, String moreInfo)
                              throws Exception 
   {
      Class[] parameterTypes = new Class[] {String.class, String.class, String.class};
      Method loadCertificateInfo;
      Object[] arguments = new Object[] {path, password, moreInfo};
      Class objectClass;
      CertificadoFirmaX509Info certificate;

      objectClass = ClassLoader.getClass(className);

      loadCertificateInfo = objectClass.getMethod("loadCertificateInfo", parameterTypes);
      certificate = (CertificadoFirmaX509Info)loadCertificateInfo.invoke(objectClass.newInstance(), arguments);

      return certificate;

   }

   /**
    * Verifica un certificado (validez, revocación) utilizando el nombre de la clase que
    * implementa la verificación para el tipo de certificado. 
    * 
    * @param className Nombre de la clase que implementa el interfaz de
    * certificados.
    * @param cert Certificado
    * @param info Información asociada a un conector (en formato xml). Esta
    * información se utiliza siempre que esté implicado este conector.
    * @param additionalInfo Información adicional (en formato xml). Normalmente 
    * se utilizará si se quiere comprobar políticas. Esta información está
    * asociada a una autenticación concreta.
    * @return La información necesaria extraída del certificado y si es válido.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static CertificadoAutenticacionX509Info verifyCertificate(String className, X509Certificate cert,
                              String info, String additionalInfo)
                              throws Exception 
   {
      Class[] parameterTypes = new Class[] {X509Certificate.class, String.class, String.class};
      Method verifyCertificate;
      Object[] arguments = new Object[] {cert, info, additionalInfo};
      Class objectClass;
      CertificadoAutenticacionX509Info certInfo;

      objectClass = ClassLoader.getClass(CLASS_NAME);

      verifyCertificate = objectClass.getMethod("verifyCertificate", parameterTypes);
      certInfo = (CertificadoAutenticacionX509Info)verifyCertificate.invoke(objectClass.newInstance(), arguments);
      
      return certInfo;
   }
   
   
   /**
    * Verifica un certificado (validez, revocación) utilizando el nombre de la clase que
    * implementa la verificación para el tipo de certificado. 
    * 
    * @param className Nombre de la clase que implementa el interfaz de
    * certificados.
    * @param cert Certificado
    * @return La información necesaria extraída del certificado y si es válido.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static CertificadoAutenticacionX509Info verifyCertificate(String className, X509Certificate cert)
   		throws Exception {
	   Class[] parameterTypes = new Class[] {X509Certificate.class}; //, String.class, String.class};
	   Method verifyCertificate;
	   Object[] arguments = new Object[] {cert}; //, info, additionalInfo};
	   Class objectClass;
	   CertificadoAutenticacionX509Info certInfo;

	   objectClass = ClassLoader.getClass(CLASS_NAME);

	   verifyCertificate = objectClass.getMethod("verifyCertificate", parameterTypes);
	   certInfo = (CertificadoAutenticacionX509Info)verifyCertificate.invoke(objectClass.newInstance(), arguments);
      
	   return certInfo;
   }

   private static final String CLASS_NAME = "ieci.tecdoc.sgm.autenticacion.util.hook.ValidacionCertificadoAdapter";
}