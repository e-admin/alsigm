package ieci.tecdoc.sgm.autenticacion.util.cryptography;

import ieci.tecdoc.sgm.autenticacion.util.ConectorCertificado;
import ieci.tecdoc.sgm.autenticacion.exception.AutenticacionExcepcion;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadCodigosError;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info;
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conector;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import java.security.MessageDigest;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.engines.ElGamalEngine;
import org.bouncycastle.crypto.params.ElGamalKeyParameters;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class Crypto {

   /**
    * Obtiene el hash a partir de un stream de datos.
    * 
    * @param data Datos de los que se quiere obtener el hash.
    * @param algorithm Algoritmo de hash. Puede tener uno de los siguientes
    * valores: SHA-1, SHA-256, SHA-384, SHA-512, MD2, MD5.
    * @return El hash mencionado.
    * @throws java.lang.Exception Si se produce algún error.
    */
    
   public static String generateHash(InputStream data, String algorithm) throws AutenticacionExcepcion {
   
      String hash = null;
      MessageDigest gen;
      int available, count;
      byte[] buffer;

      logger.debug("generateHash: " + algorithm);

      try {
         gen = MessageDigest.getInstance(algorithm);

         buffer = new byte[CHUNK_SIZE];
         available = data.available();
         while (available > 0) {
            count = data.read(buffer);
            gen.update(buffer, 0, count);
            available = data.available();
         }
         buffer = gen.digest();
         
         hash = Base64Util.encode(buffer);
         
      }
      catch(Exception exc)
      {
         throw new AutenticacionExcepcion(SeguridadCodigosError.EC_HASH);
      }
      
      return hash;
   }

   /**
    * Obtiene el hash a partir de un stream de datos y utiliza para ello el
    * algoritmo SHA-1.
    * 
    * @param data Datos de los que se quiere obtener el hash.
    * @return El hash mecionado. 
    * @throws java.lang.Exception Si se produce algún error.
    */
    
   public static String generateHash(InputStream data) throws AutenticacionExcepcion {
   
      return generateHash(data, "SHA-1");   
   }

   /**
    * Obtiene el hash a partir de un stream de datos.
    * 
    * @param data Datos de los que se quiere obtener el hash.
    * @param algorithm Algoritmo de hash. Puede tener uno de los siguientes
    * valores: SHA-1, SHA-256, SHA-384, SHA-512, MD2, MD5.
    * @return El hash mencionado.
    * @throws java.lang.Exception Si se produce algún error.
    */
    
   public static String generateHash(byte[] data, String algorithm) throws AutenticacionExcepcion {
   
      String hash = null;
      MessageDigest gen;
      byte[] buffer;

      logger.debug("generateHash: " + algorithm);

      try {
         gen = MessageDigest.getInstance(algorithm);

         gen.update(data, 0, data.length);
         buffer = gen.digest();
         
         hash = Base64Util.encode(buffer);
         
      }
      catch(Exception exc)
      {
         throw new AutenticacionExcepcion(SeguridadCodigosError.EC_HASH);
      }
      
      return hash;
   }

   /**
    * Obtiene el hash a partir de un stream de datos y utiliza para ello el
    * algoritmo SHA-1.
    * 
    * @param data Datos de los que se quiere obtener el hash.
    * @return El hash mecionado. 
    * @throws java.lang.Exception Si se produce algún error.
    */
    
   public static String generateHash(byte[] data) throws AutenticacionExcepcion {
   
      return generateHash(data, "SHA-1");   
   }

   /**
    * Obtiene la fecha y hora del servidor.
    * 
    * @return La fecha y hora del servidor.
    */
   public static Date getTimeStamp() {
   
      return DateTimeUtil.getCurrentDateTime();
      
   }
   
   /**
    * Cifra unos datos.
    * 
    * @param data Datos a cifrar
    * @return Datos cifrados
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static byte[] encrypt(byte[] data) throws AutenticacionExcepcion
   {
      byte[] encrypted = null;
      //ElGamalEngine engine = new ElGamalEngine();
      BlowfishEngine engine1 = new BlowfishEngine();
      //ElGamalParameters params = null;
      //ElGamalKeyParameters parms;
      KeyParameter pars;
      //byte[] tmpData;
      
      logger.debug("encrypt");

      try {
        // params = new ElGamalParameters(BigInteger.valueOf(166324), BigInteger.valueOf(231231));
         pars = new KeyParameter(Crypto.class.getName().getBytes());
         engine1.init(true, pars);
         encrypted = new byte[engine1.getBlockSize()];
         engine1.processBlock(data, 0, encrypted, 0);
      }
      catch(Exception exc)
      {
         throw new AutenticacionExcepcion(SeguridadCodigosError.EC_ENCRYPT);
      }
      
      return encrypted;
   }
   
   /**
    * Cifra unos datos.
    * 
    * @param data Datos a cifrar
    * @return Datos cifrados en formato base64
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static String encryptBase64(byte[] data) throws AutenticacionExcepcion
   {
      return Base64Util.encode(encrypt(data));
   }
   
   /**
    * Descifra unos datos.
    * 
    * @param data Datos a descifrar
    * @return Datos descifrados
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static byte[] decrypt(byte[] data) throws AutenticacionExcepcion
   {
      byte[] decrypted = null;
      ElGamalEngine engine = new ElGamalEngine();
      String val1, val2;
      ElGamalParameters params = null;
      
      logger.debug("decrypt");

      try {
         val1 = Crypto.class.getName();
         val2 = val1.concat(new Integer(3).toString());
         params = new ElGamalParameters(new BigInteger(val1), new BigInteger(val2));
         engine.init(false, params);
         decrypted = engine.processBlock(data, 0, data.length);
      }
      catch(Exception exc)
      {
         throw new AutenticacionExcepcion(SeguridadCodigosError.EC_DECRYPT);
      }
      
      return decrypted;
   }
   
   /**
    * Descifra unos datos.
    * 
    * @param data Datos a descifrar
    * @return Datos descifrados en formato base64
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static byte[] decryptBase64(String data) throws AutenticacionExcepcion
   {
      byte dataDec[] = null;
      
      try
      {
         decrypt(Base64Util.decode(data));
      }
      catch (Exception exc)
      {
         throw new AutenticacionExcepcion(SeguridadCodigosError.EC_DECRYPT);
      }
      
      return dataDec;
   }
   
   /**
    * Recupera un certificado. La información del certificado se encuentra en
    * la tabla de configuración global.
    * 
    * @param webUser Si se trata de usuario Web Nivel 2.
    * @return La información del certificado.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static CertificadoFirmaX509Info cargarInfoCertificado() throws AutenticacionExcepcion
   {
      CertificadoFirmaX509Info certificate = null;
      String[] values;
      String certPath, certPwd, certInfo;
      
         certPath = "/usr/apache-tomcat-5.5.23/conf/certs/servidor.pfx";//"D:\\Software Desarrollo\\jakarta-tomcat-5.5.9\\conf\\certs\\servidor.pfx";
         certPwd = "1234";//GlobalInfo.GL_CERTIFICATE_PWD_WEBUSER;
         certInfo = "b9d1b6e6f650fb0b3edb1c72e00e8200_25cb6513-a6e5-436e-9d52-589262c3ebfd";//GlobalInfo.GL_CERTIFICATE_INFO_WEBUSER;
      //String[] keys = {certPath, certPwd, certInfo, GlobalInfo.GL_SERVER_CERT_HOOK};
      String path, password, moreInfo, hookId;
      Conector hook;

      
      logger.debug("getCertificate");

      try {
         //hook = catalog.getHook(values[3]);
         // TODO. Se debería comprobar que el tipo es de firma.
         certificate = ConectorCertificado.loadCertificateInfo("ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoFirmaX509Info",
        		 certPath,certPwd, certInfo);
      }
      catch(Exception exc)
      {
         throw new AutenticacionExcepcion(SeguridadCodigosError.EC_GET_CERTIFICATE);
      }
     
      return certificate;
      
   }

   private static final int CHUNK_SIZE = 10240; 
   private static final Logger logger = Logger.getLogger(Crypto.class);
}