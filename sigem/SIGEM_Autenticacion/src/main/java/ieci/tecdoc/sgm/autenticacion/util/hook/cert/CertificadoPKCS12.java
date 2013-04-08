package ieci.tecdoc.sgm.autenticacion.util.hook.cert;
import ieci.tecdoc.sgm.autenticacion.util.cryptography.Certificate;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * Conector para la obtención de un certificado PKCS12.
 * 
 * @author IECISA
 *
 */
public class CertificadoPKCS12 implements Certificate
{
   public CertificadoPKCS12()
   {
	   System.out.println("Entro en CertificadoPKCS12");
   }
   
   /**
    * Método de carga de un certificado desde un almacén en el sistema de archivos.
    * @param path Cadena con la ubicación del almacén de claves en el sistema de archivos.
    * @param password Contraseña del alamacén de certificados.
    * @param moreInfo Información adicional necesaria para la obtención del certificado.
    */   
   public CertificadoFirmaX509Info loadCertificateInfo(String path, String password, 
                              String moreInfo) throws Exception
   {
      Security.addProvider(new BouncyCastleProvider());
      KeyStore keystore = null;
      CertificadoFirmaX509Info certInfo = new CertificadoFirmaX509Info();
      ArrayList certList = new ArrayList();
      java.security.cert.Certificate[] certChain = null;
      String alias = moreInfo;

      keystore = KeyStore.getInstance("PKCS12");
      keystore.load(new FileInputStream(path), password.toCharArray());
      certChain = keystore.getCertificateChain(alias);
      for (int i = 0; i < certChain.length;i++)
         certList.add(certChain[i]);
      
      certInfo.cert = (X509Certificate)keystore.getCertificate(alias);
      certInfo.privKey = (PrivateKey)(keystore.getKey(alias, password.toCharArray()));
      certInfo.certs = certChain;
      certInfo.certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
      
      return certInfo;
      
   }
   
}