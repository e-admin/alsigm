
package es.ieci.tecdoc.isicres.admin.base.crypto;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

import es.ieci.tecdoc.isicres.admin.base.base64.Base64Util;

public final class CryptoUtil
{

   private CryptoUtil()
   {
   }
   
   public static byte[] generateSha1Hash(String msg) throws Exception
   {
      
      byte[]        hash;
      MessageDigest gen;
      
      gen = MessageDigest.getInstance("SHA-1");
      
      hash = gen.digest(msg.getBytes());
      
      return hash;
      
   }
   
   public static byte[] generateSha1HmacKey() throws Exception
   {
      
      byte[]       key;
      KeyGenerator gen;
      SecretKey    sk;
      
      gen = KeyGenerator.getInstance("HmacSHA1");
      
      sk = gen.generateKey();
      
      key = sk.getEncoded();
      
      return key;
      
   }
   
   public static byte[] generateSha1Hmac(String msg, byte[] key)
                        throws Exception
   {
      
      byte[]    hmac;
      SecretKey sk;
      Mac       gen;
      
      sk = new SecretKeySpec(key, "HmacSHA1");
      
      gen = Mac.getInstance("HmacSHA1");      
      gen.init(sk);
      
      hmac = gen.doFinal(msg.getBytes());
      
      return hmac;
      
   }

	  public static String encryptPass(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		  byte [] bytePass = password.getBytes("UTF-8");
		  //MessageDigest md = MessageDigest.getInstance("SHA-1");
		  MessageDigest md = MessageDigest.getInstance("MD5");
		  byte [] byteEncryptedPass = md.digest(bytePass);
		  return Base64Util.encode(byteEncryptedPass);
	  }
} // class
